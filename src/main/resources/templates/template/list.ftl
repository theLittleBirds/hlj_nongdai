<!DOCTYPE html>
<html>
<head>
    <title>合同模板</title>
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
									
									    <button onclick="detail('')" class="btn btn-primary"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加</button>
									
									  </div>									
									
									  <div class="form-group">
									
									    <label class="control-label" for="finsName">金融机构:</label>
									
									    <input type="text" class="form-control" id="finsName">
									
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
			url: "/contacttemplate/list",
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
				var finsName = $("#finsName").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	    			finsName:finsName,
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1		//页码
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
					field: 'id',
					title: '编号',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
				},
				{
					field: 'finsId',
					title: '金融机构',
					align: 'center',
					width: '50',
					valign: 'middle'
				},
				{
					field: 'title',
					title: '标题',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'isOpean',
					title: '状态',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: statusFormatter
				},
				{
					field: 'updDate',
					title: '最后修改时间',
					align: 'center',
					width: '50',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: '',
					title: '操作',
					align: 'center',
					width: '50',
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

function statusFormatter(value) {
	if(value == 1){
		return "启用";
	}else if(value == 2){
		return "禁用";
	}
}
//table里面的操作列显示
function actionFormatter(value, row, index) {
	var html = "<button class='btn btn-primary' type='button' onclick='detail(\""+row.id+"\")'><i class='fa fa-search'></i>详情</button><button class='btn btn-primary' type='button' onclick='del(\""+row.id+"\")'><i class='fa fa-search'></i>删除</button>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function detail(id){
	var url = "/contacttemplate/edit?id="+id;
	window.location.href= url;
}

function del(id){
	$.ajax({
		type: "POST",
		url: "/contacttemplate/delete",
		data:{"id":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	$('#tableid').bootstrapTable('refresh');
	    }
	});
}
</script>
</body>
</html>

