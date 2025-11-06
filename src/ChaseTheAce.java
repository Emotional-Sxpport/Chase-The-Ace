import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChaseTheAce extends JFrame {

    private int test = 0;
    public ChaseTheAce() {
        setTitle("Chase the Ace");
        setSize(2100, 1400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DrawingPanel panel = new DrawingPanel();
        add(panel);

        setVisible(true);
    }

    // Custom JPanel class for drawing
    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            PlayingCard card = new PlayingCard();
            //Player player = new Player();
            System system = new System();
            for (int i = 0; i < 4; i++) {
                system.getPlayer(i).draw(100 + 150 * i, 100, g);
            }
            //player.draw(200, 200, g);
            if (test == 1)
                card.draw(300, 300, g);

        }
    }

    //@Override
    public void actionPerformed(KeyEvent e) {
        // This method is called when an action occurs (e.g., button click)
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            //System.out.println("Enter key pressed!");
            test = 1;

        } else if (keyCode == KeyEvent.VK_SPACE) {
            //System.out.println("Space bar pressed!");
        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new ChaseTheAce();
        });
    }
}