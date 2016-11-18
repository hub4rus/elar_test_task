//package ru.test.service.Impl;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
//import java.util.logging.Logger;
//
///**
// * Created by rrv on 17.11.16.
// */
//public class FailAuthEvent implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
//
//    private static Logger log = Logger.getLogger(FailAuthEvent.class.getName());
//
//    @Override
//    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
//        Object userName=event.getAuthentication().getPrincipal();
//        Object userPass=event.getAuthentication().getCredentials();
//
//        log.info("BAD userName="+userName);
//    }
//}
