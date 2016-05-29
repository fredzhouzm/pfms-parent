package com.pfms.web.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Fred on 16/5/22.
 */
public class Authentication implements Serializable {

    private static final long serialVersionID = 0L;
    //用户信息
    private String loginName;
    private String nickName;
    private String password;
    private int id;
    //权限信息
    private Set<String> myActions;
    //是否过期
    private boolean isValid = true;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getMyActions() {
        return myActions;
    }

    public void setMyActions(Set<String> myActions) {
        this.myActions = myActions;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
