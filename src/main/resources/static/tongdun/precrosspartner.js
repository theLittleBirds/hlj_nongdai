
//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(precrosspartner)){
	$("#precrosspartner").append("<label class=\"col-sm-4 control-label\" >身份证号："+precrosspartner.monitor_value+"</label>")
		.append("<label class=\"col-sm-4 control-label\" >平台总数："+precrosspartner.new_platform_total+"</label>")
		.append("<label class=\"col-sm-4 control-label\" >证件类型："+precrosspartner.monitor_field_zh+"</label>");
}else{
	$("#precrosspartner").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}