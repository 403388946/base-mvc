<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sys" uri="http://www.sys.com/sys" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="authTags" uri="http://com.auth.lib.tags" %>
<sys:extends name="cssList">
    <link href="${ctx}/assets/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/bootstrapValidator.min.css" rel="stylesheet">
</sys:extends>
<sys:extends name="contentTitle">
    角色管理
</sys:extends>
<sys:extends name="contentList">
    <div class="row">
        <div class="col-sm-7 b-r">
            <form id="role_edit" role="form" action="${ctx}/role/save" class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-3 control-label">角色名称:</label>
                    <div class="col-sm-8">
                        <input placeholder="填写角色名称" id="name" name="name" class="form-control" type="text">
                    </div>
                </div>
                <div class="text-center">
                    <button class="btn btn-primary" type="reset">重置</button>
                    <button class="btn btn-primary" type="submit">确认</button>
                </div>
            </form>
        </div>
        <div class="col-sm-5">
        </div>
    </div>
    <div class="row">
        <div class="fixed-table-toolbar" id="role-toolbar">
            <div class="form-inline" role="form" id="search_div">
                <div class="pull-left search">
                    <input type="text" class="form-control input-outline" id="role_" name="role_" placeholder="角色名称" />
                </div>
                <div class="columns columns-right btn-group pull-right">
                    <button type="button" id="g_search" class="btn btn-primary btn-outline">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        查询
                    </button>
                    <button type="button" id="g_reset" class="btn btn-primary btn-outline">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        清空查询
                    </button>
                </div>
            </div>
        </div>
        <tags:table id="roles"/>
        <div id="operations_role" style="display: none">
            <a class="role-edit" href="javascript:void(0)">编辑权限</a>
        </div>
        <div class="modal inmodal" id="source_list" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content animated bounceInRight">
                    <div class="modal-header">
                        关联菜单
                    </div>
                    <div class="modal-body">
                        <div id="sourceTree"></div>
                    </div>
                    <div class="modal-footer">
                        <input id="roleId" type="hidden">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="submitResource()">
                            提交
                        </button>
                        <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</sys:extends>

<sys:extends name="scriptList">
    <script type="text/javascript" src="${ctx}/assets/js/bootstrapValidator.min.js"></script>
    <script src="${ctx}/assets/js/plugins/jsTree/jstree.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/role/list.js"></script>
    <script type="text/javascript">
        var operations_role = $('#operations_role').html();
    </script>
</sys:extends>
<jsp:include page="${ctx}/common/BaseList.jsp"/>
