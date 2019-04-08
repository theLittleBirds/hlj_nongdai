$(document).ready(function(){
	/*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/
	
	var orgParaDS;					//机构状态参数集
	var orgTypeDS;					//商户或者进件部门标识
	var productBrandDS;					//商户或者进件部门标识
	var totalSyncDS = 3;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var countSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	var _data;
	var curOrg = null;
	var yxApp = "";
	var apptotalSyncDS = 1;			//同步数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
	var appcountSyncDS = 0;			//同步数据集计数器，当前成功获取个数
	
	/*-------------------------------------取所有组织机构状态数据-----------------------------------------*/
	
	function loadSyncDS(){
		if(orgParaDS == undefined)
		{	//保证仅获取一次
			var parameterName = "ORG_STATUS";//参数USER_STATUS=用户状态
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	orgParaDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		if(orgTypeDS == undefined)
		{	//保证仅获取一次
			var parameterName = "ORG_TYPE";//参数ORG_TYPE=组织机构类型，商户或服务站
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
			    error: function(request) {
			    },
			    success: function(data) {
			    	orgTypeDS = eval(data);
			    	countSyncDS ++;
			    	showView();
			    }
			});
		}
		if(productBrandDS == undefined)
		{	//保证仅获取一次
			var parameterName = "PRODUCT_BRAND";//参数PRODUCT_BRAND=产品品牌
			$.ajax({
				type: "POST",
				//dataType: "json",
				url: "/para/paralist",
				data:{"paraGroupName":parameterName},
				error: function(request) {
				},
				success: function(data) {
					productBrandDS = eval(data);
					countSyncDS ++;
					showView();
				}
			});
		}
	}
	
	/*-------------------------------------主程序：开始  -----------------------------------------*/
	
	//页面入口点
	$("#organization_id").on("click", function(){
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
    
    
	/*-------------------------------------开始对tree的数据操作-----------------------------------------*/
	function showView()
	{
		if(countSyncDS < totalSyncDS)
		{	//同步数据集未全部完成不显示主数据
			return;
		}
		
		init_div_home();						//初始化tree外层的div视图
		
		init_tree();							//初始化tree视图
		
		init_modal_home();						//初始化modal试图
		init_app_modal();						//初始化展示应用的modal
		
		add_click();							//点击添加按钮展示表单
		get_init_tree();						//添加数据
		edit_click();							//点击修改按钮展示表单
		del_click();							//删除
		org_app();							//授权应用
	}
	
	//获得数据并初始化tree数据
	function get_init_tree()
	{
		var firstLevel = false;
		$.ajax({
    	   type:'post',
    	   url: "/org/orgList",
    	   data:{"firstLevel":firstLevel},
    	   success:function(data){
				init_org(data);			      
    	   }
        });
	}
	//初始化tree数据
	function init_org(_data)
	{
        $('#org_treeview').treeview({
	        data: _data,
	        color: "#428bca",
	        //multiSelect: true,//选择多个
	        //showCheckbox: true,
	        levels:1,
	        //showCheckbox: true,
//	        highlightSelected: true,
	        onNodeChecked: function (event, data) {
	        	
             },
	        onNodeUnchecked: function (event, data) {
	        	
            }
        });
        
        $('#org_treeview').on('nodeSelected', function (event, node) {
        	var orgid = node.id;
        	$.ajax({
         	   type:'post',
         	   url: "/org/orgInfo",
         	   data:{"orgid":orgid},
         	   success:function(data){
         		    var dataobj = eval("("+data+")");
     				$('#zs_orgid').val(dataobj.orgId);
     				$('#zs_full_cname').val(dataobj.fullCname);
     				$('#zs_full_ename').val(dataobj.fullEname);
     				$('#zs_short_cname').val(dataobj.shortCname);
     				$('#zs_short_ename').val(dataobj.shortEname);
     				$('#zs_parentorgids').val(dataobj.parentOrgIds);
     				//担保金比例
     				$('#zs_warrantRate').val(dataobj.warrantRate);
     					var productBrand = dataobj.productBrand;
     					var brandNames='';
     					if(productBrand != null & productBrand != ''){
     						var detail = productBrand.split(",");
     						for(var i =0;i<detail.length;i++){
     							for(var j =0;j<productBrandDS.length;j++){
     								if(productBrandDS[j].parameterValue==detail[i]){
     									if(i==detail.length-1){
     										brandNames+=productBrandDS[j].parameterName;
     									}else{
     										brandNames+=productBrandDS[j].parameterName+",";
     									}
     								}
     							}
     						}
     					}
     				$('#zs_product_brand').val(brandNames);
     				$('#zs_idCard').val(dataobj.idCard);
     				$('#zs_cardBank').val(dataobj.cardBank);
     				$('#zs_cardNo').val(dataobj.cardNo);
     				$('#zs_parentorgid').val(dataobj.parentOrgId);
     				$('#zs_leader').val(dataobj.leader);
     				$('#zs_address').val(dataobj.address);
     				$('#zs_phone').val(dataobj.phone);
     				$('#zs_fax').val(dataobj.fax);
     				$('#zs_email').val(dataobj.email);
     				$('#zs_filepath').val(dataobj.filepath);
     				//机构类型
     				for(var i=0;i<orgTypeDS.length;i++)
     				{
     					if(dataobj.orgType==orgTypeDS[i].parameterValue){
     						$('#zs_orgType').val(orgTypeDS[i].parameterName);
     					}
     				}
     				$('#zs_seqno').val(dataobj.seqno);
     				curOrg = dataobj;
         	   }
             });
        });
        //取消选择
        $('#org_treeview').on('nodeUnselected', function (event, node) {
        	
				$('#zs_orgid').val("");
				$('#zs_full_cname').val("");
				$('#zs_full_ename').val("");
				$('#zs_parentorgid').val("");
				$('#zs_idCard').val("");
 				$('#zs_cardBank').val("");
 				$('#zs_cardNo').val("");
				$('#zs_leader').val("");
				$('#zs_address').val("");
				$('#zs_phone').val("");
				$('#zs_fax').val("");
				$('#zs_email').val("");
				$('#zs_filepath').val("");
				//担保金比例
 				$('#zs_warrantRate').val("");
				//机构类型
 				$('#zs_orgType').val("");
				$('#zs_seqno').val("");
				curOrg = null;
       });

	}
	
	//初始化tree外层的div
	function init_div_home()
	{
		if($('#zzjgid').length == 0)
		{
			$("#content-main").empty();
			
			var htmlInfo = new StringBuffer();
			
			htmlInfo.append("<div id=\"zzjgid\">");
			htmlInfo.append("</div>");
			$("#content-main").append(htmlInfo.toString());
		}
	}
	//初始化modal
	function init_modal_home()
	{
		var htmlInfo=new StringBuffer();
		if($('#zzjgmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"zzjgmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化app table modal
	function init_app_modal()
	{
		var htmlInfo=new StringBuffer();
		if($('#appmodalid').length == 0)
		{
			htmlInfo.append("<div id=\"appmodalid\">");
			htmlInfo.append("</div>");
			$(".initmodal").append(htmlInfo.toString());
		}
	}
	//初始化tree
	function init_tree()
	{
		var htmlInfo=new StringBuffer();
		if($('#zzjgtreeid').length == 0)//判断tree是否存在，防止点一次添加一次tree
		{
			
			htmlInfo.append("<div id=\"zzjgtreeid\"  style=\"border:0px;margin-left:0px;margin-right: 5px;\">");
			
			htmlInfo.append("<div class=\"row\" id=\"\" style=\"margin-top: 10px;\">");
			htmlInfo.append("<div id=\"toolbar\" class=\"btn-group col-sm-4\">");
			htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
			htmlInfo.append("</button>");
			htmlInfo.append("<button id=\"btn_org_app\" style=\"margin-left:5px\" class=\"btn btn-success\">");
			htmlInfo.append("<span class=\"glyphicon glyphicon-wrench\" aria-hidden=\"true\"></span>授权应用");
			htmlInfo.append("</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"row\" style=\"margin-top:10px;margin-left:0px;\" id=\"\">");
			htmlInfo.append("<div class=\"panel panel-default col-sm-4\" style=\"height: 610px;\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">组织机构</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			htmlInfo.append("<div style=\"height:550px; overflow:auto\" id=\"org_treeview\" class=\"\"></div>");
		    htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"panel panel-default col-sm-8\">");
			htmlInfo.append("<div class=\"panel-heading\">");
			htmlInfo.append("<h3 class=\"panel-title\">机构明细</h3>");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"panel-body\">");
			htmlInfo.append("<form class=\"form-horizontal\" style=\"margin-top: 15px;\" id=\"\" role=\"form\">");
			htmlInfo.append("<fieldset>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">机构编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_orgid\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">所有上级机构编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_parentorgids\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">上级机构编号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_parentorgid\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">中文名称</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_full_cname\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">品牌</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_product_brand\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">担保金比例/商户付款比例(%)</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_warrantRate\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">联系地址</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_address\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">单位邮箱</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_email\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">负责人姓名</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_leader\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">负责人联系电话</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_phone\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">负责人身份证号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_idCard\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">负责人银行卡号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_cardNo\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">负责人银行卡开户行</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_cardBank\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">图片资料</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_filepath\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">机构类型</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_orgType\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">序号</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			htmlInfo.append("<input readonly class=\"form-control\" id=\"zs_seqno\" type=\"text\" placeholder=\"\"/>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</fieldset>");
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			$("#zzjgid").append(htmlInfo.toString()); 
		}
		
	}
	/*------------------------------------------结束tree的操作-----------------------------------------*/
	
	/*------------------------------------------开始表单的操作-----------------------------------------*/
	//初始化表单
	function init_modal()
	{
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"orgModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"orgdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">组织机构</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		htmlInfo.append("<form class=\"form-horizontal\" id=\"addform\" role=\"form\">");
		htmlInfo.append("<fieldset>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgid\">机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"orgId\" id=\"orgid\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"nativecode\">所有上级机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"parentOrgIds\" id=\"parentorgids\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"parentorgid\">上级机构编号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input readonly class=\"form-control\" name=\"parentOrgId\" id=\"parentorgid\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"full_cname\">机构名称</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"fullCname\" id=\"full_cname\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\">品牌：</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"productBrand\" name=\"productBrand\"  class=\"selectpicker bla bla bli\" multiple data-live-search=\"true\">");
		for(var i =0;i<productBrandDS.length;i++){
				htmlInfo.append("<option value=\""+productBrandDS[i].parameterValue+"\">"+productBrandDS[i].parameterName+"</option>");
		}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		/*htmlInfo.append("<input class=\"form-control\" name=\"productBrand\" id=\"subproductBrand\"  style=\"display: none;\" />");*/
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"warrantRate\">担保金比例/商户付款比例(%)</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"warrantRate\" id=\"warrantRate\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"address\">联系地址</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"address\" id=\"address\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"email\">单位邮箱</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"email\" id=\"email\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"leader\">负责人姓名</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"leader\" id=\"leader\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"phone\">联系电话</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"phone\" id=\"phone\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cardBank\">负责人身份证号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"idCard\" id=\"idCard\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cardNo\">负责人银行卡号</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cardNo\" id=\"cardNo\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"cardBank\">负责人银行卡开户行</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"cardBank\" id=\"cardBank\" type=\"text\" placeholder=\"\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"filepath\">图片资料</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<input class=\"form-control\" name=\"file\" id=\"file\" type=\"file\" multiple/>");
		htmlInfo.append("<input class=\"form-control\" name=\"filepath\" id=\"filepath\" type=\"hidden\"/>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgstatus\">机构状态</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"orgstatus\" name=\"status\" class=\"form-control\">");
    	for(var i=0;i<orgParaDS.length;i++)
    	{
    		htmlInfo.append("<option value="+orgParaDS[i].parameterValue+">"+orgParaDS[i].parameterName+"</option>");
    	}
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		//标识组织机构的类型，商户或者进件部门
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"orgType\">机构类型</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"orgType\" name=\"orgType\" class=\"form-control\">");
		for(var i=0;i<orgTypeDS.length;i++)
		{
			htmlInfo.append("<option value="+orgTypeDS[i].parameterValue+">"+orgTypeDS[i].parameterName+"</option>");
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
	    $("#zzjgmodalid").append(htmlInfo.toString()); 
	}
	
	function clearData()
	{
		$('#zs_orgid').val("");
		$('#zs_full_cname').val("");
		$('#zs_full_ename').val("");
		$('#zs_short_cname').val("");
		$('#zs_short_ename').val("");
		$('#zs_nativecode').val("");
		$('#zs_parentorgid').val("");
		$('#zs_leader').val("");
		//担保金比例
		$('#zs_warrantRate').val("");
		$('#zs_idCard').val("");
		$('#zs_cardBank').val("");
		$('#zs_cardNo').val("");
		$('#zs_product_brand').val("");
		$('#zs_address').val("");
		$('#zs_phone').val("");
		$('#zs_fax').val("");
		$('#zs_email').val("");
		$('#zs_filepath').val("");
		//机构类型
		$('#zs_orgType').val("");
		$('#zs_seqno').val("");
		curOrg = null;
	}
	
	
	//点击添加展示添加页面
	function add_click()
	{
		$('#btn_add').on("click",function(){
			if($('#orgdialogid').length!=0){
				$("#productBrand").selectpicker('refresh');
			}
			if($('#orgdialogid').length == 0)
			{
				init_modal();//初始化提交表单
				submit_form();//提交表单操作
			}
			$("#file").fileinput('clear');
			$('#addform')[0].reset();
			if(curOrg!=null)
			{
				$('#parentorgid').val(curOrg.orgId);
				$('#parentorgids').val(curOrg.parentOrgIds);
			}
			$('.selectpicker').selectpicker({
                'noneSelectedText': '请选择',
                'width':350	
            });
			$('#orgModal').modal('show');
		});
	}
	
	//点击编辑展示修改页面
	function edit_click()
	{
		$('#btn_edit').on("click",function(){
			if(curOrg!=null)
			{
				if($('#orgdialogid').length!=0){
					$("#productBrand").selectpicker('refresh');
				}
				if($('#orgdialogid').length == 0)
				{
					init_modal();//初始化提交表单
					submit_form();//提交表单操作
				}
				$('.selectpicker').selectpicker({
	                'noneSelectedText': '请选择',
	                'width':350
	            });
				var productBrand = curOrg.productBrand;
				var detail ;
				if(productBrand != null & productBrand != ''){
					var detail = productBrand.split(",");								
					for (var int = 0; int < detail.length; int++) {
						$("#productBrand"+detail[int]).prop("checked",true);
					}
				}
				$('#orgid').val(curOrg.orgId);
 				$('#full_cname').val(curOrg.fullCname);
 				$('#full_ename').val(curOrg.fullEname);
 				$('#short_cname').val(curOrg.shortCname);
 				$('#short_ename').val(curOrg.shortEname);
 				$('#parentorgids').val(curOrg.parentOrgIds);
 				//担保金比例
 				$('#warrantRate').val(curOrg.warrantRate);
 				$('#idCard').val(curOrg.idCard);
 				$('#cardBank').val(curOrg.cardBank);
 				$('#cardNo').val(curOrg.cardNo);
 				$("#productBrand").selectpicker('val', detail);
 				/*$("#productBrand").selectpicker('refresh');*/
 				/*$("#productBrand").selectpicker('render');*/
 				$('#parentorgid').val(curOrg.parentOrgId);
 				$('#orgstatus').val(curOrg.status);
 				$('#leader').val(curOrg.leader);
 				$('#address').val(curOrg.address);
 				$('#phone').val(curOrg.phone);
 				$('#fax').val(curOrg.fax);
 				$('#email').val(curOrg.email);
 				$('#filepath').val(curOrg.filepath);
 				//机构类型
 				$('#orgType').val(curOrg.orgType);
 				$('#seqno').val(curOrg.seqno);
 				form_file1();
				$('#orgModal').modal('show');
			
				
	         
			}
			else{
				bootbox.alert("请先选择要修改的机构！","");
			}
		});
		$('#cancel_btn').on("click",function(){
			$("#file").fileinput('clear');
			$('#addform')[0].reset();
		});
		$('#close_btn').on("click",function(){
			$("#file").fileinput('clear');
			$('#addform')[0].reset();
		});
	}
	
	//删除
	function del_click()
	{
		$("#btn_del").on("click",function(){
			
			if(curOrg!=null)
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
			        message: '确认删除该机构吗?',  
			        callback: function(result) {  
			            if(result) {  
			                bootbox.hideAll();
			                $.ajax({
								type: "POST",
								dataType: "json",
								url: "/org/orgDel",
								data:{"orgId":curOrg.orgId},
							    error: function(request) {
							        //alert("Connection error");
							    },
							    success: function(data) {
							    	bootbox.alert(data.msg,"");
							    	clearData();
							    	get_init_tree();//刷新数据
							    }
							});
			            } else {  
			            }  
			        },  
		        });
				
			}
			else{
				bootbox.alert("请先选择要删除的机构！","");
			}
		});
	}
	
	//提交表单
	function submit_form()
	{
		var path = "";
		$('#file').fileinput({
	        theme : 'fa',
	        language : 'zh',
	        uploadAsync : true,//异步上传
	        uploadUrl : "/org/upload",
	        allowedFileExtensions : [ 'JPG','PNG'],
	        maxFileSize : 0,
	        maxFileCount : 5,
	        msgFilesTooMany: "选择上传的文件数量 超过允许的最大数值！"
	    }).on("fileuploaded", function(event, data) { //异步上传成功结果处理
	        path += data.response.src + ";";
	    })
		$('#submit_btn').on("click",function(){
			$("#filepath").val(path);
			var full_cname = $('#full_cname').val();
			/*var productBrand=$("#productBrand").val();
			$("#subproductBrand").val(productBrand);
			alert($("#subproductBrand").val());*/
			var phone = $('#phone').val();
			var fax = $('#fax').val();
			var email = $('#email').val();
			var seqno = $('#seqno').val();
			var v2 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			var v3 = /^[0-9]*$/;
			if(full_cname!="")
			{
				if (v2.test(email) || email == null || email =="") {
					if (v3.test(seqno) || seqno == null || seqno =="") {
						$.ajax({
							type: "POST",
							dataType: "json",
							url:"/org/orgSave",
							data:$('#addform').serialize(), //formid
							error: function(request) {
								//alert("Connection error");
							},
							success: function(data) {
								path = "";
								bootbox.alert(data.msg,"");
								clearData();
								$('#addform')[0].reset();
								$('#orgModal').modal('hide');
								get_init_tree();//刷新数据
							}
						});
					}else {
						showError("序号为数字格式，请修改！", '');
					}
				}else {
					showError("请输入正确的e-mail！", '');
				}
			}else{
				showError("中文名称为必填项，请填写！", '');
				//bootbox.alert("中文名称为必填项，请填写！","");
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
			url : "/org/getImage", 
			data:{"orgId":curOrg.orgId},
			success : function(data) { 
				showPhotos(data); 
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				
			} 
		}); 
	}
	function showPhotos(djson){ 
		//在 ajax中 初始化 fileinput 是配置参数是不起作用的  需要 先销毁，再初始化 (重要....................)
		$("#file").fileinput('destroy');
		var reData = eval(djson);
		// 预览图片json数据组 
		var preList = new Array(); 
		for ( var i = 0; i < reData.length; i++) { 
			var array_element = reData[i]; 
			// 此处指针对.txt判断，其余自行添加 
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
		$('#file').fileinput({
			theme : 'fa',
	        language : 'zh',
	        uploadAsync : true,//异步上传
	        uploadUrl : "/org/upload",
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
	        layoutTemplates:{
	        	actionDelete:'',
	        	actionUpload:''
	        }
	    }).on("fileuploaded", function(event, data) { //异步上传成功结果处理
	        $("#filepath").val(data.response.src);
	    })
	}

	//授权应用
	function org_app()
	{
		$("#btn_org_app").on("click",function(){
			if(curOrg!=null){
				if($('#appdialogid').length == 0)
				{
					init_apptable();//初始化
					submit_appform();//提交操作
				}
				$('#appModal').modal('show');
				findYxApp();
				getApps();//得到角色数据
			}else{
				bootbox.alert("请选择要授权应用的机构！","");
			}
		});
	}
	
	//初始化授权应用table
	function init_apptable()
	{
		$("#appModal").remove();
		
		var htmlInfo=new StringBuffer();
		
		htmlInfo.append("<div class=\"modal fade\" id=\"appModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		htmlInfo.append("<div class=\"modal-dialog\" id=\"appdialogid\">");
		htmlInfo.append("<div class=\"modal-content\">");
		
		htmlInfo.append("<div class=\"modal-header\">");
		htmlInfo.append("<button type=\"button\" id=\"appclose_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		htmlInfo.append("<h4 class=\"modal-title\" id=\"\">授权应用</h4>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-body\">");
		if($('#applistid').length == 0)//判断table是否存在，防止点一次添加一次table
		{
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\"applistid\">");
			htmlInfo.append("</table>");
		}
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"modal-footer\">");
		htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"appcancel_btn\" data-dismiss=\"modal\">取消</button> ");
	    htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"appsubmit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
	    htmlInfo.append("</div>");
	    
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    htmlInfo.append("</div>");
	    $("#appmodalid").append(htmlInfo.toString()); 
	}
	
	//提交选择的应用
	function submit_appform()
	{
		$('#appsubmit_btn').on("click",function(){
			
			var selData = $('#applistid').bootstrapTable('getSelections');//选中的数据
			var appIds ='';
			var flag = false;
			for(var i=0; i < selData.length; i++)
			{
				if (selData[i].appId != undefined) {
					appIds += selData[i].appId + ',';
				}else{
					flag = true;
				}
			}
			appIds = appIds.substring(0,appIds.length-1);
				
				if (appIds != "" || flag == false) {
					$.ajax({
						type : "POST",
						dataType : "json",
						url : "/org/saveAppByOrg",
						data : {
							"appIds" : appIds,
							"orgId" : curOrg.orgId
						},
						error : function(request) {
							//alert("Connection error");
						},
						success : function(data) {
							bootbox.alert(data.msg, "");
							$('#appModal').modal('hide');
							$('#personlistid').bootstrapTable('refresh');
							curOrg = null;
							get_init_tree();
						}
					});
				}else{
					showError("请选择应用进行授权！", '');
				}
		});
		
		$('#appcancel_btn').on("click",function(){
			
		});
		$('#appclose_btn').on("click",function(){
			
		});
	}
	/*------------------------------授权应用时的操作--------------------------------------------*/
	//查找已经选择的数据
	function findYxApp()
	{
		$.ajax({
			type: "POST",
			url:"/org/getAppByOrg",
			data:{"orgId":curOrg.orgId},
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	yxApp = data.objectIds;
		    	appcountSyncDS++;
		    	getApps();//得到应用数据
		    }
		});
	}
	//得到应用数据
	function getApps()
	{
		if(appcountSyncDS < apptotalSyncDS)
		{
			return;
		}
		$('#applistid').bootstrapTable({
			method: 'post', 
			url: "/application/selectApplication",
			dataType: "json",
			striped:true,
			height: $(window).height()-200,
			sidePagenation:'server',//设置为服务器端分页
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
		  					title: '名称',
		  				},
		  				{
		  					field: 'appId',
		  					title: '应用编号',
		  				}
		  			],
		  			treeShowField: 'name',
		            parentIdField: 'pid',
					onLoadSuccess:function(data){
						$('#applistid').treegrid({
		                    initialState: 'collapsed',//收缩
		                    treeColumn: 1,//指明第几列数据改为树形
		                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
		                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
		                    onChange: function() {
		                    	$('#applistid').bootstrapTable('resetWidth');
		                    }
		                });
		            },
		            onUncheck: function(row){
		            },
		            onLoadError: function () {
		            }
				});
				$('#applistid').bootstrapTable('refresh');
				appcountSyncDS = 0;
	}
	
	//对应的函数进行判断；
	function stateFormatter(value, row, index) {
		if(yxApp != "")
		{
			var appcodeArr = yxApp.split(",");
			for(var j=0;j<appcodeArr.length;j++)
			{
				if( appcodeArr[j] == row.appId)
				{
					return {
					      checked : true//设置选中
					};
				}
			}
		}
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
