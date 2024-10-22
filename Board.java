import java.util.ArrayList;

public class Board {

    public char[][] matrix;
    public int column;
    public int row;
    final char emptyTile = '0';
    final char wall = '1';
    final char player = '2';
    final char goal = '3';

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

    int[] findInitialLocation(char[][] matrix) {

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

        while (matrix[row + dy][column + dx] != wall
                && matrix[row + dy][column + dx] != goal) {
            matrix[row][column] = wall;
            row += dy;
            column += dx;
            matrix[row][column] = player;
        }
        if (matrix[row + dy][column + dx] == goal) {
            matrix[row][column] = wall;
            row += dy;
            column += dx;
            System.out.println("goal");
        }
    }

    ArrayList<MOVE> possibleMoves() {
        int row = this.row;
        int column = this.column;
        ArrayList<MOVE> moves = new ArrayList<>();
        if (matrix[row + 1][column] == emptyTile || matrix[row + 1][column] == goal) {
            moves.add(MOVE.DOWN);
        }
        if (matrix[row - 1][column] == emptyTile || matrix[row - 1][column] == goal) {
            moves.add(MOVE.UP);
        }
        if (matrix[row][column + 1] == emptyTile || matrix[row][column + 1] == goal) {
            moves.add(MOVE.RIGHT);
        }
        if (matrix[row][column - 1] == emptyTile || matrix[row][column - 1] == goal) {
            moves.add(MOVE.LEFT);
        }
        return moves;
    }

}
