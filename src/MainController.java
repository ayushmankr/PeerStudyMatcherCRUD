package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.StudentModel;
import util.FileHandler;

import java.io.IOException;
import java.util.List;

public class MainController {
    private StudentModel studentModel;
    private ObservableList<Student> studentList;
    private ObservableList<String> matchResults;

    @FXML private TextField nameField;
    @FXML private ToggleGroup roleGroup;
    @FXML private RadioButton helperRadio;
    @FXML private RadioButton needyRadio;
    @FXML private TextField subjectsCanHelpField;
    @FXML private TextField subjectsNeedHelpField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> roleColumn;
    @FXML private TableColumn<Student, String> canHelpColumn;
    @FXML private TableColumn<Student, String> needHelpColumn;
    @FXML private ListView<String> matchResultsList;

    public void initialize() {
        studentModel = new StudentModel();
        studentList = FXCollections.observableArrayList();
        matchResults = FXCollections.observableArrayList();

        // Set up table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        canHelpColumn.setCellValueFactory(new PropertyValueFactory<>("subjectsCanHelp"));
        needHelpColumn.setCellValueFactory(new PropertyValueFactory<>("subjectsNeedHelp"));

        studentTable.setItems(studentList);
        matchResultsList.setItems(matchResults);

        // Load existing data
        try {
            List<Student> loadedStudents = FileHandler.loadStudents();
            studentModel.loadStudents(loadedStudents);
            studentList.setAll(studentModel.getAllStudents());
        } catch (IOException e) {
            showAlert("Error", "Failed to load student data: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddStudent() {
        try {
            String name = nameField.getText().trim();
            String role = helperRadio.isSelected() ? "Helper" : "Needy";
            String subjectsCanHelp = subjectsCanHelpField.getText().trim();
            String subjectsNeedHelp = subjectsNeedHelpField.getText().trim();

            // Validation
            if (name.isEmpty() || (subjectsCanHelp.isEmpty() && subjectsNeedHelp.isEmpty())) {
                showAlert("Validation Error", "Name and at least one subject field are required.");
                return;
            }

            if (roleGroup.getSelectedToggle() == null) {
                showAlert("Validation Error", "Please select a role (Helper or Needy).");
                return;
            }

            Student student = new Student(name, role, subjectsCanHelp, subjectsNeedHelp);
            studentModel.addStudent(student);
            studentList.setAll(studentModel.getAllStudents());

            // Clear fields
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "Failed to add student: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("Selection Error", "Please select a student to update.");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String role = helperRadio.isSelected() ? "Helper" : "Needy";
            String subjectsCanHelp = subjectsCanHelpField.getText().trim();
            String subjectsNeedHelp = subjectsNeedHelpField.getText().trim();

            // Validation
            if (name.isEmpty() || (subjectsCanHelp.isEmpty() && subjectsNeedHelp.isEmpty())) {
                showAlert("Validation Error", "Name and at least one subject field are required.");
                return;
            }

            int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
            Student updatedStudent = new Student(name, role, subjectsCanHelp, subjectsNeedHelp);
            studentModel.updateStudent(selectedIndex, updatedStudent);
            studentList.setAll(studentModel.getAllStudents());

            // Clear fields
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "Failed to update student: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("Selection Error", "Please select a student to delete.");
            return;
        }

        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        studentModel.deleteStudent(selectedIndex);
        studentList.setAll(studentModel.getAllStudents());
        clearFields();
    }

    @FXML
    private void handleMatchStudents() {
        matchResults.clear();
        List<Student> needyStudents = studentModel.getAllStudents().stream()
                .filter(s -> s.getRole().equalsIgnoreCase("Needy"))
                .toList();

        if (needyStudents.isEmpty()) {
            showAlert("No Needy Students", "There are no students marked as needing help.");
            return;
        }

        for (Student needy : needyStudents) {
            List<Student> helpers = studentModel.findHelpersForStudent(needy);
            if (helpers.isEmpty()) {
                matchResults.add(needy.getName() + ": No suitable helpers found.");
            } else {
                StringBuilder sb = new StringBuilder(needy.getName() + " can be helped by: ");
                for (Student helper : helpers) {
                    sb.append(helper.getName()).append(", ");
                }
                matchResults.add(sb.substring(0, sb.length() - 2));
            }
        }
    }

    @FXML
    private void handleSave() {
        try {
            FileHandler.saveStudents(studentModel.getAllStudents());
            showAlert("Success", "Student data saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to save student data: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoad() {
        try {
            List<Student> loadedStudents = FileHandler.loadStudents();
            studentModel.loadStudents(loadedStudents);
            studentList.setAll(studentModel.getAllStudents());
            showAlert("Success", "Student data loaded successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to load student data: " + e.getMessage());
        }
    }

    @FXML
    private void handleTableClick() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            nameField.setText(selectedStudent.getName());
            if (selectedStudent.getRole().equalsIgnoreCase("Helper")) {
                helperRadio.setSelected(true);
            } else {
                needyRadio.setSelected(true);
            }
            subjectsCanHelpField.setText(selectedStudent.getSubjectsCanHelp());
            subjectsNeedHelpField.setText(selectedStudent.getSubjectsNeedHelp());
        }
    }

    private void clearFields() {
        nameField.clear();
        roleGroup.selectToggle(null);
        subjectsCanHelpField.clear();
        subjectsNeedHelpField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}