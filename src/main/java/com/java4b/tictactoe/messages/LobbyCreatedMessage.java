package com.java4b.tictactoe.messages;

public class LobbyCreatedMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String privateLobbyChannel;

    public LobbyCreatedMessage(String playerSubChannel, String privateLobbyChannel) {
        super(playerSubChannel, "LOBBY_CREATED");
        this.privateLobbyChannel = privateLobbyChannel;
    }

    public String getPrivateLobbyChannel() {
        return privateLobbyChannel;
    }

    @Override
    public String toString() {
        return super.toString() + "LobbyCreatedMessage{" +
                "privateLobbyChannel='" + privateLobbyChannel + '\'' +
                '}';
    }
}
