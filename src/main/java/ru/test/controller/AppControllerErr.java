package ru.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.test.model.User;
import ru.test.service.LastLogin;
import ru.test.service.UserService;

import java.util.logging.Logger;

/**
 * Created by rrv on 17.11.16.
 */

@Controller
@ComponentScan("ru.reut")
public class AppControllerErr
{
    private static Logger log = Logger.getLogger(AppControllerErr.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private LastLogin lastLogin;

    @RequestMapping(value="/error",method = RequestMethod.GET)
    public String errorPage(Model model) {
        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //log.info("auth2 = "+auth);
        log.info("authName = "+auth.getName());

        if (auth instanceof AnonymousAuthenticationToken) {

            log.info("getLastUseLogin="+lastLogin.getLastUseLogin());

            User user=userService.findUserByLogin(lastLogin.getLastUseLogin());
            if (user!=null) {
                model.addAttribute("message", "Incorrect password [login='"+lastLogin.getLastUseLogin()+"']");
                return "err";
            } else {
                model.addAttribute("message", "Not exists user [login='"+lastLogin.getLastUseLogin()+"']");
                return "err";
            }
        } else {
            return "redirect:" + "/";
        }
    }
}
