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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    }

    private HBox getForm(){
        HBox form = new HBox();
        form.setSpacing(5);
        form.setAlignment(Pos.CENTER);

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

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati korisnika iz tablice duplim klikom!");
            alert.showAndWait();
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String privileges = privilegesChoiceBox.getValue().getName();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Morate popuniti sva polja!");
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
        clearInput();


    }

    private void removeUser(ActionEvent actionEvent) {
       User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
       UserServiceLocal userService = UserServiceFactory.USER_SERVICE.getUserService();


        userService.removeById(selectedUser.getId());
       usersObservableList.remove(selectedUser);

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati korisnika iz tablice duplim klikom!");
            alert.showAndWait();
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String privileges = privilegesChoiceBox.getValue().getName();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Morate popuniti sva polja!");
            alert.showAndWait();
            return;
        }



        selectedUser.setUsername(username);
        selectedUser.setPassword(password);
        selectedUser.setName(name);
        selectedUser.setSurname(surname);
        userService.remove(selectedUser);
    }
    String password = passwordField.getText(); // Dobijanje unesene lozinke

    // Generisanje heš vrijednosti lozinke
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    private void addUser(ActionEvent event){
        if (validate()) {
            User users = new User();
            users.setUsername(usernameTextField.getText());
            users.setPassword(passwordField.getText());
            users.setName(nameTextField.getText());
            users.setSurname(surnameTextField.getText());
            users.setIdPrivilege(privilegesChoiceBox.getValue());
            UserServiceLocal userService = UserServiceFactory.USER_SERVICE.getUserService();
            userService.create(users);
            usersObservableList.add(users);
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

