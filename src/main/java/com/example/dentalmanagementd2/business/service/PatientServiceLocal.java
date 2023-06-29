package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.business.model.Patient;
import com.example.dentalmanagementd2.business.model.User;

import java.util.List;

public interface PatientServiceLocal {

    List<Patient> findAll();

    List<Patient> findAllByApointment(String appointmentId);

    List<Patient> findAllByTypeOfCheckup(String typeOfCheckupId);

    void create(Patient patient);

    void remove(Patient patient);

    void edit(Patient patient);

    Patient getLatestPatient();

    void removeById(Integer id);
}