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

public class OverviewCertificatesScene extends GUIScene {

    private Scene overviewCertificatesScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final SearchBar searchBar;

    public OverviewCertificatesScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.gui = gui;
        this.searchBar = new SearchBar();

        createScene();
        setScene(overviewCertificatesScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);

        VBox overviewCertificatesPane = new VBox(15);
        HBox searchBarPane = new HBox(5);
        ScrollPane certificatesListScroll = new ScrollPane();

        overviewCertificatesScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label titleLabel = new Label("Certificates Overview");
        Button homeButton = new Button("Home");
        Button newCertificateButton = new Button("New Certificate");

        TextField searchBarInput = new TextField();
        Button searchButton = new Button("Search");

        // Styling
        overviewCertificatesScene.setUserAgentStylesheet("/style.css");
        headerPane.setId("header");
        titleLabel.setId("title");
        navigationPane.setId("navigation");
        searchButton.setId("actionButton");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        newCertificateButton.setOnAction((event) -> {
            ((NewCertificateScene) getSceneObject("newCertificateScene")).resetScene();
            showScene("newCertificateScene");
        });

        searchButton.setOnAction((event) -> {
            String searchInput = searchBarInput.getText();

            try {
                ArrayList<Certificate> searchResult = searchBar.searchCertificates(searchInput, gui.getCertificates());
                certificatesListScroll.setContent(createCertificatesListPane(searchResult));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewCertificatesPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, newCertificateButton);

        overviewCertificatesPane.getChildren().addAll(searchBarPane, certificatesListScroll);
        searchBarPane.getChildren().addAll(searchBarInput, searchButton);

        try {
            certificatesListScroll.setContent(createCertificatesListPane(gui.getCertificates()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that will convert a list of Courses to a vertical Pane containing a row for each Course
    private VBox createCertificatesListPane(ArrayList<Certificate> certificates) {
        // Panes
        VBox certificatesListPane = new VBox(5);

        int index = 0;
        for (Certificate certificate : certificates) {
            HBox certificateInfoRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label studentEmailLabel = new Label(certificate.getStudentEmail());
            Label informationDividerLabel = new Label("-");
            Label courseNameLabel = new Label(certificate.getCourseName());
            Label secondInformationDividerLabel = new Label("-");
            Label certificateScoreLabel = new Label(certificate.getScore() + "/10");

            // Styling
            certificateInfoRow.setId("clickable");

            // Event Handlers
            certificateInfoRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                ((ViewCertificateScene) getSceneObject("viewCertificateScene")).resetScene(certificate);
                showScene("viewCertificateScene");
            });

            // Appending
            certificateInfoRow.getChildren().addAll(indexLabel, studentEmailLabel, informationDividerLabel, courseNameLabel,
                    secondInformationDividerLabel, certificateScoreLabel);
            certificatesListPane.getChildren().add(certificateInfoRow);

            index++;
        }

        return certificatesListPane;
    }

    public void resetScene() {
        createScene();
        setScene(overviewCertificatesScene);
    }
}
