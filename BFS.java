import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    // Tree BFS
    ArrayList<State> bfs(State initState, int level) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initState);

        // Open a PrintWriter to append to the DOT file
        try (PrintWriter writer = new PrintWriter(new FileWriter("_tree" + level + ".dot", true))) { // 'true' for
                                                                                                     // append mode
            // Write the DOT header if this is the first write (optional)
            if (new File("_tree" + level + ".dot").length() == 0) {
                writer.println("digraph G {");
                writer.println("node [style=filled];"); // Enable filled style for nodes
            }

            // Traverse the tree and add edges to the DOT file
            while (!queue.isEmpty()) {
                State stat = queue.poll();

                // Generate a unique node identifier using hashcode
                String nodeId = String.valueOf(stat.hashCode());

                if (stat.checkState()) {
                    // Write the node with red background for solutions
                    writer.println("\"" + nodeId + "\" [label=\"" + stat.lastMove + "\", fillcolor=\"red\"];");
                    if (stat.parent != null) {
                        // Write the edge from parent to the current solution node
                        writer.println("\"" + stat.parent.hashCode() + "\" -> \"" + nodeId + "\";");
                    }
                    break; // Found a solution
                } else {
                    // Write the node with the last move as the label
                    writer.println("\"" + nodeId + "\" [label=\"" + stat.lastMove + "\"];");
                    if (stat.parent != null) {
                        // Write the edge from parent to the current node
                        writer.println("\"" + stat.parent.hashCode() + "\" -> \"" + nodeId + "\";");
                    }
                    // Add children to the queue
                    for (var child : stat.getChildren()) {
                        queue.add(child);
                    }
                }
            }

            // Write the closing bracket for the DOT file (if needed, depending on your use
            // case)
            // writer.println("}"); // Uncomment if you want to close the graph file here

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // NO SOLUTION FOUND
    }
}
