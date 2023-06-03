package Swing;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class DicePanel extends JPanel {
    private BufferedImage image;
    private Rectangle2D.Double rect;
    private Color playerColor;

    public DicePanel() {
    }

    public void lancarDado(int dice) {
        
        String imagePath = "Dado" + dice + ".png";
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        repaint(); //renderizar 
    }
    
    public void createPlayerRect(Color player) {
    	try {
    	rect = new Rectangle2D.Double(0, 0, image.getWidth(), image.getHeight());
    	}
    	catch (Exception ex) {
			System.out.println(ex.getMessage());
            System.exit(1);

    	}
    	playerColor = player;
        repaint(); //renderizar 

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Draw the image at the desired location
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(playerColor);
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(rect);

        }
    }
}
