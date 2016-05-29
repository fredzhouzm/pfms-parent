package com.pfms.web.domain;

import java.util.Date;

/**
 * Created by Fred on 16/2/28.
 */
public class FormToShow {
    private String id;
    private String type;
    private String amount;
    private Date valueDate;
    private String valueDateStr;
    private String peroid;
    private String peroidStr;
    private String proOne;
    private String proOneStr;
    private String proTwo;
    private String proTwoStr;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueDateStr() {
        return valueDateStr;
    }

    public void setValueDateStr(String valueDateStr) {
        this.valueDateStr = valueDateStr;
    }

    public String getPeroid() {
        return peroid;
    }

    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    public String getPeroidStr() {
        return peroidStr;
    }

    public void setPeroidStr(String peroidStr) {
        this.peroidStr = peroidStr;
    }

    public String getProOne() {
        return proOne;
    }

    public void setProOne(String proOne) {
        this.proOne = proOne;
    }

    public String getProOneStr() {
        return proOneStr;
    }

    public void setProOneStr(String proOneStr) {
        this.proOneStr = proOneStr;
    }

    public String getProTwo() {
        return proTwo;
    }

    public void setProTwo(String proTwo) {
        this.proTwo = proTwo;
    }

    public String getProTwoStr() {
        return proTwoStr;
    }

    public void setProTwoStr(String proTwoStr) {
        this.proTwoStr = proTwoStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
