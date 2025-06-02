package com.yourname.store.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:store.db";
    private DBConnection() { }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
