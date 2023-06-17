package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.TypeOfCheckup;
import com.example.dentalmanagementd2.commons.Constans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TypeOfCheckupService extends AbstractService<TypeOfCheckup, Integer> implements TypeOfCheckupServiceLocal {
    public TypeOfCheckupService(){
        super(TypeOfCheckup.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constans.PU_NAME);
        return entityManagerFactory.createEntityManager();
    }
}
