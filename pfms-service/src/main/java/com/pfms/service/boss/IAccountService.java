package com.pfms.service.boss;

import com.pfms.dao.mybatis.model.FormVm;
import com.pfms.dao.mybatis.model.PfmsForm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fred on 2016/01/31.
 */
public interface IAccountService {

    //根据收入/支出类型和用户ID获取对应的一级科目列表
    public HashMap getProOne(String type, int userId);

    //根据一级科目ID获取对应的二级科目列表
    public HashMap getProTwoByProOne(String proOneId);

    //插入一条单据
    public void insertForm(PfmsForm pfmsForm);

    //根据月份和用户ID获取当月用户所产生的所有单据记录
    public List<PfmsForm> getFormListByMonth(String month, int userId);

    //根据单据ID获取该单据记录
    public PfmsForm getFormById(String formId);

    //更新一条单据
    public void updateForm(PfmsForm pfmsForm);

    //删除一条单据
    public void deleteForm(PfmsForm pfmsForm);

    //获取指定月份的相应金额总和 type-1为收入,type-2为支出
    public BigDecimal getTotalAmountByMonth(int year, int month, String type, int creatorId);

    //获取指定月份记录的收支单据流水
    public List<FormVm> getFormVmByMonth(int year, int month, int creatorId);

    public List<FormVm> getFormVmListByCondition(String type,Date beginDate,Date endDate,String proOneId,String proTwoId,String id);

}
