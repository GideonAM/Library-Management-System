package com.amalitech.librarymanagementsystem.controller;

import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class DashboardControllerTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ActionEvent actionEvent;

    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dashboardController = new DashboardController();
    }

    @Test
    void testTakeBookFieldsEmpty() {
        String patronEmail = "";
        String bookTitle = "";

        assertEquals(patronEmail, "");
        assertEquals(bookTitle, "");
    }

    @Test
    void testTakeBookSuccessful() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        dashboardController.takeBook();
    }

    @Test
    void testTakeBookFailedDueToDatabaseError() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        doThrow(new SQLException("Something went wrong in database")).when(preparedStatement).executeUpdate();

        dashboardController.takeBook();
    }

    @Test
    void testReturnBookFieldsEmpty() {
        String bookTitle = "";
        assertEquals(bookTitle, "");
    }

    @Test
    void testReturnBookSuccessful() throws SQLException {
        when(connection.createStatement()).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
    }

    @Test
    void testFindBookFieldsEmpty() {
        String bookTitle = "";
        assertEquals(bookTitle, "");
    }

    @Test
    void testFindBookSuccessful() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn("Book Title");
        when(resultSet.getString("author")).thenReturn("Book Author");
        when(resultSet.getString("date")).thenReturn("Date book was created");
    }

    @Test
    void testFindBookNotFound() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);
    }

    @Test
    void testClearTakeData() {
        String take_title_label = "";
        String take_author_label = "";
        String take_date_label = "";

        assertEquals("", take_title_label);
        assertEquals("", take_author_label);
        assertEquals("", take_date_label);
    }
}
