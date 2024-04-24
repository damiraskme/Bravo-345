package Test;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


enum Role {
    ADMINISTRATOR,
    STUDENT,
    ADVISOR,
    FACULTY
}

public class AccountDatabase {
    final private String fileName = "../Bravo-345/doc/methods/accounts.csv";

    public boolean checkCredentials(String loginName, String password, Role role) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credential = line.trim().split(","); // Trim whitespace before splitting
    
                if (credential.length < 5) {
                    System.err.println("Invalid format in accounts.csv file: " + line);
                    continue;
                }
    
                String username = credential[1].trim(); // Trim whitespace from username
                String pass = credential[2].trim(); // Trim whitespace from password
                String userRole = credential[3].trim(); // Trim whitespace from role
                String status = credential[4].trim(); // Trim whitespace from status
    
                // Print the extracted fields for debugging
                System.out.println("Username: " + username + ", Password: " + pass + ", Role: " + userRole + ", Status: " + status);
    
                if (username.equals(loginName) && pass.equals(password) && userRole.equals(role.toString()) && status.equals("active")) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }
    
        return false;
    }

    public void block(int userID) {
        ArrayList<String[]> credentials = new ArrayList<>();
        StringBuilder newFileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }
            for (int i = 0; i < credentials.size(); i++) {
                if (credentials.get(i)[0].equals(String.valueOf(userID))) {
                    credentials.get(i)[4] = "blocked";
                }
            }
            for (int i = 0; i < credentials.size(); i++) {
                newFileContent.append(String.join(",", credentials.get(i))).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role provided.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }
    
        // Write the updated contents back to the accounts.csv file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newFileContent.toString());
            System.out.println("User " + userID + " has been blocked.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
        }
    }
    public void unblock(int userID) {
        ArrayList<String[]> credentials = new ArrayList<>();
        StringBuilder newFileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }
            for(int i = 0; i < credentials.size(); i++) {
                if (credentials.get(i)[0].equals(String.valueOf(userID))) {
                    credentials.get(i)[4] = "active";
                }
            }
            for(int i = 0; i < credentials.size(); i++) {
                newFileContent.append(String.join(",", credentials.get(i))).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role provided.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newFileContent.toString());
            System.out.println("User " + userID + " has been unblocked.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
        }

    }
    
    public StudentAccount getStudentAccount(String loginName, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credential = line.trim().split(",");
    
                if (credential.length < 5) {
                    System.err.println("Invalid format in accounts.csv file: " + line);
                    continue;
                }
    
                int id = Integer.parseInt(credential[0].trim());
                String username = credential[1].trim();
                String pass = credential[2].trim();
                String roleStr = credential[3].trim(); // Store the role as a string
                String status = credential[4].trim();
    
                if (username.equals(loginName) && pass.equals(password) && roleStr.equalsIgnoreCase("STUDENT") && status.equals("active")) {
                    Role role = Role.valueOf(roleStr.toUpperCase()); // Convert the role string to the Role enum
                    return new StudentAccount(id, username, pass, role, status);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }
    
        return null;
    }
   

    public void changePassword(Session session, StudentAccount account) throws Exception {
        List<String[]> credentials = new ArrayList<>();
        StringBuilder newFileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }
            for (int i = 0; i < credentials.size(); i++) {
                if (credentials.get(i)[0].equals(String.valueOf(account.getId(session)))) {
                    credentials.set(i, new String[]{
                            String.valueOf(account.getId(session)),
                            account.getLogin(session),
                            account.getPass(session),
                            account.getRole(session).toString(),
                            account.getStatus(session)
                    });
                }
            }
            for (String[] credential : credentials) {
                newFileContent.append(String.join(",", credential)).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newFileContent.toString());
            System.out.println("Student account has been updated.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
        }
    }
   
    
   
    
   
    public StudentAccount createAccount(Session session, String loginName, String password) throws DuplicateRecordException, AccessViolationException, ExpiredSessionException {
        if (session == null || !session.isActive()) {
            throw new ExpiredSessionException("Session has expired.");
        }

        if (session.getRole() != Role.ADMINISTRATOR) {
            throw new AccessViolationException("Access denied. Only administrators can create accounts.");
        }

        if (checkIfLoginNameExists(loginName)) {
            throw new DuplicateRecordException("An account with the provided login name already exists.");
        }

        int newId = getNextAvailableId();
        String newStatus = "active";
        Role newRole = Role.STUDENT;

        List<String[]> credentials = new ArrayList<>();
        StringBuilder newFileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }

            credentials.add(new String[]{
                    String.valueOf(newId),
                    loginName,
                    password,
                    newRole.toString(),
                    newStatus
            });

            for (String[] credential : credentials) {
                newFileContent.append(String.join(",", credential)).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newFileContent.toString());
            System.out.println("New student account has been created.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
        }
        return new StudentAccount(loginName, password);
    }

    private boolean checkIfLoginNameExists(String loginName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credential = line.trim().split(",");
                if (credential.length >= 2 && credential[1].trim().equals(loginName)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }
        return false;
    }

    private int getNextAvailableId() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credential = line.trim().split(",");
                if (credential.length > 0) {
                    int id = Integer.parseInt(credential[0].trim());
                    maxId = Math.max(maxId, id);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
        }
        return maxId + 1;
    }
    public void viewAccount(Session session) {
        if (session == null || !session.isActive()) {
            System.out.println("Error: Session has expired or is invalid.");
            return;
        }
    
        if (session.getRole() == Role.STUDENT) {
            if (session.isActive()) {
                Session.StudentAccount account = session.studentAcc;
                System.out.println("Student Account Details:");
                System.out.println("ID: " + studentAccount.getId(session));
                System.out.println("Username: " + studentAccount.getUsername());
                System.out.println("Password: " + studentAccount.getPassword());
                System.out.println("Role: " + studentAccount.getRole());
                System.out.println("Status: " + studentAccount.getStatus());
            } else {
                System.out.println("Error: Student account not initialized in the session.");
            }
        
        } else {
            System.out.println("Error: Invalid account role.");
        }
    }
    public boolean passwordCheck(String password) {
        // Password requirements:
        // 1. Should have at least 8 characters
        // 2. Should contain at least one uppercase letter
        // 3. Should contain at least one lowercase letter
        // 4. Should contain at least one digit
        // 5. Should contain at least one special character (!@#$%^&*()_+)

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*()_+".contains(Character.toString(c))) {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    public static void passwordCheckInstruction() {
        System.out.println("Password requirements:");
        System.out.println("1. Should have at least 8 characters");
        System.out.println("2. Should contain at least one uppercase letter");
        System.out.println("3. Should contain at least one lowercase letter");
        System.out.println("4. Should contain at least one digit");
        System.out.println("5. Should contain at least one special character (!@#$%^&*()_+)");
    }
    public boolean changeLoginName(Session session, String newLoginName) {
        if (session == null || !session.isActive()) {
            System.err.println("Session has expired or is invalid.");
            return false;
        }
    
        if (session.getRole() != Role.ADMINISTRATOR) {
            System.err.println("Access denied. Only administrators can change login names.");
            return false;
        }
    
        if (checkIfLoginNameExists(newLoginName)) {
            System.err.println("The provided login name is already in use.");
            return false;
        }
    
        List<String[]> credentials = new ArrayList<>();
        StringBuilder newFileContent = new StringBuilder();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }
    
            for (int i = 0; i < credentials.size(); i++) {
                String[] credential = credentials.get(i);
                if (credential.length >= 2 && credential[1].trim().equals((session.getLogin()))) {
                    credential[1] = newLoginName; // Update the login name
                    credentials.set(i, credential);
                }
            }
    
            for (String[] credential : credentials) {
                newFileContent.append(String.join(",", credential)).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
            return false;
        } catch (IOException e) {
            System.err.println("An error occurred while reading the accounts file.");
            return false;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newFileContent.toString());
            System.out.println("Login name has been updated successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
            return false;
        }
    
        return true;
    }
    
    
    

    

}

