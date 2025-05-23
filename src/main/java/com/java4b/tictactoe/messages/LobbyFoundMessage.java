package com.java4b.tictactoe.messages;

public class LobbyFoundMessage extends Message{
    private static final long serialVersionUID = 1L;
    private final String lobbyChannel;
    private final String playerSubChannel;

    public LobbyFoundMessage(String channel, String lobbyChannel) {
        super(channel, "LOBBY_FOUND");
        this.playerSubChannel = channel;
        this.lobbyChannel = lobbyChannel;
    }

    public String getLobbyChannel() {
        return lobbyChannel;
    }

    public String getPlayerSubChannel() { return playerSubChannel; }

    @Override
    public String toString() {
        return super.toString() + "\nDelete lobby";
    }
}
