package com.pfms.dao.mybatis.dao;

import com.pfms.dao.mybatis.model.ProsetVm;
import com.pfms.dao.mybatis.model.ProsetVmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProsetVmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int countByExample(ProsetVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int deleteByExample(ProsetVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insert(ProsetVm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insertSelective(ProsetVm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    List<ProsetVm> selectByExample(ProsetVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExampleSelective(@Param("record") ProsetVm record, @Param("example") ProsetVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_proset
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExample(@Param("record") ProsetVm record, @Param("example") ProsetVmExample example);
}