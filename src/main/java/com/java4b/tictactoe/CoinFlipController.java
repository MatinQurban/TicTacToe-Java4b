package com.java4b.tictactoe;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

public class CoinFlipController {
    @FXML
    private Cylinder coinShape;

    @FXML
    private ImageView anchorImage;

    @FXML
    private ImageView flotationImage;

    @FXML
    public void initialize() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GOLDENROD);
        material.setSpecularColor(Color.PALEGOLDENROD);
        coinShape.setMaterial(material);

        int startingAngle = 30;
        Point3D rotationAxis = new Point3D(1.0, 0.0, 0.0);

        coinShape.setRotationAxis(rotationAxis);
        coinShape.setRotate(startingAngle);
        anchorImage.setRotationAxis(rotationAxis);
        anchorImage.setRotate(270 + startingAngle);
        flotationImage.setRotationAxis(rotationAxis);
        flotationImage.setRotate(270 + startingAngle);
        flotationImage.setOpacity(0.0);
    }

    @FXML
    protected void onFlipButtonClick() {
        Point3D rotationPoint = new Point3D(1, 0.0, 0.0);
        int totalTime = 3600;
        int numHalfRotations = 9;
        int timePerRotation = totalTime / numHalfRotations;
        int fromAngle = 30;
        int toAngle = fromAngle + 180;
        int lowPos = 0;
        int highPos = -300;

        RotateTransition cylinderRotate = rotateTransition(coinShape, timePerRotation, rotationPoint,
                fromAngle, toAngle, numHalfRotations);

        SequentialTransition cylinderTranslate = upDownTransition(coinShape, totalTime, lowPos, highPos);
        ParallelTransition totalCylinderTransition = new ParallelTransition(coinShape, cylinderRotate, cylinderTranslate);

        ParallelTransition xLabelTransition = xLabelTransition(anchorImage, totalTime, numHalfRotations, rotationPoint,
                fromAngle, toAngle, lowPos, highPos, 1.0, 0.0);

        ParallelTransition oLabelTransition = oLabelTransition(flotationImage, totalTime, numHalfRotations,
                rotationPoint, fromAngle, toAngle, lowPos, highPos, 0.0, 1.0);

        totalCylinderTransition.play();
        xLabelTransition.play();
        oLabelTransition.play();
    }

    private ParallelTransition xLabelTransition(Node node, int duration, int numCycles,
                                               Point3D rotationPoint, int fromAngle, int toAngle,
                                               int lowPos, int highPos,
                                               double fromOpacity, double toOpacity) {

        int timePerRotation = duration / numCycles;
        SequentialTransition labelRotate = rotateImageTransition(node, timePerRotation, rotationPoint,
                (fromAngle + 270), (toAngle + 270), numCycles);

        SequentialTransition labelTranslate = upDownTransition(node, duration, lowPos, highPos);
        SequentialTransition labelFade = fadeTransition(node, timePerRotation, fromAngle, fromOpacity, toOpacity, numCycles);

        return new ParallelTransition(node, labelRotate, labelTranslate, labelFade);
    }

    private ParallelTransition oLabelTransition(Node node, int duration, int numCycles,
                                                Point3D rotationPoint, int fromAngle, int toAngle,
                                                int lowPos, int highPos,
                                                double fromOpacity, double toOpacity) {

        int timePerRotation = duration / numCycles;
        SequentialTransition labelRotate = rotateImageTransition(node, timePerRotation, rotationPoint,
                (fromAngle + 270), (toAngle + 270), numCycles);

        SequentialTransition labelTranslate = upDownTransition(node, duration, lowPos, highPos);
        SequentialTransition labelFade = fadeTransition(node, timePerRotation, fromAngle, fromOpacity, toOpacity, numCycles);

        return new ParallelTransition(node, labelRotate, labelTranslate, labelFade);
    }

    private SequentialTransition rotateImageTransition(Node node, int duration, Point3D rotationPoint,
                                              int fromAngle, int toAngle, int numCycles) {
        node.setRotationAxis(rotationPoint);
        SequentialTransition totalRotation = new SequentialTransition(node);

        for (int i = 0; i < numCycles; ++i) {
            RotateTransition rotation = new RotateTransition(Duration.millis(duration), node);
            rotation.setFromAngle(fromAngle + (i % 2) * 180);
            rotation.setToAngle(toAngle + (i % 2) * 180);
            System.out.println(node.getRotate());
            rotation.setInterpolator(Interpolator.LINEAR);

            totalRotation.getChildren().add(rotation);
        }

        return totalRotation;
    }

    private RotateTransition rotateTransition(Node node, int duration, Point3D rotationPoint,
                                              int fromAngle, int toAngle, int numCycles) {
        node.setRotationAxis(rotationPoint);

        RotateTransition rotation = new RotateTransition(Duration.millis(duration), node);
        rotation.setFromAngle(fromAngle);
        rotation.setToAngle(toAngle);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setCycleCount(numCycles);

        return rotation;
    }

    private SequentialTransition upDownTransition(Node node, int duration, int lowPos, int highPos) {
        TranslateTransition upTranslation = new TranslateTransition(Duration.millis(duration / 2.0), node);
        upTranslation.setFromY(lowPos);
        upTranslation.setToY(highPos);
        upTranslation.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition downTranslation = new TranslateTransition(Duration.millis(duration / 2.0), node);
        downTranslation.setFromY(highPos);
        downTranslation.setToY(lowPos);
        downTranslation.setInterpolator(Interpolator.EASE_IN);

        return new SequentialTransition(node, upTranslation, downTranslation);
    }

    private SequentialTransition fadeTransition(Node node, int duration, int angleOffset,
                                                double fromOpacity, double toOpacity, int numCycles) {

        SequentialTransition totalFade = new SequentialTransition(node);

        int durationOffset = (int)(duration * angleOffset / 180.0);
        int duration1 = duration - durationOffset;
        int duration2 = durationOffset;
        double opacity1 = fromOpacity;
        double opacity2 = toOpacity;

        for (int i = 0; i < numCycles; ++i) {
            FadeTransition fade1 = new FadeTransition(Duration.millis(duration1), node);
            fade1.setFromValue(opacity1);
            fade1.setToValue(opacity1);
            fade1.setInterpolator(Interpolator.DISCRETE);

            FadeTransition fade2 = new FadeTransition(Duration.millis(duration2), node);
            fade2.setFromValue(opacity2);
            fade2.setToValue(opacity2);
            fade2.setInterpolator(Interpolator.DISCRETE);

            totalFade.getChildren().add(fade1);
            totalFade.getChildren().add(fade2);

            opacity1 = (opacity1 == 1.0 ? 0 : 1.0);
            opacity2 = (opacity2 == 1.0 ? 0 : 1.0);
        }

        return totalFade;
    }
}