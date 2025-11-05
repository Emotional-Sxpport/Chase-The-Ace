import java.awt.*;

public class Player {
    private PlayingCard playingCard;

    public Player() {
        playingCard = new PlayingCard(0, 0);
    }

    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }

    public PlayingCard getCard() { return playingCard; }
    public void setCard(PlayingCard playingCard) { this.playingCard = playingCard; }

    public void draw(int x, int y, Graphics g) {

        //super.paintComponent(g);

        g.setColor(Color.BLUE);
        //int size = 100;
        g.fillRect(x, y, 80, 80);
    }
}
