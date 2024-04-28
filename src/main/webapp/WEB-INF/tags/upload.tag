<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" uri="http://www.sys.com/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ attribute name="targetId" required="true"%>
<%@ attribute name="path" required="true"%>
<%@ attribute name="clazz" required="false"%>
<%@ attribute name="preview" required="true"%>
<sys:extends name="cssTag">
    <link href="${ctx}/assets/css/plugins/webuploader/webuploader.css" rel="stylesheet">
</sys:extends>
<div id="image_target" class="${clazz}">选择文件</div>
<div id="preview_list" class="uploader-list">
    <img id="preview_" src="" style="width:100%">
</div>

<sys:extends name="scriptTag">
    <script src="${ctx}/assets/js/plugins/webuploader/webuploader.min.js"></script>
    <script type="text/javascript">
        var uploader = WebUploader.create({
            auto: true,
            // swf文件路径
            swf: ctx_ + '/assets/js/plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: ctx_ + '/image/upload?path=${path}',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#image_target',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        //预览
        if(!('false' == '${preview}')) {
            uploader.on('fileQueued', function(file) {
                var $img = $('#preview_');
                $('#preview_list').append($img);
                uploader.makeThumb( file, function( error, src ) {
                    if (error) {
                        $img.replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $img.attr('src', src);
                });
            });
        }

        uploader.on('uploadSuccess', function(file, result) {
            if(!!result) {
                $('#' + '${targetId}').val(result)
            }
        });
        function refresh() {
            uploader.refresh();
        }
    </script>
</sys:extends>
