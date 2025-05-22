package com.java4b.tictactoe;

import com.java4b.tictactoe.messages.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class OnlineMPGameController extends GameController {

    private String myGamerTag;
    private Avatar myAvatar;
    private PlayerClient playerClient;

    @FXML
    public void initialize() {
        super.initialize();

        gameModeLabel.setText("Online Multiplayer Game");
        disableMove();

        Platform.runLater(() -> {
            playerClient.respondToReadyForGameMessages();
        });
    }

    public void initData(PlayerClient caller, String myGamerTag, String opponentGamerTag, Avatar myAvatar,
                         Avatar opponentAvatar,String firstPlayer) {

        this.playerClient = caller;
        this.myGamerTag = myGamerTag;
        this.myAvatar = myAvatar;

        name1Label.setText(myGamerTag + ":");
        name2Label.setText(opponentGamerTag + ":");
        avatar1ImageView.setImage(myAvatar.getImage());
        avatar2ImageView.setImage(opponentAvatar.getImage());
        setActivePlayerLabel(firstPlayer + "'s turn");
    }

    protected void setActivePlayerLabel(String labelText) {
        activePlayerLabel.setText(labelText);
    }

    protected void disableMove() {
        gridPane.setDisable(true);
    }

    protected void enableMove() {
        Platform.runLater(() -> gridPane.setDisable(false));
    }

    public void processPlayerTurnMessage(PlayerTurnMessage message) {
        Platform.runLater(() -> {
            String activePlayer = message.getGamerTag();
            String labelText = activePlayer + "'s turn";

            if (activePlayer.equals(myGamerTag)) {
                labelText = "Your turn";
                enableMove();
            }

            setActivePlayerLabel(labelText);
        });
    }

    public void processInvalidMoveMessage(int move) {
        Platform.runLater(() -> {
            animateError(cells.get(move));
        });
    }

    public void processMakeMoveMessage(int move, Avatar avatar) {
        Platform.runLater(() -> {
            try {
                playCell(move, avatar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void processGameOverMessage(String winner) throws IOException {
        Platform.runLater(() -> {
            try {
                declareWinner(winner);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @FXML
    protected void onCellClicked(MouseEvent event) throws IOException {
        disableMove();
        StackPane selectedCell = (StackPane)event.getSource();
        playerClient.respondToCellClicked(cells.indexOf(selectedCell));
    }

    @Override
    protected void setCursorAsAvatar() {
        if (gridPane.isHover()) {
            String fileName = myAvatar.getFileName();
            Image image = new Image(getClass().getResource(fileName).toString(), 50, 50, true, false);
            Scene scene = activePlayerLabel.getScene();

            // Change the cursor to the active player's avatar and move the cursor's "hotspot" to the center of the image
            scene.setCursor(new ImageCursor(image, image.getWidth() / 2.0, image.getHeight() / 2.0));
        }
    }

    protected void playCell(int cellIndex, Avatar avatar) throws IOException {

        // Update the view
        StackPane cell = cells.get(cellIndex);
        BackgroundSize imageSize = new BackgroundSize(0.70, 0.70, true, true, false, false);
        cell.setBackground(new Background(new BackgroundImage(avatar.getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imageSize)));

        cell.getStyleClass().clear(); // Remove hover effect
    }

    protected void declareWinner(String winner) throws IOException {
        String resultMessage;
        Stage stage = (Stage) activePlayerLabel.getScene().getWindow();
        if (winner != null) {
            resultMessage = winner + " wins!";
        } else {
            resultMessage = "It's a tie!";
        }

        activePlayerLabel.setText(resultMessage);
        showGameOver(resultMessage);
    }
}