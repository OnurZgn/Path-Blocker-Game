import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Visuals {
    Color getColor(char tile) {
        final char emptyTile = '0';
        final char wall = '1';
        final char player = '2';
        final char goal = '3';
        Color playerColor = new Color(248, 204, 68), goalColor = Color.red, wallColor = new Color(56, 12, 100),
                emptyTileColor = new Color(136, 140, 236);
        switch (tile) {
            case emptyTile:
                return emptyTileColor;
            case wall:
                return wallColor;
            case player:
                return playerColor;
            case goal:
                return goalColor;
            default:
                return null;
        }
    }

    void visualizePath(ArrayList<State> path, int level) {
        int rectSize = 50;

        String folderName = String.format("Images/level%02d", level);
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int nodei = path.size() - 1; nodei >= 0; nodei--) {
            BufferedImage image = new BufferedImage(rectSize * path.get(nodei).board.matrix.length,
                    rectSize * path.get(nodei).board.matrix[0].length,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            for (int i = path.get(nodei).board.matrix.length - 1; i >= 0; i--) {
                for (int j = path.get(nodei).board.matrix[i].length - 1; j >= 0; j--) {
                    g.setColor(getColor(path.get(nodei).board.matrix[i][j]));
                    g.fillRect(j * rectSize, i * rectSize, rectSize, rectSize);
                }
            }
            g.dispose();

            try {
                String fileName = String.format("%s/%04d.png", folderName, path.size() - nodei - 1);
                File levelFile = new File(fileName);
                ImageIO.write(image, "png", levelFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
