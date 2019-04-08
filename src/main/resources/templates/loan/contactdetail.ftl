<!DOCTYPE html>
<html>
<head>
    <title>合同详情</title>
    
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/style.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/font-awesome.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
   <div id="edit_left" class="col-sm-2" style="padding-right: 10px;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-content clearfix" style="padding: 0px 10px 20px 10px;">
				<ul class="folder-list m-b-md" style="padding: 0; font-size: 12px;">
					<#list arr as one>   
            			<li style="height: 30px; line-height: 5px;">
							<a onclick="change('${one.id}')" class="fa fa-database" style="padding-left: 10px; margin-top :10px;">${one.title}</a>
						</li>        
        			</#list> 					
				</ul>
				<input type="hidden" id="loanId" value="${loanId}">
				<input type="hidden" id="id" value="${id!''}">
			</div>
		</div>
   </div>
   <div id="edit_right" class="col-sm-10" style="padding-left: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-content clearfix" style="padding: 0;width: 680px;" id="right_container">
			
			</div>
		</div>
   </div>
   
</div>
<script type="text/javascript">
$(document).ready(function(e){
	var id = $("#id").val();
	if(id != ""){
		change(id);
	}
});

function change(id){
	var loanId = $("#loanId").val();
	$.ajax({
		type: "POST",
		url: "/loan/contracthtml",
		data:{"loanId":loanId,"id":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	if(data.code == 200){	    		
	    		$("#right_container").empty();
	    		$("#right_container").append(data.content);
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
}
</script>
</body>
</html>
