import ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AstroBlaster extends JFrame {
    private GamePanel gamePanel;

    public AstroBlaster() {
        setTitle("AstroBlaster");
        setSize(800, 600); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Add game panel
        gamePanel = new GamePanel();
        add(gamePanel);

        // Keyboard controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                    gamePanel.moveShipLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                    gamePanel.moveShipRight();
                }
            }
        });

        setFocusable(true); // Listen for key presses
        setVisible(true); // Show the window
    }

    public static void main(String[] args) {
        new AstroBlaster();
    }
}