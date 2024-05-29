/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fop_sem2;

import java.io.*;
import java.text.*;
import java.util.*;

//	Average execution time of the jobs submitted to UMHPC
class Job {
    String id;
    Date start;
    Date end;

    public Job(String id, Date start) {
        this.id = id;
        this.start = start;
    }

    long getDuration() {
        return (end.getTime() - start.getTime()) / 1000;
    }
}

public class PartDTime {
    public static void main(String[] args) {
        Map<String, Job> jobs = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long totalDuration = 0;
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("G:\\桌面\\FOP Assignment\\sem2\\extracted_log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(" ");
                    if (parts.length < 3) {
                        continue; // Skip lines that don't have enough parts
                    }
                    Date timestamp = formatter.parse(parts[0].substring(1, parts[0].length() - 1));
                    String event = parts[1];
                    String[] idParts = parts[2].split("=");
                    if (idParts.length < 2) {
                        continue; // Skip lines that don't have a valid id part
                    }
                    String id = idParts[1];

                    if (event.equals("_slurm_rpc_submit_batch_job:")) {
                        jobs.put(id, new Job(id, timestamp));
                    } else if (event.equals("_job_complete:")) {
                        Job job = jobs.get(id);
                        if (job != null) {
                            job.end = timestamp;
                            totalDuration += job.getDuration();
                            count++;
                        }
                    }
                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error processing line: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        if (count > 0) {
            double averageDuration = (double) totalDuration / count;
            System.out.println("Average execution time: " + averageDuration + " seconds");
        } else {
            System.out.println("No completed jobs found.");
        }
    }
}
