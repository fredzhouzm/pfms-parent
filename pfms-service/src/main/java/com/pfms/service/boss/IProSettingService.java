package com.pfms.service.boss;

import com.pfms.dao.mybatis.model.PfmsUsageOne;
import com.pfms.dao.mybatis.model.PfmsUsageTwo;
import com.pfms.dao.mybatis.model.RealStatistics;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Fred on 2016/1/2.
 */
public interface IProSettingService {

    public void insertProOne(PfmsUsageOne pmfsUsageOne);

    public void updateProOne(PfmsUsageOne pfmsUsageOne);

    public void deleteProOne(PfmsUsageOne pfmsUsageOne);

    public void insertProTwo(PfmsUsageTwo pfmsUsageTwo);

    public void updateProTwo(PfmsUsageTwo pfmsUsageTwo);

    public void deleteProTwo(PfmsUsageTwo pfmsUsageTwo);

    public List<PfmsUsageOne> getProOne(String id, String type, Integer creatorId);

    public List<PfmsUsageTwo> getProTwo(String id, String type, String parentId, Integer creatorId);

    public PfmsUsageOne insertProOneWithPara(String name, String type, int userId);

    public PfmsUsageTwo insertProTwoWithPara(String name, String type, int userId, String fatherId, String budget);

    public RealStatistics getOrInsertMonthBudget(String id, String month, BigDecimal money);

    public RealStatistics updateMonthBudget(String id, String month, BigDecimal money);

    public BigDecimal getParentIdBudget(String fatherId);
}
