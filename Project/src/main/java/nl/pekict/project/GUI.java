package nl.pekict.project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GUI extends Application {

    public GUI() {

    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("CodeCademy Overview");

        // Panes
        VBox header = new VBox(10);
        HBox navigation = new HBox(10);
        BorderPane mainPane = new BorderPane();

        VBox usersListPane = new VBox(10);
        ScrollPane usersListScroll = new ScrollPane();
        VBox usersList = new VBox();
        ArrayList<HBox> users = setUsers();

        VBox newUserPane = new VBox(10);

        VBox selectedUserPane = new VBox(10);

        VBox popupPane = new VBox(10);
        Popup confirmationPopup = new Popup();
        confirmationPopup.setAutoHide(true);

        mainPane.setPadding(new Insets(20, 20, 20, 20));
        usersListPane.setPadding(new Insets(20, 20, 20, 20));

        // Scenes
        Scene mainScene = new Scene(mainPane, 400, 500);

        // Styling
//        mainScene.getStylesheets().add(GUI.class.getResource("resources/nl/pekict/project/style.css").toExternalForm());

        // Nodes
        Label pageTitle = new Label("CodeCademy");
        Button usersButton = new Button("Users");
        Button homeButton = new Button("Home");
        Button newUserButton = new Button("New User");
        Button editUserButton = new Button("Edit User");
        Label empty = new Label();

        Label newUserName = new Label("Name:");
        TextField newUserNameInput = new TextField();
        Label newUserEmail = new Label("Email:");
        TextField newUserEmailInput = new TextField();
        Label newUserGender = new Label("Gender");
        ComboBox newUserGenderBox = new ComboBox();
        newUserGenderBox.getItems().add("Male");
        newUserGenderBox.getItems().add("Female");
        newUserGenderBox.getItems().add("Other");
        Label newUserBDay = new Label("BirthDay:");
        DatePicker newUserBDayPicker = new DatePicker();
        Button createNewUserButton = new Button("Create User");
        Button updateUserButton = new Button("Update User");
        Button cancelUpdateButton = new Button("Cancel");
        Button deleteUserButton = new Button("Delete");
        Label errorMessage = new Label("");

        Label selectedUserId = new Label("");
        Label selectedUserName = new Label("");
        Label selectedUserEmail = new Label("");
        Label selectedUserGender = new Label("");
        Label selectedUserBDay = new Label("");

        Label confirmDeleteQuestion = new Label("");
        Button confirmDeleteButton = new Button("Yes");
        Button cancelDeleteButton = new Button("No");

        pageTitle.setId("pageTitle");

        // Events
        homeButton.setOnAction((event) -> {
            pageTitle.setText("CodeCademy");
            mainPane.setCenter(empty);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(usersButton);
        });

        usersButton.setOnAction((event) ->  {
            pageTitle.setText("Users");
            mainPane.setCenter(usersListScroll);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(homeButton, newUserButton);
        });

        newUserButton.setOnAction((event) -> {
            pageTitle.setText("New User");
            mainPane.setCenter(newUserPane);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(homeButton, usersButton);

            newUserNameInput.clear(); newUserEmailInput.clear(); newUserGenderBox.setValue(null); newUserBDayPicker.setValue(null); errorMessage.setText(null);
            newUserPane.getChildren().removeAll(createNewUserButton, updateUserButton);
            newUserPane.getChildren().add(createNewUserButton);
        });

        createNewUserButton.setOnAction((event) -> {
            if (!newUserNameInput.getText().isEmpty() && !newUserEmailInput.getText().isEmpty() && newUserGenderBox.getValue() != null && newUserBDayPicker.getValue() != null) {
                createUser(newUserNameInput.getText(), newUserEmailInput.getText(), newUserGenderBox.getValue().toString(),
                        newUserBDayPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                errorMessage.setText("User " + newUserNameInput.getText() + " successfully created!");
                newUserNameInput.setText("");
                newUserEmailInput.setText("");
                newUserGenderBox.setValue(null);
                newUserBDayPicker.setValue(null);
            } else {
                errorMessage.setText("Please fill in all the fields!");
            }
        });

        editUserButton.setOnAction((event) -> {
            mainPane.setCenter(newUserPane);
            navigation.getChildren().removeAll(editUserButton);
            navigation.getChildren().addAll(deleteUserButton, cancelUpdateButton);

            newUserPane.getChildren().removeAll(createNewUserButton, updateUserButton);
            newUserPane.getChildren().add(updateUserButton);
            String[] data = users.get(Integer.valueOf(selectedUserId.getText().split("ID: ")[1])).getId().split(",");
            newUserNameInput.clear(); newUserEmailInput.clear(); newUserGenderBox.setValue(null); newUserBDayPicker.setValue(null); errorMessage.setText(null);
            newUserNameInput.setText(data[1]);
            newUserEmailInput.setText(data[2]);
            newUserGenderBox.setValue(data[3]);
            newUserBDayPicker.setValue(LocalDate.parse(data[4]));
        });

        cancelUpdateButton.setOnAction((event) -> {
            mainPane.setCenter(selectedUserPane);
            navigation.getChildren().removeAll(cancelUpdateButton, deleteUserButton);
            navigation.getChildren().add(editUserButton);
        });

        updateUserButton.setOnAction((event) -> {
            errorMessage.setText("User " + newUserNameInput.getText() + " has successfully updated!");
        });

        deleteUserButton.setOnAction((event) -> {
            confirmationPopup.show(window);
            String[] data = users.get(Integer.valueOf(selectedUserId.getText().split("ID: ")[1])).getId().split(",");
            confirmDeleteQuestion.setText("Are you sure you want to delete " + data[1] + "?");
        });

        confirmDeleteButton.setOnAction((event) -> {
            confirmationPopup.hide();
            for (int i = 0; i < users.size(); i++) {
                String[] data = users.get(i).getId().split(",");
                if (Integer.valueOf(data[0]) == Integer.valueOf(selectedUserId.getText().split("ID: ")[1])) {
                    usersList.getChildren().remove(users.get(i));
                    break;
                }
            }
            pageTitle.setText("Users");
            mainPane.setCenter(usersListScroll);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(homeButton, newUserButton);
        });

        cancelDeleteButton.setOnAction((event) -> {
            confirmationPopup.hide();
        });

        // Adding Nodes to the Panes
        header.getChildren().addAll(pageTitle, navigation);
        navigation.getChildren().addAll(usersButton);
        mainPane.setTop(header);

        for (HBox user : users) {
            usersList.getChildren().add(user);
            user.getStyleClass().add("userRow");

            user.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String[] data = user.getId().split(",");
                    pageTitle.setText(data[1]);
                    mainPane.setCenter(selectedUserPane);
                    navigation.getChildren().clear();
                    navigation.getChildren().addAll(homeButton, usersButton, editUserButton);

                    selectedUserId.setText("ID: " + data[0]);
                    selectedUserName.setText("Name: " + data[1]);
                    selectedUserEmail.setText("Email: " + data[2]);
                    selectedUserGender.setText("Gender: " + data[3]);
                    selectedUserBDay.setText("Birthday: " + data[4]);
                }
            });
        }
        usersListScroll.setContent(usersList);

        newUserPane.getChildren().addAll(newUserName, newUserNameInput, newUserEmail, newUserEmailInput,
                newUserGender, newUserGenderBox, newUserBDay, newUserBDayPicker, createNewUserButton, errorMessage);

        selectedUserPane.getChildren().addAll(selectedUserId, selectedUserName, selectedUserEmail, selectedUserGender, selectedUserBDay);

        popupPane.getChildren().addAll(confirmDeleteQuestion, confirmDeleteButton, cancelDeleteButton);
        confirmationPopup.getContent().add(popupPane);

        header.setId("header");
        popupPane.setId("popup");

        window.setScene(mainScene);
        window.show();
    }

    // Function that will return a list of users
    private static ArrayList<HBox> setUsers() {
        ArrayList<HBox> users = new ArrayList<>();

        // Gets changed to a for-loop with the users table
        for (int i = 0; i < 100; i++) {
            HBox newUser = new HBox(10);
            Label userIndex = new Label(String.valueOf(i + 1) + ".");
            Label name = new Label("Evert Jansen");
            Label email = new Label("evert.jansen@gmail.com");
            String gender = "Male";
            String bDay = "2005-09-01";

            newUser.setId(String.valueOf(i + 1) + "," + name.getText() + "," + email.getText() + "," + gender + "," + bDay);

            newUser.getChildren().addAll(userIndex, name, email);
            users.add(newUser);
        }

        return users;
    }

    public static void createUser(String name, String email, String gender, String bday) {
        System.out.println(name + ", " + email + ", " + gender + ", " + bday);
    }

    public static void updateUser(String[] data) {

    }
}
