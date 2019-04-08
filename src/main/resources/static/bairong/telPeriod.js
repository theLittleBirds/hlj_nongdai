
//手机在网时长
var MESSAGE_TEL_PERIOD = telPeriod.product;

if(MESSAGE_TEL_PERIOD){
	//运营商类型
	var TYPE_TEL_PERIOD = MESSAGE_TEL_PERIOD.operation;
	//手机在网时长信息
	var VALUE_TEL_PERIOD = MESSAGE_TEL_PERIOD.data.value;
	//手机在网时长    验证结果
	var RESULT_TEL_PERIOD = MESSAGE_TEL_PERIOD.result;
	
	if(TYPE_TEL_PERIOD == '1'){
		TYPE_TEL_PERIOD = '电信';
	}else if(TYPE_TEL_PERIOD == '2'){
		TYPE_TEL_PERIOD = '联通';
	}else if(TYPE_TEL_PERIOD == '3'){
		TYPE_TEL_PERIOD = '移动';
	}else if(TYPE_TEL_PERIOD == '4'){
		TYPE_TEL_PERIOD = '其他运营商';
	}
	
	if(RESULT_TEL_PERIOD=='1'){
		RESULT_TEL_PERIOD = '查询成功';
		//查询成功之后在，做时长判断
		if(VALUE_TEL_PERIOD == '1'){
			VALUE_TEL_PERIOD = TYPE_TEL_PERIOD + '，在网时长小于6个月';
			
		}else if(VALUE_TEL_PERIOD == '2'){
			VALUE_TEL_PERIOD = TYPE_TEL_PERIOD + '，在网时长大于等于6个月，小于12个月';
			
		}else if(VALUE_TEL_PERIOD == '3'){
			VALUE_TEL_PERIOD = TYPE_TEL_PERIOD + '，在网时长大于等于12个月，小于24个月';
			
		}else if(VALUE_TEL_PERIOD == '4'){
			VALUE_TEL_PERIOD = TYPE_TEL_PERIOD + '，在网时长大于等于24个月';
			
		}else{
			VALUE_TEL_PERIOD = '无';
		}
		$("#telPeriodCheck").append(VALUE_TEL_PERIOD);
	}else if(RESULT_TEL_PERIOD=='0'){
		RESULT_TEL_PERIOD = '查无结果';
		$("#telPeriodCheck").append(RESULT_TEL_PERIOD);
	}else if(RESULT_TEL_PERIOD=='2'){
		RESULT_TEL_PERIOD = '请求超时';
		$("#telPeriodCheck").append(RESULT_TEL_PERIOD);
	}else{
		RESULT_TEL_PERIOD = '系统异常';
		$("#telPeriodCheck").append(RESULT_TEL_PERIOD);
	}
}else{
	$("#telPeriodCheck").html("手机在网时长结果：无");
}