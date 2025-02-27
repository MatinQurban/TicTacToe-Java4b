package com.java4b.tictactoe;
import java.util.Random;

public interface AIPlayer {
    int getMove(GameState gameState);
}

class EasyAI implements AIPlayer {
    public int getMove(GameState gameState) {
        Random random = new Random();
        int compMove = random.nextInt(9);
        while (!gameState.isCellEmpty(compMove)) {
            compMove = random.nextInt(9);
        }
        return compMove;
    }
}

class HardAI implements AIPlayer {
    public int getMove(GameState gameState) {
        int depth = 9 - gameState.getNumMoves();
        int maxEval = -10;
        int indexOfMax = 0;
        int alpha = -10;
        int beta = 10;

        for (int i = 0; i < 9; ++i) {
            if (gameState.isCellEmpty(i)) {
                gameState.playCell(i);

                int eval = minimax(gameState, depth - 1, alpha, beta, false);
                if (eval > maxEval) {
                    maxEval = eval;
                    indexOfMax = i;
                }

                gameState.undoPlayCell(i);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
        }

        return indexOfMax;

    }

    // Copied from GameState.java, added GameState parameter.
    private int minimax(GameState gameState, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (gameState.isAWin())
            return (maximizingPlayer ? (depth + 1) * -1 : (depth + 1));
        else if (depth == 0)
            return 0;

        if (maximizingPlayer) {
            int maxEval = -10;

            for (int i = 0; i < 9; ++i) {
                if (gameState.isCellEmpty(i)) {
                    gameState.playCell(i);
                    int eval = minimax(gameState, depth - 1, alpha, beta, false);
                    maxEval = Math.max(eval, maxEval);
                    alpha = Math.max(alpha, eval);

                    gameState.undoPlayCell(i);
                    if (beta <= alpha)
                        break;
                }
            }

            return maxEval;
        } else {
            int minEval = 10;

            for (int i = 0; i < 9; ++i) {
                if (gameState.isCellEmpty(i)) {
                    gameState.playCell(i);
                    int eval = minimax(gameState, depth - 1, alpha, beta, true);
                    minEval = Math.min(eval, minEval);

                    gameState.undoPlayCell(i);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha)
                        break;
                }
            }

            return minEval;
        }
    }
}
