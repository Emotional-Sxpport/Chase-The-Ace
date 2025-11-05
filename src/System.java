public class System {

    private boolean[] availableCards;
    private Player[] turnOrder;
    private int turn;
    private int playerCount;

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
    }

    public System(int count) {
        availableCards = new boolean[52];
        playerCount = count;
        turnOrder = new Player[playerCount];

        for(int i = 0; i < 52; i++) {
            availableCards[i] = true;
        }
    }

    public int getPlayerCount() { return playerCount; }
    public Player getPlayer(int index) {
        return turnOrder[index];
    }

    //public draw ()
}
