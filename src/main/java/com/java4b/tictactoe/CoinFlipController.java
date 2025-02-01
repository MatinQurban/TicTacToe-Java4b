package com.java4b.tictactoe;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

public class CoinFlipController {
    @FXML
    private Cylinder coinShape;

    @FXML
    private Label xLabel;

    @FXML
    private Label oLabel;

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
    }

    @FXML
    protected void onFlipButtonClick() {
        Point3D rotationPoint = new Point3D(1, 0.0, 0.1);
        int totalTime = 3600;
        int numRotations = 4;
        int timePerRotation = totalTime / numRotations;
        int fromAngle = 30;
        int toAngle = fromAngle + 360;
        int lowPos = 0;
        int highPos = -300;

        RotateTransition cylinderRotate = rotateTransition(coinShape, timePerRotation, rotationPoint,
                fromAngle, toAngle, numRotations);

        SequentialTransition cylinderTranslate = upDownTransition(coinShape, totalTime, lowPos, highPos);
        ParallelTransition totalCylinderTransition = new ParallelTransition(coinShape, cylinderRotate, cylinderTranslate);

        ParallelTransition xLabelTransition = xLabelTransition(anchorImage, totalTime, numRotations, rotationPoint,
                fromAngle, toAngle, lowPos, highPos, 1.0, 0.0);

        ParallelTransition oLabelTransition = labelTransition(flotationImage, totalTime, numRotations,
                rotationPoint, fromAngle, toAngle, lowPos, highPos, 0.0, 1.0);

        totalCylinderTransition.play();
        xLabelTransition.play();
        oLabelTransition.play();

//        totalLabelTransition.play();
//        oTotalLabelTransition.play();
//        coinRotate.play();

    }

    private ParallelTransition labelTransition(Node node, int duration, int numCycles,
                                               Point3D rotationPoint, int fromAngle, int toAngle,
                                               int lowPos, int highPos,
                                               double fromOpacity, double toOpacity) {

        int timePerRotation = duration / numCycles;
        RotateTransition labelRotate = rotateTransition(node, timePerRotation, rotationPoint,
                (fromAngle + 90), (toAngle + 90), numCycles);

        SequentialTransition labelTranslate = upDownTransition(node, duration, lowPos, highPos);
        SequentialTransition labelFade = fadeTransition(node, timePerRotation, fromAngle, fromOpacity, toOpacity, numCycles);

        return new ParallelTransition(node, labelRotate, labelTranslate, labelFade);
//
//        return new ParallelTransition(node, labelRotate, labelTranslate);
    }

    private ParallelTransition xLabelTransition(Node node, int duration, int numCycles,
                                               Point3D rotationPoint, int fromAngle, int toAngle,
                                               int lowPos, int highPos,
                                               double fromOpacity, double toOpacity) {

        int timePerRotation = duration / numCycles;
        RotateTransition labelRotate = rotateTransition(node, timePerRotation, rotationPoint,
                (fromAngle + 270), (toAngle + 270), numCycles);

        SequentialTransition labelTranslate = upDownTransition(node, duration, lowPos, highPos);
        SequentialTransition labelFade = fadeTransition(node, timePerRotation, fromAngle, fromOpacity, toOpacity, numCycles);

        return new ParallelTransition(node, labelRotate, labelTranslate, labelFade);
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
//        upTranslation.setInterpolator(Interpolator.LINEAR);

        TranslateTransition downTranslation = new TranslateTransition(Duration.millis(duration / 2.0), node);
        downTranslation.setFromY(highPos);
        downTranslation.setToY(lowPos);
        downTranslation.setInterpolator(Interpolator.EASE_IN);
//        downTranslation.setInterpolator(Interpolator.LINEAR);

        return new SequentialTransition(node, upTranslation, downTranslation);
    }

    private SequentialTransition fadeTransition(Node node, int duration, int angleOffset,
                                                double fromOpacity, double toOpacity, int numCycles) {

        int originalFadeDuration = (duration / 2) - (int)((duration / 2.0) * (angleOffset / 180.0));
//        originalFadeDuration = duration / 2;
        FadeTransition originalFade = new FadeTransition(Duration.millis(originalFadeDuration), node);
        originalFade.setFromValue(fromOpacity);
        originalFade.setToValue(toOpacity);
        originalFade.setInterpolator(Interpolator.DISCRETE);

        int reverseFadeDuration = (duration / 2) + (int)((duration / 2.0) * (angleOffset / 180.0));
        reverseFadeDuration = duration / 2;
        FadeTransition reverseFade = new FadeTransition(Duration.millis(reverseFadeDuration), node);
        reverseFade.setFromValue(toOpacity);
        reverseFade.setToValue(fromOpacity);
        reverseFade.setInterpolator(Interpolator.DISCRETE);

        int thirdFadeDuration = (int)((duration / 2.0) * (angleOffset / 180.0));
        FadeTransition thirdFade = new FadeTransition(Duration.millis(thirdFadeDuration), node);
        thirdFade.setFromValue(fromOpacity);
        thirdFade.setToValue(fromOpacity);
        thirdFade.setInterpolator(Interpolator.DISCRETE);

        SequentialTransition fadeTransition = new SequentialTransition(node, originalFade, reverseFade, thirdFade);
        fadeTransition.setCycleCount(numCycles);

        return fadeTransition;
    }
}