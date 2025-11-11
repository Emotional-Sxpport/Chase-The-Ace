import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

// Note: PlayingCard is used primarily as a struct.

public class PlayingCard {
    private int suit; // 0-3
    private int rank; // 0-12
    private BufferedImage image, fourHearts;
    private String[] suits = {"H", "D", "C", "S"};
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private String name;

    /* CONSTRUCTORS */
    public PlayingCard() {
        suit = 0;
        rank = 0;
        name = "src/resources/images/cards/" + suits[suit] + ranks[rank] + ".png";
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fourHearts = ImageIO.read(new File("src/resources/images/cards/H4.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* SHUFFLES CARD */
    public void randomize() {
        suit = (int) (Math.random() * 4);
        rank = (int) (Math.random() * 13);
        name = "src/resources/images/cards/" + suits[suit] + ranks[rank] + ".png";
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* GET / SET FUNCTIONS */
    public int getSuit() { return suit; }
    public int getRank() { return rank; }
    public int getValue() { return 4*suit+rank; }


    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g, double scale, int offsetX, int offsetY, ImageObserver obs) {
        //g.setColor(Color.GREEN);
        g.drawImage(image, offsetX + (int)(x*scale), offsetY + (int)(y*scale), (int) (200 * scale), (int) (300 * scale), obs);
        //g.fillRect(offsetX + (int)(x*scale), offsetY + (int)(y*scale), (int)(100*scale), (int)(150*scale));
        //g.setColor(Color.WHITE);
        //g.setFont(new Font("Arial", Font.PLAIN, (int)(32*scale)));
        //g.drawString(ranks[getRank()], offsetX + (int) ((x + 15) * scale), offsetY + (int) ((y + 47) * scale));
        //g.drawString(suits[getSuit()], offsetX + (int) ((x + 15) * scale), offsetY + (int) ((y + 87) * scale));
    }
}
