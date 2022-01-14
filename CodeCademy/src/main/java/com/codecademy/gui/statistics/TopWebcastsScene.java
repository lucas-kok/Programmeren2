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


public class TopWebcastsScene extends GUIScene {
    private Scene TopWebcastsScene;
    private final int sceneWidth;
    private final int sceneHeight;

    public TopWebcastsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(TopWebcastsScene);
    }

    private void createScene() {
        BorderPane mainPane = new BorderPane();
        HBox header = new HBox();

        TopWebcastsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        String numberOne = null;
        String numberTwo = null;
        String numberThree = null;

        //Nodes
        final Label label = new Label("Top 3 Most watched webcasts");
        ListView<String> webcastsList = new ListView<>();
        Button buttonHome = new Button("Home");


        // Appending
        webcastsList.getItems().add(numberOne);
        webcastsList.getItems().add(numberTwo);
        webcastsList.getItems().add(numberThree);

        header.getChildren().addAll(label, buttonHome);
        buttonHome.setAlignment(Pos.CENTER_RIGHT);

        mainPane.setTop(header);
        mainPane.setCenter(webcastsList);

    }

    public void resetScene() {
        createScene();
        setScene(TopWebcastsScene);
    }
}
