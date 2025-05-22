package com.java4b.tictactoe.messages;

public class LobbyCreatedMessage extends Message {
    private static final long serialVersionUID = 1L;

    public LobbyCreatedMessage(String playerSubChannel) {
        super(playerSubChannel, "LOBBY_CREATED");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
