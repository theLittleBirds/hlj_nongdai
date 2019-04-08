/**
 * 部门表单页面js
 */
document.write('<script src="' + ctx + '/static/admin/default/plugins/hplus_v4.1.0/js/plugins/validate/jquery.validate.min.js"></script>');
document.write('<script src="' + ctx + '/static/admin/default/plugins/hplus_v4.1.0/js/plugins/validate/messages_zh.min.js"></script>');
document.write('<script src="' + ctx + '/static/admin/default/js/validate.expand.js"></script>');

$(document).ready(function () {

    var layer = window.parent.layer || layer;
    $.validator.setDefaults({
        ignore: "",
        highlight: function (a) {
            $(a).closest(".form-group").removeClass("has-success").addClass("has-error")
        }, success: function (a) {
            a.closest(".form-group").removeClass("has-error").addClass("has-success")
        }, errorElement: "span", errorPlacement: function (a, b) {
            if (b.is(":radio") || b.is(":checkbox")) {
                a.appendTo(b.parent().parent().parent())
            } else {
                a.appendTo(b.parent())
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });

    $().ready(function () {
        $('form').validate({
            submitHandler: function (form) {
                var action = $('form').attr('action').replace('form', 'save');
                var end = action.indexOf('?');
                action = action.substring(0, end > 0 ? end : action.length);
                $.post(action, $('form').serialize(), function (ret) {
                    if (ret.statusCode == 200) {
                        layer.msg(ret.message, {icon: 1, time: 2000}, function () {
                            if (ret.url && ret.url != "") {
                                parent.location.href = ret.url;
                            } else {
                                parent.location.href = "/admin/department/init";
                            }
                        });
                    } else {
                        layer.msg(ret.message, {icon: 2});
                    }
                });
            }
        });
    });
});