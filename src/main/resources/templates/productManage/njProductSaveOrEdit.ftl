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
                    <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addform">	
                    
                   	<div class="form-group">
						<label class="col-sm-2 control-label" for="productBrand">商品品牌：</label>
				    	<div class="col-sm-3">
					    	<select id="productBrand" name="productBrand" class="form-control">
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
					    	<select id="productCategory" name="productCategory" class="form-control">
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
							<input type="text" class="form-control" id="productName" name="productName" value="${cs.productName!''}">
						</div>
						
                   		<label class="col-sm-2 control-label">商品规格（单位：kg/袋）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="standard" name="standard" value="${cs.standard!''}">
						</div>											
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">显示价格（单位：元/袋）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="price" name="price" value="${cs.price!''}">
						</div>
						
				 		<label class="col-sm-2 control-label">结算价格（单位：元/袋）：</label>								
						<div class="col-sm-3">								
							<input type="text" class="form-control" id="settlePrice" name="settlePrice" value="${cs.settlePrice!''}">
						</div>
				 	</div>
				 	
				 	<div class="form-group">
				 		<label class="col-sm-2 control-label">商品描述：</label>								
						<div class="col-sm-8">								
							<textarea class="form-control" id="productDesc" name="productDesc" rows="7">${cs.productDesc!''}</textarea>
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


<script type="text/javascript">
function checkForm(){
	var productBrand = $("#productBrand").val();
	var productCategory = $("#productCategory").val();
	var productName = $("#productName").val();
	var standard = $("#standard").val();
	var price = $("#price").val();
	var settlePrice = $("#settlePrice").val();
	var productDesc = $("#productDesc").val();
	var coverPicFile = $("#coverPicFile").val();
	var detailPicFile = $("#detailPicFile").val();
	
	//获取图片的src的值
	var coverPicImg = $("#coverPicImg").attr("src");
	var detailPicImg = $("#detailPicImg").attr("src");
	if(productBrand == ""){
		parent.window.bootbox.alert("请选择商品品牌");
		return ;
	}
	if(productCategory == ""){
		parent.window.bootbox.alert("请选择商品分类");
		return ;
	}
	if(productName == ""){
		parent.window.bootbox.alert("商品名称不能为空");
		return ;
	}
	if(standard == ""){
		parent.window.bootbox.alert("商品规格不能为空");
		return ;
	}	
	if(price == ""){
		parent.window.bootbox.alert("显示价格不能为空");
		return ;
	}
	if(settlePrice == ""){
		parent.window.bootbox.alert("结算价格不能为空");
		return ;
	}
	if(productDesc == ""){
		parent.window.bootbox.alert("商品描述不能为空");
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
	
	parent.window.bootbox.dialog({
		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存中</span>",
		closeButton: false,
		className: "submitPrimaryModal",
	});
 	var formData = new FormData($("#addform")[0]); 
	$.ajax({
	    url: "/njProduct/njSaveOrEdit", 
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

