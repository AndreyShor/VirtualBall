import com.sun.source.tree.WhileLoopTree;

import java.util.Scanner;

public class ClientProgram {

    private static Client client;

    public static void main(String[] args) {

        try {
            System.out.println("VIRTUAL BALL !!!!");
            System.out.println("Choice option");
            System.out.println("1. Login Game, 2. Exit Game");
            var commandInput = inputInt();

            if (commandInput == 1)  {
                System.out.println("Online Multiplayer Game 'Virtual Ball' is Starting.");

                System.out.println("Enter your ID");
                commandInput = inputInt();

                client = new Client(commandInput);
                System.out.println("Your ID is: " + commandInput);
                while (true) {
                    System.out.println("Player List:");
                    String[] playerList = client.getUserList();
                    for (var player: playerList) {
                        System.out.println(player);
                    }

                    System.out.println("Choice option");
                    System.out.println("1. Kick Ball, 2. Refresh User List Manually, 3. Exit Game");
                    commandInput = inputInt();
                    if (commandInput == 1) {
                        System.out.println("Enter Player ID Number: ");
                        commandInput = inputInt();
                        client.sendBall(commandInput);
                        System.out.println("Ball kicked to user with ID: " + commandInput);
                    } else if (commandInput == 2) {
                    } else if (commandInput == 3) {
                        break;

                    } else {
                        System.out.println("You have entered incorrect command");
                    }
                }
            } else if (commandInput == 2) {
            } else {
                System.out.println("You have entered incorrect command");
            }


            if (!client.exit()) {
                System.out.println("Error during deletion process");
            }
            System.out.println("Game is finished");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int inputInt() {
        Scanner in = new Scanner(System.in);
        return Integer.parseInt(in.nextLine());
    }


}
