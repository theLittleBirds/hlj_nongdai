<!DOCTYPE html>
<html>
<head>
    <title>审核</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
					 <form class="form-horizontal col-lg-8 m-t" onsubmit="return checkForm()" id="dataForm" style="margin-top: 20px;"
                          method="post">
                        <input type="hidden" name="id" id="id" value="${id!''}">                       
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">出借人：</label>
                             <div class="col-sm-3">
                             		<select  id="lenderId" name="lenderId" class="form-control">
	                                 	${lender}	                                 	
                                 	</select>
                             </div>                             
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">合同名称：</label>
                             <div class="col-sm-3">
                             		<input type="text" id="name" name="name"
                                        class="form-control">
                             </div>                             
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">文件名：</label>
                             <div class="col-sm-3">
                             		<input type="text" id="fileName" name="fileName"
                                        class="form-control">
                             </div>                             
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">状态：</label>
                             <div class="col-sm-3">
                             		<select  id="isOpean" name="isOpean" class="form-control">
	                                 	<option value="1">启用</option>	 
	                                 	<option value="2">禁用</option>	                                	
                                 	</select>
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
	var id = $("#id").val();
	if(id != ''){
		$.ajax({
			type: "POST",
			url:"/contactdetail/getbyid",
			data:{"id":id},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	if(data.code == 200){
		    		if(data.para != null){
		    			$("#lenderId").val(data.para.lenderId);
		    			$("#name").val(data.para.name);
		    			$("#fileName").val(data.para.fileName);
		    			$("#isOpean").val(data.para.isOpean);
		    		}
		    	}else{
			    	parent.window.bootbox.alert(data.msg,"");
		    	}				    	
		    }
		});
	}
	
});

function checkForm(){
	var lenderId = $("#lenderId").val();
	var name = $("#name").val();
	var fileName = $("#fileName").val();
	var isOpean = $("#isOpean").val();
	if(lenderId == ''){
		parent.window.bootbox.alert("请选择出借人");
		return false;
	}
	if(name == ''){
		parent.window.bootbox.alert("合同名称必填");
		return false;
	}
	if(fileName == ''){
		parent.window.bootbox.alert("文件名必填");
		return false;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/contactdetail/save",
		data:$('#dataForm').serialize(),
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg,"");
	    	if(data.code == 200){
	    		window.location.href = "/contactdetail/page";
	    	}				    	
	    }
	});
	return false;
}
</script>
</body>
</html>

