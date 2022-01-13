package com.codecademy.gui.statistics;

import com.codecademy.gui.GUI;
import com.codecademy.gui.GUIScene;
import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.gui.registration.OverviewRegistrationsScene;
import com.codecademy.gui.student.OverviewStudentsScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.Group;

public class StatisticsScene extends GUIScene {
    private Scene statisticsScene;
    private final int sceneWidth;
    private final int sceneHeight;
    private final TableView<String> table = new TableView<>();

    public StatisticsScene(GUI gui, int sceneWidth, int sceneHeight) {
        super(gui);

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        createScene();
        setScene(statisticsScene);
    }

    private void createScene() {
        Scene scene = new Scene(new Group());
        BorderPane mainPane = new BorderPane();
        BorderPane buttonsPane = new BorderPane();

        // To be initialised into original values
        String totAppliedVal = "null";
        String perPassedVal = "null";


        statisticsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        //Nodes
        final Label label = new Label("Gender Filtered");


        TableColumn<String, String> totApplied = new TableColumn<>("Total applied");
        TableColumn<String, String> perPassed = new TableColumn<>("Percentage");
        Button buttonMale = new Button("Male");
        Button buttonFemale = new Button("Female");
        Button buttonOther = new Button("Other");


        // Table initialisation
        final VBox vbox = new VBox(1);
        table.getColumns().addAll(totApplied, perPassed);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,0,0,10));
        vbox.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        // Appending
        buttonsPane.setLeft(buttonFemale);
        buttonsPane.setRight(buttonMale);
        buttonsPane.setCenter(buttonOther);

        mainPane.setTop(vbox);
        mainPane.setCenter(buttonsPane);

    }

    public void resetScene() {
        createScene();
        setScene(statisticsScene);
    }
}
