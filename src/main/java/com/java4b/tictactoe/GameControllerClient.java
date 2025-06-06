package com.java4b.tictactoe;

import com.java4b.tictactoe.messages.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameControllerClient extends Client {

    private Map<String, GameState> gameStateForChannel = new ConcurrentHashMap<>();

//    public static void main(String [] args) throws IOException {new GameControllerClient("localhost", 11111);}

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
                        break;
                    case "PLAYER_READY":
                        processPlayerReadyMessage((PlayerReadyMessage) message);
                        break;
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

    private void processPlayerReadyMessage(PlayerReadyMessage message) {
        String gameChannel = message.getChannel();
        GameState gameState = gameStateForChannel.get(gameChannel);

        if (gameState.getPlayer1().getName().equals(message.getGamerTag())) {
            gameState.setReadyPlayer1(true);
        } else if (gameState.getPlayer2().getName().equals(message.getGamerTag())) {
            gameState.setReadyPlayer2(true);
        }

        if (gameState.isReadyPlayer1() && gameState.isReadyPlayer2()) {
            sendMessage(new PlayerTurnMessage(gameChannel, gameState.getActivePlayer().getName()));
        }
    }
}