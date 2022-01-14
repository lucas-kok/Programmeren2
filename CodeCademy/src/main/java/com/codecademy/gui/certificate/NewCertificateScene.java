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

    private final GUI gui;
    private final CertificateInformationValidator certificateInformationValidator;
    private final CertificateRepository certificateRepository;

    public NewCertificateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        certificateInformationValidator = new CertificateInformationValidator();
        certificateRepository = new CertificateRepository();

        createScene();
        setScene(newCertificateScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        HBox navigation = new HBox(15);
        VBox newCertificatePane = new VBox(15);

        newCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Create new Certificate");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label studentEmailLabel = new Label("Email student:");
        TextField studentEmailInput = new TextField();

        Label courseNameLabel = new Label("Course name:");
        TextField courseNameInput = new TextField();

        Label scoreLabel = new Label("Grade: ");
        TextField scoreInput = new TextField();

        Label staffNameLabel = new Label("Staff name: ");
        TextField staffNameInput = new TextField();

        Button createCertificateButton = new Button("Create new Certificate");
        Label messageLabel = new Label();

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCertificateScene) getSceneObject("overviewCertificateScene")).resetScene();
            showScene("overviewCertificateScene");
        });

        createCertificateButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!studentEmailInput.getText().isEmpty() && !courseNameInput.getText().isEmpty() && !scoreInput.getText().isEmpty() && !staffNameInput.getText().isEmpty()) {

                InformationFormatter informationFormatter = new InformationFormatter();
                String studentEmail = studentEmailInput.getText().toLowerCase().strip();
                String courseName = informationFormatter.capitalizeString(courseNameInput.getText().toLowerCase());
                String staffName = informationFormatter.capitalizeString(staffNameInput.getText().toLowerCase());
                int score = Integer.parseInt(scoreInput.getText());

                String response = null;
                try {
                    response = certificateInformationValidator.validateNewCertificate(studentEmail, courseName, score);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    try {
                        certificateRepository.createCertificate(studentEmail, courseName, score, staffName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Clearing all fields
                    studentEmailInput.clear();
                    courseNameInput.clear();
                    scoreInput.clear();
                    staffNameInput.clear();

                    messageLabel.setText("The Certificate has been created!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(newCertificatePane);

        header.getChildren().addAll(title, navigation);
        navigation.getChildren().addAll(homeButton, backButton);

        newCertificatePane.getChildren().addAll(studentEmailLabel, studentEmailInput, courseNameLabel, courseNameInput, scoreLabel, scoreInput, staffNameLabel, staffNameInput, createCertificateButton, messageLabel);
    }

    public void resetScene() {
        createScene();
        setScene(newCertificateScene);
    }

}
