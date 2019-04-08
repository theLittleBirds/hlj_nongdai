<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>上架审核</title>
    
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
						<label class="col-sm-2 control-label" for="productBrand">商品品牌：</label>
				    	<div class="col-sm-3">
					    	<select id="productBrand" name="productBrand" class="form-control" disabled="disabled">
	                            <option value="">--请选择--</option>
	                         	<#list brandList as brand>
	                         		<#if brand.value==cs.productBrand!''>
	                         			<option value="${brand.value}" selected="selected">${brand.descr}</option>
	                         		<#else>
	                         			<option value="${brand.value}" >${brand.descr}</option>
	                         		</#if>	
	                         	</#list>                                       
	                        </select>
                        </div>
						
						<label class="col-sm-2 control-label" for="productCategory">商品分类：</label>
				    	<div class="col-sm-3">
					    	<select id="productCategory" name="productCategory" class="form-control" disabled="disabled">
	                        	<option value="">--请选择--</option>
	                        	<#list categoryList as category>
	                        		<#if category.value==cs.productCategory!''>
	                        			<option value="${category.value}"  selected="selected">${category.descr}</option>
	                        		<#else>
	                         			<option value="${category.value}" >${category.descr}</option>
	                         		</#if>	
	                        	</#list>                                      
	                        </select>
                        </div>
				 	</div>				 	
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">商品名称：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="productName" name="productName" value="${cs.productName!''}" disabled="disabled">
						</div>
						
                   		<label class="col-sm-2 control-label">商品规格：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="standard" name="standard" value="${cs.standard!''}" disabled="disabled">kg/袋
						</div>											
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">显示价格：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="price" name="price" value="${cs.price!''}" disabled="disabled">元/袋
						</div>
						
				 		<label class="col-sm-2 control-label">结算价格：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="settlePrice" name="settlePrice" value="${cs.settlePrice!''}" disabled="disabled">元/袋
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">商品描述：</label>								
						<div class="col-sm-8">								
							<textarea class="form-control" id="productDesc" name="productDesc" rows="7" disabled="disabled">${cs.productDesc!''}</textarea>
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2"></label>							
						<div class="col-sm-3">		
							<img alt="封面图片" id="img1"  height="200" width="200" src="${cs.coverPic!''}">
						</div>
						
                   		<label class="col-sm-2"></label>							
						<div class="col-sm-3">	
							<img alt="详情图片" id="img2"  height="200" width="200" src="${cs.detailPic!''}">
						</div>
				 	</div>
				 	
				 	
				 	<input type="hidden" id="id" name="id" value="${cs.id!''}">
				 	
				 	 <div class="clearfix form-actions">
                        <div class="col-md-10 text-center">
                           <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                               <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                           </button>
                           <button class="btn btn-w-m btn-success" type="button" onclick="checkForm('1')">
                               <i class="ace-icon fa fa-check bigger-110"></i> 通过
                           </button>  
                           <button class="btn btn-w-m btn-success" type="button" onclick="checkForm('3')">
                               <i class="ace-icon fa fa-check bigger-110"></i> 驳回
                           </button>                          
                        </div>
                    </div> 
                    
                  </form> 
				</div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
function checkForm(status){
	var id = $("#id").val();
	window.location.href = "/njReview/njProductReviewInfo?id="+id+"&status="+status;
}

</script>
</body>
</html>

