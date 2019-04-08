$(document).ready(function(){

	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var menuParaDS;					//菜单状态参数集
	var isleafParaDS;				//是否叶子状态参数集
	var categoryParaDS;				//菜单功能分类参数集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var _data;
	var curMenu = null;
	var orgcode = null;
	var oldRoleIds;
	
	var yxRole = "";				//已选角色
	
	/*-------------------------------------取所有菜单状态数据-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(menuParaDS == undefined)
		{	//保证仅获取一次
			var parameterName = "MENU_STATUS";//参数MENU_STATUS=菜单状态
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
			    error: function(request) {
					
			    },
			    success: function(data) {
			    	menuParaDS = eval(data);
			    }
			});
		}
		
		if(isleafParaDS == undefined)
		{	//保证仅获取一次
			var parameterName = "MENU_IS_LEAF";
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
			    error: function(request) {
					
			    },
			    success: function(data) {
			    	isleafParaDS = eval(data);
			    }
			});
		}
		
		if(categoryParaDS == undefined)
		{	//保证仅获取一次
			var parameterName = "MENU_CATEGORY";
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
			    error: function(request) {
					
			    },
			    success: function(data) {
			    	categoryParaDS = eval(data);
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#menu_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		init_div_home();							//初始化tree外层的div视图
		
		init_tree();								//初始化tree视图
		
		init_modal_home();							//初始化modal试图
		init_role_modal();							//初始化展示角色的modal
		
		init_tool_action();							//初始化操作工具栏
		get_orgList();								//添加机构tree数据
		
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
	//监听程序：键盘敲击	
    document.onkeydown = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode == 27)
        {	// 按 Esc 
        }
        if(e && e.keyCode == 113)
        {	// 按 F2 
        }            
        if(e && e.keyCode == 13)
        {	// enter 键
        	 $("#_btn").click();
        }
    };
    
    /*-------------------------------------主程序：结束  -----------------------------------------*/
    
    
	/*-------------------------------------开始对tree的数据操作-----------------------------------------*/
  //获得机构tree数据
	function get_orgList()
	{
		var firstLevel = false; //用于判断是否只展示一级
		$.ajax({
    	   type:'post',
    	   url: "/org/orgList",//获得一级机构
    	   data:{"firstLevel":firstLevel},
    	   success:function(data){
				init_org(data);			      
    	   }
        });
	}
	//初始化机构tree数据
	function init_org(entry)
	{
        $('#org_treeview').treeview({
	        data: entry,
	        color: "#428bca",
	        levels:1
        });
        //选择一行
        $('#org_treeview').on('nodeSelected', function (event, node) {
        	
        	orgcode = node.id;
        	get_init_tree();//生成菜单tree
        	curMenu = null;
        });

	}
	
	//获得数据并初始化tree数据
	function get_init_tree()
	{
		$('#toolbar').show();
		$.ajax({
    	   type:'post',
    	   url: "/menu/navMenu",
    	   data: {"orgcode":orgcode,"status":"2"},
    	   success:function(data){
				init_menu(data);			      
    	   }
        });
	}
	//初始化tree数据
	function init_menu(_data)
	{
        $('#menu_treeview').treeview({
	        data: _data,
	        color: "#428bca",
//	        multiSelect: true,//选择多个
//	        showCheckbox: true,
	        levels:1
        });
        
        //详细信息数据
        $('#menu_treeview').on('nodeSelected', function (event, node) {
        	var menuId = node.id;
        	$.ajax({
         	   type:'post',
         	   url: "/menu/menuInfo",
         	   data:{"menuId":menuId},
         	   success:function(data){
         		    var dataobj = eval("("+data+")");
     				curMenu = dataobj;
         	   }
             });
        });
        //取消选择
        $('#menu_treeview').on('nodeUnselected', function (event, node) {
				curMenu = null;
       });
        
	}
	
	//初始化tree外层的div
	function init_div_home()
	{
		if($('#cdglid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"cdglid\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
	}
	//初始化 form modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#cdglmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"cdglmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	
	//初始化role table modal
	function init_role_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#rolemodalid').length == 0)
		{
			htmlInfo.append("<div id=\"rolemodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	
	//初始化tree
	function init_tree()
	{
		var htmlInfo=new StringBuffer();
		if($('#cdgltreeid').length == 0)//判断tree是否存在，防止点一次添加一次tree
		{
			htmlInfo.append("<div id=\"cdgltreeid\" style=\"border:0px;margin-left: -10px;margin-right: -10px;\">");
			
			htmlInfo.append("<div class=\"row\" style=\"margin-top:10px;\" id=\"\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\" style=\"height: 625px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">组织机构</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			htmlInfo.append("<div style=\"height:550px; overflow:auto\" id=\"org_treeview\" class=\"\"></div>");
		    htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\" style=\"height: 625px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">菜单管理</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			
			htmlInfo.append("<div class=\"row\">");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"row\">");
			htmlInfo.append("<div style=\"height:500px; overflow:auto; margin-top:10px;\" id=\"menu_treeview\" class=\"\"></div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			$("#cdglid").append(htmlInfo.toString()); 
		}
		
	}
	/*------------------------------------------结束tree的操作-----------------------------------------*/
	
	/*------------------------------------------开始表单的操作-----------------------------------------*/
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"menuModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"menudialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">菜单管理</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"menucode\">菜单编号</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"menuId\" id=\"menuId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"parentMenuId\">父级编号</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"parentMenuId\" id=\"parentMenuId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"parentMenuIds\">上级编号</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"parentMenuIds\" id=\"parentMenuIds\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"seqno\">序号</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"nameCn\">中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameCn\" id=\"nameCn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"nameEn\">英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameEn\" id=\"nameEn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"htmlid\">页面ID</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"htmlId\" id=\"htmlId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"actionJs\">JS路径</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"actionJs\" id=\"actionJs\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"actionUrl\">功能地址</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"actionUrl\" id=\"actionUrl\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"logoUrl\">图片URL</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<input class=\"form-control\" name=\"logoUrl\" id=\"logoUrl\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"isleaf\">是否叶子</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<select id=\"isleaf\" name=\"isLeaf\" class=\"form-control\">");
    	for(var i=0;i<isleafParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+isleafParaDS[i].parameterValue+">"+isleafParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"status\">菜单状态</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<menuParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+menuParaDS[i].parameterValue+">"+menuParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"category\">功能分类</label>");
		htmlInfo.append("<div class=\"col-sm-4\">");
		htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
    	for(var i=0;i<categoryParaDS.length;i++)
    	{
    		if(i == 0)
    		{
    			htmlInfo.append("<option value=></option>");
    		}
    		htmlInfo.append("<option value="+categoryParaDS[i].parameterValue+">"+categoryParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#cdglmodalid").append(htmlInfo.toString()); 
	}
	
	//工具栏操作初始化
	function init_tool_action()
	{
		//添加
		$('#btn_add').on("click",function(){
			if($('#menudialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#addform')[0].reset();
//			$('#orgCode').val(orgcode);
			if(curMenu!=null)
			{
				$('#parentMenuId').val(curMenu.menuId);
				if(curMenu.parentMenuIds != null && curMenu.parentMenuIds != ""){
					$('#parentMenuIds').val(curMenu.parentMenuIds + "," + curMenu.menuId);
				}else{
					$('#parentMenuIds').val(curMenu.menuId);
				}
			}
			$('#menuModal').modal('show');
		});
		
		//编辑
		$('#btn_edit').on("click",function(){
			if(curMenu!=null)
			{
				if($('#menudialogid').length == 0)
				{
					init_modal();//初始化提交表单
					submit_form();//提交表单操作
				}
				$('#menuId').val(curMenu.menuId);
 				$('#nameCn').val(curMenu.nameCn);
 				$('#nameEn').val(curMenu.nameEn);
 				$('#parentMenuId').val(curMenu.parentMenuId);
 				$('#parentMenuIds').val(curMenu.parentMenuIds);
 				$('#htmlId').val(curMenu.htmlId);
 				$('#actionJs').val(curMenu.actionJs);
 				$('#actionUrl').val(curMenu.actionUrl);
 				$('#logoUrl').val(curMenu.logoUrl);
 				$('#isleaf').val(curMenu.isLeaf);
 				$('#status').val(curMenu.status);
 				$('#seqno').val(curMenu.seqno);
 				$('#category').val(curMenu.category);
				$('#menuModal').modal('show');
			}
			else{
				bootbox.alert("请先选择要修改的菜单！","");
			}
		});
		$('#cancel_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		
		//删除
		$("#btn_del").on("click",function(){
			if(curMenu!=null)
			{
				bootbox.confirm({  
			        buttons: {  
			            confirm: {
			                label: '是'
			            },  
			            cancel: {  
			                label: '否'  
			            }  
			        },  
			        message: '确认删除该菜单吗?',  
			        callback: function(result) {  
			            if(result) {  
			                bootbox.hideAll();
			                $.ajax({
								type: "POST",
								dataType: "json",
								url: "/menu/delMenu",
								data:{"menuId":curMenu.menuId},
							    error: function(request) {
							        //alert("Connection error");
							    },
							    success: function(data) {
							    	bootbox.alert(data.msg,"");
							    	get_init_tree();//刷新数据
							    	curMenu = null;
							    }
							});
			            } else {  
			            }  
			        },  
		        });
			}
			else{
				bootbox.alert("请先选择要删除的菜单！","");
			}
		});
	}
	
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var nameCn = $('#nameCn').val();
			var htmlId = $('#htmlId').val();
			var actionJs = $('#actionJs').val();
                if(nameCn!="" || htmlId!="" ||actionJs!="")
                {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/menu/save",
                        data:$('#addform').serialize(), //formid
                        error: function(request) {
                            //alert("Connection error");
                        },
                        success: function(data) {
                            bootbox.alert(data.msg,"");
                            $('#addform')[0].reset();
                            $('#menuModal').modal('hide');
                            get_init_tree();//刷新数据
                            curMenu = null;
                        }
                    });

                }else{
                    showError("中文名称,页面ID,JS文件路径为必填项，请填写！", '');
                    //bootbox.alert("中文名称,页面ID,JS文件路径为必填项，请填写！","");
                }
		});
		
		$('#cancel_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#addform')[0].reset();
		});
	}
	/*------------------------------------------结束表单的操作-----------------------------------------*/
	
	//url参数的方法方法：
    function getUrlParam(name) {
    	var searchStr = window.location.search;
    	//解码
    	searchStr = decodeURIComponent(searchStr);
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = searchStr.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    
});
