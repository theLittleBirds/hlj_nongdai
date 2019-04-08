$(document).ready(function(){

    /*-------------------------------------定义全局变量/全局数据集对象-----------------------------------------*/

    var baseDivId = "base_div";
    
    var actionInitBody=false;
    
    var finsDS;
    
    var category;
    var curRowBody;
    var finsId;


    var totalSyncDS4Body = 0;			//同步主数据集总个数，全部数据集获取成功后展示数据，每个数据聚集异步获取
    var countSyncDS4Body = 0;			//同步主数据集计数器，当前成功获取个数


    //应用管理
    var bodyDivId = "reportBuilder_div";
    var bodyTableId = "reportBuilder_table";
    var bodyModalId = "reportBuilder_modal";
    var bodyDialogId = "reportBuilder_dialog";
    var bodyFormId = "reportBuilder_form";

    /*-------------------------------------主数据视图展现前获取同步数据集-----------------------------------------*/
    function loadSyncDS4Body()
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
                    show_view_body();
                }
            });
        }
    }

    /*-------------------------------------主程序：开始  -----------------------------------------*/

    //页面入口点
    $("#reportBuilder_id").on("click", function(){

        init_global();

        init_layout();								//初始化页面布局

//        init_tool_action_body();					//初始化工具栏

        init_table_body();							//初始化table

        loadSyncDS4Body();							//加载的数据

        show_view_body(); 							//展示table

    });

    function init_global()
    {
        $("#content-main").empty();
        $(".initmodal").empty();
    }

    //展示主数据的table
    function show_view_body(){

        if(countSyncDS4Body < totalSyncDS4Body){
            return;  //如果数据没加载完，则不展示页面
        }
        $("#"+bodyTableId).bootstrapTable({
            method:'post',
            url: "/reportEntry/selectAll",
            dataType: "json",
            striped:true,
            height: $(window).height()-210,
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
                },
                {
                    field:'paixu',
                    title:'排序'
                },
                {
                    field:'operator',
                    title:'操作',
                    events:operatorEvents,
                    formatter:operatorFormatter
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
            	curRowBody = "";
            	curRowBody = row;
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

    function operatorFormatter(value, row, index) {
    	if(row!=null && row.rptId!=undefined){
    		return '<a class="mod" >查看详情</a><span>|</span><a class="export" >导出</a>';
    	}
    }
    //表格  - 操作 - 事件-查看
    window.operatorEvents = {
	        'click .mod': function(e, value, row, index) {      
	        		look(e,row);
	            },
            'click .export': function(e, value, row, index) {   
            	
            	init_modal_body();//初始化提交表单
        		init_form_action_body();//提交表单操作
        		$("#"+bodyFormId)[0].reset();
        		$("#"+bodyModalId).modal('show');
        		$('#curRowBody_name').val(row.name);
           
            }
        }
    function look(e,entry) {
		//对字符串编码
//		var args =entry.rptId;
//		args = encodeURIComponent(args);  
		if(e.currentTarget.text == "查看详情")
		{
			window.open("/reportEntry/look1?rptId=" + entry.rptId, '', 'height=1000, width=1500, top=0, left=0,  toolbar=yes, menubar=yes, scrollbars=yes,resizable=yes,location=no,status=yes');
		}
	}
    function finsDSFormatter(value) {
        for(var i=0;i<finsDS.length;i++)
        {
            if(finsDS[i].parameterValue == value)
            {
                return ['<span>'+finsDS[i].parameterName+'</span>']
            }
        }
    }
    //初始化table外层的div
    function init_layout()
    {

        if($("#"+baseDivId).length == 0)
        {
            var htmlInfo = new StringBuffer();
            //整个页面div
            htmlInfo.append("<div class=\"row\" id=\""+baseDivId+"\">");

            htmlInfo.append("<div class=\"panel panel-default col-sm-12\" style=\"\">");

//            htmlInfo.append("<div class=\"panel-heading\" style=\"height: 42.5px;\">");
//            htmlInfo.append("<h3 class=\"panel-title\"></h3>");
//            htmlInfo.append("</div>");
            //添加工具栏div
            htmlInfo.append("<div id=\"toolbar\" class=\"btn-group\">");
            htmlInfo.append("</div>");
            //展示表格数据div
            htmlInfo.append("<div id=\""+bodyDivId+"\">");
            htmlInfo.append("</div>");

            htmlInfo.append("</div>");		//panel

            htmlInfo.append("</div>");

            $("#content-main").append(htmlInfo.toString());
        }
    }
    //主数据工具栏操作初始化
    function init_tool_action_body()
    {
        if(!actionInitBody)
        {
            $("#toolbar").empty();

            var htmlInfo = new StringBuffer();

            htmlInfo.append("<button id=\"btn_build\" class=\"btn btn-primary\">");
            htmlInfo.append("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>报表生成");
            htmlInfo.append("</button>");

            $("#toolbar").append(htmlInfo.toString());

            $('#btn_build').on("click",function()
            {
            	if(curRowBody!=null && curRowBody.rptId!=undefined){
            		
            		$.ajax({
        	            type: "POST",
        	            dataType: "json",
        	            url: "/reportEntry/exportModel",
        	            data: {"rptId":curRowBody.rptId}, 
        	            error: function(request) {
        	            	bootbox.alert(request.msg,"");
        	            },
        	            success: function(data) {
        	            	 var filedir = data.msg;
					    	if(filedir != "")
					    	{
					    		$.download('/reportEntry/downloadExcel', 'post', filedir); // 下载文件
					    	}
        	            }
        			})
            	}else{
            		 bootbox.alert("请选择报表进行导出！");
            	}
            });
        }
    }
  //文件下载
	jQuery.download = function(url, method, name){
	    jQuery('<form action="'+url+'" method="'+(method||'post')+'">' +  // action请求路径及推送方法
	                '<input type="text" name="fileName" value="'+name+'"/>' + // 文件名字
	            '</form>')
	    .appendTo('body').submit().remove();
	};
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
        htmlInfo.append("<h4 class=\"modal-title\" id=\"myModalLabel\">报表导出</h4>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-body\">");
        htmlInfo.append("<form class=\"form-horizontal\" id=\""+bodyFormId+"\" role=\"form\">");
        htmlInfo.append("<fieldset>");

        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"curRowBody_name\">报表名称</label>");
        htmlInfo.append("<div class=\"col-sm-6\" style = \"width:47.333333%\">");
        htmlInfo.append("<input readonly class=\"form-control\" name=\"curRowBody_name\" id=\"curRowBody_name\" type=\"text\" placeholder=\"\"/>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"startDate\">起始日期</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input type=\"text\" readonly placeholder=\"可以不选\" class=\"form-control layer-date laydate-icon\"  id=\"startDate\" name=\"startDate\" onclick=\"laydate({format:'YYYY-MM-DD'})\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        
        htmlInfo.append("<div class=\"form-group\">");
        htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"expiredDate\">截止日期</label>");
        htmlInfo.append("<div class=\"col-sm-8\">");
        htmlInfo.append("<input type=\"text\" readonly placeholder=\"可以不选\" class=\"form-control layer-date laydate-icon\"  id=\"expiredDate\" name=\"expiredDate\" onclick=\"laydate({format:'YYYY-MM-DD'})\">");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
		
        htmlInfo.append("</fieldset>");
        htmlInfo.append("</form>");
        htmlInfo.append("</div>");

        htmlInfo.append("<div class=\"modal-footer\">");
        htmlInfo.append("<button type=\"button\" class=\"btn btn-default\" id=\"body_cancel_btn\" data-dismiss=\"modal\">取消</button> ");
        htmlInfo.append("<button style=\"margin-right: 40%;\" id=\"body_submit_btn\" type=\"button\" class=\"btn btn-primary\">导出</button>");
        htmlInfo.append("</div>");

        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        htmlInfo.append("</div>");
        $(".initmodal").append(htmlInfo.toString());
    }


    //表单提交
    function init_form_action_body()
    {
        $('#body_submit_btn').on("click",function(){
        	var startDate = $('#startDate').val();
        	var expiredDate = $('#expiredDate').val();
        	var d1 = new Date(startDate.replace(/\-/g, "\/"));
        	var d2 = new Date(expiredDate.replace(/\-/g, "\/"));
        	if(startDate != "" && expiredDate != "" && d1 >= d2){
        		bootbox.alert("开始时间不能大于结束时间,请重新选择！");
        	}else{
        		$.ajax({
    	            type: "POST",
    	            dataType: "json",
    	            url: "/reportEntry/exportModel",
    	            data: {"rptId":curRowBody.rptId,"startDate":startDate,"expiredDate":expiredDate}, 
    	            error: function(request) {
    	            	bootbox.alert(request.msg,"");
    	            },
    	            success: function(data) {
    	            	$("#"+bodyFormId)[0].reset();
                        $("#"+bodyModalId).modal('hide');
    	            	var filedir = data.msg;
    			    	if(filedir != "")
    			    	{
    			    		$.download('/reportEntry/downloadExcel', 'post', filedir); // 下载文件
    			    	}
    	            }
    			})
        	}
        });
        $('#body_cancel_btn').on("click",function(){
            $("#"+bodyFormId)[0].reset();
        });
        $('#body_close_btn').on("click",function(){
            $("#"+bodyFormId)[0].reset();
        });
    }
});