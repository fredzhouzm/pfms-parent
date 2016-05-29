package com.pfms.web.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Fred on 2015/12/21.
 */
public class LoginUser {

    @NotEmpty(message = "{error.required.loginName}")
    private String loginName;

    @NotEmpty(message = "{error.required.password}")
    @Pattern(regexp = "[a-zA-Z0-9_@!]{8,32}", message = "{error.pattern.password}")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
