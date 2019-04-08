$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var curRowData = "";			//记录所选择的行用于点击行
	var appId;
	var value;
	var ruleDS;
	var categoryDS;
	var applicationDS;
	var actionDS;
	var finsDS;
	var caseIdDS;
	var caseIdByAppIdDS;
	var totalSyncDS = 4;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var totalSyncDS2 = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS2 = 0;
	
	var arrActionIds = new Array();	//记录去掉选择的基础参数数组
	
	var bodyObject = "rule";
	var menuId = bodyObject + "_id";
	var bodyDivId = bodyObject + "_div";
	var bodyTableId = bodyObject + "_table";
	var bodyModalId = bodyObject + "_modal";
	var bodyDialogId = bodyObject + "_dialog";
	var bodyListController = "/rule/listRule";
	var bodyAddController = "/rule/save";
	var bodyDelController = "/rule/delete";
	
	var yxLAction = "";
	var yxRAction = "";
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		//金融机构名称-编号
		if(finsDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/fins/getFinsDS1",
				error: function(request) {					
				},
				success: function(data) {
					finsDS = eval(data);
					countSyncDS ++;
					show_view_body();
				}
			});
		}
		//执行措施名称-编号
		if(actionDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/action/actionDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	actionDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//决策条件简称-编号
		if(caseIdDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/dePoCase/caseIdDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	caseIdDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		
		//规则类别
		if(categoryDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "RULE_CATEGORY";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	categoryDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
	}
	
	function loadSyncDS2(){
		//规则类别
		if(ruleDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/rule/getRuleDS",
				data:{"appId":appId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	ruleDS = eval(data);
			    	countSyncDS2 ++;
			    	showRuleTable();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#rule_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		show_view_body(); 
		
		init_left_modal();
		
		init_right_modal();
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	
	function show_view_body()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		
		init_layout();							//初始化table外层的div
		
		init_table();							//初始化table
		
		init_modal_home();						//初始化modal
		
		init_tool_action();						//初始化工具栏操作
		
		$('#'+bodyTableId).bootstrapTable({
			method:'post',
			url: "/rule/selectRule",
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
                    title:'名称'
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
			onLoadSuccess:function(data){
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
            	if(row._level == 2){
            		value = row.value;
            		appId = row._parent.appId;
            		ruleDS = undefined;
            		countSyncDS2 = 0;
            		show_view_rule();
            		$("#table_div").empty();
            	}
				$("#" + bodyTableId).each(function() { 
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
//            	curRule = row;
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败xxx！","");
            }
		});
		
	}
	
	//table里面显示数据类型的中文描述
	function caseIdDSFormatter(value) {
    	for(var i=0;i<caseIdDS.length;i++)
    	{
    		if(caseIdDS[i].parameterValue == value)
    		{
    			return ['<span>'+caseIdDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function actionDSFormatter(value) {
		for(var i=0;i<actionDS.length;i++)
		{
			if(actionDS[i].parameterValue == value)
			{
				return ['<span>'+actionDS[i].parameterName+'</span>']
			}
		}
	}
	
	
	function show_view_rule()
	{
		init_table3();
		
		loadSyncDS2();
		//初始化右侧上面的table
	    showRuleTable();
	}
	
	function showRuleTable()
	{
		if(countSyncDS2 < totalSyncDS2)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		$('#tableId3').bootstrapTable({
			method: 'post', 
			url: "/rule/ruleList",
			dataType: "json",
			cache: false,
			height: $(window).height()-570,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100,120],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams3,//参数
			contentType: "application/x-www-form-urlencoded",
//			showColumns: true,
//			showRefresh: true,
//			showToggle: true,
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler3,
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
					title: 'ID',
					align: 'center',
					width: '15',
					valign: 'left',
					visible: false
				},
				{
					field: 'ruleId',
					title: '执行规则',
					align: 'center',
					width: '15',
					valign: 'left',
					formatter: ruleDSFormatter
				},
				{
					field: 'seqno',
					title: '排序',
					align: 'center',
					width: '15',
					valign: 'left',
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	
            	curRowData = row;
            	show_view_body0();  
            	
            	$('#tableId3').each(function() { 
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
		$('#tableId3').bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function queryParams3(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
    	  pageSize: params.limit,			//页面大小
    	  currentPage: params.offset/params.limit+1,		//页码
	      value: value,
	      appId: appId
	    };
	    return temp;
	}
	
	
	//处理服务器端响应数据，使其适应分页格式
	function responseHandler3(res)
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
	
	function ruleDSFormatter(value) {
		for(var i=0;i<ruleDS.length;i++)
		{
			if(ruleDS[i].parameterValue == value)
			{
				return ['<span>'+ruleDS[i].parameterName+'</span>']
			}
		}
	}
	
	function show_view_body0()
	{
		    init_modal_rule();
		//初始化右侧下面的table
			init_table1();	  
		//初始化右侧下面的table
	    	init_table2();
	    //展示右侧下面左边table
			showleftTable();
		//展示右侧下面右边table
			showrightTable();
	}
	
	function showleftTable()
	{
		init_tool_action1();
		
		$('#tableId1').bootstrapTable({
			method: 'post', 
			url: "/ruleact/listLeft",
			dataType: "json",
			cache: false,
			height: $(window).height()-220,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100,120],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams1,//参数
			contentType: "application/x-www-form-urlencoded",
//			showColumns: true,
//			showRefresh: true,
//			showToggle: true,
//			toolbar:'#fwjg_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler1,
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
					title: 'ID',
					align: 'center',
					width: '15',
					valign: 'left',
					visible: false
				},
				{
					field: 'actionId',
					title: '执行措施',
					align: 'center',
					width: '15',
					valign: 'left',
					formatter : actionDSFormatter
				},
				{
					field: 'seqno',
					title: '排序',
					align: 'center',
					width: '15',
					valign: 'left',
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(){
            	$('#tableId1').each(function() { 
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
		$('#tableId1').bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function queryParams1(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
//	      limit: params.limit,			//页面大小
//	      offset: params.offset/params.limit+1,		//页码
	      code: curRowData.ruleId 
	    };
	    return temp;
	}
	
	
	//处理服务器端响应数据，使其适应分页格式
	function responseHandler1(res)
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
	
	
	function showrightTable()
	{
		init_tool_action2();
		
		$('#tableId2').bootstrapTable({
			method: 'post', 
			url: "/ruleact/listRight",
			dataType: "json",
			cache: false,
			height: $(window).height()-220,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100,120],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams2,//参数
			contentType: "application/x-www-form-urlencoded",
//			showColumns: true,
//			showRefresh: true,
//			showToggle: true,
//			toolbar:'#fwjg_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler2,
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
			        	  title: 'ID',
			        	  align: 'center',
			        	  width: '15',
			        	  valign: 'left',
			        	  visible: false
			          },
			          {
			        	  field: 'actionId',
			        	  title: '执行措施',
			        	  align: 'center',
			        	  width: '15',
			        	  valign: 'left',
			        	  formatter : actionDSFormatter
			          },
			          {
			        	  field: 'seqno',
			        	  title: '排序',
			        	  align: 'center',
			        	  width: '15',
			        	  valign: 'left',
			          }
			          ],
			          onLoadSuccess:function(){
			        	  
			          },
			          onClickRow:function(){
			        	  $('#tableId2').each(function() { 
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
		$('#tableId2').bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function queryParams2(params)
	{
		var temp = {					//这里的键的名字和控制器的变量名必须一致
//				limit: params.limit,			//页面大小
//				offset: params.offset/params.limit+1,		//页码
				code: curRowData.ruleId 
		};
		return temp;
	}
	
	
	//处理服务器端响应数据，使其适应分页格式
	function responseHandler2(res)
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
	
	//初始化服务实现table外层的div
	function init_layout()
	{
		if($('#'+bodyDivId).length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<div class=\"row\" id=\"ruleid\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">规则类别</h3>");
			htmlInfo.append("</div>");

			htmlInfo.append("<div id=\""+bodyDivId+"\">");//
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">决策规则</h3>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
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
			
			//规则table
			htmlInfo.append("<div id=\"ruleTable_id\" class=\"col-sm-12\">");
			htmlInfo.append("</div>");

			htmlInfo.append("<div class=\"row clearfix\" id=\"table_div\">");
			
//			htmlInfo.append("<div class=\"col-sm-6\" id=\"mz_id\" style=\"padding-left:0px;padding-right:5px;margin-top: 5px;\">");		
//			htmlInfo.append("<div class=\"panel-heading\">");
//			htmlInfo.append("<h3 class=\"panel-title\">满足条件</h3>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("<div id=\"toolbar1\" class=\"btn-group\">");
//			htmlInfo.append("</div>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("<div class=\"col-sm-6\" id=\"bmz_id\" style=\"padding-right:0px;padding-left:5px;margin-top: 5px;\">");
//			htmlInfo.append("<div class=\"panel-heading\">");
//			htmlInfo.append("<h3 class=\"panel-title\">不满足条件</h3>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("<div id=\"toolbar2\" class=\"btn-group\">");
//			htmlInfo.append("</div>");
//			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#'+bodyModalId).length == 0)
		{
			htmlInfo.append("<div id=\""+bodyModalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	
	function init_modal_rule()
	{
		    $("#table_div").empty();
	     	var htmlInfo=new StringBuffer();
			htmlInfo.append("<div class=\"col-sm-6\" id=\"mz_id\" style=\"padding-left:0px;padding-right:5px;margin-top: 5px;\">");		
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">满足条件</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"toolbar1\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"col-sm-6\" id=\"bmz_id\" style=\"padding-right:0px;padding-left:5px;margin-top: 5px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">不满足条件</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"toolbar2\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			$("#table_div").append(htmlInfo.toString());
	}
	//初始化table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#'+bodyTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\""+bodyTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+bodyDivId).append(htmlInfo.toString()); 
		}
		
	}
	//初始化规则措施table
	function init_table1()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableId1').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableId1\">");
			htmlInfo.append("</table>");
			$("#mz_id").append(htmlInfo.toString()); 
		}
	}
	function init_table2()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableId2').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableId2\">");
			htmlInfo.append("</table>");
			$("#bmz_id").append(htmlInfo.toString()); 
		}
	}
	
	function init_table3()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableId3').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableId3\">");
			htmlInfo.append("</table>");
			$("#ruleTable_id").append(htmlInfo.toString()); 
		}
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add').on("click",function()
		{
			if($('#'+bodyDialogId).length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
				schange();
			}
			$('#addform')[0].reset();
			$('#ruleModal').modal('show');
		});
		$('#btn_edit').on("click",function() {
			if(curRowData != "")
			{
				if($('#edit').length == 0)//判断表单是否已经初始化
				{
					init_edit_modal();		//初始化提交表单
					submit_edit_form();		//提交表单操作
				}
		    	$('#ruleId').val(curRowData.ruleId);
				$('#appId').val(curRowData.appId);
                $.ajax({
                    type: "POST",
                    url: "/dePoCase/caseIdByAppIdDS",
                    data:{"appId" : curRowData.appId},
                    error: function(request) {
                    },
                    success: function(data) {
                    	caseIdByAppIdDS = eval(data);
						$("#caseId").empty();
                        var htmlInfo = new StringBuffer();
                        for(var i=0;i<caseIdByAppIdDS.length;i++)
                    	{
                            if(i == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+caseIdByAppIdDS[i].parameterValue+">"+caseIdByAppIdDS[i].parameterName+"</option>");
                        }
                        $("#caseId").append(htmlInfo.toString());
                        $('#caseId').val(curRowData.caseId);
                    }
                });
				$('#category').val(curRowData.category);
				$('#name').val(curRowData.name);
				$('#seqno').val(curRowData.seqno);
				
				$("#ruleEditModal").modal('show');
			}
			else{
				bootbox.alert("请选择要修改的规则！","");
			}
		});
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableId3').bootstrapTable('getSelections');//选中的数据
				var ruleIds ='';
				if(selData.length > 0)
				{
					for(var i=0; i < selData.length; i++)
					{
						ruleIds += selData[i].ruleId+',';
					}
					
					ruleIds = ruleIds.substring(0,ruleIds.length-1);
					delSImpl(ruleIds);//删除确认
				}else{
					bootbox.alert("请选择要删除的数据！","");
				}
		});
	}
	
	function delSImpl(currIds)
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
	        message: '确认删除该规则吗?',
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: bodyDelController,
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#tableId3').bootstrapTable('refresh');
					    }
					});
	            } 
	        },  
        });
		
	}
	
	function init_tool_action1()
	{
		$("#toolbar1").empty();
		
		var htmlInfo = new StringBuffer();
		htmlInfo.append("<button id=\"btn_add1\" class=\"btn btn-primary\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
		htmlInfo.append("</button>");
		htmlInfo.append("<button id=\"btn_del1\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
		htmlInfo.append("</button>");
		
		$("#toolbar1").append(htmlInfo.toString()); 
		
		$('#btn_add1').on("click",function()
		{
			if($('#leftdialogid').length == 0)
			{
				init_left_table();//初始化提交表单
				submit_left_form();//提交表单操作
			}
//			$('#addform')[0].reset();
			$('#leftModal').modal('show');
			findLeftYxData();
			getLeft();//得到数据
		});
		$("#btn_del1").on("click",function()
		{
			var selData = $('#tableId1').bootstrapTable('getSelections');//选中的数据
			var ids ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					ids += selData[i].id+',';
				}
				
				ids = ids.substring(0,ids.length-1);
				delLeft(ids);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});
	}
	
	function delLeft(currIds)
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
	        message: '确认删除该规则吗?',
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/ruleact/deleteLeft",
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#tableId1').bootstrapTable('refresh');
					    }
					});
	            } 
	        },  
        });
		
	}
	
	function findLeftYxData(){
		$.ajax({
			type: "POST",
			url:"/ruleact/getLeftList",
			data:{"ruleId":curRowData.ruleId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxLAction = data.objectIds;
//		    	orgcountSyncDS++;
		    	getLeft();//得到角色数据
		    }
		});		
	}
	
	function getLeft()
	{
		$('#leftlistid').bootstrapTable({
			method:'post',
			url: "/ruleact/showLeft",
			dataType: "json",
			queryParams: leftqueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
            striped:true,
            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true,
                    formatter : stateLFormatter
                },{
                    field:'name',
                    title:'名称'
                },{
                	field:'actionId',
                	title:'执行措施ID',
                }
            ],
		            treeShowField: 'name',
		            parentIdField: 'pid',
		            onLoadSuccess: function(data) {
		            	$("#leftlistid").treegrid({
		                    initialState: 'collapsed',//收缩
		                    treeColumn: 1,//指明第几列数据改为树形
		                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
		                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
		                    onChange: function() {
		                    	$("#leftlistid").bootstrapTable('resetWidth');
		                    }
		                });
		            },
            onUncheck: function(row){
            	//取消选择记录Id
            	unactionId = row.actionId;
            	if($.inArray(unactionId, arrActionIds) == -1)
            	{
            		arrActionIds.push(unactionId);
            	}
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$('#leftlistid').bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function leftqueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	    appId: curRowData.appId
	    };
	    return temp;
	}
	//对应的函数进行判断；
	function stateLFormatter(value, row, index) {
		if(yxLAction != "")
		{
			var actionIdArr = yxLAction.split(",");
			for(var j=0;j<actionIdArr.length;j++)
			{
				if( actionIdArr[j] == row.actionId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
	}
	
	//提交选择
	function submit_left_form()
	{
		$('#leftsubmit_btn').on("click",function(){
			var selData = $('#leftlistid').bootstrapTable('getSelections');//选中的数据
			var actionIds ='';
			var url = "/ruleact/actionToLeft";
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					actionIds += selData[i].actionId+',';
				}
				actionIds = actionIds.substring(0,actionIds.length-1);
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data: {"actionIds":actionIds,"ruleId":curRowData.ruleId},
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	unactionId = "";
				    	arrActionIds = [];
				    	$('#leftModal').modal('hide');
				    	$('#tableId1').bootstrapTable('refresh');
				    }
				});
				
			}else{
				bootbox.alert("请选择数据！","");
			}
		});
		
		$('#leftcancel_btn').on("click",function(){
			
		});
		$('#leftclose_btn').on("click",function(){
			
		});
	}
	/*-----------------------分割线----------------------------*/
	function init_tool_action2()
	{
		$("#toolbar2").empty();
		
		var htmlInfo = new StringBuffer();
		htmlInfo.append("<button id=\"btn_add2\" class=\"btn btn-primary\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
		htmlInfo.append("</button>");
		htmlInfo.append("<button id=\"btn_del2\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
		htmlInfo.append("</button>");
		
		$("#toolbar2").append(htmlInfo.toString()); 
		
		$('#btn_add2').on("click",function()
		{
			if($('#rightdialogid').length == 0)
			{
				init_right_table();//初始化提交表单
				submit_right_form();//提交表单操作
			}
//			$('#addform')[0].reset();
			$('#rightModal').modal('show');
			findRightYxData();
			getRight();//得到数据
		});
		$("#btn_del2").on("click",function()
		{
			var selData = $('#tableId2').bootstrapTable('getSelections');//选中的数据
			var ids ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					ids += selData[i].id+',';
				}
				
				ids = ids.substring(0,ids.length-1);
				delRight(ids);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});
	}
	
	function delRight(currIds)
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
	        message: '确认删除该规则吗?',
	        callback: function(result) {
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/ruleact/deleteRight",
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#tableId2').bootstrapTable('refresh');
					    }
					});
	            } 
	        },  
        });
		
	}
	
	function findRightYxData(){
		$.ajax({
			type: "POST",
			url:"/ruleact/getRightList",
			data:{"ruleId":curRowData.ruleId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxRAction = data.objectIds;
//		    	orgcountSyncDS++;
		    	getRight();//得到数据
		    }
		});		
	}
	
	function getRight()
	{
		$('#rightlistid').bootstrapTable({
			method:'post',
			url: "/ruleact/showRight",
			dataType: "json",
			queryParams: rightqueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
            striped:true,
            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true,
                    formatter : stateRFormatter
                },{
                    field:'name',
                    title:'名称'
                },{
                	field:'actionId',
                	title:'执行措施ID',
                }
            ],
		            treeShowField: 'name',
		            parentIdField: 'pid',
		            onLoadSuccess: function(data) {
		            	$("#rightlistid").treegrid({
		                    initialState: 'collapsed',//收缩
		                    treeColumn: 1,//指明第几列数据改为树形
		                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
		                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
		                    onChange: function() {
		                    	$("#rightlistid").bootstrapTable('resetWidth');
		                    }
		                });
		            },
            onUncheck: function(row){
            	//取消选择记录Id
            	unactionId = row.actionId;
            	if($.inArray(unactionId, arrActionIds) == -1)
            	{
            		arrActionIds.push(unactionId);
            	}
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$('#rightlistid').bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function rightqueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	    appId: curRowData.appId
	    };
	    return temp;
	}
	//对应的函数进行判断；
	function stateRFormatter(value, row, index) {
		if(yxRAction != "")
		{
			var actionIdArr = yxRAction.split(",");
			for(var j=0;j<actionIdArr.length;j++)
			{
				if( actionIdArr[j] == row.actionId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
	}
	
	//提交选择
	function submit_right_form()
	{
		$('#rightsubmit_btn').on("click",function(){
			var selData = $('#rightlistid').bootstrapTable('getSelections');//选中的数据
			var actionIds ='';
//			var actionNames ='';
			var unActionIds = arrActionIds.join(",");
			var url = "/ruleact/actionToRight";
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					actionIds += selData[i].actionId+',';
				}
				actionIds = actionIds.substring(0,actionIds.length-1);
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data: {"unactionIds":unActionIds,"actionIds":actionIds,"ruleId":curRowData.ruleId},
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	unactionId = "";
				    	arrActionIds = [];
				    	$('#rightModal').modal('hide');
				    	$('#tableId2').bootstrapTable('refresh');
				    }
				});
				
			}else{
				bootbox.alert("请选择数据！","");
			}
		});
		
		$('#rightcancel_btn').on("click",function(){
			
		});
		$('#rightclose_btn').on("click",function(){
			
		});
	}
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"ruleModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">决策规则</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_ruleId\">决策规则编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"ruleId\" id=\"curRowBody_ruleId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_finsId\">金融机构</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"curRowBody_finsId\" name=\"finsId\" class=\"form-control\">");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_appId\">产品应用</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"curRowBody_appId\" name=\"appId\" class=\"form-control\">");
//    	for(var i=0;i<appIdDS.length;i++)
//    	{
//    		htmlInfo.append("<option value="+appIdDS[i].parameterValue+">"+appIdDS[i].parameterName+"</option>");
//    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_category\">决策规则类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"curRowBody_category\" name=\"category\" class=\"form-control\">");
    	for(var i=0;i<categoryDS.length;i++)
    	{
    		htmlInfo.append("<option value="+categoryDS[i].parameterValue+">"+categoryDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_name\">规则名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"curRowBody_name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_caseId\">决策条件</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"curRowBody_caseId\" name=\"caseId\" class=\"form-control\">");
//    	for(var i=0;i<caseIdDS.length;i++)
//    	{
//    		htmlInfo.append("<option value="+caseIdDS[i].parameterValue+">"+caseIdDS[i].parameterName+"</option>");
//    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"curRowBody_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn1\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn1\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+bodyModalId).append(htmlInfo.toString()); 
	}
	
	function init_edit_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"ruleEditModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"edit\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">决策规则</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"editform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ruleId\">决策规则编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"ruleId\" id=\"ruleId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appId\">应用编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"category\">决策规则类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
    	for(var i=0;i<categoryDS.length;i++)
    	{
    		htmlInfo.append("<option value="+categoryDS[i].parameterValue+">"+categoryDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">规则名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"caseId\">决策条件</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"caseId\" name=\"caseId\" class=\"form-control\">");
//    	for(var i=0;i<caseIdDS.length;i++)
//    	{
//    		htmlInfo.append("<option value="+caseIdDS[i].parameterValue+">"+caseIdDS[i].parameterName+"</option>");
//    	}
		htmlInfo.append("</select>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+bodyModalId).append(htmlInfo.toString()); 
	}
	
	function schange(){
        $("#curRowBody_finsId").change(function(){
            var finsId="";
            finsId = $("#curRowBody_finsId").val();
            $("#curRowBody_appId").empty();
            if(finsId!=null && finsId!=""){
                $.ajax({
                    type: "POST",
                    url: "/application/getApplicationByFins",
                    data:{"finsId" : finsId},
                    error: function(request) {
                    },
                    success: function(data) {
                    	applicationDS = eval(data);
                        var htmlInfo = new StringBuffer();
                        for(var i=0;i<applicationDS.length;i++)
                    	{
                            if(i == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+applicationDS[i].parameterValue+">"+applicationDS[i].parameterName+"</option>");
                        }
                        $("#curRowBody_appId").append(htmlInfo.toString());
                    }
                });
            }else{
                $("#curRowBody_appId").empty();$("#curRowBody_appId").css({'display':'none'})
            }
        })
        
         $("#curRowBody_appId").change(function(){
            var appId="";
            appId = $("#curRowBody_appId").val();
            $("#curRowBody_caseId").empty();
            if(appId!=null && appId!=""){
                $.ajax({
                    type: "POST",
                    url: "/dePoCase/caseIdByAppIdDS",
                    data:{"appId" : appId},
                    error: function(request) {
                    },
                    success: function(data) {
                    	caseIdByAppIdDS = eval(data);
                        var htmlInfo = new StringBuffer();
                        for(var i=0;i<caseIdByAppIdDS.length;i++)
                    	{
                            if(i == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+caseIdByAppIdDS[i].parameterValue+">"+caseIdByAppIdDS[i].parameterName+"</option>");
                        }
                        $("#curRowBody_caseId").append(htmlInfo.toString());
                    }
                });
            }else{
                $("#curRowBody_caseId").empty();$("#curRowBody_caseId").css({'display':'none'})
            }
        })
    }
	
		
	//提交添加表单
	function submit_form()
	{
		$('#submit_btn1').on("click",function(){
			var finsId = $('#curRowBody_finsId').val();
			var appId = $('#curRowBody_appId').val();
			var name = $('#curRowBody_name').val();
			var url = bodyAddController;
			if (finsId != "" && finsId != undefined && appId!="" && appId != undefined) {
				if(name!="" && name != undefined)
				{
					$.ajax({
						type: "POST",
						dataType: "json",
						url:url,
						data:$('#addform').serialize(), //formid
						error: function(request) {
						},
						success: function(data) {
							bootbox.alert(data.msg,"");
							$('#addform')[0].reset();
							$('#ruleModal').modal('hide');
							$('#tableId3').bootstrapTable('refresh');
						}
					});
				}else{
					showError("规则名称为必填项，请填写！", '');
				}
			}else {
				showError("请选择金融机构和产品！", '');
			}
		});
		
		$('#cancel_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#addform')[0].reset();
		});
	}
	//提交编辑表单
	function submit_edit_form()
	{
		$('#submit_btn').on("click",function(){
			var name = $('#name').val();
			var url = bodyAddController;
				if(name!="" && name != undefined)
				{
					$.ajax({
						type: "POST",
						dataType: "json",
						url:url,
						data:$('#editform').serialize(), //formid
						error: function(request) {
						},
						success: function(data) {
							bootbox.alert(data.msg,"");
							$('#editform')[0].reset();
							$('#ruleEditModal').modal('hide');
							$('#tableId3').bootstrapTable('refresh');
						}
					});
				}else{
					showError("规则名称为必填项，请填写！", '');
				}
		});
		
		$('#cancel_btn').on("click",function(){
			$('#editform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#editform')[0].reset();
		});
	}
	/*------------------------------------------结束表单的操作-----------------------------------------*/
	
	
	/*------------------------------------------执行措施操作-----------------------------------------*/
	
	function init_left_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#leftmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"leftmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	function init_right_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#rightmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"rightmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//满足
	function init_left_table()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"leftModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"leftdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"leftclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">添加</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#leftlistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-striped\" id=\"leftlistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"leftcancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"leftsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#leftmodalid").append(htmlInfo.toString()); 
	}
	//不满足
	function init_right_table()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"rightModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"rightdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"rightclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">添加</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#rightlistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-striped\" id=\"rightlistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"rightcancel_btn\" data-dismiss=\"modal\">取消</button> ");
		htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"rightsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		$("#rightmodalid").append(htmlInfo.toString()); 
	}
	
});