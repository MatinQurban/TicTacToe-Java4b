package com.java4b.tictactoe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerClient extends Client {

    String gameChannel;
    String lobbySubChannel;

    // Temporary unique identifier - might want a more robust solution later
    String gamerTag;
    boolean isConnected;

    JoinQueueController joinQueueController;

    public PlayerClient(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("join-queue-view.fxml"));
        Stage joinQueueStage = new Stage();
        joinQueueStage.setScene(new Scene(loader.load()));
        joinQueueController = loader.getController();
        joinQueueController.initData(this, mainStage);
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "NAME_UNAVAILABLE":
                        if (((NameUnavailableMessage) message).getPlayerName().equals(gamerTag))
                            processNameUnavailableMessage();
                        break;

                    case "LOGIN_SUCCESSFUL":
                        if (((LoginSuccessfulMessage) message).getGamerTag().equals(gamerTag))
                            processLoginSuccessfulMessage();
                        break;

                    case "SEARCHING_FOR_GAME":
                        processSearchingForGameMessage();
                        break;

                    case "GAME_FOUND":
                        if (((GameFoundMessage) message).getPlayerName().equals(gamerTag))
                            processGameFoundMessage((GameFoundMessage) message);
                        break;

                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processLoginSuccessfulMessage() {
        lobbySubChannel = "/lobby/" + gamerTag;
        sendMessage(new RegistrationMessage(lobbySubChannel));
        sendMessage(new UnregisterMessage("/login"));
        joinQueueController.processLoginSuccessfulMessage();
    }

    private void processNameUnavailableMessage() {
        joinQueueController.processNameUnavailableMessage(gamerTag);
    }

    private void processSearchingForGameMessage() {
        joinQueueController.processSearchingForGameMessage();
        System.out.println("Searching for game ...");
    }

    private void processGameFoundMessage(GameFoundMessage message) {
        gameChannel = message.getGameChannel();
        System.out.println("\nGame found!");
        System.out.println("\tGame Channel: " + gameChannel);
        System.out.println("\tAvatar: " + message.getAvatar());

        joinQueueController.processGameFoundMessage();
    }

    public void respondToLoginClicked(String serverIP, int serverPort, String gamerTag) {
        if (!isConnected) {
            connectToRouter(serverIP, serverPort);
            isConnected = true;
            sendMessage(new RegistrationMessage("/login"));
        }

        this.gamerTag = gamerTag;
        sendMessage(new AttemptLoginMessage(this.gamerTag));
    }

    public void respondToFindGameClicked() {
        sendMessage(new JoinQueueMessage(lobbySubChannel, gamerTag));
    }
}
