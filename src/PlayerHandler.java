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

    public void connect(int id) {
        this.userID = id;
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

    public void kickBall(int to) {
        synchronized (lock) {
            for (var e : playerList) {
                if (e.getID() == this.userID) {
                    e.setPermission(false);
                }

                if (e.getID() == to) {
                    e.setPermission(true);
                }
            }
        }
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
                            kickBall(to);
                            writer.println(true);
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
                            writer.println(true);
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

