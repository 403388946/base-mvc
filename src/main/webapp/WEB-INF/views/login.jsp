<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>XXXXX管理后台</title>
    <link rel="shortcut icon" href="${ctx}/static/img/logo.png" type="x-icon">
    <link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/font-awesome.min93e3.css" rel="stylesheet">
    <link href="${ctx}/assets/css/animate.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/style.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/login.min.css" rel="stylesheet">
    <script>
        if(window.top!==window.self){window.top.location=window.location};
    </script>

</head>

<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>XXXXX管理后台</h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>管理后台</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>XXXXX</li>
                    </ul>
                    <strong style="color: red">${error}</strong>
                </div>
            </div>
            <div class="col-sm-5">
                <form method="post" action="${ctx}/login">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">登录到管理后台</p>
                    <input type="text" name="username" class="form-control uname" placeholder="用户名" />
                    <input type="password" name="password" class="form-control pword m-b" placeholder="密码" />
                    <button class="btn btn-success btn-block">登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                XXXXX管理后台
            </div>
        </div>
    </div>
</body>
</html>
