package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.business.model.TypeOfCheckup;

import java.util.List;

public interface TypeOfCheckupServiceLocal {

    List<TypeOfCheckup> findAll();

    void create(TypeOfCheckup typeOfCheckup);

    void remove(TypeOfCheckup typeOfCheckup);

    void edit(TypeOfCheckup typeOfCheckup);
}
