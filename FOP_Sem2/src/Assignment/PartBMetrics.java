package Assignment;

import java.util.*;
import java.io.*;

class PartBMetrics {
    protected String partitions;

    public PartBMetrics(String partitions) {
        this.partitions = partitions;
    }

    public String toString() {
        return partitions;
    }

    public static void main(String[] args) {
        String filePath = "extracted_log";

        try {
            List<PartBMetrics> assignments = NParser.parseAssignmentB(filePath);
            NParser.displayTable(assignments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class NParser {
    public static List<PartBMetrics> parseAssignmentB(String filePath) throws IOException {
        List<PartBMetrics> assignments = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
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
                                    assignments.add(new PartBMetrics(value));
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

    public static void displayTable(List<PartBMetrics> assignments) {
        System.out.println("+-----------------------+");
        System.out.println("| Partition  | Count    |");
        System.out.println("+-----------------------+");
        
        // Count occurrences of each partition type
        Map <String, Integer> partitionCount = new HashMap<>();
        for (PartBMetrics assignment : assignments) {
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
