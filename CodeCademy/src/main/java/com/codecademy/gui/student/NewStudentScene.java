package com.codecademy.gui.student;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.InformationHandler;
import com.codecademy.informationhandling.validators.StudentInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class NewStudentScene extends GUIScene {
    private Scene newStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final StudentInformationValidator studentInformationValidator;
    private final InformationHandler informationHandler;

    public NewStudentScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        studentInformationValidator = new StudentInformationValidator();
        informationHandler = new InformationHandler();

        createScene();
        setScene(newStudentScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        HBox navigation = new HBox(15);
        VBox newStudentPane = new VBox(15);

        newStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Create new Student");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label studentNameLabel = new Label("Name:");
        TextField studentNameInput = new TextField();

        Label studentEmailLabel = new Label("Email:");
        TextField studentEmailInput = new TextField();

        Label studentAddressLabel = new Label("Address:");
        TextField studentAddressInput = new TextField();

        Label studentPostalCodeLabel = new Label("Postal Code:");
        TextField studentPostalCodeInput = new TextField();

        Label studentCityLabel = new Label("City:");
        TextField studentCityInput = new TextField();

        Label studentCountryLabel = new Label("Country:");
        TextField studentCountryInput = new TextField();

        Label studentGenderLabel = new Label("Gender:");
        ComboBox<String> studentGenderInput = new ComboBox<>();
        studentGenderInput.getItems().addAll("Male", "Female", "Other");

        Label studentBirthdayLabel = new Label("Birthday:");
        DatePicker studentBirthdayInput = new DatePicker();

        Button createStudentButton = new Button("Create new Student");
        Label messageLabel = new Label();

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewStudentsScene) getSceneObject("overviewStudentsScene")).resetScene();
            showScene("overviewStudentsScene");
        });

        createStudentButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!studentNameInput.getText().isBlank() && !studentEmailInput.getText().isEmpty() && !studentAddressInput.getText().isEmpty() &&
                    !studentCityInput.getText().isEmpty() && !studentPostalCodeInput.getText().isEmpty() && !studentCountryInput.getText().isEmpty() &&
                    studentGenderInput.getValue() != null && studentBirthdayInput.getValue() != null) {
                String name = studentNameInput.getText();
                String email = studentEmailInput.getText();
                String address = studentAddressInput.getText();
                String postalCode = studentPostalCodeInput.getText();
                String city = studentCityInput.getText();
                String country = studentCountryInput.getText();
                String gender = studentGenderInput.getValue();
                LocalDate birthday = studentBirthdayInput.getValue();

                String response = studentInformationValidator.validateNewStudent(name, email, postalCode, birthday, gui.getStudents());
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    informationHandler.createNewStudent(name, email, address, postalCode, city, country, gender, birthday);

                    // Clearing all fields
                    studentNameInput.clear();
                    studentEmailInput.clear();
                    studentAddressInput.clear();
                    studentPostalCodeInput.clear();
                    studentCityInput.clear();
                    studentCountryInput.clear();
                    studentGenderInput.setValue(null);
                    studentBirthdayInput.setValue(null);

                    messageLabel.setText("The Student '" + name + "' has successfully been created!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(newStudentPane);

        header.getChildren().addAll(title, navigation);
        navigation.getChildren().addAll(homeButton, backButton);

        newStudentPane.getChildren().addAll(studentNameLabel, studentNameInput, studentEmailLabel, studentEmailInput,
                studentAddressLabel, studentAddressInput, studentPostalCodeLabel, studentPostalCodeInput, studentCityLabel,
                studentCityInput, studentCountryLabel, studentCountryInput, studentGenderLabel, studentGenderInput,
                studentBirthdayLabel, studentBirthdayInput, createStudentButton, messageLabel);
    }

    public void resetScene() {
        createScene();
        setScene(newStudentScene);
    }

}