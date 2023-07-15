package com.example.dentalmanagementd2.gui.login;

import com.example.dentalmanagementd2.gui.Controller;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

public class LoginView extends GridPane {

    private final Label usernameLabel = new Label("Korisničko ime: ");
    private final Label passwordLabel = new Label("Lozinka: ");
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Prijava");

    private final Button cancelButton = new Button("Odustani");
    private final Label messageLabel = new Label();//ovdje ne piše ništa..sadržaj ćemo možda dinamički popuniti

    public LoginView() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgb(138, 43, 226);");


        StackPane root = new StackPane(new Label("Login Form"));
        Rectangle border = new Rectangle(1100, 555); // Prilagodite veličinu prema potrebi
        border.setStroke(Color.BLACK); // Postavlja boju obruba
        border.setStrokeWidth(10); // Postavlja debljinu obruba
        border.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(border);



        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        usernameLabel.setEffect(dropShadow);
        passwordLabel.setEffect(dropShadow);
        usernameTextField.setEffect(dropShadow);
        passwordField.setEffect(dropShadow);
        loginButton.setEffect(dropShadow);
        cancelButton.setEffect(dropShadow);

        usernameTextField.setStyle("-fx-background-color: rgb(135, 206, 250);");
        passwordField.setStyle("-fx-background-color: rgb(135, 206, 250);");
        loginButton.setStyle("-fx-background-color: silver;");
        cancelButton.setStyle("-fx-background-color: silver;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        usernameLabel.setFont(boldFont);
        passwordLabel.setFont(boldFont);
        usernameTextField.setFont(boldFont);
        passwordField.setFont(boldFont);
        loginButton.setFont(boldFont);
        cancelButton.setFont(boldFont);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        usernameTextField.setEffect(lighting);
        passwordField.setEffect(lighting);
        loginButton.setEffect(lighting);
        cancelButton.setEffect(lighting);





        //username
        add(usernameLabel, 0, 0);
        add(usernameTextField, 1, 0);
        //password
        add(passwordLabel, 0, 1);
        add(passwordField, 1, 1);
        //FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);
        flowPane.setHgap(10);

        loginButton.setOnAction(Controller.instance().getEventBus().getLoginEvent());
        cancelButton.setOnAction(Controller.instance().getEventBus().getCancelEvent());
        flowPane.getChildren().addAll(loginButton, cancelButton);
        add(flowPane, 1, 2);
        //message
        add(messageLabel, 1, 3);
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setLoginMessage(String message){
        messageLabel.setText(message);
    }
}


