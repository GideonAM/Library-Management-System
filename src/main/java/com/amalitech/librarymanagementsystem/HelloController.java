package com.amalitech.librarymanagementsystem;

import com.amalitech.librarymanagementsystem.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    @FXML
    private TextField email_field;

    @FXML
    private Button login_field;

    @FXML
    private PasswordField password_field;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public void login() {
        String sql = "SELECT * FROM patron WHERE email = ? and password = ?";
        connection = DatabaseConnection.connection();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email_field.getText());
            preparedStatement.setString(2, password_field.getText());
            resultSet = preparedStatement.executeQuery();

            Alert alert;
            if (email_field.getText().isBlank() || password_field.getText().isBlank()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("All fields are required.");
                alert.showAndWait();

            } else {
                if (resultSet.next()) {

                    GetData.patronEmail = email_field.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful login");
                    alert.showAndWait();

                    login_field.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 986, 600);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong email or password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
