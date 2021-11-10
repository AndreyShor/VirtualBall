public class Player {

    final private Integer playerID;
    private Boolean connectionStatus;
    private Boolean permission; // check if user can use ball

    Player(int playerID) {
        this.playerID = playerID;
        this.connectionStatus = true;
        this.permission = false;

    }


    public int getID() {
        return this.playerID;
    }

    public boolean getConnectionStatus() {
        return this.connectionStatus;
    }

    public void setConnectionStatus(boolean change) {
        this.connectionStatus = change;
    }

    public boolean getPermission() {
        return this.permission;
    }

    public void setPermission(boolean change) {
        this.permission = change;
    }




}
