/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fop_sem2;

/**
 *
 * @author Nurhani Zahra
 */
import java.io.*;
public class FOP_Sem2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = "C:\\Users\\WAN QISTINA DAMIA\\OneDrive\\Documents\\Damiens Folder\\FOP\\extracted_log";

        BufferedReader reader = null;
        int[] submissionFrequency = new int[24]; 
        int[] completionFrequency = new int[24]; 
        int peakSubmissionTime = -1; 
        int peakCompletionTime = -1; 
        int leastSubmissionTime = -1; 
        int leastCompletionTime = -1; 
        int maxSubmissions = Integer.MIN_VALUE; 
        int maxCompletions = Integer.MIN_VALUE; 
        int minSubmissions = Integer.MAX_VALUE; 
        int minCompletions = Integer.MAX_VALUE; 

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 0) {
                    String timestamp = parts[0];
                    String[] timeParts = timestamp.split("T");
                    if (timeParts.length > 1) {
                        String time = timeParts[1];
                        String hour = time.split(":")[0];
                        int hourIndex = Integer.parseInt(hour);
                        if (line.contains("_slurm_rpc_submit_batch_job")) {
                            submissionFrequency[hourIndex]++;
                            if (submissionFrequency[hourIndex] > maxSubmissions) {
                                maxSubmissions = submissionFrequency[hourIndex];
                                peakSubmissionTime = hourIndex;
                            }
                            if (submissionFrequency[hourIndex] < minSubmissions) {
                                minSubmissions = submissionFrequency[hourIndex];
                                leastSubmissionTime = hourIndex;
                            }
                        } else if (line.contains("_job_complete")) {
                            completionFrequency[hourIndex]++;
                            if (completionFrequency[hourIndex] > maxCompletions) {
                                maxCompletions = completionFrequency[hourIndex];
                                peakCompletionTime = hourIndex;
                            }
                            if (completionFrequency[hourIndex] < minCompletions) {
                                minCompletions = completionFrequency[hourIndex];
                                leastCompletionTime = hourIndex;
                            }
                        }
                    }
                }
            }

            // Print peak and least submission times
            System.out.println("PEAK AND LEAST SUBMISSION TIMES");
            printPeakAndLeastTimes(submissionFrequency, "Job Submission Count", peakSubmissionTime, leastSubmissionTime);

            // Print peak and least completion times
            System.out.println("\n\nPEAK AND LEAST COMPLETION TIMES");
            printPeakAndLeastTimes(completionFrequency, "Job Completion Count", peakCompletionTime, leastCompletionTime);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //To print peak and least times
    private static void printPeakAndLeastTimes(int[] frequency, String label, int peakTime, int leastTime) {
        System.out.println(label + ":");
        for (int i = 0; i < frequency.length; i++) {
            int nextHourIndex = (i + 1) % 24; // Wrap around for 24-hour format
            System.out.println(formatHourRange(i, nextHourIndex) + ": " + frequency[i] + " jobs");
        }
        System.out.println("Peak time is between " + formatHourRange(peakTime, (peakTime + 1) % 24) + " with " + frequency[peakTime] + " job submissions.");
        System.out.println("Least time is between " + formatHourRange(leastTime, (leastTime + 1) % 24) + " with " + frequency[leastTime] + " job submissions.");
    }

    //Formatting hour range
    private static String formatHourRange(int startHour, int endHour) {
        return String.format("%02d00H-%02d00H", startHour, endHour);
    }
    
}
