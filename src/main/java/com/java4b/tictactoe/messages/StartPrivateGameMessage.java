package com.java4b.tictactoe.messages;

public class StartPrivateGameMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String privateLobbyChannel;

    public StartPrivateGameMessage(String privateLobbyChannel) {
        super(privateLobbyChannel, "START_PRIVATE_GAME");
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
