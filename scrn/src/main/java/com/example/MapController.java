package com.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    @FXML
    private WebView mapView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL htmlURL = getClass().getResource("/com/example/map.html");
        if (htmlURL != null) {
            mapView.getEngine().load(htmlURL.toExternalForm());
        } else {
            System.err.println("map.html not found!");
        }
    }
}