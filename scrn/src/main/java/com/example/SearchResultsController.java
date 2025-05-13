package com.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultsController implements Initializable {

    @FXML
    private Label searchResultsTitleLabel;

    @FXML
    private ListView<AnchorPane> resultsListView; // Will display AnchorPane posts

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (App.currentSearchResults != null && !App.currentSearchResults.isEmpty()) {
            resultsListView.setItems(FXCollections.observableArrayList(App.currentSearchResults));
            System.out.println("SearchResultsController: Displaying " + App.currentSearchResults.size() + " results.");
        } else {
            resultsListView.setPlaceholder(new Label("No matching posts found."));
            System.out.println("SearchResultsController: No search results to display.");
        }
        // Consider when/how to clear App.currentSearchResults and App.currentSearchCriteriaDescription
        // Maybe clear them when a new search is initiated from SearchPage.fxml
    }

    @FXML
    void goBackToSearchPage(ActionEvent event) {
        try {
            // Clear previous results before going back, so a fresh search starts clean
            App.currentSearchResults.clear();
            App.currentSearchCriteriaDescription = "";

            Parent searchPageRoot = FXMLLoader.load(getClass().getResource("SearchPage.fxml"));
            Scene scene = new Scene(searchPageRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load SearchPage.fxml from SearchResults: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void goToHomePage(ActionEvent event) {
        try {
            Parent homePageRoot = FXMLLoader.load(getClass().getResource("homePage.fxml"));
            Scene scene = new Scene(homePageRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load homePage.fxml from SearchResults: " + e.getMessage());
            e.printStackTrace();
        }
    }
}