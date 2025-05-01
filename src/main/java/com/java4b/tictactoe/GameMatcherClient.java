package com.java4b.tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameMatcherClient extends Client {

    private ArrayList<String> allPlayerNames = new ArrayList<>();
    private Queue<String> playerQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        new GameMatcherClient();
    }

    public GameMatcherClient() {
        super();

        sendMessage(new RegistrationMessage("/lobby"));
        sendMessage(new RegistrationMessage("/create-new-game"));

        new Thread(this::matchPlayers).start();
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "JOIN_QUEUE":
                        processJoinQueueMessage((JoinQueueMessage) message);
                        break;
                    default:
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processJoinQueueMessage(JoinQueueMessage message) {
        String newPlayerName = message.getPlayerName();

        if (allPlayerNames.contains(newPlayerName)) {
            sendMessage(new NameUnavailableMessage(newPlayerName));
        } else {
            sendMessage(new SearchingForGameMessage(newPlayerName));
            allPlayerNames.add(newPlayerName);
            playerQueue.add(newPlayerName);
            System.out.println(newPlayerName + " has joined the queue");
        }
    }

    private void matchPlayers() {
        while (true) {
            if (playerQueue.size() >= 2) {
                String player1Name = playerQueue.poll();        // pop the front of the queue and return it
                String player2Name = playerQueue.poll();
                sendMessage(new CreateNewGameMessage(player1Name, player2Name));
            }
        }
    }
}
