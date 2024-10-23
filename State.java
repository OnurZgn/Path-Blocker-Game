import java.util.ArrayList;

public class State {

    public Board board;
    private MOVE lastMove;
    State parent = null;

    public State(Board board, MOVE lastMove, State parent) {
        this.board = board;
        this.lastMove = lastMove;
        this.parent = parent;
    }

    public ArrayList<State> getChildren() {
        // Returns a list of children states based on possible moves.
        ArrayList<State> states = new ArrayList<>();
        for (MOVE element : board.possibleMoves()) {
            char[][] clonedMatrix = new char[board.matrix.length][];
            for (int i = 0; i < board.matrix.length; i++) {
                clonedMatrix[i] = board.matrix[i].clone();
                // The matrix has to be cloned to overcome addressing the same memory.
            }

            Board newBoard = new Board(clonedMatrix, board.column, board.row);
            newBoard.adjustMoveBoard(element);
            State newChildState = new State(newBoard, element, this);
            states.add(newChildState);
        }
        return states;
    }

    public boolean checkState() {
        // Returns true if the agent's position is the same as goal.
        if (board.matrix[board.row][board.column] == Board.goal) {
            board.matrix[board.row][board.column] = Board.player;
            return true;
        }
        return false;
    }

}
