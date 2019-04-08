<!DOCTYPE html>
<html>
<head>
    <title>保函出具</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
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
										
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
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
    $('#tableid').bootstrapTable({
			method: 'post', 
			url: "/intopiece/list",
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
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			idCard: idCard,
	      			phone: phone,
	      			status: 4,
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
					field: 'fullCname',
					title: '部门',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'memberName',
					title: '姓名',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'phone',
					title: '手机号',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'appName',
					title: '产品',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'capital',
					title: '申请金额（元）',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'term',
					title: '申请期数（月）',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				/* {
					field: 'serviceFeeWay',
					title: '服务费支付方式',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: payWayFormatter
				},
				{
					field: 'serviceStatus',
					title: '扣款状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormatter
				}, */
				{
					field: 'creDate',
					title: '申请时间',
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

function payWayFormatter(value) {

	if(value == '')
	{
			return ['<span>-</span>'];
   	}else if (value == '1'){
   		return ['<span>银行卡支付</span>'];
   	}
   	else if (value == '2'){
   		return ['<span>微信支付</span>'];
   	}else if (value == '4'){
   		return ['<span>银行代收 </span>'];
   	}
}

function statusFormatter(value) {
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
	var html = "<button class='btn btn-primary' type='button' onclick='detail(\""+row.id+"\")'><i class='fa fa-search'></i>详情</button><br/>"+
	//"<button class='btn btn-primary' type='button' onclick='file(\""+row.id+"\")'><i class='fa fa-search'></i>文档</button><br>"+
	"<button class='btn btn-primary' type='button' onclick='examine(\""+row.id+"\")'><i class='fa fa-search'></i>审核</button><br/>"+
	"<button class='btn btn-primary' type='button' onclick='push(\""+row.id+"\")'><i class='fa fa-search'></i>提交</button>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function detail(id){
	parent.window.jsOpeanIntoPieceDetail(id,"/examine/guarantee");
}

function file(id){
	var url = "/examine/file?id="+id;
	window.location.href= url;
}

function examine(id){
	var url = "/examine/guaranteeform?intoPieceId="+id;
	window.location.href= url;
}

function push(id){
    bootbox.dialog({
					message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>合同制作中，请稍等。。。</span>",
					backdrop: false,
					closeButton: false,
					className: "ipbootboxModal",
				  });
	var url = "/examine/guaranteepush?id="+id;
	
	window.location.href= url;
}
</script>
</body>
</html>

