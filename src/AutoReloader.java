import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;

public class AutoReloader extends TimerTask {

    private ArrayList<Player> checkArray;
    private static Client playerHandler;

    AutoReloader(Client client) {
        playerHandler = client;
        this.checkArray = null;
    }


    @Override
    public void run() {
        String[] dataServer = playerHandler.getUserList();

        if (checkArray == null || checkArray.size() == 0) {

            // initial Setup of checkArray
            this.checkArray = new ArrayList<>();
            int numberPlayers = dataServer.length;

            for (int i = 0; i < numberPlayers; i++) {
                String[] substrings = dataServer[i].split(" ");
                // Get ID
                int playerID = Integer.parseInt(substrings[0]);
                var player = new Player(playerID);
                //Get Status
                boolean playerStatus = Boolean.parseBoolean(substrings[1]);
                player.setPermission(playerStatus);

                this.checkArray.add(player);
            }
        } else if (dataServer.length != checkArray.size()) {
            // Check if player list has changed in size
            for (var player: dataServer) {
                System.out.println(player);
            }

            System.out.println("Choice option");
            System.out.println("1. Kick Ball, 2. Refresh User List Manually, 3. Exit Game");

            // rewrite past changeList in local player memory
            this.checkArray = new ArrayList<>();
            int numberPlayers = dataServer.length;

            for (int i = 0; i < numberPlayers; i++) {
                String[] substrings = dataServer[i].split(" ");
                // Get ID
                int playerID = Integer.parseInt(substrings[0]);
                var player = new Player(playerID);
                //Get Status
                boolean playerStatus = Boolean.parseBoolean(substrings[1]);
                player.setPermission(playerStatus);

                this.checkArray.add(player);
            }
        }
        else {
            int numberPlayersServer = dataServer.length;

            // Check Permission Change

            for (int i = 0; i < numberPlayersServer - 1; i++) {
                String[] substrings = dataServer[i].split(" ");
                // Get ID
                int playerID = Integer.parseInt(substrings[0]);
                var playerServer = new Player(playerID);
                //Get Status
                boolean playerStatus = Boolean.parseBoolean(substrings[1]);
                playerServer.setPermission(playerStatus);
                var playerSaved = this.checkArray.get(i);
                if(!playerSaved.equals(playerServer)) {
                    this.checkArray.set(i, playerServer);

                    for (var player: dataServer) {
                        System.out.println(player);
                    }

                    System.out.println("Choice option");
                    System.out.println("1. Kick Ball, 2. Refresh User List Manually, 3. Exit Game");
                };
            }
        }
    }
}
