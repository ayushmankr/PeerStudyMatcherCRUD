package util;

import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_PATH = "data/students.txt";

    public static void saveStudents(List<Student> students) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // Create directory if it doesn't exist
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        }
    }

    public static List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return students;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromString(line));
            }
        }
        return students;
    }
}