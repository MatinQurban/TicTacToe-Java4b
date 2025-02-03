package com.java4b.tictactoe;

import java.util.Random;

public class GameState {

    private Player player1;
    private Player player2;
    private Board board;

    private Player activePlayer;


    public GameState(String name1, Avatar avatar1, String name2, Avatar avatar2) {
        player1 = new Player(name1, avatar1);
        player2 = new Player(name2, avatar2);
        board = new Board();

        activePlayer = player1;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean isCellEmpty(int index) {
        return board.getCell(index) == Avatar.NONE;
    }

    public void playCell(int index) {
        board.setCell(index, activePlayer.getAvatar());
    }

    public void toggleActivePlayer () {
        if (activePlayer == player1)
            activePlayer = player2;
        else
            activePlayer = player1;
    }

    private void randomizeWhoGoesFirst() {
        Random random = new Random();

        if (random.nextInt(2) == 0)
            activePlayer = player1;
        else
            activePlayer = player2;
    }
}
