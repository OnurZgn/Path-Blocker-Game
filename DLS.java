import java.util.ArrayList;

class DLS {
    ArrayList<State> dls(State initState, int limit) {
        return recursiveDLS(initState, limit);
    }

    private ArrayList<State> recursiveDLS(State state, int limit) {
        if (state.checkState()) {
            System.out.println("DLS caught the goal!");
            ArrayList<State> path = new ArrayList<>();
            State node = state;
            while (node != null) {
                path.add(node);
                node = node.parent;
            }
            return path;
        }

        if (limit <= 0) {
            return null;
        }

        for (State child : state.getChildren()) {
            ArrayList<State> result = recursiveDLS(child, limit - 1);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
