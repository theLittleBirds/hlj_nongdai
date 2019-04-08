$(document).ready(function(){

    /*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/

    var baseDivId = "base_div";
    var category;

    var curRowBody4;
    var appId;
    var finsId;

    var finsDS;
    var applicationDS;
    var actionCategoryDS;
    var actionOperatorDS;
    var isAllNullDS;
    var item1DS;
    var itemDS;
    var startNodeDS;
    var allNodeDS;

    var totalSyncDS4Body = 6;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
    var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数


    //应用管理
    var bodyDivId = "action_div";
    var bodyTableId = "action_table";
    var bodyDelAction = "/action/delAction";

    var bodyModalId = "action_modal";
    var bodyDialogId = "action_dialog";
    var bodyFormId = "action_form";

    var bodyModalId1 = "action_modal1";
    var bodyDialogId1 = "action_dialog1";
    var bodyFormId1 = "action_form1";
    
	//选项页二
	var opt2Object = "action2";
	var opt2DivId = opt2Object + "_div";
	var opt2TableId = opt2Object + "_table";
	var opt2ModalId = opt2Object + "_modal";
	var opt2DialogId = opt2Object + "_dialog";
	var opt2FormId = opt2Object + "_form";
	var opt2DelAction = "/strategy/delStrule";

    /*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
    function loadSyncDS4Body2()
    {
        //金融机构名称-编号
        if(finsDS == undefined)
        {
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/fins/getFinsDS1",
                error: function(request) {
                },
                success: function(data) {
                    finsDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_opt2();
                }
            });
        }
        //所有的数据项
        if(itemDS == undefined)
        {
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/item/getItemDS",
                error: function(request) {
                },
                success: function(data) {
                    itemDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_opt2();
                }
            });
        }
        //所有节点
        if(allNodeDS == undefined)
        {
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/flowDirection/allNodeDS",
                error: function(request) {
                },
                success: function(data) {
                	allNodeDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_opt2();
                }
            });
        }
        //执行措施类别
        if(actionCategoryDS == undefined)
        {
            var appcatParaGrpName = "ACTION_CATEGORY";	
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/para/paralist",
                data:{"paraGroupName":appcatParaGrpName},
                error: function(request) {
                },
                success: function(data) {
                    actionCategoryDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_opt2();
                }
            });
        }
        //操作运算符
        if(actionOperatorDS == undefined)
        {
            var appcatParaGrpName = "ACTION_OPERATOR";	
            $.ajax({
                type: "POST",
                //dataType: "json",
                url: "/para/paralist",
                data:{"paraGroupName":appcatParaGrpName},
                error: function(request) {
                },
                success: function(data) {
                    actionOperatorDS = eval(data);
                    countSyncDS4Body ++;
                    show_view_opt2();
                }
            });
        }
        //是否全部检查为空
        if(isAllNullDS == undefined)
        {
        	var appcatParaGrpName = "IS_ALL_NULL";	
        	$.ajax({
        		type: "POST",
        		//dataType: "json",
        		url: "/para/paralist",
        		data:{"paraGroupName":appcatParaGrpName},
        		error: function(request) {
        		},
        		success: function(data) {
        			isAllNullDS = eval(data);
        			countSyncDS4Body ++;
        			show_view_opt2();
        		}
        	});
        }

    }

    /*-------------------------------------主程序：开始  -----------------------------------------*/

    //页面入口点
    $("#action_id").on("click", function(){

        init_global();

        init_layout();								//初始化页面布局

//        init_tool_action_body();					//初始化工具栏

        init_table_body();							//初始化table

//        loadSyncDS4Body();							//加载的数据

        show_view_body(); 							//展示table

    });

    function init_global()
    {
        $("#content-main").empty();
        $(".initmodal").empty();
    }

    //展示主数据的table
    function show_view_body(){

//        if(countSyncDS4Body < totalSyncDS4Body){
//            return;  //如果数据没加载完，则不展示页面
//        }
        //三级表格树
        $("#"+bodyTableId).bootstrapTable({
            method:'post',
            url: "/action/selectAction2",
            dataType: "json",
            striped:true,
            height: $(window).height()-260,
            sidePagenation:'server',
            idField:'id',
            columns:[
                {
                    field: 'ck',
                    checkbox: true
                },
                {
                    field:'name',
                    title:'名称',
                }
            ],
            treeShowField: 'name',
            parentIdField: 'pid',
            onLoadSuccess: function(data) {
                $("#"+bodyTableId).treegrid({
                    initialState: 'collapsed',//收缩
                    treeColumn: 1,//指明第几列数据改为树形
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                        $("#"+bodyTableId).bootstrapTable('resetWidth');
                    }
                });
            },
            onClickRow:function(row){
                if(row._level == 2){
                	appId = row._parent.appId;
                    value = row.value;
                    show_view_opt();
                }
                $("#" + bodyTableId).each(function () {
                    var self = this;
                    // 选择行变色
                    $("tr", $(self)).click(function () {
                        var trThis = this;
                        $(self).find(".trSelected").removeClass('trSelected');
                        if ($(trThis).get(0) == $("tr:first", $(self)).get(0)) {
                            return;
                        }
                        $(trThis).addClass('trSelected');
                    });
                });
            },
            onLoadError: function(data) {

            }
        });
    }
  
    
	//选项数据显示
	
	function show_view_opt()
	{
		loadSyncDS4Body2();
		
		show_view_opt2();
	}
	
	
	function show_view_opt2()
	{
		if(countSyncDS4Body < totalSyncDS4Body){
           return;  //如果数据没加载完，则不展示页面
        }
		
		init_tool_action_opt2();
		
		init_table_opt2();
		
		$('#'+opt2Object+'_toolbar').show();

		$("#"+opt2TableId).bootstrapTable({
			url: "/action/actionList",
			method: 'post', 
			dataType: "json",
			cache: false,
			height: $(window).height()-270,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [10,20],
			pageSize:10,
			pageNumber:1,
			sidePagination:'server',//设置为服务器端分页
			queryParams: opt2QueryParams,//参数
			contentType: "application/x-www-form-urlencoded",
//			showRefresh: true,
//			showToggle: true,
			toolbar:'#'+opt2Object+'_toolbar',
			minimumCountColumns: 3,
			smartDisplay:true,
			responseHandler: opt2ResponseHandler,
			columns: [
			  {
				  field: 'section_selection',
				  titleTooltip: '全选/全不选',
				  checkbox: true,
				  align: 'center',
				  valign: 'middle'
			  },
			  {
	              field:'name',
	              title:'名称',
	          },
	          {
	              field:'seqno',
	              title:'排序'
	          }
			],
			onLoadSuccess:function(row){
            },
            onClickRow:function(row){
            	curRowBody4 = row;
            	$("#"+opt2TableId).each(function() { 
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
            	
            },
            onLoadError: function () {
            }
        });
		$("#"+opt2TableId).bootstrapTable('refresh');
	}
	//设置table传递到服务器的参数
	function opt2QueryParams(params)
	{
	    var temp = {						//这里的键的名字和控制器的变量名必须一致
	    	pageSize: params.limit,			//页面大小
	    	currentPage: params.offset/params.limit+1,		//页码
	  		value: value,
	  		appId: appId
	    };
	    return temp;
	 }
	
	//处理服务器端响应数据，使其适应分页格式
	function opt2ResponseHandler(res)
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
	
	  
    //数据项
    function itemDSFormatter(value) {
        for(var i=0;i<itemDS.length;i++)
        {
            if(itemDS[i].parameterValue == value)
            {
                return ['<span>'+itemDS[i].parameterName+'</span>']
            }
        }
    }
    //所有节点
    function allNodeDSFormatter(value) {
        for(var i=0;i<allNodeDS.length;i++)
        {
            if(allNodeDS[i].parameterValue == value)
            {
                return ['<span>'+allNodeDS[i].parameterName+'</span>']
            }
        }
    }
    
    //检查数据项--如果不判断是否为空，则出错
    function checkItemDSFormatter(value){
    	var htmlInfo="";
    	if(value!=null && value!=""){
    		var value1=value.split(",");
    		htmlInfo=new StringBuffer;
    		for(var j=0;j<value1.length;j++){
    			for(var i=0;i<itemDS.length;i++){
    				if(itemDS[i].parameterValue == value1[j]){
    					htmlInfo.append("<span>"+itemDS[i].parameterName+"</span>");
    					if(j!=value1.length-1){
    						htmlInfo.append(";");
    					}
    				}
    			}
    		}
    	}
		return [htmlInfo.toString()]
	}
    
    //初始化table外层的div
    function init_layout()
    {

        if($("#"+baseDivId).length == 0)
        {
            var htmlInfo = new StringBuffer();
            //整个页面div
            htmlInfo.append("<div class=\"row\" id=\""+baseDivId+"\">");

            htmlInfo.append("<div class=\"panel panel-default col-sm-5\" style=\"\">");

            htmlInfo.append("<div class=\"panel-heading\" style=\"height: 42.5px;\">");
            htmlInfo.append("<h3 class=\"panel-title\">措施分类</h3>");
            htmlInfo.append("</div>");
            //添加工具栏div
            htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
            htmlInfo.append("</div>");
            //展示表格数据div
            htmlInfo.append("<div id=\""+bodyDivId+"\">");
            htmlInfo.append("</div>");

            htmlInfo.append("</div>");		//panel
            
            htmlInfo.append("<div class=\"panel panel-default col-sm-7\" style=\"\">");
			
			htmlInfo.append("<ul id=\"myTab\" class=\"nav nav-tabs\">");
			htmlInfo.append("<li  class=\"active\"><a href=\"."+opt2DivId+"\" data-toggle=\"tab\">执行措施</a></li>");
			htmlInfo.append("</ul>");
			
			htmlInfo.append("<div id=\"myTabContent\" class=\"tab-content\">");	
			//opt2 tab
			htmlInfo.append("<div class=\"tab-pane fade in active " + opt2DivId + "\">");
			
			htmlInfo.append("<div id=\"" + opt2Object + "_toolbar\" class=\"btn-group\" style=\"display: none;margin-top:5px;margin-bottom:5px;\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div id=\"" + opt2DivId + "\">");
	        htmlInfo.append("</div>");
	        
			htmlInfo.append("</div>");
	    	htmlInfo.append("</div>");		//myTabContent
			htmlInfo.append("</div>");		//panel

            htmlInfo.append("</div>");

            $("#content-main").append(htmlInfo.toString());
        }
    }
    //主数据工具栏操作初始化
    function init_tool_action_opt2()
    {
            $("#"+opt2Object+"_toolbar").empty();

            var htmlInfo = new StringBuffer();

            htmlInfo.append("<button id=\"btn_add\" class=\"btn btn-primary\">");
            htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>添加");
            htmlInfo.append("</button>");
            htmlInfo.append("<button id=\"btn_edit\" style=\"margin-left:5px\" class=\"btn btn-info\">");
            htmlInfo.append("<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>编辑");
            htmlInfo.append("</button>");
            htmlInfo.append("<button id=\"btn_del\" style=\"margin-left:5px\" class=\"btn btn-danger\">");
            htmlInfo.append("<span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>删除");
            htmlInfo.append("</button>");

            $("#"+opt2Object+"_toolbar").append(htmlInfo.toString());

            $('#btn_add').on("click",function()
            {
                $("#curRowBody_appId").empty();
                init_modal_body();//初始化提交表单
                init_form_action_body();//提交表单操作
                $("#div_1").empty(); $("#div_1").css({'display':'none'})
                $("#div_2").empty(); $("#div_2").css({'display':'none'})
                $("#div_3").empty(); $("#div_3").css({'display':'none'})
                $("#div_4").empty(); $("#div_4").css({'display':'none'})
                $("#row_nodeId").empty();$("#row_nodeId").css({'display':'none'})
                schange();
                schange1();
                change2();
                $("#"+bodyFormId)[0].reset();
                $("#"+bodyModalId).modal('show');
            });

            $('#btn_edit').on("click",function()
            {
                if(curRowBody4.actionId != undefined){
                    edit(curRowBody4);
                }else{
                    bootbox.alert("请选择执行措施修改！","");
                }
            });

            $("#btn_del").on("click",function()
            {
                var selData = $('#'+opt2TableId).bootstrapTable('getSelections');//选中的数据
                if(selData.length == 1 && selData[0]._level == 0){
                    bootbox.alert("请选择执行措施删除！","");
                }else{
                    var objCodes = '';
                    if(selData.length > 0)
                    {
                        for(var i=0;i<selData.length;i++)
                        {
                            objCodes += selData[i].actionId + ',';
                        }
                        objCodes = objCodes.substring(0, objCodes.length-1);
                        delMany(bodyDelAction, objCodes, '#'+opt2TableId);
                    }
                    else
                    {
                        bootbox.alert("请选择要删除的数据记录！","");
                    }
                }
            });
    }
    
    //执行措施类别编号-名称转换
    function changeCategory(value){
    	 for(var i=0;i<actionCategoryDS.length;i++)
         {
             if(actionCategoryDS[i].parameterValue == value)
             {
                 return [actionCategoryDS[i].parameterName]
             }
         }
    }
    
    //主数据编辑操作
    function edit(entry)
    {
        if(entry != null && entry != "")
        {
            init_modal_body1();
            init_form_action_body1();

            $("#row_nodeId1").empty();$("#row_nodeId1").css({'display':'none'})
            $("#div_11").empty();$("#div_11").css({'display':'none'})
            $("#div_21").empty();$("#div_21").css({'display':'none'})
            $("#div_31").empty(); $("#div_31").css({'display':'none'})
            $("#div_41").empty();$("#div_41").css({'display':'none'})
            $("#div_51").empty();$("#div_51").css({'display':'none'})
            $("#div_61").empty();$("#div_61").css({'display':'none'})

            appId=entry.appId;
            category = entry.category;
            $('#curRowBody1_actionId').val(entry.actionId);
            $('#curRowBody1_appId').val(entry.appId);
            $('#curRowBody1_name').val(entry.name);
            $('#curRowBody1_category').val(changeCategory(entry.category))
            if(entry.category == 1){
                $("#row_nodeId1").empty(); $("#row_nodeId1").css({'display':'none'});
                $("#div_51").empty();$("#div_41").css({'display':'none'});$("#div_61").empty();
                $.ajax({
                    type: "POST",
                    url: "/entity/getItem1DS",
                    data:{"appId" : appId},
                    error: function(request) {
                    },
                    success: function(data) {
                        item1DS = eval(data);
                        //
                        var htmlInfo=new StringBuffer();
                        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_fromItemId\">源数据项</label>");
                        htmlInfo.append("<div class=\"col-sm-8\">");
                        htmlInfo.append("<select id=\"curRowBody1_fromItemId\" name=\"fromItemId\" class=\"form-control\">");
                        for(var i=0;i<item1DS.length;i++)
                        {
                            if(i == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                        }
                        htmlInfo.append("</select>");
                        htmlInfo.append("</div>");
                        $("#div_11").append(htmlInfo.toString());
                        $("#div_11").css({'display':'block'})
                        //
                        var htmlInfo1=new StringBuffer();
                        htmlInfo1.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_operator\">操作运算</label>");
                        htmlInfo1.append("<div class=\"col-sm-8\">");
                        htmlInfo1.append("<select id=\"curRowBody1_operator\" name=\"operator\" class=\"form-control\">");
                        for(var i=0;i<actionOperatorDS.length;i++)
                        {
                            if(i == 0)
                            {
                                htmlInfo1.append("<option value=></option>");
                            }
                            htmlInfo1.append("<option value="+actionOperatorDS[i].parameterValue+">"+actionOperatorDS[i].parameterName+"</option>");
                        }
                        htmlInfo1.append("</select>");
                        htmlInfo1.append("</div>");
                        $("#div_21").append(htmlInfo1.toString());
                        $("#div_21").css({'display':'block'})
                        //
                        var htmlInfo2=new StringBuffer();
                        htmlInfo2.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_optValue\">运算值</label>");
                        htmlInfo2.append("<div class=\"col-sm-8\">");
                        htmlInfo2.append("<input class=\"form-control\" name=\"optValue\" id=\"curRowBody1_optValue\" type=\"text\" placeholder=\"\"/>");
                        htmlInfo2.append("</div>");
                        $("#div_31").append(htmlInfo2.toString());
                        $("#div_31").css({'display':'block'})
                        //
                        var htmlInfo3=new StringBuffer();
                        htmlInfo3.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_toItemId\">目标数据项</label>");
                        htmlInfo3.append("<div class=\"col-sm-8\">");
                        htmlInfo3.append("<select id=\"curRowBody1_toItemId\" name=\"toItemId\" class=\"form-control\">");
                        for(var i=0;i<item1DS.length;i++)
                        {
                            if(i == 0)
                            {
                                htmlInfo3.append("<option value=></option>");
                            }
                            htmlInfo3.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                        }
                        htmlInfo3.append("</select>");
                        htmlInfo3.append("</div>");
                        $("#div_41").append(htmlInfo3.toString());
                        $("#div_41").css({'display':'block'})
                        $('#curRowBody1_fromItemId').val(entry.fromItemId);
                        $('#curRowBody1_operator').val(entry.operator);
                        $('#curRowBody1_optValue').val(entry.optValue);
                        $('#curRowBody1_toItemId').val(entry.toItemId);
                    }
                });
            }
            if(entry.category == 2){
                $("#div_11").empty(); $("#div_11").css({'display':'none'})
                $("#div_21").empty(); $("#div_21").css({'display':'none'})
                $("#div_31").empty(); $("#div_31").css({'display':'none'})
                $("#div_41").empty(); $("#div_41").css({'display':'none'})
                $("#div_51").empty(); $("#div_51").css({'display':'none'})
                $("#div_61").empty(); $("#div_61").css({'display':'none'})
                $.ajax({
                    type: "POST",
                    url: "/flowDirection/getStartNodeDSByAppId",
                    data:{"appId":appId},
                    error: function(request) {
                    },
                    success: function(data) {
                        startNodeDS = eval(data);
                        var htmlInfo=new StringBuffer();
                        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_toNodeId\">目标节点</label>");
                        htmlInfo.append("<div class=\"col-sm-8\">");
                        htmlInfo.append("<select id=\"curRowBody1_toNodeId\" name=\"toNodeId\" class=\"form-control\">");
                        for(var i=0;i<startNodeDS.length;i++)
                        {
                            if(i == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+startNodeDS[i].parameterValue+">"+startNodeDS[i].parameterName+"</option>");
                        }
                        htmlInfo.append("</select>");
                        htmlInfo.append("</div>");
                        $("#row_nodeId1").append(htmlInfo.toString());
                        $("#row_nodeId1").css({'display':'block'})
                        $('#curRowBody1_toNodeId').val(entry.toNodeId);
                    }
                });
            }
        if(entry.category == 3){
        	 $("#div_11").empty(); $("#div_11").css({'display':'none'})
             $("#div_21").empty(); $("#div_21").css({'display':'none'})
             $("#div_31").empty(); $("#div_31").css({'display':'none'})
             $("#div_41").empty(); $("#div_41").css({'display':'none'})
             $("#row_nodeId1").empty(); $("#row_nodeId1").css({'display':'none'})
             $.ajax({
                 type: "POST",
                 url: "/entity/getItem1DS",
                 data:{"appId" : appId},
                 error: function(request) {
                 },
                 success: function(data) {
                     item1DS = eval(data);
                     //
                     var htmlInfo=new StringBuffer();
                     htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_checkItemIds\">检查数据项</label>");
                     htmlInfo.append("<div class=\"col-sm-8\">");
                     htmlInfo.append("<select id=\"curRowBody1_checkItemIds\" name=\"checkItemIds\" multiple class=\"selectpicker show-tick form-control\">");
                     for(var i=0;i<item1DS.length;i++)
                     {
                         htmlInfo.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                     }
                     htmlInfo.append("</select>");
                     htmlInfo.append("</div>");
                     $("#div_51").append(htmlInfo.toString());
                     $("#div_51").css({'display':'block'});
                     $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
                     if(entry.checkItemIds)
         			{
         				var arr4 = entry.checkItemIds.replace(/\s/g,'').split(/[,]/g);
         				$('#curRowBody1_checkItemIds').selectpicker('val', arr4);
         			}
                 }
             });
             //
             var htmlInfo1=new StringBuffer();
             htmlInfo1.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_isAllNull\">是否全部检查</label>");
             htmlInfo1.append("<div class=\"col-sm-8\">");
             htmlInfo1.append("<select id=\"curRowBody1_isAllNull\" name=\"isAllNull\" class=\"form-control\">");
             for(var i=0;i<isAllNullDS.length;i++)
             {
                 if(i == 0)
                 {
                     htmlInfo1.append("<option value=></option>");
                 }
                 htmlInfo1.append("<option value="+isAllNullDS[i].parameterValue+">"+isAllNullDS[i].parameterName+"</option>");
             }
             htmlInfo1.append("</select>");
             htmlInfo1.append("</div>");

             $("#div_61").append(htmlInfo1.toString());
             $("#div_61").css({'display':'block'})
             $('#curRowBody1_isAllNull').val(entry.isAllNull);
        }
        $('#curRowBody1_seqno').val(entry.seqno);
        $("#"+bodyModalId1).modal('show');
    }
        else{
            bootbox.alert("请选择要修改的数据！","");
        }
    }

    //删除多个数据记录操作
    function delMany(acturl, codes, refreshid)
    {
        remove(acturl, codes, refreshid);
    }


    function remove(acturl, codes, refreshid)
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
                            $(refreshid).bootstrapTable('refresh');
                        }, "json");
                }
                else
                {
                }
            },
        });
    }

    //初始化主数据的table
    function init_table_body()
    {
        if($("#"+bodyTableId).length == 0)//判断table是否存在，防止点一次添加一次table
        {
            var htmlInfo = new StringBuffer();
            htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+bodyTableId+"\">");
            htmlInfo.append("</table>");
            $("#"+bodyDivId).append(htmlInfo.toString());
        }
    }

	//初始化当前选项页二的table
	function init_table_opt2()
	{
		if($("#"+opt2TableId).length == 0)//判断table是否存在，防止点一次添加一次table
		{
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<table class=\"table table-no-bordered\" id=\""+opt2TableId+"\">");
			htmlInfo.append("</table>");
			$("#"+opt2DivId).append(htmlInfo.toString());
		}
	}

    //初始化表单
    function init_modal_body()
    {
        $("#"+bodyModalId).remove();

        var htmlInfo = new StringBuffer();

        htmlInfo.append("<div class=\"modal fade\" id=\""+bodyModalId+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
        htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId+"\">");
        htmlInfo.append("<div class=\"modal-content\">");

        htmlInfo.append("<div class=\"modal-header\">");
        htmlInfo.append("<button type=\"button\" id=\"body_close_btn\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
        htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">执行措施</h4>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-body\">");
        htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
        htmlInfo.append("<fieldset>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_actionId\">措施id</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input readonly class=\"form-control\" name=\"actionId\" id=\"curRowBody_actionId\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");


        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_finsId\">金融机构</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<select id=\"curRowBody_finsId\" name=\"finsId\" class=\"form-control\">");
        for(var i=0;i<finsDS.length;i++)
        {
            if(i == 0)
            {
                htmlInfo.append("<option value=></option>");
            }
            htmlInfo.append("<option value="+finsDS[i].parameterValue+">"+finsDS[i].parameterName+"</option>");
        }
        htmlInfo.append("</select>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_appId\">产品</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<select id=\"curRowBody_appId\" name=\"appId\" class=\"form-control\">");
//        for(var i=0;i<applicationDS.length;i++)
//        {
//        	if(i == 0)
//            {
//                htmlInfo.append("<option value=></option>");
//            }
//            htmlInfo.append("<option value="+applicationDS[i].parameterValue+">"+applicationDS[i].parameterName+"</option>");
//        }
        htmlInfo.append("</select>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_name\">名称</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"curRowBody_name\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_category\">执行措施类别</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<select id=\"curRowBody_category\" name=\"category\" class=\"form-control\">");
        for(var i=0;i<actionCategoryDS.length;i++)
        {
            if(i == 0)
            {
                htmlInfo.append("<option value=></option>");
            }
            htmlInfo.append("<option value="+actionCategoryDS[i].parameterValue+">"+actionCategoryDS[i].parameterName+"</option>");
        }
        htmlInfo.append("</select>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_1\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_2\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_3\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_4\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"row_nodeId\" style=\"display:none\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\" id=\"div_5\" style=\"display:none\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\" id=\"div_6\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_seqno\">排列序号</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"curRowBody_seqno\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("</fieldset>");
        htmlInfo.append("</form>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-footer\">");
        htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"body_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
        htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"body_submit_btn\" type=\"button\" class=\"btn btn-primary\">提交</button>");
        htmlInfo.append("</div>");

        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        $(".initmodal").append(htmlInfo.toString());
    }


    function schange(){
        $("#curRowBody_finsId").change(function(){
            var finsId="";
            finsId = $("#curRowBody_finsId").val();
            $("#curRowBody_appId").empty();
            if(finsId!=null && finsId!=""){
                $.ajax({
                    type: "POST",
                    url: "/application/getApplicationByFins",
                    data:{"finsId" : finsId},
                    error: function(request) {
                    },
                    success: function(data) {
                        applicationDS = eval(data);
                        var htmlInfo = new StringBuffer();
                        for(var j=0;j<applicationDS.length;j++)
                        {
                            if(j == 0)
                            {
                                htmlInfo.append("<option value=></option>");
                            }
                            htmlInfo.append("<option value="+applicationDS[j].parameterValue+">"+applicationDS[j].parameterName+"</option>");
                        }
                        $("#curRowBody_appId").append(htmlInfo.toString());
                    }
                });
            }else{
                $("#curRowBody_appId").empty();$("#curRowBody_appId").css({'display':'none'})
            }
        })
    }
    function schange1(){
        $("#curRowBody_category").change(function(){
            var value=$("#curRowBody_category").val();
            var appId=$("#curRowBody_appId").val();
            if(appId==null || appId==""){
                bootbox.alert("请选择机构和产品","");
            }else if(value==""){
                $("#div_1").empty(); $("#div_1").css({'display':'none'})
                $("#div_2").empty(); $("#div_2").css({'display':'none'})
                $("#div_3").empty(); $("#div_3").css({'display':'none'})
                $("#div_4").empty(); $("#div_4").css({'display':'none'})
                $("#div_5").empty(); $("#div_5").css({'display':'none'})
                $("#div_6").empty(); $("#div_6").css({'display':'none'})
                $("#row_nodeId").empty();$("#row_nodeId").css({'display':'none'});
            }else{
                if(value==1){
                    $("#row_nodeId").empty();$("#row_nodeId").css({'display':'none'});
                    $("#div_5").empty(); $("#div_5").css({'display':'none'});
                    $.ajax({
                        type: "POST",
                        url: "/entity/getItem1DS",
                        data:{"appId" : appId},
                        error: function(request) {
                        },
                        success: function(data) {
                            item1DS = eval(data);
                            //
                            var htmlInfo=new StringBuffer();
                            htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_fromItemId\">源数据项</label>");
                            htmlInfo.append("<div class=\"col-sm-8\">");
                            htmlInfo.append("<select id=\"curRowBody_fromItemId\" name=\"fromItemId\" class=\"form-control\">");
                            for(var i=0;i<item1DS.length;i++)
                            {
                                if(i == 0)
                                {
                                    htmlInfo.append("<option value=></option>");
                                }
                                htmlInfo.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                            }
                            htmlInfo.append("</select>");
                            htmlInfo.append("</div>");
                            $("#div_1").append(htmlInfo.toString());
                            $("#div_1").css({'display':'block'})
                            //
                            var htmlInfo1=new StringBuffer();
                            htmlInfo1.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_operator\">操作运算</label>");
                            htmlInfo1.append("<div class=\"col-sm-8\">");
                            htmlInfo1.append("<select id=\"curRowBody_operator\" name=\"operator\" class=\"form-control\">");
                            for(var i=0;i<actionOperatorDS.length;i++)
                            {
                                if(i == 0)
                                {
                                    htmlInfo1.append("<option value=></option>");
                                }
                                htmlInfo1.append("<option value="+actionOperatorDS[i].parameterValue+">"+actionOperatorDS[i].parameterName+"</option>");
                            }
                            htmlInfo1.append("</select>");
                            htmlInfo1.append("</div>");
                            $("#div_2").append(htmlInfo1.toString());
                            $("#div_2").css({'display':'block'})
                            //
                            var htmlInfo2=new StringBuffer();
                            htmlInfo2.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_optValue\">运算值</label>");
                            htmlInfo2.append("<div class=\"col-sm-8\">");
                            htmlInfo2.append("<input class=\"form-control\" name=\"optValue\" id=\"curRowBody_optValue\" type=\"text\" placeholder=\"\"/>");
                            htmlInfo2.append("</div>");
                            $("#div_3").append(htmlInfo2.toString());
                            $("#div_3").css({'display':'block'})
                            //
                            var htmlInfo3=new StringBuffer();
                            htmlInfo3.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_toItemId\">目标数据项</label>");
                            htmlInfo3.append("<div class=\"col-sm-8\">");
                            htmlInfo3.append("<select id=\"curRowBody_toItemId\" name=\"toItemId\" class=\"form-control\">");
                            for(var i=0;i<item1DS.length;i++)
                            {
                                if(i == 0)
                                {
                                    htmlInfo3.append("<option value=></option>");
                                }
                                htmlInfo3.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                            }
                            htmlInfo3.append("</select>");
                            htmlInfo3.append("</div>");
                            $("#div_4").append(htmlInfo3.toString());
                            $("#div_4").css({'display':'block'})
                        }
                    });
                }
                if(value==2){
                    $("#div_1").empty(); $("#div_1").css({'display':'none'})
                    $("#div_2").empty(); $("#div_2").css({'display':'none'})
                    $("#div_3").empty(); $("#div_3").css({'display':'none'})
                    $("#div_4").empty(); $("#div_4").css({'display':'none'})
                    $("#div_5").empty(); $("#div_5").css({'display':'none'})
                    $("#div_6").empty(); $("#div_6").css({'display':'none'})
                    $.ajax({
                        type: "POST",
                        url: "/flowDirection/getStartNodeDSByAppId",
                        data:{"appId":appId},
                        error: function(request) {
                        },
                        success: function(data) {
                            startNodeDS = eval(data);
                            var htmlInfo=new StringBuffer();
                            htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_toNodeId\">目标节点</label>");
                            htmlInfo.append("<div class=\"col-sm-8\">");
                            htmlInfo.append("<select id=\"curRowBody_toNodeId\" name=\"toNodeId\" class=\"form-control\">");
                            for(var i=0;i<startNodeDS.length;i++)
                            {
                                if(i == 0)
                                {
                                    htmlInfo.append("<option value=></option>");
                                }
                                htmlInfo.append("<option value="+startNodeDS[i].parameterValue+">"+startNodeDS[i].parameterName+"</option>");
                            }
                            htmlInfo.append("</select>");
                            htmlInfo.append("</div>");
                            $("#row_nodeId").append(htmlInfo.toString());
                            $("#row_nodeId").css({'display':'block'})
                        }
                    });
                }
                if(value==3){
                	 $("#div_1").empty(); $("#div_1").css({'display':'none'})
                     $("#div_2").empty(); $("#div_2").css({'display':'none'})
                     $("#div_3").empty(); $("#div_3").css({'display':'none'})
                     $("#div_4").empty(); $("#div_4").css({'display':'none'})
                     $("#div_5").empty(); $("#div_5").css({'display':'none'})
                     $("#div_6").empty(); $("#div_6").css({'display':'none'})
                     $("#row_nodeId").empty();$("#row_nodeId").css({'display':'none'})
                    $.ajax({
                        type: "POST",
                        url: "/entity/getItem1DS",
                        data:{"appId" : appId},
                        error: function(request) {
                        },
                        success: function(data) {
                            item1DS = eval(data);
                            //
                            var htmlInfo=new StringBuffer();
                            htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_checkItemIds\">检查数据项</label>");
                            htmlInfo.append("<div class=\"col-sm-8\">");
                            htmlInfo.append("<select id=\"curRowBody_checkItemIds\" name=\"checkItemIds\" multiple class=\"selectpicker show-tick form-control\">");
                            for(var i=0;i<item1DS.length;i++)
                            {
                                htmlInfo.append("<option value="+item1DS[i].parameterValue+">"+item1DS[i].parameterName+"</option>");
                            }
                            htmlInfo.append("</select>");
                            htmlInfo.append("</div>");
                            $("#div_5").append(htmlInfo.toString());
                            $("#div_5").css({'display':'block'})
                            $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
                            //
                            var htmlInfo1=new StringBuffer();
                            htmlInfo1.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_isAllNull\">是否全部检查</label>");
                            htmlInfo1.append("<div class=\"col-sm-8\">");
                            htmlInfo1.append("<select id=\"curRowBody_isAllNull\" name=\"isAllNull\" class=\"form-control\">");
                            for(var i=0;i<isAllNullDS.length;i++)
                            {
                                if(i == 0)
                                {
                                    htmlInfo1.append("<option value=></option>");
                                }
                                htmlInfo1.append("<option value="+isAllNullDS[i].parameterValue+">"+isAllNullDS[i].parameterName+"</option>");
                            }
                            htmlInfo1.append("</select>");
                            htmlInfo1.append("</div>");
                            $("#div_6").append(htmlInfo1.toString());
                            $("#div_6").css({'display':'block'})
                        }
                    });
                }
            }
        })
    }
    function change2(){
        $("#curRowBody_appId").change(function(){
            $("#div_1").empty(); $("#div_1").css({'display':'none'})
            $("#div_2").empty(); $("#div_2").css({'display':'none'})
            $("#div_3").empty(); $("#div_3").css({'display':'none'})
            $("#div_4").empty(); $("#div_4").css({'display':'none'})
            $("#row_nodeId").empty();$("#row_nodeId").css({'display':'none'})
            $("#curRowBody_category").empty();
            var htmlInfo=new StringBuffer();
            for(var i=0;i<actionCategoryDS.length;i++)
            {
                if(i == 0)
                {
                    htmlInfo.append("<option value=></option>");
                }
                htmlInfo.append("<option value="+actionCategoryDS[i].parameterValue+">"+actionCategoryDS[i].parameterName+"</option>");
            }
            $("#curRowBody_category").append(htmlInfo.toString());
        })
    }
    //表单提交
    function init_form_action_body()
    {
        $('#body_submit_btn').on("click",function(){
            var appId = $('#curRowBody_appId').val();
            var name = $('#curRowBody_name').val();
            var category = $('#curRowBody_category').val();
            var formItemId=$('#curRowBody_fromItemId').val();
            var toItemId=$('#curRowBody_toItemId').val();
            var checkItemId=$('#curRowBody_checkItemIds').val();
            if(name != null && name!="" && appId != null && category != null){
            	if(category == 1){
            		if(formItemId != null && formItemId != "" && toItemId != null && toItemId != ""){
            			var item1=formItemId.substring(0,1);
            			var item2=toItemId.substring(0,1);
		            	if(item1 == "I" && item2 == "I"){
			                $.ajax({
			                    type: "POST",
			                    dataType: "json",
			                    url: "/action/save",
			                    data: $("#"+bodyFormId).serialize(), //formid
			                    error: function(request) {
			                    },
			                    success: function(data) {
			                        bootbox.alert(data.msg,"");
			                        $("#"+bodyFormId)[0].reset();
			                        $("#"+bodyModalId).modal('hide');
			                        $("#"+opt2TableId).bootstrapTable('refresh');
			                    }
			                });
		            	} else{
				            showError("请选择正确的数据项", '');
				         }
            		}
            	 } 
            	if(category == 2){
            		 $.ajax({
		                    type: "POST",
		                    dataType: "json",
		                    url: "/action/save",
		                    data: $("#"+bodyFormId).serialize(), //formid
		                    error: function(request) {
		                    },
		                    success: function(data) {
		                        bootbox.alert(data.msg,"");
		                        $("#"+bodyFormId)[0].reset();
		                        $("#"+bodyModalId).modal('hide');
		                        $("#"+opt2TableId).bootstrapTable('refresh');
		                    }
		                });
            	}
            	if(category == 3){
            		 var checkItemIds=(checkItemId+'').split(",");
            		 var j=0;
            		 for(var i=0;i<checkItemIds.length;i++){
            			 var item3=checkItemIds[i].substring(0,1);
            			 if(item3 == "E"){
            				 j++;
            			 	}
            			 }
            		 if(j == 0){
            			 $.ajax({
			                    type: "POST",
			                    dataType: "json",
			                    url: "/action/save",
			                    data: $("#"+bodyFormId).serialize(), //formid
			                    error: function(request) {
			                    },
			                    success: function(data) {
			                        bootbox.alert(data.msg,"");
			                        $("#"+bodyFormId)[0].reset();
			                        $("#"+bodyModalId).modal('hide');
			                        $("#"+opt2TableId).bootstrapTable('refresh');
			                    }
			                });
            		 }else{
            			 showError("请选择正确的数据项", '');
            		 }
            	 }
            }else{
                bootbox.alert("请填写不为空的选项","");
            }
        });
        $('#body_cancel_btn').on("click",function(){
            $("#"+bodyFormId)[0].reset();
        });
        $('#body_close_btn').on("click",function(){
            $("#"+bodyFormId)[0].reset();
        });
    }

    //初始化表单
    function init_modal_body1()
    {
        $("#"+bodyModalId1).remove()

        var htmlInfo = new StringBuffer();

        htmlInfo.append("<div class=\"modal fade\" id=\""+bodyModalId1+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
        htmlInfo.append("<div class=\"modal-dialog\" id=\""+bodyDialogId1+"\">");
        htmlInfo.append("<div class=\"modal-content\">");

        htmlInfo.append("<div class=\"modal-header\">");
        htmlInfo.append("<button type=\"button\" id=\"body_close_btn1\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
        htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel1\">执行措施</h4>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-body\">");
        htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId1+"\" role=\"form\">");
        htmlInfo.append("<fieldset>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_actionId\">措施id</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input readonly class=\"form-control\" name=\"actionId\" id=\"curRowBody1_actionId\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_appId\">产品编号</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input readonly class=\"form-control\" name=\"appId\" id=\"curRowBody1_appId\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_name\">名称</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input class=\"form-control\" name=\"name\" id=\"curRowBody1_name\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_category\">执行措施类别</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input readonly class=\"form-control\" name=\"category\" id=\"curRowBody1_category\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_11\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_21\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_31\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"div_41\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\" id=\"row_nodeId1\" style=\"display:none\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\" id=\"div_51\" style=\"display:none\">");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\" id=\"div_61\" style=\"display:none\">");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody1_seqno\">排列序号</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input class=\"form-control\" name=\"seqno\" id=\"curRowBody1_seqno\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");

        htmlInfo.append("</fieldset>");
        htmlInfo.append("</form>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-footer\">");
        htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"body_cancel_btn1\" data-dismiss=\"modal\">取消</button> ");
        htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"body_submit_btn1\" type=\"button\" class=\"btn btn-primary\">提交</button>");
        htmlInfo.append("</div>");

        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        $(".initmodal").append(htmlInfo.toString());
    }
    
    //表单提交
    function init_form_action_body1()
    {
        $('#body_submit_btn1').on("click",function(){
        	$("#curRowBody1_category").val(category);
            var appId = $('#curRowBody1_appId').val();
            var name = $('#curRowBody1_name').val();
            var formItemId=$('#curRowBody1_fromItemId').val();
            var toItemId=$('#curRowBody1_toItemId').val();
            var checkItemId=$('#curRowBody1_checkItemIds').val();
            if(name != null && name!="" && appId != null && category != null){
            	if(category == 1){
            		if(formItemId != null && formItemId != "" && toItemId != null && toItemId != ""){
            			var item1=formItemId.substring(0,1);
            			var item2=toItemId.substring(0,1);
		            	if(item1 == "I" && item2 == "I"){
			                $.ajax({
			                    type: "POST",
			                    dataType: "json",
			                    url: "/action/save",
			                    data: $("#"+bodyFormId1).serialize(), //formid
			                    error: function(request) {
			                    },
			                    success: function(data) {
			                    	 bootbox.alert(data.msg,"");
		                            $("#"+bodyFormId1)[0].reset();
		                            $("#"+bodyModalId1).modal('hide');
		                            $("#"+opt2TableId).bootstrapTable('refresh');
		                            curRowBody4 = "";
		                            category = "";
			                    }
			                });
		            	} else{
				            showError("请选择正确的数据项", '');
				         }
            		}
            	 } 
            	if(category == 2){
            		 $.ajax({
		                    type: "POST",
		                    dataType: "json",
		                    url: "/action/save",
		                    data: $("#"+bodyFormId1).serialize(), //formid
		                    error: function(request) {
		                    },
		                    success: function(data) {
		                    	 bootbox.alert(data.msg,"");
	                            $("#"+bodyFormId1)[0].reset();
	                            $("#"+bodyModalId1).modal('hide');
	                            $("#"+opt2TableId).bootstrapTable('refresh');
	                            curRowBody4 = "";
	                            category = "";
		                    }
		                });
            	}
            	if(category == 3){
            		 var checkItemIds=(checkItemId+'').split(",");
            		 var j=0;
            		 for(var i=0;i<checkItemIds.length;i++){
            			 var item3=checkItemIds[i].substring(0,1);
            			 if(item3 == "E"){
            				 j++;
            			 	}
            			 }
            		 if(j == 0){
            			 $.ajax({
			                    type: "POST",
			                    dataType: "json",
			                    url: "/action/save",
			                    data: $("#"+bodyFormId1).serialize(), //formid
			                    error: function(request) {
			                    },
			                    success: function(data) {
			                    	 bootbox.alert(data.msg,"");
			                        $("#"+bodyFormId1)[0].reset();
			                        $("#"+bodyModalId1).modal('hide');
			                        $("#"+opt2TableId).bootstrapTable('refresh');
			                        curRowBody4 = "";
			                        category = "";
			                    }
			                });
            		 }else{
            			 showError("请选择正确的数据项", '');
            		 }
            	 }
            }else{
                bootbox.alert("请填写不为空的选项","");
            }
        });
        $('#body_cancel_btn1').on("click",function(){
            $("#"+bodyFormId1)[0].reset();
        });
        $('#body_close_btn1').on("click",function(){
            $("#"+bodyFormId1)[0].reset();
        });
    }
});