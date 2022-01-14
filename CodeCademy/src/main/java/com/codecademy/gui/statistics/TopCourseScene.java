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


public class TopCourseScene extends GUIScene {
    private Scene TopCourseScene;
    private final int sceneWidth;
    private final int sceneHeight;

    public TopCourseScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(TopCourseScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        HBox header = new HBox();

        TopCourseScene = new Scene(mainPane, sceneWidth, sceneHeight);

        String numberOne = null;
        String numberTwo = null;
        String numberThree = null;

        //Nodes
        final Label label = new Label("Top 3 Most certificates per course");
        ListView<String> certificatesList = new ListView<>();
        Button buttonHome = new Button("Home");


        // Appending
        certificatesList.getItems().add(numberOne);
        certificatesList.getItems().add(numberTwo);
        certificatesList.getItems().add(numberThree);

        header.getChildren().addAll(label, buttonHome);
        buttonHome.setAlignment(Pos.CENTER_RIGHT);

        mainPane.setTop(header);
        mainPane.setCenter(certificatesList);

    }

    public void resetScene() {
        createScene();
        setScene(TopCourseScene);
    }
}

