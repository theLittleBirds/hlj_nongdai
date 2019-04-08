<!DOCTYPE html>
<html>
<head>
    <title>担保金</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script src="/admin/js/timeOut.js"></script>
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
			url: "/stationbond/list",
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
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			idCard: idCard
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
					field: 'orgId',
					title: '部门',
					align: 'center',
					width: '50',
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
					width: '50',
					valign: 'middle'
				},
				{
					field: 'capital',
					title: '借款金额（元）',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'recieveNongZi',
					title: '农资金额（元）',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'bond',
					title: '应收保证金（元）',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'payer',
					title: '支付人',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'payWay',
					title: '支付方式',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: payFormatter
				},
				{
					field: 'status',
					title: '状态',
					align: 'center',
					width: '50',
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
	if(row.status == "S"){
		return "<button class='btn btn-primary' type='button' onclick='picture(\""+row.intoPieceId+"\")'><i class='fa fa-search'></i>图片信息</button>";
	}else if(row.status == "I"){
		return "";
	}
	var html = "<button class='btn btn-primary' type='button' onclick='stationmasterPay(\""+row.id+"\")'><i class='fa fa-search'></i>站长代扣</button>"
	+"<button class='btn btn-primary' type='button' onclick='wxPay(\""+row.id+"\")'><i class='fa fa-search'></i>微信支付</button>"
	+"<button class='btn btn-primary' type='button' onclick='underLinePay(\""+row.id+"\")'><i class='fa fa-search'></i>线下支付</button>";
    return html;
}

function payFormatter(value, row, index) {
	if(value == "1"){
		return "站长代付";
	}else if(value == "2"){
		return "微信支付";
	}else if(value == "3"){
		return "线下支付";
	}else{
		return "-";
	}
}

function statusFormatter(value, row, index) {
	if(value == "S"){
		return "支付成功";
	}else if(value == "F"){
		return "支付失败";
	}else if(value == "I"){
		return "支付中";
	}else{
		return "未支付";
	}
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}


function picture(intoPieceId){
	var url = "/picture/intopiecepicture?intoPieceId="+intoPieceId;
	window.location.href= url;
}

function stationmasterPay(id){
	parent.window.bootbox.alert("暂未开通");
}
function wxPay(id){
	parent.window.bootbox.alert("暂未开通");
}
function underLinePay(id){
	var url = "/stationbond/underlinepay?id="+id;
	window.location.href= url;
}
</script>
</body>
</html>

