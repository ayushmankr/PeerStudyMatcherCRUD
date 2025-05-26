import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
import java.io.*;

public class PeerStudyGUI extends Application {
    StudentManager manager = new StudentManager();

    @Override
    public void start(Stage stage) {
        TextField nameField = new TextField();
        TextField canHelpField = new TextField();
        TextField needHelpField = new TextField();

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton helperBtn = new RadioButton("Helper");
        RadioButton needyBtn = new RadioButton("Needy");
        helperBtn.setToggleGroup(roleGroup);
        needyBtn.setToggleGroup(roleGroup);

        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button saveBtn = new Button("Save");
        Button loadBtn = new Button("Load");

        ListView<Student> listView = new ListView<>();
        listView.getItems().addAll(manager.getStudents());

        addBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            ArrayList<String> canHelp = new ArrayList<>(Arrays.asList(canHelpField.getText().split(",")));
            ArrayList<String> needHelp = new ArrayList<>(Arrays.asList(needHelpField.getText().split(",")));
            if (helperBtn.isSelected()) {
                manager.addStudent(new HelperStudent(name, canHelp));
            } else if (needyBtn.isSelected()) {
                manager.addStudent(new NeedyStudent(name, needHelp));
            }
            listView.getItems().setAll(manager.getStudents());
        });

        updateBtn.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                String name = nameField.getText().trim();
                ArrayList<String> canHelp = new ArrayList<>(Arrays.asList(canHelpField.getText().split(",")));
                ArrayList<String> needHelp = new ArrayList<>(Arrays.asList(needHelpField.getText().split(",")));
                if (helperBtn.isSelected()) {
                    manager.updateStudent(selectedIndex, new HelperStudent(name, canHelp));
                } else if (needyBtn.isSelected()) {
                    manager.updateStudent(selectedIndex, new NeedyStudent(name, needHelp));
                }
                listView.getItems().setAll(manager.getStudents());
            }
        });

        deleteBtn.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                manager.deleteStudent(selectedIndex);
                listView.getItems().setAll(manager.getStudents());
            }
        });

        saveBtn.setOnAction(e -> {
            try {
                manager.saveToFile("students.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        loadBtn.setOnAction(e -> {
            try {
                manager.loadFromFile("students.txt");
                listView.getItems().setAll(manager.getStudents());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, nameField, canHelpField, needHelpField, helperBtn, needyBtn,
                             new HBox(10, addBtn, updateBtn, deleteBtn),
                             new HBox(10, saveBtn, loadBtn), listView);
        root.setStyle("-fx-padding: 15;");
        stage.setScene(new Scene(root, 500, 600));
        stage.setTitle("Peer Study Matcher - CRUD GUI");
        stage.show();
    }
}