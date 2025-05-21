package com.java4b.tictactoe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JoinQueueController {

    Stage mainStage;

    @FXML
    protected TextField serverAddressField, portNumberField,
            gamerTagField, gameNameField, gamePasswordField;

    @FXML
    protected Label networkErrorLabel, nameErrorLabel, loggedInLabel, searchingLabel,
    gameFoundLabel, serverAddressLabel, portNumberLabel, gameNameLabel, gamePasswordLabel;

    @FXML
    protected Button loginButton, findGameButton, cancelButton, mainMenuButton, createGameButton;

    protected PlayerClient playerClient;

    @FXML
    public void initialize() {
        networkErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        loggedInLabel.setVisible(false);
        searchingLabel.setVisible(false);
        gameFoundLabel.setVisible(false);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        createGameButton.setVisible(false);
        createGameButton.setDisable(true);

        Platform.runLater(() -> gamerTagField.requestFocus());
    }

    public void initData(PlayerClient caller, Stage mainStage) throws IOException {
        this.playerClient = caller;
        this.mainStage = mainStage;
        Stage joinQueueStage = (Stage) findGameButton.getScene().getWindow();


        joinQueueStage.initModality(Modality.APPLICATION_MODAL);
        joinQueueStage.initStyle(StageStyle.UNDECORATED);
        joinQueueStage.initOwner(mainStage);
        joinQueueStage.show();

//        mainStage.getScene().getRoot().setVisible(false);
        joinQueueStage.setX(mainStage.getX() + mainStage.getWidth() / 2.0 - joinQueueStage.getWidth() / 2.0);
        joinQueueStage.setY(mainStage.getY() + mainStage.getHeight() / 2.0 - joinQueueStage.getHeight() / 2.0 + 10);
    }

    @FXML
    protected void onLoginButtonClicked() {
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

        playerClient.respondToLoginClicked(serverAddress, Integer.parseInt(portNumberText), gamerTag);
    }

    @FXML
    protected void onFindGameButtonClicked() throws IOException, InterruptedException {
        playerClient.respondToFindGameClicked();
    }

    @FXML
    protected void onCancelButtonClicked() {
        playerClient.respondToCancelButtonClicked();
    }

    @FXML
    protected void onMainMenuButtonClicked() throws IOException {
        Stage stage = (Stage) findGameButton.getScene().getWindow();
        stage.close();
        TicTacToeApplication.switchScene("MainMenu", mainStage);
    }

    @FXML
    protected void onCreateGameButtonClicked() {
        // When user clicks this button, we will change the screen and
        // let the user input their game information
        // When user clicks this button again, will process game
        // information
        System.out.println("onCreateGameButtonClicked");

        if(gameNameField.isVisible()) {
            // check if game information has been submitted --> process
        }
        else {
            serverAddressLabel.setVisible(false);
            portNumberLabel.setVisible(false);
            gameNameLabel.setVisible(true);
            gamePasswordLabel.setVisible(true);

            gameNameField.setVisible(true);
            gamePasswordField.setVisible(true);
            serverAddressField.setVisible(false);
            portNumberField.setVisible(false);
        }
        //TicTacToeApplication.switchScene("CreateGame", createGameStage);
    }

    public void processNameUnavailableMessage(String gamerTag) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nameErrorLabel.setText(gamerTag + " is unavailable, please try another one");
                nameErrorLabel.setVisible(true);
                gamerTagField.setText("");
                gamerTagField.requestFocus();
            }
        });
    }

    public void processLoginSuccessfulMessage() {
        networkErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        loggedInLabel.setVisible(true);
        serverAddressLabel.setVisible(false);
        portNumberLabel.setVisible(false);

        loginButton.setDisable(true);
        loginButton.setVisible(false);
        findGameButton.setVisible(true);
        findGameButton.setDisable(false);
        createGameButton.setVisible(true);
        createGameButton.setDisable(false);

        serverAddressField.setDisable(true);
        serverAddressField.setVisible(false);
        portNumberField.setDisable(true);
        portNumberField.setVisible(false);
        gamerTagField.setDisable(true);
    }

    public void processSearchingForGameMessage() {
        loggedInLabel.setVisible(false);
        searchingLabel.setVisible(true);

        findGameButton.setDisable(true);
        findGameButton.setVisible(false);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
    }

    public void processQueueCancelledMessage() {
        findGameButton.setDisable(false);
        findGameButton.setVisible(true);

        searchingLabel.setVisible(false);

        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
    }

    public void processGameFoundMessage() {
        searchingLabel.setVisible(false);
        gameFoundLabel.setVisible(true);
        mainMenuButton.setDisable(true);
        mainMenuButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
    }
}
