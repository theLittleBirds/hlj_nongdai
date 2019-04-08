
//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(postcrosspartner)){
	$("#postcrosspartner").append("<label class=\"col-sm-4 control-label\" >身份证号："+postcrosspartner.monitor_value+"</label>")
		.append("<label class=\"col-sm-4 control-label\" >证据类型："+postcrosspartner.new_platform_total+"</label>")
		.append("<label class=\"col-sm-4 control-label\" >证件类型："+postcrosspartner.monitor_field_zh+"</label>");
	
}else{
	$("#postcrosspartner").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}