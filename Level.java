import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
    ArrayList<char[][]> loadLevels() {
        final int numOfLevels = 10;
        ArrayList<char[][]> levels = new ArrayList<>();
        for (int i = 1; i <= numOfLevels; i++) {
            String fileName = String.format("Levels/level%02d.txt", i);
            levels.add(readLevel(fileName));
        }

        return levels;
    }

    private char[][] readLevel(String fileName) {
        ArrayList<char[]> level = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                level.add(line.replace(",", "").toCharArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return level.toArray(new char[level.size()][]);
    }
}
