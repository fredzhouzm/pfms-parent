package com.pfms.web.domain;

/**
 * Created by Fred on 2016/1/3.
 */
public class LevelTwoProject {

    private String projectID;
    private String projectType;
    private String projectFatherID;
    private String projectName;
    private String projectAmount;
    private String projectMonthBudget;
    private String realAmountByMonth;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectFatherID() {
        return projectFatherID;
    }

    public void setProjectFatherID(String projectFatherID) {
        this.projectFatherID = projectFatherID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(String projectAmount) {
        this.projectAmount = projectAmount;
    }

    public String getProjectMonthBudget() {
        return projectMonthBudget;
    }

    public void setProjectMonthBudget(String projectMonthBudget) {
        this.projectMonthBudget = projectMonthBudget;
    }

    public String getRealAmountByMonth() {
        return realAmountByMonth;
    }

    public void setRealAmountByMonth(String realAmountByMonth) {
        this.realAmountByMonth = realAmountByMonth;
    }
}
