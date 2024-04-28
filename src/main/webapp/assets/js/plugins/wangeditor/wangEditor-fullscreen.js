window.wangEditor.fullscreen = {
    // editor create之后调用
    init: function(editorSelector){
        $(editorSelector + " .w-e-toolbar").append('<div class="w-e-menu"><a class="_wangEditor_btn_fullscreen" href="###" onclick="window.wangEditor.fullscreen.toggleFullscreen(\'' + editorSelector + '\')">全屏</a></div>');
    },
    toggleFullscreen: function(editorSelector){
        $(editorSelector).toggleClass('fullscreen-editor');
        if($(editorSelector + ' ._wangEditor_btn_fullscreen').text() == '全屏'){
            $(editorSelector + ' ._wangEditor_btn_fullscreen').text('退出全屏');
            if (window.wangEditor.registedEditor && window.wangEditor.registedEditor.length > 0) {
                window.wangEditor.registedEditor.forEach(function (groupSelector) {
                    if ($(groupSelector).find(editorSelector).length == 0){
                        $(groupSelector).css('display', 'none')
                    }
                })
            }
        }else{
            $(editorSelector + ' ._wangEditor_btn_fullscreen').text('全屏');
            window.wangEditor.registedEditor.forEach(function (groupSelector) {
                $(groupSelector).css('display', 'block');
            })
        }
    }
};
window.wangEditor.registedEditor = [];