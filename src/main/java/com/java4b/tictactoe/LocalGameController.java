package com.java4b.tictactoe;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LocalGameController extends GameController {
    GameState gameState;

    @FXML
    public void initialize() {
        super.initialize();

        name1Label.setText(gameState.getPlayer1().getName() + ":");
        name2Label.setText(gameState.getPlayer2().getName() + ":");
        avatar1ImageView.setImage(gameState.getPlayer1().getAvatar().getImage());
        avatar2ImageView.setImage(gameState.getPlayer2().getAvatar().getImage());

        Platform.runLater(() -> {
            try {
                showSettings();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void showSettings() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Stage settingsStage = new Stage();
        settingsStage.setScene(new Scene(loader.load()));
        ((SettingsController) loader.getController()).initData(this, gameState);
        setSideMenuStage(settingsStage);
    }

    protected void showGameOver(String gameWinText) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("endScreen.fxml"));
        Stage gameOverStage = new Stage();
        gameOverStage.setScene(new Scene(loader.load()));
        ((EndGameController) loader.getController()).initData(this, gameWinText);
        setSideMenuStage(gameOverStage);
    }

    //NOTE: Sort later
    public void startGame(Player firstPlayer) throws IOException {
        activePlayerLabel.getScene().getRoot().setOpacity(1.0);
        gameState.setActivePlayer(firstPlayer);
        setActivePlayerLabel();
    }

    @FXML
    protected void onCellClicked(MouseEvent event) throws IOException {
        // Get the object that the event was triggered on. In this case, it's the StackPane cell that was clicked on.
        StackPane selectedCell = (StackPane)event.getSource();
        int indexOfSelected = cells.indexOf(selectedCell);

        // Check to see if that cell has already been filled.
        if (gameState.isCellEmpty(indexOfSelected)) {
            playCell(indexOfSelected);
        } else {
            animateError(selectedCell); // Invalid move feedback
        }
    }

    protected void playCell(int cellIndex) throws IOException {

        // Update the view
        StackPane cell = cells.get(cellIndex);
        BackgroundSize imageSize = new BackgroundSize(0.70, 0.70, true, true, false, false);
        cell.setBackground(new Background(new BackgroundImage(gameState.getActivePlayer().getAvatar().getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imageSize)));

        cell.getStyleClass().clear(); // Remove hover effect

        // Update the game state
        gameState.playCell(cellIndex);

        if (gameState.isAWin() || gameState.isATie())
            declareWinner();
        else
            setupNextTurn();
    }

    protected void setupNextTurn() throws IOException {
        setActivePlayerLabel();
        setCursorAsAvatar();
    }

    protected void setCursorAsAvatar() {
        if (((GridPane) cell0.getParent()).isHover()) {
            String fileName = gameState.getActivePlayer().getAvatar().getFileName();
            Image image = new Image(getClass().getResource(fileName).toString(), 50, 50, true, false);
            Scene scene = activePlayerLabel.getScene();

            // Change the cursor to the active player's avatar and move the cursor's "hotspot" to the center of the image
            scene.setCursor(new ImageCursor(image, image.getWidth() / 2.0, image.getHeight() / 2.0));
        }
    }

    protected void setActivePlayerLabel() {
        activePlayerLabel.setText(gameState.getActivePlayer().getName() + "'s turn");
    }

    protected void declareWinner() throws IOException {
        String resultMessage;
        Stage stage = (Stage) activePlayerLabel.getScene().getWindow();
        if (gameState.isAWin()) {
//            resultMessage = gameState.getActivePlayer().getAvatar() == Avatar.ANCHOR ? "Anchor wins!" : "Life-Saver wins!";
            resultMessage = gameState.getActivePlayer().getName() + " wins!";
        } else {
            // No winner, check if the game is a draw
            resultMessage = "It's a tie!";
        }

        activePlayerLabel.setText(resultMessage);
        showGameOver(resultMessage);
    }
}