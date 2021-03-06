package com.pfms.dao.mybatis.dao;

import com.pfms.dao.mybatis.model.FormVm;
import com.pfms.dao.mybatis.model.FormVmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormVmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int countByExample(FormVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int deleteByExample(FormVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insert(FormVm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insertSelective(FormVm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    List<FormVm> selectByExample(FormVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormVm record, @Param("example") FormVmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsvm_form
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExample(@Param("record") FormVm record, @Param("example") FormVmExample example);
}