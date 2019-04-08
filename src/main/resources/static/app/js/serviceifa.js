$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var delParaDS;	
	var categoryPDS;
	var totalSyncDS = 2;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	var bodyObject = "serviceifa";
	var menuId = bodyObject + "_id";
	var bodyDivId = bodyObject + "_div";
	var bodyTableId = bodyObject + "_table";
	var bodyModalId = bodyObject + "_modal";
	var bodyDialogId = bodyObject + "_dialog";
	var bodyFormId = bodyObject + "_form";
	var bodyListAction = "/app/listServiceIfa.action";
	var bodyAddAction = "/app/addServiceIfa.action";
	var bodyDelAction = "/app/delServiceIfa.action";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		//类别
		if(categoryPDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "SERVICEIFA_CATEGORY";
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":pstsParaGrpName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	categoryPDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
		//软删除
		if(delParaDS == undefined)
		{	//保证仅获取一次
			var pstsParaGrpName = "IS_DELETE";
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
	$("#serviceifa_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		show_view_body(); 
		
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
    };
    
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
			url: "/serviceifa/list",
			dataType: "json",
			cache: false,
			height: $(window).height()-240,
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
					width: '10',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'servifaCode',
					title: '通用服务编号',
					align: 'left',
					width: '30',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'ename',
					title: '英文名称',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'category',
					title: '类别',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: categoryFormatter
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
					width: '60',
					valign: 'middle',
					formatter: actionFormatter,
					events: operateEvents
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(){
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
	function categoryFormatter(value) {
    	for(var i=0;i<categoryPDS.length;i++)
    	{
    		if(categoryPDS[i].parameterValue == value)
    		{
    			return ['<span>'+categoryPDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      pageindex:params.pageNumber
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
		if($('#'+bodyDivId).length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\""+bodyDivId+"\">");
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
			$('#sifaModal').modal('show');
		});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#'+bodyTableId).bootstrapTable('getSelections');//选中的数据
			var sifaCodes ='';
			var sifaNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					sifaCodes += selData[i].servifaCode+',';
					sifaNames += selData[i].cname+"("+selData[i].servifaCode+")"+',';
				}
				
				sifaCodes = sifaCodes.substring(0,sifaCodes.length-1);
				sifaNames = sifaNames.substring(0,sifaNames.length-1);
				delSIfa(sifaCodes,sifaNames);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});
	}
	
	function delSIfa(currIds,currNames)
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
	        message: '确认删除通用服务吗?',
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/serviceifa/delServiceifa",
						data:{"currIds":currIds,"anyname":currNames},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#'+bodyTableId).bootstrapTable('refresh');
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	
	//编辑操作
	function edit(entry)
	{
		var servifaCode = entry.servifaCode;
		var cname = entry.cname;
		var ename = entry.ename;
		var category = entry.category;
		var seqno = entry.seqno;
		var isDelete = entry.isDelete;
		s_update_name = servifaCode;
		
		if($('#'+bodyDialogId).length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		
		$('#servifaCode').val(servifaCode);
		$('#cname').val(cname);
		$('#ename').val(ename);
		$('#category').val(category);
		$('#seqno').val(seqno);
		$('#isDelete').val(isDelete);
		
		$('#sifaModal').modal('show');
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
	                $.post("/serviceifa/delServiceifa",{"currIds":entry.servifaCode,"anyname":entry.cname+"("+entry.servifaCode+")"},
        		    function(data)
        		    {
        			    	bootbox.alert(data.msg);
        			    	$('#'+bodyTableId).bootstrapTable('refresh');
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
		
		htmlInfo.append("<div class=\"modal fade\" id=\"sifaModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">通用服务</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"servifaCode\">通用服务编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"servifaCode\" id=\"servifaCode\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"category\">类别</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"category\" name=\"category\" class=\"form-control\">");
		
    	for(var i=0;i<categoryPDS.length;i++)
    	{
    		htmlInfo.append("<option value="+categoryPDS[i].parameterValue+">"+categoryPDS[i].parameterName+"</option>");
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
			var url = "/serviceifa/save";
			if(cname!="")
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
				    	$('#sifaModal').modal('hide');
				    	$('#'+bodyTableId).bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("中文名称为必填项，请填写！", '');
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
	
	//url参数的方法方法：
    function getUrlParam(name) {
    	var searchStr = window.location.search;
    	//解码
    	searchStr = decodeURIComponent(searchStr);
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = searchStr.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
  
});
