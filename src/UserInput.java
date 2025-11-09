public class UserInput extends Thread {

    private GameSystem system;

    public UserInput(GameSystem system) {
        this.system = system;
    }

    /*
    @Override
    public void run() {
        int turn = system.getTurn();
        Player turnOrder[] = system.getTurnOrder();
        PlayingCard traded = system.getTraded();

        System.out.println("PLAYERS TURN");
        system.setWaiting(0);
        //playersTurn = true;
        while (system.getWaiting() == 0) {
            System.out.println("waiting");
        }
        if (system.getWaiting() == 1) {
            system.getTurnOrder()[turn].trade(turnOrder[turn].getCard(), traded, turnOrder, turn);
        }
        else if (system.getWaiting() == 2) {
            traded = null;
        }
        //turn = (turn + 1) % system.getPlayerCount();
    }*/

    @Override
    public void run() {
        int turn = system.getTurn();
        Player turnOrder[] = system.getTurnOrder();
        PlayingCard traded = system.getTraded();
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


                System.out.println("PLAYERS TURN");
                system.setWaiting(0);
                //playersTurn = true;
                while (system.getWaiting() == 0) {
                    System.out.println("waiting");
                }
                if (system.getWaiting() == 1) {
                    system.getTurnOrder()[turn].trade(turnOrder[turn].getCard(), traded, turnOrder, turn);
                }
                else if (system.getWaiting() == 2) {
                    traded = null;
                }
                turn = (turn + 1) % playerCount;

            }else {
                turnOrder[turn].play(traded, turnOrder, turn);
                turn = (turn + 1) % playerCount;
                //playersTurn = false;
                System.out.println("NOT PLAYERS TURN");
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ///TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
