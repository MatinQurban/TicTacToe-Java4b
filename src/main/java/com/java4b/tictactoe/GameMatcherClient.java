package com.java4b.tictactoe;

import com.java4b.tictactoe.messages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMatcherClient extends Client {

    private List<String> allPlayerNames = new CopyOnWriteArrayList<>();
    private Queue<String> playerQueue = new ConcurrentLinkedQueue<>();
    private int nextAvailableGameChannel = 1;

//    public static void main(String[] args) {new GameMatcherClient("localhost", 11111);}

    public GameMatcherClient(String serverIP, int serverPort) {
        super(serverIP, serverPort);

        sendMessage(new RegistrationMessage("/lobby"));
        sendMessage(new RegistrationMessage("/login"));
        sendMessage(new RegistrationMessage("/new-game"));

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
                    case "CANCEL_QUEUE":
                        processCancelQueueMessage((CancelQueueMessage) message);
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
        String gamerTag = message.getGamerTag();
        String lobbySubChannel = message.getLobbySubChannel();
        playerQueue.add(gamerTag);
        sendMessage(new SearchingForGameMessage(lobbySubChannel));
        System.out.println(gamerTag + " has joined the queue");
    }

    private void processCancelQueueMessage(CancelQueueMessage message) {
        // In this function we need to remove player from playerQueue, stop listening
        // on it's channel, and respond to the player to indicate queue cancellation
        // Future implementation: remove player's game from game list
        String gamerTag = message.getGamerTag();
        String lobbySubChannel = message.getLobbySubChannel();
        playerQueue.remove(gamerTag);
        sendMessage(new QueueCancelledMessage(lobbySubChannel));
        System.out.println(gamerTag + " has cancelled queue");

    }

    private void matchPlayers() {
        while (true) {
            if (playerQueue.size() >= 2) {
                String gamerTag1 = playerQueue.poll();        // pop the front of the queue and return it
                String gamerTag2 = playerQueue.poll();
                String lobbySubChannel1 = "/lobby/" + gamerTag1;
                String lobbySubChannel2 = "/lobby/" + gamerTag2;
                Avatar avatar1 = Avatar.ANCHOR;
                Avatar avatar2 = Avatar.LIFE_SAVER;
                String firstPlayer = randomizeWhoGoesFirst(gamerTag1, gamerTag2);

                String gameChannel = "/game/" + nextAvailableGameChannel;
                ++nextAvailableGameChannel;

                GameState gameState = new GameState(gamerTag1, avatar1, gamerTag2, avatar2, firstPlayer);
                sendMessage(new NewGameMessage(gameChannel, gameState));

                sendMessage(new GameFoundMessage(lobbySubChannel1, gameChannel, gamerTag2, avatar1, avatar2, firstPlayer));
                sendMessage(new GameFoundMessage(lobbySubChannel2, gameChannel, gamerTag1, avatar2, avatar1, firstPlayer));
            }
        }
    }

    private String randomizeWhoGoesFirst(String gamerTag1, String gamerTag2) {
        Random random = new Random();

        if (random.nextInt(2) == 0)
            return gamerTag1;
        else
            return gamerTag2;
    }
}
