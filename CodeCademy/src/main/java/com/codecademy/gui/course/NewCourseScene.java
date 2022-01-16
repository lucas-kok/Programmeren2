package com.codecademy.gui.course;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.informationhandling.contentitem.ContentItem;
import com.codecademy.informationhandling.contentitem.ContentItemRepository;
import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.course.CourseRepository;
import com.codecademy.informationhandling.validators.CourseInformationValidator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class NewCourseScene extends GUIScene {

    private Scene newCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;
    private ArrayList<ContentItem> selectedContentItems;
    private ArrayList<ContentItem> availableContentItems;

    private ScrollPane selectedContentItemsScroll;
    private ScrollPane availableContentItemsScroll;

    private final CourseInformationValidator courseInformationValidator;
    private final CourseRepository courseRepository;
    private final ContentItemRepository contentItemRepository;

    public NewCourseScene(GUI gui, int sceneWidth, int sceneHeight) throws SQLException{
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        selectedContentItems = new ArrayList<>();
        courseInformationValidator = new CourseInformationValidator();
        courseRepository = new CourseRepository();
        contentItemRepository = new ContentItemRepository();

        availableContentItems = contentItemRepository.getUnusedContentItems();

        createScene();
        setScene(newCourseScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox newCoursePane = new VBox(15);

        HBox contentItemsSelectionWrapperPane = new HBox(5);

        VBox newCourseSelectedContentItemsWrapper = new VBox(5);
        selectedContentItemsScroll = new ScrollPane();

        VBox availableModulesWrapper = new VBox(5);
        availableContentItemsScroll = new ScrollPane();

        newCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        headerPane.setAlignment(Pos.CENTER);

        // Nodes
        Label titleLabel = new Label("Create new Course");
        Button homeButton = new Button("Home");
        Button backButton = new Button("Back");

        Label courseNameLabel = new Label("Name:");
        TextField courseNameInput = new TextField();

        Label courseSubjectLabel = new Label("Subject:");
        TextField courseSubjectInput = new TextField();

        Label courseIntroductionTextLabel = new Label("Introduction text:");
        TextArea courseIntroductionTextInput = new TextArea();

        Label courseLevelLabel = new Label("Level:");
        ComboBox<String> courseLevelInput = new ComboBox<>();
        courseLevelInput.getItems().addAll("Beginner", "Advanced", "Expert");

        Label courseRelatedCoursesLabel = new Label("Related Courses (Split with ', ' and Case-Sensitive)");
        TextField courseRelatedCoursesInput = new TextField();

        Label selectedModulesLabel = new Label("Selected Modules:");
        Label availableModulesLabel = new Label("Available Modules:");

        Button createCourseButton = new Button("Create new Course");
        Label messageLabel = new Label();

        // Styling
        newCourseScene.setUserAgentStylesheet("/style.css");
        headerPane.setId("header");
        titleLabel.setId("title");
        navigationPane.setId("navigation");
        courseLevelInput.setId("clickable");
        createCourseButton.setId("actionButton");
        messageLabel.setId("errorMessage");

        // Event Handlers
        homeButton.setOnAction((event) -> showScene("mainScene"));

        backButton.setOnAction((event) -> {
            ((OverviewCoursesScene) getSceneObject("overviewCoursesScene")).resetScene();
            showScene("overviewCoursesScene");
        });

        createCourseButton.setOnAction((event) -> {
            // Only proceed if all fields are filled in
            if (!courseNameInput.getText().isBlank() && !courseSubjectInput.getText().isEmpty() &&
                    !courseIntroductionTextInput.getText().isEmpty() && courseLevelInput.getValue() != null && selectedContentItems.size() > 0) {
                String name = courseNameInput.getText();
                String subject = courseSubjectInput.getText();
                String introductionText = courseIntroductionTextInput.getText();
                String level = courseLevelInput.getValue();
                String relatedCoursesString = courseRelatedCoursesInput.getText();

                String response = null;
                try {
                    response = courseInformationValidator.validateNewCourse(name, relatedCoursesString);

                    messageLabel.setId("errorMessage");
                    messageLabel.setText(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                assert response != null;
                if (response.isBlank()) { // No errors, all inputs are valid
                    Course newCourse = new Course(name, subject, introductionText, level, relatedCoursesString);
                    courseRepository.createCourse(newCourse);
                    contentItemRepository.addContentItemsToCourse(newCourse, selectedContentItems);

                    // Clearing all fields
                    courseNameInput.clear();
                    courseSubjectInput.clear();
                    courseIntroductionTextInput.clear();
                    courseLevelInput.setValue(null);
                    courseRelatedCoursesInput.clear();
                    selectedContentItems = new ArrayList<>();

                    try {
                        availableContentItems = contentItemRepository.getUnusedContentItems();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    selectedContentItemsScroll.setContent(createSelectedContentItemsPane(selectedContentItems));
                    availableContentItemsScroll.setContent(createAvailableContentItemsPane(availableContentItems));

                    messageLabel.setId("goodMessage");
                    messageLabel.setText("\nThe Course '" + name + "' has successfully been created!");
                }

            } else {
                messageLabel.setId("errorMessage");
                messageLabel.setText("\nPlease fill in all the fields!");
            }
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(newCoursePane);

        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().addAll(homeButton, backButton);

        newCoursePane.getChildren().addAll(courseNameLabel, courseNameInput, courseSubjectLabel, courseSubjectInput,
                courseIntroductionTextLabel, courseIntroductionTextInput, courseLevelLabel, courseLevelInput,
                courseRelatedCoursesLabel, courseRelatedCoursesInput, contentItemsSelectionWrapperPane, createCourseButton, messageLabel);

        contentItemsSelectionWrapperPane.getChildren().addAll(newCourseSelectedContentItemsWrapper, availableModulesWrapper);
        newCourseSelectedContentItemsWrapper.getChildren().addAll(selectedModulesLabel, selectedContentItemsScroll);
        availableModulesWrapper.getChildren().addAll(availableModulesLabel, availableContentItemsScroll);

        selectedContentItemsScroll.setContent(createSelectedContentItemsPane(new ArrayList<>()));

        try {
            availableContentItems = contentItemRepository.getUnusedContentItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        availableContentItemsScroll.setContent(createAvailableContentItemsPane(availableContentItems));
    }

    // Function that will convert a list of the selected Content Items into a VBox
    private VBox createSelectedContentItemsPane(ArrayList<ContentItem> selectedContentItems) {
        VBox contentItemsListPane = new VBox(5);
        contentItemsListPane.setId("listPane");

        int index = 0;
        for (ContentItem contentItem : selectedContentItems) {
            // Panes
            HBox contentItemInformationRow = new HBox(10);

            // Nodes
            Label indexLabel = new Label(index + 1 + ". ");
            Label contentItemPublicationDateLabel = new Label(contentItem.getPublicationDate());
            Label informationDividerLabel = new Label("-");
            Label contentItemTitleLabel = new Label(contentItem.getTitle());

            // Styling
            contentItemInformationRow.setId("clickable");

            // Event Handlers
            contentItemInformationRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                selectedContentItems.remove(contentItem);

                selectedContentItemsScroll.setContent(createSelectedContentItemsPane(selectedContentItems));
                availableContentItemsScroll.setContent(createAvailableContentItemsPane(availableContentItems));
            });

            // Appending
            contentItemInformationRow.getChildren().addAll(indexLabel, contentItemPublicationDateLabel, informationDividerLabel, contentItemTitleLabel);
            contentItemsListPane.getChildren().add(contentItemInformationRow);

            index++;
        }

        return contentItemsListPane;
    }

    // Function that will convert a list of the available Content Items into a VBox
    private VBox createAvailableContentItemsPane(ArrayList<ContentItem> availableContentItems) {
        VBox contentItemsListPane = new VBox(5);
        contentItemsListPane.setId("listPane");

        int index = 0;
        for (ContentItem contentItem : availableContentItems) {
            if (!selectedContentItems.contains(contentItem)) {
                // Panes
                HBox contentItemInformationRow = new HBox(10);

                // Nodes
                Label indexLabel = new Label(index + 1 + ". ");
                Label contentItemPublicationDateLabel = new Label(contentItem.getPublicationDate());
                Label informationDividerLabel = new Label("-");
                Label contentItemTitleLabel = new Label(contentItem.getTitle());

                // Styling
                contentItemInformationRow.setId("clickable");

                // Event Handlers
                contentItemInformationRow.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                    if (!selectedContentItems.contains(contentItem)) { // Could be removed
                        selectedContentItems.add(contentItem);

                        availableContentItemsScroll.setContent(createAvailableContentItemsPane(availableContentItems));
                        selectedContentItemsScroll.setContent(createSelectedContentItemsPane(selectedContentItems));
                    }
                });

                // Appending
                contentItemInformationRow.getChildren().addAll(indexLabel, contentItemPublicationDateLabel, informationDividerLabel, contentItemTitleLabel);
                contentItemsListPane.getChildren().add(contentItemInformationRow);

                index++;
            }
        }

        return contentItemsListPane;
    }

    public void resetScene() {
        selectedContentItems = new ArrayList<>();
        createScene();
        setScene(newCourseScene);
    }
}
