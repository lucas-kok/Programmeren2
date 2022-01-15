package com.codecademy.gui.statistics;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import com.codecademy.informationhandling.statistics.StatisticsRepository;

import java.sql.SQLException;


public class OverviewStatisticsScene extends GUIScene {
    private Scene statisticsScene;
    private final int sceneWidth;
    private final int sceneHeight;
    private final StatisticsRepository statisticsRepository;

    public OverviewStatisticsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.statisticsRepository = new StatisticsRepository();

        createScene();
        setScene(statisticsScene);
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox overviewStatisticsPane = new VBox(15);
        HBox genderButtonsPane = new HBox(15);

        headerPane.setAlignment(Pos.CENTER);
        genderButtonsPane.setAlignment(Pos.CENTER);
        overviewStatisticsPane.setAlignment(Pos.CENTER);
        navigationPane.setAlignment(Pos.CENTER);

        statisticsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label titleLabel = new Label("Statistics Menu");
        Button homeButton = new Button("Home");

        Label selectedGenderLabel = new Label("Selected Gender:");
        Label applicantsStatisticLabel = new Label("Applicants:");
        Label certificatesStatisticLabel = new Label("Certificates:");
        Label successRateStatisticLabel = new Label("Success rate:");

        Button maleButton = new Button("Male");
        Button femaleButton = new Button("Female");
        Button otherButton = new Button("Other");

        Label topThreeCoursesLabel = new Label("Top 3 courses with the most certificates");
        Label firstCourseLabel = new Label("1.");
        Label secondCourseLabel = new Label("2.");
        Label thirdCourseLabel = new Label("3.");

        Label topThreeWebcastsLabel = new Label("Top 3 most viewed webcasts");
        Label firstWebcastLabel = new Label("1.");
        Label secondWebcastLabel = new Label("2.");
        Label thirdWebcastLabel = new Label("3.");

        try {
            String[] topThreeCoursesList = statisticsRepository.getTopThreeCourses();
            setTopLabels(firstCourseLabel, secondCourseLabel, thirdCourseLabel, topThreeCoursesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String[] topThreeWebcastsList = statisticsRepository.getTopThreeWebcasts();
            setTopLabels(firstWebcastLabel, secondWebcastLabel, thirdWebcastLabel, topThreeWebcastsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Event Handlers
        maleButton.setOnAction((event) -> {
            selectedGenderLabel.setText("Selected Gender: Male");
            try {
                String[] genderStatisticPieces = statisticsRepository.getCertificatePercentage("m");
                updateStatistics(applicantsStatisticLabel, certificatesStatisticLabel, successRateStatisticLabel, genderStatisticPieces);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        femaleButton.setOnAction((event) -> {
            selectedGenderLabel.setText("Selected Gender: Female");
            try {
                String[] genderStatisticPieces = statisticsRepository.getCertificatePercentage("f");
                updateStatistics(applicantsStatisticLabel, certificatesStatisticLabel, successRateStatisticLabel, genderStatisticPieces);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        otherButton.setOnAction((event) -> {
            selectedGenderLabel.setText("Selected Gender: Other");
            try {
                String[] genderStatisticPieces = statisticsRepository.getCertificatePercentage("x");
                updateStatistics(applicantsStatisticLabel, certificatesStatisticLabel, successRateStatisticLabel, genderStatisticPieces);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        homeButton.setOnAction((event) -> showScene("mainScene"));

        // Appending
        genderButtonsPane.getChildren().addAll(maleButton, femaleButton, otherButton);
        overviewStatisticsPane.getChildren().addAll(selectedGenderLabel, applicantsStatisticLabel, certificatesStatisticLabel, successRateStatisticLabel, genderButtonsPane,
                topThreeCoursesLabel, firstCourseLabel, secondCourseLabel, thirdCourseLabel, topThreeWebcastsLabel, firstWebcastLabel, secondWebcastLabel, thirdWebcastLabel);

        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewStatisticsPane);
        headerPane.getChildren().addAll(titleLabel, navigationPane);
        navigationPane.getChildren().add(homeButton);
    }

    private void setTopLabels(Label firstLabel, Label secondLabel, Label thirdLabel, String[] topPieces) {
        firstLabel.setText("1. " + topPieces[0]);
        secondLabel.setText("2. " +  topPieces[1]);
        thirdLabel.setText("3. " + topPieces[2]);
    }

    private void updateStatistics(Label applicantsLabel, Label certificatesLabel, Label successRateLabel, String[] genderStatisticPieces) {
        applicantsLabel.setText("Applicants: " +  genderStatisticPieces[0]);
        certificatesLabel.setText("certificates: " + genderStatisticPieces[1]);
        successRateLabel.setText("Success rate: " + genderStatisticPieces[2] + "%");
    }

    public void resetScene() {
        createScene();
        setScene(statisticsScene);
    }
}
