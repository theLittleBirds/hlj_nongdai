<!DOCTYPE html>
<html>
<head>
    <title>大数据风险评估报告</title>
    
    <link rel="stylesheet" href="/dataReport/plugins/bootstrap-3.3.6/css/bootstrap.min.css" >
	<link href="/dataReport/plugins/hplus_v4.1.0/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	<link href="/dataReport/plugins/hplus_v4.1.0/css/animate.min.css" rel="stylesheet">
	<link href="/dataReport/plugins/hplus_v4.1.0/css/style.min.css" rel="stylesheet">
	<link href="/dataReport/plugins/hplus_v4.1.0/css/plugins/iCheck/custom.css" rel="stylesheet">
	<link rel="stylesheet" href="/dataReport/plugins/webuploader-0.1.5/webuploader.css">
	<link rel="stylesheet" href="/dataReport/plugins/hplus_v4.1.0/css/plugins/chosen/chosen.css">
	<link rel="stylesheet" href="/dataReport/css/style.css">
    <!-- checkbox样式 -->
    <link rel="stylesheet"
          href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css">
    <!-- radio样式 -->
    <link rel="stylesheet"
          href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/iCheck/custom.css">

    <style>
        .control-label {
            text-align: left !important;
        }

        .title {
            font-size: 14px;
            text-align: left;
            color: #000000;
            border-bottom: 1px dotted #CBCBCB;
            padding-bottom: 5px;
            margin-bottom: 5px !important;
        }
    </style>

</head>

<body class="gray-bg">

<div class="wrapper wrapper-content" style="padding: 0;">
    <div class="row">
        <div class="col-sm-12">
            <form class="form-horizontal" action="" method="post">
                <div class="ibox">
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group">
                        	<!-- 同盾 -->
                        	<label class="col-sm-3 control-label col-md-4" >审核报告编号：${report_id!'无'}</label>
                        	<label class="col-sm-3 control-label col-md-4" >报告生成时间:${report_time!'无'}</label>
                        </div>
                        <div class="form-group">
                        	<!-- 同盾 -->
                        	<label class="col-sm-3 control-label" style="color: #ff0000;">报告结论：${final_score!'无'}，报告评分： ${final_decision!'无'}</label>
                        </div>
                        <!-- <div class="form-group">
                        	百融
                        	<label class="col-sm-3 control-label" id="isReject" style="color: #ff3333;">反欺诈评分：无， 决策结果：无</label>
                        	<label class="col-sm-3 control-label" id="dangerSummary" style="color: #ff3333;">风险汇总：无</label>
                        </div> -->
                    </div>    
                    
                    <div class="ibox-title">
                        <h2 >个人基本信息</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group">
                        	<!-- 同盾 -->
                            <label class="col-sm-3 control-label">姓    名：${name!'无'}</label>
                            <label class="col-sm-3 control-label">身份证号：${id_number!'无'}</label>
                            <label class="col-sm-3 control-label">手机号码：${mobile!'无'}</label>
                        </div>
                        <!-- <div class="form-group">
                        	百融
                            <label class="col-sm-3 control-label" id="idCardCheck" style="color: #ff3333;">身份证验证结果：无</label>
                            <label class="col-sm-3 control-label" id="telStatusCheck" style="color: #ff3333;">手机在网状态结果：无</label>
							<label class="col-sm-3 control-label" id="telPeriodCheck" style="color: #ff3333;">手机在网时长结果：无</label>
                        </div> -->
                        <div class="form-group">
                        	<!-- 同盾 -->
                            <label class="col-sm-3 control-label">身份证所属地：${id_card_address!'无'}</label>
                            <label class="col-sm-3 control-label">手机所属地：${mobile_address!'无'}</label>
                        </div>
                    </div>
                    
                    <!-- 同盾 -->
                    <div class="ibox-title">
                        <h2>风险信息扫描</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="personList">
                        </div>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">    
                        <div class="form-group" id="relationList">
                        </div>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">    
                        <div class="form-group" id="customList">
                        </div>
                    </div>   
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">    
                        <div class="form-group" id="dangerList">
                        </div>
                    </div>    
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">    
                        <div class="form-group" id="platformList">
                        </div>
                    </div>
                    
                    <!-- 百融 -->
                    <!-- <div class="ibox-title">
                        <h2 >法院被执行人-失信</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="unFaithCourt">
                        	
                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >法院被执行人-被执行人</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="individualCourt">
                        	
                        </div>
                    </div> -->
                    <div class="ibox-title">
                        <h2 >公检法信息复核</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="specialList_c">
                        	
                        </div>
                    </div>
                    
                </div>

                <div class="clearfix form-actions" style="margin-bottom: 20px;">
                    <div class="col-md-9 text-center">
                        <button class="btn btn-w-m" type="button" onclick="window.close();">
                            <i class="ace-icon fa fa-reply bigger-110"></i>
                            	关闭
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/dataReport/plugins/jquery-2.2.2.min.js"></script>
<script src="/dataReport/plugins/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<script src="/dataReport/plugins/hplus_v4.1.0/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/dataReport/js/validate.expand.js"></script>
<script src="/dataReport/plugins/hplus_v4.1.0/js/plugins/validate/messages_zh.min.js"></script>
<script src="/dataReport/plugins/hplus_v4.1.0/js/plugins/iCheck/icheck.min.js"></script>
<script src="/dataReport/plugins/hplus_v4.1.0/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/dataReport/plugins/layer/layer.min.js"></script>
<script src="/dataReport/js/script.js"></script>
<script src="/admin/js/timeOut.js"></script>
<script>

    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    });
	
//***********************************************************************************************************************************************************    
    //个人基本信息核查
    var personList = ${personList};
    //alert("个人基本信息核查："+ personList.length );
    if(personList.length == 0){
    	$("#personList").append("<label class=\"col-sm-4 control-label col-md-11\">个人基本信息核查：无风险</label>");
    }else{
		for(var i=0; i<personList.length; i++){
			$("#personList").append("<label class=\"col-sm-4 control-label col-md-11\" >个人基本信息核查："+personList[i].item_name+"</label>");
				if(personList[i].risk_level=="低"){
					$("#personList").append("<label class=\"control-label\">风险级别:<span style=\"color: #03aa3e;\">"+personList[i].risk_level+"</span></label></br></br>");
				}else if(personList[i].risk_level=="中"){
					$("#personList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ffe43a;\">"+personList[i].risk_level+"</span></label></br></br>");
				}else{
					$("#personList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ff3333;\">"+personList[i].risk_level+"</span></label></br></br>");
				}		
        }
    }
    
    //关联人信息扫描
    var relationList = ${relationList};
    //alert("关联人信息扫描："+ relationList.length );
    if(relationList.length == 0){
    	$("#relationList").append("<label class=\"col-sm-4 control-label col-md-11\">关联人信息扫描：无风险</label>");
    }else{
    	for(var i=0; i<relationList.length; i++){
    		$("#relationList").append("<label class=\"col-sm-4 control-label col-md-11\" >个人基本信息核查："+relationList[i].item_name+"</label>");
			if(relationList[i].risk_level=="低"){
				$("#relationList").append("<label class=\"control-label\">风险级别:<span style=\"color: #03aa3e;\">"+relationList[i].risk_level+"</span></label></br></br>");
			}else if(relationList[i].risk_level=="中"){
				$("#relationList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ffe43a;\">"+relationList[i].risk_level+"</span></label></br></br>");
			}else{
				$("#relationList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ff3333;\">"+relationList[i].risk_level+"</span></label></br></br>");
			}
        }
    }
    
    //客户行为检测
    var customList = ${customList};
    //alert("客户行为检测："+ customList.length );
    if(customList.length == 0){
    	$("#customList").append("<label class=\"col-sm-4 control-label col-md-11\">客户行为检测：无风险</label>");
    }else{
        for(var i=0; i<customList.length; i++){
        	$("#customList").append("<label class=\"col-sm-4 control-label col-md-11\" >客户行为检测："+customList[i].item_name+"</label>");
			if(customList[i].risk_level=="低"){
				$("#customList").append("<label class=\"control-label\">风险级别:<span style=\"color: #03aa3e;\">"+customList[i].risk_level+"</span></label></br></br>");
			}else if(customList[i].risk_level=="中"){
				$("#customList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ffe43a;\">"+customList[i].risk_level+"</span></label></br></br>");
			}else{
				$("#customList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ff3333;\">"+customList[i].risk_level+"</span></label></br></br>");
			}
        }    	
    }
    
    //风险信息扫描
    var dangerList = ${dangerList};
    //alert("风险信息扫描："+ dangerList.length );
    if(dangerList.length == 0){
    	$("#dangerList").append("<label class=\"col-sm-4 control-label col-md-11\">风险信息扫描：无风险</label>");
    }else{
    	for(var i=0; i<dangerList.length; i++){
    		$("#dangerList").append("<label class=\"col-sm-4 control-label col-md-11\" >风险信息扫描："+dangerList[i].item_name+"</label>");
			if(dangerList[i].risk_level=="低"){
				$("#dangerList").append("<label class=\"control-label\">风险级别:<span style=\"color: #03aa3e;\">"+dangerList[i].risk_level+"</span></label></br></br>");
			}else if(dangerList[i].risk_level=="中"){
				$("#dangerList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ffe43a;\">"+dangerList[i].risk_level+"</span></label></br></br>");
			}else{
				$("#dangerList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ff3333;\">"+dangerList[i].risk_level+"</span></label></br></br>");
			}
        }
    }
    
    
    //多平台借贷申请检测
    var platformList = ${platformList};
    //alert("多平台借贷申请检测："+ platformList.length );
    if(platformList.length == 0){
    	$("#platformList").append("<label class=\"col-sm-4 control-label col-md-11\">多平台借贷申请检测：无风险</label>");
    }else{
    	for(var i=0; i<platformList.length; i++){
    		$("#platformList").append("<label class=\"col-sm-4 control-label col-md-4\" >多平台借贷申请检测："+platformList[i].item_name+"</label>");
    		$("#platformList").append("<label class=\"col-sm-2 control-label col-md-7\">总个数:"+platformList[i].platform_count+"</label>");
			if(platformList[i].risk_level=="低"){
				$("#platformList").append("<label class=\"control-label\">风险级别:<span style=\"color: #03aa3e;\">"+platformList[i].risk_level+"</span></label></br></br>");
			}else if(platformList[i].risk_level=="中"){
				$("#platformList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ffe43a;\">"+platformList[i].risk_level+"</span></label></br></br>");
			}else{
				$("#platformList").append("<label class=\"control-label\">风险级别:<span style=\"color: #ff3333;\">"+platformList[i].risk_level+"</span></label></br></br>");
			}
    		$("#platformList").append("<label class=\"control-label\">平台详情:"+platformList[i].platformDetail.platform_detail+"</label></br></br></br>");
        }
    }
    
    
/*     //百融素有接口返回信息
    var third_platform = ${third_platform};
    
	//baiRongRule规则
    var baiRongRule = third_platform.baiRongRule;
    //console.log("baiRongRule规则:");
    //console.log(baiRongRule);
    
    //idTwo_z规则
    var idTwo_z = third_platform.idTwo_z;
    //console.log("idTwo_z规则:");
    //console.log(idTwo_z);

    //telPeriod规则
    var telPeriod = third_platform.telPeriod;
    //console.log("telPeriod规则:");
    //console.log(telPeriod);    
    
    //telStatus规则
    var telStatus = third_platform.telStatus;
    //console.log("telStatus规则:");
    //console.log(telStatus);    
    
    //execution规则
    var execution = third_platform.execution;
    //console.log("execution规则:");
    //console.log(execution);    
    
    //specialList_c规则
    //var specialList_c = third_platform.specialList_c;
    //console.log("specialList_c规则:");
    //console.log(specialList_c);  */   
    
    //特殊名单核查json
    var countJson = "${isCountJson}";
    var intoPieceId = "${intoPieceId}";
    var idCardNo = "${idCardNo}";
    if(countJson){
    	$("#specialList_c").html("<label class=\"col-sm-4 control-label\" >无风险   &thinsp;&thinsp;&thinsp; <a href='/dataRecord/pyReportAnalysis?intoPieceId="+ intoPieceId +"&idCardNo="+ idCardNo +"' target= '_blank' >特殊名单详情</a></label>");
    }else{
    	$("#specialList_c").html("<label class=\"col-sm-4 control-label\" >有风险   &thinsp;&thinsp;&thinsp; <a href='/dataRecord/pyReportAnalysis?intoPieceId="+ intoPieceId +"&idCardNo="+ idCardNo +"' target= '_blank' >特殊名单详情</a></label>");
    }
    
</script>

<!-- <script src="/bairong/baiRongRule.js"></script>
<script src="/bairong/idTwo_z.js"></script>
<script src="/bairong/telPeriod.js"></script>
<script src="/bairong/telStatus.js"></script>
<script src="/bairong/execution.js"></script> -->
<!-- <script src="/bairong/specialList_c.js"></script> -->
</body>
</html>

