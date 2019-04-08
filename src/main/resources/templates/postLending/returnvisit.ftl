<!DOCTYPE html>
<html>
<head>
    <title>回访记录</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form class="form-horizontal col-lg-8 m-t" id="primaryForm" onsubmit="return checkForm()" style="margin-top: 50px;"
                          method="post">
                        <input type="hidden" id="id" value="${id}">                                                   
                        
                        <#list visitList as one> 
                        <div class="form-group">
                             <label class="col-sm-2 control-label">${one.creDate}：</label>
                             <div class="col-sm-8">
                                 <textarea class="form-control">${one.content}</textarea>
                             </div>
                      	</div>
                      	</#list> 
                      	
                        <div class="form-group">
                             <label class="col-sm-2 control-label">回访记录：</label>
                             <div class="col-sm-8">
                                 <textarea id="content" class="form-control" maxlength="300"></textarea>                              
                             </div>
                        </div>
                        
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
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
<script src="/resource/echarts/echarts.min.js"></script>
<script type="text/javascript">
function checkForm(){
	var id = $("#id").val();
	var content = $("#content").val();
	if(id == ""){
		parent.window.bootbox.alert("借款标识为空");
		return false;
	}
	if(content == ""){
		parent.window.bootbox.alert("回访记录为空");
		return false;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/returnvisit/save",
		data:{"loanId":id,"content":content}, //formid
	    error: function(request) {
	    	parent.window.bootbox.alert("保存失败");
	    },
	    success: function(data) {
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

