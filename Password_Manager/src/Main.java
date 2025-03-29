
import com.passwordmanager.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("Welcome to Password Manager");
        System.out.println("1. Register\n2. Login\nChoose an option:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter master password: ");
            String password = scanner.nextLine();
            if (userService.registerUser(username, password)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("User registration failed.");
            }
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter master password: ");
            String password = scanner.nextLine();
            if (userService.loginUser(username, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login failed.");
            }
        } else {
            System.out.println("Invalid option.");
        }
        scanner.close();
    }
}
