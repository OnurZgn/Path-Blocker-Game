import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
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

class DFS {
    ArrayList<State> dfs(State iniState) {
        Stack<State> stack = new Stack<>();
        stack.add(iniState);
        while (!stack.isEmpty()) {
            State state = stack.pop();
            if (state.checkState()) {
                System.out.println("DFS caught the goal!");
                ArrayList<State> path = new ArrayList<>();
                State node = state;

                while (node != null) {
                    path.add(node);
                    node = node.parent;
                }
                return path;
            }

            for (State child : state.getChildren()) {
                stack.push(child);
            }
        }
        return null;
    }
}

class BFS {
    // Tree bfs
    ArrayList<State> bfs(State initState) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initState);
        while (!queue.isEmpty()) {
            State stat = queue.poll();
            // State.printBoard(stat.board);
            if (stat.checkState()) {

                System.out.println("BFS caught the goal!");
                State node = stat;
                ArrayList<State> path = new ArrayList<>(); // Initialize the path list

                while (node != null) { // Change to check if node is null
                    path.add(node); // Add the current node to the path
                    node = node.parent; // Move to the parent
                }

                return path;
            }
            // we are in the goal

            for (var child : stat.getChildren()) {
                // child.printBoard(child.board);
                queue.add(child);
            }
        }

        return null; // NO SOLUTION FOUND
    }

}

class DLS {
    ArrayList<State> dls(State initState, int limit) {
        return recursiveDLS(initState, limit);     // Call the recursive depth-limited search function
    }

    private ArrayList<State> recursiveDLS(State state, int limit) {
        if (state.checkState()) {                       // If goal is found
            System.out.println("DLS caught the goal!");
            ArrayList<State> path = new ArrayList<>();
            State node = state;
            while (node != null) {
                path.add(node); 
                node = node.parent; 
            }
            return path;
        }
        
        if (limit <= 0) {                    // If depth limit reached, stop further exploration
            return null; 
        }

        for (State child : state.getChildren()) {
            ArrayList<State> result = recursiveDLS(child, limit - 1); // Recursive call with reduced limit
            if (result != null) {
                return result; 
            }
        }
        return null; 
    }
}

class IDS {
    ArrayList<State> ids(State initState, int maxDepth) {
        for (int i = 0; i <= maxDepth; i++) {        // i = depth
            System.out.println("Current searching depth limit: " + i);
            ArrayList<State> result = dls(initState, i);
            if (result != null) {
                System.out.println("IDS caught the goal in depth" + i);
                return result; 
            }
        }
        return null;
    }

    private ArrayList<State> dls(State state, int limit) {
        if (state.checkState()) {       // If goal is found
            ArrayList<State> path = new ArrayList<>();
            State node = state;
            while (node != null) {
                path.add(node); 
                node = node.parent; 
            }
            return path; 
        }
       
        if (limit <= 0) {       // If depth limit reached, stop further exploration
            return null;
        }

        for (State child : state.getChildren()) {
            ArrayList<State> result = dls(child, limit - 1); // Recursive call with reduced limit
            if (result != null) {
                return result; 
            }
        }
        return null; 
    }
}

enum MOVE {           //  Player movements are kept with enum class
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

    public State(Board board, MOVE lastMove, State parent) {
        this.board = board;
        this.lastMove = lastMove;
        this.parent = parent;
    }

    public ArrayList<State> getChildren() {

        ArrayList<State> states = new ArrayList<>();
        System.out.println(possibleMoves(board).size());
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
        System.out.println("Checking state");
        
        if (board.matrix[board.row][board.column] == State.goal) {        //  returns true if the player is at goal;
            board.matrix[board.row][board.column] = State.player;
            System.out.println("I caught the goal");
            return true;
        } else
            return false;
    }

    public static void printBoard(Board board) {

        System.out.println("Board state:");
        for (int i = 0; i < board.matrix.length; i++) {
            for (int j = 0; j < board.matrix[i].length; j++) {
                System.out.print(board.matrix[i][j] + " ");
            }
            System.out.println(); 
        }
        System.out.println();
    }

    public static ArrayList<MOVE> possibleMoves(Board board) {

        int row = board.row;
        int column = board.column;

        ArrayList<MOVE> moves = new ArrayList<>();             // The moves that can be done are added in the ArrayList (if one unit distance is empty or goal)
        if (board.matrix[row + 1][column] == emptyTile || board.matrix[row + 1][column] == goal) {
            moves.add(MOVE.DOWN);
        }
        if (board.matrix[row - 1][column] == emptyTile || board.matrix[row - 1][column] == goal) {
            moves.add(MOVE.UP);
        }
        if (board.matrix[row][column + 1] == emptyTile || board.matrix[row][column + 1] == goal) {
            moves.add(MOVE.RIGHT);
        }
        if (board.matrix[row][column - 1] == emptyTile || board.matrix[row][column - 1] == goal) {
            moves.add(MOVE.LEFT);
        }

        return moves;
    }

    public static void adjustMoveBoard(Board board, MOVE move) {

        // CHANGES THE BOARD, doesn't make "moves"
        int dx = 0;
        int dy = 0;

        switch (move) {                   // Displacement is calculated according to the type of move
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

        while (board.matrix[board.row + dy][board.column + dx] != wall      // Unless the wall or goal is reached in the selected move,
                && board.matrix[board.row + dy][board.column + dx] != goal) {  // progress is constant and the coordinates passed are marked as walls.
            board.row += dy;
            board.column += dx;
            board.matrix[board.row][board.column] = player;
        }
        if (board.matrix[board.row + dy][board.column + dx] == goal) {     // If the goal is one unit further in the selected movement, the goal is reached.       
            board.matrix[board.row][board.column] = wall;
            board.row += dy;
            board.column += dx;
            System.out.println("goal");
        } 

    }

}

public class Main {

    public static void main(String[] args) {

        ArrayList<char[][]> levels = loadLevels();       
        System.out.println(levels.size());
        int i = 1;
        for (var level : levels) {            // creates a game board for each level and visualizes if a solution is exist.
            Board board = new Board(level, findInitialLocation(level));
            State state = new State(board, null, null);
            BFS solver = new BFS();
            ArrayList<State> path = solver.bfs(state);
            if (path != null) {
                System.out.println(path.size());
                visual.visualizePath(path, i);
            }
            i++;
        }
    }

}