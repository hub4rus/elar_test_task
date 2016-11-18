package ru.test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.test.config.roles.UserRoleEnum;
import ru.test.model.User;
import ru.test.service.LastLogin;
import ru.test.service.UserService;

import java.util.logging.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rrv on 16.11.16.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static Logger log = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private LastLogin lastLogin;

    public UserDetails loadUserByUsername(String sLogin) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        User user=userService.findUserByLogin(sLogin);
        lastLogin.setLastUseLogin(sLogin);

        log.info("sLogin="+sLogin);
        //log.info("service.findAllUser().size()="+userService.findAllUser());
        log.info("User="+user);
        {
            Set<GrantedAuthority> roles=new HashSet();
            roles.add(new SimpleGrantedAuthority((user!=null)?UserRoleEnum.USER_ROLE.name():UserRoleEnum.NONE_ROLE.name()));

            log.info("roles="+roles);
            log.info("pass="+userService.getMD5PassById(user.getId()));

            userDetails =
                    new org.springframework.security.core.userdetails.User(
                            sLogin,
                            userService.getMD5PassById(user.getId()),
                            roles);
            log.info("userDetails="+userDetails);
        }
        return userDetails;
    }
}
