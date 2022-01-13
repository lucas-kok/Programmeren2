package com.codecademy.gui.template;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TemplateScene extends GUIScene {
    private Scene crudTypeObjectNameScene; // Example: createStudentScene
    private int sceneWidth;
    private int sceneHeight;

    public TemplateScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(crudTypeObjectNameScene); // Function in the Super-Class GUIScene
    }

    private void createScene() {
        // Panes & Scene
        BorderPane mainPane = new BorderPane();
        VBox headerPane = new VBox(15);
        HBox navigationPane = new HBox(15);
        VBox crudTypeObjectNamePane = new VBox(15); // Example: createScenePane

        crudTypeObjectNameScene = new Scene(mainPane, sceneWidth, sceneHeight);

        // Nodes
        Label title = new Label("Hello World!");

        Button homeButton = new Button("Home");

        Label randomLabel = new Label("Gabagui");

        // Event Handlers
        homeButton.setOnAction((event) -> {
//            ((MainScene)getSceneObject("mainScene")).resetScene(); -> Other Scenes
            showScene("mainScene");
        });

        // Appending
        mainPane.setTop(headerPane);
        mainPane.setCenter(crudTypeObjectNamePane);

        headerPane.getChildren().addAll(title, navigationPane);
        navigationPane.getChildren().addAll(homeButton);

        crudTypeObjectNamePane.getChildren().addAll(randomLabel);
    }

    public void resetScene() { // Public because it will be called from other Classes
        createScene();
        setScene(crudTypeObjectNameScene);
    }
}
