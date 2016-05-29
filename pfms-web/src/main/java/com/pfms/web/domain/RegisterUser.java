package com.pfms.web.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fred on 2015/12/5.
 */
public class RegisterUser {

    @NotEmpty(message = "{error.required.loginName}")
    private String loginName;

    @NotEmpty(message = "{error.required.password}")
    @Pattern(regexp = "[a-zA-Z0-9_@!]{8,32}", message = "{error.pattern.password}")
    private String password;

    @NotEmpty(message = "{error.required.nickName}")
    private String nickName;

    @NotEmpty(message = "{error.required.gender}")
    @Pattern(regexp = "1|2", message = "{error.pattern.gender}")
    private String gender;

    @NotEmpty(message = "{error.required.birthdate}")
    private String birthdate;

    @Email(message = "{error.pattern.email}")
    private String email;

    public RegisterUser() {
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public Date getBirthdateDB() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdateDB = null;
        try {
            birthdateDB = sdf.parse(this.getBirthdate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthdateDB;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "RegisterUser{loginName=\'}" + this.loginName + "\',password=\'" + this.password + "\',nickName=\'" + this.nickName + "\',gender=\'" + this.gender + "\',birthdate=\'" + this.birthdate + "\',email=\'" + this.email + "\'}";
    }
}
