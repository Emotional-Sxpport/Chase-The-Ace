public class UserInput extends Thread {

    private GameSystem system;

    public UserInput(GameSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        int turn = system.getTurn();
        Player turnOrder[] = system.getTurnOrder();
        PlayingCard traded = null;
        int turnStart = system.getTurnStart();
        int playerCount = system.getPlayerCount();

        system.shuffle();
        turnStart = (int) (Math.random() * playerCount);
        turn = turnStart;
        traded = null;

        for (int i = 0; i < playerCount; i++) {
            if(turn % playerCount == 0){
                //Generate the buttons
                //Wait for the player to click
                //if trade, call trade
                //if stay, increment turn

                system.setTurn(turn);
                System.out.println("PLAYERS TURN");
                system.setWaiting(0);
                //playersTurn = true;
                while (system.getWaiting() == 0) {
                    System.out.println("waiting");
                }
                if (system.getWaiting() == 1) {
                    turnOrder[turn].play(traded, turnOrder, turn, turnStart, 1);
                }
                else if (system.getWaiting() == 2) {
                    traded = null;
                }
                turn = (turn + 1) % playerCount;


            }else {
                system.setTurn(turn);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                turnOrder[turn].play(traded, turnOrder, turn, turnStart, 0);
                turn = (turn + 1) % playerCount;
                //playersTurn = false;
                System.out.println("NOT PLAYERS TURN");
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        system.endRound();
    }
}
