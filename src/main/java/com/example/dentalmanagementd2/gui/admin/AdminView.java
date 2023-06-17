package com.example.dentalmanagementd2.gui.admin;

import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.business.service.UserServiceFactory;
import com.example.dentalmanagementd2.gui.Controller;
import com.example.dentalmanagementd2.gui.admin.dentist.DentistAdminPanel;
import com.example.dentalmanagementd2.gui.admin.user.UserAdminPanel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class AdminView extends BorderPane {

    private final Button userButton = new Button("Korisnici");

    /*
    <li>1. izbacite toggle buttons i ubacite dentist choice box
    <li>2. postavite listener na choice box tako da kad god odaberete drugog stomatologa prikaže u tabeli ispod
    samo njegove rezervacije
     */
    private ComboBox<User> dentistComboBox = new ComboBox<>();



    private final Button logoutButton = new Button("Odjava");

    private UserAdminPanel userAdminPanel = new UserAdminPanel();
    private DentistAdminPanel dentistAdminPanel = new DentistAdminPanel();


    public AdminView(){
        setCenter(userAdminPanel);

        List<User> users = UserServiceFactory.USER_SERVICE.getUserService().findAllByPrivileges("2");
        dentistComboBox.setItems(FXCollections.observableList(users));
        dentistComboBox.getSelectionModel().select(0);


        userButton.setDefaultButton(true);

        HBox mainMenu = new HBox();
        mainMenu.setSpacing(5);
        mainMenu.setPadding(new Insets(12, 12, 12, 12));
        logoutButton.setOnAction(Controller.instance().getEventBus().getLogoutEvent());
        logoutButton.setText("Odjava: Prijavljenji korisnik - "+Controller.instance().getLoggedUser().getName()+"");
        userButton.setOnAction(e->setCenter(userAdminPanel));

        dentistComboBox.setOnMouseClicked(e->setCenter(
                dentistComboBox.getValue().getIdPrivilege().getId() == 2 ? dentistAdminPanel : null));

        System.out.println(dentistComboBox.getValue().getIdPrivilege().getId());


        mainMenu.getChildren().addAll(userButton, dentistComboBox);

        HBox logoutHBox = new HBox(logoutButton);
        logoutHBox.setAlignment(Pos.CENTER_RIGHT);
        logoutHBox.setPadding(new Insets(12, 12, 12, 12));

        GridPane topPane = new GridPane();
        topPane.add(mainMenu,0, 0);
        topPane.add(logoutHBox, 1, 0);
        setTop(topPane);
    }
}

