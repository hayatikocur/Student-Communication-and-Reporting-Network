package com.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

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

public class mainController implements Initializable{

    
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
    
    //Category ToggleButtons
    @FXML private ToggleButton brokenEquipmentBtn;
    @FXML private ToggleButton roadIssuesBtn;
    @FXML private ToggleButton buildingIssuesBtn;
    @FXML private ToggleButton cateringIssuesBtn;
    @FXML private ToggleButton dormIssuesBtn;
    @FXML private ToggleButton missingEquipmentBtn;
    // Time ToggleButtons
    @FXML private ToggleButton lastWeekButton;
    @FXML private ToggleButton lastMonthButton;
    @FXML private ToggleButton lastYearButton;
    // Location ToggleButtons
    @FXML private ToggleButton bBuildingButton;
    @FXML private ToggleButton cateringBuildingButton;
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


    public boolean validateBilkentEmail(String email) {
        // 1. Validate format
        String regex = "^[a-zA-Z]+\\.([a-zA-Z]+)?@(ug\\.)?bilkent\\.edu\\.tr$";
        if (!email.matches(regex)) {
            showError("Invalid Email", "Email is not valid!");
            return false;
        }

        // 2. Validate password inputs
        String password = tfPasswordSup.getText();
        String confirmPassword = tfConfirmPasswordSup.getText();

        if (password.isEmpty()) {
            showError("Password mistake", "Please enter a password!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showError("Password mistake", "Passwords do not match!");
            return false;
        }

        // 3. Check email existence in database
        if (emailExistsInDatabase(email)) {
            showError("Email already exists", "User with the same email already exists");
            return false;
        }

        // 4. Parse name and surname
        String[] parts = email.split("@")[0].split("\\.");
        String name = capitalize(parts[0]);
        String surname = capitalize(parts[1]);

        // 5. Send confirmation email
        SendGmail.sendEmail(email);

        // 6. Create user and save
        if (email.contains("ug")) {
            Student s = new Student(name, surname, email, password, true, true, false);
            s.saveToDatabase();
        } else {
            Authority a = new Authority(name, surname, email, password, true, true, false);
            a.saveToDatabase();
        }

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

             if (!validateUserFromDatabase(email, password)) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Wrong email or password");
                 alert.setHeaderText(null);
                 alert.setContentText("Email or Password wrong!");
                 alert.showAndWait();
             } else {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                 Parent root = loader.load();

                 // Look up UI elements
                 TextField tfProfileName = (TextField) root.lookup("#tfProfileName");
                 TextField tfProfileSurname = (TextField) root.lookup("#tfProfileSurname");
                 TextField tfProfileMail = (TextField) root.lookup("#tfProfileMail");
                 TextField tfProfilePassword = (TextField) root.lookup("#tfProfilePassword");
                 Label profileLabel = (Label) root.lookup("#profileLabel");

                 Stage stage = new Stage();
                 stage.setScene(new Scene(root));
                 stage.setOpacity(0);
                 stage.show();

                 // Fill profile info
                 App.getCurrentUser().setProfileFields(tfProfileName, tfProfileSurname, tfProfileMail, tfProfilePassword, profileLabel);

                 stage.close();

                 Thread.sleep(175);
                 loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
                 root = loader.load();

                 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                 stage.setScene(new Scene(root));
                 stage.show();
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
    }


    public boolean validationOnSignIn(String email, String password) {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("Missing db.properties file");
                return false;
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    // Build appropriate user (Student or Authority)
                    String name = rs.getString("userName");
                    String surname = rs.getString("userSurname");
                    boolean mailNotification = rs.getBoolean("mailNotification");
                    boolean appNotification = rs.getBoolean("appNotification");
                    boolean isAuthority = rs.getBoolean("isAuthority");

                    User user;
                    if (isAuthority) {
                        user = new Authority(name, surname, email, storedPassword, mailNotification, appNotification, true);
                    } else {
                        user = new Student(name, surname, email, storedPassword, mailNotification, appNotification, false);
                    }

                    App.setCurrentUser(user);
                    return true;
                } else {
                    return false; // Password mismatch
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Email not found
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
        
    public void forgotPassword(ActionEvent e) {
        String email = tfEmailSin.getText().trim();

        if (email.isEmpty()) {
            showError("Input Error", "Enter your email to the email field.");
            return;
        }

        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                showError("Config Error", "Cannot load db.properties.");
                return;
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            showError("I/O Error", "Could not load DB configuration.");
            return;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        String selectQuery = "SELECT * FROM users WHERE email = ?";
        String updateQuery = "UPDATE users SET password = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setString(1, email);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String newTempPassword = tempPassword;

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newTempPassword);
                    updateStmt.setString(2, email);
                    int updated = updateStmt.executeUpdate();

                    if (updated > 0) {
                        // Optionally update in-memory object if loaded
                        SendGmail.sendPassword(email, newTempPassword);
                        showInfo("Password Reset", "A new password has been sent to your email.");
                        return;
                    }
                }
            }

            showError("User Not Found", "No user registered with this email.");

        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Database Error", "Could not query or update user.");
        }
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


    private void updateToggleText(ToggleButton button) {
        if (button != null) {
            button.setText(button.isSelected() ? "Turn On" : "Turn Off");
            button.setOnAction(e -> {
            button.setText(button.isSelected() ? "Turn On" : "Turn Off");
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            "Maintenance", "Cleaning", "Electrical", "Safety", "Other"
        ));
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

                                        if (postOwner != null && resolver != null) {
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
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            showError("Deletion Failed", "No user is currently signed in.");
            return;
        }

        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                showError("Configuration Error", "Missing db.properties file.");
                return;
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            showError("I/O Error", "Could not load database configuration.");
            return;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, currentUser.getEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                App.setCurrentUser(null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Your account has been successfully deleted.");
                alert.showAndWait();

                // Load login page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showError("Account Deletion Failed", "User could not be found in the database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Database Error", "An error occurred while deleting the account.");
        }
    }

    private void setupToggleButton(ToggleButton toggle, boolean isOn) {
        toggle.setSelected(isOn);
        updateToggleAppearance(toggle);
    }

    // Toggle durumuna göre görünüm güncelle
    private void updateToggleAppearance(ToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setText("Turn Off");
            toggle.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-background-radius: 20;");
        } else {
            toggle.setText("Turn On");
            toggle.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-background-radius: 20;");
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
    
        if (cateringIssuesBtn.isSelected()) {
            selectedCategories.add("cateringIssues");
        } 
    
        if (dormIssuesBtn.isSelected()) {
            selectedCategories.add("dormIssues");
        } 
        
        if (missingEquipmentBtn.isSelected()) {
            selectedCategories.add("missingEquipment");
        } 

        // Time selections
        if (lastWeekButton.isSelected()) {
            selectedDates.add("Last Week");
        }
        if (lastMonthButton.isSelected()) {
            selectedDates.add("Last Month");
        }
        if (lastYearButton.isSelected()) {
            selectedDates.add("Last Year");
        }

        // Location selections
        if (bBuildingButton.isSelected()) {
            selectedLocations.add("B Building");
        }
        if (cateringBuildingButton.isSelected()) {
            selectedLocations.add("Catering Building");
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
    
    ArrayList<String> selectedCategories = new ArrayList();
    ArrayList<String> selectedDates = new ArrayList();
    ArrayList<String> selectedLocations = new ArrayList();
    
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

                        if (postOwner != null && resolver != null) {
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
        fullPostContent.getChildren().addAll(toggleCommentButton, commentSection);
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

    private boolean validateUserFromDatabase(String email, String password) {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password); // ❗Consider hashing in production

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // User exists – set current user info
                User user = new User(
                        rs.getString("userName"),
                        rs.getString("userSurname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("mailNotification"),
                        rs.getBoolean("appNotification"),
                        rs.getBoolean("isAuthority")
                );
                App.setCurrentUser(user);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailExistsInDatabase(String email) {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        String url = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        String query = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If any row is returned, email exists

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    private String createRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
