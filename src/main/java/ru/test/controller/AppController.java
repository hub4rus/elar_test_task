package ru.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.test.service.UserService;
import java.util.logging.Logger;

/**
 * Created by rrv on 16.11.16.
 */

@Controller
@RequestMapping("/")
@ComponentScan("ru.reut")
@EnableTransactionManagement // @EnableTransactionManagement активирует возможности Spring бесшовной транзакции через @Transactional
public class AppController
{
    private static Logger log = Logger.getLogger(AppController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String make(Model model) {
        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            //return prepOk(model,auth);
        return "redirect:/ok";
        else
            return "index";
    }

    @Transactional
    private String prepOk(Model model,Authentication auth) {
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addAttribute("message",
                "Successful authorized user [login='" +
                userDetail.getUsername() +
                "', name='"+
                userService.findUserByLogin(userDetail.getUsername()).getName()+
                "']");
        return "ok";
    }

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String loginPage(Model model){
        return "login";
    }

    @RequestMapping(value="/ok",method = RequestMethod.GET)
    //@Secured({"ROLE_USER"}) //see SecurityConfig
    public String okPage(Model model) {
        return prepOk(model,SecurityContextHolder.getContext().getAuthentication());
    }

//    @RequestMapping(value="/j_spring_security_check", method = RequestMethod.POST)
//    public void getLogin(@PathVariable("j_username") String sLogin) {
//        log.info("sCheckLogin="+sLogin);
//    }
}
