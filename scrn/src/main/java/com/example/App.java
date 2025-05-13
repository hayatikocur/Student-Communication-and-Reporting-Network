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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaFX App
 */
public class App extends Application {

    // Her postun çözülme durumunu saklamak için:
    private static Map<AnchorPane, Boolean> postSolvedStatus = new HashMap<>();
    private static Map<AnchorPane, Button> statusButtons = new HashMap<>();

    public static void registerStatusButton(AnchorPane post, Button button) {
        statusButtons.put(post, button);
    }
    

    public static void setPostSolvedStatus(AnchorPane post, boolean isSolved) {
        postSolvedStatus.put(post, isSolved);
        Button btn = statusButtons.get(post);
        if (btn != null) {
            btn.setText(isSolved ? "SOLVED" : "UNSOLVED");
            btn.setStyle(isSolved
                ? "-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold;"
                : "-fx-background-color: #ef5350; -fx-text-fill: white; -fx-font-weight: bold;");
        }
    }

    public static boolean getPostSolvedStatus(AnchorPane post) {
        return postSolvedStatus.getOrDefault(post, false);
    }

    public static Map<AnchorPane, Button> getStatusButtons() {
        return statusButtons;
    }


    private static ArrayList<AnchorPane> allPosts = new ArrayList<>();
    public static ArrayList<AnchorPane> getAllPosts() {
        return allPosts;
    }
    public static void addPost(AnchorPane postBox) {
        allPosts.add(0, postBox);
    }

    public static Map<AnchorPane, Map<User, Integer>> getPostVotes() {
        return postVotes;
    }

    public static void sortPostsByVotes() {
        allPosts.sort((p1, p2) -> {
            // Butonları bul
            Button btn1 = findStatusButton(p1);
            Button btn2 = findStatusButton(p2);

            boolean isSolved1 = btn1 != null && "SOLVED".equals(btn1.getText());
            boolean isSolved2 = btn2 != null && "SOLVED".equals(btn2.getText());

            // Önce UNSOLVED'lar gelsin
            if (isSolved1 && !isSolved2) return 1;
            if (!isSolved1 && isSolved2) return -1;

            // Aynı statüdeyse, oya göre sırala (büyükten küçüğe)
            int v1 = postVotes.getOrDefault(p1, new HashMap<>()).values().stream().mapToInt(Integer::intValue).sum();
            int v2 = postVotes.getOrDefault(p2, new HashMap<>()).values().stream().mapToInt(Integer::intValue).sum();
            return Integer.compare(v2, v1);
        });
    }

    public static Button findStatusButton(AnchorPane post) {
        for (Node node : post.getChildren()) {
            if (node instanceof VBox) {
                VBox contentBox = (VBox) node;
                for (Node innerNode : contentBox.getChildren()) {
                    if (innerNode instanceof Button) {
                        Button btn = (Button) innerNode;
                        if (btn.getText().equals("SOLVED") || btn.getText().equals("UNSOLVED")) {
                            return btn;
                        }
                    }
                }
            }
        }
        return null;
    }



    // Her gönderiye özel kullanıcı oylama durumu: postPane → (user → vote)
    private static Map<AnchorPane, Map<User, Integer>> postVotes = new HashMap<>();

    public static Map<User, Integer> getVotesForPost(AnchorPane post) {
        return postVotes.getOrDefault(post, new HashMap<>());
    }

    public static void initVotesForPost(AnchorPane post) {
        postVotes.put(post, new HashMap<>());
    }

    

    // Her post için yorum listesi: postPane → yorum listesi
    private static Map<AnchorPane, ArrayList<HBox>> postComments = new HashMap<>();

    public static void initCommentsForPost(AnchorPane post) {
        postComments.put(post, new ArrayList<>());
    }

    public static void addCommentToPost(AnchorPane post, HBox commentHBox) {
        postComments.get(post).add(commentHBox);
    }

    public static ArrayList<HBox> getCommentsForPost(AnchorPane post) {
        return postComments.getOrDefault(post, new ArrayList<>());
    }


    //MAP EKLEME DENEMESİNDEN ÖNCEKİ VERSİYON

    private static Map<String, Integer> buildingReports = new HashMap<>();

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
        // Eklemek istersen catering building vs
    }

    public static Map<String, Integer> getBuildingReports() {
        return buildingReports;
    }

    public static void incrementBuildingReport(String buildingName) {
        buildingReports.put(buildingName, buildingReports.getOrDefault(buildingName, 0) + 1);
        generateMapHTML();  // Harita güncelleniyor.
    }


    public static void generateMapHTML() {
    try {
        // Template dosyasını oku
        InputStream is = App.class.getResourceAsStream("/com/example/map_template.html");
        if (is == null) {
            System.err.println("Template file not found.");
            return;
        }

        String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // JSON array üret
        StringBuilder buildingsJS = new StringBuilder("[\n");
        for (Map.Entry<String, Integer> entry : buildingReports.entrySet()) {
            String name = entry.getKey();
            int reports = entry.getValue();
            double lat = BuildingLocations.getLat(name);
            double lng = BuildingLocations.getLng(name);
            buildingsJS.append(String.format("  { name: \"%s\", lat: %.6f, lng: %.6f, reports: %d },\n", name, lat, lng, reports));
        }
        if (buildingsJS.length() > 2) buildingsJS.setLength(buildingsJS.length() - 2); // sondaki virgülü sil
        buildingsJS.append("\n]");

        // Şablondaki placeholder'ı değiştir
        String finalHtml = template.replace("__BUILDINGS__", buildingsJS.toString());

        // map.html olarak kaydet
        File target = new File("src/main/resources/com/example/map.html");
        Files.writeString(target.toPath(), finalHtml, StandardCharsets.UTF_8);

        System.out.println("✅ map.html generated successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    //HAAHHAYT

    // Her postun sahibini tutan harita
    private static Map<AnchorPane, User> postOwners = new HashMap<>();

    public static void registerPostOwner(AnchorPane post, User owner) {
        postOwners.put(post, owner);
    }

    public static User getPostOwner(AnchorPane post) {
        return postOwners.get(post);
    }

    private static Scene scene;

    private static User currentUser;// this is for one time use when user signs in. dont store this in database.
    public static ArrayList<ProblemReport> getReports() {
        return reports;
    }

    private static ArrayList<ProblemReport> reports = new ArrayList();

    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    //Choosing categories for posts.
    private static Map<AnchorPane, List<String>> postCategories = new HashMap<>();  

    public static void setPostCategories(AnchorPane post, List<String> categories) {
    postCategories.put(post, categories);
    }
    
    public static List<String> getPostCategories(AnchorPane post) {
        return postCategories.getOrDefault(post, List.of());
    }

    
    public static void main(String[] args) {
        launch();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    } 

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        App.scene = scene;
    }
}