package edu.ser216.checkers.test;

import edu.ser216.checkers.core.CheckersGameLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGameLogicTest {
    private CheckersGameLogic game;
    private final PrintStream sysOut = System.out;
    private final ByteArrayOutputStream newOut = new ByteArrayOutputStream();

    /**
     * Setup method to create CheckersGameLogic object before each test.
     */
    @BeforeEach
    void setUp() {
        game = new CheckersGameLogic(null);
        System.setOut(new PrintStream(newOut));
    }

    /**
     * Tear down method to dispose of CheckersGameLogic object after each test.
     */
    @AfterEach
    void tearDown() {
        game = null;
        System.setOut(sysOut);
    }

    /**
     * Test for isGameOver method when starting game.
     */
    @Test
    void isGameOver1() {
        assertEquals(false, game.isGameOver());
    }

    /**
     * Test for isGameOver method when game ends.
     */
    @Test
    void isGameOver2() {
        game.onEnd();   // marks game as over
        assertEquals(true, game.isGameOver());
    }

    /**
     * Test for isComputerPlayer method when game starts.
     */
    @Test
    void isComputerPlayer1() {
        assertEquals(false, game.isComputerPlayer());
    }

    /**
     * Test for isComputerPlayer method when computer player is selected.
     */
    @Test
    void isComputerPlayer2() {
        game.setComputer(true);
        assertEquals(true, game.isComputerPlayer());
    }

    /**
     * Test for getWinningPlayer method when no player has won.
     */
    @Test
    void getWinningPlayer1() {
        assertEquals('_', game.getWinningPlayer());
    }

    /**
     * Test for getWinningPlayer method when Player X wins.
     */
    @Test
    void getWinningPlayer2() {
        game.setCountO(0);
        assertEquals('x', game.getWinningPlayer());
    }

    /**
     * Test for getWinningPlayer method when Player O wins.
     */
    @Test
    void getWinningPlayer3() {
        game.setCountX(0);
        assertEquals('o', game.getWinningPlayer());
    }

    /**
     * Test for getWinningPlayer method when opponent can't move.
     */
    @Test
    void getWinningPlayer4() {
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', 'x', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        });
        game.setSquare(0, 6, 'o');
        game.setPlayer('x');
        assertEquals('x', game.getWinningPlayer());
    }

    /**
     * Test for getWinningPlayer method when opponent can still move.
     */
    @Test
    void getWinningPlayer5() {
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'x', '_', 'x', '_', '_', '_', '_', '_'},
                {'_', 'o', '_', '_', '_', '_', '_', '_'}
        });
        assertEquals('_', game.getWinningPlayer());
    }

    /**
     * Test for nextTurn method when Player X finishes turn.
     */
    @Test
    void nextTurn1() {
        game.setPlayer('x');
        game.nextTurn();
        assertEquals('o', game.getPlayer());
    }

    /**
     * Test for nextTurn method when Player O finishes turn.
     */
    @Test
    void nextTurn2() {
        game.setPlayer('o');
        game.nextTurn();
        assertEquals('x', game.getPlayer());
    }

    /**
     * Test for doTurn method when input is invalid for UI choice.
     */
    @Test
    void doTurn1() {
        game = new CheckersGameLogic(new Scanner("aa\n"));
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Begin Game. Enter 'G' to play with a GUI;"
                            + " enter 'C' to play with a console-based UI."
                            + System.lineSeparator()
                            + "Invalid input. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when choice of UI is invalid.
     */
    @Test
    void doTurn2() {
        game = new CheckersGameLogic(new Scanner("a\n"));
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Begin Game. Enter 'G' to play with a GUI;"
                            + " enter 'C' to play with a console-based UI."
                            + System.lineSeparator()
                            + "Invalid choice. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when input is invalid for opponent choice.
     */
    @Test
    void doTurn3() {
        game = new CheckersGameLogic(new Scanner("g\naa\n"));
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Begin Game. Enter 'G' to play with a GUI;"
                            + " enter 'C' to play with a console-based UI."
                            + System.lineSeparator()
                            + "Enter 'P' if you want to play"
                            + " against another player; enter 'C' "
                            + "to play against\n"
                            + "computer" + System.lineSeparator()
                            + "Invalid input. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when choice of opponent is invalid.
     */
    @Test
    void doTurn4() {
        game = new CheckersGameLogic(new Scanner("c\na\n"));
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Begin Game. Enter 'G' to play with a GUI;"
                            + " enter 'C' to play with a console-based UI."
                            + System.lineSeparator()
                            + "Enter 'P' if you want to play"
                            + " against another player; enter 'C' "
                            + "to play against\n"
                            + "computer" + System.lineSeparator()
                            + "Invalid choice. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn when computer is selected as opponent.
     */
    @Test
    void doTurn5() {
        game = new CheckersGameLogic(new Scanner("c\nc\n"));
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertTrue(game.isComputerPlayer());
        }
    }

    /**
     * Test for doTurn method when it's computer's turn.
     */
    @Test
    void doTurn6() {
        game.setPlayer('o');
        game.setComputer(true);
        game.setBegin(false);
        game.doTurn();
        assertEquals("Computer's turn",
                newOut.toString().trim());
    }

    /**
     * Test for doTurn method when move input is invalid.
     */
    @Test
    void doTurn7() {
        game = new CheckersGameLogic(new Scanner("a\n"));
        game.setBegin(false);
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Player X - your turn."
                            + System.lineSeparator()
                            + "Choose a cell position of piece to be moved"
                            + " and the new position. e.g., 3a-4b"
                            + System.lineSeparator()
                            + "Invalid input. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when move is invalid.
     */
    @Test
    void doTurn8() {
        game = new CheckersGameLogic(new Scanner("1a-2b\n"));
        game.setBegin(false);
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Player X - your turn."
                            + System.lineSeparator()
                            + "Choose a cell position of piece to be moved"
                            + " and the new position. e.g., 3a-4b"
                            + System.lineSeparator()
                            + "Invalid move. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when move is valid.
     */
    @Test
    void doTurn9() {
        game = new CheckersGameLogic(new Scanner("3a-4b\n"));
        game.setBegin(false);
        game.doTurn();
        assertEquals("Player X - your turn."
                        + System.lineSeparator()
                        + "Choose a cell position of piece to be moved"
                        + " and the new position. e.g., 3a-4b",
                newOut.toString().trim());
        assertEquals('_', game.getSquare(2, 0));    //3a
        assertEquals('x', game.getBoard()[3][1]);    //4b
    }

    /**
     * Test for doTurn method when GUI is used, and it's computer's turn.
     */
    @Test
    void doTurn10() {
        game.setUseGUI(true);
        game.setComputer(true);
        game.setPlayer('o');
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'o', '_', '_', '_', '_', '_', '_', '_'}    // i=7, j=0
        });
        game.doTurn();
        assertEquals('_', game.getSquare(7, 0));
    }

    /**
     * Test for doTurn method when GUI is used, and it's player's turn.
     */
    @Test
    void doTurn11() {
        game.setUseGUI(true);
        game.setGUI(new Thread());
        game.doTurn();
        assertEquals('x', game.getWinningPlayer());
    }

    /**
     * Test for doTurn method when piece X is taken.
     */
    @Test
    void doTurn12() {
        game.setBegin(false);
        game.setComputer(true);
        game.setPlayer('o');
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', 'x', '_', '_', '_', '_', '_', '_'},
                {'_', '_', 'o', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'o', '_', '_', '_', '_', '_', '_', '_'}
        });
        game.doTurn();
        assertEquals('o', game.getSquare(0, 0));
    }

    /**
     * Test for doTurn method when piece O is taken.
     */
    @Test
    void doTurn13() {
        game = new CheckersGameLogic(new Scanner("5c-7a\n"));
        game.setBegin(false);
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', 'x', '_', '_', '_', '_', '_'},
                {'_', 'o', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        });
        game.doTurn();
        assertEquals('x', game.getSquare(6, 0));
    }

    /**
     * Test for doTurn when GUI is selected.
     */
    @Test
    void doTurn14() {
        game = new CheckersGameLogic(new Scanner("g\nc\n"));
        game.doTurn();
        assertNotNull(game.getGUI());
    }

    /**
     * Test for doTurn method when move is out of bounds.
     */
    @Test
    void doTurn15() {
        game = new CheckersGameLogic(new Scanner("1a-0a\n"));
        game.setBegin(false);
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Player X - your turn."
                            + System.lineSeparator()
                            + "Choose a cell position of piece to be moved"
                            + " and the new position. e.g., 3a-4b"
                            + System.lineSeparator()
                            + "Invalid move. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when invalid jump is attempted.
     */
    @Test
    void doTurn16() {
        game = new CheckersGameLogic(new Scanner("5c-7a\n"));
        game.setBegin(false);
        game.setBoard(new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', 'x', '_', '_', '_', '_', '_'},
                {'_', 'o', '_', '_', '_', '_', '_', '_'},
                {'o', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}
        });
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Player X - your turn."
                            + System.lineSeparator()
                            + "Choose a cell position of piece to be moved"
                            + " and the new position. e.g., 3a-4b"
                            + System.lineSeparator()
                            + "Invalid move. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for doTurn method when attempting to move backwards.
     */
    @Test
    void doTurn17() {
        game = new CheckersGameLogic(new Scanner("3a-2b\n"));
        game.setBegin(false);
        try {
            game.doTurn();
        } catch (NoSuchElementException e) {
            assertEquals("Player X - your turn."
                            + System.lineSeparator()
                            + "Choose a cell position of piece to be moved"
                            + " and the new position. e.g., 3a-4b"
                            + System.lineSeparator()
                            + "Invalid move. Try again.",
                    newOut.toString().trim());
        }
    }

    /**
     * Test for onEnd method when GUI is not used.
     */
    @Test
    void onEnd1() {
        game.setUseGUI(false);
        game.onEnd();
        assertEquals("7|_|o|_|o|_|o|_|o|" + System.lineSeparator()
                + "6|o|_|o|_|o|_|o|_|" + System.lineSeparator()
                + "5|_|o|_|o|_|o|_|o|" + System.lineSeparator()
                + "4|_|_|_|_|_|_|_|_|" + System.lineSeparator()
                + "3|_|_|_|_|_|_|_|_|" + System.lineSeparator()
                + "2|x|_|x|_|x|_|x|_|" + System.lineSeparator()
                + "1|_|x|_|x|_|x|_|x|" + System.lineSeparator()
                + "0|x|_|x|_|x|_|x|_|" + System.lineSeparator()
                + "  a b c d e f g h\n" + System.lineSeparator()
                + "Player _ Won the Game", newOut.toString().trim());
    }

    /**
     * Test for onEnd method when GUI is used.
     */
    @Test
    void onEnd2() {
        game.setUseGUI(true);
        game.onEnd();
        assertEquals("Player _ Won the Game", newOut.toString().trim());
    }
}