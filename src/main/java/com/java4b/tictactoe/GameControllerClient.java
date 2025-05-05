package com.java4b.tictactoe;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameControllerClient extends Client {

    int nextAvailableGameChannel = 1;

    private Map<String, GameState> gameStateForChannel = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new GameControllerClient("localhost", 11111);
    }

    public GameControllerClient(String serverIP, int serverPort) {
        super(serverIP, serverPort);

        sendMessage(new RegistrationMessage("/new-game"));
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "NEW_GAME":
                        processNewGameMessage((NewGameMessage) message);
                        break;
                    case "CHECK_MOVE":
                        processCheckMoveMessage((CheckMoveMessage) message);
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processNewGameMessage(NewGameMessage message) throws InterruptedException {
        String gameChannel = message.getGameChannel();
        GameState gameState = message.getGameState();
        gameStateForChannel.put(gameChannel, gameState);

        sendMessage(new RegistrationMessage(gameChannel));

        // This is a dumb way to ensure the channel is registered before trying to send a message to it
        // Could maybe try using .wait() and .notify() with a synchronized block?
        Thread.sleep(100);
        sendMessage(new PlayerTurnMessage(gameChannel, gameState.getActivePlayer().getName()));
    }

    private void processCheckMoveMessage(CheckMoveMessage message) {
        String gameChannel = message.getChannel();
        int move = message.getMove();
        GameState gameState = gameStateForChannel.get(message.getChannel());

        if (gameState.isCellEmpty(move)) {

            Avatar avatar = gameState.getActivePlayer().getAvatar();
            sendMessage(new MakeMoveMessage(gameChannel, move, avatar));

            gameState.playCell(move);
            String gamerTag = gameState.getActivePlayer().getName();

            if (gameState.isAWin()) {
                sendMessage(new GameOverMessage(gameChannel, gamerTag));
            } else if (gameState.isATie()) {
                sendMessage((new GameOverMessage(gameChannel, null)));
            } else {
                sendMessage(new PlayerTurnMessage(gameChannel, gamerTag));
            }
        } else {
            sendMessage(new InvalidMoveMessage(gameChannel, gameState.getActivePlayer().getName(), move));
        }
    }
}