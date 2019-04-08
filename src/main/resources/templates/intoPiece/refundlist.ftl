<!DOCTYPE html>
<html>
<head>
    <title>退款列表</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
	<script src="/admin/js/timeOut.js"></script>
	<script src="/resource/bootbox/bootbox.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
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
									
									    <label class="control-label" for="orgName">部门:</label>
									
									    <input type="text" class="form-control" id="orgName">
									
									  </div>
									
									  <div class="form-group">
									
									    <label class="control-label" for="memberName">借款人姓名:</label>
									
									    <input type="text" class="form-control" id="memberName">
									
									  </div>
									
									 <div class="form-group">
									
									    <label class="control-label" for="idCard">身份证号:</label>
									
									    <input type="text" class="form-control" id="idCard">
									
									  </div>
									 
									  <div class="form-group">
									
									    <label class="control-label" for="phone">手机号码:</label>
									
									    <input type="text" class="form-control" id="phone">
									
									  </div> 
									  
									  <div class="form-group">
									
									    <label class="control-label" for="phone">退款状态:</label>
										<select id="status" class="form-control">
		                                 	<option value="">--请选择--</option>
		                                 	<option value="S">退款成功</option>
		                                 	<option value="I">退款处理中</option>
		                                 	<option value="F">退款失败</option>                                        
		                                 	<option value="P">弃贷待审核</option>                                        
		                                </select>
									
									  </div>
									  
									  <div class="form-group">
									  
									  	<label class="control-label" for="startDate">开始时间:</label>
									  	
									  	<div class="input-group date" id="start">
									  	
									  	<input id="startDate" type="text" class="form-control required"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									  	
									  	</div>
									  	
									  </div>
									  
									  <div class="form-group">
									  
									  	<label class="control-label" for="endDate">结束时间:</label>
									  	
									  	<div class="input-group date" id="end">
									  	
									  		<input id="endDate" type="text" class="form-control required"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									  	
									  	</div>
									  	
									  </div>
										
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        <button class="btn btn-primary" type="button" onclick="exportinfo()" style="margin-left:10px;"><i class="fa fa-search"></i> 导出
                                        </button>
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
	
	$("#start").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
	
	$("#end").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
	
    $('#tableid').bootstrapTable({
			method: 'post', 
			url: "/refund/list",
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
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var orgName = $("#orgName").val();
				var memberName = $("#memberName").val();
				var idCard = $("#idCard").val();
				var phone = $("#phone").val();
				var status = $("#status").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			idCard: idCard,
	      			phone: phone,
	      			status: status,
	      			startDate: startDate,
	      			endDate:endDate,
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
					field: 'id',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'orgName',
					title: '部门',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'use',
					title: '客户类型',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: useFormatter
				},
				{
					field: 'accountName',
					title: '借款人',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'certificateNo',
					title: '身份证号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'mobileNo',
					title: '手机号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'accountNo',
					title: '贷款金额(元)',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'totalAmount',
					title: '退款金额（元）',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'payer',
					title: '收款人',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'payerIdcard',
					title: '收款人身份证号',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'payWay',
					title: '退款方式',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: payWayFormatter
				},
				{
					field: 'status',
					title: '退款状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'createDate',
					title: '申请退款时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'updateDate',
					title: '退款处理时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '60',
					valign: 'middle',
					formatter: actionFormatter
				}
			],
			onLoadSuccess:function(data){
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

function statusFormatter(value) {

	if(value == '')
	{
			return ['<span>-</span>'];
   	}else if (value == 'S'){
   		return ['<span>退款成功</span>'];
   	}
   	else if (value == 'I'){
   		return ['<span>退款处理中</span>'];
   	}else if (value == 'F'){
   		return ['<span>退款失败</span>'];
   	}else if (value == 'P'){
   		return ['<span>弃贷待审核</span>'];
   	}
}

function payWayFormatter(value) {

	if(value == '')
	{
			return ['<span>-</span>'];
   	}else if (value == '1'){
   		return ['<span>银行卡支付</span>'];
   	}
   	else if (value == '2'){
   		return ['<span>微信支付</span>'];
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
	var html = ""; 
	if(row.status=='F'){
		html = "<button class='btn btn-primary' type='button' onclick='repay(\""+row.id+"\")'><i class='fa fa-search'></i>重新退款</button>";
	}
	if(row.status=='P'){
		html = "<button class='btn btn-primary' type='button' onclick='approve(\""+row.id+"\")'><i class='fa fa-search'></i>审核</button>";
	}
    return html;
}

function useFormatter(value, row, index) {
	if(value =='1'){
		return "新增";
	}else if(value =='2'){
		return "转贷";
	}else{
		return "-";
	}
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function approve(id){
	parent.window.bootbox.confirm({  
		buttons: {  
        confirm: {
			label: '同意'
        },  
        cancel: {  
        	label: '不同意'
        }  
        },  
        message: '是否确定同意放弃贷款，放弃贷款将无法继续申请贷款并退还已交服务费！',  
        callback: function(result) {
        	
        	if(result){
        		parent.window.bootbox.dialog({
            		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>申请退款中</span>",
            		closeButton: false,
            		className: "bootboxModal",
            	});
        		$.ajax({
			        url: "/refund/approveAbandon", // 请求的URL
			        dataType: 'json',
			        type: "post",
			        data:{"id":id,"option":1},
			        success: function (data) {
			        	parent.removeBootBox("bootboxModal");
			        	if(data.errono==2000){
			        		bootbox.alert(data.msg);
			        		$('#tableid').bootstrapTable('refresh');
			        	}else{
			        		parent.removeBootBox("bootboxModal");
			        		parent.showError(data.msg, '');
			        	}
			        }
			    });
        	}else{
        		$.ajax({
			        url: "/refund/approveAbandon", // 请求的URL
			        dataType: 'json',
			        type: "post",
			        data:{"id":id,"option":2},
			        success: function (data) {
			        	if(data.errono==2000){
			        		bootbox.alert(data.msg);
			        		$('#tableid').bootstrapTable('refresh');
			        	}else{
			        		parent.showError(data.msg, '');
			        	}
			        }
			    });
        	}
        }
	});
}


function repay(id){
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>申请退款中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	$.ajax({
  		type: "POST",
  		url: "/refund/refundAgain",
  		data:{"id":id},
  	    error: function(request) {			    	
  	    },
  	    success: function(data) {	
  	    	parent.removeBootBox("bootboxModal");
        	if(data.errono==2000){
        		bootbox.alert(data.msg);
        		$('#tableid').bootstrapTable('refresh');
        	}else{
        		parent.removeBootBox("bootboxModal");
        		parent.showError(data.msg, '');
        	}
  	    }
 	});
}


function exportinfo(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var orgName = $("#orgName").val();
	var memberName = $("#memberName").val();
	var idCard = $("#idCard").val();
	var phone = $("#phone").val();
	var status = $("#status").val();
	var para = "?startDate="+startDate+"&endDate="+endDate+"&orgName="+orgName+"&memberName="+memberName
	+"&idCard="+idCard+"&phone="+phone+"&status="+status;
	window.location.href= "/refund/refundexport"+para;
}
</script>
</body>
</html>

