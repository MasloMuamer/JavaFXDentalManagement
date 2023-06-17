package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.User;
import com.example.dentalmanagementd2.commons.Constans;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;

class UserService extends AbstractService<User, Integer> implements UserServiceLocal {
    public UserService() {
        super(User.class);
    }

    @Override
    public User login(String username, String password) {
        // Generisanje heš vrijednosti lozinke
        String hashedPassword = BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt());

        if (username == null || username.isEmpty()) {
            return null;
        }
        if (password == null || password.isEmpty()) {
            return null;
        }

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        try {
            User user = (User) query.getSingleResult();
            if (BCrypt.checkpw(password, hashedPassword)) {
                return user;
            }
        }catch (NoResultException e){
            System.err.println("Not exist user with username: " + username);
            return null;
        }
        return null;


    }

    public List<User> findAllByPrivileges(String privilegeId) {
        return getEntityManager().createQuery(
                        "SELECT c FROM User c WHERE c.idPrivilege = :privilegeId", User.class)
                .setParameter("privilegeId", privilegeId)
                .getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constans.PU_NAME);
        return entityManagerFactory.createEntityManager();
    }
}

