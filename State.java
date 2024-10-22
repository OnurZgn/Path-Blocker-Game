import java.util.ArrayList;

public class State {

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
        System.out.println(board.possibleMoves().size());
        for (MOVE element : board.possibleMoves()) {
            char[][] clonedMatrix = new char[board.matrix.length][];
            for (int i = 0; i < board.matrix.length; i++) {
                clonedMatrix[i] = board.matrix[i].clone();
            }
            // Should clone the current board and use adjustMoves function on it;
            Board newBoard = new Board(clonedMatrix, board.column, board.row);
            newBoard.adjustMoveBoard(element);
            State newChildState = new State(newBoard, element, this);
            states.add(newChildState);
        }
        return states;
    }

    public boolean checkState() {
        System.out.println("Checking state");
        if (board.matrix[board.row][board.column] == board.goal) {         //  returns true if the player is at goal;
            board.matrix[board.row][board.column] = board.player;
            System.out.println("I caught the goal");
            return true;
        } else {
            return false;
        }
    }

}
