package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Privilege;
import com.example.dentalmanagementd2.commons.Constans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class PrivilegeService extends AbstractService<Privilege, Integer> implements PrivilegeServiceLocal {
    public PrivilegeService() {
        super(Privilege.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constans.PU_NAME);
        return entityManagerFactory.createEntityManager();
    }
}
