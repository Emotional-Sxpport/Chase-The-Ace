import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class ChaseTheAce extends JFrame implements KeyListener {

    private GameSystem system;
    private int test = 0;
    private int screenWidth = 1120, screenHeight = 630;
    private int initWidth = 1120, initHeight = 630;

    /* CREATES THE PANEL */
    public ChaseTheAce() {
        setTitle("Chase the Ace");
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        system = new GameSystem();

        DrawingPanel panel = new DrawingPanel();

        add(panel);
        setVisible(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);
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
            double scaleX = (double) panelWidth / initWidth;
            double scaleY = (double) panelHeight / initHeight;

            if (panelWidth / 16 <= panelHeight / 9) {
                scale = (double) panelWidth / (double) initWidth;
                offsetX = 0;
                offsetY = (panelHeight - (int)(initHeight * scale)) / 2;
                System.out.println("offsetY: " + offsetY);
            }
            else {
                scale = (double) panelHeight / (double) initHeight;
                offsetX = (panelWidth - (int)(initWidth * scale)) / 2;
                offsetY = 0;
                System.out.println("offsetX: " + offsetX);
            }

            g.setColor(Color.WHITE);
            g.fillRect(offsetX, offsetY, (int) (initWidth * scale), (int) (initHeight * scale));
            PlayingCard card = new PlayingCard();
            system.draw(g, scale, offsetX, offsetY);

            //if (test == 1)
            card.draw(200, 200, g, scale, offsetX, offsetY);

        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            System.out.println("Enter pressed!");
            test = 1;
            repaint();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            System.out.println("Space pressed!");
            system.shuffle();
            repaint();
        }
    }

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