import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Visuals {
    private Color getColor(char tile) {
        switch (tile) {
            case Board.emptyTile:
                return new Color(136, 140, 236);
            case Board.wall:
                return new Color(56, 12, 100);
            case Board.player:
                return new Color(248, 204, 68);
            case Board.goal:
                return Color.red;
            default:
                return null;
        }
    } 

    private void saveImage(BufferedImage image, String folderName, int step) {
        try {
            String fileName = String.format("%s/%04d.png", folderName, step);
            File levelFile = new File(fileName);
            ImageIO.write(image, "png", levelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void visualizePath(ArrayList<State> path, int level) {
        int rectSize = 10;

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

            char matrix[][] = path.get(nodei).board.matrix;

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    g.setColor(getColor(matrix[i][j]));
                    g.fillRect(j * rectSize, i * rectSize, rectSize, rectSize);
                }
            }
            g.dispose();

            saveImage(image, folderName, path.size() - nodei - 1);
        }
    }
}
