package com.codecademy.gui.student;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
import com.codecademy.informationhandling.student.Student;
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

public class OverviewStudentsScene extends GUIScene {
    private Scene overviewStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewStudentsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.gui = gui;
        this.searchBar = new SearchBar();

        createScene();
        setScene(overviewStudentScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox studentsOverviewWrapperPane = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane studentsListScroll = new ScrollPane();

        headerPane.setAlignment(Pos.CENTER);

        overviewStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label titleLabel = new Label("Students Overview");
        Button homeButton = new Button("Home");
        Button newStudentButton = new Button("New Student");

        TextField searchBarInput = new TextField();
        Button searchButton = new Button("Search");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        newStudentButton.setOnAction((event) -> {
            ((NewStudentScene) getSceneObject("newStudentScene")).resetScene();
            showScene("newStudentScene");
        });

        searchButton.setOnAction((event) -> {
            String searchInput = searchBarInput.getText();

            try {
                ArrayList<Student> searchResult = searchBar.searchStudents(searchInput, gui.getStudents());
                studentsListScroll.setContent(createStudentsListPane(searchResult));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(studentsOverviewWrapperPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newStudentButton);

        studentsOverviewWrapperPane.getChildren().addAll(searchBarPane, studentsListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);

        try {
            studentsListScroll.setContent(createStudentsListPane(new ArrayList<>(gui.getStudents().values())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Students to a vertical Pane containing a row for each Student
    private VBox createStudentsListPane(ArrayList<Student> students) {
        VBox studentsListPane = new VBox(5);

        int index = 0;
        for (Student student : students) {
            // Panes
            HBox studentInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label studentNameLabel = new Label(student.getName());
            Label informationDividerLabel = new Label("-");
            Label studentEmailLabel = new Label(student.getEmail());

            // Event Handlers
            studentInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewStudentScene) getSceneObject("viewStudentScene")).resetScene(student);
                showScene("viewStudentScene");
            });

            // Appending
            studentInfoRow.getChildren().addAll(indexLabel, studentNameLabel, informationDividerLabel, studentEmailLabel);
            studentsListPane.getChildren().add(studentInfoRow);

            index++;
        }

        return studentsListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewStudentScene);
    }
}
