<!DOCTYPE html>
<html>
<head>
    <title>待放款</title>
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
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			orgName: orgName,
	      			memberName: memberName,
	      			status: 8,
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
					field: 'phone',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'repaymentType',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'bankCardNo',
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
					field: 'idCard',
					title: '身份证号',
					align: 'center',
					width: '30',
					valign: 'middle',
				},
				{
					field: 'capital',
					title: '授信额度(元)',
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
				/* {
					field: 'rate',
					title: '月利率(%)',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'serviceFee',
					title: '服务费(元)',
					align: 'center',
					width: '50',
					valign: 'middle'
				}, */
				{
					field: 'guaranteeReverse',
					title: '反担保金',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: guaranteeReverseact
				},
				{
					field: 'applyTime',
					title: '申请时间',
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

function hrefaction(value, row, index){
	var html = "<a style='cursor:pointer;' onclick='detail(\""+row.intoPieceId+"\")'>"+row.memberName+"</a>";
	return html;
}
//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='reject(\""+row.intoPieceId+"\")'><i class='fa fa-search'></i>放弃贷款</button>"
	+"<button class='btn btn-primary' type='button' onclick='underline(\""+row.id+"\",\""+row.memberName+"\",\""+row.idCard+"\",\""+row.phone+"\",\""+row.bankCardNo+"\",\""+row.capital+"\",\""+row.serviceFee+"\",\""+row.repaymentType+"\")'><i class='fa fa-search'></i>操作</button>";
    return html;
}
function guaranteeReverseact(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='reversedetail(\""+row.intoPieceId+"\")'><i class='fa fa-search'></i>详情</button>";
    return html;
}

function reject(id){
	bootbox.confirm({  
        buttons: {  
            confirm: {
                label: '是'
            },  
            cancel: {  
                label: '否'  
            }  
        },  
        message: '此操作为不可逆操作，请再次确认客户是否放弃贷款?',  
        callback: function(result) {  
            if(result) { 
            	$.ajax({
        			type: "POST",
        			dataType: "json",
        			url: "/intopiece/abandon",
        			data:{"intoPieceId":id},
        		    error: function(request) {	
        		    	bootbox.alert(data.msg);
        		    },
        		    success: function(data) {
        		    	bootbox.alert(data.msg);
        		    	if(data.code == 2000){
        		    		$('#tableid').bootstrapTable('refresh');
        		    	}
        		    }
        		});
            } else {  
            }  
        },  
    });
}
//详情
function reversedetail(id){
	var url = "/guaranteeReverse/reverseDetail?id="+id;
	window.location.href= url;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function detail(id){
	parent.window.jsOpeanIntoPieceDetail(id,"/lending/waitloan");
}

function online(id){
	alert(id);
}

//, member_name, idCard, mobile_no, bank_card_no, capital, service_fee, repaymentType原来参数
function underline(id){

    
    var content =  '<form id="bankbackform"    enctype="multipart/form-data" method="post">'+
    	'<table class="table  table-condensed table-hover table-bordered">' +
				    '     <tbody>' +
				    '         <tr><td align="right">银行借款合同：</td><td>' + '<input type=\"text\"  hidden=\"hidden\" id=\"fileNamel\" name=\"fileNamel\"/>'+
			        '<input type=\"file\" name=\"bankloan\" id = \"bankloan\" value = \"点击上传\" />' +'<input type=\"text\" hidden="hidden" name=\"id\" id = \"id\" value = \"'+id+'\" />' + '</td></tr>' +
			        '         <tr><td align="right">回单：</td><td>' + '<input type=\"text\"  hidden=\"hidden\" id=\"fileNamer\" name=\"fileNamer\"/>'+
			        '<input type=\"file\" name=\"bankBack\" id = \"bankBack\" value = \"点击上传\" />' + '</td></tr>'+
				    '         <tr><td align="right">放款日期：</td><td>' + '<div class="col-sm-7 input-group date" id =\'startDate\'>'
        			+'<input id="start_date" type="text" name="start_date" onchange="startDate()" class="form-control required" />'
        			+'<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>'
        		+'</div>' + '</td></tr>' +
				    '     </tbody>' +
				    '</table>'
				    +'</form>';
    	
    	
        layer.confirm(content, {
        title: '请上传银行回单',
        area: ['400px', '380px'],
        btn: ['确认', '取消']
    }, function () {
    	var loandate= $("#start_date").val();
    	if(loandate == ""){
			parent.window.bootbox.alert("放款时间必填");
			return;
		}
    	var fileName = document.getElementById("bankBack").files[0].name;
  		$("#fileNamer").val(fileName);
    	var fileName1 = document.getElementById("bankloan").files[0].name;
  		$("#fileNamel").val(fileName1);
  		var formData = new FormData($( "#bankbackform" )[0]);
  		$.ajax({
			type: "POST",
			url:'/lending/underlineloan',
		    contentType: false, 
		    processData: false,
			data:formData, //formid
		    error: function(ret) {
		    },
		    success: function(ret) {
		    	if (ret.code == 200) {
	                layer.msg(ret.msg, {icon: 1}, function () {
	                    window.location.reload();
	                });
	            } else {
	                layer.msg(ret.msg, {icon: 2});
	            }
		    	}
		});
    });
    //初始化日期选择
    usedate();  	
}

function usedate(){
     	$("#startDate").datepicker({
             todayBtn: "linked",
             keyboardNavigation: false,
             forceParse: false,
             autoclose: true
         });
}

function startDate(){
	var start_date= $("#start_date").val();
	var repaymentDay= $("#repaymentDay").val();
	if(start_date!=""&&repaymentDay!=""){
		var startDate = new Date(start_date);
		var endDate = new Date(startDate.getFullYear(), startDate.getMonth() + parseInt('${loan.term}'), $("#repaymentDay").val(), startDate.getHours(), startDate.getMinutes(), startDate.getSeconds());
	    var y = endDate.getFullYear();
	    var m = endDate.getMonth() + 1;
	    m = m < 10 ? ('0' + m) : m;
	    var d = endDate.getDate();
	    d = d < 10 ? ('0' + d) : d;
	    $("#end_date").val(y + '-' + m + '-' + d);
	}
	
}
</script>
</body>
</html>

