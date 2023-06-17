package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;
import com.example.dentalmanagementd2.commons.Constans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppointmentService extends AbstractService<Appointment, Integer> implements AppointmentServiceLocal{
    AppointmentService(){
        super(Appointment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constans.PU_NAME);
        return entityManagerFactory.createEntityManager();    }

    public Appointment getLatestAppointment() {
        return getEntityManager().createQuery(
                        "SELECT c FROM Appointment c ORDER BY c.id desc", Appointment.class)
                .getResultList().get(0);
    }

}

