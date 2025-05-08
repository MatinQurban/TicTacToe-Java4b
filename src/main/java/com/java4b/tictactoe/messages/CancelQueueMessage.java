package com.java4b.tictactoe.messages;

public class CancelQueueMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel, gamerTag;

    public CancelQueueMessage(String lobbySubChannel, String gamertag) {
        super(lobbySubChannel, "CANCEL_QUEUE");
        this.lobbySubChannel = lobbySubChannel;
        this.gamerTag = gamertag;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nlobbySubChannel: " + lobbySubChannel
                + "\ngamerTag: " + gamerTag;
    }
}
