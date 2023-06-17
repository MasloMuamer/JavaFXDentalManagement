package com.example.dentalmanagementd2.gui.login;

import com.example.dentalmanagementd2.gui.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

        //username
        add(usernameLabel, 0, 0);
        add(usernameTextField, 1, 0);
        //password
        add(passwordLabel, 0, 1);
        add(passwordField, 1, 1);
        //FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);
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

    //String password = passwordField.getText(); // Dobijanje unesene lozinke

    //private org.mindrot.jbcrypt.BCrypt BCrypt;
    // Generisanje heš vrijednosti lozinke
    //String hashedPassword = BCrypt.hashpw(String.valueOf(passwordField), BCrypt.gensalt());


    public void setLoginMessage(String message){
        messageLabel.setText(message);
    }
}


