package com.example.dentalmanagementd2.gui.employee.dentist;

import com.dlsc.gemsfx.TimePicker;
import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.business.model.Patient;
import com.example.dentalmanagementd2.business.model.TypeOfCheckup;
import com.example.dentalmanagementd2.business.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDentistPanel extends VBox {

    int chosenDentist = 2;

    public void setChosenDentist(int value) {
        this.chosenDentist = value;
    }

    public int getChosenDentist() {
        return this.chosenDentist;
    }

    private Label titleLabel = new Label("Rezervacija pacijenata");
    private ObservableList<Patient> patientsObservableList;
    private ObservableList<Appointment> appointmentObservableList;
    private ObservableList<TypeOfCheckup> typeOfCheckupObservableList;

    private TableView<Patient> patientsTableView = new TableView<>();

    private TextField nameTextField = new TextField();
    private TextField surnameTextField = new TextField();
    private TextField phoneTextField = new TextField();
    private TextField emailTextField = new TextField();

    private DatePicker appointmentDatePicker = new DatePicker();
    private TimePicker appointmentTimePicker = new TimePicker();
    private ChoiceBox<TypeOfCheckup> typeOfCheckupsChoiceBox= new ChoiceBox<>();

    private Button addPatientButton  = new Button("Unesi Pacijenta");
    private Button deletePatientButton = new Button("Obriši Pacijenta");
    private Button editPatientButon = new Button("Ispravka unosa pacijenta");

    public EmployeeDentistPanel(){

        titleLabel.setFont(new Font("Arial", 22));
        setSpacing(5);
        setPadding(new Insets(12, 12, 12, 12));

        phoneTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d+")){
                    phoneTextField.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });

        TableColumn<Patient, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));

        TableColumn<Patient, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));

        TableColumn<Patient, Integer> phoneColumn = new TableColumn<>("Broj telefona");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("phone"));

        TableColumn<Patient, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));

        TableColumn<Patient, String> appointmentColumnDate = new TableColumn<>("Datum termina");
        appointmentColumnDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                .getAppointmentList().isEmpty() ? "Nema" :
                String.valueOf(cellData.getValue().getAppointmentList().get(0).getDate())));

        TableColumn<Patient, String> appointmentColumnTime = new TableColumn<>("Vrijeme termina");
        appointmentColumnTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()
                .getAppointmentList().isEmpty() ? "Nema" :
                String.valueOf(cellData.getValue().getAppointmentList().get(0).getTime())));

        TableColumn<Patient, String> typeOfCheckupColumn = new TableColumn<>("Vrsta pregleda");
        typeOfCheckupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeOfCheckupList().isEmpty() ? "Nema" : cellData.getValue().getTypeOfCheckupList().get(0).getName()));

        PatientService patientService = PatientServiceFactory.SERVICE.getPatientService();
        patientsObservableList = FXCollections.observableList(patientService.findAll());
        patientsTableView.setItems(patientsObservableList);
        patientsTableView.getColumns().addAll(nameColumn, surnameColumn, phoneColumn, emailColumn, appointmentColumnDate, appointmentColumnTime, typeOfCheckupColumn);

        getChildren().addAll(titleLabel, patientsTableView, getForm(), getNewForm());

        patientsTableView.setStyle("-fx-control-inner-background: rgb(135, 206, 250);"); // Promijenite boju podloge prema potrebi

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        patientsTableView.setEffect(lighting);
    }
    private HBox getForm(){
        HBox form = new HBox();
        form.setSpacing(5);
        form.setAlignment(Pos.CENTER);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        nameTextField.setEffect(dropShadow);
        surnameTextField.setEffect(dropShadow);
        phoneTextField.setEffect(dropShadow);
        emailTextField.setEffect(dropShadow);
        typeOfCheckupsChoiceBox.setEffect(dropShadow);
        appointmentDatePicker.setEffect(dropShadow);
        appointmentTimePicker.setEffect(dropShadow);

        nameTextField.setStyle("-fx-background-color: aqua;");
        surnameTextField.setStyle("-fx-background-color: aqua;");
        phoneTextField.setStyle("-fx-background-color: aqua;");
        emailTextField.setStyle("-fx-background-color:aqua;");
        typeOfCheckupsChoiceBox.setStyle("-fx-background-color: aqua;");
        appointmentDatePicker.setStyle("-fx-background-color: aqua;");
        appointmentTimePicker.setStyle("-fx-background-color: aqua;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        nameTextField.setFont(boldFont);
        surnameTextField.setFont(boldFont);
        phoneTextField.setFont(boldFont);
        emailTextField.setFont(boldFont);
        typeOfCheckupsChoiceBox.setStyle("-fx-font-weight: bold;");
        appointmentDatePicker.setStyle("-fx-font-weight: bold;");
        appointmentTimePicker.setStyle("-fx-font-weight: bold;");

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        nameTextField.setEffect(lighting);
        surnameTextField.setEffect(lighting);
        phoneTextField.setEffect(lighting);
        emailTextField.setEffect(lighting);
        typeOfCheckupsChoiceBox.setEffect(lighting);
        appointmentDatePicker.setEffect(lighting);
        appointmentTimePicker.setEffect(lighting);

        List<TypeOfCheckup> typeOfCheckups = TypeOfCheckupServiceFactory.TYPE_OF_CHECKUP_SERVICE.getTypeOfCheckupsService().findAll();
        typeOfCheckupsChoiceBox.setItems(FXCollections.observableList(typeOfCheckups));
        typeOfCheckupsChoiceBox.getSelectionModel().select(0);

        nameTextField.setPromptText("Ime:");
        surnameTextField.setPromptText("Prezime:");
        phoneTextField.setPromptText("Broj telefona:");
        emailTextField.setPromptText("Email:");


        LocalTime rightNow = LocalTime.now();
        LocalDate dateNow = LocalDate.now();

        patientsTableView.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Patient selectedPatient = (Patient) row.getItem();

                    nameTextField.setText(selectedPatient.getName());
                    surnameTextField.setText(selectedPatient.getSurname());
                    phoneTextField.setText(String.valueOf(selectedPatient.getPhone()));
                    emailTextField.setText(selectedPatient.getEmail());
                    appointmentDatePicker.setValue(dateNow);
                    appointmentTimePicker.setTime(rightNow);
                    typeOfCheckupsChoiceBox.setValue((TypeOfCheckup) selectedPatient.getTypeOfCheckupList());


                }
            });
            return row ;
        });

        form.getChildren().addAll(nameTextField,surnameTextField, phoneTextField, emailTextField,
                appointmentDatePicker, appointmentTimePicker, typeOfCheckupsChoiceBox);
        return form;

    }

    private HBox getNewForm(){
        HBox newForm = new HBox();
        newForm.setSpacing(5);
        newForm.setAlignment(Pos.CENTER);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.AQUA);
        addPatientButton.setEffect(dropShadow);
        deletePatientButton.setEffect(dropShadow);
        editPatientButon.setEffect(dropShadow);

        addPatientButton.setStyle("-fx-background-color: silver;");
        deletePatientButton.setStyle("-fx-background-color: silver;");
        editPatientButon.setStyle("-fx-background-color: silver;");

        Font boldFont = Font.font("Arial", FontWeight.BOLD, 11); // Promijenite naziv fonta i veličinu prema potrebi
        addPatientButton.setFont(boldFont);
        deletePatientButton.setFont(boldFont);
        editPatientButon.setFont(boldFont);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135);
        light.setElevation(30);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        addPatientButton.setEffect(lighting);
        deletePatientButton.setEffect(lighting);
        editPatientButon.setEffect(lighting);




        addPatientButton.setOnAction(this::addPatient);
        deletePatientButton.setOnAction(this::removePatient);
        editPatientButon.setOnAction(this::editPatient);

        newForm.getChildren().addAll(addPatientButton, deletePatientButton, editPatientButon);
        return newForm;
    }
    private void addPatient(ActionEvent event){
        Patient patient = new Patient();
        List<Patient> patientsList = new ArrayList<>();

        List<TypeOfCheckup> checkupList = new ArrayList<>();
        checkupList.add(typeOfCheckupsChoiceBox.getSelectionModel().getSelectedItem());

        List<Appointment> appointmentsList = new ArrayList<>();
        Appointment appointmentNew = new Appointment();

        AppointmentServiceLocal appointmentServiceService = AppointmentServiceFactory.
                APPOINTMENT_SERVICE.getAppointmentService();

        if (validate()) {
            patient.setName(nameTextField.getText());
            patient.setSurname(surnameTextField.getText());
            patient.setPhone(Integer.parseInt(phoneTextField.getText()));
            patient.setEmail(emailTextField.getText());
            patient.setTypeOfCheckupList(checkupList);

            // Dodavanje Appointment-a
            appointmentNew.setDate(appointmentDatePicker.getValue());
            appointmentNew.setTime(appointmentTimePicker.getTime());
            appointmentNew.setPatientList(patientsList);
            appointmentServiceService.create(appointmentNew);

            Appointment addedAppointment = appointmentServiceService.getLatestAppointment();
            appointmentsList.add(addedAppointment);

            // Kreiranje korisnika
            patientsList.add(patient);
            patient.setAppointmentList(appointmentsList);

            PatientServiceLocal patientService = PatientServiceFactory.SERVICE.getPatientService();
            patientService.create(patient);
            patientsObservableList.add(patient);

            Patient addedPatient = patientService.getLatestPatient();
            List<Patient> patientListForAppointment = new ArrayList<>();
            patientListForAppointment.add(addedPatient);
            addedAppointment.setPatientList(patientListForAppointment);
            appointmentServiceService.edit(addedAppointment);

            patientsTableView.getSelectionModel().clearSelection();
            clearInputPatient();

        }else {


            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String phone = phoneTextField.getText();
            String email = emailTextField.getText();
            String datePicker = String.valueOf(appointmentDatePicker.getValue());
            String timePicker = String.valueOf(appointmentTimePicker.getTime());

            if (name.isEmpty() || surname.isEmpty() || phone.isEmpty() || email == null ||
                    datePicker.isEmpty() || timePicker.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText(null);
                alert.setContentText("Morate popuniti sva polja!");

                Region dialogPane = (Region) alert.getDialogPane();
                dialogPane.setStyle("-fx-background-color: aqua;"); // Promijenite boju pozadine prozora prema potrebi

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
        return     !nameTextField.getText().isBlank()
                && !surnameTextField.getText().isBlank()
                && !phoneTextField.getText().isBlank()
                && !emailTextField.getText().isBlank()
                && !appointmentDatePicker.toString().isBlank()
                && !appointmentTimePicker.toString().isBlank();
    }


    void clearInputPatient(){
        LocalTime midnight = LocalTime.of(0, 0, 0);

        nameTextField.clear();
        surnameTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        appointmentDatePicker.getEditor().clear();
        appointmentTimePicker.setTime(midnight);
    }

    private void removePatient(ActionEvent actionEvent) {
        Patient selectedPatient = patientsTableView.getSelectionModel().getSelectedItem();
        PatientServiceLocal patientService = PatientServiceFactory.SERVICE.getPatientService();
        AppointmentServiceLocal appointmentService = AppointmentServiceFactory.APPOINTMENT_SERVICE.getAppointmentService();

        if (selectedPatient == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati korisnika iz tablice!");

            Region dialogPane = (Region) alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: aqua;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alert.showAndWait();
            return;
        }
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String datePicker = String.valueOf(appointmentDatePicker.getValue());
        String timePicker = String.valueOf(appointmentTimePicker.getTime());



        if (!selectedPatient.getAppointmentList().isEmpty()) {
            appointmentService.remove(selectedPatient.getAppointmentList().get(0));
        }
        patientService.removeById(selectedPatient.getId());

        patientsObservableList.remove(selectedPatient);
        patientsTableView.getSelectionModel().clearSelection();
        clearInputPatient();



    }


    private void editPatient(ActionEvent actionEvent){
        Patient selectedPatient = patientsTableView.getSelectionModel().getSelectedItem();
        PatientServiceLocal patientService = PatientServiceFactory.SERVICE.getPatientService();

        AppointmentServiceLocal appointmentServiceService = AppointmentServiceFactory.
                APPOINTMENT_SERVICE.getAppointmentService();

        List<TypeOfCheckup> checkupList = new ArrayList<>();
        checkupList.add(typeOfCheckupsChoiceBox.getSelectionModel().getSelectedItem());

        if (selectedPatient == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati pacijenta iz tablice duplim klikom!");

            Region dialogPane = (Region) alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: aqua;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alert.showAndWait();
            return;
        }
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String datePicker = String.valueOf(appointmentDatePicker.getValue());
        String timePicker = String.valueOf(appointmentTimePicker.getTime());



        if (name.isEmpty() || surname.isEmpty() || phone.isEmpty() || email == null ||
                datePicker.isEmpty() || timePicker.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Morate popuniti sva polja!");

            Region dialogPane = (Region) alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: aqua;"); // Promijenite boju pozadine prozora prema potrebi

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);

            dialogPane.setEffect(lighting);

            alert.showAndWait();
            return;
        }

        patientsTableView.refresh();

        Appointment appointmentNew = selectedPatient.getAppointmentList().get(0);
        appointmentNew.setDate(appointmentDatePicker.getValue());
        appointmentNew.setTime(appointmentTimePicker.getTime());

        appointmentServiceService.edit(appointmentNew);

        selectedPatient.setName(name);
        selectedPatient.setSurname(surname);
        selectedPatient.setPhone(Integer.parseInt(phone));
        selectedPatient.setEmail(email);
        selectedPatient.setTypeOfCheckupList(checkupList);
        patientService.edit(selectedPatient);
        patientsTableView.getSelectionModel().clearSelection();
        clearInputPatient();



    }

}
