package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.validators.RegistrationInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditRegistrationScene extends GUIScene {

    private Scene editRegistrationScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final RegistrationInformationValidator registrationInformationValidationTools;
    private final RegistrationRepository registrationRepository;
    private Registration selectedRegistration;

    public EditRegistrationScene(GUI gui, int sceneWidth, int sceneHeight, Registration selectedRegistration) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        registrationInformationValidationTools = new RegistrationInformationValidator();
        registrationRepository = new RegistrationRepository();
        this.selectedRegistration = selectedRegistration;

        if (selectedRegistration != null) {
            createScene();
            setScene(editRegistrationScene);
        }
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        HBox navigation = new HBox(15);
        VBox editRegistrationPane = new VBox(15);
        HBox editRegistrationDatePane = new HBox(5);

        editRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Edit Registration");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");
        Button deleteCourseButton = new Button("Delete");

        Label registrationDateLabel = new Label("Registration Date:");
        TextField registrationDateDayInput = new TextField();
        TextField registrationDateMonthInput = new TextField();
        TextField registrationDateYearInput = new TextField();

        registrationDateDayInput.setPromptText("Day");
        registrationDateMonthInput.setPromptText("Month");
        registrationDateYearInput.setPromptText("Year");

        Label registrationStudentEmailLabel = new Label("Student email: " + selectedRegistration.getStudentEmail());
        Label registrationCourseNameLabel = new Label("Course name: " + selectedRegistration.getCourseName());

        Button updateSelectedCourseButton = new Button("Update Course");
        Label messageLabel = new Label("");

        // Setting the TextFields to the registration current info
        String[] registrationDatePieces = selectedRegistration.getRegistrationDatePieces();
        registrationDateDayInput.setText(registrationDatePieces[2]);
        registrationDateMonthInput.setText(registrationDatePieces[1]);
        registrationDateYearInput.setText(registrationDatePieces[0]);


        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        deleteCourseButton.setOnAction((event) -> {
            registrationRepository.deleteRegistration(selectedRegistration);

            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        updateSelectedCourseButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!registrationDateDayInput.getText().isBlank() && !registrationDateMonthInput.getText().isBlank() && !registrationDateYearInput.getText().isBlank()) {
                String day = registrationDateDayInput.getText();
                String month = registrationDateMonthInput.getText();
                String year = registrationDateYearInput.getText();
                String[] newRegistrationDatePieces = new String[] { year, month, day };

                String response = null;
                response = registrationInformationValidationTools.validateEditedRegistration(newRegistrationDatePieces);
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    registrationRepository.updateRegistration(selectedRegistration, newRegistrationDatePieces);
                    messageLabel.setText("The Registration has successfully been updated!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(editRegistrationPane);

        header.getChildren().addAll(title, navigation);
        navigation.getChildren().addAll(homeButton, backButton, deleteCourseButton);

        editRegistrationPane.getChildren().addAll(registrationDateLabel, editRegistrationDatePane, registrationStudentEmailLabel,
                registrationCourseNameLabel, updateSelectedCourseButton, messageLabel);

        editRegistrationDatePane.getChildren().addAll(registrationDateDayInput, registrationDateMonthInput, registrationDateYearInput);
    }

    public void resetScene(Registration selectedRegistration) {
        if (selectedRegistration == null) return;

        this.selectedRegistration = selectedRegistration;
        createScene();
        setScene(editRegistrationScene);
    }
}
