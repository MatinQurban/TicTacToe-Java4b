package com.java4b.tictactoe;

import com.java4b.tictactoe.messages.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javafx.util.Pair;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

// We are going to want to create a new map that holds concurrent threads for
// each player who created a new game. For each new game made we will make a
// game id similarly to how we are doing in matchPlayers function

public class GameMatcherClient extends Client {

    private List<String> allPlayerNames = new CopyOnWriteArrayList<>();
    private Queue<String> playerQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentHashMap<String, Pair<String, PrivateLobby>> activeLobbies = new ConcurrentHashMap<String, Pair<String, PrivateLobby>>();
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
                        break;
                    case "START_PRIVATE_GAME":
                        processStartPrivateGameMessage((StartPrivateGameMessage) message);
                        break;
                    case "CREATE_LOBBY":
                        processCreateLobbyMessage((CreateLobbyMessage) message);
                        break;
                    case "JOIN_LOBBY":
                        processJoinLobbyMessage((JoinLobbyMessage) message);
                        break;
                    case "DELETE_LOBBY":
                        processDeleteLobbyMessage((DeleteLobbyMessage) message);
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

    private void processCreateLobbyMessage(CreateLobbyMessage message) {
        String playerSubChannel = message.getLobbySubChannel();
        String gameName = message.getGameName();
        String gamePassword = message.getGamePassword();
        String gameLobbyChannel = playerSubChannel + "/" + gameName;
//        System.out.println("Processing Lobby Creation:" +
//                "\nPlayer: " + playerSubChannel +
//                "\nGame Name: " + gameName +
//                "\nGame Pswd: _" + gamePassword + "_");
        if(activeLobbies.containsKey(gameName)){
            sendMessage(new InvalidLobbyMessage(playerSubChannel, gameName));
            return;
        }
        // Map < Key: PrivateLobbyName, Value: Pair( Key: Password , Value: PrivateLobby Object )>
        Stack<String> players = new Stack<>();
        players.push(playerSubChannel.split("/")[2]);
        PrivateLobby newLobby = new PrivateLobby(false, gameLobbyChannel, players);
        activeLobbies.put(gameName, new Pair<>(gamePassword, newLobby));
        sendMessage(new LobbyCreatedMessage(playerSubChannel, gameLobbyChannel));
        sendMessage(new RegistrationMessage(gameLobbyChannel));
    }

    private void processJoinLobbyMessage(JoinLobbyMessage message) {
        String playerSubChannel = message.getLobbySubChannel();
        String targetGameName = message.getGameName();
        String passwordAttempt = message.getGamePassword();
        Pair<String, PrivateLobby> lobbyInfo = activeLobbies.get(targetGameName);

        // Check if lobby exists
        if(!activeLobbies.containsKey(targetGameName)){
            sendMessage(new InvalidLobbyMessage(playerSubChannel, targetGameName));
            return;
        }

        // Check if user entered correct password for the lobby
        if(!lobbyInfo.getKey().isEmpty() && !lobbyInfo.getKey().equals(passwordAttempt)) {
            // If the password is incorrect, send a message to the player to indicate so
            sendMessage(new InvalidLobbyMessage(playerSubChannel, targetGameName));
            //sendMessage(new InvalidLobbyPasswordMessage(playerSubChannel, targetGameName)); if feeling fancy can make specific message
            return;
        }

        // Check if the lobby is FULL
        if(lobbyInfo.getValue().isFull()){
            sendMessage(new InvalidLobbyMessage(playerSubChannel, targetGameName));
            return;
        }
        
        // If user entered an existing lobby that is open and the corresponding password, add player and return PrivateLobbyAccessChannel
        lobbyInfo.getValue().getPlayers().push(playerSubChannel.split("/")[2]);
        String lobbyChannel = lobbyInfo.getValue().getPrivateAccessChannel();
        sendMessage(new LobbyFoundMessage(playerSubChannel, lobbyChannel));
        sendMessage(new LobbyFoundMessage(lobbyChannel, lobbyChannel));

        // Mark lobby as FULL
        lobbyInfo.getValue().setFull(true);
        activeLobbies.put(targetGameName, lobbyInfo); // Might not need this line
    }

    private void processDeleteLobbyMessage(DeleteLobbyMessage message) {
        String gameName = message.getGameName();
        String gameChannel = activeLobbies.get(gameName).getValue().getPrivateAccessChannel();
        activeLobbies.remove(gameName);
        sendMessage(new LobbyDeletedMessage(message.getLobbySubChannel(), gameChannel));
        sendMessage(new UnregisterMessage(gameChannel));
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

    private void processStartPrivateGameMessage(StartPrivateGameMessage message) {
        String privateLobbyChannel = message.getPrivateLobbyChannel();
        String lobbyName = privateLobbyChannel.split("/")[3];
        String gamerTag1 = activeLobbies.get(lobbyName).getValue().getPlayers().pop();
        String gamerTag2 = activeLobbies.get(lobbyName).getValue().getPlayers().pop();
        Avatar avatar1 = Avatar.ANCHOR;
        Avatar avatar2 = Avatar.LIFE_SAVER;
        String firstPlayer = randomizeWhoGoesFirst(gamerTag1, gamerTag2);

        String gameLobbyChannel = "/game/" + nextAvailableGameChannel;
        ++nextAvailableGameChannel;

        GameState gameState = new GameState(gamerTag1, avatar1, gamerTag2, avatar2, firstPlayer);
        sendMessage(new NewGameMessage(gameLobbyChannel, gameState));

        String player1SubChannel = "/lobby/" + gamerTag1;
        String player2SubChannel = "/lobby/" + gamerTag2;
        sendMessage(new GameFoundMessage(player1SubChannel, gameLobbyChannel, gamerTag2, avatar1, avatar2, firstPlayer, true));
        sendMessage(new GameFoundMessage(player2SubChannel, gameLobbyChannel, gamerTag1, avatar2, avatar1, firstPlayer, true));

        activeLobbies.remove(lobbyName);
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

                sendMessage(new GameFoundMessage(lobbySubChannel1, gameChannel, gamerTag2, avatar1, avatar2, firstPlayer, false));
                sendMessage(new GameFoundMessage(lobbySubChannel2, gameChannel, gamerTag1, avatar2, avatar1, firstPlayer, false));
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