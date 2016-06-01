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
<@template.head "999"/>
<div class="content">
    <div class="proset">
        <div>
            <ul class="nav nav-tabs">
                <li role="presentation" class="active" id="nav_tab_income">
                    <a><@spring.message 'nav.settings.income'/></a></li>
                <li role="presentation" id="nav_tab_expire"><a><@spring.message 'nav.settings.expire'/></a></li>
            </ul>
        </div>
        <div>
            <div id="block_incomeTab">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th width="225px"><@spring.message 'nav.settings.proOne'/></th>
                        <th width="350px"><@spring.message 'nav.settings.proTwo'/></th>
                        <th width="185px"><@spring.message 'nav.settings.proAmount'/></th>
                        <th width="240px"><@spring.message 'nav.settings.operation'/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if (incomeNode?size)!=0>
                        <#list incomeNode?keys as key>
                        <tr id="incomeOne${key}">
                            <td>${incomeNode[key].projectName}</td>
                            <td></td>
                            <td>${incomeNode[key].realAmountByMonth}&nbsp;/&nbsp;${incomeNode[key].projectMonthBudget}</td>
                            <td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProOne" data-proid="${key}" data-level="1">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <@spring.message 'button.modify'/>
                                </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-proid="${key}" data-level="1" <#if (incomeNode[key].levelTwoProjectList?size)!=0>disabled="disabled"</#if>>
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    <@spring.message 'button.delete'/>
                                </a>
                            </td>
                        </tr>
                            <#if (incomeNode[key].levelTwoProjectList?size)!=0>
                                <#list incomeNode[key].levelTwoProjectList as levelTwoProject>
                                <tr id="incomeTwo${levelTwoProject.projectID}" parent="incomeOne${key}">
                                    <td></td>
                                    <td>${levelTwoProject.projectName}</td>
                                    <td>${levelTwoProject.realAmountByMonth}&nbsp;/&nbsp;${levelTwoProject.projectMonthBudget}</td>
                                    <td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProTwo" data-proid="${levelTwoProject.projectID}" data-level="2">
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            <@spring.message 'button.modify'/>
                                        </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-proid="${levelTwoProject.projectID}" data-level="2">
                                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                            <@spring.message 'button.delete'/>
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                        <tr id="incomeTwo${key}btn" parent="incomeOne${key}">
                            <td></td>
                            <td><a class="btn btn-info" id="addIncome${key}ProTwo" data-toggle="modal" data-target="#addProTwoPanel" data-type="1" data-parentid="${key}">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    <@spring.message 'text.addProTwo'/>
                                </a>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        </#list>
                    </#if>
                    <tr id="incomeOneAddBtn">
                        <td colspan="4" align="center">
                            <a class="btn btn-info" id="addIncomeProOne" data-toggle="modal" data-target="#addProOnePanel" data-type="1">
                                <i class="icon-plus-sign-alt icon-large"></i>&nbsp;&nbsp;
                                <@spring.message 'text.addProOne'/>
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="block_expendTab">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th width="225px"><@spring.message 'nav.settings.proOne'/></th>
                        <th width="350px"><@spring.message 'nav.settings.proTwo'/></th>
                        <th width="185px"><@spring.message 'nav.settings.proAmount'/></th>
                        <th width="240px"><@spring.message 'nav.settings.operation'/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if (expendNode?size)!=0>
                        <#list expendNode?keys as key>
                        <tr id="expendOne${key}">
                            <td>${expendNode[key].projectName}</td>
                            <td></td>
                            <td>${expendNode[key].realAmountByMonth}&nbsp;/&nbsp;${expendNode[key].projectMonthBudget}</td>
                            <td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProOne" data-proid="${key}" data-level="1">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <@spring.message 'button.modify'/>
                                </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-proid="${key}" data-level="1" <#if (expendNode[key].levelTwoProjectList?size)!=0>disabled="disabled"</#if>>
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    <@spring.message 'button.delete'/>
                                </a>
                            </td>
                        </tr>
                            <#if (expendNode[key].levelTwoProjectList?size)!=0>
                                <#list expendNode[key].levelTwoProjectList as levelTwoProject>
                                <tr id="expendTwo${levelTwoProject.projectID}" parent="expendOne${key}">
                                    <td></td>
                                    <td>${levelTwoProject.projectName}</td>
                                    <td>${levelTwoProject.realAmountByMonth}&nbsp;/&nbsp;${levelTwoProject.projectMonthBudget}</td>
                                    <td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProTwo" data-proid="${levelTwoProject.projectID}" data-level="2">
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            <@spring.message 'button.modify'/>
                                        </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-proid="${levelTwoProject.projectID}" data-level="2">
                                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                            <@spring.message 'button.delete'/>
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                        <tr id="expendTwo${key}btn" parent="expendOne${key}">
                            <td></td>
                            <td><a class="btn btn-info" id="addExpend${key}ProTwo" data-toggle="modal" data-target="#addProTwoPanel" data-type="2" data-parentid="${key}">
                                    <i class="icon-plus-sign-alt icon-large"></i>&nbsp;&nbsp;
                                    <@spring.message 'text.addProTwo'/>
                                </a>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        </#list>
                    </#if>
                    <tr id="expendOneAddBtn">
                        <td colspan="4" align="center">
                            <a class="btn btn-info" id="addExpendProOne" data-toggle="modal" data-target="#addProOnePanel" data-type="2">
                                <i class="icon-plus-sign-alt icon-large"></i>&nbsp;&nbsp;
                                <@spring.message 'text.addProOne'/>
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<@template.foot/>
<!-- Modal 1-->
<div class="modal fade" id="modifyPanelProOne" tabindex="-1" role="dialog" aria-labelledby="modifyPanelProOnehead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modifyPanelProOnehead"><@spring.message 'text.modifyProName'/></h4>
            </div>
            <div class="modal-body">
                <form id="modifyProOne" name="modifyProOne">
                    <input id="proOneIdForModify" name="proOneIdForModify" type="hidden"/>
                    <div class="form-group">
                        <label for="proOneNameModify"><@spring.message 'text.proName'/>:</label>
                        <input type="text" name="proOneNameModify" id="proOneNameModify" class="form-control" tabindex="1">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="proOneNameModifyBtn"
                        id="proOneNameModifyBtn"><@spring.message 'button.modify'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal 2-->
<div class="modal fade" id="modifyPanelProTwo" tabindex="-1" role="dialog" aria-labelledby="modifyPanelProTwohead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modifyPanelProTwohead"><@spring.message 'text.modifyProName'/></h4>
            </div>
            <div class="modal-body">
                <form id="modifyProTwo" name="modifyProTwo">
                    <input id="proTwoIdForModify" name="proTwoIdForModify" type="hidden"/>
                    <input id="proTwoLevelForModify" name="proTwoLevelForModify" type="hidden"/>
                    <div class="form-group">
                        <label for="proTwoNameModify"><@spring.message 'text.proName'/>:</label>
                        <input type="text" name="proTwoNameModify" id="proTwoNameModify" class="form-control" tabindex="1">
                    </div>
                    <div class="form-group">
                        <label for="proTwoBudgetModify"><@spring.message 'text.budget'/>:</label>
                        <input type="text" name="proTwoBudgetModify" id="proTwoBudgetModify" class="form-control" tabindex="2">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="proTwoModifyBtn"
                        id="proTwoModifyBtn"><@spring.message 'button.modify'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal 3-->
<div class="modal fade" id="addProOnePanel" tabindex="-1" role="dialog" aria-labelledby="addProOnePanelhead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addProOnePanelhead"><@spring.message 'text.addProOne'/></h4>
            </div>
            <div class="modal-body">
                <form id="addProOne" name="addProOne">
                    <input id="proOneNameAddType" name="proOneNameAddType" type="hidden"/>
                    <div class="form-group">
                        <label for="proOneNameAdd"><@spring.message 'nav.settings.proOne'/>:</label>
                        <input type="text" name="proOneNameAdd" id="proOneNameAdd" class="form-control" tabindex="1">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="proOneNameAddBtn"
                        id="proOneNameAddBtn"><@spring.message 'button.submit'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal 4-->
<div class="modal fade" id="addProTwoPanel" tabindex="-1" role="dialog" aria-labelledby="addProTwoPanelhead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addProTwoPanelhead"><@spring.message 'text.addProTwo'/></h4>
            </div>
            <div class="modal-body">
                <form id="addProTwo" name="addProTwo">
                    <input id="proTwoType" name="proTwoType" type="hidden"/>
                    <input id="proOneId" name="proOneId" type="hidden"/>
                    <div class="form-group">
                        <label for="proOneNameAddforProTwo"><@spring.message 'nav.settings.proOne'/>:</label>
                        <input type="text" name="proOneNameAddforProTwo" id="proOneNameAddforProTwo"
                               class="form-control" readonly>
                    </div>
                    <div class="form-group">
                        <label for="proTwoNameAdd"><@spring.message 'nav.settings.proTwo'/>:</label>
                        <input type="text" name="proTwoNameAdd" id="proTwoNameAdd" class="form-control" tabindex="1">
                    </div>
                    <div class="form-group">
                        <label for="proTwobudgetAdd"><@spring.message 'text.budget'/>:</label>
                        <input type="text" name="proTwobudgetAdd" id="proTwobudgetAdd" class="form-control" tabindex="2">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="proTwoAddBtn"
                        id="proTwoAddBtn"><@spring.message 'button.submit'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal 5-->
<div class="modal fade" id="deletePanel" tabindex="-1" role="dialog" aria-labelledby="deletePanelhead">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deletePanellhead"><@spring.message 'text.deletePro'/></h4>
            </div>
            <div class="modal-body">
                <form id="deleteName" name="deleteName">
                    <input id="proIdforDelete" name="proIdforDelete" type="hidden"/>
                    <input id="proLevelforDelete" name="proLevelforDelete" type="hidden"/>
                    <div class="form-group">
                        <label for="proNameforDelete"><@spring.message 'text.proName'/>:</label>
                        <input type="text" name="proNameforDelete" id="proNameforDelete" class="form-control" readonly/>
                    </div>
                    <div class="form-group">
                        <label for="proBudgetforDelete"><@spring.message 'text.budget'/>:</label>
                        <input type="text" name="proBudgetforDelete" id="proBudgetforDelete" class="form-control" readonly/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal"><@spring.message 'button.cancel'/></button>
                <button type="button" class="btn btn-primary" name="proNameDeleteBtn"
                        id="proNameDeleteBtn"><@spring.message 'button.delete'/></button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="../JavaScript/jquery-2.1.4.min.js"></script>
<script src="../JavaScript/bootstrap.min.js"></script>
<script src="../JavaScript/normal.js"></script>
</html>