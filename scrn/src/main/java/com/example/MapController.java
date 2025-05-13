package com.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    @FXML
    private WebView mapView;
    private static WebView staticMapView; // Static reference to the WebView
    public static final String MAP_FILE_PATH = "src/main/resources/com/example/map.html";


    public void goToHomePage(ActionEvent event){
        try {
            Thread.sleep(175);
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("homePage.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticMapView = mapView; // Assign the instance to the static variable
        loadMap();
    }

    private void loadMap() {
        File mapFile = new File(MAP_FILE_PATH);
        if (mapFile.exists()) {
            String url = mapFile.toURI().toString();
            System.out.println("MapController: Loading map from: " + url);
            // Initial load
            Platform.runLater(() -> {
                mapView.getEngine().load(url);
                System.out.println("MapController: Initial map load attempted.");
            });
        } else {
            System.err.println("MapController: map.html not found at: " + mapFile.getAbsolutePath());
        }
    }

    public static void refreshMapGlobally() {
        System.out.println("MapController: refreshMapGlobally() called.");
        if (staticMapView != null) {
            File mapFile = new File(MAP_FILE_PATH);
            if (mapFile.exists()) {
                String url = mapFile.toURI().toString();
                // Add a cache-busting query parameter
                String urlToLoad = url + "?t=" + System.currentTimeMillis();
                System.out.println("MapController: Refreshing map from: " + urlToLoad);

                Platform.runLater(() -> {
                    staticMapView.getEngine().load(urlToLoad);
                    System.out.println("MapController: Map refresh load attempted.");
                });
            } else {
                System.err.println("MapController: map.html not found for refresh at: " + mapFile.getAbsolutePath());
            }
        } else {
            System.err.println("MapController: staticMapView is null. Map page might not be open or initialized.");
        }
    }

    public void goToOptionPage(ActionEvent event){

        try {
            Thread.sleep(175);
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Settings.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToProfilePage(ActionEvent event){

        try {
            Thread.sleep(175);
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Profile.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void goToSavedIssuesPage(ActionEvent event){

        try {
            Thread.sleep(175);
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("SavedIssuesPage.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToSearchPage(ActionEvent event){

        try {
            Thread.sleep(175);
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("SearchPage.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}