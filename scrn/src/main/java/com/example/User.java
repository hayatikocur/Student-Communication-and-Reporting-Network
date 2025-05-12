package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class User {

    protected int userId;
    protected String userName;
    protected String userSurname;
    protected String email;
    protected String password;
    private boolean mailNotification;
    private boolean appNotification;
    private boolean isAuthority;

    public User(String name, String surname, String email, String password) {
        this.userName = name;
        this.userSurname = surname;
        this.email = email;
        this.password = password;
        this.mailNotification = true;
        this.appNotification = true;
        this.isAuthority = false;

        saveToDatabase(); // Save user to DB upon creation
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void setProfileFields(TextField nameField, TextField surnameField, TextField mailField, TextField passwordField, Label roleLabel) {
        nameField.setText(userName);
        surnameField.setText(userSurname);
        mailField.setText(email);
        passwordField.setText(password);

        if (this instanceof Student) {
            roleLabel.setText("Student");
        } else if (this instanceof Authority) {
            roleLabel.setText("Authority");
        } else {
            roleLabel.setText("User");
        }
    }

    public boolean isMailNotificationEnabled() {
        return mailNotification;
    }

    public void setMailNotification(boolean mailNotification) {
        this.mailNotification = mailNotification;
    }

    public boolean isAppNotificationEnabled() {
        return appNotification;
    }

    public void setAppNotification(boolean appNotification) {
        this.appNotification = appNotification;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getEmail() {
        return email;
    }

    public void changeName(String newName) {
        this.userName = newName;
    }

    public void changeSurname(String newSurname) {
        this.userSurname = newSurname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void addComment(ProblemReport pr, String comment) {
        pr.addComment(new Comment(this, comment));
    }

    private void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUser = "root";
        String dbPassword = "12345678";

        String sql = "INSERT INTO users (userName, userSurname, email, password, mailNotification, appNotification, isAuthority) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.userName);
            stmt.setString(2, this.userSurname);
            stmt.setString(3, this.email);
            stmt.setString(4, this.password);
            stmt.setBoolean(5, this.mailNotification);
            stmt.setBoolean(6, this.appNotification);
            stmt.setBoolean(7, this.isAuthority);

            stmt.executeUpdate();
            System.out.println("User saved to database.");
        } catch (SQLException e) {
            System.err.println("Failed to save user to database:");
            e.printStackTrace();
        }
    }
}