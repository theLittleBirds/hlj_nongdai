//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(blacklist)){
	$("#blacklist").append("<label class=\"col-sm-4 control-label\" >身份证号："+blacklist.monitor_value+"</label>")
		.append("<label class=\"col-sm-4 control-label\" >证据类型："+blacklist.monitor_field_zh+"</label>");

	$("#_blacklist").append("<label class=\"col-sm-12 control-label\" >风险类型（法院执行）：<pre>"+blacklist.detail+"</pre></label>");
}else{
	$("#blacklist").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}