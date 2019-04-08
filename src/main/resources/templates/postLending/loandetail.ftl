<!DOCTYPE html>
<html>
<head>
    <title>还款详情</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootbox/bootbox.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                	<div class="row" style="background-color: white;padding: 0px;margin-left: 0px;">   
                		<div>
                		<table id="tableid">
         					
                    	</table>
                		</div>              	
                    	
                    	<div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                            <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                            </button>                          
                            </div>
                        </div>	
                        <input type="hidden" id="id" name="id" value="${id}"/> 
                        <input type="hidden" id="channel" name="channel" value="${channel!''}"/> 
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
			url: "/postlending/loandetaillist",
			dataType: "json",
			cache: false,
			height: $(window).height()-100,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: function (params) {
				var id = $("#id").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			id :id
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
					field: 'sort',
					title: '期数',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: '',
					title: '应还本息',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: sumFormat
				},
				{
					field: 'capital',
					title: '应还本金',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'intrest',
					title: '应还利息',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'serviceFee',
					title: '应还服务费',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveCapital',
					title: '实收本金',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveInterest',
					title: '实收利息',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'serviceFee',
					title: '实收服务费',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveOverdue',
					title: '实收逾期利息',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormat
				},
				{
					field: 'deadlineDate',
					title: '还款日期',
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

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return date.getFullYear() + "-" + month + "-" + currentDate;
    }
}

function statusFormat(val){
	if(val == ""){
		return "-";
	}else if(val == 1){
		return "待还款";
	}else if(val == 2){
		return "还款中";
	}else if(val == 3){
		return "还款失败";
	}else if(val == 4){
		return "已还清";
	}else{
		return "";
	}	
}
function responseHandler(res)
{
	if(res.length > 0)
	{
		var result = res;
		var totalcount = res.length;
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
	var channel = $("#channel").val();
	if(channel == 'SX'){
		return '';
	}
	var html = "";
	if(row.recentWaitRep == 1){
		html = "<button class='btn btn-primary' type='button' onclick='boxModel(\""+row.id+"\",\"1\")'><i class='fa fa-search'></i>线下还款</button>"
		+"<button class='btn btn-primary' type='button' onclick='boxModel(\""+row.id+"\",\"2\")'><i class='fa fa-search'></i>线上还款</button>";
	}
    return html;
}

function sumFormat(value, row, index){
	var capital = row.capital;
	var intrest = row.intrest;
	var sum = capital + intrest;
	return sum.toFixed(2);
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function boxModel(id,type){
	$.ajax({
		type: "POST",
		url: "/postlending/loandetailbyid",
		data:{"id":id},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		var model = data.model;
	    		var html = "<table class='table table-bordered m-t'><tbody>"
	    		+"<tr><td>本金</td><td>"+model.capital+"</td></tr>"
	    		+"<tr><td>利息</td><td>"+model.intrest+"</td></tr>"
	    		+"<tr><td>服务费</td><td>"+model.serviceFee+"</td></tr>"
	    		+"</tbody></table>";
	    		parent.window.bootbox.dialog({
        			message: html,
        			//backdrop: false,
        			className: "loandetailModal",
        			buttons: {	        			     
        			    // 其中一个按钮配置
        			    success: {   
        			      // 按钮显示的名称
        			      label: "确定",	        			       
        			      // 按钮的类名
        			      className: "btn-success",	        			       
        			      // 点击按钮时的回调函数
        			      callback: function() {
        			    	  $(".loandetailModal").remove();
        			    	  if(type == "1"){
        			    		  underLine(id);
        			    	  }else if(type == "2"){
        			    		  onLine(id);
        			    	  }
        			      }
        			    },
        			    cancel: {
        		            label : "取消",
        		            className : "btn-danger"
        		            }
        			}
        		});
	    	}	    	
	    }
	});
}

function underLine(id){
	$.ajax({
		type: "POST",
		url: "/postlending/underlinerepay",
		data:{"id":id},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code == 200){
	    		$('#tableid').bootstrapTable('refresh');
	    	}	    	
	    }
	});
}
function onLine(id){
	$.ajax({
		type: "POST",
		url: "/postlending/onLineRepay",
		data:{"id":id},
	    error: function(request) {	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	
	    	$('#tableid').bootstrapTable('refresh');
	    }
	});
}

</script>
</body>
</html>

