package ru.test.dao.Impl;

import org.hibernate.Criteria;
import org.hibernate.Query;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ru.test.dao.AbstractDAO;
import ru.test.dao.UserDAO;
import ru.test.model.User;

import java.util.List;

/**
 * Created by rrv on 15.11.16.
 */

@Repository("userDao")
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO
{

    public User findUserById(int id) {
        return getByKey(id);
    }

    public void saveUser(User user) {
        persist(user);
    }

    public void deleteUserById(int id) {
        Query query = getSession().createSQLQuery("delete from user_tab where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    public List findAllUser() {
        Criteria criteria = createEntityCriteria();
        return (List) criteria.list();
    }

    public User findUserByLogin(String login) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("login", login!=null?login.toUpperCase():null));
        return (User) criteria.uniqueResult();
    }
}
