package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.jar.Attributes.Name;

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

    public User(String name, String surname, String email, String password, boolean mailNotification, boolean appNotification, boolean isAuthority) {
        this.userName = name;
        this.userSurname = surname;
        this.email = email;
        this.password = password;
        this.mailNotification = true;
        this.appNotification = true;
        this.isAuthority = isAuthority;
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

    public void changePassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void changeName(String newName) {
        this.userName = newName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changeSurname(String newSurname) {
        this.userSurname = newSurname;
    }

    public void addComment(ProblemReport pr, String comment) {
        pr.addComment(new Comment(this, comment));
    }

    public void saveToDatabase() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("db.properties file not found in resources!");
                return;
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

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

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User saved to database successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to save user to database.");
        }
    }
}