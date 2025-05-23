package com.java4b.tictactoe.messages;

public class DeleteLobbyMessage extends Message{
    private static final long serialVersionUID = 1L;
    private final String gameName;
    private final String lobbySubChannel;

    public DeleteLobbyMessage(String channel, String gameName) {
        super(channel, "DELETE_LOBBY");
        this.lobbySubChannel = channel;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getLobbySubChannel() { return lobbySubChannel; }

    @Override
    public String toString() {
        return super.toString() + "\nDelete lobby";
    }
}
