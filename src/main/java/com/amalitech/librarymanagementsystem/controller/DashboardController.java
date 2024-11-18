package com.amalitech.librarymanagementsystem.controller;

import com.amalitech.librarymanagementsystem.HelloApplication;
import com.amalitech.librarymanagementsystem.database.DatabaseConnection;
import com.amalitech.librarymanagementsystem.dto.AvailableBooks;
import com.amalitech.librarymanagementsystem.dto.GetData;
import com.amalitech.librarymanagementsystem.dto.ReturnBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane availableBook_form;

    @FXML
    private AnchorPane issue_form;

    @FXML
    private AnchorPane returnBooks_form;

    @FXML
    private Label availableBook_title;

    @FXML
    private Button availableBooks_btn;

    @FXML
    private ImageView availableBooks_image;

    @FXML
    private TableView<AvailableBooks> availableBooks_table;

    @FXML
    private Circle circle_image;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_author;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_bookTitle;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_bookType;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_publishedDate;

    @FXML
    private Button issuedBooks_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Label patron_email_label;

    @FXML
    private Button returnedBooks_btn;

    @FXML
    private AnchorPane savedBooks_form;

    @FXML
    private Button save_btn;

    @FXML
    private Button savedBooks_btn;

    @FXML
    private Button take_btn;

    @FXML
    private TextField take_BookTitle;

    @FXML
    private Button take_TakeBtn;

    @FXML
    private Label take_author_label;

    @FXML
    private Button take_clearBtn;

    @FXML
    private Label take_date_label;

    @FXML
    private TextField take_issuedDate;

    @FXML
    private Label take_patron_email;

    @FXML
    private Label take_title_label;

    @FXML
    private Label currentForm_label;

    @FXML
    private ComboBox<String> take_transactionType;
    @FXML
    private TableColumn<ReturnBook, String> return_Author;
    @FXML
    private TableColumn<ReturnBook, String> return_BookTitle;
    @FXML
    private TableColumn<ReturnBook, String> return_BookType;
    @FXML
    private TableColumn<ReturnBook, String> return_DateIssued;
    @FXML
    private TableView<ReturnBook> return_TableView;
    @FXML
    private Button return_button;


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;

    private ObservableList<AvailableBooks> listBooks;

    private final String [] transactionType = {"BORROW", "RETURN"};

    public void selectTransactionType() {
        ObservableList<String> list = FXCollections.observableArrayList();
        Collections.addAll(list, transactionType);
        take_transactionType.setItems(list);
    }

    public void takeBook() {
        String sql = """
                INSERT INTO transaction
                (transactionType, patronEmail, bookTitle, author, date, checkReturn)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        connection = DatabaseConnection.connection();

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        try {
            Alert alert;
            if (take_transactionType.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Select a transaction type");
                alert.showAndWait();

            } else if (take_title_label.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Message");
                alert.setContentText("Indicate book you want to borrow");
                alert.showAndWait();

            } else {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, take_transactionType.getSelectionModel().getSelectedItem());
                preparedStatement.setString(2, take_patron_email.getText());
                preparedStatement.setString(3, take_title_label.getText());
                preparedStatement.setString(4, take_author_label.getText());
                preparedStatement.setDate(5, sqlDate);

                String check = "Borrowed";

                preparedStatement.setString(6, check);
                preparedStatement.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Message");
                alert.setContentText("Book borrowed successfully");
                alert.showAndWait();

                clearTakeData();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ObservableList<ReturnBook> returnBookData() {
        ObservableList<ReturnBook> listReturnBook = FXCollections.observableArrayList();

        String checkReturn = "Borrowed";
        String sql = "SELECT * FROM transaction " +
                "WHERE checkReturn = '" + checkReturn + "' AND patronEmail = '" + GetData.patronEmail + "'";
        connection = DatabaseConnection.connection();

        try {
            ReturnBook returnBooks;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                returnBooks = new ReturnBook(resultSet.getString("bookTitle"),
                        resultSet.getString("author"),
                        resultSet.getDate("date"));

                listReturnBook.add(returnBooks);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return listReturnBook;
    }

    private void showReturnBooks() {
        ObservableList<ReturnBook> returnBooks;
        returnBooks = returnBookData();
        return_BookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        return_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        return_DateIssued.setCellValueFactory(new PropertyValueFactory<>("date"));

        return_TableView.setItems(returnBooks);
    }

    public void selectReturnBook() {
        ReturnBook returnBook = return_TableView.getSelectionModel().getSelectedItem();
        int num = return_TableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1)
            return;

        GetData.takeBookTitle = returnBook.getTitle();
    }

    public void returnBook() {
        String sql = "UPDATE transaction SET checkReturn = 'Returned' WHERE bookTitle = '" + GetData.takeBookTitle + "'";
        connection = DatabaseConnection.connection();

        Alert alert;
        try {

            if (return_BookTitle.getText() == null) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Select the book you want to return");
                alert.showAndWait();
            } else {
                statement = connection.createStatement();
                statement.execute(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Book returned successfully");
                alert.showAndWait();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void findBook(ActionEvent event) {
        String sql = "SELECT * FROM book WHERE title LIKE '%" + take_BookTitle.getText() + "%'";
        connection = DatabaseConnection.connection();

        try {

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Alert alert;
            boolean check = false;

            if (take_BookTitle.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Enter a valid book name");
                alert.showAndWait();

            } else {
                while (resultSet.next()) {
                    take_title_label.setText(resultSet.getString("title"));
                    take_author_label.setText(resultSet.getString("author"));
                    take_date_label.setText(resultSet.getString("date"));
                    check = true;
                }

                if (!check) {
                    take_title_label.setText("No book found with this name");
                    take_author_label.setText("");
                    take_date_label.setText("");
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void clearTakeData() {
        take_title_label.setText("");
        take_author_label.setText("");
        take_date_label.setText("");
        take_BookTitle.setText("");
    }

    public void issuedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        take_issuedDate.setText(date);
    }

    public ObservableList<AvailableBooks> dataList() {
        String sql = "SELECT * FROM book";
        ObservableList<AvailableBooks> booksObservableList = FXCollections.observableArrayList();

        connection = DatabaseConnection.connection();
        try {
            AvailableBooks availableBooks;
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                availableBooks = new AvailableBooks(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("bookType"),
                        resultSet.getDate("date")
                );

                booksObservableList.add(availableBooks);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return booksObservableList;
    }

    public void showAvailableBooks() {
        listBooks = dataList();

        col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_ab_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_ab_bookType.setCellValueFactory(new PropertyValueFactory<>("bookType"));
        col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableBooks_table.setItems(listBooks);
    }

    public void selectAvailableBooks() {
        AvailableBooks availableBooks = availableBooks_table.getSelectionModel().getSelectedItem();
        int num = availableBooks_table.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1)
            return;

        availableBook_title.setText(availableBooks.getTitle());
    }

    public void availableBookTakeButton(ActionEvent event) {
        if (event.getSource() == take_btn) {
            issue_form.setVisible(true);
            availableBook_form.setVisible(false);
            savedBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);

        }
    }

    public void showPatronEmail() {
        patron_email_label.setText(GetData.patronEmail);
        take_patron_email.setText(GetData.patronEmail);
    }

    public void navButtonDesign(ActionEvent event) {

        if (event.getSource() == availableBooks_btn) {
            currentForm_label.setText("Available Books");
            issue_form.setVisible(false);
            availableBook_form.setVisible(true);
            savedBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);

        } else if (event.getSource() == issuedBooks_btn) {
            currentForm_label.setText("Borrow a book");
            issue_form.setVisible(true);
            availableBook_form.setVisible(false);
            savedBooks_form.setVisible(false);
            returnBooks_form.setVisible(false);

        } else if (event.getSource() == savedBooks_btn) {
            currentForm_label.setText("Saved Books");
            issue_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBooks_form.setVisible(true);
            returnBooks_form.setVisible(false);

        } else if (event.getSource() == returnedBooks_btn) {
            currentForm_label.setText("Returned Books");
            issue_form.setVisible(false);
            availableBook_form.setVisible(false);
            savedBooks_form.setVisible(false);
            returnBooks_form.setVisible(true);
            showReturnBooks();
        }

    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource() == logout_btn) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 986, 600);
                stage.setScene(scene);
                stage.show();
                logout_btn.getScene().getWindow().hide();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvailableBooks();
        showPatronEmail();
        selectTransactionType();
        issuedDate();
        showReturnBooks();
    }
}
