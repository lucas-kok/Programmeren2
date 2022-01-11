package com.codecademy.gui.registration;

import com.codecademy.Registration;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewRegistrationScene extends GUIScene {
    private Scene viewRegistrationScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Registration selectedRegistration;

    public ViewRegistrationScene(GUI gui, int sceneWidth, int sceneHeight, Registration selectedRegistration) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedRegistration = selectedRegistration;

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

        viewRegistrationScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Viewing Registration: " + selectedRegistration);

        Button homeButton = new Button("Home");
        Button registrationsButton = new Button("Registrations");
        Button editSelectedRegistration = new Button("Edit Registration");

        Label selectedRegistrationDateLabel = new Label("Registration Date: " + selectedRegistration.getRegistrationDate());
        Label selectedRegistrationStudentEmailLabel = new Label("Student email: " + selectedRegistration.getStudentEmail());
        Label selectedRegistrationCourseNameLabel = new Label("Course: " + selectedRegistration.getCourseName());

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        registrationsButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene) getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        editSelectedRegistration.setOnAction((event) -> {
            // Edit Registration Scene
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewStudentPane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, registrationsButton, editSelectedRegistration);

        viewStudentPane.getChildren().addAll(selectedRegistrationDateLabel, selectedRegistrationStudentEmailLabel, selectedRegistrationCourseNameLabel);
    }

    public void resetScene(Registration selectedRegistration) {
        if (selectedRegistration == null) return;

        this.selectedRegistration = selectedRegistration;

        createScene();
        setScene(viewRegistrationScene);
    }
}
