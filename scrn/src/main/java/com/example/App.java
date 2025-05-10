package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static ArrayList<User> allUsers = new ArrayList();
    
    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public ArrayList<User> getUsers(){
        return allUsers;
    }

}