<#import "spring.ftl" as spring/>
<#import "template.ftl" as template/>
﻿<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>PFMS Welcome Page</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../css/main.css" type="text/css">
</head>
<body>
<@template.head "002"/>
<div class="content">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="btn-group" role="group">
                <button id="persentationIncome" type="button" class="btn btn-default">收入</button>
                <button id="persentationExpend" type="button" class="btn btn-default">支出</button>
            </div>
            <p/>
            <form class="form-inline" id="orderForm">
                <div class="form-group">
                    <label for="amount">金额</label>
                    <input type="text" class="form-control" id="amount" placeholder="0.00">
                </div>
                <div id="incomeProOneDiv" class="form-group">
                    &nbsp;&nbsp;
                    <label for="incomeProOne">一级科目</label>
                    <select id="incomeProOne" class="form-control" style="width: 200px;">
                        <option value="default">请选择…</option>
                    <#if incomeProOneList?? && incomeProOneList?size gt 0>
                        <#list incomeProOneList?keys as key>
                            <option value="${key}">${incomeProOneList[key]!""}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
                <div id="expendProOneDiv" class="form-group">
                    &nbsp;&nbsp;
                    <label for="expendProOne">一级科目</label>
                    <select id="expendProOne" class="form-control" style="width: 200px;">
                        <option value="default">请选择…</option>
                    <#if expendProOneList?? && expendProOneList?size gt 0>
                        <#list expendProOneList?keys as key>
                            <option value="${key}">${expendProOneList[key]!""}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
                <div id="ProTwoDiv" class="form-group">
                    &nbsp;&nbsp;
                    <label for="ProTwo">二级科目</label>
                    <select id="ProTwo" class="form-control" disabled style="width: 200px;">
                    </select>
                </div>
                <div class="form-group">
                    &nbsp;&nbsp;
                    <label for="accountDate">日期</label>
                    <div id="datetimepicker" class="input-group date form_date">
                        <input type="text" class="form-control" id="accountDate" placeholder="YYYY-MM-DD" readonly
                               required></input>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
                <p/>
                <div class="form-group">
                    <label for="accountPeriod">时段</label>
                    <select id="accountPeriod" class="form-control" style="width: 173px;">
                        <option value="00">请选择…</option>
                        <option value="01">早上</option>
                        <option value="02">上午</option>
                        <option value="03">中午</option>
                        <option value="04">下午</option>
                        <option value="05">傍晚</option>
                        <option value="06">晚上</option>
                        <option value="07">午夜</option>
                    </select>
                </div>
                <div class="form-group">
                    &nbsp;&nbsp;
                    <label for="remark">备注</label>
                    <input type="text" class="form-control" id="remark" placeholder="好记性不如烂笔头……" style="width: 500px;">
                </div>
                <div class="form-group" id="incomeSmtBtnDiv">
                    &nbsp;&nbsp;
                    <button type="button" class="btn btn-success" id="incomeSmtBtn" style="width: 285px;">提交(收入)
                    </button>
                </div>
                <div class="form-group" id="expendSmtBtnDiv">
                    &nbsp;&nbsp;
                    <button type="button" class="btn btn-danger" id="expendSmtBtn" style="width: 285px;">提交(支出)</button>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" id="detailHeading">
            <div class="col-lg-3">
                <div class="input-group">
                    <input type="text" id="searchMonth" value="${currentYear}${currentMonth}" class="form-control" placeholder="YYYYMM">
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="monthSearchBtn" type="button">SEARCH</button>
                    </span>
                </div>
            </div>
            <div class="col-lg-5" id="detailTitle">
                <strong class="titleYear">${currentYear}</strong>年<strong class="titleMonth">${currentMonth}</strong>月份的记账数据
            </div>
            <div class="col-lg-4" id="detailMoney">
                <p id="moneyin"><span class="glyphicon glyphicon-plus"></span><span id="totalamountin">${totalAmountIn}</span>
                </p>&nbsp;&nbsp;&nbsp;&nbsp;
                <p id="moneyout"><span class="glyphicon glyphicon-minus"></span><span id="totalamountout">${totalAmountOut}</span>
                </p>
            </div>
        </div>
        <table class="table" id="formList">
            <tr id="titleTr">
                <th>记账种类</th>
                <th>金额</th>
                <th>时间</th>
                <th>科目</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            <#list formList as form>
            <tr id="${form.id}">
                <td>
                    <#if form.type=="1"><span class="label label-success">收入</span>
                    <#else><span class="label label-danger">支出</span>
                    </#if>
                </td>
                <td>${form.amount}</td>
                <td>${form.valueDateStr}&nbsp;${form.peroidStr}</td>
                <td>${form.proOneStr}&nbsp;-&nbsp;${form.proTwoStr}</td>
                <td>${form.remark}</td>
                <td>
                    <a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanel" data-id="${form.id}" data-type="${form.type}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="${form.id}" data-type="${form.type}"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>
                </td>
            </tr>
            </#list>
        </table>
    </div>
</div>
<@template.foot/>
<!-- moda_1 -->
<div class="modal fade"  id="modifyPanel" tabindex="-1" role="dialog" aria-labelledby="modifyPanelHead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modifyPanelHead"><@spring.message 'text.modifyProName'/></h4>
            </div>
            <div class="modal-body">
                <form id="modifyOrder" name="modifyOrder">
                    <input id="orderIdForModify" name="orderIdForModify" type="hidden"/>
                    <input id="orderTypeForModify" name="orderTypeForModify" type="hidden"/>
                    <div class="form-group">
                        <label for="amountForModify">金额:</label>
                        <input type="text" name="amountForModify" id="amountForModify" class="form-control" tabindex="1">
                    </div>
                    <div class="form-group" id="divForIncomeProOneForModify">
                        <label for="incomeProOneForModify">一级科目:</label>
                        <select id="incomeProOneForModify" class="form-control" tabindex="2">
                            <option value="default">请选择…</option>
                        <#if incomeProOneList?? && incomeProOneList?size gt 0>
                            <#list incomeProOneList?keys as key>
                                <option value="${key}">${incomeProOneList[key]!""}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                    <div class="form-group" id="divForExpendProOneForModify">
                        <label for="expendProOneForModify">一级科目:</label>
                        <select id="expendProOneForModify" class="form-control" tabindex="2">
                            <option value="default">请选择…</option>
                            <#if expendProOneList?? && expendProOneList?size gt 0>
                                <#list expendProOneList?keys as key>
                                    <option value="${key}">${expendProOneList[key]!""}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="proTwoForModify">二级科目:</label>
                        <select id="proTwoForModify" class="form-control" tabindex="3">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="accountDateForModify">日期:</label>
                        <div id="datetimepicker" class="input-group date form_date">
                            <input type="text" class="form-control" id="accountDateForModify" placeholder="YYYY-MM-DD" readonly required tabindex="4"></input>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="accountPeriodForModify">时段:</label>
                        <select id="accountPeriodForModify" class="form-control" tabindex="5">
                            <option value="00">请选择…</option>
                            <option value="01">早上</option>
                            <option value="02">上午</option>
                            <option value="03">中午</option>
                            <option value="04">下午</option>
                            <option value="05">傍晚</option>
                            <option value="06">晚上</option>
                            <option value="07">午夜</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="remarkForModify">备注:</label>
                        <input type="text" class="form-control" id="remarkForModify" tabindex="6">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="orderForModifyBtn" id="orderForModifyBtn"><@spring.message 'button.modify'/></button>
            </div>
        </div>
    </div>
</div>
<!-- modal_2 -->
<div class="modal fade"  id="deletePanel" tabindex="-1" role="dialog" aria-labelledby="deletePanelHead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deletePanelHead"><@spring.message 'text.modifyProName'/></h4>
            </div>
            <div class="modal-body">
                <form id="deleteOrder" name="deleteOrder">
                    <input id="orderIdForDelete" name="orderIdForDelete" type="hidden"/>
                    <input id="orderTypeForDelete" name="orderTypeForDelete" type="hidden"/>
                    <p id="deleteMessage" class="h4">是否确定删除此单据？</p>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="orderForDeleteBtn" id="orderForDeleteBtn"><@spring.message 'button.delete'/></button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="../JavaScript/jquery-2.1.4.min.js"></script>
<script src="../JavaScript/bootstrap.min.js"></script>
<script src="../JavaScript/accountJs.js"></script>
<script type="text/javascript" src="../JavaScript/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../JavaScript/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
    $(".form_date").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        minView: 2,
        autoClose: true,
        todayHighlight: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        language: 'zh-CN'
    });
</script>
</html>