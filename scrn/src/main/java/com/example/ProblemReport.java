package com.example;

import java.sql.*;
import java.util.ArrayList;

public class ProblemReport {
    private int reportId;
    private String reportTitle;
    private String reportDescription;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int upvoteCount = 0;
    private int downvoteCount = 0;
    private ArrayList<MediaAttachment> mediaAttachments = new ArrayList<>();
    private int wasUsefulCount = 0;
    private int wasNotUsefulCount = 0;
    private int commentNumber = 0;
    private Location location;
    private Category category;
    private boolean resolved = false;
    private ArrayList<User> savedUsers;
    private int categoryID;
    private int locationID;

    public ProblemReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
        this.reportTitle = title;
        this.reportDescription = description;
        this.category = category;
        this.location = location;
        this.categoryID = 0; //category.getCategoryId();       // assumes valid Category object
        this.locationID = 0; //location.getLocationId();       // assumes valid Location object
        this.savedUsers = new ArrayList<>();
        this.reportId = 0; //change later.

        if (attachment != null)
            mediaAttachments.add(attachment);

        saveToDatabase();
    }

    public void addComment(Comment c) {
        comments.add(c);
        commentNumber++;
    }

    public void incrementUpvoteCount() {
        upvoteCount++;
    }

    public void incrementDownvoteCount() {
        downvoteCount++;
    }

    public void addMediaAttachment(MediaAttachment m) {
        mediaAttachments.add(m);
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void incrementWasNotUsefulCount() {
        wasNotUsefulCount++;
    }

    public void incrementWasUsefulCount() {
        wasUsefulCount++;
    }

    public ArrayList<User> getSavedUsers() {
        return savedUsers;
    }

    public int getReportId() {
        return reportId;
    }

    private void saveToDatabase() {
        String sql = "INSERT INTO problem_report " +
                "(reportTitle, reportDescription, upvoteCount, downvoteCount, wasUsefulCount, wasNotUsefulCount, commentNumber, resolved, category, location) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, this.reportTitle);
            stmt.setString(2, this.reportDescription);
            stmt.setInt(3, this.upvoteCount);
            stmt.setInt(4, this.downvoteCount);
            stmt.setInt(5, this.wasUsefulCount);
            stmt.setInt(6, this.wasNotUsefulCount);
            stmt.setInt(7, this.commentNumber);
            stmt.setBoolean(8, this.resolved);
            stmt.setInt(9, this.categoryID);
            stmt.setInt(10, this.locationID);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.reportId = rs.getInt(1);
                }
            }

            System.out.println("Problem Report saved to database with ID: " + reportId);

        } catch (SQLException e) {
            System.err.println("Failed to save problem report to database:");
            e.printStackTrace();
        }
    }
}