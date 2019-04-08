/**
 * Created by quyang on 15/12/1.
 */

var utils = {
    removeHtmlTag: function (html) {
        return $('<p>' + html + '</p>').text();
    },
    len: function (str, length) {
        str = $.removeHtmlTag(str);
        if (str && str.length > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    },
    htmlEncode: function (str) {
        return $('<span>').text(str).html();
    },
    htmlDecode: function (str) {
        return $('<span>').html(str).text();
    },
    id2path:function(ids, callback){
        $.getJSON(config.ctx+'/media',{mediaIds:ids},function(ret){
            if (ret.statusCode == 200) {
                callback(ret.data);
            } else {
                layer.msg(ret.message, {icon: 2});
            }

        })
    }
};

jQuery.fn.extend({
    loadOptions: function (url, data, callBack) {
        if (typeof data == 'function' && !callBack) callBack = data, data = null;
        var target = $(this);

        $.getJSON(url, data, function (result) {
            if (result && result.statusCode == 200) {
                target.html('');
                if (!target.hasClass("required")) {
                    target.html($('<option/>').attr('value', '').text('--请选择--'));
                }
                $.each(result.data, function (i, item) {
                    if (item.value == target.data('value')) {
                        target.append($('<option/>').attr('value', item.value).text(item.label).attr("selected", true));
                    } else {
                        target.append($('<option/>').attr('value', item.value).text(item.label));
                    }
                });

                // 激活控件, 或者更新控件
                if (target.is(':visible')) {
                    target.chosen({search_contains: true});
                } else {
                    target.trigger("chosen:updated");
                }

                // 回调处理
                if (!callBack) return;
                if (target.val()) {
                    try {
                        callBack(target.val());
                    } catch (e) {
                        console.log(e);
                    }
                }

                // 绑定 on change 事件 : 两个是 配合 地区选择插件
                target.off('change');
                target.on('change', function () {
                    try {
                        callBack($(target).val(), true);
                    } catch (e) {
                        console.log(e);
                    }
                });
            } else {
                alert('获取远程数据失败，请稍后再试！');
            }
        });
    },
});


String.prototype.endWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substring(this.length - s.length) == s)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
}