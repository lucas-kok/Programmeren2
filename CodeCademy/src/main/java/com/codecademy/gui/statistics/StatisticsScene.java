package com.codecademy.gui.statistics;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.MainScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;


public class StatisticsScene extends GUIScene {
    private Scene statisticsScene;
    private final int sceneWidth;
    private final int sceneHeight;

    public StatisticsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(statisticsScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        VBox overviewPane = new VBox(15);
        HBox buttonBox = new HBox(15);
        HBox navigationBox = new HBox(15);

        headerPane.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        overviewPane.setAlignment(Pos.CENTER);
        navigationBox.setAlignment(Pos.CENTER);

        statisticsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        final Label title = new Label("Statistics Menu");

        Label selectedGender = new Label("Selected Gender: ");
        Label totalApplied = new Label("Applicants: ");
        Label totalCertified = new Label("Certificates: ");

        Button male = new Button("Male");
        Button female = new Button("Female");
        Button other = new Button("Other");

        Button home = new Button("Home");
        Button back = new Button("Back");

        //Actions
        male.setOnAction(event -> {
            selectedGender.setText("Selected Gender: Male");
        });

        female.setOnAction(event -> {
            selectedGender.setText("Selected Gender: Female");
        });

        other.setOnAction(event -> {
            selectedGender.setText("Selected Gender: Other");
        });

        home.setOnAction(event -> {
            showScene("mainScene");
        });

        back.setOnAction(event -> {
            showScene("statisticsMenuScene");
        });

        // Appending
        buttonBox.getChildren().addAll(male, female, other);
        overviewPane.getChildren().addAll(selectedGender, totalApplied, totalCertified, buttonBox);

        mainPane.setTop(headerPane);
        mainPane.setCenter(overviewPane);
        headerPane.getChildren().addAll(title, navigationBox);
        navigationBox.getChildren().addAll(home, back);
    }

    public void resetScene() {
        createScene();
        setScene(statisticsScene);
    }
}
