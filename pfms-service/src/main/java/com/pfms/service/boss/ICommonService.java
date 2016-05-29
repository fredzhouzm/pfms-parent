package com.pfms.service.boss;

import com.pfms.dao.mybatis.model.PfmsUser;

/**
 * Created by Fred on 2015/12/7.
 */
public interface ICommonService {

    //出生日期的检查，主要是检查出生日期是否晚于今天，如果晚于今天，返回false
    public boolean birthdateCheck(String birthdate);

    //用户名称检查，如果用户名称已经存在，返回true
    public boolean checkUserNameIsExist(String userName);

    //向数据库中插入用户信息，用于新用户注册
    public void insertPfmsUser(PfmsUser pfmsUser);

    //通过特定的规则获取用户完整信息，返回用户信息域对象pfmsuser
    public PfmsUser getPfmsUser(Object object, String types);

    public boolean isCorrectPwd(String inputPwd, String dbPwd);

}
