import java.awt.*;

public class GameSystem {

    private boolean[] availableCards;
    private Player[] turnOrder;
    private int turnStart, turn;
    private int playerCount;
    private PlayingCard deckCard;

    /* INITIALIZES VARIABLES */
    public GameSystem() {
        availableCards = new boolean[52];
        playerCount = 4;
        turnOrder = new Player[playerCount];
        deckCard = new PlayingCard();

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
        for (int i = 0; i < playerCount; i++) {
            turnOrder[i] = new Player();
        }

        shuffle();
    }

    /* INITIALIZES VARIABLES */
    public GameSystem(int count) {
        availableCards = new boolean[52];
        playerCount = count;
        turnOrder = new Player[playerCount];

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
        for (int i = 0; i < playerCount; i++) {
            turnOrder[i] = new Player();
        }

        //shuffle();
    }

    public void start() {
        shuffle();
        turnStart = (int) (Math.random() * playerCount);
        turn = turnStart;

        for (int i = 0; i < playerCount - 1; i++) {
            turnOrder[turn].play(turnOrder[(turn+1)%playerCount].getCard(), turnOrder, turn);
            turn = (turn+1)%playerCount;
            ///TimeUnit.SECONDS.sleep(1);
        }
        turnOrder[turn].play(deckCard, turnOrder, turn);
    }

    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }

        for (int i = 0; i < playerCount; i++) {
            getPlayer(i).getCard().randomize();
            if (!availableCards[getPlayer(i).getCard().getValue()])
                i--;
            else
                availableCards[getPlayer(i).getCard().getValue()] = false;
        }
        deckCard.randomize();
        while (!availableCards[deckCard.getValue()]) {
            deckCard.randomize();
        }
        availableCards[deckCard.getValue()] = false;
    }

    /* GET / SET FUNCTIONS */
    public int getPlayerCount() { return playerCount; }
    public Player getPlayer(int index) { return turnOrder[index]; }

    /* DRAWING FUNCTION */
    public void draw(Graphics g, double scaleX, double scaleY) {
        for (int i = 0; i < playerCount; i++) {
            getPlayer(i).draw(100 + 150 * i, 100, g);
        }
    }
}
