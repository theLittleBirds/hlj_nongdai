document.write('<link rel="stylesheet" href="/static/admin/default/plugins/plupload/css/style.css">');
document.write('<script src="/static/admin/default/plugins/plupload/js/plupload.full.min.js"></script>');

jQuery.fn.extend({
    plupload: function (flag,multiple) {

        var pluploader = new plupload.Uploader({
            runtimes: 'html5,flash,silverlight,html4',
            browse_button: flag + '-btn', // you can pass in id...
            url: '/admin/appUpload?startupPath=' + $('#startupPath').val(),
            multi_selection: multiple,
            filters: {
                max_file_size: '500kb',
                mime_types: [
                    {title: "Image files", extensions: "jpg,jpeg,bmp,png"}
                ],
                prevent_duplicates: false
            },
            flash_swf_url: 'Moxie.swf',
            silverlight_xap_url: 'Moxie.xap',

            init: {
                PostInit: function () {
                    document.getElementById(flag + '-bar').innerHTML = '';
                },

                FilesAdded: function (up, files) {
                    plupload.each(files, function (file) {
                        document.getElementById(flag + '-bar').innerHTML = '<div class="progress" style="display: inline-block;"><div class="progress-bar" style="width: 0%"></div></div><b></b>';
                    });

                    pluploader.start();
                },

                UploadProgress: function (up, file) {
                    var d = document.getElementById(flag + '-bar');
                    d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";

                    var prog = d.getElementsByTagName('div')[0];
                    var progBar = prog.getElementsByTagName('div')[0]
                    progBar.style.width = 2 * file.percent + 'px';
                    progBar.setAttribute('aria-valuenow', file.percent);
                },

                FileUploaded: function (up, file, info) {

                    if (info.status >= 200 || info.status < 200) {
                        // 隐藏进度条
                        document.getElementById(flag + '-bar').innerHTML = '';

                        var res = eval("(" + info.response + ")");
                        $("#" + flag + "-img").html('<img src="' + res.path + '" />');
                        $('#' + flag).val(res.mediaid);
                    }
                    else {
                        layer.msg(info.response, {icon: 2});
                    }
                },

                Error: function (up, args) {
                    layer.msg(args.message, {icon: 2});
                }
            }
        });

        pluploader.init();
    }
});

