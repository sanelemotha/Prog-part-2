package progassignment;

import java.util.Scanner;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String name;
    private String surname;

    // Constructor for creating a Login instance with credentials
    public Login(String registeredUsername, String registeredPassword, String name, String surname) {
        this.registeredUsername = registeredUsername;
        this.registeredPassword = registeredPassword;
        this.name = name;
        this.surname = surname;
    }

    // Instance method to perform login
    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Login ===");

        while (true) {
            System.out.print("Enter username: ");
            String usernameInput = scanner.nextLine();

            System.out.print("Enter password: ");
            String passwordInput = scanner.nextLine();

            if (loginCheck(usernameInput, passwordInput)) {
                System.out.println("Welcome " + name + " " + surname + "!");
                break;
            } else {
                System.out.println("Incorrect username or password. Please try again.");
            }
        }
    }

    // Instance method to check the login credentials
    public boolean loginCheck(String inputUsername, String inputPassword) {
        return registeredUsername.equals(inputUsername) && registeredPassword.equals(inputPassword);
    }

    // Static method to perform login (for non-instance use)
    public static void staticLogin(String registeredUsername, String registeredPassword, String name, String surname) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Login ===");

        while (true) {
            System.out.print("Enter username: ");
            String usernameInput = scanner.nextLine();

            System.out.print("Enter password: ");
            String passwordInput = scanner.nextLine();

            if (loginCheckStatic(usernameInput, passwordInput, registeredUsername, registeredPassword)) {
                System.out.println("Welcome " + name + " " + surname + "!");
                break;
            } else {
                System.out.println("Incorrect username or password. Please try again.");
            }
        }
    }

    // Static method to check the login credentials
    public static boolean loginCheckStatic(String inputUsername, String inputPassword, String registeredUsername, String registeredPassword) {
        return registeredUsername.equals(inputUsername) && registeredPassword.equals(inputPassword);
    }

    
}
