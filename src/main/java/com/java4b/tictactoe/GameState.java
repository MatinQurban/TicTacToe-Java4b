package com.java4b.tictactoe;

import java.util.Random;

public class GameState {

    private Player player1;
    private Player player2;
    private Board board;

    private Player activePlayer;

    private int numMoves = 0;

    public GameState(String name1, Avatar avatar1, String name2, Avatar avatar2) {
        player1 = new Player(name1, avatar1);
        player2 = new Player(name2, avatar2);
        board = new Board();

        activePlayer = player1;
    }

    public Player getActivePlayer() { return activePlayer; }

    public void setActivePlayer(Player activePlayer) { this.activePlayer = activePlayer; }

    public Player getPlayer1() { return player1; }

    public Player getPlayer2() { return player2; }

    public boolean isCellEmpty(int index) {
        return board.getCell(index) == Avatar.NONE;
    }

    public void playCell(int index) {
        board.setCell(index, activePlayer.getAvatar());
        ++numMoves;

        if (numMoves < 9 && !isAWin()) {
            toggleActivePlayer();
        }
    }

    public void undoPlayCell(int index) {
        if (numMoves < 9 && !isAWin()) {
            toggleActivePlayer();
        }

        board.setCell(index, Avatar.NONE);
        --numMoves;
    }

    private void toggleActivePlayer () {
        if (activePlayer == player1)
            activePlayer = player2;
        else
            activePlayer = player1;
    }

    public boolean isATie() {
        return (numMoves == 9 && !isAWin());
    }

    public boolean isAWin() {
        return (numMoves >= 5 && (checkRowWin() || checkColWin() || checkDiagWin()));
    }

    public int getComputerMove() {
        int depth = 9 - numMoves;
        int maxEval = -10;
        int indexOfMax = 0;
        int alpha = -10;
        int beta = 10;

        for (int i = 0; i < 9; ++i) {
            if (isCellEmpty(i)) {
                playCell(i);

                int eval = minimax(depth - 1, alpha, beta, false);
                if (eval > maxEval) {
                    maxEval = eval;
                    indexOfMax = i;
                }

                undoPlayCell(i);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
        }

        return indexOfMax;
    }

    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (isAWin())
            return (maximizingPlayer ? (depth + 1) * -1 : (depth + 1));
        else if (depth == 0)
            return 0;

        if (maximizingPlayer) {
            int maxEval = -10;

            for (int i = 0; i < 9; ++i) {
                if (isCellEmpty(i)) {
                    playCell(i);
                    int eval = minimax(depth - 1, alpha, beta, false);
                    maxEval = Math.max(eval, maxEval);
                    alpha = Math.max(alpha, eval);

                    undoPlayCell(i);
                    if (beta <= alpha)
                        break;
                }
            }

            return maxEval;
        }
        else {
            int minEval = 10;

            for (int i = 0; i < 9; ++i) {
                if (isCellEmpty(i)) {
                    playCell(i);
                    int eval = minimax(depth - 1, alpha, beta, true);
                    minEval = Math.min(eval, minEval);

                    undoPlayCell(i);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha)
                        break;
                }
            }

            return minEval;
        }
    }

    private void randomizeWhoGoesFirst() {
        Random random = new Random();

        if (random.nextInt(2) == 0)
            activePlayer = player1;
        else
            activePlayer = player2;
    }

    private boolean checkRowWin() {
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

    private boolean checkColWin() {
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

    private boolean checkDiagWin() {
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
