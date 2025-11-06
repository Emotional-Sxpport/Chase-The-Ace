import java.awt.*;

public class Player {
    private PlayingCard playingCard;

    /* INITIALIZES PLAYER */
    public Player() {
        playingCard = new PlayingCard();
        playingCard.randomize();
    }

    /* INITIALIZES PLAYER */
    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }

    /* GET / SET FUNCTIONS */
    public PlayingCard getCard() { return playingCard; }
    public void setCard(PlayingCard playingCard) { this.playingCard = playingCard; }

    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g) {

        String str = "S" + getCard().getSuit() + " R" + getCard().getRank();

        g.setColor(Color.BLUE);
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.WHITE);
        g.drawString(str, x + 5, y+75);
    }
}
