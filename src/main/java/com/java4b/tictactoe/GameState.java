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

    public Player getActivePlayer() { return activePlayer; }

    public Player getPlayer1() { return player1; }

    public Player getPlayer2() { return player2; }

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
        return checkRowWin() || checkColWin() || checkDiagWin() || (move == 9 && !checkRowWin() && !checkColWin() && !checkDiagWin());// no win and no draw
    }

    private void randomizeWhoGoesFirst() {
        Random random = new Random();

        if (random.nextInt(2) == 0)
            activePlayer = player1;
        else
            activePlayer = player2;
    }

    private int computerNextMove() {
        //this function will loop through the board and evaluate if placing an avatar
        //at the current cell is the best move. It is the best move if it leads to a win
        //in the fewest moves
        int moveEval = -100000;
        int nextMove = -1;

        for (int i = 1; i <= 9; i++) {
            if(isCellEmpty(i))
            {
                //call minimax
                moveEval = minimax(i, 0, true); // check all possible outcomes for this position
                if (moveEval >= 0) // if the move will lead to a loss, do not make the move
                {
                    board.setCell(i, activePlayer.getAvatar());
                }
            }
        }
    }

    private int minimax(int currentPos, int depth, boolean maximizingPlayer)
    {
        if (checkRowWin() || checkColWin() || checkDiagWin()) {
            return maximizingPlayer ? depth*1 : depth*-1;
        }

        if (depth == 9) {
            return 0;
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 1; i <= 9; i++) {
            if (isCellEmpty(i)) {
                board.setCell(i, activePlayer.getAvatar());
                int eval = minimax(i, depth + 1, false);
                board.setCell(i, Avatar.NONE);
                maxEval = Math.max(maxEval, eval);
            }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 1; i <= 9; i++) {
            if (isCellEmpty(i)) {
                board.setCell(i, activePlayer.getAvatar());
                int eval = minimax(i, depth + 1, true);
                board.setCell(i, Avatar.NONE);
                minEval = Math.min(minEval, eval);
            }
            }
            return minEval;
        }
    }

    boolean checkRowWin() {
        int[] startCheck = {0, 3, 6}; // Adjusted for 0-based index

        for (int start : startCheck) {
            if (!board.getCell(start).isEmpty() &&
                    board.getCell(start).equals(board.getCell(start + 1)) &&
                    board.getCell(start).equals(board.getCell(start + 2))) {
                return true;
            }
        }
        return false;
    }

    boolean checkColWin() {
        int[] startCheck = {0, 1, 2}; // Adjusted for 0-based index

        for (int start : startCheck) {
            if (!board.getCell(start).isEmpty() &&
                    board.getCell(start).equals(board.getCell(start + 3)) &&
                    board.getCell(start).equals(board.getCell(start + 6))) {
                return true;
            }
        }
        return false;
    }

    boolean checkDiagWin() {
        // Top-left to bottom-right diagonal
        if (!board.getCell(0).isEmpty() &&
                board.getCell(0).equals(board.getCell(4)) &&
                board.getCell(0).equals(board.getCell(8))) {
            return true;
        }
        // Top-right to bottom-left diagonal
        if (!board.getCell(2).isEmpty() &&
                board.getCell(2).equals(board.getCell(4)) &&
                board.getCell(2).equals(board.getCell(6))) {
            return true;
        }

        return false;
    }
}
