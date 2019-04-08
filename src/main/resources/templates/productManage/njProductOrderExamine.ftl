<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>商品添加</title>
    
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix"> 
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
												<select class="form-control" name="productId" onchange="calculatePrice(this);" disabled="disabled">
				                                 	<option value="">--请选择--</option>
				                                 	<#list njProductList as product>
				                                 		<#if product.id==orderCs.id!''>
						                        			<option value="${product.id}"  selected="selected">${product.productBrandName}</option>
						                        		<#else>
						                         			<option value="${product.id}">${product.productBrandName}</option>
						                         		</#if>
				                                 	</#list>                                       
				                                </select>
				                                <#list njProductList as product>
								               		<input type="hidden" name="${product.id}" value="${product.standard}" readonly="readonly"/>
								               	</#list>  
											</td>
											<td height="30" width="300">
												<input type="text" name="standard" class="form-control" readonly="readonly"/>
											</td>
											<td height="30" width="300">
												<input type="text" name="productNum" class="form-control" value="${orderCs.productNum}" readonly="readonly"/>
											</td>
											<td>
											</td>
										</tr>
										</#list>					
									<#else>
										<tr>
											<td height="30" width="300">
												<select class="form-control" name="productId" onchange="calculatePrice(this);" disabled="disabled">
				                                 	<option value="">--请选择--</option>
				                                 	<#list njProductList as product>
				                                 		<option value="${product.id}">${product.productBrandName}</option>
				                                 	</#list>                                       
				                                </select>
				                                <#list njProductList as product>
								               		<input type="hidden" name="${product.id}" value="${product.standard}" readonly="readonly"/>
								               	</#list>  
											</td>
											<td height="30" width="300">
												<input type="text" name="standard" class="form-control" readonly="readonly"/>
											</td>
											<td height="30" width="300">
												<input type="text" name="productNum" class="form-control" readonly="readonly"/>
											</td>
											<td>
											</td>
										</tr>
									</#if>
								</tbody>
							</table>
						</div>
                    </div>
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">结算价格（单位：元）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderSettleprice" name="orderSettleprice" value="${cs.orderSettleprice!''}" readonly="readonly">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">显示价格（单位：元）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderPrice" name="orderPrice" value="${cs.orderPrice!''}" readonly="readonly">
						</div>
				 		
				 		<label class="col-sm-2 control-label">套餐名称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="orderName" name="orderName" value="${cs.orderName!''}" readonly="readonly">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">套餐描述：</label>								
						<div class="col-sm-8">								
							<textarea class="form-control" id="orderDesc" name="orderDesc" rows="7" readonly="readonly">${cs.orderDesc!''}</textarea>
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
							 <#if cs.status == 2>	
	                             <button class="btn btn-w-m btn-success" type="button" onclick="checkForm(1)">
	                                <i class="ace-icon fa fa-check bigger-110"></i> 上架
	                             </button> 
                             <#else>
	                             <button class="btn btn-w-m btn-success" type="button" onclick="checkForm(2)">
	                                <i class="ace-icon fa fa-check bigger-110"></i> 下架
	                             </button>  
                             </#if>                          
                         </div>
                    </div> 
                    
                  </form> 
				</div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function(){
	$("#dynamicTable tbody tr").each(function () { 
		var productNum = $(this).find("option:selected").val();
		var standardNum = $(this).find("input[name='"+ productNum +"']").val();
		$(this).find("input[name='standard']").val(standardNum);
	});
});

//商品规格 
function calculatePrice(_this){
	var productNum = $(_this).parent().find("select").val();
	var standardNum = $(_this).parent().find("input[name='"+ productNum +"']").val();
	$(_this).parent().parent().find("input[name='standard']").val(standardNum);
}

function checkForm(status){
	var id = $("#id").val();
	window.location.href = "/njTable/njProductOrderTableInfo?id="+id+"&status="+status;
}

</script>
</body>
</html>

