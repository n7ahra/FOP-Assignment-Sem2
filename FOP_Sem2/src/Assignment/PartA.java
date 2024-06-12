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
        String logFile = "extracted_log";
        String start = "2022-06-01T01:02:35.148";
        String end = "2022-12-16T14:55:46.311";

        try {
            //initialize array to display
            int[] jobCounts = logReader(logFile, start, end);
            int jobsCreated = jobCounts[0];
            int jobsCompleted = jobCounts[1];
            int jobsKilled=jobCounts[2];

            //call display method
            outcomeTable(jobsCreated, jobsCompleted, jobsKilled, start, end);
        } catch (IOException e) {
            System.out.println("Problem with file input.");
        }
    }

    
    private static int[] logReader(String logFile, String start, String end) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        String line;
        int jobsCreated = 0;
        int jobsCompleted = 0;
        int jobsKilled=0;

        while ((line = reader.readLine()) != null) {
            //keyword check to indicate
            if (line.contains("sched: Allocate")) {
                String timestamp = timestamp(line);
                if (checkTimerange(timestamp, start, end)) {
                    jobsCreated++;
                }
            // Check if the line indicates a job completion
            } else if (line.contains("_job_complete: JobId=")) {
                String timestamp = timestamp(line);
                if (checkTimerange(timestamp, start, end)) {
                    jobsCompleted++;
                }
            }else if(line.contains("_slurm_rpc_kill_job")){
                String timestamp = timestamp(line);
                if (checkTimerange(timestamp, start, end)) {
                    jobsKilled++;
                }
            }
        }

        reader.close();
        return new int[]{jobsCreated, jobsCompleted,jobsKilled};
    }

    // method to extract timestamp
    private static String timestamp (String line) {
        return line.substring(1, 24); // first 24 characters which is the timestamp
    }

    // method to check if the keyword is within the time range
    private static boolean checkTimerange(String timestamp, String startTime, String endTime) {
        return timestamp.compareTo(startTime) >= 0 && timestamp.compareTo(endTime) <= 0;
    }

    //method to display results
    private static void outcomeTable (int jobsCreated, int jobsCompleted,int jobsKilled, String startTime, String endTime) {
        System.out.println("+----------------+-----------------+");
        System.out.println("| Jobs Allocated | " + String.format("%15d", jobsCreated) + " |");
         System.out.println("| Jobs Killed    | " + String.format("%15d", jobsKilled) + " |");
        System.out.println("| Jobs Completed | " + String.format("%15d", jobsCompleted) + " |");
        System.out.println("+----------------+-----------------+");
    }
}



