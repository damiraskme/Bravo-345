

import java.io.*;
import java.util.ArrayList;

enum Role {
    ADMINISTRATOR,
    STUDENT,
    ADVISOR
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
            System.out.println("User " + userID + " has been blocked.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the accounts file.");
        }
    }
}
