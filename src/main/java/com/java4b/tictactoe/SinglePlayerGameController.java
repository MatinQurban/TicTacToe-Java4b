package com.java4b.tictactoe;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SinglePlayerGameController{

    @FXML
    private Label activePlayerLabel;

    @FXML
    private StackPane cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8;

    GameState gameState;
    ArrayList<StackPane> cells;

    // The initialize() method is automatically called after the @FXML fields have been injected
    @FXML
    public void initialize() {

        gameState = new GameState("Player 1", Avatar.ANCHOR, "Computer", Avatar.LIFE_SAVER);
        cells = new ArrayList<>(Arrays.asList(cell0, cell1, cell2, cell3,
                cell4, cell5, cell6, cell7, cell8));

        setActivePlayerLabel();
    }

    @FXML
    protected void onCellClicked(MouseEvent event) {
        // Get the object that the event was triggered on. In this case, it's the ImageView square that was clicked on.
        StackPane selectedCell = (StackPane)event.getSource();
        int indexOfSelected = cells.indexOf(selectedCell);

        // Check to see if that square has already been filled. If not, insert the active player's avatar icon into
        // the square, change the map entry to show it is now filled, and change the active player for the next turn.
        if (gameState.isCellEmpty(indexOfSelected)) {
            BackgroundSize imageSize = new BackgroundSize(0.70, 0.70, true, true, false, false);
            selectedCell.setBackground(new Background(new BackgroundImage(gameState.getActivePlayer().getAvatar().getImage(),
                   BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imageSize)));

            gameState.playCell(indexOfSelected);

            gameState.toggleActivePlayer();
            setActivePlayerLabel();
            setCursorImage();
            selectedCell.getStyleClass().clear();
        }
        else {
            animateError(selectedCell);
        }
    }

    @FXML
    protected void onMouseOverBoard(MouseEvent event) {
        setCursorImage();
    }

    @FXML
    protected void onMouseExitBoard(MouseEvent event) {
        Scene scene = activePlayerLabel.getScene();
        scene.setCursor(Cursor.DEFAULT);
    }

    @FXML
    protected void switchToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) activePlayerLabel.getScene().getWindow();
        TicTacToeApplication.switchScene("MainMenu", stage);
    }

    private void setCursorImage() {
        String fileName = gameState.getActivePlayer().getAvatar().getFileName();
        Image image = new Image(getClass().getResource(fileName).toString(), 50, 50, true, false);
        Scene scene = activePlayerLabel.getScene();
        scene.setCursor(new ImageCursor(image, image.getWidth() / 2.0, image.getHeight() / 2.0));
    }

    private void setActivePlayerLabel() {
        activePlayerLabel.setText(gameState.getActivePlayer().getName() + "'s turn");
    }

    private void animateError(StackPane cell) {
        Image errorImage = new Image(getClass().getResource("placement_error.png").toString(),
                cell.getWidth(), cell.getHeight(), true, false);

        ImageView errorImageView = new ImageView(errorImage);
        errorImageView.setOpacity(0.0);

        // TODO: Need to remove errorImageView from the cell's children list after animation is over
        cell.getChildren().add(errorImageView);

        FadeTransition errorFade = new FadeTransition(Duration.millis(800), errorImageView);
        errorFade.setFromValue(1.0);
        errorFade.setToValue(0.0);

        GridPane gridPane = (GridPane) cell.getParent();
        TranslateTransition shakeBoard = new TranslateTransition(Duration.millis(50), gridPane);
        shakeBoard.setByX(15);
        shakeBoard.setAutoReverse(true);
        shakeBoard.setCycleCount(10);

        errorFade.play();
        shakeBoard.play();
    }
}