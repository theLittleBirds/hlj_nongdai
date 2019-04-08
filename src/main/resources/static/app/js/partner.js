$(document).ready(function(){
	
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var delParaDS;	
	var roleDS;						//角色数据集
	var orgDS;						//组织机构数据集
	var totalSyncDS = 3;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var totalRoleDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countRoleDS = 0;			//同步数据集计数器，当前成功获取个数
	
	var bodyObject = "partner";
	var bodyDivId = bodyObject + "_div";
	var bodyDialogId = bodyObject + "_dialog";
	var bodyFormId = bodyObject + "_form";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		var firstLevel = true; //用于判断是否只展示一级
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
			    	show_view_body();
			    }
			});
		}
		if(roleDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/role/getRoleDS",
				data:{"status": 1,"orgId":""},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	roleDS = eval(data);
			    	countSyncDS ++;
			    	show_view_body();
			    }
			});
		}
	}
	
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#partner_id").on("click", function(){
		init_global();
		
		loadSyncDS();
		
		show_view_body(); 
		
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
				initmgrrole(row);
				edit(row);
			}
			else if(e.currentTarget.id == "del")
			{
				delOne(row);
			}
		}};		
		
		$('#partner_table').bootstrapTable({
			method: 'post', 
			url: "/partner/partnerList",
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
					width: '30',
					align: 'center',
					valign: 'middle',
//					visible:false
				},
				{
					field: 'partnerId',
					title: '合作伙伴id',
					align: 'left',
					width: '10',
					valign: 'middle',
					visible: false
				},
				{
					field: 'partnerCode',
					title: '编号',
					align: 'left',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'cname',
					title: '中文名称',
					align: 'left',
					width: '180',
					valign: 'middle'
				},
				{
					field: 'ename',
					title: '英文名称',
					align: 'left',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'shortCname',
					title: '中文简称',
					align: 'left',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'shortEname',
					title: '英文简称',
					align: 'left',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'mgrRoleCode',
					title: '管理员角色',
					align: 'left',
					width: '60',
					valign: 'middle',
					formatter: mgrroleFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'left',
					width: '60',
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
            	$('#partner_table').each(function() { 
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
	function mgrroleFormatter(value) {
		
    	for(var i=0;i<roleDS.length;i++)
    	{
    		if(roleDS[i].parameterValue == value)
    		{
    			return ['<span>'+roleDS[i].parameterName+'</span>']
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
		if($('#partner_modal').length == 0)
		{
			htmlInfo.append("<div id=\"partner_modal\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化table
	function init_table()
	{
		var htmlInfo=new StringBuffer();
		if($('#partner_table').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"partner_table\">");
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
			$("#mgrRoleCode").empty();
			if($('#partnerModal').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
				//initmgrrole();//初始化管理员角色数据
				schange();//点击下拉列表切换数据集
			}
			$('#addform')[0].reset();
			$('#partnerModal').modal('show');
		});
		
		$("#btn_del").on("click",function()
		{
			var selData = $('#partner_table').bootstrapTable('getSelections');//选中的数据
			var partnerIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					partnerIds += selData[i].partnerId+',';
				}
				
				partnerIds = partnerIds.substring(0,partnerIds.length-1);
				delPartner(partnerIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的数据！","");
			}
		});
	}
	
	function delPartner(currIds)
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
	        message: '确认删除该合作伙伴吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/partner/delPartner",
						data:{"currIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#partner_table').bootstrapTable('refresh');
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
		if(countRoleDS<totalRoleDS)
		{
			return;
		}
		var partnerId = entry.partnerId;
		var partnerCode = entry.partnerCode;
		var cname = entry.cname;
		var ename = entry.ename;
		var shortCname = entry.shortCname;
		var shortEname = entry.shortEname;
		var mgrRoleCode = entry.mgrRoleCode;
		var orgCode = entry.orgCode;
		var publicKey = entry.publicKey;
		var privateKey = entry.privateKey;
		var logo = entry.logo;
		var address = entry.address;
		var phone = entry.phone;
		var fax = entry.fax;
		var postcode = entry.postcode;
		var contactPerson = entry.contactPerson;
		var seqno = entry.seqno;
		var isDelete = entry.isDelete;
		s_update_name = partnerCode;
		
		$('#partnerId').val(partnerId);
		$('#partnerCode').val(partnerCode);
		$('#cname').val(cname);
		$('#ename').val(ename);
		$('#shortCname').val(shortCname);
		$('#shortEname').val(shortEname);
		$('#orgCode').val(orgCode);
		$('#mgrRoleCode').val(mgrRoleCode);
		$('#publicKey').val(publicKey);
		$('#privateKey').val(privateKey);
		$('#logo').val(logo);
		$('#address').val(address);
		$('#phone').val(phone);
		$('#fax').val(fax);
		$('#postcode').val(postcode);
		$('#contactPerson').val(contactPerson);
		$('#seqno').val(seqno);
		$('#isDelete').val(isDelete);
		
		$('#partnerModal').modal('show');
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
	                $.post("/partner/delPartner",{"currIds":entry.partnerId},
        		    function(data)
        		    {
        			    	bootbox.alert(data.msg);
        			    	$('#partner_table').bootstrapTable('refresh');
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
		
		htmlInfo.append("<div class=\"modal fade\" id=\"partnerModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">合作伙伴</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"partnerId\">合作伙伴id</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"partnerId\" id=\"partnerId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"partnerCode\">合作伙伴编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"partnerCode\" id=\"partnerCode\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgCode\">组织机构</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"orgCode\" name=\"orgCode\" class=\"form-control\">");
		
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mgrRoleCode\">管理员角色</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"mgrRoleCode\" name=\"mgrRoleCode\" class=\"form-control\">");
    	for(var i=0;i<roleDS.length;i++)
    	{
    		if(i == 0)
    		{
    			htmlInfo.append("<option value=></option>");
    		}
    		htmlInfo.append("<option value="+roleDS[i].parameterValue+">"+roleDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"publicKey\">公钥</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"publicKey\" id=\"publicKey\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"privateKey\">私钥</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"privateKey\" id=\"privateKey\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"logo\">标志路径</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"logo\" id=\"logo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"address\">通讯地址</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"address\" id=\"address\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"phone\">电话</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"phone\" id=\"phone\" type=\"text\" placeholder=\"\"/>");
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"contactPerson\">联系人姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"contactPerson\" id=\"contactPerson\" type=\"text\" placeholder=\"\"/>");
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
	    $("#partner_modal").append(htmlInfo.toString()); 
	}
	
	function initmgrrole(entry)
	{
		$("#mgrRoleCode").empty();
		var roleDS1 = null;
		if($('#partnerModal').length == 0)//判断表单是否已经初始化
		{
			init_modal();		//初始化提交表单
			submit_form();		//提交表单操作
			schange();
		}
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/role/getRoleDS",
			data:{"status": 1,"orgId": entry.orgCode},
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
		    	$("#mgrRoleCode").append(selectInfo.toString());
		    	countRoleDS++;
		    	edit(entry);
		    }
		});
		
	}
	
	function schange()
	{
		$("#orgCode").change(function () {  
			var roleDS2 = null;
			var _orgCode = $("#orgCode").val();
			$("#mgrRoleCode").empty();
			if(typeof(_orgCode)!="undefined" && _orgCode!=0)
			{
				$.ajax({
					type: "POST",
					//dataType: "json",
					url: "/role/getRoleDS",
					data:{"status": 1,"orgId": _orgCode},
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
				    	$("#mgrRoleCode").append(selectInfo.toString());
				    }
				});
			}
	    });  
	    //$("#finsCode").trigger("change");  
		
	}
	
	//提交表单
	function submit_form()
	{
		$('#submit_btn').on("click",function(){
			var partnerCode = $('#partnerCode').val();
			var cname = $('#cname').val();
			var shortCname = $('#shortCname').val();
			if(partnerCode!=""&cname!=""&& shortCname!="")
			{
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"/partner/save",
					data:$('#addform').serialize(), //formid
				    error: function(request) {
				    },
				    success: function(data) {
				    	bootbox.alert(data.msg,"");
				    	$('#addform')[0].reset();
				    	$('#partnerModal').modal('hide');
				    	$('#partner_table').bootstrapTable('refresh');
				    }
				});
				
			}else{
				showError("合作伙伴编号、中文名称、中文简称为必填项，请填写！", '');
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
