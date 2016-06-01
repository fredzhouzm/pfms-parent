/**
 * 汇付天下有限公司 Copyright (c) 2006-2016 ChinaPnR,Inc.All Rights Reserved.
 */
package com.pfms.dao.mybatis.dao;

import java.math.BigDecimal;

/**
 * <h1>Class Description</h1>
 *
 * @author fred.zhou
 * @version $ Id:CustPfmsUsageTwoMapper.java, v0.1 2016/6/1 12:10 fred.zhou Exp $
 */
public interface CustPfmsUsageTwoMapper {
    BigDecimal getAddResultByFatherId(String fatherId);
}
