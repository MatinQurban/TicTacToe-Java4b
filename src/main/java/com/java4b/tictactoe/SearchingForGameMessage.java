package com.java4b.tictactoe;

public class SearchingForGameMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel;

    public SearchingForGameMessage(String lobbySubChannel) {
        super(lobbySubChannel, "SEARCHING_FOR_GAME");
        this.lobbySubChannel = lobbySubChannel;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLobby sub-channel: " + lobbySubChannel;
    }
}
