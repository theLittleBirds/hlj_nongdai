/**
 * 表单验证扩展
 */

$(document).ready(function () {

    // 手机号码
    $.validator.addMethod("phone", function (value, element) {
        var phone = /^1[0-9]{10}$/;
        return this.optional(element) || (phone.test(value));
    }, "请输入有效的手机号码");

    // 电话号码
    $.validator.addMethod("tel", function (value, element) {
        var tel = /^0\d{2,3}-?\d{7,8}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入有效的电话号码");

    // 医师证编号
    $.validator.addMethod("docNum", function (value, element) {
        var docNum = /^[0-9a-zA-Z]{15}$/;
        return this.optional(element) || (docNum.test(value));
    }, "请输入有效的医师证编号");

    // 年龄
    $.validator.addMethod("age", function (value, element) {
        var age = /^\+?[1-9]\d*$/;
        return this.optional(element) || (age.test(value));
    }, "请输入有效的年龄");

    // 身份证号
    $.validator.addMethod("idCard", function (value, element) {
        var idCard = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
        return this.optional(element) || (idCard.test(value));
    }, "请输入有效的身份证号");

    // 银行卡账号
    $.validator.addMethod("bankCardNo", function (value, element) {
        var bankCardNo = /^\d{16}|\d{19}$/;
        return this.optional(element) || (bankCardNo.test(value));
    }, "请输入有效的银行卡账号");

    // 年限
    $.validator.addMethod("year", function (value, element) {
        var year = /^\+?[0-9]\d*$/;
        return this.optional(element) || (year.test(value));
    }, "请输入有效的年限");

    // 人数
    $.validator.addMethod("staff", function (value, element) {
        var staff = /^\+?[0-9]\d*$/;
        return this.optional(element) || (staff.test(value));
    }, "请输入有效的人数");
});