var handler = [
    {field:'id', title:'ID', align:'center', valign:'middle'},
    {field:'roleName', title:'角色', align:'center', valign:'middle'},
    {field:'username', title:'登陆名', align:'center', valign:'middle'},
    {field:'realName', title:'姓名', align:'center', valign:'middle'},
    {field:'telephone', title:'手机', align:'center', valign:'middle'},
    {field:'opt', title:'操作', align:'center', valign:'middle', events:'operationEvents', formatter: function (value, row, index) {
            return operations_user;
        }}
];

var options = {
    method: 'get',
    contentType: "json",
    url: ctx_ + '/user/findData',
    cache : false,
    pagination: true,
    singleSelect: true,
    pageSize: 10,
    pageList: [10, 25, 50],
    sidePagination: "server",
    toolbar: '#user-toolbar',
    columns: handler,
    queryParams: function (params) {
        return getQueryParams(params, 'users')
    },
    onLoadError: function (result) {
        alertShow("数据加载失败！")
    }
};


$(function () {
    $("#users").bootstrapTable(options);

    // 重置
    $('#g_reset').bind('click', function () {
        $('#userName_').val('');
    });

    // 查询
    $('#g_search').bind('click', function () {
        searchToolBar('users');
    });

    //添加
    $('#g_edit').bind('click', function () {
        layer.open({
            type: 2,
            title: false,
            shadeClose: true,
            shade: 0.3,
            area: ['80%', '80%'],
            content: ctx_ + '/user/edit'
        });
    });

    window.operationEvents = {
        'click .user-edit' : function(e, value, row, index){
            layer.open({
                type: 2,
                title: false,
                shadeClose: true,
                shade: 0.3,
                area: ['80%', '80%'],
                content: ctx_ + '/user/edit?id=' +  row.id
            });
        }
    };

    $('#userForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            realName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            telephone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            }
        },
        submitHandler: function(validator, form, submitButton) {
            post(form.attr('action'), form.serialize(), function(result) {
                alertShow(result.message);
                if(result.status == 1) {
                    console.info('robber');
                    parent.searchToolBar('users', true);
                    parent.layer.close(parent.layer.index);
                }
            });
            $(form).bootstrapValidator('disableSubmitButtons', false);
        }
    });

});