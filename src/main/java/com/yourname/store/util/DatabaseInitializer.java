package com.yourname.store.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    /**
     * Wczytuje store.sql z resources i wykonuje wszystkie polecenia SQL
     * (CREATE TABLE …).
     * Jeśli tabela już istnieje, SQLite zignoruje błąd i pójdzie dalej.
     */
    public static void init() {
        // 1) Wczytaj store.sql ze ścieżki classpath
        try (InputStream is =
                     DatabaseInitializer.class.getClassLoader().getResourceAsStream("store.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            // 2) Połącz się do bazy i wykonaj każdy statement oddzielnie
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {

                // rozdzielamy po średnikach i wykonujemy
                for (String part : sql.split(";")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
            // e.printStackTrace();
        }
    }
}