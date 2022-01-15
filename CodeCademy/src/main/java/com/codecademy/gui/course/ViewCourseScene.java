package com.codecademy.gui.course;

import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.course.CourseRepository;
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

public class ViewCourseScene extends GUIScene {

    private Scene viewCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Course selectedCourse;
    private final CourseRepository courseRepository;

    public ViewCourseScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        courseRepository = new CourseRepository();

        // Not creating a scene, because when initializing the GUI no Course has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewCoursePane = new VBox(15);

        ScrollPane selectedCourseContentItemsScroll = new ScrollPane();

        viewCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Viewing Course: " + selectedCourse.getName());

        Button homeButton = new Button("Home");
        Button coursesButton = new Button("Courses");
        Button editSelectedCourseButton = new Button("Edit Course");

        Label selectedCourseNameLabel = new Label("Name: " + selectedCourse.getName());
        Label selectedCourseSubjectLabel = new Label("Subject: " + selectedCourse.getSubject());
        Label selectedCourseIntroductionTextLabel = new Label("Introduction: " + selectedCourse.getIntroductionText());
        Label selectedCourseLevelLabel = new Label("Level: " + selectedCourse.getLevel());
        Label selectedCourseRelatedCoursesLabel = new Label("Related Courses: " + selectedCourse.getRelatedCoursesAsString());
        Label selectedCourseNumberOfCertificatesLabel = new Label("Number of Certificates: 0");
        Label selectedCourseModulesLabel = new Label("Modules & Average Progression: ");

        try {
            selectedCourseNumberOfCertificatesLabel = new Label("Number of Certificates: " + courseRepository.getAmountOfCertificatesOfCourse(selectedCourse));
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

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, coursesButton, editSelectedCourseButton);

        viewCoursePane.getChildren().addAll(selectedCourseNameLabel, selectedCourseSubjectLabel,
                selectedCourseIntroductionTextLabel, selectedCourseLevelLabel, selectedCourseRelatedCoursesLabel,
                selectedCourseNumberOfCertificatesLabel, selectedCourseModulesLabel, selectedCourseContentItemsScroll);

        try {
            selectedCourseContentItemsScroll.setContent(createSelectedCourseModulesPane(courseRepository.getAverageProgressPerContentItem(selectedCourse)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Content Items linked to a selected Course into a VBox
    private VBox createSelectedCourseModulesPane(Map<ContentItem, Integer> contentItemsOfCourse) {
        VBox contentItemsListPane = new VBox(5);

        int index = 0;
        for (ContentItem contentItem : contentItemsOfCourse.keySet()) {
            // Panes
            HBox contentItemInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label contentItemNameLabel = new Label(contentItem.getTitle());
            Label informationDividerLabel = new Label("-");
            Label contentItemAverageProgressLabel = new Label(contentItemsOfCourse.get(contentItem) + "%");

            // Appending
            contentItemInfoRow.getChildren().addAll(indexLabel, contentItemNameLabel, informationDividerLabel, contentItemAverageProgressLabel);
            contentItemsListPane.getChildren().add(contentItemInfoRow);

            index++;
        }

        return contentItemsListPane;
    }

    public void resetScene(Course selectedCourse) {
        if (selectedCourse == null) return;

        this.selectedCourse = selectedCourse;

        createScene();
        setScene(viewCourseScene);
    }
}
