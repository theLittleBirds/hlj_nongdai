<!DOCTYPE html>
<html>
<head>
    <title>跟进信息</title> 
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/hplus/dynamic-data.css" rel="stylesheet">
    <link href="/resource/hplus/style.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script> 
    <script src="/resource/bootbox/bootbox.js"></script>  
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-xs-12">
            <div class="ibox float-e-margins">
              <div class="ibox-content clearfix" style="padding: 0px;">  
              	<form id="dataForm" class="form-horizontal col-xs-12" onsubmit="return postdynamicdata()">
              		<div class="info" style="background-color: #FFF; padding-top: 10px;">
              			<ul id="title">
						
						</ul>
						<div id="data" style="padding: 0 20px 10px 20px; overflow-y: scroll;">
						
						</div>
					</div>
					<div id="action" class="clearfix form-actions" style="margin: 10px 0;">
						<div class="col-md-9 text-center">
						<button class="btn btn-w-m btn-success" type="submit"><i class="ace-icon fa fa-check bigger-110"></i>提交</button>
						</div>
					</div>
					<input type="hidden" id = "token" value="${token}" />
					<input type="hidden" id = "followTypeId"/>
					<input type="hidden" id="id" value="${id!''}"/>
              	</form>              	
              </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

$(function () {  
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/nj/operateext/selectall",
		beforeSend: function (xhr) {  
		       xhr.setRequestHeader("token", $("#token").val());  
		},
		data : {"id":id},
	    error: function(request) {					
	    },
	    success: function(data) {	    	
	    	var html = "";
	    	for(var i=0;i<data.length;i++)
	    	{	
	    		if(i==0){
	    			html = html + "<li onclick=\"changeTab('"+data[i].id+"', this);\" class=\"active\" style=\"display:inline-block;\">"+data[i].name+"</li>";
	    		}else{
	    			html = html + "<li onclick=\"changeTab('"+data[i].id+"', this);\" style=\"display:inline-block;\">"+data[i].name+"</li>";
	    		}	
	    	}
	    	$("#title").append(html);
	    	//初始化第一个标签
	    	if(data.length != 0){
	    		loadData(data[0].id);
	    	}
	    }
	});
}); 

//动态数据切换sheet页
function changeTab(followTypeId, obj) {
    if (obj) {
        $(".info > ul").find("li").each(function (i, d) {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active");
            }
        });
        $(obj).addClass("active");
    }
    loadData(followTypeId);
}
//加载动态数据
function loadData(followTypeId){
    $("#followTypeId").val(followTypeId);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/nj/operateext/loaddata", 
		beforeSend: function (xhr) {  
		       xhr.setRequestHeader("token", $("#token").val());  
		},
		data:{"type":followTypeId,"id":$("#id").val()},
	    error: function(request) {					
	    },
	    success: function(data) {
	    	$("#data").empty();
	    	var html = "";
	    	if(data.length == 0){
	    		html = html +"<p style='text-align: center; margin-top: 20px; font-size: 15px;'>没有数据！</p>";
	    	}else{
	    		html = html +"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;\"><tbody>";
	    		for(var i=0;i<data.length;i++)
		    	{	
	    			html = html +"<tr>";
	    			//标准
	    			if(data[i].inputType == 1){
	    				html = html +"<td style=\"background-color: #F9F9F9;\"><b>"+data[i].name+"</b></td><td colspan=\"3\"><input type=\"text\" name=\""+data[i].id+"\"";
	    				if(data[i].itemValue != null){
	    					html = html +" value="+data[i].itemValue;
	    				}
	    				if(data[i].inputWidth != null & data[i].inputWidth != ''){
	    					html.append(" style=\"width:"+data[i].inputWidth+"px;\"");
	    				}
	    				html = html +" /><span class=\"unit\">"+data[i].unit+"</span></td>";
	    			}
	    			//单选
	    			if(data[i].inputType == 2){
	    				html = html +"<td style=\"background-color: #F9F9F9;\"><b>"+data[i].name+"</b></td><td colspan=\"3\"><div class='checkbox i-checks'>";
	    				if(data[i].inputOption != null & data[i].inputOption != ''){
	    					var option = data[i].inputOption.split(",");  
	    					for (var j = 0; j < option.length; j++) {
	    						var one = option[j].split(":");
	    						html = html +"<label><input type=\"radio\" name=\""+data[i].id+"\" value=\""+one[1]+"\"";
								if(data[i].itemValue == one[1]){
									html = html +" checked=\"checked\"";
								}
								html = html +"/>"+one[0]+"</label>";	
							}    		
	    				}
	    				html = html +"</div></td>";
	    			}
	    			//复选
	    			if(data[i].inputType == 3){
	    				html = html +"<td style=\"background-color: #F9F9F9;\"><b>"+data[i].name+"</b></td><td colspan=\"3\"><div class='checkbox i-checks'>";
	    				if(data[i].inputOption != null & data[i].inputOption != ''){
	    					var option = data[i].inputOption.split(",");  
	    					var result = data[i].itemValue+",";
	    					for (var j = 0; j < option.length; j++) {
	    						var one = option[j].split(":");
	    						html = html +"<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\" name=\""+data[i].id+"\" value=\""+one[1]+"\"";
								if(result.indexOf(one[1]+",") != -1){
									html = html +" checked=\"checked\"";
								}
								html = html +"/>"+one[0]+"</label>";
							}    		
	    				}
	    				html = html +"</div></td>";
	    			}
	    			html = html +"</tr>";
		    	}
	    		html = html +"</tbody></table>";
	    	}
	    	$("#data").append(html);
	    }
	});
}


//提交动态数据表单
function postdynamicdata(){
	var followTypeId = $('#followTypeId').val();
	var id = $('#id').val();
	var data = $('#dataForm').serializeArray();
	var strData = '';
	for (var int = 0; int < data.length; int++) {
		if(int == 0){
			strData = data[int].name +":" +data[int].value;
		}else{
			strData = strData + "," + data[int].name +":" +data[int].value ;
		}
	}
	$.ajax({
        type: "POST",
        dataType: "json",
        url: "/nj/operateext/save",
        beforeSend: function (xhr) {  
		       xhr.setRequestHeader("token", $("#token").val());  
		},
        data : {"followTypeId":followTypeId,"id":id,"data": strData},
        success: function(result) {       	
        	bootbox.alert(result.msg);
        }
    });
    return false;
}
</script>
</body>
</html>

