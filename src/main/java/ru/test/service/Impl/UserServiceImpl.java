package ru.test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.dao.UserDAO;
import ru.test.model.User;
import ru.test.service.PassService;
import ru.test.service.UserService;
import ru.test.config.roles.UserRoleEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rrv on 15.11.16.
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDAO dao;

    @Autowired
    private PassService pass_serv;

    public User findUserById(int id) {
        return dao.findUserById(id);
    }

    public void saveUser(User user) {
        dao.saveUser(user);
    }

    public void updateUser(User user) {
        User entity = dao.findUserById(user.getId());
        if(entity!=null){
            entity.setLogin(user.getLogin());
            entity.setName(user.getName());
        }
    }

    public void deleteUserById(int id) {
        dao.deleteUserById(id);
    }

    public List findAllUser() {
        return dao.findAllUser();
    }

    public User findUserByLogin(String login) {
        return dao.findUserByLogin(login);
    }

    public String getMD5PassById(int id) {
        return (pass_serv!=null && pass_serv.getMap()!=null)?pass_serv.getMap().get(id):null;
    }
}
