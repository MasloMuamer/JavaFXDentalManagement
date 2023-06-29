package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.business.model.Patient;
import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.commons.Constans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PatientService extends AbstractService<Patient, Integer> implements PatientServiceLocal{
    PatientService() {
        super(Patient.class);
    }

    //public List<Patient> findAllByAppointment(String appointmentId) {
    //    return getEntityManager().createQuery(
    //                    "SELECT c FROM Patient c WHERE c.appointmentList = :appointmentId", Patient.class)
    //            .setParameter("appointmentId", appointmentId).getResultList();
   // }


    @Override
    public List<Patient> findAllByApointment(String appointmentId) {
        return getEntityManager().createQuery(
                        "SELECT c FROM Patient c WHERE c.appointmentList = :appointmentId", Patient.class)
                .setParameter("appointmentId", appointmentId).getResultList();
    }


    public List<Patient> findAllByTypeOfCheckup(String typeOfCheckupId) {
        return getEntityManager().createQuery(
           "SELECT  c FROM Patient c WHERE c.typeOfCheckupList = :typeOfCheckupId", Patient.class)
                .setParameter("typeOfCheckupId", typeOfCheckupId).getResultList();
    }

//    public List<Patient> findAllByDentistId(int dentistId) {
//        return getEntityManager().createQuery(
//                        "SELECT c FROM Appointment c WHERE c.id_patient = :dentistId", Patient.class)
//                .setParameter("userId", userId)
//                .getResultList();
//    }

    public Patient getLatestPatient() {
        return getEntityManager().createQuery(
                        "SELECT c FROM Patient c ORDER BY c.id desc", Patient.class)
                .getResultList().get(0);
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constans.PU_NAME);
        return entityManagerFactory.createEntityManager();
    }





}
