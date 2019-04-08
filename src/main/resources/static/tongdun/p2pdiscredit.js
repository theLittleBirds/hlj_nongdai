
//json对象为{}返回true，返回false即不为{}
if(!$.isEmptyObject(p2pdiscredit)){
	$("#p2pdiscredit").append("<label class=\"col-sm-4 control-label\" >身份证号："+p2pdiscredit.monitor_value+"</label>");
	
	var _p2pdiscredit = jQuery.parseJSON(p2pdiscredit.details);
	for(var i=0; i<_p2pdiscredit.length; i++){
		$("#_p2pdiscredit").append("<label class=\"col-sm-4 control-label\" style=\"color: #ff3333;\">逾期金额："+_p2pdiscredit[i].overdue_amount_range+"</label>")
		.append("<label class=\"col-sm-4 control-label\" style=\"color: #ff3333;\">逾期天数："+_p2pdiscredit[i].overdue_day_range+"</label>")
		.append("<label class=\"col-sm-4 control-label\" style=\"color: #ff3333;\">逾期笔数："+_p2pdiscredit[i].overdue_count+"</label>");
	}
	
}else{
	$("#p2pdiscredit").html("<label class=\"col-sm-4 control-label\" >无风险</label>");
}