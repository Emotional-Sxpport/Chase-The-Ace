import javax.smartcardio.Card;
import java.awt.*;

// Note: PlayingCard is used primarily as a struct.

public class PlayingCard {
    private int suit; // 0-3
    private int rank; // 0-12

    /* INITIALIZES VARIABLES */
    public PlayingCard() {
        suit = 0;
        rank = 0;
    }

    /* INITIALIZES VARIABLES */
    public PlayingCard(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /* SHUFFLES CARD */
    public void randomize() {
        suit = (int) (Math.random() * 4);
        rank = (int) (Math.random() * 13);
    }

    /* GET / SET FUNCTIONS */
    public int getSuit() { return suit; }
    public void setSuit(int suit) { this.suit = suit; }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
    public int getValue() { return 4*suit+rank; }

    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 100, 150);
    }
}
