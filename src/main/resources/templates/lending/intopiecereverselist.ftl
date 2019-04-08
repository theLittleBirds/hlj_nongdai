<!DOCTYPE html>
<html>
<head>
    <title>反担保金列表</title>
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
									
									    <label class="control-label" for="memberName">客户姓名:</label>
									
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
									
									    <label class="control-label" for="phone">状态:</label>
										<select id="status" class="form-control">
		                                 	<option value="">--请选择--</option>
		                                 	<option value="1">已收取</option>
		                                 	<option value="2">退款中</option>
		                                 	<option value="3">已退款</option>                                        
		                                 	<option value="4">退款失败</option>                                        
		                                </select>
									
									  </div>
									  
									  
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        <!-- <button class="btn btn-primary" type="button" onclick="exportinfo()" style="margin-left:10px;"><i class="fa fa-search"></i> 导出
                                        </button> -->
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
			url: "/intopiecereverse/list",
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
					field: 'memberName',
					title: '客户姓名',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '20',
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
					field: 'capital',
					title: '借款金额(元)',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'farmerReverse',
					title: '农户反担保金(元)',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'stationReverse',
					title: '站长反担保金(元)',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '20',
					valign: 'middle',
					formatter: statusFormatter
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
   	}else if (value == '1'){
   		return ['<span>已收取</span>'];
   	}
   	else if (value == '2'){
   		return ['<span>退款中</span>'];
   	}else if (value == '3'){
   		return ['<span>已退款</span>'];
   	}else if (value == '4'){
   		return ['<span>退款失败</span>'];
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
	if(row.status=='1'||row.status=='4'){
		html = "<button class='btn btn-primary' type='button' onclick='refund(\""+row.id+"\")'><i class='fa fa-search'></i>退款</button>";
	}
    return html;
}

function refund(id) {
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/intopiecereverse/refund",
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

</script>
</body>
</html>

