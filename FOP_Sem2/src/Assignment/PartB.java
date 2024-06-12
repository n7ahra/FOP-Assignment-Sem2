package Assignment;

import java.util.*;
import java.io.*;

class PartB {
    protected String partitions;

    public PartB(String partitions) {
        this.partitions = partitions;
    }

    PartB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toString() {
        return partitions;
    }

    /*public static void main(String[] args) {
    String logFile = "extracted_log";
    
    try {
    List<PartB> assignments = NParser.parseAssignmentB(logFile);
    NParser.displayTable(assignments);
    } catch (IOException e) {
    e.printStackTrace();
    }
    }*/
}


class NParser {
    public static List<PartB> parseAssignmentB(String logFile) throws IOException {
        List<PartB> assignments = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(logFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        String[] keyValue = part.split("=", 2);
                        if (keyValue.length == 2) {
                            String key = keyValue[0];
                            String value = keyValue[1];

                            switch (key) {
                                case "Partition":
                                    assignments.add(new PartB(value));
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return assignments;
    }

    public static void displayTable(List<PartB> assignments) {
        System.out.println("+-----------------------+");
        System.out.println("| Partition  | Count    |");
        System.out.println("+-----------------------+");
        
        // Count occurrences of each partition type
        Map <String, Integer> partitionCount = new HashMap<>();
        for (PartB assignment : assignments) {
            String partition = assignment.toString();
            partitionCount.put(partition, partitionCount.getOrDefault(partition, 0) + 1);
        }
        
        // Display partition counts in the table
        for (Map.Entry<String, Integer> entry : partitionCount.entrySet()) {
            String partition = entry.getKey();
            int count = entry.getValue();
            System.out.printf("| %-10s | %-8d |\n", partition, count);
        }
        
        System.out.println("+-----------------------+");
    }

    
}
