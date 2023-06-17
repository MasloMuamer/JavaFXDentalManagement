package com.example.dentalmanagementd2.business.service;

import com.example.dentalmanagementd2.business.model.User;

import java.util.List;

public interface UserServiceLocal {

    User login(String username, String password);

    List<User> findAll();

    List<User> findAllByPrivileges(String privilegeId);

    void create(User user);

    void edit(User user);
    void remove(User user);

    void removeById(Integer id);

    User  find(Integer id);
}
