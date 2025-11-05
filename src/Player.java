public class Player {
    private PlayingCard playingCard;

    public Player() {
        playingCard = new PlayingCard(0, 0);
    }

    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }

    public void draw() {}
}
