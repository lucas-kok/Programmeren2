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
        HBox buttonsBox = new HBox(2);
        HBox listBox = new HBox();
        HBox header = new HBox();

        // Database connection to be implemented
        String totApplied = null;
        String perPassed = null;

        statisticsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        //Nodes
        final Label label = new Label("Gender Filtered");
        ListView<String> totAppliedList = new ListView<>();
        ListView<String> perPassedList = new ListView<>();
        Button buttonMale = new Button("Male");
        Button buttonFemale = new Button("Female");
        Button buttonOther = new Button("Other");
        Button buttonHome = new Button("Home");

        // Event Handlers
        buttonFemale.setOnAction((event) -> {
            // Filter on Females
        });

        buttonMale.setOnAction((event) -> {
            // Filter on Male
        });

        buttonOther.setOnAction((event) -> {
            // Filter on Other
        });

        buttonHome.setOnAction((event) ->  {
            ((MainScene)getSceneObject("MainScene")).resetScene();
            showScene("MainScene");
        });

        // Appending
        buttonsBox.setAlignment(Pos.CENTER);
        header.getChildren().addAll(label, buttonHome);
        buttonHome.setAlignment(Pos.CENTER_RIGHT);

        totAppliedList.getItems().add(totApplied);
        perPassedList.getItems().add(perPassed);

        listBox.getChildren().addAll(totAppliedList, perPassedList);
        buttonsBox.getChildren().addAll(buttonFemale, buttonOther, buttonMale);

        mainPane.setTop(header);
        mainPane.setCenter(listBox);
        mainPane.setBottom(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER);

    }

    public void resetScene() {
        createScene();
        setScene(statisticsScene);
    }
}
