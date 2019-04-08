<!DOCTYPE html>
<html>
<head>
    <title>购物车</title>
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
                	<div class="row" style="background-color: white;padding: 10px;margin-left: 0px;">
                        <div style="margin-top: 5px;margin-bottom: 5px;">
                            <button class="btn btn-primary" type="button" onclick="delAll()">
                                <i class="fa fa-search"></i>全部删除</button>
                        </div>
                        <table id="tableid">
         	
                    	</table>

                            <div class="form-group" style="margin-top: 1%;">
                                <div class="col-sm-8"></div>
                                <div class="col-sm-4">
                                    <div class="col-sm-6" style="font-size: 16px;color: red">
                                        <label>合计：￥</label>
                                        <span id="totalMoney">0.00</span>
                                    </div>
                                    <div class="col-sm-4">
                                        <button class="btn btn-w-m" style="background-color: #85c360; margin-top: -10px;" type="button" onclick="commitOrder()">
                                            <i class="ace-icon fa fa-reply bigger-110"></i>  去下单
                                        </button>
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
			url: "/njShoppingCar/njShoppingCarList",
			dataType: "json",
			cache: false,
			height: $(window).height()-130,
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
                 field: 'selectItem',
                 titleTooltip: '全选/全不选',
                 checkbox: true,
                 width: '30',
                 align: 'center',
                 valign: 'middle'
             },
            {
                field: 'productBrandName',
                title: '品牌',
                align: 'center',
                width: '40',
                valign: 'middle'
            },
            {
                field: 'productName',
                title: '商品名称',
                align: 'center',
                width: '85',
                valign: 'middle'
            },
            {
                field: 'productCategoryName',
                title: '类别',
                align: 'center',
                width: '100',
                valign: 'middle'
            },
            {
                field: 'productPrice',
                title: '单价(元)',
                align: 'center',
                width: '60',
                valign: 'middle'
            },
            {
                field: 'productNum',
                title: '数量',
                align: 'center',
                width: '10',
                valign: 'middle',
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
            },
            onCheck:function(){
                total();
            },
            onUncheck:function(){
                total();
            },
            onCheckAll:function(){
                total();
            },
            onUncheckAll: function () {
                $("#totalMoney").text("0.00");
            }
		});
});

function responseHandler(res) {
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
function actionFormatter(value,row) {
    var html = "<button class='btn btn-primary' type='button' onclick='delProduct(\""+row.shopingCarId+"\",this)'>" +
			   "<i class='fa fa-search'></i>删除</button>"
    return html;
}

//删除
function delProduct(id){
    parent.window.bootbox.confirm({
        buttons: {
            cancel: {
                label: '否'
            },
            confirm: {
                label: '是'
            }
        },
        message: '确认要删除该商品吗？',
        callback: function(result) {
            if(result){
                $.ajax({
                    url:'/njShoppingCar/njDeletePro',
                    type:'POST',
                    async:true,
                    data:{
                        id: id
                    },
                    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        if(data){
                            parent.window.bootbox.alert("删除成功！");
                            window.location.reload();
                        }else{
                            parent.window.bootbox.alert("删除失败！");
                            window.location.reload();
                        }
                    }
                })
            }
        }
    });
}
//全部删除
function delAll() {
    //获得多选框选中的数据
    var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
    var objCodes = '';
    if(selData.length > 0)
    {
        for(var i=0;i<selData.length;i++)
        {
            objCodes += selData[i].shopingCarId + ',';
        }
        objCodes = objCodes.substring(0, objCodes.length-1)
    }else{
        parent.window.bootbox.alert("请选择要删除的商品！");
        return;
    }
    parent.window.bootbox.confirm({
        buttons: {
            cancel: {
                label: '否'
            },
            confirm: {
                label: '是'
            }
        },
        message: '确认要删除全部商品吗？',
        callback: function(result) {
            if(result){
                $.ajax({
                    url:'/njShoppingCar/njDelAll',
                    type:'POST',
                    async:true,
                    data:{
                        ids: objCodes
                    },
                    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        if(data){
                            parent.window.bootbox.alert("删除成功！");
                            window.location.reload();
                        }else{
                            parent.window.bootbox.alert("删除失败！");
                            window.location.reload();
                        }
                    }
                })
            }
        }
    });
}
//确认订单
function commitOrder() {
    //获得多选框选中的数据
    var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
    var objCodes = '';
    if(selData.length > 0)
    {
        for(var i=0;i<selData.length;i++)
        {
            objCodes += selData[i].shopingCarId + ',';
        }
        objCodes = objCodes.substring(0, objCodes.length-1)
        window.location.href = "/njSubmitOrder/njSubmitOrderPage?carIds="+objCodes;
    }else{
        alert("请至少选择一种商品")
    }
}
//总金额计算
function total() {
    //获得多选框选中的数据
    var selData = $('#tableid').bootstrapTable('getSelections');//选中的数据
    var productNums;
    var productPrice;
    var totalMoney = 0;
    if(selData.length > 0){
        for(var i=0;i<selData.length;i++){
            productNums = selData[i].productNum;
            productPrice = selData[i].productPrice;
            totalMoneyTmp =accMul(productNums,productPrice);
            totalMoney = accAdd(totalMoney,totalMoneyTmp);
        }
    }
    totalMoney += '.00'
    $("#totalMoney").text(totalMoney);
}
//乘法函数
function accMul(arg1,arg2){
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//加法函数
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0};
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0};
    m=Math.pow(10,Math.max(r1,r2));
    return (arg1*m+arg2*m)/m;
}
</script>
</body>
</html>

