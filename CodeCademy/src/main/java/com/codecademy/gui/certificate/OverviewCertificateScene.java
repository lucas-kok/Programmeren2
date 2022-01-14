package com.codecademy.gui.certificate;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.SearchBar;
import com.codecademy.informationhandling.certificate.Certificate;
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

public class OverviewCertificateScene extends GUIScene {

    private Scene overviewCertificateScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewCertificateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.gui = gui;
        this.searchBar = new SearchBar();
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(overviewCertificateScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox courseOverviewWrapper = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane certificatesListScroll = new ScrollPane();
        AtomicReference<VBox> certificatesListPane = new AtomicReference<>(new VBox(5));

        headerPane.setAlignment(Pos.CENTER);

        overviewCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label title = new Label("Certificates Overview");
        Button homeButton = new Button("Home");
        Button newCertificateButton = new Button("New Certificate");

        TextField searchBarInput = new TextField();
        Button searchButton = new Button("Search");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        newCertificateButton.setOnAction((event) -> {
            ((NewCertificateScene) getSceneObject("newCertificateScene")).resetScene();
            showScene("newCertificateScene");
        });

        searchButton.setOnAction((event) -> {
            String searchInput = searchBarInput.getText();
            ArrayList<Certificate> result = null;
            try {
                result = searchBar.searchCertificates(searchInput, gui.getCertificates());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            certificatesListPane.get().getChildren().clear();
            certificatesListScroll.setContent(createCertificateListPane(result));
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(courseOverviewWrapper);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newCertificateButton);

        courseOverviewWrapper.getChildren().addAll(searchBarPane, certificatesListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);
        try {
            certificatesListScroll.setContent(createCertificateListPane(gui.getCertificates()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Courses to a vertical Pane containing a row for each Course
    private VBox createCertificateListPane(ArrayList<Certificate> certificates) {
        // Panes
        VBox certificateListPane = new VBox(5);

        int index = 0;
        for (Certificate certificate : certificates) {
            HBox certificateInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label studentEmailLabel = new Label(certificate.getStudentEmail());
            Label informationDivider = new Label("-");
            Label courseNameLabel = new Label(certificate.getCourseName());
            Label certificateScoreLabel = new Label(String.valueOf(certificate.getScore()));

            // Event Handlers
            certificateInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewCertificateScene) getSceneObject("viewCertificateScene")).resetScene(certificate);
                showScene("viewCertificateScene");
            });

            // Appending
            certificateInfoRow.getChildren().addAll(indexLabel, studentEmailLabel, informationDivider, courseNameLabel, certificateScoreLabel);
            certificateListPane.getChildren().add(certificateInfoRow);

            index++;
        }

        return certificateListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewCertificateScene);
    }

}
