package com.example.dentalmanagementd2.gui.events;

import com.example.dentalmanagementd2.business.model.Privilege;
import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.business.service.UserServiceFactory;
import com.example.dentalmanagementd2.gui.Controller;
import com.example.dentalmanagementd2.gui.admin.AdminView;
import com.example.dentalmanagementd2.gui.employee.EmployeeView;
import com.example.dentalmanagementd2.gui.login.LoginView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class LoginEvent implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {

        LoginView loginView = Controller.instance().getLoginView();
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            loginView.setLoginMessage("Obavezno unošenje korisničkog imena i lozinke. ");
            return;
        }
        User user = UserServiceFactory.USER_SERVICE.getUserService().login(username, password);
        if (user == null){
            loginView.setLoginMessage("Pogrešan unos korisničkog imena ili lozinke");
        }else {
            Controller.instance().setLoggedUser(user);
            Privilege privilege = user.getIdPrivilege();
            BorderPane mainPanel;
            if ("admin".equalsIgnoreCase(privilege.getName())){
                mainPanel = new AdminView();
                Controller.instance().setAdminView((AdminView) mainPanel);
                Controller.instance().getStage().setTitle("Admin Panel: " + Controller.instance().getLoggedUser().getName());




            }else{
                mainPanel = new EmployeeView();
                Controller.instance().setEmployeeView((EmployeeView) mainPanel);
                Controller.instance().getStage().setTitle("Dentist Panel: " + user.getName()+" " + user.getSurname());




            }
            Scene scene = new Scene(mainPanel,1200, 600);
            Controller.instance().getStage().setScene(scene);
        }

    }
}
