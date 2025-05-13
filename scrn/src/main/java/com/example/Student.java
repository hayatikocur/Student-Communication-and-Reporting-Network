package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class Student extends User {
    public Student(String name, String surname, String email, String password) {
        super(name, surname, email, password);

        saveToDatabase();
    }
    private ArrayList<ProblemReport> savedReports = new ArrayList<>();
    private Set<ProblemReport> upvotes = new HashSet<>();
    private Set<ProblemReport> downvotes = new HashSet<>();
    private Set<Comment> likes = new HashSet<>();
    private Map<ProblemReport, Boolean> wasPostHelpful = new HashMap<>();

    public void incrementUpvotes(ProblemReport pr) {
        upvotes.add(pr);
        pr.incrementUpvoteCount();
    }

    public void incrementDownvotes(ProblemReport pr) {
        downvotes.add(pr);
        pr.incrementDownvoteCount();
    }

    public void attachMedia(ProblemReport pr, MediaAttachment attachment) {
        pr.addMediaAttachment(attachment);
    }

    public void saveReport(ProblemReport pr) {
        if (!savedReports.contains(pr)) {
            savedReports.add(pr);
            pr.getSavedUsers().add(this);
        }
    }

    public void unsaveReport(ProblemReport pr) {
        savedReports.remove(pr);
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
                    stmt.setBoolean(7, false);

                    stmt.executeUpdate();
                    System.out.println("User saved successfully.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving user to database.");
        }
    }

    //public void createReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
    //    ProblemReport report = new ProblemReport(title, description, category, location, attachment);
    //    // Add report to global list or DB
    //}
}
