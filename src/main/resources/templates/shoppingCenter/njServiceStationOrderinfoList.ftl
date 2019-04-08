<!DOCTYPE html>
<html>
<head>
    <title>子订单列表</title>
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


                        <input type="hidden" id="orderId" name="orderId" value="${orderId}">
						<input type="hidden" id="orgId" name="orgId" value="${orgId}">
                    	<table id="tableid">
         	
                    	</table>
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                                <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                    <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                                </button>
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
			url: "/njServiceOrder/njOrderInfoList",
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
				var orgId = $("#orgId").val();
                var orderId = $("#orderId").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
                    orgId: orgId,
                    orderId: orderId,
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
					field: 'merName',
					title: '商户名字',
					align: 'center',
					width: '40',
					valign: 'middle'
				},
				{
					field: 'productName',
					title: '商品名称',
					align: 'center',
					width: '85',
					valign: 'middle'
				},
				{
					field: 'productBrandName',
					title: '商品品牌名称',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'productCategoryName',
					title: '商品分类名称',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'productNum',
					title: '商品数量',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'productPrice',
					title: '商品单价',
					align: 'center',
					width: '60',
					valign: 'middle'
				}
				// {
				// 	field: 'productSettlePrice',
				// 	title: '商品结算单价',
				// 	align: 'center',
				// 	width: '60',
				// 	valign: 'middle'
				// }
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

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

//编辑 
function productEdit(id){
	window.location.href = "/njServiceOrder/njOrderDetails?orderId="+id;
}


</script>
</body>
</html>

