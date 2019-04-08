$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var ustsParaDS;					//用户状态参数集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(ustsParaDS == undefined)
		{	//保证仅获取一次
			var ustsParaGrpName = "USER_STATUS";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	ustsParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#user_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		showView(); 
		
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	
	function showView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		
		init_layout();							//初始化table外层的div
		
		init_table();							//初始化table
		
		init_modal_home();						//初始化modal
		
		init_tool_action();						//初始化工具栏操作
		
		//列操作事件定义
		var operateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "edit")
			{
				edit(row);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
		}};		
		
		$('#tableid').bootstrapTable({
			method: 'post', 
			url: "/user/userList",
			dataType: "json",
			cache: false,
			height: $(window).height()-64,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showColumns: true,
			showRefresh: true,
			showToggle: true,
			toolbar:'#toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler,
			columns: [
				{
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'userId',
					title: '用户编号',
					align: 'center',
					width: '98',
					valign: 'middle'
				},
				{
					field: 'username',
					title: '账号名称',
					align: 'left',
					width: '96',
					valign: 'middle'
				},
				/*
				{
					field: 'password',
					visible: false,
					title: '用户密码',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				*/
				{
					field: 'nickname',
					title: '用户昵称',
					align: 'left',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'lastIp',
					title: '最后登录IP',
					align: 'center',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'lastTime',
					title: '最后登录时间',
					align: 'left',
					width: '15',
					valign: 'middle',
					formatter: dateFormatter
				},
				{
					field: 'status',
					title: '用户状态',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '60',
					valign: 'middle',
					formatter: actionFormatter,
					events: operateEvents
				}
			],
			onLoadSuccess:function(data){
				var rows = data.rows;
				for(var i=0;i<rows.length;i++)
				{
					if(rows[i].isDelete == 1)
					{
						// 删除行变色
						$('#tableid tbody').find('tr:eq('+i+')').css("color","#d9534f");
						
					}
				}
            },
            onClickRow:function(){
            	$('#tableid').each(function() { 
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
		
	}
	
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: params.offset/params.limit+1,		//页码
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
	
	
	/*-------------------------------------函数：视图-----------------------------------------*/
	
	//初始化table外层的div
	function init_layout()
	{
		if($('#yhzhid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"yhzhid\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#ucmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"ucmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableid\">");
			htmlInfo.append("</table>");
			$("#yhzhid").append(htmlInfo.toString()); 
		}
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示用户状态的中文描述
	function statusFormatter(value) {
		//var data = eval(s_data.responseText);
    	for(var i=0;i<ustsParaDS.length;i++)
    	{
    		if(parseInt(ustsParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+ustsParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	function dateFormatter(value){
	    var date =  new Date(value);
	    return ['<span>' + date.toLocaleString() +'</span>']
	}
	
	
	//table里面的操作列显示
	function actionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="edit" href="#"><span>编辑</span></a>',
	       '<a class="operate" id="del" href="#"><span>删除</span></a>'
	     ].join('   ');
	}
	
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add').on("click",function()
		{
			if($('#userdialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#addform')[0].reset();
			$('#myModalLabel').val("添加用户账号");
			$('#userModal').modal('show');
		});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
			var userIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					userIds += selData[i].userId+',';
				}
				
				userIds = userIds.substring(0,userIds.length-1);
				delUser(userIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的用户！","");
			}
		});
	}
	
	function delUser(currIds)
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
	        message: '确认删除该用户吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/user/delUser",
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#tableid').bootstrapTable('refresh');
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	
	//编辑操作
	function edit(entry)
	{
		var userId = entry.userId;
		var username = entry.username;
		var password = entry.password;
		var nickname = entry.nickname;
		var status = entry.status;
		
		if($('#userdialogid').length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		
		$('#userId').val(userId);
		$('#username').val(username);
		$('#password').val(password);
		$('#nickname').val(nickname);
		$('#ustatus').val(status);
		
		$('#userModal').modal('show');
	}
	
	
	//删除单个数据记录操作
	function delOne(entry)
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
	        message: '确认删除选中的数据记录吗?',  
	        callback: function(result) {  
	            if(result)
	            {  
	                bootbox.hideAll();
	                $.post("/user/delUser",{"currIds":entry.userId},
        		    function(data)
        		    {
        			    	bootbox.alert(data.msg);
        			    	$('#tableid').bootstrapTable('refresh');
        	    	}, "json");
	            }
	            else
	            {  
	            }  
	        },  
        });
	}
	
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();	
		
		htmlInfo.append("<div class=\"modal fade\" id=\"userModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"userdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">用户账号</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ucode\">用户编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"userId\" id=\"userId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"uname\">账号名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"username\" id=\"username\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"upwd\">用户密码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"password\" id=\"password\" type=\"password\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"nickname\">用户昵称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nickname\" id=\"nickname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ustatus\">用户状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<ustsParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+ustsParaDS[i].parameterValue+">"+ustsParaDS[i].parameterName+"</option>");
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
	    $("#ucmodalid").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var uname = $('#uname').val();
			var upwd = $('#upwd').val();
			var url = "/user/save";
			if(uname!=""&& upwd!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$('#addform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#addform')[0].reset();
				    	$('#userModal').modal('hide');
				    	$('#tableid').bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("账号名称和用户密码为必填项，请填写！", '');
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
