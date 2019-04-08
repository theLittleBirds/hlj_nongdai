<!DOCTYPE html>
<html>
<head>
    <title>农鲸版本管理</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
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
                	<div class="row" style="background-color: white;padding: 0px;">
                    	<div class="panel">
                            <div id="search-panel">
                                <div class="panel-body">                                   
                                    <div class="form-inline">
									  <button id="btn_add" class="btn btn-primary" onclick="add()">
									  <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
									  </button>
									  <div class="form-group">
									
									    <label class="control-label" for="versionNumber">版本号:</label>
									
									    <input type="text" class="form-control" id="versionNumber">
									
									  </div>
									
									  <div class="form-group">
									
									    <label class="control-label" for="type">操作系统:</label>
									
									    <select id="type" name="type" class="form-control">
                                        	<option value selected="selected">--请选择--</option>
                                        	<option value=1 >ios</option>
                                        	<option value=2 >android</option>
                                        </select>
                                      </div>
                                      <div class="form-group">
                                        <label class="control-label" for="channel">渠道:</label>
                                        <select  id="channel" name="channel" class="form-control">
				                                 	${app}                                         
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
			url: "/appVersion/appList",
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
				var versionNumber = $("#versionNumber").val();
				var type = $("#type").val();
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			versionNumber: versionNumber,
	      			type: type,
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
					field: 'versionNumber',
					title: '版本号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'type',
					title: '操作系统',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter:typeFormatter,
				},
				{
					field: 'forceUpdate',
					title: '强制更新',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter:forceFormatterdb,
				},
				{
					field: 'url',
					title: '下载地址',
					align: 'center',
					width: '150',
					valign: 'middle'
				},
				{
					field: 'host',
					title: '路由前缀',
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
	var html = "<a href=\"/appVersion/form?id="+row.id+"\" class=\"btn btn-outline btn-primary\">编辑</a><a href=\"javascript:void(0);\" onclick=\"del('"+row.id+"');\" class=\"btn btn-outline btn-danger\">删除</a>";
    return html;
}

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

function add(){
	window.location.href="/appVersion/form";
}

function del(id){
	

	bootbox.confirm({  
		buttons: {  
        confirm: {
			label: '是'
        },  
        cancel: {  
        	label: '否'
        }  
        },  
        message: '确认删除选中的数据记录吗?',  
        callback: function(result) {  
            if(result)
            {  
            	var url = "/appVersion/del?id="+id;
        		window.location.href= url;
            }
            else
            {  
            }  
        },  
    });

	
	
}
function forceFormatterdb(value) {
	if(value==0){
		return ['<span>否</span>'];
	}else{
		return ['<span>是</span>'];
	}
}
function typeFormatter(value) {
	if(value==1){
		return ['<span>ios</span>'];
	}else{
		return ['<span>android</span>'];
	}
}
</script>
</body>
</html>

