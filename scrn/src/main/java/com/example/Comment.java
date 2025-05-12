package com.example;

import java.sql.*;

public class Comment {
    private int commentId;
    private String commentContent;
    private int likeNumber;
    private User author;
    private ProblemReport belongingReport;

    /*
    MySQL Table: comment
    -----------------------------------
    commentId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    reportId INT NOT NULL,
    commentContent VARCHAR(1000),
    time TIME DEFAULT CURRENT_TIME,
    likeNumber INT
    */

    public Comment(User author, String content, ProblemReport report) {
        this.author = author;
        this.commentContent = content;
        this.belongingReport = report;
        this.likeNumber = 0;

        saveToDatabase();
    }

    public void like() {
        likeNumber++;
        // Optional: update DB here
    }

    private void saveToDatabase() {
        String sql = "INSERT INTO comment (userId, reportId, commentContent, likeNumber) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, author.getUserId());
            stmt.setInt(2, belongingReport.getReportId());
            stmt.setString(3, commentContent);
            stmt.setInt(4, likeNumber);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.commentId = rs.getInt(1);
                    System.out.println("✅ Comment saved with ID: " + this.commentId);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to save comment to database:");
            e.printStackTrace();
        }
    }
}