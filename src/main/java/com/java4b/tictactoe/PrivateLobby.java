package com.java4b.tictactoe;

import java.util.Stack;

public class PrivateLobby {
    // This class is going to hold the data for our player lobby.
    // Will be password-protected
    private boolean isFull;
    private String privateAccessChannel;
    private Stack<String> players;

    public PrivateLobby(boolean isFull, String privateAccessChannel, Stack<String> players) {
        this.isFull = isFull;
        this.privateAccessChannel = privateAccessChannel;
        this.players = players;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean bFull) {
        isFull = bFull;
    }

    public String getPrivateAccessChannel() {
        return privateAccessChannel;
    }

    public Stack<String> getPlayers() {
        return players;
    }
}
