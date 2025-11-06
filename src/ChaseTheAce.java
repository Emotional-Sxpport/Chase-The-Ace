import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChaseTheAce extends JFrame {

    private System system;
    private int test = 0;

    /* CREATES THE PANEL */
    public ChaseTheAce() {
        setTitle("Chase the Ace");
        setSize(2100, 1400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        system = new System();

        DrawingPanel panel = new DrawingPanel();
        add(panel);
        setVisible(true);
    }

    /* DRAWING COMPONENT */
    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            PlayingCard card = new PlayingCard();
            system.draw(g);

            // ignore this for now
            if (test == 1)
                card.draw(300, 300, g);

        }
    }

    //@Override
    /* ACTION LISTENERS / BUTTONS - NOT WORKING YET */
    public void actionPerformed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            //System.out.println("Enter key pressed!");
            test = 1;

        } else if (keyCode == KeyEvent.VK_SPACE) {
            //System.out.println("Space bar pressed!");
        }
    }

    /* MAIN FUNCTION */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChaseTheAce();
        });
    }
}