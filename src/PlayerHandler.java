import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerHandler implements Runnable {

    private ArrayList<Player> playerList;
    private AtomicInteger playerNotificationName;

    PlayerHandler(Socket socket, ArrayList<Player> playerList, AtomicInteger playerNotificationName) {
        this.playerList = playerList;
        this.playerNotificationName = playerNotificationName;
    }

    public void connect() {
        // create profile for self
        synchronized ()
    }

    public void disConnect() {

    }

    public void choiceBall() {

    }


    @Override
    public void run() {

    }
}
