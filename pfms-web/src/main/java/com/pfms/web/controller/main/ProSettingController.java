package com.pfms.web.controller.main;

import com.pfms.dao.mybatis.model.PfmsUsageOne;
import com.pfms.dao.mybatis.model.PfmsUsageTwo;
import com.pfms.dao.mybatis.model.PfmsUser;
import com.pfms.dao.mybatis.model.RealStatistics;
import com.pfms.service.boss.IProSettingService;
import com.pfms.util.PersonalUtil;
import com.pfms.web.domain.Authentication;
import com.pfms.web.domain.LevelOneProject;
import com.pfms.web.domain.LevelTwoProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Fred on 2016/1/2.
 */
@Scope("singleton")
@Controller
@RequestMapping("/setting")
public class ProSettingController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public IProSettingService proSettingServiceImp;
    @Autowired
    public PersonalUtil personalUtil;

    @RequestMapping(value = "/proSet.htm")
    public ModelAndView proSetting(HttpServletRequest request) {
        logger.info("开始进入科目设置操作！");
        ModelAndView modelAndView = new ModelAndView();

        //获取当前用户
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();

        //获取收入选项的科目设置
        HashMap<String, LevelOneProject> incomeNode = getProList("1", userId);
        modelAndView.addObject("incomeNode", incomeNode);

        //获取支出选项的科目设置
        HashMap<String, LevelOneProject> expendNode = getProList("2", userId);
        modelAndView.addObject("expendNode", expendNode);

        //返回ModelAndView
        modelAndView.setViewName("projectSettings");
        return modelAndView;

    }

    //新增一级科目
    @RequestMapping(value = "/addProOne.json", method = RequestMethod.POST)
    @ResponseBody
    public Map addProOne(@RequestBody Map map, HttpServletRequest request) {
        logger.info("来时新增一级科目！");

        //获取用户信息
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");

        //解析并判断JSON
        String proOneNameAdd = (String) map.get("proOneNameAdd");
        proOneNameAdd = proOneNameAdd.trim();
        String type = (String) map.get("proOneType");
        type = type.trim();
        Map jsonMap = new HashMap();
        if (personalUtil.isBlankOrNull(proOneNameAdd) || personalUtil.isBlankOrNull(type)) {
            jsonMap.put("opstatus", "fail");
            jsonMap.put("optype", "");
            jsonMap.put("opid", "");
            jsonMap.put("opname", "");
            jsonMap.put("oprealMmount", "");
            jsonMap.put("opbudgetMmount", "");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String today = sdf.format(new Date());
            PfmsUsageOne pfmsUsageOne = proSettingServiceImp.insertProOneWithPara(proOneNameAdd, type, authentication.getId());
            RealStatistics realStatisticses = proSettingServiceImp.getOrInsertMonthBudget(pfmsUsageOne.getId(),today,pfmsUsageOne.getMonthbudget());
            jsonMap.put("opstatus", "success");
            jsonMap.put("optype", pfmsUsageOne.getType());
            jsonMap.put("opid", pfmsUsageOne.getId());
            jsonMap.put("opname", pfmsUsageOne.getName());
            jsonMap.put("oprealMmount", personalUtil.bigDecimalToString(realStatisticses.getRealamount()));
            jsonMap.put("opbudgetMmount", personalUtil.bigDecimalToString(realStatisticses.getBudget()));
        }
        return jsonMap;
    }

    //新增二级科目
    @RequestMapping(value = "/addProTwo.json", method = RequestMethod.POST)
    @ResponseBody
    public Map addProTwo(@RequestBody Map map, HttpServletRequest request) {
        logger.info("开始新增二级科目！");

        //获取用户信息
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");

        //解析并判断JSON
        String proTwoNameAdd = (String) map.get("proTwoNameAdd");
        String type = (String) map.get("proTwoType");
        String proOneId = (String) map.get("proOneId");
        String budget = (String) map.get("proTwoBudgetAdd");
        Map jsonMap = new HashMap();
        if (personalUtil.isBlankOrNull(proTwoNameAdd) || personalUtil.isBlankOrNull(type) || personalUtil.isBlankOrNull(proOneId)) {
            jsonMap.put("opstatus", "fail");
            jsonMap.put("optype", "");
            jsonMap.put("opparentid", "");
            jsonMap.put("opid", "");
            jsonMap.put("opname", "");
            jsonMap.put("opparentbudgetamount", "");
            jsonMap.put("opparentrealamount", "");
            jsonMap.put("opbudgetamount", "");
            jsonMap.put("oprealamount", "");
        } else {
            //判断此一级科目在当前收入支出项下面是否存在
            List<PfmsUsageOne> pfmsUsageOnes = proSettingServiceImp.getProOne(proOneId, type, authentication.getId());
            if (pfmsUsageOnes.size() != 1) {
                jsonMap.put("opstatus", "fail");
                jsonMap.put("optype", "");
                jsonMap.put("opparentid", "");
                jsonMap.put("opid", "");
                jsonMap.put("opname", "");
                jsonMap.put("opparentbudgetamount", "");
                jsonMap.put("opparentrealamount", "");
                jsonMap.put("opbudgetamount", "");
                jsonMap.put("oprealamount", "");
            }
            //向数据库中插入此条二级科目
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                String today = sdf.format(new Date());
                //插入二级科目
                PfmsUsageTwo pfmsUsageTwo = proSettingServiceImp.insertProTwoWithPara(proTwoNameAdd, type, authentication.getId(), proOneId, budget);
                //向预算表中插入当月相关的二级科目
                proSettingServiceImp.getOrInsertMonthBudget(pfmsUsageTwo.getId(), today, pfmsUsageTwo.getMonthbudget());
                //更新一级科目
                PfmsUsageOne pfmsUsageOne = pfmsUsageOnes.get(0);
                BigDecimal bigDecimal = pfmsUsageOne.getMonthbudget();
                BigDecimal newBigDecimal = bigDecimal.add(pfmsUsageTwo.getMonthbudget());
                pfmsUsageOne.setMonthbudget(newBigDecimal);
                proSettingServiceImp.updateProOne(pfmsUsageOne);
                //在预算表中更新当月相关的一级科目
                RealStatistics realStatistics = proSettingServiceImp.updateMonthBudget(pfmsUsageOne.getId(),today,newBigDecimal);
                jsonMap.put("opstatus", "success");
                jsonMap.put("optype", pfmsUsageTwo.getType());
                jsonMap.put("opparentid", pfmsUsageTwo.getFatherId());
                jsonMap.put("opid", pfmsUsageTwo.getId());
                jsonMap.put("opname", pfmsUsageTwo.getName());
                jsonMap.put("opparentbudgetamount", personalUtil.bigDecimalToString(newBigDecimal));
                jsonMap.put("opparentrealamount", personalUtil.bigDecimalToString(realStatistics.getRealamount()));
                jsonMap.put("opbudgetamount", personalUtil.bigDecimalToString(pfmsUsageTwo.getMonthbudget()));
                jsonMap.put("oprealamount", "0.00");
            }
        }
        return jsonMap;
    }

    //修改科目名称
    @RequestMapping(value = "/proOneModify.json", method = RequestMethod.POST)
    @ResponseBody
    public Map modifyPro(@RequestBody Map map) {
        logger.info("开始修改科目名称！");

        Map jsonMap = new HashMap();
        String type = "";
        boolean resultType;

        //解析并判断JSON
        String proId = (String) map.get("proId");
        String proNameModify = (String) map.get("proNameModify");
        if (personalUtil.isBlankOrNull(proId) || personalUtil.isBlankOrNull(proNameModify)) {
            resultType = false;
        } else {
            List<PfmsUsageOne> pfmsUsageOnes = proSettingServiceImp.getProOne(proId, null, null);
            if (pfmsUsageOnes.size() == 1) {
                PfmsUsageOne pfmsUsageOne = pfmsUsageOnes.get(0);
                type = pfmsUsageOne.getType();
                pfmsUsageOne.setName(proNameModify);
                proSettingServiceImp.updateProOne(pfmsUsageOne);
                resultType = true;
            } else {
                resultType = false;
            }
                /*List<PfmsUsageTwo> pfmsUsageTwos = proSettingServiceImp.getProTwo(proId, null, null, null);
                if (pfmsUsageTwos.size() == 1) {
                    PfmsUsageTwo pfmsUsageTwo = pfmsUsageTwos.get(0);
                    type = pfmsUsageTwo.getType();
                    pfmsUsageTwo.setName(proNameModify);
                    proSettingServiceImp.updateProTwo(pfmsUsageTwo);
                    resultType = true;
                } else {
                    resultType = false;
                }*/
        }
        if (resultType) {
            jsonMap.put("opstatus", "success");
            jsonMap.put("optype", type);
            jsonMap.put("opid", proId);
            jsonMap.put("opname", proNameModify);
        } else {
            jsonMap.put("opstatus", "fail");
            jsonMap.put("optype", "");
            jsonMap.put("opid", "");
            jsonMap.put("opname", "");
        }
        return jsonMap;
    }

    //删除科目
    @RequestMapping(value = "/proDelete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map deletePro(@RequestBody Map map) {
        logger.info("开始删除科目操作！");

        Map jsonMap = new HashMap();
        String type = "";
        boolean resultType;

        //解析并判断JSON
        String proId = (String) map.get("proId");
        String level = (String) map.get("level");
        if (personalUtil.isBlankOrNull(proId) || personalUtil.isBlankOrNull(level)) {
            resultType = false;
        } else {
            if ("1".equals(level)) {
                List<PfmsUsageOne> pfmsUsageOnes = proSettingServiceImp.getProOne(proId, null, null);
                if (pfmsUsageOnes.size() == 1) {
                    type = pfmsUsageOnes.get(0).getType();
                    proSettingServiceImp.deleteProOne(pfmsUsageOnes.get(0));
                    resultType = true;
                } else {
                    resultType = false;
                }
            } else {
                List<PfmsUsageTwo> pfmsUsageTwos = proSettingServiceImp.getProTwo(proId, null, null, null);
                if (pfmsUsageTwos.size() == 1) {
                    type = pfmsUsageTwos.get(0).getType();
                    proSettingServiceImp.deleteProTwo(pfmsUsageTwos.get(0));
                    resultType = true;
                } else {
                    resultType = false;
                }
            }
        }
        if (resultType) {
            jsonMap.put("opstatus", "success");
            jsonMap.put("optype", type);
            jsonMap.put("oplvl", level);
            jsonMap.put("opid", proId);
        } else {
            jsonMap.put("opstatus", "fail");
            jsonMap.put("optype", "");
            jsonMap.put("oplvl", "");
            jsonMap.put("opid", "");
        }
        return jsonMap;
    }

    @RequestMapping(value = "/getProOneName.json",method = RequestMethod.POST)
    @ResponseBody
    public Map getProOneName(@RequestBody Map map,HttpServletRequest request){
        Map<String, String> resultMap = new HashMap<String, String>();
        String id = map.get("id").toString();
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();
        List<PfmsUsageOne> pfmsUsageOnes = proSettingServiceImp.getProOne(id,null,userId);
        if(pfmsUsageOnes.size() != 1){
            resultMap.put("opstatus","failure");
            resultMap.put("optname","");
        }else{
            resultMap.put("opstatus","success");
            resultMap.put("optname",pfmsUsageOnes.get(0).getName());
        }
        return resultMap;
    }

    public HashMap<String, LevelOneProject> getProList(String type, int userId) {
        List<PfmsUsageTwo> pfmsUsageTwos;
        List<PfmsUsageOne> pfmsUsageOnes;
        LinkedHashMap<String, LevelOneProject> map = new LinkedHashMap<String, LevelOneProject>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String today = sdf.format(new Date());
        pfmsUsageOnes = proSettingServiceImp.getProOne(null, type, userId);
        for (PfmsUsageOne pfmsUsageOne : pfmsUsageOnes) {
            pfmsUsageTwos = proSettingServiceImp.getProTwo(null, type, pfmsUsageOne.getId(), userId);
            LevelOneProject levelOneProject = new LevelOneProject();
            levelOneProject.setProjectID(pfmsUsageOne.getId());
            levelOneProject.setProjectType(pfmsUsageOne.getType());
            levelOneProject.setProjectName(pfmsUsageOne.getName());
            levelOneProject.setProjectChildren(pfmsUsageTwos.size());
            for (PfmsUsageTwo pfmsUsageTwo : pfmsUsageTwos) {
                LevelTwoProject levelTwoProject = new LevelTwoProject();
                RealStatistics realStatistics = proSettingServiceImp.getOrInsertMonthBudget(pfmsUsageTwo.getId(),today,pfmsUsageTwo.getMonthbudget());
                levelTwoProject.setProjectID(pfmsUsageTwo.getId());
                levelTwoProject.setProjectType(pfmsUsageTwo.getType());
                levelTwoProject.setProjectName(pfmsUsageTwo.getName());
                levelTwoProject.setProjectFatherID(pfmsUsageTwo.getFatherId());
                levelTwoProject.setRealAmountByMonth(personalUtil.bigDecimalToString(realStatistics.getRealamount()));
                levelTwoProject.setProjectMonthBudget(personalUtil.bigDecimalToString(realStatistics.getBudget()));
                levelOneProject.getLevelTwoProjectList().add(levelTwoProject);
            }
            RealStatistics realStatisticsForProOne = proSettingServiceImp.getOrInsertMonthBudget(pfmsUsageOne.getId(),today,pfmsUsageOne.getMonthbudget());
            levelOneProject.setRealAmountByMonth(personalUtil.bigDecimalToString(realStatisticsForProOne.getRealamount()));
            levelOneProject.setProjectMonthBudget(personalUtil.bigDecimalToString(realStatisticsForProOne.getBudget()));
            map.put(pfmsUsageOne.getId(), levelOneProject);
        }
        return map;
    }
}
