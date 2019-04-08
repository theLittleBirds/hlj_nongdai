<!DOCTYPE html>
<html>
<head>
    <title>农鲸版本编辑</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/admin/js/timeOut.js"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-content clearfix">
                        <form id="addform" class="form-horizontal col-lg-8 m-t"   enctype="multipart/form-data" method="post">
                            <input type="hidden" name="id" value="${appVersion.id}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">版本号：</label>
                                <div class="col-sm-8">
                                    <input id="versionNumber" name="versionNumber" value="${appVersion.versionNumber}" type="text" class="form-control required" placeholder="请输入版本号"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作系统：</label>
                                <div class="col-sm-8">
                                     <select id="type" name="type" class="form-control required">
                                                   ${APP_OPERATING_SYSTEM}
                                     </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">强制更新：</label>
                                <div class="col-sm-8">
                                     <select id="forceUpdate" name="forceUpdate" class="form-control required">
                                                 ${FORCE_UPDATE} 
                                     </select>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">上传apk：</label>
                                <div class="col-sm-8">
                                   <input type="text"  hidden="hidden" id="fileName" name="fileName"/>
                                   <input type="file" name="apk" id = "apk" value = "点击上传" />
                                </div>
                            </div> 
                            <div class="form-group">
                                        <label class="col-sm-3 control-label">渠道:</label>
                                        <div class="col-sm-8">
                                        <select  id="channel" name="channel" class="form-control">
				                                 	${app}                                         
                                  		</select>
                                  		</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">路由前缀：</label>
                                <div class="col-sm-8">
                                    <input id="url" type="text" class="form-control required" name="host" value="${appVersion.host}" placeholder="请输入路由前缀"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                    <textarea id="comment"  class="form-control" name="comment" >${appVersion.comment}</textarea>
                                </div>
                            </div>
  
                            <div class="clearfix form-actions">
                                <div class="col-md-9 text-center">
                                    <button class="btn" type="button" onclick="window.history.go(-1);">
                                        <i class="ace-icon fa fa-reply bigger-110"></i>
                                        返回
                                    </button>

                                    <button class="btn btn-primary" type="button" id="submit">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        提交
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
  	$("#submit").click(function () {
  		if(document.getElementById("apk").files[0]==undefined){
  			parent.window.bootbox.alert("请上传app");
  			return;
  		}
  		if($("#channel").val()==''){
  			parent.window.bootbox.alert("请选择渠道标识");
  			return;
  		}
  		var fileName = document.getElementById("apk").files[0].name;
  		$("#fileName").val(fileName);
  		var formData = new FormData($( "#addform" )[0]); 
  		
		var url = "/appVersion/save";
			$.ajax({
				type: "POST",
				url:url,
			    contentType: false, 
			    processData: false,
				data:formData, //formid
			    error: function(request) {
			    },
			    success: function(data) {
			    		bootbox.alert(data.msg,"");
			    		if(data.code==1001){
			    			window.location.href="/appVersion/index";
			    		}
			    	}
			});
			
	});
    </script>
</body>
</html>