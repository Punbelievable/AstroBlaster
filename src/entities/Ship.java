package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class Ship {
    private int x;
    private int y;
    private BufferedImage image;
    private int maxX;
    private int direction = 0;

    public Ship(int startX, int startY, int gameWidth, String shipImage) {
        x = startX;
        y = startY;
        maxX = gameWidth - 50; // Ship width is 50 pixels

        try {
            image = ImageIO.read(getClass().getResource("/resources/" + shipImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveLeft() {
        x = Math.max(0, x - 10); // Prevent going left of x=0
    }

    public void moveRight() {
        x = Math.min(maxX, x + 10); // Prevent going right of maxX
    }

    /*
    public void setDirection(int dir) {
        direction = dir;
    }
    */

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int shipWidth = 80;
        int shipHeight = 80;

        /*
        double angle = 0;
        if (direction == -1) {
            angle = -Math.PI / 8;
        } else if (direction == 1) {
            angle = Math.PI / 8;
        }


        int centerX = x + shipWidth / 2;
        int centerY = y + shipHeight / 2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(centerX, centerY);
        g2d.rotate(angle);
        g2d.translate(-centerX, -centerY);
         */


        // Draw Ship
        if (image != null) {
            g2d.drawImage(image, x, y, shipWidth, shipHeight, null); // Draw image if loaded
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x, y, 50, 20); // Draw rectangle as fallback
        }

        g2d.dispose();
    }
}
