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

    public int getNumMoves() { return numMoves; }

    public Board getBoard() { return board; } //

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
        // call ai interface move
        return 0; // pplacehlder
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
