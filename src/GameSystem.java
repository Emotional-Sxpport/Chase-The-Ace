import javax.swing.*;
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

        start();
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
        PlayingCard traded = null;

        for (int i = 0; i < playerCount; i++) {
            if(turn % playerCount == 0){
                //Generate the buttons
                //Wait for the player to click
                //if trade, call trade
                //if stay, increment turn
            }else {
                turnOrder[turn].play(traded, turnOrder, turn);
                turn = (turn + 1) % playerCount;
                ///TimeUnit.SECONDS.sleep(1);
            }
        }
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

    /* ELIMINATIONS */
    public void endRound() {

        //Determine what the lowest card is
        int lowestRank = 13;
        for (int i = 0; i < playerCount; i++) {
            if (turnOrder[i].getCard().getRank() < lowestRank) {
                lowestRank = turnOrder[i].getCard().getRank();
            }
        }

        //Eliminate player(s) with the lowest card
        for (int i = 0; i < playerCount; i++) {
            if (turnOrder[i].getCard().getRank() == lowestRank) {

                //End the game if the main player is eliminated
                if(i == 0){
                    //Game Over
                }

                //Eliminate a player by shifting all players down
                for (int j = i; j < playerCount - 1; j++) {
                    turnOrder[j] = turnOrder[j + 1];
                    playerCount--;
                }
            }
        }

        //Check if the main player has won
        if(playerCount == 1){
            //Player wins
        }
    }

    /* GET / SET FUNCTIONS */
    public int getPlayerCount() { return playerCount; }
    public Player getPlayer(int index) { return turnOrder[index]; }

    /* DRAWING FUNCTION */
    public void draw(Graphics g, double scale, int offsetX, int offsetY) {
        for (int i = 0; i < playerCount; i++) {
            getPlayer(i).draw(100 + 150 * i, 100, g, scale, offsetX, offsetY);
        }
    }
}
