import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {

    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;
    Integer userID;

    public Client(int customerId) throws Exception {
        // Connecting to the server and creating objects for communications
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream()); // response

        // Automatically flushes the stream with every command
        writer = new PrintWriter(socket.getOutputStream(), true); // request

        // Check if such player exist
        int connectStat = -1;
        while (true) {
            writer.println(customerId);
            String line = reader.nextLine();
            connectStat = Integer.parseInt(line);

            if (connectStat == -1 ) {
                System.out.println("This ID is taken, choice another one: ");
                Scanner in = new Scanner(System.in);
                customerId = Integer.parseInt(in.nextLine());
            } else {
                break;
            }
        }

        this.userID = customerId;

    }

    public String[] getUserList() {
        writer.println("refresh");
        String line = reader.nextLine();

        int numberOfPlayers = Integer.parseInt(line);

        String[] playerList = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            line = reader.nextLine();
            playerList[i] = line;
        }

        return playerList;
    }

    public boolean exit() {
        writer.println("exit");
        String line = reader.nextLine();
        return Boolean.parseBoolean(line);
    }

    public void sendBall(int playerID) {
        writer.println("kick " + playerID);
        String line = reader.nextLine();
        System.out.println(line);
    }

    public void checkAvailability(int accountNumber) {

    }




    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
