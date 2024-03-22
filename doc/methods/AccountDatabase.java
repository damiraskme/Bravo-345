

import java.io.*;
import java.util.ArrayList;

enum Role {
    ADMINISTRATOR,
    STUDENT,
    ADVISOR
}

public class AccountDatabase {
    final private String fileName = "accounts.csv";

    public boolean checkCredentials(String loginName, String password, Role role) {
        ArrayList<String[]> credentials = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                credentials.add(line.split(","));
            }
            for(int i = 0; i < credentials.size(); i++) {
                if (credentials.get(i)[1].equals(loginName)) {
                    if(credentials.get(i)[2].equals(password) && credentials.get(i)[3].equals(role.toString()) && credentials.get(i)[4].equals("active")) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Database file was not found.");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role provided.");
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
            for(int i = 0; i < credentials.size(); i++) {
                if (credentials.get(i)[0].equals(String.valueOf(userID))) {
                    credentials.get(i)[4] = "blocked";
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv"))) {
            writer.write(newFileContent.toString());
            System.out.println("Writing to temporary file."); // Debug log
        } catch (IOException e) {
            System.out.println("err");
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
            System.out.println("Writing to temporary file."); // Debug log
        } catch (IOException e) {
            System.out.println("err");
        }
    }
}
