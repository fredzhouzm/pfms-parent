/**
 * Created by Fred on 2016/1/2.
 */
$(document).ready(function () {

    var contentHeightNum_1 = $(window).height() - 105;
    $(".content").css("min-height", contentHeightNum_1 + "px");

    $('tr[id^="incomeOne"]').addClass('firstForm');
    $('tr[id^="incomeTwo"]').addClass('secondForm').hide();
    $('tr[id^="expendOne"]').addClass('firstForm');
    $('tr[id^="expendTwo"]').addClass('secondForm').hide();
    $('#block_incomeTab').show().siblings('div').hide();
    $("#nav_tab_expire").css({'cursor': 'pointer'});
    $('a[disabled=disabled]').hide();

    $('body').on('click', 'li[id^="nav_tab_"]', function () {
        if ($(this).hasClass('active')) {
            $(this).siblings().removeClass('active').css({'cursor': 'pointer'});
        }
        else {
            $(this).addClass('active').siblings().removeClass('active').css({'cursor': 'pointer'});
        }
        var tabId = $(this).attr('id');
        if (tabId == 'nav_tab_income') {
            $('#block_incomeTab').show().siblings('div').hide();
        }
        else if (tabId == 'nav_tab_expire') {
            $('#block_expendTab').show().siblings('div').hide();
        }
    });

    //收入一级菜单的前三个TD触发二级菜单的隐藏和显示
    $('body').on('click', 'tr[id^="incomeOne"]>td:even', function () {
        var id_pre = $(this).closest('tr').attr('id');
        $('tr[id^="incomeTwo"]').each(function () {
            var id_two = $(this).attr('parent');
            if (id_pre == id_two) {
                $(this).toggle();
            }
            else {
                $(this).hide();
            }
        })
    });
    $('body').on('click', 'tr[id^="incomeOne"]>td:odd:even', function () {
        var id_pre = $(this).closest('tr').attr('id');
        $('tr[id^="incomeTwo"]').each(function () {
            var id_two = $(this).attr('parent');
            if (id_pre == id_two) {
                $(this).toggle();
            }
            else {
                $(this).hide();
            }
        })
    });
    //支出一级菜单的前三个TD触发二级菜单的隐藏和显示
    $('body').on('click', 'tr[id^="expendOne"]>td:even', function () {
        var id_pre = $(this).closest('tr').attr('id');
        $('tr[id^="expendTwo"]').each(function () {
            var id_two = $(this).attr('parent');
            if (id_pre == id_two) {
                $(this).toggle();
            }
            else {
                $(this).hide();
            }
        })
    });
    $('body').on('click', 'tr[id^="expendOne"]>td:odd:even', function () {
        var id_pre = $(this).closest('tr').attr('id');
        $('tr[id^="expendTwo"]').each(function () {
            var id_two = $(this).attr('parent');
            if (id_pre == id_two) {
                $(this).toggle();
            }
            else {
                $(this).hide();
            }
        })
    });

    $('#modifyPanelProOne').on('show.bs.modal', function (event) {
        var modal = $(this);
        var button = $(event.relatedTarget);// Button that triggered the modal
        var proId = button.data('proid');// Extract info from data-* attributes
        var level = button.data('level');
        var proName;
        var jsonObj={
            id:proId
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: "application/json",
            url: "/setting/getProOneName.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var name = data.optname;
                if(status == "success"){
                    proName = name;
                    modal.find('#proOneIdForModify').val(proId);
                    modal.find("#proOneLevelForModify").val(level);
                    modal.find('#proOneNameModify').val(proName);
                }else{
                    return false;
                }
            }
        });
    });

    //修改一级项目名称的提交按钮触发动作
    $('body').on('click','#proOneNameModifyBtn',function(){
        var proOneNameModifyTmp = $('#proOneNameModify').val();
        if (typeof(proOneNameModifyTmp) == 'undefined' || proOneNameModifyTmp == '' || $.trim(proOneNameModifyTmp) == '') {
            return false;
        }
        var jsonObj = {
            proId: $('#proOneIdForModify').val(),
            proNameModify: $('#proOneNameModify').val()
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: 'application/json',
            url: '/setting/proOneModify.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var id = data.opid;
                var name = data.opname;
                if (status == 'success') {
                    if (type == '1') {
                        var trId = 'incomeOne' + id;
                        $('tr[id^=incomeOne]').each(function () {
                            var tr_id = $(this).attr('id');
                            if (tr_id == trId) {
                                $(this).children().eq(0).html(name);

                            }
                        })
                    }
                    else {
                        var trId = 'expendOne' + id;
                        $('tr[id^=expendOne]').each(function () {
                            var tr_id = $(this).attr('id');
                            if (tr_id == trId) {
                                $(this).children().eq(0).html(name);
                            }
                        })
                    }
                    $('#modifyPanelProOne').modal('hide');
                }
                else {
                    $('#modifyPanelProOne').modal('hide');
                    alert('修改项目名称失败!');
                }
            }
        })
    });

    $('#modifyPanelTwo').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);// Button that triggered the modal
        var proId = button.data('proid');// Extract info from data-* attributes
        var level = button.data('level');
        var name = button.data('name');
        var modal = $(this);
        modal.find('#proIdForModify').val(proId);
        modal.find("#proLevelForModify").val(level);
        modal.find('#proNameModify').val(name);
    });

    $('#addProOnePanel').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);// Button that triggered the modal
        var type = button.data('type');// Extract info from data-* attributes
        var modal = $(this);
        if (type == '1') {
            modal.find('#addProOnePanelhead').text('增加一级项目-收入');
        }
        else {
            modal.find('#addProOnePanelhead').text('增加一级项目-支出');
        }
        modal.find('#proOneNameAddType').val(type);
    });

    //新增一级项目时的提交按钮触发动作
    $('body').on('click', '#proOneNameAddBtn', function () {
        var proOneNameAddTmp = $('#proOneNameAdd').val();
        if (typeof(proOneNameAddTmp) == 'undefined' || proOneNameAddTmp == '' || $.trim(proOneNameAddTmp) == '') {
            return false;
        }
        var jsonObject = {
            proOneType: $('#proOneNameAddType').val(),
            proOneNameAdd: $('#proOneNameAdd').val()
        };
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: 'application/json',
            url: '/setting/addProOne.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var id = data.opid;
                var name = data.opname;
                var realMmount = data.oprealMmount;
                var budgetMmount = data.opbudgetMmount;
                if (status == 'success') {
                    if (type == '1') {
                        var html = '<tr id="incomeOne' + id + '" class="firstForm"><td>' + name + '</td><td></td><td>' + realMmount +'&nbsp;/&nbsp;'+ budgetMmount + '</td><td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProOne" data-proid="' + id + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '1DelBtn" data-level="1"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>';
                        html += '<tr id="incomeTwo' + id + 'btn" parent="incomeOne' + id + '" style="display: none;"><td></td><td><a class="btn btn-info" id="addIncome' + id + 'ProTwo" data-toggle="modal" data-target="#addProTwoPanel" data-type="1" data-parentid="' + id + '" data-parentname="' + name + '"><i class="icon-plus-sign-alt icon-large"></i>&nbsp;&nbsp;新增二级项目</a></td><td></td><td></td></tr>';
                        $('tr[id=incomeOneAddBtn]').before(html);
                    }
                    else {
                        var html = '<tr id="expendOne' + id + '" class="firstForm"><td>' + name + '</td><td></td><td>' + realMmount +'&nbsp;/&nbsp;'+ budgetMmount + '</td><td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanelProOne" data-proid="' + id + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '1DelBtn" data-level="1"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>';
                        html += '<tr id="expendTwo' + id + 'btn" parent="expendOne' + id + '" style="display: none;"><td></td><td><a class="btn btn-info" id="addExpend' + id + 'ProTwo" data-toggle="modal" data-target="#addProTwoPanel" data-type="2" data-parentid="' + id + '" data-parentname="' + name + '"><i class="icon-plus-sign-alt icon-large"></i>&nbsp;&nbsp;新增二级项目</a></td><td></td><td></td></tr>';
                        $('tr[id=expendOneAddBtn]').before(html);
                    }
                    $('#addProOnePanel').modal('hide');
                    $('#proOneNameAdd').val('');
                }
                else {
                    $('#addProOnePanel').modal('hide');
                    $('#proOneNameAdd').val('');
                    alert('新增一级项目失败!');
                }
            }
        })
    });

    $('#addProTwoPanel').on('show.bs.modal', function (event) {
        var modal = $(this);
        var button = $(event.relatedTarget);// Button that triggered the modal
        var type = button.data('type');// Extract info from data-* attributes
        var parentid = button.data('parentid');
        var jsonObj={
            id:parentid
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: "application/json",
            url: "/setting/getProOneName.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var name = data.optname;
                if(status == "success"){
                    if (type == '1') {
                        modal.find('#addProTwoPanelhead').text('增加二级项目-收入');
                    }
                    else {
                        modal.find('#addProTwoPanelhead').text('增加二级项目-支出');
                    }
                    modal.find('#proOneId').val(parentid);
                    modal.find('#proOneNameAddforProTwo').val(name);
                    modal.find('#proTwoType').val(type);
                }else{
                    return false;
                }
            }
        });

    });

    //新增二级项目时的提交按钮触发动作
    $('body').on('click','#proTwoAddBtn',function () {
        var proTwoNameAddTmp = $('#proTwoNameAdd').val();
        var proTwoBudgetAddTmp = $('#proTwobudgetAdd').val();
        if (typeof(proTwoNameAddTmp) == 'undefined' || proTwoNameAddTmp == '' || $.trim(proTwoNameAddTmp) == '') {
            return false;
        }
        if (typeof(proTwoBudgetAddTmp) == 'undefined' || proTwoBudgetAddTmp == '' || $.trim(proTwoBudgetAddTmp) == '') {
            return false;
        }
        var jsonObject = {
            proOneId: $('#proOneId').val(),
            proTwoType: $('#proTwoType').val(),
            proTwoNameAdd: $('#proTwoNameAdd').val(),
            proTwoBudgetAdd:$('#proTwobudgetAdd').val()
        };
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: 'application/json',
            url: '/setting/addProTwo.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var pid = data.opparentid;
                var id = data.opid;
                var name = data.opname;
                var pbamount = data.opparentbudgetamount;
                var pramount = data.opparentrealamount;
                var bamount = data.opbudgetamount;
                var ramount = data.oprealamount;
                if (status == 'success') {
                    if (type == '1') {
                        var html = '<tr id="incomeTwo' + id + '" parent="incomeOne' + pid + '"><td></td><td>' + name + '</td><td>' + amount + '</td>';
                        html += '<td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanel" data-proid="' + id + '" data-level="2" data-name="' + name + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '2DelBtn"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>'
                        var btnId = 'incomeTwo' + pid + 'btn';
                        $('tr[id^=incomeTwo][id$=btn]').each(function () {
                            var id_two_btn = $(this).attr("id");
                            if (btnId == id_two_btn) {
                                $(this).before(html);
                            }
                        })
                        $('#incomeOne' + pid).find('a').eq(1).hide();
                    }
                    else {
                        var html = '<tr id="expendTwo' + id + '" parent="expendOne' + pid + '"><td></td><td>' + name + '</td><td>' + amount + '</td>';
                        html += '<td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanel" data-proid="' + id + '" data-level="2" data-name="' + name + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '2DelBtn"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>'
                        var btnId = 'expendTwo' + pid + 'btn';
                        $('tr[id^=expendTwo][id$=btn]').each(function () {
                            var id_two_btn = $(this).attr("id");
                            if (btnId == id_two_btn) {
                                $(this).before(html);
                            }
                        })
                        $('#expendOne' + pid).find('a').eq(1).hide();
                    }
                    $('#addProTwoPanel').modal('hide');
                    $('#proTwoNameAdd').val('');
                }
                else {
                    $('#addProTwoPanel').modal('hide');
                    $('#proTwoNameAdd').val('');
                    alert('新增二级项目失败!');
                }
            }
        })
    });

    $('#deletePanel').on('show.bs.modal', function (event) {
        var obj = $(event.relatedTarget);// Obj that triggered the modal
        var proid = obj.data('id');
        var father = obj.parent().parent();
        var idlength = proid.length;
        var proidtr = proid.substring(0, idlength - 7);
        var name;
        var level = proid.substring(idlength - 7, idlength - 6);
        if (level == '1') {
            name = father.children().eq(0).text();
        }
        else {
            level = '2';
            name = father.children().eq(1).text();
        }
        var modal = $(this);
        modal.find('#proIdforDelete').val(proidtr);
        modal.find("#proLevelforDelete").val(level);
        modal.find('#proNameforDelete').val(name);
    });



    //新增二级项目时的提交按钮触发动作
    $('body').on('click', '#proTwoNameAddBtn', function () {
        var proTwoNameAddTmp = $('#proTwoNameAdd').val();
        if (typeof(proTwoNameAddTmp) == 'undefined' || proTwoNameAddTmp == '' || $.trim(proTwoNameAddTmp) == '') {
            return false;
        }
        var jsonObject = {
            proTwoNameAdd: $('#proTwoNameAdd').val(),
            proTwoType: $('#proTwoNameType').val(),
            proOneId: $('#proOneId').val()
        };
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: 'application/json',
            url: '/setting/addProTwo.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var pid = data.opparentid;
                var id = data.opid;
                var name = data.opname;
                var amount = data.amount;
                if (status == 'success') {
                    if (type == '1') {
                        var html = '<tr id="incomeTwo' + id + '" parent="incomeOne' + pid + '"><td></td><td>' + name + '</td><td>' + amount + '</td>';
                        html += '<td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanel" data-proid="' + id + '" data-level="2" data-name="' + name + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '2DelBtn"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>'
                        var btnId = 'incomeTwo' + pid + 'btn';
                        $('tr[id^=incomeTwo][id$=btn]').each(function () {
                            var id_two_btn = $(this).attr("id");
                            if (btnId == id_two_btn) {
                                $(this).before(html);
                            }
                        })
                        $('#incomeOne' + pid).find('a').eq(1).hide();
                    }
                    else {
                        var html = '<tr id="expendTwo' + id + '" parent="expendOne' + pid + '"><td></td><td>' + name + '</td><td>' + amount + '</td>';
                        html += '<td><a class="btn btn-default btn-xs" data-toggle="modal" data-target="#modifyPanel" data-proid="' + id + '" data-level="2" data-name="' + name + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deletePanel" data-id="' + id + '2DelBtn"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a></td></tr>'
                        var btnId = 'expendTwo' + pid + 'btn';
                        $('tr[id^=expendTwo][id$=btn]').each(function () {
                            var id_two_btn = $(this).attr("id");
                            if (btnId == id_two_btn) {
                                $(this).before(html);
                            }
                        })
                        $('#expendOne' + pid).find('a').eq(1).hide();
                    }
                    $('#addProTwoPanel').modal('hide');
                    $('#proTwoNameAdd').val('');
                }
                else {
                    $('#addProTwoPanel').modal('hide');
                    $('#proTwoNameAdd').val('');
                    alert('新增二级项目失败!');
                }
            }
        })
    });

    //修改项目时的修改按钮触发动作
    $('body').on('click', '#proNameModifyBtn', function () {
        var proNameModifyTmp = $('#proNameModify').val();
        if (typeof(proNameModifyTmp) == 'undefined' || proNameModifyTmp == '' || $.trim(proNameModifyTmp) == '') {
            return false;
        }
        var jsonObj = {
            proId: $('#proIdForModify').val(),
            level: $('#proLevelForModify').val(),
            proNameModify: $('#proNameModify').val()
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: 'application/json',
            url: '/setting/proModify.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var level = data.oplvl;
                var id = data.opid;
                var name = data.opname;
                if (status == 'success') {
                    if (type == '1') {
                        if (level == '1') {
                            var trId = 'incomeOne' + id;
                            $('tr[id^=incomeOne]').each(function () {
                                var tr_id = $(this).attr('id');
                                if (tr_id == trId) {
                                    $(this).children().eq(0).html(name);

                                }
                            })
                        }
                        else {
                            var trId = 'incomeTwo' + id;
                            $('tr[id^=incomeTwo]').each(function () {
                                var tr_id = $(this).attr('id');
                                if (tr_id == trId) {
                                    $(this).children().eq(1).html(name);
                                }
                            })
                        }
                    }
                    else {
                        if (level == '1') {
                            var trId = 'expendOne' + id;
                            $('tr[id^=expendOne]').each(function () {
                                var tr_id = $(this).attr('id');
                                if (tr_id == trId) {
                                    $(this).children().eq(0).html(name);
                                }
                            })
                        }
                        else {
                            var trId = 'expendTwo' + id;
                            $('tr[id^=expendTwo]').each(function () {
                                var tr_id = $(this).attr('id');
                                if (tr_id == trId) {
                                    $(this).children().eq(1).html(name);
                                }
                            })
                        }
                    }
                    $('#modifyPanel').modal('hide');
                }
                else {
                    $('#modifyPanel').modal('hide');
                    alert('修改项目名称失败!');
                }
            }
        })
    });

    //删除项目时的删除按钮出发动作
    $('body').on('click', '#proNameDeleteBtn', function () {
        var jsonObj = {
            proId: $('#proIdforDelete').val(),
            level: $('#proLevelforDelete').val()
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: "application/json",
            url: "/setting/proDelete.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var type = data.optype;
                var level = data.oplvl;
                var id = data.opid;
                if (status == "success") {
                    if (type == '1') {
                        if (level == '1') {
                            var trId = 'incomeOne' + id;
                            $('tr[parent^=' + trId + ']').remove();
                            $('#' + trId).remove();
                        } else {
                            var trId = 'incomeTwo' + id;

                            var parentid = $('#incomeTwo' + id).attr('parent');
                            $('#' + trId).remove();
                            var num = $('tr[parent=' + parentid + ']').length;
                            if ($('tr[parent=' + parentid + ']').length == 1) {
                                $('#' + parentid).find('a').eq(1).show().attr("disabled", false);
                            }
                        }
                    }
                    else {
                        if (level == '1') {
                            var trId = 'expendOne' + id;
                            $('tr[parent^=' + trId + ']').remove();
                            $('#' + trId).remove();
                        } else {
                            var trId = 'expendTwo' + id;
                            $('#' + trId).remove();
                        }
                    }
                    $('#deletePanel').modal('hide');
                }
                else {
                    $('#deletePanel').modal('hide');
                    alert("项目删除操作失败！ ")
                }
            }
        })

    })
    //
    function getProOneName(id, proName){
        var jsonObj={
            id:id
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: "application/json",
            url: "/setting/getProOneName.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var name = data.optname;
                if(status == "success"){
                    proName = name;
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    function getProTwoNameAndBudget(id, proInfo){
        var jsonObj={
            id:id
        };
        var jsonString = JSON.stringify(jsonObj);
        $.ajax({
            contentType: "application/json",
            url: "/setting/getProTwoNameAndBudget.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var name = data.optname;
                var budget = data.optbudget;
                if(status == "success"){
                    proInfo = {'name':name,'budget':budget};
                    return true;
                }else{
                    return false;
                }
            }
        });
    }
});
