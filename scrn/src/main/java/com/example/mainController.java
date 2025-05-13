package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.stream.Collectors;

public class mainController implements Initializable{

    @FXML private ListView<AnchorPane> savedIssuesListView;
    //TODO: those will be used for sign in page you will use those to validate the password and email. add for sign up in same way.
    //you can look at the id(variable name) in signup.fxml file
    @FXML
    TextField tfEmailSin;
    @FXML
    TextField tfPasswordSin;
    @FXML
    TextField tfEmailSup;
    @FXML
    PasswordField tfPasswordSup;
    @FXML
    PasswordField tfConfirmPasswordSup;

    //
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    
    @FXML
    Label profileLabel;
    @FXML
    TextField tfProfileSurname;
    @FXML
    TextField tfProfilePassword;
    @FXML
    TextField tfProfileMail;
    @FXML
    TextField tfProfileName;
    @FXML
    Label profileEditLabel;
    @FXML
    ListView searchResultsListView;

    @FXML private TextField searchKeywordTextField;

    ArrayList<String> selectedCategories = new ArrayList<>();
    ArrayList<String> selectedDates = new ArrayList<>();
    ArrayList<String> selectedLocations = new ArrayList<>();

    @FXML
    private void confirmSearchAndNavigate(ActionEvent event) {
        // Clear previous selections from instance lists before populating them again
        selectedCategories.clear();
        selectedDates.clear();
        selectedLocations.clear();

        String keywordQuery = "";
        if (searchKeywordTextField != null && searchKeywordTextField.getText() != null) {
            keywordQuery = searchKeywordTextField.getText().trim().toLowerCase();
        } else {
            System.out.println("mainController: searchKeywordTextField is not injected or has no text for SearchPage.fxml.");
        }

        // --- Gather selected categories (your existing logic) ---
        if (brokenEquipmentBtn.isSelected()) selectedCategories.add("Broken Equipment"); // Use user-friendly names or map them
        if (roadIssuesBtn.isSelected()) selectedCategories.add("Road Issues");
        if (buildingIssuesBtn.isSelected()) selectedCategories.add("Building Issues");
        if (dormIssuesBtn.isSelected()) selectedCategories.add("Dorm Issues");
        if (missingEquipmentBtn.isSelected()) selectedCategories.add("Missing Equipment");
        // Note: The strings "Broken Equipment", "Road Issues" etc. must match what you expect
        // when comparing with App.getPostCategories(postPane) which might store "Maintenance", "Cleaning", etc.
        // You need a consistent way to handle these category names.
        // For now, this collects user-facing names. The filter logic will need to be smart.

        // --- Gather selected locations (your existing logic) ---
        if (bBuildingButton.isSelected()) selectedLocations.add("B Binası"); // Use exact names as in BuildingLocations / cbPostLocation
        if (sbBuildingButton.isSelected()) selectedLocations.add("SB Binası");
        if (faBuildingButton.isSelected()) selectedLocations.add("FA Binası");
        if (fbBuildingButton.isSelected()) selectedLocations.add("FB Binası");
        if (fcBuildingButton.isSelected()) selectedLocations.add("FC Binası");
        if (fdBuildingButton.isSelected()) selectedLocations.add("FD Binası");
        if (ffBuildingButton.isSelected()) selectedLocations.add("FF Binası");
        if (kutuphaneButton.isSelected()) selectedLocations.add("Kütüphane");
        if (vBuildingButton.isSelected()) selectedLocations.add("V Binası");// Ensure consistency

        // --- Build Criteria Description for display on results page ---
        StringBuilder criteriaDescBuilder = new StringBuilder();
        if (!keywordQuery.isEmpty()) {
            criteriaDescBuilder.append("Keyword: '").append(searchKeywordTextField.getText().trim()).append("'");
        }
        if (!selectedCategories.isEmpty()) {
            if (criteriaDescBuilder.length() > 0) criteriaDescBuilder.append("; ");
            criteriaDescBuilder.append("Categories: ").append(String.join(", ", selectedCategories));
        }
        if (!selectedLocations.isEmpty()) {
            if (criteriaDescBuilder.length() > 0) criteriaDescBuilder.append("; ");
            criteriaDescBuilder.append("Locations: ").append(String.join(", ", selectedLocations));
        }
        if (!selectedDates.isEmpty()) { // Add date criteria if you implement date filtering
            if (criteriaDescBuilder.length() > 0) criteriaDescBuilder.append("; ");
            criteriaDescBuilder.append("Timeframe: ").append(String.join(", ", selectedDates));
        }
        if (criteriaDescBuilder.length() == 0) {
            criteriaDescBuilder.append("All Posts (no specific filters)");
        }
        App.currentSearchCriteriaDescription = criteriaDescBuilder.toString();
        System.out.println("Search Criteria: " + App.currentSearchCriteriaDescription);


        // --- Perform Search/Filter Logic ---
        List<AnchorPane> matchingPosts = new ArrayList<>();
        ArrayList<AnchorPane> allPosts = App.getAllPosts();

        for (AnchorPane postPane : allPosts) {
            boolean keywordMatches = keywordQuery.isEmpty(); // True if no keyword
            boolean categoryMatches = selectedCategories.isEmpty(); // True if no categories selected
            boolean locationMatches = selectedLocations.isEmpty(); // True if no locations selected
            // boolean dateMatches = selectedDates.isEmpty(); // True if no dates selected (implement actual date logic later)

            // 1. Keyword Search
            if (!keywordQuery.isEmpty()) {
                StringBuilder searchablePostText = new StringBuilder();
                Label titleLabel = (Label) postPane.lookup("#postTitleLabel");
                if (titleLabel != null) searchablePostText.append(titleLabel.getText().toLowerCase()).append(" ");

                Label contentLabel = (Label) postPane.lookup("#postContentLabel"); // Ensure this ID is set during post creation
                if (contentLabel != null) searchablePostText.append(contentLabel.getText().toLowerCase()).append(" ");
                
                Label userLabel = (Label) postPane.lookup("#postUserLabel"); // Ensure this ID is set
                if (userLabel != null) searchablePostText.append(userLabel.getText().toLowerCase()).append(" ");

                if (searchablePostText.toString().contains(keywordQuery)) {
                    keywordMatches = true;
                }
            }

            // 2. Category Filter
            if (!selectedCategories.isEmpty()) {
                List<String> postActualCategories = App.getPostCategories(postPane); // e.g., ["Maintenance", "Electrical"]
                if (postActualCategories != null && !postActualCategories.isEmpty()) {
                    // This requires your `selectedCategories` (from ToggleButtons, e.g. "Building Issues")
                    // to be translatable or comparable to `postActualCategories` (e.g. "Building").
                    // For this demo, we assume a simple direct comparison logic needs to be implemented
                    // based on your category system.
                    // Let's assume for now that `App.getPostCategories` returns strings that can be
                    // somewhat matched to what `selectedCategories` contains.
                    // This is a placeholder for more precise matching logic.
                    boolean foundCat = false;
                    for (String selectedFilterCategory : selectedCategories) {
                        for (String postCat : postActualCategories) {
                            // Example: If selectedFilterCategory is "Building Issues" and postCat is "Building"
                            // This needs careful thought based on your actual category values.
                            if (postCat.toLowerCase().contains(selectedFilterCategory.split(" ")[0].toLowerCase())) { // Very basic match
                                foundCat = true;
                                break;
                            }
                        }
                        if (foundCat) break;
                    }
                    if (foundCat) categoryMatches = true; else categoryMatches = false; // if categories selected, must match one
                } else if (!selectedCategories.isEmpty()){ // Post has no categories, but filter requires categories
                     categoryMatches = false;
                }
            }

            // 3. Location Filter
            if (!selectedLocations.isEmpty()) {
                Label buildingLabel = (Label) postPane.lookup("#postBuildingLabel"); // Ensure this ID is set
                if (buildingLabel != null) {
                    String postBuildingText = buildingLabel.getText(); // e.g., "🏢 B Binası"
                    boolean foundLoc = false;
                    for (String selectedLocFilter : selectedLocations) { // selectedLocFilter is e.g., "B Binası"
                        if (postBuildingText.toLowerCase().contains(selectedLocFilter.toLowerCase())) {
                            foundLoc = true;
                            break;
                        }
                    }
                    if (foundLoc) locationMatches = true; else locationMatches = false;
                } else if (!selectedLocations.isEmpty()) { // Post has no location label, but filter requires location
                    locationMatches = false;
                }
            }

            // 4. Date Filter (Placeholder - implement actual date logic if needed)
            // if (!selectedDates.isEmpty()) { /* ... date filtering logic ... dateMatches = ...; */ }


            // Combine matches (All conditions must be met if specified)
            if (keywordMatches && categoryMatches && locationMatches /* && dateMatches */) {
                matchingPosts.add(postPane);
            }
        }

        App.currentSearchResults = matchingPosts;
        System.out.println("mainController: Search/Filter complete. Found " + matchingPosts.size() + " posts. Navigating to results page.");

        // Navigate to SearchResults.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchResult.fxml"));
            Parent searchResultsRoot = loader.load();
            Scene scene = new Scene(searchResultsRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("mainController: Failed to load SearchResult.fxml: " + e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the search results page.");
            alert.showAndWait();
        }
    }

    // Your ArrayLists for filters - these are already in your mainController
    // ArrayList<String> selectedCategories = new ArrayList();
    // ArrayList<String> selectedDates = new ArrayList();
    // ArrayList<String> selectedLocations = new ArrayList();

    // ... (rest of your mainController.java, including initialize, navigation methods etc.)
    // Make sure your categoryAction methods still update these lists correctly.

    
    //Category ToggleButtons
    @FXML private ToggleButton brokenEquipmentBtn;
    @FXML private ToggleButton roadIssuesBtn;
    @FXML private ToggleButton buildingIssuesBtn;
    @FXML private ToggleButton dormIssuesBtn;
    @FXML private ToggleButton missingEquipmentBtn;
    // Location ToggleButtons
    @FXML private ToggleButton bBuildingButton;
    @FXML private ToggleButton vBuildingButton;
    @FXML private ToggleButton sbBuildingButton;
    @FXML private ToggleButton faBuildingButton;
    @FXML private ToggleButton fbBuildingButton;
    @FXML private ToggleButton fcBuildingButton;
    @FXML private ToggleButton fdBuildingButton;
    @FXML private ToggleButton ffBuildingButton;
    @FXML private ToggleButton kutuphaneButton;

    @FXML private Button createBtn;
    @FXML private ImageView createIcon;

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ScrollPane notificationsScrollPane;
    @FXML
    private Button notificationBtn;

  
    static String tempPassword = "";
    //

    





    
    public void changeToSignUp(ActionEvent event){
        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToSignInFromSignUp(ActionEvent event){
        try {
            if(validateBilkentEmail(tfEmailSup.getText().trim())){
                Thread.sleep(175);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateBilkentEmail(String email){
        // email must be in this format: name.surname@ug.bilkent.edu.tr
        String regex = "^[a-zA-Z]+\\.([a-zA-Z]+)?@(ug\\.)?bilkent\\.edu\\.tr$";
        if(!email.matches(regex)){    
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null); // No header
            alert.setContentText("Email is not valid!");
            alert.showAndWait();
            return false;
        }
        if (tfPasswordSup.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password mistake");
            alert.setHeaderText(null); // No header
            alert.setContentText("Please enter a password!");
            alert.showAndWait();
            return false;
        }
        if(!tfPasswordSup.getText().equals(tfConfirmPasswordSup.getText())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password mistake");
            alert.setHeaderText(null); // No header
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();
            return false;
        }
        
        for(int i=0; i<App.getUsers().size(); i++){
            if(App.getUsers().get(i).getEmail().equals(email)){
                Alert alert = new Alert(AlertType.ERROR);
                //alert.setTitle("");
                alert.setHeaderText(null); // No header
                alert.setContentText("User with the same email already exists");
                alert.showAndWait();
                return false;
            }
        }

        String[] parts = email.split("@")[0].split("\\.");
        String name = parts[0];
        String surname = parts[1];

        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1).toLowerCase();

        SendGmail.sendEmail(email);
        //TODO: in this code everybody is added as users not separated such as student or authority. separate them. 
        if(email.contains("ug")){
            App.getUsers().add(new Student(name, surname, email, tfPasswordSup.getText()));
        }
        else{
            App.getUsers().add(new Authority(name, surname, email, tfPasswordSup.getText()));
        }
        
        // App.getUsers().add(new User(name, surname, email, tfPasswordSup.getText()));
        return true;
    }

    public void changeToSignIn(ActionEvent event){
        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnToFirstPage(ActionEvent event){
        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     public void goToHomePageFromLogin(ActionEvent event){
        //TODO: Need to check if user's email and password is correct. Then it should go to home page.

        try {
            if(!validationOnSignIn(tfEmailSin.getText().trim(), tfPasswordSin.getText())){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Wrong email or password");
                alert.setHeaderText(null); // No header
                alert.setContentText("Email or Password wrong!");
                alert.showAndWait();
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                Parent root = loader.load();

                // Create elements
                TextField tfProfileName = (TextField) root.lookup("#tfProfileName");
                TextField tfProfileSurname = (TextField) root.lookup("#tfProfileSurname");
                TextField tfProfileMail = (TextField) root.lookup("#tfProfileMail");
                TextField tfProfilePassword = (TextField) root.lookup("#tfProfilePassword");
                Label profileLabel = (Label) root.lookup("#profileLabel");

                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                stage.setOpacity(0);
                stage.show();                

                // Set profile informations
                App.getCurrentUser().setProfileFields(tfProfileName, tfProfileSurname, tfProfileMail, tfProfilePassword, profileLabel);

                stage.close();

                Thread.sleep(175);
                loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
                root = loader.load();

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validationOnSignIn(String email, String password){
        for(int i=0; i<App.getUsers().size(); i++){
            if(tfEmailSin.getText().trim().equals(App.getUsers().get(i).getEmail())){
                App.setCurrentUser(App.getUsers().get(i));
                if(App.getCurrentUser().getPassword().equals(password)){

                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void goToHomePage(ActionEvent event){
        //TODO: Need to check if user's email and password is correct. Then it should go to home page.

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(ActionEvent e){
        if(tfEmailSin.getText().trim().equals("")){
            Alert alert = new Alert(AlertType.ERROR);
            //alert.setTitle("Wrong email or password");
            alert.setHeaderText(null); // No header
            alert.setContentText("Enter your email to the email field.");
            alert.showAndWait();
        }
        //TODO: Will check if email exists then send random generated password through email
        for(int i=0; i<App.getUsers().size(); i++){
            if(tfEmailSin.getText().trim().equals(App.getUsers().get(i).getEmail())){
                tempPassword = createRandomPassword();
                App.getUsers().get(i).setPassword(tempPassword);
                SendGmail.sendPassword(App.getUsers().get(i).getEmail());
                break;
            }
        }

        Alert alert = new Alert(AlertType.ERROR);
        //alert.setTitle("Wrong email or password");
        alert.setHeaderText(null); // No header
        alert.setContentText("user not found.");
        alert.showAndWait();
    }

    private String createRandomPassword(){
        String password = "";
        Random ran = new Random();
        for(int i=0; i<5; i++){
            password += ran.nextInt(10);
        }
        return password;
    }

     public void goToMapPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mapPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToOptionPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToProfilePage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToSavedIssuesPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedIssuesPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToCreatePage(ActionEvent event){

        if(App.getCurrentUser().getEmail().contains("ug")){
            try {
                Thread.sleep(175);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("createPage.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        

    }

    public void goToSearchPage(ActionEvent event){

        try {
            Thread.sleep(175);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Şunları database bağlayın.
    public void setEditAction(Event arg0){ 

        if (tfProfilePassword.isEditable() == false) {
            tfProfilePassword.setEditable(true);
            tfProfileName.setEditable(true);
            tfProfileSurname.setEditable(true);
            profileEditLabel.setText("You can Edit!");
            profileEditLabel.setStyle("-fx-text-fill: #66bb6a;");
        }
        else{
            tfProfileSurname.setEditable(false);
            tfProfileName.setEditable(false);
            tfProfilePassword.setEditable(false);
            profileEditLabel.setText("Press Icon to Edit");
            profileEditLabel.setStyle("-fx-text-fill: #e53935;");

            App.getCurrentUser().changeName(tfProfileName.getText());
            App.getCurrentUser().changeSurname(tfProfileSurname.getText());
            App.getCurrentUser().changePassword(tfProfilePassword.getText());

        }
    }   

    @FXML
    ToggleButton mailToggle;

    @FXML
    ToggleButton appNotiToggle;

    @FXML
    private ComboBox<String> cbPostLocation;

    @FXML
    private VBox postContainer1;



    // private void updateToggleText(ToggleButton button) {
    //     if (button != null) {
    //         button.setText(button.isSelected() ? "Turn On" : "Turn Off");
    //         button.setOnAction(e -> {
    //         button.setText(button.isSelected() ? "Turn On" : "Turn Off");
    //         });
    //     }
    // }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (savedIssuesListView != null) {
            List<AnchorPane> savedPosts = App.getSavedPostsForUser(App.getCurrentUser());
            savedIssuesListView.setItems(FXCollections.observableArrayList(savedPosts));
        }

        
        if (postContainer != null) {
            App.sortPostsByVotes(); // önce sıralama
            postContainer.getChildren().clear();
            postContainer.getChildren().addAll(App.getAllPosts());
        }

        if (postFormPane != null) {
            postFormPane.setVisible(true); // Sayfa yüklenince post formu açık gelsin
        }

        if (tfProfileName != null) {
            tfProfileName.setText(App.getCurrentUser().getUserName());
        }
        if (tfProfileSurname != null) {
            tfProfileSurname.setText(App.getCurrentUser().getUserSurname());
        }
        if (tfProfileMail != null) {
            tfProfileMail.setText(App.getCurrentUser().getEmail());
        }
        if (tfProfilePassword != null) {
            tfProfilePassword.setText(App.getCurrentUser().getPassword());
        }
        if (profileLabel != null) {
            if(App.getCurrentUser().getEmail().contains("ug")){
                profileLabel.setText("Student"); // ya da Student / Authority'ye göre ayır
            }
            else{
                profileLabel.setText("Authority"); 
            }
        }

        if (mailToggle != null) {
            setupToggleButton(mailToggle, App.getCurrentUser().isMailNotificationEnabled());
        }

        if (appNotiToggle != null) {
            setupToggleButton(appNotiToggle, App.getCurrentUser().isAppNotificationEnabled());
        }

        if (cbPostLocation != null) {
            cbPostLocation.setItems(FXCollections.observableArrayList(App.getBuildingReports().keySet()));
        }

        if(createBtn != null && !App.getCurrentUser().getEmail().contains("ug")){
            createBtn.setVisible(false);
            createIcon.setVisible(false);
        }

        //Category Initializing
        if (categoryListView != null) {
            categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            categoryListView.setItems(FXCollections.observableArrayList(
            "Broken equipment", "Road related issues", "Building related issues", "Dorm related issues", "Missing equipment"
        ));
        }

        if(notificationsScrollPane != null && notificationBtn != null){
            notificationsScrollPane.setVisible(false);

            notificationBtn.setOnAction(e -> {
            // toggle visibility
            boolean showing = notificationsScrollPane.isVisible();
            notificationsScrollPane.setVisible(!showing);
            });
        }
        

        
       

        // ✔ Tüm postların durum butonlarını tekrar bağla
        for (AnchorPane post : App.getAllPosts()) {
            Button btn = App.getStatusButtons().get(post);
            if (btn != null) {
                boolean isSolved = App.getPostSolvedStatus(post);
                btn.setText(isSolved ? "SOLVED" : "UNSOLVED");
                btn.setStyle(isSolved
                    ? "-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold;"
                    : "-fx-background-color: #ef5350; -fx-text-fill: white; -fx-font-weight: bold;");

                if (App.getCurrentUser() instanceof Authority) {
                    btn.setDisable(isSolved); // ✅ zaten SOLVED ise kapalı olsun

                    if (!isSolved) {
                        btn.setOnAction(e -> {
                            if (btn.getText().equals("UNSOLVED")) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirm Resolution");
                                alert.setHeaderText("Are you sure you want to mark this issue as SOLVED?");
                                alert.setContentText("Once marked as solved, it cannot be changed again.");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == javafx.scene.control.ButtonType.OK) {
                                        App.setPostSolvedStatus(post, true);
                                        btn.setText("SOLVED");
                                        btn.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold; -fx-opacity: 1.0;");
                                        btn.setDisable(true);

                                        // ✅ MAIL GÖNDER
                                        User postOwner = App.getPostOwner(post);
                                        User resolver = App.getCurrentUser();

                                        if (postOwner != null && resolver != null && postOwner.isMailNotificationEnabled()) {
                                            String subject = "Problem Solved: \"" + getPostTitleFromPost(post) + "\"";
                                            String message = "Dear " + postOwner.getUserName() + ",\n\n"
                                                + "Your reported problem titled \"" + getPostTitleFromPost(post) + "\" has been marked as SOLVED by "
                                                + resolver.getUserName() + " " + resolver.getUserSurname() + ".\n\n"
                                                + "Thank you for your feedback.\n\nBest regards,\nSCRN System";

                                            SendGmail.sendEmail(postOwner.getEmail(), subject, message);
                                        }
                                    }
                                });
                            }
                        });

                    }
                } else {
                    btn.setDisable(true); // öğrenci zaten tıklayamaz
                }
            }
        }

        if (postContainer != null) {
            for (AnchorPane post : App.getAllPosts()) {

                // Eğer zaten saveButton eklenmişse bir daha eklemeyelim
                if (post.lookup("#saveButton") == null) {

                    Button saveButton = new Button();
                    saveButton.setId("saveButton");

                    updateSaveButtonState(saveButton, post);

                    saveButton.setOnAction(e -> {
                        App.toggleSavedPost(App.getCurrentUser(), post);
                        updateSaveButtonState(saveButton, post);
                    });

                    VBox contentBox = null;
                    for (Node node : post.getChildren()) {
                        if (node instanceof VBox) {
                            contentBox = (VBox) node;
                            break;
                        }
                    }

                    if (contentBox != null) {
                        contentBox.getChildren().add(saveButton);
                    }
                } else {
                    // varsa bile görünümünü güncelle
                    Node node = post.lookup("#saveButton");
                    if (node instanceof Button) {
                        updateSaveButtonState((Button) node, post);
                    }
                }
            }
        }

        if (notificationsScrollPane != null && notificationBtn != null) {
            notificationsScrollPane.setVisible(false);

            notificationBtn.setOnAction(e -> {

                if (!App.getCurrentUser().isAppNotificationEnabled()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Notifications Disabled");
                    alert.setHeaderText(null);
                    alert.setContentText("You have disabled in-app notifications in Settings.");
                    alert.showAndWait();
                    return;
                }

                boolean showing = notificationsScrollPane.isVisible(); // mevcut durumu kontrol et
                notificationsScrollPane.setVisible(!showing); // tersi duruma geçir
                postContainer1.getChildren().clear(); // Öncekileri temizle

                for (AnchorPane post : App.getAllPosts()) {
                    User owner = App.getPostOwner(post);
                    boolean isSolved = App.getPostSolvedStatus(post);

                    if (owner != null && owner.equals(App.getCurrentUser()) && isSolved) {
                        String title = getPostTitleFromPost(post);
                        Label solvedPostLabel = new Label("✅ \"" + title + "\" has been marked as SOLVED.");
                        solvedPostLabel.setWrapText(true);
                        solvedPostLabel.setMaxWidth(230);
                        solvedPostLabel.setStyle("""
                            -fx-background-color: white;
                            -fx-padding: 8;
                            -fx-background-radius: 10;
                            -fx-border-radius: 10;
                            -fx-border-color: #66bb6a;
                            -fx-font-size: 14px;
                            -fx-text-fill: #2e7d32;
                        """);
                        postContainer1.getChildren().add(solvedPostLabel);
                    }
                }

                if (postContainer1.getChildren().isEmpty()) {
                    Label emptyLabel = new Label("No solved posts yet.");
                    emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                    postContainer1.getChildren().add(emptyLabel);
                }
            });
        }


        if (location != null && location.getPath().endsWith("SearchPage.fxml")) {
            System.out.println("mainController initializing SearchPage.fxml specific components.");
            // Initialize ToggleButton states or any specific setup for SearchPage.
            // For example, set default styles for ToggleButtons:
            if (brokenEquipmentBtn != null) updateToggleOfCategories(brokenEquipmentBtn);
            if (roadIssuesBtn != null) updateToggleOfCategories(roadIssuesBtn);
            // ... and so on for all your filter ToggleButtons
            if (bBuildingButton != null) updateToggleOfCategories(bBuildingButton);
            // ...
        } else if (location != null && location.getPath().endsWith("homePage.fxml")) {
             // Your existing homepage init logic
            if (savedIssuesListView != null && App.getCurrentUser() != null) { // Added null check for App.getCurrentUser()
                List<AnchorPane> savedPosts = App.getSavedPostsForUser(App.getCurrentUser());
                savedIssuesListView.setItems(FXCollections.observableArrayList(savedPosts));
            }
            // ... (rest of your homepage specific init code from the repo) ...
            if (postContainer != null) {
                App.sortPostsByVotes(); // önce sıralama
                postContainer.getChildren().clear();
                postContainer.getChildren().addAll(App.getAllPosts());
            }
            // ... (and so on, ensure you copy all relevant parts from your initialize)
        }
        // ... (other initializations from your original file)

    }


    

    private String getPostTitleFromPost(AnchorPane post) {
        Node found = post.lookup("#postTitleLabel");
        if (found instanceof Label) {
            return ((Label) found).getText();
        }
        return "Unknown Title";
    }





    @FXML
    public void deleteAccount(ActionEvent event) {
        App.getUsers().remove(App.getCurrentUser());
        App.setCurrentUser(null);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Account Deleted");
        alert.setHeaderText(null);
        alert.setContentText("Your account has been successfully deleted.");
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupToggleButton(ToggleButton toggle, boolean isOn) {
        toggle.setSelected(isOn);
        updateToggleAppearance(toggle);
    }

    // Toggle durumuna göre görünüm güncelle
    private void updateToggleAppearance(ToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setText("Turned On");
            toggle.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-background-radius: 20;");
        } else {
            toggle.setText("Turned Off");
            toggle.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-background-radius: 20;");
        }
    }

    @FXML
    private void confirmSearch(ActionEvent event){
        if (brokenEquipmentBtn.isSelected()) {
            selectedCategories.add("brokenEquipment");
        } 
    
        if (roadIssuesBtn.isSelected()) {
            selectedCategories.add("roadIssues");
        } 
    
        if (buildingIssuesBtn.isSelected()) {
           selectedCategories.add("buildingIssues");
        } 
    
        if (dormIssuesBtn.isSelected()) {
            selectedCategories.add("dormIssues");
        } 
        
        if (missingEquipmentBtn.isSelected()) {
            selectedCategories.add("missingEquipment");
        } 


        // Location selections
        if (bBuildingButton.isSelected()) {
            selectedLocations.add("B Building");
        }

        if (vBuildingButton.isSelected()) {
            selectedLocations.add("V Building");
        }
        if (sbBuildingButton.isSelected()) {
            selectedLocations.add("SB Building");
        }
        if (faBuildingButton.isSelected()) {
            selectedLocations.add("FA Building");
        }
        if (fbBuildingButton.isSelected()) {
            selectedLocations.add("FB Building");
        }
        if (fcBuildingButton.isSelected()) {
            selectedLocations.add("FC Building");
        }
        if (fdBuildingButton.isSelected()) {
            selectedLocations.add("FD Building");
        }
        if (ffBuildingButton.isSelected()) {
            selectedLocations.add("FF Building");
        }
        if (kutuphaneButton.isSelected()) {
            selectedLocations.add("Kütüphane");
        }


        //create a personalized feed
        selectedCategories.clear();// at the end empty the arraylists.
        selectedDates.clear();
        selectedLocations.clear();
    }
    
    
    @FXML
    private void categoryAction(ActionEvent event){
        ToggleButton source = (ToggleButton) event.getSource();
        updateToggleOfCategories(source);
    }

    private void updateToggleOfCategories (ToggleButton toggle){
        if (toggle.isSelected()) {
            toggle.setStyle("-fx-background-color: linear-gradient(to right, #cdffd8, #94b9ff);-fx-border-color: #000000;-fx-border-radius: 20; -fx-background-radius: 20;");
        }
        else{
            toggle.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000;-fx-border-radius: 20; -fx-background-radius: 20");
        }
    }


    @FXML
    private void toggleBold(ActionEvent event) {
        isBold = !isBold;
        updateTextFieldStyle();
    }

    @FXML
    private void toggleItalic(ActionEvent event) {
        isItalic = !isItalic;
        updateTextFieldStyle();
    }

    @FXML
    private void toggleUnderline(ActionEvent event) {
        isUnderline = !isUnderline;
        updateTextFieldStyle();
    }

    private void updateTextFieldStyle() {

        StringBuilder style = new StringBuilder();
        style.append("-fx-background-color: #D9DDDC; ");
        style.append("-fx-background-radius: 0 0 0 20; ");

        if (isBold) {
            style.append("-fx-font-weight: bold;");
        }
        if (isItalic) {
            style.append("-fx-font-style: italic;");
        }
        if (isUnderline) {
            style.append("-fx-underline: true;");
        }

        tfPostContent.setStyle(style.toString());
}

    @FXML
    public void toggleMail(ActionEvent event) {
        boolean currentState = mailToggle.isSelected();
        App.getCurrentUser().setMailNotification(currentState);
        updateToggleAppearance(mailToggle);
    }

    @FXML
    public void toggleInApp(ActionEvent event) {
        boolean currentState = appNotiToggle.isSelected();
        App.getCurrentUser().setAppNotification(currentState);
        updateToggleAppearance(appNotiToggle);
    }

    
    @FXML
    private TextField tfPostTitle;


    @FXML
    private TextField tfPostContent;

    @FXML
    private ListView<String> postListView;

    @FXML
    private AnchorPane postFormPane;

    @FXML
    private VBox postContainer;

    @FXML
    private ScrollPane scrollPane;

    private void updateSaveButtonState(Button saveButton, AnchorPane post) {
        boolean isSaved = App.isPostSaved(App.getCurrentUser(), post);
        saveButton.setText(isSaved ? "✅ Saved" : "🔖 Save");
        saveButton.setStyle(isSaved
            ? "-fx-background-color: #c8e6c9; -fx-font-weight: bold;"
            : "-fx-background-color: #ffcc80; -fx-font-weight: bold;");
    }


   @FXML
    private void submitPost(ActionEvent event) {
        String title = tfPostTitle.getText();
        String content = tfPostContent.getText();

        // Kullanıcı bilgileri
        String fullName = App.getCurrentUser().getUserName() + " " + App.getCurrentUser().getUserSurname();
        Label userLabel = new Label(fullName);
        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        // Başlık ve içerik
        Label titleLabel = new Label(title);
        titleLabel.setId("postTitleLabel"); // 🔹 bu çok önemli
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        

        // Binanın adı
        String selectedBuilding = cbPostLocation.getValue();
        Label buildingLabel = new Label("🏢 " + (selectedBuilding != null && !selectedBuilding.isEmpty() ? selectedBuilding : "No Building Selected"));
        buildingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #000000;");  // Siyah renk
        buildingLabel.setId("postBuildingLabel");

        // Oy bileşenleri
        Label voteCountLabel = new Label("0");
        voteCountLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
        Button upvoteButton = new Button("🢙");
        upvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24");
        Button downvoteButton = new Button("🢛");
        downvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24");

        // Post işlemi içinde bina seçildiğinde harita raporunun arttırılması
        selectedBuilding = cbPostLocation.getValue();
        if (selectedBuilding != null && !selectedBuilding.isEmpty()) {
            App.incrementBuildingReport(selectedBuilding);
        }


        VBox voteBox = new VBox(5, upvoteButton, voteCountLabel, downvoteButton);
        voteBox.setStyle("-fx-alignment: center;");
        AnchorPane votePane = new AnchorPane();
        votePane.setPrefWidth(60);
        votePane.getChildren().add(voteBox);
        AnchorPane.setTopAnchor(voteBox, 0.0);
        AnchorPane.setLeftAnchor(voteBox, 0.0);

        // Post içeriği
        VBox fullPostContent = new VBox(10, userLabel, titleLabel, contentLabel, buildingLabel);

        if (selectedImageFile != null) {
            ImageView imageView = new ImageView(new Image(selectedImageFile.toURI().toString()));
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            fullPostContent.getChildren().add(imageView);
        }

        // Yorum bileşenleri
        VBox commentSection = new VBox(5);
        commentSection.setVisible(false);
        commentSection.setManaged(false);
        commentSection.setStyle("-fx-padding: 10;");
        Label commentLabel = new Label("Comments:");
        commentLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ListView<HBox> commentList = new ListView<>();
        commentList.setPrefHeight(100);
        TextField commentInput = new TextField();
        commentInput.setPromptText("Write a comment...");
        commentInput.setPrefWidth(400);
        Button submitCommentButton = new Button("Post");
        Button toggleCommentButton = new Button("💬 Comment");

        AnchorPane postWithVotes = new AnchorPane();
        postWithVotes.setPrefWidth(600);


        Button saveButton = new Button("🔖 Save");
        saveButton.setId("saveButton"); // 🔧 EKLE BU
        updateSaveButtonState(saveButton, postWithVotes);
        
        saveButton.setStyle("-fx-background-color: #ffcc80; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            App.toggleSavedPost(App.getCurrentUser(), postWithVotes);
            updateSaveButtonState(saveButton, postWithVotes);


            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Post saved to your Saved Issues.");
            alert.showAndWait();
        });

        // Uygulamaya kaydet
        App.addPost(postWithVotes);
        App.initVotesForPost(postWithVotes);
        App.initCommentsForPost(postWithVotes);
        App.registerPostOwner(postWithVotes, App.getCurrentUser());
        commentList.setItems(FXCollections.observableArrayList(App.getCommentsForPost(postWithVotes)));

        toggleCommentButton.setOnAction(ev -> {
            boolean visible = commentSection.isVisible();
            commentSection.setVisible(!visible);
            commentSection.setManaged(!visible);
            toggleCommentButton.setText(!visible ? "❌ Hide Comments" : "💬 Comment");
            toggleCommentButton.setStyle(!visible
                ? "-fx-background-color: #E3F2FD; -fx-border-color: #2196f3; -fx-border-radius: 5; -fx-background-radius: 5;"
                : "-fx-background-color: transparent;");
        });

        submitCommentButton.setOnAction(ev -> {
            String comment = commentInput.getText().trim();
            if (!comment.isEmpty()) {
                String name = App.getCurrentUser().getUserName();
                String surname = App.getCurrentUser().getUserSurname();

                Label nameLabel = new Label(name + " " + surname + ": ");
                nameLabel.setStyle("-fx-font-weight: bold;");
                Label commentText = new Label(comment);
                HBox commentLine = new HBox(5, nameLabel, commentText);

                App.addCommentToPost(postWithVotes, commentLine);
                commentList.setItems(FXCollections.observableArrayList(App.getCommentsForPost(postWithVotes)));
                commentInput.clear();
            }
        });

        Button statusButton = new Button("UNSOLVED");
        statusButton.setStyle("-fx-background-color: #ef5350; -fx-text-fill: white; -fx-font-weight: bold;");
            
        App.registerStatusButton(postWithVotes, statusButton);
        App.setPostSolvedStatus(postWithVotes, false);

        //haha
        // Erişim yetkisi kontrolü
        if (App.getCurrentUser() instanceof Authority) {
            statusButton.setOnAction(ev -> {
            if (statusButton.getText().equals("UNSOLVED")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Resolution");
                alert.setHeaderText("Are you sure you want to mark this issue as SOLVED?");
                alert.setContentText("Once marked as solved, it cannot be changed again.");

                // Evet/Hayır butonlarını bekle
                alert.showAndWait().ifPresent(response -> {
                    if (response == javafx.scene.control.ButtonType.OK) {
                        App.setPostSolvedStatus(postWithVotes, true);
                        statusButton.setText("SOLVED");
                        statusButton.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold; -fx-opacity: 1.0;");

                        User postOwner = App.getPostOwner(postWithVotes);
                        User resolver = App.getCurrentUser();
                        String postTitle = tfPostTitle.getText();

                        if (postOwner != null && resolver != null && postOwner.isMailNotificationEnabled()) {
                            String subject = "Problem Solved: \"" + postTitle + "\"";
                            String contentMail = "Dear " + postOwner.getUserName() + ",\n\n"
                                    + "Your reported problem titled \"" + postTitle + "\" has been marked as SOLVED by "
                                    + resolver.getUserName() + " " + resolver.getUserSurname() + ".\n\n"
                                    + "Thank you for your feedback.\n\nBest regards,\nSCRN System";

                            SendGmail.sendEmail(postOwner.getEmail(), subject, contentMail);
                        }

                        statusButton.setDisable(true); // artık tıklanamaz
                    }
                });
            }
        });


        } else {
            statusButton.setDisable(true); // Diğer kullanıcılar tıklayamasın
            statusButton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: black;");
            statusButton.setTooltip(new javafx.scene.control.Tooltip("Only authorities can mark posts as solved."));
        }

        commentSection.getChildren().addAll(commentLabel, commentList, new HBox(5, commentInput, submitCommentButton));
        fullPostContent.getChildren().addAll(toggleCommentButton, saveButton, commentSection);
        fullPostContent.getChildren().add(statusButton);
        AnchorPane.setLeftAnchor(fullPostContent, 60.0);
        postWithVotes.getChildren().addAll(votePane, fullPostContent);

        // Oy işlemleri
        upvoteButton.setOnAction(e -> {
            Map<User, Integer> voteMap = App.getVotesForPost(postWithVotes);
            int previousVote = voteMap.getOrDefault(App.getCurrentUser(), 0);
            int currentVotes = Integer.parseInt(voteCountLabel.getText());

            int newVoteCount = currentVotes;

            if (previousVote == 1) {
                // 🔁 Remove upvote
                newVoteCount -= 1;
                voteMap.put(App.getCurrentUser(), 0);
                upvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: black;");
            } else {
                // ⬆️ Apply upvote
                newVoteCount += (previousVote == -1) ? 2 : 1;
                voteMap.put(App.getCurrentUser(), 1);
                upvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: rgb(194, 116, 7);");
                downvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: black;");
            }
        
            voteCountLabel.setText(String.valueOf(newVoteCount));
        
            if (postContainer != null) {
                App.sortPostsByVotes();
                postContainer.getChildren().setAll(App.getAllPosts());
            }
        });


        downvoteButton.setOnAction(e -> {
            Map<User, Integer> voteMap = App.getVotesForPost(postWithVotes);
            int previousVote = voteMap.getOrDefault(App.getCurrentUser(), 0);
            int currentVotes = Integer.parseInt(voteCountLabel.getText());

            int newVoteCount = currentVotes;

            if (previousVote == -1) {
                // 🔁 Remove downvote
                newVoteCount += 1;
                voteMap.put(App.getCurrentUser(), 0);
                downvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: black;");
            } else {
                // ⬇️ Apply downvote
                newVoteCount -= (previousVote == 1) ? 2 : 1;
                voteMap.put(App.getCurrentUser(), -1);
                downvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: rgb(7, 135, 194);");
                upvoteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 24; -fx-text-fill: black;");
            }
        
            voteCountLabel.setText(String.valueOf(newVoteCount));
        
            if (postContainer != null) {
                App.sortPostsByVotes();
                postContainer.getChildren().setAll(App.getAllPosts());
            }
        });

        if (postContainer != null) {
            App.sortPostsByVotes(); // oylara göre sırala
            postContainer.getChildren().clear();
            for (AnchorPane post : App.getAllPosts()) {
                postContainer.getChildren().add(post);
            }
        }



        // Temizle ve formu kapat
        tfPostTitle.clear();
        tfPostContent.clear();
        selectedImageFile = null;
        postImagePreview.setImage(null);

        // HomePage yeniden yüklenmeden anlık olarak güncellensin
        if (postContainer != null) {
            App.sortPostsByVotes();
            postContainer.getChildren().setAll(App.getAllPosts());
        }

        // Sayfayı yeniden yükle
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> selectedCategories = categoryListView.getSelectionModel().getSelectedItems();

        AnchorPane postBox = new AnchorPane(); // or load from FXML
        App.setPostCategories(postWithVotes, selectedCategories);
        
        // Show categories in post UI
        Label categoriesLabel = new Label("Categories: " + String.join(", ", selectedCategories));
        categoriesLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        fullPostContent.getChildren().add(categoriesLabel);
        
        VBox postContent = new VBox();
        if (categoriesLabel != null && postContainer != null) {
            postContent.getChildren().addAll(categoriesLabel, postContainer);
        }
        postBox.getChildren().add(postContent);

        
    }



    public void addReport(ProblemReport report){
        App.getReports().add(report);
    }

    @FXML
    public void createPost(ActionEvent event) {

        //only student can creat posts
        // Formu aç
        
        postFormPane.setVisible(true);
        
        
    }

    @FXML
    private ImageView postImagePreview;

    @FXML
    private Button btnChooseImage;

    private File selectedImageFile;

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if (file != null) {
            selectedImageFile = file;
            Image image = new Image(file.toURI().toString());
            postImagePreview.setImage(image);
        }
    }
}