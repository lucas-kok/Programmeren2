package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.contentitem.ContentItemRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditRegistrationScene extends GUIScene {

    private Scene editRegistrationScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final RegistrationInformationValidator registrationInformationValidationTools;
    private final ContentItemRepository contentItemRepository;
    private final RegistrationRepository registrationRepository;
    private Registration selectedRegistration;

    public EditRegistrationScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        registrationInformationValidationTools = new RegistrationInformationValidator();
        contentItemRepository = new ContentItemRepository();
        registrationRepository = new RegistrationRepository();

        // Not creating a scene, because when initializing the GUI no Registration has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox editRegistrationPane = new VBox(15);
        HBox editRegistrationDatePane = new HBox(5);

        ScrollPane selectedRegistrationProgressScroll = new ScrollPane();

        editRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Edit Registration: " + selectedRegistration.getRegisterID());
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

        Button updateSelectedRegistrationButton = new Button("Update Course");
        Label messageLabel = new Label("");

        // Setting the TextFields to the info of the selected Registration
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

        updateSelectedRegistrationButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!registrationDateDayInput.getText().isBlank() && !registrationDateMonthInput.getText().isBlank() &&
                    !registrationDateYearInput.getText().isBlank() && progressionIsEmpty(selectedRegistrationProgressScroll)) {
                String day = registrationDateDayInput.getText();
                String month = registrationDateMonthInput.getText();
                String year = registrationDateYearInput.getText();
                String[] newRegistrationDatePieces = new String[] { year, month, day };
                ArrayList<Integer> newProgression = new ArrayList<>(getNewProgressionInput(selectedRegistrationProgressScroll).values());

                String response;
                response = registrationInformationValidationTools.validateEditedRegistration(newRegistrationDatePieces, newProgression);
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    registrationRepository.updateRegistration(selectedRegistration, newRegistrationDatePieces);
                    registrationRepository.updateProgress(selectedRegistration, getNewProgressionInput(selectedRegistrationProgressScroll));
                    messageLabel.setText("The Registration has successfully been updated!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(editRegistrationPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton, deleteCourseButton);

        editRegistrationPane.getChildren().addAll(registrationDateLabel, editRegistrationDatePane, registrationStudentEmailLabel,
                registrationCourseNameLabel, registrationProgressionLabel, selectedRegistrationProgressScroll, updateSelectedRegistrationButton, messageLabel);

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
            Label contentIDLabel = new Label("#" + contentItem.getId());
            Label contentItemNameLabel = new Label(contentItem.getTitle());
            Label informationDivider = new Label("-");
            TextField contentItemProgression = new TextField();

            contentItemProgression.setText(String.valueOf(progression.get(contentItem)));
            contentItemProgression.setPromptText("Progression");

            // Appending
            contentItemInfoRow.getChildren().addAll(indexLabel, contentIDLabel, contentItemNameLabel, informationDivider, contentItemProgression);
            modulesListPane.getChildren().add(contentItemInfoRow);

            index++;
        }

        return modulesListPane;
    }

    private boolean progressionIsEmpty(ScrollPane progressionInputScroll) {
        VBox progressionInputPane = (VBox) progressionInputScroll.getContent();
        HBox[] inputRows = progressionInputPane.getChildren().toArray(new HBox[0]);

        for (HBox inputRow : inputRows) {
            TextField input = (TextField) inputRow.getChildren().get(4);
            if (input.getText().isBlank()) return false;
        }

        return true;
    }

    private Map<ContentItem, Integer> getNewProgressionInput(ScrollPane progressionInputScroll) {
        Map<ContentItem, Integer> newProgression = new HashMap<>();

        VBox progressionInputPane = (VBox) progressionInputScroll.getContent();
        HBox[] inputRows = progressionInputPane.getChildren().toArray(new HBox[0]);

        for (HBox inputRow : inputRows) {
            Label contentItemIDInput = (Label) inputRow.getChildren().get(1);
            TextField progressInput = (TextField) inputRow.getChildren().get(4);

            String contentItemID = contentItemIDInput.getText();
            contentItemID = contentItemID.replaceAll("#", "");
            int newProgress = Integer.parseInt(progressInput.getText());

            try {
                newProgression.put(contentItemRepository.getContentItem(contentItemID), newProgress);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newProgression;
    }

    public void resetScene(Registration selectedRegistration) {
        if (selectedRegistration == null) return;

        this.selectedRegistration = selectedRegistration;
        createScene();
        setScene(editRegistrationScene);
    }
}
