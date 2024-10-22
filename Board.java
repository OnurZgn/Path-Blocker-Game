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

    int[] findInitialLocation(char[][] matrix) {  // Finds the player's starting position in the level

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
        switch (move) {     // Displacement is calculated according to the type of move
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
 
        while (matrix[row + dy][column + dx] != wall      // Unless the wall or goal is reached in the selected move, 
                && matrix[row + dy][column + dx] != goal) {   // progress is constant and the coordinates passed are marked as walls.
            matrix[row][column] = wall;
            row += dy;
            column += dx;
            matrix[row][column] = player;
        }
        if (matrix[row + dy][column + dx] == goal) {    // If the goal is one unit further in the selected movement, the goal is reached.
            matrix[row][column] = wall;
            row += dy;
            column += dx;
            System.out.println("goal");
        }
    }

    ArrayList<MOVE> possibleMoves() {  // The moves that can be done are added in the ArrayList (if one unit distance is empty or goal)
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
