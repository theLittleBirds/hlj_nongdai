<!DOCTYPE html>
<html>
<head>
    <title>贷中监控</title>
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
									    <div class="form-group">
									    	<label class="control-label" for="phone">手机号:</label>
									    	<input type="text" class="form-control" id="phone">
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
			url: "/tongDunRiskPost/asyncLoanRiskPostList",
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
				var phone = $("#phone").val();				
	    		var temp = {					//这里的键的名字和控制器的变量名必须一致
	      			pageSize: params.limit,			//页面大小
	      			currentPage: (params.offset / params.limit) + 1,		//页码
	      			memberName: memberName,
	      			idCard: idCard,
	      			phone: phone,
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
					field: 'memberName',
					title: '姓名',
					align: 'center',
					width: '40',
					valign: 'middle'
				},
				{
					field: 'idCard',
					title: '身份证',
					align: 'center',
					width: '85',
					valign: 'middle'
				},
				{
					field: 'phone',
					title: '手机号',
					align: 'center',
					width: '100',
					valign: 'middle'
				},
				{
					field: 'loanDate',
					title: '放款日期',
					align: 'center',
					width: '60',
					valign: 'middle',
					formatter: dateFormatter
				},
				{
					field: 'loanTerm',
					title: '放款期限',
					align: 'center',
					width: '60',
					valign: 'middle'
				},
				{
					field: 'overdueLevel',
					title: '逾期风险等级',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: universalFormatter
				},
				{
					field: 'badRate',
					title: '"坏"客户占比',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: universalFormatter
				},
				{
					field: 'score',
					title: '用户行为分',
					align: 'center',
					width: '10',
					valign: 'middle',
					formatter: universalFormatter
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

function selectByPara(){
	$('#tableid').bootstrapTable('refresh');
}

//table里面的操作列显示actionFormatter
function actionFormatter(value, row, index) {
	var html = null;
	if(row.data){
		var html = "<button disabled='disabled' class='btn btn-primary' type='button' onclick='riskList(\""+row.reportId+"\",\""+row.intoPieceId+"\",this)'><i class='fa fa-search'></i>获的风险</button>"
						+"<button class='btn btn-primary' type='button' onclick='riskListInfo(\""+row.id+"\",this)'><i class='fa fa-search'></i>风险详情</button>";
	}else{
		//暂时修改为无风险列表也允许查看风险报告 
		/* var html = "<button class='btn btn-primary' type='button' onclick='riskList(\""+row.reportId+"\",\""+row.intoPieceId+"\",this)'><i class='fa fa-search'></i>获的风险</button>"
						+"<button disabled='disabled' class='btn btn-primary' type='button' onclick='riskListInfo(\""+row.id+"\",this)'><i class='fa fa-search'></i>风险详情</button>"; */
		var html = "<button disabled='disabled' class='btn btn-primary' type='button' onclick='riskList(\""+row.reportId+"\",\""+row.intoPieceId+"\",this)'><i class='fa fa-search'></i>获的风险</button>"
						+"<button class='btn btn-primary' type='button' onclick='riskListInfo(\""+row.id+"\",this)'><i class='fa fa-search'></i>风险详情</button>";
	}
    return html;
}

//table里面的操作列显示
function universalFormatter(value, row, index) {
	var html = null;
	if(value){
		html = value;
	}else{
		html = "没有获的风险详情";
	}
    return html;
}

//获的风险
function riskList(reportId,intoPieceId,_this){
	$(_this).attr("disabled","disabled");
	
	$.ajax({
	    url:'/tongDunRiskPost/loanRiskPostInfo',
	    type:'POST', 
	    async:true,
	    data:{
	    	reportId: reportId,
	    	intoPieceId: intoPieceId
	    },
	    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
	    success:function(data,textStatus,jqXHR){
	        /* console.log(data);
	        console.log(textStatus);
	        console.log(jqXHR); */
	        
	        if(data){
	        	parent.window.bootbox.alert("风险列表获取成功！");
	        	window.location.reload();
	        }else{
	        	parent.window.bootbox.alert("风险列表获取失败！");
	        	window.location.reload();
	        }
	    }
	})

/*     $.ajax({
        type : 'post',
        async : false,
        url : '/tongDunRiskPost/loanRiskPostInfo',
        data:{
 	    	reportId: reportId,
 	    	intoPieceId: intoPieceId
 	    },
        dataType : 'json',
        success : function(data, status) {
            alert(data);
            if(data&&data.code&&data.code=='101'){
                modals.error("操作失败，请刷新重试，具体错误："+data.message);
                return false;
            }
        },
        error : function(err, err1, err2) {
            console.log("ajaxPost发生异常，请仔细检查请求url是否正确，如下面错误信息中出现success，则表示csrftoken更新，请忽略");
            console.log(err.responseText);
            if(err && err.readyState && err.readyState == '4'){
                var sessionstatus=err.getResponseHeader("session-status");
                if(sessionstatus=="timeout"){
                    //如果超时就处理 ，指定要跳转的页面
                    top.location.href="/" ;
                }
            }           
        }
    }); */

}

//风险详情
function riskListInfo(id,_this){
	$(_this).attr("disabled","disabled");
	
	setTimeout(function(){
		$(_this).attr("disabled",false);
	}, 3000);
	
	window.open("/tongDunRiskPost/tdloanRiskPostInfo?id="+id);
}

function timeFormatter(value, row, index) {
	if(value != null){
		return [
			       '<span>'+fmtDate(value)+'</span>'
			     ];
	}else{
		return null;
	}
     
}
function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}

function dateFormatter(value){
    var date =  new Date(value);
    return ['<span>' + date.toLocaleString() +'</span>']
}

</script>
</body>
</html>

