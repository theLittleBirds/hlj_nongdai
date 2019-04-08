
//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(greylist)){
	$("#greylist").append("<label class=\"col-sm-4 control-label\" >身份证号："+greylist.monitor_value+"</label>")
	.append("<label class=\"col-sm-4 control-label\" >风险等级："+greylist.risk_level_zh+"</label>")	
	.append("<label class=\"col-sm-4 control-label\" >证据类型："+greylist.monitor_field_zh+"</label>");
	
//	var reg = new RegExp("\"" , "g" )
//	var riskType = greylist.risk_type_zh.replace("[","").replace("]","").replace(reg ,"").split(",");
	
	var riskType = jQuery.parseJSON(greylist.risk_type_zh);
	var _riskType = "";
	for(var i=0; i<riskType.length; i++){
		_riskType = _riskType + riskType[i]+","
	}
	
	$("#_greylist").append("<label class=\"col-sm-12 control-label\" >风险类型（法院执行）：<pre>"+riskType+"</pre></label>");
}else{
	$("#greylist").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}