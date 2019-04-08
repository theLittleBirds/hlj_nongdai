<!DOCTYPE html>
<html>
<head>
    <title>还款复核</title>
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
                	<div class="row" style="background-color: white;padding: 0px; margin-left: 0px;">
                    	<div class="panel">
                            <div id="search-panel">
                                <div class="panel-body">                                   
                                    <div class="form-inline">
                                    
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
			url: "/loanDetailRep/asyncWaitVerifyInfoList",
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
				var memberName = $("#memberName").val();
				var idCard = $("#idCard").val();				
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
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
					field: '',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '30',
					align: 'center',
					valign: 'middle'
				},
				{
					field: 'member_name',
					title: '姓名',
					align: 'center',
					width: '40',
					valign: 'middle'
				},
				{
					field: 'id_card',
					title: '身份证',
					align: 'center',
					width: '85',
					valign: 'middle'
				},
				{
					field: 'receive_capital_interest',
					title: '本息合计',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'receive_capital',
					title: '还款本金',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'receive_interest',
					title: '还款利息',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'receive_overdue',
					title: '逾期费用',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'repayment_way',
					title: '还款方式',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'info',
					title: '备注',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '100',
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

//table里面的操作列显示actionFormatter
function actionFormatter(value, row, index) {
	var html = null;
		
	var html = "<button class='btn btn-primary' type='button' onclick='riskList(\""+row.id+"\", this)'><i class='fa fa-search'></i>复审</button>";
	
    return html;
}

//复核 
function riskList(id, _this){
	$(_this).attr("disabled","disabled");
	
	$.ajax({
	    url:'/loanDetailRep/waitVerifyCheck',
	    type:'POST', 
	    async:true,
	    data:{
	    	ldrId: id
	    },
	    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
	    success:function(data,textStatus,jqXHR){
	        
	        if(data){
	        	parent.window.bootbox.alert("还款复核成功！");
	        	window.location.reload();
	        }else{
	        	parent.window.bootbox.alert("还款复核失败！");
	        	window.location.reload();
	        }
	    }
	})

}

</script>
</body>
</html>

