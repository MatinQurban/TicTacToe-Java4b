package com.java4b.tictactoe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JoinQueueController {

    Stage mainStage;

    @FXML
    protected TextField serverAddressField, portNumberField,
            gamerTagField;

    @FXML
    protected Label networkErrorLabel, nameErrorLabel, searchingLabel,
            gameFoundLabel;

    @FXML
    protected Button findGameButton, cancelButton, mainMenuButton;

    protected PlayerClient playerClient;

    @FXML
    public void initialize() {
        networkErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        searchingLabel.setVisible(false);
        gameFoundLabel.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);

        Platform.runLater(() -> gamerTagField.requestFocus());
    }

    public void initData(PlayerClient caller, Stage mainStage) throws IOException {
        this.playerClient = caller;
        this.mainStage = mainStage;
        Stage joinQueueStage = (Stage) findGameButton.getScene().getWindow();


        joinQueueStage.initModality(Modality.APPLICATION_MODAL);
        joinQueueStage.initStyle(StageStyle.UNDECORATED);
        joinQueueStage.show();

        mainStage.getScene().getRoot().setVisible(false);
        joinQueueStage.setX(mainStage.getX() + mainStage.getWidth() / 2.0 - joinQueueStage.getWidth() / 2.0);
        joinQueueStage.setY(mainStage.getY() + mainStage.getHeight() / 2.0 - joinQueueStage.getHeight() / 2.0 + 10);
    }

    @FXML
    protected void onFindGameButtonClicked() throws IOException, InterruptedException {
        String serverAddress = serverAddressField.getText();
        String portNumberText = portNumberField.getText();
        String gamerTag = gamerTagField.getText();

        if (serverAddress.isEmpty() || portNumberText.isEmpty()) {
            networkErrorLabel.setText("Please enter the server network details");
            networkErrorLabel.setVisible(true);
            return;
        }
        if (gamerTag.isEmpty()) {
            nameErrorLabel.setText("Please enter your gamer tag");
            nameErrorLabel.setVisible(true);
            return;
        }

        playerClient.respondToFindGameClicked(serverAddress, Integer.parseInt(portNumberText), gamerTag);

//        networkErrorLabel.setVisible(false);
//        nameErrorLabel.setVisible(false);
//        findGameButton.setDisable(true);
//        findGameButton.setVisible(false);
//        searchingLabel.setVisible(true);
//        cancelButton.setVisible(true);
//        cancelButton.setDisable(false);
//        serverAddressField.setDisable(true);
//        portNumberField.setDisable(true);
//        gamerTagField.setDisable(true);

//        Player firstPlayer = (player1Toggle.isSelected() ? gameState.getPlayer1() : gameState.getPlayer2());
//        Stage stage = (Stage) startButton.getScene().getWindow();
//        stage.close();
//
//        caller.startGame(firstPlayer);
    }

    @FXML
    protected void onMainMenuButtonClicked() {
        Stage stage = (Stage) findGameButton.getScene().getWindow();
        stage.close();
        mainStage.getScene().getRoot().setVisible(true);
    }

    public void processNameUnavailableMessage(String gamerTag) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nameErrorLabel.setText(gamerTag + " is unavailable, please try another one");
                nameErrorLabel.setVisible(true);
                gamerTagField.setText("");
            }
        });
    }

    public void processSearchingForGameMessage() {
        networkErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        findGameButton.setDisable(true);
        findGameButton.setVisible(false);
        searchingLabel.setVisible(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        serverAddressField.setDisable(true);
        portNumberField.setDisable(true);
        gamerTagField.setDisable(true);
    }

    public void processGameFoundMessage() {
        searchingLabel.setVisible(false);
        gameFoundLabel.setVisible(true);
    }
}
