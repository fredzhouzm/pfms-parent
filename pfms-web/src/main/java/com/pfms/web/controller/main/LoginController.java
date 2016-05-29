package com.pfms.web.controller.main;

import com.pfms.dao.mybatis.model.PfmsUser;
import com.pfms.service.boss.ICommonService;
import com.pfms.util.Constants;
import com.pfms.web.domain.Authentication;
import com.pfms.web.domain.LoginUser;
import com.pfms.web.domain.RegisterUser;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Fred on 2015/12/5.
 */
@Scope("singleton")
@Controller
@RequestMapping("/main/")
public class LoginController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ICommonService commonService;

    public LoginController() {}

    @RequestMapping({"/redirectLogin.htm"})
    public ModelAndView loginRedict(HttpServletRequest request) {
        logger.info("有人访问系统，进行重定向操作");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("user", new RegisterUser());
        return mav;
    }

    @RequestMapping({"/login.htm"})
    public ModelAndView loginOp(@Valid @ModelAttribute("user") LoginUser user, BindingResult bindingResult, HttpServletRequest request) {
        logger.info("用户 [" + user.getLoginName() + "] 开始登录系统啦");
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.setViewName("login");
            mav.addObject("user", user);
            return mav;
        }
        if (!commonService.checkUserNameIsExist(user.getLoginName())) {
            bindingResult.rejectValue("loginName", "error.notExist.login");
            mav.setViewName("login");
            mav.addObject("user", user);
            return mav;
        }
        PfmsUser pfmsUser = commonService.getPfmsUser(user.getLoginName(), Constants.BY_LOGIN_NAME);
        Boolean isCorrectPwd = commonService.isCorrectPwd(user.getPassword(), pfmsUser.getPassword());
        if (isCorrectPwd) {
            Authentication authentication = new Authentication();
            authentication.setLoginName(pfmsUser.getLoginName());
            authentication.setNickName(pfmsUser.getName());
            authentication.setPassword(pfmsUser.getPassword());
            authentication.setId(pfmsUser.getId());
            authentication.setValid(false);
            authentication.setMyActions(new HashSet<String>());
            request.getSession(true).setAttribute("session_authentication", authentication);
            logger.info(request.getSession().getId());
            mav.setViewName("welcome");
        } else {
            bindingResult.rejectValue("password", "error.incorrect.password");
            mav.setViewName("login");
            mav.addObject("user", user);
        }
        return mav;
    }

    @RequestMapping({"/register.htm"})
    public ModelAndView registerOp() {
        logger.info("请注意！有人要注册新账号啦！");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        mav.addObject("user", new RegisterUser());
        return mav;
    }

    @RequestMapping({"/registerCheck.htm"})
    public ModelAndView registerChk(@Valid @ModelAttribute("user") RegisterUser user, BindingResult bindingResult, HttpSession httpSession) throws ParseException {
        logger.info("开始进行用户注册");
        logger.info("用户：" + user.toString());
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.setViewName("register");
            mav.addObject("user", user);
            return mav;
        }
        if (commonService.checkUserNameIsExist(user.getLoginName())) {
            bindingResult.rejectValue("loginName", "error.exist.loginName");
            mav.setViewName("register");
            mav.addObject("user", user);
            return mav;
        }
        if (commonService.birthdateCheck(user.getBirthdate())) {
            bindingResult.rejectValue("birthdate", "error.past.birthdate");
            mav.setViewName("register");
            mav.addObject("user", user);
            return mav;
        } else {
            PfmsUser pfmsUser = new PfmsUser();
            pfmsUser.setLoginName(user.getLoginName());
            pfmsUser.setPassword(user.getPassword());
            pfmsUser.setName(user.getNickName());
            pfmsUser.setGender(user.getGender());
            pfmsUser.setBirthDate(user.getBirthdateDB());
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                pfmsUser.setMailAddress(user.getEmail());
            }
            pfmsUser.setTimestamp(new Date(System.currentTimeMillis()));
            //设置注册用户为首次登录
            pfmsUser.setFirstLogin("1");
            //设置注册用户的用户种类为“1”普通用户
            pfmsUser.setUserType("1");
            //初始化新注册用户的科目数为0
            pfmsUser.setUsageNum(0);
            commonService.insertPfmsUser(pfmsUser);
            pfmsUser = commonService.getPfmsUser(pfmsUser.getLoginName(), Constants.BY_LOGIN_NAME);
            httpSession.setAttribute("session_user", pfmsUser);
            mav.setViewName("welcome");
            return mav;
        }

    }
}
