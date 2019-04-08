$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var smsStsParaDS;				//短信发送结果参数集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(smsStsParaDS == undefined)
		{	//保证仅获取一次
			var stsParaGrpName = "SMS_RESULT";			//用户状态参数类别名称
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":stsParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	smsStsParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#sms_record").on("click", function(){
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
			url: "/smshistory/list",
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
					width: '50',
					valign: 'middle',
					visible:false
				},
				{
					field: 'baseOrgId',
					title: '部门',
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
					field: 'content',
					title: '短信内容',
					align: 'center',
					width: '150',
					valign: 'middle'
				},
				{
					field: 'sendDate',
					title: '发送时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'result',
					title: '发送结果',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormatter
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
	
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{	
		var phone = $("#phone").val();
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      pageSize: params.limit,			//页面大小
	      currentPage: (params.offset / params.limit) + 1,		//页码
	      phone:phone,
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
		if($('#container').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px;border-radius:3px;\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<div class=\"pull-left\" style=\"margin-left:20px;\">");	
			htmlInfo.append("手机号：<input type=\"text\" name=\"phone\" id=\"phone\"/>");
			htmlInfo.append("<button id=\"btn_query\" style=\"margin-left:5px;margin-top:5px;\">");
			htmlInfo.append("<span class=\"glyphicon\" aria-hidden=\"true\"></span>查询");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"container\" style=\"border:0px;margin-left:0px;margin-right:5px;\">");
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
		
	}
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示用户状态的中文描述
	function statusFormatter(value) {
		//var data = eval(s_data.responseText);
    	for(var i=0;i<smsStsParaDS.length;i++)
    	{
    		if(parseInt(smsStsParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+smsStsParaDS[i].parameterName+'</span>']
	    	}
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
	     return  '<a class="operate" id="del" href="#"><span>删除</span></a>';
	}

	//工具栏操作初始化
	function init_tool_action()
	{
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
				bootbox.alert("请选择要删除的记录！","");
			}
		});
		
		$("#btn_query").on("click",function(){
			$('#tableid').bootstrapTable('refresh');
		});
	}
	
	function delMany(currIds)
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
	        message: '确认删除该记录吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/smshistory/delete",
						data:{"ids":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	if(data.code == 200){
					    		bootbox.alert(data.msg,"");
						    	$('#tableid').bootstrapTable('refresh');
					    	}else{
					    		bootbox.alert(data.msg,"");
					    	}					    	
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
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
	        message: '确认删除选中的短信吗?',  
	        callback: function(result) {  
	            if(result)
	            {  
	                bootbox.hideAll();
	                $.post("/smshistory/delete",{"ids":entry.id},
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
