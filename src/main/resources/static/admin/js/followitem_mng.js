$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var inputType;					//输入框类型
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(inputType == undefined)
		{	//保证仅获取一次
			var inputTypeName = "ITEM_INPUT_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":inputTypeName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	inputType = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#followitem").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		showView(); 
		
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
    /*-------------------------------------主程序：结束  -----------------------------------------*/
	
	/*-------------------------------------函数：显示主数据视图 -----------------------------------------*/
	
	function showView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		
		init_layout();							//初始化table外层的div
		
		init_table();							//初始化table
		
		init_modal_home();						//初始化modal
		
		init_tool_action();						//初始化工具栏操作
			
				
		$('#tableid').bootstrapTable({
			method: 'post', 
			url: "/followtype/list",
			dataType: "json",
			cache: false,
			height: $(window).height()-64,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: queryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler,
			columns: [
				{
					field: 'xh',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '10',
					align: 'center',
					valign: 'left'
				},
				{
					field: 'id',
					title: '序号',
					align: 'center',
					width: '50',
					valign: 'middle',
					visible:false
				},
				{
					field: 'name',
					title: '类型名',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '100',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(data){
				var rows = data.rows;
				for(var i=0;i<rows.length;i++)
				{
					if(rows[i].isDelete == 1)
					{
						// 删除行变色
						$('#tableid tbody').find('tr:eq('+i+')').css("color","#d9534f");
						
					}
				}
            },
            onClickRow:function(){
            	$('#tableid').each(function() { 
        			var self = this; 
        			// 选择行变色 
        			$("tr", $(self)).click(function (){ 
        				var trThis = this; 
        				$(self).find(".trSelected").removeClass('trSelected'); 
        				if ($(trThis).get(0) == $("tr:first", $(self)).get(0)){ 
        					return; 
        				} 
        				$(trThis).addClass('trSelected'); 
        				
        				$('#tableid_right').bootstrapTable('refresh');
        			}); 
        		}); 
            },
            onLoadError: function () {
                //mif.showErrorMessageBox("数据加载失败！");
            }
		});
		
		$('#tableid_right').bootstrapTable({
			method: 'post', 
			url: "/followitem/list",
			dataType: "json",
			cache: false,
			height: $(window).height()-64,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: function (params) {
				var type = $("#tableid tbody .trSelected input").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			type: type,
	    			};
	    		return temp;
			},
			contentType: "application/x-www-form-urlencoded",
			toolbar:'#toolbar_right',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: responseHandler,
			columns: [
				{
					field: 'xh',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '10',
					align: 'center',
					valign: 'left'
				},
				{
					field: 'id',
					title: '序号',
					align: 'center',
					width: '50',
					valign: 'middle',
					visible:false
				},
				{
					field: 'name',
					title: '类型名',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'inputType',
					title: '类型',
					align: 'center',
					width: '100',
					valign: 'middle',
					formatter: inputTypeFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '100',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(data){
				var rows = data.rows;
				for(var i=0;i<rows.length;i++)
				{
					if(rows[i].isDelete == 1)
					{
						// 删除行变色
						$('#tableid tbody').find('tr:eq('+i+')').css("color","#d9534f");
						
					}
				}
            },
            onClickRow:function(){
            	$('#tableid_right').each(function() { 
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
	
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{	
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: (params.offset / params.limit) + 1,		//页码
	    };
	    return temp;
	}
	
	
	//处理服务器端响应数据，使其适应分页格式
	function responseHandler(res)
	{
		if(res != null & res.totalNum > 0)
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
		if($('#container').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\">");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<input type=\"hidden\" id=\"followTypeId\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"container\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\" style=\"padding-top:15px;\">");
			htmlInfo.append("<div id=\"toolbar_right\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add_right\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit_right\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del_right\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<input type=\"hidden\" id=\"followItemId\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"container_right\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#ucmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"ucmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableid\">");
			htmlInfo.append("</table>");
			$("#container").append(htmlInfo.toString()); 
		}
		var htmlInfo_right=new StringBuffer();
		if($('#tableid_right').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo_right.append("<table class=\"table\" id=\"tableid_right\">");
			htmlInfo_right.append("</table>");
			$("#container_right").append(htmlInfo_right.toString()); 
		}
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示用户状态的中文描述
	function inputTypeFormatter(value) {

    	for(var i=0;i<inputType.length;i++)
    	{
    		if(parseInt(inputType[i].parameterValue) == value)
    		{
    			return ['<span>'+inputType[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add').on("click",function()
		{
			if($('#typedialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#typeModal').modal('show');
		});
		
		$('#btn_edit').on("click",function()
		{
			var typeId = $("#tableid tbody .trSelected input").val();
			if(typeId == undefined | typeId == ""){
				bootbox.alert("请选择要编辑的类型");
				return ;
			}
			if($('#typedialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$('#typeModal').modal('show');
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/followtype/selectbyid",
				data:{"id":typeId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	if(data != null){
			    		$("#typeid").val(typeId);
			    		$("#typename").val(data.name);
			    		$("#typeseqno").val(data.seqno);
			    	}else{
			    		bootbox.alert("查询该条数据出错");
			    	}
			    }
			});
			
		});
				
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
			var ids ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					ids += selData[i].id+',';
				}
				
				ids = ids.substring(0,ids.length-1);
				
				bootbox.confirm({  
			        buttons: {  
			            confirm: {
			                label: '是'
			            },  
			            cancel: {  
			                label: '否'  
			            }  
			        },  
			        message: '确认删除该记录吗?',  
			        callback: function(result) {  
			            if(result) {  
			                bootbox.hideAll();
			                $.ajax({
								type: "POST",
								dataType: "json",
								url: "/followtype/delete",
								data:{"ids":ids},
							    error: function(request) {
							    },
							    success: function(data) {
							    	bootbox.alert(data.msg,"");
							    	if(data.code == 200){
							    		$('#tableid').bootstrapTable('refresh');
							    	}							    								    	
							    }
							});
			            } else {  
			            }  
			        },  
		        });
			}else{
				bootbox.alert("请选择要删除的记录！","");
			}
		});
		
		$('#btn_add_right').on("click",function()
		{	
			var typeId = $("#tableid tbody .trSelected input").val();
			if(typeId == undefined | typeId == ""){
				bootbox.alert("请先选择跟进信息类型！","");
				return;
			}
			if($('#itemdialogid').length == 0)
			{
				init_modal_right();//初始化提交表单
				submit_form_right();//提交表单操作
			}
			$('#itemModal').modal('show');
			$("#type").val(typeId);
		});
		
		$('#btn_edit_right').on("click",function()
		{
			var followItemId = $("#tableid_right tbody .trSelected input").val();
			if(followItemId  == undefined | followItemId == ""){
				bootbox.alert("请选择要编辑的数据项");
				return ;
			}
			if($('#itemdialogid').length == 0)
			{
				init_modal_right();//初始化提交表单
				submit_form_right();//提交表单操作
			}
			$('#itemModal').modal('show');
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/followitem/selectbyid",
				data:{"id":followItemId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	if(data != null){
			    		$("#id").val(followItemId);
			    		$("#type").val(data.type);
			    		$("#name").val(data.name);
			    		$("#seqno").val(data.seqno);
			    		$("#inputType").val(data.inputType);
			    		$("#unit").val(data.unit);
			    		$("#inputWidth").val(data.inputWidth);
			    		$("#inputOption").val(data.inputOption);
			    	}else{
			    		bootbox.alert("查询该条数据出错");
			    	}
			    }
			});
			
		});
				
		
		$("#btn_del_right").on("click",function()
		{
			var selData = $('#tableid_right').bootstrapTable('getSelections');//选中的数据
			var ids ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					ids += selData[i].id+',';
				}
				
				ids = ids.substring(0,ids.length-1);
				
				bootbox.confirm({  
			        buttons: {  
			            confirm: {
			                label: '是'
			            },  
			            cancel: {  
			                label: '否'  
			            }  
			        },  
			        message: '确认删除该记录吗?',  
			        callback: function(result) {  
			            if(result) {  
			                bootbox.hideAll();
			                $.ajax({
								type: "POST",
								dataType: "json",
								url: "/followitem/delete",
								data:{"ids":ids},
							    error: function(request) {
							    },
							    success: function(data) {
							    	bootbox.alert(data.msg,"");
							    	if(data.code == 200){
							    		$('#tableid_right').bootstrapTable('refresh');
							    	}							    								    	
							    }
							});
			            } else {  
			            }  
			        },  
		        });
			}else{
				bootbox.alert("请选择要删除的记录！","");
			}
		});
	}
	
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();	
		
		htmlInfo.append("<div class=\"modal fade\" id=\"typeModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"typedialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"typeclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\">跟进信息类型</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"typeaddform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"typename\" type=\"text\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"typeseqno\">序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"typeseqno\" type=\"number\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");		
		
		htmlInfo.append("<input type=\"hidden\" id=\"typeid\" name=\"id\" >");		
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"typecancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"typesubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#ucmodalid").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#typesubmit_btn').on("click",function(){
			var name = $("#typename").val();
			var seqno = $("#typeseqno").val();
			if(name != '' & seqno !=''){
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/followtype/save",
					data:$('#typeaddform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	if(data.code == 200){
				    		$('#typeaddform')[0].reset();
					    	$('#typeModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
				    	}				    	
				    }
				});
			}else{
				showError("类型名和序号必填！", '');
			}
			
		});
		
		$('#typecancel_btn').on("click",function(){
			$('#typeaddform')[0].reset();
		});
		$('#typeclose_btn').on("click",function(){
			$('#typeaddform')[0].reset();
		});
	}	
	//初始化表单
	function init_modal_right()
	{
		var htmlInfo=new StringBuffer();	
		
		htmlInfo.append("<div class=\"modal fade\" id=\"itemModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"itemdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\">跟进信息类型</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"inputType\">输入类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"inputType\" name=\"inputType\" class=\"form-control\">");
    	for(var i=0;i<inputType.length;i++)
    	{
    		htmlInfo.append("<option value="+inputType[i].parameterValue+">"+inputType[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"inputWidth\">输入框长度</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"inputWidth\" id=\"inputWidth\" type=\"number\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"unit\">单位</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"unit\" id=\"unit\" type=\"text\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"inputOption\">选项</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<textarea id=\"inputOption\" name=\"inputOption\" class=\"form-control\" rows=\"3\"></textarea>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"number\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");		
		
		htmlInfo.append("<input type=\"hidden\" id=\"id\" name=\"id\" >");	
		htmlInfo.append("<input type=\"hidden\" id=\"type\" name=\"type\" >");	
		
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
	    $("#ucmodalid").append(htmlInfo.toString()); 
	}
	
		
	//提交表单
	function submit_form_right()
	{
		$('#submit_btn').on("click",function(){
			var name = $("#name").val();
			var seqno = $("#seqno").val();
			var inputType = $("#inputType").val();
			if(name != '' & seqno !='' & inputType !=''){
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/followitem/save",
					data:$('#addform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	if(data.code == 200){
				    		$('#addform')[0].reset();
					    	$('#itemModal').modal('hide');
					    	$('#tableid_right').bootstrapTable('refresh');
				    	}				    	
				    }
				});
			}else{
				showError("名称、类型、序号必填！", '');
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