package com.codecademy.gui.registration;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
import com.codecademy.informationhandling.registration.Registration;
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

public class OverviewRegistrationsScene extends GUIScene {
    private Scene overviewRegistrationsScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewRegistrationsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.gui = gui;
        this.searchBar = new SearchBar();

        createScene();
        setScene(overviewRegistrationsScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox overviewRegistrationsPane = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane registrationsListScroll = new ScrollPane();

        headerPane.setAlignment(Pos.CENTER);

        overviewRegistrationsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label titleLabel = new Label("Registrations Overview");
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

            try {
                ArrayList<Registration> searchResult = searchBar.searchRegistrations(searchInput, gui.getRegistrations());
                registrationsListScroll.setContent(createRegistrationsListPane(searchResult));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewRegistrationsPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newRegistrationButton);

        overviewRegistrationsPane.getChildren().addAll(searchBarPane, registrationsListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);

        try {
            registrationsListScroll.setContent(createRegistrationsListPane(new ArrayList<>(gui.getRegistrations().values())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Registrations to a vertical Pane containing a row for each Registration
    private VBox createRegistrationsListPane(ArrayList<Registration> registrations) {
        VBox registrationsListPane = new VBox(5);

        int index = 0;
        for (Registration registration : registrations) {
            // Panes
            HBox registrationInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ".");
            Label registrationDateLabel = new Label(registration.getRegistrationDateAsString());
            Label registrationStudentEmailLabel = new Label(registration.getStudentEmail());
            Label informationDividerLabel = new Label("-");
            Label registrationCourseNameLabel = new Label(registration.getCourseName());

            // Event Handlers
            registrationInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewRegistrationScene) getSceneObject("viewRegistrationScene")).resetScene(registration);
                showScene("viewRegistrationScene");
            });

            // Appending
            registrationInfoRow.getChildren().addAll(indexLabel, registrationDateLabel,
                    registrationStudentEmailLabel, informationDividerLabel, registrationCourseNameLabel);
            registrationsListPane.getChildren().add(registrationInfoRow);

            index++;
        }

        return registrationsListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewRegistrationsScene);
    }
}
