/**
 * 适用于表单表页面的通用js
 */
document.write('<script src="' + ctx +'/static/admin/default/plugins/hplus_v4.1.0/js/plugins/validate/jquery.validate.min.js"></script>');
document.write('<script src="' + ctx +'/static/admin/default/plugins/hplus_v4.1.0/js/plugins/validate/messages_zh.min.js"></script>');
document.write('<script src="' + ctx +'/static/admin/default/js/validate.expand.js"></script>');
document.write('<script src="' + ctx +'/static/admin/default/plugins/webuploader-0.1.5/webuploader.min.js"></script>');
//document.write('<script src="' + ctx +'/static/admin/default/plugins/ueditor-1.4.3.1/ueditor.config.js"></script>');
//document.write('<script src="' + ctx +'/static/admin/default/plugins/ueditor-1.4.3.1/ueditor.all.js"></script>');
//document.write('<script src="' + ctx +'/static/admin/default/plugins/ueditor-1.4.3.1/lang/zh-cn/zh-cn.js"></script>');

//window.UEDITOR_HOME_URL = ctx + "/static/admin/default/plugins/ueditor-1.4.3.1/";

$(document).ready(function () {

    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });

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
                                parent.location.href = "/admin/menu/init";
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

jQuery.fn.extend({
    fileUpload: function (type, multiple) {

        var optionsList = {
            image: {
                title: '图片',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*',
                def_src: 'data:image/gif;base64,R0lGODlhAQABAIAAAMrK2f///yH5BAAHAP8ALAAAAAABAAEAAAICRAEAOw==',
                thumbnail: '<img class="thumbnail uploaded" title="点击删除" style="min-width:10px;max-width:100%;max-height:300px;float: left;">'
            },
            video: {
                title: '视频',
                extensions: 'mp4',
                mimeTypes: 'video/*',
                def_src: '',
                thumbnail: '<video controls="controls" style=" width: 100%;background: black;">您的浏览器不支持播放视频</video>'
            },
            voice: {
                title: '语音',
                extensions: 'amr,mp3',
                mimeTypes: 'audio/*',
                def_src: '',
                thumbnail: '<audio controls="controls" style=" width: 100%;">您的浏览器不支持播放语音</audio>'
            },
            music: {
                title: '音乐',
                extensions: 'amr,mp3',
                mimeTypes: 'audio/*',
                def_src: '',
                thumbnail: '<audio controls="controls" style=" width: 100%;">您的浏览器不支持播放音乐</audio>'
            }
        };

        var options = eval('optionsList.' + type);
        if (!options) {
            return layer.msg('上传文件类型错误！不支持的类型:' + type, {icon: 2});
        }

        var input = $(this);
        input.wrap('<div class="no-padding"/>');
        var wrap = input.parent();
        var upload_picker = $('<div class="upload-picker">点击上传</div>');
        var upload_progressBar = $('<div class="progress progress-mini progress-striped active no-margin"><div class="progress-bar progress-info" style="width: 35%;"></div></div>').hide();
        var upload_thumbnail = $(options.thumbnail);
        var upload_thumbnail_wrap = $('<div class="thumbnail_wrap clearfix">');
        wrap.append(upload_picker, upload_progressBar, upload_thumbnail_wrap);

        //  值回显
        var val = input.val();
        if (val) {
            $.each(val.split(','), function (i, one) {
                if (one) {
                    if (!multiple) {
                        upload_picker.find('.webuploader-pick').text('点击重新上传');
                    } else {
                        upload_picker.find('.webuploader-pick').text('点击继续上传');
                    }
                    var path = one;

                    var thumbnail = upload_thumbnail.clone().attr('src', path);

                    if (multiple) thumbnail.on('click', function () {
                        input.val(input.val().replace($(this).attr('src'), ''));
                        $(this).remove();
                    })

                    upload_thumbnail_wrap.prepend(thumbnail);
                }
            });
        }

        var uploader = WebUploader.create({
            auto: false,
            resize: true,
            swf: 'static/plugins/webuploader-0.1.5/Uploader.swf',
            server: '/admin/fileUpload',
            pick: upload_picker,
            fileVal: 'upfile',
            accept: {
                title: options.title,
                extensions: options.extensions,
                mimeTypes: options.mimeTypes
            }
        });
        uploader.on('filesQueued', function (files) {
            if (!multiple && files.length > 1) {
                uploader.reset();
                return layer.msg('此处只需要一个文件!', {icon: 2});
            }
            for (var i in files) {
                uploader.upload(files[i]);
            }
        });

        uploader.on('uploadStart', function (file) {
            upload_progressBar.children().width('0%');
            upload_progressBar.show();
        });

        uploader.on('uploadProgress', function (file, percentage) {
            upload_progressBar.children().width(percentage * 100 + '%');
        });

        uploader.on('uploadComplete', function (file) {
            upload_progressBar.children().width('0%');
            upload_progressBar.hide();
        });

        uploader.on('uploadSuccess', function (file, response) {
            if (response.statusCode == '200') {
                if (!multiple) {
                    input.val('');
                    upload_picker.find('.webuploader-pick').text('点击重新上传');
                    upload_thumbnail_wrap.html('');
                } else {
                    upload_picker.find('.webuploader-pick').text('点击继续上传');
                }
                var path = response.path;

                input.val((input.val() ? input.val() + ',' : '') + path);
                var thumbnail = upload_thumbnail.clone().attr('src', path);

                if (multiple) thumbnail.on('click', function () {
                    input.val(input.val().replace($(this).attr('src'), ''));
                    $(this).remove();
                })

                upload_thumbnail_wrap.prepend(thumbnail);
            } else {
                alert(response.message);
            }
        });

        uploader.on('uploadError', function (file, reason) {
            if (!multiple) input.val('');
            alert(reason);
        });
        uploader.on('error', function (type) {
            if (!multiple) input.val('');
            alert(type == 'F_DUPLICATE' ? '此文件已上传过了!' : type);
        });

    }
    /*,
    UEditor: function (options) {
        var target = $(this);

        var def = {
            toolbars: [
                ['fullscreen', 'source', 'undo', 'redo', 'insertunorderedlist', 'insertorderedlist', 'unlink', 'link', 'cleardoc', 'selectall', 'searchreplace', 'preview', 'drafts', 'separate', 'horizontal', 'simpleupload', 'date', 'time', 'blockquote', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'forecolor', 'backcolor', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'touppercase', 'tolowercase', 'indent', 'removeformat', 'formatmatch', 'autotypeset', 'fontfamily', 'fontsize', 'inserttable']
            ],
            autoHeightEnabled: true,
            autoFloatEnabled: true
        };

        target.removeClass('form-control').val(utils.htmlDecode(target.val()));

        var editor = UE.getEditor($(this)[0].id, $.extend({}, def, options));
        editor.ready(function () {
            editor.addListener('contentChange', function () {
                target.val(editor.getContent());
            })
        });
        return editor;
    }*/
});