import java.util.ArrayList;

class IDS {
    ArrayList<State> ids(State initState, int maxDepth) {
        for (int i = 0; i <= maxDepth; i++) {
            System.out.println("Current searching depth limit: " + i);
            ArrayList<State> result = dls(initState, i);
            if (result != null) {
                System.out.println("IDS caught the goal in depth" + i);
                return result;
            }
        }
        return null;
    }

    private ArrayList<State> dls(State state, int limit) {
        if (state.checkState()) {
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
            ArrayList<State> result = dls(child, limit - 1);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
