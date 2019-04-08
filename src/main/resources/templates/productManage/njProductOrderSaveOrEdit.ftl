<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>商品添加</title>
    
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
	<script src="/admin/js/timeOut.js"></script>
	<script src="/resource/bootbox/bootbox.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix"> 
                	<input type="hidden" id="orderCsHidden" value="${njProductList?size}"/>
                    <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addform">	
                    <div class="form-group">
                    	<label class="col-sm-2 control-label">套餐商品：</label>
	                    <div class="col-sm-10">
							<table id="dynamicTable">
								<thead>
									<tr>
										<td height="30" width="300" bgcolor="#CCCCCC">商品名称</td>
										<td height="30" width="300" bgcolor="#CCCCCC">商品规格（单位：kg/袋）</td>
										<td height="30" width="300" bgcolor="#CCCCCC">数量（单位：袋）</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<#if orderCs?? && (orderCs?size > 0)>	
										<#list orderCs as orderCs>
										<tr>
											<td height="30" width="300">
												<select class="form-control" name="productId" onchange="calculatePrice(this);">
				                                 	<option value="">--请选择--</option>
				                                 	<#list njProductList as product>
				                                 		<#if product.id==orderCs.id!''>
						                        			<option value="${product.id}"  selected="selected">${product.productName}</option>
						                        		<#else>
						                         			<option value="${product.id}">${product.productName}</option>
						                         		</#if>
				                                 	</#list>                                       
				                                </select>
				                                <#list njProductList as product>
								               		<input type="hidden" name="${product.id}" value="${product.standard}"/>
								               	</#list>  
											</td>
											<td height="30" width="300">
												<input type="text" name="standard" class="form-control" readonly="readonly"/>
											</td>
											<td height="30" width="300">
												<input type="text" name="productNum" class="form-control" value="${orderCs.productNum}"/>
											</td>
											<td>
												<input type="button" id="Button2" class="btn btn-primary"  onClick="deltr(this)" value="删行">
											</td>
										</tr>
										</#list>					
									<#else>
										<tr>
											<td height="30" width="300">
												<select class="form-control" name="productId" onchange="calculatePrice(this);">
				                                 	<option value="">--请选择--</option>
				                                 	<#list njProductList as product>
				                                 		<option value="${product.id}">${product.productName}</option>
				                                 	</#list>                                       
				                                </select>
				                                <#list njProductList as product>
								               		<input type="hidden" name="${product.id}" value="${product.standard}"/>
								               	</#list>  
											</td>
											<td height="30" width="300">
												<input type="text" name="standard" class="form-control" readonly="readonly"/>
											</td>
											<td height="30" width="300">
												<input type="text" name="productNum" class="form-control"/>
											</td>
											<td>
												<input type="button" id="Button2" class="btn btn-primary"  onClick="deltr(this)" value="删行">
											</td>
										</tr>
									</#if>
								</tbody>
							</table>
							<input id="btn_addtr" type="button" class="btn btn-primary" value="增行">
						</div>
                    </div>
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">结算价格（单位：元）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderSettleprice" name="orderSettleprice" value="${cs.orderSettleprice!''}" readonly="readonly">
						</div>
						<div class="col-sm-3">								
							<button class="btn btn-primary" type="button" onclick="orderCalculate();"><i class="fa fa-search"></i> 计算结算价格</button>
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">显示价格（单位：元）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderPrice" name="orderPrice" value="${cs.orderPrice!''}">
						</div>
				 		
				 		<label class="col-sm-2 control-label">套餐名称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderName" name="orderName" value="${cs.orderName!''}">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">套餐描述：</label>								
						<div class="col-sm-8">								
							<textarea class="form-control" id="orderDesc" name="orderDesc" rows="7">${cs.orderDesc!''}</textarea>
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">封面图片：</label>								
						<div class="col-sm-3">								
							<input type="file" id="coverPicFile" name="coverPicFile" >
						</div>
						
                   		<label class="col-sm-2 control-label">详情图片：</label>								
						<div class="col-sm-3">								
							<input type="file" id="detailPicFile" name="detailPicFile" >
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2"></label>							
						<div class="col-sm-3">		
							<#if cs.coverPic!'' != ''>						
								<img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${cs.coverPic!''}">
							<#else>
								<img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${cs.coverPic!''}" hidden="hidden">
							</#if>	
						</div>
						
                   		<label class="col-sm-2"></label>							
						<div class="col-sm-3">	
							<#if cs.detailPic!'' != ''>							
								<img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${cs.detailPic!''}">
							<#else>
								<img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${cs.detailPic!''}" hidden="hidden">
							</#if>	
						</div>
				 	</div>
				 	
				 	
				 	<input type="hidden" id="id" name="id" value="${cs.id!''}">
				 	
				 	 <div class="clearfix form-actions">
                           <div class="col-md-10 text-center">
                           <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                               <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                           </button>

                           <button class="btn btn-w-m btn-success" type="button" onclick="checkForm()">
                               <i class="ace-icon fa fa-check bigger-110"></i> 保存
                           </button>                            
                           </div>
                    </div> 
                    
                  </form> 
				</div>
            </div>
        </div>
    </div>
</div>
<table id="tab11" style="display: none">
	<tbody>
		<tr>
			<td height="30" width="300">
				<select class="form-control" name="productId" onchange="calculatePrice(this);">
                	<option value="">--请选择--</option>
                	<#list njProductList as product>
                		<option value="${product.id}">${product.productName}</option>
                	</#list>                                       
                </select>
                <#list njProductList as product>
               		<input type="hidden" name="${product.id}" value="${product.standard}"/>
               	</#list> 
			</td>
			<td height="30" width="300">
				<input type="text" name="standard" class="form-control" readonly="readonly"/>
			</td>
			<td height="30" width="300">
				<input type="text" name="productNum" class="form-control"/>
			</td>
			<td>
				<input type="button" id="Button1" class="btn btn-primary" onClick="deltr(this)" value="删行">
			</td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">
$(function(){
	var orderCsHidden = $("#orderCsHidden").val();
	if(orderCsHidden<=0){
		parent.window.bootbox.alert("请先添加商品或审核通过商品");
		window.history.go(-1);
	}
})
$(function(){
	$("#dynamicTable tbody tr").each(function () { 
		var productNum = $(this).find("option:selected").val();
		var standardNum = $(this).find("input[name='"+ productNum +"']").val();
		$(this).find("input[name='standard']").val(standardNum);
	});
});

$(function () {
	var show_count = 20;   //要显示的条数
	var count = 1;    //递增的开始值，这里是你的ID
	$("#btn_addtr").click(function () {
 
		var length = $("#dynamicTable tbody tr").length;
		//alert(length);
		if (length < show_count)    //点击时候，如果当前的数字小于递增结束的条件
		{
			$("#tab11 tbody tr").clone().appendTo("#dynamicTable tbody");   //在表格后面添加一行
			//changeIndex();//更新行号
		}
	});
 
 
});
/* function changeIndex() {
	var i = 1;
	$("#dynamicTable tbody tr").each(function () { //循环tab tbody下的tr
		$(this).find("input[name='NO']").val(i++);//更新行号
	});
} */
 
function deltr(opp) {
	var length = $("#dynamicTable tbody tr").length;
	//alert(length);
	if (length <= 1) {
		parent.window.bootbox.alert("至少保留一行");
	} else {
		$(opp).parent().parent().remove();//移除当前行
		//changeIndex();
	}
}

//商品规格 
function calculatePrice(_this){
	var productNum = $(_this).parent().find("select").val();
	var standardNum = $(_this).parent().find("input[name='"+ productNum +"']").val();
	$(_this).parent().parent().find("input[name='standard']").val(standardNum);
}

//计算结算价格
function orderCalculate(){
	var productArray = [];
	var flag = true;
	$("#dynamicTable tbody tr").each(function () {
		//判断不过不计算价格
		if(!flag){
			return;
		}
		
		var productId = $(this).find("select").val();
		if(!productId){
			flag = false;
			parent.window.bootbox.alert("请选择商品名称");
			return;
		}
		//判断不过不计算价格
		if(!flag){
			return;
		}
		
		var productNum = $(this).find("input[name='productNum']").val();
		if(!productNum){
			flag = false;
			parent.window.bootbox.alert("请选择商品数量");
			return;
		}
		//判断不过不计算价格
		if(!flag){
			return;
		}
	
		var arrayObj = {"productId":productId, "productNum":productNum};
		productArray.push(arrayObj);
	});
	//判断不过不计算价格
	if(!flag){
		return;
	}
	
	$.ajax({
	    url: '/njTable/njOrderCalculate',
	    type: 'POST', 
	    contentType: "application/json",
	    async: true,
	    data: JSON.stringify(productArray),
	    dataType: 'json', 
	    success:function(data){
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		//计算结算价格 
		    	$("#orderSettleprice").val(data.calculate);
	    	}
	    }
	})
}

function checkForm(){
	var orderSettleprice = $("#orderSettleprice").val();
	var orderPrice = $("#orderPrice").val();
	var orderName = $("#orderName").val();
	var orderDesc = $("#orderDesc").val();
	var coverPicFile = $("#coverPicFile").val();
	var detailPicFile = $("#detailPicFile").val();
	//获取图片的src的值
	var coverPicImg = $("#coverPicImg").attr("src");
	var detailPicImg = $("#detailPicImg").attr("src");
	if(orderSettleprice == ""){
		parent.window.bootbox.alert("结算价格不能为空");
		return ;
	}
	if(orderPrice == ""){
		parent.window.bootbox.alert("显示价格不能为空");
		return ;
	}
	if(orderName == ""){
		parent.window.bootbox.alert("套餐名称 不能为空");
		return ;
	}	
	if(orderDesc == ""){
		parent.window.bootbox.alert("套餐描述不能为空");
		return ;
	}
	if(coverPicFile == "" && coverPicImg == ""){
		parent.window.bootbox.alert("请上传封面图片");
		return ;
	}
	if(detailPicFile == "" && detailPicImg == ""){
		parent.window.bootbox.alert("请上传详情图片");
		return ;
	}
	
	var flag = true;
	var formData = new FormData($("#addform")[0]); 
	var orderProductinfo = [];
	$("#dynamicTable tbody tr").each(function () { 
		//判断不过不提交
		if(!flag){
			return;
		}
		
		var productId = $(this).find("select").val();
		if(!productId){
			flag = false;
			parent.window.bootbox.alert("请选择商品名称");
			return;
		}
		//判断不过不提交
		if(!flag){
			return;
		}
		
		var productNum = $(this).find("input[name='productNum']").val();
		if(!productNum){
			flag = false;
			parent.window.bootbox.alert("请选择商品数量");
			return;
		}
		//判断不过不提交
		if(!flag){
			return;
		}
		
		var productNum = $(this).find("input[name='productNum']").val();
		if(!productNum){
			flag = false;
			parent.window.bootbox.alert("请选择商品数量");
			return;
		}
		//判断不过不提交
		if(!flag){
			return;
		}
	
		var arrayObj = {"productId":productId, "productNum":productNum};
		orderProductinfo.push(arrayObj);
	})
	//判断不过不提交
	if(!flag){
		return;
	}
	
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存中</span>",
		closeButton: false,
		className: "submitPrimaryModal",
	});
	
	formData.append('orderProductinfo', JSON.stringify(orderProductinfo));
	$.ajax({
	    url: "/njTable/njOrderSaveOrEdit", 
        type: "POST", 
        data: formData, 
        async: true, 
        cache: false, 
        contentType: false, 
        processData: false, 
	    error: function(request) {
	    	parent.removeBootBox("submitPrimaryModal");
	    },
	    success: function(data) {
	    	parent.removeBootBox("submitPrimaryModal");
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code==200){
	    		window.history.go(-1);
	    	}
	    }
	});
}

</script>
</body>
</html>

