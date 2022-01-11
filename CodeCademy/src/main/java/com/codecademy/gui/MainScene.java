package com.codecademy.gui;

import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.gui.student.OverviewStudentsScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainScene extends GUIScene{
    private Scene mainScene;
    private final int sceneWidth;
    private final int sceneHeight;

    public MainScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(mainScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        VBox homePageButtonsPane = new VBox(15);

        mainScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);
        homePageButtonsPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("CodeCademy Overview");

        Button studentsButton = new Button("Students");
        Button coursesButton = new Button("Courses");
        Button certificationsButton = new Button("Certifications");
        Button registrationsButton = new Button("Registrations");
        Button statisticsButton = new Button("Statistics");

        // Event Handlers
        studentsButton.setOnAction((event) -> {
            ((OverviewStudentsScene)getSceneObject("overviewStudentsScene")).resetScene();
            showScene("overviewStudentsScene");
        });

        coursesButton.setOnAction((event) -> {
            ((OverviewCoursesScene)getSceneObject("overviewCoursesScene")).resetScene();
            showScene("overviewCoursesScene");
        });

        certificationsButton.setOnAction((event) -> {
            System.out.println("Certifications Clicked");
            // Reset Scene Certifications
            // Show Scene Certifications
        });

        registrationsButton.setOnAction((event) -> {
            System.out.println("Registrations Clicked");
            // Reset Scene Registrations
            // Show Scene Registrations
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(homePageButtonsPane);

        header.getChildren().add(title);
        homePageButtonsPane.getChildren().addAll(studentsButton, coursesButton, certificationsButton, registrationsButton, statisticsButton);
    }

    public void resetScene() {
        createScene();
        setScene(mainScene);
    }

}
