package com.example.dentalmanagementd2.gui.admin.user;

import com.example.dentalmanagementd2.business.model.Privilege;
import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.business.service.PrivilegeServiceFactory;
import com.example.dentalmanagementd2.business.service.UserServiceFactory;
import com.example.dentalmanagementd2.business.service.UserServiceLocal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkinBase;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.event.MouseEvent;
import java.util.List;

public class UserAdminPanel extends VBox {
    private Label titleLabel = new Label("Administracija korisnika");
    private ObservableList<User> usersObservableList;
    private TableView<User> usersTableView = new TableView<>();
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField nameTextField = new TextField();
    private final TextField surnameTextField = new TextField();
    private final ChoiceBox<Privilege> privilegesChoiceBox = new ChoiceBox<>();

    private Button addUserButton = new Button("Dodaj korisnika");
    private Button deleteUserButton = new Button("Obriši korisnika");
    private Button updateButton = new Button("Ispravka unosa korisnika");

    public UserAdminPanel(){
        titleLabel.setFont(new Font("Arial", 22));
        setSpacing(5);
        setPadding(new Insets(12, 12, 12, 12));


        TableColumn<User, String> usernameColumn = new TableColumn<>("Korisničko ime");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        TableColumn<User, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));

        TableColumn<User, String> privilegeColumn = new TableColumn<>("Privilegija korisnika");
        privilegeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPrivilege().getName()));

        List<User> usersList = UserServiceFactory.USER_SERVICE.getUserService().findAll();
        usersObservableList = FXCollections.observableList(usersList);
        usersTableView.setItems(usersObservableList);
        usersTableView.getColumns().addAll(usernameColumn, nameColumn, surnameColumn, privilegeColumn);
        getChildren().addAll(titleLabel, usersTableView, getForm(), getNewForm());


        usersTableView.setStyle("-fx-control-inner-background: rgb(135, 206, 250);"); // Promijenite boju podloge prema potrebi

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        usersTableView.setEffect(lighting);


    }


    private HBox getForm(){
        HBox form = new HBox();
        form.setSpacing(5);
        form.setAlignment(Pos.CENTER);



        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        usernameTextField.setEffect(dropShadow);
        passwordField.setEffect(dropShadow);
        nameTextField.setEffect(dropShadow);
        surnameTextField.setEffect(dropShadow);
        privilegesChoiceBox.setEffect(dropShadow);

        usernameTextField.setStyle("-fx-background-color: aqua;");
        passwordField.setStyle("-fx-background-color:aqua;");
        nameTextField.setStyle("-fx-background-color: aqua;");
        surnameTextField.setStyle("-fx-background-color: aqua;");
        privilegesChoiceBox.setStyle("-fx-background-color: aqua;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        usernameTextField.setFont(boldFont);
        passwordField.setFont(boldFont);
        nameTextField.setFont(boldFont);
        surnameTextField.setFont(boldFont);
        privilegesChoiceBox.setStyle("-fx-font-weight: bold;");

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        usernameTextField.setEffect(lighting);
        passwordField.setEffect(lighting);
        nameTextField.setEffect(lighting);
        surnameTextField.setEffect(lighting);
        privilegesChoiceBox.setEffect(lighting);

        List<Privilege> privileges = PrivilegeServiceFactory.PRIVILEGE_SERVICE.getPrivilegeService().findAll();
        privilegesChoiceBox.setItems(FXCollections.observableList(privileges));
        privilegesChoiceBox.getSelectionModel().select(0);

        usernameTextField.setPromptText("Username:");
        passwordField.setPromptText("Password:");
        nameTextField.setPromptText("Ime:");
        surnameTextField.setPromptText("Prezime:");

        usersTableView.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    User selectedUser = (User) row.getItem();

                    usernameTextField.setText(selectedUser.getUsername());
                    passwordField.setText(selectedUser.getPassword());
                    nameTextField.setText(selectedUser.getName());
                    surnameTextField.setText(selectedUser.getSurname());
                    privilegesChoiceBox.setValue(selectedUser.getIdPrivilege());

                }
            });
            return row ;
        });

        form.getChildren().addAll(
                usernameTextField,
                passwordField,
                nameTextField,
                surnameTextField,
                privilegesChoiceBox
               );
        return form;

    }


    public HBox getNewForm() {
        HBox newForm = new HBox();
        newForm.setSpacing(5);
        newForm.setAlignment(Pos.CENTER);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        addUserButton.setEffect(dropShadow);
        deleteUserButton.setEffect(dropShadow);
        updateButton.setEffect(dropShadow);

        addUserButton.setStyle("-fx-background-color: silver;");
        deleteUserButton.setStyle("-fx-background-color: silver;");
        updateButton.setStyle("-fx-background-color: silver;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        addUserButton.setFont(boldFont);
        deleteUserButton.setFont(boldFont);
        updateButton.setFont(boldFont);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        addUserButton.setEffect(lighting);
        deleteUserButton.setEffect(lighting);
        updateButton.setEffect(lighting);


        addUserButton.setOnAction(this::addUser);
        deleteUserButton.setOnAction(this::removeUser);
        updateButton.setOnAction(this::edit);

        newForm.getChildren().addAll(
                addUserButton,
                deleteUserButton,
                updateButton
        );

        return newForm;
    }

    private void edit(ActionEvent actionEvent) {
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        UserServiceLocal userService = UserServiceFactory.USER_SERVICE.getUserService();

        String pwdField = passwordField.getText(); // Dobijanje unesene lozinke
        // Generisanje heš vrijednosti lozinke
        String hashedPassword = BCrypt.hashpw(String.valueOf(pwdField), BCrypt.gensalt());

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati korisnika iz tablice duplim klikom!");

            Region dialogPane = (Region) alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: silver;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alert.showAndWait();
            return;
        }
        String username = usernameTextField.getText();
        String password = hashedPassword;
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String privileges = privilegesChoiceBox.getValue().getName();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname == null || privileges.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Morate popuniti sva polja!");

            Region dialogPane = (Region) alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: silver;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alert.showAndWait();
            return;
        }

        usersTableView.refresh();

        selectedUser.setUsername(username);
        selectedUser.setPassword(password);
        selectedUser.setName(name);
        selectedUser.setSurname(surname);
        selectedUser.setIdPrivilege(privilegesChoiceBox.getValue());
        userService.edit(selectedUser);
        usersTableView.getSelectionModel().clearSelection();
        clearInput();


    }

    private void removeUser(ActionEvent actionEvent) {
       User selectedUserRemove = usersTableView.getSelectionModel().getSelectedItem();
       UserServiceLocal userService = UserServiceFactory.USER_SERVICE.getUserService();

        if (selectedUserRemove == null) {
            Alert alertRemove = new Alert(Alert.AlertType.WARNING);
            alertRemove.setTitle("Upozorenje");
            alertRemove.setHeaderText(null);
            alertRemove.setContentText("Morate selektirati korisnika iz tablice!");

            Region dialogPane = (Region) alertRemove.getDialogPane();
            dialogPane.setStyle("-fx-background-color: silver;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alertRemove.showAndWait();
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String privileges = privilegesChoiceBox.getValue().getName();

        userService.removeById(selectedUserRemove.getId());
       usersObservableList.remove(selectedUserRemove);

        usersTableView.getSelectionModel().clearSelection();
        clearInput();

       selectedUserRemove.setUsername(username);
        selectedUserRemove.setPassword(password);
        selectedUserRemove.setName(name);
        selectedUserRemove.setSurname(surname);
        userService.remove(selectedUserRemove);
    }

    private void addUser(ActionEvent event){
        String pwdField = passwordField.getText(); // Dobijanje unesene lozinke
        // Generisanje heš vrijednosti lozinke
        String hashedPassword = BCrypt.hashpw(String.valueOf(pwdField), BCrypt.gensalt());

        if (validate()) {
            User users = new User();
            users.setUsername(usernameTextField.getText());
            users.setPassword(hashedPassword);
            users.setName(nameTextField.getText());
            users.setSurname(surnameTextField.getText());
            users.setIdPrivilege(privilegesChoiceBox.getValue());
            UserServiceLocal userService = UserServiceFactory.USER_SERVICE.getUserService();
            userService.create(users);
            usersObservableList.add(users);
            usersTableView.getSelectionModel().clearSelection();
            clearInput();
        }else {

            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            //PRIKAZATI SMISLENU PORUKU ZA SVAKO POLJE AKO SE OSTAVI PRAZNO
            //usernameTextField.setPromptText("Morate popuniti polje:");

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText(null);
                alert.setContentText("Morate popuniti sva polja!");

                Region dialogPane = (Region) alert.getDialogPane();
                dialogPane.setStyle("-fx-background-color: silver;"); // Promijenite boju pozadine prozora prema potrebi

                Light.Distant light = new Light.Distant();
                light.setAzimuth(-135.0);

                Lighting lighting = new Lighting();
                lighting.setLight(light);
                lighting.setSurfaceScale(5.0);

                dialogPane.setEffect(lighting);

                alert.showAndWait();
                return;
            }


        }
    }

    private boolean validate() {
        return !usernameTextField.getText().isBlank()
                && !passwordField.getText().isBlank()
                && !nameTextField.getText().isBlank()
                && !surnameTextField.getText().isBlank();
    }


    private void clearInput(){
        usernameTextField.clear();
        passwordField.clear();
        nameTextField.clear();
        surnameTextField.clear();
    }
}

