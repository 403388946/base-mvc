<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="id" required="true"%>
<%@ attribute name="clazz" required="false"%>
<table id="${id}" class="${not empty clazz ? clazz : 'table table-bordered'}">
</table>