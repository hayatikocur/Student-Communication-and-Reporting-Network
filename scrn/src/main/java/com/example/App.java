package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;

public class App extends Application {

    private static ArrayList<User> allUsers;
    private static User currentUser;
    private static ArrayList<ProblemReport> reports = new ArrayList<>();
    private static Scene scene;

    private static final Map<AnchorPane, Boolean> postSolvedStatus = new HashMap<>();
    private static final Map<AnchorPane, Button> statusButtons = new HashMap<>();
    private static final ArrayList<AnchorPane> allPosts = new ArrayList<>();
    private static final Map<AnchorPane, Map<User, Integer>> postVotes = new HashMap<>();
    private static final Map<AnchorPane, ArrayList<HBox>> postComments = new HashMap<>();
    private static final Map<String, Integer> buildingReports = new HashMap<>();
    private static final Map<AnchorPane, User> postOwners = new HashMap<>();
    private static final Map<AnchorPane, List<String>> postCategories = new HashMap<>();

    static {
        buildingReports.put("B Binası", 15);
        buildingReports.put("SB Binası", 22);
        buildingReports.put("V Binası", 5);
        buildingReports.put("FC Binası", 9);
        buildingReports.put("FB Binası", 13);
        buildingReports.put("FA Binası", 6);
        buildingReports.put("FD Binası", 18);
        buildingReports.put("FF Binası", 11);
        buildingReports.put("Kütüphane", 30);
    }

    @Override
    public void start(Stage stage) throws IOException {
        allUsers = setUsersStart();
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static ArrayList<User> setUsersStart() {
        ArrayList<User> users = new ArrayList<>();
        Properties props = new Properties();

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("❌ db.properties file not found!");
                return users;
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return users;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        if (url == null || dbUser == null || dbPassword == null) {
            System.err.println("❌ Database credentials are missing in db.properties");
            return users;
        }

        String query = "SELECT userName, userSurname, email, password FROM users";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("userName");
                String surname = rs.getString("userSurname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                users.add(new User(name, surname, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void main(String[] args) {
        launch();
    }

    // Getter and setter methods remain unchanged
    public static ArrayList<User> getAllUsers() { return allUsers; }
    public static ArrayList<User> getUsers() { return allUsers; }
    public static User getCurrentUser() { return currentUser; }
    public static void setCurrentUser(User user) { currentUser = user; }
    public static Scene getScene() { return scene; }
    public static void setScene(Scene scene) { App.scene = scene; }

    // Remaining methods (post handling, votes, comments, map generation, etc.) would remain here
    // These can be moved to dedicated service classes for better modularity
}
