import java.util.Scanner;

public class test_2 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountDatabase accountDatabase = new AccountDatabase();

    public static void main(String[] args) {
        //System.out.println("Current working directory: " + System.getProperty("user.dir"));

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Check credentials");
            System.out.println("2. Block user");
            System.out.println("3. Unblock user");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    checkCredentials();
                    break;
                case 2:
                    blockUser();
                    break;
                case 3:
                    unblockUser();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void checkCredentials() {
        System.out.print("Enter login name: ");
        String loginName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMINISTRATOR, STUDENT, or ADVISOR): ");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());
        

        boolean valid = accountDatabase.checkCredentials(loginName, password, role);
        System.out.println("Credential is " + (valid ? "valid" : "invalid"));
    }

    private static void blockUser() {
        System.out.print("Enter user ID to block: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        accountDatabase.block(userID);
        System.out.println("User " + userID + " has been blocked.");
    }

    private static void unblockUser() {
        System.out.print("Enter user ID to unblock: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        accountDatabase.unblock(userID);
        System.out.println("User " + userID + " has been unblocked.");
    }
}