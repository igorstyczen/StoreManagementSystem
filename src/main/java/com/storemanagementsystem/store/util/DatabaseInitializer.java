package com.storemanagementsystem.store.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    public static void init() {
        try (InputStream is =
                     DatabaseInitializer.class.getClassLoader().getResourceAsStream("store.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {

                for (String part : sql.split(";")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
}