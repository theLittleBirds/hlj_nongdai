<!DOCTYPE html>
<html>
<head>
    <title>文档信息</title>
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
                    <div class="ibox-content clearfix col-sm-8" style="padding-top: 0px;margin-left: 20px;">
                    <form class="form-horizontal" id="fileForm">
		                <div class="form-group">
				    	<div class="col-sm-4">
				    	<button class="btn btn-primary" type="button" onclick="fileupload()"><i></i>上传</button>
					    	<div style="display: none;">
					    	<input type="file" name="file" id="file" onchange="postfile()" />
					    	</div>
				    	</div>
				    	<input type="hidden" id="intoPieceId" name="intoPieceId" value="${id}"/>
				    	</div>
					</form>
			    	<table class="table table-bordered">
			    	<thead>
			    	<tr>
			    	<th>文档名称</th><th>上传日期</th><th>查看</th>
			    	</tr>
			    	</thead>
			    	<tbody id="fileTable">
			    	</tbody>
					</table>
					
					</div>
                   <div class="col-md-8 text-center">
                       <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                           <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                       </button>                          
                   </div>             
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
$(document).ready(function(e){
	refreshFileMedia();
});

//加载文档列表
function refreshFileMedia(){
	$.ajax({
		type: "POST",
		url: "/media/filelist",
		//type=4 文档
		data: {"intoPieceId":$("#intoPieceId").val(),"type":"4"},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	$("#fileTable").empty();
	    	if(data != null){
	    		var htmlInfo="";
	    		for (var int = 0; int < data.length; int++) {
	    			htmlInfo = htmlInfo+"<tr>";	    			
	    			htmlInfo = htmlInfo+"<td>"+data[int].name+"</td>";
					htmlInfo = htmlInfo+"<td>"+changeDateFormat(data[int].creDate)+"</td>";
					htmlInfo = htmlInfo+"<td><a href='/media/load?id="+data[int].id+"' target='_blank'>下载</a></td>";
					htmlInfo = htmlInfo+"</tr>";
				}
	    		$("#fileTable").append(htmlInfo);
	    	}
	    }
	});
}

function fileupload(){
	$("#file").click();
}
function postfile(){
	var formData = new FormData($("#fileForm")[0]); 
	$.ajax({
		url: "/media/filesave", 
        type: "POST", 
        data: formData, 
        async: false, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		refreshFileMedia();
	    	}
	    }
	});
}

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}
</script>
</body>
</html>

