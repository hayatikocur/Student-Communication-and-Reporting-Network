package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.File;

public class MapViewer extends Application {
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        File htmlFile = new File("map.html"); // map.html aynı klasörde olmalı
        webView.getEngine().load(htmlFile.toURI().toString());

        stage.setScene(new Scene(webView, 1000, 700));
        stage.setTitle("Bilkent Harita");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}