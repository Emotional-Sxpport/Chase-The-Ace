public class UserInput extends Thread {

    private GameSystem system;

    public UserInput(GameSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        Player turnOrder[] = system.getTurnOrder();
        int traded = -1;
        int playerCount = system.getPlayerCount();
        int turnStart = (int) (Math.random() * playerCount);
        int turn = turnStart;
        system.shuffle();

        for (int i = 0; i < playerCount; i++) {
            if(turn % playerCount == 0){
                //Generate the buttons
                //Wait for the player to click
                //if trade, call trade
                //if stay, increment turn

                system.setTurn(turn);
                System.out.println("PLAYERS TURN");
                system.setWaiting(0);
                while (system.getWaiting() == 0) {
                    System.out.println(".");
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (system.getWaiting() == 1) {
                    turnOrder[turn].play(traded, turnOrder, turn, turnStart, 1, system.getPlayerCount());
                }
                else if (system.getWaiting() == 2) {
                    traded = -1;
                }
                turn = (turn + 1) % playerCount;


            }else {
                system.setTurn(turn);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                turnOrder[turn].play(traded, turnOrder, turn, turnStart, 0, system.getPlayerCount());
                turn = (turn + 1) % playerCount;
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
