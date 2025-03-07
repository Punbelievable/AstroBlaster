package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Encapsulates the ship's position, image, movement logic, and drawing behavior.
 */

public class Ship {
    private int x;
    private int y;
    private BufferedImage image;
    private int maxX;

    public Ship(int startX, int startY, int gameWidth) {
        x = startX;
        y = startY;
        maxX = gameWidth - 50; // Ship width is 50 pixels

        try {
            image = ImageIO.read(getClass().getResource("/resources/SpaceShip.png"));
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

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, 50, 50, null); // Draw image if loaded
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, 50, 20); // Draw rectangle as fallback
        }
    }
}
