package edu.ser216.checkers.ui;

import edu.ser216.checkers.core.CheckersGameLogic;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A class used to create and display a GUI for a game of checkers.
 *
 * @author Ibrahim Noriega A.
 * @version 1.0 April 4, 2023
 */
public class CheckersGUI extends Application {
    private static Queue<Integer[]> movesQ = new LinkedList<>();
    private Label label;
    private char[][] board;
    private Button[][] buttons;

    /**
     * Used to create a new CheckersGUI object.
     */
    public CheckersGUI() {
        board = CheckersGameLogic.getBoard();
        label = new Label("Player X - your turn.");
        if (CheckersGameLogic.isComputerPlayer()) {
            label.setText("Playing against Computer.\n" + label.getText());
        }
    }

    /**
     * Static method used to access moves queue.
     *
     * @return Moves queue
     */
    public static Queue<Integer[]> getMoves() {
        return movesQ;
    }

    /**
     * Application starting point.
     *
     * @param s Application's stage
     */
    @Override
    public void start(Stage s) {
        // Root
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(
                Color.grayRgb(25), null, null)));

        // Board Grid
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(
                Color.grayRgb(100), null, null)));
        grid.setGridLinesVisible(true);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        // row/column constraints
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100.0 / 8);
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100.0 / 8);
        grid.getColumnConstraints().addAll(cc, cc, cc, cc, cc, cc, cc, cc);
        grid.getRowConstraints().addAll(rc, rc, rc, rc, rc, rc, rc, rc);
        // add buttons
        Handler handler = new Handler();
        buttons = new Button[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String str = "" + (i + 1) + (char) (j + 'a');
                buttons[i][j] = new Button(str);
                buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttons[i][j].setBackground(Background.EMPTY);
                buttons[i][j].setFont(new Font(0));
                buttons[i][j].setOnAction(handler);
                grid.add(buttons[i][j], j, 7 - i);
            }
        }
        updateGrid();   // paint circle pieces
        // row/column constraints
        cc = new ColumnConstraints();
        ColumnConstraints cc1 = new ColumnConstraints();
        cc.setPercentWidth(10);
        cc1.setPercentWidth(80);
        rc = new RowConstraints();
        rc.setPercentHeight(80);
        root.getColumnConstraints().addAll(cc, cc1, cc);
        root.getRowConstraints().add(rc);
        root.add(grid, 1, 0);

        // Display Label
        StackPane sp = new StackPane();
        sp.getChildren().add(label);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        label.setTextFill(Color.WHITE);
        rc = new RowConstraints();
        rc.setPercentHeight(20);
        root.getRowConstraints().add(rc);
        root.add(sp, 1, 1);

        // Create and display scene
        Scene sc = new Scene(root, 600, 600);
        s.setTitle("Checkers");
        s.setScene(sc);
        s.setResizable(false);
        s.show();
    }

    /**
     * Class used to handle grid button presses.
     */
    class Handler implements EventHandler<ActionEvent> {
        Integer[] move = new Integer[]{-1, -1, -1, -1}; // stores move

        /**
         * Handles button press and adds desired move to moves list.
         *
         * @param actionEvent action performed
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            Button b = (Button) actionEvent.getSource();
            if (move[0] == -1) {    // first grid selection
                move[0] = b.getText().charAt(0) - '1';
                move[1] = b.getText().charAt(1) - 'a';
                label.setText(b.getText() + "-");
            } else {  // second grid selection
                move[2] = b.getText().charAt(0) - '1';
                move[3] = b.getText().charAt(1) - 'a';
                movesQ.add(move);   // add move to moves list
                move = new Integer[]{-1, -1, -1, -1};   // new move
                updateGrid();
                char player = CheckersGameLogic.getPlayer();
                if (CheckersGameLogic.isGameOver()) {
                    label.setText("Player " + Character.toUpperCase(player)
                            + " Won the Game.");
                } else {
                    label.setText("Player " + Character.toUpperCase(player)
                            + " - your turn.");
                }
            }
        }
    }

    /**
     * Used to display game board pieces in the GUI.
     */
    private void updateGrid() {
        if (board == null) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double radius = 25;
                Circle piece = new Circle(radius, radius, radius);
                piece.setFill(Color.TRANSPARENT);
                if (board[i][j] == 'o') {
                    piece.setStroke(Color.BLACK);
                    piece.setFill(Color.BLACK);
                } else if (board[i][j] == 'x') {
                    piece.setStroke(Color.BLACK);
                    piece.setFill(Color.DARKRED);
                }
                buttons[i][j].setGraphic(piece);
            }
        }
    }
}