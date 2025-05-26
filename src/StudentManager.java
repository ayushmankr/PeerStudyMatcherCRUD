import java.io.*;
import java.util.*;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void updateStudent(int index, Student newStudent) {
        if (index >= 0 && index < students.size()) {
            students.set(index, newStudent);
        }
    }

    public void deleteStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Student s : students) {
            writer.write(s.getClass().getSimpleName() + "," + s.getName() + "," +
                         String.join(";", s.getSubjectsCanHelp()) + "," +
                         String.join(";", s.getSubjectsNeedHelp()));
            writer.newLine();
        }
        writer.close();
    }

    public void loadFromFile(String filename) throws IOException {
        students.clear();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String type = parts[0];
            String name = parts[1];
            ArrayList<String> canHelp = new ArrayList<>(Arrays.asList(parts[2].split(";")));
            ArrayList<String> needHelp = new ArrayList<>(Arrays.asList(parts[3].split(";")));
            if (type.equals("HelperStudent")) {
                students.add(new HelperStudent(name, canHelp));
            } else {
                students.add(new NeedyStudent(name, needHelp));
            }
        }
        reader.close();
    }
}