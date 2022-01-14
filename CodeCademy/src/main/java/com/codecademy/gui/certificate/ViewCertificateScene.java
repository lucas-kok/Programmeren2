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

    public ViewCertificateScene(GUI gui, int sceneWidth, int sceneHeight, Certificate selectedCertificate) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.selectedCertificate = selectedCertificate;

        if (selectedCertificate != null) {
            createScene();
            setScene(viewCertificateScene);
        }
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox viewCoursePane = new VBox(15);

        viewCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Certificate for course: " + selectedCertificate.getCourseName());

        Button homeButton = new Button("Home");
        Button certificatesButton = new Button("Certificates");
        Button editSelectedCertificateButton = new Button("Edit Certificate");

        Label selectedCertificateNameLabel = new Label("Certificate for course: " + selectedCertificate.getCourseName());
        Label selectedCertificateStudentEmailLabel = new Label("Student email: " + selectedCertificate.getStudentEmail());
        Label selectedCertificateScoreLabel = new Label("Grade: " + selectedCertificate.getScore());

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        certificatesButton.setOnAction((event) -> {
            ((OverviewCertificateScene) getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        editSelectedCertificateButton.setOnAction((event) -> {
            ((EditCertificateScene) getSceneObject("editCertificateScene")).resetScene(selectedCertificate);
            showScene("editCertificateScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(viewCoursePane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton, certificatesButton, editSelectedCertificateButton);

        viewCoursePane.getChildren().addAll(selectedCertificateNameLabel, selectedCertificateStudentEmailLabel, selectedCertificateScoreLabel);
    }

    public void resetScene(Certificate selectedCertificate) {
        if (selectedCertificate == null) return;

        this.selectedCertificate = selectedCertificate;

        createScene();
        setScene(viewCertificateScene);
    }

}
