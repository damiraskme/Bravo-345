import java.util.InputMismatchException;
import java.util.Scanner;

public class test_2 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountDatabase accountDatabase = new AccountDatabase();
    private static Session adminSession;


    public static void main(String[] args) {
        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Check credentials");
            System.out.println("2. Block user");
            System.out.println("3. Unblock user");
            System.out.println("4. View student account");
            System.out.println("5. Create student account");
            System.out.println("6. Change student password");
            System.out.println("7. Change login name");
            System.out.println("8. Exit");



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
                    viewAccount();
                    break;
                case 5:
                    createAccount();
                    break;
                case 6:
                    changePassword();
                    break;
                case 7:
                    changeLoginName();
                    break;
              
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static boolean passwordCheck(String password) {
        return AccountDatabase.passwordCheck(password);
    }

    private static void passwordCheckInstruction() {
        AccountDatabase.passwordCheckInstruction();
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
        try {
            int userID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            accountDatabase.block(userID);
            //System.out.println("User " + userID + " has been blocked.");
        } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid user ID (numbers only).");
                scanner.nextLine(); // Consume the incorrect input to avoid an infinite loop
        }
        
    }

    private static void unblockUser() {
        System.out.print("Enter user ID to unblock: ");
        try {
            int userID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            accountDatabase.unblock(userID);
            //System.out.println("User " + userID + " has been unblocked.");
        } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid user ID (numbers only).");
                scanner.nextLine(); // Consume the incorrect input to avoid an infinite loop
        }
    }
    private static void viewAccount() { 
        System.out.print("Enter student login name: ");
        String loginName = scanner.nextLine();
        System.out.print("Enter student password: ");
        String password = scanner.nextLine();

        StudentAccount studentAccount = accountDatabase.getStudentAccount(loginName, password);
        if (studentAccount == null) {
            System.out.println("Invalid student credentials. (Or this is not a student account or the student has been blocked.)");
            return;
        }
        System.out.println("Current student account details:");
        System.out.println("ID: " + studentAccount.getId());
        System.out.println("Username: " + studentAccount.getUsername());
        System.out.println("Password: " + studentAccount.getPassword());
        System.out.println("Role: " + studentAccount.getRole());
        System.out.println("Status: " + studentAccount.getStatus());

        
    
    }
    
    private static void createAccount() { 
        // create an admin account adminsession
        
        adminSession = new Session(Role.ADMINISTRATOR, new SessionManager()); // Initialize adminSession

        adminSession.role=Role.ADMINISTRATOR; // for testing
        if (adminSession == null || adminSession.role != Role.ADMINISTRATOR) {
            System.out.println("Access denied. Only administrators can create accounts.");
            return;
        }

        System.out.print("Enter new login name: ");
    String loginName = scanner.nextLine();

    passwordCheckInstruction(); // Display password requirements
    System.out.print("Enter new password: ");
    String password = scanner.nextLine();

    while (!passwordCheck(password)) { // Validate password
        System.out.println("Invalid password. Please try again.");
        passwordCheckInstruction(); // Display password requirements again
        System.out.print("Enter new password: ");
        password = scanner.nextLine();
    }

    try {
        accountDatabase.createAccount(adminSession, loginName, password);
        System.out.println("New account created successfully.");
    } catch (DuplicateRecordException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (AccessViolationException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (ExpiredSessionException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }
 
    private static void changePassword() { 
        System.out.print("Enter student login name: ");
        String loginName = scanner.nextLine();
        System.out.print("Enter student password: ");
        String password = scanner.nextLine();

        StudentAccount studentAccount = accountDatabase.getStudentAccount(loginName, password);
        if (studentAccount == null) {
            System.out.println("Invalid student credentials. (Or this is not a student account or the student has been blocked.)");
            return;
        }
       

        System.out.print("Enter new password (or press Enter to keep the current one): ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            studentAccount.setPassword(newPassword);
        }

        accountDatabase.changePassword(studentAccount);
    
    }
    private static void changeLoginName() { // not just student
        System.out.print("Enter administrator login name: ");
        String loginName = scanner.nextLine();
        System.out.print("Enter administrator password: ");
        String password = scanner.nextLine();
    
        StudentAccount studentAccount = accountDatabase.getStudentAccount(loginName, password);
        if (studentAccount != null) {
            System.out.println("This is a student account. Students cannot change their login names.");
            return;
        }
    
        Session session = new Session(Role.ADMINISTRATOR, new SessionManager());
        session.studentAccount = studentAccount;
    
        System.out.print("Enter new login name: ");
        String newLoginName = scanner.nextLine();
    
        if (accountDatabase.changeLoginName(session, newLoginName)) {
            System.out.println("Login name changed successfully.");
        } else {
            System.out.println("Failed to change login name.");
        }
    }
    
    

} 
