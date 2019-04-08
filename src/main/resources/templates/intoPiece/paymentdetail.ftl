<!DOCTYPE html>
<html>
<head>
    <title>扣费详情</title>
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
			url: "/guaranteeFee/guaranteefeelist",
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
	    		var temp = {	
	    			pageSize: params.limit,			//页面大小
		      		currentPage: (params.offset / params.limit) + 1,		//页码//这里的键的名字和控制器的变量名必须一致
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
					field: 'merchantNo',
					title: '订单号',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'totalAmount',
					title: '服务费金额',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'createDate',
					title: '发起扣款日期',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'realSettleTime',
					title: '实际结算日期',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormat
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

function statusFormat(value){
	if(value == '')
	{
			return ['<span>-</span>'];
   	}else if (value == 'S'){
   		return ['<span>支付成功</span>'];
   	}
   	else if (value == 'I'){
   		return ['<span>支付处理中</span>'];
   	}else if (value == 'F'){
   		return ['<span>支付失败</span>'];
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


function sumFormat(value, row, index){
	var capital = row.capital;
	var intrest = row.intrest;
	var sum = capital + intrest;
	return sum.toFixed(2);
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}



</script>
</body>
</html>

