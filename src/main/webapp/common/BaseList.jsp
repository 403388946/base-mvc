<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sys" uri="http://www.sys.com/sys" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta name="referrer" content="never">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>XXXXX</title>
    <link href="${ctx}/assets/css/bootstrap.min14ed.css" rel="stylesheet">
    <link href="${ctx}/assets/css/font-awesome.min93e3.css" rel="stylesheet">
    <link href="${ctx}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/bootstrapValidator.min.css" />
    <link href="${ctx}/assets/css/animate.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/style.min862f.css" rel="stylesheet">
    <link href="${ctx}/assets/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <sys:block name="cssList">

    </sys:block>
    <sys:block name="cssTag">

    </sys:block>
</head>
<body class="gray-bg">
    <div id="wrapper wrapper-content animated">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>
                    <sys:block name="contentTitle">

                    </sys:block>
                </h5>
            </div>
            <div class="ibox-content">
                <sys:block name="contentList">

                </sys:block>
            </div>
        </div>
    </div>
    <script src="${ctx}/assets/js/jquery.min.js"></script>
    <script src="${ctx}/assets/js/bootstrap.min.js"></script>
    <script src="${ctx}/assets/js/content.min.js"></script>
    <script src="${ctx}/assets/js/bootbox.js"></script>
    <script src="${ctx}/assets/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${ctx}/assets/js/bootstrapValidator.min.js"></script>
    <script src="${ctx}/assets/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/assets/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/assets/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ctx}/assets/js/video.min.js"></script>
    <script src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript">
        var ctx_ = "${ctx}";
        $.validator.addMethod("isInteger",function(value,element,regex){
            return regex.test(value);
        },"必须是一个非负整数");
    </script>
    <sys:block name="scriptList">

    </sys:block>
    <sys:block name="scriptTag">

    </sys:block>
</body>
</html>

