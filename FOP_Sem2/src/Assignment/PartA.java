/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author Nurhani Zahra
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {

    public static void main(String[] args) {
        // Define the log file name and time range
        String logFile = "extracted_log";
        String startTime = "2022-06-01T01:02:35.148";
        String endTime = "2022-12-16T14:55:46.311";

        try {
            // Analyze the log file and get the job counts
            int[] jobCounts = analyzeLogFile(logFile, startTime, endTime);
            int jobsCreated = jobCounts[0];
            int jobsCompleted = jobCounts[1];

            // Display the results
            displayResults(jobsCreated, jobsCompleted, startTime, endTime);
        } catch (IOException e) {
            // Handle potential IOExceptions from reading the file
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }

    // Method to analyze the log file and count job events within the specified time range
    private static int[] analyzeLogFile(String logFile, String startTime, String endTime) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        String line;
        int jobsCreated = 0;
        int jobsCompleted = 0;

        // Read each line of the log file
        while ((line = reader.readLine()) != null) {
            // Check if the line indicates a job creation
            if (line.contains("sched: Allocate JobId=")) {
                String timestamp = extractTimestamp(line);
                if (isWithinTimeRange(timestamp, startTime, endTime)) {
                    jobsCreated++;
                }
            // Check if the line indicates a job completion
            } else if (line.contains("_job_complete: JobId=")) {
                String timestamp = extractTimestamp(line);
                if (isWithinTimeRange(timestamp, startTime, endTime)) {
                    jobsCompleted++;
                }
            }
        }

        reader.close();
        return new int[]{jobsCreated, jobsCompleted};
    }

    // Method to extract the timestamp from a log entry
    private static String extractTimestamp(String line) {
        return line.substring(1, 24); // Extracts the first 24 characters which is the timestamp
    }

    // Method to check if a given timestamp is within the specified time range
    private static boolean isWithinTimeRange(String timestamp, String startTime, String endTime) {
        return timestamp.compareTo(startTime) >= 0 && timestamp.compareTo(endTime) <= 0;
    }

    // Method to display the results in a table
    private static void displayResults(int jobsCreated, int jobsCompleted, String startTime, String endTime) {
        System.out.println("Job Statistics from " + startTime + " to " + endTime);
        System.out.println("+----------------+-----------------+");
        System.out.println("| Jobs Created   | " + String.format("%15d", jobsCreated) + " |");
        System.out.println("| Jobs Completed | " + String.format("%15d", jobsCompleted) + " |");
        System.out.println("+----------------+-----------------+");
    }
}



