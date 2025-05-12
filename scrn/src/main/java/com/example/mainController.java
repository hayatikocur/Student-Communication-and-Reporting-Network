package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            if(validateBilkentEmail(tfEmailSup.getText())){
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
         String regex = "^[a-zA-Z]+\\.[a-zA-Z]+@ug\\.bilkent\\.edu\\.tr$";
        if(!email.matches(regex)){    
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null); // No header
            alert.setContentText("Email is not valid!");
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
        String[] parts = email.split("@")[0].split("\\.");
        String name = parts[0];
        String surname = parts[1];

        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1).toLowerCase();

        SendGmail.sendEmail(email);
        //TODO: in this code everybody is added as users not separated such as student or authority. separate them. 
        App.getUsers().add(new User(name, surname, email, tfPasswordSup.getText()));
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
            if(!validationOnSignIn(tfEmailSin.getText(), tfPasswordSin.getText())){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Wrong email or password");
                alert.setHeaderText(null); // No header
                alert.setContentText("Entered email or password wrong!");
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

    public boolean validationOnSignIn(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create and assign current user if credentials are valid
                User user = new User(
                        rs.getString("userName"),
                        rs.getString("userSurname"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                user.userId = rs.getInt("userId");
                user.setMailNotification(rs.getBoolean("mailNotification"));
                user.setAppNotification(rs.getBoolean("appNotification"));
                // Set current user in app context
                App.setCurrentUser(user);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        //TODO: Will check if email exists then send random generated password through email.
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


    //Şunları database bağlayın.
    public void setEditAction(Event arg0){ 

        if (tfProfilePassword.isEditable() == false) {
            tfProfilePassword.setEditable(true);
            tfProfilePassword.setVisible(true);
            tfProfileName.setEditable(true);
            tfProfileSurname.setEditable(true);
        }
        else{
            tfProfileSurname.setEditable(false);
            tfProfilePassword.setVisible(false);
            tfProfileName.setEditable(false);
            tfProfilePassword.setEditable(false);

        }


        
        
    }   

    //Bunu da
    public void confirmChangesToProfile(ActionEvent event){
        //User'ın passwordu ve username i değiştirmesi lazım.
        App.getCurrentUser().changeName(tfProfileName.getText());
        App.getCurrentUser().changeSurname(tfProfileSurname.getText());
        App.getCurrentUser().changePassword(tfProfilePassword.getText());

        //System.out.println("hello world");
        //System.out.println(App.getCurrentUser().getUserName());
    }

    @FXML
    ToggleButton mailToggle;

    @FXML
    ToggleButton appNotiToggle;

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
            profileLabel.setText("Student"); // ya da Student / Authority'ye göre ayır
        }

        
        if (mailToggle != null) {
            setupToggleButton(mailToggle, App.getCurrentUser().isMailNotificationEnabled());
        }

        if (appNotiToggle != null) {
            setupToggleButton(appNotiToggle, App.getCurrentUser().isAppNotificationEnabled());
        }
    }

    
    @FXML
    public void deleteAccount(ActionEvent event) {
        // Şimdilik boş bırakıldı
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
}
