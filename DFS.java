import java.util.ArrayList;
import java.util.Stack;

class DFS {
    ArrayList<State> dfs(State iniState) {
        Stack<State> stack = new Stack<>();
        stack.add(iniState);
        while (!stack.isEmpty()) {
            State state = stack.pop();
            if (state.checkState()) {
                System.out.println("DFS caught the goal!");
                ArrayList<State> path = new ArrayList<>();
                State node = state;

                while (node != null) {
                    path.add(node);
                    node = node.parent;
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
