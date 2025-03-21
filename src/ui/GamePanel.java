package ui;

import entities.Ship;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel{
    private Ship ship;

    public GamePanel() {
        setFocusable(true); // Allow key events

        // Keyboard controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (ship != null) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                        //ship.setDirection(-1);
                        ship.moveLeft();
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                        //ship.setDirection(1);
                        ship.moveRight();
                    }

                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ||
                            e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                        //ship.setDirection(0);
                        repaint();
                    }
            }
        });

    }

    public void setShip(String selectedShip) {
        ship = new Ship(400, 530, 850, selectedShip); // Initial position and game width
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Draw background
        ship.draw(g); // Draw the ship
    }
}
