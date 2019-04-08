<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>签署机构编辑</title>
    
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
                    <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addform">	
                    
                   	<div class="form-group">
                   		<label class="col-sm-2 control-label">企业名称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="name" name="name" value="${cs.name!''}">
						</div>
						<label class="col-sm-2 control-label">企业简称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="ename" name="ename" value="${cs.ename!''}">
						</div>						
				 	</div>				 	
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">证件号：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="identityCard" name="identityCard" value="${cs.identityCard!''}">
						</div>
						
                   		<label class="col-sm-2 control-label">企业邮箱：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="email" name="email" value="${cs.email!''}">
						</div>											
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">企业法人：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="legalName" name="legalName" value="${cs.legalName!''}">
						</div>
						
				 		<label class="col-sm-2 control-label">法人身份证号：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="legalIdCard" name="legalIdCard" value="${cs.legalIdCard!''}">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">法人手机号：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="phone" name="phone" value="${cs.phone!''}">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">营业执照照片：</label>								
						<div class="col-sm-3">								
							<input type="file" id="image1" onchange="imageChange1()">
							<input type="hidden" id="businessLicence" name="businessLicence" value="${cs.businessLicence!''}">	
						</div>
						
                   		<label class="col-sm-2 control-label">授权书照片：</label>								
						<div class="col-sm-3">								
							<input type="file" id="image2" onchange="imageChange2()">
							<input type="hidden" id="authorization" name="authorization" value="${cs.authorization!''}">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2"></label>							
						<div class="col-sm-3">								
							<img alt="营业执照" id="img1"  height="200" width="200" src="">
						</div>
						
                   		<label class="col-sm-2"></label>							
						<div class="col-sm-3">								
							<img alt="授权书" id="img2"  height="200" width="200" src="">
						</div>
				 	</div>
				 	
				 	
				 	<input type="hidden" id="id" name="id" value="${cs.id!''}">
				 	
				 	 <div class="clearfix form-actions">
                           <div class="col-md-10 text-center">
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
$(document).ready(function(e){
	var businessLicence = $("#businessLicence").val();
	if(businessLicence != ''){
		$("#img1").attr("src","/media/localimageshow?path="+businessLicence);
	}
	var authorization = $("#authorization").val();
	if(authorization != ''){
		$("#img2").attr("src","/media/localimageshow?path="+authorization);
	}
});

function checkForm(){
	var name = $("#name").val();
	var ename = $("#ename").val();
	var identityCard = $("#identityCard").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	var legalName = $("#legalName").val();
	var legalIdCard = $("#legalIdCard").val();
	var businessLicence = $("#businessLicence").val();
	var authorization = $("#authorization").val();
	if(name == ""){
		parent.window.bootbox.alert("企业名称不能为空");
		return ;
	}
	if(ename == ""){
		parent.window.bootbox.alert("企业简称不能为空");
		return ;
	}
	if(identityCard == ""){
		parent.window.bootbox.alert("企业证件号不能为空");
		return ;
	}
	if(email == ""){
		parent.window.bootbox.alert("企业邮箱不能为空");
		return ;
	}	
	if(legalName == ""){
		parent.window.bootbox.alert("法人姓名不能为空");
		return ;
	}
	if(legalIdCard == ""){
		parent.window.bootbox.alert("法人身份证号不能为空");
		return ;
	}
	if(phone == ""){
		parent.window.bootbox.alert("法人手机号不能为空");
		return ;
	}
	if(businessLicence == ""){
		parent.window.bootbox.alert("营业执照照片不能为空");
		return ;
	}
	if(authorization == ""){
		parent.window.bootbox.alert("授权书照片不能为空");
		return ;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/contactsignatory/save",
		data:$('#addform').serialize(),
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

function imageChange1(){
	var file = document.getElementById('image1').files[0];
    var fd = new FormData();
    fd.append('file', file);
    $.ajax({
        url:'/media/imagesavelocal',
        type:'POST',
        data:fd,
        processData:false,
        contentType: false,  
        error: function(request) {	
        	parent.window.bootbox.alert("上传失败");
	    },
	    success: function(data) {
	    	if(data.code==200){
	    		$("#businessLicence").val(data.path);
	    		$("#img1").attr("src","/media/localimageshow?path="+data.path);
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}	    	
	    }

    });
}

function imageChange2(){
	var file = document.getElementById('image2').files[0];
    var fd = new FormData();
    fd.append('file', file);
    $.ajax({
        url:'/media/imagesavelocal',
        type:'POST',
        data:fd,
        processData:false,
        contentType: false,  
        error: function(request) {	
        	parent.window.bootbox.alert("上传失败");
	    },
	    success: function(data) {
	    	if(data.code==200){
	    		$("#authorization").val(data.path);
	    		$("#img2").attr("src","/media/localimageshow?path="+data.path);
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}	    	
	    }

    });
}

</script>
</body>
</html>

