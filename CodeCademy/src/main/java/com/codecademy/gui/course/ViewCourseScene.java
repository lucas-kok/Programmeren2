package com.codecademy.gui.course;

import com.codecademy.gui.registration.ViewRegistrationScene;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.course.CourseRepository;
import com.codecademy.informationhandling.registration.Registration;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ViewCourseScene extends GUIScene {

    private Scene viewCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Course selectedCourse;
    private final CourseRepository courseRepository;

    public ViewCourseScene(GUI gui, int sceneWidth, int sceneHeight, Course selectedCourse) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedCourse = selectedCourse;
        courseRepository = new CourseRepository();

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

        ScrollPane selectedCourseModulesScroll = new ScrollPane();

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
        Label selectedCourseNumberOfCertificatesLabel = new Label("Number of Certificates:");
        Label selectedCourseModulesLabel = new Label("Modules & Average Progression: ");
        try {
            selectedCourseNumberOfCertificatesLabel = new Label("Number of Certificates: " + courseRepository.getAmountOfCertificates(selectedCourse));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                selectedCourseIntroductionTextLabel, selectedCourseLevelLabel, selectedCourseRelatedCoursesLabel,
                selectedCourseNumberOfCertificatesLabel, selectedCourseModulesLabel, selectedCourseModulesScroll);

        try {
            selectedCourseModulesScroll.setContent(createSelectedCourseModulesPane(courseRepository.getAverageProgressPerContentItem(selectedCourse)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of ContentItems connected to the selected Course into a VBox
    private VBox createSelectedCourseModulesPane(Map<ContentItem, Integer> contentItems) {
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

    public void resetScene(Course selectedCourse) {
        if (selectedCourse == null) return;

        this.selectedCourse = selectedCourse;

        createScene();
        setScene(viewCourseScene);
    }

}
