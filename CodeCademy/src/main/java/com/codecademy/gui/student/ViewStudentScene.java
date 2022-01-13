package com.codecademy.gui.student;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.registration.ViewRegistrationScene;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.student.StudentRepository;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ViewStudentScene extends GUIScene {
    private Scene viewStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Student selectedStudent;
    private final StudentRepository studentRepository;

    public ViewStudentScene(GUI gui, int sceneWidth, int sceneHeight, Student selectedStudent) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedStudent = selectedStudent;
        studentRepository = new StudentRepository();

        if (selectedStudent != null) {
            createScene();
            setScene(viewStudentScene);
        }
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewStudentPane = new VBox(15);

        ScrollPane selectedStudentRegistrationsList = new ScrollPane();

        viewStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Viewing Student: " + selectedStudent.getName());

        Button homeButton = new Button("Home");
        Button usersButton = new Button("Students");
        Button editSelectedUserButton = new Button("Edit Student");

        Label selectedStudentNameLabel = new Label("Name: " + selectedStudent.getName());
        Label selectedStudentEmailLabel = new Label("Email: " + selectedStudent.getEmail());
        Label selectedStudentAddressLabel = new Label("Address: " + selectedStudent.getAddress());
        Label selectedStudentCityLabel = new Label("City: " + selectedStudent.getCity());
        Label selectedStudentCountryLabel = new Label("Country: " + selectedStudent.getCountry());
        Label selectedStudentGenderLabel = new Label("Gender: " + selectedStudent.getGender());
        Label selectedStudentBirthdayLabel = new Label("Birthday: " + selectedStudent.getBirthdayAsString());

        Label selectedStudentFollowingCoursesLabel = new Label("Following Courses:");


        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        usersButton.setOnAction((event) -> {
            ((OverviewStudentsScene) getSceneObject("overviewStudentsScene")).resetScene();
            showScene("overviewStudentsScene");
        });

        editSelectedUserButton.setOnAction((event) -> {
            ((EditStudentScene) getSceneObject("editStudentScene")).resetScene(selectedStudent);
            showScene("editStudentScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewStudentPane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, usersButton, editSelectedUserButton);

        viewStudentPane.getChildren().addAll(selectedStudentNameLabel, selectedStudentEmailLabel, selectedStudentAddressLabel,
                selectedStudentCityLabel, selectedStudentCountryLabel, selectedStudentGenderLabel, selectedStudentBirthdayLabel,
                selectedStudentFollowingCoursesLabel, selectedStudentRegistrationsList);

        try {
            selectedStudentRegistrationsList.setContent(createSelectedStudentRegistrationsPane(studentRepository.getAllRegistrationsForStudent(selectedStudent)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Registrations connected to the selected Student into a VBox
    private VBox createSelectedStudentRegistrationsPane(ArrayList<Registration> registrations) {
        VBox studentListPane = new VBox(5);

        int index = 0;
        for (Registration registration : registrations) {
            // Panes
            HBox registrationInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label registrationCourseNameLabel = new Label(registration.getCourseName());

            // Event Handlers
            registrationInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewRegistrationScene) getSceneObject("viewRegistrationScene")).resetScene(registration);
                showScene("viewRegistrationScene");
            });

            // Appending
            registrationInfoRow.getChildren().addAll(indexLabel, registrationCourseNameLabel);
            studentListPane.getChildren().add(registrationInfoRow);

            index++;
        }

        return studentListPane;
    }

    public void resetScene(Student selectedStudent) {
        if (selectedStudent == null) return;

        this.selectedStudent = selectedStudent;

        createScene();
        setScene(viewStudentScene);
    }

}
