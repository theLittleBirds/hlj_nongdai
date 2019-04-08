
//手机在网状态
var MESSAGE_TEL_STATUS = telStatus.product;

if(MESSAGE_TEL_STATUS){
	//运营商类型
	var TYPE_TEL_STATUS = MESSAGE_TEL_PERIOD.operation;
	//手机在网状态 信息
	var VALUE_TEL_STATUS = MESSAGE_TEL_PERIOD.data.value;
	//手机在网状态    验证结果
	var RESULT_TEL_STATUS = MESSAGE_TEL_PERIOD.result;	
	
	if(TYPE_TEL_STATUS == '1'){
		TYPE_TEL_STATUS = '电信';
	}else if(TYPE_TEL_STATUS == '2'){
		TYPE_TEL_STATUS = '联通';
	}else if(TYPE_TEL_STATUS == '3'){
		TYPE_TEL_STATUS = '移动';
	}else if(TYPE_TEL_STATUS == '4'){
		TYPE_TEL_STATUS = '其他运营商';
	}
	
	if(RESULT_TEL_STATUS=='1'){
		RESULT_TEL_STATUS = '查询成功';
		//查询成功之后在，做时长判断
		if(VALUE_TEL_STATUS == '1'){
			VALUE_TEL_STATUS = TYPE_TEL_STATUS + '，正常';
			
		}else if(VALUE_TEL_STATUS == '2'){
			VALUE_TEL_STATUS = TYPE_TEL_STATUS + '，停机 ';
			
		}else if(VALUE_TEL_STATUS == '3'){
			VALUE_TEL_STATUS = TYPE_TEL_STATUS + '，销号';
			
		}else if(VALUE_TEL_STATUS == '4'){
			VALUE_TEL_STATUS = TYPE_TEL_STATUS + '，异常';
			
		}else{
			VALUE_TEL_STATUS = '无';
		}
		$("#telStatusCheck").html("手机在网状态结果： " + VALUE_TEL_STATUS);
	}else if(RESULT_TEL_STATUS=='0'){
		RESULT_TEL_STATUS = '查无结果';
		$("#telStatusCheck").html("手机在网状态结果： " + RESULT_TEL_STATUS);
	}else if(RESULT_TEL_STATUS=='2'){
		RESULT_TEL_STATUS = '请求超时';
		$("#telStatusCheck").html("手机在网状态结果： " + RESULT_TEL_STATUS);
	}else{
		RESULT_TEL_STATUS = '系统异常';
		$("#telStatusCheck").html("手机在网状态结果： " + RESULT_TEL_STATUS);
	}
	
}else{
	$("#telStatusCheck").html("手机在网状态结果：无");
}