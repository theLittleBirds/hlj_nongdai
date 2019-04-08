
//银行卡四要素验证
var MESSAGE_BANK_JSON = bankFourPro.product;

if(MESSAGE_BANK_JSON){
	//银行卡四要素验证信息
	var MESSAGE_BANK = MESSAGE_BANK_JSON.msg;
	//银行卡四要素验证结果
	var RESULT_BANK = MESSAGE_BANK_JSON.result;
	
	if(RESULT_BANK=='00'){
		MESSAGE_BANK = '验证匹配';
		$("#bankCardCheck").append(MESSAGE_BANK);
	}else if(RESULT_BANK=='01'){
		MESSAGE_BANK = '验证不匹配';
		$("#bankCardCheck").append(MESSAGE_BANK);
	}else if(RESULT_BANK=='10'){
		MESSAGE_BANK = '要素格式错误';
		$("#bankCardCheck").append(MESSAGE_BANK);
	}else if(RESULT_BANK=='11'){
		MESSAGE_BANK = '银行卡问题';
		$("#idCardCheck").append(MESSAGE_BANK);
	}else if(RESULT_BANK=='20'){
		MESSAGE_BANK = '银行卡不支持验';
		$("#bankCardCheck").append(MESSAGE_BANK);
	}else{
		MESSAGE_BANK = '系统错误';
		$("#bankCardCheck").append(MESSAGE_BANK);
	}
}else{
	$("#bankCardCheck").html("银行卡验证结果：无");
}


