package com.java4b.tictactoe.messages;

public class GameOverMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String winner;

    public GameOverMessage(String gameChannel, String winner) {
        super(gameChannel, "GAME_OVER");
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nWinner: " + (winner == null ? "tie" : winner);
    }
}
