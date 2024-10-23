import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        /*
         * 1) Why you prefer the search algorithm you choose?
         * -- We prefer the DFS algorithm for reasons such as its ability to make in
         * depth exploration, more memory efficiency, guarantee of finding a full
         * solution.
         * -- Except for the first state maximum moves can the agent make is two
         * resulting in more deep and less branching tree.
         * 
         * 2) Can you achieve the optimal result? Why? Why not?
         * -- DFS doesn't always guarantee that we find the shortest path.
         * 
         * 3) How you achieved efficiency for keeping the states?
         * -- Using DFS is memory efficient. States could be kept as list of moves but
         * that would increase the computational complexity.
         * 
         * 4) If you prefer to use DFS (tree version) then do you need to avoid cycles?
         * -- We don't need to. Whether there is undo or not, undo will visit back to
         * the previously visited state so it is redundant to go back and since every
         * move results in a unique state this is a naturally occuring tree.
         * 
         * 5) What will be the path-cost for this problem?
         * -- Path-cost doesn't mean much in the context of this game so we can say it
         * is the cost of each move is same.
         */

        ArrayList<char[][]> levels = (new Level()).loadLevels();
        ArrayList<ArrayList<State>> paths = new ArrayList<>();
        DFS solver = new DFS();
        Visuals visual = new Visuals();

        for (var i = 0; i < levels.size(); i++) {
            Board board = new Board(levels.get(i));// Creates a game board for each level
            State state = new State(board, null, null); // Initial state in the level
            long start = System.nanoTime();
            paths.add(solver.dfs(state));
            long duration = System.nanoTime() - start;

            String level = String.format("Level %02d took %f milliseconds", i + 1, duration / 1e+6);
            System.out.println(level);
        }
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i) != null)
                visual.visualizePath(paths.get(i), i + 1);
        }
    }
}

/*
 * Lv BFS DFS Winner
 * 01 1.721999 1.562001 DFS
 * 02 0.595101 0.270600 DFS
 * 03 0.744800 1.263200 BFS
 * 04 0.539900 0.384699 DFS
 * 05 0.469200 0.457800 ---
 * 06 0.350800 0.253399 DFS
 * 07 0.335800 0.243900 DFS
 * 08 0.793800 1.602101 BFS
 * 09 1.337800 0.525600 DFS
 * 10 0.659599 0.707400 BFS
 */
