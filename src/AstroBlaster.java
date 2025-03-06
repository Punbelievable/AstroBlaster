import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AstroBlaster extends JFrame {
    // Ship’s starting X position
    private int shipX = 350;

    public AstroBlaster() {
        setTitle("AstroBlaster");
        setSize(800, 600); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Add game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        // Keyboard controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    shipX -= 10; // Move left
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    shipX += 10; // Move right
                }
                repaint(); // Redraw the screen
            }
        });
        setFocusable(true); // Listen for key presses
        setVisible(true); // Show the window
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK); // Background
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE); // Ship color
            g.fillRect(shipX, 500, 50, 20); // Draw ship (rectangle for now)
        }
    }

    public static void main(String[] args) {
        new AstroBlaster();
    }
}