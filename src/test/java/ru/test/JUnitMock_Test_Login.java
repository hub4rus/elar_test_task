package ru.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.test.config.AppConfig;
import ru.test.config.HibernateConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rrv on 16.11.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, HibernateConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class JUnitMock_Test_Login
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void st() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysDo(print())
                .dispatchOptions(true).build();
    }

    @Test
    public void indexSuccess() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/index.jsp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginSuccess() throws Exception {
        MockHttpServletRequestBuilder loginRequest = MockMvcRequestBuilders.get("/login");
//                .param("username","bigcat")
//                .param("password", "black");
        mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/login.jsp"));
    }

    @Test
    public void loginErr() throws Exception {
        MockHttpServletRequestBuilder loginRequest = MockMvcRequestBuilders.get("/error");
        mockMvc.perform(loginRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/err.jsp"));
    }
}
