package com.java4b.tictactoe;

import com.java4b.tictactoe.messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayerClient extends Client {

    private String gameChannel;
    private String lobbySubChannel;
    private String privateLobbyChannel;
    private boolean isConnected;

    private String gamerTag;

    private Stage mainStage;
    private Stage joinQueueStage, coinFlipStage;
    private JoinQueueController joinQueueController;
    private OnlineMPGameController onlineMPGameController;

    public PlayerClient(Stage mainStage) throws IOException {
        this.mainStage = mainStage;

        FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("join-queue-view.fxml"));
        joinQueueStage = new Stage();
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
                    case "QUEUE_CANCELLED":
                        processQueueCancelledMessage();
                        break;
                    case "LOBBY_CREATED":
                        processLobbyCreatedMessage((LobbyCreatedMessage) message);
                        break;
                    case "LOBBY_FOUND":
                        processLobbyFoundMessage((LobbyFoundMessage) message);
                        break;
                    case "LOBBY_DELETED":
                        processLobbyDeletedMessage((LobbyDeletedMessage) message);
                        break;
                    case "INVALID_LOBBY":
                        processInvalidLobbyMessage(((InvalidLobbyMessage) message));
                        break;
                    case "GAME_FOUND":
                        processGameFoundMessage((GameFoundMessage) message);
                        break;
                    case "PLAYER_TURN":
                        processPlayerTurnMessage((PlayerTurnMessage) message);
                        break;
                    case "INVALID_MOVE":
                        if (((InvalidMoveMessage) message).getGamerTag().equals(gamerTag))
                            processInvalidMoveMessage((InvalidMoveMessage) message);
                        break;
                    case "MAKE_MOVE":
                        processMakeMoveMessage((MakeMoveMessage) message);
                        break;
                    case "GAME_OVER":
                        processGameOverMessage((GameOverMessage) message);
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
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

    private void processLobbyCreatedMessage(LobbyCreatedMessage message) {
        this.privateLobbyChannel = message.getPrivateLobbyChannel();
        sendMessage(new RegistrationMessage(privateLobbyChannel));
        joinQueueController.processLobbyCreatedMessage();
    }

    private void processLobbyFoundMessage(LobbyFoundMessage message) {
        this.privateLobbyChannel = message.getLobbyChannel();
        String hostName = privateLobbyChannel.split("/")[2];
        if(!Objects.equals(this.gamerTag, hostName)) sendMessage(new RegistrationMessage(privateLobbyChannel));
        joinQueueController.processLobbyFoundMessage(hostName, this.gamerTag);
    }

    private void processLobbyDeletedMessage(LobbyDeletedMessage message) {
        sendMessage(new UnregisterMessage(message.getGameLobbyChannel()));
        joinQueueController.processLobbyDeletedMessage();
    }

    private void processInvalidLobbyMessage(InvalidLobbyMessage message) { joinQueueController.processInvalidLobbyMessage(); }

    private void processSearchingForGameMessage() {
        joinQueueController.processSearchingForGameMessage();
        System.out.println("Searching for game ...");
    }

    private void processQueueCancelledMessage() { joinQueueController.processQueueCancelledMessage(); }

    private void processGameFoundMessage(GameFoundMessage message) throws InterruptedException, IOException {
        gameChannel = message.getGameChannel();
        String opponentGamerTag = message.getOpponentName();
        Avatar myAvatar  = message.getMyAvatar();
        Avatar opponentAvatar  = message.getOpponentAvatar();
        String firstPlayer = message.getFirstPlayer();

        sendMessage(new RegistrationMessage(gameChannel));
        if(message.isPrivate()){ sendMessage(new UnregisterMessage(message.getTargetChannel())); }

        System.out.println("\nGame found!");
        System.out.println("\tGame channel: " + message.getGameChannel());
        System.out.println("\tOpponent name: " + message.getOpponentName());
        System.out.println("\tMy avatar: " + message.getMyAvatar());
        System.out.println("\tOpponent avatar: " + message.getOpponentAvatar());
        System.out.println("\tFirst player: " + message.getFirstPlayer());

        joinQueueController.processGameFoundMessage();
        Thread.sleep(1000);
        joinQueueController.closeSubStage();

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("game-setup-view.fxml"));
                coinFlipStage = new Stage();
                coinFlipStage.setScene(new Scene(loader.load()));
                CoinFlipController coinFlipController = loader.getController();
                coinFlipController.initData(this, gamerTag, opponentGamerTag, myAvatar, opponentAvatar, firstPlayer, mainStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void processPlayerTurnMessage(PlayerTurnMessage message) {
        onlineMPGameController.processPlayerTurnMessage(message);
    }

    public void processInvalidMoveMessage(InvalidMoveMessage message) {
        onlineMPGameController.processInvalidMoveMessage(message.getMove());
    }

    public void processMakeMoveMessage(MakeMoveMessage message) {
        onlineMPGameController.processMakeMoveMessage(message.getMove(), message.getAvatar());
    }

    public void processGameOverMessage(GameOverMessage message) throws IOException {
        onlineMPGameController.processGameOverMessage(message.getWinner());
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

    public void respondToCreateGameClicked(String gameName, String gamePassword) { sendMessage(new CreateLobbyMessage(lobbySubChannel, gameName, gamePassword)); }

    public void respondToStartGameClicked() { sendMessage(new StartPrivateGameMessage(privateLobbyChannel)); }

    public void respondToDeleteGameClicked(String gameName) { sendMessage(new DeleteLobbyMessage(lobbySubChannel, gameName)); }

    public void respondToJoinGameClicked(String gameName, String gamePassword) {
        sendMessage(new JoinLobbyMessage(lobbySubChannel, gameName, gamePassword));
    }

    public void respondToFindGameClicked() { sendMessage(new JoinQueueMessage(lobbySubChannel, gamerTag)); }

    public void respondToCancelButtonClicked() { sendMessage(new CancelQueueMessage(lobbySubChannel, gamerTag)); }

    public void respondToCellClicked(int move) {
        sendMessage(new CheckMoveMessage(gameChannel, move));
    }

    public void respondToReadyForGameMessages() {
        sendMessage(new PlayerReadyMessage(gameChannel, gamerTag));
    }

    public void gameSetupFinished(String myGamerTag, String opponentGamerTag, Avatar myAvatar, Avatar opponentAvatar, String firstPlayer) {
        FXMLLoader loader = new FXMLLoader(TicTacToeApplication.class.getResource("game-view.fxml"));
        onlineMPGameController = new OnlineMPGameController();
        loader.setController(onlineMPGameController);

        try {
            mainStage.setScene(new Scene(loader.load()));
            OnlineMPGameController gameController = loader.getController();
            gameController.initData(this, gamerTag, opponentGamerTag, myAvatar, opponentAvatar, firstPlayer);
            coinFlipStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
