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

    public boolean winOrDraw(int move) {
        if(move < 5) return false; // impossible to have a win before 5 moves

        // if move == 10 that means 9 turns have been played, return True for draw
        return checkRowWin() || checkColWin() || checkDiagWin() || move >= 10;// no win and no draw
    }

    private void randomizeWhoGoesFirst() {
        Random random = new Random();

        if (random.nextInt(2) == 0)
            activePlayer = player1;
        else
            activePlayer = player2;
    }

    private boolean checkRowWin() {
        int[] startCheck = {1, 4, 7};

        for(int start : startCheck)
        {
            if(board.getCell(start).equals(board.getCell(start+1))
            && board.getCell(start).equals(board.getCell(start+2)))
                return true;
        }
        return false;
    }

    private boolean checkColWin() {
        int[] startCheck = {1, 2, 3};

        for(int start : startCheck)
        {
            if(board.getCell(start) == board.getCell(start+3)
            && board.getCell(start) == board.getCell(start+6))
                return true;
        }
        return false;
    }

    private boolean checkDiagWin() {
        // Top left to bottom right diagonal
        if(board.getCell(1) == board.getCell(5)
        && board.getCell(1) == board.getCell(9))
        {
            return true;
        }
        // Top right to bottom left diagonal
        else if (board.getCell(3) == board.getCell(5)
              && board.getCell(3) == board.getCell(7))
        {
            return true;
        }

        return false;
    }
}
