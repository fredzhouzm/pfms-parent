package com.pfms.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class PfmsForm {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.VALUE_DATE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private Date valueDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.TIME_NO
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String timeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.AMOUNT
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.ACC_USAGE1
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String accUsage1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.ACC_USAGE2
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String accUsage2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.CREATOR_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private Integer creatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.CREATE_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.MODIFIER_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private Integer modifierId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.MODIFY_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.REMARK
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pfmsdb_form.TYPE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    private String type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.ID
     *
     * @return the value of pfmsdb_form.ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.ID
     *
     * @param id the value for pfmsdb_form.ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.VALUE_DATE
     *
     * @return the value of pfmsdb_form.VALUE_DATE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public Date getValueDate() {
        return valueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.VALUE_DATE
     *
     * @param valueDate the value for pfmsdb_form.VALUE_DATE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.TIME_NO
     *
     * @return the value of pfmsdb_form.TIME_NO
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getTimeNo() {
        return timeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.TIME_NO
     *
     * @param timeNo the value for pfmsdb_form.TIME_NO
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setTimeNo(String timeNo) {
        this.timeNo = timeNo == null ? null : timeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.AMOUNT
     *
     * @return the value of pfmsdb_form.AMOUNT
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.AMOUNT
     *
     * @param amount the value for pfmsdb_form.AMOUNT
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.ACC_USAGE1
     *
     * @return the value of pfmsdb_form.ACC_USAGE1
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getAccUsage1() {
        return accUsage1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.ACC_USAGE1
     *
     * @param accUsage1 the value for pfmsdb_form.ACC_USAGE1
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setAccUsage1(String accUsage1) {
        this.accUsage1 = accUsage1 == null ? null : accUsage1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.ACC_USAGE2
     *
     * @return the value of pfmsdb_form.ACC_USAGE2
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getAccUsage2() {
        return accUsage2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.ACC_USAGE2
     *
     * @param accUsage2 the value for pfmsdb_form.ACC_USAGE2
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setAccUsage2(String accUsage2) {
        this.accUsage2 = accUsage2 == null ? null : accUsage2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.CREATOR_ID
     *
     * @return the value of pfmsdb_form.CREATOR_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.CREATOR_ID
     *
     * @param creatorId the value for pfmsdb_form.CREATOR_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.CREATE_TIME
     *
     * @return the value of pfmsdb_form.CREATE_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.CREATE_TIME
     *
     * @param createTime the value for pfmsdb_form.CREATE_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.MODIFIER_ID
     *
     * @return the value of pfmsdb_form.MODIFIER_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public Integer getModifierId() {
        return modifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.MODIFIER_ID
     *
     * @param modifierId the value for pfmsdb_form.MODIFIER_ID
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.MODIFY_TIME
     *
     * @return the value of pfmsdb_form.MODIFY_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.MODIFY_TIME
     *
     * @param modifyTime the value for pfmsdb_form.MODIFY_TIME
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.REMARK
     *
     * @return the value of pfmsdb_form.REMARK
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.REMARK
     *
     * @param remark the value for pfmsdb_form.REMARK
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pfmsdb_form.TYPE
     *
     * @return the value of pfmsdb_form.TYPE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pfmsdb_form.TYPE
     *
     * @param type the value for pfmsdb_form.TYPE
     *
     * @mbggenerated Sun May 22 17:23:06 CST 2016
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}