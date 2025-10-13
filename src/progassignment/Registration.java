package progassignment;

import java.util.Scanner;
import java.util.regex.*;

public class Registration {

    public static User register() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Registration ===");

        // Name input
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Surname input
        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();

        // Username input with validation loop
        String username;
        while (true) {
            System.out.print("Enter username (must contain '_' and be 5 characters or less): ");
            username = scanner.nextLine();

            if (validateUsername(username).equals("valid")) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted your user name must have 5 characters or less and contain _ Please try again.");
            }
        }

        // Password input with validation loop
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            String passwordResult = validatePassword(password);
            if (passwordResult.equals("valid")) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Phone number input with validation loop
        String phone;
        while (true) {
            System.out.print("Enter phone number (e.g. +27831234567): ");
            phone = scanner.nextLine();
            if (validateCellPhone(phone).equals("valid")) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code. Please try again.");
            }
        }

        // Create new User object with the collected info
        User newUser = new User(name, surname, username, password);

        // Now call the Login process (after successful registration)
        Login userLogin = new Login(username, password, name, surname);
        userLogin.login();  // Calls the login method for non-static approach

         // Attempt to login
        boolean loginSuccessful = userLogin.loginCheck(username, password);
        
        if (loginSuccessful) {
            // If login is successful, create a chatmenu instance
            chatmenu userChatMenu = new chatmenu();
            userChatMenu.quickchatMenu();  // This starts the chat menu
        } else {
            // If login fails, prompt user
            System.out.println("Login failed. Please try again.");
        }

        return newUser;
    }

    public static String validateUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return "valid";
        } else {
            return "invalid";
        }
    }

    public static String validatePassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
            return "valid";
        } else {
            return "invalid";
        }
    }

    public static String validateCellPhone(String phoneNumber) {
        String regex = "^\\+27\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            return "valid";
        } else {
            return "invalid";
           // OpenAI (2025) ChatGPT (Aug 25). Available at: https://chat.openai.com/ (Accessed: 25 August 2025).
        }
    }
}
