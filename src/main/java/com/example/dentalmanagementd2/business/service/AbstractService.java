package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.Appointment;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractService<E, P> {

    private Class<E> entityClass;

    public AbstractService(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(E entity) {
        EntityManager entityManager =  getEntityManager();
        entityManager.getTransaction().begin();

        if (!entityManager.contains(entity)){
            entityManager.persist(entity);
            entityManager.flush();
        }
        entityManager.getTransaction().commit();
    }

    public void edit(E entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.flush();
        entityManager.getTransaction().commit();    }

    public void remove(E entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        if (!entityManager.contains(entity)){
            entity = entityManager.merge(entity);
        }
        entityManager.remove(entity);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void removeById(P primaryKey){
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        E entity = entityManager.find(entityClass, primaryKey);
        entityManager.remove(entity);
        System.out.println("Test");
        entityManager.flush();
        entityManager.getTransaction().commit();

    }



    public E find(P id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<E> findAll() {
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
}

