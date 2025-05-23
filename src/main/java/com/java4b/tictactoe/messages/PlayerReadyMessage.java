package com.java4b.tictactoe.messages;

public class PlayerReadyMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gamerTag;

    public PlayerReadyMessage(String channel, String gamerTag) {
        super(channel, "PLAYER_READY");
        this.gamerTag = gamerTag;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    @Override
    public String toString() {
        return super.toString() + "\nGamerTag: " + gamerTag;
    }
}
