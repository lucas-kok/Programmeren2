package com.codecademy.gui.student;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.certificate.ViewCertificateScene;
import com.codecademy.gui.registration.ViewRegistrationScene;
import com.codecademy.informationhandling.certificate.Certificate;
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

public class ViewStudentScene extends GUIScene {
    private Scene viewStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Student selectedStudent;
    private final StudentRepository studentRepository;

    public ViewStudentScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        studentRepository = new StudentRepository();

        // Not creating a scene, because when initializing the GUI no Student has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewStudentPane = new VBox(15);

        ScrollPane selectedStudentRegistrationsList = new ScrollPane();
        ScrollPane selectedStudentCertificatesList = new ScrollPane();

        viewStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Viewing Student: " + selectedStudent.getName());

        Button homeButton = new Button("Home");
        Button usersButton = new Button("Students");
        Button editSelectedStudentButton = new Button("Edit Student");

        Label selectedStudentNameLabel = new Label("Name: " + selectedStudent.getName());
        Label selectedStudentEmailLabel = new Label("Email: " + selectedStudent.getEmail());
        Label selectedStudentAddressLabel = new Label("Address: " + selectedStudent.getAddress());
        Label selectedStudentCityLabel = new Label("City: " + selectedStudent.getCity());
        Label selectedStudentCountryLabel = new Label("Country: " + selectedStudent.getCountry());
        Label selectedStudentGenderLabel = new Label("Gender: " + selectedStudent.getGender());
        Label selectedStudentBirthdayLabel = new Label("Birthday: " + selectedStudent.getBirthday());

        Label selectedStudentFollowingCoursesLabel = new Label("Following Courses:");
        Label selectedStudentCertificatesLabel = new Label("Certificates:");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        usersButton.setOnAction((event) -> {
            ((OverviewStudentsScene) getSceneObject("overviewStudentsScene")).resetScene();
            showScene("overviewStudentsScene");
        });

        editSelectedStudentButton.setOnAction((event) -> {
            ((EditStudentScene) getSceneObject("editStudentScene")).resetScene(selectedStudent);
            showScene("editStudentScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewStudentPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, usersButton, editSelectedStudentButton);

        viewStudentPane.getChildren().addAll(selectedStudentNameLabel, selectedStudentEmailLabel, selectedStudentAddressLabel,
                selectedStudentCityLabel, selectedStudentCountryLabel, selectedStudentGenderLabel, selectedStudentBirthdayLabel,
                selectedStudentFollowingCoursesLabel, selectedStudentRegistrationsList, selectedStudentCertificatesLabel, selectedStudentCertificatesList);

        try {
            selectedStudentRegistrationsList.setContent(createSelectedStudentRegistrationsPane(studentRepository.getAllRegistrationsForStudent(selectedStudent)));
            selectedStudentCertificatesList.setContent(createSelectedStudentCertificationsPane(studentRepository.getAllCertificatesForStudent(selectedStudent)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Registrations linked to a selected Student into a VBox
    private VBox createSelectedStudentRegistrationsPane(ArrayList<Registration> registrationsOfStudent) {
        VBox registrationsListPane = new VBox(5);

        int index = 0;
        for (Registration registration : registrationsOfStudent) {
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
            registrationsListPane.getChildren().add(registrationInfoRow);

            index++;
        }

        return registrationsListPane;
    }

    // Function that will convert a list of Certificates liked to a selected Student into a VBox
    private VBox createSelectedStudentCertificationsPane(ArrayList<Certificate> certificatesOfStudent) {
        VBox certificatesListPane = new VBox(5);

        int index = 0;
        for (Certificate certificate : certificatesOfStudent) {
            // Panes
            HBox certificateInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label certificateCourseNameLabel = new Label(certificate.getCourseName());
            Label informationDividerLabel = new Label("-");
            Label certificateScoreLabel = new Label(certificate.getScore() + "/10");

            // Event Handlers
            certificateInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewCertificateScene) getSceneObject("viewCertificateScene")).resetScene(certificate);
                showScene("viewCertificateScene");
            });

            // Appending
            certificateInfoRow.getChildren().addAll(indexLabel, certificateCourseNameLabel, informationDividerLabel, certificateScoreLabel);
            certificatesListPane.getChildren().add(certificateInfoRow);

            index++;
        }

        return certificatesListPane;
    }

    public void resetScene(Student selectedStudent) {
        if (selectedStudent == null) return;

        this.selectedStudent = selectedStudent;

        createScene();
        setScene(viewStudentScene);
    }

}
