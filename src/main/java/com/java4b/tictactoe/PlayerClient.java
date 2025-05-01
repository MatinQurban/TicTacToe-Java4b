package com.java4b.tictactoe;

import java.io.IOException;
import java.util.Scanner;

public class PlayerClient extends Client {

    String gameChannel;

    // Temporary unique identifier - might want a more robust solution later
    String playerName;
    boolean isNameValid;

    public static void main(String[] args) {
        new PlayerClient();
    }

    public PlayerClient() {
        super();
        playerName = getGamerTag();

        sendMessage(new RegistrationMessage("/lobby"));
        sendMessage(new JoinQueueMessage(playerName));
    }

    private String getGamerTag() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your gamer tag: ");
        String name = scanner.nextLine();

        return name;
    }

    @Override
    protected void listenForMessages() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) in.readObject();

                switch (message.getType()) {
                    case "NAME_UNAVAILABLE":

                        // If the playerName was already validated then this player is not the intended recipient
                        if (((NameUnavailableMessage) message).getPlayerName().equals(playerName) && !isNameValid)
                            processNameUnavailableMessage();
                        break;

                    case "SEARCHING_FOR_GAME":
                        if (((SearchingForGameMessage) message).getPlayerName().equals(playerName))
                            processSearchingForGameMessage();
                        break;

                    case "GAME_FOUND":
                        if (((GameFoundMessage) message).getPlayerName().equals(playerName))
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

    private void processNameUnavailableMessage() {
        System.out.println("ERROR: That name is already taken, please try a different one\n");
        playerName = getGamerTag();
        sendMessage(new JoinQueueMessage(playerName));
    }

    private void processSearchingForGameMessage() {
        isNameValid = true;
        System.out.println("Searching for game ...");
    }

    private void processGameFoundMessage(GameFoundMessage message) {
        gameChannel = message.getGameChannel();
        System.out.println("\nGame found!");
        System.out.println("\tGame Channel: " + gameChannel);
        System.out.println("\tAvatar: " + message.getAvatar());
    }
}
