<!DOCTYPE html>
<html>
<head>
    <title>档案管理</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/util/StringBuffer.js"></script>
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootbox/bootbox.js"></script>
	<script src="/resource/bootstrap/js/bootstrap-treeview.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
	<!-- 模态窗 -->
    <div class="initmodal">
    	<div id="ucmodalid">
    	</div>
	</div>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                	<div class="row" style="background-color: white;padding: 0px;margin-left: 0px;">
                    	<div class="panel">
                            <div id="search-panel">
                                <div class="panel-body">                                   
                                    <div class="form-inline">
                                    
                                      <div class="form-group">
									
									    <button id="btn_add" class="btn btn-primary">
									    	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
									    </button>
									
									  </div>

									  <div class="form-group">
									
									    <label class="control-label" for="org">部门:</label>
									
									    <input type="text" class="form-control" id="org">
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="operate">操作人:</label>
									
									    <input type="text" class="form-control" id="operate">
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="name">姓名:</label>
									
									    <input type="text" class="form-control" id="name">
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="phone">手机号:</label>
									
									    <input type="text" class="form-control" id="phone">
									
									  </div>
									
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        <button class="btn btn-w-m btn-success" type="button" onclick="exportExc()"> 导出全部档案
                                        </button>
                                        <button class="btn btn-w-m btn-success" type="button" onclick="exportExcPart()"> 选择导出档案
                                        </button>
                                        <button class="btn btn-w-m btn-success" type="button" onclick="importExc()"> 导入档案
                                        </button>
                                        <button class="btn btn-primary" type="button" onclick="changeOpers()"> 批量分配操作人
                                        </button>
                                        <form class="form-horizontal col-lg-10 m-t" id="fileForm">
                                        	<div style="display: none;">
                                        		<input  type="file" name="excelFile" id="excelFile" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"  onchange="uploadExc()"  >
                                        	</div>
                                        </form>
									</div>
                                </div>
                            </div>
                        </div>
                    	<table id="tableid">
         	
                    	</table>
                    
                    </div>
                </div>
            </div>
        </div>
    </div>
    
</div>


<script type="text/javascript">
$(document).ready(function(e){
	
	$('#tableid').bootstrapTable({
		method: 'post', 
		url: "/memberoperate/list",
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
			var org = $("#org").val();
			var operate = $("#operate").val();
			var name = $("#name").val();
			var phone = $("#phone").val();
    		var temp = {					//这里的键的名字和控制器的变量名必须一致
      			pageSize: params.limit,			//页面大小
      			currentPage: (params.offset / params.limit) + 1,		//页码
      			org : org,
      			operate: operate,
      			name: name,
      			phone: phone
    			};
    		return temp;
		},
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
				title: '客户编号',
				align: 'center',
				width: '50',
				valign: 'middle',
				visible:false
			},
			{
				field: 'orgId',
				title: '部门',
				align: 'center',
				width: '50',
				valign: 'middle'
			},
			{
				field: 'operUserId',
				title: '操作人',
				align: 'center',
				width: '30',
				valign: 'middle'
			},
			{
				field: 'name',
				title: '姓名',
				align: 'center',
				width: '30',
				valign: 'middle'
			},
			{
				field: 'phone',
				title: '手机号',
				align: 'center',
				width: '30',
				valign: 'middle'
			},
			{
				field: 'operateType',
				title: '经营类型',
				align: 'center',
				width: '50',
				valign: 'middle',
				formatter: operateTypeFormat
			},
			{
				field: 'cropType',
				title: '经营品种',
				align: 'center',
				width: '50',
				valign: 'middle',
				formatter: cropTypeFormat
			},
			{
				field: 'completingDegree',
				title: '资料完整度',
				align: 'center',
				width: '30',
				valign: 'middle'
			},
			{
				field: 'creDate',
				title: '创建时间',
				align: 'center',
				width: '30',
				valign: 'middle',
				formatter: changeDateFormat
			},
			{
				field: '',
				title: '操作',
				align: 'center',
				width: '100',
				valign: 'middle',
				formatter: actionFormatter
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
			
});

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

function operateTypeFormat(val){
	if(val == null | val == ''){
		return "";
	}
	var str='';
	if(val.indexOf(1) != -1){
		str = str + "大田作物、";
	}
	if(val.indexOf(2) != -1){
		str = str + "经济作物、";
	}
	if(val.indexOf(3) != -1){
		str = str + "养殖、";
	}
	if(val.indexOf(4) != -1){
		str = str + "经商/个体经营、";
	}
	if(val.indexOf(5) != -1){
		str = str + "其他、";
	}
	if(str==''){
		return str;
	}
	return str.substring(0,str.length-1);
}

function cropTypeFormat(value, row, index){
	var text = "";
	if(row.cropType != "" & row.cropType != null){
		if(row.cropType.indexOf(1) != -1){
			text = text + "水稻、";		
		}
		if(row.cropType.indexOf(2) != -1){
			text = text + "玉米、";		
		}
		if(row.cropType.indexOf(3) != -1){
			text = text + "小麦、";		
		}
		if(row.cropType.indexOf(4) != -1){
			text = text + "蔬菜、";		
		}
		if(row.cropType.indexOf(5) != -1){
			text = text + "水果、";		
		}
		if(row.cropType.indexOf(7) != -1){
			text = text + "其他、";		
		}
	}
	
	if(row.livestockType != "" & row.livestockType != null){
		if(row.livestockType.indexOf(1) != -1){
			text = text + "牛、";		
		}
		if(row.livestockType.indexOf(2) != -1){
			text = text + "猪、";		
		}
		if(row.livestockType.indexOf(3) != -1){
			text = text + "羊、";		
		}
		if(row.livestockType.indexOf(4) != -1){
			text = text + "鸡、";		
		}
		if(row.livestockType.indexOf(5) != -1){
			text = text + "鱼、";		
		}
		if(row.livestockType.indexOf(6) != -1){
			text = text + "其他、";		
		}
	}
	if(row.nongsellOtherType != "" & row.nongsellOtherType != null){
		if(row.nongsellOtherType.indexOf(1) != -1){
			text = text + "农资销售、";		
		}
		if(row.nongsellOtherType.indexOf(2) != -1){
			text = text + "批发、";		
		}
		if(row.nongsellOtherType.indexOf(3) != -1){
			text = text + "饭店等个体经营、";		
		}
		if(row.nongsellOtherType.indexOf(4) != -1){
			text = text + "上班、";		
		}
		if(row.nongsellOtherType.indexOf(5) != -1){
			text = text + "其他、";		
		}
	}
	if(text==''){
		return text;
	}
	return text.substring(0,text.length-1);
}
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

//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='detail(\""+row.id+"\")'><i class='fa fa-search'></i>详情</button>"
	+"<button class='btn btn-primary' type='button' onclick='intoPiece(\""+row.id+"\",\""+row.name+"\",\""+row.phone+"\")'><i class='fa fa-search'></i>进件</button>";
    return html;
}

$("#btn_add").click(function(){
	window.location.href= "/memberoperate/form?id=";
});

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function importExc(){
	$("#excelFile").click();
}
function uploadExc(){
	var file= $("#excelFile").val();
	if(file.length == 0){
		bootbox.alert("请选择表格");
		return false;
	}
	var formData = new FormData($("#fileForm")[0]); 
	$.ajax({ 
        url: '/memberoperate/importExc', 
        type: 'POST', 
        data: formData, 
        async: false, 
        cache: false, 
        contentType: false, 
        processData: false, 
        success: function (data) { 
            if(data.errno == 2000){ 
            	bootbox.alert(data.msg);
            	$("#excelFile").val('');
            	$('#tableid').bootstrapTable('refresh');
            }else{ 
            	
            	bootbox.alert(data.msg);
            } 
        }, 
        error: function (data) { 
        	bootbox.alert(data.msg);
        } 
    });
	return false;
}

function exportExc(){
	var org = $("#org").val();
	var operate = $("#operate").val();
	var name = $("#name").val();
	var phone = $("#phone").val();
	window.location.href= "/memberoperate/exportExc?org="+org+"&operate="+operate+"&name="+name+"&phone="+phone;
}

function exportExcPart(){
	var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
	var ids ='';
	if(selData.length > 0)
	{
		for(var i=0; i < selData.length; i++)
		{
			ids += selData[i].id+',';
		}
		
		ids = ids.substring(0,ids.length-1);
		window.location.href= "/memberoperate/exportExc?ids="+ids;
	}else{
		bootbox.alert("请选择要导出的记录！","");
	}
	
}




function changeOpers(){
	var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
	var ids ='';
	for(var i=0; i < selData.length; i++)
	{
		ids += selData[i].id+',';
	}
	ids = ids.substring(0,ids.length-1);
	if(selData.length > 0)
	{
		$(window.parent.document).find(".initmodal").empty();
		init_modal_home();
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/memberoperate/searchOrgs",
			error: function(request) {					
			},
			success: function(data) {
				
				if($(window.parent.document).find('#operdialogid').length == 0)//判断表单是否已经初始化
				{
					init_modal();		//初始化提交表单
					submit_form();		//提交表单操作
				}
				$(window.parent.document).find('#MOIds').val(ids);
				$(window.parent.document).find('#orgId').empty();
				$(window.parent.document).find('#operUserId').empty();
				$(window.parent.document).find('#operUserId').prop('disabled',true);
				if(data.errno == 2000){
					$(window.parent.document).find('#orgId').append(data.result);
				}else{
					parent.window.bootbox.alert(data.msg);
				}
				$(window.parent.document).find('#operModal').modal('show');
//				if(data.code == 200){
//					$('#tableid').bootstrapTable('refresh');
//				}
			}
		});
		/* for(var i=0; i < selData.length; i++)
		{
			ids += selData[i].id+',';
		}
		
		ids = ids.substring(0,ids.length-1);
		window.location.href= "/memberoperate/exportExc?ids="+ids; */
	}else{
		parent.window.bootbox.alert("请选择需要分配的记录！","");
	}
	
}



function init_modal_home()
{
	var htmlInfo=new StringBuffer();
	if($(window.parent.document).find('#ucmodalid').length == 0)
	{
		htmlInfo.append("<div id=\"ucmodalid\">");
		htmlInfo.append("</div>");
		$(window.parent.document).find(".initmodal").append(htmlInfo.toString());
	}
}

function detail(id){
	var url = "/memberoperate/form?id="+id;
	window.location.href= url;
}

function intoPiece(memberOperateId,memberOperateName,memberOperatePhone){
	$(".wrapper-content").empty();
	initIntoPiece(memberOperateId,memberOperateName,memberOperatePhone);
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
		htmlInfo.append("<label class=\"col-sm-3 control-label\"   for=\"orgId\">部门</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"orgId\" name=\"orgId\"  class=\"form-control\">");
		htmlInfo.append("</select>");
		htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"operUserId\">操作人员</label>");
		htmlInfo.append("<div class=\"col-sm-8\">");
		htmlInfo.append("<select id=\"operUserId\" name=\"operUserId\" class=\"form-control\">");
		htmlInfo.append("</select>");
		htmlInfo.append("<input hidden=\"hidden\" name=\"MOIds\" id=\"MOIds\" type=\"text\" />");
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
	    $(window.parent.document).find("#ucmodalid").append(htmlInfo.toString()); 
}
function submit_form()
{
	$(window.parent.document).find('#submit_btn_oper').on("click",function(){
		var operUserId = $(window.parent.document).find('#operUserId').val();
		var url = "/memberoperate/operUserIdChange";
		if(operUserId!=""){
			$.ajax({
				type: "POST",
				dataType: "json",
				url:url,
				data:$(window.parent.document).find('#addform1').serialize(), //formid
			    error: function(request) {
			        //alert("Connection error");
			    },
			    success: function(data) {
			    	if(data.errno==2000){
			    		parent.window.bootbox.alert(data.msg,"");
			    		$(window.parent.document).find('#addform1')[0].reset();
			    		$(window.parent.document).find('#operModal').modal('hide');
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
	$(window.parent.document).find('#cancel_btn_oper').on("click",function(){
		$(window.parent.document).find('#addform1')[0].reset();
	});
	$(window.parent.document).find('#orgId').on("change",function(){
		var orgId = $(window.parent.document).find("#orgId").val();
		if(orgId!=''){
			$(window.parent.document).find("#operUserId").empty();
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/memberoperate/operChange",
				data:{"orgId":orgId},
				error: function(request) {					
				},
				success: function(data) {
					
					if(data.errno == 2000){
						$(window.parent.document).find('#operUserId').append(data.result);
					}else{
						bootbox.alert(data.msg);
					}
//					if(data.code == 200){
//						$('#tableid').bootstrapTable('refresh');
//					}
				}
			});
			$(window.parent.document).find("#operUserId").prop('disabled',false);
			
		}else{
			$(window.parent.document).find("#operUserId").empty();
			$(window.parent.document).find("#operUserId").prop('disabled',true);
		}
	});
}
function initIntoPiece(memberOperateId,memberOperateName,memberOperatePhone){
	//获取数据字典
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/para/someparalist",
		data:{"someParaGroupName":"ID_CARD_TYPE,EDUCATION_LEVEL,MARITAL_STATUS,DUTY,SEX"},
	    error: function(request) {					
	    },
	    success: function(data) {
		    var id_card_type = eval(data.ID_CARD_TYPE);
		    var education_level = eval(data.EDUCATION_LEVEL);
		    var marital_status = eval(data.MARITAL_STATUS);
		    var duty = eval(data.DUTY);
		    var sex = eval(data.SEX);
			var htmlInfo = new StringBuffer();
			htmlInfo.append("<h3>进件信息</h3>");
			htmlInfo.append("<div class=\"ibox-content clearfix\">");
			htmlInfo.append("<form id=\"intopieceform\" class=\"form-horizontal col-lg-8 m-t\" onsubmit=\"return postintopiece()\">");
			
			htmlInfo.append("<input type=\"hidden\" name=\"id\" id=\"id\" >");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"bindOrg\">部门：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\" >");
			htmlInfo.append("<select id=\"orgId\" name=\"orgId\" onchange=\"searchModelByOrg()\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">产品：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"modelId\" name=\"modelId\" disabled=true class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			htmlInfo.append("</select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">申请额度：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<div class=\"input-group\">");
			htmlInfo.append("<input type=\"number\" id=\"capital\" name=\"capital\"  class=\"form-control required\"/>");
			htmlInfo.append("<div class=\"input-group-addon\">元</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">申请期限：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<div class=\"input-group\"> <input type=\"number\" id=\"term\" name=\"term\" class=\"form-control required\"/>");
			htmlInfo.append("<div class=\"input-group-addon\">月</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");		
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">借款用途备注：</label>");
			htmlInfo.append("<div class=\"col-sm-9\">");
			htmlInfo.append("<textarea id=\"useRemark\" name=\"useRemark\" class=\"form-control\" rows=\"3\"></textarea>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
		
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">客户姓名：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"memberName\" value=\""+memberOperateName+"\" type=\"text\" class=\"form-control required\" maxlength=\"50\" name=\"memberName\" />");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">身份证号：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"idCard\" onchange=\"idMetch()\" type=\"text\" class=\"form-control required idCard\" maxlength=\"18\" name=\"idCard\">");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">年龄：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"age\" type=\"number\" class=\"form-control required age\" maxlength=\"2\" name=\"age\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">性别：<span style=\"color: red\">（必填）</span></label>");
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
			htmlInfo.append("<label class=\"col-sm-3 control-label\">手机号码：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"phone\" type=\"text\" value=\""+memberOperatePhone+"\" class=\"form-control required phone\" maxlength=\"11\" name=\"phone\">");
			htmlInfo.append("</div>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">文化程度：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <select id=\"educationLevel\" name=\"educationLevel\" class=\"form-control required\">");
			htmlInfo.append("<option value=''>--请选择--</option>");
			for(var i=0;i<education_level.length;i++)
			{
				htmlInfo.append("<option value="+education_level[i].parameterValue+">"+education_level[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">婚姻状况：<span style=\"color: red\">（必选）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<select id=\"maritalStatus\" name=\"maritalStatus\" class=\"form-control required\">");
			for(var i=0;i<marital_status.length;i++)
			{
				htmlInfo.append("<option value="+marital_status[i].parameterValue+">"+marital_status[i].parameterName+"</option>");
			}
			htmlInfo.append(" </select>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">银行卡开户行：<span style=\"color: red\">（必填）</span></label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bank\" type=\"text\" class=\"form-control required\" name=\"bank\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">银行卡账号：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append(" <input id=\"bankCardNo\" type=\"text\" class=\"form-control\" name=\"bankCardNo\"> ");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append(" <input id=\"memberOperateId\"  hidden=\"hidden\" value=\""+memberOperateId+"\"  name=\"memberOperateId\"> ");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">近6个月详细住址：</label>");
			htmlInfo.append("<div class=\"col-sm-9\">");
			htmlInfo.append(" <input id=\"address\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"address\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\" for=\"duty\">从事职业</label>");
			htmlInfo.append("<div class=\"col-sm-8\">");
			for(var i =0;i<duty.length;i++){
				htmlInfo.append("<label style=\"padding-left: 0;\"><input  type=\"checkbox\" style=\"margin-left:20px;\" name=\"duty\" id=\"duty"+duty[i].parameterValue+"\" value=\""+duty[i].parameterValue+"\" />"+duty[i].parameterName+"</label>");
			}
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">非农职业 -- 单位名称：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComName\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComName\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">单位电话：</label>");
			htmlInfo.append("<div class=\"col-sm-3\">");
			htmlInfo.append("<input id=\"nonfarmComPhone\" type=\"text\" class=\"form-control\" maxlength=\"20\" name=\"nonfarmComPhone\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"form-group duty4_hide\">");
			htmlInfo.append("<label class=\"col-sm-3 control-label\">单位地址：</label>");
			htmlInfo.append("<div class=\"col-sm-9\">");
			htmlInfo.append("<input id=\"nonfarmComAddress\" type=\"text\" class=\"form-control\" maxlength=\"255\" name=\"nonfarmComAddress\" >");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("<div class=\"clearfix form-actions\" style=\"margin-top: 40px;\">");
			htmlInfo.append("<div class=\"col-md-12 text-center\">");
			htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\">");
			htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-110\"></i>提交</button>");
			htmlInfo.append("</div>");
			htmlInfo.append("</div>");
			
			htmlInfo.append("</form>");
			htmlInfo.append("</div>");
			$(".wrapper-content").append(htmlInfo.toString());
			
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
            		parent.window.bootbox.alert(ret.msg,"");
            	}
            }
		});
	}
	function isNum(value) {
        return (value !== undefined && value !== null && value !== '') ? true : false;
    }
}
function postintopiece(){
	var test = 1;
	$("form .required").each(function(){
		var value = $(this).val();
        if(value==''){
        	$(this).focus();
        	parent.window.bootbox.alert("必填项不能为空");
            test = 0;
            return false;
        }
	});
	if(test == 0){
		return false;
	}
	var url = "/intopiece/save";
	if($("#intoPieceId").val() == undefined){
		//新增
		$.ajax({
			type: "POST",
			dataType: "json",
			url:"/intopiece/save",
			data:$('#intopieceform').serialize(), //formid
		    error: function(request) {
		        //alert("Connection error");
		    },
		    success: function(data) {
		    	if(data.errono==1000){
		    		parent.window.bootbox.alert(data.msg);
		    	}else{
		    		var flag= parent.window.bootbox.alert(data.msg);
		    		
		    		$(window.parent.document).find("#memberoperate").click();
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
		    		parent.window.bootbox.alert(data.msg);
		    	}else{
		    		parent.window.bootbox.alert(data.msg,"");
		    	}
		    }
		});
	}
	return false;
}
</script>
</body>
</html>

