$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var stsParaDS;					//用户状态参数集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(stsParaDS == undefined)
		{	//保证仅获取一次
			var stsParaGrpName = "MEMBERLOGIN_STATUS";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":stsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	stsParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#memberlogin_id").on("click", function(){
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
			url: "/memberlogin/list",
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
					field: 'xh',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'loginId',
					title: '编号',
					align: 'center',
					width: '98',
					valign: 'middle',
					visible:false
				},
				{
					field: 'loginName',
					title: '登录账户',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'name',
					title: '姓名',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'bank',
					title: '开户银行',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'bankCardNo',
					title: '银行卡账户',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'bankPhone',
					title: '银行预留手机号',
					align: 'center',
					width: '50',
					valign: 'middle'
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
					field: 'creDate',
					title: '注册时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
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
		var loginName = $("#q_loginName").val();
		var name = $("#q_name").val();
		var idCard = $("#q_idCard").val();
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: (params.offset / params.limit) + 1,		//页码
	      loginName: loginName,
	      name: name,
	      idCard: idCard,
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
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px;border-radius:3px;\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<div class=\"pull-left\" style=\"margin-left:20px;\">");
			htmlInfo.append("登录账户：<input type=\"text\" name=\"q_loginName\" id=\"q_loginName\"/>");
			htmlInfo.append("姓名：<input type=\"text\" name=\"q_name\" id=\"q_name\"/>");
			htmlInfo.append("身份证号：<input type=\"text\" name=\"q_idCard\" id=\"q_idCard\"/>");
			htmlInfo.append("<button id=\"btn_query\" style=\"margin-left:5px;margin-top:5px;\">");
			htmlInfo.append("<span class=\"glyphicon\" aria-hidden=\"true\"></span>查询");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
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
    	for(var i=0;i<stsParaDS.length;i++)
    	{
    		if(parseInt(stsParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+stsParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	
	//table里面的操作列显示
	function actionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="edit" href="#"><span>编辑</span></a>',
	       '<a class="operate" id="del" href="#"><span>删除</span></a>'
	     ].join('   ');
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
    
	//工具栏操作初始化
	function init_tool_action()
	{
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
			var loginIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					loginIds += selData[i].loginId+',';
				}
				
				loginIds = loginIds.substring(0,loginIds.length-1);
				delUser(loginIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的用户！","");
			}
		});
		
		$("#btn_query").on("click",function(){
			$('#tableid').bootstrapTable('refresh');
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
						url: "/memberlogin/delete",
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
		var loginId = entry.loginId;
		var loginName = entry.loginName;
		var name = entry.name;
		var idCard = entry.idCard;
		var bank = entry.bank;
		var bankCardNo = entry.bankCardNo;
		var bankPhone = entry.bankPhone;
		var status = entry.status;
		
		if($('#logindialogid').length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		
		$('#loginId').val(loginId);
		$('#loginName').val(loginName);
		$('#name').val(name);
		$('#idCard').val(idCard);
		$('#bank').val(bank);
		$('#bankCardNo').val(bankCardNo);
		$('#bankPhone').val(bankPhone);
		$('#status').val(status);

		$('#loginModal').modal('show');
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
	                $.post("/memberlogin/delete",{"currIds":entry.loginId},
        		    function(data)
        		    {
        			    if(data.code == 200)
        			    {
        			    	bootbox.alert(data.msg);
        			    	$('#tableid').bootstrapTable('refresh');
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    }
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
		
		htmlInfo.append("<div class=\"modal fade\" id=\"loginModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"logindialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">用户账号</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"loginId\">编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"loginId\" id=\"loginId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"loginName\">登录账户</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"loginName\" id=\"loginName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"idCard\">身份证号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"idCard\" id=\"idCard\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bank\">开户银行</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"bank\" id=\"bank\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bankCardNo\">银行卡号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"bankCardNo\" id=\"bankCardNo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bankPhone\">银行预留手机号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"bankPhone\" id=\"bankPhone\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">用户状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<stsParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+stsParaDS[i].parameterValue+">"+stsParaDS[i].parameterName+"</option>");
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
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"/memberlogin/update",
				data:$('#addform').serialize(), //formid
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	if(data.code == 200){
				    	$('#addform')[0].reset();
				    	$('#loginModal').modal('hide');
				    	$('#tableid').bootstrapTable('refresh');
			    	}
			    }
			});
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
