module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jakarta.mail;
    requires jakarta.activation;
    requires java.desktop;
    requires javafx.graphics;

    opens com.example to javafx.fxml;
    exports com.example;
}