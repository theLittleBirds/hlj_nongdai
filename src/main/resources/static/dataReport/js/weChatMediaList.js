/**
 * Created by quyang on 15/11/30.
 */
/**
 * 适用于列表页面的通用js
 */
$(document).ready(function () {
    var layer = window.parent.layer || layer;
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
    $("[title]").popover({html: true, placement: 'auto top', trigger: 'hover', delay: {show: 500, hide: 100}});

    // 表头排序
    var orderField = $('.search-form input[name=orderField]').val();
    var orderDirection = $('.search-form input[name=orderDirection]').val();

    if (orderField && orderDirection) {
        $('table th[name=' + orderField + ']').removeClass('asc desc').addClass(orderDirection);
    }

    $('.search-form .form-group input,.search-form .form-group select').each(function () {
        if ($(this).val()) {
            $('#search-panel').addClass('in');

        }
    });

    $('table thead').on('click', 'th.sortable', function () {
        orderField = $(this).attr('name');
        orderDirection = (orderDirection == 'desc') ? 'asc' : 'desc';

        $('.search-form input[name=pageCurrent]').val('');
        $('.search-form input[name=orderField]').val(orderField);
        $('.search-form input[name=orderDirection]').val(orderDirection);
        $('body').removeClass('fadeIn').addClass('fadeOut');
        $('.search-form').submit();
    });

    // 全选/全不选
    $('table thead').on('click', '.dropdown-menu li', function () {
        var index = $(this).index();
        if (index == 0) {
            $("table input[type=checkbox]").iCheck('check');
        } else if (index == 1) {
            $("table input[type=checkbox]").iCheck('toggle');
        } else {
            $("table input[type=checkbox]").iCheck('uncheck');
        }
    });

    //  列表项目基本操作
    $('.btn-toolbar').on('click', 'button', function () {
        var url = $('.search-form').attr('action');
        if (url != null && url != '') {
            if (!url.endWith("/")) url = url + "/";
            
            if ($(this).hasClass('list-edit')) {
                var ids = getCheckedIds();
                if (ids.length == 0)
                    return layer.alert('请选中一个项目!', {icon: 0});
                if (ids.length > 1)
                    return layer.alert('一次只能编辑一个项目哦!', {icon: 0});

                window.location = url + "form?id=" + ids[0];
            }
            if ($(this).hasClass('list-delete')) {
                var ids = getCheckedIds();
                var length = ids.length;
                if (length == 0) {
                    return layer.alert('请至少选择一项!', {icon: 0});
                }
                layer.confirm('确认删除 ' + length + ' 个项目？', {
                    btn: ['确认', '取消']
                }, function () {
                    $.post(url + 'delete', {ids: ids.toString()}, function (ret) {
                        if (ret.statusCode == 200) {
                            layer.msg(ret.message, {icon: 1}, function () {
                                window.location.reload();
                            });
                        } else {
                            layer.msg(ret.message, {icon: 2});
                        }
                    })
                });
            }
            if ($(this).hasClass('list-enabled')) {
                var ids = getCheckedIds();
                var length = ids.length;
                if (length == 0) {
                    return layer.alert('请至少选择一项!', {icon: 0});
                }
                layer.confirm('确认启用 ' + length + ' 个项目？', {
                    btn: ['确认', '取消']
                }, function () {
                    $.post(url + 'enabled', {ids: ids.toString()}, function (ret) {
                        if (ret.statusCode == 200) {
                            layer.msg(ret.message, {icon: 1}, function () {
                                window.location.reload();
                            });
                        } else {
                            layer.msg(ret.message, {icon: 2});
                        }
                    })
                });
            }
            if ($(this).hasClass('list-disabled')) {
                var ids = getCheckedIds();
                var length = ids.length;
                if (length == 0) {
                    return layer.alert('请至少选择一项!', {icon: 0});
                }
                layer.confirm('确认禁用 ' + length + ' 个项目？', {
                    btn: ['确认', '取消']
                }, function () {
                    $.post(url + 'disabled', {ids: ids.toString()}, function (ret) {
                        if (ret.statusCode == 200) {
                            layer.msg(ret.message, {icon: 1}, function () {
                                window.location.reload();
                            });
                        } else {
                            layer.msg(ret.message, {icon: 2});
                        }
                    })
                });
            }
            if ($(this).hasClass('list-export')) {
                window.location.href = url + 'export?ids=' + getCheckedIds().toString() + '&' + $('.search-form').serialize();
            }
            if ($(this).hasClass('list-import-template')) {
                window.location.href = url + 'import/template';
            }
            if ($(this).hasClass('list-import')) {
                var timestamp = new Date().getTime();
                var html ='<input type="text" class="hide" name="importFile" id="importFile_' + timestamp + '">'
                layer.open({
                    title: '请选择文件',
                    content: html,
                    success: function(layero, index){
                        importFile(url, timestamp);
                    },
                    btn: [],
                });
            }
        }
    });
});

function importFile(url, timestamp) {
    var input = $('input#importFile_' + timestamp);
    var upload_picker = $('<div class="upload-picker">点击上传</div>');
    input.parent().append(upload_picker);
    var uploader = WebUploader.create({
        auto: true,
        swf: '/static/plugins/webuploader-0.1.5/Uploader.swf',
        server: url + 'import',
        pick: upload_picker,
        accept: { mimeTypes: '.xls,.xlsx' }
    });
    uploader.on('uploadSuccess', function (file, response) {
        layer.open({
            title: '导入结果',
            content: response.message,
            ok: function (layero, index) {
                if (response.statusCode == 200) {
                    window.location.reload();
                } else {
                    layer.close(index);
                }
            }
        });
    });
    uploader.on('uploadError', function (file, reason) {
        layer.alert("文件导入失败:" + reason);
    });
}

// 翻页方法
function page(pageCurrent, pageSize, funcParam) {
    $('.search-form input[name=pageCurrent]').val(pageCurrent);
    $('body').removeClass('fadeIn').addClass('fadeOut');
    $('.search-form').submit();
}

// 获取选中项目id
function getCheckedIds() {
    var array = [];
    $('table input[type=checkbox][name=ids]:checked').each(function () {
        array.push($(this).val());
    });
    return array;
}


function del(id) {
    var url = $('.search-form').attr('action');
    if (!url.endWith("/")) url = url + "/";
    top.layer.confirm('确认删除该项目？', {
        btn: ['确认', '取消']
    }, function () {
        $.post(url + 'delete', {ids: id}, function (ret) {
            if (ret.statusCode == 200) {
                top.layer.msg(ret.message, {icon: 1}, function () {
                    window.location.reload();
                });
            } else {
                top.layer.msg(ret.message, {icon: 2});
            }
        })
    });
}

function copy(id) {
    var url = $('.search-form').attr('action');
    if (!url.endWith("/")) url = url + "/";
    top.layer.confirm('确认复制该项目？', {
        btn: ['确认', '取消']
    }, function () {
        $.post(url + 'copy', {id: id}, function (ret) {
            if (ret.statusCode == 200) {
                top.layer.msg(ret.message, {icon: 1}, function () {
                    window.location.reload();
                });
            } else {
                top.layer.msg(ret.message, {icon: 2});
            }
        })
    });
}

function loadUrl(url) {
    location.href = url;
}