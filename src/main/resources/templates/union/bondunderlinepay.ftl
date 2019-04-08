<!DOCTYPE html>
<html>
<head>
    <title>线下扣保证金</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
<style type="text/css">
td{
	width:"25%";
}

</style>    
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form class="form-horizontal col-sm-12 m-t" id="loanForm" onsubmit="return checkForm()" style="margin-top: 30px;"
                          method="post">
                        <input type="hidden" name="id" id="id" value="${id}">
						<input type="hidden" id="intoPieceId" value="${intoPieceId}">
                        <div class="form-group">
                             <label class="col-sm-2 control-label">支付金额：</label>
                             <div class="col-sm-3">
                                 <input type="text"  value="${money}" readonly  class="form-control"/>                                 
                             </div>
                             
                             <label class="col-sm-2 control-label">打款日期：</label>
                             <div class="col-sm-3 input-group date" id ='payment'>
                                    <input id="paymentDate" type="text" name="paymentDate" class="form-control required" />
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>
                             </div>
                        </div>  
                        
                        <div class="form-group">
                        	<button class="btn btn-primary" type="button" onclick="file_click()" style="margin-left: 15%;"><i></i>上传</button>
                        	<div style="display: none;">
                        		<input type="file" id="imageFile" accept="image/jpeg,image/png" multiple="multiple" onchange="postimage()">
                        	</div>                       
                        </div>                             
						
						<div class="form-group">
							<table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
								<tbody id = "imageList">
									
								</tbody>
							</table>
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
                    <input type="hidden" name="token" id="token" value="${token}">
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
<script src="/resource/bootstrap/js/moment.js"></script> 
<script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
<script src="/resource/qiniu/qiniu.js"></script>
<script src="/admin/js/timeOut.js"></script>

<script type="text/javascript">
$(document).ready(function(e){ 
	$("#payment").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	
	refreshMedia();
}); 

function checkForm(){	
	var paymentDate = $("#paymentDate").val();
	if(paymentDate == ''){
		parent.window.bootbox.alert("请选择扣款日期");
		return false;
	}
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/stationbond/underlinepaysave",
		data: {"id":id,"paymentDate":paymentDate},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code == 200){
	    		window.location.href="/stationbond/listpage";
	    	}
	    }
	});
	return false;
}

function file_click(){
	$("#imageFile").click();
}

function postimage(){
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>上传中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	var files=document.getElementById("imageFile");
	var file=files.files;//每一个file对象对应一个文件。
	if(file.length == 0){
		parent.window.bootbox.alert("请选择图片");
		return false;
	};
	var token = $("#token").val();
	var options = {
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
			  next:function(res){

			  },
			  error:function(err){
				  parent.window.bootbox.alert("保存失败"+err.message);
			  },
			  complete:function(res){
				  $.ajax({
						type: "POST",
						url: "/picture/save",
						data: {"intoPieceId":$("#intoPieceId").val(),"fileType": 2,"hashKey":res.hash},
					    error: function(request) {	
					    },
					    success: function(data) {						    	
					    	sum = sum + 1;
					    	if(sum == file.length){
					    		parent.removeBootBox("bootboxModal");
					    		parent.window.bootbox.alert("保存成功");
					    		refreshMedia();
					    	}				    	
					    }
					});
			  }
			};
	for (var int = 0; int < file.length; int++) {
		qiniu.compressImage(file[int], options).then(function (data) {
			  var observable = qiniu.upload(data.dist, null, token, putExtra, config);
			  var subscription = observable.subscribe(observer) ;// 上传开始
		});
	}
	
}

function refreshMedia(){
	$.ajax({
		type: "POST",
		url: "/picture/list",
		data: {"intoPieceId":$("#intoPieceId").val(),"type":"1","fileType":2},
	    error: function(request) {	
	    	
	    },
	    success: function(data) {
	    	$("#imageList").empty();
	    	if(data != null){
	    		var html = "";	    		
	    		for (var i = 0; i < data.length; i++) {
	    			var remainder = i%4;
	    			if(remainder == 0){
	    				html = html + "<tr>";
	    			}
	    			html = html + "<td align='center'><img style='height:100px;width:160px;' src='" + data[i].small + "'/></td>";
	    			if(remainder == 3){
	    				html = html + "</tr>";
	    			}
	    		}
	    		var size = data.length%4;
	    		if(size == 1){
	    			html = html + "<td></td><td></td><td></td></tr>";
	    		}else if(size == 2){
	    			html = html + "<td></td><td></td></tr>";
	    		}else if(size == 3){
					html = html + "<td></td></tr>";
				}
	    		$("#imageList").append(html);
	    	}
	    }
	});
}
</script>
</body>
</html>

