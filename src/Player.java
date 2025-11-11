import java.awt.*;
import java.awt.image.ImageObserver;

public class Player {
    private PlayingCard playingCard;

    /* CONSTRUCTORS */
    public Player() { playingCard = new PlayingCard(); }
    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }


    /* PLAYER TRADES CARDS */
    public void trade(PlayingCard mycard, int othercard, Player[] turnOrder, int nextPlayer, int initialP){
        PlayingCard temp;
        if(nextPlayer == initialP) {
            //Generate new card
            temp = new PlayingCard();
            temp.randomize();
            this.setCard(temp);
        }else if(turnOrder[nextPlayer].getCard().getRank() == 12){
            //If the next player has a king, the trade is blocked
            othercard = -1;
            return;
        }else {
            //Get next player's card
            temp = turnOrder[nextPlayer].getCard();
            turnOrder[nextPlayer].setCard(mycard);
            othercard = temp.getRank();
            this.setCard(temp);
        }
    }


    /* PLAYER MAKES THEIR TURN */
    public void play(int othercard, Player[] turnOrder, int currentPlayer, int initialP, int isUser, int pCount){
        //logic for their decision to stay or trade

        if(isUser == 1) {
            trade(playingCard, othercard, turnOrder, (currentPlayer+1)%pCount, initialP);

            //If they weren't traded with, just base their decision on card rank
        }else if(othercard == -1){
            if(playingCard.getRank() < 6){
                //trade
                trade(playingCard, othercard, turnOrder, (currentPlayer+1)%pCount, initialP);
            }else{
                //stay
                othercard = -1;
            }

        //If they were traded with, consider their rank to the previous one
        }else if(othercard > this.playingCard.getRank() && playingCard.getRank() < 6){
            //trade
            trade(playingCard, othercard, turnOrder, (currentPlayer+1)%pCount, initialP);
        } else {
            //stay
            othercard = -1;
        }
    }


    /* GET / SET FUNCTIONS */
    public PlayingCard getCard() { return playingCard; }
    public void setCard(PlayingCard playingCard) { this.playingCard = playingCard; }


    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g, double scale, int offsetX, int offsetY, Color color, ImageObserver obs) {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        g.setColor(color);
        g.fillRect(offsetX + (int) (x * scale), offsetY + (int) (y * scale), (int) (80 * scale), (int) (80 * scale));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int)(16*scale)));
        g.drawString(ranks[getCard().getRank()] + " of", offsetX + (int) ((x + 5) * scale), offsetY + (int) ((y + 60) * scale));
        g.drawString(suits[getCard().getSuit()], offsetX + (int) ((x + 5) * scale), offsetY + (int) ((y + 75) * scale));
    }
}
