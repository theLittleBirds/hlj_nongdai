
document.write('<script src="' + ctx +'/static/admin/default/plugins/kindeditor-4.1.10/kindeditor.js"></script>');
document.write('<script src="' + ctx +'/static/admin/default/plugins/kindeditor-4.1.10/lang/zh_CN.js"></script>');

jQuery.fn.extend({
    kindEditor: function (options) {
        var def = {
            resizeType: 2,
            width: '100%',
            minWidth: '100%',
            themeType: 'simple',
            allowFileupload: false,
            allowFlashUpload: false,
            allowMediaUpload: false,
            uploadJson: 'fileUpload?model=editor',
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link', '|', 'fullscreen']
        };
        $(this).wrap('<div class="no-padding"/>');
        var editor = KindEditor.create($(this), $.extend({}, def, options));
        editor.html(utils.htmlDecode($(this).val()));
        return editor;
    }
});