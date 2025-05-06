package edu.ser216.checkers.core;

/**
 * A class used to simulate a computer player for a game of checkers.
 *
 * @author Ibrahim Noriega A.
 * @version 1.0 Mar 23, 2023
 */
public class CheckersComputerPlayer {

    /**
     * Generates possible moves for computer player.
     *
     * @param pieceCount amount of pieces remaining
     * @param board checkers game board
     * @return Array of possible moves for each piece
     */
    public static int[][] getMoves(int pieceCount, char[][] board) {
        if (pieceCount <= 0 || board == null) {
            return null;
        }
        int[][] moves = new int[pieceCount * 4][4]; // 4 possible moves/piece
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'o') {
                    for (int k = 0; k < 4; k++) {
                        moves[count + k][0] = i;
                        moves[count + k][1] = j;
                    }
                    // jump left
                    moves[count][2] = i - 2;
                    moves[count][3] = j - 2;
                    // jump right
                    moves[count + 1][2] = i - 2;
                    moves[count + 1][3] = j + 2;
                    // left
                    moves[count + 2][2] = i - 1;
                    moves[count + 2][3] = j - 1;
                    // right
                    moves[count + 3][2] = i - 1;
                    moves[count + 3][3] = j + 1;
                    count += 4;
                }
            }
        }
        return moves;
    }
}
