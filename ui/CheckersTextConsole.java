package edu.ser216.checkers.ui;

import edu.ser216.checkers.core.CheckersGame;

/**
 * A class used to display a game of checkers to console. Implements
 * the CheckersViewer interface.
 *
 * @author Ibrahim Noriega A., Baron
 * @version 1.0 Feb 21, 2023
 */
public class CheckersTextConsole implements CheckersViewer {

    /**
     * Returns string representation of the board in a CheckersGame. A 'o' will represent a piece
     * for player O, and an 'x' will represent a piece for player X. A '_' will represent an
     * unoccupied space. The axis will be labeled with numbers and letters as shown below, a
     * vertical bar will separate columns.
     * <p>
     * For example:
     * 8|_|o|_|o|_|o|_|o|
     * 7|o|_|o|_|o|_|o|_|
     * 6|_|o|_|o|_|o|_|o|
     * 5|_|_|_|_|_|_|_|_|
     * 4|_|_|_|_|_|_|_|_|
     * 3|x|_|x|_|x|_|x|_|
     * 2|_|x|_|x|_|x|_|x|
     * 1|x|_|x|_|x|_|x|_|
     * a b c d e f g h
     *
     * @param game Game to visualize.
     * @return String representation of board.
     */
    @Override
    public String printBoard(CheckersGame game) {
        String str = "";
        for (int i = 7; i >= 0; i--) {
            str = str + (i + 1) + '|';
            for (int j = 0; j < 8; j++) {
                str = str + game.getSquare(i, j) + '|';
            }
            str = str + '\n';
        }
        str = str + "  a b c d e f g h\n";

        return str;
    }
}
