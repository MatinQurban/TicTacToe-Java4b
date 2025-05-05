package com.java4b.tictactoe.messages;

public class InvalidMoveMessage extends Message {
    private static final long serialVerisionUID = 1L;
    private final String gamerTag;
    private final int move;

    public InvalidMoveMessage(String channel, String gamerTag, int move) {
        super(channel, "INVALID_MOVE");
        this.gamerTag = gamerTag;
        this.move = move;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public int getMove() {
        return move;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nGamer tag: " + gamerTag
                + "\nInvalid move (cell index): " + move;
    }
}
