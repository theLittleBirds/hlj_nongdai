$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var typeParaDs;
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(typeParaDs == undefined)
		{	//保证仅获取一次
			var typeParaGrpName = "LENDER_TYPE";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":typeParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	typeParaDs = eval(data);
			    	countSyncDS ++;
			    	showView(); 
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#lender_id").on("click", function(){
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
				edit(row);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
		}};		
		
		$('#tableid').bootstrapTable({
			method: 'post', 
			url: "/lender/lenderList",
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
					field: 'lenderId',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'name',
					title: '姓名/银行',
					align: 'left',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'left',
					width: '65',
					valign: 'middle'
				},
				/*
				{
					field: 'password',
					visible: false,
					title: '用户密码',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				*/
				{
					field: 'phone',
					title: '手机号',
					align: 'left',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'address',
					title: '地址',
					align: 'left',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'lenderName',
					title: '开户名',
					align: 'left',
					width: '15',
					valign: 'middle',
				},
				{
					field: 'lenderBank',
					title: '开户银行名',
					align: 'left',
					width: '15',
					valign: 'middle',
				},
				{
					field: 'lenderCardNo',
					title: '开户账号',
					align: 'left',
					width: '15',
					valign: 'middle',
				},
				{
					field: 'rate',
					title: '利率(%)',
					align: 'left',
					width: '15',
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
		if($('#lender_tableid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add_person\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加出借人");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_add_bank\" style=\"margin-left:5px\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加出借银行");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px;border-radius:3px;\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<div class=\"pull-left\" style=\"margin-left:20px;margin-top:5px;\">");	
			htmlInfo.append("姓名：<input type=\"text\" name=\"q_name\" id=\"q_name\"/>");
			htmlInfo.append("证件号：<input type=\"text\" name=\"q_idCard\" id=\"q_idCard\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("<button id=\"btn_query\" style=\"margin-left:5px;margin-top:5px;\">");
			htmlInfo.append("<span class=\"glyphicon\" aria-hidden=\"true\"></span>查询");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"lender_tableid\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	function queryParams(params)
	{	
		var name = $("#q_name").val();
		var idCard = $("#q_idCard").val();
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: params.offset,		//页码
	      name: name,
	      idCard: idCard,
	    };
	    return temp;
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#lendermodalid').length == 0)
		{
			htmlInfo.append("<div id=\"lendermodalid\">");
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
			$("#lender_tableid").append(htmlInfo.toString()); 
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
	
	//工具栏操作初始化
	function init_tool_action()
	{
		$('#btn_add_person').on("click",function()
		{	
			if($('#lenderdialogid').length != 0){
				$('#lendermodalid').empty();
			}
			if($('#lenderdialogid').length == 0)
			{
				init_modal(1);//初始化提交表单
				submit_form();//提交表单操作
			}
			getTree('noId');
			$('#myModalLabel').val("添加出借人");
			$('#lenderModal').modal('show');
		});
		$('#btn_add_bank').on("click",function()
				{
			if($('#lenderdialogid').length != 0){
				$('#lendermodalid').empty();
			}
			if($('#lenderdialogid').length == 0)
			{
				init_modal(2);//初始化提交表单
				submit_form();//提交表单操作
			}
			getTree('noId');
			$('#myModalLabel').val("添加出借银行");
			$('#lenderModal').modal('show');
				});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
			var lenderIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					lenderIds += selData[i].lenderId+',';
				}
				
				lenderIds = lenderIds.substring(0,lenderIds.length-1);
				delLender(lenderIds);//删除确认
				
			}else{
				bootbox.alert("请选择要删除的用户！","");
			}
		});
		
		$("#btn_query").on("click",function(){
			$('#tableid').bootstrapTable('refresh');
		});
	}
	
	
	
	function delLender(currIds)
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
						url: "/lender/delLender",
						data:{"currIds":currIds},
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
	
	//编辑操作
	function edit(entry)
	{
		var lenderId = entry.lenderId;
		var name = entry.name;
		var idCard = entry.idCard;
		var phone = entry.phone;
		var address = entry.address;
		var lenderName = entry.lenderName;
		var lenderBank = entry.lenderBank;
		var lenderCardNo = entry.lenderCardNo;
		var lenderWechat = entry.lenderWechat;
		var lenderAlipay = entry.lenderAlipay;
		var rate = entry.rate;
		if($('#lenderdialogid').length != 0){
			$('#lendermodalid').empty();
		}
		if($('#lenderdialogid').length == 0)//判断表单是否已经初始化
		{
			init_modal(entry.type);		//初始化提交表单
			submit_form();		//提交表单操作
		}
		getTree(lenderId);
		$('#lenderId').val(lenderId);
		$('#name').val(name);
		$('#idCard').val(idCard);
		$('#phone').val(phone);
		$('#address').val(address);
		$('#lenderName').val(lenderName);
		$('#lenderBank').val(lenderBank);
		$('#lenderCardNo').val(lenderCardNo);
		$('#lenderWechat').val(lenderWechat);
		$('#lenderAlipay').val(lenderAlipay);
		$('#rate').val(rate);
		
		$('#lenderModal').modal('show');
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
	                $.post("/lender/delLender",{"currIds":entry.lenderId},
        		    function(data)
        		    {
        			    if(data.msg == "删除成功")
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
	
	
	/*------------------------------------------函数：表单-----------------------------------------*/
	
	//初始化表单
	function init_modal(type)
	{
		var htmlInfo=new StringBuffer();
		if(type==1){
			
			htmlInfo.append("<div class=\"modal fade\" id=\"lenderModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\"lenderdialogid\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderId\">编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"lenderId\" id=\"lenderId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">姓名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"idCard\">证件号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"idCard\" id=\"idCard\" type=\"text\" maxlength=\"18\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"phone\">手机号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"phone\" id=\"phone\" type=\"text\" maxlength=\"11\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"address\">地址</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"address\" id=\"address\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderName\">开户名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lenderName\" id=\"lenderName\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderBank\">开户银行名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lenderBank\" id=\"lenderBank\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderCardNo\">开户账号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lenderCardNo\" id=\"lenderCardNo\" type=\"text\" maxlength=\"19\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bindOrg\">部门</label>");
			htmlInfo.append("<div class=\"col-sm-8\" >");
			htmlInfo.append("<input class=\"form-control\" name=\"bindOrg\" id=\"bindOrg\" type=\"text\" value=\"\" readonly=\"readonly\" onclick=\"$('#hideDiv').show()\" placeholder=\"绑定部门\"/>");
			htmlInfo.append("<div id=\"hideDiv\" style=\"display: none;\">");
			htmlInfo.append("<div id=\"orgTree\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<button class=\"btn btn-danger\" type=\"button\" onclick=\"$('#hideDiv').hide()\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-eye-open\"></span> 确定");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			//隐藏域，存放选中的出借人/银行Id
			htmlInfo.append("<input  name=\"chooseOrgIds\" id=\"chooseOrgIds\" type=\"text\"  hidden=\"hidden\"/>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderWechat\">收款微信号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lenderWechat\" id=\"lenderWechat\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderAlipay\">收款支付宝账号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"lenderAlipay\" id=\"lenderAlipay\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			//出借人标记
			htmlInfo.append("<input  name=\"type\" id=\"type\" type=\"text\" value=\"1\" hidden=\"hidden\" placeholder=\"\"/>");
			
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
		}else{
			htmlInfo.append("<div class=\"modal fade\" id=\"lenderModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\"lenderdialogid\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"lenderId\">编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" name=\"lenderId\" id=\"lenderId\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">银行名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"rate\">利率(%)</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"rate\" id=\"rate\" type=\"number\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bindOrg\">部门</label>");
			htmlInfo.append("<div class=\"col-sm-8\" >");
			htmlInfo.append("<input class=\"form-control\" name=\"bindOrg\" id=\"bindOrg\" type=\"text\" value=\"\" readonly=\"readonly\" onclick=\"$('#hideDiv').show()\" placeholder=\"绑定部门\"/>");
			htmlInfo.append("<div id=\"hideDiv\" style=\"display: none;\">");
			htmlInfo.append("<div id=\"orgTree\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<button class=\"btn btn-danger\" type=\"button\" onclick=\"$('#hideDiv').hide()\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-eye-open\"></span> 确定");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			//隐藏域，存放选中的出借人/银行Id
			htmlInfo.append("<input  name=\"chooseOrgIds\" id=\"chooseOrgIds\" type=\"text\"  hidden=\"hidden\"/>");
			
			//出借银行标记
			htmlInfo.append("<input  name=\"type\" id=\"type\" type=\"text\" value=\"2\" hidden=\"hidden\" placeholder=\"\"/>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"modal-footer\">");
			htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"cancel_btn\" data-dismiss=\"modal\">取消</button> ");
		    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"submit_btn1\" type=\"button\" class=\"btn btn-primary\">提交</button>");
		    htmlInfo.append("</div>");
		    
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
		}
	    $("#lendermodalid").append(htmlInfo.toString()); 
	}
		
	//部门树组件
	function getTree(lenderId) {
        $.ajax({
            url: "/lender/getOrgTree", // 请求的URL
            dataType: 'json',
            type: "post",
            data:{"lenderId":lenderId},
            success: function (data) {
            	var tree = data.jsonData;
            	var chooseOrgIds = data.ids;
            	var bindOrg = data.names;
            	$("#chooseOrgIds").val(chooseOrgIds);
            	$("#bindOrg").val(bindOrg);
            	$('#orgTree').treeview({
                    color: "#428bca",
                    data: tree,
                    showCheckbox: true,
                    multiSelect: true,
                    onNodeChecked: function (event, data) {
                    	var ids = $("#chooseOrgIds").val();
                        var str = $("#bindOrg").val();
                        if (str.length > 0) {
                        	$("#chooseOrgIds").val(ids + data.id + ',');
                            $("#bindOrg").val(str + data.text + ',');
                        } else {
                        	$("#chooseOrgIds").val(data.id + ',');
                            $("#bindOrg").val(data.text + ',');
                        }
                    },
                    onNodeUnchecked: function (event, data) {
                    	var ids = $("#chooseOrgIds").val();
                        var str = $("#bindOrg").val();
                        $("#bindOrg").val(str.replace(data.text + ",", ""));
                        $("#chooseOrgIds").val(ids.replace(data.id + ",", ""));
                    }
                });


            }
        });
    }
	
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var idCard = $('#idCard').val();
			var name = $('#name').val();
			var url = "/lender/save";
			if(idCard!=""&& name!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$('#addform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	if(data.errorno==1000){
				    		showError("身份证格式不正确！", '');
				    	}else{
				    		bootbox.alert(data.msg,"");
					    	$('#addform')[0].reset();
					    	$('#lenderModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
					    	
					    
				    	}
				    	}
				});
				
			}else{
				showError("身份证，出借人名必填！", '');
			}
		});
		
		$('#submit_btn1').on("click",function(){
			var rate = $('#rate').val();
			var name = $('#name').val();
			var url = "/lender/save";
			if(rate!=""&& name!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:url,
					data:$('#addform').serialize(), //formid
				    error: function(request) {
				        //alert("Connection error");
				    },
				    success: function(data) {
				    	if(data.errorno==1000){
				    		showError("系统错误！", '');
				    	}else{
				    		bootbox.alert(data.msg,"");
					    	$('#addform')[0].reset();
					    	$('#lenderModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
					    	
					    
				    	}
				    	}
				});
				
			}else{
				showError("出借银行，利率必填！", '');
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
