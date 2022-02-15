package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Timer {
    long startTime;
    long stopTime;
    long result;

    public Timer() {
        startTime = System.nanoTime();
    }

    public void start_timer() {
        startTime = System.nanoTime();
    }

    public void stop_timer(String name, int puzzle_id, int backtracking) {
        stopTime = System.nanoTime();
        // System.out.println("Print_12: Time of Service: " + (stopTime - startTime) + " NS");
        write_timer(name, puzzle_id, backtracking);
    }

    private void write_timer(String name,int puzzle_id, int backtracking) {
        try {
            File myObj = new File("src/main/resources/evaluation.txt");
            if (myObj.createNewFile()) {
                System.out.println("Print_13: " + "File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("Print_14: " + "An error occurred.");
            e.printStackTrace();
        }
        try {
            result = (stopTime - startTime)/(1000*1000);
            String write = (puzzle_id + ", " + backtracking + ", " + Long.toString(result) + ", " +name + "\n");
            Files.write(Paths.get("src/main/resources/evaluation.txt"), write.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
