
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameSystem {

    private boolean[] availableCards;
    private Player[] turnOrder;
    private int turnStart, turn, playerCount;
    private PlayingCard deckCard;
    public PlayingCard traded;
    private PlayingCard prevCard;
    private BufferedImage table, b1, b2, b3, lives;

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
        gameOver = 3;
        iterator = -1;
        itMove = -1;

        try {
            table = ImageIO.read(new File("src/resources/images/table.png"));
            b1 = ImageIO.read(new File("src/resources/images/chars/body1.png"));
            b2 = ImageIO.read(new File("src/resources/images/chars/body2.png"));
            b3 = ImageIO.read(new File("src/resources/images/chars/body3.png"));
            lives = ImageIO.read(new File("src/resources/images/lives.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < 52; i++) { availableCards[i] = true; }
        for (int i = 0; i < playerCount; i++) { turnOrder[i] = new Player(); }
        setWaiting(0);
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

        //Remove lives from the player(s) with the lowest card(s)
        for (int i = 0; i < playerCount; i++) {
            if (turnOrder[i].getCard().getRank() == lowestRank) {
                turnOrder[i].loseLife();
                if (turnOrder[i].getLives() <= 0) {


                    //End the game if the main player is eliminated
                    if (i == 0) {
                        //Game Over
                        gameOver = 1;
                        setWaiting(0);
                        return;

                        //Eliminate a player by shifting all players down
                    } else {
                        Player[] copy = new Player[playerCount - 1];
                        System.arraycopy(turnOrder, 0, copy, 0, i);
                        System.arraycopy(turnOrder, i + 1, copy, i, playerCount - i - 1);
                        turnOrder = copy;
                        playerCount--;
                    }
                }
            }

        }
        if(playerCount == 1){
            gameOver = 2;
            setWaiting(0);
        } else{
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

    /* FONT CREATION */
    public class FontLoaderExample extends JPanel {
        private Font customFont;

        public FontLoaderExample() {
            try (InputStream is = getClass().getResourceAsStream("resources/fonts/Minecraft.ttf")) {
                // load font from classpath resource `\`/fonts/MyFont.ttf\``
                Font f = Font.createFont(Font.TRUETYPE_FONT, is);
                // register so it's available to the JVM
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
                // derive a size (float) and style if needed
                customFont = f.deriveFont(Font.PLAIN, 24f);
                // optionally set as default for this component
                setFont(customFont);
            } catch (Exception ex) {
                ex.printStackTrace();
                // fallback to a logical font
                customFont = new Font("Serif", Font.PLAIN, 24);
            }
        }

        public Font getCustomFont() {
            return customFont;
        }
    }

    /* DRAWING FUNCTION */
    public void draw(Graphics g, double scale, int offsetX, int offsetY, ImageObserver obs) throws IOException {
        if (gameOver == 0) {

            for (int i = 0; i < getPlayer(0).getLives(); i++) {
                g.drawImage(lives, offsetX + (int)((1051-51*i)*scale), offsetY + (int)(25*scale), (int) (44 * scale), (int) (44 * scale), obs);
            }

            g.drawImage(b1, offsetX + (int)(800*scale), offsetY + (int)(364*scale), (int) (144 * scale), (int) (304 * scale), obs);
            g.drawImage(b2, offsetX + (int)(475*scale), offsetY + (int)(300*scale), (int) (184 * scale), (int) (312 * scale), obs);
            g.drawImage(b3, offsetX + (int)(150*scale), offsetY + (int)(364*scale), (int) (208 * scale), (int) (304 * scale), obs);
            g.drawImage(table, offsetX + (int)(200*scale), offsetY + (int)(470*scale), (int) (720 * scale), (int) (198 * scale), obs);

            getPlayer(0).draw(100, 100, g, scale, offsetX, offsetY, Color.GREEN, obs);

            // DRAWS THE USER'S CARD & ANIMATING IT
            if (itMove > -1) {
                if (itMove < 14)
                    prevCard.draw(475, 750 - (int)(400 *Math.cos(itMove/10.0)), g, scale, offsetX, offsetY, obs);
                else
                    getPlayer(0).getCard().draw(475, 750 - (int)(400 *Math.cos(itMove/10.0 + 3.14)), g, scale, offsetX, offsetY, obs);
                if (itMove < 32)
                    itMove++;
                else
                    itMove = -1;
            } else {
                getPlayer(0).getCard().draw(475, 350, g, scale, offsetX, offsetY, obs);
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
            FontLoaderExample f = new FontLoaderExample();
            g.setFont(f.getCustomFont());
            if (gameOver == 1) {
                g.drawString("You Lost!", offsetX + (int) (500 * scale), offsetY + (int) (300 * scale));
                g.drawString("Press ENTER to play again", offsetX + (int)(410*scale), offsetY + (int)(350*scale));
                if (waiting == 1) {
                    //reset game
                    playerCount = 4;
                    turnOrder = new Player[playerCount];
                    for (int i = 0; i < playerCount; i++) {
                        turnOrder[i] = new Player();
                    }
                    gameOver = 0;
                    start();
                }
            }else if (gameOver == 2) {
                g.drawString("You Won!", offsetX + (int) (500 * scale), offsetY + (int) (300 * scale));
                g.drawString("Press ENTER to play again", offsetX + (int)(410*scale), offsetY + (int)(350*scale));
                if (waiting == 1) {
                    //reset game
                    playerCount = 4;
                    turnOrder = new Player[playerCount];
                    for (int i = 0; i < playerCount; i++) {
                        turnOrder[i] = new Player();
                    }
                    gameOver = 0;
                    start();
                }
            }else if (gameOver == 3) {
                g.drawString("Welcome to Chase the Ace", offsetX + (int) (430 * scale), offsetY + (int) (300 * scale));
                g.drawString("Press ENTER to begin", offsetX + (int) (450 * scale), offsetY + (int) (350 * scale));
                if(waiting == 1) {
                    //start game
                    playerCount = 4;
                    turnOrder = new Player[playerCount];
                    for (int i = 0; i < playerCount; i++) {
                        turnOrder[i] = new Player();
                    }
                    gameOver = 0;
                    start();
                }
            }
        }
    }
}
