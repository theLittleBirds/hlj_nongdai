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
	var groupDS;
	var finsDS;
	var finsDS1;
	var appIdDS;
	var finsGroupDS;
	
	var totalSyncDS4Body = 4;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	var totalSyncDS4Opt2 = 3;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
	var delParaDS;
	var appStatusDS;
	var loanTypeDS;
	
	var baseDivId = "base_div";
	
	//应用管理
	var bodyDivId = "group_div";
	var bodyTableId = "group_table";
	var bodyModalId = "group_modal";
	var bodyDialogId = "group_dialog";
	var bodyFormId = "group_form";
	var bodyDelAction = "/group/delete";
	
	//选项页一
	var opt1Object = "case";
	var opt1DivId = opt1Object + "_div";
	var opt1TableId = opt1Object + "_table";
	var opt1ModalId = opt1Object + "_modal";
	var opt1DialogId = opt1Object + "_dialog";
	var opt1FormId = opt1Object + "_form";
	var opt1DelAction = "/dePoCase/delete";
	
	
	//选项页二
	var opt2Object = "application";
	var opt2DivId = opt2Object + "_div";
	var opt2TableId = opt2Object + "_table";
	var opt2ModalId = opt2Object + "_modal";
	var opt2DialogId = opt2Object + "_dialog";
	var opt2FormId = opt2Object + "_form";
	var opt2DelAction = "/application/delete";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS4Body()
	{
		//产品应用名称-编号
		if(appIdDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/application/appIdDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	appIdDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}

		
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
		
		if(loanTypeDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "LOAN_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	loanTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//金融机构--权限查询
		if(finsDS1 == undefined)
		{
			$.ajax({
				type: "POST",
				url: "/group/getFins",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	finsDS1 = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
	}
	
	function loadSyncDS4Body2 (){
		
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
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
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
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}
		
		//应用类别名称-编号
		if(groupDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/group/getGroupDS2",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	groupDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#group_id").on("click", function(){
		init_global();
	
		init_layout();								//初始化页面布局
		
		init_tool_action_body();
		
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
			url: "/group/groupTreeStr",
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
            	curRowOpt2 = "";
            	curRowOpt1 = "";
            	countSyncDS4Opt2 = 0;
            	curRowBody2 = undefined;
            	appStatusDS = undefined;
            	groupDS = undefined;
            	delParaDS = undefined;
            	curRowBody = row;
            	if(row._level != 0){
            	 	curAppCode = row.groupId;
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
	
	
	function typematter(value) {
    	for(var i=0;i<typeDS.length;i++)
    	{
    		if(typeDS[i].parameterValue == value)
    		{
    			return ['<span>'+typeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	function categorymatter(value) {
    	for(var i=0;i<categoryDS.length;i++)
    	{
    		if(categoryDS[i].parameterValue == value)
    		{
    			return ['<span>'+categoryDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//选项数据显示
	
	function show_view_opt()
	{
//		show_view_opt1();
//		
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
//                	formatter: dataTypeFormatter
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
			url: "/application/listApplication",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-270,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,20],
			pageSize:10,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt2QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			showRefresh: true,
//			showToggle: true,
			toolbar:'#'+opt2Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt2ResponseHandler,
			columns: [
				{
					field: 'section_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'cname',
					title: '应用名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'groupId',
					title: '应用分类',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: startFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(row){
            },
            onClickRow:function(row){
            	curRowBody2 = row;
            	$("#"+opt2TableId).each(function() { 
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
		$("#"+opt2TableId).bootstrapTable('refresh');
	}
	//设置table传递到服务器的参数
	function opt2QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	    	offset: params.offset/params.limit+1,		//页码
	  	    limit: params.limit,			//页面大小
	  		groupId: curAppCode,
	    };
	    return temp;
	 }
	
	//处理服务器端响应数据，使其适应分页格式
	function opt2ResponseHandler(res)
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
	
	function startFormatter(value) {
    	for(var i=0;i<groupDS.length;i++)
    	{
    		if(groupDS[i].parameterValue == value)
    		{
    			return ['<span>'+groupDS[i].parameterName+'</span>']
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
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-5\" style=\"\">");
			
			htmlInfo.append("<div class=\"panel-heading\" style=\"height: 42.5px;\">");
			htmlInfo.append("<h3 class=\"panel-title\">应用类别</h3>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");		//panel
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-7\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li  class=\"active\"><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">产品应用</a></li>");
//			htmlInfo.append("<li><a href=\"."+opt1DivId+"\" data-toggle=\"tab\">待续</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
//			//opt1 tab
//			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt1DivId + "\">");
//			
//			htmlInfo.append("<div id=\"" + opt1Object + "_toolbar\" class=\"btn-group\" style=\"display: none;margin-top:5px;margin-bottom:5px;\">");
//			htmlInfo.append("</div>");
//			
//			htmlInfo.append("<div id=\"" + opt1DivId + "\">");
//	        htmlInfo.append("</div>");
//	        
//			htmlInfo.append("</div>");
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
					    	   url: "/group/getGroupById",
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
				url: "/group/getGroupDS",
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
//			$('#parentGroupIds').val(entry.parentGroupIds);
			$('#name').val(entry.name);
			$('#seqno').val(entry.seqno);
			schange();
			$("#"+bodyModalId).modal('show');
		}
		else{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
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
			htmlInfo.append("<button id=\"" + opt2Object + "_btn_copy\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>应用复制");
			htmlInfo.append("</button>");
			
			$("#"+opt2Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt2Object+"_btn_add").on("click",function()
			{
					init_modal_opt2();//初始化提交表单
					init_form_action_opt2();//提交表单操作
					$("#"+opt2FormId)[0].reset();
					for(var i=0;i<finsDS.length;i++)
			    	{
			    		if(finsDS[i].parameterValue == curRowBody.finsId)
			    		{
			    			$('#opt2_finsCode').val(finsDS[i].parameterName);
				    	}
			    	}
					for(var i=0;i<groupDS.length;i++)
			    	{
			    		if(groupDS[i].parameterValue == curRowBody.groupId)
			    		{
			    			$('#opt2_groupId').val(groupDS[i].parameterName);
				    	}
			    	}
//					
				    $("#"+opt2ModalId).modal('show');
			
			});
			
			$("#"+opt2Object+"_btn_edit").on("click",function()
			{
				if(curRowBody2 != undefined){
					init_modal_opt2();//初始化提交表单
					init_form_action_opt2();//提交表单操作
					$("#"+opt2FormId)[0].reset();
					$('#opt2_appId').val(curRowBody2.appId);
					for(var i=0;i<finsDS.length;i++)
			    	{
			    		if(finsDS[i].parameterValue == curRowBody2.finsCode)
			    		{
			    			$('#opt2_finsCode').val(finsDS[i].parameterName);
				    	}
			    	}
					for(var i=0;i<groupDS.length;i++)
			    	{
			    		if(groupDS[i].parameterValue == curRowBody2.groupId)
			    		{
			    			$('#opt2_groupId').val(groupDS[i].parameterName);
				    	}
			    	}
					$('#opt2_cname').val(curRowBody2.cname);
					$('#opt2_ename').val(curRowBody2.ename);
					if(curRowBody2.ename!='1'){
						$('#opt2_stationRate').val(curRowBody2.stationRate);
						$('#opt2_farmerRate').val(curRowBody2.farmerRate);
						$(".rate").show();
					}
					$('#opt2_shortCname').val(curRowBody2.shortCname);
					$('#opt2_shortEname').val(curRowBody2.shortEname);
					$('#opt2_status').val(curRowBody2.status);
					$('#opt2_memo').val(curRowBody2.memo);
					$('#opt2_seqno').val(curRowBody2.seqno);
					$('#opt2_isDelete').val(curRowBody2.isDelete);
					
				    $("#"+opt2ModalId).modal('show');
				}else{
					bootbox.alert("请选择要编辑的数据记录！","");
				}
			});
			
			$("#"+opt2Object+"_btn_del").on("click",function()
			{			
				var selData = $('#'+opt2TableId).bootstrapTable('getSelections');//选中的数据
					var objCodes = '';
					if(selData.length > 0)
					{
						for(var i=0;i<selData.length;i++)
						{
							objCodes += selData[i].appId + ',';
						}
						objCodes = objCodes.substring(0, objCodes.length-1)+"";
						delMany(opt2DelAction, objCodes,'#'+opt2TableId);
					}
					else
					{
						bootbox.alert("请选择要删除的数据记录！","");
					}
			});
			$("#"+opt2Object+"_btn_copy").on("click",function()
			{
				if(curRowBody2 != undefined){
					init_modal_opt3();//初始化提交表单
					init_form_action_opt3();//提交表单操作
					schange1();
				}else{
					bootbox.alert("请选择要复制的数据记录！","");
				}
				$("#"+opt2FormId)[0].reset();	
				$('#appId').val(curRowBody2.appId);
				 $("#"+opt2ModalId).modal('show');
			});
			actionInitOpt2 = true;
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
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">应用类别</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupId\">类别编号</label>");
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
//			
//			htmlInfo.append("<div class=\"form-group\">");
//			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"parentGroupIds\">所有上级分类</label>");
//			htmlInfo.append("<div class=\"col-sm-8\">");
//			htmlInfo.append("<input readonly class=\"form-control\" name=\"parentGroupIds\" id=\"parentGroupIds\" type=\"text\" placeholder=\"\"/>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("</div>");
			
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
	
	//初始化主数据表单操作
	function init_form_action_body()
	{
		$('#body_submit_btn').on("click",function(){
			var name = $('#name').val();
			if(name != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/group/save",
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
		$("#finsId").change(function () {
			var finsId = $("#finsId").val();
			$.ajax({
				type: "POST",
				url: "/group/getGroupDS",
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
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt2ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt2DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt2Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">应用</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt2FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appCode\">应用编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt2_appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsCode\">金融机构</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"finsCode\" id=\"opt2_finsCode\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupId\">应用类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"groupId\" id=\"opt2_groupId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cname\">中文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"opt2_cname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ename\">类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_ename\" name=\"ename\"  class=\"form-control\">");
			for(var i=0;i<loanTypeDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+loanTypeDS[i].parameterValue+">"+loanTypeDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div style=\"display: none\" class=\"form-group rate\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortCname\">农户担保金比例（%）</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"farmerRate\" id=\"opt2_farmerRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div style=\"display: none\" class=\"form-group rate\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortCname\">站长担保金比例（%）</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"stationRate\" id=\"opt2_stationRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortCname\">中文简称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"shortCname\" id=\"opt2_shortCname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortEname\">额度计算公式</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"shortEname\" id=\"opt2_shortEname\" type=\"text\" placeholder=\"如:ED=(DL*0.7*17000+WL*0.7*20000)*T\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortEname\"></label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<label ><span style=\"color: red\">说明：ED表示额度，DL表示在册旱地面积，WL表示在册水田面积，T表示年限</span></label>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_status\" name=\"status\" class=\"form-control\">");
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
			htmlInfo.append("<textarea class=\"form-control\" name=\"memo\" id=\"opt2_memo\" rows=\"3\"></textarea>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt2_seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isDelete\">软删除标志</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_isDelete\" name=\"isDelete\" class=\"form-control\">");
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
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt2Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt2Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 
		    caculation();
	}
	function caculation()
	{
		$("#deleteParam").on("click",function(){
	    	
	    });
		//切换类别时显示或隐藏
		$("#opt2_ename").on("change",function(){
			var type= $("#opt2_ename").val();
			//单纯土地抵押贷
			if(type==1){
				$(".rate").hide();
				$("#opt2_farmerRate").val('');
				$("#opt2_stationRate").val('');
				//包含农资（2,3）
			}else{
				$(".rate").show();
			}
		});
		
	}
	
	function changeCategory(){
		
	}
	
	//提交表单
	function init_form_action_opt2()
	{
		$('#'+opt2Object+'_submit_btn').on("click",function(){
			$('#opt2_finsCode').val(curRowBody.finsId);
			$('#opt2_groupId').val(curRowBody.groupId);
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/application/save",
					data: $("#"+opt2FormId).serialize(), //formid
				    error: function(request) {
				        alert("11");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt2FormId)[0].reset();
				    	$("#"+opt2ModalId).modal('hide');
				    	$("#"+opt2TableId).bootstrapTable('refresh');
				    	curRowOpt2 = "";
				    }
				});
		});
		
		$('#'+opt2Object+'_cancel_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
		$('#'+opt2Object+'_close_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
	}
	
	//初始化表单
	function init_modal_opt3()
	{
		    $("#"+opt2ModalId).remove();
		   
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt2ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt2DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt2Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">应用复制</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt2FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appId\">应用Id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appName\">新应用名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"appName\" id=\"appName\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsId\">金融机构</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"finsId\" name=\"finsId\" class=\"form-control\">");
	    	for(var i=0;i<finsDS1.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+finsDS1[i].parameterValue+">"+finsDS1[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupId\">应用类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"groupId\" name=\"groupId\" class=\"form-control\">");
//	    	for(var i=0;i<groupDS.length;i++)
//	    	{
//	    		if(i == 0)
//	    		{
//	    			htmlInfo.append("<option value=></option>");
//	    		}
//	    		htmlInfo.append("<option value="+groupDS[i].parameterValue+">"+groupDS[i].parameterName+"</option>");
//	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt2Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt2Object+"_submit_btn2\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 
	}
	//提交表单
	function init_form_action_opt3()
	{
		$('#'+opt2Object+'_submit_btn2').on("click",function(){
			var appId = $("#appId").val();
			var appName  = $("#appName").val();
			var finsId  = $("#finsId").val();
			var groupId  = $("#groupId").val();
			if(appName != null && appName != "" && finsId != null && finsId != "" && groupId != null && groupId != ""){
				bootbox.dialog({
					message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>信息复制中，请稍等。。。</span>",
					backdrop: false,
					closeButton: false,
					className: "ipbootboxModal",
				});
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/copy/dataCopy",
					data: {"appName":appName,"finsId":finsId,"groupId":groupId,"appId":appId},
				    error: function(request) {
				    	$(".ipbootboxModal").remove();
				    	bootbox.alert(data.msg,"");
				    },
				    success: function(data) {
				    	$(".ipbootboxModal").remove();
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt2FormId)[0].reset();
				    	$("#"+opt2ModalId).modal('hide');
				    	$("#"+opt2TableId).bootstrapTable('refresh');
				    	curRowOpt2 = "";
				    }
				});
			}else{
				bootbox.alert("所有选项都不能为空，请检查");
			}
				
		});
		
		$('#'+opt2Object+'_cancel_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
		$('#'+opt2Object+'_close_btn').on("click",function(){
			$("#"+opt2FormId)[0].reset();
		});
	}
	function schange1(){
		$("#finsId").click(function(){
			$("#groupId").empty();
			var finsId = $("#finsId").val();
			$.ajax({
				type: "POST",
				url: "/group/getGroupDSByfinsId",
				data:{"finsId":finsId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	finsGroupDS = eval(data);
			    	var htmlInfo = new StringBuffer();
			    	for(var i=0;i<finsGroupDS.length;i++)
			    	{
			    		if(i == 0)
			    		{
			    			htmlInfo.append("<option value=></option>");
			    		}
			    		htmlInfo.append("<option value="+finsGroupDS[i].parameterValue+">"+finsGroupDS[i].parameterName+"</option>");
			    	}
			    	$("#groupId").append(htmlInfo.toString());
			    }
			});
		})
	}
});
