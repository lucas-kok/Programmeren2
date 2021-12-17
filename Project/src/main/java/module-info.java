module nl.pekict.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    opens nl.pekict.project to javafx.fxml;
    exports nl.pekict.project;
}