package nl.pekict.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;

public class GUI extends Application {

    public GUI() {

    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("CodeCademy Overview");

        // Panes
        VBox mainPane = new VBox(10);
        VBox usersListPane = new VBox(10);
        ArrayList<HBox> users = setUsers();

        mainPane.setPadding(new Insets(20, 20, 20, 20));
        usersListPane.setPadding(new Insets(20, 20, 20, 20));

        // Scenes
        Scene mainScene = new Scene(mainPane, 300, 300);
        Scene usersScene = new Scene(usersListPane, 300, 300);

        // Nodes
        Label mainTitle = new Label("CodeCademy");
        Button usersButton = new Button("Users");

        Label usersListTitle = new Label("Users");
        Button menuButton = new Button("Menu");

        // Style
        mainTitle.setFont(new Font(25.0));

        usersListTitle.setFont(new Font(25.0));

        // Events
        usersButton.setOnAction((event) -> window.setScene(usersScene));

        menuButton.setOnAction((event) -> window.setScene(mainScene));

        // Adding Nodes to the Panes
        mainPane.getChildren().addAll(mainTitle, usersButton);

        usersListPane.getChildren().addAll(usersListTitle, menuButton);
        for (HBox user : users) {
            usersListPane.getChildren().add(user);
        }

        window.setScene(mainScene);
        window.show();
    }

    // Function that will return a list of users
    private ArrayList<HBox> setUsers() {
        ArrayList<HBox> users = new ArrayList<>();
        int index = 1;

        // Gets changed to a for-loop with the users table
        HBox newUser = new HBox(10);
        Label userIndex = new Label(String.valueOf(index) + ".");
        Label name = new Label("Evert Jansen");
        Label email = new Label("evert.jansen@gmail.com");

        newUser.getChildren().addAll(userIndex, name, email);
        users.add(newUser);

        return users;
    }
}
