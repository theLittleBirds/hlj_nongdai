<!DOCTYPE html>
<html>
<head>
    <title>轮播图管理</title>
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
                                    
									  <form  id="imageForm">				                        
				                        <div class="form-inline" style="padding-left: 20px;">
					                        <div class="form-group">
										
											    <label class="control-label" for="channel">渠道:</label>
											    
											    <select  id="channel" name="channel" class="form-control">
				                                 	${app}                                         
                                  				</select>										      
											
											 </div>	
											 
											 <div class="form-group">
											 
											 	<button class="btn btn-primary" type="button" onclick="file_click()"><i></i>上传</button>
											 	
											 	<div style="display: none;">
											 	
											 		<input type="file" id="tu" name="tu" accept="image/jpeg,image/png" multiple="multiple" onchange="postimage()">
											 		
											 	</div>
											 												 	
											 </div>
											 
										 </div>										
				                    </form>
									  
                                </div>
                            </div>
                        </div>
                    	<table id="tableid">
         	
                    	</table>
                    	
                    	<div class="initmodal">
							<div id="lendermodalid">
								<div class="modal fade in" id="carouselFigureModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"  aria-hidden="true" style="display: none;">
									<div class="modal-dialog" id="carouselFiguredialogid">
										<div class="modal-content">
											<div class="modal-body" >
					                    		<form class="form-horizontal" id="addform" role="form">
					                    			<fieldset>
					                    				<div class="form-group">
					                    				<label class="col-sm-3 control-label" for="status">渠道</label>
					                    					<div class="col-sm-8">
							                    				<select id="channel_add" name="channel" class="form-control">
																	${app}
																</select>
															</div>
					                    				</div>
					                    				<div class="form-group">
					                    				<label class="col-sm-3 control-label" for="status">是否显示</label>
					                    					<div class="col-sm-8">
							                    				<select id="status" name="status" class="form-control">
																	<option value="0">不显示</option>
																	<option value="1">显示</option>
																</select>
															</div>
					                    				</div>
					                    				<input id="id" name="id" hidden="hidden" />
					                    				<div class="form-group">
					                    				<label class="col-sm-3 control-label" for="number">顺序号</label>
						                    				<div class="col-sm-8">
						                    					<input  class="form-control" name="number" id="number" type="text" placeholder="">
						                    				</div>
					                    				</div>
					                    			</fieldset>
					                    		</form>
					                    	</div>
					                    	<div class="modal-footer">
					                    		<button  id="submit_btn" type="button" class="btn btn-primary">提交</button>
					                    		<button type="button" class="btn btn-default" id="cancel_btn" data-dismiss="modal" style="margin-right: 40%;">取消</button>
					                    	</div>
										</div>
									</div>
								</div>
							</div>
						</div>
                    	
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
			url: "/carouselFigure/carouselFigureList",
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
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
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
					field: 'channel',
					title: '渠道',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'status',
					title: '是否显示',
					align: 'center',
					width: '30',
					valign: 'middle',
					formatter:showFormatterdb
				},
				{
					field: 'number',
					title: '顺序号',
					align: 'center',
					width: '30',
					valign: 'middle'
				},
				{
					field: 'size',
					title: '大小',
					align: 'center',
					width: '30',
					valign: 'middle',
				},
				{
					field: 'creDate',
					title: '上传日期',
					align: 'center',
					width: '40',
					valign: 'middle',
					formatter: changeDateFormat
				},
				{
					field: 'name',
					title: '缩略图',
					align: 'center',
					width: '110',
					valign: 'middle'
				},
				{
					field: 'updOperName',
					title: '访问链接',
					align: 'center',
					width: '30',
					valign: 'middle',
					visible:false
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
	var html = "<a href=\""+row.updOperName+"\" target=\"_blank\" class=\"btn btn-outline btn-primary\">原图</a><a href=\"javascript:void(0);\" onclick=\"del('"+row.id+"');\" class=\"btn btn-outline btn-danger\">删除</a><a href=\"javascript:void(0);\" onclick=\"edit('"+row.id+"','"+row.channel+"','"+row.status+"','"+row.number+"');\" class=\"btn btn-outline btn-primary\">维护</a>";
    return html;
}

function file_click(){
	var channel = $("#channel").val();
	if(channel == ''){
		parent.window.bootbox.alert("请选择渠道标识");
		return;
	}
	$("#tu").click();
}

function postimage(){
	var formData = new FormData($("#imageForm")[0]); 
	$.ajax({
		url: "/carouselFigure/upload", 
        type: "POST", 
        data: formData, 
        async: false, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	    	$(".bootboxModal").remove();
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		$('#tableid').bootstrapTable('refresh');
	    	}
	    }
	});
}

function edit(id,channel,status,number){
	$("#id").val(id);
	$("#number").val(number);
	$("#channel_add").val(channel);
	var status1=  document.getElementById("status");
    for(var i=0;i<status1.length;i++)
    {

        if(status1[i].value==status)
        {
            //alert(1);
            status1[i].selected=true;
        }
    }
	$("#carouselFigureModal").modal("show");
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
            	var url = "/carouselFigure/del?id="+id;
        		window.location.href= url;
            }
            else
            {  
            }  
        },  
    });

	
	
}
function showFormatterdb(value) {
	if(value==0){
		return ['<span>不显示</span>'];
	}else{
		return ['<span>显示</span>'];
	}
}

$("#submit_btn").click(function (){
	$.ajax({
		type: "POST",
		dataType: "json",
		url:"/carouselFigure/update",
		data:$('#addform').serialize(), //formid
	    error: function(request) {
	        //alert("Connection error");
	    },
	    success: function(data) {
	    	if(data.errorno==1000){
	    		showError("系统错误！", '');
	    	}else{
	    		$('#carouselFigureModal').modal('hide');
		    	$('#addform')[0].reset();
		    	$('#tableid').bootstrapTable('refresh');
	    	}
	    	}
	});
});
</script>
</body>
</html>

