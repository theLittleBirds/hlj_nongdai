<!DOCTYPE html>
<html>
<head>
    <title>待支付</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/layer/layer.min.js"></script>
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
									      <label class="control-label" for="productName">商品名称:</label>
									      <input type="text" class="form-control" id="productName">
									    </div>									
										
										<div class="form-group">
									      <label class="control-label" for="status">订单状态:</label>
									      <select id="status" class="form-control">
			                                 <option value="">--请选择--</option>
			                                 <option value="1">待接单</option>
			                                 <option value="2">待发货</option>
			                                 <option value="3">已发货</option>
			                                 <option value="4">已完成</option>
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
			url: "/njMerchant/asyncLoanRiskPostList",
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
				var productName = $("#productName").val();
				var status = $("#status").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			productName: productName,
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
					field: '',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '10',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'merName',
					title: '商家名称',
					align: 'center',
					width: '50',
					valign: 'middle'
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
					field: 'productName',
					title: '商品名称',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'productNum',
					title: '数量(规格)',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'settleTotalprice',
					title: '订单金额(元)',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'downTotalprice',
					title: '已收定金(元)',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '订单状态',
					align: 'center',
					width: '20',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'downPricetime',
					title: '定金支付时间',
					align: 'center',
					width: '20',
					valign: 'middle'
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '80',
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
function statusFormatter(value, row, index) {
	var html = "";
	if(value == 1){
		html = "待接单";
	}else if(value == 2){
		html = "待发货";
	}else if(value == 3){
		html = "已发货";
	}else if(value == 4){
		html = "已完成";
	}
    return html;
}

//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "暂无操作";
	if(row.status == 1 && row.isDeleted == 0){
		html = "<button class='btn btn-primary' type='button' onclick='firstPay(\""+row.intoPieceId+"\", \""+row.id+"\")'><i class='fa fa-search'></i>接单</button>";
	}else if(row.status == 2 && row.isDeleted == 0){
		html = "<button class='btn btn-primary' type='button' onclick='lastPay(\""+row.intoPieceId+"\",\""+row.id+"\")'><i class='fa fa-search'></i>发货</button>";
	}else{
		html = "<button class='btn btn-primary' type='button' onclick='picture(\""+row.intoPieceId+"\",\""+row.id+"\")'><i class='fa fa-search'></i>图片信息</button>";
	}
    return html;
}

function dateFormatter(value){
    var date =  new Date(value);
    return ['<span>' + date.toLocaleString() +'</span>']
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function firstPay(intoPieceId,id){
	var url = "/picture/merShowList?intoPieceId="+intoPieceId+"&type=jd"+"&id="+id;
	window.location.href= url;
}

function lastPay(intoPieceId,id){
	var url = "/picture/merShowList?intoPieceId="+intoPieceId+"&type=fh"+"&id="+id;
	window.location.href= url;	  	
}

function picture(intoPieceId,id){
	var url = "/picture/merShowList?intoPieceId="+intoPieceId+"&id="+id;
	window.location.href= url;
}

</script>
</body>
</html>

