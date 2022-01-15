package com.codecademy.gui.certificate;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.certificate.Certificate;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewCertificateScene extends GUIScene {

    private Scene viewCertificateScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private Certificate selectedCertificate;

    public ViewCertificateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        // Not creating a scene, because when initializing the GUI no Certificate has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewCertificatePane = new VBox(15);

        viewCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Viewing Certificate: #" + selectedCertificate.getCertificateID());

        Button homeButton = new Button("Home");
        Button certificatesButton = new Button("Certificates");
        Button editSelectedCertificateButton = new Button("Edit Certificate");

        Label selectedCertificateCourseNameLabel = new Label("Course: " + selectedCertificate.getCourseName());
        Label selectedCertificateStudentEmailLabel = new Label("Student: " + selectedCertificate.getStudentEmail());
        Label selectedCertificateScoreLabel = new Label("Grade: " + selectedCertificate.getScore() + "/10");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        certificatesButton.setOnAction((event) -> {
            ((OverviewCertificatesScene) getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        editSelectedCertificateButton.setOnAction((event) -> {
            ((EditCertificateScene) getSceneObject("editCertificateScene")).resetScene(selectedCertificate);
            showScene("editCertificateScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewCertificatePane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, certificatesButton, editSelectedCertificateButton);

        viewCertificatePane.getChildren().addAll(selectedCertificateCourseNameLabel, selectedCertificateStudentEmailLabel, selectedCertificateScoreLabel);
    }

    public void resetScene(Certificate selectedCertificate) {
        if (selectedCertificate == null) return;

        this.selectedCertificate = selectedCertificate;

        createScene();
        setScene(viewCertificateScene);
    }
}
