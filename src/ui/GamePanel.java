package ui;

import entities.Ship;
import javax.swing.*;
import java.awt.*;

/**
 * Manages the game's display,
 * including the background and the ship,
 * and provides methods to move the ship based on input.
 */

public class GamePanel extends JPanel{
    private Ship ship;

    public GamePanel() {
        ship = new Ship(350, 500, 800);
        setFocusable(true); // Make panel focusable
        requestFocusInWindow(); // Request focus to capture key events
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Draw background
        ship.draw(g); // Draw the ship
    }

    public void moveShipLeft() {
        ship.moveLeft();
        repaint();
    }

    public void moveShipRight() {
        ship.moveRight();
        repaint();
    }
}
