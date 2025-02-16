package com.java4b.tictactoe;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

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


    @Override
    protected void onCellClicked(MouseEvent event) throws IOException {
        int startNumMoves = gameState.getNumMoves();

//        if (startNumMoves == 0) {
//            gameState.toggleActivePlayer();
//            playComputerTurn();
//            return;
//        }

        super.onCellClicked(event);

        if (gameState.getNumMoves() != startNumMoves) {
            playComputerTurn();
        }
    }

    private void playComputerTurn() throws IOException {
        int cellIndex = gameState.getComputerMove();
        StackPane cell = cells.get(cellIndex);

        // Add the avatar to the cell that was clicked on
        BackgroundSize imageSize = new BackgroundSize(0.70, 0.70, true, true, false, false);
        cell.setBackground(new Background(new BackgroundImage(gameState.getActivePlayer().getAvatar().getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imageSize)));

        // Update the game state
        gameState.playCell(cellIndex);

        // Check if the game ended (win or draw)
        if (gameState.winOrDraw(getMoveCount())) {
            declareWinner();
        } else {
            gameState.toggleActivePlayer();
            setActivePlayerLabel();
            setCursorAsAvatar();
        }

        cell.getStyleClass().clear(); // Remove hover effect
    }
}