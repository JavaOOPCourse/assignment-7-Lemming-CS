import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.*;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();


    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader("input/students.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");
                if (parts.length != 2 || parts[0].trim().isEmpty()) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }
                try {
                    double score = Double.parseDouble(parts[1]);
                    if (score > 100 || score < 0) {
                        throw new InvalidScoreException("Score must be between 0 to 100! Invalid data: " + line);
                    }
                    Student s = new Student(parts[0], score);
                    students.add(s);
                    System.out.println(line);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }
                catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        if (students.isEmpty()) {
            averageScore = 0;
            highestStudent = null;
            return;
        }

        students.sort((s1, s2) -> Double.compare(s2.getScore(), s1.getScore()));

        double sum = 0;
        for (Student s : students) {
            sum += s.getScore();
        }

        averageScore = sum / students.size();
        highestStudent = students.get(0);
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/report.txt"))) {
            if (students.isEmpty()) {
                writer.write("No valid student data found.");
                writer.newLine();
                return;
            }

            writer.write("Average: " + averageScore);
            writer.newLine();

            writer.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
            writer.newLine();

            writer.write("Sorted Students:");
            writer.newLine();

            for (Student s : students) {
                writer.write(s.getName() + " - " + s.getScore());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();
        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception{
    public InvalidScoreException(String message) {
        super(message);
    }
}
// class Student (name, score)
class Student {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }
    public double getScore() {
        return this.score;
    }
    public String getName() {
        return this.name;
    }
}