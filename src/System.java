public class System {

    private boolean[] availableCards;
    private Player[] turnOrder;

    public System() {
        availableCards = new boolean[52];
        turnOrder = new Player[4];

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
    }
}
