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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class GUI extends Application {

    public GUI() {

    }

    @Override
    public void start(Stage window) {
        window.setTitle("CodeCademy Overview");


        // Panes
        VBox header = new VBox(10);
        HBox navigation = new HBox(10);
        BorderPane mainPane = new BorderPane();

        VBox usersListPane = new VBox(10);
        ScrollPane usersListScroll = new ScrollPane();
        VBox usersList = new VBox();

        //Get Student from database
        StudentList studentList = new dbCon().getAllStudents();
        AtomicReference<ArrayList<HBox>> users = new AtomicReference<>(setUsers(studentList));

        VBox newUserPane = new VBox(10);

        VBox selectedUserPane = new VBox(10);

        VBox popupPane = new VBox(10);
        Popup confirmationPopup = new Popup();
        confirmationPopup.setAutoHide(true);

        mainPane.setPadding(new Insets(20, 20, 20, 20));
        usersListPane.setPadding(new Insets(20, 20, 20, 20));


        // Scenes
        Scene mainScene = new Scene(mainPane, 500, 700);

        // Styling
//        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


        // Nodes
        // Navigation
        Label pageTitle = new Label("CodeCademy");
        Button usersButton = new Button("Users");
        Button homeButton = new Button("Home");
        Button newUserButton = new Button("New User");
        Button editUserButton = new Button("Edit User");
        Label empty = new Label();

        // New User Panel
        Label newUserName = new Label("Name:");
        TextField newUserNameInput = new TextField();
        Label newUserEmail = new Label("Email:");
        TextField newUserEmailInput = new TextField();
        Label newUserAddress = new Label("Adress:");
        TextField newUserAddressInput = new TextField();
        Label newUserCity = new Label("City:");
        TextField newUserCityInput = new TextField();
        Label newUserCountry = new Label("Country:");
        TextField newUserCountryInput = new TextField();
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

        // User Overview Pannel
        Label selectedUserId = new Label("");
        Label selectedUserName = new Label("");
        Label selectedUserEmail = new Label("");
        Label selectedUserAddress = new Label("");
        Label selectedUserCity = new Label("");
        Label selectedUserCountry = new Label("");
        Label selectedUserGender = new Label("");
        Label selectedUserBDay = new Label("");

        // Delete Popup
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

        usersButton.setOnAction((event) -> {
            pageTitle.setText("Users");
            mainPane.setCenter(usersListScroll);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(homeButton, newUserButton);

            users.set(setUsers(studentList));
            usersList.getChildren().clear();
            for (HBox user : users.get()) {
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
                        selectedUserAddress.setText("Address: " + data[3]);
                        selectedUserCity.setText("City: " + data[4]);
                        selectedUserCountry.setText("Country: " + data[5]);
                        selectedUserGender.setText("Gender: " + data[6]);
                        selectedUserBDay.setText("Birthday: " + data[7]);
                    }
                });
            }
        });

        newUserButton.setOnAction((event) -> {
            pageTitle.setText("New User");
            mainPane.setCenter(newUserPane);
            navigation.getChildren().clear();
            navigation.getChildren().addAll(homeButton, usersButton);

            newUserNameInput.clear(); newUserEmailInput.clear(); newUserAddressInput.clear();newUserCityInput.clear();
            newUserCountryInput.clear(); newUserGenderBox.setValue(null); newUserBDayPicker.setValue(null); errorMessage.setText(null);
            newUserPane.getChildren().removeAll(createNewUserButton, updateUserButton);
            newUserPane.getChildren().add(createNewUserButton);
        });

        createNewUserButton.setOnAction((event) -> {
            if (!newUserNameInput.getText().isEmpty() && !newUserEmailInput.getText().isEmpty() && !newUserAddressInput.getText().isEmpty() &&
                    !newUserCityInput.getText().isEmpty() && !newUserCountryInput.getText().isEmpty() && newUserGenderBox.getValue() != null &&
                    newUserBDayPicker.getValue() != null) {

                String bday = newUserBDayPicker.getValue().toString();
                createUser(newUserNameInput.getText(), newUserEmailInput.getText(), newUserAddressInput.getText(), newUserCityInput.getText(),
                        newUserCountryInput.getText(), newUserGenderBox.getValue().toString(), bday, studentList);

                errorMessage.setText("User " + newUserNameInput.getText() + " successfully created!");
                newUserNameInput.clear(); newUserEmailInput.clear(); newUserAddressInput.clear();newUserCityInput.clear();
                newUserCountryInput.clear(); newUserGenderBox.setValue(null); newUserBDayPicker.setValue(null);
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
            String[] data = users.get().get(Integer.parseInt(selectedUserId.getText().split("ID: ")[1]) - 1).getId().split(",");
            newUserNameInput.clear(); newUserEmailInput.clear(); newUserGenderBox.setValue(null); newUserBDayPicker.setValue(null); errorMessage.setText(null);
            newUserNameInput.setText(data[1]);
            newUserEmailInput.setText(data[2]);
            newUserAddressInput.setText(data[3]);
            newUserCityInput.setText(data[4]);
            newUserCountryInput.setText(data[5]);
            newUserGenderBox.setValue(data[6]);
            newUserBDayPicker.setValue(LocalDate.parse(data[7]));
        });

        cancelUpdateButton.setOnAction((event) -> {
            mainPane.setCenter(selectedUserPane);
            navigation.getChildren().removeAll(cancelUpdateButton, deleteUserButton);
            navigation.getChildren().add(editUserButton);
        });

        updateUserButton.setOnAction((event) -> {
            if (!newUserNameInput.getText().isEmpty() && !newUserEmailInput.getText().isEmpty() && !newUserAddressInput.getText().isEmpty() &&
                    !newUserCityInput.getText().isEmpty() && !newUserCountryInput.getText().isEmpty() && newUserGenderBox.getValue() != null &&
                    newUserBDayPicker.getValue() != null) {
                String bday = newUserBDayPicker.getValue().toString();
                deleteUser(selectedUserEmail.getText(), studentList);
                createUser(newUserNameInput.getText(), newUserEmailInput.getText(), newUserAddressInput.getText(), newUserCityInput.getText(),
                        newUserCountryInput.getText(), newUserGenderBox.getValue().toString(), bday, studentList);

                errorMessage.setText("User " + newUserNameInput.getText() + " has successfully updated!");
            } else {
                errorMessage.setText("Please fill in all the fields!");
            }

        });

        deleteUserButton.setOnAction((event) -> {
            confirmationPopup.show(window);
            String[] data = users.get().get(Integer.parseInt(selectedUserId.getText().split("ID: ")[1])).getId().split(",");
            confirmDeleteQuestion.setText("Are you sure you want to delete " + data[1] + "?");
        });

        confirmDeleteButton.setOnAction((event) -> {
            confirmationPopup.hide();
            for (int i = 0; i < users.get().size(); i++) {
                String[] data = users.get().get(i).getId().split(",");
                if (Integer.valueOf(data[0]).equals(Integer.valueOf(selectedUserId.getText().split("ID: ")[1]))) {
                    usersList.getChildren().remove(users.get().get(i));
                    deleteUser(data[2], studentList);
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

        usersListScroll.setContent(usersList);

        newUserPane.getChildren().addAll(newUserName, newUserNameInput, newUserEmail, newUserEmailInput,
                newUserAddress, newUserAddressInput, newUserCity, newUserCityInput, newUserCountry, newUserCountryInput,
                newUserGender, newUserGenderBox, newUserBDay, newUserBDayPicker, createNewUserButton, errorMessage);

        selectedUserPane.getChildren().addAll(selectedUserId, selectedUserName, selectedUserEmail, selectedUserAddress,
                selectedUserCity, selectedUserCountry, selectedUserGender, selectedUserBDay);

        popupPane.getChildren().addAll(confirmDeleteQuestion, confirmDeleteButton, cancelDeleteButton);
        confirmationPopup.getContent().add(popupPane);

        header.setId("header");
        popupPane.setId("popup");

        window.setScene(mainScene);
        window.show();
    }

    // Function that will return a list of users
    private static ArrayList<HBox> setUsers(StudentList studentlist) {
        ArrayList<HBox> users = new ArrayList<>();
        ArrayList<Student> studentList = studentlist.getAllStudents();
        // Gets changed to a for-loop with the users table
        int i = 0;
        for (Student student : studentList) {
            HBox newUser = new HBox(10);
            String userIndex = String.valueOf(i + 1) + ".";
            String name = student.getName();
            String email = student.getEmail();
            String address = student.getAdress();
            String city = student.getCity();
            String country = student.getCountry();
            String gender = student.getGender();
            String bDay = String.valueOf(student.getBirthday());

            newUser.setId(String.valueOf(i + 1) + "," + name + "," + email + "," + address + "," + city + "," + country + "," + gender + "," + bDay);

            newUser.getChildren().addAll(new Label(userIndex), new Label(name), new Label(email));
            users.add(newUser);
            i++;
        }

        return users;
    }

    public static void createUser(String name, String email, String adress, String city, String country, String gender, String bday, StudentList studentList) {
        Student newStudent = new Student(name, gender, email.toLowerCase(), adress, city, country, bday);
        studentList.addStudent(newStudent);
    }

    public static void deleteUser(String email, StudentList studentList) {
        studentList.deleteStudent(email);
    }

    public static void updateUser(String[] data) {
        
    }
}
