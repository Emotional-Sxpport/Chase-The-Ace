import javax.swing.*;
import java.awt.*;

public class ChaseTheAce extends JFrame {

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

            //PlayingCard card = new PlayingCard();
            Player player = new Player();
            player.getCard().draw(100, 100, g);
            player.draw(200, 200, g);

        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new ChaseTheAce();
        });
    }
}