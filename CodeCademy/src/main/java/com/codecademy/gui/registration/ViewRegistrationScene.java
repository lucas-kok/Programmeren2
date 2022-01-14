package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.registration.Registration;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Map;

public class ViewRegistrationScene extends GUIScene {
    private Scene viewRegistrationScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Registration selectedRegistration;
    private final RegistrationRepository registrationRepository;

    public ViewRegistrationScene(GUI gui, int sceneWidth, int sceneHeight, Registration selectedRegistration) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedRegistration = selectedRegistration;
        registrationRepository = new RegistrationRepository();

        if (selectedRegistration != null) {
            createScene();
            setScene(viewRegistrationScene);
        }
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewStudentPane = new VBox(15);

        ScrollPane selectedRegistrationProgressScroll = new ScrollPane();

        viewRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Viewing Registration: " + selectedRegistration.getStudentEmail() + ", " + selectedRegistration.getCourseName());

        Button homeButton = new Button("Home");
        Button registrationsButton = new Button("Registrations");
        Button editSelectedRegistration = new Button("Edit Registration");

        Label selectedRegistrationDateLabel = new Label("Registration Date: " + selectedRegistration.getRegistrationDateAsString());
        Label selectedRegistrationStudentEmailLabel = new Label("Student email: " + selectedRegistration.getStudentEmail());
        Label selectedRegistrationCourseNameLabel = new Label("Course: " + selectedRegistration.getCourseName());
        Label selectedRegistrationModulesLabel = new Label("Modules Progression:");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        registrationsButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        editSelectedRegistration.setOnAction((event) -> {
            ((EditRegistrationScene)getSceneObject("editRegistrationScene")).resetScene(selectedRegistration);
            showScene("editRegistrationScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewStudentPane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, registrationsButton, editSelectedRegistration);

        viewStudentPane.getChildren().addAll(selectedRegistrationDateLabel, selectedRegistrationStudentEmailLabel, selectedRegistrationCourseNameLabel,
                selectedRegistrationModulesLabel, selectedRegistrationProgressScroll);

        try {
            selectedRegistrationProgressScroll.setContent(createSelectedRegistrationModulesPane(registrationRepository.getProgressForRegistration(selectedRegistration)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of ContentItems connected to the selected Registration into a VBox
    private VBox createSelectedRegistrationModulesPane(Map<ContentItem, Integer> contentItems) {
        VBox modulesListPane = new VBox(5);

        int index = 0;
        for (ContentItem contentItem : contentItems.keySet()) {
            // Panes
            HBox contentItemInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label contentItemNameLabel = new Label(contentItem.getTitle());
            Label informationDivider = new Label("-");
            Label contentItemAverageProgressLabel = new Label(contentItems.get(contentItem) + "%");

            // Appending
            contentItemInfoRow.getChildren().addAll(indexLabel, contentItemNameLabel, informationDivider, contentItemAverageProgressLabel);
            modulesListPane.getChildren().add(contentItemInfoRow);

            index++;
        }

        return modulesListPane;
    }

    public void resetScene(Registration selectedRegistration) {
        if (selectedRegistration == null) return;

        this.selectedRegistration = selectedRegistration;

        createScene();
        setScene(viewRegistrationScene);
    }

}
