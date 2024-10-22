
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.util.Queue;

class Board {

    public char[][] matrix;
    public int column;
    public int row;

    public Board(char[][] matrix, int column, int row) {
        this.matrix = matrix;
        this.column = column;
        this.row = row;
    }

    public Board(char[][] matrix, int[] position) {
        this.matrix = matrix;
        this.column = position[1];
        this.row = position[0];
    }

}

enum MOVE {
    UP,
    DOWN,
    RIGHT,
    LEFT
}

class State {

    final static char emptyTile = '0';
    final static char wall = '1';
    final static char player = '2';
    final static char goal = '3';

    public Board board;
    MOVE lastMove;
    State parent = null;
    int depth = 0;
    // children ?

    public State(Board board, MOVE lastMove, State parent) {
        this.board = board;
        this.lastMove = lastMove;
        this.parent = parent;
        if (parent != null) {
            this.depth = parent.depth + 1;

        } else
            System.out.println("z");
    }

    public ArrayList<State> getChildren() {

        ArrayList<State> states = new ArrayList<>();
        for (MOVE element : possibleMoves(board)) {

            char[][] clonedMatrix = new char[board.matrix.length][];
            for (int i = 0; i < board.matrix.length; i++) {
                clonedMatrix[i] = board.matrix[i].clone();
            }
            // Should clone the current board and use adjustMoves function on it;
            Board newBoard = new Board(clonedMatrix, board.column, board.row);
            adjustMoveBoard(newBoard, element);
            State newChildState = new State(newBoard, element, this);
            states.add(newChildState);
        }

        return states;
    }

    public boolean checkState() {
        // System.out.println("Checking state");
        // returns true if the player is at goal;
        if (board.matrix[board.row][board.column] == State.goal) {
            board.matrix[board.row][board.column] = State.player;
            // System.out.println("I caught the goal");
            return true;
        } else
            return false;
    }

    // Printing the board.
    public static void printBoard(Board board) {

        System.out.println("Board state:");
        for (int i = 0; i < board.matrix.length; i++) {
            for (int j = 0; j < board.matrix[i].length; j++) {
                System.out.print(board.matrix[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }
        System.out.println();
    }

    public static ArrayList<MOVE> possibleMoves(Board board) {
        int row = board.row;
        int column = board.column;

        ArrayList<MOVE> moves = new ArrayList<>();

        if (board.matrix[row - 1][column] == emptyTile || board.matrix[row - 1][column] == goal) {
            moves.add(MOVE.UP);
        }
        if (board.matrix[row + 1][column] == emptyTile || board.matrix[row + 1][column] == goal) {
            moves.add(MOVE.DOWN);
        }
        if (board.matrix[row][column + 1] == emptyTile || board.matrix[row][column + 1] == goal) {
            moves.add(MOVE.RIGHT);
        }
        if (board.matrix[row][column - 1] == emptyTile || board.matrix[row][column - 1] == goal) {
            moves.add(MOVE.LEFT);
        }

        // Shuffle the list of moves
        // Collections.shuffle(moves);
        return moves;
    }

    public static void adjustMoveBoard(Board board, MOVE move) {

        // CHANGES THE BOARD, doesn't make "moves"
        int dx = 0;
        int dy = 0;

        switch (move) {
            case UP:
                dy = -1;
                break;
            case DOWN:
                dy = +1;
                break;
            case RIGHT:
                dx = +1;
                break;
            case LEFT:
                dx = -1;
                break;
        }

        while (board.matrix[board.row + dy][board.column + dx] != wall
                && board.matrix[board.row + dy][board.column + dx] != goal) {
            board.matrix[board.row][board.column] = wall;
            board.row += dy;
            board.column += dx;
            board.matrix[board.row][board.column] = player;
        }
        if (board.matrix[board.row + dy][board.column + dx] == goal) {
            board.matrix[board.row][board.column] = wall;
            board.row += dy;
            board.column += dx;
            // System.out.println("goal");
        } // goal

    }

}

public class Main {

    public static void main(String[] args) {
        // Load the levels from files
        ArrayList<char[][]> levels = loadLevels();
        System.out.println("Number of levels loaded: " + levels.size());

        // Solve each level using BFS and DFS
        int levelIndex = 1;
        for (var level : levels) {
            // Initialize the board with the current level
            Board board = new Board(level, findInitialLocation(level));
            State initialState = new State(board, null, null);

            // Solve using BFS
            BFS bfsSolver = new BFS();
            bfsSolver.bfs(initialState, levelIndex);

            // Solve using DFS
            DFS dfsSolver = new DFS();
            dfsSolver.dfs(initialState, levelIndex);

            // Increment the level index for the next iteration
            levelIndex++;
        }
    }

    // Method to find the initial location of the player
    static int[] findInitialLocation(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == State.player) {
                    return new int[] { i, j }; // Return the found location
                }
            }
        }
        return null; // Return null if not found
    }

    // Method to load levels from text files
    static ArrayList<char[][]> loadLevels() {
        final int numOfLevels = 14;
        ArrayList<char[][]> levels = new ArrayList<>();
        for (int i = 1; i <= numOfLevels; i++) {
            String fileName = String.format("Levels/level%02d.txt", i);
            levels.add(readLevel(fileName));
        }
        return levels; // Return the loaded levels
    }

    // Method to read a single level from a file
    static char[][] readLevel(String fileName) {
        ArrayList<char[]> level = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                level.add(line.replace(",", "").toCharArray());
            }
        } catch (IOException e) {
            System.err.println("Error reading level file: " + fileName);
            e.printStackTrace();
        }
        return level.toArray(new char[level.size()][]);
    }
}
