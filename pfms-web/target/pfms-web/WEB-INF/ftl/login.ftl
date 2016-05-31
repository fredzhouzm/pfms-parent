<#import "spring.ftl" as spring/>
<html lang="zh-CN">
<head>
    <title><@spring.message 'login_head'/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
    <style type="text/css">
        body {
            background: #808080 url(../images/login.jpg) no-repeat fixed 0px 0px;
        }

        div#loginDiv {
            padding: 20px;
            background-color: #FFFFFF;
            opacity: 1.0;
            position: absolute;
            left: 50%;
            top: 50%;
            margin: -220px 0 0 -250px;
            width: 500px;
        }
    </style>
</head>
<body>
<div id="loginDiv">
    <form action="login.htm" method="post">
        <div style="color: #ff0000" class="alert alert-error">
        <@spring.bind "user.loginName"/><@spring.showErrors ""/>
                <@spring.bind "user.password"/><@spring.showErrors ""/>
        </div>
        <div class="form-group">
            <label for="loginID"><@spring.message 'text.userLoginName'/></label>
            <input type="text" value="${user.loginName}" class="form-control" name="loginName" id="loginID"
                   placeholder="<@spring.message 'plahdr.userLoginName'/>" required autofocus>
        </div>
        <div class="form-group">
            <label for="loginPWD"><@spring.message 'text.userPassword'/></label>
            <input type="password" value="${(user.password)}" class="form-control" name="password" id="loginPWD"
                   placeholder="<@spring.message 'plahdr.userPassword'/>" required>
        </div>
        <button id="login" type="submit"
                class="btn btn-lg btn-primary btn-block"><@spring.message 'button.login'/></button>
        <br/>
        <button id="register" type="button" class="btn btn-lg btn-primary btn-block"
                onclick="redirect()"><@spring.message 'button.register'/></button>
        <br/>
        <p align="center"><@spring.message 'text.copyRight'/></p>
    </form>
</div>
</body>
<script type="text/javascript" src="../JavaScript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../JavaScript/bootstrap.min.js"></script>
<script type="text/javascript">
    function redirect() {
        window.location = "register.htm";
    }
</script>
</html>

