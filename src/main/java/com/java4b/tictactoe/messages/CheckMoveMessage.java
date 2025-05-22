package com.java4b.tictactoe.messages;

public class CheckMoveMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final int move;

    public CheckMoveMessage(String channel, int move) {
        super(channel, "CHECK_MOVE");
        this.move = move;
    }

    public int getMove() {
        return move;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCheck move (cell index): " + move;
    }
}
