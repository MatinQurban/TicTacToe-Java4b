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
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processNewGameMessage(NewGameMessage message) {
        String gameChannel = message.getGameChannel();
        gameStateForChannel.put(gameChannel, message.getGameState());
        sendMessage(new RegistrationMessage(gameChannel));
    }
}