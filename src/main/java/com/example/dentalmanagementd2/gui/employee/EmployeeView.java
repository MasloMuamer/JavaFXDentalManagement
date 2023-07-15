package com.example.dentalmanagementd2.gui.employee;

import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.business.service.UserServiceFactory;
import com.example.dentalmanagementd2.gui.Controller;
import com.example.dentalmanagementd2.gui.employee.dentist.EmployeeDentistPanel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class EmployeeView extends BorderPane {

    private ComboBox<User> dentistComboBox = new ComboBox<>();

    private final Button logoutButton = new Button("Odjava");

    private EmployeeDentistPanel employeeDentistPanel = new EmployeeDentistPanel();

    /*private VBox getEmployeeDentistPanel(int value, int dentistId) {
        if (value == 2) {
            employeeDentistPanel.setChosenDentist(dentistId);
            return this.employeeDentistPanel;
        }
        return null;
    }*/


   public EmployeeView(){
        setCenter(employeeDentistPanel);

        List<User> users = UserServiceFactory.USER_SERVICE.getUserService().findAllByPrivileges("2");
        dentistComboBox.setItems(FXCollections.observableList(users));
        dentistComboBox.getSelectionModel().select(0);

        dentistComboBox.buttonCellProperty();



    HBox mainMenu = new HBox();
        mainMenu.setSpacing(5);
        mainMenu.setPadding(new Insets(12, 12, 12, 12));
       mainMenu.setAlignment(Pos.CENTER);
        logoutButton.setOnAction(Controller.instance().getEventBus().getLogoutEvent());
        logoutButton.setText("Odjava: Prijavljenji korisnik - "+Controller.instance().getLoggedUser().getName()+"");



        mainMenu.getChildren().addAll(dentistComboBox);

    HBox logoutHBox = new HBox(logoutButton);
        logoutHBox.setAlignment(Pos.CENTER_RIGHT);
        logoutHBox.setPadding(new Insets(12, 12, 12, 12));

    GridPane topPane = new GridPane();
        topPane.add(mainMenu,0, 0);
        topPane.add(logoutHBox, 1, 0);
        setTop(topPane);

       DropShadow dropShadow = new DropShadow();
       dropShadow.setColor(Color.AQUA);
       dentistComboBox.setEffect(dropShadow);
       logoutButton.setEffect(dropShadow);

       dentistComboBox.setStyle("-fx-background-color: silver;");
       logoutButton.setStyle("-fx-background-color: silver;");

       Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
       dentistComboBox.setStyle("-fx-font-weight: bold;");
       logoutButton.setFont(boldFont);

       Light.Distant light = new Light.Distant();
       light.setAzimuth(-135);
       light.setElevation(30);

       Lighting lighting = new Lighting();
       lighting.setLight(light);
       dentistComboBox.setEffect(lighting);
       logoutButton.setEffect(lighting);

       Rectangle customShape = new Rectangle(100, 50); // Prilagodite veličinu prema potrebi
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



