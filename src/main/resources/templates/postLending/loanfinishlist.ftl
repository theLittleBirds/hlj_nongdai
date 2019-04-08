<!DOCTYPE html>
<html>
<head>
    <title>还款完成</title>
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
			url: "/postlending/finishlist",
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
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			ipstatus: 13,
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
					field: 'xh',
					titleTooltip: '全选/全不选',
					checkbox: true,
					width: '30',
					align: 'center',
					valign: 'middle'
				},
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
					width: '50',
					valign: 'middle'					
				},
				{
					field: 'memberName',
					title: '姓名',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: hrefaction
				},
				{
					field: 'loanedManFee',
					title: '还款总额(元)',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'term',
					title: '期限(月)',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveCapital',
					title: '已还本金',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveInterest',
					title: '已还利息',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'receiveOverdue',
					title: '已还逾期利息',
					align: 'center',
					width: '50',
					valign: 'middle'
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
        
        return date.getFullYear() + "-" + month + "-" + currentDate ;
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

function hrefaction(value, row, index){
	var html = "<a style='cursor:pointer;' onclick='intoPiecedetail(\""+row.intoPieceId+"\")'>"+row.memberName+"</a>";
	return html;
}

//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='detail(\""+row.id+"\")'><i class='fa fa-search'></i>明细</button>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function intoPiecedetail(id){
	parent.window.jsOpeanIntoPieceDetail(id,"/postlending/loanfinish");
}

function detail(id){
	var url = "/postlending/loandetail?id="+id;
	window.location.href= url;
}

</script>
</body>
</html>

