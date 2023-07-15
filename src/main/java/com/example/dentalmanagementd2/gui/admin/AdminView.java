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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    private VBox getDentistAdminPanel(int value, int dentistId) {
        if (value == 2) {
            dentistAdminPanel.setChosenDentist(dentistId);
            return this.dentistAdminPanel;
        }

        return null;
    }

    public AdminView(){
        setCenter(userAdminPanel);

        List<User> users = UserServiceFactory.USER_SERVICE.getUserService().findAllByPrivileges("2");
        dentistComboBox.setItems(FXCollections.observableList(users));
        dentistComboBox.getSelectionModel().select(0);

        userButton.setDefaultButton(true);

        HBox mainMenu = new HBox();
        mainMenu.setSpacing(5);
        mainMenu.setPadding(new Insets(12, 12, 12, 12));
        mainMenu.setAlignment(Pos.CENTER);
        logoutButton.setOnAction(Controller.instance().getEventBus().getLogoutEvent());
        logoutButton.setText("Odjava: Prijavljenji korisnik - "+Controller.instance().getLoggedUser().getName()+"");
        userButton.setOnAction(e->setCenter(userAdminPanel));

        dentistComboBox.setOnMouseClicked(e->setCenter(
                this.getDentistAdminPanel(dentistComboBox.getValue().getIdPrivilege().getId(), dentistComboBox.getValue().getId())
        ));

        mainMenu.getChildren().addAll(userButton, dentistComboBox);

        HBox logoutHBox = new HBox(logoutButton);
        logoutHBox.setAlignment(Pos.CENTER_RIGHT);
        logoutHBox.setPadding(new Insets(12, 12, 12, 12));

        GridPane topPane = new GridPane();
        topPane.add(mainMenu,0, 0);
        topPane.add(logoutHBox, 1, 0);
        setTop(topPane);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        userButton.setEffect(dropShadow);
        dentistComboBox.setEffect(dropShadow);
        logoutButton.setEffect(dropShadow);

        userButton.setStyle("-fx-background-color: silver;");
        dentistComboBox.setStyle("-fx-background-color: silver;");
        logoutButton.setStyle("-fx-background-color: silver;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        userButton.setFont(boldFont);
        dentistComboBox.setStyle("-fx-font-weight: bold;");
        logoutButton.setFont(boldFont);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        userButton.setEffect(lighting);
        dentistComboBox.setEffect(lighting);
        logoutButton.setEffect(lighting);

        Rectangle customShape = new Rectangle(100, 50); // Prilagodite veličinu prema potrebi
        userButton.setShape(customShape);
        dentistComboBox.setShape(customShape);
        logoutButton.setShape(customShape);


        setStyle("-fx-background-color: rgb(138, 43, 226);");

        StackPane root = new StackPane(new Label("Login Form"));
        Rectangle border = new Rectangle(1100, 555); // Prilagodite veličinu prema potrebi
        border.setStroke(Color.BLACK); // Postavlja boju obruba
        border.setStrokeWidth(10); // Postavlja debljinu obruba
        border.setStrokeType(StrokeType.OUTSIDE);
        root.getChildren().add(border);
    }
}

