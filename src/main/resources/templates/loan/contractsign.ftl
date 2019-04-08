<!DOCTYPE html>
<html>
<head>
    <title>合同签署</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-8">
            <div class="ibox">
                <div class="ibox-content clearfix">
                	<div class="row" style="padding: 10px;margin-left: 10px;">                    	
                    	<table class="table table-bordered m-t" style="margin-top: 10px;">
         					<thead><tr><th>合同名称</th><th>签约人</th><th>状态</th><th>操作</th></tr></thead>
         					<tbody id="tableid">
         					
         					</tbody>
                    	</table>   
                    	<input id="id" type="hidden" value="${id}">                
                    </div>
                </div>
                <div class="clearfix form-actions">
                    <div class="col-sm-12 text-center" id="btn_div">
                                                                  
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
$(document).ready(function(e){
	loadTable();
});

function loadTable(){
	var id = $("#id").val();
    $.ajax({
		type: "POST",
		url: "/loan/contractstatus",
		data:{"id":id},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		$("#btn_div").empty();
	    		$("#tableid").empty();
	    		var arr = data.arr;
	    		var flag = data.flag;
	    		var html = "";
	    		if(flag == 1){
	    			for(var i=0;i<arr.length;i++){
	    				html = html + "<tr><td>"+arr[i].name+"</td><td>"+arr[i].signatories+"</td>";
	    				if(arr[i].status == 0){
	    					html = html + "<td>上传失败</td><td></td></tr>";
	    				}else if(arr[i].status == 1){
	    					html = html + "<td>上传中</td><td></td></tr>";
	    				}else if(arr[i].status == 2){
	    					html = html + "<td>签约中</td><td><a style='cursor:pointer;' onclick=\"signDetail('"+arr[i].id+"')\">详情</a></td></tr>";
	    				}else if(arr[i].status == 3){
	    					html = html + "<td>签约完成</td><td><a style='cursor:pointer;' href='/loan/loadpdf?id="+arr[i].id+"'>下载</a></td></tr>";
	    				}else{
	    					html = html + "<td></td><td></td></tr>";
	    				}
	    			}
	    		}else if(flag == 2){
	    			for(var i=0;i<arr.length;i++){	    				
	    				html = html + "<tr><td>"+arr[i].name+"</td><td>"+arr[i].signatories+"</td><td></td><td></td></tr>";	    				
	    			}
	    			$("#btn_div").append("<button class='btn btn-w-m btn-success' type='button' id='push_btn' onclick='sign()'><i class='ace-icon fa fa-check bigger-110'></i>签署</button>");
	    		}	    		
	    		$("#tableid").append(html);
	    	}else{
		    	parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});	
}

function signDetail(id){
	$.ajax({
		type: "POST",
		url:"/loan/contactsignatories",
		data:{"id":id}, 
	    error: function(request) {
	    	parent.window.bootbox.alert("获取数据失败");
	    },
	    success: function(data) {
	        if(data != ""){
	        	if(data.code == 200){
	        		var html = "<table class='table table-bordered m-t'><thead><tr><th>姓名</th><th>手机号</th><th>状态</th><th>操作</th></tr></thead><tbody>";
	        		if(data.list != "" & data.list != null){
	        			var list = data.list;
	        			for (var i = 0; i < list.length; i++) {
	        				html = html + "<tr><td>"+list[i].fullName+"</td><td>"+list[i].mobile+"</td>";
	        				if(list[i].status == 0){
	        					html = html + "<td>待签</td>";
	        				}else if(list[i].status == 1){
	        					html = html + "<td>已签</td>";
	        				}else if(list[i].status == 2){
	        					html = html + "<td>拒签</td>";
	        				}else if(list[i].status == 3){
	        					html = html + "<td>发送失败</td>";
	        				}else{
	        					html = html + "<td></td>";
	        				}
	        				if(list[i].status == 0 & list[i].identityType == 1){
	        					html = html + "<td><a style='cursor:pointer;' onclick=\"sendSms('"+list[i].id+"')\">发送短信</a></td>";
	        				}else{
	        					html = html + "<td></td>";
	        				}
	        				html = html + "</tr>";
						}
	        		}
	        		html = html + "</tbody></table>";
	        		bootbox.dialog({
	        			message: html,
	        			backdrop: false,
	        			closeButton: false,
	        			className: "smsModal",
	        			buttons: {	        			     
	        			    // 其中一个按钮配置
	        			    success: {   
	        			      // 按钮显示的名称
	        			      label: "关闭",	        			       
	        			      // 按钮的类名
	        			      className: "btn-success",	        			       
	        			      // 点击按钮时的回调函数
	        			      callback: function() {
	        			    	  $(".smsModal").remove();
	        			      }
	        			    }
	        			}
	        		});
	        	}else{
	        		parent.window.bootbox.alert(data.msg);
	        	}
	        }else{
	        	parent.window.bootbox.alert("获取数据失败");
	        }    	
	    }
	});
}

//发送签约短信
function sendSms(id){
	var loanId = $("#id").val();
	$.ajax({
		type: "POST",
		url:"/loan/sendsms",
		data:{"loanId":loanId,"id":id}, 
	    error: function(request) {
	    	parent.window.bootbox.alert("发送失败");
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);    	
	    }
	});
}

//上天合同到君子签
function sign(){
	var id = $("#id").val();
	$("#push_btn").attr("disabled", "disabled");
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>上传电子合同中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	$.ajax({
		type: "POST",
		url:"/loan/pushsign",
		data:{"id":id}, 
	    error: function(request) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert("上传失败");
	    },
	    success: function(data) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert(data.msg);
	    	loadTable();	    	
	    }
	});
}
</script>
</body>
</html>