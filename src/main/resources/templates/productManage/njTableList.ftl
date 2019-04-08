<!DOCTYPE html>
<html>
<head>
    <title>商品列表</title>
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
									    	<label class="control-label" for="orderName">套餐名称:</label>
									    	<input type="text" class="form-control" id="orderName">
									    </div>
									    <div class="form-group">
									    	<label class="control-label" for="status">套餐状态:</label>
									    	<select id="status" class="form-control">
			                                 	<option value="">--请选择--</option>
			                                 	<option value="1">上架</option>
			                                 	<option value="2">下架</option>
			                                </select>
									    </div>
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        <button class="btn btn-primary" type="button" onclick="orderAdd()"><i class="fa fa-search"></i> 新增套餐
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
			url: "/njTable/asyncLoanRiskPostList",
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
				var orderName = $("#orderName").val();
				var status = $("#status").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orderName: orderName,
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
					field: 'orderName',
					title: '套餐名称',
					align: 'center',
					width: '40',
					valign: 'middle'
				},
				{
					field: 'orderPrice',
					title: '套餐单价',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'orderSettleprice',
					title: '套餐结算单价',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '套餐状态',
					align: 'center',
					width: '85',
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
	var html = "<button class='btn btn-primary' type='button' onclick='orderEdit(\""+row.id+"\",this)'><i class='fa fa-search'></i>编辑</button>"
					+"<button class='btn btn-primary' type='button' onclick='orderDelete(\""+row.id+"\",this)'><i class='fa fa-search'></i>删除</button>"
					+"<button class='btn btn-primary' type='button' onclick='orderInfo(\""+row.id+"\",this)'><i class='fa fa-search'></i>上下架</button>";
    return html;
}

//table里面的操作列显示
function statusFormatter(value, row, index) {
	var html = null;
	if(value == 1){
		html = "上架";
	}else{
		html = "下架";
	}
    return html;
}

//编辑 
function orderEdit(id){
	window.location.href = "/njTable/toNjProductOrderSaveOrEdit?id="+id;
}

//添加 
function orderAdd(){
	window.location.href = "/njTable/toNjProductOrderSaveOrEdit";
}

//审核 
function orderInfo(id){
	window.location.href = "/njTable/toNjProductOrderExamine?id="+id;
}

//删除 
function orderDelete(id){
	parent.window.bootbox.confirm({  
		buttons: {  
	        cancel: {  
	        	label: '否'
	        },
	        confirm: {
				label: '是'
	        }
        },  
        message: '确认要删除该商品吗？',  
        callback: function(result) {  
            if(result){
            	$.ajax({
	        	    url:'/njTable/njProductOrderDelete',
	        	    type:'POST', 
	        	    async:true,
	        	    data:{
	        	    	id: id
	        	    },
	        	    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
	        	    success:function(data,textStatus,jqXHR){
	        	        /* console.log(data);
	        	        console.log(textStatus);
	        	        console.log(jqXHR); */
	        	        
	        	        if(data){
	        	        	parent.window.bootbox.alert("套餐删除成功！");
	        	        	window.location.reload();
	        	        }else{
	        	        	parent.window.bootbox.alert("套餐删除失败！");
	        	        	window.location.reload();
	        	        }
	        	    }
	        	}) 
            }  
        }
    });
}

</script>
</body>
</html>

