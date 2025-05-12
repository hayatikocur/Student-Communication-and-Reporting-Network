package com.example;

import java.sql.*;

public class Notification {
    private User user;
    private int notificationId;
    private String notifContent;

    public Notification(User user, String content) {
        this.user = user;
        this.notifContent = content;
        saveToDatabase();
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO notifications (userId, notifContent) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, this.user.getUserId());
            stmt.setString(2, this.notifContent);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.notificationId = rs.getInt(1);
                }
            }

            System.out.println("Notification saved to database with ID: " + notificationId);

        } catch (SQLException e) {
            System.err.println("Failed to save notification to database:");
            e.printStackTrace();
        }
    }
}