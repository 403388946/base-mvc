<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<sys:extends name="cssList">
</sys:extends>
<sys:extends name="contentTitle">
    基础信息
</sys:extends>
<sys:extends name="contentList">
    <form id="userForm" action="${ctx}/user/save" class="form-horizontal">
        <div class="form-group">
            <div class="row">
                <div class="col-md-12">
                    <label class="col-sm-2 control-label"><span style="color: red;">*</span>登录名:</label>
                    <div class="col-sm-10">
                        <input type="hidden" name="id" value="${user.id}">
                        <input placeholder="填写登录名,请保证不重复" name="username" value="${user.username}" id="username" class="form-control" type="text">
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group ${user.id > 0 ? 'hidden' : ''}">
            <div class="row">
                <div class="col-md-12">
                    <label class="col-sm-2 control-label">密码:</label>
                    <div class="col-sm-10">
                        <input placeholder="填写密码,不修改或重置密码可不填" name="password" id="password" class="form-control" type="text">
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <div class="col-md-12">
                    <label class="col-sm-2 control-label">姓名:</label>
                    <div class="col-sm-10">
                        <input placeholder="填写姓名" name="realName" id="realName" value="${user.realName}" class="form-control" type="text">
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <div class="col-md-12">
                    <label class="col-sm-2 control-label"><span style="color: red;">*</span>手机号:</label>
                    <div class="col-sm-10">
                        <input placeholder="填写手机号" name="telephone" value="${user.telephone}" id="telephone" class="form-control" type="text">
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <div class="col-md-12">
                    <label class="col-sm-2 control-label"><span style="color: red;">*</span>角色:</label>
                    <div class="col-sm-10">
                        <select name="roleId" id="roleId" class="form-control">
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}" ${role.id == user.roleId ? 'selected' : ''}>${role.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <div class="text-center">
                <button type="submit" class="btn btn-primary">保存</button>
                <button type="button" onclick="cancel()" class="btn btn-white layui-layer-close">取消</button>
            </div>
        </div>
    </form>
</sys:extends>

<sys:extends name="scriptList">
    <script type="text/javascript" src="${ctx}/assets/js/plugins/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/plugins/layer/laydate/laydate.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/shiro/user/list.js"></script>
    <script type="text/javascript">
        $(function(){
            $('.chosen-select').chosen({});
        });
        function cancel() {
            parent.layer.close(parent.layer.index);
        }
    </script>

</sys:extends>
<jsp:include page="${ctx}/common/BaseList.jsp"/>
