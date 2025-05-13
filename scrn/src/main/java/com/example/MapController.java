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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    @FXML
    private WebView mapView;
    private static WebView staticMapView; // Static reference to the WebView    

    public void goToHomePage(ActionEvent event){
        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml")); // Ensure this FXML exists
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interruption status
            System.err.println("MapController: goToHomePage interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("MapController: IOException in goToHomePage (FXML loading likely failed for homePage.fxml): " + e.getMessage());
            e.printStackTrace();
        }
         catch (Exception e) { // Catch any other unexpected exceptions
            System.err.println("MapController: Unexpected exception in goToHomePage: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticMapView = mapView; // Assign the instance to the static variable
        loadMap();
    }

    private void loadMap() {
        System.out.println("MapController: loadMap() called.");
        if (App.currentMapHtmlContent != null && !App.currentMapHtmlContent.isEmpty()) {
            // Log a small part to confirm it's not the default placeholder from App.java
            System.out.println("MapController: Attempting to load content into WebView (length: " + App.currentMapHtmlContent.length() + "). Snippet: " + App.currentMapHtmlContent.substring(0, Math.min(200, App.currentMapHtmlContent.length())));
            Platform.runLater(() -> {
                mapView.getEngine().loadContent(App.currentMapHtmlContent, "text/html");
                System.out.println("MapController: Initial map content loading requested via loadContent().");
            });
        } else {
            System.err.println("MapController: App.currentMapHtmlContent is NULL or EMPTY. Cannot load initial map.");
            Platform.runLater(() -> {
                mapView.getEngine().loadContent("<html><body><h1>Error: Map HTML content was not available from App class.</h1></body></html>", "text/html");
            });
        }
    }

    public static void refreshMapGlobally() {
        System.out.println("MapController: refreshMapGlobally() called.");
        if (staticMapView != null) {
            if (App.currentMapHtmlContent != null && !App.currentMapHtmlContent.isEmpty()) {
                 System.out.println("MapController: Refreshing map with new content (length: " + App.currentMapHtmlContent.length() + "). Snippet: " + App.currentMapHtmlContent.substring(0, Math.min(200, App.currentMapHtmlContent.length())));
                Platform.runLater(() -> {
                    staticMapView.getEngine().loadContent(App.currentMapHtmlContent, "text/html");
                    System.out.println("MapController: Map refresh with new content requested via loadContent().");
                });
            } else {
                System.err.println("MapController: App.currentMapHtmlContent is NULL or EMPTY on refresh. Cannot refresh map.");
                Platform.runLater(() -> {
                    staticMapView.getEngine().loadContent("<html><body><h1>Error: Updated map HTML content was not available from App class for refresh.</h1></body></html>", "text/html");
                });
            }
        } else {
            System.err.println("MapController: staticMapView is NULL. Cannot refresh map globally. Was the map page initialized?");
        }
    }

    public void goToOptionPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml")); // Ensure this FXML exists
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("MapController: goToOptionPage interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("MapController: IOException in goToOptionPage (FXML loading likely failed for Settings.fxml): " + e.getMessage());
            e.printStackTrace();
        }
         catch (Exception e) {
            System.err.println("MapController: Unexpected exception in goToOptionPage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void goToProfilePage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml")); // Ensure this FXML exists
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("MapController: goToProfilePage interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("MapController: IOException in goToProfilePage (FXML loading likely failed for Profile.fxml): " + e.getMessage());
            e.printStackTrace();
        }
         catch (Exception e) {
            System.err.println("MapController: Unexpected exception in goToProfilePage: " + e.getMessage());
            e.printStackTrace();
        }

    }

     public void goToSavedIssuesPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedIssuesPage.fxml")); // Ensure this FXML exists
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("MapController: goToSavedIssuesPage interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("MapController: IOException in goToSavedIssuesPage (FXML loading likely failed for SavedIssuesPage.fxml): " + e.getMessage());
            e.printStackTrace();
        }
         catch (Exception e) {
            System.err.println("MapController: Unexpected exception in goToSavedIssuesPage: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void goToSearchPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPage.fxml")); // Ensure this FXML exists
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("MapController: goToSearchPage interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("MapController: IOException in goToSearchPage (FXML loading likely failed for SearchPage.fxml): " + e.getMessage());
            e.printStackTrace();
        }
         catch (Exception e) {
            System.err.println("MapController: Unexpected exception in goToSearchPage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }