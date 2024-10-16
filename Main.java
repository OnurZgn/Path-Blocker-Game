import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
    // children ?

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
        // returns true if the player is at goal;
        if (board.matrix[board.row][board.column] == State.goal) {
            board.matrix[board.row][board.column] = State.player;
            System.out.println("I caught the goal");
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
            System.out.println("goal");
        } // goal

    }

}

public class Main {

    public static void main(String[] args) {

        ArrayList<char[][]> levels = loadLevels();
        System.out.println(levels.size());
        int i = 1;
        for (var level : levels) {
            Board board = new Board(level, findInitialLocation(level));
            State state = new State(board, null, null);
            BFS solver = new BFS();
            ArrayList<State> path = solver.bfs(state);
            if (path != null) {
                System.out.println(path.size());
                visualizePath(path, i);
            }
            i++;
        }
    }

    static void visualizePath(ArrayList<State> path, int level) {
        int rectSize = 50;

        String folderName = String.format("Images/level%02d", level);
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int nodei = path.size() - 1; nodei >= 0; nodei--) {
            BufferedImage image = new BufferedImage(rectSize * path.get(nodei).board.matrix.length,
                    rectSize * path.get(nodei).board.matrix[0].length,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            for (int i = path.get(nodei).board.matrix.length - 1; i >= 0; i--) {
                for (int j = path.get(nodei).board.matrix[i].length - 1; j >= 0; j--) {
                    g.setColor(getColor(path.get(nodei).board.matrix[i][j]));
                    g.fillRect(j * rectSize, i * rectSize, rectSize, rectSize);
                }
            }
            g.dispose();

            try {
                String fileName = String.format("%s/%04d.png", folderName, path.size() - nodei);
                File levelFile = new File(fileName);
                ImageIO.write(image, "png", levelFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static int[] findInitialLocation(char[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == State.player) {
                    int[] arr = { i, j };
                    System.out.println(Arrays.toString(arr));
                    return arr;
                }
            }

        }
        return null;
    }

    static ArrayList<char[][]> loadLevels() {
        final int numOfLevels = 10;
        ArrayList<char[][]> levels = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            /*
             * StringBuffer fileName = new StringBuffer("levels/level");
             * fileName.append(Integer.toString(i)).append(".txt");
             */
            String fileName = String.format("Levels/level%02d.txt", i);

            levels.add(readLevel(fileName));
        }

        return levels;
    }

    static char[][] readLevel(String fileName) {
        ArrayList<char[]> level = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                level.add(line.replace(",", "").toCharArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return level.toArray(new char[level.size()][]);
    }

    static Color getColor(char tile) {
        final char emptyTile = '0';
        final char wall = '1';
        final char player = '2';
        final char goal = '3';
        Color playerColor = new Color(248, 204, 68), goalColor = Color.red, wallColor = new Color(56, 12, 100),
                emptyTileColor = new Color(136, 140, 236);
        switch (tile) {
            case emptyTile:
                return emptyTileColor;
            case wall:
                return wallColor;
            case player:
                return playerColor;
            case goal:
                return goalColor;
            default:
                return null;
        }
    }

}