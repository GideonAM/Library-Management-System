package com.amalitech.librarymanagementsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
