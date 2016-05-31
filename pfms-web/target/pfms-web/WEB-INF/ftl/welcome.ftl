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
<@template.head "001"/>
<div class="content">
    <h1 align="center">欢迎！</h1>
</div>
<@template.foot/>
</body>
<script src="../JavaScript/jquery-2.1.4.min.js"></script>
<script src="../JavaScript/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var contentHeightNum_1 = $(window).height() - 105;
        var contentHeightNum_2 = $(".content").height();
        var contentHeightNum = contentHeightNum_1 > contentHeightNum_2 ? contentHeightNum_1 : contentHeightNum_2;
        $(".content").css("height", contentHeightNum + "px");
    });
</script>
</html>