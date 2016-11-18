package ru.test.service;

import ru.test.model.User;

import java.util.List;

/**
 * Created by rrv on 15.11.16.
 */

public interface UserService
{
    User findUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(int id);
    List findAllUser();
    User findUserByLogin(String login);

    public String getMD5PassById(int id);
}

