package com.java4b.tictactoe;

public class SearchingForGameMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String playerName;

    public SearchingForGameMessage(String playerName) {
        super("/lobby", "SEARCHING_FOR_GAME");
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
