package com.java4b.tictactoe.messages;

public class PlayerTurnMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gamerTag;

    public PlayerTurnMessage(String channel, String gamerTag) {
        super(channel, "PLAYER_TURN");
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
