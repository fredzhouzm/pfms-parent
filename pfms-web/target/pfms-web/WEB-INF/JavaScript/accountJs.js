/**
 * Created by Fred on 2016/1/20.
 */
$(document).ready(function () {
    var contentHeightNum_1 = $(window).height() - 105;
    $(".content").css("min-height", contentHeightNum_1 + "px");

    $("#persentationIncome").addClass("active btn-success");
    tabSwitch(0);

    $("body").on("click", "#persentationIncome", function () {
        tabSwitch(0);
    });

    $("body").on("click", "#persentationExpend", function () {
        tabSwitch(1);
    });

    $("#incomeProOne").change(function () {
        var proOneId = $(this).val();
        if (proOneId != undefined && proOneId != "default") {
            getNewProTwoData(proOneId);
        }
        else {
            $('#ProTwo').empty().attr("disabled", true);
        }
    });

    $("#expendProOne").change(function () {
        var proOneId = $(this).val();
        if (proOneId != undefined && proOneId != "default") {
            getNewProTwoData(proOneId);
        }
        else {
            $('#ProTwo').empty().attr("disabled", true);
        }
    });

    $('body').on('click', '#incomeSmtBtn', function () {
        $('#incomeSmtBtn').attr("disabled", "true");
        var type = '1';
        var amount = $("#amount").val();
        var proOneId = $('#incomeProOne').val();
        var proTwoId = $("#ProTwo").val();
        var date = $("#accountDate").val();
        var peroid = $("#accountPeriod").val();
        var remark = $("#remark").val();
        submitForm(type, amount, proOneId, proTwoId, date, peroid, remark);
        $('#incomeSmtBtn').removeAttr("disabled");

    });

    $('body').on('click', '#expendSmtBtn', function () {
        $('#expendSmtBtn').attr("disabled", "true");
        var type = '2';
        var amount = $("#amount").val();
        var proOneId = $('#expendProOne').val();
        var proTwoId = $("#ProTwo").val();
        var date = $("#accountDate").val();
        var peroid = $("#accountPeriod").val();
        var remark = $("#remark").val();
        submitForm(type, amount, proOneId, proTwoId, date, peroid, remark);
        $('#expendSmtBtn').removeAttr("disabled");
    });

    $('body').on('click','#monthSearchBtn',function(){
        $('#monthSearchBtn').attr("disabled","true");
        var month = $('#searchMonth').val();
        switchMonth(month);
        $('#monthSearchBtn').removeAttr("disabled");
    })

    function tabSwitch(index) {

        $("#amount").val("").focus();
        $("#incomeProOne").val("default");
        $("#expendProOne").val("default");
        $("#ProTwo").empty();
        $("#accountDate").val("");
        $("#accountPeriod").val("00");
        $("#remark").val("");
        $("#expendSmtBtnDiv").hide();
        $("#incomeSmtBtnDiv").hide();
        $("#persentationIncome").removeClass("active btn-success");
        $("#persentationExpend").removeClass("active btn-danger");
        $("#incomeProOneDiv").hide();
        $("#expendProOneDiv").hide();

        if (index != undefined && index == "0") {
            $("#incomeProOneDiv").show();
            $("#incomeSmtBtnDiv").show();
            $("#persentationIncome").addClass("active btn-success");
        }
        else {
            $("#expendProOneDiv").show();
            $("#expendSmtBtnDiv").show();
            $("#persentationExpend").addClass("active btn-danger");
        }
    };

    function getNewProTwoData(proOneId) {
        var jsonObject = {
            proOneId: proOneId
        }
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: "application/json",
            url: "/account/getProTwoList.json",
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var map = data.opmap;
                if (status == "success") {
                    createSelectOptions(map);
                }
                else {
                    alert("获取二级科目失败");
                }
            },
        })
    };

    function createSelectOptions(data) {
        var myOptions = '<option value="default">请选择…</option>';
        var count = 0;
        for (var o in data) {
            ++count;
            myOptions += '<option value="' + o + '">' + data[o] + '</option>';
        }
        if (count > 0) {
            $('#ProTwo').empty().html(myOptions).attr("disabled", false);
        }
    };

    function submitForm(type, amount, proOneId, proTwoId, date, peroid, remark) {
        var jsonObject = {
            type: type,
            amount: amount,
            proOneId: proOneId,
            proTwoId: proTwoId,
            date: date,
            peroid: peroid,
            remark: remark
        };
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: 'application/json',
            url: '/account/insertForm.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data) {
                var status = data.opstatus;
                var id = data.opid;
                var type = data.optype;
                var amount = data.opamount;
                var date = data.opdate;
                var period = data.opperiod;
                var pro = data.oppro;
                var remark = data.opremark;
                var month = data.opselectedMonth;
                var totalAmountIn = data.optotalAmountIn;
                var totalAmountOut = data.optotalAmountOut;
                if (status == "success") {
                     var pageMonth = $("#searchMonth").val();
                    if (month == pageMonth) {
                        totalHtml = '<tr id=' + id + '><td>';
                        typeHtml_in = '<span class="label label-success">收入</span>';
                        typeHtml_out = '<span class="label label-danger">支出</span>';

                        if (type == "1") {
                            totalHtml += typeHtml_in;
                        }
                        else {
                            totalHtml += typeHtml_out;
                        }
                        totalHtml += "</td><td>" + amount + "</td><td>" + date + "&nbsp;" + period + "</td><td>" + pro + "</td><td>" + remark + "</td>";
                        totalHtml += '<td><a class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                        totalHtml += '<a class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>';

                        $('#titleTr').after(totalHtml);
                        $('#totalamountin').html(totalAmountIn);
                        $('#totalamountout').html(totalAmountOut);
                    }
                }
                else {
                    alert("单据创建失败!");
                }
                clearInput();
            }
        });
    }

    function switchMonth(month) {
        var jsonObject={
            newMonth:month
        }
        var jsonString = JSON.stringify(jsonObject);
        $.ajax({
            contentType: 'application/json',
            url: '/account/switchMonth.json',
            type: 'POST',
            data: jsonString,
            dataType: 'json',
            success: function (data){
                var status = data.opstatus;
                var year = data.opcurrentYear;
                var month = data.opcurrentMonth;
                var totalAmountIn = data.optotalAmountIn;
                var totalAmountOut = data.optotalAmountOut;
                var hasData = data.opsearchResult;
                var dataList = data.opformList;
                if(status == "success"){
                    $('#searchMonth').val(year+month);
                    $('.titleYear').html(year);
                    $('.titleMonth').html(month);
                    $('#totalamountin').html(totalAmountIn);
                    $('#totalamountout').html(totalAmountOut);
                    $('table tr:gt(0)').remove();
                    if(hasData == 'yes'){
                        var totalHtml = '';
                        for(var i=0;i<dataList.length;i++){
                            totalHtml += '<tr><td>';
                            if(dataList[i].type == "1"){
                                totalHtml += '<span class="label label-success">收入</span>';
                            }
                            else{
                                totalHtml += '<span class="label label-danger">支出</span>';
                            }
                            totalHtml += '</td>';
                            totalHtml += '<td>'+dataList[i].amount+'</td>';
                            totalHtml += '<td>'+dataList[i].valueDateStr+'&nbsp;'+dataList[i].peroidStr+'</td>';
                            totalHtml += '<td>'+dataList[i].proOneStr+'-'+dataList[i].proTwoStr+'</td>';
                            totalHtml += '<td>'+dataList[i].remark+'</td>';
                            totalHtml += '<td>';
                            totalHtml += '<a class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                            totalHtml += '<a class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>';
                            totalHtml += '</td></tr>';
                        }
                        $('table tr:eq(0)').after(totalHtml);
                    }
                    else{
                        totalHtml = '<tr><td colspan="6" class="noDataInfo">当月没有任何数据!</td></tr>';
                        $('table tr:eq(0)').after(totalHtml);
                    }

                }
            }
        })
    }
    function clearInput(){
        $("#amount").val("");
        $('#incomeProOne').val("default");
        $('#expendProOne').val("default");
        $('#ProTwo').empty().attr("disabled", true);
        $("#accountDate").val("");
        $("#accountPeriod").val("");
        $("#remark").val("");
    }

})