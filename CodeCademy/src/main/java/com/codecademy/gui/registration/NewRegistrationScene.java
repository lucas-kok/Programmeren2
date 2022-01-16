package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.validators.RegistrationInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;

public class NewRegistrationScene extends GUIScene {
    private Scene newRegistrationScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final RegistrationInformationValidator registrationInformationValidator;
    private final RegistrationRepository registrationRepository;

    public NewRegistrationScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        registrationInformationValidator = new RegistrationInformationValidator();
        registrationRepository = new RegistrationRepository();

        createScene();
        setScene(newRegistrationScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox newRegistrationPane = new VBox(15);

        newRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Create new Registration");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label registrationStudentEmailLabel = new Label("Student Email:");
        TextField registrationStudentEmailInput = new TextField();

        Label registrationCourseNameLabel = new Label("Course Name:");
        TextField registrationCourseNameInput = new TextField();

        Button createRegistrationButton = new Button("Create");
        Label messageLabel = new Label("");

        // Styling
        newRegistrationScene.setUserAgentStylesheet("/style.css");
        headerPane.setId("header");
        titleLabel.setId("title");
        navigationPane.setId("navigation");
        createRegistrationButton.setId("actionButton");
        messageLabel.setId("errorMessage");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        createRegistrationButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!registrationStudentEmailInput.getText().isBlank() && !registrationCourseNameInput.getText().isBlank()) {

                String studentEmail = registrationStudentEmailInput.getText();
                String courseName = registrationCourseNameInput.getText();

                String response = null;
                try {
                    response = registrationInformationValidator.validateNewRegistration(studentEmail, courseName);

                    messageLabel.setId("errorMessage");
                    messageLabel.setText(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if (response.isBlank()) { // No errors, all inputs are valid
                    // Create new Registration
                    try {
                        LocalDate now = LocalDate.now();
                        String[] registrationDatePieces = now.toString().split("-");

                        registrationRepository.createRegistration(studentEmail, courseName, registrationDatePieces);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Clearing all fields
                    registrationStudentEmailInput.clear();
                    registrationCourseNameInput.clear();

                    messageLabel.setId("goodMessage");
                    messageLabel.setText("\nThe Registration for '" + studentEmail.toLowerCase() + "' has successfully been created!");
                }

            } else {
                messageLabel.setId("errorMessage");
                messageLabel.setText("\nPlease fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(newRegistrationPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton);

        newRegistrationPane.getChildren().addAll(registrationStudentEmailLabel, registrationStudentEmailInput,
                registrationCourseNameLabel, registrationCourseNameInput, createRegistrationButton, messageLabel);

    }

    public void resetScene() {
        createScene();
        setScene(newRegistrationScene);
    }
}
