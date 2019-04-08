$(document).ready(function(){
	
/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var roleDS;						//角色数据集
	var orgDS;						//组织机构数据集
	var delParaDS;					//删除标志参数集
	var totalSyncDS = 3;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var totalRoleDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countRoleDS = 0;			//同步数据集计数器，当前成功获取个数
	var baseDivId = "fins_div";
	var baseTableId = "fins_table";
	var baseModalId = "fins_modal";
	var baseFormId = "fins_form";
	
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		var firstLevel = true; //用于判断是否只展示一级
		if(roleDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/role/getRoleDS",
				data:{"status":1,"orgId": ""},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	roleDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		
		if(orgDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/org/orgList",
				data:{"firstLevel":firstLevel},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	orgDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		
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
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
	}
	
	
/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#fins_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		showView(); 
		
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
	
	function showView()
	{
		
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		init_layout();							//初始化table外层的div
		
		init_table();							//初始化table
		
		init_modal();							//初始化modal
		
		init_tool_action();						//初始化工具栏操作
		
		//列操作事件定义
		var operateEvents = {'click .operate': function (e, value, row, index){
			if(e.currentTarget.id == "edit")
			{
				initmgrrole(row);
				
				edit(row);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
		}};		
		
		$('#'+baseTableId).bootstrapTable({
			method: 'post', 
			url: "/fins/selectAll",
			dataType: "json",
			cache: false,
			height: $(window).height()-240,
			//striped: true,//使表格带有条纹
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
			//clickToSelect: true,//点击行即可选中单选/复选框  
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
					field: 'finsId',
					title: '机构编号',
					align: 'center',
					width: '98',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '',
					valign: 'middle',
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
					field: 'shortCname',
					title: '中文简称',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'shortEname',
					title: '英文简称',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'orgId',
					title: '组织机构',
					align: 'left',
					width: '400',
					valign: 'middle',
					formatter: orgFormatter
				},
				{
					field: 'mgrRoleCode',
					title: '管理员角色',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false,
					formatter: mgrroleFormatter
				},
				{
					field: 'pbcCode',
					title: '人行编码',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'unionCode',
					title: '银联编码',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'address',
					title: '通讯地址',
					align: 'left',
					width: '',
					valign: 'middle',
				},
				{
					field: 'contactPerson',
					title: '联系人',
					align: 'left',
					width: '100',
					valign: 'middle',
				},
				{
					field: 'phone',
					title: '联系电话',
					align: 'left',
					width: '100',
					valign: 'middle',
				},
				{
					field: 'fax',
					title: '传真',
					align: 'left',
					width: '',
					valign: 'middle',
					visible: false
				},
				{
					field: 'postcode',
					title: '邮政编码',
					align: 'left',
					width: '8',
					valign: 'middle',
					visible: false
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '',
					valign: 'middle'
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '',
					valign: 'middle',
					formatter: actionFormatter,
					events: operateEvents
				}
			],
			onLoadSuccess:function(){
			
            },
            onLoadError: function () {
            	bootbox.alert("数据加载失败！","");
            },
            onClickRow:function(){
            	$('#'+baseTableId).each(function() { 
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
	
	
/*-------------------------------------函数：视图-----------------------------------------*/
	
	//初始化table外层的div
	function init_layout()
	{
		if($('#'+baseDivId).length == 0)
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
			htmlInfo.append("<div id=\""+baseDivId+"\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	
	
	//初始化modal
	function init_modal()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+baseModalId).length == 0)
		{
			htmlInfo.append("<div id=\""+baseModalId+"\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	
	
	//初始化table
	function init_table()
	{
		var htmlInfo = new StringBuffer();
		if($('#'+baseTableId).length == 0)	//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\""+baseTableId+"\">");
			htmlInfo.append("</table>");
			$("#"+baseDivId).append(htmlInfo.toString()); 
		}
		
	}
	
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示用户状态的中文描述
	function mgrroleFormatter(value) {
    	for(var i=0;i<roleDS.length;i++)
    	{
    		if(roleDS[i].parameterValue == value)
    		{
    			return ['<span>'+roleDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	
	function orgFormatter(value) {
		var result = "";
		var values = value.split(",");
		for(var j =0;j<values.length;j++){
	    	for(var i=0;i<orgDS.length;i++)
	    	{
	    		if(orgDS[i].id == values[j])
	    		{
	    			if(result != "")
	    				result+=","
	    			result +=['<span>'+orgDS[i].text+'</span>'];
		    	}
	    	}
		}
		return result;
	}
	
	
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
			$("#mgrrolecode").empty();
			if($('#'+baseFormId).length == 0)
			{
				init_form();//初始化提交表单
				submit_form();//提交表单操作
//				initmgrrole();//初始化管理员角色数据
				schange();//点击下拉列表切换数据集
			}
			$('#addform')[0].reset();
			$('#baseModalLabel').val("添加金融机构");
			$('#'+baseFormId).modal('show');
		});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#'+baseTableId).bootstrapTable('getSelections');//选中的数据
			var objCodes ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					objCodes += selData[i].finsId+',';
				}
				
				objCodes = objCodes.substring(0,objCodes.length-1);
				delMany(objCodes);
			}
			else
			{
				bootbox.alert("请选择要删除的数据记录！","");
			}
		});
	}
	
	
	/*-------------------------------------函数：列操作-----------------------------------------*/
	
	//编辑操作
	function edit(entry)
	{
		if(countRoleDS<totalRoleDS)
		{
			return;
		}
		
		var finsId = entry.finsId;
		var orgId = entry.orgId;
		var cname = entry.cname;
		var ename = entry.ename;
		var shortcname = entry.shortCname;
		var shortename = entry.shortEname;
		var mgrrolecode = entry.mgrRoleCode;
		var pbccode = entry.pbcCode;
		var unioncode = entry.unionCode;
		var address = entry.address;
		var phone = entry.phone;
		var fax = entry.fax;
		var postcode = entry.postcode;
		var contactperson = entry.contactPerson;
		var seqno = entry.seqno;
		var isdelete = entry.isDelete;
		s_update_name = entry.finsId;
//		b_update_name = entry.cname;
		

		
		$('#finsId').val(finsId);
		if (orgId != null) {
			var arr = orgId.replace(/\s/g, '').split(/[,]/g);
			$('#orgId').selectpicker('val', arr);
		}
		$('#cname').val(cname);
		$('#ename').val(ename);
		$('#shortcname').val(shortcname);
		$('#shortename').val(shortename);
		$('#mgrrolecode').val(mgrrolecode);
		$('#pbccode').val(pbccode);
		$('#unioncode').val(unioncode);
		$('#address').val(address);
		$('#phone').val(phone);
		$('#fax').val(fax);
		$('#postcode').val(postcode);
		$('#contactperson').val(contactperson);
		$('#seqno').val(seqno);
		$('#isdelete').val(isdelete);
		
		
		$('#'+baseFormId).modal('show');
	}
	
	
	//删除单个数据记录操作
	function delOne(entry)
	{
		remove("/fins/delFins", entry.finsId, entry.cname+"("+entry.finsId+")");
	}
	
	
	//删除多个数据记录操作
	function delMany(codes)
	{
		remove("/fins/delFins", codes);
	}
	
	
	function remove(acturl, codes)
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
        			    	$('#'+baseTableId).bootstrapTable('refresh');
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
	function init_form()
	{
		var htmlInfo = new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\""+baseFormId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"baseModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"basedialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"baseModalLabel\">金融机构</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"finsId\">机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"finsId\" id=\"finsId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgId\">组织机构</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"orgId\" name=\"orgId\" multiple class=\"selectpicker show-tick form-control\">");
		
    	for(var i=0;i<orgDS.length;i++)
    	{
    		if(i == 0)
    		{
    			htmlInfo.append("<option value=></option>");
    		}
    		htmlInfo.append("<option value="+orgDS[i].id+">"+orgDS[i].text+"</option>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortcname\">中文简称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"shortCname\" id=\"shortcname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"shortename\">英文简称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"shortEname\" id=\"shortename\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mgrrolecode\">管理员角色</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"mgrrolecode\" name=\"mgrRoleCode\" class=\"form-control\">");
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");

		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"address\">通讯地址</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"address\" id=\"address\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"contactperson\">联系人</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"contactPerson\" id=\"contactperson\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"phone\">联系电话</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"phone\" id=\"phone\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"pbccode\">人行编码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"pbcCode\" id=\"pbccode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"unioncode\">银联编码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"unionCode\" id=\"unioncode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"fax\">传真</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"fax\" id=\"fax\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"postcode\">邮政编码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"postcode\" id=\"postcode\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">排列序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		/* */
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isdelete\">删除标志</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"isdelete\" name=\"isDelete\" class=\"form-control\">");
		
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
	    $("#"+baseModalId).append(htmlInfo.toString()); 
	    $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	}
	
	function initmgrrole(entry)
	{
		$("#mgrrolecode").empty();
		var roleDS1 = null;
		var orgId = "";
		if($('#'+baseFormId).length == 0)//判断表单是否已经初始化
		{
			init_form();		//初始化提交表单
			submit_form();		//提交表单操作
			schange();
		}
		if(entry == undefined && entry == null){
			orgId = "";
		}else{
			orgId = entry.orgId;
		}
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/role/getRoleDS",
			data:{"status":1,"orgId": orgId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	var selectInfo = new StringBuffer();
		    	roleDS1 = eval(data);
		    	for(var i=0;i<roleDS1.length;i++)
		    	{
		    		if(i == 0)
		    		{
		    			selectInfo.append("<option value=></option>");
		    		}
		    		selectInfo.append("<option value="+roleDS1[i].parameterValue+">"+roleDS1[i].parameterName+"</option>");
		    	}
		    	$("#mgrrolecode").append(selectInfo.toString());
		    	countRoleDS++;
		    	edit(entry);
		    }
		});
		
	}
	
	function schange()
	{
		$("#orgId").change(function () {  
			var roleDS2 = null;
			var _orgId = $("#orgId").val();
			$("#mgrrolecode").empty();
			if(typeof(_orgId)!="undefined" && _orgId!=0)
			{
				$.ajax({
					type: "POST",
					//dataType: "json",
					url: "/role/getRoleDS",
					data:{"status": 1,"orgId": _orgId},
				    error: function(request) {					
				    },
				    success: function(data) {
				    	var selectInfo = new StringBuffer();
				    	roleDS2 = eval(data);
				    	for(var i=0;i<roleDS2.length;i++)
				    	{
				    		if(i == 0)
				    		{
				    			selectInfo.append("<option value=></option>");
				    		}
				    		selectInfo.append("<option value="+roleDS2[i].parameterValue+">"+roleDS2[i].parameterName+"</option>");
				    	}
				    	$("#mgrrolecode").append(selectInfo.toString());
				    }
				});
			}
	    });  
	    //$("#finsId").trigger("change");  
		
	}
	
		
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){

			var cname = $('#cname').val();

			var phone=$('#phone').val();
            var fax=$('#fax').val();
            var postcode=$('#postcode').val();

            var phoneRole=/^1[3|4|5|7|8][0-9]{9}$/;
            var faxRole= /^(\d{3,4}-)?\d{7,8}$/;
            var postcodeRole= /^[1-9][0-9]{5}$/;
            if(phoneRole.test(phone) || phone == null || phone==""){
				if(faxRole.test(fax) || fax == null || fax==""){
					if(postcodeRole.test(postcode) || postcode == null || postcode==""){
                        if(cname != "")
                        {
                            $.ajax({
                                type: "POST",
                                dataType: "json",
                                url:"/fins/save",
                                data:$('#addform').serialize(), //formid
                                error: function(request) {
                                    //alert("Connection error");
                                },
                                success: function(data) {
                                	bootbox.alert(data.msg,"");
                                    $('#addform')[0].reset();
                                    $('#'+baseFormId).modal('hide');
                                    $('#'+baseTableId).bootstrapTable('refresh');
                                }
                            });

                        }
                        else
                        {
                            showError("中文名称为必填项，请填写！", '');
                            //bootbox.alert("中文名称为必填项，请填写！","");
                        }
					}else{
                        showError("邮政编码格式不对，请检查", '');
                    }
				}else{
                    showError("传真格式为:XXX-12345678或XXXX-1234567或XXXX-12345678", '');
                }
			}else{
                showError("电话格式不对，请检查", '');
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
    
});
