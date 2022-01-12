package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
import com.codecademy.informationhandling.Registration.Registration;
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

public class OverviewRegistrationsScene extends GUIScene {
    private Scene overviewRegistrationsScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewRegistrationsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.gui = gui;
        this.searchBar = new SearchBar();
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(overviewRegistrationsScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox registrationOverviewWrapper = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane registrationListScroll = new ScrollPane();
        AtomicReference<VBox> registrationListPane = new AtomicReference<>(new VBox(5));

        headerPane.setAlignment(Pos.CENTER);

        overviewRegistrationsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label title = new Label("Students Overview");
        Button homeButton = new Button("Home");
        Button newRegistrationButton = new Button("New Registration");

        TextField searchBarInput = new TextField();
        Button searchButton = new Button("Search");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        newRegistrationButton.setOnAction((event) -> {
            ((NewRegistrationScene) getSceneObject("newRegistrationScene")).resetScene();
            showScene("newRegistrationScene");
        });

        searchButton.setOnAction((event) -> {
            String searchInput = searchBarInput.getText();
            ArrayList<Registration> searchResult = searchBar.searchRegistrations(searchInput, gui.getRegistrations());

            registrationListPane.get().getChildren().clear();
            registrationListScroll.setContent(createRegistrationListPane(searchResult));
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(registrationOverviewWrapper);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newRegistrationButton);

        registrationOverviewWrapper.getChildren().addAll(searchBarPane, registrationListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);
        registrationListScroll.setContent(createRegistrationListPane(new ArrayList<>(gui.getRegistrations())));
    }

    // Function that will convert a list of Students to a vertical Pane containing a row for each Student
    private VBox createRegistrationListPane(ArrayList<Registration> registrations) {
        VBox studentListPane = new VBox(5);

        int index = 0;
        for (Registration registration : registrations) {
            // Panes
            HBox studentInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ".");
            Label registrationDateLabel = new Label(registration.getRegistrationDate() + ":");
            Label registrationStudentEmailLabel = new Label(registration.getStudentEmail());
            Label informationDivider = new Label("-");
            Label registrationCourseNameLabel = new Label(registration.getCourseName());

            // Event Handlers
            studentInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewRegistrationScene) getSceneObject("viewRegistrationScene")).resetScene(registration);
                showScene("viewRegistrationScene");
            });

            // Appending
            studentInfoRow.getChildren().addAll(indexLabel, registrationDateLabel,
                    registrationStudentEmailLabel, informationDivider, registrationCourseNameLabel);
            studentListPane.getChildren().add(studentInfoRow);

            index++;
        }

        return studentListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewRegistrationsScene);
    }
}
