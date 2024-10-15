import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g=image.createGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 50, 50);

        g.dispose();

        try{
            File rectPng=new File("rect.png");
            ImageIO.write(image, "png", rectPng);
            
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }      
}
