package com.java4b.tictactoe;

public class MakeMoveMessage extends Message {
    private static final long serialVerisionUID = 1L;
    private final int move;
    private final Avatar avatar;

    public MakeMoveMessage(String channel, int move, Avatar avatar) {
        super(channel, "MAKE_MOVE");
        this.move = move;
        this.avatar = avatar;
    }

    public int getMove() {
        return move;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nMake move (cell index): " + move
                + "\nAvatar: " + avatar;
    }
}
