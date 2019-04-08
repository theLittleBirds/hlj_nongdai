/**
 * Created by maf on 2017/7/7.
 */

function initChildren(paramList, html) {
    if (paramList != null && paramList.length > 0) {
        for (var i in paramList) {

            var param = paramList[i];

            // 有下级参数的标题
            if (param.input_type == 'hasSubset') {
                html += "<tr><td colspan='4' style='background-color: #F9F9F9;'><b>" + param.param_name + "</b></td></tr>";
                html = initChildren(param.children, html);
            } else {
                // 文本域、独占一行、第一列tb
                if (param.display == 2 || i % 2 == 0) html += "<tr>";
                html += "   <td style='background-color: #F9F9F9;'><b>" + param.param_name + "</b></td>";

                var _colspan = (param.display == 2 ? 3 : 1);

                // 文本框
                if (param.input_type == 'text') {

                    html += "   <td colspan='" + _colspan + "'><input" + " style='" + (param.input_width != null ? ("width: " + param.input_width + "px;") : "") + "' type='text' id='" + param.id + "' name='" + param.id + "' value='" + undefinedToNull(param.param_value) + "' data-rule='" + undefinedToNull(param.validate_rule) + "' placeholder='" + undefinedToNull(param.placeholder) + "'><span class='unit'>" + undefinedToNull(param.unit) + "</span></td>";

                    // CheckBox
                } else if (param.input_type == 'checkbox') {

                    html += "   <td colspan='" + _colspan + "'>" + initCheckBox(param.id, param.options, param.option_type, param.validate_rule, param.param_value) + "</td>";

                }

                // 独占一行、第二列tb
                if (param.display == 2 || i % 2 == 1) html += "</tr>";
            }
        }
    }

    return html;
}

/**
 * 参数转html
 * @param paramList
 */
function transToHtml(paramList) {

    var html = "";

    if (paramList != null && paramList.length > 0) {

        for (var i in paramList) {

            var param = paramList[i];

            // 标题
            if (param.input_type == 'title') {
                html += "    <h5 class='secondTitle' id='title-i-j'><span>" + param.param_name + "</span></h5>";
                html += "<table id='table-" + i + "' width='100%' border='0' cellspacing='0' cellpadding='0' style=' border-collapse: collapse;'>";
                html = initChildren(param.children, html);
                html += "</table>";
            } else if (i == 0) {
                html += "<table id='table-" + i + "' width='100%' border='0' cellspacing='0' cellpadding='0' style=' border-collapse: collapse;'>";
            }

            // 有下级参数的标题
            if (param.input_type == 'hasSubset') {
                html += "<tr><td colspan='4' style='background-color: #F9F9F9;'><b>" + param.param_name + "</b></td></tr>";
                html = initChildren(param.children, html);
                html += "</table>";
            } else if (param.input_type != 'title') {
                // 文本域、独占一行、第一列tb
                if (param.display == 2 || i % 2 == 0) html += "<tr>";
                html += "   <td style='background-color: #F9F9F9;'><b>" + param.param_name + "</b></td>";

                var _colspan = (param.display == 2 ? 3 : 1);

                // 文本框
                if (param.input_type == 'text') {

                    html += "   <td colspan='" + _colspan + "'><input" + " style='" + (param.input_width != null ? ("width: " + param.input_width + "px;") : "") + "' type='text' id='" + param.id + "' name='" + param.id + "' value='" + undefinedToNull(param.param_value) + "' data-rule='" + undefinedToNull(param.validate_rule) + "' placeholder='" + undefinedToNull(param.placeholder) + "'><span class='unit'>" + undefinedToNull(param.unit) + "</span></td>";

                // CheckBox
                } else if (param.input_type == 'checkbox') {

                    html += "   <td colspan='" + _colspan + "'>" + initCheckBox(param.id, param.options, param.option_type, param.validate_rule, param.param_value) + "</td>";

                }

                // 独占一行、第二列tb
                if (param.display == 2 || i % 2 == 1) html += "</tr>";
            }
        }

        html += "</table>";
    }
    return html;
}

function undefinedToNull(value) {
    return value == undefined ? "" : value;
}

/**
 * 生成radio、checkbox
 * @param id
 * @param options
 * @param optionType
 * @param validateRule
 * @param reportValue
 * @returns {string}
 */
function initCheckBox(id, options, optionType, validateRule, paramValue) {
    var _html = "<div class='checkbox i-checks'>";
    if (options != null && options != '') {
        $.each(options.split("?"), function (i, e) {
            _html += "<label><input type='" + (optionType == 1 ? 'radio' : 'checkbox') + "' id='" + e + "' name='" + id + "' value='" + undefinedToNull(e) + "' data-rule='" + undefinedToNull(validateRule) + "'" + (paramValue != null && paramValue != '' && paramValue.indexOf(e) >= 0 ? 'checked' : '') + ">" + e + '</label>';
        })
        _html += '</div>';
    }
    return _html;
}

function getScrollHeight() {
    var h = document.body.scrollHeight;
    if (h == 0) h = document.documentElement.scrollHeight;
    return h;
}