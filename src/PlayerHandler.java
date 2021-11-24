import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerHandler implements Runnable {

    private static ArrayList<Player> playerList;
    private AtomicInteger playerNotificationName;
    private Player playerProfile;
    private final Socket socket;

    Integer userID;

    public static final Object lock = new Object();

    PlayerHandler(Socket socket, ArrayList<Player> playerList, AtomicInteger playerNotificationName) {
        this.socket =socket;
        PlayerHandler.playerList = playerList;
        this.playerNotificationName = playerNotificationName;
    }

    public void connect(int id) {
        this.userID = id;
        synchronized (lock) {
            if (playerList.size() != 0 ) {
                playerProfile = new Player(userID);
                playerList.add(playerProfile);
            } else {
                // First player in a game will have a ball at the start of the game
                playerProfile = new Player(userID);
                playerProfile.setPermission(true);
                playerList.add(playerProfile);
            }

        }
        System.out.println("New connection; customer ID " + userID);
    }

    public void disConnect() {
        synchronized (lock) {
            // If player have a ball, before exit he gives it to another person
            if (playerProfile.getPermission()) {
                playerList.removeIf(e -> e.getID() == this.userID);

                int upperBound = playerList.size();
                Random rand = new Random();
                int randomPlayer = rand.nextInt(upperBound);
                playerList.get(randomPlayer).setPermission(true);
            } else {
                // delete player from a player pool
                playerList.removeIf(e -> e.getID() == this.userID);
            }


        }
    }

    public boolean kickBall(int to) {
        boolean workDone;
        synchronized (lock) {
            if (this.playerProfile.getPermission()) {
                this.playerProfile.setPermission(false);
                for (var e : playerList) {
                    if (e.getID() == to) {
                        e.setPermission(true);
                    }
                }
                workDone = true;
            } else {
                workDone = false;
            }
        }
        return workDone;
    }

    public boolean checkPlayerExist(int id ) {
        boolean exist = false;
        synchronized (lock) {
            if (playerList.size() != 0 ) {
                for (var player : playerList){
                    if (player.getID() == id ) {
                        exist = true;
                    }
                }
            }
        }
        return exist;
    }

    public void getPlayerList(PrintWriter writer) {
        // Send Amount of Players#
        synchronized (lock) {
            writer.println(playerList.size());
            // Send each player data
            for (var player: playerList) {
                String playerInfo = "" + player.getID() + " " + player.getPermission();
                writer.println(playerInfo);
            }
        }
    }





    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            try {

                // Check if user id is unique
                while (true) {
                    if (this.userID != null){
                        break;
                    } else {
                        int check  = Integer.parseInt(scanner.nextLine());
                        if (!checkPlayerExist(check)) {
                            connect(check);
                            writer.println(0);
                        } else {
                            writer.println(-1);
                        }
                    }

                }

                // Commands for user
                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "kick":
                            int to = Integer.parseInt(substrings[1]);

                            var check = kickBall(to);
                            if (check) {
                                writer.println(1);
                            } else {
                                writer.println(0);
                            }

                            break;

                        case "refresh":
                            getPlayerList(writer);

                            break;


                        case "exit":
                            disConnect();
                            writer.println("true");
                            socket.close();
                            break;
                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

