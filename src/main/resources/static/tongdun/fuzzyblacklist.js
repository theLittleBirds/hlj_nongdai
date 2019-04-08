
//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(fuzzyblacklist)){
	$("#fuzzyblacklist").append("<label class=\"col-sm-4 control-label\" >身份证号："+fuzzyblacklist.monitor_value+"</label>")
	.append("<label class=\"col-sm-4 control-label\" >证据类型："+fuzzyblacklist.monitor_field_zh+"</label>");

	$("#_fuzzyblacklist").append("<label class=\"col-sm-12 control-label\" >风险类型（法院执行）：<pre>"+fuzzyblacklist.detail+"</pre></label>");
}else{
	$("#fuzzyblacklist").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}