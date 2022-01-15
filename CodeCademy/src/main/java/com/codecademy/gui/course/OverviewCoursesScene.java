package com.codecademy.gui.course;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
import com.codecademy.informationhandling.course.Course;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class OverviewCoursesScene extends GUIScene {

    private Scene overviewCoursesScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewCoursesScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.gui = gui;
        this.searchBar = new SearchBar();

        createScene();
        setScene(overviewCoursesScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox overviewCoursePane = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane coursesListScroll = new ScrollPane();

        headerPane.setAlignment(Pos.CENTER);

        overviewCoursesScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label titleLabel = new Label("Courses Overview");
        Button homeButton = new Button("Home");
        Button newCourseButton = new Button("New Course");

        TextField searchBarInput = new TextField();
        Button searchButton = new Button("Search");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        newCourseButton.setOnAction((event) -> {
            ((NewCourseScene) getSceneObject("newCourseScene")).resetScene();
            showScene("newCourseScene");
        });

        searchButton.setOnAction((event) -> {
            String searchInput = searchBarInput.getText();

            try {
                ArrayList<Course> searchResult = searchBar.searchCourses(searchInput, gui.getCourses());
                coursesListScroll.setContent(createCoursesListPane(searchResult));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewCoursePane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newCourseButton);

        overviewCoursePane.getChildren().addAll(searchBarPane, coursesListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);
        try {
            coursesListScroll.setContent(createCoursesListPane(new ArrayList<>(gui.getCourses().values())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Courses to a vertical Pane containing a row for each Course
    private VBox createCoursesListPane(ArrayList<Course> courses) {
        // Panes
        VBox coursesListPane = new VBox(5);

        int index = 0;
        for (Course course : courses) {
            HBox courseInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label courseNameLabel = new Label(course.getName());
            Label informationDividerLabel = new Label("-");
            Label courseSubjectLabel = new Label(course.getSubject());

            // Event Handlers
            courseInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewCourseScene) getSceneObject("viewCourseScene")).resetScene(course);
                showScene("viewCourseScene");
            });

            // Appending
            courseInfoRow.getChildren().addAll(indexLabel, courseNameLabel, informationDividerLabel, courseSubjectLabel);
            coursesListPane.getChildren().add(courseInfoRow);

            index++;
        }

        return coursesListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewCoursesScene);
    }
}
