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
    private TableView table = new TableView();

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
        VBox header = new VBox(15);
        VBox homePageButtonsPage = new VBox(15);

        statisticsScene = new Scene(mainPane, sceneWidth, sceneHeight);

        //Nodes, using tables to display data
        final Label label = new Label("Gender Filtered");

        table.setEditable(false);

        TableColumn course = new TableColumn("Course");
        TableColumn totApplied = new TableColumn("Total applied");
        TableColumn percPassed = new TableColumn("Percentage");

        table.getColumns().addAll(course, totApplied, percPassed);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,0,0,10));
        vbox.getChildren().addAll (label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        mainPane.setTop(header);
    }
}
