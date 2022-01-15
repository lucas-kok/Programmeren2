package com.codecademy.gui.student;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.student.StudentRepository;
import com.codecademy.informationhandling.validators.StudentInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class NewStudentScene extends GUIScene {
    private Scene newStudentScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final StudentInformationValidator studentInformationValidator;
    private final StudentRepository studentRepository;

    public NewStudentScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        studentInformationValidator = new StudentInformationValidator();
        studentRepository = new StudentRepository();

        createScene();
        setScene(newStudentScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox newStudentPane = new VBox(15);
        HBox studentBirthdayPane = new HBox(5);

        newStudentScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Create new Student");
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
        TextField studentBirthdayDayInput = new TextField();
        TextField studentBirthdayMonthInput = new TextField();
        TextField studentBirthdayYearInput = new TextField();

        studentBirthdayDayInput.setPromptText("Day");
        studentBirthdayMonthInput.setPromptText("Month");
        studentBirthdayYearInput.setPromptText("Year");

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
            if (!studentNameInput.getText().isBlank() && !studentEmailInput.getText().isEmpty() && !studentAddressInput.getText().isEmpty() && !studentCityInput.getText().isEmpty() &&
                    !studentPostalCodeInput.getText().isEmpty() && !studentCountryInput.getText().isEmpty() &&studentGenderInput.getValue() != null &&
                    !studentBirthdayDayInput.getText().isBlank() && !studentBirthdayMonthInput.getText().isBlank() && !studentBirthdayYearInput.getText().isBlank()) {
                String name = studentNameInput.getText();
                String email = studentEmailInput.getText();
                String address = studentAddressInput.getText();
                String postalCode = studentPostalCodeInput.getText();
                String city = studentCityInput.getText();
                String country = studentCountryInput.getText();
                String gender = studentGenderInput.getValue();

                String birthdayDay = studentBirthdayDayInput.getText();
                String birthdayMonth = studentBirthdayMonthInput.getText();
                String birthdayYear = studentBirthdayYearInput.getText();
                String[] birthdayPieces = new String[] { birthdayDay, birthdayMonth, birthdayYear };

                String response = null;
                try {
                    response = studentInformationValidator.validateNewStudent(name, email, postalCode, birthdayPieces);
                    messageLabel.setText(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if (response.isBlank()) { // No errors, all inputs are valid
                    // Create new Student
                    String birthday = birthdayPieces[2] + "-" + birthdayPieces[1] + "-" + birthdayPieces[0];
                    studentRepository.createStudent(new Student(email, name, birthday, gender, address, city, country, postalCode));

                    // Clearing all fields
                    studentNameInput.clear();
                    studentEmailInput.clear();
                    studentAddressInput.clear();
                    studentPostalCodeInput.clear();
                    studentCityInput.clear();
                    studentCountryInput.clear();
                    studentGenderInput.setValue(null);
                    studentBirthdayDayInput.clear();
                    studentBirthdayMonthInput.clear();
                    studentBirthdayYearInput.clear();

                    messageLabel.setText("The Student '" + name + "' has successfully been created!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(newStudentPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton);

        newStudentPane.getChildren().addAll(studentNameLabel, studentNameInput, studentEmailLabel, studentEmailInput,
                studentAddressLabel, studentAddressInput, studentPostalCodeLabel, studentPostalCodeInput, studentCityLabel,
                studentCityInput, studentCountryLabel, studentCountryInput, studentGenderLabel, studentGenderInput,
                studentBirthdayLabel, studentBirthdayPane, createStudentButton, messageLabel);

        studentBirthdayPane.getChildren().addAll(studentBirthdayDayInput, studentBirthdayMonthInput, studentBirthdayYearInput);
    }

    public void resetScene() {
        createScene();
        setScene(newStudentScene);
    }
}
