package com.codecademy.gui.course;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.Course.Course;
import com.codecademy.informationhandling.Course.CourseRepository;
import com.codecademy.informationhandling.validators.CourseInformationValidator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewCourseScene extends GUIScene {

    private Scene newCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    private final GUI gui;
    private final CourseInformationValidator courseInformationValidator;
    private final CourseRepository courseRepository;

    public NewCourseScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.gui = gui;
        courseInformationValidator = new CourseInformationValidator();
        courseRepository = new CourseRepository();

        createScene();
        setScene(newCourseScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox header = new VBox(15);
        HBox navigation = new HBox(15);
        VBox newCoursePane = new VBox(15);

        newCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        header.setAlignment(Pos.CENTER);

        // Nodes
        Label title = new Label("Create new Course");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label courseNameLabel = new Label("Name:");
        TextField courseNameInput = new TextField();

        Label courseSubjectLabel = new Label("Subject:");
        TextField courseSubjectInput = new TextField();

        Label courseIntroductionTextLabel = new Label("IntroductionText");
        TextArea courseIntroductionTextInput = new TextArea();

        Label courseLevelLabel = new Label("Level:");
        ComboBox<String> courseLevelInput = new ComboBox<>();
        courseLevelInput.getItems().addAll("Beginner", "Advanced", "Expert");

        Label courseRelatedCoursesLabel = new Label("Related Courses (Split with ', ' and Case-Sensitive)");
        TextField courseRelatedCoursesInput = new TextField();

        Button createCourseButton = new Button("Create new Course");
        Label messageLabel = new Label();

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCoursesScene) getSceneObject("overviewCoursesScene")).resetScene();
            showScene("overviewCoursesScene");
        });

        createCourseButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!courseNameInput.getText().isBlank() && !courseSubjectInput.getText().isEmpty() &&
                    !courseIntroductionTextInput.getText().isEmpty() && courseLevelInput.getValue() != null) {
                String name = courseNameInput.getText();
                String subject = courseSubjectInput.getText();
                String introductionText = courseIntroductionTextInput.getText();
                String level = courseLevelInput.getValue();
                String relatedCoursesString = courseRelatedCoursesInput.getText();

                String response = courseInformationValidator.validateNewCourse(name, relatedCoursesString, gui.getCourses());
                messageLabel.setText(response);

                if (response.isBlank()) { // No errors, all inputs are valid
                    courseRepository.createNewCourse(name, subject, introductionText, level, relatedCoursesString);

                    // Clearing all fields
                    courseNameInput.clear();
                    courseSubjectInput.clear();
                    courseIntroductionTextInput.clear();
                    courseLevelInput.setValue(null);
                    courseRelatedCoursesInput.clear();

                    messageLabel.setText("The Course '" + name + "' has successfully been created!");
                }

            } else {
                messageLabel.setText("Please fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(header);
        mainPane.setCenter(newCoursePane);

        header.getChildren().addAll(title, navigation);
        navigation.getChildren().addAll(homeButton, backButton);

        newCoursePane.getChildren().addAll(courseNameLabel, courseNameInput, courseSubjectLabel, courseSubjectInput,
                courseIntroductionTextLabel, courseIntroductionTextInput, courseLevelLabel, courseLevelInput,
                courseRelatedCoursesLabel, courseRelatedCoursesInput, createCourseButton, messageLabel);
    }

    public void resetScene() {
        createScene();
        setScene(newCourseScene);
    }

}
