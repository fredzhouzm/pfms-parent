package com.pfms.service.worker;

import com.pfms.dao.mybatis.dao.*;
import com.pfms.dao.mybatis.model.*;
import com.pfms.service.boss.IAccountService;
import com.pfms.service.boss.ISequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fred on 2016/01/31.
 */
@Scope("singleton")
@Service
public class AccountServiceImp implements IAccountService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PfmsFormMapper pfmsFormMapper;

    @Autowired
    PfmsUsageOneMapper pfmsUsageOneMapper;

    @Autowired
    PfmsUsageTwoMapper pfmsUsageTwoMapper;

    @Autowired
    CustomMapper customMapper;

    @Autowired
    FormVmMapper formVmMapper;

    @Autowired
    ISequenceService sequenceService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HashMap getProOne(String type, int userId) {
        logger.info("开始获取用户ID为[" + userId + "],TYPE为[" + type + "]的一级科目分类!");

        LinkedHashMap<String, String> proOneTmp = new LinkedHashMap<String, String>();
        PfmsUsageOneExample pfmsUsageOneExample = new PfmsUsageOneExample();

        pfmsUsageOneExample.createCriteria().andTypeEqualTo(type).andCreatorIdEqualTo(userId);
        List<PfmsUsageOne> pfmsUsageOneList = pfmsUsageOneMapper.selectByExample(pfmsUsageOneExample);
        if (pfmsUsageOneList.size() > 0) {
            for (PfmsUsageOne pfmsUsageOne : pfmsUsageOneList) {
                proOneTmp.put(pfmsUsageOne.getId(), pfmsUsageOne.getName());
            }
        }
        return proOneTmp;
    }

    public HashMap getProTwoByProOne(String proOneId) {
        logger.info("开始获取父ID为[" + proOneId + "]的二级科目分类");

        LinkedHashMap<String, String> proTwoTmp = new LinkedHashMap<String, String>();
        PfmsUsageTwoExample pfmsUsageTwoExample = new PfmsUsageTwoExample();

        pfmsUsageTwoExample.createCriteria().andFatherIdEqualTo(proOneId);
        List<PfmsUsageTwo> pfmsUsageTwoList = pfmsUsageTwoMapper.selectByExample(pfmsUsageTwoExample);
        if (pfmsUsageTwoList.size() > 0) {
            for (PfmsUsageTwo pfmsUsageTwo : pfmsUsageTwoList) {
                proTwoTmp.put(pfmsUsageTwo.getId(), pfmsUsageTwo.getName());
            }
        }
        return proTwoTmp;
    }

    public void insertForm(PfmsForm pfmsForm) {
        logger.info("开始向数据库中插入新的单据记录");

        pfmsFormMapper.insert(pfmsForm);
    }

    public List<PfmsForm> getFormListByMonth(String month, int userId) {
        return null;
    }

    public PfmsForm getFormById(String formId) {
        return null;
    }

    public void updateForm(PfmsForm pfmsForm) {

    }

    public void deleteForm(PfmsForm pfmsForm) {

    }

    //获取指定月份的相应金额总和 type-1为收入,type-2为支出
    public BigDecimal getTotalAmountByMonth(int year, int month, String type, int creatorId) {
        Date firstDate = getDateOfMonth(year, month, "B");
        Date lastDate = getDateOfMonth(year, month, "L");
        HashMap queryParam = new HashMap();
        queryParam.put("type", type);
        queryParam.put("firstDate", firstDate);
        queryParam.put("lastDate", lastDate);
        BigDecimal sumAmount = customMapper.selTotAmtByMon(type,firstDate,lastDate,creatorId);
        return sumAmount;
    }

    //获取指定月份记录的收支单据流水
    public List<FormVm> getFormVmByMonth(int year, int month, int creatorId) {
        Date firstDate = getDateOfMonth(year, month, "B");
        Date lastDate = getDateOfMonth(year, month, "L");
        FormVmExample formVmExample = new FormVmExample();
        formVmExample.createCriteria().andValueDateBetween(firstDate, lastDate).andCreatorIdEqualTo(creatorId);
        formVmExample.setOrderByClause("VALUE_DATE DESC");
        List<FormVm> formVmList = formVmMapper.selectByExample(formVmExample);
        return formVmList;
    }

    public Date getDateOfMonth(int year, int month, String type) {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        if ("B".equals(type)) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
        } else {
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            date = calendar.getTime();
        }
        return date;
    }

    public List<FormVm> getFormVmListByCondition(String type, Date beginDate, Date endDate, String proOneId, String proTwoId, String id) {
        FormVmExample formVmExample = new FormVmExample();
        FormVmExample.Criteria criteria = formVmExample.createCriteria();
        if(type != null && ("1".equals(type) || "2".equals(type))){
            criteria.andTypeEqualTo(type);
        }
        if(beginDate !=null && endDate != null && beginDate.before(endDate) && endDate.before(new Date())) {
            criteria.andValueDateBetween(beginDate, endDate);
        }
        if(proOneId != null && !"".equals(proOneId)){
            criteria.andAccUsage1EqualTo(proOneId);
        }
        if(proTwoId !=null && !"".equals(proTwoId)){
            criteria.andAccUsage2EqualTo(proTwoId);
        }
        if(id != null && !"".equals(id)){
            criteria.andIdEqualTo(id);
        }
        List<FormVm> formVmList = formVmMapper.selectByExample(formVmExample);
        return  formVmList;
    }
}
