import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* 
 * visualizePath()-> saves path as png files
 * 
 * 
*/
public class PathBlockerGame {
    final static int levelNumber = 10;
    final static char emptyTile='0';
    final static char wall = '1';
    final static char player = '2';
    final static char goal = '3';

    public static void main(String[] args) {

        for (int levelNum = 1; levelNum <= 1; levelNum++) {
            String fileName = "level" + Integer.toString(levelNum) + ".txt";
            ArrayList<char[]> level = null;
            try {
                level = readLevel(fileName);
            } catch (IOException e) {
                System.err.print(e.getMessage());
            }
            ArrayList<char[][]> path=new ArrayList<>();
            path.add(level.toArray(new char[0][]));
            visualizePath(path);

        }
    }

    static ArrayList<char[]> readLevel(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        ArrayList<char[]> level = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            //char[] arr=line.split(",").toString().toCharArray();
            level.add(line.replace(",", "").toCharArray());// or level.add(line.split(","));
        }
        br.close();
        return level;
    }

    static ArrayList<char[][]> solver(ArrayList<char[]> state) {
        return null;
    }

    static Color getColor(char tile) {
        Color playerColor = new Color(248, 204, 68), goalColor = Color.red, wallColor = new Color(56, 12, 100),
                emptyTileColor = new Color(136, 140, 236);
        if(tile==emptyTile){
            return emptyTileColor;
        }
        else if(tile==wall){
            return wallColor;
        }   
        else if(tile==player){
            return playerColor;
        }
        else if(tile==goal){
            return goalColor;
        }
        return null;
    }

    static void visualizePath(ArrayList<char[][]> path) {

        //
        int rectSize=50;
        char[][] initialState = path.get(0);
        //

        BufferedImage image = new BufferedImage(rectSize*initialState[0].length, rectSize*initialState.length, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = image.createGraphics();
        
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState[i].length; j++) {
                g.setColor(getColor(initialState[i][j]));
                g.fillRect(j*rectSize, i*rectSize, rectSize, rectSize);
            }
        }
        g.dispose();

        try {
            File rectPng = new File("rect.png");
            ImageIO.write(image, "png", rectPng);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}