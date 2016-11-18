package ru.test.service.Impl;

import org.springframework.stereotype.Component;
import ru.test.service.LastLogin;

/**
 * Created by rrv on 17.11.16.
 */

@Component("LastLogin")
public class LastLoginImpl implements LastLogin
{
    private String lastUseLogin;

    public void setLastUseLogin(String lastUseLogin) {
        this.lastUseLogin = lastUseLogin;
    }

    public String getLastUseLogin() {
        return lastUseLogin;
    }
}
