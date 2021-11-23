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

        writer.println(customerId);
        this.userID = customerId;

    }

    public void getUserList() {
        writer.println("refresh");
        String line = reader.nextLine();
    }

    public void sendBall(int accountNumber) {

    }

    public void checkAvailability(int accountNumber) {

    }

    public void refresh(int fromAccount, int toAccount, int amount) throws Exception {

    }

    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
