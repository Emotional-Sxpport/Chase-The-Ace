import java.awt.*;

public class System {

    private boolean[] availableCards;
    private Player[] turnOrder;
    private int turn;
    private int playerCount;

    /* INITIALIZES VARIABLES */
    public System() {
        availableCards = new boolean[52];
        playerCount = 4;
        turnOrder = new Player[playerCount];

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
        for (int i = 0; i < playerCount; i++) {
            turnOrder[i] = new Player();
        }

        shuffle();
    }

    /* INITIALIZES VARIABLES */
    public System(int count) {
        availableCards = new boolean[52];
        playerCount = count;
        turnOrder = new Player[playerCount];

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
        for (int i = 0; i < playerCount; i++) {
            turnOrder[i] = new Player();
        }

        shuffle();
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
    }

    /* GET / SET FUNCTIONS */
    public int getPlayerCount() { return playerCount; }
    public Player getPlayer(int index) { return turnOrder[index]; }

    /* DRAWING FUNCTION */
    public void draw(Graphics g) {
        for (int i = 0; i < playerCount; i++) {
            getPlayer(i).draw(100 + 150 * i, 100, g);
        }
    }
}
