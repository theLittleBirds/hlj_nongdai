$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var actionInitBody = false;		//主数据操作按钮初始化标志
	var actionInitOpt0 = false;		
	var actionInitOpt4 = false;		
	var actionInitOpt6 = false;		
	
	var groupId;
	var curAppCode;					//当前选中的主数据行的应用编号
	var curNodeId;
	var curFlowEntranceId           //当前选中的主数据行的流程入口编号
	var dsFlowId;					//当前节点数据集里的流程编号
	var finsId;					//当前角色数据集里的金融机构编号
	var curRowBody;					//当前选中的主数据行
	var curRowOpt0;					//当前选中的选项0数据行
	var curRowOpt4;					
	var curRowOpt6;					
	
	var totalSyncDS4Body = 4;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	
	var totalSyncDS4Opt2 = 1;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
	
	var groupDS;
	var finsDS;
	var delParaDS;
	var typeDS;
	var statusDS;
	var tplDS;
	
	var baseDivId = "base_div";	
	var rightContent_model = "rightContent_model";
	
	//应用管理
	var bodyDivId = "group_div";
	var bodyTableId = "group_table";
	var bodyModalId = "group_modal";
	var bodyDialogId = "group_dialog";
	var bodyFormId = "group_form";
	var bodyDelAction = "/reportGroup/delete";
	
	//右上
	//选项页0
	var opt0Object = "report";
	var opt0DivId = opt0Object + "_div";
	var opt0TableId = opt0Object + "_table";
	var opt0ModalId = opt0Object + "_modal";
	var opt0DialogId = opt0Object + "_dialog";
	var opt0FormId = opt0Object + "_form";
	var opt0DelAction = "/reportEntry/delete";
	
	
	//选项页四
	var opt4Object = "sourceSql";
	var opt4DivId = opt4Object + "_div";
	var opt4TableId = opt4Object + "_table";
	var opt4ModalId = opt4Object + "_modal";
	var opt4DialogId = opt4Object + "_dialog";
	var opt4FormId = opt4Object + "_form";
	var opt4DelAction = "/reportSourcesql/delete";
	
	
	//选项页六
	var opt6Object = "model";
	var opt6DivId = opt6Object + "_div";
	var opt6TableId = opt6Object + "_table";
	var opt6ModalId = opt6Object + "_modal";
	var opt6DialogId = opt6Object + "_dialog";
	var opt6FormId = opt6Object + "_form";
	var opt6DelAction = "/reportTemplate/delete";
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/

	function loadSyncDS4Body()
	{
		//金融机构名称-编号
		if(finsDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/fins/getFinsDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	finsDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//类型
		if(typeDS == undefined)
		{	//保证仅获取一次
			var ustsParaGrpName = "REPORT_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	typeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//模板状态
		if(statusDS == undefined)
		{	//保证仅获取一次
			var ustsParaGrpName = "MODEL_STATUS";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	statusDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//软删除
		if(delParaDS == undefined)
		{	//保证仅获取一次
			var ustsParaGrpName = "IS_DELETE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	delParaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
	}
	function loadSyncDS4Body2 (){
		if(tplDS == undefined)
		{	
			$.ajax({
				type: "POST",
				url: "/reportTemplate/getTemplateDS",
				data:{"rptId":curRowOpt0.rptId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	tplDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt4();
			    }
			});
		}
	}
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#report_id").on("click", function(){
		
		init_global();
		
		init_layout();								//初始化页面布局
		
		loadSyncDS4Body();
		
		show_view_body(); 
		
	});
	
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
		
		curRowBody = "";
    	curRowOpt0 = "";
		actionInitBody = false;
		actionInitOpt0 = false;
		actionInitOpt4 = false;
		actionInitOpt6 = false;
	}
	
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	
	function show_view_body()
	{
		if(countSyncDS4Body < totalSyncDS4Body)
		{	//同步主数据集未全部完成不显示主数据
			return;
		}
		init_table_body();							//初始化主数据table视图

		init_tool_action_body();					//初始化主数据工具栏操作
		
		$("#"+bodyTableId).bootstrapTable({
			method:'post',
			url: "/reportGroup/groupTreeStr",
			dataType: "json",
            striped:true,
            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },{
                    field:'name',
                    title:'类别名称'
                },{
                	field:'finsId',
                	title:'机构编号',
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
            onLoadSuccess: function(data) {
            	$("#"+bodyTableId).treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$("#"+bodyTableId).bootstrapTable('resetWidth');
                    }
                });
            },
            onClickRow:function(row){
            	curRowBody = "";
            	curRowBody = row;
            	if(row._level != 0){
            	 	groupId = row.groupId;
                	show_view_opt();
				}
				$("#" + bodyTableId).each(function () {
					var self = this;
					// 选择行变色 
					$("tr", $(self)).click(function () {
						var trThis = this;
						$(self).find(".trSelected").removeClass('trSelected');
						if ($(trThis).get(0) == $("tr:first", $(self)).get(0)) {
							return;
						}
						$(trThis).addClass('trSelected');
					});
				});
            },
			onLoadError: function(data) {
			}
        });
	}
	//主数据工具栏操作初始化
	function init_tool_action_body()
	{		
		if(!actionInitBody)
		{
			$("#toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#toolbar").append(htmlInfo.toString()); 
			
			$('#btn_add').on("click",function()
			{
				init_modal_body();		//初始化提交表单
				init_form_action_body();		//提交表单操作
				schange();
				$("#"+bodyFormId)[0].reset();
				$("#"+bodyModalId).modal('show');
			});
			
			$('#btn_edit').on("click",function()
			{
				if(curRowBody != undefined && curRowBody._level != 0){
					init_modal_body();		//初始化提交表单
					init_form_action_body();		//提交表单操作
					if(curRowBody.groupId != ""){
						$.ajax({
					    	   type:'post',
					    	   url: "/reportGroup/getGroupById",
					    	   data:{"groupId":curRowBody.groupId},
					    	   success:function(data){
					    		   edit(data);		      
					    	   }
					     });
					}else{
						bootbox.alert("失败");
					}
				}else{
					bootbox.alert("请选择类别修改");
				}
			});
			
			$("#btn_del").on("click",function()
			{					
				var selData = $('#'+bodyTableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						if(selData[i]._level != 0){
						  objCodes += selData[i].groupId + ',';
						}
					}
					if(objCodes != ""){
						objCodes = objCodes.substring(0, objCodes.length-1);
						delMany(bodyDelAction,objCodes,'#'+bodyTableId);
					}else{
						bootbox.alert("不能在此页面删除机构","");
					}
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			actionInitBody = true;
		}
	}
	//主数据编辑操作
	function edit(entry)
	{
		if(entry != null && entry != "")
		{
			$('#groupId').val(entry.groupId);
			$('#finsId').val(entry.finsId);
			$.ajax({
				type: "POST",
				url: "/reportGroup/getGroupDS",
				data:{"finsId": entry.finsId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	$("#parentGroupId").empty();
			    	var selectInfo1 = new StringBuffer();
			    	groupDS = eval(data);
			    	for(var i=0;i<groupDS.length;i++)
			    	{
			    		if(i == 0)
			    		{
			    			selectInfo1.append("<option value=></option>");
			    		}
			    		selectInfo1.append("<option value="+groupDS[i].parameterValue+">"+groupDS[i].parameterName+"</option>");
			    	}
			    	$("#parentGroupId").append(selectInfo1.toString());
			    	$('#parentGroupId').val(entry.parentGroupId);
			    }
		    });
			$('#name').val(entry.name);
			$('#seqno').val(entry.seqno);
			schange();
			$("#"+bodyModalId).modal('show');
		}
		else{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	function schange()
	{
		$("#finsId").change(function () {
			var finsId = $("#finsId").val();
			$.ajax({
				type: "POST",
				url: "/reportGroup/getGroupDS",
				data:{"finsId": finsId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	$("#parentGroupId").empty();
			    	var selectInfo1 = new StringBuffer();
			    	groupDS = eval(data);
			    	for(var i=0;i<groupDS.length;i++)
			    	{
			    		if(i == 0)
			    		{
			    			selectInfo1.append("<option value=></option>");
			    		}
			    		selectInfo1.append("<option value="+groupDS[i].parameterValue+">"+groupDS[i].parameterName+"</option>");
			    	}
			    	$("#parentGroupId").append(selectInfo1.toString());
			    }
		    });
		});  
	}
	
	//删除多个数据记录操作
	function delMany(acturl, codes, refreshid)
	{
		remove(acturl, codes, refreshid);
	}
	
	
	function remove(acturl, codes, refreshid)
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
	                $.post(acturl, {"currIds":codes},
        		    function(data)
        		    {
                	    bootbox.alert(data.msg);
    			    	$(refreshid).bootstrapTable('refresh');
        	    	}, "json");
	            }
	            else
	            {  
	            }  
	        },  
        });
	}
	
	
	function show_view_opt()
	{	
		show_view_opt0();
	}
	
	//选项数据显示-右上1
	function show_view_opt0()
	{
		//先清空右下
		$("#"+rightContent_model).empty();
		
		init_tool_action_opt0(); 
		
		init_table_opt0();
		
		$('#'+opt0Object+'_toolbar').show();
		
		$("#"+opt0TableId).bootstrapTable({
			url: "/reportEntry/selectByGroupId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-545,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10,20],
			pageSize:5,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt0QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt0Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt0ResponseHandler,
			columns: [
				{
					field: 'reportEntry_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'rptId',
					title: '报表ID',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'name',
					title: '报表名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'creOperName',
					title: '建立人名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'updOperName',
					title: '修改人名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            		curRowOpt0="";
            		curRowOpt0 = row;
            		tplDS = undefined;
            		countSyncDS4Opt2 = 0;
            		loadSyncDS4Body2();
                	show_view_opt41(); 
            	$("#"+opt0TableId).each(function() { 
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
            onCheck:function(row){
            	
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+opt0TableId).bootstrapTable('refresh');
		
	}
	
	
	//选项数据显示-右下
	function show_view_opt41()
	{	
		//加载选项卡和操作选项
		init_layout1();
		
		show_view_opt6();
		show_view_opt4();
	}
	//右下选项卡
	function show_view_opt6()
	{
		init_table_opt6();
		
		init_tool_action_opt6(); 
		
		$('#'+opt6Object+'_toolbar').show();
		
		$("#"+opt6TableId).bootstrapTable({
			url: "/reportTemplate/selectByReptId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-490,
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [100000,200000],
			pageSize:100000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt6QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			cardView: true, 
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt6Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt6ResponseHandler,
			columns: [
				{
					field: 'template_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
				},
				{
					field: 'name',
					title: '模板名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'type',
					title: '类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:typeDSFormatter
				},
				{
					field: 'step',
					title: '行列间距',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'status',
					title: '状态',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:statusDSFormatter
				},
				{
					field: 'filepath',
					title: '位置',
					align: 'center',
					width: '',
					valign: 'middle',
				},
				{
					field: 'seqno',
					title: '排列序号',
					align: 'left',
					width: '',
					valign: 'middle'
				},
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt6 = "";
            	curRowOpt6 = row;
            	$("#"+opt6TableId).each(function() { 
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
            onCheck:function(row){
            	
            },
            onLoadError: function () {
            }
		});
		$("#"+opt6TableId).bootstrapTable('refresh');
		
	}
	//右下选项卡
	function show_view_opt4()
	{
		if(countSyncDS4Opt2 < totalSyncDS4Opt2){
			return;
		}
		$("#"+opt4TableId).bootstrapTable('removeAll');
		
		init_table_opt4();
		
		init_tool_action_opt4(); 
		
		$('#'+opt4Object+'_toolbar').show();
		
		$("#"+opt4TableId).bootstrapTable({
			url: "/reportSourcesql/list",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-490,
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [100000,200000],
			pageSize:100000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt4QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			cardView: true, 
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt4Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt4ResponseHandler,
			columns: [
				{
					field: 'sourceSql_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
				},
				{
					field: 'tplId',
					title: '模板名称',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:tplDSFormatter
					
				},
				{
					field: 'name',
					title: '数据源名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'startRow',
					title: '起始行号',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'startCol',
					title: '起始列号',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'type',
					title: '类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:typeDSFormatter
				},
				{
					field: 'status',
					title: '状态',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:statusDSFormatter
				},
				{
					field: 'seqno',
					title: '排列序号',
					align: 'left',
					width: '',
					valign: 'middle'
				},
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt4 = row;
            	$("#"+opt4TableId).each(function() { 
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
            onCheck:function(row){
            	
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+opt4TableId).bootstrapTable('refresh');
		
	}
	function init_modal_opt6(){
		
		$("#"+opt6ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt6ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt6DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt6Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">统计报表_模板</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt6FormId+"\" role=\"form\" enctype=\"multipart/form-data\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_tplId\">模板ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"tplId\" id=\"opt6_tplId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_rptId\">报表ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"rptId\" id=\"opt6_rptId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_name\">模板名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"opt6_name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_type\">类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt6_type\" name=\"type\" class=\"form-control\">");
    	for(var i=0;i<typeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+typeDS[i].parameterValue+">"+typeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_step\">行列间距</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"step\" id=\"opt6_step\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_filepath\">文件路径</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"file\" id=\"opt6_file\" type=\"file\" />");
		htmlInfo.append("<input class=\"form-control\" name=\"filepath\" id=\"opt6_filepath\" type=\"hidden\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt6_status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<statusDS.length;i++)
    	{
    		htmlInfo.append("<option value="+statusDS[i].parameterValue+">"+statusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_isDelete\">软删除标志</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt6_isDelete\" name=\"isDelete\" class=\"form-control\">");
    	for(var i=0;i<delParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+delParaDS[i].parameterValue+">"+delParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt6_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt6Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt6Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
		
		$("#"+opt6DivId).append(htmlInfo.toString());
		
	}


	//设置table传递到服务器的参数
	function opt0QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      groupId: groupId
	    };
	    return temp;
	}
	//处理服务器端响应数据，使其适应分页格式
	function opt0ResponseHandler(res)
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
	//设置table传递到服务器的参数
	function opt4QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      rptId: curRowOpt0.rptId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt4ResponseHandler(res)
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
	//设置table传递到服务器的参数
	function opt6QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      rptId: curRowOpt0.rptId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt6ResponseHandler(res)
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
	/*-------------------------------------函数：结束主数据视图 -----------------------------------------*/
	
	/*-------------------------------------函数：视图-----------------------------------------*/
	
	//初始化table外层的div
	function init_layout()
	{
		if($("#"+baseDivId).length == 0)
		{	
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"row\" id=\""+baseDivId+"\">");
			//panel-left
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\" style=\"\">");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");		//panel
			
			//panel-rightUp
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\" style=\"\">");
			
//			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
//			htmlInfo.append("<li class=\"active\"><a href=\"."+opt0DivId+"\" data-toggle=\"tab\">统计报表</a></li>");
//			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
			//opt0 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt0DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt0Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div style=\"overFlow:hidden;\" id=\"" + opt0DivId + "\">");
	        htmlInfo.append("</div>");
	        
			//关联
			htmlInfo.append("<div id=\"" + rightContent_model + "\">");
	        htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
//			//opt1 tab
//			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt1DivId + "\">");
//			htmlInfo.append("<div id=\"" + opt1Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
//			htmlInfo.append("</div>");
//			htmlInfo.append("<div id=\"" + opt1DivId + "\">");
//	        htmlInfo.append("</div>");
//			htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");		//myTabContent
	    	
			htmlInfo.append("</div>");		//panel

			
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}

	//初始化右下加载
	function init_layout1(){
		$("#"+rightContent_model).empty();
		//首先加载选项卡
		var htmlInfo = new StringBuffer();
		//panel
		htmlInfo.append("<ul id=\"myTab1\" class=\"nav nav-tabs\">");
		htmlInfo.append("<li class=\"active\"><a href=\"."+opt6DivId+"\" data-toggle=\"tab\">模板</a></li>");
		htmlInfo.append("<li><a href=\"."+opt4DivId+"\" data-toggle=\"tab\">数据源</a></li>");
		htmlInfo.append("</ul>");
		
		htmlInfo.append("<div id=\"myTabContent1\" class=\"tab-content\">");	
		//opt4 tab
		htmlInfo.append("<div class=\"tab-pane fade in " + opt4DivId + "\">");
		htmlInfo.append("<div id=\"" + opt4Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt4DivId + "\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
		//opt6 tab
		htmlInfo.append("<div class=\"tab-pane fade in active " + opt6DivId + "\">");
		htmlInfo.append("<div id=\"" + opt6Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt6DivId + "\">");
        htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
    	htmlInfo.append("</div>");		//myTabContent
		
	    $("#"+rightContent_model).append(htmlInfo.toString());
		}
	
	//初始化主数据的table
	function  init_table_body()
	{
		if($("#"+bodyTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+bodyTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+bodyDivId).append(htmlInfo.toString()); 
		}
	}
	
	//初始化流程节点的table
	function init_table_opt0()
	{
		if($("#"+opt0TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt0TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt0DivId).append(htmlInfo.toString());
		}
	}
	//初始化当前选项页4的table
	function init_table_opt4()
	{
		if($("#"+opt4TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt4TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt4DivId).append(htmlInfo.toString());
		}
	}
	//初始化当前选项页6的table
	function init_table_opt6()
	{
		if($("#"+opt6TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt6TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt6DivId).append(htmlInfo.toString());
		}
	}
	/*-------------------------------------函数：选项0数据增删改-----------------------------------------*/
	function init_tool_action_opt0()
	{
		if(!actionInitOpt0)
		{
			$("#"+opt0Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt0Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt0Object+"_btn_add").on("click",function()
			{
				if(curRowBody.finsId != undefined){
					if($("#"+opt0DialogId).length == 0)
					{
						init_modal_opt0();//初始化提交表单
						init_form_action_opt0();//提交表单操作
	                }
	                $("#"+opt0FormId)[0].reset();
					$('#opt0_finsId').val(curRowBody.finsId);
					$('#opt0_groupId').val(curRowBody.groupId);
					$("#"+opt0ModalId).modal('show');
				}else{
					bootbox.alert("请选择产品进行添加！","");
				}
			});
			
			$("#"+opt0Object+"_btn_edit").on("click",function()
			{
				edit_opt0(curRowOpt0);
			});
			
			$("#"+opt0Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt0TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].rptId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					delMany(opt0DelAction, objCodes,'#'+opt0TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});

			actionInitOpt0 = true;
		}
	}
	//选项0数据编辑操作
	function edit_opt0(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt0();//初始化提交表单
			init_form_action_opt0();//提交表单操作
			$('#opt0_rptId').val(entry.rptId);
			$('#opt0_finsId').val(entry.finsId);
			$('#opt0_groupId').val(entry.groupId);
			$('#opt0_name').val(entry.name);
			$('#opt0_idDelete').val(entry.idDelete);
			$('#opt0_creOperId').val(entry.creOperId);
			$('#opt0_creOperName').val(entry.creOperName);
			$('#opt0_creOrgId').val(entry.creOrgId);
			$('#opt0_updOperId').val(entry.updOperId);
			$('#opt0_updOperName').val(entry.updOperName);
			$('#opt0_updOrgId').val(entry.updOrgId);
			$('#opt0_seqno').val(entry.seqno);
			
			$("#"+opt0ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	/*-------------------------------------函数：选项4数据增删改-----------------------------------------*/
	function init_tool_action_opt4()
	{
			$("#"+opt4Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt4Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt4Object+"_btn_add").on("click",function()
			{
					init_modal_opt4();//初始化提交表单
					init_form_action_opt4();//提交表单操作
                $("#"+opt4FormId)[0].reset();
				$("#"+opt4ModalId).modal('show');
			}); 
			
			$("#"+opt4Object+"_btn_edit").on("click",function()
			{
				edit_opt4(curRowOpt4);
			});
			
			$("#"+opt4Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt4TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].dsId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					//删除url修改
					delMany(opt4DelAction, objCodes,'#'+opt4TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
	}
	//选项0数据编辑操作
	function edit_opt4(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt4();//初始化提交表单
			init_form_action_opt4();//提交表单操作
			$('#opt4_dsId').val(entry.dsId);
			$('#opt4_tplId').val(entry.tplId);
			$('#opt4_name').val(entry.name);
			$('#opt4_sqlCode').val(entry.sqlCode);
			$('#opt4_startRow').val(entry.startRow);
			$('#opt4_startCol').val(entry.startCol);
			$('#opt4_stepRow').val(entry.stepRow);
			$('#opt4_stepCol').val(entry.stepCol);
			$('#opt4_type').val(entry.type);
			$('#opt4_status').val(entry.status);
			$('#opt4_seqno').val(entry.seqno);			
			$("#"+opt4ModalId).modal('show');
		}else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}


	function init_tool_action_opt6()
	{
		if(!actionInitOpt6)
		{
			$("#"+opt6Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt6Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt6Object+"_btn_add").on("click",function()
			{
				if($("#"+opt6DialogId).length == 0)
				{
					init_modal_opt6();//初始化提交表单
					init_form_action_opt6();//提交表单操作
				}
                $("#"+opt6FormId)[0].reset();
                $('#opt6_rptId').val(curRowOpt0.rptId);
				$("#"+opt6ModalId).modal('show');
			}); 
			
			$("#"+opt6Object+"_btn_edit").on("click",function()
			{
				edit_opt6(curRowOpt6);
			});
			
			$("#"+opt6Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt6TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].tplId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					//删除url修改
					delMany(opt6DelAction, objCodes,'#'+opt6TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});

		}
	}
	function edit_opt6(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt6();//初始化提交表单
			init_form_action_opt6();//提交表单操作
			$('#opt6_tplId').val(entry.tplId);
			$('#opt6_rptId').val(entry.rptId);
			$('#opt6_name').val(entry.name);
			$('#opt6_type').val(entry.type);
			$('#opt6_step').val(entry.step);
			$('#opt6_filepath').val(entry.filepath);
			$('#opt6_status').val(entry.status);
			$('#opt6_isDelete').val(entry.isDelete);
			$('#opt6_seqno').val(entry.seqno);			
			$("#"+opt6ModalId).modal('show');
		}else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}



	/*------------------------------------------函数：主对象表单-----------------------------------------*/
	
	//初始化表单
	function init_modal_body()
	{
		 $("#"+bodyModalId).remove();
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+bodyModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\"body_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">统计报表_报表分类</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupId\">分类Id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"groupId\" id=\"groupId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsId\">金融机构</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"finsId\" name=\"finsId\" class=\"form-control\">");
	    	for(var i=0;i<finsDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+finsDS[i].parameterValue+">"+finsDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"parentGroupId\">父级分类</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"parentGroupId\" name=\"parentGroupId\" class=\"form-control\">");
            ////////////////////////////////////////////////////////////////////////
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">分类名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"body_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"body_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 
	}
	
		
	//初始化主数据表单操作--左侧表单提交
	function init_form_action_body()
	{
		$('#body_submit_btn').on("click",function(){
			var seqno = $('#seqno').val();
			var seqnoRole=/^[0-9]*$/;
				if(seqnoRole.test(seqno) || seqno == null || seqno == ""){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/reportGroup/save",
                        data: $("#"+bodyFormId).serialize(), //formid
                        error: function(request) {
                        },
                        success: function(data) {
                            bootbox.alert(data.msg,"");
                            $("#"+bodyFormId)[0].reset();
                            $("#"+bodyModalId).modal('hide');
                            $("#"+bodyTableId).bootstrapTable('refresh');
                            curRowBody = "";
                        }
                    });

			}else{
					showError("序号为纯数字，请修改！", '');
                }
		});
		
		$('#body_cancel_btn').on("click",function(){
			$("#"+bodyFormId)[0].reset();
		});
		$('#body_close_btn').on("click",function(){
			$("#"+bodyFormId)[0].reset();
		});
	}
	
	/*------------------------------------------函数：选项一数据表单-----------------------------------------*/
	
	//初始化表单
	function init_modal_opt0()
	{
		$("#"+opt0ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt0ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt0DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt0Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">统计报表_基本记录</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt0FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_rptId\">报表ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"rptId\" id=\"opt0_rptId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_finsId\">金融机构ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"finsId\" id=\"opt0_finsId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_groupId\">报表分类</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"groupId\" id=\"opt0_groupId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_name\">报表名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"opt0_name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_creOperId\">建立人Id</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"creOperId\" id=\"opt0_creOperId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_creOperName\">建立人名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"creOperName\" id=\"opt0_creOperName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_creOrgId\">建立人机构Id</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"creOrgId\" id=\"opt0_creOrgId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_updOperId\">修改人Id</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"updOperId\" id=\"opt0_updOperId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_updOperName\">修改人名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"updOperName\" id=\"opt0_updOperName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_updOrgId\">修改人机构Id</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"updOrgId\" id=\"opt0_updOrgId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_isDelete\">软删除标志</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_isDelete\" name=\"isDelete\" class=\"form-control\">");
    	for(var i=0;i<delParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+delParaDS[i].parameterValue+">"+delParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt0_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt0Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt0Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
	//初始化表单1
	function init_modal_opt1()
	{
		$("#"+opt1ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt1ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt1DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt1Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabe2\">流程入口</h4>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt1FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_entranceId\">流程入口ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"entranceId\" id=\"opt1_entranceId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_appId\">应用ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt1_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_entranceDS\">决策条件</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt1_entranceDS\" name=\"pcId\" class=\"form-control\">");
    	for(var i=0;i<entranceDS.length;i++)
    	{
    		htmlInfo.append("<option value="+entranceDS[i].parameterValue+">"+entranceDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_startNodeId\">起始节点</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt1_startNodeId\" name=\"startNodeId\" class=\"form-control\">");
    	for(var i=0;i<startNodeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+startNodeDS[i].parameterValue+">"+startNodeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt1_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt1Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt1Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
	//初始化表单4
	function init_modal_opt4()
	{
		$("#"+opt4ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt4ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt4DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt4Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">SQL数据源</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt4FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_dsId\">数据源ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"dsId\" id=\"opt4_dsId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_tplId\">模板</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_tplId\" name=\"tplId\" class=\"form-control\">");
		for(var i=0;i<tplDS.length;i++)
    	{    
			htmlInfo.append("<option value="+tplDS[i].parameterValue+">"+tplDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_name\">数据源名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"opt4_name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_sqlCode\">SQL代码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<textarea class=\"form-control\" name=\"sqlCode\" id=\"opt4_sqlCode\" rows=\"3\"></textarea>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_startRow\">起始行号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"startRow\" id=\"opt4_startRow\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_startCol\">起始列号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"startCol\" id=\"opt4_startCol\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_stepRow\">行间距</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"stepRow\" id=\"opt4_stepRow\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_stepCol\">列间距</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"stepCol\" id=\"opt4_stepCol\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_type\">类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_type\" name=\"type\" class=\"form-control\">");
		for(var i=0;i<typeDS.length;i++)
    	{    
			htmlInfo.append("<option value="+typeDS[i].parameterValue+">"+typeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_status\" name=\"status\" class=\"form-control\">");
		for(var i=0;i<statusDS.length;i++)
    	{    
			htmlInfo.append("<option value="+statusDS[i].parameterValue+">"+statusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt4_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt4Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt4Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
	}	
	
	
/*---------------------------------表单提交---------------------------------------------------------------------*/
	//提交表单0
	function init_form_action_opt0()
	{
		$('#'+opt0Object+'_submit_btn').on("click",function(){
			var name = $('#opt0_name').val();
			if(name != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/reportEntry/save",
					data: $("#"+opt0FormId).serialize(), //formid
				    error: function(request) {
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt0FormId)[0].reset();
				    	$("#"+opt0ModalId).modal('hide');
				    	$("#"+opt0TableId).bootstrapTable('refresh');
				    }
				});
			}else{
				showError("中文名称为必填项，请填写！", '');
			}
		});
		
		$('#'+opt0Object+'_cancel_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
		$('#'+opt0Object+'_close_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
	}
	//提交表单4
	function init_form_action_opt4()
	{
		$('#'+opt4Object+'_submit_btn').on("click",function(){
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "/reportSourcesql/save",
						data: $("#"+opt4FormId).serialize(), //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt4FormId)[0].reset();
					    	$("#"+opt4ModalId).modal('hide');
					    	$("#"+opt4TableId).bootstrapTable('refresh');
					    }
					});
		});
		
		$('#'+opt4Object+'_cancel_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
		$('#'+opt4Object+'_close_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
	}
	
	//提交表单6
	function init_form_action_opt6()
	{
		$('#opt6_file').fileinput({
	        theme : 'fa',
	        language : 'zh',
	        uploadAsync : true,//异步上传
	        uploadUrl : "/reportTemplate/upload",
	        allowedFileExtensions : [ 'xlsx','xls'],
	        maxFileSize : 0,
	        maxFileCount : 1
	    }).on("fileuploaded", function(event, data) { //异步上传成功结果处理
	        $("#opt6_filepath").val(data.response.src);
	    })
		//提交
		$('#'+opt6Object+'_submit_btn').on("click",function(){
			var filePath = $("#opt6_filepath").val();
			if(filePath == "" || filePath == null){
				bootbox.alert("请先上传模板文件");
			}else{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/reportTemplate/save",
					data: $("#"+opt6FormId).serialize()+"&file"+filePath, //formid
				    error: function(request) {
				    },
				    success: function(data) {
				    		bootbox.alert(data.msg,"");
				    		$("#"+opt6ModalId).modal('hide');
				    		$("#"+opt6TableId).bootstrapTable('refresh');
				    }
				});
			}
		});
		$('#'+opt6Object+'_cancel_btn').on("click",function(){
			$("#"+opt6FormId)[0].reset();
		});
		$('#'+opt6Object+'_close_btn').on("click",function(){
			$("#"+opt6FormId)[0].reset();
		});
	}
	
	function finsDSFormatter(value) {
    	for(var i=0;i<finsDS.length;i++)
    	{
    		if(finsDS[i].parameterValue == value)
    		{
    			return ['<span>'+finsDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function typeDSFormatter(value) {
    	for(var i=0;i<typeDS.length;i++)
    	{
    		if(typeDS[i].parameterValue == value)
    		{
    			return ['<span>'+typeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function statusDSFormatter(value) {
    	for(var i=0;i<statusDS.length;i++)
    	{
    		if(statusDS[i].parameterValue == value)
    		{
    			return ['<span>'+statusDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function delParaDSFormatter(value) {
    	for(var i=0;i<delParaDS.length;i++)
    	{
    		if(delParaDS[i].parameterValue == value)
    		{
    			return ['<span>'+delParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function tplDSFormatter(value) {
    	for(var i=0;i<tplDS.length;i++)
    	{
    		if(tplDS[i].parameterValue == value)
    		{
    			return ['<span>'+tplDS[i].parameterName+'</span>']
	    	}
    	}
	}
});
