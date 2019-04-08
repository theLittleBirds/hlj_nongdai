$(document).ready(function(){
	//$.fn.editable.defaults.mode = 'inline';
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var curScore = "";				//记录所选择的评分卡行用于点击前面选择框
	var curRowData = "";			//记录所选择的评分卡行用于点击行
	var curRowBody = "";
	var curscvar = "";				//记录所选择的评分卡变量行用于点击前面选择框
	var curscvarRowData = "";		//记录所选择的评分卡变量行用于点击行
	var curScoreid;
	var curCvId;
	var rightContent_model = "rightContent_model";
	
	var unroleId = "";			//记录去掉选择的角色
	var arrRoleIds = new Array();	//记录去掉选择的角色数组
	
	var unvarId = "";				//记录去掉选择的基础参数
	var arrVarIds = new Array();	//记录去掉选择的基础参数数组
	
	var totalSyncDS = 10;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	var totalSyncDS4YxData = 1;		//同步已选数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4YxData = 0;		//同步已选数据集计数器，当前成功获取个数
	
	var countScVar = 0;				//添加判例时评分卡变量计数器
	var totalScVar = 1;				//总数
	
	var countScVarAction = 0;		//控制评分卡变量操作栏加载次数
	var totalScVarAction = 2;		//控制操作栏加载次数
	
	var countComvalueAction = 0;		//控制多变量组合操作栏加载次数
	var totalComvalueAction = 1;		//控制操作栏加载次数
	
	var countComcaseAction = 0;		//控制组合实例操作栏加载次数
	var totalComcaseAction = 1;		//控制操作栏加载次数
	
	var countClassAction = 0;		//控制评分等级操作栏加载次数
	var totalClassAction = 1;		//控制操作栏加载次数
	
	var countScVarCaseAction = 0;	//控制评分卡变量判例操作栏加载次数
	var totalScVarCaseAction = 1;	//控制操作栏加载次数
	
	var countScvarChart = 0;			//控制变量图表显示
	var totalScvarChart = 1;
	var countScvarCaseChart = 0;
	var totalScvarCaseChart = 1;
	
	var finsIdDS;
	var varIdDS;
	var vdtypeParaDS;
	var typeParaDS;
	var categoryParaDS;
	var scoreStatusDS;
	var delParaDS;
	var orgNameDS;
	var varTypeDS;
	var yxRole = "";
	var scVarIdDS = null;//判例变量
	var scvarIdDS = null;
	var scvarIdDS2 = null;
	var lowLimitDS = null;
	var highLimitDS = null;
	var paraSEDS = null;//起止选择
	
	var baseDivId = "base_div";
	var modalId = "modalId";
	//评分卡参数
	var scoreDivId = "score_div";
	var scoreTableId = "score_table";
	var scoreModalId = "score_modal";
	var scoreDialogId = "score_dialog";
	var scoreFormId = "score_form";
	//评分卡变量参数
	var scvarDivId = "scvar_div";
	var scvarTujieId = "scvar_tujie";
	var scvarTableId = "scvar_table";
	var scvarModalId = "scvar_modal";
	var scvarDialogId = "scvar_dialog";
	var scvarFormId = "scvar_form";
	//判例变量参数
	var scvarcaseDivId = "scvarcase_div";
	var scvarcaseTujieId = "scvarcase_tujie";
	var scvarcaseTableId = "scvarcase_table";
	//评分等级参数
	var classTableId = "class_table";
	var classDivId = "class_div";
	var classDialogId = "class_dialog";
	var classModalId = "class_modal";
	var classFormId = "class_form";
	//多变量组合
	var comvalueTableId = "comvalue_table";
	var comvalueDivId = "comvalue_div";
	var comvalueDialogId = "comvalue_dialog";
	var comvalueModalId = "comvalue_modal";
	var comvalueFormId = "comvalue_form";
	//组合实例
	var comcaseTableId = "comcase_table";
	var comcaseDivId = "comcase_div";
	var comcaseDialogId = "comcase_dialog";
	var comcaseModalId = "comcase_modal";
	var comcaseFormId = "comcase_form";
	//图解参数
	var scvarPieChart;
	var scvarcaseBarChart;
	var scvar_data;
	var bar_score = new Array();
	var bar_text = new Array();
	
	//基础变量
	var paraDS;
	var varDivId = "var_div";
	var baseTableId = "var_table";
	var baseModalId = "var_modal";
	var baseFormId = "var_form";
	//数据参数
	var paraDivId = "para_div";
	var paraTableId = "para_table";
	var paraModalId = "para_modal";
	var paraFormId = "para_form";
	
	var yxData = "";
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		//金融机构名称
		if(finsIdDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/fins/getFinsDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	finsIdDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//组织机构名称
		if(orgNameDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/org/getOrgDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	orgNameDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//基础变量名称
		if(varIdDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/var/getVarIdDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	varIdDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//数据类型
		if(vdtypeParaDS == undefined)
		{	//保证仅获取一次
			var vdtypeParaGrpName = "VAR_DATA_TYPE";			//变量数据类型
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":vdtypeParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	vdtypeParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//评分卡类型
		if(typeParaDS == undefined)
		{	//保证仅获取一次
			var typeName = "SCORE_TYPE";			//变量数据类型
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":typeName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	typeParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//评分卡类别
		if(categoryParaDS == undefined)
		{	//保证仅获取一次
			var categoryName = "SCORE_CATEGORY";			//变量数据类型
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":categoryName},
				error: function(request) {					
				},
				success: function(data) {
					categoryParaDS = eval(data);
					countSyncDS ++;
					showView();
					showvarView();
					showvarParaView();
				}
			});
		}
		
		//状态
		if(scoreStatusDS == undefined)
		{	//保证仅获取一次
			var statusName = "SCORE_STATUS";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":statusName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	scoreStatusDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//软删除
		if(delParaDS == undefined)
		{	//保证仅获取一次
			var ustsParaGrpName = "IS_DELETE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	delParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		if(paraDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/scorePara/getParaDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	paraDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//评分卡变量类型
		if(varTypeDS == undefined)
		{	//保证仅获取一次
			var varTypeName = "SCORE_VAR_TYPE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":varTypeName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	varTypeDS = eval(data);
			    	countSyncDS ++;
			    	showView();
					showvarView();
					showvarParaView();
			    }
			});
		}
		
		//评分卡判例下限符
		if(lowLimitDS == undefined)
		{
			var lowLimitName = "LOW_LIMIT";
			$.ajax({
				type: "POST",
				url: "/para/paralist2",
				data:{"paraGroupName":lowLimitName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	lowLimitDS = eval(data);
			    }
			});
		}
		//评分卡判例上限符
		if(highLimitDS == undefined)
		{
			var highLimitName = "HIGH_LIMIT";
			$.ajax({
				type: "POST",
				url: "/para/paralist2",
				data:{"paraGroupName":highLimitName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	highLimitDS = eval(data);
			    }
			});
		}
		
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#score_id").on("click", function(){
		init_global();
		loadSyncDS();
		showView(); 
		showvarView();
		showvarParaView();
		curRowData = "";//切换菜单时，清空选中的数据
		curRowBody = "";//切换菜单时，清空选中的数据
		countScVarAction = 0;//防止确定删除框出现好多个
		countClassAction = 0;
		countComvalueAction = 0;
		countComcaseAction = 0;
		countScVarCaseAction = 0;
		
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
    }
    
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	//参数类别显示
	function showView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		
		init_layout();							//初始化table外层的div视图
		
		init_scoretable();							//初始化table视图
		
		init_modal_home();						//初始化modal视图
		init_scvar_modal();						//初始化批量添加时的modal
		init_comcase_modal();
		
		init_tool_action();						//初始化工具栏操作

		$("#"+scoreTableId).bootstrapTable({
			method:'post',
			url: "/score/selectScore",
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
                },{
                	field:'version',
                	title:'版本'
                },{
                	field:'finsId',
                	title:'金融机构',
                	formatter:finsIdFormatter
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
			onLoadSuccess:function(data){
            	$("#"+scoreTableId).treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$("#"+scoreTableId).bootstrapTable('resetWidth');
                    }
                });
            },
            onClickRow:function(row){
            	curRowData = row;
            	init_scvarcasetable();//初始化评分卡变量判例table
            	init_scvartable();	  //初始化评分卡变量table
            	init_comvaluetable();	  //初始化多变量组合table
            	init_classtable();	  //初始化评分等级table
            	
            	if(row._level != 0){
            		curScoreId = row.scoreId;
            		countScVar = 0;
            		findScVarId();      //根据scoreid去查找scvarid,用于判例表选择变量
            		getScvarId();      
            		getscvarData();       //得到评分卡变量构造json,用于图表
            		showscvarcaseView();  //填充判例数据
            		showcomvalueView();      //填充多变量组合
            		showclassView();      //填充评分等级
            	}
				$("#" + scoreTableId).each(function() { 
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
            	curScore = row;
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		
	}
	
	//table里面显示中文描述
	function finsIdFormatter(value) {
    	for(var i=0;i<finsIdDS.length;i++)
    	{
    		if(finsIdDS[i].parameterValue == value)
    		{
    			return ['<span>'+finsIdDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	function typeFormatter(value) {
		for(var i=0;i<typeParaDS.length;i++)
		{
			if(typeParaDS[i].parameterValue == value)
			{
				return ['<span>'+typeParaDS[i].parameterName+'</span>']
			}
		}
	}
	
	function categoryFormatter(value) {
    	for(var i=0;i<categoryParaDS.length;i++)
    	{
    		if(categoryParaDS[i].parameterValue == value)
    		{
    			return ['<span>'+categoryParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
/*--------------------------------------------------------------------*/
	function showvarView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		init_vartable();								//初始化table
		init_modal();								//初始化modal
		init_var_tool_action();				//初始化工具栏操作
		
		//列操作事件定义
		var varoperateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "edit")
			{
				edit(row);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
		}};		
		
		$('#'+baseTableId).bootstrapTable({
			method: 'post', 
			url: "/var/listVar",
			dataType: "json",
			cache: false,
			height: $(window).height()-271,
			//striped: true,//使表格带有条纹
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [20,40,60,80,100,120],
			pageSize:20,
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
			//clickToSelect: true,//点击行即可选中单选/复选框  
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
					field: 'varId',
					title: '变量编号',
					align: 'center',
					width: '98',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '120',
					valign: 'middle',
				},
				{
					field: 'ename',
					title: '英文名称',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'paragrpName',
					title: '参数类别',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: pGrpFormatter
				},
				{
					field: 'dataType',
					title: '数据类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: dtypeFormatter
				},
				{
					field: 'cdesc',
					title: '中文描述',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'edesc',
					title: '英文描述',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'memo',
					title: '备注',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle'
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '',
					valign: 'middle',
					formatter: varactionFormatter,
					events: varoperateEvents
				}
			],
			onLoadSuccess:function(){
			
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败1！","");
            },
            onClickRow:function(){
            	$('#'+baseTableId).each(function() { 
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
            }
		});
	}
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
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
	
/*----------------------------------------------------------------------*/
	function showvarParaView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		init_para_table();							//初始化table
		init_para_modal();						//初始化modal
		init_para_tool_action();					//初始化工具栏操作
		
		$('#'+paraTableId).bootstrapTable({
			url: "/scorePara/select",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-200,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100],
			pageSize:20,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: paraQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#para_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: paraResponseHandler,
			columns: [
						{
							field: 'mapping_selection',
							titleTooltip: '全选/全不选',
							checkbox: true,
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'id',
							title: '参数Id',
							align: 'left',
							width: '',
							valign: 'middle',
							visible: false
						},
						{
							field: 'groupName',
							title: '类别名称',
							align: 'left',
							width: '',
							valign: 'middle',
						},
						{
							field: 'groupDescr',
							title: '类别描述',
							align: 'left',
							width: '',
							valign: 'middle'
						},
						{
							field: 'name',
							title: '参数名称',
							align: 'left',
							width: '',
							valign: 'middle',
						},
						{
							field: 'value',
							title: '参数值',
							align: 'center',
							width: '',
							valign: 'middle'
						},
						{
							field: 'descr',
							title: '参数表述',
							align: 'left',
							width: '',
							valign: 'middle'
						},
						{
							field: 'seqno',
							title: '序号',
							align: 'center',
							width: '',
							valign: 'middle'
						}
					],
			onLoadSuccess:function(){
//				bootbox.alert("数据加载成功33333！","");
            },
	        onClickRow:function(row){
	        	curRowBody = row;
	        	  $('#'+paraTableId).each(function() {
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
//	            	bootbox.alert("数据加载失败33333！","");
	            }
			});
	}
	//设置table传递到服务器的参数
	function paraQueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function paraResponseHandler(res)
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
	
/*---------------------------------------------------------------------------*/
	function getScvarId()
	{
		//选择基础变量
		$.ajax({
			type: "POST",
			url:"/scvar/getScvarIdDS",
			data:{"scid":curScoreId},
		    error: function(request) {
		        
		    },
		    success: function(data) {
		    	scvarIdDS = eval(data);
		    	countScVarAction++;
		    	showscvarView();
		    }
		});
		//选择组合变量
		$.ajax({
			type: "POST",
			url:"/scvar/getScvarIdDS2",
			data:{"scid":curScoreId},
		    error: function(request) {
		        
		    },
		    success: function(data) {
		    	scvarIdDS2 = eval(data);
		    }
		});
	}
/*-------------------------------------------------------------------------------------------*/	
	//用于添加判例时选择变量
	function findScVarId()
	{
		var chart = "";
		//选择变量
		$.ajax({
			type: "POST",
			url:"/scvar/scVarIdList",
			data:{"scid":curScoreId,"chart":chart},
		    error: function(request) {
		        
		    },
		    success: function(data) {
		    	scVarIdDS = eval(data);
		    	countScVar++;
		    	showscvarcaseView();
		    }
		});
	}
	
	//评分卡变量判例数据显示
	function showscvarcaseView()
	{
		if(countScVar < totalScVar)
		{
			return;
		}
		if(countScVarCaseAction < totalScVarCaseAction)//只加载一次，防止确定删除框出现好多个。
		{
			init_scvarcase_tool_action();//初始化评分卡变量判例操作
		}
		if(curRowData.status != 1)
		{
			$('#scvarcase_toolbar').show();
		}else{
			$('#scvarcase_toolbar').hide();
		}
		
		$("#"+scvarcaseTableId).bootstrapTable({
			url: "/scVarCase/listScVarCase",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-280,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,40,80,100],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: scvarcaseQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			toolbar:'#scvarcase_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: scvarcaseResponseHandler,
			onEditableSave:function(field,row,oldValue,$el){
				$("."+row.caseId).removeAttr("disabled");
			},
			columns: [
				{
					field: 'scvarcase_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'caseId',
					title: '判例编号',
					align: 'center',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'scvarId',
					title: '变量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
					align: 'center',
					width: '20',
					valign: 'middle',
					editable: {
	          			title: '选择变量',
		          		type: 'select',
		          	    emptytext: '选择',
		          	    source: scVarIdDS,
		          	    validate: function (value) {
		          	    	//字段验证
		          	    	$.ajax({
		          				type: "POST",
		          				url:"/scvar/getScvarById",
		          				data:{"scvarId":value},
		          			    error: function(request) {
		          			        
		          			    },
		          			    success: function(data) {
		          			    	var type = data.dataType;
				                   if(type == '1') {
				                	   //隐藏起始文本和截止文本
				                	   $("#scvarcase_table tr td:eq(4)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(5)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(6)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(7)").children().css('display','none');
				                	   
				                	   $("#scvarcase_table tr td:eq(6)").children().css('display','block');
				                	   $("#scvarcase_table tr td:eq(7)").children().css('display','block');
				                	   $("#scvarcase_table tr td:eq(8)").children().css('display','block');
				                	   $("#scvarcase_table tr td:eq(9)").children().css('display','block');
				                   }
				                   if(type == '2') {
				                	   //隐藏起始和截止
				                	   $("#scvarcase_table tr td:eq(4)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(5)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(6)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(7)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(8)").children().css('display','none');
				                	   $("#scvarcase_table tr td:eq(9)").children().css('display','none');
				                	   
				                	   $("#scvarcase_table tr td:eq(4)").children().css('display','block');
				                	   $("#scvarcase_table tr td:eq(5)").children().css('display','block');
				                	   $("#scvarcase_table tr td:eq(6)").children().css('display','block');
				                   }
		          			    }
		          			});
	                }
					}
				},
				{
					field: 'cdesc',
					title: '值描述',
					align: 'center',
					width: '',
					valign: 'middle',
					editable: {
						emptytext: '填写',
	                    type: 'text'
	                }
				},
				{
					field: 'edesc',
					title: '英文描述',
					align: 'center',
					width: '',
					valign: 'middle',
	                visible: false
				},
				{
					field: 'score',
					title: '分数',
					align: 'center',
					width: '10',
					valign: 'middle',
	          		editable: {
						emptytext: '填写',
	                    type: 'text'
	                }
				},
				{
					field: 'startText',
					title: '起始文本',
					align: 'center',
					width: '20',
					valign: 'middle',
	          		editable: {
						emptytext: '填写',
	                    type: 'text'
	                }
				},
				{
					field: 'endText',
					title: '截止文本',
					align: 'center',
					width: '20',
					valign: 'middle',
	          		editable: {
						emptytext: '填写',
	                    type: 'text'
	                }
				},
				{
					field: 'lowLimit',
					title: '下限符',
					align: 'center',
					width: '10',
					valign: 'middle',
					editable: {
						title: '选择下限符',
		          		type: 'select',
		          	    emptytext: '选择',
		          	    source: lowLimitDS,
		          	    },
				},
				{
					field: 'start',
					title: '起始',
					align: 'center',
					width: '10',
					valign: 'middle',
					editable: {
						emptytext: '填写',
	                    type: 'text'
					},
				},
				{
					field: 'end',
					title: '截止',
					align: 'center',
					width: '10',
					valign: 'middle',
					editable: {
						emptytext: '填写',
	                    type: 'text'
					}
				},
				{
					field: 'highLimit',
					title: '上限符',
					align: 'center',
					width: '10',
					valign: 'middle',
					editable: {
						title: '选择上限符',
						type: 'select',
						emptytext: '选择',
						source: highLimitDS,
					},
				},
				{
					field: 'isDelete',
					title: '删除标志',
					align: 'center',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '10',
					valign: 'middle',
	          		editable: {
						emptytext: '填写',
	                    type: 'text'
	                }
				},
				{
	          		field:"cz",
	          		title:"操作",
	          		align:"center",
					valign: 'middle',
	          		formatter: actionFormatter,
					events: operateEvents
	          	}
			],
			onLoadSuccess:function(){
//				bootbox.alert("数据加载成功！","");
            },
            onClickRow:function(row){
            	$("#"+scvarcaseTableId).each(function() { 
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
		$("#"+scvarcaseTableId).bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function scvarcaseQueryParams(params)
	{
	    var temp = {								//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,						//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      scoreid:curRowData.scoreId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function scvarcaseResponseHandler(res)
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
/*--------------------------------------------------------------------------------------------------------------*/
	//table里面的操作列显示
	function actionFormatter(value, row, index) {
		if(curRowData.status == 1)
		{
			return ['<button class="btn btn-default operate id="save" disabled=true >保存</button>'];
		}else{
			return ['<button class="btn btn-default operate '+row.caseId+'" id="save" disabled=true >保存</button>'];
		}
	}
	
	function comcaseactionFormatter(value, row, index) {
		if(curRowData.status == 1)
		{
			return ['<button class="btn btn-default operate id="save" disabled=true >保存</button>'];
		}else{
			return ['<button class="btn btn-default operate '+row.Id+'" id="save" disabled=true >保存</button>'];
		}
	}
	
	function varactionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="edit" href="#"><span>编辑</span></a>',
	       '<a class="operate" id="del" href="#"><span>删除</span></a>'
	     ].join('   ');
	}
	
	function xzFormatter(value, row, index) {
		if(curRowData.status == 1)
		{
			return ['<button class="btn btn-default operate " id="choose" disabled=true >选择</button>'];
		}else{
			return ['<button class="btn btn-default operate " id="choose" >选择</button>'];
		}
	}
/*---------------------------------------------------------------------------*/	
	//列操作事件定义
	var operateEvents = {'click .operate': function (e, value, row, index){
		if(e.currentTarget.id == "save")
		{
			index++;
			save(row,index);
		}
		if(e.currentTarget.id == "choose")
		{
			index++;
			var scvarid = row.scvarId;
			var blName = $("#scvarcase_table tr:eq("+index+") td:eq(1)").text();
//			var paraGroupName = blName;
			$.ajax({
				type: "POST",
				url:"/scVarCase/findParaList",
				data:{"scvarId":scvarid},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	paraSEDS = eval(data);
			    }
			});
			setTimeout(function(){
				init_scvarcasemodal();//初始化提交表单
				submit_scvarcaseform(index);//提交表单操作
				$("#scvarcaseModalId").modal('show');
				$("#scvarName").val(blName);
				},1000);
				
			$("."+row.caseId).removeAttr("disabled");
		}
	}};
	
	function save(entry,index){
		$.ajax({
			type: "POST",
			url:"/scVarCase/getDatatype",
			data:{"scvarId":entry.scvarId},
		    error: function(request) {
		       
		    },
		    success: function(data) {
		    	var type = data.type;
		    	save1(entry,index,type);
		    }
		});
	}
	
	//编辑操作
	function save1(entry,index,type)
	{
		var startText = $("#scvarcase_table tr:eq("+index+") td:eq(4)").text();
		if(startText == "undefined" || startText == "填写")
		{
			startText = "null";
		}
		
		var endText = $("#scvarcase_table tr:eq("+index+") td:eq(5)").text();
		if(endText == "undefined" || endText == "填写")
		{
			endText = "null";
		}
		
		var start = $("#scvarcase_table tr:eq("+index+") td:eq(7)").text();
		if(start == "undefined" || start == "填写")
		{
			start = "null";
		}
		
		var end = $("#scvarcase_table tr:eq("+index+") td:eq(8)").text();
		if(end == "undefined" || end == "填写")
		{
			end = "null";
		}
		
		if (type == 1) {
			startText = "";
			endText = "";
		}else {
			start = "";
			end = "";
			entry.highLimit = "";
		}
		
		json = {
				caseId: entry.caseId, 
				scvarId: entry.scvarId, 
				cdesc: entry.cdesc, 
				edesc: entry.edesc, 
				start: start,
				end: end,
				score: entry.score, 
				lowLimit: entry.lowLimit,
				startText: startText,
				endText: endText,
				highLimit: entry.highLimit,
				seqno: entry.seqno
				}; 
		
		$.ajax({
			type: "POST",
			url:"/scVarCase/save",
		    dataType: "json",  
			data: {json:JSON.stringify(json)},
		    error: function(request) {
//		        alert("shibai");
		    },
		    success: function(data) {
		    	bootbox.alert("保存成功","");
		    	$("#"+scvarcaseTableId).bootstrapTable('refresh');
		    }
		});
		$("."+entry.caseId).attr("disabled",true);
	}
/*---------------------------------------------------------------------------*/	
	//列操作事件定义
	var comcaseoperateEvents = {'click .operate': function (e, value, row, index){
		if(e.currentTarget.id == "save")
		{
			index++;
			saveCom(row,index);
		}
		if(e.currentTarget.id == "choose")
		{
			index++;
			var Id = row.id;
			var blName = $("#comcase_table tr:eq("+index+") td:eq(1)").text();
			$.ajax({
				type: "POST",
				url:"/scVarCase/findParaList",
				data:{"scvarId":scvarid},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	paraSEDS = eval(data);
			    }
			});
			setTimeout(function(){
				init_comcasemodal();//初始化提交表单
				submit_comcaseform(index);//提交表单操作
				$("#comcaseModalId").modal('show');
				$("#comName").val(blName);
				},1000);
				
			$("."+row.id).removeAttr("disabled");
		}
	}};
	
	function saveCom(entry,index){
		$.ajax({
			type: "POST",
			url:"/scVarCase/getDatatype",
			data:{"scvarId":entry.scvarId},
		    error: function(request) {
		       
		    },
		    success: function(data) {
		    	var type = data.type;
		    	comcaseSave(entry,index,type);
		    }
		});
	}
	
	//编辑操作
	function comcaseSave(entry,index,type)
	{
		var startText = $("#comcase_table tr:eq("+index+") td:eq(3)").text();
		if(startText == "undefined" || startText == "填写")
		{
			startText = "null";
		}
		
		var endText = $("#comcase_table tr:eq("+index+") td:eq(4)").text();
		if(endText == "undefined" || endText == "填写")
		{
			endText = "null";
		}
		
		var start = $("#comcase_table tr:eq("+index+") td:eq(6)").text();
		if(start == "undefined" || start == "填写")
		{
			start = "null";
		}
		
		var end = $("#comcase_table tr:eq("+index+") td:eq(7)").text();
		if(end == "undefined" || end == "填写")
		{
			end = "null";
		}
		
		if (type == 1) {
			startText = "";
			endText = "";
		}else {
			start = "";
			end = "";
			entry.highLimit = "";
		}
		
		json = {
				Id: entry.id, 
				cvId: curCvId, 
				scvarId: entry.scvarId, 
				cdesc: entry.cdesc, 
				edesc: entry.edesc, 
				start: start,
				end: end,
				lowLimit: entry.lowLimit,
				startText: startText,
				endText: endText,
				highLimit: entry.highLimit,
				seqno: entry.seqno
				}; 
		
		$.ajax({
			type: "POST",
			url:"/comcase/save",
		    dataType: "json",  
			data: {json:JSON.stringify(json)},
		    error: function(request) {
//		        alert("失败了");
		    },
		    success: function(data) {
		    	bootbox.alert(data.msg,"");
		    	$("#"+comcaseTableId).bootstrapTable('refresh');
		    }
		});
		$("."+entry.Id).attr("disabled",true);
	}

/*------------------------------------------------------------------------------------------------*/
	//评分卡变量数据显示
	function showscvarView()
	{
		if(countScVarAction < totalScVarAction)//只加载一次，防止确定删除框出现好多个。
		{
			init_scvar_tool_action();//初始化评分卡变量操作
		}
		if(curRowData.status != 1)
		{
			$('#scvar_toolbar').show();
		}else{
			$('#scvar_toolbar').hide();
		}
		$("#"+scvarTableId).bootstrapTable({
			url: "/scvar/listScvar",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-265,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,30,50,100],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: scvarQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#scvar_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: scvarResponseHandler,
			columns: [
				{
					field: 'scvar_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'scvarId',
					title: '变量编号',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'paragrpName',
					title: '参数类别名称',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: pGrpFormatter
				},
				{
					field: 'varType',
					title: '变量类别',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: vtypeFormatter
				},
				{
					field: 'dataType',
					title: '数据类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: dtypeFormatter
				},
				{
					field: 'cdesc',
					title: '中文描述',
					align: 'left',
					width: '10',
					valign: 'middle',
					visible: false
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curscvarRowData = row;
            	$("#"+scvarTableId).each(function() { 
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
            	curscvar = row;
            },
            onLoadSuccess: function(){
//            	bootbox.alert("数据加载成功！","");
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+scvarTableId).bootstrapTable('refresh');
		curscvarRowData="";
	}
	
	//设置table传递到服务器的参数
	function scvarQueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      scoreid:curRowData.scoreId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function scvarResponseHandler(res)
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
	
/*------------------------------------------------------------------------------------*/
	//多变量组合数据显示
	function showcomvalueView()
	{
		$("#"+rightContent_model).empty();
		
		if(countComvalueAction < totalComvalueAction)//只加载一次，防止确定删除框出现好多个。
		{
			init_comvalue_tool_action();//初始化评分卡变量操作
		}
		if(curRowData.status != 1)
		{
			$('#comvalue_toolbar').show();
		}else{
			$('#comvalue_toolbar').hide();
		}
		$("#"+comvalueTableId).bootstrapTable({
			url: "/comvalue/list",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-500,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,30,50,100],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: comvalueQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#comvalue_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: comvalueResponseHandler,
			columns: [
				{
					field: 'comvalue_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'cvId',
					title: '组合ID',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'scvarId',
					title: '评分卡组合变量',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: scvarIdDSFormatter
				},
				{
					field: 'comValue',
					title: '组合值',
					align: 'center',
					width: '',
					valign: 'middle',
				},
				{
					field: 'comText',
					title: '组合描述',
					align: 'center',
					width: '',
					valign: 'middle',
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curcomvalueRowData = row;
            	curCvId = row.cvId;
            	showRightDown();
            	
            	$("#"+comvalueTableId).each(function() { 
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
            	curcomvalue = row;
            },
            onLoadSuccess: function(){
//            	bootbox.alert("数据加载成功！","");
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+comvalueTableId).bootstrapTable('refresh');
		curcomvalueRowData="";
	}
	
	function scvarIdDSFormatter(value) {
		for(var i=0;i<scvarIdDS2.length;i++)
		{
			if((scvarIdDS2[i].parameterValue) == value)
			{
				return ['<span>'+scvarIdDS2[i].parameterName+'</span>']
			}
		}
	}
	
	//设置table传递到服务器的参数
	function comvalueQueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      scoreid: curRowData.scoreId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function comvalueResponseHandler(res)
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
/*--------------------------------------------------------------------------------*/
	function showRightDown(){
		init_layout1();
		showcomcaseView();
	}
	
	//组合实例数据显示----------------------------------------------------------------------------
	function showcomcaseView()
	{
		init_comcasetable();
		
		init_comcase_tool_action();//初始化工具栏操作
			
		$('#comcase_toolbar').show();
		$("#"+comcaseTableId).bootstrapTable({
			url: "/comcase/list",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-500,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,30,50,100],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: comcaseQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#comcase_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: comcaseResponseHandler,
			onEditableSave:function(field,row,oldValue,$el){
				$("."+row.Id).removeAttr("disabled");
			},
			columns: [
						{
							field: 'comcase_xz',
							titleTooltip: '全选/全不选',
							checkbox: true,
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'id',
							title: 'Id',
							align: 'center',
							width: '',
							valign: 'middle',
							visible: false
						},
						{
							field: 'scvarId',
							title: '变量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
							align: 'center',
							width: '20',
							valign: 'middle',
							editable: {
			          			title: '选择变量',
				          		type: 'select',
				          	    emptytext: '选择',
				          	    source: scVarIdDS,
				          	    validate: function (value) {
				          	    	//字段验证
				          	    	$.ajax({
				          				type: "POST",
				          				url:"/scvar/getScvarById",
				          				data:{"scvarId":value},
				          			    error: function(request) {
				          			        
				          			    },
				          			    success: function(data) {
				          			    	var type = data.dataType;
						                   if(type == '1') {
						                	   //隐藏起始文本和截止文本
						                	   $("#comcase_table tr td:eq(3)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(4)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(5)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(6)").children().css('display','none');
						                	   
						                	   $("#comcase_table tr td:eq(5)").children().css('display','block');
						                	   $("#comcase_table tr td:eq(6)").children().css('display','block');
						                	   $("#comcase_table tr td:eq(7)").children().css('display','block');
						                	   $("#comcase_table tr td:eq(8)").children().css('display','block');
						                   }
						                   if(type == '2') {
						                	   //隐藏起始和截止
						                	   $("#comcase_table tr td:eq(3)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(4)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(5)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(6)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(7)").children().css('display','none');
						                	   $("#comcase_table tr td:eq(8)").children().css('display','none');
						                	   
						                	   $("#comcase_table tr td:eq(3)").children().css('display','block');
						                	   $("#comcase_table tr td:eq(4)").children().css('display','block');
						                	   $("#comcase_table tr td:eq(5)").children().css('display','block');
						                   }
				          			    }
				          			});
			                }
							}
						},
						{
							field: 'cdesc',
							title: '值描述',
							align: 'center',
							width: '',
							valign: 'middle',
							editable: {
								emptytext: '填写',
			                    type: 'text'
			                }
						},
						{
							field: 'edesc',
							title: '英文描述',
							align: 'center',
							width: '',
							valign: 'middle',
			                visible: false
						},
						{
							field: 'startText',
							title: '起始文本',
							align: 'center',
							width: '20',
							valign: 'middle',
			          		editable: {
								emptytext: '填写',
			                    type: 'text'
			                }
						},
						{
							field: 'endText',
							title: '截止文本',
							align: 'center',
							width: '20',
							valign: 'middle',
			          		editable: {
								emptytext: '填写',
			                    type: 'text'
			                }
						},
						{
							field: 'lowLimit',
							title: '下限符',
							align: 'center',
							width: '10',
							valign: 'middle',
							editable: {
								title: '选择下限符',
				          		type: 'select',
				          	    emptytext: '选择',
				          	    source: lowLimitDS,
							},
						},
						{
							field: 'start',
							title: '起始',
							align: 'center',
							width: '10',
							valign: 'middle',
							editable: {
								emptytext: '填写',
			                    type: 'text'
							},
						},
						{
							field: 'end',
							title: '截止',
							align: 'center',
							width: '10',
							valign: 'middle',
							editable: {
								emptytext: '填写',
			                    type: 'text'
							}
						},
						{
							field: 'highLimit',
							title: '上限符',
							align: 'center',
							width: '10',
							valign: 'middle',
							editable: {
								title: '选择上限符',
								type: 'select',
								emptytext: '选择',
				          	    source: highLimitDS,
							},
						},
						{
							field: 'seqno',
							title: '序号',
							align: 'center',
							width: '10',
							valign: 'middle',
			          		editable: {
								emptytext: '填写',
			                    type: 'text'
			                }
						},
						{
			          		field:"cz",
			          		title:"操作",
			          		align:"center",
							valign: 'middle',
			          		formatter: comcaseactionFormatter,
							events: comcaseoperateEvents
			          	}
					],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curcomcaseRowData = row;
            	
            	$("#"+comcaseTableId).each(function() { 
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
            	curcomcase = row;
            },
            onLoadSuccess: function(){
//            	bootbox.alert("数据加载成功！","");
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+comcaseTableId).bootstrapTable('refresh');
		curcomcaseRowData="";
	}
	
	//设置table传递到服务器的参数
	function comcaseQueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      cvId:curCvId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function comcaseResponseHandler(res)
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
	
	//评分卡等级数据显示-------------------------------------------------------------------
	function showclassView()
	{
		if(countClassAction < totalClassAction)//只加载一次，防止确定删除框出现好多个。
		{
			init_class_tool_action();//初始化评分卡变量操作
		}
		if(curRowData.status != 1)
		{
			$('#class_toolbar').show();
		}else{
			$('#class_toolbar').hide();
		}
		$("#"+classTableId).bootstrapTable({
			url: "/class/listClass",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-280,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,30,50,100],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: classQueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#class_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: classResponseHandler,
			columns: [
				{
					field: 'class_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'classId',
					title: '等级编号',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'name',
					title: '等级名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'startScore',
					title: '起始分值',
					align: 'center',
					width: '100',
					valign: 'middle',
				},
				{
					field: 'endScore',
					title: '截止分值',
					align: 'center',
					width: '100',
					valign: 'middle',
				},
				{
					field: 'value',
					title: '等级数值',
					align: 'center',
					width: '',
					valign: 'middle'
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curclassRowData = row;
            	$("#"+classTableId).each(function() { 
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
            	curclass = row;
            },
            onLoadSuccess: function(){
//            	bootbox.alert("数据加载成功！","");
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+classTableId).bootstrapTable('refresh');
		curclassRowData="";
	}
	
	//设置table传递到服务器的参数
	function classQueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      scoreid:curRowData.scoreId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function classResponseHandler(res)
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
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"row\" id=\""+baseDivId+"\">");
			//左侧选项卡
			htmlInfo.append("<div class=\"panel panel-default col-sm-5\" style=\"\">");			
			htmlInfo.append("<ul id=\"myTab0\" class=\"nav nav-tabs\" style=\"background:#fff;\">");
			htmlInfo.append("<li class=\"active\"><a href=\".score_div\" data-toggle=\"tab\">评分卡</a></li>");
			htmlInfo.append("<li><a href=\".var_div\" data-toggle=\"tab\">评分卡基础变量</a></li>");
			htmlInfo.append("<li><a href=\".varpara_div\" data-toggle=\"tab\">变量参数类别</a></li>");
			htmlInfo.append("</ul>");
	
			htmlInfo.append("<div id=\"myTabContent0\" class=\"tab-content\">");
			
			htmlInfo.append("<div class=\"tab-pane fade in active score_div\">");	
			htmlInfo.append("<div id=\"score_toolbar\" class=\"btn-group\" style=\"margin-top:5px;margin-bottom:5px;\">");
			htmlInfo.append("<button id=\"score_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"score_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"score_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"score_btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权角色");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+scoreDivId+"\" style=\"margin-top: 1px;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"tab-pane fade in var_div\">");
			htmlInfo.append("<div id=\"var_toolbar\" class=\"btn-group pull-left\" style=\"margin-top:10px;\">");
			htmlInfo.append("<button id=\"var_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"var_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+varDivId+"\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"tab-pane fade in varpara_div\">");
			htmlInfo.append("<div id=\"varpara_toolbar\" class=\"btn-group pull-left\" style=\"margin-top:10px;\">");
			htmlInfo.append("<button id=\"para_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"para_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"para_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+paraDivId+"\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			//右侧选项卡
			htmlInfo.append("<div class=\"panel panel-default col-sm-7\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
//			htmlInfo.append("<li><a href=\".scvar_tujie\" data-toggle=\"tab\">评分卡变量图解</a></li>");
			htmlInfo.append("<li class=\"active\"><a href=\".scvar_div\" data-toggle=\"tab\">评分卡变量</a></li>");
			htmlInfo.append("<li><a href=\".comvalue_div\" data-toggle=\"tab\">多变量组合</a></li>");
			htmlInfo.append("<li><a href=\".scvarcase_div\" data-toggle=\"tab\">评分卡变量判例</a></li>");
			htmlInfo.append("<li><a href=\".class_div\" data-toggle=\"tab\">评分等级</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");
			/*-- 评分卡变量图解 --*/
			htmlInfo.append("<div class=\"tab-pane fade in scvar_tujie\">");
			htmlInfo.append("<div id=\""+scvarTujieId+"\" style=\"margin-top: 5px; width: 600%; height:400px;\">");
	        htmlInfo.append("</div>");
	        htmlInfo.append("<div id=\""+scvarcaseTujieId+"\" style=\"margin-top: 5px; width: 100%; height:300px;\">");
	        htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			/*-- 评分卡变量判例 --*/
			htmlInfo.append("<div class=\"tab-pane fade in scvarcase_div\">");
			htmlInfo.append("<div id=\"scvarcase_toolbar\" class=\"btn-group\" style=\"display: none; margin-top: 5px;\">");
			htmlInfo.append("<button id=\"scvarcase_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"scvarcase_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+scvarcaseDivId+"\" style=\"margin-top: 5px;\">");
	        htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			/*-- 评分卡变量 --*/
			htmlInfo.append("<div class=\"tab-pane fade in active scvar_div\">");
			htmlInfo.append("<div id=\"scvar_toolbar\" class=\"btn-group\" style=\"display:none;margin-bottom:5px;\">");
			htmlInfo.append("<button id=\"scvar_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"scvar_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"scvar_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"scvar_btn_addall\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>批量添加");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+scvarDivId+"\" style=\"margin-top: 5px;\">");
	        htmlInfo.append("</div>");
	        htmlInfo.append("</div>");
	        /*-- 评分等级 --*/
	        htmlInfo.append("<div class=\"tab-pane fade in class_div\">");
			htmlInfo.append("<div id=\"class_toolbar\" class=\"btn-group\" style=\"display:none;margin-bottom:5px;\">");
			htmlInfo.append("<button id=\"class_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"class_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"class_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+classDivId+"\" style=\"margin-top: 5px;\">");
	        htmlInfo.append("</div>");
	        htmlInfo.append("</div>");
	        /*-- 多变量组合 --*/
	        htmlInfo.append("<div class=\"tab-pane fade in comvalue_div\">");
			htmlInfo.append("<div id=\"comvalue_toolbar\" class=\"btn-group\" style=\"display:none;margin-bottom:5px;\">");
			htmlInfo.append("<button id=\"comvalue_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"comvalue_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"comvalue_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div style=\"overFlow:hidden;\" id=\""+comvalueDivId+"\" style=\"margin-top: 5px;\">");
	        htmlInfo.append("</div>");
	        htmlInfo.append("<div id=\"" + rightContent_model + "\">");
	        htmlInfo.append("</div>");
	        htmlInfo.append("</div>");
	        
	    	htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	function init_layout1()
	{
		$("#"+rightContent_model).empty();
		//首先加载选项卡
		var htmlInfo = new StringBuffer();
		htmlInfo.append("<ul id=\"myTab1\" class=\"nav nav-tabs\">");
		htmlInfo.append("<li class=\"active\"><a href=\".comcase_div\" data-toggle=\"tab\">组合实例</a></li>");
		htmlInfo.append("</ul>");
		
		htmlInfo.append("<div id=\"myTabContent1\" class=\"tab-content\">");	
		//opt6 tab
		htmlInfo.append("<div class=\"tab-pane fade in active comcase_div\">");
		htmlInfo.append("<div id=\"comcase_toolbar\" class=\"btn-group\" style=\"display:none;margin-bottom:5px;\">");
		htmlInfo.append("<button id=\"comcase_btn_add\" class=\"btn btn-primary\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
		htmlInfo.append("</button>");
		htmlInfo.append("<button id=\"comcase_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
		htmlInfo.append("</button>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\""+comcaseDivId+"\" style=\"margin-top: 5px;\">");
        htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
    	htmlInfo.append("</div>");		//myTabContent
		
	    $("#"+rightContent_model).append(htmlInfo.toString());
	}
/*---------------------------------------------------------------------------------*/
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+modalId).length == 0)
		{
			htmlInfo.append("<div id=\""+modalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	function init_modal()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+baseModalId).length == 0)
		{
			htmlInfo.append("<div id=\""+baseModalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	function init_para_modal()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+paraModalId).length == 0)
		{
			htmlInfo.append("<div id=\""+paraModalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化scvar table modal
	function init_scvar_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#varmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"varmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化组合实例 table modal
	function init_comcase_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#'+comcaseModalId).length == 0)
		{
			htmlInfo.append("<div id=\""+comcaseModalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	
/*-----------------------------------------------------------------------------------*/
	//初始化评分卡table
	function init_scoretable()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+scoreTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+scoreTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+scoreDivId).append(htmlInfo.toString()); 
		}
	}
	//初始化基础变量table
	function init_vartable()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+baseTableId).length == 0)	//判断var_table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\""+baseTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+varDivId).append(htmlInfo.toString()); 
		}
	}
	//初始化评分卡数据参数table
	function init_para_table()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+paraTableId).length == 0)	//判断var_table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\""+paraTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+paraDivId).append(htmlInfo.toString()); 
		}
	}
	//初始化判例table
	function init_scvarcasetable()
	{
		$('.scvarcaseid').remove();//先清除table
		var htmlInfo=new StringBuffer();
		htmlInfo.append("<div class=\"scvarcaseid\">");
		htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+scvarcaseTableId+"\">");
		htmlInfo.append("</table>");
		htmlInfo.append("</div>");
		$("#"+scvarcaseDivId).append(htmlInfo.toString()); 
	}
	//初始化变量table
	function init_scvartable()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+scvarTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+scvarTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+scvarDivId).append(htmlInfo.toString()); 
		}
	}
	//初始化变量table用于添加多条
	function init_var_table()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"varModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"vardialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"varclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">批量添加</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#varlistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-striped\" id=\"varlistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"varcancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"varsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#varmodalid").append(htmlInfo.toString()); 
	}
	
	//初始化组合table
	function init_comvaluetable()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+comvalueTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+comvalueTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+comvalueDivId).append(htmlInfo.toString()); 
		}
	}
	
	//初始化组合实例table
	function init_comcasetable()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+comcaseTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+comcaseTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+comcaseDivId).append(htmlInfo.toString()); 
		}
	}
	
	//初始化等级table
	function init_classtable()
	{
		var htmlInfo=new StringBuffer();
		if($("#"+classTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+classTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+classDivId).append(htmlInfo.toString()); 
		}
	}
	
	/*-------------------------------------函数：评分卡增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#score_btn_add').on("click",function()
		{
			s_update_name = "";
//			b_update_name = "";
			if($("#"+scoreDialogId).length == 0)
			{
				init_scoremodal();//初始化提交表单
				submit_scoreform();//提交表单操作
			}
			$("#"+scoreFormId)[0].reset();
			$("#"+scoreModalId).modal('show');
		});
		
		$('#score_btn_edit').on("click",function()
		{
			if(curRowData!="" && curRowData._level!= 0)
			{
				s_update_name = curRowData.scoreId;
//				b_update_name = curRowData.cname;
				if($("#"+scoreDialogId).length == 0)//判断表单是否已经初始化
				{
					init_scoremodal();		//初始化提交表单
					submit_scoreform();		//提交表单操作
				}
		    	$('#scoreId').val(curRowData.scoreId);
				$('#finsId').val(curRowData.finsId);
				$('#category').val(curRowData.type);
				$('#category').val(curRowData.category);
				$('#cname').val(curRowData.cname);
				$('#ename').val(curRowData.ename);
				$('#version').val(curRowData.version);
				$('#status').val(curRowData.status);
				if (curRowData.releaseDate == null || curRowData.releaseDate == "null" ) {
					$('#releaseDate').val("");
				}else{
					$('#releaseDate').val(fmtDate(curRowData.releaseDate));
				}
				if (curRowData.effectiveDate == null || curRowData.effectiveDate == "null") {
					$('#effectiveDate').val("");
				}else{
					$('#effectiveDate').val(fmtDate(curRowData.effectiveDate));
				}
				if (curRowData.expiredDate == null || curRowData.expiredDate == "null") {
					$('#expiredDate').val("");
				}else{
					$('#expiredDate').val(fmtDate(curRowData.expiredDate));
				}
				$('#memo').val(curRowData.memo);
				if (curRowData.seqno==null || curRowData.seqno=="null") {
					$('#seqno').val("");
				}else{
					$('#seqno').val(curRowData.seqno);
				}
				$('#isDelete').val(curRowData.isDelete);
				
				$("#"+scoreModalId).modal('show');
			}
			else{
				bootbox.alert("请选择要修改的评分卡！","");
			}
		});
		
		$("#score_btn_del").on("click",function()
		{
			var selData = $("#"+scoreTableId).bootstrapTable('getSelections');//选中的数据
			var scoreIds ='';
//			var scoreNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					scoreIds += selData[i].scoreId+',';
//					scoreNames += selData[i].cname+"("+selData[i].scoreId+")"+',';
				}
				
				scoreIds = scoreIds.substring(0,scoreIds.length-1);
//				scoreNames = scoreNames.substring(0,scoreNames.length-1);
				delScore(scoreIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
		//授权角色
		$("#score_btn_sqrole").on("click",function(){
			var selData = $("#"+scoreTableId).bootstrapTable('getSelections');//选中的数据
			if(selData.length > 0){
				bootbox.alert("请取消勾选，单击选择一个评分卡进行授权！","");
			}else{
				if(curRowData!="" && curRowData._level!= 0)
				{
					if($('#roledialogid').length == 0)
					{
						init_roletable();//初始化
						submit_roleform();//提交操作
					}
					$('#roleModal').modal('show');
					findYxRole();//查找已经选择的数据用于授权角色
					getRoles();//得到角色数据
				}
				else{
					bootbox.alert("请选择要授权角色的评分卡！","");
				}
			}
		});
		
	}
	
	function fmtDate(obj){
	    var date =  new Date(obj);
	    var y = 1900+date.getYear();
	    var m = "0"+(date.getMonth()+1);
	    var d = "0"+date.getDate();
	    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
	}

	
	//删除确认操作
	function delScore(currIds)
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
	                $.post("/score/delete",{"currIds":currIds},
        		    function(data)
        		    {
        			    if(data.msg == "success")
        			    {
        			    	$("#"+scoreTableId).bootstrapTable('refresh');
        			    	curRowData = "";
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+scoreTableId).bootstrapTable('refresh');
        			    	curRowData = "";
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
	/*-------------------------------------函数：基础变量增删-----------------------------------------*/
	
	//table里面显示数据类型的中文描述
	function dtypeFormatter(value) {
    	for(var i=0;i<vdtypeParaDS.length;i++)
    	{
    		if(parseInt(vdtypeParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+vdtypeParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function vtypeFormatter(value) {
		for(var i=0;i<varTypeDS.length;i++)
		{
			if(parseInt(varTypeDS[i].parameterValue) == value)
			{
				return ['<span>'+varTypeDS[i].parameterName+'</span>']
			}
		}
	}
	function pGrpFormatter(value) {
    	for(var i=0;i<paraDS.length;i++)
    	{
    		if(paraDS[i].parameterValue == value)
    		{
    			return ['<span>'+paraDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//工具栏操作初始化
	function init_var_tool_action()
	{
		$('#var_btn_add').on("click",function()
		{
			s_update_name = "";
//			b_update_name = "";
			if($('#'+baseFormId).length == 0)
			{
				init_form();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#baseModalLabel').val("添加金融机构");
			$('#addform')[0].reset();
			$('#'+baseFormId).modal('show');
		});
		
		$("#var_btn_del").on("click",function()
		{
			var selData = $('#'+baseTableId).bootstrapTable('getSelections');//选中的数据
			var objCodes ='';
//			var objNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					objCodes += selData[i].varId+',';
//					objNames += selData[i].cname+"("+selData[i].varId+")"+',';
				}
				
				objCodes = objCodes.substring(0,objCodes.length-1);
//				objNames = objNames.substring(0,objNames.length-1);
				delMany(objCodes);
			}
			else
			{
				bootbox.alert("请选择要删除的数据记录！","");
			}
		});
	}
	
/*-----------------------------------函数：评分卡数据参数增删改----------------------------*/
	//工具栏操作初始化
	function init_para_tool_action()
	{
		$("#para_btn_add").on("click",function()
		{
			if($("#"+paraFormId).length == 0)
			{
				init_para_form();//初始化提交表单
				submit_para_form();//提交表单操作
			}
			$("#paraform")[0].reset();
			$("#"+paraFormId).modal('show');
		});

		$("#para_btn_edit").on("click",function()
		{
			edit_para(curRowBody);
			curRowBody = "";
		});
		
		$("#para_btn_del").on("click",function()
		{
			var selData = $('#'+paraTableId).bootstrapTable('getSelections');//选中的数据
			var objCodes ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					objCodes += selData[i].id+',';
				}
				
				objCodes = objCodes.substring(0,objCodes.length-1);
				delParaMany(objCodes);
				$("#"+paraTableId).bootstrapTable('refresh');
			}
			else
			{
				bootbox.alert("请选择要删除的数据记录！","");
			}
		});
	}
	
	function edit_para(entry){
		if(entry != null && entry != "")
		{
			if($("#"+paraFormId).length == 0){
				init_para_form();//初始化提交表单
				submit_para_form();//提交表单操作
			}
			
			$('#para_id').val(entry.id);
			$('#para_groupName').val(entry.groupName);
			$('#para_groupDescr').val(entry.groupDescr);			
			$('#para_name').val(entry.name);
			$('#para_value').val(entry.value);
			$('#para_descr').val(entry.descr);
			$('#para_seqno').val(entry.seqno);
			
			$("#"+paraFormId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	function delParaMany(ids){
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
	                $.post("/scorePara/delete", {"currIds":ids},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+paraTableId).bootstrapTable('refresh');
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
/*------------------------------------------函数：数据参数表单-----------------------------------------*/
	
	//初始化表单
	function init_para_form()
	{
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+paraFormId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"paraModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"paradialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"para_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"baseModalLabel\">数据参数</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"paraform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"id\">参数编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"id\" id=\"para_id\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupName\">类别名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"groupName\" id=\"para_groupName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupDescr\">类别描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"groupDescr\" id=\"para_groupDescr\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">参数名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"para_name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cdesc\">参数值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"value\" id=\"para_value\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"edesc\">参数描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"descr\" id=\"para_descr\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"para_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"para_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"para_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+paraModalId).append(htmlInfo.toString()); 
	}
	
	//提交表单
	function submit_para_form()
	{
		$('#para_submit_btn').on("click",function(){
			var cname = $('#para_name').val();
			var value = $('#para_value').val();
			if(cname!="" && value!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/scorePara/save",
					data:$("#paraform").serialize(), //formid
					error: function(request) {
						//alert("Connection error");
					},
					success: function(data) {
						bootbox.alert(data.msg,"");
						$("#paraform")[0].reset();
						$("#"+paraFormId).modal('hide');
						$("#"+paraTableId).bootstrapTable('refresh');
					}
				});
			}
			else
			{
				showError("参数名称和参数值为必填项，请填写！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#para_cancel_btn').on("click",function(){
			$("#paraform")[0].reset();
		});
		$('#para_close_btn').on("click",function(){
			$("#paraform")[0].reset();
		});
	}
/*------------------------------------------结束表单的操作-----------------------------------------*/	

/*-------------------------------------函数：列操作-----------------------------------------*/
	
	//编辑操作
	function edit(entry)
	{
		if($('#'+baseFormId).length == 0)//判断表单是否已经初始化
		{
			init_form();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		var varId = entry.varId;
		var cname = entry.cname;
		var ename = entry.ename;
		var datatype = entry.dataType;
		var paragrpName = entry.paragrpName;
		var cdesc = entry.cdesc;
		var edesc = entry.edesc;
		var memo = entry.memo;
		var seqno = entry.seqno;
		var isdelete = entry.isDelete;
		s_update_name = varId;
//		b_update_name = cname;
		
		$('#var_varid').val(varId);
		$('#var_cname').val(cname);
		$('#var_ename').val(ename);
		$('#var_datatype').val(datatype);
		$('#var_paragrpName').val(paragrpName);
		$('#var_cdesc').val(cdesc);
		$('#var_edesc').val(edesc);
		$('#var_memo').val(memo);
		$('#var_seqno').val(seqno);
		$('#var_isdelete').val(isdelete);
		
		$('#'+baseFormId).modal('show');
	}
	
	
	//删除单个数据记录操作
	function delOne(entry)
	{
		remove("/var/delete", entry.varId);
	}
	
	
	//删除多个数据记录操作
	function delMany(ids)
	{
		remove("/var/delete", ids);
	}
	
	
	function remove(acturl, ids)
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
	                $.post(acturl, {"currIds":ids},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$('#'+baseTableId).bootstrapTable('refresh');
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
	
/*------------------------------------------函数：基础变量表单-----------------------------------------*/
	
	//初始化表单
	function init_form()
	{
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+baseFormId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"baseModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"basedialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"baseModalLabel\">基础变量</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"varid\">变量编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"varId\" id=\"var_varid\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cname\">中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"var_cname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ename\">英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"var_ename\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"datatype\">数据类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"var_datatype\" name=\"dataType\" class=\"form-control\">");
    	for(var i=0;i<vdtypeParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+vdtypeParaDS[i].parameterValue+">"+vdtypeParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"paragrpName\">参数类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"var_paragrpName\" name=\"paragrpName\" class=\"form-control\">");
    	for(var i=0;i<paraDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraDS[i].parameterValue+">"+paraDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cdesc\">中文描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cdesc\" id=\"var_cdesc\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"edesc\">英文描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"edesc\" id=\"var_edesc\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"memo\">备注</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"memo\" id=\"var_memo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"var_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isdelete\">删除标志</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"var_isdelete\" name=\"isDelete\" class=\"form-control\">");		
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+baseModalId).append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var ename = $('#var_ename').val();
			var cname = $('#var_cname').val();
			var url = "/var/save";
			if(ename != "" && cname != "")
			{
//				if(s_update_name == "")
//				{
//					anyname = cname;
//				}else{
//					anyname = cname + "(" + s_update_name + ")";
//				}
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
				    	$('#'+baseFormId).modal('hide');
				    	$('#'+baseTableId).bootstrapTable('refresh');
				    }
				});
				
			}
			else
			{
				showError("中文名称、英文名称为必填项，请填写！", '');
				//bootbox.alert("英文名称为必填项，请填写！","");
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
	
	
	/*-------------------------------------函数：评分卡变量增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_scvar_tool_action()
	{
		countScVarAction++;
		$('#scvar_btn_add').on("click",function()
		{
			if(curRowData == "")
			{
				bootbox.alert("请先选择左侧评分卡","");
			}else{
				$('#componentVarIds').selectpicker('val', "");
				s_update_name = "";
//				b_update_name = "";
				if($("#"+scvarDialogId).length == 0)
				{
					init_scvarmodal();//初始化提交表单
					submit_scvarform();//提交表单操作
				}
				$("#"+scvarFormId)[0].reset();
				$('#scoreId').val(curRowData.scoreId);
				$("#"+scvarModalId).modal('show');
			}
			
		});
		
		$('#scvar_btn_addall').on("click",function()
		{
			if(curRowData.scoreId == "" || curRowData.scoreId == undefined)
			{
				bootbox.alert("请先选择左侧评分卡","");
			}else{
				s_update_name = "";
//				b_update_name = "";
				if($('#vardialogid').length == 0)
				{
					init_var_table();//初始化提交表单
					submit_varform();//提交表单操作
				}
				$("#varModal").modal('show');
				findYxData();
				getVar();//得到数据
			}
		});
		
		$('#scvar_btn_edit').on("click",function()
		{
			if(curscvarRowData!="")
			{
				s_update_name = curscvarRowData.scvarId;
//				b_update_name = curscvarRowData.cname;
				if($("#"+scvarDialogId).length == 0)//判断表单是否已经初始化
				{
					init_scvarmodal();  //初始化提交表单
					submit_scvarform();		//提交表单操作
				}
				
		    	$('#scvarId').val(curscvarRowData.scvarId);
				$('#scoreId').val(curscvarRowData.scoreId);
				$('#varId').val(curscvarRowData.varId);
				$('#cname').val(curscvarRowData.cname);
				$('#ename').val(curscvarRowData.ename);
				$('#varType').val(curscvarRowData.varType);
				if (curscvarRowData.componentVarIds != null) {
					var arr = curscvarRowData.componentVarIds.replace(/\s/g, '').split(/[,]/g);
					$('#componentVarIds').selectpicker('val', arr);
				}else {
					var arr = "";
					$('#componentVarIds').selectpicker('val', arr);
				}
				$('#dataType').val(curscvarRowData.dataType);
				$('#paragrpName').val(curscvarRowData.paragrpName);
				$('#cdesc').val(curscvarRowData.cdesc);
				$('#edesc').val(curscvarRowData.edesc);
				$('#memo').val(curscvarRowData.memo);
				$('#seqno').val(curscvarRowData.seqno);
				$('#isDelete').val(curscvarRowData.isDelete);
				
				$("#"+scvarModalId).modal('show');
			}
			else{
				bootbox.alert("请选择要修改的数据！","");
			}
		});
		
		$("#scvar_btn_del").on("click",function()
		{
			var selData = $("#"+scvarTableId).bootstrapTable('getSelections');//选中的数据
			var scvarIds ='';
			var scvarNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					scvarIds += selData[i].scvarId+',';
					scvarNames += selData[i].cname+"("+selData[i].scvarId+")"+',';
				}
				
				scvarIds = scvarIds.substring(0,scvarIds.length-1);
				scvarNames = scvarNames.substring(0,scvarNames.length-1);
				delScvar(scvarIds,scvarNames);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
	}
	
	//删除确认操作
	function delScvar(currIds,currNames)
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
	                $.post("/scvar/delete",{"currIds":currIds},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+scvarTableId).bootstrapTable('refresh');
        			    	bootbox.alert("删除成功！");
        			    	curscvarRowData = "";
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+scvarTableId).bootstrapTable('refresh');
        			    	curscvarRowData = "";
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
/*-------------------------------------函数：评分等级增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_class_tool_action()
	{
		countClassAction++;
		$('#class_btn_add').on("click",function()
		{
			if(curRowData == "")
			{
				bootbox.alert("请先选择左侧评分卡","");
			}else{
				s_update_name = "";
//				b_update_name = "";
				if($("#"+classDialogId).length == 0)
				{
					init_classmodal();//初始化提交表单
					submit_classform();//提交表单操作
				}
				$("#"+classFormId)[0].reset();
				$('#scoreId').val(curRowData.scoreId);
				$("#"+classModalId).modal('show');
			}
			
		});
		
		$('#class_btn_edit').on("click",function()
		{
			if(curclassRowData!="")
			{
				s_update_name = curclassRowData.classId;
//				b_update_name = curscvarRowData.cname;
				if($("#"+classDialogId).length == 0)//判断表单是否已经初始化
				{
					init_classmodal();  //初始化提交表单
					submit_classform();		//提交表单操作
				}
				
		    	$('#classId').val(curclassRowData.classId);
				$('#scoreId').val(curclassRowData.scoreId);
				$('#name').val(curclassRowData.name);
				$('#value').val(curclassRowData.value);
				$('#startScore').val(curclassRowData.startScore);
				$('#endScore').val(curclassRowData.endScore);
				$('#seqno').val(curclassRowData.seqno);
				
				$("#"+classModalId).modal('show');
			}
			else{
				bootbox.alert("请选择要修改的数据！","");
			}
		});
		
		$("#class_btn_del").on("click",function()
		{
			var selData = $("#"+classTableId).bootstrapTable('getSelections');//选中的数据
			var classIds ='';
			var classNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					classIds += selData[i].classId+',';
					classNames += selData[i].cname+"("+selData[i].classId+")"+',';
				}
				
				classIds = classIds.substring(0,classIds.length-1);
				classNames = classNames.substring(0,classNames.length-1);
				delClass(classIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
	}
	
	//删除确认操作
	function delClass(currIds)
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
	                $.post("/class/delete",{"currIds":currIds},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+classTableId).bootstrapTable('refresh');
        			    	bootbox.alert("删除成功！");
        			    	curclassRowData = "";
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+classTableId).bootstrapTable('refresh');
        			    	curclassRowData = "";
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
/*-------------------------------------函数：多变量则和增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_comvalue_tool_action()
	{
		countComvalueAction++;
		$('#comvalue_btn_add').on("click",function()
		{
			if(curRowData == "")
			{
				bootbox.alert("请先选择左侧评分卡","");
			}else{
				s_update_name = "";
//				b_update_name = "";
				if($("#"+comvalueDialogId).length == 0)
				{
					init_comvaluemodal();//初始化提交表单
					submit_comvalueform();//提交表单操作
				}
				$("#"+comvalueFormId)[0].reset();
				$('#scoreId').val(curRowData.scoreId);
				$("#"+comvalueModalId).modal('show');
			}
			
		});
		
		$('#comvalue_btn_edit').on("click",function()
		{
			if(curcomvalueRowData!="")
			{
				s_update_name = curcomvalueRowData.cvId;
//				b_update_name = curscvarRowData.cname;
				if($("#"+comvalueDialogId).length == 0)//判断表单是否已经初始化
				{
					init_comvaluemodal();  //初始化提交表单
					submit_comvalueform();		//提交表单操作
				}
				
		    	$('#cvId').val(curcomvalueRowData.cvId);
				$('#scvarId').val(curcomvalueRowData.scvarId);
				$('#comValue').val(curcomvalueRowData.comValue);
				$('#comText').val(curcomvalueRowData.comText);
				$('#seqno').val(curcomvalueRowData.seqno);
				
				$("#"+comvalueModalId).modal('show');
			}
			else{
				bootbox.alert("请选择要修改的数据！","");
			}
		});
		
		$("#comvalue_btn_del").on("click",function()
		{
			var selData = $("#"+comvalueTableId).bootstrapTable('getSelections');//选中的数据
			var comvalueIds ='';
			var comvalueNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					comvalueIds += selData[i].cvId+',';
					comvalueNames += selData[i].cname+"("+selData[i].cvId+")"+',';
				}
				
				comvalueIds = comvalueIds.substring(0,comvalueIds.length-1);
				comvalueNames = comvalueNames.substring(0,comvalueNames.length-1);
				delComb(comvalueIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
	}
	
	//删除确认操作
	function delComb(currIds)
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
	                $.post("/comvalue/delete",{"currIds":currIds},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+comvalueTableId).bootstrapTable('refresh');
        			    	bootbox.alert("删除成功！");
        			    	curcomvalueRowData = "";
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+comvalueTableId).bootstrapTable('refresh');
        			    	curcomvalueRowData = "";
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
/*-------------------------------------函数：组合实例增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_comcase_tool_action()
	{
		countComcaseAction++;
		$('#comcase_btn_add').on("click",function()
		{
			var data = {};
			if(curcomvalueRowData == "")
			{
				bootbox.alert("请先选择组合变量","");
			}else{
				$("#"+comcaseTableId).bootstrapTable('prepend',data);
			}
		});
		
		$("#comcase_btn_del").on("click",function()
		{
			var selData = $("#"+comcaseTableId).bootstrapTable('getSelections');//选中的数据
			var comcaseIds ='';
			var comcaseNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					comcaseIds += selData[i].id+',';
					comcaseNames += selData[i].cname+"("+selData[i].id+")"+',';
				}
				
				comcaseIds = comcaseIds.substring(0,comcaseIds.length-1);
				comcaseNames = comcaseNames.substring(0,comcaseNames.length-1);
				delComcase(comcaseIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
	}
	
	//删除确认操作
	function delComcase(currIds)
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
	                $.post("/comcase/delete",{"currIds":currIds},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+comcaseTableId).bootstrapTable('refresh');
        			    	bootbox.alert("删除成功！");
        			    	curcomcaseRowData = "";
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+comcaseTableId).bootstrapTable('refresh');
        			    	curcomcaseRowData = "";
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
	/*-------------------------------------函数：评分卡变量判例增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_scvarcase_tool_action()
	{
		countScVarCaseAction++;
		$('#scvarcase_btn_add').on("click",function()
		{
			var data = {};
			if(curRowData == "")
			{
				bootbox.alert("请先选择左侧评分卡","");
			}else{
				$("#"+scvarcaseTableId).bootstrapTable('prepend',data);
			}
		});
		
		$("#scvarcase_btn_del").on("click",function()
		{
			var selData = $("#"+scvarcaseTableId).bootstrapTable('getSelections');//选中的数据
			var scvarcaseIds ='';
			var scvarIds = '';
			var objNames = [];
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					scvarcaseIds += selData[i].caseId+',';
					scvarIds += selData[i].scvarId+',';
				}
				scvarIds = scvarIds.substring(0, scvarIds.length-1);
				var items = scvarIds.replace(/\s/g,'').split(/[,]/g);
		    	for(var i=0;i<scVarIdDS.length;i++)
		    	{
		    		if($.inArray(scVarIdDS[i].value,items) >= 0)
		    		{
		    			objNames.push(scVarIdDS[i].text);
			    	}
		    	}
		    	objNames = objNames.toString();
				scvarcaseIds = scvarcaseIds.substring(0,scvarcaseIds.length-1);
				delScvarCase(scvarcaseIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});

	}
	
	//删除确认操作
	function delScvarCase(scvarcaseIds)
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
	                $.post("/scVarCase/delete",{"currIds":scvarcaseIds},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	$("#"+scvarcaseTableId).bootstrapTable('refresh');
        			    }
        			    else
        			    {
        			    	bootbox.alert(data.msg);
        			    	$("#"+scvarcaseTableId).bootstrapTable('refresh');
        			    }
        	    	}, "json");
	            }
	        },  
        });
	}
	
	/*-------------------------------------函数：评分卡变量图解-----------------------------------------*/
	
	//获得变量数据
	function getscvarData()
	{
		$('#'+scvarcaseTujieId).empty();
		var chart = "pieChart";
		$.ajax({
			type: "POST",
			url:"/scvar/scVarIdList",
			data:{"scid":curScoreId,"chart":chart},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	scvar_data = eval(data);
		    	countScvarChart++;
		    	init_scvartujie();
		    }
		});
	}
	//得到变量，构造饼图(好像没有引用echart.js)
	function init_scvartujie()
	{
		if(countScvarChart < totalScvarChart)
		{
			return
		}
		scvarPieChart = echarts.init(document.getElementById(""+scvarTujieId+""));
		pieoption = {
			title: {
				text: '变量分布',
				left: 'center'
			},
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
//			legend: {
//				// orient: 'vertical',
//				// top: 'middle',
//				bottom:10,
//				left: 'center',
//				data: ['部门','职位','用户','新人','旧人']
//			},
			series: [{
				name: '变量',
				type: 'pie',
				radius: '55%',
				center: ['50%', '50%'],
				selectedMode: 'single',
				data: 
					scvar_data,
//					[
//					{ value: 10, name: '新人' },
//					{ value: 20, name: '旧人' },
//					{ value: 5, name: '用户' },
//					{ value: 3, name: '部门' },
//					{ value: 5, name: '职位' }
//				],
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				}
			}]
		};
		scvarPieChart.setOption(pieoption);
		scvarPieChart.on('click', function (param) {
			var _value = param.data.value;
//			getscvarcaseData(_value);
		});
	}
	
	//获得变量判例数据
	function getscvarcaseData(val)
	{
		var chart = "barChart";
		$.ajax({
			type: "POST",
			url:"/scVarCase/CaseByScVarId",
			data:{"scvarid":val,"chart":chart},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	bar_score = [];
		    	bar_text = [];
		    	data = eval(data);
		    	for(var i=0;i<data.length;i++)
		    	{
		    		bar_score.push(data[i].score);  
		    		bar_text.push(data[i].text);  
		    	}
		    	countScvarCaseChart++;
		    	init_scvarcasetujie();
		    }
		});
	}
	
	//得到变量，构造柱状图
	function init_scvarcasetujie()
	{
		if(countScvarCaseChart < totalScvarCaseChart)
		{
			return
		}
		scvarcaseBarChart = echarts.init(document.getElementById(""+scvarcaseTujieId+""));
		
		// 显示标题，图例和空的坐标轴
		var baroption = {
        		title: {
		            text: '判例分布'
		        },
		        tooltip: {
		        	trigger: 'axis',
		            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        	}
		        },
		        legend: {
		        },
		        toolbox: {
		            show : true,
		            orient: 'vertical',
		            x: 'right',
		            y: 'center',
		            feature : {
		                magicType : {show: true, type: ['line', 'bar']},
		                restore : {show: true}
		        	}
		        },
		        xAxis: {
		        	type : 'category',
		            data: bar_text
		        },
		        yAxis: [  
                    {  
                        type: 'value'  
                    }  
                ],
		        series: [{
		        	name: '分数',
		            type: 'bar',
		            data: bar_score
		        }]
        };
		scvarcaseBarChart.clear();
		scvarcaseBarChart.setOption(baroption);
	}
	
	/*------------------------------------------函数：评分卡表单-----------------------------------------*/
	
	//初始化表单
	function init_scoremodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+scoreModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+scoreDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"score_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分卡</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+scoreFormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scoreId\">评分卡编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"scoreId\" id=\"scoreId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsId\">金融机构名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"finsId\" name=\"finsId\" class=\"form-control\">");
    	for(var i=0;i<finsIdDS.length;i++)
    	{
    		htmlInfo.append("<option value="+finsIdDS[i].parameterValue+">"+finsIdDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"type\">评分卡类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"type\" name=\"type\" class=\"form-control\">");
		for(var i=0;i<typeParaDS.length;i++)
		{
			htmlInfo.append("<option value="+typeParaDS[i].parameterValue+">"+typeParaDS[i].parameterName+"</option>");
		}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"category\">评分卡分类</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
    	for(var i=0;i<categoryParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+categoryParaDS[i].parameterValue+">"+categoryParaDS[i].parameterName+"</option>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"version\">版本号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"version\" id=\"version\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"releaseDate\">发布日期</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input type=\"text\" class=\"form-control layer-date laydate-icon\"  id=\"releaseDate\" name=\"releaseDate\" onclick=\"laydate({format:'YYYY-MM-DD'})\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
		
        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"effectiveDate\">生效日期</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input type=\"text\" class=\"form-control layer-date laydate-icon\"  id=\"effectiveDate\" name=\"effectiveDate\" onclick=\"laydate({format:'YYYY-MM-DD'})\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"expiredDate\">截止日期</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input type=\"text\" class=\"form-control layer-date laydate-icon\"  id=\"expiredDate\" name=\"expiredDate\" onclick=\"laydate({format:'YYYY-MM-DD'})\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
	
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<scoreStatusDS.length;i++)
    	{
    		htmlInfo.append("<option value="+scoreStatusDS[i].parameterValue+">"+scoreStatusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
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
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"score_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"score_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_scoreform()
	{
		$('#score_submit_btn').on("click",function(){
			var cname = $('#cname').val();
			var version = $('#version').val();
			
			if(cname!=""&& version!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/score/save",
					data:$("#"+scoreFormId).serialize(), //formid
				    error: function(request) {
				    	
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+scoreFormId)[0].reset();
				    	$("#"+scoreModalId).modal('hide');
				    	$("#"+scoreTableId).bootstrapTable('refresh');
				    	curRowData = "";
				    }
				});
				
			}else{
				showError("中文名称和版本号为必填项，请填写！", '');
				//bootbox.alert("中文名称和版本号为必填项，请填写！","");
			}
		});
		
		$('#score_cancel_btn').on("click",function(){
			$("#"+scoreFormId)[0].reset();
		});
		$('#score_close_btn').on("click",function(){
			$("#"+scoreFormId)[0].reset();
		});
	}
	
	/*------------------------------------------函数：角色授权-----------------------------------------*/
	//初始化角色授权table
	function init_roletable()
	{
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
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
	
	/*------------------------------授权角色时的操作--------------------------------------------*/
	//查找已经选择的数据
	function findYxRole()
	{
		$.ajax({
			type: "POST",
			url:"/score/getScoreRightByScoreId",
			data:{"scoreid":curRowData.scoreId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	if(data.msg == "成功")
		    	{
		    		data = eval(data);
		    		yxRole = data.objectIds;
		    	}else{
		    		yxRole = "";
		    	}
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
			method: 'post', 
			url:"/role/roleListAll",
			dataType: "json",
			cache: false,
			height: $(window).height()-250,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10000,20000],
			pageSize:10000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: rolequeryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			//showRefresh: true,
			smartDisplay:true,
			responseHandler: roleresponseHandler,
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
					visible: false,
					width: '10',
					valign: 'middle'
				},
				{
					field: 'nameCn',
					title: '角色名称',
					align: 'center',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'orgId',
					title: '机构名称',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: orgNameFormatter
				}
			],
			onLoadSuccess:function(data){
				//用于默认选择已选择过的数据
				if(yxRole != "")
				{
					if(data!=null)
					{
						var objs = data.rows;
						for(var i=0;i<objs.length;i++)
						{
							var obj = objs[i];
							var roleidArr = yxRole.split(",");
							for(var j=0;j<roleidArr.length;j++)
							{
								var yxroleid = roleidArr[j];
								if( yxroleid == obj.roleId)
								{
									$("#rolelistid").bootstrapTable("checkBy", {field:'roleId', values:[obj.roleId]})
								}
							}
						}
					}
				}
				
            },
            onUncheck: function(row){
            	//取消选择记录id
            	unroleId = row.roleId;
            	if($.inArray(unroleId, arrRoleIds) == -1)
            	{
            		arrRoleIds.push(unroleId);
            	}
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$('#rolelistid').bootstrapTable('refresh');
	}
	
	//table里面显示中文描述
	function orgNameFormatter(value) {
    	for(var i=0;i<orgNameDS.length;i++)
    	{
    		if(orgNameDS[i].parameterValue == value)
    		{
    			return ['<span>'+orgNameDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//设置table传递到服务器的参数
	function rolequeryParams(params)
	{
	    var temp = {				//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,		//页面大小
	      offset: params.offset/params.limit+1,		//页码
//	      scoreid: curRowData.scoreId,
//	      finsId: curRowData.finsId//当前评分卡的机构编号
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function roleresponseHandler(res)
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
	
	//提交选择的角色
	function submit_roleform()
	{
		$('#rolesubmit_btn').on("click",function(){
			
			var selData = $('#rolelistid').bootstrapTable('getSelections');//选中的数据
			var roleIds ='';
			var unRoleIds = arrRoleIds.join(",");
			var url = "/score/addRoleAndScore";
			
			for(var i=0; i < selData.length; i++)
			{
				roleIds += selData[i].roleId+',';
			}
			roleIds = roleIds.substring(0,roleIds.length-1);
			
			$.ajax({
				type: "POST",
				dataType: "json",
				url:url,
				data: {"roleIds":roleIds,"scoreid":curRowData.scoreId},
//				data: {"unRoleIds":unRoleIds,"roleIds":roleIds,"scoreid":curRowData.scoreId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	unroleId = "";
			    	arrRoleIds = [];
			    	$('#roleModal').modal('hide');
			    	$("#"+scoreTableId).bootstrapTable('refresh');
			    	curRowData = "";
			    }
			});
				
		});
		
		$('#rolecancel_btn').on("click",function(){
			
		});
		$('#roleclose_btn').on("click",function(){
			
		});
	}
	/*------------------------------结束授权角色时的操作--------------------------------------------*/
	
/*------------------------------------------函数：评分卡判例表单-----------------------------------------*/
	
	//初始化表单
	function init_scvarcasemodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"scvarcaseModalId\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"scvarcaseDialogId\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"scvarcase_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分卡变量判例</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"scvarcaseFormId\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scvarName\">评分卡变量</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"scvarName\" id=\"scvarName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"start\">起始</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"start\" name=\"start\" class=\"form-control\">");
    	for(var i=0;i<paraSEDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraSEDS[i].value+">"+paraSEDS[i].text+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"end\">截止</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"end\" name=\"end\" class=\"form-control\">");
		for(var i=0;i<paraSEDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraSEDS[i].value+">"+paraSEDS[i].text+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"scvarcase_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"scvarcase_submit_btn\" type=\"button\" class=\"btn btn-primary\">确定</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
	
	//提交表单
	function submit_scvarcaseform(index)
	{
		$('#scvarcase_submit_btn').on("click",function(){
			var startval = $('#start').val();
			var endval = $('#end').val();
			var starttext = $('#start').find("option:selected").text();
			var endtext = $('#end').find("option:selected").text();
			$("#scvarcase_table tr:eq("+index+") td:eq(6)").editable('setValue',startval);
			$("#scvarcase_table tr:eq("+index+") td:eq(7)").editable('setValue',endval);
			$("#scvarcase_table tr:eq("+index+") td:eq(4)").editable('setValue',starttext);
			$("#scvarcase_table tr:eq("+index+") td:eq(5)").editable('setValue',endtext);
			
			$("#scvarcaseFormId")[0].reset();
	    	$("#scvarcaseModalId").modal('hide');
			
		});
		
		$('#scvarcase_cancel_btn').on("click",function(){
			$("#scvarcaseFormId")[0].reset();
		});
		$('#scvarcase_close_btn').on("click",function(){
			$("#scvarcaseFormId")[0].reset();
		});
	}
	
/*------------------------------------------函数：组合实例表单-----------------------------------------*/
	
	//初始化表单
	function init_comcasemodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"comcaseModalId\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"comcaseDialogId\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"comcase_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分卡变量判例</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"comcaseFormId\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"comName\">评分卡变量</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"comName\" id=\"comName\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"start\">起始</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"start\" name=\"start\" class=\"form-control\">");
    	for(var i=0;i<paraSEDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraSEDS[i].value+">"+paraSEDS[i].text+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"end\">截止</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"end\" name=\"end\" class=\"form-control\">");
		for(var i=0;i<paraSEDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraSEDS[i].value+">"+paraSEDS[i].text+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"comcase_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"comcase_submit_btn\" type=\"button\" class=\"btn btn-primary\">确定</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
	
	//提交表单
	function submit_comcaseform(index)
	{
		$('#comcase_submit_btn').on("click",function(){
			var startval = $('#start').val();
			var endval = $('#end').val();
			var starttext = $('#start').find("option:selected").text();
			var endtext = $('#end').find("option:selected").text();
			$("#comcase_table tr:eq("+index+") td:eq(5)").editable('setValue',startval);
			$("#comcase_table tr:eq("+index+") td:eq(6)").editable('setValue',endval);
			$("#comcase_table tr:eq("+index+") td:eq(3)").editable('setValue',starttext);
			$("#comcase_table tr:eq("+index+") td:eq(4)").editable('setValue',endtext);
			
			$("#comcaseFormId")[0].reset();
	    	$("#comcaseModalId").modal('hide');
			
		});
		
		$('#comcase_cancel_btn').on("click",function(){
			$("#comcaseFormId")[0].reset();
		});
		$('#comcase_close_btn').on("click",function(){
			$("#comcaseFormId")[0].reset();
		});
	}
	
	/*------------------------------------------函数：评分卡变量表单-----------------------------------------*/
	
	//初始化表单
	function init_scvarmodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+scvarModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+scvarDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"scvar_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分卡变量</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+scvarFormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scvarId\">变量编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"scvarId\" id=\"scvarId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scoreId\">评分卡编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"scoreId\" id=\"scoreId\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"varType\">变量类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"varType\" name=\"varType\" class=\"form-control\">");
    	for(var i=0;i<varTypeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+varTypeDS[i].parameterValue+">"+varTypeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"componentVarIds\">变量组合</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"componentVarIds\" name=\"componentVarIds\" multiple class=\"selectpicker show-tick form-control\">");
		for(var i=0;i<scvarIdDS.length;i++)
    	{
//			if(i == 0)
//    		{
//    			htmlInfo.append("<option value=>请选择...</option>");
//    		}
			htmlInfo.append("<option value="+scvarIdDS[i].parameterValue+">"+scvarIdDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"paragrpName\">参数类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"paragrpName\" name=\"paragrpName\" class=\"form-control\">");
    	for(var i=0;i<paraDS.length;i++)
    	{
    		htmlInfo.append("<option value="+paraDS[i].parameterValue+">"+paraDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"dataType\">数据类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"dataType\" name=\"dataType\" class=\"form-control\">");
    	for(var i=0;i<vdtypeParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+vdtypeParaDS[i].parameterValue+">"+vdtypeParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cdesc\">中文描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cdesc\" id=\"cdesc\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"edesc\">英文描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"edesc\" id=\"edesc\" type=\"text\" placeholder=\"\"/>");
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
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"scvar_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"scvar_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
		
	//提交表单
	function submit_scvarform()
	{
		$('#scvar_submit_btn').on("click",function(){
			var cname = $('#cname').val();
			var ename = $('#ename').val();
			var paragrp = $('#paragrpName').val();
			var url = "/scvar/save";
			if(cname!=""&& ename!=""&&paragrp!="")
			{
				if(s_update_name == "")
				{
					anyname = cname;
				}else{
					anyname = cname + "(" + s_update_name + ")";
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$("#"+scvarFormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+scvarFormId)[0].reset();
				    	$("#"+scvarModalId).modal('hide');
				    	$("#"+scvarTableId).bootstrapTable('refresh');
				    	curscvarRowData = "";
				    }
				});
				
			}else{
				showError("中文名称、英文名称和参数类别名称为必填项，请填写！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#scvar_cancel_btn').on("click",function(){
			$("#"+scvarFormId)[0].reset();
		});
		$('#scvar_close_btn').on("click",function(){
			$("#"+scvarFormId)[0].reset();
		});
	}
	
/*------------------------------------------函数：多变量组合表单-----------------------------------------*/
	
	//初始化表单
	function init_comvaluemodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+comvalueModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+comvalueDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"comvalue_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">多变量组合</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+comvalueFormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cvId\">组合值ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"cvId\" id=\"cvId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
//		htmlInfo.append("<div class=\"form-group\">");
//		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scvarId\">评分卡变量编号</label>");
//		htmlInfo.append("<div class=\"col-sm-8\">");
//		htmlInfo.append("<input class=\"form-control\" name=\"scvarId\" id=\"scvarId\" type=\"text\" placeholder=\"\"/>");
//		htmlInfo.append("</div>");
//		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scvarId\">评分卡组合变量</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"scvarId\" name=\"scvarId\" class=\"form-control\">");
    	for(var i=0;i<scvarIdDS2.length;i++)
    	{
    		htmlInfo.append("<option value="+scvarIdDS2[i].parameterValue+">"+scvarIdDS2[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"comValue\">组合值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"comValue\" id=\"comValue\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"comText\">组合描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"comText\" id=\"comText\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"comvalue_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"comvalue_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
		
	//提交表单
	function submit_comvalueform()
	{
		$('#comvalue_submit_btn').on("click",function(){
			var name = $('#name').val();
			var url = "/comvalue/save";
			if(name!="")
			{
				if(s_update_name == "")
				{
					anyname = name;
				}else{
					anyname = name + "(" + s_update_name + ")";
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$("#"+comvalueFormId).serialize(), //formid
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+comvalueFormId)[0].reset();
				    	$("#"+comvalueModalId).modal('hide');
				    	$("#"+comvalueTableId).bootstrapTable('refresh');
				    	curcomvalueRowData = "";
				    }
				});
				
			}else{
				showError("等级名称为必填项，请填写！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#comvalue_cancel_btn').on("click",function(){
			$("#"+comvalueFormId)[0].reset();
		});
		$('#comvalue_close_btn').on("click",function(){
			$("#"+comvalueFormId)[0].reset();
		});
	}
	
/*------------------------------------------函数：评分等级表单-----------------------------------------*/
	
	//初始化表单
	function init_classmodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+classModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+classDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"class_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分等级</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+classFormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"classId\">等级编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"classId\" id=\"classId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"scoreId\">评分卡编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"scoreId\" id=\"scoreId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">等级名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"startScore\">起始分值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"startScore\" id=\"startScore\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"endScore\">截止分值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"endScore\" id=\"endScore\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"value\">等级数值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"value\" id=\"value\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"class_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"class_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+modalId).empty();
	    $("#"+modalId).append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_classform()
	{
		$('#class_submit_btn').on("click",function(){
			var name = $('#name').val();
			var url = "/class/save";
			if(name!="")
			{
				if(s_update_name == "")
				{
					anyname = name;
				}else{
					anyname = name + "(" + s_update_name + ")";
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$("#"+classFormId).serialize(), //formid
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+classFormId)[0].reset();
				    	$("#"+classModalId).modal('hide');
				    	$("#"+classTableId).bootstrapTable('refresh');
				    	curclassRowData = "";
				    }
				});
				
			}else{
				showError("等级名称为必填项，请填写！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#class_cancel_btn').on("click",function(){
			$("#"+classFormId)[0].reset();
		});
		$('#class_close_btn').on("click",function(){
			$("#"+classFormId)[0].reset();
		});
	}
	
	/*------------------------------批量添加时的操作--------------------------------------------*/
	//查找已经选择的数据
	function findYxData()
	{
		$.ajax({
			type: "POST",
			url:"/scvar/getScvarByScoreId",
			data:{"scoreId":curRowData.scoreId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxData = data.objectIds;
//		    	countSyncDS++;
		    	getVar();//得到角色数据
		    }
		});
	}
	
	function getVar()
	{
		$('#varlistid').bootstrapTable({
			method: 'post', 
			url:"/scvar/listOtherVar",
			dataType: "json",
			cache: false,
			height: $(window).height()-270,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10000,20000],
			pageSize:10000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: varqueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			smartDisplay:true,
			responseHandler: varresponseHandler,
			columns: [
				{
					field: 'scvarmodal_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle',
					formatter : stateLFormatter
				},
				{
					field: 'varId',
					title: '变量编号',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'ename',
					title: '英文名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'objectCode',
					title: '已选数据',
					align: 'center',
					width: '10',
					valign: 'middle',
					visible: false,
				}
			],
			onLoadSuccess:function(data){
				//用于默认选择已选择过的数据
				if(data!=null)
				{
					var objs = data.totalNum;
					for(var i=0;i<objs.length;i++)
					{
						var obj = objs[i];
						var scvaridArr = obj.objectCode.split(",");
						for(var j=0;j<scvaridArr.length;j++)
						{
							var yxscvarid = scvaridArr[j];
							if( yxscvarid == obj.varId)
							{
								$("#varlistid").bootstrapTable("checkBy", {field:'varId', values:[obj.varId]})
							}
						}
					}
				}
				
            },
            onUncheck: function(row){
            	//取消选择记录Id
            	unvarId = row.varId;
            	if($.inArray(unvarId, arrVarIds) == -1)
            	{
            		arrVarIds.push(unvarId);
            	}
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$('#varlistid').bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function varqueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      scoreid: curRowData.scoreId
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function varresponseHandler(res)
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
	//对应的函数进行判断；
	function stateLFormatter(value, row, index) {
		if(yxData != "")
		{
			var varIdArr = yxData.split(",");
			for(var j=0;j<varIdArr.length;j++)
			{
				if( varIdArr[j] == row.varId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
	}
	
	//提交选择
	function submit_varform()
	{
		$('#varsubmit_btn').on("click",function(){
			
			var selData = $('#varlistid').bootstrapTable('getSelections');//选中的数据
			var varIds ='';
//			var varNames ='';
			var unVarIds = arrVarIds.join(",");
			var url = "/scvar/varToScVar";
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					varIds += selData[i].varId+',';
//					varNames += selData[i].cname+',';
				}
				varIds = varIds.substring(0,varIds.length-1);
//				varNames = varNames.substring(0,varNames.length-1);
//				if(b_update_name == "")
//				{
//					anyname = s_update_name+varNames;
//				}else{
//					anyname = s_update_name+b_update_name+"为"+varNames;
//				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data: {"unvarIds":unVarIds,"varIds":varIds,"scoreid":curRowData.scoreId},
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	unvarId = "";
				    	arrVarIds = [];
				    	$('#varModal').modal('hide');
				    	$("#"+scvarTableId).bootstrapTable('refresh');
				    }
				});
				
			}else{
				bootbox.alert("请选择数据！","");
			}
		});
		
		$('#varcancel_btn').on("click",function(){
			
		});
		$('#varclose_btn').on("click",function(){
			
		});
	}
	
	
});