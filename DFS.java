
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File; // Add this import

public class DFS {

    // Tree DFS
    ArrayList<State> dfs(State initState, int level) {
        Stack<State> stack = new Stack<>();
        stack.push(initState);

        // Open a PrintWriter to append to the DOT file
        try (PrintWriter writer = new PrintWriter(new FileWriter("_tree" + level + ".dot", true))) { // 'true' for
                                                                                                     // append mode
            // Traverse the tree and add edges to the DOT file
            while (!stack.isEmpty()) {
                State stat = stack.pop();

                if (stat.checkState()) {
                    // Write the node with red background for solutions
                    writer.println("\"Solution" + stat.hashCode() + "\" [fillcolor=\"red\"];");
                    if (stat.parent != null) {
                        // Write the edge from parent to the current solution node
                        writer.println("\"" + stat.parent.hashCode() + "\" -> \"Solution" + stat.hashCode() + "\";");
                    }
                    break; // Found a solution
                } else {
                    // Write the node with green background for ordinary nodes
                    writer.println("\"" + stat.hashCode() + "\" [fillcolor=\"green\"];");
                    if (stat.parent != null) {
                        // Write the edge from parent to the current node
                        writer.println("\"" + stat.parent.hashCode() + "\" -> \"" + stat.hashCode() + "\";");
                    }
                    // Add children to the stack (push them onto the stack)
                    for (var child : stat.getChildren()) {
                        stack.push(child);
                    }
                }
            }

            writer.println("}");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // NO SOLUTION FOUND

    }
}
