<#import "spring.ftl" as spring/>
<html lang="zh-CN">
<head>
    <title><@spring.message 'register_head'/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta charset="UTF-8">
    <style type="text/css">
        body {
            background: #808080 url(../images/login.jpg) no-repeat fixed 0px 0px;
        }

        div#registerDiv {
            padding: 20px;
            background-color: #FFFFFF;
            opacity: 1.0;
            position: absolute;
            left: 50%;
            top: 50%;
            margin: -250px 0 0 -250px;
            width: 500px;
        }
    </style>
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css" type="text/css">
</head>
<body>
<div id="registerDiv">
    <form class="form-horizontal" action="registerCheck.htm" method="post">
        <div style="color: #ff0000" class="alert alert-error">
        <@spring.bind "user.loginName"/><@spring.showErrors ""/>
            <@spring.bind "user.password"/><@spring.showErrors ""/>
            <@spring.bind "user.nickName"/><@spring.showErrors ""/>
            <@spring.bind "user.gender"/><@spring.showErrors ""/>
            <@spring.bind "user.birthdate"/><@spring.showErrors ""/>
            <@spring.bind "user.email"/><@spring.showErrors ""/>
        </div>
        <div class="form-group">
            <label for="registerID" class="col-sm-4 control-label"><@spring.message 'text.userLoginName'/>
                &nbsp;*</label>
            <div class="col-sm-8">
                <input type="text" value="${(user.loginName)}" class="form-control" name="loginName" id="registerID"
                       placeholder="<@spring.message 'plahdr.userLoginName'/>" required autofocus>
            </div>
        </div>
        <div class="form-group">
            <label for="registerPWD" class="col-sm-4 control-label"><@spring.message 'text.userPassword'/>
                &nbsp;*</label>
            <div class="col-sm-8">
                <input type="password" value="${(user.password)}" class="form-control" name="password" id="registerPWD"
                       placeholder="<@spring.message 'plahdr.userPassword'/>" required onpaste="return false"
                       oncontextmenu="return false" oncopy="return false" oncut="return false">
            </div>
        </div>
        <div class="form-group">
            <label for="registerPWD" class="col-sm-4 control-label"><@spring.message 'text.userPasswordConfirm'/>
                &nbsp;*</label>
            <div class="col-sm-8">
                <input type="password" class="form-control" name="passwordConfirm" id="registerPWDConfirm"
                       placeholder="<@spring.message 'plahdr.userPasswordConfirm'/>" required onpaste="return false"
                       oncontextmenu="return false" oncopy="return false" oncut="return false">
            </div>
        </div>
        <div class="form-group">
            <label for="registerName" class="col-sm-4 control-label"><@spring.message 'text.nickName'/>&nbsp;*</label>
            <div class="col-sm-8">
                <input type="text" value="${(user.nickName)}" class="form-control" name="nickName" id="registerNickname"
                       placeholder="<@spring.message 'plahdr.nickName'/>" required>
            </div>
        </div>
        <div class="form-group">
            <label for="registerName" class="col-sm-4 control-label"><@spring.message 'text.gender'/>&nbsp;*</label>
            <div class="col-sm-8">
                <label class="radio-inline">
                    <input type="radio" name="gender" id="gender1" value="1"
                           <#if "${(user.gender)}"=="1">checked</#if>> <@spring.message 'text.gender.male'/>
                </label>
                <label class="radio-inline">
                    <input type="radio" name="gender" id="gender2" value="2"
                           <#if "${(user.gender)}"=="2">checked</#if>> <@spring.message 'text.gender.female'/>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label for="registerBirthdate" class="col-sm-4 control-label"><@spring.message 'text.birthdate'/>
                &nbsp;*</label>
            <div class="col-sm-8">
                <div calss="form-group">
                    <div id="datetimepicker" class="input-group date form_date">
                        <input type="text" value="${(user.birthdate)}" class="form-control" name="birthdate"
                               id="registerBirthdate" placeholder="<@spring.message 'plahdr.birthDate'/>" readonly
                               required></input>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="registerEmail" class="col-sm-4 control-label"><@spring.message 'text.email'/></label>
            <div class="col-sm-8">
                <input type="email" value="${(user.email)}" class="form-control" name="email" id="registerEmail"
                       placeholder="<@spring.message 'plahdr.email'/>" required>
            </div>
        </div>
        <button id="login" type="submit"
                class="btn btn-lg btn-primary btn-block"><@spring.message 'button.register'/></button>
        <br/>
        <p align="center"><@spring.message 'text.copyRight'/></p>
    </form>
</div>
</body>
<script type="text/javascript" src="../JavaScript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../JavaScript/bootstrap.min.js"></script>
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