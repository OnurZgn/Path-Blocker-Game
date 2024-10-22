import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<char[][]> levels = (new Level()).loadLevels();
        Visuals visual = new Visuals();
        System.out.println(levels.size());
        int i = 1;
        for (var level : levels) {
            Board board = new Board(level);
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