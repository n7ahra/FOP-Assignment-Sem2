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

    public static int[] logReader() throws IOException {
  
        String logFile = "extracted_log";
        String start = "2022-06-01T01:02:35.148";
        String end = "2022-12-16T14:55:46.311";
        
        String line;
        int jobsCreated = 0;
        int jobsCompleted = 0;
        int jobsKilled=0;
        
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        while ((line = reader.readLine()) != null) {
            //keyword check to indicate complete,create,kill
            if (line.contains("sched: Allocate")) {
                String timestamp = timestamp(line);
                if (checkTimerange(timestamp, start, end)) {
                    jobsCreated++;
                }
            
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
    public static void displayTable (int jobsCreated, int jobsCompleted,int jobsKilled, String start, String end) {
        System.out.println("+----------------+-----------------+");
        System.out.println("| Jobs Allocated | " + String.format("%15d", jobsCreated) + " |");
        System.out.println("| Jobs Killed    | " + String.format("%15d", jobsKilled) + " |");
        System.out.println("| Jobs Completed | " + String.format("%15d", jobsCompleted) + " |");
        System.out.println("+----------------+-----------------+");
    }
}



