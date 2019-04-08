document.write('<link rel="stylesheet" href="/static/admin/default/plugins/plupload/css/style.css">');
document.write('<script src="/static/admin/default/plugins/plupload/js/plupload.full.min.js"></script>');

jQuery.fn.extend({
    plupload: function (multiple) {

        var pluploader = new plupload.Uploader({
            runtimes: 'html5,flash,silverlight,html4',
            browse_button: 'fileupload-btn', // you can pass in id...
            url: '/admin/fileUpload?startupPath=' + $('#intoPieceId').val(),
            multi_selection: multiple,
            filters: {
                max_file_size: '500kb',
                mime_types: [
                    {title: "Image files", extensions: "gif,jpg,jpeg,bmp,png"}
                ],
                prevent_duplicates: false
            },
            flash_swf_url: 'Moxie.swf',
            silverlight_xap_url: 'Moxie.xap',

            init: {
                PostInit: function () {
                    document.getElementById('progress_bar').innerHTML = '';
                },

                FilesAdded: function (up, files) {
                    plupload.each(files, function (file) {
                        document.getElementById('progress_bar').innerHTML = '<div class="progress" style="display: inline-block;"><div class="progress-bar" style="width: 0%"></div></div><b></b>';
                    });

                    pluploader.start();
                },

                BeforeUpload:function(up, file){
                    up.setOption({"url" : '/admin/fileUpload?startupPath=' + $('#intoPieceId').val() + '&fileType=' + $('#fileType').val()});
                },

                UploadProgress: function (up, file) {
                    var d = document.getElementById('progress_bar');
                    d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";

                    var prog = d.getElementsByTagName('div')[0];
                    var progBar = prog.getElementsByTagName('div')[0]
                    progBar.style.width = 2 * file.percent + 'px';
                    progBar.setAttribute('aria-valuenow', file.percent);
                },

                UploadComplete: function (uploader, files) {

                    layer.msg('上传成功', {icon: 1, time: 2000}, function () {
                        window.location.reload();
                    });
                },

                Error: function (up, args) {
                    layer.msg(args.message, {icon: 2});
                }
            }
        });

        pluploader.init();
    }
});

