<!DOCTYPE html>
<html>
<head>
    <title>客户资料</title>
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
									
									    <label class="control-label" for="q_name">姓名:</label>
									
									    <input type="text" class="form-control" id="q_name">
									
									  </div>
									
									  <div class="form-group">
									
									    <label class="control-label" for="q_idCard">身份证号:</label>
									
									    <input type="text" class="form-control" id="q_idCard">
									
									  </div>
									  
									  <div class="form-group">
									
									    <label class="control-label" for="q_phone">手机号:</label>
									
									    <input type="text" class="form-control" id="q_phone">
									
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
		url: "/member/memberList",
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
			var q_name = $("#q_name").val();
			var q_idCard = $("#q_idCard").val();
			var q_phone = $("#q_phone").val();
    		var temp = {					//这里的键的名字和控制器的变量名必须一致
      			pageSize: params.limit,			//页面大小
      			currentPage: (params.offset / params.limit) + 1,		//页码
      			name : q_name,
      			idCard: q_idCard,
      			bankPhone: q_phone,
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
				field: 'memberId',
				title: '客户编号',
				align: 'center',
				width: '50',
				valign: 'middle',
				visible:false
			},
			{
				field: 'name',
				title: '姓名',
				align: 'left',
				width: '15',
				valign: 'middle'
			},
			{
				field: 'idCard',
				title: '证件号',
				align: 'left',
				width: '65',
				valign: 'middle'
			},
			{
				field: 'bankPhone',
				title: '手机号',
				align: 'center',
				width: '15',
				valign: 'middle'
			},
			{
				field: 'bank',
				title: '银行卡开户行',
				align: 'left',
				width: '15',
				valign: 'middle'
			},
			{
				field: 'bankCardNo',
				title: '银行卡号',
				align: 'center',
				width: '15',
				valign: 'middle',
			},
			{
				field: 'creDate',
				title: '创建时间',
				align: 'center',
				width: '15',
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
			var rows = data.rows;
			for(var i=0;i<rows.length;i++)
			{
				if(rows[i].isDelete == 1)
				{
					// 删除行变色
					$('#tableid tbody').find('tr:eq('+i+')').css("color","#d9534f");
					
				}
			}
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

//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='intoPiece(\""+row.memberId+"\")'><i class='fa fa-search'></i>进件信息</button>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function intoPiece(id){
	$.ajax({
		type: "POST",
		url:"/member/lastintopiece",
		data:{"id":id}, //formid
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	if(data.code==200){
	    		var intoPieceId = data.intoPieceId;
	    		parent.window.jsOpeanIntoPieceDetail(intoPieceId,"/member/list");
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
}
</script>
</body>
</html>

