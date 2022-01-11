package com.codecademy.gui.course;

import com.codecademy.informationhandling.Course.Course;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewCourseScene extends GUIScene {

    private Scene viewCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Course selectedCourse;

    public ViewCourseScene(GUI gui, int sceneWidth, int sceneHeight, Course selectedCourse) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedCourse = selectedCourse;

        if (selectedCourse != null) {
            createScene();
            setScene(viewCourseScene);
        }
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewCoursePane = new VBox(15);

        viewCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Viewing Course: " + selectedCourse.getName());

        Button homeButton = new Button("Home");
        Button coursesButton = new Button("Courses");
        Button editSelectedCourseButton = new Button("Edit Course");

        Label selectedCourseNameLabel = new Label("Name: " + selectedCourse.getName());
        Label selectedCourseSubjectLabel = new Label("Subject: " + selectedCourse.getSubject());
        Label selectedCourseIntroductionTextLabel = new Label("Introduction: " + selectedCourse.getIntroductionText());
        Label selectedCourseLevelLabel = new Label("Level: " + selectedCourse.getLevel());
        Label selectedCourseRelatedCoursesLabel = new Label("Related Courses: " + selectedCourse.getRelatedCoursesAsString());

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        coursesButton.setOnAction((event) -> {
            ((OverviewCoursesScene) getSceneObject("overviewCoursesScene")).resetScene();
            showScene("overviewCoursesScene");
        });

        editSelectedCourseButton.setOnAction((event) -> {
            ((EditCourseScene) getSceneObject("editCourseScene")).resetScene(selectedCourse);
            showScene("editCourseScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewCoursePane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, coursesButton, editSelectedCourseButton);

        viewCoursePane.getChildren().addAll(selectedCourseNameLabel, selectedCourseSubjectLabel,
                selectedCourseIntroductionTextLabel, selectedCourseLevelLabel, selectedCourseRelatedCoursesLabel);
    }

    public void resetScene(Course selectedCourse) {
        if (selectedCourse == null) return;

        this.selectedCourse = selectedCourse;

        createScene();
        setScene(viewCourseScene);
    }

}
