import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<char[][]> levels = (new Level()).loadLevels();
        Visuals visual = new Visuals();
        System.out.println(levels.size());
        int i = 1;
        for (var level : levels) {        // Creates a game board for each level
            Board board = new Board(level);
            State state = new State(board, null, null);  // Initial state in the level
            BFS solver = new BFS();
            ArrayList<State> path = solver.bfs(state); 
            if (path != null) {           //  Visualizes if a solution is exist
                System.out.println(path.size());
                visual.visualizePath(path, i);
            }
            i++;
        }
    }

}
