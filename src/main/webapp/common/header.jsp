<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header" id="manager-ico">
                <div class="dropdown profile-element">
                    <span><img alt="image" class="img-circle" src="${ctx}/static/img/logo.png" /></span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0)">
                        <span class="clear">
                            <span class="block m-t-xs"><strong class="font-bold">欢迎,${realName}</strong></span>
                        </span>
                    </a>
                </div>
            </li>
            <jsp:include page="left.jsp"/>
        </ul>
    </div>
</nav>
<!--左侧导航结束-->