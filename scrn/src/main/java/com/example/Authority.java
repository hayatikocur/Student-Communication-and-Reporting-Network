package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Authority extends User {

    public Authority(String userName, String userSurname, String email, String password){
        super(userName, userSurname, email, password);

        saveToDatabase();
    }

    public void resolveReport(ProblemReport pr) {
        pr.setResolved(true);
    }

    public void removeReport(ProblemReport pr) {
        // Remove from DB or list
    }

    private void saveToDatabase() {
        try {
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                props.load(input);
            }

            String url = props.getProperty("db.url");
            String dbUser = props.getProperty("db.user");
            String dbPassword = props.getProperty("db.password");

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                String sql = "INSERT INTO users (userName, userSurname, email, password, mailNotification, appNotification, isAuthority) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, userName);
                    stmt.setString(2, userSurname);
                    stmt.setString(3, email);
                    stmt.setString(4, password); // You should hash this in real use
                    stmt.setBoolean(5, true);
                    stmt.setBoolean(6, true);
                    stmt.setBoolean(7, true);

                    stmt.executeUpdate();
                    System.out.println("User saved successfully.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving user to database.");
        }
    }
}