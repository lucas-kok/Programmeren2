package com.codecademy.gui.course;

import com.codecademy.informationhandling.Course.Course;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class OverviewCoursesScene extends GUIScene {

    private Scene overviewCoursesScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewCoursesScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.gui = gui;
        this.searchBar = new SearchBar();
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(overviewCoursesScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox courseOverviewWrapper = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane coursesListScroll = new ScrollPane();
        AtomicReference<VBox> coursesListPane = new AtomicReference<>(new VBox(5));

        headerPane.setAlignment(Pos.CENTER);

        overviewCoursesScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label title = new Label("Courses Overview");
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
            ArrayList<Course> result = searchBar.searchCourses(searchInput, gui.getCourses());

            coursesListPane.get().getChildren().clear();
            coursesListScroll.setContent(createCourseListPane(result));
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(courseOverviewWrapper);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newCourseButton);

        courseOverviewWrapper.getChildren().addAll(searchBarPane, coursesListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);
        coursesListScroll.setContent(createCourseListPane(new ArrayList<>(gui.getCourses().values())));
    }

    // Function that will convert a list of Courses to a vertical Pane containing a row for each Course
    private VBox createCourseListPane(ArrayList<Course> courses) {
        // Panes
        VBox courseListPane = new VBox(5);

        int index = 0;
        for (Course course : courses) {
            HBox courseInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label courseName = new Label(course.getName());
            Label informationDivider = new Label("-");
            Label courseSubject = new Label(course.getSubject());

            // Event Handlers
            courseInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewCourseScene) getSceneObject("viewCourseScene")).resetScene(course);
                showScene("viewCourseScene");
            });

            // Appending
            courseInfoRow.getChildren().addAll(indexLabel, courseName, informationDivider, courseSubject);
            courseListPane.getChildren().add(courseInfoRow);
        }

        return courseListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewCoursesScene);
    }

}
