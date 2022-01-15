package com.codecademy.gui.course;

import com.codecademy.informationhandling.course.CourseRepository;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.registration.RegistrationRepository;
import com.codecademy.informationhandling.validators.CourseInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class EditCourseScene extends GUIScene {
    private Scene editCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final CourseInformationValidator courseInformationValidationTools;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    private Course selectedCourse;

    public EditCourseScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        courseInformationValidationTools = new CourseInformationValidator();
        courseRepository = new CourseRepository();
        registrationRepository = new RegistrationRepository();

        // Not creating a scene, because when initializing the GUI no Course has been selected
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox editStudentPane = new VBox(15);

        editCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Edit Course: " + selectedCourse.getName());
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");
        Button deleteCourseButton = new Button("Delete");

        Label courseNameLabel = new Label("Name:");
        TextField courseNameInput = new TextField();

        Label courseSubjectLabel = new Label("Subject:");
        TextField courseSubjectInput = new TextField();

        Label courseIntroductionTextLabel = new Label("Introduction:");
        TextArea courseIntroductionTextInput = new TextArea();

        Label courseLevelLabel = new Label("Level:");
        ComboBox<String> courseLevelInput = new ComboBox<>();
        courseLevelInput.getItems().addAll("Beginner", "Advanced", "Expert");

        Label courseRelatedCoursesLabel = new Label("Related Courses:");
        TextField courseRelatedCoursesInput = new TextField();

        Button updateSelectedCourseButton = new Button("Update Course");
        Label messageLabel = new Label("");

        // Setting the TextFields to the info of the selected Course
        courseNameInput.setText(selectedCourse.getName());
        courseSubjectInput.setText(selectedCourse.getSubject());
        courseIntroductionTextInput.setText(selectedCourse.getIntroductionText());
        courseLevelInput.setValue(selectedCourse.getLevel());
        courseRelatedCoursesInput.setText(selectedCourse.getRelatedCoursesAsString());

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCoursesScene) getSceneObject("overviewCoursesScene")).resetScene();
            showScene("overviewCoursesScene");
        });

        deleteCourseButton.setOnAction((event) -> {
            // Prevent the Course from getting deleted when it has Registrations for the Course
            try {
                if (!courseInformationValidationTools.courseHasRegistrations(selectedCourse.getName(), registrationRepository.getAllRegistrations())) {
                    courseRepository.deleteCourse(selectedCourse);

                    ((OverviewCoursesScene) getSceneObject("overviewCoursesScene")).resetScene();
                    showScene("overviewCoursesScene");
                } else {
                    messageLabel.setText("The Course can't be deleted because it has Registrations!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        updateSelectedCourseButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!courseNameInput.getText().isEmpty() && !courseSubjectInput.getText().isEmpty() && !courseIntroductionTextInput.getText().isEmpty() &&
                    courseLevelInput.getValue() != null) {
                String name = courseNameInput.getText();
                String subject = courseSubjectInput.getText();
                String introductionText = courseIntroductionTextInput.getText();
                String level = courseLevelInput.getValue();
                String relatedCourses = courseRelatedCoursesInput.getText();

                String response = null;
                try {
                    response = courseInformationValidationTools.validateEditedCourse(name, relatedCourses, gui.getCourses(), selectedCourse);
                    messageLabel.setText(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if (response.isBlank()) { // No errors, all inputs are valid
                    courseRepository.updateCourse(selectedCourse, name, subject, introductionText, level, relatedCourses);
                    messageLabel.setText("The Course '" + name + "' has successfully been updated!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(editStudentPane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton, deleteCourseButton);

        editStudentPane.getChildren().addAll(courseNameLabel, courseNameInput, courseSubjectLabel, courseSubjectInput,
                courseIntroductionTextLabel, courseIntroductionTextInput, courseLevelLabel, courseLevelInput,
                courseRelatedCoursesLabel, courseRelatedCoursesInput, updateSelectedCourseButton, messageLabel);
    }

    public void resetScene(Course selectedCourse) {
        if (selectedCourse == null) return;

        this.selectedCourse = selectedCourse;

        createScene();
        setScene(editCourseScene);
    }
}
