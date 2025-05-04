package com.java4b.tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameMatcherClient extends Client {

    private ArrayList<String> allPlayerNames = new ArrayList<>();
    private Queue<String> playerQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        new GameMatcherClient("localhost", 11111);
    }

    public GameMatcherClient(String serverIP, int serverPort) {
        super(serverIP, serverPort);

        sendMessage(new RegistrationMessage("/lobby"));
        sendMessage(new RegistrationMessage("/login"));
        sendMessage(new RegistrationMessage("/create-new-game"));

        new Thread(this::matchPlayers).start();
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "ATTEMPT_LOGIN":
                        processAttemptLoginMessage((AttemptLoginMessage) message);
                        break;
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

    private void processAttemptLoginMessage(AttemptLoginMessage message) {
        String gamerTag = message.getGamerTag();

        if (allPlayerNames.contains(gamerTag)) {
            sendMessage(new NameUnavailableMessage(gamerTag));
        } else {
            sendMessage(new LoginSuccessfulMessage(gamerTag));
            allPlayerNames.add(gamerTag);
            sendMessage(new RegistrationMessage("/lobby/" + gamerTag));
            System.out.println(gamerTag + " has logged in");
        }
    }

    private void processJoinQueueMessage(JoinQueueMessage message) {
        String lobbySubChannel = message.getLobbySubChannel();
        playerQueue.add(lobbySubChannel);
        sendMessage(new SearchingForGameMessage(lobbySubChannel));
        System.out.println(lobbySubChannel + " has joined the queue");
    }

    private void matchPlayers() {
        while (true) {
            if (playerQueue.size() >= 2) {
                String lobbySubChannel1 = playerQueue.poll();        // pop the front of the queue and return it
                String lobbySubChannel2 = playerQueue.poll();
//                sendMessage(new CreateNewGameMessage(player1Name, player2Name));
            }
        }
    }
}
