package com.pfms.web.controller.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pfms.dao.mybatis.model.FormVm;
import com.pfms.dao.mybatis.model.PfmsForm;
import com.pfms.dao.mybatis.model.PfmsUser;
import com.pfms.service.boss.IAccountService;
import com.pfms.service.boss.ISequenceService;
import com.pfms.util.Constants;
import com.pfms.util.PersonalUtil;
import com.pfms.web.domain.Authentication;
import com.pfms.web.domain.FormToShow;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fred on 2016/01/31.
 */
@Scope("singleton")
@Controller
@RequestMapping("/account")
public class AccountingController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public IAccountService accountServiceImp;

    @Autowired
    public ISequenceService sequenceServiceImp;

    @Autowired
    public PersonalUtil personalUtil;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    @RequestMapping(value = "/show.htm")
    public ModelAndView accountInit(HttpServletRequest request) {
        logger.info("记账页面初始化");
        ModelAndView modelAndView = new ModelAndView();
        HashMap<String, String> incomeProOneList;
        HashMap<String, String> expendProOneList;
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int currentYear;
        int currentMonth;
        Double totalAmountIn;
        Double totalAmountOut;
        LinkedList<FormToShow> formToShowLinkedList= new LinkedList<>();
        List<FormVm> formVmList;

        String now = simpleDateFormat.format(new Date());
        currentYear = Integer.parseInt(now.substring(0, 4));
        currentMonth = Integer.parseInt(now.substring(5, 7));
        incomeProOneList = accountServiceImp.getProOne("1", authentication.getId());
        expendProOneList = accountServiceImp.getProOne("2", authentication.getId());
        totalAmountIn = accountServiceImp.getTotalAmountByMonth(currentYear,currentMonth,"1",authentication.getId()).doubleValue();
        totalAmountOut = accountServiceImp.getTotalAmountByMonth(currentYear,currentMonth,"2",authentication.getId()).doubleValue();
        formVmList = accountServiceImp.getFormVmByMonth(currentYear,currentMonth,authentication.getId());
        for(FormVm formVm:formVmList){
            FormToShow formToShow = formVmToFromShow(formVm);
            formToShowLinkedList.add(formToShow);
        }

        modelAndView.addObject("incomeProOneList", incomeProOneList);
        modelAndView.addObject("expendProOneList", expendProOneList);
        modelAndView.addObject("currentYear", String.valueOf(currentYear));
        modelAndView.addObject("currentMonth", personalUtil.intToString(currentMonth,2));
        modelAndView.addObject("totalAmountIn",decimalFormat.format(totalAmountIn));
        modelAndView.addObject("totalAmountOut",decimalFormat.format(totalAmountOut));
        modelAndView.addObject("formList",formToShowLinkedList);
        modelAndView.setViewName("account");
        return modelAndView;
    }

    @RequestMapping(value = "/getProTwoList.json", method = RequestMethod.POST)
    @ResponseBody
    public Map getProTwoList(@RequestBody Map map) {
        logger.info("AJAX异步获取对应的二级科目分类!");

        Map jsonMap = new HashMap();
        boolean resultType;

        //解析并判断JSON
        String proOneId = (String) map.get("proOneId");
        if (personalUtil.isBlankOrNull(proOneId)) {
            resultType = false;
        } else {
            logger.info("一级科目分类的ID,proOneId为:" + proOneId);
            HashMap<String, String> proTwolist;
            proTwolist = accountServiceImp.getProTwoByProOne(proOneId);
            resultType = true;
            jsonMap.put("opmap", proTwolist);
        }
        if (resultType) {
            jsonMap.put("opstatus", "success");
        } else {
            jsonMap.put("opstatus", "failure");
        }
        return jsonMap;
    }

    @RequestMapping(value = "/insertForm.json", method = RequestMethod.POST)
    @ResponseBody
    public Map insertForm(@RequestBody Map map, HttpServletRequest request) throws ParseException {
        logger.info("Ajax异步提交单据");

        Map jsonMap = new HashMap();
        boolean resultType;
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();
        PfmsForm pfmsForm;

        //
        String type = (String) map.get("type");
        String amount = (String) map.get("amount");
        String proOneId = (String) map.get("proOneId");
        String proTwoId = (String) map.get("proTwoId");
        String date = (String) map.get("date");
        String peroid = (String) map.get("peroid");
        String remark = (String) map.get("remark");

        pfmsForm = transToForm(type, amount, proOneId, proTwoId, date, peroid, remark, userId);
        accountServiceImp.insertForm(pfmsForm);

        FormVm formVm = accountServiceImp.getFormVmListByCondition(null,null,null,null,null,pfmsForm.getId()).get(0);
        int selectedYear;
        int seletedMonth;
        Double totalAmountIn = 0.00;
        Double totalAmountOut = 0.00;

        selectedYear = Integer.parseInt(date.substring(0, 4));
        seletedMonth = Integer.parseInt(date.substring(5, 7));

        FormToShow formToShow = formVmToFromShow(formVm);
        totalAmountIn = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"1",userId).doubleValue();
        totalAmountOut = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"2",userId).doubleValue();

        jsonMap.put("opstatus", "success");
        jsonMap.put("opid",formToShow.getId());
        jsonMap.put("optype",formToShow.getType());
        jsonMap.put("opamount",formToShow.getAmount());
        jsonMap.put("opdate",formToShow.getValueDateStr());
        jsonMap.put("opperiod",formToShow.getPeroidStr());
        jsonMap.put("oppro",formToShow.getProOneStr()+"-"+formToShow.getProTwoStr());
        jsonMap.put("opremark",formToShow.getRemark());
        jsonMap.put("opselectedMonth",String.valueOf(selectedYear)+personalUtil.intToString(seletedMonth,2));
        jsonMap.put("optotalAmountIn",decimalFormat.format(totalAmountIn));
        jsonMap.put("optotalAmountOut",decimalFormat.format(totalAmountOut));
        return jsonMap;

    }

    @RequestMapping(value = "/switchMonth.json",method = RequestMethod.POST)
    @ResponseBody
    public Map swithMonth(@RequestBody Map map, HttpServletRequest request){
        logger.info("更改月份");

        Map jsonMap = new HashMap();
        boolean resultType;
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();

        String monthStr = (String)map.get("newMonth");
        int year = Integer.parseInt(monthStr.substring(0,4));
        int month = Integer.parseInt(monthStr.substring(4));
        Double totalAmountIn;
        Double totalAmountOut;
        LinkedList<FormToShow> formToShowLinkedList= new LinkedList<>();
        List<FormVm> formVmList;

        totalAmountIn = accountServiceImp.getTotalAmountByMonth(year,month,"1",userId).doubleValue();
        totalAmountOut = accountServiceImp.getTotalAmountByMonth(year,month,"2",userId).doubleValue();

        jsonMap.put("opcurrentYear", String.valueOf(year));
        jsonMap.put("opcurrentMonth", personalUtil.intToString(month,2));
        jsonMap.put("optotalAmountIn",decimalFormat.format(totalAmountIn));
        jsonMap.put("optotalAmountOut",decimalFormat.format(totalAmountOut));

        formVmList = accountServiceImp.getFormVmByMonth(year,month,userId);
        if(formVmList != null && formVmList.size() > 0) {
            for (FormVm formVm : formVmList) {
                FormToShow formToShow = formVmToFromShow(formVm);
                formToShowLinkedList.add(formToShow);
            }
            jsonMap.put("opsearchResult","yes");
        }
        else{
            jsonMap.put("opsearchResult","no");
        }
        jsonMap.put("opformList",formToShowLinkedList);
        jsonMap.put("opstatus","success");
        return jsonMap;
    }

    @RequestMapping(value = "/modifyInit.json",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyInit(@RequestBody Map map, HttpServletRequest request){
        logger.info("修改单据初始化");
        String id = map.get("id").toString();
        logger.info("单据ID编号为："+id);
        Map jsonMap = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PfmsForm pfmsForm = accountServiceImp.getFormById(id);
        String proOneId = pfmsForm.getAccUsage1();
        if(pfmsForm != null){
            jsonMap.put("opstatus","success");
            jsonMap.put("opamount",personalUtil.bigDecimalToString(pfmsForm.getAmount()));
            jsonMap.put("opproone",pfmsForm.getAccUsage1());
            jsonMap.put("protwo",pfmsForm.getAccUsage2());
            jsonMap.put("opdate",sdf.format(pfmsForm.getValueDate()));
            jsonMap.put("opperoid",pfmsForm.getTimeNo());
            jsonMap.put("opremark",pfmsForm.getRemark());
            HashMap<String, String> proTwolist;
            proTwolist = accountServiceImp.getProTwoByProOne(proOneId);
            jsonMap.put("opprotwolist",proTwolist);
        }else{
            jsonMap.put("opstatus","failure");
            jsonMap.put("opamount","");
            jsonMap.put("opproone","");
            jsonMap.put("protwo","");
            jsonMap.put("opdate","");
            jsonMap.put("opperoid","");
            jsonMap.put("opremark","");
            jsonMap.put("opprotwolist","");
        }
        return jsonMap;
    }

    @RequestMapping(value = "/modifyForm.json",method = RequestMethod.POST)
    public Map modifyForm(@RequestBody Map map,HttpServletRequest request) throws ParseException {
        logger.info("修改单据提交开始");
        String id = map.get("id").toString();
        logger.info("单据ID编号为："+id);
        String type = map.get("type").toString();
        logger.info("单据类型为："+type);
        String amount = map.get("amount").toString();
        logger.info("单据金额为："+amount);
        String proOneId = map.get("proOneId").toString();
        logger.info("单据一级科目为："+proOneId);
        String proTwoId = map.get("proTwoId").toString();
        logger.info("单据二级科目为："+proTwoId);
        String date = map.get("date").toString();
        logger.info("单据日期为："+date);
        String peroid = map.get("peroid").toString();
        logger.info("单据时段为："+peroid);
        String remark = map.get("remark").toString();
        logger.info("单据备注为："+remark);
        String month = map.get("month").toString();
        logger.info("当前页面查询月份为："+month);
        Map jsonMap = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();
        PfmsForm pfmsForm = accountServiceImp.getFormById(id);
        if(pfmsForm == null){
            jsonMap.put("opstatus", "failure");
            jsonMap.put("opid","");
            jsonMap.put("optype","");
            jsonMap.put("opamount","");
            jsonMap.put("opdate","");
            jsonMap.put("opperiod","");
            jsonMap.put("oppro","");
            jsonMap.put("opremark","");
            jsonMap.put("opselectedMonth","");
            jsonMap.put("optotalAmountIn","");
            jsonMap.put("optotalAmountOut","");
            return jsonMap;
        }else{
            pfmsForm.setValueDate(sdf.parse(date));
            pfmsForm.setTimeNo(peroid);
            pfmsForm.setAmount(new BigDecimal(amount));
            pfmsForm.setAccUsage1(proOneId);
            pfmsForm.setAccUsage2(proTwoId);
            pfmsForm.setModifierId(userId);
            pfmsForm.setModifyTime(new Date());
            pfmsForm.setRemark(remark);
            accountServiceImp.updateForm(pfmsForm);
            FormVm formVm = accountServiceImp.getFormVmListByCondition(null,null,null,null,null,pfmsForm.getId()).get(0);
            FormToShow formToShow = formVmToFromShow(formVm);

            int selectedYear = Integer.parseInt(month.substring(0, 4));
            int seletedMonth = Integer.parseInt(month.substring(4, 6));
            Double totalAmountIn = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"1",userId).doubleValue();
            Double totalAmountOut = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"2",userId).doubleValue();
            String orderMonth = date.substring(0,4).concat(date.substring(5,7));

            jsonMap.put("opstatus", "success");
            jsonMap.put("opid",formToShow.getId());
            jsonMap.put("optype",formToShow.getType());
            jsonMap.put("opamount",formToShow.getAmount());
            jsonMap.put("opdate",formToShow.getValueDateStr());
            jsonMap.put("opperiod",formToShow.getPeroidStr());
            jsonMap.put("oppro",formToShow.getProOneStr()+"-"+formToShow.getProTwoStr());
            jsonMap.put("opremark",formToShow.getRemark());
            jsonMap.put("opselectedMonth",orderMonth);
            jsonMap.put("optotalAmountIn",decimalFormat.format(totalAmountIn));
            jsonMap.put("optotalAmountOut",decimalFormat.format(totalAmountOut));
            return jsonMap;
        }
    }

    @RequestMapping(value = "/deleteForm.json",method = RequestMethod.POST)
    public Map deleteForm(@RequestBody Map map,HttpServletRequest request){
        logger.info("删除单据开始");
        String id = map.get("id").toString();
        logger.info("单据ID编号为："+id);
        String type = map.get("type").toString();
        logger.info("单据类型为："+type);
        String month = map.get("month").toString();
        logger.info("当前页面查询月份为："+month);
        Map jsonMap = new HashMap();
        Authentication authentication = (Authentication) request.getSession().getAttribute("session_authentication");
        int userId = authentication.getId();
        accountServiceImp.deleteForm(id);
        int selectedYear = Integer.parseInt(month.substring(0, 4));
        int seletedMonth = Integer.parseInt(month.substring(4, 6));
        Double totalAmountIn = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"1",userId).doubleValue();
        Double totalAmountOut = accountServiceImp.getTotalAmountByMonth(selectedYear,seletedMonth,"2",userId).doubleValue();
        jsonMap.put("opstatus", "success");
        jsonMap.put("opid",id);
        jsonMap.put("optype",type);
        jsonMap.put("optotalAmountIn",decimalFormat.format(totalAmountIn));
        jsonMap.put("optotalAmountOut",decimalFormat.format(totalAmountOut));
        return  jsonMap;
    }
    public PfmsForm transToForm(String type, String amount, String proOneId, String proTwoId, String date, String peroid, String remark, int userId) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String id = sequenceServiceImp.getId(Constants.SEQ_FORMID);
        PfmsForm pfmsForm = new PfmsForm();

        pfmsForm.setId(id);
        pfmsForm.setType(type);
        pfmsForm.setAmount(new BigDecimal(amount));
        pfmsForm.setAccUsage1(proOneId);
        pfmsForm.setAccUsage2(proTwoId);
        pfmsForm.setValueDate(sdf.parse(date));
        pfmsForm.setTimeNo(peroid);
        pfmsForm.setRemark(remark);
        pfmsForm.setCreatorId(userId);
        pfmsForm.setCreateTime(new Date());

        return pfmsForm;
    }

    public FormToShow formVmToFromShow(FormVm formVm){
        FormToShow formToShow = new FormToShow();
        formToShow.setId(formVm.getId());
        formToShow.setType(formVm.getType());
        formToShow.setAmount(personalUtil.bigDecimalToString(formVm.getAmount()));
        formToShow.setValueDate(formVm.getValueDate());
        formToShow.setValueDateStr(simpleDateFormat.format(formVm.getValueDate()));
        formToShow.setPeroid(formVm.getTimeNo());
        formToShow.setPeroidStr(getStrOfPeriod(formVm.getTimeNo()));
        formToShow.setProOne(formVm.getAccUsage1());
        formToShow.setProOneStr(formVm.getUsage1Name());
        formToShow.setProTwo(formVm.getAccUsage2());
        formToShow.setProTwoStr(formVm.getUsage2Name());
        formToShow.setRemark(formVm.getRemark());
        return formToShow;
    }

    public String getStrOfPeriod(String period){
        String periodStr;
        switch (period){
            case "01":
                periodStr = "早上";
                break;
            case "02":
                periodStr = "上午";
                break;
            case "03":
                periodStr = "中午";
                break;
            case "04":
                periodStr = "下午";
                break;
            case "05":
                periodStr = "傍晚";
                break;
            case "06":
                periodStr = "晚上";
                break;
            case "07":
                periodStr = "午夜";
                break;
            default:
                periodStr = "";
                break;
        }
        return periodStr;
    }
}
