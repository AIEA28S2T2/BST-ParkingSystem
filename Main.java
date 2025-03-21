import java.time.LocalDateTime;
import java.util.Scanner;
import DataStructures.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select the tree implementation:");
        System.out.println("1. Binary Search Tree");
        System.out.println("2. AVL Tree");
        System.out.println("3. Red-Black Tree");
        System.out.print("Enter choice (1-3): ");
        
        int treeChoice = scanner.nextInt();
        int treeType;
        
        switch (treeChoice) {
            case 2:
                treeType = ParkingSystem.AVL;
                System.out.println("Using AVL Tree");
                break;
            case 3:
                treeType = ParkingSystem.RED_BLACK;
                System.out.println("Using Red-Black Tree");
                break;
            default:
                treeType = ParkingSystem.BST;
                System.out.println("Using Binary Search Tree");
                break;
        }
        
        System.out.print("Enter the number of parking spots: ");
        int n = scanner.nextInt();
        ParkingSystem parkingSystem = new ParkingSystem(n, treeType);
        System.out.println("Initial parking spot tree:");
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
