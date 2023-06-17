package com.example.dentalmanagementd2.gui.events;

import com.example.dentalmanagementd2.gui.Controller;
import com.example.dentalmanagementd2.gui.login.LoginView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogoutEvent implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Controller.instance().setLoggedUser(null);
        Controller.instance().getStage().setTitle("Login");

        LoginView loginView = new LoginView();
        Controller.instance().setLoginView(loginView);
        Scene scene = new Scene(loginView,1000, 555);
        Stage stage =new Stage();
        Controller.instance().getStage().setScene(scene);

    }
}
