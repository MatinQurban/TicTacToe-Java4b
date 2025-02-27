package com.java4b.tictactoe;

import javafx.fxml.FXML;

import java.io.IOException;

public class SinglePlayerGameController extends GameController {
    private AIPlayer ai;

    @Override
    public void initialize() {
        gameState = new GameState("Player 1", Avatar.ANCHOR, "Computer", Avatar.LIFE_SAVER);
        super.initialize();

        gameModeLabel.setText("Single Player Game");
    }

    @Override
    public void startGame(Player firstPlayer) throws IOException {
        super.startGame(firstPlayer);

        if ((gameState.getActivePlayer().getName()).equals("Computer")) {
            int computerMove = ai.getMove(gameState);
            playCell(computerMove);
        }
    }

    @Override
    protected void setupNextTurn() throws IOException {
        super.setupNextTurn();

        if (gameState.getActivePlayer().getName().equals("Computer")) {
            int computerMove = ai.getMove(gameState);
            playCell(computerMove);
        }
    }
}