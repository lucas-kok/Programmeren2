package com.codecademy.gui;

import com.codecademy.gui.certificate.OverviewCertificatesScene;
import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.gui.registration.OverviewRegistrationsScene;
import com.codecademy.gui.statistics.OverviewStatisticsScene;
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
        VBox headerPane = new VBox(15);
        VBox homePageButtonsPane = new VBox(15);

        mainScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);
        homePageButtonsPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("CodeCademy Overview");

        Button studentsButton = new Button("Students");
        Button coursesButton = new Button("Courses");
        Button certificatesButton = new Button("Certificates");
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

        certificatesButton.setOnAction((event) -> {
            ((OverviewCertificatesScene)getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        registrationsButton.setOnAction((event) -> {
            ((OverviewRegistrationsScene)getSceneObject("overviewRegistrationsScene")).resetScene();
            showScene("overviewRegistrationsScene");
        });

        statisticsButton.setOnAction((event) -> {
            ((OverviewStatisticsScene)getSceneObject("statisticsScene")).resetScene();
            showScene("statisticsScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(homePageButtonsPane);

        headerPane.getChildren().add(title);
        homePageButtonsPane.getChildren().addAll(studentsButton, coursesButton, certificatesButton, registrationsButton, statisticsButton);
    }

    public void resetScene() {
        createScene();
        setScene(mainScene);
    }
}
