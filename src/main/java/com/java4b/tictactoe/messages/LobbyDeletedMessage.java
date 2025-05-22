package com.java4b.tictactoe.messages;

public class LobbyDeletedMessage extends Message{
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel;
    private final String gameLobbyChannel;

    public LobbyDeletedMessage(String lobbySubChannel, String gameLobbyChannel) {
        super(lobbySubChannel, "LOBBY_DELETED");
        this.lobbySubChannel = lobbySubChannel;
        this.gameLobbyChannel = gameLobbyChannel;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    public String getGameLobbyChannel() {
        return gameLobbyChannel;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLobbyDeletedMessage{" +
                "lobbySubChannel='" + lobbySubChannel + '\'' +
                ", gameLobbyChannel='" + gameLobbyChannel + '\'' +
                '}';
    }
}
