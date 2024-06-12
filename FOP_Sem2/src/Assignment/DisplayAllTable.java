/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author Nurhani Zahra
 */
import Assignment.PartA;
import Assignment.PartB;
import Assignment.PartC;
import Assignment.PartD;
import Assignment.PartE;
import static Assignment.PartE.displayTable;
import java.io.IOException;
import java.util.List;
public class DisplayAllTable {
    public static void main(String[] args) {
        PartA a=new PartA();
        NParser b=new NParser();
        PartC c=new PartC();
        PartD d=new PartD();
        PartE E=new PartE();
        
        String logFile = "extracted_log";
        String start = "2022-06-01T01:02:35.148";
        String end = "2022-12-16T14:55:46.311";
        
        

        try {
            //initialize array to display
            int[] statistics = PartA.logReader();
            int jobsCreated = statistics[0];
            int jobsCompleted = statistics[1];
            int jobsKilled=statistics[2];
            
            int[] submissionFrequency = new int[24]; 
            int[] completionFrequency = new int[24]; 
            int peakSubmissionTime = -1; 
            int peakCompletionTime = -1; 
            int leastSubmissionTime = -1; 
            int leastCompletionTime = -1; 
            
            System.out.println("-------------SLURM Controller Metrics Extraction-------------");

            //PART A
            System.out.println("a) Number of jobs created/completed within a given time range");
            a.displayTable(jobsCreated, jobsCompleted, jobsKilled, start, end);
            System.out.println( );
            
            //PART B
            System.out.println("b) Number of jobs by partitions");
            List<PartB> assignments = NParser.parseAssignmentB(logFile);
            // Display the assignments using NParser's displayTable method
            b.displayTable(assignments);
            System.out.println( );
            
            //PART C
            System.out.println("c) Number of jobs causing error and the corresponding user");
            c.logAnalyzer();
            System.out.println( );
            
            //PART D
            System.out.println("d) Average excution time of jobs submitted to UMHPC");
            d.displayTable();
            System.out.println( );
            
            //PART E
            System.out.println("e)Frequency of jobs submitted and completed. ");
            E.Analyzer();

            
        } catch (IOException e) {
            System.out.println("Problem with file input.");
        }
    }
}
