package com.example;

import java.io.File;
import java.net.URL;
import java.util.Random;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
        String regex = "^[a-zA-Z]+\\.([a-zA-Z]+)?@(ug\\.)?bilkent\\.edu\\.tr$";
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

    public boolean validationOnSignIn(String email, String password){
        for(int i=0; i<App.getUsers().size(); i++){
            if(tfEmailSin.getText().equals(App.getUsers().get(i).getEmail())){
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
        if(tfEmailSin.getText().equals("")){
            Alert alert = new Alert(AlertType.ERROR);
            //alert.setTitle("Wrong email or password");
            alert.setHeaderText(null); // No header
            alert.setContentText("Enter your email to the email field.");
            alert.showAndWait();
        }
        //TODO: Will check if email exists then send random generated password through email
        for(int i=0; i<App.getUsers().size(); i++){
            if(tfEmailSin.getText().equals(App.getUsers().get(i).getEmail())){
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

        // Yeni post için VBox (tekil kart)
        VBox postBox = new VBox(5);
        postBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);

        postBox.getChildren().addAll(titleLabel, contentLabel);

        // Eğer resim seçildiyse, ImageView ile göster
        if (selectedImageFile != null) {
            ImageView imageView = new ImageView(new Image(selectedImageFile.toURI().toString()));
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            postBox.getChildren().add(imageView);
        }

        // Post'u scrollPane içindeki container'a ekle
        postContainer.getChildren().add(0, postBox); // en üste ekler

        // Alanları temizle
        tfPostTitle.clear();
        tfPostContent.clear();
        selectedImageFile = null;
        postImagePreview.setImage(null);
    }

    @FXML
    public void createPost(ActionEvent event) {
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
