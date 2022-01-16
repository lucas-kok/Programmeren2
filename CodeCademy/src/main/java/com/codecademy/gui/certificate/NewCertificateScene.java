package com.codecademy.gui.certificate;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.certificate.CertificateRepository;
import com.codecademy.informationhandling.validators.CertificateInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class NewCertificateScene extends GUIScene {
    private Scene newCertificateScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final CertificateInformationValidator certificateInformationValidator;
    private final CertificateRepository certificateRepository;

    public NewCertificateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        certificateInformationValidator = new CertificateInformationValidator();
        certificateRepository = new CertificateRepository();

        createScene();
        setScene(newCertificateScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox newCertificatePane = new VBox(15);

        newCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Create new Certificate");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label studentEmailLabel = new Label("Email Student:");
        TextField studentEmailInput = new TextField();

        Label courseNameLabel = new Label("Course Name:");
        TextField courseNameInput = new TextField();

        Label scoreLabel = new Label("Grade:");
        TextField scoreInput = new TextField();

        Label staffNameLabel = new Label("Staff Name:");
        TextField staffNameInput = new TextField();

        Button createCertificateButton = new Button("Create new Certificate");
        Label messageLabel = new Label();

        // Styling
        newCertificateScene.setUserAgentStylesheet("/style.css");
        headerPane.setId("header");
        titleLabel.setId("title");
        navigationPane.setId("navigation");
        createCertificateButton.setId("actionButton");
        messageLabel.setId("errorMessage");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCertificatesScene) getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        createCertificateButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!studentEmailInput.getText().isEmpty() && !courseNameInput.getText().isEmpty() && !scoreInput.getText().isEmpty() && !staffNameInput.getText().isEmpty()) {
                InformationFormatter informationFormatter = new InformationFormatter();
                String studentEmail = studentEmailInput.getText().toLowerCase().strip();
                String courseName = informationFormatter.capitalizeString(courseNameInput.getText().toLowerCase());
                String staffName = informationFormatter.capitalizeString(staffNameInput.getText().toLowerCase());
                String scoreString = scoreInput.getText();

                String response = null;
                try {
                    response = certificateInformationValidator.validateNewCertificate(studentEmail, courseName, scoreString);

                    messageLabel.setId("errorMessage");
                    messageLabel.setText(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if (response.isBlank()) { // No errors, all inputs are valid
                    try {
                        int score = Integer.parseInt(scoreString);
                        certificateRepository.createCertificate(studentEmail, courseName, score, staffName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Clearing all fields
                    studentEmailInput.clear();
                    courseNameInput.clear();
                    scoreInput.clear();
                    staffNameInput.clear();

                    messageLabel.setId("goodMessage");
                    messageLabel.setText("\nThe Certificate has successfully been created!");
                }

            } else {
                messageLabel.setId("errorMessage");
                messageLabel.setText("\nPlease fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(newCertificatePane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton);

        newCertificatePane.getChildren().addAll(studentEmailLabel, studentEmailInput, courseNameLabel, courseNameInput, scoreLabel, scoreInput, staffNameLabel, staffNameInput, createCertificateButton, messageLabel);
    }

    public void resetScene() {
        createScene();
        setScene(newCertificateScene);
    }
}
