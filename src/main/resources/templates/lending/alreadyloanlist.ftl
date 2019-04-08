<!DOCTYPE html>
<html>
<head>
    <title>已放款</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
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
									
									    <label class="control-label" for="memberName">贷户姓名:</label>
									
									    <input type="text" class="form-control" id="memberName">
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="idCard">身份证号:</label>
									
									    <input type="text" class="form-control" id="idCard">
									
									  </div>
									  
									  <!-- <div class="form-group">
									
									    <label class="control-label" for="startDate">借款开始日期:</label>
									
									    <div class="input-group date"  id ='start'>
		                                    <input id="startDate" type="text"  class="form-control required"/>
		                                    <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar" ></span></span>
		                            	</div>	
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="endDate">借款结束日期:</label>
									
									    <div class="input-group date"  id ='end'>
		                                    <input id="endDate" type="text"  class="form-control required"/>
		                                    <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar" ></span></span>
		                            	</div>	
									
									  </div> -->
									
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        <button class="btn btn-primary" type="button" onclick="exportFile()"><i class="fa fa-search"></i> 导出
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
	
	/* $("#start").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	$("#end").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    }); */
    $('#tableid').bootstrapTable({
			method: 'post', 
			url: "/loan/contractlist",
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
				//var startDate = $("#startDate").val();
				//var endDate = $("#endDate").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			idCard:idCard,
	      			//startDate:startDate,
	      			//endDate:endDate,
	      			status: 9,
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
					width: '100',
					valign: 'middle'
				},
				{
					field: 'use',
					title: '客户类型',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: useFormat
				},
				{
					field: 'memberName',
					title: '贷户姓名',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: hrefaction
				},
				{
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'address',
					title: '家庭住址',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'capital',
					title: '贷款金额(元)',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'loanDate',
					title: '放款日期',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'endDate',
					title: '贷款到期日期',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'finsName',
					title: '放款机构（银行）',
					align: 'center',
					width: '100',
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
	var html = "<a style='cursor:pointer;' onclick='detail(\""+row.intoPieceId+"\")'>"+row.memberName+"</a>";
	return html;
}
function useFormat(value, row, index){
	if(value == "1"){
		return '新增';
	}else if(value == "2"){
		return '转贷';
	}else{
		return '-';
	}	
}
//table里面的操作列显示
function actionFormatter(value, row, index) {
    return "";
}

function detail(id){
	parent.window.jsOpeanIntoPieceDetail(id,"/lending/alreadyloan");
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function exportFile(){
	var orgName= $("#orgName").val();
	var memberName= $("#memberName").val();
	var idCard= $("#idCard").val();
	window.location.href= "/loan/alreadyloanexport?orgName="+orgName+"&memberName="+memberName+"&idCard="+idCard;
}
</script>
</body>
</html>

