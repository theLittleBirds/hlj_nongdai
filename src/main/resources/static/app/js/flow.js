$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var actionInitBody = false;		//主数据操作按钮初始化标志
	var actionInitOpt0 = false;		
	var actionInitOpt1 = false;		
	var actionInitOpt2 = false;		
	var actionInitOpt3 = false;		
	var actionInitOpt4 = false;		
	var actionInitOpt5 = false;		
	var actionInitOpt6 = false;		
	var actionInitOpt7 = false;		
	var actionInitOpt81 = false;		
	var actionInitOpt82 = false;		
	
	var curAppCode;					//当前选中的主数据行的应用编号
	var curNodeId;
	var curFlowEntranceId           //当前选中的主数据行的流程入口编号
	var dsFlowId;					//当前节点数据集里的流程编号
	var dsFInsId;					//当前角色数据集里的金融机构编号
	var curRowBody;					//当前选中的主数据行
	var curRowOpt0;					//当前选中的选项0数据行
	var curRowOpt1;					//当前选中的选项一数据行
	var curRowOpt2;	
	var curRowOpt3;					
	var curRowOpt4;					
	var curRowOpt5;	
	var curRowOpt6;					
	var curRowOpt7;	
	var curRowOpt81;
	var curRowOpt82;
	
	var flowCode_itemDS;			//数据项数据集对应流程编号
	var flowCode_sectionDS;			//数据区域数据集对应流程编号

	//左边金融机构+流程的四个数据集参数
	var totalSyncDS4Body = 17;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数
	
    //右边程序事件数据集参数
    var countSyncDS4Opt2 = 0;			//同步选项二数据集计数器，当前成功获取个数
    var totalSyncDS4Opt2 = 7;			//同步选项二数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
    
    var countSyncDS4Opt3 = 0;			
    var totalSyncDS4Opt3 = 3;	
    
    var countSyncDS4Opt4 = 0;			
    var totalSyncDS4Opt4 = 1;	

	var totalSyncDS4YxData = 1;			//同步已选数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS4YxData = 0;			//同步已选数据集计数器，当前成功获取个数
	
	var finsDS;
	var appCategoryDS;
	var appStatusDS;
	var delParaDS;
	var typeDS;
	var srvunIdDS;
	var startNodeDS;
	var entranceDS;
	var directionDS;
	var flowTypeDS;
	var flowCategoryDS;
	var flowStatusDS;
	var nodeTypeDS;
	var approverTypeDS;
	var approverCountDS;
	var approveModeDS;
	var nodeDS;
	var roleDS;
	var scoreDS;
	var dataSectionDS;
	var nodeActionDS;
	var comYesNoDS;
	var itemDS;
	var sectionDS;
	var neventTypeDS;
	var startTimeDS;
	var decisionStrategyDS;
	var neventRuntimeDS;
	var orgNameDS;
	var servifaDS;
	var servimplDS;
	var nodeStatusDS;
	var nodeSubStatusDS;
	var nodeNeventDS;
	var neventValue;
	var resultDS;
	var resultDS1;
	var objectTypeDS;
	var controlTypeDS;
	var baseDivId = "base_div";	
	var rightContent_model = "rightContent_model";
	
	//流程管理
	var bodyObject = "flow";
	var menuId = bodyObject + "_id";
	var bodyDivId = bodyObject + "_div";
	var bodyTableId = bodyObject + "_table";
	var bodyModalId = bodyObject + "_modal";
	var bodyDialogId = bodyObject + "_dialog";
	var bodyFormId = bodyObject + "_form";
	var bodyDelAction = "/application/delete";
	var yxRole = ""; 				//记录已选角色
	
	//右上
	//选项页0
	var opt0Object = "node";
	var opt0DivId = opt0Object + "_div";
	var opt0TableId = opt0Object + "_table";
	var opt0ModalId = opt0Object + "_modal";
	var opt0DialogId = opt0Object + "_dialog";
	var opt0FormId = opt0Object + "_form";
	var opt0DelAction = "/fleNode/delete";
	
	//选项页一
	var opt1Object = "section";
	var opt1DivId = opt1Object + "_div";
	var opt1TableId = opt1Object + "_table";
	var opt1ModalId = opt1Object + "_modal";
	var opt1DialogId = opt1Object + "_dialog";
	var opt1FormId = opt1Object + "_form";
	var opt1DelAction = "/flowEntrance/delEntrance";
	
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
	var opt4Object = "event";
	var opt4DivId = opt4Object + "_div";
	var opt4TableId = opt4Object + "_table";
	var opt4ModalId = opt4Object + "_modal";
	var opt4DialogId = opt4Object + "_dialog";
	var opt4FormId = opt4Object + "_form";
	var opt4DelAction = "/flowNevent/delete";
	
	//选项页五
	var opt5Object = "item1";
	var opt5DivId = opt5Object + "_div";
	var opt5TableId = opt5Object + "_table";
	var opt5ModalId = opt5Object + "_modal";
	var opt5DialogId = opt5Object + "_dialog";
	var opt5FormId = opt5Object + "_form";
	var opt5DelAction = "/flowDirection/delDirections";
	
	//选项页六
	var opt6Object = "shenpi";
	var opt6DivId = opt6Object + "_div";
	var opt6TableId = opt6Object + "_table";
	var opt6ModalId = opt6Object + "_modal";
	var opt6DialogId = opt6Object + "_dialog";
	var opt6FormId = opt6Object + "_form";
	var opt6DelAction = "/appPara/delete";
	
	//选项页七
	var opt7Object = "status";
	var opt7DivId = opt7Object + "_div";
	var opt7TableId = opt7Object + "_table";
	var opt7ModalId = opt7Object + "_modal";
	var opt7DialogId = opt7Object + "_dialog";
	var opt7FormId = opt7Object + "_form";
	var opt7DelAction = "/section/delItemSection";
	
	//选项页八
	var opt81Object = "data";
	var opt82Object = "data2"
		
	var opt8DivId = "data_div";
	
	var opt81DivId = opt81Object + "1_div";
	var opt82DivId = opt82Object + "2_div";
	
	var opt81TableId = opt81Object + "1_table";
	var opt82TableId = opt82Object + "2_table";
	
	var opt81ModalId = opt81Object + "_modal";
	var opt81DialogId = opt81Object + "_dialog";
	var opt81FormId = opt81Object + "_form";
	var opt82ModalId = opt82Object + "_modal";
	var opt82DialogId = opt82Object + "_dialog";
	var opt82FormId = opt82Object + "_form";
	
	var opt81DelAction = "/data/delData";
	var opt82DelAction = "/data/delData";
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/

	//金融机构+流程数据集
	function loadSyncDS4Body()
	{
		//事件类型
		if(neventTypeDS == undefined)
		{
			var appcatParaGrpName = "NEVENT_TYPE";			
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	neventTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//开始时间
		if(startTimeDS == undefined)
		{
			var appcatParaGrpName = "NEVENT_STARTTIME";			
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	startTimeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//对象类型
		if(objectTypeDS == undefined)
		{
			var appcatParaGrpName = "DATAUICL_TYPE";			
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	objectTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//权限类型
		if(controlTypeDS == undefined)
		{
			var appcatParaGrpName = "UICTL_TYPE";			//应用类别
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":appcatParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	controlTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//服务结果
		 if(resultDS1 == undefined){
				$.ajax({
					type: "POST",
					//dataType: "json",
					url: "/flowNevent/getResult",
//					data:{"resultId":curRowOpt5.resultId},
				    error: function(request) {					
				    },
				    success: function(data) {
				    	resultDS1 = eval(data);
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
		//流向类型
		if(typeDS == undefined)
		{
			var flowDirectionParaGrpName = "DIRECTION_TYPE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":flowDirectionParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	typeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		//节点类型名称-值
		if(nodeTypeDS == undefined)
		{
			var paraGrpName = "NODE_TYPE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":paraGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	nodeTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//审批实体类型名称-值
		if(approverTypeDS == undefined)
		{
			var paraGrpName = "APPROVER_TYPE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":paraGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	approverTypeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//审批实体数量名称-值
		if(approverCountDS == undefined)
		{
			var paraGrpName = "APPROVER_COUNT";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":paraGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	approverCountDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
		
		//审批模式-值
		if(approveModeDS == undefined)
		{
			var paraGrpName = "APPROVE_MODE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":paraGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	approveModeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
        //节点操作名称-值
        if(nodeActionDS == undefined)
        {
            var paraGrpName = "NODE_ACTION";
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/para/paralist",
                data:{"paraGroupName":paraGrpName},
                error: function(request) {
                },
                success: function(data) {
                    nodeActionDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_body();
                }
            });
        }
        //通用是否名称-值
        if(comYesNoDS == undefined)
        {
            var paraGrpName = "COMMON_YESNO";
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/para/paralist",
                data:{"paraGroupName":paraGrpName},
                error: function(request) {
                },
                success: function(data) {
                    comYesNoDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_body();
                }
            });
        }
        
      //节点事件执行时间名称-值
		if(neventRuntimeDS == undefined)
		{
			var paraGrpName = "NEVENT_RUNTIME";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":paraGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	neventRuntimeDS = eval(data);
			    	countSyncDS4Body ++;
			    	show_view_body();
			    }
			});
		}
//		//通用服务
//		if(servifaDS == null || servifaDS == undefined)
//		{
//			$.ajax({
//				type: "POST",
//				url:"/serviceifa/sifaDS",
//			    error: function(request) {					
//			    },
//			    success: function(data) {
//			    	servifaDS = eval(data);
//			    	countSyncDS4Body ++;
//			    	show_view_body();
//			    }
//			});
//		}
//		//服务实现
//		 if(servimplDS == null || servimplDS == undefined)
//		 {
//		 	$.ajax({
//		 		type: "POST",
//		 		url: "/serviceimpl/serimplDS",
//		 	    error: function(request) {
//		 	    },
//		 	    success: function(data) {
//		 	    	servimplDS = eval(data);
//		 	    	countSyncDS4Body ++;
//		 	    	show_view_body();
//		 	    }
//		 	});
//		 }
	}
	function loadSyncDS4Opt11()
	{
		//当前应用下的功能服务
		if(srvunIdDS == undefined)
		{
			$.ajax({
				type: "POST",
				url: "/srvunit/getSrvunitByAppId",
				data:{"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	srvunIdDS = eval(data);
//			    	countSyncDS4Opt2 ++;
//			    	show_view_opt1();
			    }
			});
		}
	}
	
	function loadSyncDS4Opt2()
	{
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
			    	countSyncDS4Opt2 ++;
			    	show_view_opt1();
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
			    	countSyncDS4Opt2 ++;
			    	show_view_opt1();
			    }
			});
		}
		//起始节点
		if(startNodeDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/flowDirection/getStartNodeDSByAppId",
				data:{"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	startNodeDS = eval(data);
			    	countSyncDS4Opt2 ++;
			    	show_view_opt1();
			    }
			});
		}
		//流程入口
        if(entranceDS == undefined)
        {
            var paraGrpName = "2";
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/dePoCase/getDecisionByGroupNameAndAppId",
                data:{"paraGroupName":paraGrpName,"appId":curAppCode},
                error: function(request) {
                },
                success: function(data) {
                	entranceDS = eval(data);
                    countSyncDS4Opt2 ++;
                    show_view_opt1();
                }
            });
        }
        //流程流向
        if(directionDS == undefined)
        {
            var paraGrpName = "3";
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/dePoCase/getDecisionByGroupNameAndAppId",
                data:{"paraGroupName":paraGrpName,"appId":curAppCode},
                error: function(request) {
                },
                success: function(data) {
                	directionDS = eval(data);
                    countSyncDS4Opt2 ++;
                    show_view_opt1();
                }
            });
        }
        //节点状态
		if(nodeStatusDS == undefined)
		{
			var paraGrpName = "NODE_STATUS";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/appPara/getNodeStatusDS",
				data:{"appParaGroupName":paraGrpName,"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	nodeStatusDS = eval(data);
			    	countSyncDS4Opt2 ++;
                    show_view_opt1();
			    }
			});
		}
		//初始，打开，保存状态
		if(nodeSubStatusDS == undefined)
		{
			var paraGrpName = "NODE_SUBSTATUS";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/appPara/getNodeStatusDS",
				data:{"appParaGroupName":paraGrpName,"appId":curAppCode},
			    error: function(request) {	
			    },
			    success: function(data) {
			    	nodeSubStatusDS = eval(data);
			    	countSyncDS4Opt2 ++;
                    show_view_opt1();
			    }
			});
		}
	}
	function loadSyncDS4Opt3()
	{
		 //节点事件
        if(nodeNeventDS == undefined){
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/flowNevent/getNeventByNodeId",
				data:{"nodeId":curNodeId},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	nodeNeventDS = eval(data);
			    	countSyncDS4Opt3 ++;
			    	show_view_opt41();
			    }
			});
		}
        
      //页面区段
        if(sectionDS == undefined){
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/section/getSectionByAppId",
				data:{"appId":curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	sectionDS = eval(data);
			    	countSyncDS4Opt3 ++;
			    	show_view_opt41();
			    }
			});
		}
        
      //数据项
        if(itemDS == undefined){
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/item/getItemDS1",
				data:{"appId":curAppCode},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	itemDS = eval(data);
			    	countSyncDS4Opt3 ++;
			    	show_view_opt41();
			    }
			});
		}
        
	}
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#flow_id").on("click", function(){
		
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
		curRowOpt3 = "";
    	curRowOpt2 = "";
    	curRowOpt1 = "";
    	curRowOpt0 = "";
		actionInitBody = false;
		actionInitOpt0 = false;
		actionInitOpt1 = false;
		actionInitOpt2 = false;
		actionInitOpt3 = false;
		actionInitOpt4 = false;
		actionInitOpt5 = false;
		actionInitOpt6 = false;
		actionInitOpt7 = false;
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

//		init_tool_action_body();					//初始化主数据工具栏操作
		
		$("#"+bodyTableId).bootstrapTable({
			method:'post',
			url: "/application/selectApplication",
			dataType: "json",
            striped:true,
            height: $(window).height()-180,
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
                	field:'category',
                	title:'类型'
                },{
                	field:'status',
                	title:'状态'
                },{
                	field:'seqno',
                	title:'序号'
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
            	if(row.appId != undefined){
            		curAppCode = "";
            		curAppCode = curRowBody.appId;
            		decisionStrategyDS=undefined;
            		scoreDS=undefined;
	            	startNodeDS=undefined;
	            	entranceDS=undefined;
	            	directionDS=undefined;
	            	nodeNeventDS=undefined;
	            	nodeStatusDS=undefined;
	            	nodeSubStatusDS=undefined;
	            	srvunIdDS=undefined;
	            	countSyncDS4Opt2=0;
	             	curRowOpt0 = "";
	            	curRowOpt1 = "";
	            	loadSyncDS4Opt11();
	                show_view_opt();  //刷新选项页数据
            	}
            	
            	$("#"+bodyTableId).each(function() { 
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
            	bootbox.alert("数据加载失败！","");
            }
		});
		
	}
	
	function show_view_opt()
	{	
		loadSyncDS4Opt2();
		show_view_opt0();
		show_view_opt1();
//		show_view_opt2();
//		show_view_opt3();
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
			url: "/fleNode/selectByAppId",
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
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'nodeId',
					title: '节点编号',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '节点名称',
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
					field: 'approverCode',
					title: '审批角色',
					align: 'left',
					width: '',
					valign: 'middle',
//					formatter: aprroleCodeFormatter,
					visible: false
				},
				{
					field: 'type',
					title: '类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: nodeTypeCodeFormatter
				},
				{
					field: 'approveMode',
					title: '流转方式',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: aprmodeCodeFormatter,
//					visible: false
				},
				{
					field: 'approverType',
					title: '实体类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: aprtypeCodeFormatter,
//					visible: false
				},
				{
					field: 'approverCount',
					title: '实体数量',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: aprcountCodeFormatter,
//					visible: false
				},
				{
					field: 'nodeStatus',
					title: '节点状态',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: nodeStatusFormatter,
					visible: false
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
            	 	curNodeId = curRowOpt0.nodeId;
            	 	nodeNeventDS=undefined;
            	 	sectionDS=undefined;
            	 	itemDS=undefined;
            	 	countSyncDS4Opt3=0;
            	 	loadSyncDS4Opt3();
                	show_view_opt41();  //刷新选项页数据
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
	
	//选项数据显示-右上2
	function show_view_opt1()
	{
		if(countSyncDS4Opt2 < totalSyncDS4Opt2){
			return ;
		}
		init_tool_action_opt1(); 
		
		init_table_opt1();
		
		$('#'+opt1Object+'_toolbar').show();
		
		$("#"+opt1TableId).bootstrapTable({
			url: "/flowEntrance/selectByAppId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-250,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [100000,200000],
			pageSize:100000,
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
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'entranceId',
					title: '入口id',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'appId',
					title: '应用id',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'startNodeId',
					title: '起始节点',
					align: 'left',
					width: '250px',
					valign: 'middle',
					formatter: startNodeFormatter
				},
				{
					field: 'pcId',
					title: '决策条件',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:entranceFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle',
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            		curRowOpt1 = row;
            		curFlowEntranceId=row.entranceId;
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
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+opt1TableId).bootstrapTable('refresh');
		
	}
	
	//选项数据显示-右下
	function show_view_opt41()
	{	
		if(countSyncDS4Opt3 < totalSyncDS4Opt3){
			return ;
		}
		//加载选项卡和操作选项
		init_layout1();
		
		show_view_opt6();
		show_view_opt7();
		show_view_opt4();
		show_view_opt5();
		show_view_opt8();
	}
	//右下选项卡
	function show_view_opt4()
	{
		init_table_opt4();
		
		init_tool_action_opt4(); 
		
		$('#'+opt4Object+'_toolbar').show();
		
		$("#"+opt4TableId).bootstrapTable({
			url: "/flowNevent/selectByNodeId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-540,
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
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
				},
				{
					field: 'runtime',
					title: '运行时间',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: neventRuntimeCodeFormatter,
				},
				{
					field: 'starttime',
					title: '运行时间',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: startTimeDSFormatter,
				},
				{
					field: 'srvunId',
					title: '功能服务',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: srvunDSFormatter,
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
            	if(row._level != 0){
            		
            	}
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
	//右下选项卡
	function show_view_opt5()
	{
		//初始化工具栏
		init_tool_action_opt5(); 
		
		init_table_opt5();
		
		$('#'+opt5Object+'_toolbar').show();
		
		$("#"+opt5TableId).bootstrapTable({
			url: "/flowDirection/selectByNodeId",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-540,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [100000,200000],
			pageSize:100000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt5QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#'+opt5Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt5ResponseHandler,
			columns: [
				{
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'directionId',
					title: '流向id',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'nodeId',
					title: '节点id',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'type',
					title: '流向类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: directionTypeFormatter
				},
				{
					field: 'pcId',
					title: '决策条件/节点事件',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:directionFormatter
				},
				{
					field: 'toNodeIds',
					title: '目标节点',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:startNodeFormatter
				},
				{
					field: 'resultId',
					title: '服务结果',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter:resultDSFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'left',
					width: '',
					valign: 'middle'
				},

				
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt5 = row;
            	if(row._level != 0){
//            	 	curNodeId = row.nodeId;
            	}
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
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$("#"+opt5TableId).bootstrapTable('refresh');
	}
	//右下选项卡1
	function show_view_opt6(){
		init_table_opt6();
		init_shenpi_table_opt6();
		$('#opt0_approverType').val(curRowOpt0.approverType);
		$('#opt0_approverCount').val(curRowOpt0.approverCount);
		$('#opt0_approveMode').val(curRowOpt0.approveMode);
		submit_form_action_opt6();
	}
	//右下选项卡2
	function show_view_opt7(){
		init_table_opt7();
		init_status_table_opt7();
		$('#opt0_nodeStatus').val(curRowOpt0.nodeStatus);
		$('#opt0_initStatus').val(curRowOpt0.initStatus);
		$('#opt0_openStatus').val(curRowOpt0.openStatus);
		$('#opt0_saveStatus').val(curRowOpt0.saveStatus);
		submit_form_action_opt7();
	}
	//选项卡1所需数据
	function init_shenpi_table_opt6(){
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"table-responsive\">");
		htmlInfo.append("<table class=\"table\" id=\"test\">");
		
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt6FormId+"\" role=\"form\">");
		htmlInfo.append("<tbody>");
		htmlInfo.append("<tr>");
		htmlInfo.append("<td style=\"padding: 5px;width:180px;\">审批实体类型</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_approverType\" name=\"approverType\" style=\"width: 100px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<approverTypeDS.length;i++)
    	{
    		htmlInfo.append("<option  value="+approverTypeDS[i].parameterValue+">"+approverTypeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">审批实体数量</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_approverCount\" name=\"approverCount\" style=\"width: 100px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<approverCountDS.length;i++)
    	{
    		htmlInfo.append("<option value="+approverCountDS[i].parameterValue+">"+approverCountDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">审批模式</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_approveMode\" name=\"approveMode\" style=\"width: 100px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<approveModeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+approveModeDS[i].parameterValue+">"+approveModeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		htmlInfo.append("</form>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">审批实体</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<button id=\"btn_sqrole\" style=\"width: 100px;height:33px;background:#fff;border:1px solid #ccc;\">实体选择</button>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		htmlInfo.append("</tbody>");		
		
		htmlInfo.append("</table>");	
		htmlInfo.append("</div>");			
		htmlInfo.append("<button id=\""+opt6Object+"_submit_btn\" style=\"margin-left: 13%;width: 50px;height:33px;\">提交</button>");
		
		$("#"+opt6DivId).append(htmlInfo.toString());
		
		//授权角色
		$("#btn_sqrole").on("click",function(){
			if(curRowOpt0!="")
			{
//				if($('#roledialogid').length == 0)
//				{
					init_roletable();//初始化
					submit_roleform();//提交操作
//				}
				$('#roleModal').modal('show');
				findYxRole();//查找已经选择的数据用于授权角色
				getRoles();  //得到角色数据
			}
			else{
				bootbox.alert("请选择要授权角色的实体！","");
			}
		});
	}
	//选项卡2所需数据
	function init_status_table_opt7(){
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"table-responsive\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt7FormId+"\" role=\"form\">");
		htmlInfo.append("<table class=\"table\">");
		
		htmlInfo.append("<tbody>");

		htmlInfo.append("<tr>");
		htmlInfo.append("<td style=\"padding: 5px; width:150px\">节点状态</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_nodeStatus\" name=\"nodeStatus\" style=\"width: 140px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<nodeStatusDS.length;i++)
    	{
    		 if(i == 0)
    		 {
    		 	htmlInfo.append("<option value=></option>");
    		 }
    		htmlInfo.append("<option value="+nodeStatusDS[i].parameterValue+">"+nodeStatusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">初始状态</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_initStatus\" name=\"ininStatus\" style=\"width: 140px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<nodeSubStatusDS.length;i++)
    	{
    		 if(i == 0)
    		 {
    		 	htmlInfo.append("<option value=></option>");
    		 }
    		htmlInfo.append("<option value="+nodeSubStatusDS[i].parameterValue+">"+nodeSubStatusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">打开状态</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_openStatus\" name=\"openStatus\" style=\"width: 140px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<nodeSubStatusDS.length;i++)
    	{
    		 if(i == 0)
    		 {
    		 	htmlInfo.append("<option value=></option>");
    		 }
    		htmlInfo.append("<option value="+nodeSubStatusDS[i].parameterValue+">"+nodeSubStatusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("<td style=\"padding: 5px;\">保存状态</td>");
		htmlInfo.append("<td style=\"padding: 5px;\">");
		htmlInfo.append("<select id=\"opt0_saveStatus\" name=\"saveStatus\" style=\"width: 140px; border:none;font-size:13px;\" class=\"form-control\">");
    	for(var i=0;i<nodeSubStatusDS.length;i++)
    	{
    		 if(i == 0)
    		 {
    		 	htmlInfo.append("<option value=></option>");
    		 }
    		htmlInfo.append("<option value="+nodeSubStatusDS[i].parameterValue+">"+nodeSubStatusDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</td>");
		htmlInfo.append("</tr>");
		
		htmlInfo.append("</tbody>");		
		htmlInfo.append("</table>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");			
		htmlInfo.append("<button id=\""+opt7Object+"_submit_btn\" style=\"margin-left: 15%;width: 50px;height:33px;\">提交</button>");
		
		$("#"+opt7DivId).append(htmlInfo.toString());
	}

	function show_view_opt8(){
		
		init_table_opt81();
		init_table_opt82();
		$("#"+opt81TableId).bootstrapTable('destroy');
		$("#"+opt82TableId).bootstrapTable('destroy');
		
		//展示左边table
		showleftTable();
		//展示右边table
		showrightTable();
	}
	function showleftTable(){
		$('#'+opt81Object+'_toolbar').show();
		init_tool_action_opt81(); 
		
		$("#"+opt81TableId).bootstrapTable({
			url: "/data/listLeft",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-520,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [3,6,10],
			pageSize:3,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt81QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			showRefresh: true,
//			showToggle: true,
			toolbar:'#'+opt81Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt81ResponseHandler,
			columns: [
				{
					field: 'node_selection',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'objectType',
					title: '对象类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: objectTypeDSFormatter
				},
				{
					field: 'objectId',
					title: '对象',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: sectionDSFormatter
				},
				{
					field: 'controlType',
					title: '权限类型',
					align: 'left',
					width: '',
					valign: 'middle',
					formatter: controlTypeDSFormatter
				}
			],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt81 = row;
            	if(row._level != 0){
//            	 	curNodeId = row.nodeId;
            	}
            	$("#"+opt81TableId).each(function() { 
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
		$("#"+opt81TableId).bootstrapTable('refresh');
	}
	function showrightTable(){
		init_tool_action_opt82(); 
		$('#'+opt82Object+'_toolbar').show();
		
		$("#"+opt82TableId).bootstrapTable({
			url: "/data/listRight",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-530,
//			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [3,15,30],
			pageSize:3,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt82QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			showRefresh: true,
//			showToggle: true,
			toolbar:'#'+opt82Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt82ResponseHandler,
			columns: [
						{
							field: 'node_selection',
							titleTooltip: '全选/全不选',
							checkbox: true,
							align: 'center',
							valign: 'middle'
						},
						{
							field: 'objectType',
							title: '对象类型',
							align: 'left',
							width: '',
							valign: 'middle',
							formatter: objectTypeDSFormatter
						},
						{
							field: 'objectId',
							title: '对象',
							align: 'left',
							width: '',
							valign: 'middle',
							formatter: itemDSFormatter
						},
						{
							field: 'controlType',
							title: '权限类型',
							align: 'left',
							width: '',
							valign: 'middle',
							formatter: controlTypeDSFormatter
						}
					],
			onLoadSuccess:function(){
            },
            onClickRow:function(row){
            	curRowOpt82 = row;
            	if(row._level != 0){
//            	 	curNodeId = row.nodeId;
            	}
            	$("#"+opt82TableId).each(function() { 
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
		$("#"+opt82TableId).bootstrapTable('refresh');
	}
	//table里面显示中文描述---状态值对应为相应的状态
	function nodeTypeCodeFormatter(value) {
    	for(var i=0;i<nodeTypeDS.length;i++)
    	{
    		if(nodeTypeDS[i].parameterValue == value)
    		{
    			return ['<span>'+nodeTypeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//软删除标志
	function isDeleteFormatter(value) {
    	for(var i=0;i<delParaDS.length;i++)
    	{
    		if(delParaDS[i].parameterValue == value)
    		{
    			return ['<span>'+delParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//流向类型
	function directionTypeFormatter(value) {
    	for(var i=0;i<typeDS.length;i++)
    	{
    		if(typeDS[i].parameterValue == value)
    		{
    			return ['<span>'+typeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//审批角色
	function aprroleCodeFormatter(value) {
    	for(var i=0;i<roleDS.length;i++)
    	{
    		if(roleDS[i].parameterValue == value)
    		{
    			return ['<span>'+roleDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//节点状态
	function nodeStatusFormatter(value) {
    	for(var i=0;i<nodeStatusDS.length;i++)
    	{
    		if(nodeStatusDS[i].parameterValue == value)
    		{
    			return ['<span>'+nodeStatusDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//初始，打开，保存状态
	function nodeSubStatusFormatter(value) {
    	for(var i=0;i<nodeSubStatusDS.length;i++)
    	{
    		if(nodeSubStatusDS[i].parameterValue == value)
    		{
    			return ['<span>'+nodeSubStatusDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function nodeActionDSFormatter(value) {
    	for(var i=0;i<nodeActionDS.length;i++)
    	{
    		if(nodeActionDS[i].parameterValue == value)
    		{
    			return ['<span>'+nodeActionDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function nodeCodeFormatter(value) {
		if(value != null)
		{
			var items = value.replace(/\s/g,'').split(/[,]/g);
			var nodeName = [];
	    	for(var i=0;i<nodeDS.length;i++)
	    	{
	    		if($.inArray(nodeDS[i].parameterValue,items) >= 0)
	    		{
	    			nodeName.push(nodeDS[i].parameterName);
		    	}
	    	}
	    	return ['<span>'+nodeName+'</span>']
		}
	}
	//审批模式
	function aprmodeCodeFormatter(value) {
    	for(var i=0;i<approveModeDS.length;i++)
    	{
    		if(approveModeDS[i].parameterValue == value)
    		{
    			return ['<span>'+approveModeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//审批实体类型
	function aprtypeCodeFormatter(value) {
    	for(var i=0;i<approverTypeDS.length;i++)
    	{
    		if(approverTypeDS[i].parameterValue == value)
    		{
    			return ['<span>'+approverTypeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//审批实体数量
	function aprcountCodeFormatter(value) {
    	for(var i=0;i<approverCountDS.length;i++)
    	{
    		if(approverCountDS[i].parameterValue == value)
    		{
    			return ['<span>'+approverCountDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//流程入口
	function entranceFormatter(value) {
    	for(var i=0;i<entranceDS.length;i++)
    	{
    		if(entranceDS[i].parameterValue == value)
    		{
    			return ['<span>'+entranceDS[i].parameterName+'</span>']
	    	}
    	}
	}
	//流程流向
	function directionFormatter(value) {
    	for(var i=0;i<directionDS.length;i++)
    	{
    		if(directionDS[i].parameterValue == value)
    		{
    			return ['<span>'+directionDS[i].parameterName+'</span>']
	    	}
    	}
    	 for(var j=0;j<nodeNeventDS.length;j++)
 		{
 			if(nodeNeventDS[j].parameterValue == value)
 			{
 				return ['<span>'+nodeNeventDS[j].parameterName+'</span>']
 	    	}
 		}
	}
	//服务结果
	function resultDSFormatter(value) {
    	for(var i=0;i<resultDS1.length;i++)
    	{
    		if(resultDS1[i].parameterValue == value)
    		{
    			return ['<span>'+resultDS1[i].parameterName+'</span>']
	    	}
    	}
	}
	function objectTypeDSFormatter(value) {
    	for(var i=0;i<objectTypeDS.length;i++)
    	{
    		if(objectTypeDS[i].parameterValue == value)
    		{
    			return ['<span>'+objectTypeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function controlTypeDSFormatter(value) {
    	for(var i=0;i<controlTypeDS.length;i++)
    	{
    		if(controlTypeDS[i].parameterValue == value)
    		{
    			return ['<span>'+controlTypeDS[i].parameterName+'</span>']
	    	}
    	}
	}

	//起始节点
	function startNodeFormatter(value){
		var value1=value.split(",");
		var htmlInfo=new StringBuffer;
		for(var j=0;j<value1.length;j++){
			for(var i=0;i<startNodeDS.length;i++){
				if(startNodeDS[i].parameterValue == value1[j]){
					htmlInfo.append("<span>"+startNodeDS[i].parameterName+"</span>");
					if(j!=value1.length-1){
						htmlInfo.append(";");
					}
				}
			}
		}
		return [htmlInfo.toString()]
	}
	
	//节点运行时间
	function neventRuntimeCodeFormatter(value) {
		for(var i=0;i<neventRuntimeDS.length;i++)
		{
			if(neventRuntimeDS[i].parameterValue == value)
			{
				return ['<span>'+neventRuntimeDS[i].parameterName+'</span>']
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
	//节点事件sectionDS
	function neventFormatter(value) {
	 for(var i=0;i<nodeNeventDS.length;i++)
		{
			if(nodeNeventDS[i].parameterValue == value)
			{
				return ['<span>'+nodeNeventDS[i].parameterName+'</span>']
	    	}
		}
	}
	//页面区段
	function sectionDSFormatter(value) {
	 for(var i=0;i<sectionDS.length;i++)
		{
			if(sectionDS[i].parameterValue == value)
			{
				return ['<span>'+sectionDS[i].parameterName+'</span>']
	    	}
		}
	}
	//数据项
	function itemDSFormatter(value) {
	 for(var i=0;i<itemDS.length;i++)
		{
			if(itemDS[i].parameterValue == value)
			{
				return ['<span>'+itemDS[i].parameterName+'</span>']
	    	}
		}
	}
	//事件类型
	function neventTypeDSFormatter(value) {
	 for(var i=0;i<neventTypeDS.length;i++)
		{
			if(neventTypeDS[i].parameterValue == value)
			{
				return ['<span>'+neventTypeDS[i].parameterName+'</span>']
	    	}
		}
	}
	//开始时间
	function startTimeDSFormatter(value) {
	 for(var i=0;i<startTimeDS.length;i++)
		{
			if(startTimeDS[i].parameterValue == value)
			{
				return ['<span>'+startTimeDS[i].parameterName+'</span>']
	    	}
		}
	}
	//当前应用下的评分卡
	function scoreDSFormatter(value) {
	 for(var i=0;i<scoreDS.length;i++)
		{
			if(scoreDS[i].parameterValue == value)
			{
				return ['<span>'+scoreDS[i].parameterName+'</span>']
	    	}
		}
	}
	//功能服务
	function srvunDSFormatter(value){
		 for(var i=0;i<srvunIdDS.length;i++)
		{
			if(srvunIdDS[i].parameterValue == value)
			{
				return ['<span>'+srvunIdDS[i].parameterName+'</span>']
		    }
		}
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
	//设置table传递到服务器的参数
	function opt1QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
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
	//设置table传递到服务器的参数
	function opt4QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      nodeId: curNodeId
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
	function opt5QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      nodeId: curNodeId
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
	//设置table传递到服务器的参数
	function opt81QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset/params.limit+1,			//页码
	      nodeId: curNodeId,
	      type:1
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt81ResponseHandler(res)
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
	function opt82QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,				//页面大小
	      offset: params.offset,			//页码
	      nodeId: curNodeId,
	      type:2
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function opt82ResponseHandler(res)
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
			htmlInfo.append("<div class=\"panel panel-default col-sm-5\" style=\"\">");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");		//panel
			
			//panel-rightUp
			htmlInfo.append("<div class=\"panel panel-default col-sm-7\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li class=\"active\"><a href=\"."+opt0DivId+"\" data-toggle=\"tab\">流程节点</a></li>");
			htmlInfo.append("<li><a href=\"."+opt1DivId+"\" data-toggle=\"tab\">流程入口</a></li>");
//			htmlInfo.append("<li><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">test2</a></li>");
//			htmlInfo.append("<li><a href=\"."+opt3DivId+"\" data-toggle=\"tab\">test3</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			
			//opt0 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt0DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt0Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div style=\"overFlow:hidden;\" id=\"" + opt0DivId + "\">");
	        htmlInfo.append("</div>");
	        
			//关联
//	    	htmlInfo.append("<div id=\"" + opt0Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
//			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"" + rightContent_model + "\">");
	        htmlInfo.append("</div>");
	        
//			htmlInfo.append("<div class=\"panel panel-default col-sm-7\" id=\"" + rightContent_model + "\" style=\"\">");
//			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			//opt1 tab
			htmlInfo.append("<div id=\"entity\" class=\"tab-pane fade in " + opt1DivId + "\">");
			htmlInfo.append("<div id=\"" + opt1Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"" + opt1DivId + "\">");
	        htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
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
//		htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"width: 100%;\">");
		htmlInfo.append("<ul id=\"myTab1\" class=\"nav nav-tabs\">");
		htmlInfo.append("<li class=\"active\"><a href=\"."+opt6DivId+"\" data-toggle=\"tab\">审批</a></li>");
//		htmlInfo.append("<li><a href=\"."+opt7DivId+"\" data-toggle=\"tab\">状态</a></li>");
		htmlInfo.append("<li><a href=\"."+opt4DivId+"\" data-toggle=\"tab\">事件</a></li>");
		htmlInfo.append("<li><a href=\"."+opt5DivId+"\" data-toggle=\"tab\">流向</a></li>");
		htmlInfo.append("<li><a href=\"."+opt8DivId+"\" data-toggle=\"tab\">数据权限</a></li>");
		htmlInfo.append("</ul>");
		
		htmlInfo.append("<div id=\"myTabContent1\" class=\"tab-content\">");	
		//opt4 tab
		htmlInfo.append("<div class=\"tab-pane fade in " + opt4DivId + "\">");
		htmlInfo.append("<div id=\"" + opt4Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt4DivId + "\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
		//opt5 tab
		htmlInfo.append("<div class=\"tab-pane fade in " + opt5DivId + "\">");
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
		//opt7tab
		htmlInfo.append("<div class=\"tab-pane fade in " + opt7DivId + "\">");
		htmlInfo.append("<div id=\"" + opt7Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt7DivId + "\">");
        htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		//opt8tab
		htmlInfo.append("<div class=\"tab-pane fade in " + opt8DivId + "\">");
		htmlInfo.append("<div id=\"" + opt81Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt82Object + "_toolbar\" class=\"btn-group\" style=\"display: none;\">");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"" + opt8DivId + "\">");
		
		htmlInfo.append("<div style=\"overFlow:hidden;\" class=\"col-sm-6\" id=\"" + opt81DivId + "\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div style=\"overFlow:hidden;\" class=\"col-sm-6\" id=\"" + opt82DivId + "\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
    	htmlInfo.append("</div>");		//myTabContent
//		htmlInfo.append("</div>");		//panel
		
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
	//初始化流程节点的table
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
	//初始化当前选项页5的table
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
	//初始化当前选项页7的table
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
	//初始化当前选项页8的table1
	function init_table_opt81()
	{
		if($("#"+opt81TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\"  id=\""+opt81TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt81DivId).append(htmlInfo.toString());
		}
	}
	//初始化当前选项页8的table2
	function init_table_opt82()
	{
		if($("#"+opt82TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt82TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt82DivId).append(htmlInfo.toString());
		}
	}

	/*-------------------------------------函数：流程增删改-----------------------------------------*/
	
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
				if(curRowBody != undefined && curRowBody._level == 0){
					if($("#"+bodyDialogId).length == 0)
					{
						init_modal_body();//初始化提交表单
						init_form_action_body();//提交表单操作
					}
					$("#"+bodyFormId)[0].reset();
					$('#finsId').val(curRowBody.finsId);
					$("#"+bodyModalId).modal('show');
				}else{
					bootbox.alert("请选择机构添加","");
				}
				
			});
			
			$('#btn_edit').on("click",function()
			{
				if(curRowBody != undefined && curRowBody._level != 0){
					$.ajax({
				    	   type:'post',
				    	   url: "/application/getAppById",
				    	   data:{"appId":curRowBody.appId},
				    	   success:function(data){
				    		   edit(data);     
				    	   }
				     });
				}else{
					bootbox.alert("请选择数据项修改！","");
				}
			});
			
			$("#btn_del").on("click",function()
			{					
				var selData = $('#'+bodyTableId).bootstrapTable('getSelections');//选中的数据
				if(selData.length == 1 && selData[0]._level == 0){
					bootbox.alert("请选择流程删除！","");
				}else{
					var objCodes = '';
					if(selData.length > 0)
					{
						for(var i=0;i<selData.length;i++)
						{
							objCodes += selData[i].appId + ',';
						}
						objCodes = objCodes.substring(0, objCodes.length-1);
						delMany(bodyDelAction, objCodes, '#'+bodyTableId);
					}
					else
					{
						bootbox.alert("请选择要删除的数据记录！","");
					}
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
			init_modal_body();
			init_form_action_body();
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
			$("#"+bodyModalId).modal('show');
		}
		else{
			bootbox.alert("请选择要修改的数据！","");
		}
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
				if(curRowBody.appId != undefined){
					$('#opt0_actions').selectpicker('val', "");
					if($("#"+opt0DialogId).length == 0)
					{
						init_modal_opt0();//初始化提交表单
						init_form_action_opt0();//提交表单操作
	                }
	                $("#"+opt0FormId)[0].reset();
					$('#opt0_appId').val(curAppCode);
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
						objCodes += selData[i].nodeId + ',';
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
			$('#opt0_nodeId').val(entry.nodeId);
			$('#opt0_appId').val(entry.appId);
			$('#opt0_type').val(entry.type);
			$('#opt0_cname').val(entry.cname);
			$('#opt0_ename').val(entry.ename);
			$('#opt0_approverType').val(entry.approverType);
			$('#opt0_approverCount').val(entry.approverCount);
			$('#opt0_approveMode').val(entry.approverCount);
			if(entry.actions)
			{
				var arr4 = entry.actions.replace(/\s/g,'').split(/[,]/g);
				$('#opt0_actions').selectpicker('val', arr4);
			}
			
//			$('#opt1_traceStep').selectpicker('val', entry.traceStep);
			$('#opt0_memo').val(entry.memo);
			$('#opt0_seqno').val(entry.seqno);
			$('#opt0_isDelete').val(entry.isDelete);
			
			$("#"+opt0ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	/*-------------------------------------函数：选项1数据增删改-----------------------------------------*/
	function init_tool_action_opt1()
	{
		if(!actionInitOpt1)
		{
			$("#"+opt1Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt1Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			
			$("#"+opt1Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt1Object+"_btn_add").on("click",function()
			{
				if(curRowBody.appId != undefined){
					init_modal_opt1();//初始化提交表单
					init_form_action_opt1();//提交表单操作
	                $("#"+opt1FormId)[0].reset();
					$('#opt1_appId').val(curAppCode);
					$("#"+opt1ModalId).modal('show');
				}else{
					bootbox.alert("请选择应用进行添加！","");
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
						objCodes += selData[i].entranceId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					delMany(opt1DelAction, objCodes,'#'+opt1TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});

//			actionInitOpt1 = true;
		}
	}
	//选项1数据编辑操作
	function edit_opt1(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt1();//初始化提交表单
			init_form_action_opt1();//提交表单操作
			$('#opt1_entranceId').val(entry.entranceId);
			$('#opt1_appId').val(entry.appId);
			$('#opt1_pcId').val(entry.pcId);
			$('#opt1_startNodeId').val(entry.startNodeId);
			$('#opt1_seqno').val(entry.seqno);
			
			$("#"+opt1ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	/*-----------------------------------------右下选项卡二---------------------------------------*/
	function init_tool_action_opt5()
	{
		if(!actionInitOpt5)
		{
			$("#"+opt5Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt5Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt5Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt5Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt5Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt5Object+"_btn_add").on("click",function()
			{
					init_modal_opt5();//初始化提交表单
					init_form_action_opt5();//提交表单操作
					schange();
                $("#"+opt5FormId)[0].reset();
                $('#opt5_nodeId').val(curNodeId);
				$("#"+opt5ModalId).modal('show');
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
						objCodes += selData[i].directionId + ',';
					}
					objCodes = objCodes.substring(0, objCodes.length-1);
					delMany(opt5DelAction, objCodes,'#'+opt5TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
			});

//			actionInitOpt5 = true;
		}
	}
	
	
	//数据编辑操作
	function edit_opt5(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt5();//初始化提交表单
			init_form_action_opt5();//提交表单操作
			schange();
			
			if(entry.type == 20){
					//节点事件
					$("#opt5_change1").empty();
					var htmlInfo=new StringBuffer();
					htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_eventDS\">节点事件</label>");
					htmlInfo.append("<div class=\"col-sm-8\">");
					htmlInfo.append("<select id=\"opt5_eventDS\" name=\"pcId\" class=\"form-control\">");
			    	for(var i=0;i<nodeNeventDS.length;i++)
			    	{
			    		if(i == 0)
						{
							htmlInfo.append("<option value=></option>");
						}
			    		htmlInfo.append("<option value="+nodeNeventDS[i].parameterValue+">"+nodeNeventDS[i].parameterName+"</option>");
			    	}
					htmlInfo.append("</select>");
					htmlInfo.append("</div>");
			    	$("#opt5_change1").append(htmlInfo.toString());
			    	$("#opt5_eventDS").val(entry.pcId);
			    	//二级联动
			    	$("#opt5_eventDS").change(function(){

			    		$("#opt5_resultId").empty();
			    		neventValue=$("#opt5_eventDS").val();//获取选择事件的key
			    		resultDS=undefined;
			    		if(resultDS == undefined){
			    			$.ajax({
			    				type: "POST",
			    				url: "/flowNevent/getServiceResultByNeventId",
			    				data:{"neventId":neventValue},
			    			    error: function(request) {					
			    			    },
			    			    success: function(data) {
			    			    	resultDS = eval(data);
			    			    	var htmlInfo1 = new StringBuffer();
			    			    	for(var j=0;j<resultDS.length;j++)
			    			    	{
			    			    		if(j == 0)
			    			    		{
			    			    			htmlInfo1.append("<option value=></option>");
			    			    		}
			    			    		htmlInfo1.append("<option value="+resultDS[j].parameterValue+">"+resultDS[j].parameterName+"</option>");
			    			    	}
			    			    	$("#opt5_resultId").append(htmlInfo1.toString());
			    			    }
			    			});
			            }
			    	
			    	})
			    	//
//		    		$("#opt5_resultId").empty();
		    		neventValue=$("#opt5_eventDS").val();//获取选择事件的key
		    		resultDS=undefined;
		    		if(resultDS == undefined){
		    			$.ajax({
		    				type: "POST",
		    				url: "/flowNevent/getServiceResultByNeventId",
		    				data:{"neventId":neventValue},
		    			    error: function(request) {					
		    			    },
		    			    success: function(data) {
		    			    	resultDS = eval(data);
		    			    	var htmlInfo1 = new StringBuffer();
		    			    	for(var j=0;j<resultDS.length;j++)
		    			    	{
		    			    		if(j == 0)
		    			    		{
		    			    			htmlInfo1.append("<option value=></option>");
		    			    		}
		    			    		htmlInfo1.append("<option value="+resultDS[j].parameterValue+">"+resultDS[j].parameterName+"</option>");
		    			    	}
		    			    	$("#opt5_resultId").append(htmlInfo1.toString());
		    			    	$("#opt5_resultId").val(entry.resultId)
		    			    }
		    			});
		            }
		    	
			}else{
				$('#opt5_directionDS').val(entry.pcId);
			}
			
			$('#opt5_directionId').val(entry.directionId);
			$('#opt5_nodeId').val(entry.nodeId);
			$('#opt5_type').val(entry.type);
				if(entry.toNodeIds)
				{
					var arr1 = entry.toNodeIds.replace(/\s/g,'').split(/[,]/g);
					$('#opt5_toNodeIds').selectpicker('val', arr1);
				}
			$('#opt5_seqno').val(entry.seqno);
			$("#"+opt5ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	
	/*-------------------------------------函数：选项4数据增删改-----------------------------------------*/
	function init_tool_action_opt4()
	{
		if(!actionInitOpt4)
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
                $('#opt4_nodeId').val(curNodeId);
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
						objCodes += selData[i].neventId + ',';
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
	}
	//选项0数据编辑操作
	function edit_opt4(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt4();//初始化提交表单
			init_form_action_opt4();//提交表单操作
			$('#opt4_neventId').val(entry.neventId);
			$('#opt4_nodeId').val(entry.nodeId);
			$('#opt4_runtime').val(entry.runtime);
			$('#opt4_starttime').val(entry.starttime);
			$('#opt4_srvunId').val(entry.srvunId);
			$('#opt4_seqno').val(entry.seqno);			
			$("#"+opt4ModalId).modal('show');
		}else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	function init_tool_action_opt81()
	{
		if(!actionInitOpt81)
		{
			$("#"+opt81Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt81Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
//			htmlInfo.append("<button id=\"" + opt81Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
//			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt81Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt81Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt81Object+"_btn_add").on("click",function()
			{
//				if($("#"+opt81DialogId).length == 0)
//				{
					init_modal_opt81();//初始化提交表单
					init_form_action_opt81();//提交表单操作
//					schange1();
//                }
                $("#"+opt81FormId)[0].reset();
                $('#opt8_nodeId').val(curNodeId);
				$("#"+opt81ModalId).modal('show');
			});
			
			$("#"+opt81Object+"_btn_edit").on("click",function()
			{
				edit_opt81(curRowOpt81);
			});
			
			$("#"+opt81Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt81TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].dataId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					//删除url修改
					delMany(opt81DelAction, objCodes,'#'+opt81TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});

		}
	}
	//选项8数据编辑操作
	function edit_opt81(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt81();//初始化提交表单
			init_form_action_opt81();//提交表单操作
//			schange1();
			$('#opt8_dataId').val(entry.dataId);
			$('#opt8_nodeId').val(entry.nodeId);
			if(entry.objectId)
			{
				var arr81 = entry.objectId.replace(/\s/g,'').split(/[,]/g);
				$('#opt8_objectId').selectpicker('val', arr81);
			}
			$('#opt8_controlType').val(entry.controlType);
			
			$("#"+opt81ModalId).modal('show');
		}
		else
		{
			bootbox.alert("请选择要修改的数据！","");
		}
	}
	
	function init_tool_action_opt82()
	{
		if(!actionInitOpt82)
		{
			$("#"+opt82Object+"_toolbar").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<button id=\"" + opt82Object + "_btn_add\" class=\"btn btn-primary\" style=\"margin-top:10px\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
//			htmlInfo.append("<button id=\"" + opt82Object + "_btn_edit\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-info\">");
//			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>");
//			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"" + opt82Object + "_btn_del\" style=\"margin-left:5px;margin-top:10px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>");
			htmlInfo.append("</button>");
			
			$("#"+opt82Object+"_toolbar").append(htmlInfo.toString()); 
			
			$("#"+opt82Object+"_btn_add").on("click",function()
			{
//				if($("#"+opt82DialogId).length == 0)
//				{
					init_modal_opt82();//初始化提交表单
					init_form_action_opt82();//提交表单操作
//					schange1();
//                }
                $("#"+opt82FormId)[0].reset();
                $('#opt82_nodeId').val(curNodeId);
				$("#"+opt82ModalId).modal('show');
			});
			
			$("#"+opt82Object+"_btn_edit").on("click",function()
			{
				edit_opt82(curRowOpt82);
			});
			
			$("#"+opt82Object+"_btn_del").on("click",function()
			{					
				var selData = $('#'+opt82TableId).bootstrapTable('getSelections');//选中的数据
				var objCodes = '';
				if(selData.length > 0)
				{
					for(var i=0;i<selData.length;i++)
					{
						objCodes += selData[i].dataId + ',';
					}
					
					objCodes = objCodes.substring(0, objCodes.length-1);
					//删除url修改
					delMany(opt82DelAction, objCodes,'#'+opt82TableId);
				}
				else
				{
					bootbox.alert("请选择要删除的数据记录！","");
				}
				
			});

		}
	}
	//选项8数据编辑操作
	function edit_opt82(entry)
	{
		if(entry != null && entry != "")
		{
			init_modal_opt82();//初始化提交表单
			init_form_action_opt82();//提交表单操作
//			schange1();
			$('#opt82_dataId').val(entry.dataId);
			$('#opt82_nodeId').val(entry.nodeId);
			$('#opt82_controlType').val(entry.controlType);
			if(entry.objectId)
			{
				var arr82 = entry.objectId.replace(/\s/g,'').split(/[,]/g);
				$('#opt82_objectId').selectpicker('val', arr82);
			}
			
			$("#"+opt82ModalId).modal('show');
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
	
		
	//初始化主数据表单操作--左侧表单提交
	function init_form_action_body()
	{
		$('#body_submit_btn').on("click",function(){
			var cname = $('#cname').val();
			var seqno = $('#seqno').val();
			var seqnoRole=/^[0-9]*$/;
			if(cname != "")
			{
				if(seqnoRole.test(seqno) || seqno == null || seqno == ""){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/application/save",
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
			}else {
				showError("中文名称为必填项，请填写！", '');
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
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">流程节点</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt0FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_nodeId\">节点编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"nodeId\" id=\"opt0_nodeId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_appId\">产业编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"opt0_appId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_type\">节点类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_type\" name=\"type\" class=\"form-control\">");
    	for(var i=0;i<nodeTypeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+nodeTypeDS[i].parameterValue+">"+nodeTypeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_cname\">节点中文名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cname\" id=\"opt0_cname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_ename\">节点状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"ename\" id=\"opt0_ename\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_actions\">启用操作</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_actions\" name=\"actions\" multiple class=\"selectpicker show-tick form-control\">");
		
		for(var i=0;i<nodeActionDS.length;i++)
    	{    
			htmlInfo.append("<option value="+nodeActionDS[i].parameterValue+">"+nodeActionDS[i].parameterName+"</option>");
    	}
		
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_traceStep\">跟踪日志</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt0_traceStep\" name=\"traceStep\" class=\"form-control\">");
    	for(var i=0;i<comYesNoDS.length;i++)
    	{
    		htmlInfo.append("<option value="+comYesNoDS[i].parameterValue+">"+comYesNoDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_memo\">备注</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<textarea class=\"form-control\" name=\"memo\" id=\"opt0_memo\" rows=\"3\"></textarea>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt0_seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"opt0_seqno\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">节点事件</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt4FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_neventId\">事件ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"neventId\" id=\"opt4_neventId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_nodeId\">节点ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"nodeId\" id=\"opt4_nodeId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_runtime\">运行时间</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_runtime\" name=\"runtime\" class=\"form-control\">");
		for(var i=0;i<neventRuntimeDS.length;i++)
    	{    
			htmlInfo.append("<option value="+neventRuntimeDS[i].parameterValue+">"+neventRuntimeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_starttime\">开始时间</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_starttime\" name=\"starttime\" class=\"form-control\">");
		for(var i=0;i<startTimeDS.length;i++)
    	{    
			htmlInfo.append("<option value="+startTimeDS[i].parameterValue+">"+startTimeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt4_srvunId\">功能服务</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt4_srvunId\" name=\"srvunId\" class=\"form-control\">");
		for(var i=0;i<srvunIdDS.length;i++)
    	{    
			if(i == 0)
    		{
    			htmlInfo.append("<option value=></option>");
    		}
			htmlInfo.append("<option value="+srvunIdDS[i].parameterValue+">"+srvunIdDS[i].parameterName+"</option>");
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
	
	function schange4(){
		$("#opt4_neventType").change(function(){
			var neventType=$("#opt4_neventType").val();
			$("#opt4_tardataId").empty();
			var htmlInfo = new StringBuffer();
			if(neventType == 1){
				$("#opt4_tardataId").empty();
			}
			if(neventType == 50){
		    	for(var i=0;i<scoreDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+scoreDS[i].parameterValue+">"+scoreDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt4_tardataId").append(htmlInfo.toString());
			}
			if(neventType == 80){
				for(var i=0;i<decisionStrategyDS.length;i++)
		    	{
		    		htmlInfo.append("<option value="+decisionStrategyDS[i].parameterValue+">"+decisionStrategyDS[i].parameterName+"</option>");
		    	}	
		    	$("#opt4_tardataId").append(htmlInfo.toString());
			}
		})
	}
	//初始化表单5
	function init_modal_opt5()
	{
		$("#"+opt5ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt5ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt5DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt5Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabe5\">流向</h4>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt5FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_directionId\">流向ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"directionId\" id=\"opt5_directionId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_nodeId\">节点ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"nodeId\" id=\"opt5_nodeId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_type\">流向类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt5_type\" name=\"type\" class=\"form-control\">");
    	for(var i=0;i<typeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+typeDS[i].parameterValue+">"+typeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\" id=\"opt5_change1\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_directionDS\">决策条件</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt5_directionDS\" name=\"pcId\" class=\"form-control\">");
    	for(var i=0;i<directionDS.length;i++)
    	{
    		if(i == 0)
			{
				htmlInfo.append("<option value=></option>");
			}
    		htmlInfo.append("<option value="+directionDS[i].parameterValue+">"+directionDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_resultId\">服务结果</label>");
		htmlInfo.append("<div class=\"col-sm-8\" id=\"opt5_change2\">");
		htmlInfo.append("<select id=\"opt5_resultId\" name=\"resultId\" class=\"form-control\">");
		////
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_toNodeIds\">目标节点</label>");
		htmlInfo.append("<div id=\"opt5_toNodeId\" class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt5_toNodeIds\" name=\"toNodeIds\" multiple  class=\"selectpicker show-tick form-control\">");
		for(var i=0;i<startNodeDS.length;i++)
    	{
    		htmlInfo.append("<option value="+startNodeDS[i].parameterValue+">"+startNodeDS[i].parameterName+"</option>");
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
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
	//三级联动
	function schange(){
		$("#opt5_type").change(function(){
			var type=$("#opt5_type").val();
			$("#opt5_change1").empty();
			
			var htmlInfo = new StringBuffer();
			if(type == 20){
				//节点事件
				htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_eventDS\">节点事件</label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				htmlInfo.append("<select id=\"opt5_eventDS\" name=\"pcId\" class=\"form-control\">");
		    	for(var i=0;i<nodeNeventDS.length;i++)
		    	{
		    		if(i == 0)
					{
						htmlInfo.append("<option value=></option>");
					}
		    		htmlInfo.append("<option value="+nodeNeventDS[i].parameterValue+">"+nodeNeventDS[i].parameterName+"</option>");
		    	}
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
		    	$("#opt5_change1").append(htmlInfo.toString());
		    	//服务结果
		    	$("#opt5_eventDS").change(function(){
		    		$("#opt5_resultId").empty();
		    		neventValue=$("#opt5_eventDS").val();//获取选择事件的key
		    		resultDS=undefined;
		    		if(resultDS == undefined){
		    			$.ajax({
		    				type: "POST",
		    				url: "/flowNevent/getServiceResultByNeventId",
		    				data:{"neventId":neventValue},
		    			    error: function(request) {					
		    			    },
		    			    success: function(data) {
		    			    	resultDS = eval(data);
		    			    	var htmlInfo1 = new StringBuffer();
		    			    	for(var j=0;j<resultDS.length;j++)
		    			    	{
		    			    		if(j == 0)
		    			    		{
		    			    			htmlInfo1.append("<option value=></option>");
		    			    		}
		    			    		htmlInfo1.append("<option value="+resultDS[j].parameterValue+">"+resultDS[j].parameterName+"</option>");
		    			    	}
		    			    	$("#opt5_resultId").append(htmlInfo1.toString());
		    			    }
		    			});
		            }
		    	})
		    	
			}else{
				//决策条件数据
				htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt5_directionDS\">决策条件</label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				htmlInfo.append("<select id=\"opt5_directionDS\" name=\"pcId\" class=\"form-control\">");
		    	for(var i=0;i<directionDS.length;i++)
		    	{
		    		if(i == 0)
					{
						htmlInfo.append("<option value=></option>");
					}
		    		htmlInfo.append("<option value="+directionDS[i].parameterValue+">"+directionDS[i].parameterName+"</option>");
		    	}
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
				$("#opt5_change1").append(htmlInfo.toString());
				//服务结果
				$("#opt5_resultId").empty();
			}
		})
	}
	//初始化表单81
	function init_modal_opt81()
	{
		$("#"+opt81ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt81ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt81DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt81Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">数据权限</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt81FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt8_dataId\">数据权限ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"dataId\" id=\"opt8_dataId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt8_nodeId\">节点ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"nodeId\" id=\"opt8_nodeId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt8_objectType\">对象类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"objectType\" id=\"opt8_objectType\" type=\"text\" placeholder=\"页面区段\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt8_objectId\">对象</label>");
		htmlInfo.append("<div class=\"col-sm-8\" id=\"opt8_object\">");
		htmlInfo.append("<select id=\"opt8_objectId\" name=\"objectId\" multiple  class=\"selectpicker show-tick form-control\">");
		for(var i=0;i<sectionDS.length;i++)
    	{
			if(i == 0)
			{
				htmlInfo.append("<optionvalue=></option>");
			}
    		htmlInfo.append("<option value="+sectionDS[i].parameterValue+">"+sectionDS[i].parameterName+"</option>");
    	}
    	htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt8_controlType\">权限类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt8_controlType\" name=\"controlType\" class=\"form-control\">");
		for(var i=0;i<controlTypeDS.length;i++)
    	{    
			if(i == 0)
			{
				htmlInfo.append("<optionvalue=></option>");
			}
			htmlInfo.append("<option value="+controlTypeDS[i].parameterValue+">"+controlTypeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt81Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt81Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}	
	//初始化表单82
	function init_modal_opt82()
	{
		$("#"+opt82ModalId).remove();
		
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+opt82ModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+opt82DialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\""+opt82Object+"_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">数据权限</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\""+opt82FormId+"\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt82_dataId\">数据权限ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"dataId\" id=\"opt82_dataId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt82_nodeId\">节点ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"nodeId\" id=\"opt82_nodeId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt82_objectType\">对象类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"objectType\" id=\"opt82_objectType\" type=\"text\" placeholder=\"数据项\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");	
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt82_objectId\">对象</label>");
		htmlInfo.append("<div class=\"col-sm-8\" id=\"opt82_object\">");
		htmlInfo.append("<select id=\"opt82_objectId\" name=\"objectId\" multiple  class=\"selectpicker show-tick form-control\">");
		for(var i=0;i<itemDS.length;i++)
    	{
			if(i == 0)
			{
				htmlInfo.append("<optionvalue=></option>");
			}
    		htmlInfo.append("<option value="+itemDS[i].parameterValue+">"+itemDS[i].parameterName+"</option>");
    	}
    	htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"opt82_controlType\">权限类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"opt82_controlType\" name=\"controlType\" class=\"form-control\">");
		for(var i=0;i<controlTypeDS.length;i++)
    	{    
			if(i == 0)
			{
				htmlInfo.append("<optionvalue=></option>");
			}
			htmlInfo.append("<option value="+controlTypeDS[i].parameterValue+">"+controlTypeDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\""+opt82Object+"_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\""+opt82Object+"_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $(".initmodal").append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}	
	
	function schange1(){
		$("#opt8_objectType").change(function(){
			var type=$("#opt8_objectType").val();
			$("#opt8_object").empty();
			var htmlInfo=new StringBuffer();
			if(type==1){
				htmlInfo.append("<select id=\"opt8_objectId\" name=\"objectId\" multiple  class=\"selectpicker show-tick form-control\">");
				for(var i=0;i<sectionDS.length;i++)
		    	{    
					if(i == 0)
					{
						htmlInfo.append("<optionvalue=></option>");
					}
					htmlInfo.append("<option value="+sectionDS[i].parameterValue+">"+sectionDS[i].parameterName+"</option>");
		    	}
				htmlInfo.append("</select>");
			}
			if(type==2){
				htmlInfo.append("<select id=\"opt8_objectId\" name=\"objectId\" multiple  class=\"selectpicker show-tick form-control\">");
				for(var i=0;i<itemDS.length;i++)
		    	{    
					if(i == 0)
					{
						htmlInfo.append("<optionvalue=></option>");
					}
					htmlInfo.append("<option value="+itemDS[i].parameterValue+">"+itemDS[i].parameterName+"</option>");
		    	}
				htmlInfo.append("</select>");
			}
			$("#opt8_object").append(htmlInfo.toString());
			$('.selectpicker').selectpicker({noneSelectedText:'请选择'});
		})
	}
/*---------------------------------表单提交---------------------------------------------------------------------*/
	//提交表单0
	function init_form_action_opt0()
	{
		$('#'+opt0Object+'_submit_btn').on("click",function(){
			var cname = $('#opt0_cname').val();
			var type = $('#opt0_type').val();
			if(cname != "" && type != "")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/fleNode/save",
					data: $("#"+opt0FormId).serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt0FormId)[0].reset();
				    	$("#"+opt0ModalId).modal('hide');
				    	$("#"+opt0TableId).bootstrapTable('refresh');
				    	//重新加载选项一数据对象表单需要的数据集
						//nodeDS = null;
						//loadAsyncDS4Opt1Form();
				    }
				});
			}else{
				showError("中文名称和节点类型为必填项，请填写！", '');
				//bootbox.alert("中文名称和节点类型为必填项，请填写！","");
			}
		});
		
		$('#'+opt0Object+'_cancel_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
		$('#'+opt0Object+'_close_btn').on("click",function(){
			$("#"+opt0FormId)[0].reset();
		});
	}
	//提交表单1
	function init_form_action_opt1()
	{
		$('#'+opt1Object+'_submit_btn').on("click",function(){
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/flowEntrance/save",
					data: $("#"+opt1FormId).serialize(), //formid
				    error: function(request) {
				    	bootbox.alert(request.msg,"");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$("#"+opt1FormId)[0].reset();
				    	$("#"+opt1ModalId).modal('hide');
				    	$("#"+opt1TableId).bootstrapTable('refresh');
				    	//重新加载选项一数据对象表单需要的数据集
						//nodeDS = null;
						//loadAsyncDS4Opt1Form();
				    }
				});
		});
		
		$('#'+opt1Object+'_cancel_btn').on("click",function(){
			$("#"+opt1FormId)[0].reset();
		});
		$('#'+opt1Object+'_close_btn').on("click",function(){
			$("#"+opt1FormId)[0].reset();
		});
	}
	//提交表单4
	function init_form_action_opt4()
	{
		$('#'+opt4Object+'_submit_btn').on("click",function(){
			var srvunit=$("#opt4_srvunId").val();
			if(srvunit == null || srvunit == ""){
				bootbox.alert("功能服务不能为空！","");
			}else{
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "/flowNevent/save",
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
			}
		});
		
		$('#'+opt4Object+'_cancel_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
		$('#'+opt4Object+'_close_btn').on("click",function(){
			$("#"+opt4FormId)[0].reset();
		});
	}
	//提交表单
	function init_form_action_opt5()
	{
		$('#'+opt5Object+'_submit_btn').on("click",function(){
			var toNodeIds = $('#opt5_toNodeIds').val();
			if(toNodeIds.length > 0){
					var url = "/flowDirection/save";
					$.ajax({
						type: "POST",
						dataType: "json",
						url:url,
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
				}else{
					showError("目标节点至少选择一个！", '');
				}
		});
		
		$('#'+opt5Object+'_cancel_btn').on("click",function(){
			$("#"+opt5FormId)[0].reset();
		});
		$('#'+opt5Object+'_close_btn').on("click",function(){
			$("#"+opt5FormId)[0].reset();
		});
	}

	//提交表单6
	function submit_form_action_opt6()
	{
		//提交
		$('#'+opt6Object+'_submit_btn').on("click",function(){
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/fleNode/saveNode",
					data: $("#"+opt6FormId).serialize()+"&nodeId="+curRowOpt0.nodeId+"&approverType="+$('#opt0_approverType').val()+"&approverCount="+$('#opt0_approverCount').val()+"&approveMode="+$('#opt0_approveMode').val(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
//				    	$("#"+opt6FormId)[0].reset();
				    	$("#"+opt6ModalId).modal('hide');
				    	$("#"+opt0TableId).bootstrapTable('refresh');
				    }
				});
		});
	}
	
	//提交表单7
	function submit_form_action_opt7()
	{
		//提交
		$('#'+opt7Object+'_submit_btn').on("click",function(){
			var nodeStatus = $('#opt0_nodeStatus').val();
			var openStatus = $('#opt0_openStatus').val();
			var saveStatus = $('#opt0_saveStatus').val();
			var initStatus = $('#opt0_initStatus').val();
			if(nodeStatus == null){
				nodeStatus = 0;
			}
			if(openStatus == null){
				openStatus = 0;
			}
			if(saveStatus == null){
				saveStatus = 0;
			}
			if(initStatus == null){
				initStatus = 0;
			}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/fleNode/saveStatus",
					data: "nodeId="+curRowOpt0.nodeId+"&nodeStatus="+nodeStatus+"&openStatus="+openStatus+"&saveStatus="+saveStatus+"&initStatus="+initStatus, //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
//				    	$("#"+opt7FormId)[0].reset();
				    	$("#"+opt7ModalId).modal('hide');
				    	$("#"+opt0TableId).bootstrapTable('refresh');

				    }
				});
		});
	}
	//提交表单8
	function init_form_action_opt81()
	{
		$('#'+opt81Object+'_submit_btn').on("click",function(){
					$.ajax({
						type: "POST",
						dataType: "json",
						url:"/data/saveleft",
						data:$("#"+opt81FormId).serialize(), //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt81FormId)[0].reset();
					    	$("#"+opt81ModalId).modal('hide');
					    	$("#"+opt81TableId).bootstrapTable('refresh');
					    }
					});
		});
		
		$('#'+opt81Object+'_cancel_btn').on("click",function(){
			$("#"+opt81FormId)[0].reset();
		});
		$('#'+opt81Object+'_close_btn').on("click",function(){
			$("#"+opt81FormId)[0].reset();
		});
	}
	
	//提交表单8
	function init_form_action_opt82()
	{
		$('#'+opt82Object+'_submit_btn').on("click",function(){
					$.ajax({
						type: "POST",
						dataType: "json",
						url:"/data/saveright",
						data:$("#"+opt82FormId).serialize(), //formid
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$("#"+opt82FormId)[0].reset();
					    	$("#"+opt82ModalId).modal('hide');
					    	$("#"+opt82TableId).bootstrapTable('refresh');
					    }
					});
		});
		
		$('#'+opt82Object+'_cancel_btn').on("click",function(){
			$("#"+opt82FormId)[0].reset();
		});
		$('#'+opt82Object+'_close_btn').on("click",function(){
			$("#"+opt82FormId)[0].reset();
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
			url:"/fleNode/getApproverIdsByNodeId",
			data:{"nodeId":curRowOpt0.nodeId},
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
					title: '编号',
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
//            	bootbox.alert("数据加载失败！","");
            }
		});
		$('#rolelistid').bootstrapTable('refresh');
		countSyncDS4YxData = 0;
	}
	
	//对应的函数进行判断；
	function stateFormatter(value, row, index) {
		if(yxRole !=null && yxRole != "")
		{
			var rolecodeArr = yxRole.split(";");
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
				roleCodes += selData[i].roleId+';';
			}
			roleCodes = roleCodes.substring(0,roleCodes.length-1);
			
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/fleNode/saveRoles",
				data: "&roleCodes="+roleCodes+"&nodeId="+curRowOpt0.nodeId,
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	unroleCode = "";
			    	$('#roleModal').modal('hide');
//			    	$("#"+bodyTableId).bootstrapTable('refresh');
//			    	curRowBody = "";
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
