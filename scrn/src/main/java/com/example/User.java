package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    protected int userId;
    protected String userName;
    protected String userSurname;
    protected String email;
    protected String password;

    public User(String name, String surname, String email, String password) {
        this.userName = name;
        this.userSurname = surname;
        this.email = email;
        this.password = password;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
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
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String pwd = "12345678";

        String sql = "INSERT INTO users (user_name, user_surname, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pwd);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.userName);
            stmt.setString(2, this.userSurname);
            stmt.setString(3, this.email);
            stmt.setString(4, this.password);
            stmt.executeUpdate();

            System.out.println("User saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}