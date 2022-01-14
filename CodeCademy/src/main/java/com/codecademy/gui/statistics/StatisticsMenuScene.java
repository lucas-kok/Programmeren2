package com.codecademy.gui.statistics;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsMenuScene extends GUIScene {
    private Scene statisticsMenuScene;
    private final int sceneWidth;
    private final int sceneHeight;

    public StatisticsMenuScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(statisticsMenuScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        VBox buttonsBox = new VBox(15);
        VBox headerPane = new VBox(15);
        HBox navigationBox = new HBox(15);

        headerPane.setAlignment(Pos.CENTER);
        navigationBox.setAlignment(Pos.CENTER);

        statisticsMenuScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        final Label title = new Label("Statistics Menu");
        Button statisticsButton = new Button("Gender Statistics");
        Button topCourseButton = new Button ("Top 3 highest");
        Button topWebcastButton = new Button("Top 3 Webcasts");

        Button home = new Button("Home");

        // Event Handlers
        statisticsButton.setOnAction((event) -> {
            ((StatisticsScene)getSceneObject("statisticsScene")).resetScene();
            showScene("statisticsScene");
        });

        topCourseButton.setOnAction((event) -> {
            ((TopCourseScene)getSceneObject("topCourseScene")).resetScene();
            showScene("topCourseScene");
        });

        topWebcastButton.setOnAction((event) -> {
            ((TopWebcastsScene)getSceneObject("topWebcastScene")).resetScene();
            showScene("topWebcastScene");
        });

        home.setOnAction(event -> {
            showScene("mainScene");
        });

        // Appending
        buttonsBox.getChildren().addAll(statisticsButton, topCourseButton, topWebcastButton);

        mainPane.setTop(headerPane);
        mainPane.setCenter(buttonsBox);

        headerPane.getChildren().addAll(title, navigationBox);
        navigationBox.getChildren().addAll(home);

        buttonsBox.setAlignment(Pos.CENTER);
        title.setAlignment(Pos.CENTER);
    }

    public void resetScene() {
        createScene();
        setScene(statisticsMenuScene);
    }
}
