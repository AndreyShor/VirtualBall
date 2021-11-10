import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private final static int port = 8888;
    private static ArrayList<Player> playerList;
    private static AtomicInteger playerNotificationID;

    public static void main(String[] args)
    {
        playerList = new ArrayList<>();
        RunServer();
    }

    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for incoming connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new PlayerHandler(socket, playerList, playerNotificationID)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
