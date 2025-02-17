package com.java4b.tictactoe;

public class TwoPlayerGameController extends GameController {

    @Override
    public void initialize() {
        gameState = new GameState("Player 1", Avatar.ANCHOR, "Player 2", Avatar.LIFE_SAVER);
        super.initialize();
        gameModeLabel.setText("Two Player Game");
    }
}
