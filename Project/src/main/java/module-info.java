module nl.pekict.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
//    requires com.microsoft.sqlserver.jdbc;

    opens nl.pekict.project to javafx.fxml;
    exports nl.pekict.project;
}