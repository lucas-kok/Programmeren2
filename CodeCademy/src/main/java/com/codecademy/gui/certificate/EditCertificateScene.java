package com.codecademy.gui.certificate;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.certificate.Certificate;
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

public class EditCertificateScene extends GUIScene {

    private Scene editCertificateScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final CertificateInformationValidator certificateInformationValidator;
    private final CertificateRepository certificateRepository;
    private Certificate selectedCertificate;

    public EditCertificateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        certificateInformationValidator = new CertificateInformationValidator();
        certificateRepository = new CertificateRepository();

        // Not creating a scene, because when initializing the GUI no Certificate has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox editStudentPane = new VBox(15);

        editCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Edit Certificate: " + selectedCertificate.getCertificateID());
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");
        Button deleteCertificateButton = new Button("Delete");

        Label studentEmail = new Label("Student: " + selectedCertificate.getStudentEmail());

        Label editStaffNameLabel = new Label("Staff Name:");
        TextField editStaffNameInput = new TextField();

        Label editScoreLabel = new Label("Score:");
        TextField editScoreTextInput = new TextField();

        Button updateSelectedCertificateButton = new Button("Update Certificate");
        Label messageLabel = new Label("");

        // Setting the TextFields to the info of the selected Certificate
        editScoreTextInput.setText(String.valueOf(selectedCertificate.getScore()));
        editStaffNameInput.setText(selectedCertificate.getStaffName());

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCertificatesScene) getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        deleteCertificateButton.setOnAction((event) -> {
            certificateRepository.deleteCertificate(selectedCertificate);

            ((OverviewCertificatesScene) getSceneObject("overviewCertificatesScene")).resetScene();
            showScene("overviewCertificatesScene");
        });

        updateSelectedCertificateButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!editScoreTextInput.getText().isEmpty() && !editStaffNameInput.getText().isEmpty()) {
                int score = Integer.parseInt(editScoreTextInput.getText());
                String staffName = editStaffNameInput.getText();

                String response = certificateInformationValidator.validateEditedCertificate(score);
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    certificateRepository.updateCertificate(selectedCertificate, staffName, score);
                    messageLabel.setText("The Certificate has successfully been updated!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(editStudentPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton, deleteCertificateButton);

        editStudentPane.getChildren().addAll(studentEmail, editStaffNameLabel, editStaffNameInput, editScoreLabel, editScoreTextInput, updateSelectedCertificateButton, messageLabel);
    }

    public void resetScene(Certificate selectedCertificate) {
        if (selectedCertificate == null) return;

        this.selectedCertificate = selectedCertificate;

        createScene();
        setScene(editCertificateScene);
    }
}
