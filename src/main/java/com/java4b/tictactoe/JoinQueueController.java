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
    gameFoundLabel, serverAddressLabel, portNumberLabel, gameNameLabel, gamePasswordLabel,
    gameNameErrorLabel, invalidLobbyLabel, waitingForOpponentLabel, waitingForHostLabel;

    @FXML
    protected Button loginButton, findGameButton, cancelButton, mainMenuButton,
            createGameButton, deleteGameButton, joinGameButton, startGameButton;

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
        joinGameButton.setVisible(false);
        joinGameButton.setDisable(true);

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
//        String lc = "/lobby/hostname/boof";
//        String hn = lc.split("/")[2];
//        System.out.println("hn: " + hn);
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
        gameNameField.setVisible(false);
        gamePasswordField.setVisible(false);

        gameNameLabel.setVisible(false);
        gamePasswordLabel.setVisible(false);

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
    protected void onJoinGameButtonClicked() throws IOException {
        // When the user clicks this button, we will allow them to
        // input the desired game information.
        // Upon the second click, process game information.
        System.out.println("onJoinGameButtonClicked");
//        System.out.println("Game Name Field Visible?: " + gameNameField.isVisible());
        if(gameNameField.isVisible()){
            String gameName = gameNameField.getText();
            String gamePassword = gamePasswordField.getText();
            System.out.println("Empty?: " + gameName.isEmpty() + "\nGN: " + gameName + ", GP: " + gamePassword);
            if(gameName.isEmpty()) {
                gameNameErrorLabel.setVisible(true);
                return;
            }
            findGameButton.setDisable(true);
            findGameButton.setVisible(false);
            gameNameErrorLabel.setVisible(false);
            createGameButton.setDisable(true);
            createGameButton.setVisible(false);

            playerClient.respondToJoinGameClicked(gameName, gamePassword);
        }
        initPrivateLobbyForm();

        createGameButton.setDisable(true);
        createGameButton.setVisible(false);
    }

    @FXML
    protected void onCreateGameButtonClicked() {
        // When user clicks this button, we will change the screen and
        // let the user input their game information
        // When user clicks this button again, will process game information
        System.out.println("onCreateGameButtonClicked");
//        System.out.println("Game Name Field Visible?: " + gameNameField.isVisible());
        if(gameNameField.isVisible()) {
            String gameName = gameNameField.getText();
            String gamePassword = gamePasswordField.getText();
            System.out.println("Empty?: " + gameName.isEmpty() + "\nGN: " + gameName + ", GP: " + gamePassword);
            if(gameName.isEmpty()) {
                gameNameErrorLabel.setVisible(true);
                return;
            }
            findGameButton.setDisable(true);
            findGameButton.setVisible(false);
            joinGameButton.setDisable(true);
            joinGameButton.setVisible(false);
            gameNameErrorLabel.setVisible(false);

            playerClient.respondToCreateGameClicked(gameName, gamePassword);
        }
        else {
            initPrivateLobbyForm();

            joinGameButton.setDisable(true);
            joinGameButton.setVisible(false);
        }
        //TicTacToeApplication.switchScene("CreateGame", createGameStage);
    }

    @FXML
    protected void onStartGameButtonClicked() { playerClient.respondToStartGameClicked(); }

    @FXML
    protected void onDeleteGameButtonClicked() { playerClient.respondToDeleteGameClicked(gameNameField.getText()); }

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

    public void processLobbyCreatedMessage() {
        gameNameField.setVisible(false);
        gamePasswordField.setVisible(false);

        gameNameLabel.setVisible(false);
        gamePasswordLabel.setVisible(false);
        waitingForOpponentLabel.setVisible(true);

        createGameButton.setDisable(true);
        createGameButton.setVisible(false);
        deleteGameButton.setVisible(true);
        deleteGameButton.setDisable(false);
    }

    public void processLobbyFoundMessage(String hostName, String gamerTag) {
        // at this point both players are in the lobby.
        // we will display a message saying "waiting for host to start"
        // the host will be shown a button to start the match
        // we can check if it's the host by retrieving their username from
        // the private lobby channel: /lobby/XXXX/lobbyName.
        // if username == XXXX, show them the button.
        // we should also include a way for the player to leave the lobby. but that
        // can be done later.

        gameNameField.setDisable(true);
        gameNameField.setVisible(false);
        gamePasswordField.setDisable(true);
        gamePasswordField.setVisible(false);

        gameNameLabel.setVisible(false);
        gamePasswordLabel.setVisible(false);
        waitingForOpponentLabel.setVisible(false);
        waitingForHostLabel.setVisible(true);

        if(hostName.equals(gamerTag)) {
            // Reveal start game button and functionality
            startGameButton.setDisable(false);
            startGameButton.setVisible(true);
        }
    }

    public void processLobbyDeletedMessage() {
        gameNameField.setDisable(true);
        gameNameField.setVisible(false);
        gamePasswordField.setDisable(true);
        gamePasswordField.setVisible(false);
        serverAddressField.setVisible(true);
        portNumberField.setVisible(true);

        gameNameLabel.setVisible(false);
        gamePasswordLabel.setVisible(false);
        serverAddressLabel.setVisible(true);
        portNumberLabel.setVisible(true);
        waitingForOpponentLabel.setVisible(false);
        waitingForHostLabel.setVisible(false);

        deleteGameButton.setDisable(true);
        deleteGameButton.setVisible(false);
        createGameButton.setDisable(false);
        createGameButton.setVisible(true);
        joinGameButton.setDisable(false);
        joinGameButton.setVisible(true);
        findGameButton.setDisable(false);
        findGameButton.setVisible(true);
    }

    public void processInvalidLobbyMessage() {
        invalidLobbyLabel.setVisible(false);
        invalidLobbyLabel.setVisible(true);
    }

    public void processLoginSuccessfulMessage() {
        networkErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        serverAddressLabel.setVisible(false);
        portNumberLabel.setVisible(false);
        waitingForOpponentLabel.setVisible(false);
        loggedInLabel.setVisible(true);

        loginButton.setDisable(true);
        loginButton.setVisible(false);
        findGameButton.setVisible(true);
        findGameButton.setDisable(false);
        createGameButton.setVisible(true);
        createGameButton.setDisable(false);
        joinGameButton.setVisible(true);
        joinGameButton.setDisable(false);
        startGameButton.setVisible(false);
        startGameButton.setDisable(true);

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
        createGameButton.setDisable(true);
        createGameButton.setVisible(false);
        joinGameButton.setDisable(true);
        joinGameButton.setVisible(false);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
    }

    public void processQueueCancelledMessage() {
        findGameButton.setDisable(false);
        findGameButton.setVisible(true);
        createGameButton.setDisable(false);
        createGameButton.setVisible(true);
        joinGameButton.setDisable(false);
        joinGameButton.setVisible(true);

        searchingLabel.setVisible(false);
        serverAddressLabel.setVisible(true);
        serverAddressField.setVisible(true);
        portNumberLabel.setVisible(true);
        portNumberField.setVisible(true);

        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
    }

    public void processGameFoundMessage() {
        searchingLabel.setVisible(false);
        waitingForHostLabel.setVisible(false);
        gameFoundLabel.setVisible(true);

        mainMenuButton.setDisable(true);
        mainMenuButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
        createGameButton.setDisable(true);
        createGameButton.setVisible(false);
        joinGameButton.setDisable(true);
        joinGameButton.setVisible(false);
        startGameButton.setDisable(true);
        startGameButton.setVisible(false);
        deleteGameButton.setDisable(true);
        deleteGameButton.setVisible(false);
    }

    private void initPrivateLobbyForm()
    {
        serverAddressLabel.setVisible(false);
        portNumberLabel.setVisible(false);
        gameNameLabel.setVisible(true);
        gameNameErrorLabel.setVisible(false);
        gamePasswordLabel.setVisible(true);
        loggedInLabel.setVisible(false);

        gameNameField.setVisible(true);
        gameNameField.setDisable(false);
        gamePasswordField.setVisible(true);
        gamePasswordField.setDisable(false);
        serverAddressField.setVisible(false);
        portNumberField.setVisible(false);
    }
}
