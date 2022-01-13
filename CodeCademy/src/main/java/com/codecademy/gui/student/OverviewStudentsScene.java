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
import java.util.concurrent.atomic.AtomicReference;

public class OverviewStudentsScene extends GUIScene {
    private Scene overviewStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewStudentsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.gui = gui;
        this.searchBar = new SearchBar();
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(overviewStudentScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox studentOverviewWrapper = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane studentListScroll = new ScrollPane();
        AtomicReference<VBox> studentListPane = new AtomicReference<>(new VBox(5));

        headerPane.setAlignment(Pos.CENTER);

        overviewStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label title = new Label("Students Overview");
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
            ArrayList<Student> searchResult = null;
            try {
                searchResult = searchBar.searchStudents(searchInput, gui.getStudents());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            studentListPane.get().getChildren().clear();
            studentListScroll.setContent(createStudentListPane(searchResult));
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(studentOverviewWrapper);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newStudentButton);

        studentOverviewWrapper.getChildren().addAll(searchBarPane, studentListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);
        try {
            studentListScroll.setContent(createStudentListPane(new ArrayList<>(gui.getStudents().values())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Students to a vertical Pane containing a row for each Student
    private VBox createStudentListPane(ArrayList<Student> students) {
        VBox studentListPane = new VBox(5);

        int index = 0;
        for (Student student : students) {
            // Panes
            HBox studentInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label studentName = new Label(student.getName());
            Label informationDivider = new Label("-");
            Label studentEmail = new Label(student.getEmail());

            // Event Handlers
            studentInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewStudentScene) getSceneObject("viewStudentScene")).resetScene(student);
                showScene("viewStudentScene");
            });

            // Appending
            studentInfoRow.getChildren().addAll(indexLabel, studentName, informationDivider, studentEmail);
            studentListPane.getChildren().add(studentInfoRow);

            index++;
        }

        return studentListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewStudentScene);
    }
}
