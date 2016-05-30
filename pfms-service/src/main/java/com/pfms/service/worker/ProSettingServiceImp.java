package com.pfms.service.worker;

import com.pfms.dao.mybatis.dao.PfmsUsageOneMapper;
import com.pfms.dao.mybatis.dao.PfmsUsageTwoMapper;
import com.pfms.dao.mybatis.dao.RealStatisticsMapper;
import com.pfms.dao.mybatis.model.*;
import com.pfms.service.boss.IProSettingService;
import com.pfms.service.boss.ISequenceService;
import com.pfms.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Fred on 2016/1/3.
 */
@Scope("singleton")
@Service
public class ProSettingServiceImp implements IProSettingService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PfmsUsageOneMapper pfmsUsageOneMapper;

    @Autowired
    PfmsUsageTwoMapper pfmsUsageTwoMapper;

    @Autowired
    ISequenceService sequenceService;

    @Autowired
    RealStatisticsMapper realStatisticsMapper;

    @Override
    public void insertProOne(PfmsUsageOne pmfsUsageOne) {
        pfmsUsageOneMapper.insert(pmfsUsageOne);
    }

    @Override
    public void updateProOne(PfmsUsageOne pfmsUsageOne) {
        pfmsUsageOneMapper.updateByPrimaryKey(pfmsUsageOne);
    }

    @Override
    public void deleteProOne(PfmsUsageOne pfmsUsageOne) {
        pfmsUsageOneMapper.deleteByPrimaryKey(pfmsUsageOne.getId());
    }

    @Override
    public void insertProTwo(PfmsUsageTwo pfmsUsageTwo) {
        pfmsUsageTwoMapper.insert(pfmsUsageTwo);
    }

    @Override
    public void updateProTwo(PfmsUsageTwo pfmsUsageTwo) {
        pfmsUsageTwoMapper.updateByPrimaryKey(pfmsUsageTwo);
    }

    @Override
    public void deleteProTwo(PfmsUsageTwo pfmsUsageTwo) {
        pfmsUsageTwoMapper.deleteByPrimaryKey(pfmsUsageTwo.getId());
    }

    @Override
    public List<PfmsUsageOne> getProOne(String id, String type, Integer creatorId) {

        PfmsUsageOneExample pfmsUsageOneExample = new PfmsUsageOneExample();
        PfmsUsageOneExample.Criteria criteria = pfmsUsageOneExample.createCriteria();
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (creatorId != null) {
            criteria.andCreatorIdEqualTo(creatorId);
        }
        pfmsUsageOneExample.setOrderByClause("ID ASC");
        return pfmsUsageOneMapper.selectByExample(pfmsUsageOneExample);
    }

    @Override
    public List<PfmsUsageTwo> getProTwo(String id, String type, String parentId, Integer creatorId) {

        PfmsUsageTwoExample pfmsUsageTwoExample = new PfmsUsageTwoExample();
        PfmsUsageTwoExample.Criteria criteria = pfmsUsageTwoExample.createCriteria();
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (parentId != null) {
            criteria.andFatherIdEqualTo(parentId);
        }
        if (creatorId != null) {
            criteria.andCreatorIdEqualTo(creatorId);
        }
        pfmsUsageTwoExample.setOrderByClause("ID ASC");
        return pfmsUsageTwoMapper.selectByExample(pfmsUsageTwoExample);
    }


    @Override
    public PfmsUsageOne insertProOneWithPara(String name, String type, int userId) {
        PfmsUsageOne pfmsUsageOne = new PfmsUsageOne();
        pfmsUsageOne.setId(sequenceService.getId(Constants.SEQ_PRO_ONE_ID));//一级科目ID
        pfmsUsageOne.setType(type);//"1"为收入一级科目，"2"为支出一级科目
        pfmsUsageOne.setName(name);//一级科目名称
        pfmsUsageOne.setMonthbudget(BigDecimal.valueOf(0.00));//一级科目预算金额
        pfmsUsageOne.setAmount(BigDecimal.valueOf(0.00));//暂时废弃
        pfmsUsageOne.setChildren(0);//一级科目下属的二级科目数量
        pfmsUsageOne.setCreatorId(userId);//创建者/所属者ID
        pfmsUsageOne.setCreateTime(new Date());//创建时间
        pfmsUsageOne.setDescript("");//描述，默认为空
        insertProOne(pfmsUsageOne);
        return pfmsUsageOne;
    }

    @Override
    public PfmsUsageTwo insertProTwoWithPara(String name, String type, int userId, String fatherId) {
        PfmsUsageTwo pfmsUsageTwo = new PfmsUsageTwo();
        pfmsUsageTwo.setId(sequenceService.getId(Constants.SEQ_PRO_TWO_ID));//二级科目ID
        pfmsUsageTwo.setType(type);//"1"为收入二级科目，"2"为支出二级科目
        pfmsUsageTwo.setName(name);//二级科目名称
        pfmsUsageTwo.setFatherId(fatherId);//二级科目父ID
        pfmsUsageTwo.setAmount(BigDecimal.valueOf(0.00));//二级科目已获取/消费金额
        pfmsUsageTwo.setCreatorId(userId);//创建者/所属者ID
        pfmsUsageTwo.setCreateTime(new Date());//创建时间
        pfmsUsageTwo.setDescript("");//描述，默认为空
        insertProTwo(pfmsUsageTwo);
        return pfmsUsageTwo;
    }

    @Override
    public RealStatistics getOrInsertMonthBudget(String id, String month, BigDecimal money) {
        RealStatisticsExample realStatisticsExample = new RealStatisticsExample();
        realStatisticsExample.createCriteria().andIdEqualTo(id).andMonthEqualTo(month);
        List<RealStatistics> realStatisticses = realStatisticsMapper.selectByExample(realStatisticsExample);
        if(realStatisticses.size() < 1){
            RealStatistics realStatistics = new RealStatistics();
            realStatistics.setId(id);
            realStatistics.setMonth(month);
            realStatistics.setBudget(money);
            realStatistics.setRealamount(new BigDecimal(0.00));
            realStatisticsMapper.insert(realStatistics);
            return realStatistics;
        }
        else{
            return realStatisticses.get(0);
        }
    }
}
