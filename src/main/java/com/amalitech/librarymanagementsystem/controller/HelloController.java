package com.amalitech.librarymanagementsystem.controller;

import com.amalitech.librarymanagementsystem.HelloApplication;
import com.amalitech.librarymanagementsystem.database.DatabaseConnection;
import com.amalitech.librarymanagementsystem.dto.GetData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private TextField register_email_field;

    @FXML
    private Button register_here_btn;

    @FXML
    private PasswordField register_password_field;

    @FXML
    private Button sign_in_here_btn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane register_form;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public void registerHereBtn(ActionEvent event) {
        if (event.getSource() == register_here_btn) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        }
    }

    public void signInHereBtn(ActionEvent event) {
        if (event.getSource() == sign_in_here_btn) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    public void register() {
        String sql = "SELECT * FROM patron WHERE email = ?";
        connection = DatabaseConnection.connection();
        String createPatronSql = """
                            INSERT INTO patron
                            (email, password)
                            VALUES (?, ?)
                            """;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, register_email_field.getText());
            resultSet = preparedStatement.executeQuery();

            Alert alert;
            if (register_email_field.getText().isBlank() || register_password_field.getText().isBlank()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("All fields are required.");
                alert.showAndWait();

            } else {
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("This email is already registered");
                    alert.showAndWait();

                } else {

                    connection = DatabaseConnection.connection();
                    preparedStatement = connection.prepareStatement(createPatronSql);
                    preparedStatement.setString(1, register_email_field.getText());
                    preparedStatement.setString(2, register_password_field.getText());
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Account created successfully");
                    alert.showAndWait();

                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

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
