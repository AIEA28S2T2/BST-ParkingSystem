import java.time.LocalDateTime;
import java.util.Scanner;
import DataStructures.*;

/*
I have no idea why I wrote such a messy procedure here; I forgot the fact that all the methods could be called in the correct
order inside the ParkingSystem class anyway; that'd keep this place cleaner
*/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of parking spots: ");
        int n = scanner.nextInt();
        ParkingSystem parkingSystem = new ParkingSystem(n);
        parkingSystem.printTree();

        while (true) {
            System.out.println("Enter 1 to add a car, 2 to remove a car, or 0 to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (choice == 0) {
                break;
            }

            System.out.print("Enter the registration number: ");
            String regNo = scanner.nextLine();

            if (choice == 1) {
                System.out.println("Before:");
                parkingSystem.printTree();
                parkingSystem.printQueue();
                parkingSystem.addCar(regNo);       
                System.out.println("After:");
                parkingSystem.printTree();
                parkingSystem.printQueue();
                System.out.println("Car added successfully.");
            } else if (choice == 2) {
                System.out.println("Before:");
                parkingSystem.printTree();
                parkingSystem.printQueue();
                parkingSystem.removeCar(regNo);
                System.out.println("After:");
                parkingSystem.printTree();
                parkingSystem.printQueue();
                System.out.println("Car removed successfully.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
