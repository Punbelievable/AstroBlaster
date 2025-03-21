import ui.GamePanel;
import ui.StartPanel;

import javax.swing.*;
import java.awt.*;

public class AstroBlaster extends JFrame {
    private GamePanel gamePanel;
    private StartPanel startPanel;
    private CardLayout cardLayout;

    public AstroBlaster() {
        setTitle("AstroBlaster");
        setSize(900, 700); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize and set the CardLayout
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        // Add game panel first so it’s available for ui.StartPanel
        gamePanel = new GamePanel();
        add(gamePanel, "game");

        // Add start panel
        startPanel = new StartPanel(cardLayout, this, gamePanel);
        add(startPanel, "start");

        // Show start panel initially
        cardLayout.show(getContentPane(), "start");

        // Listen for key presses
        setVisible(true); // Show the window
    }

    public static void main(String[] args) {
        new AstroBlaster();
    }
}