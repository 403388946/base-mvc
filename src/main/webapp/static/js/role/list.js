var $url = ctx_ + "/role/findData";
$(function () {
    $('#role_edit').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            }
        },
        submitHandler: function(validator, form, submitButton) {
            post(form.attr('action'), form.serialize(), function(result) {
                alertShow(result.message);
                if(result.status == 1) {
                    searchToolBar('roles');
                }
            });
            $(form).bootstrapValidator('disableSubmitButtons', false);
        }
    });
    //初始化表格
    $('#roles').bootstrapTable({
        method: 'get',
        contentType: "json",
        url: $url,
        cache : false,
        pagination: true,
        singleSelect: false,
        pageSize: 10,
        pageList: [10, 50],
        sidePagination: "server", //服务端请求
        toolbar : '#role-toolbar',
        columns: [
            {field: 'roleId', checkbox: true},
            {field: 'name', title: '名称', align: 'center',valign: 'middle',sortable: true},
            {field: 'operation', title: '操作',align: 'center',valign: 'middle',sortable: true,
                events: 'operateEvents',formatter: function(value,row,index){
                return operations_role;
            }}
        ],
        queryParams: function (params) {
            return getQueryParams(params, 'roles');
        },
        onLoadError: function () {alertShow("数据加载失败！");}
    });
    //重置
    $("#g_reset").bind("click", function(){
        $('#role_').val('');
    });
    //查询
    $("#g_search").bind("click", function(){
        searchToolBar('roles');
    });

    window.operateEvents = {
        'click .role-edit' : function(e, value, row, index){
            $('#source_list').modal('show');
            $('#roleId').val(row.id);
            $.jstree.destroy ();
            post(ctx_ + '/role/findResource',{'id' : row.id}, function (tree) {
                $('#sourceTree').jstree({
                    "core" : {
                        "animation" : 0,
                        "themes" : { "default" : true },
                        'data' : tree
                    },
                    "plugins":["checkbox"]
                });
            });
        }
    };
});
function submitResource() {
    var roleId = $('#roleId').val();
    var ids = "";
    console.log($('#sourceTree').find('li[aria-selected=true]'));
    $('#sourceTree').find('li[aria-selected=true]').each(function () {
            var id = $(this).attr('id');
            ids += id + ",";
    });
    post(ctx_ + '/role/updateResource', {roleId : roleId, ids : ids}, function (flag) {
        if(flag) {
            alertShow('保存成功!');
            searchToolBar('roles');
        } else {
            alertShow('保存失败!');
        }
    });
    ids = "";
}