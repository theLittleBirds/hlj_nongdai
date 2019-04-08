$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var actionInitBody = false;		//主数据操作按钮初始化标志
	var actionInitOpt1 = false;		//选项一数据操作按钮初始化标志
	var actionInitOpt2 = false;		//选项二数据操作按钮初始化标志
	var curAppCode;					//当前选中的主数据行的应用编号
	var curRowBody;					//当前选中的主数据行
	var curRowOpt0;					//当前选中的选项0数据行
	var curRowOpt1;					//当前选中的选项一数据行
	var curRowOpt2;	
	var sectionId;
	var operatorDS;
	
	var totalSyncDS4Body = 6;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	var totalSyncDS4Opt = 2;			//同步各选项共用数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt = 0;			//同步各选项共用数据集计数器，当前成功获取个数
	var totalSyncDS4Opt1 = 1;			//同步选项一数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt1 = 0;			//同步选项一数据集计数器，当前成功获取个数
	var totalSyncDS4Opt2 = 2;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
	var totalSyncDS4YxData = 1;			//同步已选数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4YxData = 0;			//同步已选数据集计数器，当前成功获取个数
	
	var finsDS;
	var decisionCategoryDS;
	var paraDS;
	var appDS;
	var itemDS;
	var baseDS;
	var appStatusDS;
	var itemByAppIdDS;
	
	var baseDivId = "base_div";
	
	//应用管理
	var bodyDivId = "application_div";
	var bodyTableId = "application_table";
	var bodyModalId = "application_modal";
	var bodyDialogId = "application_dialog";
	var bodyFormId = "application_form";
	var bodyDelAction = "/application/delete";
	var yxRole = ""; 				//记录已选角色
	
	//选项页一
	var opt1Object = "case";
	var opt1DivId = opt1Object + "_div";
	var opt1TableId = opt1Object + "_table";
	var opt1ModalId = opt1Object + "_modal";
	var opt1DialogId = opt1Object + "_dialog";
	var opt1FormId = opt1Object + "_form";
	var opt1DelAction = "/dePoCase/delete";
	
	
	//选项页二
	var opt2Object = "bases";
	var opt2DivId = opt2Object + "_div";
	var opt2TableId = opt2Object + "_table";
	var opt2ModalId = opt2Object + "_modal";
	var opt2DialogId = opt2Object + "_dialog";
	var opt2FormId = opt2Object + "_form";
	var opt2DelAction = "/dePoBase/delete";
	
	//选项页三
	var opt4Object = "sese";
	var opt4DivId = opt4Object + "_div";
	var opt4TableId = opt4Object + "_table";
	var opt4ModalId = opt4Object + "_modal";
	var opt4DialogId = opt4Object + "_dialog";
	var opt4FormId = opt4Object + "_form";
	var opt4DelAction = "/section/delItemSection";
	
	//选项页三
	var opt5Object = "se";
	var opt5DivId = opt5Object + "_div";
	var opt5TableId = opt5Object + "_table";
	var opt5ModalId = opt5Object + "_modal";
	var opt5DialogId = opt5Object + "_dialog";
	var opt5FormId = opt5Object + "_form";
	var opt5DelAction = "/dePoCase/delCaba";
	
	//选项页三
	var opt6Object = "she";
	var opt6DivId = opt6Object + "_div";
	var opt6TableId = opt6Object + "_table";
	var opt6ModalId = opt6Object + "_modal";
	var opt6DialogId = opt6Object + "_dialog";
	var opt6FormId = opt6Object + "_form";
	var opt6DelAction = "/dePoCase/delCaba";
	
	
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
		
		//产品应用名称-编号
		if(appDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/application/appIdDS",
				error: function(request) {					
				},
				success: function(data) {
					appDS = eval(data);
					countSyncDS4Body ++;
					show_view_body();
				}
			});
		}
	
		//数据项名称-编号
		if(itemDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/item/getItemDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	itemDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//决策类别
		if(decisionCategoryDS == undefined)
		{
			var appcatParaGrpName = "DECISION_CATEGORY";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	decisionCategoryDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//操作符
		if(operatorDS == undefined)
		{
			var appcatParaGrpName = "DECISION_ALL_OPERATOR";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	operatorDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//应用状态
		if(appStatusDS == undefined)
		{	//保证仅获取一次
			var statusName = "APP_STATUS";			//应用状态
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":statusName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	appStatusDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
	}
	
	function loadSyncDS4Body2 (){
		
		if(itemByAppIdDS == undefined){
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/item/getItemDS1",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	itemByAppIdDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}
		
		if(baseDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/dePoBase/getBaseDS",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	baseDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#plybase_id").on("click", function(){
		init_global();
	
		init_layout();								//初始化页面布局
//		init_tool_action_body();
		
		init_table_body();
		loadSyncDS4Body();
		show_view_body(); 
		
	});
	
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
		
		curRowBody = "";
		actionInitBody = false;
		actionInitOpt1 = false;
		actionInitOpt2 = false;
	}
	
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	
	function show_view_body()
	{
		if(countSyncDS4Body < totalSyncDS4Body){
			return;
		}
		$("#"+bodyTableId).bootstrapTable({
			method:'post',
			url: "/application/selectApplication",
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
                    title:'产品'
                },{
                	field:'zhuangtai',
                	title:'状态',
                	formatter: statusFormatter
                },{
                	field:'paixu',
                	title:'排序'
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
            	curRowOpt2 = "";
            	curRowOpt1 = "";
            	curAppCode = "";
            	countSyncDS4Opt2 = 0;
            	baseDS = undefined;
            	itemByAppIdDS = undefined;
            	curRowBody = row;
            	if(row.appId != undefined){
            	 	curAppCode = row.appId;
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
	//table里面显示中文描述
	function statusFormatter(value) {
    	for(var i=0;i<appStatusDS.length;i++)
    	{
    		if(appStatusDS[i].parameterValue == value)
    		{
    			return ['<span>'+appStatusDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//选项数据显示
	
	function show_view_opt()
	{
		show_view_opt1();
		
		loadSyncDS4Body2();
		
		show_view_opt2();
		
	}
	
	
	function show_view_opt1()
	{
		$('#'+opt4DivId).empty();
		$('#'+opt4Object + "_toolbar").empty();
		init_tool_action_opt1();
		
		init_table_opt1();
		
		$('#'+opt1Object+'_toolbar').show();
		
		$("#"+opt1TableId).bootstrapTable({
			method:'post',
			url: "/dePoCase/deciCase",
			dataType: "json",
			queryParams: opt1QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
            striped:true,
//            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },{
                    field:'name',
                    title:'名称'
                },{
                	field:'appId',
                	title:'产品',
                    formatter: appDSFormatter
                },{
                	field:'caseId',
                	title:'Id',
//                	formatter: designTypeFormatter
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
            onLoadSuccess: function(data) {
            	$("#"+opt1TableId).treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$("#"+opt1TableId).bootstrapTable('resetWidth');
                    }
                });
            },
            onClickRow:function(row){
        		curRowOpt1 = row;
            	if(row._level != 0){
            	 	curcaseId = row.caseId;
            	 	show_view_opt4();
							}
							$("#" + opt1TableId).each(function () {
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
		$("#"+opt1TableId).bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function opt1QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      appId: curAppCode
	    };
	    return temp;
	}

	
	function show_view_opt2()
	{
		if(countSyncDS4Opt2 < totalSyncDS4Opt2){
			return;
		}
		
		init_tool_action_opt2();
		
		init_table_opt2();
		
		$('#'+opt2Object+'_toolbar').show();

		$("#"+opt2TableId).bootstrapTable({
			method:'post',
			url: "/dePoBase/deciBase",
			dataType: "json",
			queryParams: opt2QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
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
                    title:'名称'
                },{
                	field:'appId',
                	title:'产品',
                	formatter: appDSFormatter
                },{
                	field:'baseId',
                	title:'Id',
//                	formatter: designTypeFormatter
                },{
                	field:'itemId',
                	title:'数据项',
                	formatter: itemDSFormatter
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
            onLoadSuccess: function(data) {
            	$("#"+opt2TableId).treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$("#"+opt2TableId).bootstrapTable('resetWidth');
                    }
                });
            },
            onClickRow:function(row){
							curRowOpt2 = row;
							$("#" + opt2TableId).each(function () {
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
		$("#"+opt2TableId).bootstrapTable('refresh');
	}
	
	//table里面显示中文描述
	function itemDSFormatter(value) {
    	for(var i=0;i<itemDS.length;i++)
    	{
    		if(itemDS[i].parameterValue == value)
    		{
    			return ['<span>'+itemDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	function appDSFormatter(value) {
		for(var i=0;i<appDS.length;i++)
		{
			if(appDS[i].parameterValue == value)
			{
				return ['<span>'+appDS[i].parameterName+'</span>']
			}
		}
	}
	
	//设置table传递到服务器的参数
	function opt2QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      appId : curAppCode
	    };
	    return temp;
	 }

	function show_view_opt4()
	{
		init_tool_action_opt4();
		
		init_layout4();
		
		init_table_opt4();
		
		$('#'+opt4Object+'_toolbar').show();
		
		show_view_opt5();
		
		show_view_opt6();
		
	}
	
	function show_view_opt5()
	{
		
		$("#"+opt5TableId).bootstrapTable({
			url: "/dePoCase/caseList",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-520,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10,15,20],
			pageSize:5,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt5QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: false,
			showToggle: false,
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt5ResponseHandler,
			columns: [
				{
					field: 'mapping_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'baseId',
					title: '基本条件',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: baseFormatter
				},
				{
					field: 'type',
					title: '类型',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	$("#"+opt5TableId).each(function() { 
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
//            	bootbox.alert("数据加载失败33333！","");
            }
		});
		$("#"+opt5TableId).bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function opt5QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	  	  pageSize: params.limit,			//页面大小
		  currentPage: params.offset,		//页码
	      caseId: curcaseId,
	      type: 1
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt5ResponseHandler(res)
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
	
	function show_view_opt6()
	{
		
		$("#"+opt6TableId).bootstrapTable({
			url: "/dePoCase/caseList",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-520,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10],
			pageSize:5,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt6QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: false,
			showToggle: false,
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt6ResponseHandler,
			columns: [
				{
					field: 'mapping_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'baseId',
					title: '基本条件Id',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: baseFormatter
				},
				{
					field: 'type',
					title: '类型',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
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
//            	bootbox.alert("数据加载失败33333！","");
            }
		});
		$("#"+opt6TableId).bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function opt6QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	  	  pageSize: params.limit,			//页面大小
		  currentPage: params.offset,		//页码
	      caseId: curcaseId,
	      type: 2
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
	
	 function baseFormatter(value) {
			
	    	for(var i=0;i<baseDS.length;i++)
	    	{
	    		if(baseDS[i].parameterValue == value)
	    		{
	    			return ['<span>'+baseDS[i].parameterName+'</span>']
		    	}
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
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"\">");
			
			htmlInfo.append("<div class=\"panel-heading\" style=\"height: 42.5px;\">");
			htmlInfo.append("<h3 class=\"panel-title\">条件变量</h3>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");		//panel
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li  class=\"active\"><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">基本条件</a></li>");
			htmlInfo.append("<li><a href=\"."+opt1DivId+"\" data-toggle=\"tab\">决策条件</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
			//opt1 tab
			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt1DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt1Object + "_toolbar\" class=\"btn-group\" style=\"display: none;margin-top:5px;margin-bottom:5px;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt1DivId + "\">");
	        htmlInfo.append("</div>");
	        //关联表
	        
			htmlInfo.append("<div id=\"" + opt4Object + "_toolbar\" class=\"btn-group pull-right\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
	        htmlInfo.append("<div id=\"" + opt4DivId +"\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			//
			
			//opt2 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt2DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt2Object + "_toolbar\" class=\"btn-group\" style=\"display: none;margin-top:5px;margin-bottom:5px;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt2DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			//
			
			
	    	htmlInfo.append("</div>");		//myTabContent
	    	
			htmlInfo.append("</div>");		//panel
			
			htmlInfo.append("</div>");
			
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}

	function init_layout4()
	{
		  $("#"+opt4DivId).empty();
			
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<ul id=\"myTab1\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li id=\"and\" class=\"active\"><a href=\"."+opt6DivId+"\" data-toggle=\"tab\">与条件</a></li>");
			htmlInfo.append("<li id=\"or\"><a href=\"."+opt5DivId+"\" data-toggle=\"tab\">或条件</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent1\" class=\"tab-content\">");	
			//opt5 tab
			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt5DivId + "\">");
			htmlInfo.append("<div id=\"" + opt5Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"" + opt5DivId + "\">");
	        htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			//opt6 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt6DivId + "\">");
			htmlInfo.append("<div id=\"" + opt6Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"" + opt6DivId + "\">");
	        htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
	    	htmlInfo.append("</div>");	
	    	$("#"+opt4DivId).append(htmlInfo.toString());
		
	}
	
	//初始化主数据的table
	function init_table_body()
	{
		if($("#"+bodyTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+bodyTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+bodyDivId).append(htmlInfo.toString()); 
		}
	}
	
	//初始化当前选项页一的table
	function init_table_opt1()
	{
		if($("#"+opt1TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt1TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt1DivId).append(htmlInfo.toString());
		}
	}
	
	
	//初始化当前选项页二的table
	function init_table_opt2()
	{
		if($("#"+opt2TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt2TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt2DivId).append(htmlInfo.toString());
		}
	}
	
	function init_table_opt4()
	{
		////////与关系表
		if($("#"+opt5TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt5TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt5DivId).append(htmlInfo.toString());
		}
        ////////或关系表
		if($("#"+opt6TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt6TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt6DivId).append(htmlInfo.toString());
		}
	}
	/*-------------------------------------函数：应用管理增删改-----------------------------------------*/
	
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
//			htmlInfo.append("<button id=\"btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权角色");
//			htmlInfo.append("</button>");
			
			$("#toolbar").append(htmlInfo.toString()); 
			
			$('#btn_add').on("click",function()
			{
				if(curRowBody != undefined && curRowBody._level == 0){
					var status = 1;
					initcode(curRowBody,status);
				}else{
					bootbox.alert("请选择机构添加","");
				}
			});
			
			$('#btn_edit').on("click",function()
			{
				if(curRowBody != undefined && curRowBody._level != 0){
					var status = 2;
					initcode(curRowBody,status);
				}else{
					bootbox.alert("请选择产品编辑","");
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
						  objCodes += selData[i].appId + ',';
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
	
	function initcode(entry,status)
	{
		if(status == 1){
			if($("#"+bodyDialogId).length == 0)//判断表单是否已经初始化
			{
				init_modal_body();		//初始化提交表单
				init_form_action_body();		//提交表单操作
			}
			$("#"+bodyFormId)[0].reset();
			if(entry.finsCode != ""){
				$("#finsCode").val(entry.finsCode);
			}
			$("#"+bodyModalId).modal('show');
			
		}else{
			if($("#"+bodyDialogId).length == 0)//判断表单是否已经初始化
			{
				init_modal_body();		//初始化提交表单
				init_form_action_body();		//提交表单操作
			}
			if(entry.appId != ""){
				$.ajax({
			    	   type:'post',
			    	   url: "/application/getAppById",
			    	   data:{"appId":entry.appId},
			    	   success:function(data){
			    		   edit(data);		      
			    	   }
			     });
			}else{
				bootbox.alert("失败");
			}
		}
	}
	
	//主数据编辑操作
	function edit(entry)
	{
		if(entry != null && entry != "")
		{
			$('#appId').val(entry.appId);
			$('#finsCode').val(entry.finsCode);
			$('#category').val(entry.category);
			$('#cname').val(entry.cname);
			$('#ename').val(entry.ename);
			$('#shortCname').val(entry.shortCname);
			$('#shortEname').val(entry.shortEname);
			$('#status').val(entry.status);
			$('#memo').val(entry.memo);
			$('#seqno').val(entry.seqno);
			$('#isDelete').val(entry.isDelete);
//			schange();
			$("#"+bodyModalId).modal('show');
		}
		else{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	
	//删除单个数据记录操作
//	function delOne(acturl, entry, refreshid)
//	{
//		remove(g_AppRoot+bodyDelAction, entry.appCode, entry.cname, refreshid);
//	}
	
	
	//删除多个数据记录操作
	function delMany(acturl,codes, refreshid)
	{
		remove(acturl,codes, refreshid);
	}
	
	
	function remove(acturl,codes, refreshid)
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
	
	/*-------------------------------------函数：选项数据区域增删改-----------------------------------------*/
	
	function init_tool_action_opt1()
	{
		if(!actionInitOpt1)
		{
			$("#"+opt1Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt1Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt1Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					if(curRowOpt1 != undefined && curRowOpt1._level == 0){
						init_modal_opt1();//初始化提交表单
						init_form_action_opt1();//提交表单操作
						
						$("#"+opt1FormId)[0].reset();
						$('#opt1_appId').val(curAppCode);
						$('#opt1_decisionCategoryId').val(curRowOpt1.paraId);
						$("#"+opt1ModalId).modal('show');
					}else{
						bootbox.alert("请选择类型添加条件！","");
					}
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt1Object+"_btn_edit").on("click",function()
			{
				if(curRowOpt1 != undefined && curRowOpt1._level != 0){
					$.ajax({
				    	   type:'post',
				    	   url: "/dePoCase/getCase",
				    	   data:{"caseId":curRowOpt1.caseId},
				    	   success:function(data){
				    		   edit_opt1(data);      
				    	   }
				     });
				}else{
					bootbox.alert("请选择基础条件修改！","");
				}
			});
			
			$("#"+opt1Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt1TableId).bootstrapTable('getSelections');//选中的数据
				if(selData.length == 1 && selData[0]._level == 0){
					bootbox.alert("请选择基础条件删除！","");
				}else{
					var objCodes = '';
					if(selData.length > 0)
					{
						for(var i=0;i<selData.length;i++)
						{
							if(selData[i]._level != 0){
								objCodes += selData[i].caseId + ',';
							}
						}
						objCodes = objCodes.substring(0, objCodes.length-1)+"";
						delMany(opt1DelAction, objCodes,'#'+opt1TableId);
					}
					else
					{
						bootbox.alert("请选择要删除的数据记录！","");
					}
				}
			});
			actionInitOpt1 = true;
		}
	}
	
	
	//选项一数据编辑操作
	function edit_opt1(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt1();//初始化提交表单
			init_form_action_opt1();//提交表单操作
			
			$('#opt1_caseId').val(entry.caseId);
			$('#opt1_appId').val(entry.appId);
			$('#opt1_decisionCategoryId').val(entry.decisionCategoryId);
			$('#opt1_miaoshu').val(entry.miaoshu);			
			$('#opt1_seqno').val(entry.seqno);
			
			$("#"+opt1ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	
	function init_tool_action_opt2()
	{
		if(!actionInitOpt2)
		{
			$("#"+opt2Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt2Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt2Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt2Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt2Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt2Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					if(curRowOpt2 != undefined && curRowOpt2._level == 0){
						init_modal_opt2();//初始化提交表单
						init_form_action_opt2();//提交表单操作
						$("#"+opt2FormId)[0].reset();
						$('#opt2_decisionCategoryId').val(curRowOpt2.paraId);
						$('#opt2_appId').val(curAppCode);
						$("#"+opt2ModalId).modal('show');
					}else{
						bootbox.alert("请选择类型添加条件！","");
					}
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt2Object+"_btn_edit").on("click",function()
			{
				if(curRowOpt2 != undefined && curRowOpt2._level != 0){
					$.ajax({
				    	   type:'post',
				    	   url: "/dePoBase/getBase",
				    	   data:{"baseId":curRowOpt2.baseId},
				    	   success:function(data){
				    		   edit_opt2(data);      
				    	   }
				     });
				}else{
					bootbox.alert("请选择基础条件修改！","");
				}
			});
			
			$("#"+opt2Object+"_btn_del").on("click",function()
			{			
				var selData = $('#'+opt2TableId).bootstrapTable('getSelections');//选中的数据
				if(selData.length == 1 && selData[0]._level == 0){
					bootbox.alert("请选择基础条件删除！","");
				}else{
					var objCodes = '';
					if(selData.length > 0)
					{
						for(var i=0;i<selData.length;i++)
						{
							if(selData[i]._level != 0){
								objCodes += selData[i].baseId + ',';
							}
						}
						objCodes = objCodes.substring(0, objCodes.length-1)+"";
						delMany(opt2DelAction, objCodes,'#'+opt2TableId);
					}
					else
					{
						bootbox.alert("请选择要删除的数据记录！","");
					}
				}
			});

			actionInitOpt2 = true;
		}
	}
	
	//选项一数据编辑操作
	function edit_opt2(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt2();//初始化提交表单
			init_form_action_opt2();//提交表单操作
			
			$('#opt2_baseId').val(entry.baseId);
			$('#opt2_appId').val(entry.appId);
			$('#opt2_decisionCategoryId').val(entry.decisionCategoryId);
			$('#opt2_itemId').val(entry.itemId);
			$('#opt2_lowerValue').val(entry.lowerValue);
			$('#opt2_lowerOperator').val(entry.lowerOperator);			
			$('#opt2_upperValue').val(entry.upperValue);
			$('#opt2_upperOperator').val(entry.upperOperator);
			$('#opt2_miaoshu').val(entry.miaoshu);
			$('#opt2_seqno').val(entry.seqno);
			
			$("#"+opt2ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	
	function init_tool_action_opt4()
	{
			$("#"+opt4Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt4Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt4Object+"_btn_add").on("click",function()
			{
				var type;
				init_modal_opt4();
				init_form_action_opt4();
				$("#"+opt4FormId)[0].reset();
				$('#opt4_caseId').val(curcaseId);
				if($("#and").attr("class") == "active"){
					$('#opt4_type').val(2);
					type = 2;
				}
				if($("#or").attr("class") == "active"){
					$('#opt4_type').val(1);
					type = 1;
				}
				$.ajax({
		         	   type:'post',
		         	   url: "/dePoCase/getCabaString",
		         	   data:{"caseId":curcaseId,"type":type},
		         	   success:function(data){
		                if(data){
		                	var arr = data.replace(/\s/g,'').split(/[,]/g);
		    				$('#opt4_baseId').selectpicker('val', arr);
		                }
		                $("#"+opt4ModalId).modal('show');
		         	    }
		        });
			});
			$("#"+opt4Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt5TableId).bootstrapTable('getSelections');//选中的数据
				var selData2 = $('#'+opt6TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].csbaseId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					delMany(opt5DelAction, objCodes,'#'+opt5TableId);
				}else{
					if(selData2.length > 0){
						for(var i=0;i<selData2.length;i++)
						{
							objCodes += selData2[i].csbaseId + ',';
						}
						
						objCodes = objCodes.substring(0, objCodes.length-1)
						delMany(opt6DelAction, objCodes,'#'+opt6TableId);
					}else{
						bootbox.alert("请选择要删除的数据记录！","");
					}
				}
			});
	}
	/*------------------------------------------函数：主对象表单-----------------------------------------*/
	
	//初始化表单
	function init_modal_body()
	{
		if($("#"+bodyModalId).length == 0)
		{
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+bodyModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\"body_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">应用</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appCode\">应用编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsCode\">金融机构</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"finsCode\" name=\"finsCode\" class=\"form-control\">");
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
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"category\">应用类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
	    	for(var i=0;i<appCategoryDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+appCategoryDS[i].parameterValue+">"+appCategoryDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cname\">中文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"cname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ename\">英文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"ename\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortCname\">中文简称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"shortCname\" id=\"shortCname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortEname\">英文简称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"shortEname\" id=\"shortEname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
	    	for(var i=0;i<appStatusDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+appStatusDS[i].parameterValue+">"+appStatusDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"memo\">备注</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<textarea class=\"form-control\" name=\"memo\" id=\"memo\" rows=\"3\"></textarea>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isDelete\">软删除标志</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"isDelete\" name=\"isDelete\" class=\"form-control\">");
	    	for(var i=0;i<delParaDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+delParaDS[i].parameterValue+">"+delParaDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
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
		
	}
	
	
	//初始化主数据表单操作
	function init_form_action_body()
	{
		$('#body_submit_btn').on("click",function(){
			var cname = $('#cname').val();
			if(cname != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/application/save",
					data: $("#"+bodyFormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+bodyFormId)[0].reset();
				    	$("#"+bodyModalId).modal('hide');
				    	$("#"+bodyTableId).bootstrapTable('refresh');
				    	curRowBody = "";
				    }
				});				
			}
			else
			{
				showError("中文名称为必填项，请填写！", '');
				//bootbox.alert("中文名称为必填项，请填写！", "");
			}
		});
		
		$('#body_cancel_btn').on("click",function(){
			$("#"+bodyFormId)[0].reset();
		});
		$('#body_close_btn').on("click",function(){
			$("#"+bodyFormId)[0].reset();
		});
	}
	
	/*------------------------------------------函数：选项0数据表单-----------------------------------------*/
	
	
	function schange()
	{
		$("#opt0_type").change(function () {
			var type = $("#opt0_type").val();
			$("#opt0_parentId").empty();
			if(type == 2){
				var selectInfo = new StringBuffer();
		    	for(var i=0;i<MainEntityDS.length;i++)
		    	{
		    		if(i == 0)
					{
		    			selectInfo.append("<option value=></option>");
					}
		    		selectInfo.append("<option value="+MainEntityDS[i].parameterValue+">"+MainEntityDS[i].parameterName+"</option>");
		    	}
		    	$("#opt0_parentId").append(selectInfo.toString());
			}
		});  
	    //$("#finsId").trigger("change");  
	}
	
	/*------------------------------------------函数：选项一数据表单-----------------------------------------*/
	
	//初始化表单
	function init_modal_opt1()
	{
		$("#"+opt1ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt1ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt1DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt1Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">决策条件</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt1FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_caseId\">决策条件</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"caseId\" id=\"opt1_caseId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_appId\">应用编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt1_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_decisionCategoryId\">决策类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt1_decisionCategoryId\" name=\"decisionCategoryId\" class=\"form-control\">");
    	for(var i=0;i<decisionCategoryDS.length;i++)
    	{
    		htmlInfo.append("<option value="+decisionCategoryDS[i].parameterValue+">"+decisionCategoryDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_miaoshu\">描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"miaoshu\" id=\"opt1_miaoshu\" type=\"text\" placeholder=\"\"/>");
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
	}
	
		
	//提交表单
	function init_form_action_opt1()
	{
		$('#'+opt1Object+'_submit_btn').on("click",function(){
//			var cname = $('#opt1_cname').val();
//			if(cname != "")
//			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/dePoCase/save",
					data: $("#"+opt1FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt1FormId)[0].reset();
				    	$("#"+opt1ModalId).modal('hide');
				    	$("#"+opt1TableId).bootstrapTable('refresh');
				    }
				});
				
//			}else{
//				showError("中文名称和字段名称为必填项，请填写！", '');
//				//bootbox.alert("中文名称和字段名称为必填项，请填写！","");
//			}
		});
		
		$('#'+opt1Object+'_cancel_btn').on("click",function(){
			$("#"+opt1FormId)[0].reset();
		});
		$('#'+opt1Object+'_close_btn').on("click",function(){
			$("#"+opt1FormId)[0].reset();
		});
	}
	
	
/*------------------------------------------函数：选项二数据表单-----------------------------------------*/
	
	//初始化表单
	function init_modal_opt2()
	{
		    $("#"+opt2ModalId).remove();
		   
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt2ModalId+"\" tabindex=\"-2\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt2DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt2Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">基本条件</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt2FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_baseId\">条件编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"baseId\" id=\"opt2_baseId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_appId\">应用编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt2_appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_decisionCategoryId\">决策类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_decisionCategoryId\" name=\"decisionCategoryId\" class=\"form-control\">");
	    	for(var i=0;i<decisionCategoryDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+decisionCategoryDS[i].parameterValue+">"+decisionCategoryDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_itemId\">数据项</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_itemId\" name=\"itemId\" class=\"form-control\">");
	    	for(var i=0;i<itemByAppIdDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+itemByAppIdDS[i].parameterValue+">"+itemByAppIdDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_lowerValue\">下限值</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lowerValue\" id=\"opt2_lowerValue\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_lowerOperator\">下限操作符</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_lowerOperator\" name=\"lowerOperator\" class=\"form-control\">");
	    	for(var i=0;i<operatorDS.length;i++)
	    	{
	    		htmlInfo.append("<option value=\""+operatorDS[i].parameterValue+"\">"+operatorDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_upperValue\">上限值</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"upperValue\" id=\"opt2_upperValue\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_upperOperator\">上限操作符</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_upperOperator\" name=\"upperOperator\" class=\"form-control\">");
	    	for(var i=0;i<operatorDS.length;i++)
	    	{
	    		htmlInfo.append("<option value=\""+operatorDS[i].parameterValue+"\">"+operatorDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_miaoshu\">描述</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"miaoshu\" id=\"opt2_miaoshu\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt2_seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt2Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt2Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 

	}
	
		
	//提交表单
	function init_form_action_opt2()
	{
		$('#'+opt2Object+'_submit_btn').on("click",function(){
//			var cname = $('#opt2_cname').val();
//			var ename = $('#opt2_ename').val();
//			if(cname!="" && ename!="")
//			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/dePoBase/save",
					data: $("#"+opt2FormId).serialize(), //formid
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt2FormId)[0].reset();
				    	$("#"+opt2ModalId).modal('hide');
				    	$("#"+opt2TableId).bootstrapTable('refresh');
				    	curRowOpt2 = "";
				    }
				});
				
//			}else{
//				showError("中文名称和英文名称为必填项，请填写！", '');
//				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
//			}
		});
		
		$('#'+opt2Object+'_cancel_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
		$('#'+opt2Object+'_close_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
	}
	
	//初始化表单
	function init_modal_opt4()
	{
		    $("#"+opt4ModalId).remove();
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt4ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt4DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt4Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">决策条件</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt4FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_caseId\">决策条件编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"caseId\" id=\"opt4_caseId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_type\">类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt4_type\" name=\"type\" class=\"form-control\">");
	    		htmlInfo.append("<option value=2>与关系</option>");
	    		htmlInfo.append("<option value=1>或关系</option>");
	    	htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_baseId\">基本条件</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt4_baseId\" name=\"baseId\" multiple class=\"selectpicker show-tick form-control\">");
			
			for(var i=0;i<baseDS.length;i++)
	    	{    
				htmlInfo.append("<option value="+baseDS[i].parameterValue+">"+baseDS[i].parameterName+"</option>");
	    	}
			
			htmlInfo.append("</select>");
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
		    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
		
	//提交表单
	function init_form_action_opt4()
	{
		$('#'+opt4Object+'_submit_btn').on("click",function(){
			if(curcaseId != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/dePoCase/saveCaseBase",
					data: $("#"+opt4FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt4FormId)[0].reset();
				    	$("#"+opt4ModalId).modal('hide');
				    	$("#"+opt5TableId).bootstrapTable('refresh');
				    	$("#"+opt6TableId).bootstrapTable('refresh');
				    	curscvarRowData = "";
				    }
				});
			}else{
				showError("请选择决策条件！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#'+opt4Object+'_cancel_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
		$('#'+opt4Object+'_close_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
	}
	
	
	/*------------------------------------------函数：角色授权-----------------------------------------*/
	//初始化角色授权table
	function init_roletable()
	{
		$("#roleModal").remove();
		
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"roleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"roledialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"roleclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">授权角色</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#rolelistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"rolelistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"rolecancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"rolesubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $(".initmodal").append(htmlInfo.toString()); 
	}
	
	/*------------------------------授权角色时的操作--------------------------------------------*/
	//查找已经选择的数据
	function findYxRole()
	{
		$.ajax({
			type: "POST",
			url: "/application/getAppright",
			data:{"entityId":curRowOpt0.entityId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    		data = eval(data);
		    		yxRole = data.objectIds;
		    	countSyncDS4YxData++;
		    	getRoles();//得到角色数据
		    }
		});
	}
	//得到角色数据
	function getRoles()
	{
		if(countSyncDS4YxData < totalSyncDS4YxData)
		{
			return;
		}
		$('#rolelistid').bootstrapTable({
			method:'post',
			url: "/role/getRoleTree",
			dataType: "json",
            striped:true,
            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
			columns: [
				{
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					formatter : stateFormatter
				},
				{
					field: 'name',
					title: '名字',
				},
				{
					field: 'roleId',
					title: '角色编号',
				}
			],
            treeShowField: 'name',
            parentIdField: 'pid',
			onLoadSuccess:function(data){
				$('#rolelistid').treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$('#rolelistid').bootstrapTable('resetWidth');
                    }
                });
            },
            onUncheck: function(row){
            },
            onLoadError: function () {
            }
		});
		$('#rolelistid').bootstrapTable('refresh');
		countSyncDS4YxData = 0;
	}
	
	//对应的函数进行判断；
	function stateFormatter(value, row, index) {
		if(yxRole != "")
		{
			var rolecodeArr = yxRole.split(",");
			for(var j=0;j<rolecodeArr.length;j++)
			{
				if( rolecodeArr[j] == row.roleId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
	}
	
	//提交选择的角色
	function submit_roleform()
	{
		$('#rolesubmit_btn').on("click",function(){
			var selData = $('#rolelistid').bootstrapTable('getSelections');//选中的数据
			var roleCodes ='';
			
			for(var i=0; i < selData.length; i++)
			{
				if(selData[i]._level != 0)
				roleCodes += selData[i].roleId+',';
			}
			roleCodes = roleCodes.substring(0,roleCodes.length-1);
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"/entity/saveRole",
				data: {"roleCodes":roleCodes,"entityId":curRowOpt0.entityId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	$('#roleModal').modal('hide');
			    	$("#"+bodyTableId).bootstrapTable('refresh');
			    	curRowOpt1 = "";
			    }
			});
		});
		
		$('#rolecancel_btn').on("click",function(){
			
		});
		$('#roleclose_btn').on("click",function(){
			
		});
	}
	/*------------------------------结束授权角色时的操作--------------------------------------------*/
  
});
