package com.java4b.tictactoe;

public class NewGameMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final String gameChannel;
    private final GameState gameState;

    public NewGameMessage(String gameChannel, GameState gameState) {
        super("/new-game", "NEW_GAME");
        this.gameChannel = gameChannel;
        this.gameState = gameState;
    }

    public String getGameChannel() {
        return gameChannel;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nGame channel: " + gameChannel
                + "\nGame state: " + gameState;
    }
}
