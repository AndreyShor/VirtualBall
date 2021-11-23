import com.sun.source.tree.WhileLoopTree;

import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {

        try {
            System.out.println("VIRTUAL BALL !!!!");
            System.out.println("Choice option");
            System.out.println("1. Login Game, 2. Exit Game");
            Scanner in = new Scanner(System.in);
            int command = Integer.parseInt(in.nextLine());
            if (command == 1)  {
                System.out.println("Online Multiplayer Game 'Virtual Ball' is Starting.");
                System.out.println("Your ID is: 2");
                while (true) {
                    System.out.println("Player List:");
                    System.out.println(" ");
                    System.out.println("| Player id: 1 | Hold Ball: NO ");
                    System.out.println("| Player id: 2 | Hold Ball: YES ");
                    System.out.println("| Player id: 3 | Hold Ball: NO ");
                    System.out.println(" ");
                    System.out.println("Choice option");
                    System.out.println("1. Kick Ball, 2. Refresh User List Manually, 3. Exit Game");
                    in = new Scanner(System.in);
                    command = Integer.parseInt(in.nextLine());
                    if (command == 1) {
                        System.out.println("Enter Player ID Number: ");
                        in = new Scanner(System.in);
                        command = Integer.parseInt(in.nextLine());
                        System.out.println("Ball kicked to user with ID: " + command);
                    } else if (command == 2) {
                    } else if (command == 3) {

                        break;
                    } else {
                        System.out.println("You have entered incorrect command");
                    }
                }
            } else if (command == 2) {
            } else {
                System.out.println("You have entered incorrect command");
            }

            System.out.println("Game is finished");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
