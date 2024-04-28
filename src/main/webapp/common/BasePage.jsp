<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" uri="http://com.auth.lib.tags" %>
<%@ taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="referrer" content="never">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>XXXXX</title>
    <link rel="shortcut icon" href="${ctx}/static/img/logo.png" type="x-icon">
    <link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/font-awesome.min93e3.css" rel="stylesheet">
    <link href="${ctx}/assets/css/animate.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/style.min.css" rel="stylesheet">
    <link href="${ctx}/assets/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <sys:block name="style"></sys:block>
    <script type="text/javascript">
        var ctx_ = "${ctx}" + '/';
    </script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--头开始-->
        <jsp:include page="/common/header.jsp"/>
        <!--主体-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <%--<jsp:include page="message.jsp"/>--%>
            <jsp:include page="excutor.jsp"/>
            <!--内容-->
            <div class="row J_mainContent" id="content-main">
                <sys:block name="body"></sys:block>
            </div>
        </div>
    </div>
    <script src="${ctx}/assets/js/jquery.min.js"></script>
    <script src="${ctx}/assets/js/bootstrap.min.js"></script>
    <script src="${ctx}/assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/assets/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/assets/js/plugins/layer/layer.min.js"></script>
    <script src="${ctx}/assets/js/hplus.min.js"></script>
    <script src="${ctx}/assets/js/contabs.min.js"></script>
    <script src="${ctx}/assets/js/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/assets/js/plugins/gritter/jquery.gritter.min.js"></script>
    <script src="${ctx}/static/js/common.js"></script>
    <!--主体-->
    <sys:block name="script"></sys:block>
</body>
</html>