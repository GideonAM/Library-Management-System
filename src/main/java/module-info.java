module com.amalitech.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.amalitech.librarymanagementsystem to javafx.fxml;
    exports com.amalitech.librarymanagementsystem;
}