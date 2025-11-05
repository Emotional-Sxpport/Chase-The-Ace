import javax.smartcardio.Card;

public class PlayingCard {
    private int suit;
    private int rank;

    public PlayingCard() {
        suit = 0;
        rank = 0;
    }

    public PlayingCard(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getSuit() { return suit; }
    public void setSuit(int suit) { this.suit = suit; }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public void draw() {}
}
