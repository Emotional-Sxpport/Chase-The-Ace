import java.awt.*;

public class Player {
    private PlayingCard playingCard;

    /* INITIALIZES PLAYER */
    public Player() {
        playingCard = new PlayingCard();
    }

    /* INITIALIZES PLAYER */
    public Player(PlayingCard playingCard) {
        this.playingCard = playingCard;
    }

    /* GET / SET FUNCTIONS */
    public PlayingCard getCard() { return playingCard; }
    public void setCard(PlayingCard playingCard) { this.playingCard = playingCard; }

    public void trade(PlayingCard mycard, PlayingCard othercard, Player[] turnOrder, int nextPlayer){
        PlayingCard temp;
        if(nextPlayer > turnOrder.length) {
            //Generate new card
            temp = new PlayingCard();
            temp.randomize();
            this.setCard(temp);
        }else if(turnOrder[nextPlayer].getCard().getValue() == 12){
            //If the next player has a king, the trade is blocked
            return;
        }else {
            //Get next player's card
            temp = turnOrder[nextPlayer].getCard();
            turnOrder[nextPlayer].setCard(mycard);
            othercard = temp;
            this.setCard(temp);
        }
    }

    public void play(PlayingCard othercard, Player[] turnOrder, int currentPlayer){
        //logic for their decision to stay or trade

        //If they weren't traded with, just base their decision on card rank
        if(othercard == null){
            if(playingCard.getRank() < 6){
                //trade
                trade(playingCard, othercard, turnOrder, currentPlayer+1);
            }else{
                //stay
                othercard = null;
            }

        //If they were traded with, consider their rank to the previous one
        }else if(othercard.getRank() > this.playingCard.getRank() && playingCard.getRank() < 6){
            //trade
            trade(playingCard, othercard, turnOrder, currentPlayer+1);
        } else {
            //stay
            othercard = null;
        }
    }

    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g, double scale, int offsetX, int offsetY) {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        g.setColor(Color.BLUE);
        g.fillRect(offsetX + (int) (x * scale), offsetY + (int) (y * scale), (int) (80 * scale), (int) (80 * scale));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, (int)(16*scale)));
        g.drawString(ranks[getCard().getRank()] + " of", offsetX + (int) ((x + 5) * scale), offsetY + (int) ((y + 60) * scale));
        g.drawString(suits[getCard().getSuit()], offsetX + (int) ((x + 5) * scale), offsetY + (int) ((y + 75) * scale));
    }
}
