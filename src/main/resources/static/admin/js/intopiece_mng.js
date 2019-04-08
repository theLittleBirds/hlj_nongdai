$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var ipStatusParaDS;				//进件状态参数集
	var use;						//客户类型参数集
	var totalSyncDS = 2;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var appType;					//应用场景类型
//	var creditCapital;					//授信额度
	var show_count = 20;   //要显示的条数
	var count = 1;    //递增的开始值，这里是你的ID
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(ipStatusParaDS == undefined)
		{	//保证仅获取一次
			var stsParaGrpName = "INTOPIECE_STATUS";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":stsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	ipStatusParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		if(use == undefined)
		{	//保证仅获取一次
			var useParaGrpName = "CUSTOMER_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":useParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	use = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#intopiece_id").on("click", function(){
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
		
		//列操作事件定义
		var operateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "edit")
			{
				edit(row.id);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
			else if(e.currentTarget.id == "submit_primary"){
				submit_primary(row);
			}
			else if(e.currentTarget.id == "oper_change"){
				oper_change(row);
			}
			else if(e.currentTarget.id == "abandon"){
				abandon(row);
			}
			else if(e.currentTarget.id == "continued_loan"){
				continued_loan(row);
			}
			else if(e.currentTarget.id == "applyInfo"){
				applyInfo(row);
			}
			else if(e.currentTarget.id == "dynamicInfo"){
				dynamicInfo(row);
			}
			else if(e.currentTarget.id == "memberInfo"){
				memberInfo(row);
			}
			else if(e.currentTarget.id == "transfer_land"){
				transferland(row);
			}
		}};		
		
		$('#tableid').bootstrapTable({
			method: 'post', 
			url: "/intopiece/list",
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
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'id',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'fullCname',
					title: '部门',
					align: 'center',
					width: '80',
					valign: 'middle'
				},
				{
					field: 'operName',
					title: '操作人员',
					align: 'center',
					width: '50',
					valign: 'middle'
					
				},
				{
					field: 'use',
					title: '客户类型',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: useFormat
				},
				{
					field: 'memberName',
					title: '姓名',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'phone',
					title: '手机号',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'appName',
					title: '应用场景',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'capital',
					title: '申请金额（元）',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: examineResult
				},
				{
					field: 'term',
					title: '申请期数（月）',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'creDate',
					title: '申请时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '100',
					valign: 'middle',
					formatter: ipactionFormatter,
					events: operateEvents
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
		var orgName = $("#q_orgName").val();
		var memberName = $("#q_memberName").val();
		var idCard = $("#q_idCard").val();
		var phone = $("#q_phone").val();
		var status = $("#q_status").val();
		var use = $("#q_use").val();
		var capital = $("#q_capital").val();
		var appName = $("#q_appName").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: (params.offset / params.limit) + 1,		//页码
	      orgName: orgName,
	      memberName: memberName,
	      idCard: idCard,
	      phone: phone,
	      use:use,
	      capital:capital,
	      appName:appName,
	      status: status,
	      startDate:startDate,
	      endDate:endDate
	    };
	    return temp;
	}
	
	/*-------------------------------------函数：视图-----------------------------------------*/
	
	//初始化table外层的div
	function init_layout()
	{
		if($('#ipid').length == 0)
		{
			$("#content-main").empty();
			var htmlInfo = new StringBuffer();

			htmlInfo.append("<div class=\"form-inline\" style=\"margin-top:20px;\">");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_orgName\">部门:</label>");
			htmlInfo.append("<input type=\"text\" class=\"form-control\" id=\"q_orgName\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_memberName\">姓名:</label>");
			htmlInfo.append("<input type=\"text\" class=\"form-control\" id=\"q_memberName\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_idCard\">身份证号:</label>");
			htmlInfo.append("<input type=\"text\" class=\"form-control\" id=\"q_idCard\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_phone\">手机号:</label>");
			htmlInfo.append("<input type=\"text\" class=\"form-control\" id=\"q_phone\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_phone\">状态:</label>");
			htmlInfo.append("<select class=\"form-control\" id=\"q_status\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for (var i = 0; i < ipStatusParaDS.length; i++) {
				htmlInfo.append("<option value=" + ipStatusParaDS[i].parameterValue + ">" + ipStatusParaDS[i].parameterName + "</option>");
			}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_capital\">申请金额:</label>");
			htmlInfo.append("<input type=\"number\" class=\"form-control\" id=\"q_capital\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_appName\">产品:</label>");
			htmlInfo.append("<input type=\"text\" class=\"form-control\" id=\"q_appName\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"q_use\">客户类型:</label>");
			htmlInfo.append("<select class=\"form-control\" id=\"q_use\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("<option value='1'>新增</option>");
			htmlInfo.append("<option value='2'>转贷</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"startDate\">开始时间:</label>");
			htmlInfo.append("<div class=\"input-group date\"  id ='start'>");
			htmlInfo.append("<input id=\"startDate\" type=\"text\"  class=\"form-control required\"/>");
			htmlInfo.append("<span class=\"input-group-addon\" ><span class=\"glyphicon glyphicon-calendar\"></span></span>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"control-label\" for=\"endDate\">结束时间:</label>");
			htmlInfo.append("<div class=\"input-group date\"  id ='end'>");
			htmlInfo.append("<input id=\"endDate\" type=\"text\"  class=\"form-control required\"/>");
			htmlInfo.append("<span class=\"input-group-addon\" ><span class=\"glyphicon glyphicon-calendar\"></span></span>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" id=\"btn_query\"><i class=\"fa fa-search\"></i> 检索</button>");
			htmlInfo.append("<button class=\"btn btn-primary\" style=\"margin-left:10px;\" type=\"button\" id=\"btn_export\"><i class=\"fa fa-search\"></i> 导出</button>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_intopiece\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>进件");
			htmlInfo.append("</button>");
			/*htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");*/
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"ipid\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
			
			$("#start").datepicker({
		        todayBtn: "linked",
		        keyboardNavigation: false,
		        forceParse: false,
		        autoclose: true
		    });
			$("#end").datepicker({
		        todayBtn: "linked",
		        keyboardNavigation: false,
		        forceParse: false,
		        autoclose: true
		    });
			
			$("#btn_export").on("click",function(){
				var orgName = $("#q_orgName").val();
				var memberName = $("#q_memberName").val();
				var idCard = $("#q_idCard").val();
				var phone = $("#q_phone").val();
				var status = $("#q_status").val();
				var use = $("#q_use").val();
				var capital = $("#q_capital").val();
				var appName = $("#q_appName").val();
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var para = "?orgName="+orgName+"&memberName="+memberName+"&idCard="+idCard+"&phone="+phone
				+"&status="+status+"&use="+use+"&capital="+capital+"&appName="+appName+"&startDate="+startDate+"&endDate="+endDate;
				window.location.href= "/intopiece/intopieceexport"+para;
			});
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
			htmlInfo.append("<div id=\"rjmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
			htmlInfo.append("<div class=\"modal fade\" id=\"operModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\"operdialogid\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\"addform1\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"operUserId\">操作人员</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"operUserId\" name=\"operUserId\" class=\"form-control\">");
			htmlInfo.append("</select>");
			htmlInfo.append("<input hidden=\"hidden\" name=\"id\" id=\"intoPieceIdoper\" type=\"text\" />");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn_oper\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn_oper\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
	    $("#ucmodalid").append(htmlInfo.toString()); 
	}
	//拒件模态窗
	function init_modalrj()
	{
		var htmlInfo=new StringBuffer();
		htmlInfo.append("<div class=\"modal fade\" id=\"rjModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"rjdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"rejectform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"operUserId\">拒绝原因</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<textarea id=\"rjReason\" name=\"rjReason\" class=\"form-control\" rows=\"3\"></textarea>");
		htmlInfo.append("<input hidden=\"hidden\" name=\"id\" id=\"intoPieceIdreject\" type=\"text\" />");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn_rj\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn_rj\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#rjmodalid").append(htmlInfo.toString()); 
	}
	//初始化table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#tableid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"tableid\">");
			htmlInfo.append("</table>");
			$("#ipid").append(htmlInfo.toString()); 
		}
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示状态的中文描述
	function statusFormatter(value, row, index) {

    	for(var i=0;i<ipStatusParaDS.length;i++)
    	{
    		if(parseInt(ipStatusParaDS[i].parameterValue) == value)
    		{
    			if(parseInt(ipStatusParaDS[i].parameterValue) == 0){
    				return ["<a id=\"rejectReason\" href=\"#\" onclick=\"rejectReason('"+row.id+"')\">"+ipStatusParaDS[i].parameterName+"</a>"];
    			}else{
    				return ['<span>'+ipStatusParaDS[i].parameterName+'</span>'];
    			}
    				
    			
	    	}
    	}
	}
	
	function useFormat(value, row, index){
		if(value == ''){
			return '-';
		}
		for(var i=0;i<use.length;i++)
    	{
    		if(parseInt(use[i].parameterValue) == value)
    		{
    			return use[i].parameterName;
	    	}
    	}
	}
	
	function examineResult(value, row, index){
		if(row.status > 5&&row.status<14){
			return value+"<a onclick=\"iframeChange('/examine/result?id="+row.id+"')\">(审核结论)</a>";
		}else{
			return value;
		}
	}
	
	//table里面的操作列显示
	function ipactionFormatter(value, row, index) {
		//如果状态是补全资料中，显示提交初审按钮
		if(row.status == "1"){
			return [
				       '<a class="operate" id="edit" href="#"><span>详情</span></a></br>',
//				       '<a class="operate" id="del" href="#"><span>拒绝</span></a>'+'</br>',
				       '<a class="operate" id="submit_primary" href="#"><span>提交初审</span></a></br>',
//				       '<a class="operate" id="oper_change" href="#"><span>分配人员</span></a></br>',
				       '<a class="operate" id="continued_loan" href="#"><span>续贷</span></a></br>',
				       '<a class="operate" id="abandon" href="#"><span>放弃贷款</span></a>'
				       /*,
				       '<a class="operate" id="transfer_land" href="#"><span>流转合同备案回执</span></a>'*/
				     ].join('   ');
		}else if(row.status == "2"||row.status == "3"||row.status == "4"||row.status == "5"){
			return [
				       '<a class="operate" id="edit" href="#"><span>详情</span></a></br>',
//				       '<a class="operate" id="del" href="#"><span>拒绝</span></a>'+'</br>',
//				       '<a class="operate" id="oper_change" href="#"><span>分配人员</span></a></br>',
				       '<a class="operate" id="continued_loan" href="#"><span>续贷</span></a><br/>',
				       '<a class="operate" id="abandon" href="#"><span>放弃贷款</span></a>'
				       /*,
				       '<a class="operate" id="applyInfo" href="#"><span>导出申请表</span></a>',
				       '<a class="operate" id="transfer_land" href="#"><span>流转合同备案回执</span></a>'
				       ,
				       '<a class="operate" id="dynamicInfo" href="#"><span>导出动态数据</span></a>'*/
				     ].join('   ');
		}else if(row.status == "0"){
			return [
				       '<a class="operate" id="edit" href="#"><span>详情</span></a></br>',
//				       '<a class="operate" id="oper_change" href="#"><span>分配人员</span></a></br>',
				       '<a class="operate" id="continued_loan" href="#"><span>续贷</span></a>'
				       /*,
				       '<a class="operate" id="applyInfo" href="#"><span>导出申请表</span></a>',
				       '<a class="operate" id="transfer_land" href="#"><span>流转合同备案回执</span></a>'*/
				       /*,
				       '<a class="operate" id="dynamicInfo" href="#"><span>导出动态数据</span></a>'*/
				     ].join('   ');
		}else if(row.status == "6"||row.status == "7"||row.status == "8"){
			return [
				       '<a class="operate" id="edit" href="#"><span>详情</span></a><br/>',
				       //'<a class="operate" id="oper_change" href="#"><span>分配人员</span></a>',
				       '<a class="operate" id="continued_loan" href="#"><span>续贷</span></a><br/>',
				       '<a class="operate" id="abandon" href="#"><span>放弃贷款</span></a>'
				       /*,
				       '<a class="operate" id="applyInfo" href="#"><span>导出申请表</span></a>',
				       '<a class="operate" id="memberInfo" href="#"><span>导出客户信息</span></a>',
				       '<a class="operate" id="transfer_land" href="#"><span>流转合同备案回执</span></a>'*/
				     ].join('   ');
		}else{
			return [
				       '<a class="operate" id="edit" href="#"><span>详情</span></a></br>',
//				       '<a class="operate" id="oper_change" href="#"><span>分配人员</span></a></br>',
				       '<a class="operate" id="continued_loan" href="#"><span>续贷</span></a>'
				       /*,
				       '<a class="operate" id="applyInfo" href="#"><span>导出申请表</span></a>',
				       '<a class="operate" id="memberInfo" href="#"><span>导出客户信息</span></a>',
				       '<a class="operate" id="transfer_land" href="#"><span>流转合同备案回执</span></a>'*/
				     ].join('   ');
		}
	}
	

    //工具栏操作初始化
	function init_tool_action()
	{
		$("#btn_query").on("click",function(){
			$('#tableid').bootstrapTable('refresh');
		});
		$("#btn_intopiece").on("click",function(){
			initIntoPiece();
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
						delMany(ids);//删除确认
					}else{
						bootbox.alert("请选择要删除的用户！","");
					}
				});
	}
	
	function delMany(ids)
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
	        message: '确认删除该用户吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/intopiece/delete",
						data:{"ids":ids},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#tableid').bootstrapTable('refresh');
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	//拒件
	function delOne(entry)
	{
		if($('#rjdialogid').length == 0)//判断表单是否已经初始化
		{
			init_modalrj();		//初始化提交表单
			submit_formrj();		//提交表单操作
		}
		$('#rjReason').empty();
		$('#intoPieceIdreject').val(entry.id);
		$('#rjModal').modal('show');		
	}
	//提交初审
	function submit_primary(entry){

//		bootbox.dialog({
//			message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>提交中</span>",
//			closeButton: false,
//			className: "submitPrimaryModal",
//		});
//		$.ajax({
//			type: "POST",
//			dataType: "json",
//			url: "/intopiece/submitprimary",
//			data:$('#submitPrimary').serialize(),
//		    error: function(request) {		
//		    	 $('.submitPrimaryModal').remove();
//		    },
//		    success: function(data) {	
//		    	$('.submitPrimaryModal').remove();
//		    	bootbox.alert(data.msg);
//		    	if(data.code == 200){
//		    		removeBootBox("bootboxModal");
//		    		$('#tableid').bootstrapTable('refresh');
//		    	}
//		    }
//		});  
	
		
		
		
//		$.ajax({
//			type: "POST",
//			dataType: "json",
//			url: "/family/coland",
//			data:{"intoPieceId":entry.id},
//		    error: function(request) {
//		    },
//		    success: function(data) {
//		    	if(data.code == 200){
//		    		var serviceFee = data.serviceFee;
		    		var htmlInfo = new StringBuffer();
		    		htmlInfo.append("<form id=\"submitPrimary\" onsubmit=\"return submitPrimary()\">");					
					htmlInfo.append("<div class=\"ibox-content clearfix\">");
						/*htmlInfo.append("<label><<土地流转合同>>：线下签署</label><br/>");						
						htmlInfo.append("<label><<委托服务合同>>：线下签署</label><br/>");
						htmlInfo.append("<label><<土地共有人授权书>>：</label><br/>");						
						if(data.list!=null & data.list!=''){
							var list = data.list;
							for (var int = 0; int < list.length; int++) {
								htmlInfo.append("<label style='margin-left:30px;'>"+list[int].name+"<label><input type='radio' style='margin-left:15px;' name='"+list[int].id+"' value='1'>线上</label><label><input type='radio' style='margin-left:15px;' name='"+list[int].id+"' value='2'>线下</label></label><br/>");
							}
						}*/
						htmlInfo.append("<input type='hidden' name='intoPieceId' value='"+entry.id+"'>");
//						htmlInfo.append("<label >扣款方式：</label><select  id=\"serviceFeeWay\" name=\"serviceFeeWay\" class=\"form-control\" ><option value=\"\">--请选择--</option><option value=\"1\">站长代付</option><option value=\"2\">微信支付</option><option value=\"4\">暂不支付</option></select><br/>");
//						htmlInfo.append("<label  >首期服务费(元)：</label> <input type=\"text\" id=\"serviceFee\" disabled='disabled' value=\""+serviceFee+"\" class=\"form-control\"><input type=\"hidden\" id=\"serviceFeeFirst\"  name=\"serviceFee\" value=\""+serviceFee+"\" class=\"form-control\"><br/>");
						htmlInfo.append("<label >工作站意见：</label><textarea id=\"trail_opinion\"  name=\"trail_opinion\" class=\"form-control\" ></textarea><br/>");
					htmlInfo.append("</div>");
					htmlInfo.append("<div class=\"clearfix form-actions\">");
					htmlInfo.append("<div class=\"text-center\">");
					htmlInfo.append("<button class=\"btn btn-w-m\" type=\"button\" onclick=\"removeBootBox('bootboxModal')\">");
					htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-80\"></i>取消</button>");
					htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"button\" id='submitPrimary_btn'>");
					htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-80\"></i>提交</button>");
					htmlInfo.append("</div>");
					htmlInfo.append("</div>");
					htmlInfo.append("</form>");
		    		bootbox.dialog({
		    			message: htmlInfo.toString(),
		    			closeButton: false,
		    			className: "bootboxModal",
		    		});		    		
//		    	}else{
//		    		
//		    	}
		    	$("#submitPrimary_btn").on("click",function(){
		    		bootbox.dialog({
		    			message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>提交中</span>",
		    			closeButton: false,
		    			className: "submitPrimaryModal",
		    		});
		    		$.ajax({
		    			type: "POST",
		    			dataType: "json",
		    			url: "/intopiece/submitprimary",
		    			data:$('#submitPrimary').serialize(),
		    		    error: function(request) {	
		    		    	removeBootBox("bootboxModal");
		    		    	 $('.submitPrimaryModal').remove();
		    		    	 bootbox.alert(data.msg);
		    		    },
		    		    success: function(data) {	
		    		    	$('.submitPrimaryModal').remove();
		    		    	if(data.code == 200){
		    		    		removeBootBox("bootboxModal");
		    		    		$('#tableid').bootstrapTable('refresh');
		    		    		bootbox.alert(data.msg);
		    		    	}else{
		    		    		removeBootBox("bootboxModal");
		    		    		bootbox.alert(data.msg);
		    		    	}
		    		    }
		    		});  
				});
//		    }
		   		    
//		});
		/*bootbox.confirm({  
	        buttons: {  
	            confirm: {
	                label: '线上签署'
	            },  
	            cancel: {  
	                label: '线下签署'  
	            }  
	        },  
	        message: '请选择合同签署方式',  
	        callback: function(result) { 
	        	//默认线下签署
	        	var signType = "2";
	        	if(result) { 
	        		signType = "1";
	        	}
	        	bootbox.hideAll();
	        	bootbox.dialog({
	    			message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>提交中</span>",
	    			closeButton: false,
	    			className: "bootboxModal",
	    		});
	        	$.ajax({
	    			type: "POST",
	    			dataType: "json",
	    			url: "/intopiece/submitprimary",
	    			data:{"intoPieceId":entry.id,"signType":signType},
	    		    error: function(request) {		
	    		    	removeBootBox("bootboxModal");
	    		    },
	    		    success: function(data) {
	    		    	removeBootBox("bootboxModal");
	    		    	bootbox.alert(data.msg);
	    		    	if(data.code == 200){
	    		    		$('#tableid').bootstrapTable('refresh');
	    		    	}
	    		    }
	    		});
	        } 
	    });		*/
	}
	
	//放弃贷款
	function abandon(entry){
		bootbox.confirm({  
			buttons: {  
	        confirm: {
				label: '放弃'
	        },  
	        cancel: {  
	        	label: '不放弃'
	        }  
	        },  
	        message: '是否确定要放弃贷款，放弃贷款将无法继续申请贷款并退还已交服务费！',  
	        callback: function(result) {
	        	if(result){
	        		$.ajax({
				        url: "/intopiece/abandon", // 请求的URL
				        dataType: 'json',
				        type: "post",
				        data:{"intoPieceId":entry.id},
				        success: function (data) {
				        	if(data.errono==2000){
				        		bootbox.alert(data.msg);
					    		$("#intopiece_id").click();
				        	}else{
				        		showError(data.msg, '');
				        	}
				        }
				    });
	        	}else{
	        		
	        	}
	        }
		});
	}

	
	//续贷
	function continued_loan(entry){
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/para/someparalist",
			data:{"someParaGroupName":"ID_CARD_TYPE,EDUCATION_LEVEL,MARITAL_STATUS,DUTY,SEX,CUSTOMER_TYPE"},
		    error: function(request) {					
		    },
		    success: function(data) {
			    var id_card_type = eval(data.ID_CARD_TYPE);
			    var education_level = eval(data.EDUCATION_LEVEL);
			    var marital_status = eval(data.MARITAL_STATUS);
			    var duty = eval(data.DUTY);
			    var sex = eval(data.SEX);
			    var use = eval(data.CUSTOMER_TYPE);
				$("#content-main").empty();
				var htmlInfo = new StringBuffer();
				htmlInfo.append("<h3>主借人</h3>");
				htmlInfo.append("<div class=\"ibox-content clearfix\">");
				htmlInfo.append("<form id=\"intopieceform\" class=\"form-horizontal col-sm-10 m-t\" onsubmit=\"return postintopiece()\">");
				
				htmlInfo.append("<input type=\"hidden\" name=\"continuedLoanId\" id=\"continuedLoanId\" >");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"bindOrg\">部门：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\" >");
				htmlInfo.append("<select id=\"orgId\" name=\"orgId\" onchange=\"searchModelByOrg()\" class=\"form-control required\">");
				htmlInfo.append("<option value=''>--请选择--</option>");
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">应用场景：<span style=\"color: red\">（必选）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<select id=\"modelId\" name=\"modelId\"  class=\"form-control required\">");
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">客户类型：</label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<select id=\"use\" name=\"use\" class=\"form-control required\">");
				htmlInfo.append("<option value=''>--请选择--</option>");
				for(var i=0;i<use.length;i++)
				{
					htmlInfo.append("<option value="+use[i].parameterValue+">"+use[i].parameterName+"</option>");
				}
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">申请期限：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<div class=\"input-group\"> <input type=\"number\" id=\"term\" name=\"term\" class=\"form-control required\"/>");
				htmlInfo.append("<div class=\"input-group-addon\">月</div>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");		
				
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">借款用途备注：</label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				htmlInfo.append("<textarea id=\"useRemark\" name=\"useRemark\" class=\"form-control\" rows=\"3\"></textarea>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
			
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">客户姓名：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"memberName\" type=\"text\" class=\"form-control required\" maxlength=\"50\" name=\"memberName\" />");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"idCard\" onchange=\"idMetch()\" type=\"text\" class=\"form-control required idCard\" maxlength=\"18\" name=\"idCard\">");
				htmlInfo.append("</div>");
				
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"age\" type=\"number\" class=\"form-control required age\" maxlength=\"2\" name=\"age\">");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">性别：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<select id=\"sex\" name=\"sex\" class=\"form-control required\">");
				htmlInfo.append("<option value=''>--请选择--</option>");
				for(var i=0;i<sex.length;i++)
				{
					htmlInfo.append("<option value="+sex[i].parameterValue+">"+sex[i].parameterName+"</option>");
				}
				htmlInfo.append("</select>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号码：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"phone\" type=\"text\" class=\"form-control required phone\" maxlength=\"11\" name=\"phone\">");
				htmlInfo.append("</div>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">文化程度：<span style=\"color: red\">（必选）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append(" <select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control required\">");
				htmlInfo.append("<option value=''>--请选择--</option>");
				for(var i=0;i<education_level.length;i++)
				{
					htmlInfo.append("<option value="+education_level[i].parameterValue+">"+education_level[i].parameterName+"</option>");
				}
				htmlInfo.append(" </select>");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">婚姻状况：<span style=\"color: red\">（必选）</span></label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control required\">");
				htmlInfo.append("<option value=''>--请选择--</option>");
				for(var i=0;i<marital_status.length;i++)
				{
					htmlInfo.append("<option value="+marital_status[i].parameterValue+">"+marital_status[i].parameterName+"</option>");
				}
				htmlInfo.append(" </select>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡开户行：</label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append(" <input id=\"bank\" type=\"text\" class=\"form-control \" name=\"bank\" >");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡账号：</label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append(" <input id=\"bankCardNo\" type=\"text\" class=\"form-control \" name=\"bankCardNo\"> ");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">近6个月详细住址：<span style=\"color: red\">（必填）</span></label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				htmlInfo.append(" <input id=\"address\" type=\"text\" class=\"form-control required\" maxlength=\"255\" name=\"address\" >");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				/*htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"duty\">从事职业</label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				for(var i =0;i<duty.length;i++){
					htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"duty\" id=\"duty"+duty[i].parameterValue+"\" value=\""+duty[i].parameterValue+"\" />"+duty[i].parameterName+"</label>");
				}
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");*/
//				htmlInfo.append("</div>");
				
				/*htmlInfo.append("<div class=\"form-group duty4_hide\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">非农职业 -- 单位名称：</label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"nonfarmComName\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComName\" >");
				htmlInfo.append("</div>");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">单位电话：</label>");
				htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<input id=\"nonfarmComPhone\" type=\"text\" class=\"form-control\" maxlength=\"20\" name=\"nonfarmComPhone\" >");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");*/
				
				/*htmlInfo.append("<div class=\"form-group duty4_hide\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">单位地址：</label>");
				htmlInfo.append("<div class=\"col-sm-8\">");
				htmlInfo.append("<input id=\"nonfarmComAddress\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComAddress\" >");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");*/
				
				htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
				htmlInfo.append("<div class=\"col-md-12 text-center\">");
				htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
				htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
				htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("</form>");
				htmlInfo.append("</div>");
				$("#content-main").append(htmlInfo.toString());
				
				$(".duty4_hide").each(function(i){
					   $(this).hide();
				});
				$("#duty4").click(function(){
					if($(this).is(':checked')){
						$(".duty4_hide").each(function(i){
							   $(this).show();
						});
					}else{
						$(".duty4_hide").each(function(i){
							   $(this).hide();
						});
					}				
				});
				
				//部门
				$.ajax({
			        url: "/intopiece/getOrgs", // 请求的URL
			        dataType: 'json',
			        type: "post",
			        data:{"intoPieceId":entry.id},
			        success: function (data) {
			        	if(data.errono==2000){
		    				$("#orgId").empty();
		    				$("#orgId").append(data.result);
			        	}
			        }
			    });
				//产品
				$.ajax({
					type: "POST",
					url:"/intopiece/modelList",
					data:{"intoPieceId":entry.id},
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	$("#modelId").empty();
				    	$("#modelId").append(data);
				    }
				});
				
				$.ajax({
					url: "/intopiece/getById", // 请求的URL
			        dataType: 'json',
			        type: "post",
			        data:{"intoPieceId":entry.id},
			        success: function (data) {
			        	var model = data.model;
			        	$("#modelId").val(model.modelId);
			        	$("#continuedLoanId").val(model.id);
						$("#capital").val(model.capital);
						$("#term").val(model.term);
						$("#phone").val(model.phone);
						//都为转贷
						$("#use").val(2);
						$("#useRemark").val(model.useRemark);
						$("#memberName").val(model.memberName);
						$("#sex").val(model.sex);
						$("#age").val(model.age);
						$("#idCard").val(model.idCard);
						$("#educationLevel").val(model.educationLevel);
						$("#maritalStatus").val(model.maritalStatus);
						$("#bank").val(model.bank);
						$("#bankCardNo").val(model.bankCardNo);
						$("#address").val(model.address);
						$("#nonfarmComName").val(model.nonfarmComName);
						$("#nonfarmComPhone").val(model.nonfarmComPhone);
						$("#nonfarmComAddress").val(model.nonfarmComAddress);
						var duty = model.duty;
						if(duty != null & duty != ''){
							var detail = duty.split(",");
							if(detail.indexOf(4) == -1){
								$(".duty4_hide").each(function(i){
									   $(this).hide();
								});
							}								
							for (var int = 0; int < detail.length; int++) {
								$("#duty"+detail[int]).prop("checked",true);
							}
						}
						
						if(edit == "1"){
							$("#submit_btn").empty();
							var html= new StringBuffer();
							html.append("<div class=\"col-md-12 text-center\">");
							html.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
							html.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
							html.append("</div>");
							$("#submit_btn").append(html.toString());
						}
			       }
				});
		    }
		});
	}
	
	function oper_change(entry){
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/intopiece/operChange",
			data:{"intoPieceId":entry.id},
			error: function(request) {					
			},
			success: function(data) {
				
				if($('#operdialogid').length == 0)//判断表单是否已经初始化
				{
					init_modal();		//初始化提交表单
					submit_form();		//提交表单操作
				}
				$('#operUserId').empty();
				$('#intoPieceIdoper').val(entry.id);
				if(data.errno == 2000){
					$('#operUserId').append(data.result);
				}else{
					bootbox.alert(data.msg);
				}
				$('#operModal').modal('show');
//				if(data.code == 200){
//					$('#tableid').bootstrapTable('refresh');
//				}
			}
		});
	}
	
	function submit_form()
	{
		$('#submit_btn_oper').on("click",function(){
			var operUserId = $('#operUserId').val();
			var url = "/intopiece/operUserIdChange";
			if(operUserId!=""){
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$('#addform1').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	if(data.errno==2000){
				    		bootbox.alert(data.msg,"");
					    	$('#addform1')[0].reset();
					    	$('#operModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
				    	}else{
				    		showError(data.msg, '');
				    	}
				    	}
				});
			}else{
				showError("请选择操作人员！", '');
			}
				
				
		});	
		$('#cancel_btn_oper').on("click",function(){
			$('#addform1')[0].reset();
		});
		
	}
	
	//拒件模态窗口提交按钮初始化
	function submit_formrj()
	{
		$('#submit_btn_rj').on("click",function(){
			var rjReason = $('#rjReason').val();
			var url = "/intopiece/reject";
			if(rjReason!=""){
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$('#rejectform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	if(data.errno==2000){
				    		bootbox.alert(data.msg,"");
					    	$('#rejectform')[0].reset();
					    	$('#rjModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
				    	}else{
				    		showError(data.msg, '');
				    	}
				    	}
				});
			}else{
				showError("填写拒件原因！", '');
			}
				
				
		});	
		$('#cancel_btn_rj').on("click",function(){
			$('#rejectform')[0].reset();
		});
		
	}
	
	
	function edit(id)
	{
		//填充详情页面
		init_edit(id,'');
		//默认加载基本信息
		basic();
	}
	
	function applyInfo(row){
		window.location.href= "/intopiece/applyInfo?intopieceId="+row.id;
	}
	function dynamicInfo(row){
		window.location.href= "/intopiece/dynamicDateExport?intopieceId="+row.id;
	}
	function memberInfo(row){
		window.location.href= "/intopiece/memberInfo?intopieceId="+row.id;
	}
	function transferland(row){
		window.location.href= "/intopiece/transferland?intopieceId="+row.id;
	}
	
});

function rejectReason(id) {
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/intopiece/rejectReason",
		data:{"id":id},
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	if(data.errno==3000){
	    		bootbox.alert(data.result);
	    	}else{
	    		layer.open({
	    			  type: 1, 
	    			  content: data.result,
	    			  move: false
	    			});
	    	}
	    }
	});
	
}

function initIntoPiece(){
	//获取数据字典
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/para/someparalist",
		data:{"someParaGroupName":"ID_CARD_TYPE,EDUCATION_LEVEL,MARITAL_STATUS,DUTY,SEX,CUSTOMER_TYPE"},
	    error: function(request) {					
	    },
	    success: function(data) {
		    var id_card_type = eval(data.ID_CARD_TYPE);
		    var education_level = eval(data.EDUCATION_LEVEL);
		    var marital_status = eval(data.MARITAL_STATUS);
		    var duty = eval(data.DUTY);
		    var sex = eval(data.SEX);
		    var use = eval(data.CUSTOMER_TYPE);
			$("#content-main").empty();
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<h3>主借人</h3>");
			htmlInfo.append("<div class=\"ibox-content clearfix\">");
			htmlInfo.append("<form id=\"intopieceform\" class=\"form-horizontal col-sm-10 m-t\" onsubmit=\"return postintopiece()\">");
			
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id=\"id\" >");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"bindOrg\">部门：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\" >");
			htmlInfo.append("<select id=\"orgId\" name=\"orgId\" onchange=\"searchModelByOrg()\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">应用场景：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"modelId\" name=\"modelId\" disabled=true class=\"form-control required\">");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">客户类型：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"use\" name=\"use\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<use.length;i++)
			{
				htmlInfo.append("<option value="+use[i].parameterValue+">"+use[i].parameterName+"</option>");
			}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">申请期限：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<div class=\"input-group\"> <input type=\"number\" id=\"term\" name=\"term\" class=\"form-control required\"/>");
			htmlInfo.append("<div class=\"input-group-addon\">月</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");		
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">借款用途备注：</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<textarea id=\"useRemark\" name=\"useRemark\" class=\"form-control\" rows=\"3\"></textarea>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
		
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">客户姓名：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"memberName\" type=\"text\" class=\"form-control required\" maxlength=\"50\" name=\"memberName\" />");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"idCard\" onchange=\"idMetch()\" type=\"text\" class=\"form-control required idCard\" maxlength=\"18\" name=\"idCard\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"age\" type=\"number\" class=\"form-control required age\" maxlength=\"2\" name=\"age\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">性别：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"sex\" name=\"sex\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<sex.length;i++)
			{
				htmlInfo.append("<option value="+sex[i].parameterValue+">"+sex[i].parameterName+"</option>");
			}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号码：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"phone\" type=\"text\" class=\"form-control required phone\" maxlength=\"11\" name=\"phone\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">文化程度：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<education_level.length;i++)
			{
				htmlInfo.append("<option value="+education_level[i].parameterValue+">"+education_level[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">婚姻状况：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<marital_status.length;i++)
			{
				htmlInfo.append("<option value="+marital_status[i].parameterValue+">"+marital_status[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡开户行：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bank\" type=\"text\" class=\"form-control required\" name=\"bank\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡账号：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bankCardNo\" type=\"text\" class=\"form-control required\" name=\"bankCardNo\"> ");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">近6个月详细住址：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append(" <input id=\"address\" type=\"text\" class=\"form-control required\" maxlength=\"255\" name=\"address\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			/*htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"duty\">从事职业</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			for(var i =0;i<duty.length;i++){
				htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"duty\" id=\"duty"+duty[i].parameterValue+"\" value=\""+duty[i].parameterValue+"\" />"+duty[i].parameterName+"</label>");
			}
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");*/
//			htmlInfo.append("</div>");
			
			/*htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">非农职业 -- 单位名称：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComName\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComName\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">单位电话：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComPhone\" type=\"text\" class=\"form-control\" maxlength=\"20\" name=\"nonfarmComPhone\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">单位地址：</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input id=\"nonfarmComAddress\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComAddress\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");*/
			
			htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
			htmlInfo.append("<div class=\"col-md-12 text-center\">");
			htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
			htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
			
			$(".duty4_hide").each(function(i){
				   $(this).hide();
			});
			$("#duty4").click(function(){
				if($(this).is(':checked')){
					$(".duty4_hide").each(function(i){
						   $(this).show();
					});
				}else{
					$(".duty4_hide").each(function(i){
						   $(this).hide();
					});
				}				
			});
			
			//部门
			$.ajax({
		        url: "/intopiece/getOrgs", // 请求的URL
		        dataType: 'json',
		        type: "post",
		        success: function (data) {
		        	if(data.errono==2000){
	    				$("#orgId").empty();
	    				$("#orgId").append(data.result);
		        	}
		        }
		    });
	    }
	});
}

function searchModelByOrg(){
	var orgId = $("#orgId").val();
	if(orgId!=''){
		search(orgId);
	}else{
		$("#modelId").empty();
		$("#modelId").prop("disabled",true);
	}
}

function search(orgId){
	//产品
	$.ajax({
		type: "POST",
		url:"/intopiece/modelList",
		data:{"orgId":orgId},
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	$("#modelId").empty();
	    	$("#modelId").append(data);
	    	$("#modelId").prop("disabled",false);
	    }
	});
}

/*-------------------------------------进件详情-----------------------------------------*/		
function init_edit(id,url){
	$("#content-main").empty();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<div class=\"wrapper wrapper-content\" style=\"padding: 10px 10px 0 10px;\">");		
		htmlInfo.append("<div id=\"edit_left\" class=\"col-sm-2\" style=\"padding-right: 10px;\">");
			htmlInfo.append("<div class=\"ibox\" style=\"margin-bottom: 0;\">");
				htmlInfo.append("<div class=\"ibox-title\" style=\"padding-left: 18px;\"><h5>类型</h5></div>");
				htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding: 0px 10px 20px 10px;\">");
					htmlInfo.append("<ul class=\"folder-list m-b-md\" style=\"padding: 0; font-size: 12px;\">");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"basic()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">主借人</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"lands()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">土地信息</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"applydetail()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">申请信息</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"family()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">家庭情况</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"operate()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">经营情况</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"assets()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">资产及负债</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"credit()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">征信情况</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"dynamic()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">审核记录</a></li>");
					/*htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"contact()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">通讯录</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"otherContact()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">其他联系人</a></li>");*/
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"media()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">图片信息</a></li>");
					htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"file()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">文档信息</a></li>");
					/*htmlInfo.append("<li style=\"height: 30px; line-height: 5px;\"><a onclick=\"qimoCallOut()\" class=\"fa fa-database\" style=\"padding-left: 10px; margin-top :10px;\">外呼记录</a></li>");*/
					htmlInfo.append("</ul>");
					htmlInfo.append("<input type=\"hidden\" id=\"intoPieceId\" value='"+id+"'/>");
					htmlInfo.append("<input type=\"hidden\" id=\"intoPiece_Edit\"/>");
				htmlInfo.append("</div>");
				if(url != ''){
					htmlInfo.append("<div class=\"col-md-12 text-center\">");
					htmlInfo.append("<button class=\"btn\" type=\"button\" onclick=\"iframeChange('"+url+"')\">");
					htmlInfo.append("<i class=\"ace-icon fa fa-reply bigger-110\"></i>返回");
					htmlInfo.append("</button>");
					htmlInfo.append("</div>");
				}
			htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div id=\"edit_right\" class=\"col-sm-10\" style=\"padding-left: 0;\">");
			htmlInfo.append("<div class=\"ibox\" style=\"margin-bottom: 0;\">");
				htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding: 0;\" id=\"right_container\">");
				htmlInfo.append("</div>");
			htmlInfo.append("</div>");
		htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	$("#content-main").append(htmlInfo.toString());
}
//*************************************************进件详情***********************************//
//***********************************基本信息开始**********************************************//
//基本信息
function basic(){
	//获取数据字典
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/para/someparalist",
		data:{"someParaGroupName":"ID_CARD_TYPE,EDUCATION_LEVEL,MARITAL_STATUS,DUTY,SEX,CUSTOMER_TYPE"},
	    error: function(request) {					
	    },
	    success: function(data) {
		    var id_card_type = eval(data.ID_CARD_TYPE);
		    var education_level = eval(data.EDUCATION_LEVEL);
		    var marital_status = eval(data.MARITAL_STATUS);
		    var duty = eval(data.DUTY);
		    var sex = eval(data.SEX);
		    var use = eval(data.CUSTOMER_TYPE);
			$("#right_container").empty();
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<h3>主借人</h3>");
			htmlInfo.append("<div class=\"ibox-content clearfix\">");
			htmlInfo.append("<form id=\"intopieceform\" class=\"form-horizontal col-sm-10 m-t\" onsubmit=\"return postintopiece()\">");
			
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id=\"id\" >");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"bindOrg\">部门：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\" >");
			htmlInfo.append("<select id=\"orgId\" disabled=\"disabled\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">应用场景：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"modelId\" class=\"form-control required\" disabled=\"disabled\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">客户类型：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"use\" name=\"use\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<use.length;i++)
			{
				htmlInfo.append("<option value="+use[i].parameterValue+">"+use[i].parameterName+"</option>");
			}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">申请期限：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<div class=\"input-group\"> <input type=\"number\" id=\"term\" name=\"term\" class=\"form-control required\"/>");
			htmlInfo.append("<div class=\"input-group-addon\">月</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");			
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">借款用途备注：</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<textarea id=\"useRemark\" name=\"useRemark\" class=\"form-control\" rows=\"3\"></textarea>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
		
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">客户姓名：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"memberName\" type=\"text\" class=\"form-control required\" maxlength=\"50\" name=\"memberName\" />");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"idCard\" onchange=\"idMetch()\" type=\"text\" class=\"form-control required idCard\" maxlength=\"18\" name=\"idCard\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"age\" type=\"number\" class=\"form-control required age\" maxlength=\"2\" name=\"age\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">性别：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"sex\" name=\"sex\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<sex.length;i++)
			{
				htmlInfo.append("<option value="+sex[i].parameterValue+">"+sex[i].parameterName+"</option>");
			}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号码：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"phone\" type=\"text\" class=\"form-control required phone\" maxlength=\"11\" name=\"phone\">");
			htmlInfo.append("</div>");
			/*htmlInfo.append("<div class=\"col-sm-5\">");
			htmlInfo.append("<input onclick=\"basicOutCall('Local');\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
			htmlInfo.append("<input onclick=\"basicOutCall('gateway');\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
			htmlInfo.append("</div>");*/
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">文化程度：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<education_level.length;i++)
			{
				htmlInfo.append("<option value="+education_level[i].parameterValue+">"+education_level[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">婚姻状况：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<marital_status.length;i++)
			{
				htmlInfo.append("<option value="+marital_status[i].parameterValue+">"+marital_status[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡开户行：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bank\" type=\"text\" class=\"form-control\" name=\"bank\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">银行卡账号：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bankCardNo\" type=\"text\" class=\"form-control\" name=\"bankCardNo\"> ");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">近6个月详细住址：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append(" <input id=\"address\" type=\"text\" class=\"form-control required\" maxlength=\"255\" name=\"address\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			/*htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\" for=\"duty\">从事职业</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			for(var i =0;i<duty.length;i++){
				htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"duty\" id=\"duty"+duty[i].parameterValue+"\" value=\""+duty[i].parameterValue+"\" />"+duty[i].parameterName+"</label>");
			}
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");*/
//			htmlInfo.append("</div>");
			
			/*htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">非农职业 -- 单位名称：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComName\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComName\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">单位电话：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComPhone\" type=\"text\" class=\"form-control\" maxlength=\"20\" name=\"nonfarmComPhone\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">单位地址：</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input id=\"nonfarmComAddress\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComAddress\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");*/
			
			htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\" id=\"submit_btn\">");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			$("#right_container").append(htmlInfo.toString());
			
			$("#duty4").click(function(){
				if($(this).is(':checked')){
					$(".duty4_hide").each(function(i){
						   $(this).show();
					});
				}else{
					$(".duty4_hide").each(function(i){
						   $(this).hide();
					});
				}				
			});
			var intoPieceId = $("#intoPieceId").val();
			
			
			//部门
			$.ajax({
		        url: "/intopiece/getOrgs", // 请求的URL
		        dataType: 'json',
		        type: "post",
		        data:{"intoPieceId":intoPieceId},
		        success: function (data) {
		        	if(data.errono==2000){
	    				$("#orgId").empty();
	    				$("#orgId").append(data.result);
		        	}
		        }
			});
			//产品
			$.ajax({
				type: "POST",
				url:"/intopiece/modelList",
				data:{"intoPieceId":intoPieceId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	$("#modelId").empty();
			    	$("#modelId").append(data);
			    }
			});
			
			$.ajax({
				url: "/intopiece/getById", // 请求的URL
		        dataType: 'json',
		        type: "post",
		        data:{"intoPieceId":intoPieceId},
		        success: function (data) {
		        	var model = data.model;
		        	//进件是否可编辑 0 不可编辑     1可以编辑
		        	var edit = data.edit;
		        	$("#intoPiece_Edit").val(edit);
					$("#modelId").val(model.modelId);
					$("#capital").val(model.capital);
					$("#term").val(model.term);
					$("#phone").val(model.phone);
					if(model.use != ''){
						$("#use").val(model.use);
					}					
					$("#useRemark").val(model.useRemark);
					$("#memberName").val(model.memberName);
					$("#sex").val(model.sex);
					$("#age").val(model.age);
					$("#idCard").val(model.idCard);
					$("#educationLevel").val(model.educationLevel);
					$("#maritalStatus").val(model.maritalStatus);
					$("#bank").val(model.bank);
					$("#bankCardNo").val(model.bankCardNo);
					$("#address").val(model.address);
					$("#nonfarmComName").val(model.nonfarmComName);
					$("#nonfarmComPhone").val(model.nonfarmComPhone);
					$("#nonfarmComAddress").val(model.nonfarmComAddress);
					var duty = model.duty;
					if(duty != null & duty != ''){
						var detail = duty.split(",");
						if(detail.indexOf(4) == -1){
							$(".duty4_hide").each(function(i){
								   $(this).hide();
							});
						}								
						for (var int = 0; int < detail.length; int++) {
							$("#duty"+detail[int]).prop("checked",true);
						}
					}
					
					if(edit == "1"){
						$("#submit_btn").empty();
						var html= new StringBuffer();
						html.append("<div class=\"col-md-12 text-center\">");
						html.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
						html.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
						html.append("</div>");
						$("#submit_btn").append(html.toString());
					}
		       }
			});
			

	    }
	});
}
//保存基本信息
function postintopiece(){
	var test = 1;
	$("form .required").each(function(){
		var value = $(this).val();
        if(value==''){
        	$(this).focus();
        	bootbox.alert("必填项不能为空");
            test = 0;
            return false;
        }
	});
	if(test == 0){
		return false;
	}
	var url = "/intopiece/save";
	if($("#intoPieceId").val() == undefined){
		bootbox.dialog({
			message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>信息核实中，请稍后</span>",
			closeButton: false,
			className: "ipbootboxModal",
		});
		//新增
		$.ajax({
			type: "POST",
			dataType: "json",
			url:"/intopiece/save",
			data:$('#intopieceform').serialize(), //formid
		    error: function(request) {
		    	removeBootBox("ipbootboxModal");
		    },
		    success: function(data) {
		    	removeBootBox("ipbootboxModal");
		    	if(data.errono==1000){
		    		bootbox.alert(data.msg);
		    	}else{
		    		bootbox.alert(data.msg);
		    		$("#intopiece_id").click();
		    	}
		    }
		});
	}else{
		//修改
		$("#id").val($("#intoPieceId").val());
		$.ajax({
			type: "POST",
			dataType: "json",
			url:"/intopiece/update",
			data:$('#intopieceform').serialize(), //formid
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	if(data.errono==1000){
		    		bootbox.alert(data.msg);
		    	}else{
		    		bootbox.alert(data.msg,"");
		    	}
		    }
		});
	}
	return false;
}
//主借人外呼
function basicOutCall(ExtenType){
	var intoPieceId = $("#intoPieceId").val();
	var phone = $("#phone").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/qimooutcall/callout",
		data:{"intoPieceId":intoPieceId,"phone":phone,"ExtenType":ExtenType}, 
	    success: function(data) {
	    	bootbox.alert(data.msg,"");
	    }
	});
}
//***********************************基本信息结束**********************************************//
//***********************************家庭情况开始**********************************************//
//家庭情况
function family(){
	//获取数据字典
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/para/someparalist",
		data:{"someParaGroupName":"YES_NO,HAVE"},
	    error: function(request) {					
	    },
	    success: function(data) {
			$("#right_container").empty();
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<h3>家庭情况</h3>");
			htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
			htmlInfo.append("<form id=\"familyForm\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postfamilyform()\">");
			htmlInfo.append("<div class=\"info\" style=\"background-color: #FFF; padding-top: 10px;\" width=\"100%\">");
			htmlInfo.append("<ul>");
			htmlInfo.append("<li onclick=\"changeFamilyTab(1,this)\" class=\"active\">父亲</li><li onclick=\"changeFamilyTab(2,this)\">母亲</li><li onclick=\"changeFamilyTab(3,this)\">配偶</li><li onclick=\"changeFamilyTab(4,this)\">第一子女</li><li onclick=\"changeFamilyTab(5,this)\">第二子女</li><li onclick=\"changeFamilyTab(6,this)\">第三子女</li><li onclick=\"changeFamilyTab(7,this)\">其他</li>");
			htmlInfo.append("</ul>");
			htmlInfo.append("<div id=\"familydata\" style=\"margin-top: 20px;\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin: 10px 0;\">");
			if($("#intoPiece_Edit").val() == "1"){
				htmlInfo.append("<div class=\"col-md-9 text-center\">");
				htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\" id=\"family_btn\">");
				htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
				htmlInfo.append("</div>");
			}			
			htmlInfo.append("</div>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			$("#right_container").append(htmlInfo.toString());
			//默认加载父亲
			loadFamilyData(1);
	    }
	});
	
}
//sheet页点击事件
function changeFamilyTab(type,obj){
	if (obj) {
        $(".info > ul").find("li").each(function (i, d) {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active");
            }
        });
        $(obj).addClass("active");
    }
	loadFamilyData(type);
}
//加载家庭成员
function loadFamilyData(type){
var intoPieceId = $("#intoPieceId").val();
//获取数据字典
$.ajax({
	type: "POST",
	dataType: "json",
	url: "/para/someparalist",
	data:{"someParaGroupName":"YES_NO,HEALTH_STATUS,SEX,MARITAL_STATUS,EDUCATION_LEVEL"},
    error: function(request) {					
    },
    success: function(data) {
		$("#familydata").empty();
		var htmlInfo = new StringBuffer();
		if(type == 1 || type == 2){
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id = \"id\" />");
			htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" value='"+intoPieceId+"'/>");
			htmlInfo.append("<input type=\"hidden\" name=\"type\" value="+type+" />");
			htmlInfo.append("<div class=\"form-group\">");
				htmlInfo.append("<label class=\"col-sm-2 control-label\">是否去世：</label>");
				htmlInfo.append("<div class=\"col-sm-2\">");
					htmlInfo.append("<select id=\"isDead\" name=\"isDead\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
				htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">姓名：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"number\" name=\"age\" id=\"age\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"idCard\" onchange=\"idMetch()\" id=\"idCard\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"phone\" id=\"phone\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	        	/*htmlInfo.append("<input onclick=\"familyCallOut('Local')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	        	htmlInfo.append("<input onclick=\"familyCallOut('gateway')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	    	*/htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">是否同住：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label status_hide\">不同住时地址：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2 status_hide\">");
	        		htmlInfo.append("<input type=\"text\" name=\"liveAddress\" id=\"liveAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">健康状况：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"healthStatus\" name=\"healthStatus\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.HEALTH_STATUS != null){
						var healthStatus = eval(data.HEALTH_STATUS);
						for(var i=0;i<healthStatus.length;i++)
						{
							htmlInfo.append("<option value="+healthStatus[i].parameterValue+">"+healthStatus[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label healthStatus_hide\">疾病描述：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2 healthStatus_hide\">");
	        		htmlInfo.append("<input type=\"text\" name=\"diseaseRemark\" id=\"diseaseRemark\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">从事农业相关生产经营：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"duty\" name=\"duty\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	        	htmlInfo.append("<label class=\"col-sm-2 control-label\">能否作为无限连带责任人：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"coBorrower\" name=\"coBorrower\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");	    	
	    	
	    	htmlInfo.append("<div class=\"form-group isDead_hide\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">职业信息：</label>");
	        	htmlInfo.append("<div class=\"col-sm-6\">");
	        		htmlInfo.append("<input type=\"text\" name=\"nonfarmComAddress\" id=\"nonfarmComAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");		    	
	    	htmlInfo.append("</div>");
		}
		else if(type == 3){
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id = \"id\"/>");
			htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" value='"+intoPieceId+"'/>");
			htmlInfo.append("<input type=\"hidden\" name=\"type\" value="+type+" />");
			htmlInfo.append("<input type=\"hidden\" name=\"coBorrower\" value='1' />");
			
			htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">姓名：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"number\" name=\"age\" id=\"age\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"idCard\" onchange=\"idMetch()\" id=\"idCard\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"phone\" id=\"phone\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	        	/*htmlInfo.append("<input onclick=\"familyCallOut('Local')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	        	htmlInfo.append("<input onclick=\"familyCallOut('gateway')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	    	*/htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">是否同住：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label status_hide\">不同住时地址：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2 status_hide\">");
	        		htmlInfo.append("<input type=\"text\" name=\"liveAddress\" id=\"liveAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">健康状况：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"healthStatus\" name=\"healthStatus\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.HEALTH_STATUS != null){
						var healthStatus = eval(data.HEALTH_STATUS);
						for(var i=0;i<healthStatus.length;i++)
						{
							htmlInfo.append("<option value="+healthStatus[i].parameterValue+">"+healthStatus[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label healthStatus_hide\">疾病描述：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2 healthStatus_hide\">");
	        		htmlInfo.append("<input type=\"text\" name=\"diseaseRemark\" id=\"diseaseRemark\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">文化程度：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.EDUCATION_LEVEL != null){
						var education = eval(data.EDUCATION_LEVEL);
						for(var i=0;i<education.length;i++)
						{
							htmlInfo.append("<option value="+education[i].parameterValue+">"+education[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">从事农业相关生产经营：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"duty\" name=\"duty\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");	    
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">职业信息：</label>");
	        	htmlInfo.append("<div class=\"col-sm-6\">");
	        		htmlInfo.append("<input type=\"text\" name=\"nonfarmComAddress\" id=\"nonfarmComAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");		    	
	    	htmlInfo.append("</div>");
		}
		else if(type == 4 || type == 5 || type == 6){
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id = \"id\"/>");
			htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" value='"+intoPieceId+"'/>");
			htmlInfo.append("<input type=\"hidden\" name=\"type\" value="+type+" />");

			htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">姓名：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"number\" name=\"age\" id=\"age\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"idCard\" id=\"idCard\" onchange=\"idMetch()\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"phone\" id=\"phone\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	        	/*htmlInfo.append("<input onclick=\"familyCallOut('Local')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	        	htmlInfo.append("<input onclick=\"familyCallOut('gateway')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	    	*/htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">性别：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"sex\" name=\"sex\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.SEX != null){
						var sex = eval(data.SEX);
						for(var i=0;i<sex.length;i++)
						{
							htmlInfo.append("<option value="+sex[i].parameterValue+">"+sex[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	        	htmlInfo.append("<label class=\"col-sm-2 control-label\">是否同住：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">是否在校：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"school\" name=\"school\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label school_hide\">婚姻状况：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2 school_hide\">");
			    	htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.MARITAL_STATUS != null){
						var marital = eval(data.MARITAL_STATUS);
						for(var i=0;i<marital.length;i++)
						{
							htmlInfo.append("<option value="+marital[i].parameterValue+">"+marital[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">文化程度：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.EDUCATION_LEVEL != null){
						var education = eval(data.EDUCATION_LEVEL);
						for(var i=0;i<education.length;i++)
						{
							htmlInfo.append("<option value="+education[i].parameterValue+">"+education[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	        	htmlInfo.append("<label class=\"col-sm-2 control-label\">能否作为无限连带责任人：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"coBorrower\" name=\"coBorrower\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">读院校/单位地址：</label>");
	        	htmlInfo.append("<div class=\"col-sm-6\">");
	        		htmlInfo.append("<input type=\"text\" name=\"nonfarmComAddress\" id=\"nonfarmComAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");		    	
	    	htmlInfo.append("</div>");
		}
		else if(type == 7){
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id = \"id\"/>");
			htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" value='"+intoPieceId+"'/>");
			htmlInfo.append("<input type=\"hidden\" name=\"type\" value="+type+" />");

			htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">姓名：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">年龄：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"number\" name=\"age\" id=\"age\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">身份证号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"idCard\" id=\"idCard\" onchange=\"idMetch()\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">手机号：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
	        		htmlInfo.append("<input type=\"text\" name=\"phone\" id=\"phone\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");
	        	/*htmlInfo.append("<input onclick=\"familyCallOut('Local')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	        	htmlInfo.append("<input onclick=\"familyCallOut('gateway')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">");
	    	*/htmlInfo.append("</div>");
		    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">性别：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"sex\" name=\"sex\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.SEX != null){
						var sex = eval(data.SEX);
						for(var i=0;i<sex.length;i++)
						{
							htmlInfo.append("<option value="+sex[i].parameterValue+">"+sex[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">婚姻状况：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
			    	htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.MARITAL_STATUS != null){
						var marital = eval(data.MARITAL_STATUS);
						for(var i=0;i<marital.length;i++)
						{
							htmlInfo.append("<option value="+marital[i].parameterValue+">"+marital[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div class=\"form-group\">");
	        	htmlInfo.append("<label class=\"col-sm-2 control-label\">能否作为无限连带责任人：</label>");
	        	htmlInfo.append("<div class=\"col-sm-2\">");
		        	htmlInfo.append("<select id=\"coBorrower\" name=\"coBorrower\" class=\"form-control\">");
					htmlInfo.append("<option value=''>--请选择--</option>");
					if(data.YES_NO != null){
						var yes_no = eval(data.YES_NO);
						for(var i=0;i<yes_no.length;i++)
						{
							htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
						}
					}
					htmlInfo.append("</select>");
	        	htmlInfo.append("</div>");	        	
	    	htmlInfo.append("</div>");	    
    	
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">居住地址：</label>");
	        	htmlInfo.append("<div class=\"col-sm-6\">");
	        		htmlInfo.append("<input type=\"text\" name=\"nonfarmComAddress\" id=\"nonfarmComAddress\" class=\"form-control\"/>");
	        	htmlInfo.append("</div>");		    	
	    	htmlInfo.append("</div>");
		}
		$("#familydata").append(htmlInfo.toString());
		
		$(".isDead_hide").each(function(i){
			   $(this).hide();
 		});
		$(".status_hide").each(function(i){
			   $(this).hide();
 		});
		$(".healthStatus_hide").each(function(i){
			   $(this).hide();
		});
		$(".school_hide").each(function(i){
			   $(this).hide();
		});
		$("#isDead").change(function(){
			if($(this).val() == 2){
				$(".isDead_hide").each(function(i){
					   $(this).show();
				});
			}else{
				$(".isDead_hide").each(function(i){
					   $(this).hide();
				});
			}
		});
		$("#status").change(function(){
			if($(this).val() == 2){
				$(".status_hide").each(function(i){
					   $(this).show();
				});
			}else{
				$(".status_hide").each(function(i){
					   $(this).hide();
				});
			}
		});
		$("#healthStatus").change(function(){
			if($(this).val() == 3){
				$(".healthStatus_hide").each(function(i){
					   $(this).show();
				});
			}else{
				$(".healthStatus_hide").each(function(i){
					   $(this).hide();
				});
			}
		});
		$("#school").change(function(){
			if($(this).val() == 2){
				$(".school_hide").each(function(i){
					   $(this).show();
				});
			}else{
				$(".school_hide").each(function(i){
					   $(this).hide();
				});
			}
		});
		
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/family/getbytype",
			data:{"intoPieceId":intoPieceId,"type":type},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	if(data != null){
		    		if(data.type == 1 || type == 2){
		    			$("#id").val(data.id);
		    			$("#isDead").val(data.isDead);
		    			if(data.isDead == 2){
		    				$(".isDead_hide").each(function(i){
		 					   $(this).show();
		 				});
		    			}
		    			$("#name").val(data.name);
		    			$("#age").val(data.age);
		    			$("#idCard").val(data.idCard);
		    			$("#phone").val(data.phone);
		    			$("#status").val(data.status);
		    			if(data.status == 2){
		    				$(".status_hide").each(function(i){
		    					   $(this).show();
		    				});
		    			}
		    			$("#liveAddress").val(data.liveAddress);
		    			$("#healthStatus").val(data.healthStatus);
		    			if(data.healthStatus == 3){
		    				$(".healthStatus_hide").each(function(i){
		    					   $(this).show();
		    				});
		    			}
		    			$("#diseaseRemark").val(data.diseaseRemark);
		    			$("#duty").val(data.duty);
		    			$("#coBorrower").val(data.coBorrower);
		    			$("#nonfarmComAddress").val(data.nonfarmComAddress);
		    		}
		    		else if(data.type == 3){
		    			$("#id").val(data.id);
		    			$("#name").val(data.name);
		    			$("#age").val(data.age);
		    			$("#idCard").val(data.idCard);
		    			$("#phone").val(data.phone);
		    			$("#status").val(data.status);
		    			if(data.status == 2){
		    				$(".status_hide").each(function(i){
		    					   $(this).show();
		    				});
		    			}
		    			$("#liveAddress").val(data.liveAddress);
		    			$("#healthStatus").val(data.healthStatus);
		    			if(data.healthStatus == 3){
		    				$(".healthStatus_hide").each(function(i){
		    					   $(this).show();
		    				});
		    			}
		    			$("#diseaseRemark").val(data.diseaseRemark);
		    			$("#educationLevel").val(data.educationLevel);
		    			$("#duty").val(data.duty);
		    			$("#nonfarmComAddress").val(data.nonfarmComAddress);
		    		}
		    		else if(type == 4 || type == 5 || type == 6){
		    			$("#id").val(data.id);
		    			$("#name").val(data.name);
		    			$("#age").val(data.age);
		    			$("#idCard").val(data.idCard);
		    			$("#phone").val(data.phone);
		    			$("#sex").val(data.sex);
		    			$("#status").val(data.status);
		    			$("#maritalStatus").val(data.maritalStatus);
		    			$("#educationLevel").val(data.educationLevel);
		    			$("#coBorrower").val(data.coBorrower);
		    			$("#school").val(data.school);
		    			if(data.school == 2){
		    				$(".school_hide").each(function(i){
		    					   $(this).show();
		    				});
		    			}
		    			$("#nonfarmComAddress").val(data.nonfarmComAddress);
		    		}
		    		else if(type == 7){
		    			$("#id").val(data.id);
		    			$("#name").val(data.name);
		    			$("#age").val(data.age);
		    			$("#idCard").val(data.idCard);
		    			$("#phone").val(data.phone);
		    			$("#sex").val(data.sex);
		    			$("#maritalStatus").val(data.maritalStatus);
		    			$("#coBorrower").val(data.coBorrower);
		    			$("#nonfarmComAddress").val(data.nonfarmComAddress);
		    		}
		    	}		    	
		    }
		});
    }
});
}
//提交家庭信息
function postfamilyform(){
	$("#family_btn").attr("disabled", "disabled");
	bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>信息核实中，请稍后</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/family/save",
		data:$('#familyForm').serialize(), //formid
	    error: function(request) {
	    	removeBootBox("bootboxModal");
	    },
	    success: function(data) {
	    	removeBootBox("bootboxModal");
	    	$("#family_btn").removeAttr("disabled");    	
	    	if(data.msg != ''){
	    		bootbox.alert(data.msg);
	    	}else{
	    		bootbox.alert("保存出错了");
	    	}	    	
	    }
	});
	return false;
}

//家庭成员外呼
function familyCallOut(ExtenType){
	var phone = $("#phone").val();
	var intoPieceId = $("#intoPieceId").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/qimooutcall/callout",
		data:{"intoPieceId":intoPieceId,"phone":phone,"ExtenType":ExtenType,"member_name":name,"from":"memberSelf"}, 
	    success: function(data) {
	    	bootbox.alert(data.msg,"");
	    }
	});
}
//***********************************家庭情况结束**********************************************//
//***********************************经营情况结束**********************************************//
function operate(){
$.ajax({
	type: "POST",
	dataType: "json",
	url: "/para/someparalist",
	data:{"someParaGroupName":"BIG_LAND_CROP,HISTORY_OPERATE,CASH_CROP_TYPE,LIVES_TOCK_TYPE,YES_NO,HAVE,NONG_SELL_TYPE"},
    error: function(request) {					
    },
    success: function(data) {
    	var bigLandCrop = eval(data.BIG_LAND_CROP);
    	var History_Operate = eval(data.HISTORY_OPERATE);
    	var cashCropType = eval(data.CASH_CROP_TYPE);
    	var livestockType = eval(data.LIVES_TOCK_TYPE);
    	var have = eval(data.HAVE);
    	var nongSellType = eval(data.NONG_SELL_TYPE);
		$("#right_container").empty();
		var htmlInfo = new StringBuffer();
		htmlInfo.append("<h3>经营情况</h3>");
		htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
		htmlInfo.append("<form id=\"operate\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postoperate()\">");
			
	    	htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<label class=\"col-sm-2 control-label\">经营类型：</label>");
	        	htmlInfo.append("<div class=\"col-sm-8\">");
						htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\"  name=\"operateType\" id=\"operateType1\" value=\"1\" onclick=\"operateHide(this)\"/>种植</label>");
						htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"operateType\" id=\"operateType3\" value=\"3\"  onclick=\"operateHide(this)\"/>养殖</label>");
						htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"operateType\" id=\"operateType4\" value=\"4\"  onclick=\"operateHide(this)\"/>农资/农产品经销</label>");
						htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"operateType\" id=\"operateType5\" value=\"5\"  onclick=\"operateHide(this)\"/>非农职业(含打工)</label>");
	        	htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div id=\"operate_hide1\" style=\"display: none;\">");
		    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
					htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">种植</span>");
				htmlInfo.append("</div>");
				htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
				
				htmlInfo.append("<div class=\"form-group\">");
					htmlInfo.append("<label class=\"col-sm-2 control-label\">种植类型：</label>");
					htmlInfo.append("<div class=\"col-sm-4\">");
					for(var i =0;i<bigLandCrop.length;i++){
						if(i==bigLandCrop.length-1){
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"bigLandCropType\" id=\"bigLandCropType"+bigLandCrop[i].parameterValue+"\" onclick=\"showhideoblc(this)\" value=\""+bigLandCrop[i].parameterValue+"\" />"+bigLandCrop[i].parameterName+"</label>");
						}else{
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"bigLandCropType\" id=\"bigLandCropType"+bigLandCrop[i].parameterValue+"\" value=\""+bigLandCrop[i].parameterValue+"\" />"+bigLandCrop[i].parameterName+"</label>");
						}
					}
					htmlInfo.append("</div>");
					htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"otherBlcRemark\" style=\"display: none;\" id=\"otherBlcRemark\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">种植规模（公顷）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"blcScale\" id=\"blcScale\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">种植年限（年）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"blcYears\" id=\"blcYears\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">种植预计收入（万元）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"blcExpectedValue\" id=\"blcExpectedValue\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">收获时间（月份）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"blcHarvestTime\" id=\"blcHarvestTime\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	/*htmlInfo.append("<label class=\"col-sm-2 control-label\">化肥等投入（万元/亩/年）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"blcInvestment\" id=\"blcInvestment\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");*/
		        	htmlInfo.append("<label class=\"col-sm-2 control-label\">历史经营情况：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"blcHistoryOperate\" name=\"blcHistoryOperate\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<History_Operate.length;i++)
			    			{
			    				htmlInfo.append("<option value="+History_Operate[i].parameterValue+">"+History_Operate[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");	    	
	    	htmlInfo.append("</div>");	    		    
	    	
	    	htmlInfo.append("<div id=\"operate_hide3\" style=\"display: none;\">");
		    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
					htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">养殖</span>");
				htmlInfo.append("</div>");
				htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
				
				htmlInfo.append("<div class=\"form-group\">");
					htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖类型：</label>");
					htmlInfo.append("<div class=\"col-sm-4\">");
					for(var i =0;i<livestockType.length;i++){
						if(i==livestockType.length-1){
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"livestockType\" onclick=\"showhidels(this)\" id=\"livestockType"+livestockType[i].parameterValue+"\" value=\""+livestockType[i].parameterValue+"\" />"+livestockType[i].parameterName+"</label>");
						}else{
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"livestockType\" id=\"livestockType"+livestockType[i].parameterValue+"\" value=\""+livestockType[i].parameterValue+"\" />"+livestockType[i].parameterName+"</label>");
						}
					}
					htmlInfo.append("</div>");
					htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"otherLivestockRemark\" id=\"otherLivestockRemark\" style=\"display: none;\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖规模（只/亩）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"livestockScale\" id=\"livestockScale\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖年限（年）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"livestockYears\" id=\"livestockYears\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖物预计价值（万元）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"livestockExpectedValue\" id=\"livestockExpectedValue\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖场地：</label>");
			    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"livestockSiteType\" name=\"livestockSiteType\" class=\"form-control\"  style=\"margin-top:-10px\" onchange=\"livestockSiteType_hide()\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
			    		htmlInfo.append("<option value=\"1\">租用</option>");
			    		htmlInfo.append("<option value=\"2\">自建</option>");
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<div id=\"livestockSiteType_hide1\" style=\"display: none;\">");
			        	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖场地租金（元/年）：</label>");
			        	htmlInfo.append("<div class=\"col-sm-2\">");
			        		htmlInfo.append("<input type=\"text\" name=\"livestockSiteRent\" id=\"livestockSiteRent\" class=\"form-control\"/>");
			        	htmlInfo.append("</div>");
		        	htmlInfo.append("</div>");
		        	htmlInfo.append("<div id=\"livestockSiteType_hide2\" style=\"display: none;\">");
			        	htmlInfo.append("<label class=\"col-sm-2 control-label\">养殖场地自建投入（万元）：</label>");
			        	htmlInfo.append("<div class=\"col-sm-2\">");
			        		htmlInfo.append("<input type=\"text\" name=\"livestockSiteInvestment\" id=\"livestockSiteInvestment\" class=\"form-control\"/>");
			        	htmlInfo.append("</div>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">环评证照：</label>");
			    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"eiaCertificate\" name=\"eiaCertificate\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
		        	htmlInfo.append("<label class=\"col-sm-2 control-label\">历史经营情况：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"livestockHistoryOperate\" name=\"livestockHistoryOperate\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<History_Operate.length;i++)
			    			{
			    				htmlInfo.append("<option value="+History_Operate[i].parameterValue+">"+History_Operate[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");	    	
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div id=\"operate_hide4\" style=\"display: none;\">");
		    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
					htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">农资/农产品经销 </span>");
				htmlInfo.append("</div>");
				htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
				
				htmlInfo.append("<div class=\"form-group\">");
					htmlInfo.append("<label class=\"col-sm-2 control-label\">类型：</label>");
					htmlInfo.append("<div class=\"col-sm-4\">");
					for(var i =0;i<nongSellType.length;i++){
						if(i==nongSellType.length-1){
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"nongSellType\" onclick=\"showhidens(this)\" id=\"nongSellType"+nongSellType[i].parameterValue+"\" value=\""+nongSellType[i].parameterValue+"\" />"+nongSellType[i].parameterName+"</label>");
						}else{
							htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"nongSellType\" id=\"nongSellType"+nongSellType[i].parameterValue+"\" value=\""+nongSellType[i].parameterValue+"\" />"+nongSellType[i].parameterName+"</label>");
						}
						
					}					
					htmlInfo.append("</div>");
					htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"otherNsRemark\" style=\"display: none;\" id=\"otherNsRemark\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">销售规模（万元/月）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"nsScale\" id=\"nsScale\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">从事销售年限（年）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"nsYears\" id=\"nsYears\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");		    	
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">销售场地：</label>");
			    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"nsSiteType\" name=\"nsSiteType\" class=\"form-control\"  style=\"margin-top:-10px\" onchange=\"nsSiteType_hide()\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
			    		htmlInfo.append("<option value=\"1\">租用</option>");
			    		htmlInfo.append("<option value=\"2\">自建</option>");
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<div id=\"nsSiteType_hide1\" style=\"display: none;\">");
			        	htmlInfo.append("<label class=\"col-sm-2 control-label\">销售场地租金（万元）：</label>");
			        	htmlInfo.append("<div class=\"col-sm-2\">");
			        		htmlInfo.append("<input type=\"text\" name=\"nsSiteRent\" id=\"nsSiteRent\" class=\"form-control\"/>");
			        	htmlInfo.append("</div>");
		        	htmlInfo.append("</div>");
		        	htmlInfo.append("<div  id=\"nsSiteType_hide2\" style=\"display: none;\">");
			        	htmlInfo.append("<label class=\"col-sm-2 control-label\">销售场地自建投入（万元）：</label>");
			        	htmlInfo.append("<div class=\"col-sm-2\">");
			        		htmlInfo.append("<input type=\"text\" name=\"nsSiteInvestment\" id=\"nsSiteInvestment\" class=\"form-control\"/>");
			        	htmlInfo.append("</div>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
		        	htmlInfo.append("<label class=\"col-sm-2 control-label\">历史经营情况：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"nsHistoryOperate\" name=\"nsHistoryOperate\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<History_Operate.length;i++)
			    			{
			    				htmlInfo.append("<option value="+History_Operate[i].parameterValue+">"+History_Operate[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");	    	
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<div id=\"operate_hide5\" style=\"display: none;\">");
		    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
					htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">非农职业 </span>");
				htmlInfo.append("</div>");
				htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
				
				htmlInfo.append("<div class=\"form-group\">");
					htmlInfo.append("<label class=\"col-sm-2 control-label\">非农职业名称/类型：</label>");
					htmlInfo.append("<div class=\"col-sm-6\">");
		        		htmlInfo.append("<input type=\"text\" name=\"nonenongType\" id=\"nonenongType\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
				htmlInfo.append("</div>");
				
				htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">月收入（元/月）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"nonenongIncome\" id=\"nonenongIncome\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">从事此项工作的年限（年）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"nonenongYears\" id=\"nonenongYears\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");		    	
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">有无社保：</label>");
			    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"socialSecurity\" name=\"socialSecurity\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">社保金额（合计元/月）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"socialSecurityMoney\" id=\"socialSecurityMoney\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">有无公积金：</label>");
			    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"accumulationFund\" name=\"accumulationFund\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">公积金金额（合计元/月）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"text\" name=\"accumulationFundMoney\" id=\"accumulationFundMoney\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");    	
	    	htmlInfo.append("</div>");
    	
	    	htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" id=\"ipid\" >");
	    	
	    	htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
	    	if($("#intoPiece_Edit").val() == "1"){
				htmlInfo.append("<div class=\"col-md-8 text-center\">");
				htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
				htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
				htmlInfo.append("</div>");
	    	}
			htmlInfo.append("</div>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		$("#right_container").append(htmlInfo.toString());
		var intoPieceId = $("#intoPieceId").val();
		$("#ipid").val(intoPieceId);

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/familyoperate/getbyipid",
			data:{"id":intoPieceId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	if(data != null & data.obj != null){
		    		$("#otherBlcRemark").val(data.obj.otherBlcRemark);
		    		$("#blcScale").val(data.obj.blcScale);
		    		$("#blcYears").val(data.obj.blcYears);
		    		$("#blcExpectedValue").val(data.obj.blcExpectedValue);
		    		$("#blcHarvestTime").val(data.obj.blcHarvestTime);
		    		//$("#blcInvestment").val(data.obj.blcInvestment);
		    		$("#blcHistoryOperate").val(data.obj.blcHistoryOperate);
		    		$("#livestockScale").val(data.obj.livestockScale);
		    		$("#livestockYears").val(data.obj.livestockYears);
		    		$("#livestockExpectedValue").val(data.obj.livestockExpectedValue);
		    		$("#livestockSiteType").val(data.obj.livestockSiteType);
		    		$("#otherLivestockRemark").val(data.obj.otherLivestockRemark);
		    		if(data.obj.livestockSiteType == 1){
		    			$("#livestockSiteType_hide1").show();
		    		}else if(data.obj.livestockSiteType == 2){
		    			$("#livestockSiteType_hide2").show();
		    		}
		    		$("#livestockSiteRent").val(data.obj.livestockSiteRent);
		    		$("#livestockSiteInvestment").val(data.obj.livestockSiteInvestment);
		    		$("#eiaCertificate").val(data.obj.eiaCertificate);
		    		$("#livestockHistoryOperate").val(data.obj.livestockHistoryOperate);
		    		$("#otherNsRemark").val(data.obj.otherNsRemark);
		    		$("#nsScale").val(data.obj.nsScale);
		    		$("#nsYears").val(data.obj.nsYears);
		    		$("#nsSiteType").val(data.obj.nsSiteType);
		    		if(data.obj.nsSiteType== 1){
		    			$("#nsSiteType_hide1").show();
		    		}else if(data.obj.nsSiteType == 2){
		    			$("#nsSiteType_hide2").show();
		    		}
		    		$("#nsSiteRent").val(data.obj.nsSiteRent);
		    		$("#nsSiteInvestment").val(data.obj.nsSiteInvestment);
		    		$("#nsHistoryOperate").val(data.obj.nsHistoryOperate);
		    		$("#nonenongType").val(data.obj.nonenongType);
		    		$("#nonenongIncome").val(data.obj.nonenongIncome);
		    		$("#nonenongYears").val(data.obj.nonenongYears);
		    		$("#socialSecurity").val(data.obj.socialSecurity);
		    		$("#socialSecurityMoney").val(data.obj.socialSecurityMoney);
		    		$("#accumulationFund").val(data.obj.accumulationFund);
		    		$("#accumulationFundMoney").val(data.obj.accumulationFundMoney);
		    		var operateType = data.obj.operateType;
					if(operateType != null & operateType != ''){
						var detail = operateType.split(",");														
						for (var int = 0; int < detail.length; int++) {
							$("#operateType"+detail[int]).prop("checked",true);
							$("#operate_hide"+detail[int]).show();
						}
					}
					var bigLandCropType = data.obj.bigLandCropType;
					if(bigLandCropType != null & bigLandCropType != ''){
						var detail = bigLandCropType.split(",");														
						for (var int = 0; int < detail.length; int++) {
							$("#bigLandCropType"+detail[int]).prop("checked",true);
							if(bigLandCrop[bigLandCrop.length-1]==detail[int]){
								$("#otherBlcRemark").show();
							}
						}
					}
					var livestockType = data.obj.livestockType;
					if(livestockType != null & livestockType != ''){
						var detail = livestockType.split(",");														
						for (var int = 0; int < detail.length; int++) {
							$("#livestockType"+detail[int]).prop("checked",true);
							if(livestockType[livestockType.length-1]==detail[int]){
								$("#otherLivestockRemark").show();
							}
						}
					}
					var nongSellType = data.obj.nongSellType;
					if(nongSellType != null & nongSellType != ''){
						var detail = nongSellType.split(",");														
						for (var int = 0; int < detail.length; int++) {
							$("#nongSellType"+detail[int]).prop("checked",true);
							if(nongSellType[nongSellType.length-1]==detail[int]){
								$("#otherNsRemark").show();
							}
						}
					}
		    	}
		    }
		});
    }
  });
}
function postoperate(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/familyoperate/save",
		data:$('#operate').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}
function operateHide(obj){
	var val = $(obj).val();
	if($(obj).is(':checked')){
		$("#operate_hide"+val).show();
	}else{
		$("#operate_hide"+val).hide();
	}		
}
function livestockSiteType_hide(){
	var livestockSiteType = $("#livestockSiteType").val();
	if(livestockSiteType == 1){
		$("#livestockSiteType_hide1").show();
		$("#livestockSiteType_hide2").hide();
	}else if(livestockSiteType == 2){
		$("#livestockSiteType_hide2").show();
		$("#livestockSiteType_hide1").hide();
	}else{
		$("#livestockSiteType_hide1").hide();
		$("#livestockSiteType_hide2").hide();
	}
}
function nsSiteType_hide(){
	var nsSiteType = $("#nsSiteType").val();
	if(nsSiteType == 1){
		$("#nsSiteType_hide1").show();
		$("#nsSiteType_hide2").hide();
	}else if(nsSiteType == 2){
		$("#nsSiteType_hide2").show();
		$("#nsSiteType_hide1").hide();
	}else{
		$("#nsSiteType_hide1").hide();
		$("#nsSiteType_hide2").hide();
	}
}
function showhideoblc(obj){
	if($(obj).prop("checked")==true){
		$("#otherBlcRemark").val('');
		$("#otherBlcRemark").show();
	}else{
		$("#otherBlcRemark").hide();
		$("#otherBlcRemark").val('');
	}
}
function showhidels(obj){
	if($(obj).prop("checked")==true){
		$("#otherLivestockRemark").val('');
		$("#otherLivestockRemark").show();
	}else{
		$("#otherLivestockRemark").hide();
		$("#otherLivestockRemark").val('');
	}
}
function showhidens(obj){
	if($(obj).prop("checked")==true){
		$("#otherNsRemark").val('');
		$("#otherNsRemark").show();
	}else{
		$("#otherNsRemark").hide();
		$("#otherNsRemark").val('');
	}
}
//***********************************经营情况结束**********************************************//
//***********************************征信开始**********************************************//
//征信
function credit(){
	//获取数据字典
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/para/someparalist",
		data:{"someParaGroupName":"YES_NO,HAVE"},
	    error: function(request) {					
	    },
	    success: function(data) {
	    	$("#right_container").empty();
	    	var htmlInfo = new StringBuffer();
	    	htmlInfo.append("<h3>征信情况</h3>");
	    	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
	    	htmlInfo.append("<form id=\"familycredit\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postcredit()\">");
	    	
		    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
		    		htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">央行征信</span>");
		    	htmlInfo.append("</div>");
		    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	    	
		    	htmlInfo.append("<div class=\"form-group\">");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label dynamic-title\">是否白户：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"isWhite\" name=\"isWhite\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    			if(data.YES_NO != null){
		    				var yes_no = eval(data.YES_NO);
		    				for(var i=0;i<yes_no.length;i++)
			    			{
			    				htmlInfo.append("<option value="+yes_no[i].parameterValue+">"+yes_no[i].parameterName+"</option>");
			    			}
		    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">5年内贷款总次数：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"number\" name=\"loanTimesWithFiveYear\" id=\"loanTimesWithFiveYear\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	htmlInfo.append("</select>");
		    	htmlInfo.append("<div class=\"form-group\">");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label dynamic-title\">夫妻征信当前逾期：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\">");
		    	    	htmlInfo.append("<select id=\"hasOverdueCurrent\" name=\"hasOverdueCurrent\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    			if(data.HAVE != null){
		    				var have = eval(data.HAVE);
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">夫妻征信逾期总次数：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"number\" name=\"overdueTimes\" id=\"overdueTimes\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label dynamic-title\">夫妻征信逾期超过90天以上：</label>");
	    	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
		    	    	htmlInfo.append("<select id=\"hasOverdueOutNinetyDay\" name=\"hasOverdueOutNinetyDay\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    			if(data.HAVE != null){
		    				var have = eval(data.HAVE);
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			}
		    			htmlInfo.append("</select>");
	    	    	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">夫妻征信逾期90天的金额(元)：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"number\" name=\"ninetyDayOverdueMoney\" id=\"ninetyDayOverdueMoney\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">夫妻征信机构一年查询次数：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"number\" name=\"orgSearchTimesWithCredit\" id=\"orgSearchTimesWithCredit\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
	    	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">夫妻征信本人一年查询次数：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
		        		htmlInfo.append("<input type=\"number\" name=\"selSearchTimesWithCredit\" id=\"selSearchTimesWithCredit\" class=\"form-control\"/>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<div class=\"form-group\">");
			    	htmlInfo.append("<label class=\"col-sm-2 control-label\">征信负面信息（税务、公检法等）：</label>");
		        	htmlInfo.append("<div class=\"col-sm-2\">");
			        	htmlInfo.append("<select id=\"negativeInformation\" name=\"negativeInformation\" class=\"form-control\"  style=\"margin-top:-10px\">");
		    			htmlInfo.append("<option value=''>--请选择--</option>");
		    			if(data.HAVE != null){
		    				var have = eval(data.HAVE);
		    				for(var i=0;i<have.length;i++)
			    			{
			    				htmlInfo.append("<option value="+have[i].parameterValue+">"+have[i].parameterName+"</option>");
			    			}
		    			}
		    			htmlInfo.append("</select>");
		        	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	
		    	htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" id=\"ipid\" >");
		    	
		    	htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
		    	if($("#intoPiece_Edit").val() == "1"){
					htmlInfo.append("<div class=\"col-md-8 text-center\">");
					htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
					htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
					htmlInfo.append("</div>");
		    	}
				htmlInfo.append("</div>");
				
	    	htmlInfo.append("</form>");
	    	
	    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
	    		htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">第三方征信</span>");
	    	htmlInfo.append("</div>");
	    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	    	
	    	
	    	htmlInfo.append("<table class=\"table table-bordered m-t\">");	    	
	    	htmlInfo.append("<thead>");
	    	htmlInfo.append("<tr><th>姓名</th><th>身份证号</th><th>手机号</th><th>详情</th></tr>");
	    	htmlInfo.append("</thead>");
	    	htmlInfo.append("<tbody id=\"thirdPartyCredit\">");
	    	htmlInfo.append("</tbody>");	    	
	    	htmlInfo.append("</table>");
	    	
	    	htmlInfo.append("<form id='_QRcodeForm' action='/dataRecord/dataRecordAnalysis' method='post' target='QRcodeWin' >");
	    	htmlInfo.append("<input type=\"hidden\" name=\"memberName\" id=\"_memberName\"/>");
	    	htmlInfo.append("<input type=\"hidden\" name=\"idCard\" id=\"_idCard\"/>");
	    	htmlInfo.append("<input type=\"hidden\" name=\"phone\" id=\"_phone\"/>");
	    	htmlInfo.append("<input type=\"hidden\" name=\"bankCardNo\" id=\"_bankCardNo\"/>");
	    	htmlInfo.append("<input type=\"hidden\" name=\"id\" id=\"_creditid\"/>");
	    	htmlInfo.append("</form>");
	    	
	    	htmlInfo.append("</div>");
	    	$("#right_container").append(htmlInfo.toString());
	    	var intoPieceId = $("#intoPieceId").val();
	    	$("#ipid").val(intoPieceId);
	    	
	    	//获取征信详情
	    	$.ajax({
		        url: "/familycredit/form", // 请求的URL
		        dataType: 'json',
		        type: "post",
		        data:{"intoPieceId":intoPieceId},
		        success: function (data) {
		        	if(data != null){
		        		$("#isWhite").val(data.isWhite);
		        		$("#loanTimesWithFiveYear").val(data.loanTimesWithFiveYear);
		        		$("#overdueTimes").val(data.overdueTimes);
		        		$("#hasOverdueCurrent").val(data.hasOverdueCurrent);
		        		$("#hasOverdueOutNinetyDay").val(data.hasOverdueOutNinetyDay);
		        		$("#ninetyDayOverdueMoney").val(data.ninetyDayOverdueMoney);
		        		$("#orgSearchTimesWithCredit").val(data.orgSearchTimesWithCredit);
		        		$("#selSearchTimesWithCredit").val(data.selSearchTimesWithCredit);
		        		$("#negativeInformation").val(data.negativeInformation);
		        	}
		        }
		     });
	    	
	    	//获取第三方征信列表
	    	$.ajax({
		        url: "/familycredit/thirdpartycredit", // 请求的URL
		        dataType: 'json',
		        type: "post",
		        data:{"intoPieceId":intoPieceId},
		        success: function (data) {
		        	if(data != null){
		        		var html = new StringBuffer();
		        		for (var int = 0; int < data.length; int++) {
		        			html.append("<tr>");
		        			html.append("<td>"+data[int].name+"</td>");
		        			html.append("<td>"+data[int].idCard+"</td>");
		        			html.append("<td>"+data[int].phone+"</td>");
		        			html.append("<td><button class=\"btn btn-primary\" type=\"button\" onclick=\"creditOpen('"+data[int].name+"','"+data[int].idCard+"','"+data[int].phone+"','"+data[int].bankNo+"','"+ intoPieceId +"',this)\">大数据风险报告</button></td>");
		        			html.append("</tr>");
						}
		        		$("#thirdPartyCredit").append(html.toString());
		        	}
		        }
	    	 });
	    }
	});
	
}
//保存征信
function postcredit(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/familycredit/save",
		data:$('#familycredit').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}
//详情
function creditOpen(name,idCard,phone,bankNo,intoPieceId,_this){
	$(_this).attr("disabled","disabled");
	
	$("#_memberName").val(name);
	$("#_idCard").val(idCard);
	$("#_phone").val(phone);
	$("#_bankCardNo").val(bankNo);
	$("#_creditid").val(intoPieceId);
	
	setTimeout(function(){
		$(_this).attr("disabled",false);
	}, 10000);
	
	window.open("","QRcodeWin");
	//提交表单  
	$("#_QRcodeForm").submit(); 
}
//申请信息
function applydetail() {
	var productList;
	var productmel;
	$("#right_container").empty();
	var intoPieceId = $("#intoPieceId").val();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>申请信息</h3>");
	htmlInfo.append("<div class=\"ibox-content clearfix\">");
		htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" id=\"ipid\" >");
		htmlInfo.append("<input type=\"hidden\" name=\"type\" id=\"type\" >");
		/*htmlInfo.append("<div class=\"form-group\" style=\"position:relative;\">");
//			htmlInfo.append("<div class=\"col-sm-4\" style=\"height:60px;\" id=\"score1\"></div>");
//			htmlInfo.append("<div class=\"col-sm-4\" style=\"height:60px;\" id=\"grade1\"></div>");
//			htmlInfo.append("<div class=\"col-sm-4\" style=\"height:60px;\" id=\"money1\"></div>");
			htmlInfo.append("<div class=\"col-sm-12\" style=\"height:60px;\" id=\"nothing\"></div>");
			htmlInfo.append("<div class=\"col-sm-12\" style=\"position: absolute;left:0;top:0px;border:2px solide #ddd;text-align:center;color:red;font-size:30px;\">");
				htmlInfo.append("<div id=\"span_capital\" style=\"display:inline-block\">");
//					htmlInfo.append("<label>授信额度：</label>");
				htmlInfo.append("</div>");
			htmlInfo.append("</div>");
		htmlInfo.append("</div>");*/
		htmlInfo.append("<form id=\"applydetail\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postapplydetail()\">");
			
			htmlInfo.append("<div id=\"prodiv\" class=\"form-group\">");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">申请额度：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<div class=\"input-group\">");
			htmlInfo.append("<input type=\"number\" id=\"capital\" onchange=\"calculatePrice(this);\" name=\"capital\"   class=\"form-control required\"/>");
			htmlInfo.append("<div class=\"input-group-addon\">元</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">金融产品：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
				htmlInfo.append("<select  id=\"product\" name=\"product\" class=\"form-control\" >");
				htmlInfo.append("</select>");
				htmlInfo.append("<span id=\"detail\"></span>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"nongzimoney\" class=\"form-group\">");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("<br/>");
			htmlInfo.append("<br/>");
			htmlInfo.append("<br/>");
			htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
			if($("#intoPiece_Edit").val() == "1"){
				htmlInfo.append("<div class=\"col-md-12 text-center\">");
				htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
				htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
				htmlInfo.append("</div>");
	    	}
			htmlInfo.append("</div>");
		htmlInfo.append("</form>");
	htmlInfo.append("</div>");
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/intopiece/findPro",
		data:{"intoPieceId":intoPieceId}, //formid
	    error: function(request) {
	    	
	    },
	    success: function(data) {
	    	appType=data.appType;
	    	if(data.appType==null||data.appType==1){
	    		$("#right_container").append(htmlInfo.toString());
	    		$("#ipid").val(intoPieceId); 
	    		$("#type").val(appType); 
//				getscore();
				getproduct();
				getsubmittedInfo();
	    	}else{
	    		productList=data.productList;
		    	productmel=data.productmel;
		    	var htmlInfo1 = new StringBuffer();
		    	var htmlInfo2 = new StringBuffer();
		    	htmlInfo1.append("<label class=\"col-sm-2 control-label\">农资/商品：</label>");
				htmlInfo1.append("<div class=\"col-sm-10\">");
					htmlInfo1.append("<table id=\"dynamicTable\">");
						htmlInfo1.append("<thead>");
							htmlInfo1.append("<tr>");
								htmlInfo1.append("<td height=\"30\" width=\"350\" bgcolor=\"#CCCCCC\">商品名称</td>");
								htmlInfo1.append("<td height=\"30\" width=\"100\" bgcolor=\"#CCCCCC\">商品单价（元）</td>");
								htmlInfo1.append("<td height=\"30\" width=\"100\" bgcolor=\"#CCCCCC\">数量（件）</td>");
								htmlInfo1.append("<td height=\"30\" width=\"200\" bgcolor=\"#CCCCCC\">预计使用时间</td>");
								htmlInfo1.append("<td></td>");
							htmlInfo1.append("</tr>");
							htmlInfo1.append("<tbody>");
							if(productmel!=null&&productmel.length>0){
								for (var int = 0; int < productmel.length; int++) {
									var i =-1;
									htmlInfo1.append("<tr>");
									htmlInfo1.append("<td height=\"30\" width=\"350\">");
										htmlInfo1.append("<select class=\"form-control\" name=\"productId\" onchange=\"calculatePrice(this);\">");
											htmlInfo1.append("<option value=\"\">--请选择--</option>");
											for (var int2 = 0; int2 < productList.length; int2++) {
												if(productmel[int].productId==productList[int2].id){
													i=int2;
													htmlInfo1.append("<option productType=\""+productList[int2].type+"\" price=\""+productList[int2].price+"\" value=\""+productList[int2].id+"\" selected=\"selected\">"+productList[int2].productName+"</option>");
												}else{
													htmlInfo1.append("<option productType=\""+productList[int2].type+"\" price=\""+productList[int2].price+"\" value=\""+productList[int2].id+"\">"+productList[int2].productName+"</option>");
												}
											}
										htmlInfo1.append("</select>");
									htmlInfo1.append("</td>");
									htmlInfo1.append("<td height=\"30\" width=\"100\">");
									if(i!=-1){
										htmlInfo1.append("<input type=\"number\" name=\"price\" readonly=\"readonly\" value=\""+productList[i].price+"\"  class=\"form-control\" />");
									}else{
										htmlInfo1.append("<input type=\"number\" name=\"price\" readonly=\"readonly\" value=\"\"  class=\"form-control\" />");
									}
									htmlInfo1.append("</td>");
									htmlInfo1.append("<td height=\"30\" width=\"100\">");
										htmlInfo1.append("<input type=\"number\" name=\"productNum\" value=\""+productmel[int].productNum+"\" onchange=\"calculatePrice(this);\" class=\"form-control\" />");
									htmlInfo1.append("</td>");
									htmlInfo1.append("<td height=\"30\" width=\"200\">");
										htmlInfo1.append("<div class=\"input-group date\" id ='receiveDate'>");
											htmlInfo1.append("<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-calendar\"></span></i></span>");
											htmlInfo1.append("<input id=\"receive_date\" type=\"text\" name=\"receive_date\" value=\""+productmel[int].productTime+"\" class=\"form-control\" />");
										htmlInfo1.append("</div>");
									htmlInfo1.append("</td>");
									htmlInfo1.append("<td>");
										htmlInfo1.append("<input type=\"button\" id=\"Button3\" class=\"btn btn-primary\"  onClick=\"prodetail(this)\" value=\"详情\">");
										htmlInfo1.append("<input type=\"button\" id=\"Button2\" class=\"btn btn-danger\"  onClick=\"deltr(this)\" value=\"删行\">");
									htmlInfo1.append("</td>");
									htmlInfo1.append("</tr>");
								}
							}else{
								htmlInfo1.append("<tr>");
								htmlInfo1.append("<td height=\"30\" width=\"350\">");
									htmlInfo1.append("<select class=\"form-control\" name=\"productId\" onchange=\"calculatePrice(this);\">");
										htmlInfo1.append("<option value=\"\">--请选择--</option>");
										for (var int = 0; int < productList.length; int++) {
												htmlInfo1.append("<option productType=\""+productList[int].type+"\" price=\""+productList[int].price+"\" value=\""+productList[int].id+"\">"+productList[int].productName+"</option>");
										}
									htmlInfo1.append("</select>");
								htmlInfo1.append("</td>");
								htmlInfo1.append("<td height=\"30\" width=\"100\">");
									htmlInfo1.append("<input type=\"number\" name=\"price\"  readonly=\"readonly\" value=\"\"  class=\"form-control\" />");
								htmlInfo1.append("</td>");
								htmlInfo1.append("<td height=\"30\" width=\"100\">");
									htmlInfo1.append("<input type=\"number\" name=\"productNum\" onchange=\"calculatePrice(this);\" class=\"form-control\" />");
								htmlInfo1.append("</td>");
								htmlInfo1.append("<td height=\"30\" width=\"200\">");
									htmlInfo1.append("<div class=\"input-group date\" id ='receiveDate'>");
										htmlInfo1.append("<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-calendar\"></span></i></span>");
										htmlInfo1.append("<input  type=\"text\" name=\"receive_date\" class=\"form-control\" />");
									htmlInfo1.append("</div>");
								htmlInfo1.append("</td>");
								htmlInfo1.append("<td>");
									htmlInfo1.append("<input type=\"button\" id=\"Button3\" class=\"btn btn-primary\"  onClick=\"prodetail(this)\" value=\"详情\">");
									htmlInfo1.append("<input type=\"button\" id=\"Button2\" class=\"btn btn-danger\"  onClick=\"deltr(this)\" value=\"删行\">");
								htmlInfo1.append("</td>");
								htmlInfo1.append("</tr>");
							}
							htmlInfo1.append("</tbody>");
						htmlInfo1.append("</table>");
						htmlInfo1.append("<input id=\"btn_addtr\" onclick=\"addTr()\" type=\"button\" class=\"btn btn-primary\" value=\"增行\">");
					htmlInfo1.append("</div>");
					
					
					htmlInfo2.append("<label class=\"col-sm-2 control-label\">到手农资：</label>");
					htmlInfo2.append("<div class=\"col-sm-3\">");
					htmlInfo2.append("<div class=\"input-group\">");
					htmlInfo2.append("<input type=\"text\" readonly=\"readonly\" id=\"recieveNongZi\"   class=\"form-control \"/>");
					htmlInfo2.append("<div class=\"input-group-addon\">元</div>");
					htmlInfo2.append("</div>");
					htmlInfo2.append("</div>");
					htmlInfo2.append("<label class=\"col-sm-2 control-label\">到手现金：</label>");
					htmlInfo2.append("<div class=\"col-sm-3\">");
					htmlInfo2.append("<div class=\"input-group\"> <input type=\"text\" readonly=\"readonly\" id=\"recieveCash\"  class=\"form-control \"/>");
					htmlInfo2.append("<div class=\"input-group-addon\">元</div>");
					htmlInfo2.append("</div>");
					htmlInfo2.append("</div>");
					htmlInfo2.append("<input type=\"text\" style=\"display: none\" id=\"recieveNongZiSub\" name=\"recieveNongZi\"  class=\"form-control \"/>");
					htmlInfo2.append("<input type=\"text\" style=\"display: none\" id=\"recieveCashSub\" name=\"recieveCash\" class=\"form-control \"/>");
					
					htmlInfo.append("<table id=\"tab11\" style=\"display: none\">");
						htmlInfo.append("<tbody>");
							htmlInfo.append("<tr>");
							htmlInfo.append("<td height=\"30\" width=\"350\">");
								htmlInfo.append("<select class=\"form-control\"  name=\"productId\" onchange=\"calculatePrice(this);\">");
									htmlInfo.append("<option value=\"\">--请选择--</option>");
									for (var int = 0; int < productList.length; int++) {
											htmlInfo.append("<option productType=\""+productList[int].type+"\" price=\""+productList[int].price+"\" value=\""+productList[int].id+"\">"+productList[int].productName+"</option>");
									}
								htmlInfo.append("</select>");
							htmlInfo.append("</td>");
							htmlInfo.append("<td height=\"30\" width=\"100\">");
								htmlInfo.append("<input type=\"number\"  name=\"price\"  readonly=\"readonly\" class=\"form-control\" />");
							htmlInfo.append("</td>");
							htmlInfo.append("<td height=\"30\" width=\"100\">");
								htmlInfo.append("<input type=\"number\" name=\"productNum\" onchange=\"calculatePrice(this);\" class=\"form-control\" />");
							htmlInfo.append("</td>");
							htmlInfo.append("<td height=\"30\" width=\"200\">");
								htmlInfo.append("<div class=\"input-group date\" id ='receiveDate'>");
									htmlInfo.append("<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-calendar\"></span></i></span>");
									htmlInfo.append("<input  type=\"text\" name=\"receive_date\" class=\"form-control\" />");
								htmlInfo.append("</div>");
							htmlInfo.append("</td>");
							htmlInfo.append("<td>");
								htmlInfo.append("<input type=\"button\" id=\"Button4\" class=\"btn btn-primary\"  onClick=\"prodetail(this)\" value=\"详情\">");
								htmlInfo.append("<input type=\"button\" id=\"Button1\" class=\"btn btn-danger\"  onClick=\"deltr(this)\" value=\"删行\">");
							htmlInfo.append("</td>");
							htmlInfo.append("</tr>");
						htmlInfo.append("</tbody>");
					htmlInfo.append("</table>");
					if($("#applydetail").length==0){
						$("#right_container").append(htmlInfo.toString());
						$("#prodiv").append(htmlInfo1.toString());
						$("#nongzimoney").append(htmlInfo2.toString());
						$(".date").datepicker({
					        todayBtn: "linked",
					        keyboardNavigation: false,
					        forceParse: false,
					        autoclose: true
					    });
						$("#ipid").val(intoPieceId); 
						$("#type").val(appType);
//						getscore();
						getproduct();
						show_count = 20;   
						count = 1; 
						getsubmittedInfo();
					}
	    	}
	    	
	    }
	});
}

function getsubmittedInfo(){
	var intoPieceId = $("#ipid").val();
	$.ajax({
		type: 'POST', 
	    url: '/intopiece/getsubmittedInfo',
	    data: {"intoPieceId":intoPieceId},
	    dataType: 'json', 
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success:function(data){
	    	if(data.code==200){
	    		$("#capital").val(data.capital);
	    		$("#recieveNongZi").val(data.recieveNongZi);
		    	$("#recieveCash").val(data.recieveCash);
		    	$("#recieveNongZiSub").val(data.recieveNongZi);
		    	$("#recieveCashSub").val(data.recieveCash);
//		    	$("#span_capital").html("授信额度：" + data.creditCapital + "元");
//		    	creditCapital=data.creditCapital*1;
	    	}else{
	    		bootbox.alert(data.msg);
	    	}
	    }
	});
}

function postapplydetail(){
	var intoPieceId = $("#ipid").val();
	var appType = $("#type").val();
	var capital= $("#capital").val()*1;
	var jinrongPro=$("#product").val();
	if(capital==''){
		bootbox.alert("申请金额必填");
		return false;
	}
//	if(capital>creditCapital){
//		bootbox.alert("申请金额不能大于授信额度");
//		return false;
//	}
	
	if(jinrongPro==''){
		bootbox.alert("金融产品必选");	
		return false;
	}
	if(appType==2||appType==3){
		var productArray = [];
		var flag = true;
		$("#dynamicTable tbody tr").each(function () {
			
			var productId = $(this).find("select").val();
			if(!productId){
				parent.window.bootbox.alert("请选择商品名称");
				flag = false;
				return false;
			}
			//判断不过不计算价格
			if(!flag){
				return false;
			}
			
			var productNum = $(this).find("input[name='productNum']").val();
			if(!productNum){
				flag = false;
				parent.window.bootbox.alert("请选择商品数量");
				return false;
			}
			if(productNum*1<1){
				parent.window.bootbox.alert("不能少于1件");
				return;
			}
			//判断不过不计算价格
			if(!flag){
				return false;
			}
			var receiveDate = $(this).find("input[name='receive_date']").val();
			var productType = $(this).find("select").find("option:selected").attr("productType");
			var arrayObj = {"productId":productId, "productNum":productNum , "productTime":receiveDate , "productType":productType};
			productArray.push(arrayObj);
		});
		if(!flag){
			return false;
		}
		if(productArray.length==0){
			parent.window.bootbox.alert("请选择商品");
			return false;
		}
		
		var recieveNongZi= $("#recieveNongZiSub").val();
		var recieveCash=$("#recieveCashSub").val();
		var formData =  new FormData();
		formData.append('capital',capital);
		formData.append('id',intoPieceId);
		formData.append('product',jinrongPro);
		formData.append('type',appType);
		formData.append('recieveNongZi',recieveNongZi);
		formData.append('recieveCash',recieveCash);
		formData.append('productListInfo', JSON.stringify(productArray) );
//		{"capital" : capital , "id" : intoPieceId , "product" : jinrongPro , "term" : term , "type" : appType ,"recieveNongZi" : recieveNongZi , "recieveCash" : recieveCash , "productListInfo" : JSON.stringify(productArray) }
		$.ajax({
			type: 'POST', 
		    url: '/intopiece/applyDetailSave',
		    data:formData ,
		    processData : false, 
		    contentType : false,
		    dataType: 'json', 
		    error: function(data) {
		    	bootbox.alert(data.msg);
		    },
		    success:function(data){
		    	if(data.code==200){
		    		bootbox.alert(data.msg);
		    	}else{
		    		bootbox.alert(data.msg);
		    	}
		    }
		});
	}else{
		$.ajax({
			type: 'POST', 
		    url: '/intopiece/applyDetailSave',
		    data: {"capital":capital,"id":intoPieceId,"product":jinrongPro,"type":appType},
		    dataType: 'json', 
		    error: function(request) {
		    	bootbox.alert(data.msg);
		    },
		    success:function(data){
		    	if(data.code==200){
		    		bootbox.alert(data.msg);
		    	}else{
		    		bootbox.alert(data.msg);
		    	}
		    }
		});
	}
	return false;
}

//计算到手农资及到手现金
function calculatePrice(_this){
	var select=  $(_this).parent().find("select");
	if(select!=undefined&&select.attr("name")== "productId" ){
		var price = select.find("option:selected").attr("price");
		$(_this).parent().parent().find("input[name='price']").val(price);
	}
	
	
	
	if(appType==2||appType==3){
		var capital= $("#capital").val();
		if(capital!=''){
			var productArray = [];
			var flag = true;
			$("#dynamicTable tbody tr").each(function () {
				//判断不过不计算价格
				if(!flag){
					return;
				}
				
				var productId = $(this).find("select").val();
				if(!productId){
					flag = false;
					return;
				}
				//判断不过不计算价格
				if(!flag){
					return;
				}
				
				var productNum = $(this).find("input[name='productNum']").val();
				if(!productNum){
					flag = false;
					return;
				}
				if(productNum*1<1){
					parent.window.bootbox.alert("不能少于1件");
					return;
				}
				//判断不过不计算价格
				if(!flag){
					return;
				}
			
				var arrayObj = {"productId":productId, "productNum":productNum};
				productArray.push(arrayObj);
			});
			//判断不过不计算价格
			if(!flag){
				return;
			}
			$.ajax({
			    url: '/intopiece/caculateNongZi',
			    type: 'POST', 
			    async: true,
			    data: {"capital":capital,"list":JSON.stringify(productArray)},
			    dataType: 'json', 
			    success:function(data){
			    	if(data.code==200){
			    		//计算结算价格 
				    	$("#recieveNongZi").val(data.recieveNongZi);
				    	$("#recieveCash").val(data.recieveCash);
				    	$("#recieveNongZiSub").val(data.recieveNongZi);
				    	$("#recieveCashSub").val(data.recieveCash);
			    	}else{
			    		parent.window.bootbox.alert(data.msg);
			    	}
			    }
			});
		}
	}
}

function addTr(){
	var length = $("#dynamicTable tbody tr").length;
	//alert(length);
	if (length < show_count)    //点击时候，如果当前的数字小于递增结束的条件
	{
		$("#tab11 tbody tr").clone().appendTo("#dynamicTable tbody");   //在表格后面添加一行
		//changeIndex();//更新行号
	}
	$(".date").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	
}

function deltr(opp) {
	var length = $("#dynamicTable tbody tr").length;
	//alert(length);
	if (length <= 1) {
		parent.window.bootbox.alert("至少保留一行");
	} else {
		$(opp).parent().parent().remove();//移除当前行
		//changeIndex();
	}
	calculatePrice(this);
}

function prodetail(opp) {
	var id = $(opp).parent().parent().find("select[name='productId']").find("option:selected").val();
	//1为商品，2为套餐
	var productType = $(opp).parent().parent().find("select[name='productId']").find("option:selected").attr("productType");
	if(id!=''&&id!=undefined){
		$.ajax({
		    url: '/intopiece/findProDetail',
		    type: 'POST', 
		    async: true,
		    data: {"id":id,"productType":productType},
		    dataType: 'json', 
		    success:function(data){
		    	if(data.code==200){
		    		parent.window.bootbox.alert(data.msg);
		    	}else{
		    		parent.window.bootbox.alert(data.msg);
		    	}
		    }
		});
	}else{
		alert("请先选择商品");
	}
	/*if (length <= 1) {
		parent.window.bootbox.alert("至少保留一行");
	} else {
		$(opp).parent().parent().remove();//移除当前行
		//changeIndex();
	}
	calculatePrice(this);*/
}


/*function getscore(){
	$("#btn_score").attr("disabled", "disabled");
	var id = $("#ipid").val();
	$.ajax({
		type: "POST",
		url: "/scoreMgr/scoreCalculateMgr",
		data:{"intoPieceId":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	$("#btn_score").removeAttr("disabled");
	    	if(data.code == 200){
		        var scoreCalculate = data.scoreCalculate;
		        score1 = scoreCalculate[0].scoreTotal;
				grade1 = scoreCalculate[1].scoreTotal;
				money1 = scoreCalculate[2].scoreTotal;
				var myChart1 = echarts.init(document.getElementById('score1'));
				var myChart2 = echarts.init(document.getElementById('grade1'));
				var myChart3 = echarts.init(document.getElementById('money1'));
				var option1 = {
					title: {
						text: scoreCalculate[0].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {									
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10
							}
						},
						splitLine: { 
							length: 10,
							lineStyle: { 
								color: 'auto'
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: score1,
							name: '违约成本'
						}]
					}]
				};
				var option2 = {
						title: {
						text: scoreCalculate[1].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {
							
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: grade1,
							name: '还款意愿'
						}]
					}]
				};
				var option3 = {
						title: {
						text: scoreCalculate[2].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {
							
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10,
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: money1,
							name: '还款能力'
						}]
					}]
				};


				myChart2.setOption(option2);
				myChart3.setOption(option3);
				myChart1.setOption(option1);
//				var creditCapital =data.creditCapital;
				var creditCapital1 =10000;
				$("#span_capital").html("授信额度：" + data.creditCapital + "元");
				$("#span_capital").html("授信额度：" + creditCapital1 + "元");
				creditCapital =creditCapital1;
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
}*/


function getproduct(){
	var id = $("#ipid").val();
	$.ajax({
		type: "POST",
		url: "/intopiece/findByIpId",
		data:{"intoPieceId":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	var pros=data.result;
	    	var proId = data.proId;
	    	var htmlinfo=new StringBuffer();
	    	htmlinfo.append("<option value=''>--请选择--</option>");
	    	if(pros!=null&&pros.length>0){
	    		for (var int = 0; int < pros.length; int++) {
	    			if(proId!=null){
	    				if(proId==pros[int].productId){
	    					htmlinfo.append("<option value='"+pros[int].productId+"' selected = true>"+pros[int].name+"</option>");
	    				}else{
	    					htmlinfo.append("<option value='"+pros[int].productId+"' >"+pros[int].name+"</option>");
	    				}
	    			}else{
	    				htmlinfo.append("<option value='"+pros[int].productId+"' >"+pros[int].name+"</option>");
	    			}
				}
	    	}
	    	$("#product").append(htmlinfo.toString());
	    }
	});
}

//土地信息
function lands(){

	$("#right_container").empty();
	var intoPieceId = $("#intoPieceId").val();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>土地信息</h3>");
	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	htmlInfo.append("<form id=\"familycapital\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postassets1()\">");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" id=\"ipid\" >");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">土地经营权证书编号：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"landCertId\" id=\"landCertId\" class=\"form-control\"/>");
			htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">在册水田数（公顷）：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"waterFarmlandRegistered\" id=\"waterFarmlandRegistered\" class=\"form-control\"/>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<label class=\"col-sm-2 control-label\">在册旱地/田数（公顷）：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"dryFarmlandRegistered\" id=\"dryFarmlandRegistered\" class=\"form-control\"/>");
			htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">册外水田数（公顷）：</label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"waterFarmlandUnregistered\" id=\"waterFarmlandUnregistered\" class=\"form-control\"/>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<label class=\"col-sm-2 control-label\">册外旱地/田数（公顷）：</label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"dryFarmlandUnregistered\" id=\"dryFarmlandUnregistered\" class=\"form-control\"/>");
			htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-2 control-label\">承包水田数（公顷）：</label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"waterFarmlandContract\" id=\"waterFarmlandContract\" class=\"form-control\"/>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<label class=\"col-sm-2 control-label\">承包旱地/田数（公顷）：</label>");
			htmlInfo.append("<div class=\"col-sm-2\">");
				htmlInfo.append("<input type=\"text\" name=\"dryFarmlandContract\" id=\"dryFarmlandContract\" class=\"form-control\"/>");
			htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
		if($("#intoPiece_Edit").val() == "1"){
			htmlInfo.append("<div class=\"col-md-8 text-center\">");
			htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
			htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
			htmlInfo.append("</div>");
    	}
	htmlInfo.append("</form>");
	htmlInfo.append("<br/>");
	htmlInfo.append("<br/>");
	htmlInfo.append("<br/>");
	if($("#intoPiece_Edit").val() == "1"){
		htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
		htmlInfo.append("<button id=\"addTransferLand_btn\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>增加");
		htmlInfo.append("</button>");
		htmlInfo.append("</div>");
	}
	htmlInfo.append("<table class=\"table\" id=\"tableid\">");
	htmlInfo.append("</table>");
	$("#right_container").append(htmlInfo.toString());
	
	$("#addTransferLand_btn").on("click",function(){
		initTransferLand();
	});
	var intoPieceId = $("#intoPieceId").val();
	$("#ipid").val(intoPieceId);
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/familycapital/selectByIntoPieceId",
		data:{"intoPieceId":intoPieceId}, //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
    		$("#waterFarmlandRegistered").val(data.familycapital.waterFarmlandRegistered);
    		$("#dryFarmlandRegistered").val(data.familycapital.dryFarmlandRegistered);
    		$("#waterFarmlandUnregistered").val(data.familycapital.waterFarmlandUnregistered);
    		$("#dryFarmlandUnregistered").val(data.familycapital.dryFarmlandUnregistered);
    		$("#waterFarmlandContract").val(data.familycapital.waterFarmlandContract);
    		$("#dryFarmlandContract").val(data.familycapital.dryFarmlandContract);
    		$("#landCertId").val(data.familycapital.landCertId);
	    }
	});
	
	$('#tableid').bootstrapTable({
		method: 'post', 
		url: "/transferLand/list",
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
			return {
				pageSize: params.limit,			//页面大小
			    currentPage: (params.offset / params.limit) + 1,		//页码
			    intoPieceId: intoPieceId,
	          }					
		},
		contentType: "application/x-www-form-urlencoded",
		showColumns: false,
		showRefresh: false,
		showToggle: false,
		toolbar:'#toolbar',
		minimumCountColumns: 3,
		smartDisplay:true,
		responseHandler: responseHandler,
		columns: [
			{
				field: 'id',
				title: '编号',
				align: 'center',
				width: '30',
				valign: 'middle',
				visible:false
			},
			{
				field: 'landName',
				title: '土地名称',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'landLevel',
				title: '土地等级',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'landArea',
				title: '土地面积（公顷）',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'yield',
				title: '土地产量（吨）',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'easternBorder',
				title: '东边界',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'westernBorder',
				title: '西边界',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'northernBorder',
				title: '北边界',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'southernBorder',
				title: '南边界',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'outsourcingTerm',
				title: '承包期限(年)',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: '',
				title: '操作',
				align: 'center',
				width: '100',
				valign: 'middle',
				formatter: landInfoActionFormatter
			}
		],
		onLoadSuccess:function(data){
			/*alert(data.familycapital.waterFarmlandRegistered);
			alert(data.familycapital.dryFarmlandRegistered);
			alert(data.familycapital.waterFarmlandUnregistered);
			alert(data.familycapital.dryFarmlandUnregistered);
			alert(data.familycapital.waterFarmlandContract);
			alert(data.familycapital.dryFarmlandContract);
			alert(data.familycapital.landCertId);
			$("#waterFarmlandRegistered").val(data.familycapital.waterFarmlandRegistered);
    		$("#dryFarmlandRegistered").val(data.familycapital.dryFarmlandRegistered);
    		$("#waterFarmlandUnregistered").val(data.familycapital.waterFarmlandUnregistered);
    		$("#dryFarmlandUnregistered").val(data.familycapital.dryFarmlandUnregistered);
    		$("#waterFarmlandContract").val(data.familycapital.waterFarmlandContract);
    		$("#dryFarmlandContract").val(data.familycapital.dryFarmlandContract);
    		$("#landCertId").val(data.familycapital.landCertId);*/
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
    			}); 
    		}); 
        },
        onLoadError: function () {
            //mif.showErrorMessageBox("数据加载失败！");
        }
	});

}

function postassets1(){
	/*var landCertId = $("#landCertId").val();
	if(landCertId == ''){
		bootbox.alert("土地经营权证书编号不能为空");
		return false;
	}
	var waterFarmlandRegistered= $("#waterFarmlandRegistered").val();
	if(waterFarmlandRegistered == ''){
		bootbox.alert("在册水田不能为空");
		return false;
	}
	var dryFarmlandRegistered= $("#dryFarmlandRegistered").val();
	if(dryFarmlandRegistered == ''){
		bootbox.alert("在册旱地不能为空");
		return false;
	}*/
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/familycapital/saveLand",
		data:$('#familycapital').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}

function landInfoActionFormatter(value, row, index){
	var html = "";
	if($("#intoPiece_Edit").val() == "1"){
		html = "<a class=\"operate\" href=\"#\" onclick=\"editTransferLand('"+row.id+"','"+row.landName+"','"+row.landLevel+"','"+row.landArea+"','"+row.easternBorder+"','"+row.westernBorder+"','"+row.northernBorder+"','"+row.southernBorder+"','"+row.outsource+"','"+row.outsourcingTerm+"')\"><span>详情</span></a>" +
		"<a class=\"operate\" href=\"#\" style=\"margin-left:10px;\" onclick=\"delTransferLand('"+row.id+"')\"><span>删除</span></a>";
	}
	return html;
}

function editTransferLand(id,landName,landLevel,landArea,easternBorder,westernBorder,northernBorder,southernBorder,outsource,outsourcingTerm){
	//初始化编辑页面
	initTransferLand();
	
	$("#id").val(id);
	$("#landName").val(landName);
	$("#landLevel").val(landLevel);
	$("#landArea").val(landArea);
	$("#easternBorder").val(easternBorder);
	$("#westernBorder").val(westernBorder);
	$("#northernBorder").val(northernBorder);
	$("#southernBorder").val(southernBorder);
	$("#outsource").val(outsource);
	if(outsource==1){
		$(".outTerm").show();
		$("#outsourcingTerm").val(outsourcingTerm);
	}

}

function delTransferLand(id){
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
                $.post("/transferLand/delete",{"id":id},
    		    function(data)
    		    {
    			    if(data.code == 200)
    			    {
    			    	bootbox.alert(data.msg);
    			    	$('#tableid').bootstrapTable('refresh');
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

function posttransferland(){
	var landName = $("#landName").val();
	var landLevel = $("#landLevel").val();
	var landArea = $("#landArea").val();
	if(landName == ""){
		bootbox.alert("土地名称必填");
		return false;
	}
	if(landLevel == ""){
		bootbox.alert("土地等级必填");
		return false;
	}
	if(landArea == ""){
		bootbox.alert("土地面积必填");
		return false;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/transferLand/save",
		data:$('#edittransferland').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		$('#edittransferland')[0].reset();
		    	$('#transferLandModal').modal('hide');
		    	$('#tableid').bootstrapTable('refresh');
	    	}
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}

function hideshowterm(){
	var outsource= $('#outsource').val();
	if(outsource==1){
		$(".outTerm").show();
	}else{
		$(".outTerm").hide();
		$("#outsourcingTerm").val('');
	}
}

function initTransferLand(){

	var htmlInfo=new StringBuffer();	
	
	htmlInfo.append("<div class=\"modal fade\" id=\"transferLandModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
	htmlInfo.append("<div class=\"modal-dialog\" id=\"transferlanddialog\">");
	htmlInfo.append("<div class=\"modal-content\">");
	
	htmlInfo.append("<div class=\"modal-body\">");
	htmlInfo.append("<form class=\"form-horizontal\" id=\"edittransferland\" role=\"form\" onsubmit=\"return posttransferland()\">");
	htmlInfo.append("<fieldset>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"landName\">土地名称</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"landName\" id=\"landName\" type=\"text\" />");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"landLevel\">土地等级</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"landLevel\" id=\"landLevel\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"landArea\">土地面积（公顷）</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"landArea\" id=\"landArea\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"landArea\">土地产量（吨）</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"yield\" id=\"yield\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"easternBorder\">东边界</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"easternBorder\" id=\"easternBorder\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"westernBorder\">西边界</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"westernBorder\" id=\"westernBorder\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"northernBorder\">北边界</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"northernBorder\" id=\"northernBorder\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"southernBorder\">南边界</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"southernBorder\" id=\"southernBorder\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"form-group\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\">是否外包：</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"outsource\" onchange=\"hideshowterm()\" name=\"outsource\" class=\"form-control\">");
		htmlInfo.append("<option value=''>--请选择--</option>");
		htmlInfo.append("<option value='1'>是</option>");
		htmlInfo.append("<option value='2'>否</option>");
		htmlInfo.append("</select>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div style=\"display: none\" class=\"form-group outTerm\">");
	htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"outsourcingTerm\">承包期限(年)</label>");
	htmlInfo.append("<div class=\"col-sm-8\">");
	htmlInfo.append("<input class=\"form-control\" name=\"outsourcingTerm\" id=\"outsourcingTerm\" type=\"text\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("<div class=\"modal-footer\">");
	htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
    htmlInfo.append("<button style=\"margin-right: 40%;\" type=\"submit\" class=\"btn btn-primary\">提交</button>");
    htmlInfo.append("</div>");
	
    htmlInfo.append("<input name=\"intoPieceId\" id=\"addtransferlandintoPieceId\" type=\"hidden\"/>");
    htmlInfo.append("<input name=\"id\" id=\"id\" type=\"hidden\"/>");
    
	htmlInfo.append("</fieldset>");
	htmlInfo.append("</form>");
	htmlInfo.append("</div>");
	
	htmlInfo.append("</div>");
    htmlInfo.append("</div>");
    htmlInfo.append("</div>");
    
    $("#ucmodalid").empty();
    $("#ucmodalid").append(htmlInfo.toString()); 
	$("#transferLandModal").modal('show');
	$("#addtransferlandintoPieceId").val($("#intoPieceId").val());

}

//计算年龄和性别
function idMetch() {
	var id_card = $("#idCard").val();
	if (isNum(id_card)) {
		$.ajax({
            type: "POST",
            url: "/idMetch/index",
            data: {
                "idCard": id_card
            },
            success: function (ret) {
                if (ret.code == 200) {
                         $('#age').val(ret.age);
                         var sex=  document.getElementById("sex");
                         for(var i=0;i<sex.length;i++)
                         {

                             if(sex[i].value==ret.sex)
                             {
                                 //alert(1);
                                 sex[i].selected=true;
                             }
                         }
            	}else{
            		bootbox.alert(ret.msg,"");
            	}
            }
		});
	}
	function isNum(value) {
        return (value !== undefined && value !== null && value !== '') ? true : false;
    }
}
//***********************************征信结束**********************************************//
//***********************************家庭资产开始**********************************************//
//家庭资产
function assets(){
	$("#right_container").empty();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>资产及负债</h3>");
	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
	htmlInfo.append("<form id=\"familycapital\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postassets()\">");
	
    	htmlInfo.append("<div class=\"form-group\">");
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">资产总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"totalFamilyCapital\" id=\"totalFamilyCapital\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
    	htmlInfo.append("</div>");	        	
    	
    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">房屋</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"rentHouse\" id=\"rentHouse\" value='1' />在外租房</label>");
	    	htmlInfo.append("</div>");
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"countrysideHouse\" id=\"countrysideHouse\" value='1' />有农村房屋</label>");
	    	htmlInfo.append("</div>");
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"smallPropertyHouse\" id=\"smallPropertyHouse\" value='1' />自购小产权房/安置房</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">面积(单位：m2)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"smallPropertyHouseArea\" id=\"smallPropertyHouseArea\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"smallPropertyHouseValue\" id=\"smallPropertyHouseValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");		        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"bigPropertyHouse\" id=\"bigPropertyHouse\" value='1' />自购大产权房/商品房</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">面积(单位：m2)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"bigPropertyHouseArea\" id=\"bigPropertyHouseArea\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"bigPropertyHouseValue\" id=\"bigPropertyHouseValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");		        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">车辆</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"car\" id=\"car\" value='1' />轿车</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">名称：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"carName\" id=\"carName\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"carValue\" id=\"carValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");		        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"minibus\" id=\"minibus\" value='1' />面包车</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-4 control-label\"></label>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"minibusValue\" id=\"minibusValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");		        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"truck\" id=\"truck\" value='1' />货车</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-4 control-label\"></label>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"truckValue\" id=\"truckValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");		        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"otherTricycle\" id=\"otherTricycle\" value='1' />三轮车/其他农用车</label>");
	    	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">农机</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-1\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"nongMachine\" id=\"nongMachine\" value='1' />农机</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">名称：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"nongMachineRemark\" id=\"nongMachineRemark\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">预计价值(单位：万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-1\">");
        		htmlInfo.append("<input type=\"text\" name=\"nongMachineEstimatedValue\" id=\"nongMachineEstimatedValue\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">使用年限：</label>");
        	htmlInfo.append("<div class=\"col-sm-1\">");
        		htmlInfo.append("<input type=\"text\" name=\"nongMachineYear\" id=\"nongMachineYear\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
    	htmlInfo.append("</div>");
    	
    	/*htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">土地</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");*/
    	
    	/*htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"selLandType\" id=\"selLandType\" value='1' />自有土地</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">自有土地亩数(单位：亩)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"selLandNum\" id=\"selLandNum\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"certLandType\" id=\"certLandType\" value='1' />承包土地</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">承包土地亩数(单位：亩)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"certLandNum\" id=\"certLandNum\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");*/
    	
    	/*htmlInfo.append("<div class=\"form-group\">");
    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">土地经营权证书编号：<span style=\"color: red\">（必填）</span></label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"landCertId\" id=\"landCertId\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">在册水田数（公顷）：<span style=\"color: red\">（必填）</span></label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"waterFarmlandRegistered\" id=\"waterFarmlandRegistered\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">在册旱地/田数（公顷）：<span style=\"color: red\">（必填）</span></label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"dryFarmlandRegistered\" id=\"dryFarmlandRegistered\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">册外水田数（公顷）：</label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"waterFarmlandUnregistered\" id=\"waterFarmlandUnregistered\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">册外旱地/田数（公顷）：</label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"dryFarmlandUnregistered\" id=\"dryFarmlandUnregistered\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">承包水田数（公顷）：</label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"waterFarmlandContract\" id=\"waterFarmlandContract\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">承包旱地/田数（公顷）：</label>");
	    	htmlInfo.append("<div class=\"col-sm-2\">");
	    		htmlInfo.append("<input type=\"text\" name=\"dryFarmlandContract\" id=\"dryFarmlandContract\" class=\"form-control\"/>");
	    	htmlInfo.append("</div>");	        	
		htmlInfo.append("</div>");
    	
    	htmlInfo.append("<hr>");*/
    	
    	htmlInfo.append("<div class=\"form-group\">");
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">家庭负债总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"totalFamilyDebtedness\" id=\"totalFamilyDebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
        	htmlInfo.append("<label class=\"col-sm-2 control-label\">已核实隐性负债(万元)：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"invisibleDebtedness\" id=\"invisibleDebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");
    	htmlInfo.append("</div>");	
        
    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">负债类型</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"bankLoan\" id=\"bankLoan\" value='1' />银行（贷款/信用卡）</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"bankLoanIndebtedness\" id=\"bankLoanIndebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"p2pPettyLoan\" id=\"p2pPettyLoan\" value='1' />p2p/小贷借款</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"p2pPettyLoanIndebtedness\" id=\"p2pPettyLoanIndebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"friendLoan\" id=\"friendLoan\" value='1' />亲朋借款</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"friendLoanIndebtedness\" id=\"friendLoanIndebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"guaranteeLoan\" id=\"guaranteeLoan\" value='1' />为他人担保贷款</label>");
	    	htmlInfo.append("</div>");
	    	
	    	htmlInfo.append("<label class=\"col-sm-2 control-label\">总额（万元）：</label>");
        	htmlInfo.append("<div class=\"col-sm-2\">");
        		htmlInfo.append("<input type=\"text\" name=\"guaranteeLoanIndebtedness\" id=\"guaranteeLoanIndebtedness\" class=\"form-control\"/>");
        	htmlInfo.append("</div>");	        	
    	htmlInfo.append("</div>");
    	
    	htmlInfo.append("<div class=\"form-group\" style=\"margin-top: 20px;\">");
			htmlInfo.append("<span style=\"font-weight: bold; font-size: 16px; padding-left: 15px;\">其他</span>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<p style=\"border-bottom: 1px solid #E7EAEC;\"></p>");
    	
    	htmlInfo.append("<div class=\"form-group\">");
	    	htmlInfo.append("<div class=\"col-sm-2\" style=\"padding-top:10px\" >");
	    		htmlInfo.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\"" +
	    						" name=\"mainInvest\" id=\"mainInvest\" value='1' />重大开支投资</label>");
	    	htmlInfo.append("</div>");        	
    	htmlInfo.append("</div>");
	
        htmlInfo.append("<input type=\"hidden\" name=\"intoPieceId\" id=\"ipid\" >");
    	
    	htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
    	if($("#intoPiece_Edit").val() == "1"){
			htmlInfo.append("<div class=\"col-md-8 text-center\">");
			htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
			htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
			htmlInfo.append("</div>");
    	}
		htmlInfo.append("</div>");
		
	htmlInfo.append("</form>");
	htmlInfo.append("</div>");
	$("#right_container").append(htmlInfo.toString());
	var intoPieceId = $("#intoPieceId").val();
	$("#ipid").val(intoPieceId);
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/familycapital/selectByIntoPieceId",
		data:{"intoPieceId":intoPieceId},
	    error: function(request) {	
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		$("#totalFamilyCapital").val(data.familycapital.totalFamilyCapital);
	    		$("#smallPropertyHouseArea").val(data.familycapital.smallPropertyHouseArea);
	    		$("#smallPropertyHouseValue").val(data.familycapital.smallPropertyHouseValue);
	    		$("#bigPropertyHouseArea").val(data.familycapital.bigPropertyHouseArea);
	    		$("#bigPropertyHouseValue").val(data.familycapital.bigPropertyHouseValue);
	    		$("#carName").val(data.familycapital.carName);
	    		$("#carValue").val(data.familycapital.carValue);
	    		$("#minibusValue").val(data.familycapital.minibusValue);
	    		$("#truckValue").val(data.familycapital.truckValue);
	    		$("#nongMachineRemark").val(data.familycapital.nongMachineRemark);
	    		$("#nongMachineEstimatedValue").val(data.familycapital.nongMachineEstimatedValue);
	    		//$("#selLandNum").val(data.familycapital.selLandNum);
	    		//$("#certLandNum").val(data.familycapital.certLandNum);
	    		$("#totalFamilyDebtedness").val(data.familycapital.totalFamilyDebtedness);
	    		$("#bankLoanIndebtedness").val(data.familycapital.bankLoanIndebtedness);
	    		$("#p2pPettyLoanIndebtedness").val(data.familycapital.p2pPettyLoanIndebtedness);
	    		$("#friendLoanIndebtedness").val(data.familycapital.friendLoanIndebtedness);
	    		$("#invisibleDebtedness").val(data.familycapital.invisibleDebtedness);
	    		/*$("#waterFarmlandRegistered").val(data.familycapital.waterFarmlandRegistered);
	    		$("#dryFarmlandRegistered").val(data.familycapital.dryFarmlandRegistered);
	    		$("#waterFarmlandUnregistered").val(data.familycapital.waterFarmlandUnregistered);
	    		$("#dryFarmlandUnregistered").val(data.familycapital.dryFarmlandUnregistered);
	    		$("#waterFarmlandContract").val(data.familycapital.waterFarmlandContract);
	    		$("#dryFarmlandContract").val(data.familycapital.dryFarmlandContract);*/
	    		/*$("#landCertId").val(data.familycapital.landCertId);*/
	    		if(data.familycapital.rentHouse == "1"){
	    			$("#rentHouse").prop("checked",true);
	    		}
	    		if(data.familycapital.countrysideHouse == "1"){
	    			$("#countrysideHouse").prop("checked",true);
	    		}
	    		if(data.familycapital.smallPropertyHouse == "1"){
	    			$("#smallPropertyHouse").prop("checked",true);
	    		}
	    		if(data.familycapital.bigPropertyHouse == "1"){
	    			$("#bigPropertyHouse").prop("checked",true);
	    		}
	    		if(data.familycapital.car == "1"){
	    			$("#car").prop("checked",true);
	    		}
	    		if(data.familycapital.minibus == "1"){
	    			$("#minibus").prop("checked",true);
	    		}
	    		if(data.familycapital.truck == "1"){
	    			$("#truck").prop("checked",true);
	    		}
	    		if(data.familycapital.otherTricycle == "1"){
	    			$("#otherTricycle").prop("checked",true);
	    		}
	    		if(data.familycapital.nongMachine == "1"){
	    			$("#nongMachine").prop("checked",true);
	    		}
	    		/*if(data.familycapital.selLandType == "1"){
	    			$("#selLandType").prop("checked",true);
	    		}
	    		if(data.familycapital.certLandType == "1"){
	    			$("#certLandType").prop("checked",true);
	    		}*/
	    		if(data.familycapital.bankLoan == "1"){
	    			$("#bankLoan").prop("checked",true);
	    		}
	    		if(data.familycapital.p2pPettyLoan == "1"){
	    			$("#p2pPettyLoan").prop("checked",true);
	    		}
	    		if(data.familycapital.friendLoan == "1"){
	    			$("#friendLoan").prop("checked",true);
	    		}
	    		if(data.familycapital.guaranteeLoan == "1"){
	    			$("#guaranteeLoan").prop("checked",true);
	    		}
	    		if(data.familycapital.mainInvest == "1"){
	    			$("#mainInvest").prop("checked",true);
	    		}
	    	}else{
	    		bootbox.alert(data.msg);
	    	}
	    }
	});
}
//保存家庭财产
function postassets(){
	/*var landCertId = $("#landCertId").val();
	if(landCertId == ''){
		bootbox.alert("土地经营权证书编号不能为空");
		return false;
	}
	var waterFarmlandRegistered= $("#waterFarmlandRegistered").val();
	if(waterFarmlandRegistered == ''){
		bootbox.alert("在册水田不能为空");
		return false;
	}
	var dryFarmlandRegistered= $("#dryFarmlandRegistered").val();
	if(dryFarmlandRegistered == ''){
		bootbox.alert("在册旱地不能为空");
		return false;
	}*/
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/familycapital/save",
		data:$('#familycapital').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}
//***********************************家庭财产结束**********************************************//
//***********************************通讯录开始**********************************************//
//通讯录
function contact(){
	$("#right_container").empty();
	var intoPieceId = $("#intoPieceId").val();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>通讯录</h3>");
	htmlInfo.append("<table class=\"table\" id=\"tableid\">");
	htmlInfo.append("</table>");
	$("#right_container").append(htmlInfo.toString());
	
	$('#tableid').bootstrapTable({
		method: 'post', 
		url: "/contact/list",
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
			return {
				pageSize: params.limit,			//页面大小
			    currentPage: (params.offset / params.limit) + 1,		//页码
			    intoPieceId: intoPieceId,
	          }					
		},
		contentType: "application/x-www-form-urlencoded",
		showColumns: false,
		showRefresh: false,
		showToggle: false,
		toolbar:'#toolbar',
		minimumCountColumns: 3,
		smartDisplay:true,
		responseHandler: responseHandler,
		columns: [
			{
				field: 'name',
				title: '姓名',
				align: 'center',
				width: '50',
				valign: 'middle'
			},
			{
				field: 'phone',
				title: '手机号',
				align: 'center',
				width: '50',
				valign: 'middle'
			},
			{
				field: 'creDate',
				title: '时间',
				align: 'center',
				width: '50',
				valign: 'middle',
				formatter: changeDateFormat
			},
			{
				field: '',
				title: '操作',
				align: 'center',
				width: '100',
				valign: 'middle',
				/*formatter: callOutContactFormatter*/
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
    			}); 
    		}); 
        },
        onLoadError: function () {
            //mif.showErrorMessageBox("数据加载失败！");
        }
	});
}

function callOutContactFormatter(){
	return [
		       "<input onclick=\"contactCallOut(this,\'Local\')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">",
		       "<input onclick=\"contactCallOut(this,\'gateway\')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">"
		     ].join('   ');
}
//通讯录外呼
function contactCallOut(one,ExtenType){
	var tr = $(one).closest("tr");
	var name = $(tr).children()[0].innerHTML;
	var phone = $(tr).children()[1].innerHTML;
	var intoPieceId = $("#intoPieceId").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/qimooutcall/callout",
		data:{"intoPieceId":intoPieceId,"phone":phone,"ExtenType":ExtenType,"member_name":name,"from":"memberSelf"}, 
	    success: function(data) {
	    	bootbox.alert(data.msg,"");
	    }
	});
}
//***********************************通讯录结束**********************************************//
//***********************************动态数据开始**********************************************//
//动态数据
function dynamic(){
	var intoPieceId = $('#intoPieceId').val();
	$("#right_container").empty();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>审核记录</h3>");
	htmlInfo.append("<div class=\"wrapper wrapper-content\" style=\"padding: 0;\">");
	htmlInfo.append("<div class=\"row\">");
	htmlInfo.append("<div class=\"col-sm-12\">");
	htmlInfo.append("<div class=\"ibox\" style=\"margin-bottom: 0;\">");
	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding: 0px;\">");
	htmlInfo.append("<form id=\"dataForm\" class=\"form-horizontal col-lg-12\" onsubmit=\"return postdynamicdata()\">");
	htmlInfo.append("<div class=\"info\" style=\"background-color: #FFF; padding-top: 10px;\">");
	htmlInfo.append("<ul id=\"title\">");
	htmlInfo.append("</ul>");
	htmlInfo.append("<div id=\"data\" width=\"100%\" style=\"padding: 0 20px 10px 20px; overflow-y: scroll;\"></div>");
	htmlInfo.append("</div>");
	htmlInfo.append("<div id=\"action\" class=\"clearfix form-actions\" style=\"margin: 10px 0;\">");
	if($("#intoPiece_Edit").val() == "1"){
		htmlInfo.append("<div class=\"col-md-9 text-center\">");
		htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\"><i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
		htmlInfo.append("</div>");
	}
	htmlInfo.append("</div>");
	htmlInfo.append("</form>");
	htmlInfo.append("<input type=\"hidden\" id = \"sectionId\"/>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	$("#right_container").append(htmlInfo.toString());
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/section/getbyipid",
		data:{"intoPieceId":intoPieceId},
	    error: function(request) {					
	    },
	    success: function(data) {	    	
	    	var html = new StringBuffer();
	    	for(var i=0;i<data.length;i++)
	    	{	
	    		if(i==0){
	    			html.append("<li onclick=\"changeTab('"+data[i].sectionId+"', this);\" class=\"active\">"+data[i].cname+"</li>");
	    		}else{
	    			html.append("<li onclick=\"changeTab('"+data[i].sectionId+"', this);\">"+data[i].cname+"</li>");
	    		}	
	    	}
	    	$("#title").append(html.toString());
	    	//初始化第一个标签
	    	if(data.length != 0){
	    		loadData(data[0].sectionId);
	    	}
	    }
	});
	
}
//动态数据切换sheet页
function changeTab(sectionId, obj) {
    if (obj) {
        $(".info > ul").find("li").each(function (i, d) {
            if ($(this).hasClass("active")) {
                $(this).removeClass("active");
            }
        });
        $(obj).addClass("active");
    }
    loadData(sectionId);
}
//加载动态数据
function loadData(sectionId){
	var intoPieceId = $('#intoPieceId').val();
    $("#sectionId").val(sectionId);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/applypieceext/list",
		data:{"intoPieceId":intoPieceId,"sectionId":sectionId},
	    error: function(request) {					
	    },
	    success: function(data) {
	    	$("#data").empty();
	    	var html = new StringBuffer();
	    	if(data.length == 0){
	    		html.append("<p style='text-align: center; margin-top: 20px; font-size: 15px;'>没有数据！</p>");
	    	}else{
	    		html.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;\"><tbody>");
	    		for(var i=0;i<data.length;i++)
		    	{	
	    			html.append("<tr>");
	    			//标准
	    			if(data[i].inputType == 1){
	    				html.append("<td style=\"background-color: #F9F9F9;\"><b>"+data[i].cname+"</b></td><td colspan=\"3\"><label style=\"margin-left:20px;\"><input type=\"text\" name=\""+data[i].itemId+"\"");
	    				if(data[i].itemValue != null){
	    					html.append(" value="+data[i].itemValue);
	    				}
	    				if(data[i].inputWidth != null & data[i].inputWidth != ''){
	    					html.append(" style=\"width:"+data[i].inputWidth+"px;\"");
	    				}
	    				//不能为空
	    				if(data[i].isEmpty == '0'){
	    					html.append(" required=\"required\"");
	    				}
	    				html.append(" /><span class=\"unit\">"+data[i].unit+"</span></label></td>");
	    			}
	    			//单选
	    			if(data[i].inputType == 2){
	    				html.append("<td style=\"background-color: #F9F9F9;\"><b>"+data[i].cname+"</b></td><td colspan=\"3\"><div class='checkbox i-checks'>");
	    				if(data[i].optionsGroup != null & data[i].optionsGroup != ''){
	    					var option = data[i].optionsGroup.split(",");  
	    					for (var j = 0; j < option.length; j++) {
	    						var one = option[j].split(":");
	    						html.append("<label><input type=\"radio\" name=\""+data[i].itemId+"\" value=\""+one[1]+"\"");
								if(data[i].itemValue == one[1]){
									html.append(" checked=\"checked\"");
								}
								html.append("/>"+one[0]+"</label>");	
							}    		
	    				}
	    				html.append("</div></td>");
	    			}
	    			//复选
	    			if(data[i].inputType == 3){
	    				html.append("<td style=\"background-color: #F9F9F9;\"><b>"+data[i].cname+"</b></td><td colspan=\"3\"><div class='checkbox i-checks'>");
	    				if(data[i].optionsGroup != null & data[i].optionsGroup != ''){
	    					var option = data[i].optionsGroup.split(",");  
	    					var result = data[i].itemValue+",";
	    					for (var j = 0; j < option.length; j++) {
	    						var one = option[j].split(":");
	    						html.append("<label style=\"margin-left:20px;\"><input type=\"checkbox\" style=\"vertical-align:text-top;margin-top:0;\" name=\""+data[i].itemId+"\" value=\""+one[1]+"\"");
								if(result.indexOf(one[1]+",") != -1){
									html.append(" checked=\"checked\"");
								}
								html.append("/>"+one[0]+"</label>");
							}    		
	    				}
	    				html.append("</div></td>");
	    			}
	    			html.append("</tr>");
		    	}
	    		html.append("</tbody></table>");
	    	}
	    	$("#data").append(html.toString());
	    }
	});
}
//提交动态数据表单
function postdynamicdata(){
	var intoPieceId = $('#intoPieceId').val();
	var sectionId = $('#sectionId').val();
	var data = $('#dataForm').serializeArray();
	var strData = '';
	for (var int = 0; int < data.length; int++) {
		if(int == 0){
			strData = data[int].name +":" +data[int].value;
		}else{
			strData = strData + "," + data[int].name +":" +data[int].value ;
		}
	}
	$.ajax({
        type: "POST",
        dataType: "json",
        url: "/applypieceext/save",
        data : {"intoPieceId":intoPieceId,"sectionId":sectionId, "data": strData},
        success: function(result) {
        	bootbox.alert(result.msg);
        }
    });
    return false;
}
//***********************************动态数据结束**********************************************//
//***********************************其他联系人开始**********************************************//
//初始化其他联系人
function otherContact(){
	$("#right_container").empty();
	var intoPieceId = $("#intoPieceId").val();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<h3>其他联系人</h3>");
	if($("#intoPiece_Edit").val() == "1"){
		htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
		htmlInfo.append("<button id=\"addOtherContact_btn\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
		htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>增加");
		htmlInfo.append("</button>");
		htmlInfo.append("</div>");
	}
	htmlInfo.append("<table class=\"table\" id=\"tableid\">");
	htmlInfo.append("</table>");
	$("#right_container").append(htmlInfo.toString());
	
	$("#addOtherContact_btn").on("click",function(){
		initAddOtherContact();
	});
	
	$('#tableid').bootstrapTable({
		method: 'post', 
		url: "/othercontact/list",
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
			return {
				pageSize: params.limit,			//页面大小
			    currentPage: (params.offset / params.limit) + 1,		//页码
			    intoPieceId: intoPieceId,
	          }					
		},
		contentType: "application/x-www-form-urlencoded",
		showColumns: false,
		showRefresh: false,
		showToggle: false,
		toolbar:'#toolbar',
		minimumCountColumns: 3,
		smartDisplay:true,
		responseHandler: responseHandler,
		columns: [
			{
				field: 'id',
				title: '编号',
				align: 'center',
				width: '30',
				valign: 'middle',
				visible:false
			},
			{
				field: 'relation',
				title: '关系',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'name',
				title: '姓名',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'phone',
				title: '手机号',
				align: 'center',
				width: '80',
				valign: 'middle'
			},
			{
				field: 'creDate',
				title: '时间',
				align: 'center',
				width: '80',
				valign: 'middle',
				formatter: changeDateFormat
			},
			/*{
				field: '',
				title: '外呼',
				align: 'center',
				width: '100',
				valign: 'middle',
				formatter: callOutOtherContactFormatter
			},*/
			{
				field: '',
				title: '操作',
				align: 'center',
				width: '100',
				valign: 'middle',
				formatter: callOutActionFormatter
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
    			}); 
    		}); 
        },
        onLoadError: function () {
            //mif.showErrorMessageBox("数据加载失败！");
        }
	});
}
//初始化外呼栏
function callOutOtherContactFormatter(){
	return [
		       "<input onclick=\"othercontactCallOut(this,\'Local\')\" value=\"手机外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">",
		       "<input onclick=\"othercontactCallOut(this,\'gateway\')\" value=\"IP电话外呼\" style=\"width: 105px;\" class=\"btn btn-outline btn-primary\">"
		     ].join('   ');
}
function callOutActionFormatter(value, row, index){
	var html = "";
	if($("#intoPiece_Edit").val() == "1"){
		html = "<a class=\"operate\" href=\"#\" onclick=\"editOtherContact('"+row.id+"','"+row.relation+"','"+row.name+"','"+row.phone+"')\"><span>详情</span></a>" +
		"<a class=\"operate\" href=\"#\" style=\"margin-left:10px;\" onclick=\"delOtherContact('"+row.id+"')\"><span>删除</span></a>";
	}
	return html;
}
//其他联系人外呼
function othercontactCallOut(one,ExtenType){
	var tr = $(one).closest("tr");
	var name = $(tr).children()[2].innerHTML;
	var phone = $(tr).children()[3].innerHTML;
	var intoPieceId = $("#intoPieceId").val();
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/qimooutcall/callout",
		data:{"intoPieceId":intoPieceId,"phone":phone,"ExtenType":ExtenType,"member_name":name,"from":"otherSelf"}, 
	    success: function(data) {
	    	bootbox.alert(data.msg,"");
	    }
	});
}
//编辑其他联系人
function editOtherContact(id,relation,name,phone){
	//初始化编辑页面
	initAddOtherContact();
	
	$("#id").val(id);
	$("#relation").val(relation);
	$("#name").val(name);
	$("#phone").val(phone);

}
//删除其他联系人
function delOtherContact(id){
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
                $.post("/othercontact/delete",{"ids":id},
    		    function(data)
    		    {
    			    if(data.code == 200)
    			    {
    			    	bootbox.alert(data.msg);
    			    	$('#tableid').bootstrapTable('refresh');
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
//初始化编辑页面
function initAddOtherContact(){
		var htmlInfo=new StringBuffer();	
		
		htmlInfo.append("<div class=\"modal fade\" id=\"otherContactModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"othercontactdialog\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"editothercontact\" role=\"form\" onsubmit=\"return postothercontact()\">");
		htmlInfo.append("<fieldset>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"relation\">关系</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"relation\" id=\"relation\" type=\"text\" />");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"phone\">联系方式</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"phone\" id=\"phone\" type=\"text\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" type=\"submit\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
		
	    htmlInfo.append("<input name=\"intoPieceId\" id=\"addothercontactintoPieceId\" type=\"hidden\"/>");
	    htmlInfo.append("<input name=\"id\" id=\"id\" type=\"hidden\"/>");
	    
		htmlInfo.append("</fieldset>");
		htmlInfo.append("</form>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    
	    $("#ucmodalid").empty();
	    $("#ucmodalid").append(htmlInfo.toString()); 
		$("#otherContactModal").modal('show');
		
		$("#addothercontactintoPieceId").val($("#intoPieceId").val());
}

$('#cancel_btn').on("click",function(){
	$('#editothercontact')[0].reset();
});
$('#close_btn').on("click",function(){
	$('#editothercontact')[0].reset();
});

function postothercontact(){
	var relation = $("#relation").val();
	var name = $("#name").val();
	var phone = $("#phone").val();
	if(relation == ""){
		bootbox.alert("关系必填");
		return false;
	}
	if(name == ""){
		bootbox.alert("姓名必填");
		return false;
	}
	if(phone == ""){
		bootbox.alert("手机号必填");
		return false;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/othercontact/save",
		data:$('#editothercontact').serialize(), //formid
	    error: function(request) {
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		$('#editothercontact')[0].reset();
		    	$('#otherContactModal').modal('hide');
		    	$('#tableid').bootstrapTable('refresh');
	    	}
	    	bootbox.alert(data.msg);
	    }
	});
	return false;
}
//***********************************其他联系人结束**********************************************//
//***********************************图片信息开始**********************************************//
var mediaType ;
var token;
var viewer = null;
function media(){
	$.ajax({
		type: "POST",
		//dataType: "json",
		url: "/media/imagepara",
		data: {"paraGroupName":"IMAGE_TYPE"},
	    error: function(request) {	
	    },
	    success: function(data) {	    	
	    	if(data.code == 200){
	    		mediaType = eval(data.para);
	    		token = data.token;
	    	}else{
	    		bootbox.alert(data.msg);
	    		return ;
	    	}
	    	$("#right_container").empty();
	    	var htmlInfo=new StringBuffer();
	    	htmlInfo.append("<h3>图片信息</h3>");
	    	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
		    	htmlInfo.append("<div class=\"form-horizontal col-lg-10 m-t\">");
			    	htmlInfo.append("<div class=\"form-group\">");
				    	htmlInfo.append("<label class=\"col-sm-1 control-label\">类型：</label>");
				    	htmlInfo.append("<div class=\"col-sm-2\">");
				    	htmlInfo.append("<select class=\"form-control\" name=\"fileType\" id=\"fileType\" >");
				    	htmlInfo.append("<option value=\"\">--请选择--</option>");
	    				for(var i=0;i<mediaType.length;i++)
		    			{
		    				htmlInfo.append("<option value="+mediaType[i].parameterValue+">"+mediaType[i].parameterName+"</option>");
		    			}
				    	htmlInfo.append("</select>");
				    	htmlInfo.append("</div>");
				    	htmlInfo.append("<div class=\"col-sm-2\">");
				    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"file_click()\"><i></i>上传</button>");
					    	htmlInfo.append("<div style=\"display: none;\">");
					    	htmlInfo.append("<input type=\"file\" id=\"imageFile\" accept=\"image/jpeg,image/png\" multiple=\"multiple\" onchange=\"postimage()\" />");
					    	htmlInfo.append("</div>");
				    	htmlInfo.append("</div>");
				    	htmlInfo.append("<div class=\"col-sm-6\" id=\"imageProgress\" style=\"display:none;\">");
				    	htmlInfo.append("<label class=\"col-sm-1 control-label\" id=\"detail\"></label>");
				    	htmlInfo.append("<div class=\"progress col-sm-5\" style=\"margin-left:10px;margin-top:5px;\">");
					    	htmlInfo.append("<div class=\"progress-bar progress-bar-info\" id=\"progressUpload\" role=\"progressbar\" aria-valuenow=\"0\" aria-valuemin=\"0\" aria-valuemax=\"100\">");
					    	htmlInfo.append("</div>");
				    	htmlInfo.append("</div>");
				    	htmlInfo.append("</div>");
				    htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	htmlInfo.append("<table class=\"table table-bordered m-t\">");
		    	htmlInfo.append("<thead>");
		    	htmlInfo.append("<tr>");
		    	htmlInfo.append("<th>类型</th><th>上传日期</th><th>缩略图</th><th>操作</th>");
		    	htmlInfo.append("</tr>");
		    	htmlInfo.append("</thead>");
		    	htmlInfo.append("<tbody id=\"mediaTable\">");
		    	htmlInfo.append("</tbody>");
				htmlInfo.append("</table>");
				htmlInfo.append("<div id=\"detailImageModel\">");
				htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");
	    	$("#right_container").append(htmlInfo.toString());
	    	viewer = null;   		
	    	//加载列表
	    	refreshMedia();
	    }
	});
}
function file_click(){
	var fileType = $("#fileType").val();
	if(fileType == ""){
		bootbox.alert("请先选择图片类型");
		return false;
	}
	$("#imageFile").click();
}
//上传图片
function postimage(){
	var files=document.getElementById("imageFile");
	var file=files.files;//每一个file对象对应一个文件。
	if(file.length == 0){
		bootbox.alert("请选择图片");
		return false;
	};
	var options = {
			  quality: 0.60,
			  noCompressIfLarger: true
			};
	var config = {
			  useCdnDomain: true,
			  region: qiniu.region.z0
			};
	var putExtra = {
			  fname: "",
			  params: {},
			  mimeType: ["image/png", "image/jpeg", "image/bmp"]
			};
	var sum = 0;
	var observer = {
			  next:function(res){
				  $("#imageProgress").show();
				  var order = sum + 1;
				  $("#detail").empty();
				  $("#detail").append(order+"/"+file.length);
				  $("#progressUpload").css("width",res.total.percent + "%")
			  },
			  error:function(err){
				  $("#detail").append("  "+err.message);
			  },
			  complete:function(res){
				  $.ajax({
						type: "POST",
						url: "/media/save",
						data: {"intoPieceId":$("#intoPieceId").val(),"fileType": $("#fileType").val(),"hashKey":res.hash},
					    error: function(request) {	
					    },
					    success: function(data) {						    	
					    	if(data.code == 400){
					    		bootbox.alert(data.msg);
					    	}
					    	sum = sum + 1;
					    	if(sum == file.length){
					    		$("#imageProgress").hide();
					    		bootbox.alert("保存成功");
					    		refreshMedia();
					    	}				    	
					    }
					});
			  }
			};
	for (var int = 0; int < file.length; int++) {
		qiniu.compressImage(file[int], options).then(function (data) {
			  var observable = qiniu.upload(data.dist, null, token, putExtra, config)
			  var subscription = observable.subscribe(observer) // 上传开始
		});
	}

	
}
//加载图片列表
function refreshMedia(){
	$.ajax({
		type: "POST",
		//dataType: "json",
		url: "/media/list",
		//type=1 图片
		data: {"intoPieceId":$("#intoPieceId").val(),"type":"1"},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	$("#mediaTable").empty();
	    	if(data != null){
	    		var htmlInfo=new StringBuffer();
	    		for (var int = 0; int < data.length; int++) {
	    			htmlInfo.append("<tr>");
	    			var flag = 1;
	    			for(var i=0;i<mediaType.length;i++)
	    			{
	    				if(parseInt(mediaType[i].parameterValue) == data[int].fileType){
	    					htmlInfo.append("<td>"+mediaType[i].parameterName+"</td>");
	    					flag = 2;
	    		    	}
	    			}
	    			if(flag == 1){
	    				htmlInfo.append("<td></td>");
	    			}
	    			htmlInfo.append("<td>"+changeDateFormat(data[int].creDate)+"</td>");
	    			htmlInfo.append("<td><img src='"+data[int].small+"' data-original='"+data[int].big+"' style=\"height:50px;width:80px;cursor:pointer;\"/></td>");
	    			htmlInfo.append("<td><button type=\"button\" onclick=\"delImage('"+data[int].id+"')\" class=\"btn btn-danger\">删除</button></td>");
	    			htmlInfo.append("</tr>");
				}
	    		$("#mediaTable").append(htmlInfo.toString());	
	    	}
	    	if(data.length == 0){
	    		viewer = null;
	    	}else if(viewer == null){
	    		viewer = new Viewer(document.getElementById('mediaTable'), { 
		    	    url: 'data-original' 
		    	});
	    	}else{
	    		viewer.update();
	    	}	 
	    }
	});
}
//删除该图
function delImage(id){
	$.ajax({
		type: "POST",
		//dataType: "json",
		url: "/media/delete",
		data: {"id":id},
	    error: function(request) {
	    	
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		refreshMedia();
	    		bootbox.alert(data.msg);
	    	}else{
	    		bootbox.alert(data.msg);
	    	}
	    }
	});
}

//***********************************图片信息结束**********************************************//
//***********************************文档信息开始**********************************************//
function file(){
	$("#right_container").empty();
	var htmlInfo=new StringBuffer();
	htmlInfo.append("<h3>文档信息</h3>");
	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
		htmlInfo.append("<form class=\"form-horizontal col-lg-10 m-t\" id=\"fileForm\">");
			htmlInfo.append("<div class=\"form-group\">");
		    	htmlInfo.append("<div class=\"col-sm-2\">");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"fileupload()\"><i></i>上传</button>");
			    	htmlInfo.append("<div style=\"display: none;\">");
			    	htmlInfo.append("<input type=\"file\" name=\"file\" id=\"file\" onchange=\"postfile()\" />");
			    	htmlInfo.append("</div>");
		    	htmlInfo.append("</div>");
		    	htmlInfo.append("<div class=\"col-sm-8\">");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"applyInfo()\"><i></i>导出申请表</button>");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"transferland()\"><i></i>流转合同备案回执</button>");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"examineInfo()\"><i></i>审批意见</button>");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"investigationReport()\"><i></i>入户调查表</button>");
		    	htmlInfo.append("</div>");
		    	/*htmlInfo.append("<div class=\"col-sm-2\">");
		    	htmlInfo.append("<button class=\"btn btn-primary\" type=\"button\" onclick=\"memberInfo()\"><i></i>导出客户信息</button>");
		    	htmlInfo.append("</div>");*/
		    	htmlInfo.append("<input type=\"hidden\" id=\"fileintoPieceId\" name=\"intoPieceId\" />");
		    htmlInfo.append("</div>");
		htmlInfo.append("</form>");
    	htmlInfo.append("<table class=\"table table-bordered m-t\">");
    	htmlInfo.append("<thead>");
    	htmlInfo.append("<tr>");
    	htmlInfo.append("<th>文档名称</th><th>日期</th><th>查看</th>");
    	htmlInfo.append("</tr>");
    	htmlInfo.append("</thead>");
    	htmlInfo.append("<tbody id=\"fileTable\">");
    	htmlInfo.append("</tbody>");
		htmlInfo.append("</table>");
	htmlInfo.append("</div>");
	$("#right_container").append(htmlInfo.toString());
	$("#fileintoPieceId").val($("#intoPieceId").val());
	refreshFileMedia();
}

function applyInfo(){
	var id= $("#fileintoPieceId").val();
	window.location.href= "/intopiece/applyInfo?intopieceId="+id;
}
function memberInfo(){
	var id= $("#fileintoPieceId").val();
	window.location.href= "/intopiece/memberInfo?intopieceId="+id;
}
function transferland(){
	var id= $("#fileintoPieceId").val();
	window.location.href= "/intopiece/transferland?intopieceId="+id;
}
function examineInfo(){
	var id= $("#fileintoPieceId").val();
	window.location.href= "/intopiece/examineinfo?intopieceId="+id;
}
function investigationReport(){
	var id= $("#fileintoPieceId").val();
	window.location.href= "/intopiece/investigationreport?intopieceId="+id;
}
//加载文档列表
function refreshFileMedia(){
	$.ajax({
		type: "POST",
		url: "/media/filelist",
		//type=4 文档
		data: {"intoPieceId":$("#intoPieceId").val(),"type":"4"},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	$("#fileTable").empty();
	    	if(data != null & data.code == 200){
	    		var htmlInfo=new StringBuffer();
	    		var fileList = data.fileList;
	    		for (var int = 0; int < fileList.length; int++) {
	    			htmlInfo.append("<tr>");	    			
	    			htmlInfo.append("<td>"+fileList[int].name+"</td>");
	    			htmlInfo.append("<td>"+changeDateFormat(fileList[int].time)+"</td>");
	    			htmlInfo.append("<td><a href='/loan/loadpdf?id="+fileList[int].id+"' target='_blank'>下载</a></td>");
	    			htmlInfo.append("</tr>");
				}
	    		var mediaList = data.mediaList;
	    		for (var int = 0; int < mediaList.length; int++) {
	    			htmlInfo.append("<tr>");	    			
	    			htmlInfo.append("<td>"+mediaList[int].name+"</td>");
	    			htmlInfo.append("<td>"+changeDateFormat(mediaList[int].time)+"</td>");
	    			htmlInfo.append("<td><a href='/media/load?id="+mediaList[int].id+"' target='_blank'>下载</a></td>");
	    			htmlInfo.append("</tr>");
				}
	    		$("#fileTable").append(htmlInfo.toString());
	    	}else{
	    		bootbox.alert("系统错误");
	    	}
	    }
	});
}

function fileupload(){
	$("#file").click();
}
function postfile(){
	var formData = new FormData($("#fileForm")[0]); 
	$.ajax({
		url: "/media/filesave", 
        type: "POST", 
        data: formData, 
        async: true, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		refreshFileMedia();
	    	}
	    }
	});
}
//***********************************文档信息结束**********************************************//
//***********************************外呼记录开始**********************************************//
function qimoCallOut(){
	$("#right_container").empty();
	var htmlInfo=new StringBuffer();
	htmlInfo.append("<h3>外呼记录</h3>");
	htmlInfo.append("<div class=\"ibox-content clearfix\" style=\"padding-top: 0px;\">");
		htmlInfo.append("<table class=\"table table-bordered m-t\">");
		htmlInfo.append("<thead>");
		htmlInfo.append("<tr>");
		htmlInfo.append("<th>姓名</th><th>手机号</th><th>开始时间</th><th>结束时间</th><th>录音</th>");
		htmlInfo.append("</tr>");
		htmlInfo.append("</thead>");
		htmlInfo.append("<tbody id=\"callOutTable\">");
		htmlInfo.append("</tbody>");
		htmlInfo.append("</table>");
	htmlInfo.append("</div>");
	$("#right_container").append(htmlInfo.toString());
	var intoPieceId = $("#intoPieceId").val();
	
	$.ajax({
		type: "POST",
		//dataType: "json",
		url: "/qimooutcall/list",
		data: {"intoPieceId":intoPieceId},
	    error: function(request) {
	    	
	    },
	    success: function(data) {
	    	if(data != null){
	    		var html = new StringBuffer();
	    		for (var int = 0; int < data.length; int++) {
	    			html.append("<tr>");
	    			html.append("<td>"+data[int].name+"</td>");
	    			html.append("<td>"+data[int].phoneno+"</td>");
	    			html.append("<td>"+data[int].qimostart+"</td>");
	    			html.append("<td>"+data[int].qimoend+"</td>");
	    			html.append("<td><audio src='"+data[int].voiceurl+"' controls=''></audio></td>");
	    			html.append("</tr>");
				}
	    		$("#callOutTable").append(html.toString());
	    	}
	    }
	});
}
//***********************************外呼记录结束**********************************************//
//***********************************table全局设置**********************************************//
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
//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}
//table里面的操作列显示
function actionFormatter(value, row, index) {
     return [
       '<a class="operate" id="edit" href="#"><span>详情</span></a>',
       '<a class="operate" id="del" href="#"><span>删除</span></a>'
     ].join('   ');
}

//***************************************main页面调用次方法直接跳入进件详情页面**********************************************//
//id 进件id  url返回页面的url
function jsOpeanIntoPieceDetail(id,url){
	//填充详情页面
	init_edit(id,url);
	//默认加载基本信息
	basic();
}
function jsIntopiece(id,url){
	//填充详情页面
	init_edit(id,url);
	//默认加载基本信息
	basic();
}
