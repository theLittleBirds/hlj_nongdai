<!DOCTYPE html>
<html>
<head>
    <title>详情</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/style.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/font-awesome.min.css" rel="stylesheet">
	<link href="/resource/viewer/viewer.min.css" rel="stylesheet">
    
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/viewer/viewer.min.js"></script>
    <script src="/resource/qiniu/qiniu.js"></script>
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
    						<div class=\"form-horizontal col-lg-10 m-t\">
    							 <div class="col-sm-2" style="margin-top:10px;">
			                         <button class="btn btn-primary" type="button" onclick="file_click()"><i></i>上传</button>
			                         <div style="display: none;">
			                         	<input type="file" id="imageFile" accept="image/jpeg,image/png" multiple="multiple" onchange="postimage()" />
			                         </div>
		                         </div>
		                         <div class="col-sm-6" id="imageProgress" style="display:none;">
		                         	<label class="col-sm-1 control-label" id="detail"></label>
		                         	<div class="progress col-sm-5" style="margin-left:10px;margin-top:5px;">
		                         		<div class="progress-bar progress-bar-info" id="progressUpload" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
		                         		</div>
		                         	</div>
		                         </div>
		                        <input type="hidden" id="id" value="${id!''}"/>
		                        <input type="hidden" id="token" value="${token!''}"/>
		                    </div>
		                    <table class="table table-bordered m-t" style="margin-top:10px;">
		                    	<thead>
		                    		<tr>
		                    			<th>上传日期</th><th>缩略图</th><th>操作</th>
		                    		</tr>
		                    	</thead>
		                    	<tbody id="mediaTable">		                    	
		                    	</tbody>		                    
		                    </table>
    			   </div>
    		  </div>                   
         </div>
	</div>


<script type="text/javascript">
$(document).ready(function(e){
	if(viewer != null){
		viewer = null;
	}	 
	loadImage();
});

var viewer = null;

function loadImage(){
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		url:"/memberoperatemedia/loadimage",
		data:{"id":id}, 
	    error: function(request) {
	    },
	    success: function(data) {
	    	if(data != null){
	    		var htmlInfo="";
	    		for (var int = 0; int < data.length; int++) {
	    			htmlInfo=htmlInfo+"<tr>";
	    			htmlInfo=htmlInfo+"<td>"+changeDateFormat(data[int].creDate)+"</td>";
	    			htmlInfo=htmlInfo+"<td><img src='"+data[int].small+"' data-original='"+data[int].big+"' style='height:50px;width:80px;cursor:pointer;'/></td>";
	    			htmlInfo=htmlInfo+"<td><button type='button' onclick=\"delImage('"+data[int].id+"')\" class='btn btn-danger'>删除</button></td>";
	    			htmlInfo=htmlInfo+"</tr>";
				}
				$("#mediaTable").empty();
	    		$("#mediaTable").append(htmlInfo);	
	    	}
	    	if(data.length == 0){
	    		viewer = null;
	    	}else if(viewer == null){
	    		viewer = new Viewer(document.getElementById('mediaTable'), { 
		    	    url: 'data-original' 
		    	});
	    	}else{
	    		viewer.update();
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

function delImage(id){
	alert(id);
}

function file_click(){
	$("#imageFile").click();
}

//上传图片
function postimage(){
	var token = $("#token").val();
	var files=document.getElementById("imageFile");
	var file=files.files;//每一个file对象对应一个文件。
	if(file.length == 0){
		bootbox.alert("请选择图片");
		return false;
	}
	let options = {
			  quality: 0.60,
			  noCompressIfLarger: true
			};
	var config = {
			  useCdnDomain: true,
			  region: qiniu.region.z0
			};
	var putExtra = {
			  fname: "",
			  params: {},
			  mimeType: [] || null
			};
	var sum = 0;
	var observer = {
			  next(res){
				  $("#imageProgress").show();
				  var order = sum + 1;
				  $("#detail").empty();
				  $("#detail").append(order+"/"+file.length);
				  $("#progressUpload").css("width",res.total.percent + "%")
			  },
			  error(err){
				  $("#detail").append("  "+err.message);
			  },
			  complete(res){
				  $.ajax({
						type: "POST",
						url: "/memberoperatemedia/save",
						data: {"id":$("#id").val(),"hashKey":res.hash},
					    error: function(request) {	
					    },
					    success: function(data) {						    	
					    	if(data.code == 400){
					    		parent.window.bootbox.alert(data.msg);
					    	}
					    	sum = sum + 1;
					    	if(sum == file.length){
					    		$("#imageProgress").hide();
					    		parent.window.bootbox.alert("保存成功");
					    		loadImage();
					    	}				    	
					    }
					});
			  }
			};
	for (var int = 0; int < file.length; int++) {
		qiniu.compressImage(file[int], options).then(data => {
			  var observable = qiniu.upload(data.dist, null, token, putExtra, config)
			  var subscription = observable.subscribe(observer) // 上传开始
		});
	}

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

</script>
</body>
</html>

