package com.java4b.tictactoe.messages;

public class JoinQueueMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel, gamerTag;

    public JoinQueueMessage(String lobbySubChannel, String gamerTag) {
        super(lobbySubChannel, "JOIN_QUEUE");
        this.lobbySubChannel = lobbySubChannel;
        this.gamerTag = gamerTag;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLobby sub-channel: " + lobbySubChannel
                + "\nGamer tag: " + gamerTag;
    }
}
