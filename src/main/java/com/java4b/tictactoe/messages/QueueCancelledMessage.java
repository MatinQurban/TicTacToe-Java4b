package com.java4b.tictactoe.messages;

public class QueueCancelledMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String lobbySubChannel;

    public QueueCancelledMessage(String lobbySubChannel) {
        super(lobbySubChannel, "QUEUE_CANCELLED");
        this.lobbySubChannel = lobbySubChannel;
    }

    public String getLobbySubChannel() {
        return lobbySubChannel;
    }

    @Override
    public String toString() { return super.toString()+ "\nlobbySubChannel: " + lobbySubChannel; }
}
