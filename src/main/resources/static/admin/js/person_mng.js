$(document).ready(function(){

	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var orgcode = null;				//机构编号
	var curPerson = null;			//记录点击行时选择的人员
	var unroleCode = "";			//记录去掉选择的角色
	var arrRoleCodes = new Array();	//记录去掉选择的角色数组
	var personParaDS;				//人员状态参数集
	var roleTypeParaDS;			//角色类型参数集
	var isdefaultParaDS;			//默认状态参数集
	var userCodeDS;					//用户编号数据集
	var totalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var orgtotalSyncDS = 1;
	var orgcountSyncDS = 0;
	
	var yxRole = "";				//已选角色
	var yxOrg = "";
	
	/*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
	
	function loadSyncDS()
	{
		if(personParaDS == undefined)
		{	//保证仅获取一次
			var presonParaGrpName = "PERSON_STATUS";//人员状态参数类别名称
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":presonParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	personParaDS = eval(data);
			    }
			});
		}
		if(roleTypeParaDS == undefined)
		{	//保证仅获取一次
			var ParaGrpName = "ROLE_TYPE";
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":ParaGrpName},
			    error: function(request) {					
			    },
			    success: function(data) {
			    	roleTypeParaDS = eval(data);
			    }
			});
		}
		if(isdefaultParaDS == undefined)
		{	//保证仅获取一次
			var isdefaultParaGrpName = "IS_DEFAULT";
			$.ajax({
				type: "POST",
				url: "/para/paralist",
				data:{"paraGroupName":isdefaultParaGrpName},
				error: function(request) {					
				},
				success: function(data) {
					isdefaultParaDS = eval(data);
				}
			});
		}
		if(userCodeDS == undefined)
		{	//保证仅获取一次
			$.ajax({
				type: "POST",
				url: "/user/userDS",
			    error: function(request) {					
			    },
			    success: function(data) {
			    	userCodeDS = eval(data);
			    }
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	$("#person_id").on("click",function(){
		userCodeDS = undefined;
		init_global();
		
		loadSyncDS();

		init_div_home();						//初始化tree外层的div视图
		
		init_orgtree();							//初始化tree视图
		
		init_modal_home();						//初始化modal
		init_role_modal();						//初始化展示角色的modal
		
		init_persontable();						//初始化table视图
		get_orgList();							//得到机构树数据
		init_tool_action();						//初始化操作
	});
	
	function init_global()
	{
		$("#content-main").empty();
		$(".initmodal").empty();
	}
	
	/*-------------------------------------主程序：结束  -----------------------------------------*/
	
	
	/*-------------------------------------主窗体左侧机构树(tree操作)-----------------------------------------*/
	//获得机构tree数据
	function get_orgList()
	{
		var firstLevel = false; //用于判断是否只展示一级
		$.ajax({
    	   type:'post',
    	   url: "/org/orgList",//获得一级机构
    	   data:{"firstLevel":firstLevel},
    	   success:function(data){
				init_org(data);			      
    	   }
        });
	}
	//初始化机构tree数据
	function init_org(entry)
	{
        $('#org_treeview').treeview({
	        data: entry,
	        color: "#428bca",
	        levels:1
        });
        //选择一行
        $('#org_treeview').on('nodeSelected', function (event, node) {
        	
        	orgId = node.id;
        	if(node.parentOrgIds == null || node.parentOrgIds == ""){
        		parentOrgIds = node.id;
        	}else{
        		parentOrgIds = node.parentOrgIds + "," +node.id;
        	}
        	init_personList();//生成人员table
        });

	}
	
	/*-------------------------------------主窗体右侧角色表格(table的操作)-----------------------------------------*/
	//当点击机构时生成person table
	function init_personList()
	{
		$('#toolbar').show();
		$('#personlistid').bootstrapTable({
			method: 'post', 
			url:"/person/personList",
			dataType: "json",
			cache: false,
			height: $(window).height()-230,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,15,20,50],
			pageSize:15,
			pageNumber:1,
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
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'personId',
					title: '人员编号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'userId',
					title: '账号名称',
					align: 'center',
					width: '20',
					valign: 'middle',
					formatter: userIdFormatter
				},
				{
					field: 'nameCn',
					title: '中文姓名',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'nameEn',
					title: '英文姓名',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'employeeNo',
					title: '员工编号',
					align: 'center',
					width: '20',
					valign: 'middle',
					visible: false
				},
				{
					field: 'cardNo',
					title: '身份证号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'mobile',
					title: '手机号码',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'isDefault',
					title: '默认身份',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: isDefFormatter
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'seqno',
					title: '序号',
					align: 'center',
					width: '10',
					valign: 'middle',
				},
			],
			onLoadSuccess:function(data){
				var rows = data.rows;
				for(var i=0;i<rows.length;i++)
				{
					if(rows[i].isDelete == 1)
					{
						// 删除行变色
						$('#personlistid tbody').find('tr:eq('+i+')').css("color","#d9534f");
						
					}
				}
	        },
            onClickRow:function(row){
            	curPerson = row;
            	
            	$('#personlistid').each(function() { 
            		
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
		$('#personlistid').bootstrapTable('refresh');
		curPerson=null;
	}
	
	//设置table传递到服务器的参数
	function queryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      orgId: orgId,				//机构编号
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
	
	/*-------------------------------------函数：列格式化-----------------------------------------*/
	
	//table里面显示人员状态的中文描述
	function statusFormatter(value) {
    	for(var i=0;i<personParaDS.length;i++)
    	{
    		if(parseInt(personParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+personParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//table里面显示是否默认身份的中文描述
	function isDefFormatter(value) {
    	for(var i=0;i<isdefaultParaDS.length;i++)
    	{
    		if(parseInt(isdefaultParaDS[i].parameterValue) == value)
    		{
    			return ['<span>'+isdefaultParaDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	//table里面显示账号名称的中文描述
	function userIdFormatter(value) {
    	for(var i=0;i<userCodeDS.length;i++)
    	{
    		if(userCodeDS[i].parameterValue == value)
    		{
    			return ['<span>'+userCodeDS[i].parameterName+'</span>']
	    	}
    	}
	}
	
	
	/*------------------------------------------初始化数据所在的容器（div tree table）-----------------------------------------*/
	
	//初始化tree外层的div
	function init_div_home()
	{
		if($('#ryglid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"ryglid\" style=\"border:0px;margin-left: -10px;margin-right: -10px;\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
		 
	}
	//初始化form modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#personmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"personmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化role table modal
	function init_role_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#rolemodalid').length == 0)
		{
			htmlInfo.append("<div id=\"rolemodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化orgtree
	function init_orgtree()
	{
		var htmlInfo=new StringBuffer();
		if($('#rygltreeid').length == 0)//判断tree是否存在，防止点一次添加一次tree
		{
			
			htmlInfo.append("<div id=\"rygltreeid\">");
			htmlInfo.append("<div class=\"row\" style=\"margin-top:10px;\" id=\"\">");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\" style=\"height: 625px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">组织机构</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			htmlInfo.append("<div style=\"height:550px; overflow:auto\" id=\"org_treeview\"></div>");
		    htmlInfo.append("</div>");
		    htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\" style=\"\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">人员管理</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\" style=\"display: none;\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_sqrole\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权角色");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_sqorg\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权机构");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div id=\"person_tableid\" class=\"\"></div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			$("#ryglid").append(htmlInfo.toString()); 
		}
		
	}
	
	//初始化人员table
	function init_persontable()
	{
		var htmlInfo=new StringBuffer();
		if($('#personlistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table\" id=\"personlistid\">");
			htmlInfo.append("</table>");
			$("#person_tableid").append(htmlInfo.toString()); 
		}
	}
	
	//初始化角色授权table
	function init_roletable()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"roleModal\" data-backdrop=\"static\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"roledialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"roleclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">授权角色</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#rolelistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"rolelistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"rolecancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"rolesubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#rolemodalid").append(htmlInfo.toString()); 
	}
	
	//初始化机构授权table
	function init_orgtable()
	{
		$("#orgModal").remove();
		
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"orgModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"orgdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"orgclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">授权机构</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#orglistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"orglistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"orgcancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"orgsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $(".initmodal").append(htmlInfo.toString()); 
	}
	
	/*------------------------------------------开始表单的操作-----------------------------------------*/
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"personModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"persondialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">人员管理</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"personCode\">人员编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"personId\" id=\"personId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgcode\">所属机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"orgId\" id=\"orgId\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgcode\">所有上级机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"parentOrgIds\" id=\"parentOrgIds\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" style=\"display: none;\" for=\"ucode\">账号ID</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"userId\" id=\"userId\" style=\"display: none;\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"uname\">账号名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"username\" id=\"username\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"upwd\">用户密码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"password\" id=\"password\" type=\"password\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cname\">中文姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameCn\" id=\"nameCn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"ename\">英文姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"nameEn\" id=\"nameEn\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"employeeCode\">员工编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"employeeNo\" id=\"employeeNo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cardNo\">身份证号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cardNo\" id=\"cardNo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"mobile\">手机号码</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"mobile\" id=\"mobile\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"filePath\">图片资料</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"file-loading\" multiple name=\"file\" id=\"uploadimg\" type=\"file\" />");
		htmlInfo.append("<input class=\"form-control\" name=\"filePath\" id=\"filePath\" type=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"roleType\">角色类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"roleType\" name=\"roleType\" class=\"form-control\">");
    	for(var i=0;i<roleTypeParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+roleTypeParaDS[i].parameterValue+">"+roleTypeParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"isDefault\">默认身份</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"isDefault\" name=\"isDefault\" class=\"form-control\">");
		for(var i=0;i<isdefaultParaDS.length;i++)
		{
			htmlInfo.append("<option value="+isdefaultParaDS[i].parameterValue+">"+isdefaultParaDS[i].parameterName+"</option>");
		}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"status\">状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"status\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<personParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+personParaDS[i].parameterValue+">"+personParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"seqno\">序号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"seqno\" type=\"text\" placeholder=\"\"/>");
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
	    $("#personmodalid").append(htmlInfo.toString()); 
	}
	
	//工具栏操作初始化
	function init_tool_action()
	{
		//添加
		$('#btn_add').on("click",function(){
			if($('#persondialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$("#uploadimg").fileinput('clear');
			$('#addform')[0].reset();
			if(parentOrgIds!=null)
			{
				$('#parentOrgIds').val(parentOrgIds);
				$('#orgId').val(orgId);
			}
			$('#personModal').modal('show');
		});

		//编辑
		$('#btn_edit').on("click",function(){
			if(curPerson!=null)
			{
				if($('#persondialogid').length == 0)
				{
					init_modal();//初始化提交表单
					submit_form();//提交表单操作
				}
				$('#personId').val(curPerson.personId);
				$('#parentOrgIds').val(curPerson.parentOrgIds);
				$('#orgId').val(curPerson.orgId);
				$('#nameCn').val(curPerson.nameCn);
 				$('#nameEn').val(curPerson.nameEn);
 				$('#employeeNo').val(curPerson.employeeNo);
 				$('#mobile').val(curPerson.mobile);
 				$('#cardNo').val(curPerson.cardNo);
 				$('#filePath').val(curPerson.filePath);
 				$('#roleType').val(curPerson.roleType);
 				$('#isDefault').val(curPerson.isDefault);
 				$('#seqno').val(curPerson.seqno);
 				$('#status').val(curPerson.status);
 				form_file1();
				$.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/user/getUserByPerson",
                    data: {"userId":curPerson.userId}, //formid
                    error: function (request) {
                        //alert("Connection error");
                    },
                    success: function (data) {
        				$('#userId').val(data.userId);
        				$('#username').val(data.username);
        				$('#password').val(data.password);
        				$('#personModal').modal('show');
                    }
                });
				$('#personModal').modal('show');
			}
			else{
				bootbox.alert("请先选择要修改的人员！","");
			}
		});
		$('#cancel_btn').on("click",function(){
			$("#uploadimg").fileinput('clear');
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$("#uploadimg").fileinput('clear');
			$('#addform')[0].reset();
		});
		
		//删除
		$("#btn_del").on("click",function(){
			
			var selData = $('#personlistid').bootstrapTable('getSelections');//选中的数据
			var personIds ='';
			if(selData.length > 0)
			{
				for(var i=0; i < selData.length; i++)
				{
					personIds += selData[i].personId+',';
				}
				
				personIds = personIds.substring(0,personIds.length-1);
				delPerson(personIds);//删除确认
			}else{
				bootbox.alert("请选择要删除的人员！","");
			}
		});
		
		//授权角色
		$("#btn_sqrole").on("click",function(){
			if(curPerson!=null)
			{
				if($('#roledialogid').length == 0)
				{
					init_roletable();//初始化
					submit_roleform();//提交操作
				}
				$('#roleModal').modal('show');
				findYxRole();
				getRoles();//得到角色数据
			}
			else{
				bootbox.alert("请选择要授权角色的人员！","");
			}
		});
		
		//授权机构
		$("#btn_sqorg").on("click",function(){
			if(curPerson!=null)
			{
				if($('#orgdialogid').length == 0)
				{
					init_orgtable();//初始化
					submit_orgform();//提交操作
				}
				$('#orgModal').modal('show');
				findYxOrg();
				getOrgs();//得到角色数据
			}
			else{
				bootbox.alert("请选择要授权角色的人员！","");
			}
		});
		
	}
	
	function delPerson(currIds)
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
	        message: '确认删除该人员吗?',  
	        callback: function(result) {  
	            if(result) {  
	                bootbox.hideAll();
	                $.ajax({
						type: "POST",
						dataType: "json",
						url: "/person/delPerson",
						data:{"personIds":currIds},
					    error: function(request) {
					        //alert("Connection error");
					    },
					    success: function(data) {
					    	bootbox.alert(data.msg,"");
					    	$('#personlistid').bootstrapTable('refresh');
					    	curPerson=null;
					    }
					});
	            } else {  
	            }  
	        },  
        });
		
	}
	
	//提交表单
	function submit_form()
	{
		var path = "";
		$('#uploadimg').fileinput({
	        theme : 'fa',
	        language : 'zh',
	        uploadAsync : true,//异步上传
	        uploadUrl : "/person/upload",
	        allowedFileExtensions : [ 'JPG','PNG'],
	        maxFileSize : 0,
	        maxFileCount : 5,
	        msgFilesTooMany: "选择上传的文件数量 超过允许的最大数值！",
	        layoutTemplates :{
	            //actionDelete:'', //去除上传预览的缩略图中的删除图标
	            actionUpload:'',//去除上传预览缩略图中的上传图片；
	            //actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
	        },
	    }).on("fileuploaded", function(event, data) { //异步上传成功结果处理
	        path += data.response.src + ";";
	    })
	    
		$('#submit_btn').on("click",function(){
			$("#filePath").val(path);
			var cname = $('#nameCn').val();
            var seqno = $('#seqno').val();
            var seqnoRole=/^[0-9]*$/;
            if(seqnoRole.test(seqno) || seqno == null || seqno == "") {
                if (cname != "") {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/person/addPerson",
                        data: $('#addform').serialize(), //formid
                        error: function (request) {
                            //alert("Connection error");
                        },
                        success: function (data) {
                            bootbox.alert(data.msg, "");
                            $.ajax({
                				type: "POST",
                				url: "/user/userDS",
                			    error: function(request) {					
                			    },
                			    success: function(data) {
                			    	path = "";
                			    	userCodeDS = eval(data);
                			    	$('#addform')[0].reset();
                			    	$('#personModal').modal('hide');
                			    	$('#personlistid').bootstrapTable('refresh');
                			    }
                			});
                            curPerson = null;
                        }
                    });

                } else {
                    showError("中文姓名为必填项，请填写！", '');
                    //bootbox.alert("中文姓名为必填项，请填写！","");
                }
            }else{
                showError("序号为数字格式，请修改", '');
			}
		});
		
		$('#cancel_btn').on("click",function(){
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$('#addform')[0].reset();
		});
	}
	
	/*****************附件图片反选开始*************************/
	function form_file1()
	{
		$.ajax({
			type : "post", 
			url : "/person/getImage", 
			data:{"personId":curPerson.personId},
			success : function(data) { 
				showPhotos(data); 
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				
			} 
		}); 
	}
	function showPhotos(djson){ 
		//在 ajax中 初始化 fileinput 是配置参数是不起作用的  需要 先销毁，再初始化 (重要....................)
		$("#uploadimg").fileinput('destroy');
		var reData = eval(djson);
		// 预览图片json数据组 
		var preList = new Array(); 
		for ( var i = 0; i < reData.length; i++) { 
			var array_element = reData[i]; 
//			// 此处指针对.txt判断，其余自行添加 
//			if(array_element.indexOf(".txt")>0){ 
//				// 非图片类型的展示 
//				preList[i]= "<div class='file-preview-other-frame'><div class='file-preview-other'><span class='file-icon-4x'><i class='fa fa-file-text-o text-info'></i></span></div></div>"
//			}else{ 
//				// 图片类型 
				preList[i]= "data:image/jpg;base64,"+array_element+"";
//			} 
		} 
		var previewJson = preList; 
		// 与上面 预览图片json数据组 对应的config数据 
		var preConfigList = new Array(); 
		for ( var i = 0; i < reData.length; i++) { 
			var array_element = reData[i]; 
			var tjson = {
				caption: "", // 展示的文件名 
				width: '120px', 
				url: '', // 删除url 
				key: "", // 删除是Ajax向后台传递的参数 
				extra: {id: 100} 
			}; 
			preConfigList[i] = tjson; 
		} 
		// 具体参数自行查询 
		$('#uploadimg').fileinput({
			theme : 'fa',
	        language : 'zh',
	        uploadAsync : true,//异步上传
	        uploadUrl : "/person/upload",
	        allowedFileExtensions : [ 'JPG','PNG'],
	        maxFileSize : 0,
	        maxFileCount : 5,
	        msgFilesTooMany: "选择上传的文件数量 超过允许的最大数值！", 
	        allowedPreviewTypes: ['image'],
	        //下面几个就是初始化预览图片的配置      
            initialPreviewAsData: true,  
            initialPreviewFileType: 'image',  
	        initialPreviewShowDelete:true, 
	        initialPreview: previewJson, 
	        initialPreviewConfig: preConfigList,
	        layoutTemplates :{
	            //actionDelete:'', //去除上传预览的缩略图中的删除图标
	            actionUpload:'',//去除上传预览缩略图中的上传图片；
	            //actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
	        },
	    }).on("fileuploaded", function(event, data) { //异步上传成功结果处理
	        $("#filePath").val(data.response.src);
	    })
	}
	/*------------------------------------------结束表单的操作-----------------------------------------*/
	
	/*------------------------------授权角色时的操作--------------------------------------------*/
	//查找已经选择的数据
	function findYxRole()
	{
		$.ajax({
			type: "POST",
			url:"/person/getRoleByPerson",
			data:{"personId":curPerson.personId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxRole = data.objectIds;
		    	countSyncDS++;
		    	getRoles();//得到角色数据
		    }
		});
	}
	//得到角色数据
	function getRoles()
	{
		if(countSyncDS < totalSyncDS)
		{
			return;
		}
		$('#rolelistid').bootstrapTable({
			method: 'post', 
			url: "/role/getroleByOrgId",
			dataType: "json",
			cache: false,
			height: $(window).height()-250,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [1000,2000],
			pageSize:1000,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: rolequeryParams,//参数
			contentType: "application/x-www-form-urlencoded",
			//showRefresh: true,
			smartDisplay:true,
			responseHandler: roleresponseHandler,
			columns: [
				{
					field: 'xz',
					titleTooltip: '全选/全不选',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'roleId',
					title: '角色编号',
					align: 'center',
					visible: false,
					width: '10',
					valign: 'middle'
				},
				{
					field: 'nameCn',
					title: '角色名称',
					align: 'center',
					width: '10',
					valign: 'middle'
				}
			],
			onLoadSuccess:function(data){
				//用于默认选择已选择过的数据
				if(yxRole != "")
				{
					var roleIdArr = yxRole.split(",");
					if(data != null)
					{
						var objs = data.rows;
						for(var i=0;i<objs.length;i++){
							for(var j=0;j<roleIdArr.length;j++){
								if( roleIdArr[j] == objs[i].roleId){
									$("#rolelistid").bootstrapTable("checkBy", {field:'roleId', values:[objs[i].roleId]})
								}
							}
						}
					}
				}
				
            },
            onUncheck: function(row){
            },
            onLoadError: function () {
            }
		});
		$('#rolelistid').bootstrapTable('refresh');
		countSyncDS = 0;
	}
	
	//设置table传递到服务器的参数
	function rolequeryParams(params)
	{
	    var temp = {					//这里的键的名字和控制器的变量名必须一致
	      limit: params.limit,			//页面大小
	      offset: params.offset/params.limit+1,		//页码
	      orgId: curPerson.orgId,	//当前人的机构编号
	    };
	    return temp;
	}
	
	//处理服务器端响应数据，使其适应分页格式
	function roleresponseHandler(res)
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
	
	//提交选择的角色
	function submit_roleform()
	{
		$('#rolesubmit_btn').on("click",function(){
			
			var selData = $('#rolelistid').bootstrapTable('getSelections');//选中的数据
			var roleIds ='';
			for(var i=0; i < selData.length; i++)
			{
				roleIds += selData[i].roleId+',';
			}
			roleIds = roleIds.substring(0,roleIds.length-1);
			
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"/role/addRoleAndPerson",
				data: {"roleIds":roleIds,"personId":curPerson.personId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	$('#roleModal').modal('hide');
			    	$('#personlistid').bootstrapTable('refresh');
			    	curPerson=null;
			    }
			});
				
		});
		
		$('#rolecancel_btn').on("click",function(){
			
		});
		$('#roleclose_btn').on("click",function(){
			
		});
	}
	
	
	//查找已经选择的数据
	function findYxOrg()
	{
		$.ajax({
			type: "POST",
			url:"/person/getOrgByPerson",
			data:{"personId":curPerson.personId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxOrg = data.objectIds;
		    	orgcountSyncDS++;
		    	getOrgs();//得到角色数据
		    }
		});
	}
	//得到角色数据
	function getOrgs()
	{
		if(orgcountSyncDS < orgtotalSyncDS)
		{
			return;
		}
		$('#orglistid').bootstrapTable({
			method:'post',
			url: "/org/orgTreeStr",
			dataType: "json",
            striped:true,
            height: $(window).height()-200,
            sidePagenation:'server',
            idField:'id',
            columns: [
  				{
  					field: 'xz',
  					titleTooltip: '全选/全不选',
  					checkbox: true,
  					formatter : stateFormatter
  				},
  				{
  					field: 'name',
  					title: '名字',
  				},
  				{
  					field: 'orgId',
  					title: '机构编号',
  				}
  			],
            treeShowField: 'name',
            parentIdField: 'pid',
			onLoadSuccess:function(data){
				$('#orglistid').treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                    	$('#orglistid').bootstrapTable('resetWidth');
                    }
                });
            },
            onCheck: function(row){	
            },
            onUncheck: function(row){
            },
            onLoadError: function () {
            }
		});
		$('#orglistid').bootstrapTable('refresh');
		orgcountSyncDS = 0;
	}
	
	//对应的函数进行判断；
	function stateFormatter(value, row, index) {
		if(yxOrg != "")
		{
			var orgcodeArr = yxOrg.split(",");
			for(var j=0;j<orgcodeArr.length;j++)
			{
				if( orgcodeArr[j] == row.orgId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
	}
	
	//提交选择的角色
	function submit_orgform()
	{
		$('#orgsubmit_btn').on("click",function(){
			
			var selData = $('#orglistid').bootstrapTable('getSelections');//选中的数据
			var orgIds ='';
			for(var i=0; i < selData.length; i++)
			{
				orgIds += selData[i].orgId+',';
			}
			orgIds = orgIds.substring(0,orgIds.length-1);
			
			$.ajax({
				type: "POST",
				dataType: "json",
				url:"/person/saveOrgByPerson",
				data: {"orgIds":orgIds,"personId":curPerson.personId},
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	bootbox.alert(data.msg,"");
			    	$('#orgModal').modal('hide');
			    	$('#personlistid').bootstrapTable('refresh');
			    	curPerson=null;
			    }
			});
				
		});
		
		$('#orgcancel_btn').on("click",function(){
			
		});
		$('#orgclose_btn').on("click",function(){
			
		});
	}
	/*------------------------------结束授权角色时的操作--------------------------------------------*/
	
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
