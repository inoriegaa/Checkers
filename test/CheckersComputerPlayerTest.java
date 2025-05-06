package edu.ser216.checkers.test;

import edu.ser216.checkers.core.CheckersComputerPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersComputerPlayerTest {

    private CheckersComputerPlayer computerPlayer;

    /**
     * Setup method to create CheckersComputerPlayer object before each test.
     */
    @BeforeEach
    void setUp() {
        computerPlayer = new CheckersComputerPlayer();
    }

    /**
     * Tear down method to dispose of CheckersComputerPLayer object
     * after each test.
     */
    @AfterEach
    void tearDown() {
        computerPlayer = null;
    }

    /**
     * Test for getMoves method with only one piece.
     */
    @Test
    void getMoves() {
        char[][] board = new char[][]{
                {'x', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', 'o', '_', '_', '_'},   // i=4, j=4
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        };
        int pieceCount = 1;
        int[][] moves = computerPlayer.getMoves(pieceCount, board);
        assertEquals(4,moves.length);   // correct amount of moves
        for (int[] m : moves) { // correct starting position for each move
            assertEquals(4, m[0]);
            assertEquals(4, m[1]);
        }
        // jump left
        assertEquals(4 - 2, moves[0][2]);   // i - 2
        assertEquals(4 - 2, moves[0][3]);   // j - 2
        // jump right
        assertEquals(4 - 2, moves[1][2]);   // i - 2
        assertEquals(4 + 2, moves[1][3]);   // j + 2
        // move left
        assertEquals(4 - 1, moves[2][2]);   // i - 1
        assertEquals(4 - 1, moves[2][3]);   // j - 1
        // move right
        assertEquals(4 - 1, moves[3][2]);   // i - 1
        assertEquals(4 + 1, moves[3][3]);   // j + 1
    }

    /**
     * Test for getMoves method with multiple pieces.
     */
    @Test
    void getMoves1() {
        char[][] board = new char[][]{
                {'x', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', 'o', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', 'o', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        };
        int pieceCount = 2;
        int[][] moves = computerPlayer.getMoves(pieceCount, board);
        assertEquals(8,moves.length);   // correct amount of moves
    }

    /**
     * Test for getMoves method when pieceCount is 0 or less.
     */
    @Test
    void getMoves2() {
        char[][] board = new char[][]{
                {'x', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        };
        int pieceCount = 0;
        int[][] moves = computerPlayer.getMoves(pieceCount, board);
        assertNull(moves);
    }

    /**
     * Test for getMoves method when board is null.
     */
    @Test
    void getMoves3() {
        char[][] board = null;
        int pieceCount = 1;
        int[][] moves = computerPlayer.getMoves(pieceCount, board);
        assertNull(moves);
    }
}