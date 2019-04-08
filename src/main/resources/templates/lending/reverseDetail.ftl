<!DOCTYPE html>
<html>
<head>
    <title>反担保金详情</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootbox/bootbox.js"></script>
	<script src="/admin/js/timeOut.js"></script>
	<script src="/resource/util/StringBuffer.js"></script>
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
                    </div>
                </div>
                <div class="initmodal1">
                	<div id="paywaymodalid">
                		<div class="modal fade" id="paywayModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                		<div class="modal-dialog" id="paywaydialogid">
		                		<div class="modal-content">
			                		<div class="modal-body">
								 	</div>
							 	</div>
						 	</div>
					 	</div>
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
			url: "/guaranteeReverse/reverseDetailList",
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
					field: 'payer',
					title: '支付人',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'type',
					title: '角色',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: roleFormat
				},
				{
					field: 'payWay',
					title: '支付方式',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: wayFormat
				},
				{
					field: 'totalAmount',
					title: '支付金额',
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
					field: 'createDate',
					title: '申请支付时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'updateDate',
					title: '支付时间',
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
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

function statusFormat(val){
	if(val == ""){
		return "-";
	}else if(val == "S"){
		return "支付成功";
	}else if(val == "I"){
		return "支付处理中";
	}else if(val == "F"){
		return "支付失败";
	}else{
		return "-";
	}	
}
function roleFormat(val){
	if(val == ""){
		return "-";
	}else if(val == 1){
		return "客户";
	}else if(val == 2){
		return "站长";
	}
}

function wayFormat(val){
	if(val == ""){
		return "-";
	}else if(val == 1){
		return "银行卡支付";
	}else if(val == 2){
		return "微信支付";
	}else if(val == 3){
		return "线下支付";
	}else if(val == 4){
		return "暂不支付";
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
	var html = "";
	if(row.use == 2 && row.status != 'S' ){
		html = "<button class='btn btn-primary' type='button' onclick='chooseWay(\""+row.id+"\")'><i class='fa fa-search'></i>请选择支付方式</button>";
	}else if(row.status == 'F'){
		html = "<button class='btn btn-primary' type='button' onclick='rePay(\""+row.id+"\")'><i class='fa fa-search'></i>重新发起支付</button>";
	}
    return html;
}


function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function chooseWay(id){
	$(".modal-body").empty();
	var htmlInfo = new StringBuffer();
	htmlInfo.append("<form id=\"changeWayForm\" onsubmit=\"return payWayChange()\">");					
	htmlInfo.append("<div class=\"ibox-content clearfix\">");
		htmlInfo.append("<input type='hidden' name='id' value='"+id+"'>");
			htmlInfo.append("<div class=\"form-group\">");
			htmlInfo.append("<label class=\"col-sm-4 control-label\">担保金支付方式：</label>");
			htmlInfo.append("<div class=\"col-sm-6\">");
			/* <option value=\"1\">银行卡支付</option> */
				htmlInfo.append("<select id=\"payWay\" name=\"payWay\" class=\"form-control\" ><option value=\"\">--请选择--</option><option value=\"3\">线下支付</option>"+                                        
		             	"<option value=\"4\">暂不支付</option></select>" );  
			htmlInfo.append("</div>");
		htmlInfo.append("</div>");
		htmlInfo.append("<div class=\"form-group\">");
		htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	htmlInfo.append("<div class=\"clearfix form-actions\">");
	htmlInfo.append("<div class=\"text-center\">");
	htmlInfo.append("<button class=\"btn btn-w-m\" type=\"button\" onclick=\"hideModel()\">");
	htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-80\"></i>取消</button>");
	htmlInfo.append("<button class=\"btn btn-w-m btn-success\" type=\"submit\" id='submitWay_btn'>");
	htmlInfo.append("<i class=\"ace-icon fa fa-check bigger-80\"></i>提交</button>");
	htmlInfo.append("</div>");
	htmlInfo.append("</div>");
	htmlInfo.append("</form>");
	$(".modal-body").append(htmlInfo.toString());
	$('#paywayModal').modal('show');
}

function payWayChange(){
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/guaranteeReverse/payWayChange",
			data:$('#changeWayForm').serialize(),
		    error: function(request) {	
		    	bootbox.alert(data.msg);
		    	return false;
		    },
		    success: function(data) {	
		    	if(data.code == 200){
		    		$('#tableid').bootstrapTable('refresh');
		    		bootbox.alert(data.msg);
		    		$('#paywayModal').modal('hide');
		    		return false;
		    	}else{
		    		bootbox.alert(data.msg);
		    		$('#paywayModal').modal('hide');
		    		return false;
		    	}
		    }
		});  
	return false;
}

function rePay(id){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/guaranteeReverse/repay",
		data:{"id":id},
	    error: function(request) {	
	    	bootbox.alert(data.msg);
	    },
	    success: function(data) {	
	    	if(data.code == 200){
	    		$('#tableid').bootstrapTable('refresh');
	    		bootbox.alert(data.msg);
	    	}else{
	    		bootbox.alert(data.msg);
	    	}
	    }
	});  
}

function hideModel(){
	$('#paywayModal').modal('hide');
}



</script>
</body>
</html>

