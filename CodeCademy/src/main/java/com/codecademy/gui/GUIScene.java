package com.codecademy.gui;

import javafx.scene.Scene;

public class GUIScene {
    private final GUI gui;
    private Scene scene;

    public GUIScene(GUI gui) {
        this.gui = gui;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    // Function that can be called from child to display another Scene
    public void showScene(String sceneName) {
        gui.showScene(sceneName);
    }

    // Function that can be called from child to call methods on (Like the reset function)
    public GUIScene getSceneObject(String sceneName) {
        return gui.getSceneObject(sceneName);
    }
}
