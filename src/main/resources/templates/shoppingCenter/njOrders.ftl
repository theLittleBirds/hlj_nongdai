<!DOCTYPE html>
<html>
<head>
    <title>订单列表</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/layer/layer.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                	<div class="row" style="background-color: white;padding: 0px; margin-left: 0px;">
                    	<div class="panel">
                            <div id="search-panel">
                                <div class="panel-body">                                   
                                    <div class="form-inline">
									    <div class="form-group">
									    	<label class="control-label" for="status">订单状态:</label>
									    	<select id="status" class="form-control">
			                                 	<option value="">--请选择--</option>
			                                 	<option value="1">待确认</option>
			                                 	<option value="2">已确认，待收货</option>
			                                 	<option value="3">已收货，订单已完成</option>
			                                </select>
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
			url: "/njSubmitOrder/njOrderList",
			dataType: "json",
			cache: false,
			height: $(window).height()-164,
			pagination: true,//在表格底部显示分页工具栏 
			"queryParamsType": "limit",//参数格式,发送标准的RESTFul类型的参数请求 
			pageList: [15,30,45,60,75,90],
			pageSize:15,
			pageNumber:1,
			//search: true,
			sidePagination:'server',//设置为服务器端分页
			queryParams: function (params) {
				var status = $("#status").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
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
				// {
				// 	field: '',
				// 	titleTooltip: '全选/全不选',
				// 	checkbox: true,
				// 	width: '30',
				// 	align: 'center',
				// 	valign: 'middle'
				// },
				// {
				// 	field: 'orgName',
				// 	title: '组织机构名称',
				// 	align: 'center',
				// 	width: '40',
				// 	valign: 'middle'
				// },
                {
                    field: 'orgName',
                    title: '购买机构',
                    align: 'center',
                    width: '85',
                    valign: 'middle'
                },
				{
					field: 'memberName',
					title: '购买人',
					align: 'center',
					width: '85',
					valign: 'middle'
				},
				{
					field: 'totalPrice',
					title: '购买总金额',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
                    field: 'createDate',
                    title: '下单日期',
                    align: 'center',
                    width: '100',
                    valign: 'middle',
	 				formatter: dateFormatter
                },
				{
					field: 'status',
					title: '订单状态',
					align: 'center',
					width: '10',
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

function responseHandler(res) {
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

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

//table里面的操作列显示actionFormatter
function actionFormatter(value, row) {
    var html;
	// var html = "<button class='btn btn-primary' type='button' onclick='orderInfo(\""+row.orderId+"\",this)'><i class='fa fa-search'></i>详情</button>";
		html = "<button class='btn btn-primary' type='button' onclick='receiveGoods(\""+row.orderId+"\",this)'><i class='fa fa-search'></i>收货详情</button>"
	return html;
}
//table里面的操作列显示
function statusFormatter(value) {
	var html = null;
	if(value == 1){
		html = "<span style='color: red'>待确认</span>";
	}else if(value == 2){
		html = "<span style='color: red'>已确认，待收货</span>";
	}else if(value == 3){
        html = "<span style='color: #85c360'>已收货，订单已完成</span>";
    }
    return html;
}
//日期转换
function dateFormatter(value) {
    var time = new Date(value);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}
 function add0(m){
    return m<10?'0'+m:m
}
//订单详情
function orderInfo(orderId){
    window.location.href = "/njSubmitOrder/njOrderInfoList?orderId="+orderId;
}
//收货
function receiveGoods(orderId) {
	window.location.href = "/njSubmitOrder/njOrderReceive?orderId="+orderId;
    // $.ajax({
    //     type: "POST",
    //     url: "/njSubmitOrder/njOrderReceive",
    //     data: {"orderId":orderId},
    //     error: function(request) {
    //     },
    //     success: function(data) {
    //         if(data.msg == "100"){
    //             parent.window.bootbox.alert("卖家暂没发货，请稍后从试！");
    //         }else {
    //             window.location.href = "/njSubmitOrder/njOrderReceive?orderId="+orderId;
    //         }
    //     }
    // });
}
</script>
</body>
</html>

