module com.example.codecademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.codecademy to javafx.fxml;
    exports com.codecademy;
    exports com.codecademy.gui;
    opens com.codecademy.gui to javafx.fxml;
    exports com.codecademy.gui.student;
    opens com.codecademy.gui.student to javafx.fxml;
    exports com.codecademy.informationhandling;
    opens com.codecademy.informationhandling to javafx.fxml;
    exports com.codecademy.informationhandling.validators;
    opens com.codecademy.informationhandling.validators to javafx.fxml;
}