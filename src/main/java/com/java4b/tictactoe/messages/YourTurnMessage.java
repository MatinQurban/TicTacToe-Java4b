package com.java4b.tictactoe.messages;

public class YourTurnMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gamerTag;

    public YourTurnMessage(String channel, String gamerTag) {
        super(channel, "YOUR_TURN");
        this.channel = channel;
        this.gamerTag = gamerTag;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlayer's turn: " + gamerTag;
    }
}
