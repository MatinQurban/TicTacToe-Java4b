package com.java4b.tictactoe;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameControllerClient extends Client {

    int nextAvailableGameChannel = 1;

    private Map<String, GameState> gameStateForChannel = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new GameControllerClient();
    }

    public GameControllerClient() {
        super();

        sendMessage(new RegistrationMessage("/lobby"));
        sendMessage(new RegistrationMessage("/create-new-game"));
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "CREATE_NEW_GAME":
                        processCreateNewGameMessage((CreateNewGameMessage) message);
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processCreateNewGameMessage(CreateNewGameMessage message) {
        String gameChannel = "/game/" + nextAvailableGameChannel;
        ++nextAvailableGameChannel;

        String player1Name = message.getPlayer1Name();
        Avatar player1Avatar = Avatar.ANCHOR;
        sendMessage(new GameFoundMessage(player1Name, player1Avatar, gameChannel));

        String player2Name = message.getPlayer2Name();
        Avatar player2Avatar = Avatar.LIFE_SAVER;
        sendMessage(new GameFoundMessage(player2Name, player2Avatar, gameChannel));

        GameState gameState = new GameState(player1Name, player1Avatar, player2Name, player2Avatar);
        gameState.randomizeWhoGoesFirst();

    }
}