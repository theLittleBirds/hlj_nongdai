$(document).ready(function(){

	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var orgid = null;				//机构编号
	var curRole = null;				//记录点击行时选择的角色
	var roleParaDS;					//用户角色参数集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(roleParaDS == undefined)
		{	//保证仅获取一次
			var roleParaGrpName = "ROLE_STATUS";//角色状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":roleParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	roleParaDS = eval(data);
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	$("#role_id").on("click",function(){
		init_global();
		
		loadSyncDS();
		init_div_home();						//初始化tree外层的div视图
		
		init_orgtree();							//初始化tree视图
		
		init_modal_home();						//初始化modal
		
		init_roletable();						//初始化table视图
		get_orgList();							//得到机构树数据
		init_tool_action();						//初始化操作
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
	/*-------------------------------------主程序：结束  -----------------------------------------*/
	
	
	/*-------------------------------------主窗体左侧机构树(tree操作)-----------------------------------------*/
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
        	
        	orgid = node.id;
        	init_roleList();//生成角色table
        });

	}
	
	/*-------------------------------------主窗体右侧角色表格(table的操作)-----------------------------------------*/
	//当点击机构时生成role table
	function init_roleList()
	{
		$('#toolbar').show();
		$('#rolelistid').bootstrapTable({
			method: 'post', 
			url:"/role/roleList",
			dataType: "json",
			cache: false,
			height: $(window).height()-230,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10,15,20,50],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#toolbar',
			minimumCountColumns: 3,
			//clickToSelect: true,//点击行即可选中单选/复选框  
			smartDisplay:true,
			responseHandler: responseHandler,
			columns: [
				{
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'roleId',
					title: '角色编号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'nameCn',
					title: '角色中文名称',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'nameEn',
					title: '角色英文名称',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'orgId',
					title: '机构编号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'seqno',
					title: '排列序号',
					align: 'center',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: statusFormatter
				}
			],
			onLoadSuccess:function(){
				
            },
            onClickRow:function(row){
            	curRole = row;
            	$('#rolelistid').each(function() { 
        			var self = this; 
        			// 选择行变色 
        			$("tr", $(self)).click(function (){ 
        				var trThis = this; 
        				$(self).find(".trSelected").removeClass('trSelected'); 
        				if ($(trThis).get(0) == $("tr:first", $(self)).get(0)){ 
        					return; 
        				} 
        				$(trThis).addClass('trSelected'); 
        			}); 
        		}); 
            },
            onLoadError: function () {
                //mif.showErrorMessageBox("数据加载失败！");
            }
		});
		$('#rolelistid').bootstrapTable('refresh');
		curRole=null;
	}
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      orgid: orgid,				//机构编号
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function responseHandler(res)
	{
		if(res.totalNum > 0)
		{
			var result = eval(res.items);
			var totalcount = res.totalNum;
			return {
				"rows": result,
				"total": totalcount
			};
		}
		else
		{
			return {
					"rows": [],
					"total": 0
			};
		}
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示用户状态的中文描述
	function statusFormatter(value) {
    	for(var i=0;i<roleParaDS.length;i++)
    	{
    		if(parseInt(roleParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+roleParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	
	/*------------------------------------------初始化数据所在的容器（div tree table）-----------------------------------------*/
	
	//初始化tree外层的div
	function init_div_home()
	{
		if($('#yhjsid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"yhjsid\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#rolemodalid').length == 0)
		{
			htmlInfo.append("<div id=\"rolemodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化orgtree
	function init_orgtree()
	{
		var htmlInfo=new StringBuffer();
		if($('#yhjstreeid').length == 0)//判断tree是否存在，防止点一次添加一次tree
		{
			
			htmlInfo.append("<div id=\"yhjstreeid\" style=\"border:0px;margin-left: -10px;margin-right: -10px;\">");
			htmlInfo.append("<div class=\"row\" style=\"margin-top:10px;\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\" style=\"height: 625px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">组织机构</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			htmlInfo.append("<div style=\"height:550px; overflow:auto\" id=\"org_treeview\"></div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\" style=\"\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">用户角色</h3>");
			htmlInfo.append("</div>");
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
			htmlInfo.append("<button id=\"btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>菜单授权");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"role_tableid\" class=\"\"></div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			$("#yhjsid").append(htmlInfo.toString()); 
		}
		
	}
	
	//初始化用户角色table
	function init_roletable()
	{
		var htmlInfo=new StringBuffer();
		if($('#rolelistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"rolelistid\">");
			htmlInfo.append("</table>");
			$("#role_tableid").append(htmlInfo.toString()); 
		}
	}
	
	
	/*------------------------------------------开始表单的操作-----------------------------------------*/
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"roleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"roledialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">用户角色</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"roleid\">角色编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"roleId\" id=\"roleid\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"nameCn\">角色中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameCn\" id=\"nameCn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"nameEn\">角色英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameEn\" id=\"nameEn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgid\">机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"orgId\" id=\"orgid\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<roleParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+roleParaDS[i].parameterValue+">"+roleParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
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
	    $("#rolemodalid").append(htmlInfo.toString()); 
	}
	
	//工具栏操作初始化
	function init_tool_action()
	{
		//添加
		$('#btn_add').on("click",function(){
			if($('#roledialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#addform')[0].reset();
			if(orgid!=null)
			{
				$('#orgid').val(orgid);
			}
			$('#roleModal').modal('show');
		});

		//编辑
		$('#btn_edit').on("click",function(){
			if(curRole!=null)
			{
				if($('#roledialogid').length == 0)
				{
					init_modal();//初始化提交表单
					submit_form();//提交表单操作
				}
				$('#roleid').val(curRole.roleId);
 				$('#nameCn').val(curRole.nameCn);
 				$('#nameEn').val(curRole.nameEn);
 				$('#orgid').val(curRole.orgId);
 				$('#seqno').val(curRole.seqno);
 				$('#status').val(curRole.status);
				$('#roleModal').modal('show');
			}
			else{
				bootbox.alert("请先选择要修改的角色！","");
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
			
			var selData = $('#rolelistid').bootstrapTable('getSelections');//选中的数据
			var roleIds ='';
			var roleNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					roleIds += selData[i].roleId+',';
					roleNames += selData[i].name+"("+selData[i].roleId+")"+',';
				}
				
				roleIds = roleIds.substring(0,roleIds.length-1);
				roleNames = roleNames.substring(0,roleNames.length-1);
				delRole(roleIds,roleNames);//删除确认
			}else{
				bootbox.alert("请选择要删除的角色！","");
			}
		});
		
		//菜单授权
		$("#btn_sqrole").on("click",function(){
			if(curRole!=null)
			{
				if($('#menuTree').length == 0){
					init_menu_menutable(); //初始化
					submit_menuform();//提交操作
				}
				get_init_tree();  //初始化
				$('#menuModal').modal('show');
			}else{
				bootbox.alert("请选择要授权菜单的角色！","");
			}
		});
		
	}
	
	//获得数据并初始化tree数据
	function get_init_tree()
	{
		$.ajax({
    	   type:'post',
    	   url: "/menu/navMenu",
    	   data: {"status":"3","roleId":curRole.roleId},
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
	        multiSelect: true,//选择多个
	        showCheckbox: true,
	        highlightSelected: true,
	        levels:1
        });
	}
	
	function delRole(currIds)
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
	        message: '确认删除该角色吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/role/roleDel",
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#rolelistid').bootstrapTable('refresh');
					    	curRole=null;
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	//提交选择的角色
	function submit_menuform()
	{	
		$('#menusubmit_btn').on("click",function(){
			
			if(curRole!=null){
				
				var selData = $('#menu_treeview').treeview('getChecked');
				var menuIds ='';
				for(var i=0; i < selData.length; i++)
				{
					menuIds += selData[i].id+',';
				}
				
				menuIds = menuIds.substring(0,menuIds.length-1);
				
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/role/addRoleAndMenu",
					data: {"roleId":curRole.roleId,"menuIds":menuIds},
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#menuModal').modal('hide');
				    }
				});
				
			}else{
				bootbox.alert("请先选择角色！","");
			}
		});
		
		$('#rolecancel_btn').on("click",function(){
			
		});
		$('#roleclose_btn').on("click",function(){
			
		});
	}
	
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var name = $('#nameCn').val();
            var seqno = $('#seqno').val();
            var seqnoRole=/^[0-9]*$/;
            if(seqnoRole.test(seqno) || seqno == null || seqno == "") {
                if (name != "") {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/role/roleSave",
                        data: $('#addform').serialize(), //formid
                        error: function (request) {
                            //alert("Connection error");
                        },
                        success: function (data) {
                            bootbox.alert(data.msg, "");
                            $('#addform')[0].reset();
                            $('#roleModal').modal('hide');
                            $('#rolelistid').bootstrapTable('refresh');
                            curRole = null;
                        }
                    });

                } else {
                    showError("角色名称为必填项，请填写！", '');
                    //bootbox.alert("角色名称为必填项，请填写！","");
                }
            }else{
                showError("序号为数字格式，请修改", '');
			}
		});
		
		$('#cancel_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#addform')[0].reset();
		});
	}
	
	/*------------------------------------------初始化菜单授权table-----------------------------------------*/	
	//初始化角色授权table
	function init_menu_menutable()
	{
		var htmlInfo=new StringBuffer();

		htmlInfo.append("<div class=\"modal fade\" id=\"menuModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"menuTree\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"roleclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">授权菜单</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<div style=\"overflow:auto\" id=\"menu_treeview\"></div>");
	    htmlInfo.append("</div>");
	    
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"rolecancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"menusubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#rolemodalid").append(htmlInfo.toString()); 
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
