<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<sys:extends name="cssList">
</sys:extends>
<sys:extends name="contentTitle">
    人员管理
</sys:extends>
<sys:extends name="contentList">
    <div class="row">
        <div class="fixed-table-toolbar" id="user-toolbar">
            <div class="form-inline" role="form" id="search_div">
                <div class="pull-left search">
                    <input type="text" class="form-control" id="userName_" name="userName_" placeholder="请输入登录名">
                </div>
                <div class="columns columns-right btn-group pull-left">
                    <button type="button" id="g_search" class="btn btn-primary btn-outline btn-margin">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        查询
                    </button>
                    <button type="button" id="g_reset" class="btn btn-primary btn-outline btn-margin">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        清空
                    </button>
                    <button type="button" id="g_edit" class="btn btn-primary btn-outline btn-margin">
                        <span class="glyphicon glyphicon-edit"aria-hidden="true"></span>
                        添加
                    </button>
                </div>
            </div>
        </div>
        <tags:table id="users"/>
    </div>

    <div id="operations_user" style="display: none">
        <a class="user-edit" href="javascript:void(0)">编辑</a>
    </div>

</sys:extends>

<sys:extends name="scriptList">
    <script type="text/javascript" src="${ctx}/assets/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/plugins/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/shiro/user/list.js"></script>
    <script type="text/javascript">
        var operations_user = $('#operations_user').html();
    </script>
</sys:extends>
<jsp:include page="${ctx}/common/BaseList.jsp"/>
