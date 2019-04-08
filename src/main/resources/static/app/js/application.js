$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var actionInitBody = false;		//主数据操作按钮初始化标志
	var actionInitOpt0 = false;		//选项0数据操作按钮初始化标志
	var actionInitOpt1 = false;		//选项一数据操作按钮初始化标志
	var actionInitOpt2 = false;		//选项二数据操作按钮初始化标志
	var actionInitOpt3 = false;		//选项二数据操作按钮初始化标志
	var actionInitOpt5 = false;		
	var actionInitOpt6 = false;	
	var actionInitOpt7 = false;	
	var curAppCode;					//当前选中的主数据行的应用编号
	var curRowBody;					//当前选中的主数据行
	var curRowOpt0;					//当前选中的选项0数据行
	var curRowOpt1;					//当前选中的选项一数据行
	var curRowOpt2;
	var curRowOpt3;
	var curRowOpt5;
	var curRowOpt6;
	var curRowOpt7;
	var sectionId;
	
	var totalSyncDS4Body = 15;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	var totalSyncDS4Opt0 = 3;	
	var countSyncDS4Opt10 = 0;	
	var totalSyncDS4Opt10 = 2;	//同步选项一数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt0 = 0;	
	var totalSyncDS4Opt1 = 1;			//同步选项一数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt1 = 0;			//同步选项一数据集计数器，当前成功获取个数
	var totalSyncDS4Opt2 = 1;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
	var totalSyncDS4Opt6 = 2;			
	var countSyncDS4Opt6 = 0;	
	var totalSyncDS4YxData = 1;			//同步已选数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4YxData = 0;			//同步已选数据集计数器，当前成功获取个数
	
	var typeDS;
	var servifaDS;
	var servimplDS;
	var decisionStrategyDS;
	var scoreDS;
	var finsDS;
	var appCategoryDS;
	var appStatusDS;
	var delParaDS;
	var itemTypeParaDS;
	var idtypeParaDS;
	var isEmptyDS;
	var orgNameDS;
	var inputtypeParaDS;
	var entitytypeParaDS;
	var MainEntityDS;
	var itemDS;
	var entityMappingDS;
	var paraDS;
	var appScoreDS;
	var pcDS;
	var scvarDS;
	var item1DS;
	var baseDivId = "base_div";
	var rightContent_model = "rightContent_model";
	var typeService;
	
	//应用管理
	var bodyDivId = "application_div";
	var bodyTableId = "application_table";
	var bodyModalId = "application_modal";
	var bodyDialogId = "application_dialog";
	var bodyFormId = "application_form";
	var bodyDelAction = "/application/delete";
	var yxRole = ""; 				//记录已选角色
	
	//选项页0
	var opt0Object = "entity";
	var opt0DivId = opt0Object + "_div";
	var opt0TableId = opt0Object + "_table";
	var opt0ModalId = opt0Object + "_modal";
	var opt0DialogId = opt0Object + "_dialog";
	var opt0FormId = opt0Object + "_form";
	var opt0DelAction = "/entity/delEntity";
	
	//选项页一
	var opt1Object = "section";
	var opt1DivId = opt1Object + "_div";
	var opt1TableId = opt1Object + "_table";
	var opt1ModalId = opt1Object + "_modal";
	var opt1DialogId = opt1Object + "_dialog";
	var opt1FormId = opt1Object + "_form";
	var opt1DelAction = "/section/delSection";
	
	
	//选项页二
	var opt2Object = "item";
	var opt2DivId = opt2Object + "_div";
	var opt2TableId = opt2Object + "_table";
	var opt2ModalId = opt2Object + "_modal";
	var opt2DialogId = opt2Object + "_dialog";
	var opt2FormId = opt2Object + "_form";
	var opt2DelAction = "/item/delete";
	
	//选项页三
	var opt3Object = "para";
	var opt3DivId = opt3Object + "_div";
	var opt3TableId = opt3Object + "_table";
	var opt3ModalId = opt3Object + "_modal";
	var opt3DialogId = opt3Object + "_dialog";
	var opt3FormId = opt3Object + "_form";
	var opt3DelAction = "/appPara/delete";
	
	//选项页四
	var opt4Object = "sese";
	var opt4DivId = opt4Object + "_div";
	var opt4TableId = opt4Object + "_table";
	var opt4ModalId = opt4Object + "_modal";
	var opt4DialogId = opt4Object + "_dialog";
	var opt4FormId = opt4Object + "_form";
	var opt4DelAction = "/section/delItemSection";
	
	//选项页五
	var opt5Object = "score";
	var opt5DivId = opt5Object + "_div";
	var opt5TableId = opt5Object + "_table";
	var opt5ModalId = opt5Object + "_modal";
	var opt5DialogId = opt5Object + "_dialog";
	var opt5FormId = opt5Object + "_form";
	var opt5DelAction = "/appScore/delAppScore";
	
	//选项页六
	var opt6Object = "scoreBianLiang";
	var opt6DivId = opt6Object + "_div";
	var opt6TableId = opt6Object + "_table";
	var opt6ModalId = opt6Object + "_modal";
	var opt6DialogId = opt6Object + "_dialog";
	var opt6FormId = opt6Object + "_form";
	var opt6DelAction = "/itemScvar/delAppItem";
	
	//选项页七
	var opt7Object = "srvunit";
	var opt7DivId = opt7Object + "_div";
	var opt7TableId = opt7Object + "_table";
	var opt7ModalId = opt7Object + "_modal";
	var opt7DialogId = opt7Object + "_dialog";
	var opt7FormId = opt7Object + "_form";
	var opt7DelAction = "/srvunit/delete";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS4Body()
	{
		//服务类型
		if(typeDS == undefined)
		{
			var appcatParaGrpName = "SRVUNIT_TYPE";			
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
		//通用服务
		if(servifaDS == null || servifaDS == undefined)
		{
			$.ajax({
				type: "POST",
				url:"/serviceifa/sifaDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	servifaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//服务实现
		 if(servimplDS == null || servimplDS == undefined)
		 {
		 	$.ajax({
		 		type: "POST",
		 		url: "/serviceimpl/serimplDS",
		 	    error: function(request) {
		 	    },
		 	    success: function(data) {
		 	    	servimplDS = eval(data);
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
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//应用类别名称-值
		if(appCategoryDS == undefined)
		{
			var appcatParaGrpName = "APP_CATEGORY";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	appCategoryDS = eval(data);
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
		
		//软删除
		if(delParaDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
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
		
		if(idtypeParaDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "ITEM_DATA_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	idtypeParaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		if(inputtypeParaDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "ITEM_INPUT_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	inputtypeParaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		if(isEmptyDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "IS_EMPTY";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	isEmptyDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		if(entitytypeParaDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "ENTITY_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	entitytypeParaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		if(entityMappingDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "ENTITY_MAPPING";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	entityMappingDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		if(itemTypeParaDS == undefined)
		{	//保证仅获取一次
			//alert("delParaDS="+delParaDS);
			var ustsParaGrpName = "ITEM_DESIGN_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":ustsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	itemTypeParaDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}

		if(appScoreDS == undefined)
		{	
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/score/appScoreDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	appScoreDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}

	}
	
	function loadSyncDS4Body2 (){
		if(paraDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/appPara/getParaDS",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	paraDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt2();
			    }
			});
		}
	
	}
	function loadSyncDS4Body10 (){
		//当前应用下的决策策略
		if(decisionStrategyDS == undefined)
		{
			$.ajax({
				type: "POST",
				url: "/strategy/getStrategyByAppId",
				data:{"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	decisionStrategyDS = eval(data);
			    	countSyncDS4Opt10++;
			    	show_view_opt7();	
			    }
			});
		}
		//当前应用下的评分卡
		if(scoreDS == undefined)
		{
			$.ajax({
				type: "POST",
				url: "/application/getScoreDSByAppId",
				data:{"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	scoreDS = eval(data);
			    	countSyncDS4Opt10++;
			    	show_view_opt7();	
			    }
			});
		}
	}
	function loadSyncDS4Body0 (){
		if(MainEntityDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/entity/getMainEntityDS",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	MainEntityDS = eval(data);
			      	countSyncDS4Opt0 ++;
			    	show_view_opt0();	
			    }
			});
		}
		
		if(itemDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/item/getItemDS1",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	itemDS = eval(data);
			    	countSyncDS4Opt0 ++;
			    	show_view_opt0();			
			    }
			});
		}
		
		//数据映射
        if(pcDS == undefined)
        {
            var paraGrpName = "1";
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/dePoCase/getDecisionByGroupNameAndAppId",
                data:{"paraGroupName":paraGrpName,"appId":curAppCode},
                error: function(request) {
                },
                success: function(data) {
                	pcDS = eval(data);
                	countSyncDS4Opt0 ++;
                	show_view_opt0();
                }
            });
        }
		
		
	}
	function loadSyncDS4Body6(){
		//评分变量
		if(scvarDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/score/getScvarDS",
				data:{"scoreId" : curRowOpt5.scoreId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	scvarDS = eval(data);
			    	countSyncDS4Opt6 ++;
			    	show_view_opt6();			
			    }
			});
		}
        
        //数据项
		if(item1DS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/entity/getItem1DS",
				data:{"appId" : curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	item1DS = eval(data);
			    	countSyncDS4Opt6 ++;
			    	show_view_opt6();			
			    }
			});
		}
	}
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#application_id").on("click", function(){
		init_global();
	
		init_layout();								//初始化页面布局
//		init_tool_action_body();			//工具栏操作
		
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
		actionInitOpt0 = false;
		actionInitOpt1 = false;
		actionInitOpt2 = false;
		actionInitOpt3 = false;
		actionInitOpt5 = false;
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
            height: $(window).height()-260,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },{
                    field:'name',
                    title:'应用'
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
            	curRowOpt3 = "";
            	curRowOpt2 = "";
            	curRowOpt1 = "";
            	curRowOpt0 = "";
            	curRowOpt5 = "";
            	curRowBody = "";
            	curAppCode = "";
            	countSyncDS4Opt2 = 0;
            	countSyncDS4Opt10 = 0;
            	pcDS = undefined;
            	paraDS = undefined;
        		itemDS = undefined;
        		MainEntityDS = undefined;
            	curRowBody = row;
            	if(row.appId != undefined){
            	 	curAppCode = row.appId;
            	 	decisionStrategyDS = undefined;
            	 	scoreDS = undefined;
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
		//先清空右下
		$("#"+rightContent_model).empty();
		
		loadSyncDS4Body0(); 
		show_view_opt0();
		
		show_view_opt1();
		
		loadSyncDS4Body2();
		
		show_view_opt2();
		
		show_view_opt3();
		
		show_view_opt5();
		
		loadSyncDS4Body10();
		show_view_opt7();
	}
	
	//获取实体数据
	function show_view_opt0()
	{
		if(countSyncDS4Opt0 < totalSyncDS4Opt0){
			return;
		}
		init_tool_action_opt0();
		
		init_table_opt0();
		
		$('#'+opt0Object+'_toolbar').show();
		
		$("#"+opt0TableId).bootstrapTable({
			url: "/entity/selectAll",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-310,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,20],
			pageSize:10,
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
					field: 'entity_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'entityId',
					title: '实体编号',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'nameCn',
					title: '中文名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'nameEn',
					title: '英文名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'className',
					title: '对象类名',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'formJs',
					title: '页面js',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			
            onClickRow:function(row){
            	curRowOpt0 = row;
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
            
            onLoadSuccess: function(){
//            	bootbox.alert("数据加载成功！","");
            },
            onLoadError: function () {
//            	bootbox.alert("数据加载失败000000！","");
            }
		});
		$("#"+opt0TableId).bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function opt0QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      appId: curAppCode
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
	
	
	function show_view_opt1()
	{
		if($("#"+opt4TableId).length != 0){
			$('#'+opt4DivId).empty();
			$('#'+opt4Object + "_toolbar").empty();
		}
		init_tool_action_opt1();
		
		init_table_opt1();
		
		$('#'+opt1Object+'_toolbar').show();
		
		$("#"+opt1TableId).bootstrapTable({
			url: "/section/getSectionAll",
			method: 'post', 
			dataType: "json",
			cache: false,
//			height: $(window).height()-500,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,20,40],
			pageSize:10,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt1QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt1Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt1ResponseHandler,
			columns: [
				{
					field: 'section_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'sectionId',
					title: '区段编号',
					align: 'left',
					width: '',
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
					valign: 'middle',
					visible: false
				},
				{
					field: 'appId',
					title: '产品Id',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'htmlId',
					title: '页面Id',
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
			onLoadSuccess:function(row){
            },
            onClickRow:function(row){
            	curRowOpt1 = row;
            	sectionId = row.sectionId;
            	show_view_opt4();
            	$("#"+opt1TableId).each(function() { 
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
//            	bootbox.alert("数据加载失败111111！","");
            }
		});
		$("#"+opt1TableId).bootstrapTable('refresh');
		
	}
	
	
	
	//设置table传递到服务器的参数
	function opt1QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,										//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      appId: curAppCode
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt1ResponseHandler(res)
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
			url: "/item/itemList",
			dataType: "json",
			queryParams: opt2QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
            striped:true,
            height: $(window).height()-250,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },{
                    field:'name',
                    title:'数据项'
                },{
                	field:'dataType',
                	title:'数据项类型',
                	formatter: dataTypeFormatter
                },{
                	field:'designType',
                	title:'设计类型',
                	formatter: designTypeFormatter
                },{
                	field:'optionsGroup',
                	title:'参数类别',
                	formatter: optionsGroupFormatter
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
	
	
	//设置table传递到服务器的参数
	function opt2QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      appId : curAppCode
	    };
	    return temp;
	 }
	
	 function dataTypeFormatter(value) {
		
    	for(var i=0;i<idtypeParaDS.length;i++)
    	{
    		if(idtypeParaDS[i].parameterValue == value)
    		{
    			return ['<span>'+idtypeParaDS[i].parameterName+'</span>']
	    	}
    	}
	 }
	
    function designTypeFormatter(value) {
		
    	for(var i=0;i<itemTypeParaDS.length;i++)
    	{
    		if(itemTypeParaDS[i].parameterValue == value)
    		{
    			return ['<span>'+itemTypeParaDS[i].parameterName+'</span>']
	    	}
    	}
	 }
	
     function optionsGroupFormatter(value) {
		
    	for(var i=0;i<paraDS.length;i++)
    	{
    		if(paraDS[i].parameterValue == value)
    		{
    			return ['<span>'+paraDS[i].parameterName+'</span>']
	    	}
    	}
	  }
	
	
	function show_view_opt3()
	{
		
		init_tool_action_opt3();
		
		init_table_opt3();
		
		$('#'+opt3Object+'_toolbar').show();
		
		$("#"+opt3TableId).bootstrapTable({
			url: "/appPara/select",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-360,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,20,50,100],
			pageSize:20,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt3QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt3Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt3ResponseHandler,
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
					align: 'left',
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
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
//				bootbox.alert("数据加载成功33333！","");
            },
            onClickRow:function(row){
            	curRowOpt3 = row;
            	$("#"+opt3TableId).each(function() { 
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
		$("#"+opt3TableId).bootstrapTable('refresh');
		
	}
	
	
	//设置table传递到服务器的参数
	function opt3QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset/params.limit + 1,			//页码
	      appId: curAppCode
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt3ResponseHandler(res)
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
	
	
	
	function show_view_opt4()
	{
		
		init_tool_action_opt4();
		
		init_table_opt4();
		
		$('#'+opt4Object+'_toolbar').show();
		
		$("#"+opt4TableId).bootstrapTable({
			url: "/section/getItemSectionBySectionId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-500,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10,20,40],
			pageSize:5,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt4QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: false,
			showToggle: false,
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt4ResponseHandler,
			columns: [
				{
					field: 'mapping_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
//				{
//					field: 'sectionId',
//					title: '区段名称',
//					align: 'left',
//					width: '',
//					valign: 'middle',
//				},
				{
					field: 'itemId',
					title: '数据项名称',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: itemIdFormatter 
				},
				{
					field: 'seqno',
					title: '排序',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
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
//            	bootbox.alert("数据加载失败33333！","");
            }
		});
		$("#"+opt4TableId).bootstrapTable('refresh');
	}
	//table里面显示中文描述
	function itemIdFormatter(value) {
    	for(var i=0;i<itemDS.length;i++)
    	{
    		if(itemDS[i].parameterValue == value)
    		{
    			return ['<span>'+itemDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//设置table传递到服务器的参数
	function opt4QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset/params.limit + 1,			//页码
	      sectionId: sectionId
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
	
	function show_view_opt5()
	{
		init_tool_action_opt5();
		
		init_table_opt5();
		
		$('#'+opt5Object+'_toolbar').show();
		
		$("#"+opt5TableId).bootstrapTable({
			url: "/appScore/getAppScoreByAppId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-550,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [5,10],
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
					field: 'appId',
					title: '应用',
					align: 'center',
					width: '',
					valign: 'middle',
					visible:false
				},
				{
					field: 'scoreId',
					title: '评分卡',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: scoreIdFormatter 
				},
				{
					field: 'seqno',
					title: '排序',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt5="";
            	curRowOpt5 = row;
            	scvarDS=undefined;
            	item1DS=undefined;
            	countSyncDS4Opt6=0; 
            	show_view_opt55();
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
	
	//table里面显示中文描述
	function scoreIdFormatter(value) {
    	for(var i=0;i<appScoreDS.length;i++)
    	{
    		if(appScoreDS[i].parameterValue == value)
    		{
    			return ['<span>'+appScoreDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//设置table传递到服务器的参数
	function opt5QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      appId: curAppCode
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
	
	function show_view_opt55(){
		
		init_layout1();
		
		loadSyncDS4Body6();
		show_view_opt6();
	}
	
	function show_view_opt6(){

		if(countSyncDS4Opt6 < totalSyncDS4Opt6){
			return;
		}
		
		init_tool_action_opt6();
		
		init_table_opt6();
		
		$('#'+opt6Object+'_toolbar').show();
		
		$("#"+opt6TableId).bootstrapTable({
			url: "/itemScvar/getItemScvarByAppscId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-500,
//			pagination: true,//在表格底部显示分页工具栏 
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
					field: 'itemId',
					title: '数据项',
					align: 'center',
					width: '',
					valign: 'middle',
					formatter: item1DSFormatter 
				},
				{
					field: 'scvarId',
					title: '评分变量',
					align: 'center',
					width: '',
					valign: 'middle',
					formatter: scvarFormatter 
				},
				{
					field: 'seqno',
					title: '排序',
					align: 'left',
					width: '',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
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
//            	bootbox.alert("数据加载失败33333！","");
            }
		});
		$("#"+opt6TableId).bootstrapTable('refresh');
	}
	
	//table里面显示中文描述
	function pcIdFormatter(value) {
    	for(var i=0;i<pcDS.length;i++)
    	{
    		if(pcDS[i].parameterValue == value)
    		{
    			return ['<span>'+pcDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function scvarFormatter(value) {
    	for(var i=0;i<scvarDS.length;i++)
    	{
    		if(scvarDS[i].parameterValue == value)
    		{
    			return ['<span>'+scvarDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function item1DSFormatter(value) {
    	for(var i=0;i<item1DS.length;i++)
    	{
    		if(item1DS[i].parameterValue == value)
    		{
    			return ['<span>'+item1DS[i].parameterName+'</span>']
	    	}
    	}
	}
	//设置table传递到服务器的参数
	function opt6QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      appscId: curRowOpt5.appscId
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
	//功能服务
	function show_view_opt7()
	{
		if(countSyncDS4Opt10 < totalSyncDS4Opt10){
			return;
		}
		init_table_opt7();
		
		init_tool_action_opt7(); 
		
		$('#'+opt7Object+'_toolbar').show();
		
		$("#"+opt7TableId).bootstrapTable({
			url: "/srvunit/selectByAppId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-540,
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [100000,200000],
			pageSize:100000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt7QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			cardView: true, 
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt7Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt7ResponseHandler,
			columns: [
				{
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
				},
				{
					field: 'cname',
					title: '服务名称',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'servifaId',
					title: '通用服务',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: servifaFormatter,
				},
				{
					field: 'servimplId',
					title: '服务实现',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: servimplFormatter,
				},
				{
					field: 'type',
					title: '服务类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: typeDSFormatter,
				},
				{
					field: 'tardataId',
					title: '目标数据',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: decisionStrategyDSFormatter,
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
            	curRowOpt7 = row;
            	if(row._level != 0){
            		
            	}
            	$("#"+opt7TableId).each(function() { 
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
		$("#"+opt7TableId).bootstrapTable('refresh');
	}
	function init_table_opt7()
	{
		if($("#"+opt7TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt7TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt7DivId).append(htmlInfo.toString());
		}
	}
	function opt7QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      appId: curAppCode
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt7ResponseHandler(res)
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
	//服务类型
	function typeDSFormatter(value) {
    	for(var i=0;i<typeDS.length;i++)
    	{
    		if(typeDS[i].parameterValue == value)
    		{
    			return ['<span>'+typeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//通用服务
	function servifaFormatter(value) {
	 for(var i=0;i<servifaDS.length;i++)
		{
			if(servifaDS[i].parameterValue == value)
			{
				return ['<span>'+servifaDS[i].parameterName+'</span>']
	    	}
		}
	}
	//服务实现
	function servimplFormatter(value) {
	 for(var i=0;i<servimplDS.length;i++)
		{
			if(servimplDS[i].parameterValue == value)
			{
				return ['<span>'+servimplDS[i].parameterName+'</span>']
	    	}
		}
	}
	function decisionStrategyDSFormatter(value) {
		var title = value.substring(0,2);
		if(title == "DS"){
			for(var i=0;i<decisionStrategyDS.length;i++)
			{
				if(decisionStrategyDS[i].parameterValue == value)
				{
					return ['<span>'+decisionStrategyDS[i].parameterName+'</span>']
				}
			}	
			
		}else if(title == "AS"){
			for(var i=0;i<scoreDS.length;i++)
			{
				if(scoreDS[i].parameterValue == value)
				{
					return ['<span>'+scoreDS[i].parameterName+'</span>']
				}
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
			htmlInfo.append("<h3 class=\"panel-title\">应用管理</h3>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");		//panel
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-7\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li class=\"active\"><a href=\"."+opt0DivId+"\" data-toggle=\"tab\">数据实体</a></li>");
			htmlInfo.append("<li><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">数据项</a></li>");
			htmlInfo.append("<li><a href=\"."+opt1DivId+"\" data-toggle=\"tab\">页面区段</a></li>");
			htmlInfo.append("<li><a href=\"."+opt5DivId+"\" data-toggle=\"tab\">评分卡</a></li>");
			htmlInfo.append("<li><a href=\"."+opt7DivId+"\" data-toggle=\"tab\">功能服务</a></li>");
			htmlInfo.append("<li><a href=\"."+opt3DivId+"\" data-toggle=\"tab\">数据参数</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
			//opt0 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt0DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt0Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt0DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			//
			
			//opt1 tab
			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt1DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt1Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt1DivId + "\">");
	        htmlInfo.append("</div>");
	        //关联表
	        
			htmlInfo.append("<div id=\"" + opt4Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
	        htmlInfo.append("<div id=\"" + opt4DivId +"\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			//
			
			//opt2 tab
			htmlInfo.append("<div class=\"tab-pane fade in " + opt2DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt2Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt2DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			//
			
			//opt3tab
			htmlInfo.append("<div class=\"tab-pane fade in " + opt3DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt3Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt3DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			
			//opt7tab
			htmlInfo.append("<div class=\"tab-pane fade in " + opt7DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt7Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt7DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
			
			//opt5tab
			htmlInfo.append("<div class=\"tab-pane fade in " + opt5DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt5Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"" + opt5DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("<div id=\"" + rightContent_model + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");		//opt5
			
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
		htmlInfo.append("<ul id=\"myTab1\" class=\"nav nav-tabs\">");
		htmlInfo.append("<li class=\"active\"><a href=\"."+opt6DivId+"\" data-toggle=\"tab\">评分变量</a></li>");
		htmlInfo.append("</ul>");
		
		htmlInfo.append("<div id=\"myTabContent1\" class=\"tab-content\">");	
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
	
	//初始化当前选项页0的table
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
	
	//初始化当前选项页二的table
	function init_table_opt3()
	{
		if($("#"+opt3TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt3TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt3DivId).append(htmlInfo.toString());
		}
	}
	
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
	function init_table_opt5()
	{
		if($("#"+opt5TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt5TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt5DivId).append(htmlInfo.toString());
		}
	}
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
	
	function init_tool_action_opt0()
	{
		if(!actionInitOpt0)
		{
			$("#"+opt0Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt0Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权角色");
			htmlInfo.append("</button>");
			
			$("#"+opt0Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt0Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					init_modal_opt0();//初始化提交表单
					init_form_action_opt0();//提交表单操作
					schange();
					$("#"+opt0FormId)[0].reset();
					$('#opt0_appId').val(curAppCode);
					$("#"+opt0ModalId).modal('show');
				}else {
					bootbox.alert("请选择应用进行操作！","");
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
						objCodes += selData[i].entityId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					delMany(opt0DelAction, objCodes,'#'+opt0TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			
			//授权角色
			$("#btn_sqrole").on("click",function(){
				if(curRowOpt0!="")
				{
					if($('#roledialogid').length == 0)
					{
						init_roletable();//初始化
						submit_roleform();//提交操作
					}
					$('#roleModal').modal('show');
					findYxRole();//查找已经选择的数据用于授权角色
					getRoles();  //得到角色数据
				}
				else{
					bootbox.alert("请选择要授权角色的实体！","");
				}
			});

			actionInitOpt0 = true;
		}
	}
	
	
	//选项一数据编辑操作
	function edit_opt0(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt0();//初始化提交表单
			init_form_action_opt0();//提交表单操作
			schange();
			$('#opt0_entityId').val(entry.entityId);
			$('#opt0_appId').val(entry.appId);
			$('#opt0_type').val(entry.type);
			$('#opt0_nameCn').val(entry.nameCn);
			$('#opt0_nameEn').val(entry.nameEn);			
			$('#opt0_className').val(entry.className);
			if(entry.type == 2){
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
		    	$('#opt0_parentId').val(entry.parentId);
			}
			$('#opt0_mapping').val(entry.mapping);
			
//			if(entry.parentItemId)
//			{
// 				var arr2 = entry.parentItemId.replace(/\s/g,'').split(/[,]/g);
//				$('#opt0_parentItemId').selectpicker('val', arr2);
//			}
			$('#opt0_parentItemId').val(entry.parentItemId);
			$('#opt0_formJs').val(entry.formJs);
			$('#opt0_seqno').val(entry.seqno);
			$("#"+opt0ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
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
					init_modal_opt1();//初始化提交表单
					init_form_action_opt1();//提交表单操作
					
					$("#"+opt1FormId)[0].reset();
					$('#opt1_appId').val(curAppCode);
					$("#"+opt1ModalId).modal('show');
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt1Object+"_btn_edit").on("click",function()
			{
				edit_opt1(curRowOpt1);
			});
			
			$("#"+opt1Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt1TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].sectionId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					delMany(opt1DelAction, objCodes,'#'+opt1TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
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
			
			$('#opt1_sectionId').val(entry.sectionId);
			$('#opt1_appId').val(entry.appId);
			$('#opt1_cname').val(entry.cname);
			$('#opt1_ename').val(entry.ename);			
			$('#opt1_htmlId').val(entry.htmlId);
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
				if(curRowOpt2 != undefined && curRowOpt2._level == 0){
					init_modal_opt2();//初始化提交表单
					init_form_action_opt2();//提交表单操作
					$("#"+opt2FormId)[0].reset();
					$('#opt2_entityId').val(curRowOpt2.entityId);
					$("#"+opt2ModalId).modal('show');
				}else{
					bootbox.alert("请选择实体添加数据项！","");
				}
			
			});
			
			$("#"+opt2Object+"_btn_edit").on("click",function()
			{
				if(curRowOpt2 != undefined && curRowOpt2._level != 0){
					$.ajax({
				    	   type:'post',
				    	   url: "/item/getItem",
				    	   data:{"itemId":curRowOpt2.itemId},
				    	   success:function(data){
				    		   edit_opt2(data);      
				    	   }
				     });
				}else{
					bootbox.alert("请选择数据项修改！","");
				}
			});
			
			$("#"+opt2Object+"_btn_del").on("click",function()
			{			
				var selData = $('#'+opt2TableId).bootstrapTable('getSelections');//选中的数据
				if(selData.length == 1 && selData[0]._level == 0){
					bootbox.alert("请选择数据项删除！","");
				}else{
					var objCodes = '';
					if(selData.length > 0)
					{
						for(var i=0;i<selData.length;i++)
						{
							if(selData[i]._level != 0){
								objCodes += selData[i].itemId + ',';
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
			
			$('#opt2_itemId').val(entry.itemId);
			$('#opt2_entityId').val(entry.entityId);
			$('#opt2_designType').val(entry.designType);
			$('#opt2_fieldName').val(entry.fieldName);
			$('#opt2_cname').val(entry.cname);
			$('#opt2_ename').val(entry.ename);			
			$('#opt2_htmlId').val(entry.htmlId);
			$('#opt2_pcId').val(entry.pcId);
			$('#opt2_dataType').val(entry.dataType);
			$('#opt2_inputType').val(entry.inputType);
			$('#opt2_unit').val(entry.unit);
			$('#opt2_optionsGroup').val(entry.optionsGroup);
			$('#opt2_inputWidth').val(entry.inputWidth);			
			$('#opt2_isEmpty').val(entry.isEmpty);
			$('#opt2_seqno').val(entry.seqno);
			
			$("#"+opt2ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	
	function init_tool_action_opt3()
	{
		if(!actionInitOpt3)
		{
			$("#"+opt3Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt3Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt3Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt3Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt3Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt3Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					init_modal_opt3();//初始化提交表单
					init_form_action_opt3();//提交表单操作
					$("#"+opt3FormId)[0].reset();
					$('#opt3_appId').val(curAppCode);
					$("#"+opt3ModalId).modal('show');
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt3Object+"_btn_edit").on("click",function()
			{
				edit_opt3(curRowOpt3);
			});
			
			$("#"+opt3Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt3TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].id + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					delMany(opt3DelAction, objCodes,'#'+opt3TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			actionInitOpt3 = true;
		}
	}
	//选项一数据编辑操作
	function edit_opt3(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt3();//初始化提交表单
			init_form_action_opt3();//提交表单操作
			
			$('#opt3_id').val(entry.id);
			$('#opt3_appId').val(entry.appId);
			$('#opt3_groupName').val(entry.groupName);
			$('#opt3_groupDescr').val(entry.groupDescr);			
			$('#opt3_name').val(entry.name);
			$('#opt3_value').val(entry.value);
			$('#opt3_descr').val(entry.descr);
			$('#opt3_seqno').val(entry.seqno);
			
			$("#"+opt3ModalId).modal('show');
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
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt4Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt4Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt4Object+"_btn_add").on("click",function()
			{
				init_modal_opt4();
				init_form_action_opt4();
				$("#"+opt4FormId)[0].reset();
				$('#opt4_sectionId').val(sectionId);
				$.ajax({
		         	   type:'post',
		         	   url: "/section/getSectionItemString",
		         	   data:{"sectionId":sectionId},
		         	   success:function(data){
		                if(data){
		                	var arr3 = data.replace(/\s/g,'').split(/[,]/g);
		    				$('#opt4_itemId').selectpicker('val', arr3);
		                }
		                $("#"+opt4ModalId).modal('show');
		         	   }
		        });
			});
			$("#"+opt4Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt4TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].seitId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					delMany(opt4DelAction, objCodes,'#'+opt4TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
	}
	
	function init_tool_action_opt5()
	{
		if(!actionInitOpt5)
		{
			$("#"+opt5Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt5Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
//			htmlInfo.append("<button id=\"" + opt5Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
//			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt5Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt5Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt5Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					init_modal_opt5();//初始化提交表单
					init_form_action_opt5();//提交表单操作
					$("#"+opt5FormId)[0].reset();
					$("#opt5_appId").val(curAppCode);
					$("#"+opt5ModalId).modal('show');
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt5Object+"_btn_edit").on("click",function()
			{
				edit_opt5(curRowOpt5);
			});
			
			$("#"+opt5Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt5TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].appscId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					//删除url
					delMany(opt5DelAction, objCodes,'#'+opt5TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			actionInitOpt5 = true;
		}
	}
	//选项一数据编辑操作
	function edit_opt5(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt5();//初始化提交表单
			init_form_action_opt5();//提交表单操作
			
			$('#opt5_appscId').val(entry.appscId);
			$('#opt5_appId').val(entry.appId);
			$('#opt5_scoreId').val(entry.scoreId);
			$('#opt5_seqno').val(entry.seqno);
			
			$("#"+opt5ModalId).modal('show');
		}
		else
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
			
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_edit\" style=\"margin-left:6px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt6Object + "_btn_del\" style=\"margin-left:6px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt6Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt6Object+"_btn_add").on("click",function()
			{
				init_modal_opt6();//初始化提交表单
				init_form_action_opt6();//提交表单操作
				$("#"+opt6FormId)[0].reset();
				$("#opt6_scoreId").val(curRowOpt5.scoreId);
				$("#opt6_appscId").val(curRowOpt5.appscId);
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
						objCodes += selData[i].itemscvarId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					//删除url
					delMany(opt6DelAction, objCodes,'#'+opt6TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			actionInitOpt6 = false;
		}
	}
	//选项一数据编辑操作
	function edit_opt6(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt6();//初始化提交表单
			init_form_action_opt6();//提交表单操作
			
			$('#opt6_scoreId').val(entry.scoreId);
			$('#opt6_itemscvarId').val(entry.itemscvarId);
			$('#opt6_appscId').val(entry.appscId);
			$('#opt6_itemId').val(entry.itemId);
			$('#opt6_scvarId').val(entry.scvarId);
			$('#opt6_pcId').val(entry.pcId);
			$('#opt6_seqno').val(entry.seqno);
			
			$("#"+opt6ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	function init_tool_action_opt7()
	{
		if(!actionInitOpt7)
		{
			$("#"+opt7Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt7Object + "_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt7Object + "_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt7Object + "_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt7Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt7Object+"_btn_add").on("click",function()
			{
				if (curRowBody.appId != undefined) {
					init_modal_opt7();//初始化提交表单
					init_form_action_opt7();//提交表单操作
					schange7();
					$("#"+opt7FormId)[0].reset();
					$("#opt7_appId").val(curAppCode);
					$("#"+opt7ModalId).modal('show');
				}else {
					bootbox.alert("请选择应用进行操作！","");
				}
			});
			
			$("#"+opt7Object+"_btn_edit").on("click",function()
			{
				edit_opt7(curRowOpt7);
			});
			
			$("#"+opt7Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt7TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].srvunId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1)
					//删除url
					delMany(opt7DelAction, objCodes,'#'+opt7TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});
			actionInitOpt7 = false;
		}
	}
	//选项数据编辑操作
	function edit_opt7(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt7();//初始化提交表单
			init_form_action_opt7();//提交表单操作
			schange7();
			$('#opt7_srvunId').val(entry.srvunId);
			$('#opt7_appId').val(entry.appId);
			$('#opt7_cname').val(entry.cname);
			$('#opt7_ename').val(entry.ename);
			$('#opt7_servifaId').val(entry.servifaId);
			$('#opt7_servimplId').val(entry.servimplId);
			$('#opt7_type').val(entry.type);
			$('#opt7_seqno').val(entry.seqno);

			var type=$("#opt7_type").val();
			typeService = type;
			var htmlInfo = new StringBuffer();
			if(type == 1){
				$("#opt7_tardataId").empty();
			}
			if(type == 50){
		    	for(var i=0;i<scoreDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+scoreDS[i].parameterValue+">"+scoreDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt7_tardataId").append(htmlInfo.toString());
			}
			if(type == 80){
				for(var i=0;i<decisionStrategyDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+decisionStrategyDS[i].parameterValue+">"+decisionStrategyDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt7_tardataId").append(htmlInfo.toString());
			}
			$('#opt7_tardataId').val(entry.tardataId);
			
			$("#"+opt7ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
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
	
//	function schange()
//	{
//		$("#finsCode").change(function () {
//			var tmpFlowDS = null;
//			var tmpScoreDS = null;
//			var tmpDecisionDS = null;
//			var _finsCode = $("#finsCode").val();
//			$("#flowCode").empty();
//			$("#scoreCode").empty();
//			$("#decisionCode").empty();
//			if(typeof(_finsCode)!="undefined" && _finsCode!=0)
//			{				
//				$.ajax({
//					type: "POST",
//					url: g_AppRoot+"/app/listFlowTV.action",
//					data:{"finsCode": _finsCode},
//				    error: function(request) {					
//				    },
//				    success: function(data) {
//				    	var selectInfo1 = new StringBuffer();
//				    	tmpFlowDS = eval(data);
//				    	for(var i=0;i<tmpFlowDS.length;i++)
//				    	{
//				    		if(i == 0)
//				    		{
//				    			selectInfo1.append("<option value=></option>");
//				    		}
//				    		selectInfo1.append("<option value="+tmpFlowDS[i].parameterValue+">"+tmpFlowDS[i].parameterName+"</option>");
//				    	}
//				    	$("#flowCode").append(selectInfo1.toString());
//				    }
//				});
//				
//				$.ajax({
//					type: "POST",
//					url: g_AppRoot+"/app/listScoreTV.action",
//					data:{"finsCode": _finsCode},
//				    error: function(request) {					
//				    },
//				    success: function(data) {
//				    	var selectInfo2 = new StringBuffer();
//				    	tmpScoreDS = eval(data);
//				    	for(var i=0;i<tmpScoreDS.length;i++)
//				    	{
//				    		if(i == 0)
//				    		{
//				    			selectInfo2.append("<option value=></option>");
//				    		}
//				    		selectInfo2.append("<option value="+tmpScoreDS[i].parameterValue+">"+tmpScoreDS[i].parameterName+"</option>");
//				    	}
//				    	$("#scoreCode").append(selectInfo2.toString());
//				    }
//				});
//				
//				$.ajax({
//					type: "POST",
//					url: g_AppRoot+"/app/listDecisionTV.action",
//					data:{"finsCode": _finsCode},
//				    error: function(request) {					
//				    },
//				    success: function(data) {
//				    	var selectInfo3 = new StringBuffer();
//				    	tmpDecisionDS = eval(data);
//				    	for(var i=0;i<tmpDecisionDS.length;i++)
//				    	{
//				    		if(i == 0)
//				    		{
//				    			selectInfo3.append("<option value=></option>");
//				    		}
//				    		selectInfo3.append("<option value="+tmpDecisionDS[i].parameterValue+">"+tmpDecisionDS[i].parameterName+"</option>");
//				    	}
//				    	$("#decisionCode").append(selectInfo3.toString());
//				    }
//				});
//			}
//	    });  
//		
//	}
	
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
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">数据实体</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt0FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_entityId\">实体编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"entityId\" id=\"opt0_entityId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_appId\">应用编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt0_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_type\">实体类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_type\" name=\"type\" class=\"form-control\">");
    	for(var i=0;i<entitytypeParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+entitytypeParaDS[i].parameterValue+">"+entitytypeParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_nameCn\">中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameCn\" id=\"opt0_nameCn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_nameEn\">英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameEn\" id=\"opt0_nameEn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_className\">对象类名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"className\" id=\"opt0_className\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_parentId\">父实体</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_parentId\" name=\"parentId\" class=\"form-control\">");
        ///////////////////////////////////////////////////////////
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_mapping\">父子关系</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_mapping\" name=\"mapping\" class=\"form-control\">");
    	for(var i=0;i<entityMappingDS.length;i++)
    	{
    		if(i == 0)
    		{
    			htmlInfo.append("<option value=></option>");
    		}
    		htmlInfo.append("<option value="+entityMappingDS[i].parameterValue+">"+entityMappingDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_parentItemId\">关联数据项</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_parentItemId\" name=\"parentItemId\" class=\"form-control\">");
    	for(var i=0;i<itemDS.length;i++)
    	{
    		if(i == 0){
    			htmlInfo.append("<option value=></option>");
    		}
    		htmlInfo.append("<option value="+itemDS[i].parameterValue+">"+itemDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_formJs\">页面表单</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"formJs\" id=\"opt0_formJs\" type=\"text\" placeholder=\"\"/>");
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
//	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
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
	//提交表单
	function init_form_action_opt0()
	{
		$('#'+opt0Object+'_submit_btn').on("click",function(){
			var cname = $('#opt0_nameCn').val();//中文名称
			var className = $('#opt0_className').val();//对象类名
			var type = $('#opt0_type').val();//数据实体
			var parentId = $('#opt0_parentId').val();//父实体
			var mapping = $('#opt0_mapping').val();//父子关系
			var parentItemId = $('#opt0_parentItemId').val();//关联数据项
			if(cname != "" && cname != null && className != "" && className != null)
			{
				if(type == 1){
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "/entity/save",
						data: $("#"+opt0FormId).serialize(), //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt0FormId)[0].reset();
					    	$("#"+opt0ModalId).modal('hide');
					    	$("#"+opt0TableId).bootstrapTable('refresh');
					    }
					});
				}
				if(type == 2){
					if(parentId != null && parentId != "" && mapping != null && mapping != "" && parentItemId !=null && parentItemId != "" ){
						$.ajax({
							type: "POST",
							dataType: "json",
							url: "/entity/save",
							data: $("#"+opt0FormId).serialize(), //formid
						    error: function(request) {
						        //alert("Connection error");
						    },
						    success: function(data) {
						    	bootbox.alert(data.msg,"");
						    	$("#"+opt0FormId)[0].reset();
						    	$("#"+opt0ModalId).modal('hide');
						    	$("#"+opt0TableId).bootstrapTable('refresh');
						    }
						});
					}else{
						showError("父实体、父子关系、关联数据项为必填项，请填写！", '');
					}
				}
				
				
			}else{
				showError("中文名称和对象类名为必填项，请填写！", '');
			}
		});
		
		$('#'+opt0Object+'_cancel_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
		$('#'+opt0Object+'_close_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
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
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">页面区段</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt1FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_itemCode\">区段编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"sectionId\" id=\"opt1_sectionId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_appCode\">应用编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt1_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_cname\">中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"opt1_cname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_ename\">英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"opt1_ename\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt1_htmlId\">页面ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"htmlId\" id=\"opt1_htmlId\" type=\"text\" placeholder=\"\"/>");
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
			var cname = $('#opt1_cname').val();
			if(cname != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/section/save",
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
				
			}else{
				showError("中文名称和字段名称为必填项，请填写！", '');
				//bootbox.alert("中文名称和字段名称为必填项，请填写！","");
			}
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
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">数据项</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt2FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_itemId\">数据项编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"itemId\" id=\"opt2_itemId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_entityId\">实体编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"entityId\" id=\"opt2_entityId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_designType\">数据项设计类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_designType\" name=\"designType\" class=\"form-control\">");
	    	for(var i=0;i<itemTypeParaDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+itemTypeParaDS[i].parameterValue+">"+itemTypeParaDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_cname\">中文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"opt2_cname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_ename\">英文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"opt2_ename\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_fieldName\">字段名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"fieldName\" id=\"opt2_fieldName\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_htmlId\">页面ID</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"htmlId\" id=\"opt2_htmlId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_pcId\">数据映射条件</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_pcId\" name=\"pcId\" class=\"form-control\">");
	    	for(var i=0;i<pcDS.length;i++)
	    	{
	    		if(i == 0){
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+pcDS[i].parameterValue+">"+pcDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_dataType\">数据类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_dataType\" name=\"dataType\" class=\"form-control\">");
	    	for(var i=0;i<idtypeParaDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+idtypeParaDS[i].parameterValue+">"+idtypeParaDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_inputType\">输入类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_inputType\" name=\"inputType\" class=\"form-control\">");
	    	for(var i=0;i<inputtypeParaDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+inputtypeParaDS[i].parameterValue+">"+inputtypeParaDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_optionsGroup\">选项参数类别</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt2_optionsGroup\" name=\"optionsGroup\" class=\"form-control\">");
	    	for(var i=0;i<paraDS.length;i++)
	    	{
	    		if(i == 0)
	    		{
	    			htmlInfo.append("<option value=></option>");
	    		}
	    		htmlInfo.append("<option value="+paraDS[i].parameterValue+">"+paraDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_unit\">计量单位</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"unit\" id=\"opt2_unit\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_inputWidth\">输入宽度</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"inputWidth\" id=\"opt2_inputWidth\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt2_isEmpty\">允许为空</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt1_isEmpty\" name=\"bean.isEmpty\" class=\"form-control\">");
	    	for(var i=0;i<isEmptyDS.length;i++)
	    	{
	    		htmlInfo.append("<option value="+isEmptyDS[i].parameterValue+">"+isEmptyDS[i].parameterName+"</option>");
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
			var cname = $('#opt2_cname').val();
			var fieldName = $('#opt2_fieldName').val();
			if(cname!="" && fieldName!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/item/save",
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
				
			}else{
				showError("中文名称和字段名称为必填项，请填写！", '');
			}
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
		    $("#"+opt3ModalId).remove();
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt3ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt3DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt3Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">产品参数</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt3FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"id\">参数编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"id\" id=\"opt3_id\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"appId\">产品编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt3_appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupName\">类别名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"groupName\" id=\"opt3_groupName\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"groupDescr\">类别描述</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"groupDescr\" id=\"opt3_groupDescr\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">参数名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"opt3_name\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cdesc\">参数值</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"value\" id=\"opt3_value\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"edesc\">参数描述</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"descr\" id=\"opt3_descr\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt3_seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt3Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt3Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function init_form_action_opt3()
	{
		$('#'+opt3Object+'_submit_btn').on("click",function(){
			var cname = $('#name').val();
			var value = $('#value').val();
			if(cname!="" && value!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/appPara/save",
					data:$("#"+opt3FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt3FormId)[0].reset();
				    	$("#"+opt3ModalId).modal('hide');
				    	$("#"+opt3TableId).bootstrapTable('refresh');
				    	curscvarRowData = "";
				    }
				});
				
			}else{
				showError("参数名称和参数值为必填项，请填写！", '');
				//bootbox.alert("中文名称和英文名称为必填项，请填写！","");
			}
		});
		
		$('#'+opt3Object+'_cancel_btn').on("click",function(){
			$("#"+opt3FormId)[0].reset();
		});
		$('#'+opt3Object+'_close_btn').on("click",function(){
			$("#"+opt3FormId)[0].reset();
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
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">产品参数</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt4FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_sectionId\">区段编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"sectionId\" id=\"opt4_sectionId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_itemId\">数据项</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt4_itemId\" name=\"itemId\" multiple class=\"selectpicker show-tick form-control\">");
			
			for(var i=0;i<itemDS.length;i++)
	    	{    
				htmlInfo.append("<option value="+itemDS[i].parameterValue+">"+itemDS[i].parameterName+"</option>");
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
			if(sectionId != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/section/saveItemSection",
					data: $("#"+opt4FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt4FormId)[0].reset();
				    	$("#"+opt4ModalId).modal('hide');
				    	$("#"+opt4TableId).bootstrapTable('refresh');
				    	curscvarRowData = "";
				    }
				});
			}else{
				showError("选择数据项", '');
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
	//初始化表单
	function init_modal_opt5()
	{
		    $("#"+opt5ModalId).remove();
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt5ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt5DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt5Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">产品-评分卡</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt5FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_appscId\">关联id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appscId\" id=\"opt5_appscId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_appId\">产品编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt5_appId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_scoreId\">评分卡</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt5_scoreId\" name=\"scoreId\" class=\"form-control\">");
	    	for(var i=0;i<appScoreDS.length;i++)
	    	{
	    		if(i == 0)
	        	{
	        		htmlInfo.append("<option value=></option>");
	        	}
	    		htmlInfo.append("<option value="+appScoreDS[i].parameterValue+">"+appScoreDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");

			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_seqno\">排列序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt5_seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt5Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt5Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    $(".initmodal").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function init_form_action_opt5()
	{
		$('#'+opt5Object+'_submit_btn').on("click",function(){
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/appScore/save",
					data:$("#"+opt5FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt5FormId)[0].reset();
				    	$("#"+opt5ModalId).modal('hide');
				    	$("#"+opt5TableId).bootstrapTable('refresh');
				    }
				});
		});
		
		$('#'+opt5Object+'_cancel_btn').on("click",function(){
			$("#"+opt5FormId)[0].reset();
		});
		$('#'+opt5Object+'_close_btn').on("click",function(){
			$("#"+opt5FormId)[0].reset();
		});
	}
	
	//初始化表单
	function init_modal_opt6()
	{
		    $("#"+opt6ModalId).remove();
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\""+opt6ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt6DialogId+"\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-header\">");
			htmlInfo.append("<button type=\"button\" id=\""+opt6Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
			htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">评分变量</h4>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt6FormId+"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_itemscvarId\">变量关联id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"itemscvarId\" id=\"opt6_itemscvarId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_scoreId\">评分卡id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"scoreId\" id=\"opt6_scoreId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_appscId\">评分卡关联id</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"appscId\" id=\"opt6_appscId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_itemId\">数据项</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt6_itemId\" name=\"itemId\" class=\"form-control\">");
	    	for(var i=0;i<item1DS.length;i++)
	    	{
	    		if(i == 0)
	        	{
	        		htmlInfo.append("<option value=></option>");
	        	}
	    		htmlInfo.append("<option value="+item1DS[i].parameterValue+">"+"\t"+item1DS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_scvarId\">评分变量</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"opt6_scvarId\" name=\"scvarId\" class=\"form-control\">");
	    	for(var i=0;i<scvarDS.length;i++)
	    	{
	    		if(i == 0)
	        	{
	        		htmlInfo.append("<option value=></option>");
	        	}
	    		htmlInfo.append("<option value="+scvarDS[i].parameterValue+">"+scvarDS[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
//			htmlInfo.append("<div class=\"form-group\">");
//			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt6_pdId\">决策条件</label>");
//			htmlInfo.append("<div class=\"col-sm-8\">");
//			htmlInfo.append("<select id=\"opt6_pcId\" name=\"pcId\" class=\"form-control\">");
//	    	for(var i=0;i<pcDS.length;i++)
//	    	{
//	    		if(i == 0)
//	        	{
//	        		htmlInfo.append("<option value=></option>");
//	        	}
//	    		htmlInfo.append("<option value="+pcDS[i].parameterValue+">"+pcDS[i].parameterName+"</option>");
//	    	}
//			htmlInfo.append("</select>");
//			htmlInfo.append("</div>");
//			htmlInfo.append("</div>");
			
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
		    $(".initmodal").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function init_form_action_opt6()
	{
		$('#'+opt6Object+'_submit_btn').on("click",function(){
			var itemId=$('#opt6_itemId').val();
			if(itemId!=null){
				var item=itemId.substring(0,1);
				if(item == "I"){
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "/itemScvar/save",
						data:$("#"+opt6FormId).serialize()+"&appId="+curAppCode, //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt6FormId)[0].reset();
					    	$("#"+opt6ModalId).modal('hide');
					    	$("#"+opt6TableId).bootstrapTable('refresh');
					    }
					});
				}else{
					showError("请选择数据项", '');
				}
			}else{
				showError("数据项为空，请先添加数据项", '');
			}
			
		});
		
		$('#'+opt6Object+'_cancel_btn').on("click",function(){
			$("#"+opt6FormId)[0].reset();
		});
		$('#'+opt6Object+'_close_btn').on("click",function(){
			$("#"+opt6FormId)[0].reset();
		});
	}
	
	//初始化表单7
	function init_modal_opt7()
	{
		$("#"+opt7ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt7ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt7DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt7Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">功能服务</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt7FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_srvunId\">服务ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"srvunId\" id=\"opt7_srvunId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_appId\">应用ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt7_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_cname\">中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"opt7_cname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_ename\">英文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"opt7_ename\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_servifaId\">通用服务</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt7_servifaId\" name=\"servifaId\" class=\"form-control\">");
    	for(var i=0;i<servifaDS.length;i++)
    	{
    		if(i == 0)
			{
				htmlInfo.append("<option value=></option>");
			}
    		htmlInfo.append("<option value="+servifaDS[i].parameterValue+">"+servifaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_servimplId\">服务实现</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt7_servimplId\" name=\"servimplId\" class=\"form-control\">");
    	for(var i=0;i<servimplDS.length;i++)
    	{
    		if(i == 0)
			{
				htmlInfo.append("<option value=></option>");
			}
    		htmlInfo.append("<option value="+servimplDS[i].parameterValue+">"+servimplDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_type\">服务类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt7_type\" name=\"type\" class=\"form-control\">");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_tardataId\">目标数据</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt7_tardataId\" name=\"tardataId\" class=\"form-control\">");
		////
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt7_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt7_seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt7Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt7Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
//	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}	
	function schange7(){
		$("#opt7_type").change(function(){
			var type=$("#opt7_type").val();
			$("#opt7_tardataId").empty();
			var htmlInfo = new StringBuffer();
			if(type == 1){
				$("#opt7_tardataId").empty();
			}
			if(type == 50){
		    	for(var i=0;i<scoreDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+scoreDS[i].parameterValue+">"+scoreDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt7_tardataId").append(htmlInfo.toString());
			}
			if(type == 80){
				for(var i=0;i<decisionStrategyDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+decisionStrategyDS[i].parameterValue+">"+decisionStrategyDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt7_tardataId").append(htmlInfo.toString());
			}
			typeService = type;
		})
	}
	//提交表单7
	function init_form_action_opt7()
	{
		$('#'+opt7Object+'_submit_btn').on("click",function(){
			var tardata=$("#opt7_tardataId").val();
			var type=$("#opt7_type").val();
			var cname=$("#opt7_cname").val();
			if(cname == null || cname == ""){
				bootbox.alert("中文名称不能为空！","");
			}else if(tardata == null || tardata == ""){
				bootbox.alert("目标数据不能为空！","");
			}else{
				if(type == 50){
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "/srvunit/save",
						data: $("#"+opt7FormId).serialize(), //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt7FormId)[0].reset();
					    	$("#"+opt7ModalId).modal('hide');
					    	$("#"+opt7TableId).bootstrapTable('refresh');
					    }
					});
				}else if(type == 80){
					var tardataId=tardata.substring(0,1);
					if(tardataId != "D"){
						bootbox.alert("请重新选择目标数据！","");
					}else{
						$.ajax({
							type: "POST",
							dataType: "json",
							url: "/srvunit/save",
							data: $("#"+opt7FormId).serialize(), //formid
							error: function(request) {
								//alert("Connection error");
							},
							success: function(data) {
								bootbox.alert(data.msg,"");
								$("#"+opt7FormId)[0].reset();
								$("#"+opt7ModalId).modal('hide');
								$("#"+opt7TableId).bootstrapTable('refresh');
							}
						});
					}
				}
			}
		});
		
		$('#'+opt7Object+'_cancel_btn').on("click",function(){
			$("#"+opt7FormId)[0].reset();
		});
		$('#'+opt7Object+'_close_btn').on("click",function(){
			$("#"+opt7FormId)[0].reset();
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
