$(document).ready(function(){
	
	$("#upd_password").on("click", function(){
		initpwd_modal();
	});
	
	
	//初始化表单
	function initpwd_modal()
	{
		$(".initmodal").empty();
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"pwdModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"pwddialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">修改密码</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"updpwdform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"uname\">用户名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" id=\"uname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"upwd\">原密码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" id=\"upwd\" type=\"password\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"upwd\">新密码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" id=\"upwd1\" type=\"password\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"upwd1\">确认密码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" id=\"upwd2\" type=\"password\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_pwdbtn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $(".initmodal").append(htmlInfo.toString()); 
	    
	    $('#uname').val(curpersonname);
	    
	    $('#pwdModal').modal('show');
	    submit_pwdform();
	}
	
	//提交表单
	function submit_pwdform()
	{
		$('#submit_pwdbtn').on("click",function(){
			var upwd = $('#upwd').val();
			var upwd1 = $('#upwd1').val();
			var upwd2 = $('#upwd2').val();
			if(upwd!="" && upwd1!="" && upwd2!="")
			{
				if(upwd1 == upwd2)
				{
					$.ajax({
						type: "POST",
						dataType: "json",
						url:"/user/updatepwd",
						data:{"pwd":upwd,"pwd1":upwd1},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	if(data.message == "修改成功")
					    	{
					    		bootbox.alert(data.message);
					    		$('#updpwdform')[0].reset();
						    	$('#pwdModal').modal('hide');
					    	}else{
					    		showError(data.message,"");
					    		$('#updpwdform')[0].reset();
					    		$('#uname').val(curpersonname);
					    	}
					    	
					    }
					});
				}else{
					showError("新密码和确认密码不一致，请重新填写！", '');
				}
				
			}else{
				showError("密码为必填项，请填写！", '');
			}
		});
		
		$('#cancel_btn').on("click",function(){
			$('#updpwdform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#updpwdform')[0].reset();
		});
	}
});