package com.java4b.tictactoe;

public class JoinQueueMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String playerName;

    public JoinQueueMessage(String playerName) {
        super("/lobby", "JOIN_QUEUE");
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlayer Name: " + playerName;
    }
}
