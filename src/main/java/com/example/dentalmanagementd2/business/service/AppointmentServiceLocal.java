package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;

import java.util.List;

public interface AppointmentServiceLocal {

    List<Appointment> findAll();

    void create(Appointment appointment);

    void remove(Appointment appointment);

    Appointment getLatestAppointment();

    void edit(Appointment appointment);
}