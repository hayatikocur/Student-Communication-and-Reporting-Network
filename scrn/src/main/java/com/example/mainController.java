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
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
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
    TextField tfPasswordSup;
    @FXML
    TextField tfConfirmPasswordSup;

    //

    User currentUser;
    //Şunu authority veya student olarak değiştirmek lazım duruma göre.
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
    ImageView profileEditIcon;

  
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
        String regex = "^[a-zA-Z]+\\.[a-zA-Z]+@bilkent\\.edu\\.tr$";
        if(!email.matches(regex)){
            return false;
        }
        if(!tfPasswordSup.getText().equals(tfConfirmPasswordSup.getText())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password mistake");
            alert.setHeaderText(null); // No header
            alert.setContentText("passwords do not match");
            alert.showAndWait();
            return false;
        }
        String[] parts = email.split("@")[0].split("\\.");
        String name = parts[0];
        String surname = parts[1];
        tfProfileName.setText(name);
        tfProfileSurname.setText(surname);
        tfProfilePassword.setText(tfPasswordSup.getText());
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
                alert.setTitle("wrong email or password");
                alert.setHeaderText(null); // No header
                alert.setContentText("you entered email or password wrong");
                alert.showAndWait();
            }
            else{
                Thread.sleep(175);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
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

    public boolean validationOnSignIn(String email, String password){
        for(int i=0; i<App.getUsers().size(); i++){
            if(tfEmailSin.getText().equals(App.getUsers().get(i).getEmail())){
                currentUser = App.getUsers().get(i);
                if(currentUser.getPassword().equals(password)){

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

    public void setProfileIconAction(Event arg0){
        if (tfProfileName.isEditable() == false) {
            tfProfileSurname.setEditable(true);
            tfProfilePassword.setEditable(true);
            tfProfilePassword.setVisible(true);
            tfProfileMail.setEditable(true);
            tfProfileName.setEditable(true);
        }
        else{
            tfProfileSurname.setEditable(false);
            tfProfilePassword.setEditable(false);
            tfProfilePassword.setVisible(false);
            tfProfileMail.setEditable(false);
            tfProfileName.setEditable(false);
        }
    public void setProfileIconAction(Event arg0){ 
        tfProfilePassword.setEditable(true);
        tfProfilePassword.setVisible(true);
        tfProfileMail.setEditable(true);
        tfProfileName.setEditable(true);
        tfProfileSurname.setEditable(true);
        
        
    }

    public void confirmChangesToProfile(ActionEvent event){

        tfProfileSurname.setEditable(false);
        tfProfilePassword.setEditable(false);
        tfProfilePassword.setVisible(false);
        tfProfileMail.setEditable(false);
        tfProfileName.setEditable(false);
        
    }


     @Override
     public void initialize(URL arg0, ResourceBundle arg1) {
      
     }    


}
