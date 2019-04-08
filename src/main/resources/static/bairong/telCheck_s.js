
//手机在网时长
var MESSAGE_TEL_CHECK = telCheck_s.product;

if(MESSAGE_TEL_CHECK){
	//验证结果
	var RESULT_TEL_CHECK = MESSAGE_TEL_CHECK.result;
	//运营商
	var OPERATION_TEL_CHECK = MESSAGE_TEL_CHECK.operation;
	
	if(RESULT_TEL_CHECK=='0'){
		RESULT_TEL_CHECK = '查无此号';
	}else if(RESULT_TEL_CHECK=='1'){
		RESULT_TEL_CHECK = '一致';
	}else if(RESULT_TEL_CHECK=='2'){
		RESULT_TEL_CHECK = '不一致';
	}
	
	if(OPERATION_TEL_CHECK=='1'){
		OPERATION_TEL_CHECK = '电信';
		$("#telephoneCheck").append(OPERATION_TEL_CHECK+"，"+RESULT_TEL_CHECK);
	}else if(OPERATION_TEL_CHECK=='2'){
		OPERATION_TEL_CHECK = '联通';
		$("#telephoneCheck").append(OPERATION_TEL_CHECK+"，"+RESULT_TEL_CHECK);
	}else if(OPERATION_TEL_CHECK=='3'){
		OPERATION_TEL_CHECK = '移动';
		$("#telephoneCheck").append(OPERATION_TEL_CHECK+"，"+RESULT_TEL_CHECK);
	}else if(OPERATION_TEL_CHECK=='4'){
		OPERATION_TEL_CHECK = '其他';
		$("#telephoneCheck").append(OPERATION_TEL_CHECK+"，"+RESULT_TEL_CHECK);
	}
	
}else{
	$("#telephoneCheck").html("手机号验证结果：无");
}






