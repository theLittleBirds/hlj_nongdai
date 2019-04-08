<!DOCTYPE html>
<html>
<head>
    <title>审核</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
					 <form class="form-horizontal col-lg-8 m-t" onsubmit="return checkForm()" id="lastForm" style="margin-top: 20px;"
                          method="post">
                        <input type="hidden" name="id" id="id" value="${id}">                       
                        <input type="hidden" name="wrong" id="wrong" value="${wrong}">                       
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">审核结果：</label>
                             <div class="col-sm-2">
                             		<select  id="nodeType" name="nodeType" class="form-control" onchange="nodeChange()">
	                                 	<option value="1">提交</option>
	                                 	<option value="5">回退</option>
	                                 	<option value="10">驳回</option>
                                 	</select>
                             </div>
                             <label class="col-sm-2 control-label">提交节点：</label>
                             <div class="col-sm-2">
                             	<select id="nextNodeId" name="nextNodeId" class="form-control">
                                 	
                                </select>
                                <input type = "hidden" name="pcId" id="pcId"/>
                             </div>
                        </div>

                        <div class="clearfix form-actions">
                            <div class="col-md-10 text-center">
                            <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                            </button>

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
</div>


<script type="text/javascript">
$(document).ready(function(e){
	nodeChange();
});


function nodeChange(){
		var id = $("#id").val();
		var result = $("#nodeType").val();
		var wrong = $("#wrong").val();
		if(result==1){
			if(wrong==0){
				$.ajax({
					type: "POST",
					url: "/flowmgr/getnodes",
					data:{"intoPieceId":id,"type":result},
				    error: function(request) {			    	
				    },
				    success: function(data) {
				    	if(data.code == 200){
				    		var arr = data.nodes;
				    		var html = "";
				    		if(arr != null){
				    			for(var i=0;i<arr.length;i++){
					    			html = html+"<option value='"+arr[i].nodeId+"'>"+arr[i].cname+"</option>"
					    		}
				    		}	    		
				    		$("#nextNodeId").empty();
				    		$("#nextNodeId").append(html);
				    		$("#pcId").val(data.pcId);
				    	}else{
				    		alert(data.msg);
				    	}
				    }
				});
			}else if(wrong==1){
				parent.window.bootbox.alert("请选择服务费扣款方式！");
				$("#nextNodeId").empty();
			}else{
				parent.window.bootbox.alert("服务费未支付成功，请等待！");
				$("#nextNodeId").empty();
			}
		}else{
			$.ajax({
				type: "POST",
				url: "/flowmgr/getnodes",
				data:{"intoPieceId":id,"type":result},
			    error: function(request) {			    	
			    },
			    success: function(data) {
			    	if(data.code == 200){
			    		var arr = data.nodes;
			    		var html = "";
			    		if(arr != null){
			    			for(var i=0;i<arr.length;i++){
				    			html = html+"<option value='"+arr[i].nodeId+"'>"+arr[i].cname+"</option>"
				    		}
			    		}	    		
			    		$("#nextNodeId").empty();
			    		$("#nextNodeId").append(html);
			    		$("#pcId").val(data.pcId);
			    	}else{
			    		alert(data.msg);
			    	}
			    }
			});
		}
	
}

function checkForm(){
	var nextNodeId = $("#nextNodeId").val();
	if(nextNodeId == ""){
		parent.window.bootbox.alert("节点不能为空");
		return false;
	}
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>提交审核中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/examine/guaranteepushsave",
		data:$('#lastForm').serialize(), //formid
	    error: function(request) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert("系统出错了");
	    },
	    success: function(data) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		window.history.go(-1);
	    	}
	    }
	});
	return false;
}
</script>
</body>
</html>

