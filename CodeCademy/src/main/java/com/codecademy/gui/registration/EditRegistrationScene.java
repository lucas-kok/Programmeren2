package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.validators.RegistrationInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Map;

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

        ScrollPane selectedRegistrationProgressScroll = new ScrollPane();

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
        Label registrationProgressionLabel = new Label("Progression:");

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
                registrationCourseNameLabel, registrationProgressionLabel, selectedRegistrationProgressScroll, updateSelectedCourseButton, messageLabel);

        editRegistrationDatePane.getChildren().addAll(registrationDateDayInput, registrationDateMonthInput, registrationDateYearInput);

        try {
            selectedRegistrationProgressScroll.setContent(createSelectedRegistrationModulesPane(registrationRepository.getProgressForRegistration(selectedRegistration)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of ContentItems connected to the selected Registration into a VBox
    private VBox createSelectedRegistrationModulesPane(Map<ContentItem, Integer> progression) {
        VBox modulesListPane = new VBox(5);

        int index = 0;
        for (ContentItem contentItem : progression.keySet()) {
            // Panes
            HBox contentItemInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label contentItemNameLabel = new Label(contentItem.getTitle());
            Label informationDivider = new Label("-");
            TextField contentItemProgression = new TextField();

            contentItemProgression.setText(String.valueOf(progression.get(contentItem)));
            contentItemProgression.setPromptText("Progression");

            // Appending
            contentItemInfoRow.getChildren().addAll(indexLabel, contentItemNameLabel, informationDivider, contentItemProgression);
            modulesListPane.getChildren().add(contentItemInfoRow);

            index++;
        }

        return modulesListPane;
    }

    public void resetScene(Registration selectedRegistration) {
        if (selectedRegistration == null) return;

        this.selectedRegistration = selectedRegistration;
        createScene();
        setScene(editRegistrationScene);
    }
}
