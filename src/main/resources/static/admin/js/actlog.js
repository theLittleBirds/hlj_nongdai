$(document).ready(function(){
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#actlog_id").on("click", function(){
		init_global();
		
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
		
		init_layout();							//初始化table外层的div
		
		init_table();							//初始化table
		
		init_modal_home();						//初始化modal
		
		init_tool_action();                     //初始化工具栏操作
		
		//列操作事件定义
		var operateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "info")
			{
				actLogInfo(row);
			}
		}};		
		
		$('#tableid').bootstrapTable({
			method: 'post', 
			url: "/log/logList",
			dataType: "json",
			cache: false,
			height: $(window).height()-120,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showColumns: false,
			showRefresh: false,
			showToggle: false,
			//toolbar:'#toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler,
			columns: [
				{
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '10',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'id',
					title: '日志编号',
					align: 'left',
					width: '10',
					valign: 'middle',
					visible: false
				},
				{
					field: 'personName',
					title: '中文姓名',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'orgName',
					title: '机构名称',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'actEvent',
					title: '操作名称',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'actObject',
					title: '操作对象',
					align: 'left',
					width: '200',
					valign: 'middle'
				},
				{
					field: 'ip',
					title: 'IP地址',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'actTime',
					title: '操作时间',
					align: 'left',
					width: '100',
					valign: 'middle',
					formatter: timeFormatter
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '20',
					valign: 'middle',
					formatter: actionFormatter,
					events: operateEvents
				}
			],
			onLoadSuccess:function(){
			
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
		if($('#czrzid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
//			htmlInfo.append("<div id=\"qxntoolbar\" style=\"float:right;padding-right:5px;padding-top:13px;\">");
//			htmlInfo.append("<button id=\"btn_export\" type=\"button\" class=\"btn btn-info\" style=\"border-radius:4px;padding:4px;\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-save-file\" aria-hidden=\"true\"></span>选择导出");
//			htmlInfo.append("</button>");
//			htmlInfo.append("<button id=\"btn_export1\" type=\"button\" class=\"btn btn-info\" style=\"border-radius:4px;margin-left:10px;margin-right:4px;padding:4px;\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-save-file\" aria-hidden=\"true\"></span>全部导出");
//			htmlInfo.append("</button>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
//			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:-10px;margin-top: 10px;\" class=\"btn btn-danger\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
//			htmlInfo.append("</button>");
//			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"czrzid\" style=\"margin-top: 10px;border:0px;margin-left: -10px;margin-right: -10px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#czrzmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"czrzmodalid\">");
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
			$("#czrzid").append(htmlInfo.toString()); 
		}
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面的操作列显示
	function actionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="info" href="#"><span>日志详情</span></a>'
	     ].join('   ');
	}
	
	function timeFormatter(value, row, index) {
		var d = new Date(value);
	     return [ 
	       '<span>'+ d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + "日   " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() +'</span>'
	     ];
	}
	
	//工具栏操作初始化
	function init_tool_action(){
		$('#btn_export').on("click", function () {
			        var selData = $('#tableid').bootstrapTable('getSelections'); //选中的数据
					var visibleColumns = $('#tableid').bootstrapTable('getVisibleColumns');
					var visiblefields = '';
					var visibletitles = '';
					var ids = '';
					if (selData.length > 0) {
						for (var i = 0; i < selData.length; i++) {
							ids += selData[i].logId + ',';
						}
						for (var i = 1; i < visibleColumns.length; i++) {
							visiblefields += visibleColumns[i].field + ',';
							visibletitles += visibleColumns[i].title + ',';
						}
						ids = ids.substring(0, ids.length - 1);
						visiblefields = visiblefields.substring(0, visiblefields.length - 1);
						visibletitles = visibletitles.substring(0, visibletitles.length - 1);
						import_export(ids, visiblefields, visibletitles); //导出确认
					} else {
						bootbox.alert("请选择要导出的数据！", "");
					}
		});

		$('#btn_export1').on("click", function () {
					$.ajax({
						type: "POST",
						url: g_AppRoot + "/admin/list2.action",
						data: $('#addform').serialize(),
						success: function (data) {
							var selData;
							selData = eval(data.list);
							var visibleColumns = $('#tableid').bootstrapTable('getVisibleColumns');
		
							var visiblefields = '';
							var visibletitles = '';
							var ids = '';
							if (selData.length > 0) {
								for (var i = 0; i < selData.length; i++) {
									ids += selData[i].logId + ',';
								}
								for (var i = 1; i < visibleColumns.length; i++) {
									visiblefields += visibleColumns[i].field + ',';
									visibletitles += visibleColumns[i].title + ',';
								}
		
								ids = ids.substring(0, ids.length - 1);
								visiblefields = visiblefields.substring(0, visiblefields.length - 1);
								visibletitles = visibletitles.substring(0, visibletitles.length - 1);
								import_export(ids, visiblefields, visibletitles); //导出确认
							}
						}
					});
		});
		
		$("#btn_del").on("click",function()
				{
					var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
					var userCodes ='';
					var userNames ='';
					if(selData.length > 0)
					{
						for(var i=0; i < selData.length; i++)
						{
							userCodes += selData[i].logId+',';
							userNames += selData[i].personName+"("+selData[i].actEvent+")"+',';
						}
						
						userCodes = userCodes.substring(0,userCodes.length-1);
						userNames = "删除操作："+userNames.substring(0,userNames.length-1);
						delUser(userCodes,userNames);//删除确认
					}else{
						bootbox.alert("请选择要删除的数据！","");
					}
				});
	}

	function import_export(ids, fields, titles) {
			$.ajax({
				type: "POST",
				dataType: "json",
				url: g_AppRoot + "/admin/actLogdown.action",
				data: {
					"ids": ids,
					"fields": fields,
					"titles": titles
				},
				error: function (request) {},
				success: function (data) {
					var filedir = data.excelFilePath;
					if (filedir != "") {
						$.download(g_AppRoot + '/admin/exportExcel.action', 'post', filedir); // 下载文件
					}
				}
			});
	}
	
	function delUser(logIds,logname)
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
	        message: '确认删除该记录吗?',  
	        callback: function(result) {  
	            if(result) { 
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: g_AppRoot+"/admin/actLogdele.action",
						data:{"logIds":logIds,"anyname":logname},
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
	
	//详情操作
	function actLogInfo(entry)
	{
		if($('#actlogdialogid').length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
		}
		var personName = entry.personName;
		var orgName = entry.orgName;
		var ip = entry.ip;
		var actEvent = entry.actEvent;
		var actObject = entry.actObject;
		var actTime = entry.actTime;
		
		$('#personName').val(personName);
		$('#orgName').val(orgName);
		$('#ip').val(ip);
		$('#actEvent').val(actEvent);
		$('#actObject').val(actObject);
		$('#actTime').val(actTime);
		
		$('#actlogModal').modal('show');
	}
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"actlogModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"actlogdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">操作日志详情</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"actlogform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"personName\">中文姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.personName\" id=\"personName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgName\">机构名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.orgName\" id=\"orgName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ip\">IP地址</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.ip\" id=\"ip\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"actEvent\">操作名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.actEvent\" id=\"actEvent\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"actObject\">操作对象</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.actObject\" id=\"actObject\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"actTime\">操作时间</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"bean.actTime\" id=\"actTime\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<div style=\"margin:0 auto;width:54px;\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#czrzmodalid").append(htmlInfo.toString()); 
	}
	
});
