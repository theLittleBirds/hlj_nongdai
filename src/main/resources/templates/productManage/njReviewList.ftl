<!DOCTYPE html>
<html>
<head>
    <title>上架审核列表</title>
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
									    	<label class="control-label" for="productName">商品名称:</label>
									    	<input type="text" class="form-control" id="productName">
									    </div>
									    <div class="form-group">
									    	<label class="control-label" for="productBrand">商品品牌:</label>
									    	<select id="productBrand" class="form-control">
			                                 	<option value="">--请选择--</option>
			                                 	<#list brandList as brand>
			                                 		<option value="${brand.value}">${brand.descr}</option>
			                                 	</#list>                                       
			                                </select>
									    </div>
										<div class="form-group">
									    	<label class="control-label" for="productCategory">商品分类:</label>
									    	<select id="productCategory" class="form-control">
			                                 	<option value="">--请选择--</option>
			                                 	<#list categoryList as category>
			                                 		<option value="${category.value}">${category.descr}</option>
			                                 	</#list>                                      
			                                </select>
									    </div>
									    <div class="form-group">
									    	<label class="control-label" for="status">商品状态:</label>
									    	<select id="status" class="form-control">
			                                 	<option value="">--请选择--</option>
			                                 	<option value="1">上架</option>
			                                 	<option value="2">待上架</option>
			                                 	<option value="3">驳回</option>
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
			url: "/njReview/asyncLoanRiskPostList",
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
				var productName = $("#productName").val();
				var productBrand = $("#productBrand").val();				
				var productCategory = $("#productCategory").val();				
				var status = $("#status").val();				
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			productName: productName,
	      			productBrand: productBrand,
	      			productCategory: productCategory,
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
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'productBrandName',
					title: '品牌',
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
					field: 'productCategoryName',
					title: '类别',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'price',
					title: '单价',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'settlePrice',
					title: '结算单价',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'standard',
					title: '规格',
					align: 'center',
					width: '10',
					valign: 'middle',
				},
				{
					field: 'status',
					title: '状态',
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

//table里面的操作列显示actionFormatter
function actionFormatter(value, row, index) {
	//商品状态， 1：上架，2：待上架，3：驳回
	var html = null;
	if(row.status == 1 || row.status == 3){
		html = "暂无操作";
	}else{
		html = "<button class='btn btn-primary' type='button' onclick='productInfo(\""+row.id+"\",this)'><i class='fa fa-search'></i>审核</button>";
	}
	
    return html;
}

//table里面的操作列显示
function statusFormatter(value, row, index) {
	var html = null;
	if(value == 1){
		html = "上架";
	}else if(value == 2){
		html = "待上架";
	}else{
		html = "驳回";
	}
    return html;
}

//编辑 
function productInfo(id){
	window.location.href = "/njReview/toNjProductSaveOrEdit?id="+id;
}


</script>
</body>
</html>

