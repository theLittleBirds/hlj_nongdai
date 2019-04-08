$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var curPgroup = "";				//记录所选择的参数类别行用于点击前面选择框
	var curRowData = "";			//记录所选择的参数类别行用于点击行
	
	var curpara = "";				//记录所选择的参数明细行用于点击前面选择框
	var curparaRowData = "";		//记录所选择的参数明细行用于点击行
	
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#parameter_id").on("click", function(){
		init_global();
		showView(); 
		countSyncDS = 0;
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	//参数类别显示
	function showView()
	{
		init_layout();							//初始化table外层的div视图
		
		init_table();							//初始化table视图
		
		init_modal_home();						//初始化modal视图
		
		init_tool_action();						//初始化工具栏操作
		
		$('#cslb_tableid').bootstrapTable({
			method: 'post', 
			url: "/para/pgrouplist",
			dataType: "json",
			cache: false,
			height: $(window).height()-285,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [50,100,150],
			pageSize:50,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams,//参数
			contentType: "application/x-www-form-urlencoded",
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
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'descr',
					title: '类别描述',
					align: 'left',
					width: '',
					valign: 'middle'
				},
				{
					field: 'name',
					title: '类别名称',
					align: 'left',
					width: '',
					valign: 'middle'
				},
//				{
//					field: 'pgroupId',
//					title: '类别编号',
//					align: 'center',
//					width: '',
//					valign: 'middle'
//				},				
				{
					field: 'seqno',
					title: '排列序号',
					align: 'center',
					width: '',
					valign: 'middle',
					events: del()//删除
				}
			],
			onLoadSuccess:function(){
				
            },
            onClickRow:function(row){
            	curRowData = row;
            	countSyncDS ++;
            	init_csmxtable();
            	showcsmxView();
            	$('#cslb_tableid').each(function() { 
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
            	curPgroup = row;
            },
            onLoadError: function () {
                //mif.showErrorMessageBox("数据加载失败！");
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
	
	
	//参数明细显示
	function showcsmxView()
	{
		if(countSyncDS==totalSyncDS)//只加载一次，防止确定删除框出现好多个。
		{
			init_csmxtool_action();
		}
		$('#csmx_toolbar').show();
		$('#csmx_tableid').bootstrapTable({
			url: "/para/sysparalist",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-285,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50],
			pageSize:15,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: csmxqueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			showRefresh: true,
			showToggle: true,
			toolbar:'#csmx_toolbar',
			minimumCountColumns: 3,
			//clickToSelect: true,//点击行即可选中单选/复选框  
			smartDisplay:true,
			responseHandler: csmxresponseHandler,
			columns: [
				{
					field: 'csmx_xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'descr',
					title: '参数描述',
					align: 'left',
					width: '10',
					valign: 'middle'
				},
				{
					field: 'name',
					title: '参数名称',
					align: 'left',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'value',
					title: '参数值',
					align: 'left',
					width: '10',
					valign: 'middle',
					//visible: false
				},
				{
					field: 'paraId',
					title: '参数编号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'pgroupId',
					title: '类别编号',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'seqno',
					title: '排列序号',
					align: 'center',
					width: '10',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(){
			
            },
            onClickRow:function(row){
            	curparaRowData = row;
            	$('#csmx_tableid').each(function() { 
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
            	curpara = row;
            },
            onLoadError: function () {
                //mif.showErrorMessageBox("数据加载失败！");
            }
		});
		$('#csmx_tableid').bootstrapTable('refresh');
	}
	
	//设置table传递到服务器的参数
	function csmxqueryParams(params)
	{
	    var temp = {								//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,						//页面大小
  	      offset: params.offset/params.limit+1,		//页码
	      curParaGroupCode: curRowData.pgroupId,	//所选行的类别code
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function csmxresponseHandler(res)
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
		if($('#xtcsid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div class=\"row\" id=\"xtcsid\"  style=\"margin-top:10px;margin-left:0px;margin-right: 5px;\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">参数类别</h3>");
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
			htmlInfo.append("<div id=\"cslbid\">");//参数类别
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-6\" style=\"\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">参数明细</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"csmx_toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("<button id=\"csmx_btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"csmx_btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"csmx_btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"csmxid\">");//参数明细
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
		if($('#paramodalid').length == 0)
		{
			htmlInfo.append("<div id=\"paramodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化参数类别table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#cslb_tableid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"cslb_tableid\">");
			htmlInfo.append("</table>");
			$("#cslbid").append(htmlInfo.toString()); 
		}
	}
	
	//初始化参数明细table
	function init_csmxtable()
	{
		var htmlInfo=new StringBuffer();
		if($('#csmx_tableid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"csmx_tableid\">");
			htmlInfo.append("</table>");
			$("#csmxid").append(htmlInfo.toString()); 
		}
	}
	
	
	/*-------------------------------------函数：参数类别增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add').on("click",function()
		{
			s_update_name = "";
			if($('#pGroupdialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#cslb_addform')[0].reset();
			$('#pGroupModal').modal('show');
		});
		
		$('#btn_edit').on("click",function()
		{
			if(curRowData!="")
			{
				s_update_name = curRowData.pgroupId;
//				b_update_name = curRowData.name;
				if($('#pGroupdialogid').length == 0)//判断表单是否已经初始化
				{
					init_modal();		//初始化提交表单
					submit_form();		//提交表单操作
				}
				var pgroupId = curRowData.pgroupId;
				var name = curRowData.name;
				var descr = curRowData.descr;
				var seqno = curRowData.seqno;
				
				$('#pgroupcode').val(pgroupId);
				$('#name').val(name);
				$('#descr').val(descr);
				$('#seqno').val(seqno);
				$('#pGroupModal').modal('show');
			}
			else{
				bootbox.alert("请选择要修改的参数！","");
			}
		});
		
	}
	
	//删除方法
	function del()
	{
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#cslb_tableid').bootstrapTable('getSelections');//选中的数据
			var pgroupIds ='';
			var pgroupNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					pgroupIds += selData[i].pgroupId+',';
					pgroupNames += selData[i].name+"("+selData[i].pgroupId+")"+',';
				}
				
				pgroupIds = pgroupIds.substring(0,pgroupIds.length-1);
				pgroupNames = pgroupNames.substring(0,pgroupNames.length-1);
				delpGroup(pgroupIds,pgroupNames);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
	}
	
	//删除确认操作
	function delpGroup(currIds,currNames)
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
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/para/paragroupdelete",
						data:{"selCodes":currIds,"anyname":currNames},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#cslb_tableid').bootstrapTable('refresh');
					    }
					});
	            }
	            else
	            {  
	            	
	            }  
	        },  
        });
	}
	
	/*------------------------------------------函数：参数类别表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"pGroupModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"pGroupdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"cslb_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">参数类别</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"cslb_addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"pgroupcode\">类别编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"pgroupId\" id=\"pgroupcode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">类别名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"descr\">类别描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"descr\" id=\"descr\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cslb_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"cslb_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#paramodalid").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#cslb_submit_btn').on("click",function(){
			var name = $('#name').val();
			var descr = $('#descr').val();
			var url = "/para/pgroupsave";
			if(name!=""&& descr!="")
			{
//				if(b_update_name == "")
//				{
//					anyname = s_update_name+name;
//				}else{
//					anyname = s_update_name+b_update_name+"为"+name;
//				}
				if(s_update_name == "")
				{
					anyname = name;
				}else{
					anyname = name + "(" + s_update_name + ")";
				}
				$.ajax({
					type: "POST",
//					dataType: "json",
					url:url,
					data:$.param({'anyname':anyname})+'&'+$('#cslb_addform').serialize(), //formid
				    error: function(request) {
//				        alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#cslb_addform')[0].reset();
				    	$('#pGroupModal').modal('hide');
				    	$('#cslb_tableid').bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("类别名称和类别描述为必填项，请填写！", '');
				//bootbox.alert("类别名称和类别描述为必填项，请填写！","");
			}
		});
		
		$('#cslb_cancel_btn').on("click",function(){
			$('#cslb_addform')[0].reset();
		});
		$('#cslb_close_btn').on("click",function(){
			$('#cslb_addform')[0].reset();
		});
	}
	
	
	
	/*-------------------------------------函数：参数明细增删改-----------------------------------------*/
	
	//工具栏操作初始化
	function init_csmxtool_action()
	{
		//添加
		$('#csmx_btn_add').on("click",function()
		{
			s_update_name = "添加：";
			if($('#paradialogid').length == 0)
			{
				init_csmxmodal();//初始化提交表单
				submit_csmxform();//提交表单操作
			}
			$('#csmx_addform')[0].reset();
			$('#mxpgroupcode').val(curRowData.pgroupId);
			$('#paraModal').modal('show');
		});
		
		//编辑
		$('#csmx_btn_edit').on("click",function()
		{
			if(curparaRowData!="")
			{
				s_update_name = "修改：";
//				b_update_name = curparaRowData.name;
				if($('#paradialogid').length == 0)//判断表单是否已经初始化
				{
					init_csmxmodal();//初始化提交表单
					submit_csmxform();//提交表单操作
				}
				var paraId = curparaRowData.paraId;
				var mxpgroupId = curparaRowData.pgroupId;
				var mxname = curparaRowData.name;
				var mxvalue = curparaRowData.value;
				var mxdescr = curparaRowData.descr;
				var mxseqno = curparaRowData.seqno;
				
				$('#paracode').val(paraId);
				$('#mxpgroupcode').val(mxpgroupId);
				$('#mxname').val(mxname);
				$('#mxvalue').val(mxvalue);
				$('#mxdescr').val(mxdescr);
				$('#mxseqno').val(mxseqno);
				$('#paraModal').modal('show');
			}
			else{
				bootbox.alert("请选择要修改的参数！","");
			}
		});
		
		//删除
		$("#csmx_btn_del").on("click",function()
		{
			var selData = $('#csmx_tableid').bootstrapTable('getSelections');//选中的数据
			var paraIds ='';
			var paraNames ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					paraIds += selData[i].paraId+',';
					paraNames += selData[i].name+"("+selData[i].paraId+")"+',';
				}
				
				paraIds = paraIds.substring(0,paraIds.length-1);
				paraNames = paraNames.substring(0,paraNames.length-1);
				delpara(paraIds,paraNames);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
			
		});
		
	}
	
	//删除确认操作
	function delpara(currIds,currNames)
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
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/para/paradelete",
						data:{"selCodes":currIds,"anyname":currNames},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#csmx_tableid').bootstrapTable('refresh');
					    }
					});
	            }else{  
	            }  
	        },  
        });
	}
	
	/*------------------------------------------函数：参数明细表单-----------------------------------------*/
	
	//初始化表单
	function init_csmxmodal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"paraModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"paradialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"csmx_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">参数明细</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"csmx_addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"paracode\">参数编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"paraId\" id=\"paracode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mxname\">参数名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"mxname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mxvalue\">参数值</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"value\" id=\"mxvalue\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mxdescr\">参数描述</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"descr\" id=\"mxdescr\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mxpgroupcode\">类别编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"pgroupId\" id=\"mxpgroupcode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mxseqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"mxseqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"csmx_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"csmx_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#paramodalid").append(htmlInfo.toString()); 
	    $("#mxpgroupcode").val(curRowData.pgroupId);
	}
	
		
	//提交表单
	function submit_csmxform()
	{
		$('#csmx_submit_btn').on("click",function(){
			var name = $('#mxname').val();
			var value = $('#mxvalue').val();
			var descr = $('#mxdescr').val();
			var url = "/para/parasave";
			if(name!=""&& descr!=""&& value!="")
			{
//				if(b_update_name == "")
//				{
//					anyname = s_update_name+name;
//				}else{
//					anyname = s_update_name+b_update_name+"为"+name;
//				}
				anyname = s_update_name+name;
				$.ajax({
					type: "POST",
//					dataType: "json",
					url:url,
					data:$.param({'anyname':anyname})+'&'+$('#csmx_addform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#csmx_addform')[0].reset();
				    	$('#paraModal').modal('hide');
				    	$('#csmx_tableid').bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("参数名称、参数值和参数描述为必填项，请填写！", '');
				//bootbox.alert("参数名称、参数值和参数描述为必填项，请填写！","");
			}
		});
		
		$('#csmx_cancel_btn').on("click",function(){
			$('#csmx_addform')[0].reset();
		});
		$('#csmx_close_btn').on("click",function(){
			$('#csmx_addform')[0].reset();
		});
	}

	
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
