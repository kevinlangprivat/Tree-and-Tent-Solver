package tools;

import java.io.*;
import java.util.*;

public class CsvReader {

    /**
     * Method to read csv files
     * @param fileName Name of file
     * @return Content of csv file as ArrayList
     */
    public List<List<String>> read_csv(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            System.out.println("Print_11: " + "Couldn't read csv");
            e.printStackTrace();
        }
        return records;
    }
}
