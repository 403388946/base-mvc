//异步无登录处理
$.ajaxSetup({
    complete:function(XMLHttpRequest,status){
        //如果使用jquery原始请求返回json会解析报错,需要将对象转成字符串
        var action = String(XMLHttpRequest.responseText).indexOf('index_login');
        if(action > 0){
            alertShow('登陆超时！请重新登陆！');
            window.location.reload(true);
        }
    }
});

//加入 contentType: "application/json" 后台需要@RequestBody
function post(url, param, callback) {
    $.ajax({
        url: ctx_ + url,
        async: false,
        type : 'POST',
        data : param,
        dataType:'json',
        success : callback
    });
}

function get(url, param, callback) {
    $.ajax({
        url: ctx_ + url,
        async: false,
        type : 'GET',
        data : param,
        dataType:'json',
        success : callback
    });
}

function getAsync(url, param, callback) {
    $.ajax({
        url: ctx_ + url,
        async: true,
        type : 'GET',
        data : param,
        dataType:'json',
        success : callback
    });
}

function confirmShow(title, url, param, callback) {
    bootbox.confirm({
            message: title,
            buttons: {
                confirm: {
                    label: "确认",
                    className: "btn-primary btn-sm",
                },
                cancel: {
                    label: "取消",
                    className: "btn-primary btn-sm",
                }
            },
            callback: function(result) {
                if(result) {
                    post(url, param, callback)
                }
            }
        }
    );
}

function confirmShowGet(title, url, param, callback) {
    bootbox.confirm({
            message: title,
            buttons: {
                confirm: {
                    label: "确认",
                    className: "btn-primary btn-sm",
                },
                cancel: {
                    label: "取消",
                    className: "btn-primary btn-sm",
                }
            },
            callback: function(result) {
                if(result) {
                    get(url, param, callback)
                }
            }
        }
    );
}


function alertShow(content, theme, title) {
    if(!title) {
        title = '提醒';
    }
    if(!theme) {
        theme = 'info';//success/warning/error
    }
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "7000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    toastr[theme](content, title);
}


//查询 offset 当前页 tableId 不要使用下划线 必须在parent.layer.close前才有效
function searchToolBar(tableId, isCurrent){
    var options = $("#" + tableId).bootstrapTable('getOptions');
    var toolBarId = options.toolbar;
    var url = options.url;
    var paramMap = {};
    $(toolBarId + ' input').each(function() {
        var $this = $(this);
        var $name = $this.attr('name');
        if(!!$name && $name.indexOf('_') > -1) {
            var $val = $this.val(), $name = $name.replace('_','');
            if(!!$val) {
                paramMap[$name] = $val;
            }
        }
    });
    if(isCurrent && options.pageNumber > 1) {
        $("#" + tableId).bootstrapTable('selectPage',options.pageNumber);
    } else {
        $("#" + tableId).bootstrapTable('refresh',{url:url, query:paramMap});
    }
}

function getQueryParams(params, tableId) {
    var options = $("#" + tableId).bootstrapTable('getOptions');
    var toolBarId = options.toolbar;
    var paramMap = {limit : params.limit, offset : params.offset, banned: params.banned};
    $(toolBarId + ' input').each(function(){
        var $this = $(this);
        var $name = $this.attr('name');
        if(!!$name && $name.indexOf('_') > -1) {
            var $val = $this.val(), $name = $name.replace('_','');
            if(!!$val) {
                paramMap[$name] = $val;
            }
        }
    });
    $(toolBarId + ' select option:selected').each(function () {
        var $this = $(this);
        var $name = $this.parent('select').attr('name');
        if(!!$name && $name.indexOf('_') > -1) {
            var $val = $this.val(), $name = $name.replace('_','');
            if(!!$val) {
                paramMap[$name] = $val;
            }
        }
    });
    $("#checkAll").attr("checked", false);
    return paramMap;
}

function queryParams(params, tableId) {
    var options = $("#" + tableId).bootstrapTable('getOptions');
    var toolBarId = options.toolbar;
    $(toolBarId + ' input').each(function(){
        var $this = $(this);
        var $name = $this.attr('name');
        if(!!$name && $name.indexOf('_') > -1) {
            var $val = $this.val(), $name = $name.replace('_','');
            if(!!$val) {
                params[$name] = $val;
            }
        }
    });
    $("#checkAll").attr("checked", false);
    return params;
}

function queryInitParams(params, toolBarId) {
    $(toolBarId + ' input').each(function(){
        var $this = $(this);
        var $name = $this.attr('name');
        if(!!$name && $name.indexOf('_') > -1) {
            var $val = $this.val(), $name = $name.replace('_','');
            if(!!$val) {
                params[$name] = $val;
            }
        }
    });
    $("#checkAll").attr("checked", false);
    return params;
}

function getQueryParamsByForm(params, formId) {
    var options = $("#" + formId).serialize();
    options += '&limit=' + params.limit;
    options += '&offset=' + params.offset;
    return options;
}
//去编辑
function toolEdit(url) {
    $('#main_view').load(ctx_ + url);
}


function checkNumber(dom) {
    var thisValue = parseFloat($(dom).val()).toFixed(2);
    if(!/^\d+(\.\d+)?$/.test(thisValue)) {
        alertShow('请输入数字');
        $(dom).val(0)
    }
}

function loadVideo(url, img, type) {
    $('#showList').modal('show');
    if(type == 1) {
        $("#_img_").hide();
        $("#_html_").hide();
        $("#_video_").show();
        // 初始化播放器
        var player = videojs("_video_");
        player.ready(function(){
            player.width(500);
            player.height(282);
            player.src(url);
            player.volume(0.3);
            player.play();
            player.on('dblclick',function(){
                if(player.isFullscreen_){
                    player.exitFullscreen()
                } else {
                    player.requestFullscreen()
                }
            })
        });
    } else if(type == 2 || type == 3) {
        $("#_html_").attr("src", url);
        $("#_img_").hide();
        $("#_video_").hide();
        $("#_html_").show();
    } else {
        $("#_img_").attr("src", img);
        $("#_html_").hide();
        $("#_video_").hide();
        $("#_img_").show();
    }
}

/**
 * param 将要转为URL参数字符串的对象
 * key URL参数字符串的前缀
 * encode true/false 是否进行URL编码,默认为true
 *
 * return URL参数字符串
 */
var urlEncode = function (param, key, encode) {
    if(param==null) return '';
    var paramStr = '';
    var t = typeof (param);
    if (t == 'string' || t == 'number' || t == 'boolean') {
        paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
    } else {
        for (var i in param) {
            var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : i);
            paramStr += urlEncode(param[i], k, encode);
        }
    }

    return paramStr;
};

function dateFormat(date, fmt) {
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

function uploader(path, targetId, preview) {
    var uploader;
    if (!uploader) {
        uploader = WebUploader.create({
            auto: true,
            // swf文件路径
            swf: ctx_ + '/assets/js/plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: ctx_ + '/image/upload?path=' + path,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#image_target_' + targetId,
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        //预览
        if(!('false' == preview)) {
            uploader.on('fileQueued', function(file) {
                var $img = $('#preview_' + targetId);
                $('#preview_list_' + targetId).append($img);
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
                $('#' + targetId).val(result)
            }
        });
    }
    return uploader;
}