<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="userName"><shiro:principal /></c:set>
<sys:SelectUserName username="${userName}"/>