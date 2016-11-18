package ru.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.test.config.AppConfig;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.test.model.User;
import ru.test.service.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by rrv on 16.11.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class JUnitSpring_TestUserService
{
    @Autowired
    private UserService service;

    @Test
    @Ignore
    public void t1estz() {
        System.out.println("service.findAllUser().size()="+service.findAllUser());

        User user=new User();
        user.setLogin("bigcat");
        user.setName("Иванов Петр Семенович");
        service.saveUser(user);

        System.out.println("User#1 = "+user);
        assertEquals(1, user.getId());

        user=new User();
        user.setLogin("monstro");
        user.setName("Финачева Клавдия Михайловна");
        service.saveUser(user);

        System.out.println("User#1 = "+user);
        assertEquals(2, user.getId());
    }

    @Test
    public void chech_test_user() {
        User user=service.findUserByLogin("bigcat");
        System.out.println("Find_user_id = "+user.getId());
        assertEquals(1, user.getId());
    }

    @Test
    @Transactional
    public void testUser() throws Exception {
        System.out.println("service.findAllUser().size()="+service.findAllUser());
        //
        List<User> ll=service.findAllUser();
        for(User u: ll) service.deleteUserById(u.getId());
        ll=service.findAllUser();

        System.out.println("List.size() = "+ll.size());
        assertEquals(0, ll.size());
        //
        User user=new User();
        user.setLogin("login#3");
        user.setName("Самойленко Федов Павлович");
        service.saveUser(user);

        System.out.println("User#3.id = "+user.getId());
        assert(user.getId()>0);
        //
        ll=service.findAllUser();
        for(User u: ll) System.out.println(String.format("%s  >>    pass_hash = %s",u,service.getMD5PassById(u.getId())));
    }
}
