$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var curRowData = "";			//记录所选择的行用于点击行
	var delParaDS;	
	var sitePDS;
	var statusPDS;
	var provideDS;
	var provideDS2;
	var sifaDS;
	var validResultDS;
	var totalSyncDS = 7;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	var totalSyncsrDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncsrDS = 0;			//同步数据集计数器，当前成功获取个数
	
	var bodyObject = "serviceimpl";
	var menuId = bodyObject + "_id";
	var bodyDivId = bodyObject + "_div";
	var bodyTableId = bodyObject + "_table";
	var bodyModalId = bodyObject + "_modal";
	var bodyDialogId = bodyObject + "_dialog";
	var bodyListController = "/serviceimpl/listServiceImpl";
	var bodyAddController = "/serviceimpl/addServiceImpl";
	var bodyDelController = "/serviceimpl/delServiceImpl";
	
	//服务结果
	var srBodyObject = "serviceres";
	var srBodyDivId = srBodyObject + "_div";
	var srBodyTableId = srBodyObject + "_table";
	var srBodyDialogId = srBodyObject + "_dialog";
	var srBodyListController = "/serviceresult/listServRes";
	var srBodyAddController = "/serviceresult/addServRes";
	var srBodyDelController = "/serviceresult/delServRes";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		//服务提供者名称-编号
		if(provideDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/provider/providerDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	provideDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//服务提供者简称-编号
		if(provideDS2 == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/provider/providerDS2",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	provideDS2 = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//通用服务名称-编号
		if(sifaDS == undefined)
		{
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/serviceifa/sifaDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	sifaDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//服务场所
		if(sitePDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "SERVICEIMPL_SITE";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	sitePDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//服务结果有效期
		if(validResultDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "SERVICEIMPL_VALIDRESULT";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	validResultDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//状态
		if(statusPDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "COMMON_STATUS";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	statusPDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//软删除
		if(delParaDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "COMMON_YESNO";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	delParaDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
	}
	
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#serviceimpl_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		show_view_body(); 
		countSyncsrDS = 0;
		
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
		
		$('#'+bodyTableId).bootstrapTable({
			method: 'post', 
			url: bodyListController,
			dataType: "json",
			cache: false,
			height: $(window).height()-300,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100,120],
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
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'servimplCode',
					title: '服务实现编号',
					align: 'left',
					width: '30',
					valign: 'middle',
					visible: false
				},
				{
					field: 'providerCode',
					title: '服务机构',
					align: 'left',
					width: '30',
					valign: 'middle',
					formatter: providerFormatter
				},
				{
					field: 'servifaCode',
					title: '通用服务',
					align: 'left',
					width: '30',
					valign: 'middle',
					formatter: servifaFormatter
				},
				{
					field: 'remotePrdcode',
					title: '提供者产品',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'localPrdcode',
					title: '本地产品',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'cname',
					title: '服务名称',
					align: 'left',
					width: '120',
					valign: 'middle'
				},
				{
					field: 'className',
					title: '程序类名',
					align: 'left',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'classMethod',
					title: '执行方法',
					align: 'left',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '30',
					valign: 'middle',
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '75',
					valign: 'middle',
					formatter: actionFormatter,
					events: operateEvents
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curRowData = row;
            	countSyncsrDS ++;
            	init_servrestable();
            	show_view_srbody();
            	$('#'+bodyTableId).each(function() { 
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
	
	//table里面显示中文描述
	function statusFormatter(value) {
    	for(var i=0;i<statusPDS.length;i++)
    	{
    		if(statusPDS[i].parameterValue == value)
    		{
    			return ['<span>'+statusPDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function servifaFormatter(value) {
    	for(var i=0;i<sifaDS.length;i++)
    	{
    		if(sifaDS[i].parameterValue == value)
    		{
    			return ['<span>'+sifaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	function providerFormatter(value) {
    	for(var i=0;i<provideDS2.length;i++)
    	{
    		if(provideDS2[i].parameterValue == value)
    		{
    			return ['<span>'+provideDS2[i].parameterName+'</span>']
	    	}
    	}
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
	
	function show_view_srbody()
	{
		if(countSyncsrDS==totalSyncsrDS)//只加载一次，防止确定删除框出现好多个。
		{
			init_srtool_action();
		}
		$('#fwjg_toolbar').show();
		//列操作事件定义
		var sroperateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "sredit")
			{
				sredit(row);
			}
			else if(e.currentTarget.id == "srdel")
			{
				srdelOne(row);
			}
		}};		
		
		$('#'+srBodyTableId).bootstrapTable({
			method: 'post', 
			url: srBodyListController,
			dataType: "json",
			cache: false,
			height: $(window).height()-300,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50,100,120],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: srqueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showColumns: true,
			showRefresh: true,
			showToggle: true,
			toolbar:'#fwjg_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: srresponseHandler,
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
					field: 'servresCode',
					title: '服务结果编号',
					align: 'center',
					width: '15',
					valign: 'middle',
					visible: false
				},
				{
					field: 'servimplCode',
					title: '服务实现编号',
					align: 'center',
					width: '15',
					valign: 'middle',
					visible: false
				},
				{
					field: 'resultText',
					title: '结果释义',
					align: 'left',
					width: '80',
					valign: 'middle'
				},
				{
					field: 'resultValue',
					title: '结果码值',
					align: 'left',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '10',
					valign: 'middle',
//					visible: false
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '60',
					valign: 'middle',
					formatter: sractionFormatter,
					events: sroperateEvents
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(){
            	$('#'+srBodyTableId).each(function() { 
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
		$('#'+srBodyTableId).bootstrapTable('refresh');
		
	}
	
	//设置table传递到服务器的参数
	function srqueryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      code: curRowData.servimplCode //所选行的类别code
	    };
	    return temp;
	}
	
	
	//处理服务器端响应数据，使其适应分页格式
	function srresponseHandler(res)
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
			htmlInfo.append("<div class=\"row\" id=\"serviceid\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">服务实现</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+bodyDivId+"\">");//服务实现
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">响应结果</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"fwjg_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("<button id=\"fwjg_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"fwjg_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+srBodyDivId+"\">");//服务结果
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
	//初始化服务结果table
	function init_servrestable()
	{
		var htmlInfo=new StringBuffer();
		if($('#'+srBodyTableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\""+srBodyTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+srBodyDivId).append(htmlInfo.toString()); 
		}
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面的操作列显示
	function actionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="edit" href="#"><span>编辑</span></a>',
	       '<a class="operate" id="del" href="#"><span>删除</span></a>'
	     ].join('   ');
	}
	
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add').on("click",function()
		{
			s_update_name = "";
			if($('#'+bodyDialogId).length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#addform')[0].reset();
			$('#simplModal').modal('show');
		});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#'+bodyTableId).bootstrapTable('getSelections');//选中的数据
			var simplCodes ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					simplCodes += selData[i].servimplCode+',';
				}
				
				simplCodes = simplCodes.substring(0,simplCodes.length-1);
				delSImpl(simplCodes);//删除确认
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
	        message: '确认删除服务实现吗?',
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
					    	$('#'+bodyTableId).bootstrapTable('refresh');
					    }
					});
	            } 
	        },  
        });
		
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	
	//编辑操作
	function edit(entry)
	{
		var servimplCode = entry.servimplCode;
		var servifaCode = entry.servifaCode;
		var providerCode = entry.providerCode;
		var remotePrdcode = entry.remotePrdcode;
		var localPrdcode = entry.localPrdcode;
		var cname = entry.cname;
		var ename = entry.ename;
		var version = entry.version;
		var className = entry.className;
		var classMethod = entry.classMethod;
		var site = entry.site;
		var validResult = entry.validResult;
		var costPertimeLocal = entry.costPertimeLocal;
		var costPertimeSource = entry.costPertimeSource;
		var status = entry.status;
		var seqno = entry.seqno;
		var isDelete = entry.isDelete;
		s_update_name = servimplCode;
		
		if($('#'+bodyDialogId).length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		
		$('#servimplCode').val(servimplCode);
		$('#servifaCode').val(servifaCode);
		$('#providerCode').val(providerCode);
		$('#remotePrdcode').val(remotePrdcode);
		$('#localPrdcode').val(localPrdcode);
		$('#cname').val(cname);
		$('#ename').val(ename);
		$('#version').val(version);
		$('#className').val(className);
		$('#classMethod').val(classMethod);
		$('#site').val(site);
		$('#validResult').val(validResult);
		$('#costPertimeLocal').val(costPertimeLocal);
		$('#costPertimeSource').val(costPertimeSource);
		$('#status').val(status);
		$('#seqno').val(seqno);
		$('#isDelete').val(isDelete);
		
		$('#simplModal').modal('show');
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
//	                $.post(g_AppRoot+bodyDelAction,{"currIds":entry.servimplCode,"anyname":entry.cname+"("+entry.servimplCode+")"},
	                $.post(bodyDelController,{"currIds":entry.servimplCode},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	bootbox.alert(data.msg);
        			    	$('#'+bodyTableId).bootstrapTable('refresh');
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
	
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"simplModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">服务实现</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"servimplCode\">服务实现编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"servimplCode\" id=\"servimplCode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"servifaCode\">通用服务</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"servifaCode\" name=\"servifaCode\" class=\"form-control\">");
    	for(var i=0;i<sifaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+sifaDS[i].parameterValue+">"+sifaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"providerCode\">服务提供者</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"providerCode\" name=\"providerCode\" class=\"form-control\">");
    	for(var i=0;i<provideDS.length;i++)
    	{
    		htmlInfo.append("<option value="+provideDS[i].parameterValue+">"+provideDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"remotePrdcode\">提供者产品编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"remotePrdcode\" id=\"remotePrdcode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"localPrdcode\">本地产品编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"localPrdcode\" id=\"localPrdcode\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"className\">实现类名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"className\" id=\"className\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"classMethod\">实现方法</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"classMethod\" id=\"classMethod\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"site\">服务场所</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"site\" name=\"site\" class=\"form-control\">");
    	for(var i=0;i<sitePDS.length;i++)
    	{
    		htmlInfo.append("<option value="+sitePDS[i].parameterValue+">"+sitePDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"validResult\">服务结果有效期</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"validResult\" name=\"validResult\" class=\"form-control\">");
    	for(var i=0;i<validResultDS.length;i++)
    	{
    		htmlInfo.append("<option value="+validResultDS[i].parameterValue+">"+validResultDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"costPertimeLocal\">本地每次价格</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"costPertimeLocal\" id=\"costPertimeLocal\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"costPertimeSource\">服务源每次价格</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"costPertimeSource\" id=\"costPertimeSource\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<statusPDS.length;i++)
    	{
    		htmlInfo.append("<option value="+statusPDS[i].parameterValue+">"+statusPDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isDelete\">删除标志</label>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+bodyModalId).append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var cname = $('#cname').val();
			var localPrdcode = $('#localPrdcode').val();
			var url = bodyAddController;
			if(cname!=""&&localPrdcode!="")
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
					data:$.param({'anyname':anyname})+'&'+$('#addform').serialize(), //formid
				    error: function(request) {
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#addform')[0].reset();
				    	$('#simplModal').modal('hide');
				    	$('#'+bodyTableId).bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("中文名称和本地产品编号为必填项，请填写！", '');
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
	
	
	/*------------------------------------------服务结果操作-----------------------------------------*/
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面的操作列显示
	function sractionFormatter(value, row, index) {
	     return [
	       '<a class="operate" id="sredit" href="#"><span>编辑</span></a>',
	       '<a class="operate" id="srdel" href="#"><span>删除</span></a>'
	     ].join('   ');
	}
	
	
	//工具栏操作初始化
	function init_srtool_action()
	{
		$('#fwjg_btn_add').on("click",function()
		{
			srs_update_name = "";
			if($('#'+srBodyDialogId).length == 0)
			{
				srinit_modal();//初始化提交表单
				srsubmit_form();//提交表单操作
			}
			$('#sraddform')[0].reset();
			$('#servimplCode2').val(curRowData.servimplCode);
			$('#sresultModal').modal('show');
		});
		
		$("#fwjg_btn_del").on("click",function()
		{
			var selData = $('#'+srBodyTableId).bootstrapTable('getSelections');//选中的数据
			var sresultCodes ='';
			var sresultNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					sresultCodes += selData[i].servresCode+',';
					sresultNames += selData[i].resultValue+"("+selData[i].servresCode+")"+',';
				}
				
				sresultCodes = sresultCodes.substring(0,sresultCodes.length-1);
				sresultNames = sresultNames.substring(0,sresultNames.length-1);
				delSResult(sresultCodes);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});
	}
	
	function delSResult(currIds)
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
	        message: '确认删除服务结果吗?',
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: srBodyDelController,
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#'+srBodyTableId).bootstrapTable('refresh');
					    }
					});
	            } 
	        },  
        });
		
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	function sredit(entry)
	{
		var servresCode = entry.servresCode;
		var servimplCode = entry.servimplCode;
		var resultText = entry.resultText;
		var resultValue = entry.resultValue;
		var seqno = entry.seqno;
		srs_update_name = servresCode;
		
		if($('#'+srBodyDialogId).length == 0)//判断表单是否已经初始化
		{
			srinit_modal();		//初始化提交表单
			srsubmit_form();		//提交表单操作
		}
		
		$('#servresCode').val(servresCode);
		$('#servimplCode2').val(servimplCode);
		$('#resultText').val(resultText);
		$('#resultValue').val(resultValue);
		$('#seqno').val(seqno);
		
		$('#sresultModal').modal('show');
	}
	
	
	//删除单个数据记录操作
	function srdelOne(entry)
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
	                $.post(srBodyDelController,{"currIds":entry.servresCode},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
        			    {
        			    	bootbox.alert(data.msg);
        			    	$('#'+srBodyTableId).bootstrapTable('refresh');
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
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	function srinit_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"sresultModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+srBodyDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"srclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">服务结果</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"sraddform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"servresCode\">服务结果编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"servresCode\" id=\"servresCode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"servimplCode\">服务实现编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"servimplCode\" id=\"servimplCode2\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"resultText\">结果释义</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<textarea class=\"form-control\" name=\"resultText\" id=\"resultText\" rows=\"4\"/></textarea>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"resultValue\">结果码值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"resultValue\" id=\"resultValue\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"srcancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"srsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#"+bodyModalId).append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function srsubmit_form()
	{
		$('#srsubmit_btn').on("click",function(){
			var cname = $('#resultValue').val();
			var url = srBodyAddController;
			if(srs_update_name == "")
			{
				sranyname = cname;
			}else{
				sranyname = cname + "(" + srs_update_name + ")";
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url:url,
				data:$.param({'anyname':sranyname})+'&'+$('#sraddform').serialize(), //formid
			    error: function(request) {
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	$('#sraddform')[0].reset();
			    	$('#sresultModal').modal('hide');
			    	$('#'+srBodyTableId).bootstrapTable('refresh');
			    }
			});
				
		});
		
		$('#srcancel_btn').on("click",function(){
			$('#sraddform')[0].reset();
		});
		$('#srclose_btn').on("click",function(){
			$('#sraddform')[0].reset();
		});
	}
	
});
