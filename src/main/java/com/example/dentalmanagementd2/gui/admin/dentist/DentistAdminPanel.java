package com.example.dentalmanagementd2.gui.admin.dentist;

import com.dlsc.gemsfx.TimePicker;
import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.business.model.Patient;
import com.example.dentalmanagementd2.business.model.TypeOfCheckup;
import com.example.dentalmanagementd2.business.model.User;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class DentistAdminPanel extends VBox {
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

    public DentistAdminPanel(){
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
    }

    private HBox getForm(){
        HBox form = new HBox();
        form.setSpacing(5);
        form.setAlignment(Pos.CENTER);

        List<TypeOfCheckup> typeOfCheckups = TypeOfCheckupServiceFactory.TYPE_OF_CHECKUP_SERVICE.getTypeOfCheckupsService().findAll();
        typeOfCheckupsChoiceBox.setItems(FXCollections.observableList(typeOfCheckups));
        typeOfCheckupsChoiceBox.getSelectionModel().select(0);

        nameTextField.setPromptText("Ime:");
        surnameTextField.setPromptText("Prezime:");
        phoneTextField.setPromptText("Broj telefona:");
        emailTextField.setPromptText("Email:");
        appointmentDatePicker.converterProperty();
        appointmentTimePicker.clockTypeProperty();



        form.getChildren().addAll(nameTextField,surnameTextField, phoneTextField, emailTextField,
                appointmentDatePicker, appointmentTimePicker, typeOfCheckupsChoiceBox);
        return form;

    }

    private HBox getNewForm(){
        HBox newForm = new HBox();
        newForm.setSpacing(5);
        newForm.setAlignment(Pos.CENTER);

        addPatientButton.setOnAction(this::addPatient);
        deletePatientButton.setOnAction(this::removePatient);

        newForm.getChildren().addAll(addPatientButton, deletePatientButton);
        return newForm;
    }

    private Appointment getAppointment(Appointment appointment) {
        return appointment;
    }

    private void addPatient(ActionEvent event){
        Patient patient = new Patient();
        List<Patient> patientsList = new ArrayList<>();
        patientsList.add(patient);

        List<TypeOfCheckup> checkupList = new ArrayList<>();
        checkupList.add(typeOfCheckupsChoiceBox.getSelectionModel().getSelectedItem());

        List<Appointment> appointmentsList = new ArrayList<>();
        Appointment appointmentNew = new Appointment();
        appointmentNew.setDate(appointmentDatePicker.getValue());
        appointmentNew.setTime(appointmentTimePicker.getTime());

        AppointmentServiceLocal appointmentServiceService = AppointmentServiceFactory.APPOINTMENT_SERVICE.getAppointmentService();
        appointmentServiceService.create(appointmentNew);


        Appointment addedAppointment = appointmentServiceService.getLatestAppointment();

        System.out.println(addedAppointment);
        appointmentsList.add(addedAppointment);

        if (validate()) {
            patient.setName(nameTextField.getText());
            patient.setSurname(surnameTextField.getText());
            patient.setPhone(Integer.parseInt(phoneTextField.getText()));
            patient.setEmail(emailTextField.getText());
            patient.setAppointmentList(appointmentsList);
            patient.setTypeOfCheckupList(checkupList);
            PatientServiceLocal patientService = PatientServiceFactory.SERVICE.getPatientService();
            patientService.create(patient);
            patientsObservableList.add(patient);
            clearInput();
        }else {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String phone = phoneTextField.getText();
            String email = emailTextField.getText();
            String appointmentDatePicker = appointmentObservableList.toString();
            String appointmentTimePicker = appointmentObservableList.toString();

            System.out.println(phone);


            //PRIKAZATI SMISLENU PORUKU ZA SVAKO POLJE AKO SE OSTAVI PRAZNO
            //usernameTextField.setPromptText("Morate popuniti polje:");

            if (name.isEmpty() || surname == null || phone.isEmpty() || email.isEmpty() ||
                    appointmentDatePicker.isEmpty() || appointmentTimePicker.isEmpty()) {
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
        return     !nameTextField.getText().isBlank()
                && !surnameTextField.getText().isBlank()
                && !phoneTextField.getText().isBlank()
                && !emailTextField.getText().isBlank()
                && !appointmentDatePicker.toString().isBlank()
                && !appointmentTimePicker.toString().isBlank();
    }


    private void clearInput(){
        nameTextField.clear();
        surnameTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        //appointmentDatePicker.toString().clear();
        //appointmentTimePicker.toString().clear();
    }

    private void removePatient(ActionEvent actionEvent) {
        Patient selectedPatient = patientsTableView.getSelectionModel().getSelectedItem();
        PatientServiceLocal patientService = PatientServiceFactory.SERVICE.getPatientService();

        patientService.removeById(selectedPatient.getId());
        patientsObservableList.remove(selectedPatient);

        if (selectedPatient == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(null);
            alert.setContentText("Morate selektirati korisnika iz tablice duplim klikom!");
            alert.showAndWait();
            return;
        }


        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
     //   String appointment = appointmentTextField.getId();
        String patient = typeOfCheckupsChoiceBox.getId();

    }


   // private void addPatient(ActionEvent event){
        //pogresna relacija
        /*
        Fali tabela poveznica između dvije tabele:
        Patients
        Apointemnets
         */
     //   Patient patient = new Patient();
//        Appointments appointments = new Appointments();
//        appointments.setDateTimeOfCheckup(new Date());
//        appointments.setIdTypeOfCheckup(typeOfCheckupsChoiceBox.getValue());

  //      patient.setName(nameTextField.getText());
  //      patient.setSurname(surnameTextField.getText());
  //      patient.setPhone(Integer.parseInt(phoneTextField.getText()));
  //      patient.setEmail(emailTextField.getText());
        //patient.setAppointmentList(appointmentTextField.getText());
        //appointments.setIdTypeOfCheckup(typeOfCheckupsChoiceBox.getValue());
   //     PatientServiceLocal patientService = (PatientServiceLocal) PatientServiceFactory.SERVICE.getPatientService();
   //     patientService.create(patient);
   //     patientsObservableList.add(patient);
   //     clearInput();
   // }

   // private void clearInput(){
   //     nameTextField.clear();
   //     surnameTextField.clear();
   //     phoneTextField.clear();
   //     emailTextField.clear();
   //     appointmentTextField.clear();

    }


