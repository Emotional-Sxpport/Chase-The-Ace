import java.awt.*;

public class Player {
    private PlayingCard playingCard;

    /* INITIALIZES PLAYER */
    public Player() {
        playingCard = new PlayingCard();
    }

    /* INITIALIZES PLAYER */
    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }

    /* GET / SET FUNCTIONS */
    public PlayingCard getCard() { return playingCard; }
    public void setCard(PlayingCard playingCard) { this.playingCard = playingCard; }

    public void play(PlayingCard othercard) {

    }

    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g) {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",  "Jack", "Queen", "King"};

        g.setColor(Color.BLUE);
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.WHITE);
        g.drawString(ranks[getCard().getRank()] + " of", x+5, y+60);
        g.drawString(suits[getCard().getSuit()], x+5, y+75);
    }
}
