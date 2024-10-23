import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class BFS {
    ArrayList<State> bfs(State initState) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initState);
        while (!queue.isEmpty()) {
            State stat = queue.poll();
            if (stat.checkState()) {
                State node = stat;
                ArrayList<State> path = new ArrayList<>();

                while (node != null) {
                    path.add(node);
                    node = node.parent;
                }

                return path;
            }

            for (var child : stat.getChildren()) {
                queue.add(child);
            }
        }

        return null;
    }
}
