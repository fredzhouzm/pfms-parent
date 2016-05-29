package com.pfms.dao.mybatis.dao;

import com.pfms.dao.mybatis.model.RealStatistics;
import com.pfms.dao.mybatis.model.RealStatisticsExample;
import com.pfms.dao.mybatis.model.RealStatisticsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RealStatisticsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int countByExample(RealStatisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int deleteByExample(RealStatisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int deleteByPrimaryKey(RealStatisticsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insert(RealStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int insertSelective(RealStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    List<RealStatistics> selectByExample(RealStatisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    RealStatistics selectByPrimaryKey(RealStatisticsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExampleSelective(@Param("record") RealStatistics record, @Param("example") RealStatisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByExample(@Param("record") RealStatistics record, @Param("example") RealStatisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByPrimaryKeySelective(RealStatistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pfmsdb_realStatistics
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    int updateByPrimaryKey(RealStatistics record);
}