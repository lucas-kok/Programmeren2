package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.Registration.RegistrationRepository;
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

    private final GUI gui;
    private final RegistrationInformationValidator registrationInformationValidator;
    private final RegistrationRepository registrationRepository;

    public NewRegistrationScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        registrationInformationValidator = new RegistrationInformationValidator();
        registrationRepository = new RegistrationRepository();

        createScene();
        setScene(newRegistrationScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        HBox navigation = new HBox(15);
        VBox newRegistrationPane = new VBox(15);
        HBox registrationDatePane = new HBox(5);

        newRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Create new Registration");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label registrationDateLabel = new Label("Birthday:");
        TextField registrationDateDayInput = new TextField();
        TextField registrationDateMonthInput = new TextField();
        TextField registrationDateYearInput = new TextField();

        Label registrationStudentEmailLabel = new Label("Student Email:");
        TextField registrationStudentEmailInput = new TextField();

        Label registrationCourseNameLabel = new Label("Course Name:");
        TextField registrationCourseNameInput = new TextField();

        Button createRegistrationButton = new Button("Create");
        Label messageLabel = new Label("");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        createRegistrationButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!registrationDateDayInput.getText().isBlank() && !registrationDateMonthInput.getText().isBlank() && !registrationDateYearInput.getText().isBlank() &&
                    !registrationStudentEmailInput.getText().isBlank() && !registrationCourseNameInput.getText().isBlank()) {

                String registrationDateDay = registrationDateDayInput.getText();
                String registrationDateMonth = registrationDateMonthInput.getText();
                String registrationDateYear = registrationDateYearInput.getText();
                String[] registrationDatePieces = new String[] { registrationDateDay, registrationDateMonth, registrationDateYear };

                String studentEmail = registrationStudentEmailInput.getText();
                String courseName = registrationCourseNameInput.getText();

                String response = null;
                try {
                    response = registrationInformationValidator.validateNewRegistration(registrationDatePieces, studentEmail, courseName, gui.getStudents(), gui.getCourses());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    // Create new Registration

                    // Clearing all fields
                    registrationDateDayInput.clear(); registrationDateMonthInput.clear(); registrationDateYearInput.clear();
                    registrationStudentEmailInput.clear();
                    registrationCourseNameInput.clear();

                    messageLabel.setText("The Registration for '" + studentEmail + "' has successfully been created!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(newRegistrationPane);

        header.getChildren().addAll(title, navigation);
        navigation.getChildren().addAll(homeButton, backButton);

        newRegistrationPane.getChildren().addAll(registrationDateLabel, registrationDatePane, registrationStudentEmailLabel,
                registrationStudentEmailInput, registrationCourseNameLabel, registrationCourseNameInput, createRegistrationButton, messageLabel);

        registrationDatePane.getChildren().addAll(registrationDateDayInput, registrationDateMonthInput, registrationDateYearInput);
    }

    public void resetScene() {
        createScene();
        setScene(newRegistrationScene);
    }

}
