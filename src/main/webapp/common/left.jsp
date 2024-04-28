<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:forEach items="${menus}" var="menu">
    <li>
        <c:set var="falg" value="${menu.children.size() > 0}"/>
        <a href="${ctx}${menu.url}" data-id="${menu.id}" <c:if test="${!falg}" >class="J_menuItem"</c:if>>
            <i class="fa fa-home"></i>
            <span class="nav-label">${menu.name}</span>
            <c:if test="${falg}" >
                <span class="fa arrow"></span>
            </c:if>

        </a>
        <c:if test="${falg}" >
            <ul class="nav nav-second-level">
                <c:forEach items="${menu.children}" var="child">
                    <li>
                        <a class="J_menuItem" onclick="bindMenuTab(this)" href="${ctx}${child.url}" data-index="${child.id}">${child.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </li>
</c:forEach>


