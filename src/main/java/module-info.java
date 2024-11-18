module com.amalitech.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.amalitech.librarymanagementsystem to javafx.fxml;
    exports com.amalitech.librarymanagementsystem;
    exports com.amalitech.librarymanagementsystem.dto;
    opens com.amalitech.librarymanagementsystem.dto to javafx.fxml;
    exports com.amalitech.librarymanagementsystem.controller;
    opens com.amalitech.librarymanagementsystem.controller to javafx.fxml;
}