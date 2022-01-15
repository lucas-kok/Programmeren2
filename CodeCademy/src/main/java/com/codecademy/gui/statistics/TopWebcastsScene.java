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


public class TopWebcastsScene extends GUIScene {
    private Scene TopWebcastsScene;
    private final int sceneWidth;
    private final int sceneHeight;
    private final StatisticsRepository statisticsRepository;

    public TopWebcastsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.statisticsRepository = new StatisticsRepository(); 

        createScene();
        setScene(TopWebcastsScene);
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

        TopWebcastsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        final Label title = new Label("Statistics Menu");

        Label topThreeWebcasts = new Label("Top 3 most viewed webcasts");
        Label topOne = new Label("1. ");
        Label topTwo = new Label("2. ");
        Label topThree = new Label("3. ");

        Button home = new Button("Home");
        Button back = new Button("Back");

        try {
            String[] topThreeWebcastsList = statisticsRepository.getTopThreeWebcasts();
            topOne.setText("1. " + topThreeWebcastsList[0]);
            topTwo.setText("2. " + topThreeWebcastsList[1]);
            topThree.setText("3. " + topThreeWebcastsList[2]);
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
        overviewPane.getChildren().addAll(topThreeWebcasts, topThreeList);

        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewPane);
        headerPane.getChildren().addAll(title, navigationBox);
        navigationBox.getChildren().addAll(home, back);
        topThreeList.getChildren().addAll(topOne,topTwo,topThree);
    }

    public void resetScene() {
        createScene();
        setScene(TopWebcastsScene);
    }
}
