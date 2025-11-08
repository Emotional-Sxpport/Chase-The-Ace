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

    public void trade(PlayingCard mycard, Player[] turnOrder, int nextPlayer){
        PlayingCard temp;
        if(nextPlayer >= turnOrder.length) {
            //Generate new card
            temp = new PlayingCard();
            temp.randomize();
            this.setCard(temp);
        }else {
            //Get next player's card
            temp = turnOrder[nextPlayer].getCard();
            turnOrder[nextPlayer].setCard(mycard);
            this.setCard(temp);
        }
        return;
    }

    public void play(PlayingCard othercard, Player[] turnOrder, int nextPlayer){
        //logic for their decision to stay or trade

        //If they weren't traded with, just base on card rank
        if(othercard == null){
            if(playingCard.getRank() < 6){
                //trade
                trade(playingCard, turnOrder, nextPlayer);
            }

        //if they were traded with, consider their rank to the previous one
        }else if(othercard.getRank() > this.playingCard.getRank() && playingCard.getRank() < 6){
            //trade
            trade(playingCard, turnOrder, nextPlayer);
        }
    }

    /* DRAW FUNCTION */
    public void draw(int x, int y, Graphics g, double scaleX, double scaleY) {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",  "Jack", "Queen", "King"};

        g.setColor(Color.BLUE);
        g.fillRect(x, y, 80, 80);
        g.setColor(Color.WHITE);
        g.drawString(ranks[getCard().getRank()] + " of", x+5, y+60);
        g.drawString(suits[getCard().getSuit()], x+5, y+75);
    }
}
