$(document).ready(function(){
	init_global();
	//页面入口点
	$("#mainfile_id").on("click", function(){
		
		init_global();
		
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
		initMain();
	}
	
	function initMain()
	{
		var htmlInfo=new StringBuffer();
		htmlInfo.append("<img alt=\"image\" src=\"/admin/image/main.png\" style=\"width:100%;height: 100%;\"/>");
		$("#content-main").append(htmlInfo.toString());
	}
	
});