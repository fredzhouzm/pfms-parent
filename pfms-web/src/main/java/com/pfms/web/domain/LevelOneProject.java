package com.pfms.web.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fred on 2016/1/3.
 */
public class LevelOneProject {
    private String projectID;
    private String projectType;
    private String projectName;
    private String projectAmount;
    private String projectMonthBudget;
    private String realAmountByMonth;
    private int projectChildren;
    private List<LevelTwoProject> levelTwoProjectList = new LinkedList<LevelTwoProject>();

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

    public int getProjectChildren() {
        return projectChildren;
    }

    public void setProjectChildren(int projectChildren) {
        this.projectChildren = projectChildren;
    }

    public List<LevelTwoProject> getLevelTwoProjectList() {
        return levelTwoProjectList;
    }

    public void setLevelTwoProjectList(List<LevelTwoProject> levelTwoProjectList) {
        this.levelTwoProjectList = levelTwoProjectList;
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
