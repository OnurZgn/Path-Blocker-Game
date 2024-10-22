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

    public ArrayList<State> getChildren() {      // Keeps all possible situations that may occur after the current one
        ArrayList<State> states = new ArrayList<>();
        System.out.println(board.possibleMoves().size());
        for (MOVE element : board.possibleMoves()) {
            char[][] clonedMatrix = new char[board.matrix.length][];
            for (int i = 0; i < board.matrix.length; i++) {
                clonedMatrix[i] = board.matrix[i].clone(); // Moves will be made on duplicate matrices without disturbing the board
            }
            
            Board newBoard = new Board(clonedMatrix, board.column, board.row); // A board is created for each clone
            newBoard.adjustMoveBoard(element);  // and the moves are adjusted.
            State newChildState = new State(newBoard, element, this);  // Creates updated State
            states.add(newChildState);
        }
        return states;
    }

    public boolean checkState() {        //  returns true if the player is at goal;
        System.out.println("Checking state");
        if (board.matrix[board.row][board.column] == board.goal) {        
            board.matrix[board.row][board.column] = board.player;
            System.out.println("I caught the goal");
            return true;
        } else {
            return false;
        }
    }

}
