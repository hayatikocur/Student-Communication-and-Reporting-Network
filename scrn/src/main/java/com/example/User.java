package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public User(String name, String surname, String email, String password) {
        this.userName = name;
        this.userSurname = surname;
        this.email = email;
        this.password = password;
        mailNotification = true;
        appNotification = true;
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
}