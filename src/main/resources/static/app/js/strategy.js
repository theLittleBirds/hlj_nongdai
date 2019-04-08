$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var actionInitBody = false;		//主数据操作按钮初始化标志
	var actionInitOpt2 = false;		//选项二数据操作按钮初始化标志
	var curAppCode;					//当前选中的主数据行的应用编号
	var curRowBody;					//当前选中的主数据行
	var categoryDS;
	var typeDS;
	var appIdDS;
	
	var totalSyncDS4Body = 4;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	var totalSyncDS4Opt2 = 1;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
	
	var ruleDS;
	var startDS;
	
	
	var baseDivId = "base_div";
	
	//应用管理
	var bodyDivId = "application_div";
	var bodyTableId = "application_table";
	var bodyModalId = "application_modal";
	var bodyDialogId = "application_dialog";
	var bodyFormId = "application_form";
	var bodyDelAction = "/strategy/delete";
	
	//选项页二
	var opt2Object = "bases";
	var opt2DivId = opt2Object + "_div";
	var opt2TableId = opt2Object + "_table";
	var opt2ModalId = opt2Object + "_modal";
	var opt2DialogId = opt2Object + "_dialog";
	var opt2FormId = opt2Object + "_form";
	var opt2DelAction = "/strategy/delStrule";
	
	
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
		
		//决策策略类别
		if(categoryDS == undefined)
		{
			var appcatParaGrpName = "STRATEGY_CATEGORY";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	categoryDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//决策策略类型
		if(typeDS == undefined)
		{
			var appcatParaGrpName = "STRATEGY_TYPE";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	typeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		
		if(startDS == undefined)
		{
			var appcatParaGrpName = "RULE_STARTTIME";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	startDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
	}
	
	function loadSyncDS4Body2 (){
		if(ruleDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/rule/getRuleDS",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	ruleDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}

	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#strategy_id").on("click", function(){
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
			url: "/strategy/getTree",
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
                    title:'策略'
                },{
                	field:'category',
                	title:'类别',
                	formatter: categorymatter
                },{
                	field:'type',
                	title:'类型',
                	formatter: typematter
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
            	countSyncDS4Opt2 = 0;
            	ruleDS = undefined;
            	curRowBody = row;
            	if(row._level == 3){
            	 	curStrategyId = row.strategyId;
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
		loadSyncDS4Body2();
		
		show_view_opt2();
		
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
			url: "/strategy/strulelist",
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
					field: 'ruleId',
					title: '策略规则',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: ruleIdFormatter
				},
				{
					field: 'start',
					title: '开始时间',
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
	  		strategyId: curStrategyId,
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
	
	function ruleIdFormatter(value) {
    	for(var i=0;i<ruleDS.length;i++)
    	{
    		if(ruleDS[i].parameterValue == value)
    		{
    			return ['<span>'+ruleDS[i].parameterName+'</span>'];
	    	}
    	}
	}
	
	function startFormatter(value) {
    	for(var i=0;i<startDS.length;i++)
    	{
    		if(startDS[i].parameterValue == value)
    		{
    			return ['<span>'+startDS[i].parameterName+'</span>'];
	    	}
    	}
	}
	
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
			htmlInfo.append("<h3 class=\"panel-title\">决策策略</h3>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");		//panel
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li  class=\"active\"><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">策略规则</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
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
//			htmlInfo.append("<button id=\"btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权角色");
//			htmlInfo.append("</button>");
			
			$("#toolbar").append(htmlInfo.toString()); 
			
			$('#btn_add').on("click",function()
			{
				if($("#"+bodyDialogId).length == 0)//判断表单是否已经初始化
				{
					init_modal_body();		//初始化提交表单
					init_form_action_body();		//提交表单操作
				}
				$("#"+bodyFormId)[0].reset();
				$("#"+bodyModalId).modal('show');
			});
			
			$('#btn_edit').on("click",function()
			{
				if(curRowBody != undefined && curRowBody._level == 3){
						if($("#"+bodyDialogId).length == 0)//判断表单是否已经初始化
						{
							init_modal_body();		//初始化提交表单
							init_form_action_body();		//提交表单操作
						}
						if(curRowBody.strategyId != ""){
							$.ajax({
						    	   type:'post',
						    	   url: "/strategy/getStrategy",
						    	   data:{"strategyId":curRowBody.strategyId},
						    	   success:function(data){
						    		   edit(data);		      
						    	   }
						     });
						}else{
							bootbox.alert("失败");
						}
				}else{
					bootbox.alert("请选择策略修改");
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
						if(selData[i]._level == 3){
						  objCodes += selData[i].strategyId + ',';
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
			$('#strategyId').val(entry.strategyId);
			$('#appId').val(entry.appId);
			$('#type').val(entry.type);
			$('#category').val(entry.category);
			$('#name').val(entry.name);
			$('#seqno').val(entry.seqno);
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
					init_modal_opt2();//初始化提交表单
					init_form_action_opt2();//提交表单操作
					$("#"+opt2FormId)[0].reset();
					$('#opt2_strategyId').val(curStrategyId);
				    $("#"+opt2ModalId).modal('show');
			
			});
			
			$("#"+opt2Object+"_btn_edit").on("click",function()
			{
				if(curRowBody2 != undefined){
					init_modal_opt2();//初始化提交表单
					init_form_action_opt2();//提交表单操作
					$("#"+opt2FormId)[0].reset();
					$('#opt2_id').val(curRowBody2.id);
					$('#opt2_strategyId').val(curRowBody2.strategyId);
					$('#opt2_ruleId').val(curRowBody2.ruleId);
					$('#opt2_start').val(curRowBody2.start);
					$('#opt2_seqno').val(curRowBody2.seqno);
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
							if(selData[i]._level != 0){
								objCodes += selData[i].id + ',';
							}
						}
						objCodes = objCodes.substring(0, objCodes.length-1)+"";
						delMany(opt2DelAction, objCodes,'#'+opt2TableId);
					}
					else
					{
						bootbox.alert("请选择要删除的数据记录！","");
					}
			});
			actionInitOpt2 = true;
		}
	}
	
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
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">策略</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appCode\">策略编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"strategyId\" id=\"strategyId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsCode\">应用</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"appId\" name=\"appId\" class=\"form-control\">");
	    	for(var i=0;i<appIdDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+appIdDS[i].parameterValue+">"+appIdDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"category\">决策类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"type\" name=\"type\" class=\"form-control\">");
	    	for(var i=0;i<typeDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+typeDS[i].parameterValue+">"+typeDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">决策类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
	    	for(var i=0;i<categoryDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+categoryDS[i].parameterValue+">"+categoryDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"memo\">策略名称</label>");
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
					url: "/strategy/save",
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
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">决策策略</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt2FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_id\">决策策略ID</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"id\" id=\"opt2_id\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_strategyId\">决策策略</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"strategyId\" id=\"opt2_strategyId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_ruleId\">决策规则</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_ruleId\" name=\"ruleId\" class=\"form-control\">");
			for(var i=0;i<ruleDS.length;i++)
	    	{    
				if(i == 0)
				{
					htmlInfo.append("<option value=></option>");
				}
				htmlInfo.append("<option value="+ruleDS[i].parameterValue+">"+ruleDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_start\">开始时间</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_start\" name=\"start\" class=\"form-control\">");
	    	for(var i=0;i<startDS.length;i++)
	    	{
	    		if(i == 0)
				{
					htmlInfo.append("<option value=></option>");
				}
	    		htmlInfo.append("<option value="+startDS[i].parameterValue+">"+startDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
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
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/strategy/saveStrule",
					data: $("#"+opt2FormId).serialize(), //formid
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt2FormId)[0].reset();
				    	$("#"+opt2ModalId).modal('hide');
				    	$("#"+opt2TableId).bootstrapTable('refresh');
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
  
});
