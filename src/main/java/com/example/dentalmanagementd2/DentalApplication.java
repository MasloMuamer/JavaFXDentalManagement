package com.example.dentalmanagementd2;

import com.example.dentalmanagementd2.gui.Controller;
import com.example.dentalmanagementd2.gui.login.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DentalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LoginView loginView = new LoginView();
        Controller.instance().setStage(stage);
        Controller.instance().setLoginView(loginView);
        Scene scene = new Scene(loginView, 1100, 555);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();

    }
}