import java.util.ArrayList;

public class Board {

    // A unique state of the board.
    // Keeps track of the agent position and the map.
    public char[][] matrix;
    public int column;
    public int row;
    final static char emptyTile = '0';
    final static char wall = '1';
    final static char player = '2';
    final static char goal = '3';

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

    public Board(char[][] matrix) {
        this.matrix = matrix;
        int position[] = findInitialLocation(matrix);
        this.row = position[0];
        this.column = position[1];

    }

    private int[] findInitialLocation(char[][] matrix) {
        // Traveses the array to find "2" numbered tile, which is initial location.
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == player) {
                    int[] arr = { i, j };
                    return arr;
                }
            }
        }
        return null;
    }

    void adjustMoveBoard(MOVE move) {
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

        while (matrix[row + dy][column + dx] != wall && matrix[row + dy][column + dx] != goal) {
            addWall(dx, dy);
            matrix[row][column] = player;
        }

        if (matrix[row + dy][column + dx] == goal) {
            addWall(dx, dy);
        }

    }

    void addWall(int x, int y) {
        matrix[row][column] = wall;
        row += y;
        column += x;
    }

    ArrayList<MOVE> possibleMoves() {
        // Returns a list of possible moves the agent can make.
        // Maximum of two moves except for the first state.
        ArrayList<MOVE> moves = new ArrayList<>();

        if (isMovable(row - 1, column)) {
            moves.add(MOVE.UP);
        }
        if (isMovable(row + 1, column)) {
            moves.add(MOVE.DOWN);
        }
        if (isMovable(row, column + 1)) {
            moves.add(MOVE.RIGHT);
        }
        if (isMovable(row, column - 1)) {
            moves.add(MOVE.LEFT);
        }
        return moves;
    }

    private boolean isMovable(int row, int column) {
        return (matrix[row][column] == emptyTile || matrix[row][column] == goal);
    }
}