package com.java4b.tictactoe.messages;

public class InvalidLobbyMessage extends Message{
    private static final long serialVerisionUID = 1L;
    private final String gameName;
    public InvalidLobbyMessage(String channel, String gameName) {
        super(channel, "INVALID_LOBBY");
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public String toString() {
        return super.toString() + "\nInvalid lobby";
    }
}
