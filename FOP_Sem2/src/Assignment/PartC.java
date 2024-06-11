/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retake2024;

/**
 *
 * @author dhiya
 */
import java.util.*;
import java.io.*;

public class PartC {
    public static void main(String[] args) {
        String logFilePath = "extracted_log"; // Path to your log file

        int errorCount = 0;
        String[] errorUsers = new String[1000]; // Array to store users, assuming max 1000 users for simplicity
        int[] errorCounts = new int[1000]; // Array to store error counts per user
        int userCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("error: This association")) {
                    errorCount++;
                    String user = extractUserFromLogLine(line);

                    // Find if the user already exists in the array
                    int userIndex = -1;
                    for (int i = 0; i < userCount; i++) {
                        if (errorUsers[i].equals(user)) {
                            userIndex = i;
                            break;
                        }
                    }

                    // If user exists, increment their count, otherwise add new user
                    if (userIndex != -1) {
                        errorCounts[userIndex]++;
                    } else {
                        errorUsers[userCount] = user;
                        errorCounts[userCount] = 1;
                        userCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the results in table format
        System.out.printf("%-20s | %-15s%n", "User", "Number of Errors");
        System.out.println("---------------------|----------------");
        for (int i = 0; i < userCount; i++) {
            System.out.printf("%-20s | %-15d%n", errorUsers[i], errorCounts[i]);
        }
        System.out.println("---------------------|----------------");
        System.out.printf("%-20s | %-15d%n", "Total Errors", errorCount);
    }

    // Extract user information from the log line 
    private static String extractUserFromLogLine(String logLine) {
        // Assuming the user information is present in the log line and extract it
        int startIndex = logLine.indexOf("user=") + 5;
        int endIndex = logLine.indexOf(" ", startIndex);
        if (startIndex > 4) {
            if (endIndex == -1) { // Handle case where user is at the end of the line
                return logLine.substring(startIndex).replace("'", "").replace(",", "").trim();
            } else {
                return logLine.substring(startIndex, endIndex).replace("'", "").replace(",", "").trim();
            }
        }
        return "unknown";
    }

}
