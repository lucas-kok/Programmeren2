//package com.codecademy.gui.certificate;
//
//import com.codecademy.gui.GUI;
//import com.codecademy.gui.GUIScene;
//import com.codecademy.informationhandling.certificate.Certificate;
//import com.codecademy.informationhandling.certificate.CertificateRepository;
//import com.codecademy.informationhandling.validators.CertificateInformationValidator;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//public class EditCertificateScene extends GUIScene {
//
//    private Scene editCertificateScene;
//    private final int sceneWidth;
//    private final int sceneHeight;
//
//    private final GUI gui;
//    private final CertificateInformationValidator certificateInformationValidator;
//    private final CertificateRepository certificateRepository;
//    private Certificate selectedCertificate;
//
//    public EditCertificateScene(GUI gui, int sceneWidth, int sceneHeight, Certificate selectedCertificate) {
//        super(gui);
//
//        this.sceneWidth = sceneWidth;
//        this.sceneHeight = sceneHeight;
//
//        this.gui = gui;
//        certificateInformationValidator = new CertificateInformationValidator();
//        certificateRepository = new CertificateRepository();
//
//        this.selectedCertificate = selectedCertificate;
//        if (selectedCertificate != null) {
//            createScene();
//            setScene(editCertificateScene);
//        }
//    }
//
//    private void createScene() {
//        // Panes & Scene
//        BorderPane mainPane = new BorderPane();
//        VBox header = new VBox(15);
//        HBox navigation = new HBox(15);
//        VBox editStudentPane = new VBox(15);
//
//        editCertificateScene = new Scene(mainPane, sceneWidth, sceneHeight);
//
//        header.setAlignment(Pos.CENTER);
//
//        // Nodes
//        Label title = new Label("Edit Certificate");
//        Button homeButton = new Button("Home");
//        Button backButton = new Button("Back");
//        Button deleteCertificateButton = new Button("Delete");
//
//        Label editScoreLabel = new Label("Score:");
//        TextField editScoreTextInput = new TextField();
//
//        Label editStaffLabel = new Label("Score:");
//        TextField editStaffTextInput = new TextField();
//
//        Button updateSelectedCertificateButton = new Button("Update Certificate");
//        Label messageLabel = new Label("");
//
//        // Setting the TextFields to the students current info
//        editScoreTextInput.setText(String.valueOf(selectedCertificate.getScore()));
//        editStaffTextInput.setText(selectedCertificate.getStaffName());
//
//        // Event Handlers
//        homeButton.setOnAction((event) -> showScene("mainScene"));
//
//        backButton.setOnAction((event) -> {
//            ((OverviewCertificateScene) getSceneObject("overviewCertificateScene")).resetScene();
//            showScene("overviewCertificateScene");
//        });
//
//        deleteCertificateButton.setOnAction((event) -> {
//            certificateRepository.deleteCertificate(selectedCertificate);
//
//            ((OverviewCertificateScene) getSceneObject("overviewCertificateScene")).resetScene();
//            showScene("overviewCertificateScene");
//        });
//
//        updateSelectedCertificateButton.setOnAction((event) -> {
//            // Only proceed if all fields are filled in
//            if (!editScoreTextInput.getText().isEmpty() && !editStaffTextInput.getText().isEmpty()) {
//                int score = Integer.parseInt(editScoreTextInput.getText());
//                String staff = editStaffTextInput.getText();
//
//                String response = null;
//                response = certificateInformationValidator.validateEditedCertificate(score);
//
//                messageLabel.setText(response);
//
//                if (response.isBlank()) { // No errors, all inputs are valid
//                    certificateRepository.updateCertificate(selectedCertificate, staff, score);
//                    messageLabel.setText("The Certificate has successfully been updated!");
//                }
//
//            } else {
//                messageLabel.setText("Please fill in all the fields!");
//            }
//        });
//
//        // Appending
//        mainPane.setTop(header);
//        mainPane.setCenter(editStudentPane);
//
//        header.getChildren().addAll(title, navigation);
//        navigation.getChildren().addAll(homeButton, backButton, deleteCertificateButton);
//
//        editStudentPane.getChildren().addAll(editScoreLabel, editScoreTextInput, editStaffLabel, editStaffTextInput, updateSelectedCertificateButton, messageLabel);
//    }
//
//    public void resetScene(Certificate selectedCertificate) {
//        if (selectedCertificate == null) return;
//
//        this.selectedCertificate = selectedCertificate;
//        createScene();
//        setScene(editCertificateScene);
//    }
//
//}
