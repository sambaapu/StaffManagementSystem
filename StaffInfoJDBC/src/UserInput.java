import java.util.Scanner;

public class UserInput {
    private static Scanner scanner;

    // private constructor to prevent instantiation
    private UserInput() {
    }

    public static Scanner getInstance() {
        if (scanner == null) {
            // Create a new Scanner instance if it doesn't exist
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    // close the Scanner when it's no longer needed
    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}