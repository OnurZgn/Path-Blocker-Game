import java.util.ArrayList;
import java.util.Stack;

class DFS {
    ArrayList<State> dfs(State initState) {
        Stack<State> stack = new Stack<>();
        stack.add(initState);
        while (!stack.isEmpty()) {
            State state = stack.pop();
            if (state.checkState()) {
                ArrayList<State> path = new ArrayList<>();
                // Once a solution is found we follow till the first state to obtain a solution
                // path.
                path.add(state);
                while ((state = state.parent) != null) {
                    path.add(state);
                }
                return path;
            }

            for (State child : state.getChildren()) {
                stack.push(child);
            }
        }
        return null;
    }
}
