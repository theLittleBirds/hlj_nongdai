<!DOCTYPE html>
<html>
<head>
    <title>还款中</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
  	<script src="/resource/bootbox/bootbox.js"></script>
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
									  
									  <div class="form-group">
									
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
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="status">状态:</label>
									
									    <select  id="status"  class="form-control">
		                                 	${statusList}                                        
		                                  </select>
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="month">本月还款:</label>
									
									    <select  id="month"  class="form-control">
									    	<option value="">--请选择--</option> 
											<option value="1">已还</option> 
											<option value="2">未还</option>                                       
		                                  </select>
									
									  </div>
									
										<button class="btn btn-primary" type="button" onclick="selectByPara()"><i class="fa fa-search"></i> 检索
                                        </button>
                                        
                                        <button class="btn btn-primary" type="button" onclick="upload1()"><i class="fa fa-search"></i> 导入还款
                                        </button>
                                        
                                        <div style="display: none;">
                                        	<input type="file" onchange="file1()" id="xlsx1"/>
                                        	<input type="file" onchange="file2()" id="xlsx2"/>
                                        </div>
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
	
	$("#start").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	$("#end").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	
    $('#tableid').bootstrapTable({
			method: 'post', 
			url: "/postlending/list",
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
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var loanstatus = $("#status").val();
				var month = $("#month").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			idCard:idCard,
	      			startDate:startDate,
	      			endDate:endDate,
	      			loanstatus:loanstatus,
	      			month:month,
	      			ipstatus: 10,
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
					field: 'memberName',
					title: '姓名',
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
					field: 'capital',
					title: '借款金额(元)',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'startDate',
					title: '借款开始日期',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'endDate',
					title: '借款结束日期',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'finsName',
					title: '资方',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'loanedManFee',
					title: '在贷余额',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter: balanceFormat
				},
				{
					field: 'firstRepaymentDate',
					title: '下次还款日期',
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

function balanceFormat(value, row, index){
	var receiveCapital = row.receiveCapital;
	if(receiveCapital != null){
		value = value -receiveCapital;
	}
	var receiveInterest = row.receiveInterest;
	if(receiveInterest != null){
		value = value -receiveInterest;
	}
	return value;
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
	var html = "<button class='btn btn-primary' type='button' onclick='detail(\""+row.id+"\")'><i class='fa fa-search'></i>明细</button>"
	+"<button class='btn btn-primary' type='button' onclick='visit(\""+row.id+"\")'><i class='fa fa-search'></i>回访记录</button>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function intoPiecedetail(id){
	parent.window.jsOpeanIntoPieceDetail(id,"/postlending/repayinglist");
}

function detail(id){
	var url = "/postlending/loandetail?id="+id;
	window.location.href= url;
}

function visit(id){
	var url = "/returnvisit/loanid?id="+id;
	window.location.href= url;
}


function upload1(){
	$("#xlsx1").click();
}

function upload2(){
	$("#xlsx2").click();
}

function file1(){
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>导入中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	var formData = new FormData();
    formData.append("file", document.getElementById("xlsx1").files[0]);
    $.ajax({
		url: "/postlending/importrepayxlsx", 
        type: "POST", 
        data: formData, 
        async: false, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert("导入失败");
	    },
	    success: function(data) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		$('#tableid').bootstrapTable('refresh');
	    	}
	    }
	});
}

function file2(){
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>导入中</span>",
		closeButton: false,
		className: "bootboxModal",
	});
	var formData = new FormData();
    formData.append("file", document.getElementById("xlsx2").files[0]);
    $.ajax({
		url: "/postlending/importfinishxlsx", 
        type: "POST", 
        data: formData, 
        async: false, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert("导入失败");
	    },
	    success: function(data) {
	    	parent.removeBootBox("bootboxModal");
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		$('#tableid').bootstrapTable('refresh');
	    	}
	    }
	});
}
</script>
</body>
</html>

