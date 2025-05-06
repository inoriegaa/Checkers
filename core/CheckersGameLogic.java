package edu.ser216.checkers.core;

import edu.ser216.checkers.ui.CheckersGUI;
import javafx.application.Application;

import java.util.Queue;
import java.util.Scanner;

/**
 * A class used to define a game of checkers. Implements the CheckersGame
 * interface.
 *
 * @author Ibrahim Noriega A., Baron
 * @version 1.2 April 4, 2023
 */
public class CheckersGameLogic implements CheckersGame {

    // Data members
    private static char[][] board; // game board
    private static boolean computer;   // computer player

    private static char player;    // current player

    private static boolean gameOver;    // game end
    private Scanner scan;   // input scanner
    private int countX;     // num pieces for player X
    private int countO;     // num pieces for player O
    private boolean begin;  // first round check
    private boolean useGUI; // enable GUI
    private Thread gui; // thread running GUI


    /**
     * Initializes a new CheckersGameLogic object.
     *
     * @param scan Scanner used for user input
     */
    public CheckersGameLogic(Scanner scan) {
        this.scan = scan;
        board = new char[][]{
                {'x', '_', 'x', '_', 'x', '_', 'x', '_'},
                {'_', 'x', '_', 'x', '_', 'x', '_', 'x'},
                {'x', '_', 'x', '_', 'x', '_', 'x', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', 'o', '_', 'o', '_', 'o', '_', 'o'},
                {'o', '_', 'o', '_', 'o', '_', 'o', '_'},
                {'_', 'o', '_', 'o', '_', 'o', '_', 'o'}
        };
        player = 'x';
        countX = 12;
        countO = 12;
        begin = true;
        computer = false;
        useGUI = false;
        gameOver = false;
    }

    /**
     * Indicates if the current game has finished execution.
     *
     * @return A boolean that is true if the game is over
     */
    public static boolean isGameOver() {
        return gameOver;
    }

    /**
     * Used to get the current player.
     *
     * @return A char 'x' or 'o' depending on the current player
     */
    public static char getPlayer() {
        return player;
    }

    /**
     * Used to set the current player.
     *
     * @param newPlayer char indicating new player
     */
    public static void setPlayer(char newPlayer) {
        player = newPlayer;
    }

    /**
     * Indicates if the second player is a Computer Player.
     *
     * @return A boolean that if true is P2 is a computer
     */
    public static boolean isComputerPlayer() {
        return computer;
    }

    /**
     * Used to set value for computer player.
     *
     * @param isComputer boolean value
     */
    public static void setComputer(boolean isComputer) {
        computer = isComputer;
    }

    /**
     * Used to get the game's board.
     *
     * @return A 2D char array that represents the game's board
     */
    public static char[][] getBoard() {
        return board;
    }

    /**
     * Used to set the game's board.
     *
     * @param newBoard 2D char array
     */
    public static void setBoard(char[][] newBoard) {
        board = newBoard;
    }

    /**
     * Returns the square at a specific location on the game board.
     * <p>
     * For example, given the following board:
     * 8|_|o|_|o|_|o|_|o|
     * 7|o|_|o|_|o|_|o|_|
     * 6|_|o|_|o|_|o|_|o|
     * 5|_|_|_|_|_|_|_|_|
     * 4|_|_|_|_|_|_|_|_|
     * 3|x|_|x|_|x|_|x|_|
     * 2|_|x|_|x|_|x|_|x|
     * 1|x|_|x|_|x|_|x|_|
     * a b c d e f g h
     * getSquare(0,0) would return 'x'.
     *
     * @param row    Row.
     * @param column Column.
     * @return Contents of game board at position.
     */
    @Override
    public char getSquare(int row, int column) {
        return board[row][column];
    }

    /**
     * Sets the contents ('x', 'o', or '_') of the game board.
     *
     * @param row     Row.
     * @param column  Column.
     * @param content Contents.
     */
    @Override
    public void setSquare(int row, int column, char content) {
        board[row][column] = content;
    }

    /**
     * Used to set number of pieces for Player X.
     *
     * @param countX number of pieces
     */
    public void setCountX(int countX) {
        this.countX = countX;
    }

    /**
     * Used to set number of pieces for Player O.
     *
     * @param countO number of pieces
     */
    public void setCountO(int countO) {
        this.countO = countO;
    }

    /**
     * Used to set begin attribute.
     *
     * @param begin boolean value
     */
    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    /**
     * Used to set the value of useGUI.
     *
     * @param useGUI boolean indicating if GUI is being used
     */
    public void setUseGUI(boolean useGUI) {
        this.useGUI = useGUI;
    }

    /**
     * Used to set the thread for the gui.
     *
     * @param gui a thread to run gui
     */
    public void setGUI(Thread gui) {
        this.gui = gui;
    }

    /**
     * Used to get the thread for the gui.
     *
     * @return a thread that's running the GUI
     */
    public Thread getGUI() {
        return gui;
    }

    /**
     * Returns 'x' if player X  has won, 'o' if player O has won, and '_' otherwise. May be called
     * at any time.
     *
     * @return A character representing the winter.
     */
    @Override
    public char getWinningPlayer() {
        if (countO == 0) {
            player = 'x';
            return player;
        }
        if (countX == 0) {
            player = 'o';
            return player;
        }
        if (!canMove()) {
            return player;
        }
        return '_';
    }

    /**
     * Indicates to the game that it is the next player's turn.
     */
    @Override
    public void nextTurn() {
        player = player == 'x' ? 'o' : 'x';
    }

    /**
     * Indicates to the game that a players turn should occur (i.e., read a move and act upon it).
     * To read input from the keyboard, this method must make use of the Scanner object passed
     * into the constructor of this CheckersGame. Valid inputs must look like "3a-4b".
     */
    @Override
    public void doTurn() {
        if (!useGUI) {
            doTurnConsole();
        } else {
            doTurnGUI();
        }
    }

    /**
     * Used by doTurn() to perform player turn when using a GUI
     */
    private void doTurnGUI() {
        if (computer && player == 'o') {
            makeComputerMove();
            return;
        }

        Queue<Integer[]> q = CheckersGUI.getMoves();
        while (true) {
            while (gui.isAlive() && q.isEmpty()) ;   // wait until move is given

            if (!gui.isAlive()) {
                countO = 0;
                break;
            }

            Integer[] moves = q.remove();
            if (isMoveValid(moves[0], moves[1], moves[2], moves[3])) {
                makeMove(moves[0], moves[1], moves[2], moves[3]);
                break;
            }
        }
    }

    /**
     * Used by doTurn() to perform player turn when using console UI
     */
    private void doTurnConsole() {
        if (begin) {
            begin = false;
            System.out.println("Begin Game. Enter 'G' to play with a GUI;"
                    + " enter 'C' to play with a console-based UI.");
            // Select UI
            String choiceUI;
            while (true) {
                choiceUI = scan.next();
                if (choiceUI.length() != 1) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }
                char c = Character.toUpperCase(choiceUI.charAt(0));
                if (c != 'G' && c != 'C') {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
                if (c == 'G') {
                    useGUI = true;
                }
                break;
            }
            System.out.println("Enter 'P' if you want to play"
                    + " against another player; enter 'C' to play against\n"
                    + "computer");
            // Select player 2
            String choice;
            while (true) {
                choice = scan.next();
                if (choice.length() != 1) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }
                char c = Character.toUpperCase(choice.charAt(0));
                if (c != 'C' && c != 'P') {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
                if (c == 'C') {
                    computer = true;
                }
                break;
            }
            if (computer) {
                System.out.println("Start game against computer. You are"
                        + " Player X and Computer is Player O.\n");
            }
            System.out.print("Begin Game. ");
        }
        if (useGUI) {
            gui = new Thread(() -> {
                Application.launch(CheckersGUI.class, new String[]{});
            });
            gui.start();
            doTurnGUI();
            return;
        }
        // display prompt
        if (computer && player == 'o') {
            System.out.println("Computer's turn");
        } else {
            System.out.println("Player "
                    + Character.toUpperCase(player)
                    + " - your turn.");
            System.out.println("Choose a cell position of piece"
                    + " to be moved and the new position. e.g., 3a-4b");
        }
        int rowI;
        int columnI;
        int rowF;
        int columnF;
        String input;
        // if computers turn
        if (computer && player == 'o') {
            makeComputerMove();
        } else {
            // loop for valid input
            while (true) {
                input = scan.next();
                if (input.length() != 5 || input.charAt(2) != '-') {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                // get index value from characters
                rowI = input.charAt(0) - '1';
                columnI = input.charAt(1) - 'a';
                rowF = input.charAt(3) - '1';
                columnF = input.charAt(4) - 'a';

                if (!isMoveValid(rowI, columnI, rowF, columnF)) {
                    System.out.println("Invalid move. Try again.");
                } else {
                    makeMove(rowI, columnI, rowF, columnF);
                    break;
                }
            }
        }
    }

    /**
     * Indicates to the game that it is over. Used to do things like display the winner.
     */
    @Override
    public void onEnd() {
        gameOver = true;
        // print board
        if (!useGUI) {
            for (int i = 7; i >= 0; i--) {
                System.out.print(i + "|");
                for (int j = 0; j < 8; j++) {
                    System.out.print(board[i][j] + "|");
                }
                System.out.println();
            }
            System.out.println("  a b c d e f g h\n");
        }
        // announce winner
        char winner = Character.toUpperCase(getWinningPlayer());
        System.out.println("Player " + winner + " Won the Game");
    }

    /**
     * Indicates if the opponent player can make any moves.
     *
     * @return A boolean which is true if the opponent can still move.
     */
    private boolean canMove() {
        char tmp = player;  // save current player
        player = player == 'x' ? 'o' : 'x';
        // check for possible moves
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isMoveValid(i, j, i + 1, j - 1)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i + 1, j + 1)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i + 2, j - 2)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i + 2, j + 2)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i - 1, j - 1)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i - 1, j + 1)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i - 2, j - 2)) {
                    player = tmp;
                    return true;
                }
                if (isMoveValid(i, j, i - 2, j + 2)) {
                    player = tmp;
                    return true;
                }
            }
        }
        player = tmp;
        return false;
    }

    /**
     * Indicates if a desired move is valid based on the current player.
     *
     * @param rowI    Piece row
     * @param columnI Piece column
     * @param rowF    Destination row
     * @param columnF Destination Column
     * @return A boolean which is true if the move can be made
     */
    private boolean isMoveValid(int rowI, int columnI, int rowF, int columnF) {
        // check bounds
        if (rowF < 0 || rowF > 7) {
            return false;
        }
        if (rowI < 0 || rowI > 7) {
            return false;
        }
        if (columnF < 0 || columnF > 7) {
            return false;
        }
        if (columnI < 0 || columnI > 7) {
            return false;
        }
        // check current player
        if (board[rowI][columnI] != player) {
            return false;
        }
        if (player == 'x' && rowF - rowI < 0) {
            return false;
        }
        if (player == 'o' && rowF - rowI > 0) {
            return false;
        }
        if (Math.abs(rowF - rowI) == 1 && Math.abs(columnF - columnI) == 1) {
            return board[rowF][columnF] == '_';
        }
        // check jump
        if (Math.abs(rowF - rowI) == 2 && Math.abs(columnF - columnI) == 2) {
            if (board[rowF][columnF] != '_') {
                return false;
            } else {
                if (columnF > columnI) {
                    columnF -= 1;
                } else if (columnF < columnI) {
                    columnF += 1;
                }
                if (player == 'x') {
                    rowF -= 1;
                    return board[rowF][columnF] == 'o';
                } else if (player == 'o') {
                    rowF += 1;
                    return board[rowF][columnF] == 'x';
                }
            }
        }
        return false;
    }

    /**
     * Updates the board by moving the selected piece to the desired
     * destination, removing opponent pieces if necessary.
     *
     * @param rowI    Piece row
     * @param columnI Piece column
     * @param rowF    Destination row
     * @param columnF Destination column
     */
    private void makeMove(int rowI, int columnI, int rowF, int columnF) {
        // update board
        board[rowI][columnI] = '_';
        board[rowF][columnF] = player;
        // check if jump occurred
        if (Math.abs(rowF - rowI) == 2) {
            if (player == 'x') {
                countO--;
            } else {
                countX--;
            }
            if (columnF > columnI) {
                columnF -= 1;
            } else {
                columnF += 1;
            }
            if (player == 'x') {
                rowF -= 1;
            } else {
                rowF += 1;
            }
            board[rowF][columnF] = '_';
        }
    }

    /**
     * Used to perform computer player move
     */
    private void makeComputerMove() {
        int[][] moves = CheckersComputerPlayer.getMoves(countO, board);
        for (int i = 0; i < moves.length; i++) {
            int rowI = moves[i][0];
            int columnI = moves[i][1];
            int rowF = moves[i][2];
            int columnF = moves[i][3];
            if (isMoveValid(rowI, columnI, rowF, columnF)) {
                makeMove(rowI, columnI, rowF, columnF);
                break;
            }
        }
    }
}
