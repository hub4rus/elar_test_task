package ru.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.test.service.Impl.UserDetailsServiceImpl;

/**
 * Created by rrv on 16.11.16.
 */

@Configuration
@ComponentScan("ru.test")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true) //for @Secured >> xml = <security:global-method-security secured-annotations="enabled" />
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsServiceImpl service_detail_user;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service_detail_user).passwordEncoder(getMd5PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        //Межсайтовой подделки запроса (CSRF).
        // отключена защита csrf на время тестов
        http.csrf().disable().addFilterBefore(filter,CsrfFilter.class);

//        http.authorizeRequests()
//                //.antMatchers("/").permitAll()
//                .antMatchers("/resources/**").permitAll()
//                //.antMatchers("/ok").access("hasRole('ROLE_USER')")
//                //.antMatchers("/ok").hasRole("ROLE_USER")
//                //
//                //.antMatchers("/**").authenticated()
//                .anyRequest().authenticated() //all requests will checked
//                .antMatchers("/ok").hasRole("ROLE_USER");
//                //.antMatchers("/info").hasRole("ROLE_USER");

        http.authorizeRequests()
                //.antMatchers("/").permitAll()
                .antMatchers("/j_spring_security_check").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/resources/**").permitAll()
                //.antMatchers("/ok").hasRole("ROLE_USER") //can't start ROLE_
                .antMatchers("/ok").access("hasRole('ROLE_USER')")
                .anyRequest().authenticated()  //all requests will checked
                .and().rememberMe();


        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                // указываем action с формы логина
                .loginProcessingUrl("/j_spring_security_check")
                // указываем URL при неудачном логине
                .failureUrl("/error")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/logout")
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                // делаем не валидной текущую сессию
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");;

//                .deleteCookies("JSESSIONID,SPRING_SECURITY_REMEMBER_ME_COOKIE").permitAll()
//                .and()
//                .csrf();
    }

    @Bean
    public Md5PasswordEncoder getMd5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }
}