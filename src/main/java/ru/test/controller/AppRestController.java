package ru.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.test.model.Greeting;
import ru.test.model.ListUsers;
import ru.test.model.User;
import ru.test.service.UserService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Created by rrv on 20.11.16.
 */

@RestController
@RequestMapping("/")
@ComponentScan("ru.reut")
public class AppRestController
{
    private static Logger log = Logger.getLogger(AppRestController.class.getName());

    @Autowired
    private UserService userService;


    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //"/info?id=bigcat"
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    //@Secured({"CUSTOM_ROLE"})  //@Secured annotation works only with role names starting ROLE_ by default.
    @Secured({"CUSTOM_ROLE","ROLE_USER"})
    //public @ResponseBody User infoPage1(@RequestParam("id") String login)
    public ResponseEntity<?> infoPage1(@RequestParam(value="id"/*, required=false, defaultValue="bigcat"*/) String login)
    {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken))
//        {
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        }

        log.info("infoPage1_login="+login);

        if (login==null || login.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            //return new ResponseEntity<String>(json,HttpStatus.OK);
        } else
            return new ResponseEntity<>(userService.findUserByLogin(login), HttpStatus.OK);

        //return userService.findUserByLogin(login);
    }

    //"/info/bigcat"
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('CUSTOM_ROLE')") //(with pre-post-annotations="enabled")
    @PreAuthorize("hasAuthority('CUSTOM_ROLE') or hasAuthority('ROLE_USER')") //(with pre-post-annotations="enabled")
    //@Secured("CUSTOM_ROLE")
    public @ResponseBody Greeting infoPage(@PathVariable("id") String login)
    {
        log.info("infoPage2_login="+login);

        return new Greeting(counter.incrementAndGet(),String.format(template, login));
    }

    //localhost:8080/elar_test_task-1.0/info/add
    //localhost:8080/elar_test_task-1.0/info/add?id=z_login&value=zukka
    @Secured({"CUSTOM_ROLE","ROLE_USER"})
    @RequestMapping(value = "/info/add", method = RequestMethod.GET)
    public @ResponseBody
    User
    add(@RequestParam(value="id", required=false, defaultValue="t_log") String q_login,
        @RequestParam(value="value", required=false, defaultValue="t_fio") String q_fio)
    {
        User user=new User();
        user.setLogin(q_login);
        user.setName(q_fio);
        userService.saveUser(user);

        log.info("infoPage_add="+user.getId()+"{"+q_login+";"+q_fio+"}");
        return user;
    }

    //localhost:8080/elar_test_task-1.0/info/all
    //@JsonView(Profile.FriendsView.class)
    @Secured({"CUSTOM_ROLE","ROLE_USER"})
    //@RequestMapping(value = "/info/all", method = RequestMethod.GET, produces = "application/json")
    @RequestMapping(value = "/info/all", method = RequestMethod.GET)
    public @ResponseBody
    //List<User> all()
    ListUsers all()
    {
        List<User> list=userService.findAllUser();

        log.info("infoPage_all="+list.size());
        return new ListUsers(list);
    }

//    @Secured({"CUSTOM_ROLE","ROLE_USER"})
//    @RequestMapping(value = "/info/all2", method = RequestMethod.GET)
//    public ResponseEntity<List<User>> all2()
//    {
//        List<User> list=userService.findAllUser();
//
//        log.info("infoPage_all="+list.size());
//        return (list == null || list.isEmpty()) ? new ResponseEntity<List<User>>(list, HttpStatus.NO_CONTENT)
//                : new ResponseEntity<List<User>>(list, HttpStatus.OK);
//    }
}