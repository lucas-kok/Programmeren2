package com.codecademy.gui.statistics;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import com.codecademy.informationhandling.statistics.StatisticsRepository;
import java.sql.SQLException;


public class TopCourseScene extends GUIScene {
    private Scene TopCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;
    private final StatisticsRepository statisticsRepository;

    public TopCourseScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.statisticsRepository = new StatisticsRepository();

        createScene();
        setScene(TopCourseScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        VBox overviewPane = new VBox(15);
        HBox navigationBox = new HBox(15);
        VBox topThreeList = new VBox(10);

        headerPane.setAlignment(Pos.CENTER);
        overviewPane.setAlignment(Pos.CENTER);
        navigationBox.setAlignment(Pos.CENTER);
        topThreeList.setAlignment(Pos.CENTER);

        TopCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        final Label title = new Label("Statistics Menu");

        Label topThreeCourses = new Label("Top 3 courses with the most certificates");
        Label topOne = new Label("1. ");
        Label topTwo = new Label("2. ");
        Label topThree = new Label("3. ");

        Button home = new Button("Home");
        Button back = new Button("Back");

        try {
            String[] topThreeCoursesList = statisticsRepository.getTopThreeCourses();
            topOne.setText("1. " + topThreeCoursesList[0]);
            topTwo.setText("2. " + topThreeCoursesList[1]);
            topThree.setText("3. " + topThreeCoursesList[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        home.setOnAction(event -> {
            showScene("mainScene");
        });

        back.setOnAction(event -> {
            showScene("statisticsMenuScene");
        });

        // Appending
        overviewPane.getChildren().addAll(topThreeCourses, topThreeList);

        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewPane);
        headerPane.getChildren().addAll(title, navigationBox);
        navigationBox.getChildren().addAll(home, back);
        topThreeList.getChildren().addAll(topOne,topTwo,topThree);
    }

    public void resetScene() {
        createScene();
        setScene(TopCourseScene);
    }
}

