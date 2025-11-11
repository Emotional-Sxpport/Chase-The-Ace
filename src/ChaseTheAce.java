import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ChaseTheAce extends JFrame implements KeyListener, PlayerChoiceRequester {

    private GameSystem system;
    private int initWidth = 1120, initHeight = 630;

    private JRadioButton tradeButton = null;
    private JRadioButton stayButton = null;
    private JButton submitButton = null;
    private ButtonGroup choiceGroup = null;

    private JPanel controlsPanel = null;
    private volatile String selectedChoice = null;
    private volatile CountDownLatch choiceLatch = null;

    private BufferedImage image;


    /* CREATES THE PANEL */
    public ChaseTheAce() {
        setTitle("Chase the Ace");
        setSize(initWidth, initHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        system = new GameSystem();

        DrawingPanel panel = new DrawingPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        tradeButton = new JRadioButton("Trade Card");
        tradeButton.setName("tradeButton");
        stayButton = new JRadioButton("Stay with Card");
        stayButton.setName("stayButton");
        submitButton = new JButton("Submit Choice");
        submitButton.setName("submitButton");

        choiceGroup = new ButtonGroup();
        choiceGroup.add(tradeButton);
        choiceGroup.add(stayButton);

        controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(tradeButton);
        controlsPanel.add(stayButton);
        controlsPanel.add(submitButton);
        controlsPanel.setVisible(false);

        // submit action: record choice and release latch
        submitButton.addActionListener(e -> {
            if (choiceLatch == null) return; // nothing waiting
            if (tradeButton.isSelected()) {
                selectedChoice = "Trade";
            } else if (stayButton.isSelected()) {
                selectedChoice = "Stay";
            } else {
                // default if nothing chosen
                selectedChoice = "Stay";
            }
            // hide controls and release waiting thread
            SwingUtilities.invokeLater(() -> controlsPanel.setVisible(false));
            choiceLatch.countDown();

        });

        panel.setPreferredSize(new Dimension(initWidth, initHeight));
        panel.setLayout(new BorderLayout()); // allow adding controls
        panel.add(controlsPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);

        try {
            image = ImageIO.read(new File("src/resources/images/table.jpeg")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* DRAWING COMPONENT */
    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);

            double scale;
            int offsetX, offsetY;
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            if (panelWidth / 16 <= panelHeight / 9) {
                scale = (double) panelWidth / (double) initWidth;
                offsetX = 0;
                offsetY = (panelHeight - (int)(initHeight * scale)) / 2;
            }
            else {
                scale = (double) panelHeight / (double) initHeight;
                offsetX = (panelWidth - (int)(initWidth * scale)) / 2;
                offsetY = 0;
            }

            g.setColor(Color.WHITE);
            g.fillRect(offsetX, offsetY, (int) (initWidth * scale), (int) (initHeight * scale));
            g.drawImage(image, offsetX, offsetY, (int) (initWidth * scale), (int) (initHeight * scale), this);
            PlayingCard card = new PlayingCard();
            system.draw(g, scale, offsetX, offsetY, this);

            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
        }
    }


    /**
     * Shows the choice controls on the DrawingPanel and blocks until the player submits.
     * Must be called from a non-EDT thread (this method will block).
     */
    public String requestPlayerChoiceBlocking() {
        choiceLatch = new CountDownLatch(1);
        selectedChoice = null;
        // clear previous selection and show controls on EDT
        SwingUtilities.invokeLater(() -> {
            choiceGroup.clearSelection();
            controlsPanel.setVisible(true);
            controlsPanel.revalidate();
            controlsPanel.repaint();
        });
        try {
            choiceLatch.await(); // block until submit clicked
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            selectedChoice = null;
        } finally {
            choiceLatch = null;
        }
        return selectedChoice;
    }


    /* DETECTS KEY INPUTS */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (system.getWaiting() == 0 && keyCode == KeyEvent.VK_ENTER) {
        //if (keyCode == KeyEvent.VK_ENTER) {
            System.out.println("Enter pressed!");
            system.setWaiting(1);
            repaint();
        } else if (system.getWaiting() == 0 && keyCode == KeyEvent.VK_SPACE) {

        //else if (keyCode == KeyEvent.VK_SPACE) {
            System.out.println("Space pressed!");
            //system.shuffle();
            system.setWaiting(2);
            repaint();
        }
    }

    /* I HAVE TO INCLUDE THESE OR THE CODE BREAKS */
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}


    /* MAIN FUNCTION */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChaseTheAce();

        });
    }
}