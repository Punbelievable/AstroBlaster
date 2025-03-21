package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class StartPanel extends JPanel implements ActionListener {
    private GamePanel gamePanel;
    private String[] shipNames = {"SpaceShip1.png", "SpaceShip2.png", "SpaceShip3.png"};
    private String[] shipDescriptions = {
            "Scout Ship: Fast and agile with light weapons",
            "Cruiser: Balanced speed and firepower",
            "Destroyer: Heavy firepower but slower movement"
    };
    private String[] shipStats = {
            "SPEED: ●●●●○  |  ARMOR: ●●○○○  |  FIREPOWER: ●●○○○",
            "SPEED: ●●●○○  |  ARMOR: ●●●○○  |  FIREPOWER: ●●●○○",
            "SPEED: ●●○○○  |  ARMOR: ●●●●○  |  FIREPOWER: ●●●●●"
    };
    private int selectedIndex = 0;
    private JLabel shipLabel;
    private JLabel shipNameLabel;
    private JLabel shipDescriptionLabel;
    private JLabel shipStatsLabel;
    private CardLayout cardLayout;
    private JFrame frame;
    private JButton leftArrow, rightArrow;
    private Image background;
    private ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private Timer animationTimer;
    private Color spaceBlue = new Color(10, 15, 30);
    private Color accentColor = new Color(65, 195, 225);
    private Font titleFont;
    private Font shipNameFont;
    private Font buttonFont;
    private Font descriptionFont;
    private Font statsFont;
    private JButton playButton;
    private Random random = new Random();
    private JLabel creditLabel;

    public StartPanel(CardLayout cardLayout, JFrame frame, GamePanel gamePanel) {
        this.cardLayout = cardLayout;
        this.frame = frame;
        this.gamePanel = gamePanel;

        // Load custom fonts
        try {
            titleFont = new Font("Arial", Font.BOLD, 48);
            shipNameFont = new Font("Arial", Font.BOLD, 24);
            buttonFont = new Font("Arial", Font.BOLD, 20);
            descriptionFont = new Font("Arial", Font.PLAIN, 16);
            statsFont = new Font("Consolas", Font.BOLD, 14);
        } catch (Exception e) {
            e.printStackTrace();
            titleFont = new Font("Arial", Font.BOLD, 48);
            shipNameFont = new Font("Arial", Font.BOLD, 24);
            buttonFont = new Font("Arial", Font.BOLD, 20);
            descriptionFont = new Font("Arial", Font.PLAIN, 16);
            statsFont = new Font("Consolas", Font.BOLD, 14);
        }

        // Load background
        background = createBackground(800, 600);


        // Generate stars for the background
        generateStars(150);

        // Generate asteroids
        generateAsteroids(8);

        // Set up the panel
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("AstroBlaster", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);
        titlePanel.add(title, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Ship Selection Panel
        JPanel selectionPanel = new JPanel(new BorderLayout(20, 0));
        selectionPanel.setOpaque(false);

        // Left Arrow Button
        leftArrow = createArrowButton("<");
        selectionPanel.add(leftArrow, BorderLayout.WEST);

        // Center panel for ship and description
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);

        // Ship display area
        JPanel shipDisplayPanel = new JPanel(new BorderLayout(0, 5));
        shipDisplayPanel.setOpaque(false);

        // Ship name at the top
        shipNameLabel = new JLabel("SCOUT SHIP", SwingConstants.CENTER);
        shipNameLabel.setFont(shipNameFont);
        shipNameLabel.setForeground(accentColor);
        shipDisplayPanel.add(shipNameLabel, BorderLayout.NORTH);

        // Ship Label
        shipLabel = new JLabel();
        shipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateShipImage();
        shipDisplayPanel.add(shipLabel, BorderLayout.CENTER);

        // Ship stats below the image
        shipStatsLabel = new JLabel(shipStats[selectedIndex], SwingConstants.CENTER);
        shipStatsLabel.setFont(statsFont);
        shipStatsLabel.setForeground(Color.WHITE);
        shipDisplayPanel.add(shipStatsLabel, BorderLayout.SOUTH);

        centerPanel.add(shipDisplayPanel, BorderLayout.CENTER);

        // Ship Description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(new Color(50, 75, 125));
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        shipDescriptionLabel = new JLabel("<html>" + shipDescriptions[selectedIndex] + "</html>", SwingConstants.CENTER);
        shipDescriptionLabel.setFont(descriptionFont);
        shipDescriptionLabel.setForeground(Color.WHITE);
        descriptionPanel.add(shipDescriptionLabel, BorderLayout.CENTER);

        centerPanel.add(descriptionPanel, BorderLayout.SOUTH);

        selectionPanel.add(centerPanel, BorderLayout.CENTER);

        // Right Arrow Button
        rightArrow = createArrowButton(">");
        selectionPanel.add(rightArrow, BorderLayout.EAST);

        add(selectionPanel, BorderLayout.CENTER);

        // Bottom panel for play button and credit
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Play Button
        playButton = createPlayButton("LAUNCH MISSION");
        bottomPanel.add(playButton);

        // Credit label
        creditLabel = new JLabel("Made by YIJIE");
        creditLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        creditLabel.setForeground(new Color(150, 150, 150));
        creditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(creditLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Set up keyboard navigation
        setupKeyboardNavigation();

        // Start animation timer
        animationTimer = new Timer(16, this);
        animationTimer.start();
    }

    private JButton createArrowButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 40, 60));
        button.setPreferredSize(new Dimension(60, 60));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action listener
        if (text.equals("<")) {
            button.addActionListener(e -> selectShip(-1));
        } else {
            button.addActionListener(e -> selectShip(1));
        }

        return button;
    }

    private JButton createPlayButton(String text) {
        JButton button = new JButton(text);

        button.setFont(buttonFont);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(200, 30, 60));
        button.setPreferredSize(new Dimension(80, 50));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Handle button click
        button.addActionListener(e -> {
            String selectedShip = shipNames[selectedIndex];
            gamePanel.setShip(selectedShip);
            cardLayout.show(frame.getContentPane(), "game");
            gamePanel.requestFocusInWindow();
            animationTimer.stop();
        });

        return button;
    }

    private void setupKeyboardNavigation() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        selectShip(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        selectShip(1);
                        break;

                    // "Enter" and "Space" key starts the game
                    case KeyEvent.VK_ENTER:
                    case KeyEvent.VK_SPACE:
                        playButton.doClick();
                        break;
                }
            }
        });
    }

    private void selectShip(int direction) {
        selectedIndex = (selectedIndex + direction + shipNames.length) % shipNames.length;
        updateShipImage();
        updateShipInfo();
    }

    private void updateShipInfo() {
        // Update ship name based on selection
        String[] shipDisplayNames = {"SCOUT SHIP", "CRUISER", "DESTROYER"};
        shipNameLabel.setText(shipDisplayNames[selectedIndex]);

        // Update description and stats
        shipDescriptionLabel.setText("<html>" + shipDescriptions[selectedIndex] + "</html>");
        shipStatsLabel.setText(shipStats[selectedIndex]);
    }

    private void updateShipImage() {
        try {
            // Load and scale the ship image
            BufferedImage image = ImageIO.read(getClass().getResource("/resources/" + shipNames[selectedIndex]));
            Image scaledImage = image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            shipLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            shipLabel.setText("Image not found");
            shipLabel.setForeground(Color.WHITE);
        }
    }

    private void generateStars(int count) {
        stars.clear();

        for (int i = 0; i < count; i++) {
            stars.add(new Star(
                    random.nextInt(getWidth() > 0 ? getWidth() : 800),
                    random.nextInt(getHeight() > 0 ? getHeight() : 600),
                    random.nextFloat() * 3 + 1,
                    new Color(200 + random.nextInt(55), 200 + random.nextInt(55), 200 + random.nextInt(55))
            ));
        }
    }

    private void generateAsteroids(int count) {
        asteroids.clear();

        for (int i = 0; i < count; i++) {
            asteroids.add(new Asteroid(
                    random.nextInt(getWidth() > 0 ? getWidth() : 800),
                    random.nextInt(getHeight() > 0 ? getHeight() : 600),
                    random.nextInt(30) + 20,
                    random.nextFloat() * 0.5f + 0.2f,
                    random.nextFloat() * 0.01f - 0.005f
            ));
        }
    }

    private Image createBackground(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // dark blue background color
        g2d.setColor(new Color(15, 20, 40));
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
        return image;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Update star animations
        for (Star star : stars) {
            star.twinkle();
        }

        // Update asteroid animations
        for (Asteroid asteroid : asteroids) {
            asteroid.update();

            // Wrap around screen
            if (asteroid.x < -50) asteroid.x = getWidth() + 50;
            if (asteroid.x > getWidth() + 50) asteroid.x = -50;
            if (asteroid.y < -50) asteroid.y = getHeight() + 50;
            if (asteroid.y > getHeight() + 50) asteroid.y = -50;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        if (background != null) {
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(spaceBlue);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw stars
        for (Star star : stars) {
            star.draw(g2d);
        }

        // Draw asteroids in the background
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g2d);
        }

    }

    // Inner class for animated stars
    private class Star {
        private int x, y;
        private float size;
        private Color color;
        private float alpha = 1.0f;
        private boolean fadingOut = false;
        private float fadeSpeed;

        public Star(int x, int y, float size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.fadeSpeed = random.nextFloat() * 0.03f + 0.01f;
        }

        public void twinkle() {
            if (fadingOut) {
                alpha -= fadeSpeed;
                if (alpha <= 0.3f) {
                    fadingOut = false;
                    alpha = 0.3f;
                }
            } else {
                alpha += fadeSpeed;
                if (alpha >= 1.0f) {
                    fadingOut = true;
                    alpha = 1.0f;
                }
            }
        }

        public void draw(Graphics2D g2d) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(color);

            // Draw the star as a small circle
            g2d.fillOval(x, y, Math.round(size), Math.round(size));

            // Add a subtle glow for larger stars
            if (size > 2) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.3f));
                g2d.fillOval(x - 1, y - 1, Math.round(size + 2), Math.round(size + 2));
            }

            // Reset composite
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    // Asteroids
    private class Asteroid {
        private float x, y;
        private int size;
        private float speed;
        private float rotation = 0;
        private float rotationSpeed;
        private Polygon shape;
        private Color asteroidColor;
        private Random random = new Random();


        public Asteroid(int x, int y, int size, float speed, float rotationSpeed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.rotationSpeed = rotationSpeed;

            // Asteroid shape
            shape = new Polygon();
            int points = random.nextInt(5) + 7; // 7-12 points

            for (int i = 0; i < points; i++) {
                double angle = 2 * Math.PI * i / points;
                int radius = size/2 + random.nextInt(size/4) - size/8;
                int px = (int)(Math.cos(angle) * radius);
                int py = (int)(Math.sin(angle) * radius);
                shape.addPoint(px, py);
            }


            int grayValue = random.nextInt(100) + 100;
            asteroidColor = new Color(grayValue, grayValue, grayValue);
        }



        public void update() {
            // Move asteroid
            x -= speed;
            y += speed * 0.5f;

            // Rotate asteroid
            rotation += rotationSpeed;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(asteroidColor);

            // Save the current transform
            AffineTransform oldTransform = g2d.getTransform();

            // Apply translation and rotation
            g2d.translate(x, y);
            g2d.rotate(rotation);

            // Draw the asteroid
            g2d.fill(shape);

            // Draw outline
            g2d.setColor(new Color(100, 100, 110, 150));
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.draw(shape);

            // Restore the original transform
            g2d.setTransform(oldTransform);
        }
    }
}