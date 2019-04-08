
//身份证二要素验证
var MESSAGE_JSON = idTwo_z.product;

if(MESSAGE_JSON){
	//身份证验证信息
	var MESSAGE = MESSAGE_JSON.MESSAGE;
	//身份证验证结果
	var RESULT = MESSAGE_JSON.RESULT;
	
	if(RESULT=='1'){
		MESSAGE = '一致';
		$("#idCardCheck").html("身份证验证结果： " + MESSAGE);
	}else if(RESULT=='2'){
		MESSAGE = '不一致';
		$("#idCardCheck").html("身份证验证结果： " + MESSAGE);
	}else{
		MESSAGE = '无法验证';
		$("#idCardCheck").html("身份证验证结果： " + MESSAGE);
	}
}else{
	$("#idCardCheck").html("身份证验证结果：无");
}

