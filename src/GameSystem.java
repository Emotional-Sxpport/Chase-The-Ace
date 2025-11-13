
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
//import java.util.ArrayUtils;

import static java.lang.Thread.sleep;

public class GameSystem {

    private boolean[] availableCards;
    private Player[] turnOrder;
    private int turnStart, turn, playerCount;
    private PlayingCard deckCard;
    public PlayingCard traded;
    private PlayingCard prevCard;

    // just a ton of flags
    private int gameOver; // 0 for not over, 1 for loss, 2 for win
    private int waiting;
    private boolean showingCards, threadNotMade;
    private int iterator, itMove;


    /* INITIALIZES VARIABLES */
    public GameSystem() {
        availableCards = new boolean[52];
        playerCount = 4;
        turnOrder = new Player[playerCount];
        deckCard = new PlayingCard();
        gameOver = 0;
        iterator = -1;
        itMove = -1;

        for(int i = 0; i < 52; i++) { availableCards[i] = true; }
        for (int i = 0; i < playerCount; i++) { turnOrder[i] = new Player(); }
        start();
    }


    /* INITIALIZES VARIABLES */
    public GameSystem(int count) {
        availableCards = new boolean[52];
        playerCount = count;
        turnOrder = new Player[playerCount];
        for(int i = 0; i < 52; i++) { availableCards[i] = true; }
        for (int i = 0; i < playerCount; i++) { turnOrder[i] = new Player(); }
    }


    /* STARTS A ROUND */
    public void start() {
        iterator = -10;
        Thread thread = new UserInput(this);
        thread.start();
    }


    /* SHUFFLES THE HANDS */
    public void shuffle() {
        for (int i = 0; i < 52; i++) { availableCards[i] = true; }

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
        System.out.println("Ending Round...");
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
                if(i == 0) {
                    //Game Over
                    gameOver = 1;

                    //Eliminate a player by shifting all players down
                }else{
                    Player[] copy = new Player[playerCount-1];
                    for(int j = 0; j < playerCount-1; j++){
                        if(j!=i){
                            copy[j] = turnOrder[i];
                        }
                    }
                    //turnOrder = ArrayUtils.removeElement(turnOrder, i);
                    /*if(i == playerCount - 1){
                        playerCount--;
                        break;
                    }
                    for (int j = i; j < playerCount - 2; j++) {
                        turnOrder[j] = turnOrder[j + 1];
                    }*/
                    //turnOrder[playerCount - 1] = null;
                    playerCount--;
                }
            }
        }
        if(playerCount == 1){
            gameOver = 2;
        } else {
            start();
        }
    }


    /* GET / SET FUNCTIONS */
    public int getPlayerCount() { return playerCount; }
    public Player getPlayer(int index) { return turnOrder[index]; }
    public int getWaiting() { return waiting; }
    public void setWaiting(int var) { waiting = var; }
    public Player[] getTurnOrder() { return turnOrder; }
    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }
    public int getTurnStart() { return turnStart; }
    public PlayingCard getTraded() { return traded; }
    public PlayingCard getDeckCard() { return deckCard; }
    public void setPrevCard(PlayingCard prevCard) { this.prevCard = prevCard; }
    public void setGameOver(int gameOver) { this.gameOver = gameOver; }
    public void setIterator(int iterator) { this.iterator = iterator; }
    public void setItMove(int itMove) { this.itMove = itMove; }


    /* DRAWING FUNCTION */
    public void draw(Graphics g, double scale, int offsetX, int offsetY, ImageObserver obs) throws IOException {
        if (gameOver == 0) {

            // DRAWS THE USER'S CARD & ANIMATING IT
            getPlayer(0).draw(100, 100, g, scale, offsetX, offsetY, Color.GREEN, obs);
            if (itMove > -1) {
                if (itMove < 14)
                    prevCard.draw(450, 650 - (int)(400 *Math.cos(itMove/10.0)), g, scale, offsetX, offsetY, obs);
                else
                    getPlayer(0).getCard().draw(450, 650 - (int)(400 *Math.cos(itMove/10.0 + 3.14)), g, scale, offsetX, offsetY, obs);
                System.out.println("test: " + itMove + ", " + 400*Math.sin(itMove)/10.0 + 3.14);
                if (itMove < 32)
                    itMove++;
                else
                    itMove = -1;
            } else {
                getPlayer(0).getCard().draw(450, 250, g, scale, offsetX, offsetY, obs);
            }

            for (int i = 1; i < playerCount; i++) {
                if (i == turn)
                    getPlayer(i).draw(100 + 150 * i, 100, g, scale, offsetX, offsetY, Color.RED, obs);
                else
                    getPlayer(i).draw(100 + 150 * i, 100, g, scale, offsetX, offsetY, Color.BLUE, obs);
            }

            // DRAWS CARD FLIP
            if (iterator > -1) {
                for (int i = 0; i < playerCount; i++)
                    getPlayer(i).getCard().drawFlip(150 + 150*i, 100, g, scale, offsetX, offsetY, 93*Math.cos(iterator/7.0), obs);
                if (iterator < 22) iterator++;
            }


        }
        else { // game over screens
            g.setColor(Color.BLACK);
            g.fillRect(offsetX, offsetY, (int) (1120 * scale), (int) (630 * scale));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, (int)(30*scale)));
            if (gameOver == 1)
                g.drawString("You Lost!", offsetX + (int)(500*scale), offsetY + (int)(300*scale));
            else if (gameOver == 2)
                g.drawString("You Won!", offsetX + (int)(500*scale), offsetY + (int)(300*scale));
        }
    }
}
