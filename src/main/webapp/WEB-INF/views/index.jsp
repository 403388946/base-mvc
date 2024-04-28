<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://www.sys.com/sys" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<sys:extends name="body">
    <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src=""
            frameborder="0" data-id="" seamless></iframe>
</sys:extends>
<sys:extends name="script">
    <script type="text/javascript">
        function bindMenuTab(dom) {
            $('.J_menuItem').on('click', function() {
                var dataId = $(dom).attr('href');
                var src = $('.J_iframe[data-id="' + dataId + '"]').attr('src');
                $('.J_iframe[data-id="' + dataId + '"]').attr('src', src);
            });
        }
        $(function() {
            $('.J_menuTab.active').on('click', function() {
                var dataId = $(this).attr('data-id');
                var src = $('.J_iframe[data-id="' + dataId + '"]').attr('src');
                $('.J_iframe[data-id="' + dataId + '"]').attr('src', src);
            });
        });
    </script>
</sys:extends>
<jsp:include page="/common/BasePage.jsp"/>
