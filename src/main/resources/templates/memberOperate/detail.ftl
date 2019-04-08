<!DOCTYPE html>
<html>
<head>
    <title>详情</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/style.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/font-awesome.min.css" rel="stylesheet">
    <link href="/resource/hplus/dynamic-data.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/admin/js/timeOut.js"></script>
</head>
<body>
	<div class="wrapper wrapper-content" style="padding: 10px 10px 0 10px;">
    		<div id="edit_left" class="col-sm-2" style="padding-right: 10px;">
    			<div class="ibox" style="margin-bottom: 0;">
    				
    				<div class="ibox-content clearfix" style="padding: 0px 10px 20px 10px;">
    					<ul class="folder-list m-b-md" style="padding: 0; font-size: 12px;">
    						<li style="height: 30px; line-height: 5px;">
    							<a onclick="goTo('/memberoperate/form')" class="fa fa-database" style="padding-left: 10px; margin-top :10px;">个人信息</a>
    						</li>
    						
    						<li style="height: 30px; line-height: 5px;">
    							<a onclick="goTo('/followitem/form')" class="fa fa-database" style="padding-left: 10px; margin-top :10px;">跟进信息</a>
    						</li>
    						
    						<li style="height: 30px; line-height: 5px;">
    							<a onclick="goTo('/memberoperatemedia/form')" class="fa fa-database" style="padding-left: 10px; margin-top :10px;">图片信息</a>
    						</li>
    					</ul>
    				</div>
    			</div>
    		</div>
    		
    		<div id="edit_right" class="col-sm-10" style="padding-left: 0;">
    			<div class="ibox" style="margin-bottom: 0;">
    				<div class="ibox-content clearfix" style="padding: 0;" id="right_container">
    						<form class="form-horizontal col-sm-10" id="primaryForm" onsubmit="return checkForm()" method="post">
		                         
		                        <input type="hidden" id="id" name="id" value="${id!''}"/>
		                        					                        
		                        <div class="form-group">
		                        	<span style="font-weight: bold; font-size: 16px; padding-left: 15px;">个人信息
		                        	</span>                        
		                        </div>
		                                                
		                        <div class="form-group">
		                             <label class="col-sm-2 control-label">姓名：</label>
		                             <div class="col-sm-3">
		                                 <input type="text" name="name" id="name"
		                                         class="form-control"/>                                 
		                             </div>
		
		                             <label class="col-sm-2 control-label">年龄：</label>
		                             <div class="col-sm-3">
		                                 <input type="text" name="age" id="age"
		                                        class="form-control">
		                             </div>
		                        </div>
		                        
		                        <div class="form-group">		
		                             <label class="col-sm-2 control-label">手机号：</label>
		                             <div class="col-sm-3">
		                                 <input type="text" name="phone" id="phone"
		                                        class="form-control">
		                             </div>
		                        </div>
		                        
		                        <div class="form-group">		
		                             <label class="col-sm-2 control-label">所在区域（村）：</label>
		                             <div class="col-sm-8">
		                                 <input type="text" name="address" id="address"
		                                        class="form-control">
		                             </div>
		                        </div>		                        		                        
		                        
		                        <div class="form-group">		
		                             <label class="col-sm-2 control-label">生产经营详细地址：</label>
		                             <div class="col-sm-8">
		                                 <input type="text" name="operateAddress" id="operateAddress"
		                                        class="form-control">
		                             </div>
		                        </div>
		                        
		                        <div class="form-group">		
		                             <label class="col-sm-2 control-label">经营类型：</label>
		                             <div class="col-sm-8">
		                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="operateType" id="operateType1" value="1">大田作物种植</label>
		                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="operateType" id="operateType2" value="2">经济作物种植</label>
		                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="operateType" id="operateType3" value="3">养殖</label>
		                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="operateType" id="operateType4" value="4">经商</label>
		                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="operateType" id="operateType5" value="5">其他</label>
		                             </div>
		                        </div>		                        		                        
		                        
		                        <div style="display: none;" id="hidden_type1">
		                        	<div class="form-group">
			                        	<span style="font-weight: bold; font-size: 16px; padding-left: 15px;">种植
			                        	</span>                        
			                        </div>
			                        <p style="border-bottom: 1px solid #E7EAEC;"></p>
			                        
			                        <div class="form-group">		
			                             <label class="col-sm-2 control-label">种植种类：</label>
			                             <div class="col-sm-8">
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType1" value="1">水稻</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType2" value="2">玉米</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType3" value="3">小麦</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType4" value="4">蔬菜</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType5" value="5">水果</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="cropType" id="cropType7" value="7">其他</label>
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">种植规模（亩）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="cropScale" id="cropScale"
			                                         class="form-control"/>                                 
			                             </div>
			
			                             <label class="col-sm-2 control-label">种植年限（年）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="cropYears" id="cropYears"
			                                        class="form-control">
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">预计价值（万元）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="cropExpectedValue" id="cropExpectedValue"
			                                         class="form-control"/>                                 
			                             </div>
			
			                             <label class="col-sm-2 control-label">种植周期(月)：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="cropPeriod" id="cropPeriod"
			                                        class="form-control">
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">化肥等投入品投入（万元/周期）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="cropInvestment" id="cropInvestment"
			                                        class="form-control">
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">主要需求：</label>
			                        	<div class="col-sm-8">
			                        		<textarea id="cropMainNeeds" name="cropMainNeeds" class="form-control" rows="3"></textarea>
			                        	</div>
			                        </div>
		                        </div>
		                        
		                        <div style="display: none;" id="hidden_type2">
		                        	<div class="form-group">
			                        	<span style="font-weight: bold; font-size: 16px; padding-left: 15px;">养殖
			                        	</span>                        
			                        </div>
			                        <p style="border-bottom: 1px solid #E7EAEC;"></p>
			                        
			                        <div class="form-group">		
			                             <label class="col-sm-2 control-label">养殖种类：</label>
			                             <div class="col-sm-8">
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType1" value="1">牛</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType2" value="2">猪</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType3" value="3">羊</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType4" value="4">鸡</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType5" value="5">鱼</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="livestockType" id="livestockType6" value="6">其他</label>
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">养殖规模（头/只）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="livestockScale" id="livestockScale"
			                                         class="form-control"/>                                 
			                             </div>
			
			                             <label class="col-sm-2 control-label">养殖年限（年）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="livestockYears" id="livestockYears"
			                                        class="form-control">
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">预计价值（万元）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="livestockExpectedValue" id="livestockExpectedValue"
			                                         class="form-control"/>                                 
			                             </div>
			
			                             <label class="col-sm-2 control-label">养殖周期(月)：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="livestockPeriod" id="livestockPeriod"
			                                        class="form-control">
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">养殖场地类型：</label>
			                             <div class="col-sm-3">
			                                 <select id="livestockSiteType" name="livestockSiteType" class="form-control" style="margin-top:-10px">
			                                 	<option value="">--请选择--</option>
			                                 	<option value="1">租用</option>
			                                 	<option value="2">自建</option>
			                                 </select>                               
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">主要需求：</label>
			                        	<div class="col-sm-8">
			                        		<textarea id="livestockMainNeeds" name="livestockMainNeeds" class="form-control" rows="3"></textarea>
			                        	</div>
			                        </div>
		                        </div>
		                        
		                        <div style="display: none;" id="hidden_type3">
		                        	<div class="form-group">
			                        	<span style="font-weight: bold; font-size: 16px; padding-left: 15px;">经商/其他类型
			                        	</span>                        
			                        </div>
			                        <p style="border-bottom: 1px solid #E7EAEC;"></p>
			                        
			                        <div class="form-group">		
			                             <label class="col-sm-2 control-label">经营类型：</label>
			                             <div class="col-sm-8">
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="nongsellOtherType" id="nongsellOtherType1" value="1">农资销售</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="nongsellOtherType" id="nongsellOtherType2" value="2">批发</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="nongsellOtherType" id="nongsellOtherType3" value="3">饭店等个体经营</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="nongsellOtherType" id="nongsellOtherType4" value="4">上班</label>
			                             	<label style="padding-left: 0;"><input type="checkbox" style="margin-left:20px;" name="nongsellOtherType" id="nongsellOtherType5" value="5">其他</label>
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">经营年限（年）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="noYears" id="noYears"
			                                         class="form-control"/>                                 
			                             </div>
			
			                             <label class="col-sm-2 control-label">年收入（万元）：</label>
			                             <div class="col-sm-3">
			                                 <input type="text" name="noIncome" id="noIncome"
			                                        class="form-control">
			                             </div>
			                        </div>		                        
			                        
			                        <div class="form-group">
			                             <label class="col-sm-2 control-label">经营场地：</label>
			                             <div class="col-sm-3">
			                                <select id="noSite" name="noSite" class="form-control" style="margin-top:-10px">
			                                 	<option value="">--请选择--</option>
			                                 	<option value="1">租用</option>
			                                 	<option value="2">自建</option>
			                                 </select> 
			                             </div>
			                        </div>
			                        
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">主要需求：</label>
			                        	<div class="col-sm-8">
			                        		<textarea id="noMainNeeds" name="noMainNeeds" class="form-control" rows="3"></textarea>
			                        	</div>
			                        </div>
		                        </div>

		                        <div class="clearfix form-actions">
		                            <div class="col-md-12 text-center">		                            
		
		                            <button class="btn btn-w-m btn-success" type="submit">
		                                <i class="ace-icon fa fa-check bigger-110"></i> 提交
		                            </button>                            
		                            </div>
		                        </div>
		                        
		                    </form>
    			   </div>
    		  </div>                   
         </div>
	</div>


<script type="text/javascript">
$(document).ready(function(e){
	var id =  $("#id").val();
	if(id != ''){
		$.ajax({
			type: "POST",
			url: "/memberoperate/selectbyid",
			data:{"id":id},
		    error: function(request) {			    	
		    },
		    success: function(data) {
		    	if(data != null){
		    		$("#name").val(data.name);
		    		$("#age").val(data.age);
		    		$("#phone").val(data.phone);
		    		$("#address").val(data.address);
		    		$("#operateAddress").val(data.operateAddress);
		    		if(data.operateType != null & data.operateType != ""){
		    			var type = data.operateType.split(",");
		    			for(var i=0;i<type.length;i++){
		    				$("#operateType"+type[i]).prop("checked",true);
		    				if(type[i] == 1 | type[i] == 2){
		    					$("#hidden_type1").show();
		    				}else if(type[i] == 3){
		    					$("#hidden_type2").show();
		    				}else if(type[i] == 4 | type[i] == 5){
		    					$("#hidden_type3").show();
		    				}
		    			}
		    		}
		    		if(data.livestockType != null & data.livestockType !=''){
		    			var livestockType = data.livestockType.split(",");
		    			for(var i=0;i<livestockType.length;i++){
		    				$("#livestockType"+livestockType[i]).prop("checked",true);
		    			}
		    		}
		    		$("#livestockScale").val(data.livestockScale);
		    		$("#livestockYears").val(data.livestockYears);
		    		$("#livestockExpectedValue").val(data.livestockExpectedValue);
		    		$("#livestockPeriod").val(data.livestockPeriod);
		    		$("#livestockSiteType").val(data.livestockSiteType);
		    		$("#livestockMainNeeds").val(data.livestockMainNeeds);
		    		if(data.cropType !=null & data.cropType != ''){
		    			var cropType = data.cropType.split(",");
		    			for(var i=0;i<cropType.length;i++){
		    				$("#cropType"+cropType[i]).prop("checked",true);
		    			}
		    		}
		    		$("#cropScale").val(data.cropScale);
		    		$("#cropYears").val(data.cropYears);
		    		$("#cropExpectedValue").val(data.cropExpectedValue);
		    		$("#cropPeriod").val(data.cropPeriod);
		    		$("#cropInvestment").val(data.cropInvestment);
		    		$("#cropMainNeeds").val(data.cropMainNeeds);
		    		if(data.nongsellOtherType != null & data.nongsellOtherType != ''){
		    			var nongsellOtherType = data.nongsellOtherType.split(",");
		    			for(var i=0;i<nongsellOtherType.length;i++){
		    				$("#nongsellOtherType"+nongsellOtherType[i]).prop("checked",true);
		    			}
		    		}
		    		$("#noYears").val(data.noYears);
		    		$("#noIncome").val(data.noIncome);
		    		$("#noSite").val(data.noSite);
		    		$("#noMainNeeds").val(data.noMainNeeds);
		    	}else{
		    		parent.window.bootbox.alert("获取详情失败");
		    	}
		    }
		});
	}
});

function checkForm(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/memberoperate/saveorupdate",
		data:$('#primaryForm').serialize(), //formid
	    error: function(request) {
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if($("#id").val()==""){
	    		$("#id").val(data.id);
	    	}
	    }
	});
	return false;
}

function goTo(url){
	var id = $("#id").val();
	if(id == '' | id == null){
		parent.window.bootbox.alert("请先保存基本信息");
		return ;
	}
	var to = url + "?id=" +id;
	window.location.href= to;
}

$("#operateType1,#operateType2").click(function(){
	if($("#operateType1").is(':checked') | $("#operateType2").is(':checked')){
		$("#hidden_type1").show();
	}else{
		$("#hidden_type1").hide();
	}		
});

$("#operateType3").click(function(){
	if($("#operateType3").is(':checked')){
		$("#hidden_type2").show();
	}else{
		$("#hidden_type2").hide();
	}		
});

$("#operateType4,#operateType5").click(function(){
	if($("#operateType4").is(':checked') | $("#operateType5").is(':checked')){
		$("#hidden_type3").show();
	}else{
		$("#hidden_type3").hide();
	}		
});

</script>
</body>
</html>

