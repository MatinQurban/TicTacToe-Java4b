package com.java4b.tictactoe;

public class SinglePlayerGameController extends GameController {

    @Override
    public void initialize() {
        gameState = new GameState("Player 1", Avatar.ANCHOR, "Computer", Avatar.LIFE_SAVER);
        super.initialize();
        gameModeLabel.setText("Single Player Game");
    }

    /*
       Might switch to using this initialization function instead if we need the game state to be initialized
       outside the game controller later on.
     */
//    @Override
//    public void initData(GameState gameState) {
//        super.initData(gameState);
//
//        gameModeLabel.setText("Single Player Game");
//    }
}