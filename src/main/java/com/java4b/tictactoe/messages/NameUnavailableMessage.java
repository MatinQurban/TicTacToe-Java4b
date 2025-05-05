package com.java4b.tictactoe.messages;

public class NameUnavailableMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String playerName;

    public NameUnavailableMessage(String playerName) {
        super("/login", "NAME_UNAVAILABLE");
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
