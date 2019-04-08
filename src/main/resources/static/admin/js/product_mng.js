$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	var guaranteeParaDs;			//担保（1，有  2，无）
	var borrowWayParaDs;			//借款方式（1，贷服务费  2，不贷服务费）
	var repayWayParaDs;				//还款方式（1，等额本息  2，先息后本  3，组合贷款  4，惠农通）
	var rateTypeParaDs;				//还款方式（1，等额本息  2，先息后本  3，组合贷款  4，惠农通）
	var totalSyncDS = 4;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(guaranteeParaDs == undefined)
		{	//保证仅获取一次
			var guaranteeParaGrpName = "PRODUCT_GUARANTEE";			//参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":guaranteeParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	guaranteeParaDs = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		if(rateTypeParaDs == undefined)
		{	//保证仅获取一次
			var rateTypeGrpName = "RATE_TYPE";			//利率类型
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":rateTypeGrpName},
				error: function(request) {					
				},
				success: function(data) {
					rateTypeParaDs = eval(data);
					countSyncDS ++;
					showView();
				}
			});
		}
		
		if(borrowWayParaDs == undefined)
		{	//保证仅获取一次
			var borrowWayParaGrpName = "BORROW_WAY";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":borrowWayParaGrpName},
				error: function(request) {					
				},
				success: function(data) {
					borrowWayParaDs = eval(data);
					countSyncDS ++;
					showView();
				}
			});
		}
		
		if(repayWayParaDs == undefined)
		{	//保证仅获取一次
			var repayWayParaGrpName = "REPAYMENT_WAY";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":repayWayParaGrpName},
				error: function(request) {					
				},
				success: function(data) {
					repayWayParaDs = eval(data);
					countSyncDS ++;
					showView(); 
				}
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#product_id").on("click", function(){
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
			url: "/product/productList",
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
					field: 'productId',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'name',
					title: '产品名',
					align: 'center',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'monthRateType',
					title: '利率类型',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatterRT,
				},
				{
					field: 'monthRate',
					title: '利率(%)',
					align: 'center',
					width: '35',
					valign: 'middle'
				},
				{
					field: 'serviceRateType',
					title: '服务费利率类型',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatterRT,
				},
				{
					field: 'serviceRate',
					title: '服务费利率(%)',	
					align: 'center',
					width: '35',
					valign: 'middle'
				},
				{
					field: 'overdueDayRate',
					title: '逾期天利率(%)',
					align: 'center',
					width: '15',
					valign: 'middle'
				},
				{
					field: 'haveGuarantee',
					title: '担保',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatterdb,
				},
				{
					field: 'borrowWay',
					title: '借款方式',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatterbo,
				},
				{
					field: 'repaymentWay',
					title: '还款方式',
					align: 'center',
					width: '15',
					valign: 'middle',
					formatter: statusFormatterre,
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
            onClickRow:function(row){
            	curProduct = row;
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
	
	//table里面显示是否有担保的中文描述
	function statusFormatterdb(value) {
    	for(var i=0;i<guaranteeParaDs.length;i++)
    	{
    		if(parseInt(guaranteeParaDs[i].parameterValue) == value)
    		{
    			return ['<span>'+guaranteeParaDs[i].parameterName+'</span>'];
	    	}
    	}
	}
	//table里面显示利率类型的中文描述
	function statusFormatterRT(value) {
		for(var i=0;i<rateTypeParaDs.length;i++)
		{
			if(parseInt(rateTypeParaDs[i].parameterValue) == value)
			{
				return ['<span>'+rateTypeParaDs[i].parameterName+'</span>'];
			}
		}
	}
	//table里面显示借款方式的中文描述
	function statusFormatterbo(value) {
		for(var i=0;i<borrowWayParaDs.length;i++)
		{
			if(parseInt(borrowWayParaDs[i].parameterValue) == value)
			{
				return ['<span>'+borrowWayParaDs[i].parameterName+'</span>'];
			}
		}
	}
	//table里面显示还款方式的中文描述
	function statusFormatterre(value) {
		for(var i=0;i<repayWayParaDs.length;i++)
		{
			if(parseInt(repayWayParaDs[i].parameterValue) == value)
			{
				return ['<span>'+repayWayParaDs[i].parameterName+'</span>'];
			}
		}
	}
	
	
	/*-------------------------------------函数：视图-----------------------------------------*/
	
	//初始化table外层的div
	function init_layout()
	{
		if($('#product_tableid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px;border-radius:3px;\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<div class=\"pull-left\" style=\"margin-left:20px;margin-top:5px;\">");	
			htmlInfo.append("产品名：<input type=\"text\"  name=\"q_name\" id=\"q_name\"/>");
			htmlInfo.append("<button id=\"btn_query\" style=\"margin-left:5px\">");
			htmlInfo.append("<span class=\"glyphicon\" aria-hidden=\"true\"></span>查询");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"product_tableid\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	function queryParams(params)
	{	
		var name = $("#q_name").val();
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: params.offset/params.limit + 1,		//页码
	      name: name
	    };
	    return temp;
	}
	
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#productmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"productmodalid\">");
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
			$("#product_tableid").append(htmlInfo.toString()); 
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
    
    function getFins(productId){
    	$.ajax({
			type: "POST",
			dataType: "json",
			url: "/product/findFins",
			data:{"productId":productId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	$('#finsId').empty();
		    	$('#finsId').append(data.result);
		    }
		});
    }
	
	//工具栏操作初始化
	function init_tool_action()
	{	
		//添加产品
		$('#btn_add').on("click",function()
		{	
			if($('#productdialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			getFins('noId');
			$('#myModalLabel').val("添加产品");
			$('#productModal').modal('show');
		});
		//删除toolbar
		$("#btn_del").on("click",function()
		{
			var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
			var productIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					productIds += selData[i].productId+',';
				}
				
				productIds = productIds.substring(0,productIds.length-1);
				delproduct(productIds);//删除确认
				
			}else{
				bootbox.alert("请选择要删除的产品！","");
			}
		});
		
		$("#btn_query").on("click",function(){
			$('#tableid').bootstrapTable('refresh');
		});
	}
	
	
	
	function delproduct(currIds)
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
	        message: '确认删除该产品吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/product/delProduct",
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
		var productId = entry.productId;
		var name = entry.name;
		var monthRate = entry.monthRate;
		var monthRateType = entry.monthRateType;
		var serviceRate = entry.serviceRate;
		var serviceRateType = entry.serviceRateType;
		var overdueDayRate = entry.overdueDayRate;
		var haveGuarantee = entry.haveGuarantee;
		var borrowWay = entry.borrowWay;
		var repaymentWay = entry.repaymentWay;
		if($('#productdialogid').length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
		}
		getFins(productId);
		$('#productId').val(productId);
		$('#name').val(name);
		$('#monthRateType').val(monthRateType);
		$('#monthRate').val(monthRate);
		$('#serviceRate').val(serviceRate);
		$('#serviceRateType').val(serviceRateType);
		$('#overdueDayRate').val(overdueDayRate);
		$('#haveGuarantee').val(haveGuarantee);
		$('#borrowWay').val(borrowWay);
		$('#repaymentWay').val(repaymentWay);
		
		$('#productModal').modal('show');
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
	                $.post("/product/delProduct",{"currIds":entry.productId},
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
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
			
			htmlInfo.append("<div class=\"modal fade\" id=\"productModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
			htmlInfo.append("<div class=\"modal-dialog\" id=\"productdialogid\">");
			htmlInfo.append("<div class=\"modal-content\">");
			
			
			htmlInfo.append("<div class=\"modal-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<input name=\"productId\" id=\"productId\" hidden=\"hidden\" />");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsId\">金融机构</label>");
			htmlInfo.append("<div class=\"col-sm-8\" >");
			htmlInfo.append("<select id=\"finsId\" name=\"finsId\" class=\"form-control\">");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"name\">产品名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"name\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3  control-label\" for=\"monthRate\"><select id=\"monthRateType\" name=\"monthRateType\" class=\"form-control required\" style=\"padding: 0; height: 25px; width: 40px; float: right; font-size: 13px;\" aria-required=\"true\">");
			for(var i=0;i<rateTypeParaDs.length;i++)
	    	{
	    		htmlInfo.append("<option value="+rateTypeParaDs[i].parameterValue+">"+rateTypeParaDs[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>利率(%)</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"monthRate\" id=\"monthRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"serviceRate\"><select id=\"serviceRateType\" name=\"serviceRateType\" class=\"form-control required\" style=\"padding: 0; height: 25px; width: 40px; float: right; font-size: 13px;\" aria-required=\"true\">");
			for(var i=0;i<rateTypeParaDs.length;i++)
	    	{
	    		htmlInfo.append("<option value="+rateTypeParaDs[i].parameterValue+">"+rateTypeParaDs[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>服务费率(%)</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"serviceRate\" id=\"serviceRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"overdueDayRate\">逾期天利率(%)</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input class=\"form-control\" name=\"overdueDayRate\" id=\"overdueDayRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<input  name=\"chooseOrgIds\" id=\"chooseOrgIds\" type=\"text\"  hidden=\"hidden\"/>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"haveGuarantee\">担保</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"haveGuarantee\" name=\"haveGuarantee\" class=\"form-control\">");
			for(var i=0;i<guaranteeParaDs.length;i++)
	    	{
	    		htmlInfo.append("<option value="+guaranteeParaDs[i].parameterValue+">"+guaranteeParaDs[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"borrowWay\">借款方式</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"borrowWay\" name=\"borrowWay\" class=\"form-control\">");
			for(var i=0;i<borrowWayParaDs.length;i++)
	    	{
	    		htmlInfo.append("<option value="+borrowWayParaDs[i].parameterValue+">"+borrowWayParaDs[i].parameterName+"</option>");
	    	}
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"repaymentWay\">还款方式</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<select id=\"repaymentWay\" name=\"repaymentWay\" class=\"form-control\">");
			for(var i=0;i<repayWayParaDs.length;i++)
	    	{
	    		htmlInfo.append("<option value="+repayWayParaDs[i].parameterValue+">"+repayWayParaDs[i].parameterName+"</option>");
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
	    $("#productmodalid").append(htmlInfo.toString()); 
	}
		
	
	
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var monthRate = $('#monthRate').val();
			var name = $('#name').val();
			var finsId = $('#finsId').val();
			var url = "/product/save";
			if(monthRate!=""&& name!=""&&finsId!="")
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
				    		bootbox.alert(data.msg,"");
					    	$('#addform')[0].reset();
					    	$('#productModal').modal('hide');
					    	$('#tableid').bootstrapTable('refresh');
					    	
					    
				    	}
				});
				
			}else{
				showError("金融机构，产品名，月利率必填！", '');
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
