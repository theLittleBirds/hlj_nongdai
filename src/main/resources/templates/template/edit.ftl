<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>模板编辑</title>
    
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/summernote/summernote.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
	<script src="/resource/summernote/summernote.js"></script>
	<script src="/resource/summernote/lang/summernote-zh-CN.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">                   
                    <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;">	
                   	<div class="form-group">
                   		<label class="col-sm-1 control-label">名称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="title" name="title">
						</div>
				 	</div>
				 	
				 	<div class="form-group">								
						 <label class="col-sm-1 control-label">金融机构：</label>
                         <div class="col-sm-3">
                             <select  id="finsId" name="finsId" class="form-control">
                             	<option value="">--请选择--</option> 
                             	<#list finsIdList as one>   
            					  <option value="${one.finsId}">${one.cname}</option>         
        						</#list>                                         
                              </select>                                 
                         </div>
                         <label class="col-sm-1 control-label">状态：</label>
                         <div class="col-sm-3">
                             <select  id="isOpean" name="isOpean" class="form-control">
                             	<option value="1">启用</option>
                             	<option value="2">禁用</option>                                        
                              </select>                                 
                         </div>
				 	</div>
				 	
				 	<div class="form-group">								
						 <label class="col-sm-1 control-label">甲方：</label>
                         <div class="col-sm-3">
                             <select  id="first" name="first" class="form-control">
                             	<option value="">--请选择--</option> 
                             	<#list signatoryList as one>   
            					  <option value="${one.id}">${one.name}</option>         
        						</#list>                                         
                              </select>                                 
                         </div>
                         <label class="col-sm-1 control-label">甲方标识：</label>
                         <div class="col-sm-3">
                             <input type="text" class="form-control" id="firstMark" name="firstMark">                           
                         </div>
				 	</div>
				 	
				 	<div class="form-group">								
                         <label class="col-sm-1 control-label">乙方：</label>
                         <div class="col-sm-3">
                             <select  id="second" name="second" class="form-control">
                             	<option value="">--请选择--</option>
                             	<option value="1">主借人</option>
                             	<option value="2">借款主体</option>                                        
                              </select>                                 
                         </div>
                         <label class="col-sm-1 control-label">乙方标识：</label>
                         <div class="col-sm-3">
                             <input type="text" class="form-control" id="secondMark" name="secondMark">                           
                         </div>
				 	</div>
				 	
				 	<div class="form-group">								
						 <label class="col-sm-1 control-label">丙方：</label>
                         <div class="col-sm-3">
                             <select  id="third" name="third" class="form-control">
                             	<option value="">--请选择--</option> 
                             	<#list signatoryList as one>   
            					  <option value="${one.id}">${one.name}</option>         
        						</#list>                                         
                              </select>                                 
                         </div>
                          <label class="col-sm-1 control-label">丙方标识：</label>
                         <div class="col-sm-3">
                             <input type="text" class="form-control" id="thirdMark" name="thirdMark">                           
                         </div>
				 	</div>
				 	
                   	<div class="form-group" style="padding-left: 5%;">
                   		<div class="col-sm-2">
				  			<table class="table table-bordered m-t">
				  				<thead>
				  					<tr><th>变量</th><th>含义</th></tr>
				  				</thead>
				  				<tbody>
				  					<tr><td>name</td><td>姓名</td></tr>
				  					<tr><td>idCard</td><td>身份证号</td></tr>
				  					<tr><td>phone</td><td>手机号</td></tr>
				  					<tr><td>co_name</td><td>联合借款人</td></tr>
				  					<tr><td>co_idCard</td><td>联合借款人身份证号</td></tr>
				  					<tr><td>co_phone</td><td>联合借款人手机号</td></tr>				  					
				  					<tr><td>address</td><td>住址</td></tr>
				  					<tr><td>bank</td><td>开户行</td></tr>
				  					<tr><td>bankCardNo</td><td>银行卡号</td></tr>
				  					<tr><td>capital</td><td>借款金额</td></tr>
				  					<tr><td>upperCapital</td><td>借款金额大写</td></tr>
				  					<tr><td>term</td><td>借款期限</td></tr>
				  					<tr><td>rate</td><td>利率</td></tr>
				  					<tr><td>overdueRate</td><td>逾期天利率</td></tr>
				  					<tr><td>serviceFee</td><td>服务费</td></tr>
				  					<tr><td>serviceRate</td><td>服务费率</td></tr>
				  					<tr><td>contactNo</td><td>合同编号</td></tr>
				  					<tr><td>lender</td><td>出借人</td></tr>
				  					<tr><td>start_date</td><td>借款开始日期</td></tr>
				  					<tr><td>end_date</td><td>借款结束日期</td></tr>
				  					<tr><td>repaymentDay</td><td>还款日</td></tr>
				  					<tr><td>today</td><td>当前日期</td></tr>
				  				</tbody>				  								  		
				  			</table>
				  		</div>	
                   		<div class="col-sm-10">
				  			<textarea id="summernote" name="editordata"></textarea>				  			
				  		</div>				  					  		
				 	</div>
				 	
				 	<input type="hidden" id="id" name="id" value="${id}">
				 	
				 	 <div class="clearfix form-actions">
                           <div class="col-md-12 text-center">
                           <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                               <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                           </button>

                           <button class="btn btn-w-m btn-success" type="button" onclick="checkForm()">
                               <i class="ace-icon fa fa-check bigger-110"></i> 保存
                           </button>                            
                           </div>
                    </div> 
                    
                  </form> 
				</div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

$(function () {  
	//var dpi = js_getDPI();
	$('#summernote').summernote({
		toolbar: [
					['font', ['fontname','fontsize']],
		          	['style', ['bold', 'italic', 'underline', 'clear']],		          
		          	['para', ['ul', 'ol', 'paragraph']],
		          	['misc',['codeview','help']]
		        ],
		fontNames: ['宋体', '黑体'],
		width:680,
		minHeight: 500,
		lang:'zh-CN'
		});
	$('#summernote').summernote('fontName', '宋体');
	
	var id = $("#id").val();
	if(id!= ''){
		$.ajax({
			type: "POST",
			url: "/contacttemplate/selectbyid",
			data:{"id":id},
		    error: function(request) {			    	
		    },
		    success: function(data) {
		    	if(data != ''){
		    		$('#summernote').summernote('code', data.content);
		    		$("#title").val(data.title);
		    		$("#isOpean").val(data.isOpean);
		    		$("#finsId").val(data.finsId);
		    		$("#first").val(data.first);
		    		$("#firstMark").val(data.firstMark);
		    		if(data.second !='' & data.second != null){
		    			$("#second").val(data.second);
		    			$("#secondMark").val(data.secondMark);
		    		}
		    		if(data.third !='' & data.third != null){
		    			$("#third").val(data.third);
		    			$("#thirdMark").val(data.thirdMark);
		    		}		    		
		    	}
		    }
		});
	}
	
}); 
function js_getDPI() {
	var arrDPI = new Array;
	if (window.screen.deviceXDPI) {
		arrDPI[0] = window.screen.deviceXDPI;
		//arrDPI[1] = window.screen.deviceYDPI;
	}else {
	var tmpNode = document.createElement("DIV");
		tmpNode.style.cssText = "width:1in;height:1in;position:absolute;left:0px;top:0px;z-index:99;visibility:hidden";
		document.body.appendChild(tmpNode);
		arrDPI[0] = parseInt(tmpNode.offsetWidth);
		//arrDPI[1] = parseInt(tmpNode.offsetHeight);
		tmpNode.parentNode.removeChild(tmpNode);
	}
	return arrDPI[0];
}
function checkForm(){
	var content = $('#summernote').summernote('code');
	var text = $(content).text();
	var title = $("#title").val();
	var id = $("#id").val();
	var finsId = $("#finsId").val();
	var isOpean = $("#isOpean").val();
	var first = $("#first").val();
	var firstMark = $("#firstMark").val();
	var second = $("#second").val();
	var secondMark = $("#secondMark").val();
	var third = $("#third").val();
	var thirdMark = $("#thirdMark").val();
	if(text == ""){
		parent.window.bootbox.alert("模板内容不能为空");
		return ;
	}
	if(title == ""){
		parent.window.bootbox.alert("模板标题不能为空");
		return ;
	}
	if(finsId == ""){
		parent.window.bootbox.alert("金融机构不能为空");
		return ;
	}
	if(first == ""){
		parent.window.bootbox.alert("甲方不能为空");
		return ;
	}
	if(firstMark == ""){
		parent.window.bootbox.alert("甲方标识不能为空");
		return ;
	}
	if(second != "" & secondMark == ''){
		parent.window.bootbox.alert("乙方标识不能为空");
		return ;
	}
	if(third != "" & thirdMark == ''){
		parent.window.bootbox.alert("丙方标识不能为空");
		return ;
	}
	var arr = text.split("{");
	var variable = "";
	if(arr.length>1){
		for (var i = 1; i < arr.length; i++) {
			if(arr[i] != ''){
				var text = arr[i].split("}");
				if(text.length == 2){
					if(variable.indexOf(text[0]) == -1){
						variable = variable + text[0] + ",";
					}					
				}
			}
		}
	}
	$.ajax({
		type: "POST",
		url: "/contacttemplate/save",
		data:{"id":id,"content":content,"title":title,"finsId":finsId,"isOpean":isOpean,"variable":variable,"first":first,"firstMark":firstMark,"second":second,"secondMark":secondMark,"third":third,"thirdMark":thirdMark},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		window.history.go(-1);
	    	}
	    }
	});
	
}
</script>
</body>
</html>

