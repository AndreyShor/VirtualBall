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
    private final Socket socket;

    Integer userID;

    public static final Object lock = new Object();

    PlayerHandler(Socket socket, ArrayList<Player> playerList, AtomicInteger playerNotificationName) {
        this.socket =socket;
        PlayerHandler.playerList = playerList;
        this.playerNotificationName = playerNotificationName;
    }

    public void connect() {
        synchronized (lock) {
            playerList.add(new Player(userID));
        }
        System.out.println("New connection; customer ID " + userID);
    }

    public void disConnect() {
        synchronized (lock) {
            playerList.removeIf(e -> e.getID() == this.userID);
        }
    }

    public void choiceBall(int to) {
        synchronized (lock) {
            playerList.get(this.userID).setPermission(); // it will swap ball permission value
            playerList.get(to).setPermission();
        }
    }



    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            try {
                this.userID = Integer.parseInt(scanner.nextLine());
                connect();
                writer.println("SUCCESS");

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "kick":


                            break;

                        case "refresh":
                            // Send Amount of Players
                            writer.println(playerList.size());

                            // Send each player data
                            for (var player: playerList) {
                                String playerInfo = "" + player.getID() + " " + player.getPermission();
                                writer.println(playerInfo);
                            }

                            break;

                        case "exit":
                            disConnect();
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

