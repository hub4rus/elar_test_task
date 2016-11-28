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
import org.springframework.web.bind.annotation.RequestParam;
import ru.test.model.User;
import ru.test.service.UserService;

import java.util.List;
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

    @RequestMapping(value="/list",method = RequestMethod.GET)
    //@Secured({"ROLE_USER"}) //see SecurityConfig
    public String listPage(Model model) {
        // Получаем список пользователей
        List<User> allUser = userService.findAllUser();
        model.addAttribute("users",allUser);
        return "list";
    }

    // /add?edit=${user.id}
    @RequestMapping(value="/add",method = RequestMethod.GET)
    //@Secured({"ROLE_USER"}) //see SecurityConfig
    public String editGetPage(Model model,@RequestParam(value="edit", required=false, defaultValue="-1") int user_id) {
        // Получаем список пользователей
        if (user_id>2) { //нельзя править статических пользователей
            User user = userService.findUserById(user_id);
            model.addAttribute("user", user);
        }
        return "edit";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    //@Secured({"ROLE_USER"}) //see SecurityConfig
    public String editPostPage(Model model,
                               @RequestParam(value="id", required=false, defaultValue="-1") int user_id,
                               @RequestParam(value="login", required=true) String user_login,
                               @RequestParam(value="name", required=true)  String user_name)
    {
        log.info("edit: id="+user_id);
        log.info("edit: login="+user_login);
        log.info("edit: name="+user_name);

        // Получаем список пользователей
        if (user_id<0) {
            //add
            User user=new User();
            user.setLogin(user_login);
            user.setName(user_name);
            userService.saveUser(user);
        } else
        if (user_id>2) { //нельзя править статических пользователей
            //edit
            User user = userService.findUserById(user_id);
            user.setLogin(user_login);
            user.setName(user_name);
            userService.updateUser(user);
        }
        return "redirect:/list";
    }

    // /delete?id=${user.id}
    @RequestMapping(value="/delete",method = RequestMethod.GET)
    //@Secured({"ROLE_USER"}) //see SecurityConfig
    public String deletePage(Model model,@RequestParam(value="id", required=true) int user_id) {
        // нельзя удалять статических пользователей, а то не зайдем в систему
        if (user_id>2)
            userService.deleteUserById(user_id);
        return "redirect:/list";
    }

//    @RequestMapping(value="/j_spring_security_check", method = RequestMethod.POST)
//    public void getLogin(@PathVariable("j_username") String sLogin) {
//        log.info("sCheckLogin="+sLogin);
//    }
}
