package ru.test.dao;

import ru.test.model.User;

import java.util.List;

/**
 * Created by rrv on 15.11.16.
 */
public interface UserDAO
{
    User findUserById(int id);
    void saveUser(User user);
    void deleteUserById(int id);
    List findAllUser();
    User findUserByLogin(String login);
}
