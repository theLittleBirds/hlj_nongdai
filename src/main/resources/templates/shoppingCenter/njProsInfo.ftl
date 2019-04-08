<!DOCTYPE html>
<html>
<head>
    <title>农资农具详情中心</title>
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
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <#--单品详情-->
                    <#if type?? && (type == 1) >
                         <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addShoppingCar1">
                             <div class="form-group">
                                <label class="col-sm-2 control-label" for="productBrand">商品品牌：</label>
                                <div class="col-sm-3">
                                    <#list brandList as brand>
                                        <#if brand.value==njProduct.productBrand!''>
                                            <input type="text" class="form-control" value="${brand.descr}" readonly style="border: none;background-color: white;width: 100%;">
                                        </#if>
                                    </#list>
                                 </div>

                                 <label class="col-sm-2 control-label" for="productCategory">商品分类：</label>
                                 <div class="col-sm-3">
                                    <#list categoryList as category>
                                        <#if category.value==njProduct.productCategory!''>
                                            <input type="text" class="form-control" value="${category.descr}" readonly style="border: none;background-color: white;width: 100%;">
                                        </#if>
                                    </#list>
                                </div>
                            </div>

                            <div class="form-group">
                            <label class="col-sm-2 control-label">商品名称：</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="productName" name="productName" value="${njProduct.productName!''}" readonly style="border: none;background-color: white">
                            </div>

                            <label class="col-sm-2 control-label">商品规格（单位：kg/袋）：</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="standard" name="standard" value="${njProduct.standard!''}" readonly style="border: none;background-color: white">
                            </div>
                        </div>

                             <div class="form-group">
                                <label class="col-sm-2 control-label">显示价格（单位：元/袋）：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="price" name="price" value="${njProduct.price!''}" readonly style="border: none;background-color: white">
                                </div>

                                <#--<label class="col-sm-2 control-label">结算价格（单位：元/袋）：</label>-->
                                <#--<div class="col-sm-3">-->
                                    <#--<input type="text" class="form-control" id="settlePrice" name="settlePrice" value="${njProduct.settlePrice!''}" readonly style="border: none;background-color: white">-->
                                <#--</div>-->
                            </div>

                             <div class="form-group">
                                <label class="col-sm-2 control-label">商品描述：</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" id="productDesc" name="productDesc" rows="7" readonly style="border: none;background-color: white;resize:none">${njProduct.productDesc!''}</textarea>
                                </div>
                             </div>

                             <div class="form-group">
                                <label class="col-sm-2 control-label">封面图片：</label>
                                <div class="col-sm-3">
                                <#if njProduct.coverPic!'' != ''>
                                    <img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${njProduct.coverPic!''}">
                                <#else>
                                    <img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${njProduct.coverPic!''}" hidden="hidden">
                                </#if>
                                </div>

                                    <label class="col-sm-2 control-label">详情图片：</label>
                                <div class="col-sm-3">
                                <#if njProduct.detailPic!'' != ''>
                                    <img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${njProduct.detailPic!''}">
                                <#else>
                                    <img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${njProduct.detailPic!''}" hidden="hidden">
                                </#if>
                                </div>
                             </div>

                            <input type="hidden" id="id" name="id" value="${njProduct.id!''}">

                        <div class="clearfix form-actions col-md-10 text-center">
                            <label class="col-sm-4 control-label">采购数量：</label>
                            <div class="col-sm-3">
                                <input class="form-control" id="productNum1" type="number" min="1" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                            </div>
                            <div class="col-sm-3">
                                    <button class="btn btn-w-m" style="background-color: #85c360" type="button" onclick="addCar1('${njProduct.id}')">
                                        <i class="ace-icon fa fa-reply bigger-110"></i>  加入购物车
                                    </button>
                                    <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                        <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                                    </button>
                            </div>


                        </div>

                        </form>
                    </#if>

                    <#--套餐详情-->
                    <#if type?? && (type == 2) >
                         <input type="hidden" id="orderCsHidden" value="${njProductList?size}"/>
                         <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addShoppingCar2">

                             <div class="form-group">
                                <label class="col-sm-2 control-label">套餐商品：</label>
                                <div class="col-sm-10">
                                    <table id="dynamicTable">
                                        <thead>
                                        <tr>
                                            <th height="30" width="300" bgcolor="#CCCCCC">商品名称</th>
                                            <th height="30" width="300" bgcolor="#CCCCCC">商品规格（单位：kg/袋）</th>
                                            <th height="30" width="300" bgcolor="#CCCCCC">数量（单位：袋）</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if orderCs?? && (orderCs?size > 0)>
                                            <#list orderCs as orderCs>
                                            <tr>
                                                <td height="30" width="300">
                                                        <#list njProductList as product>
                                                            <#if product.id==orderCs.id!''>
                                                                <input value="${product.productName}" readonly style="border: none;background-color: white;width: 100%">
                                                            </#if>
                                                        </#list>
                                                    <#list njProductList as product>
                                                        <input type="hidden" name="${product.id}" value="${product.standard}"/>
                                                    </#list>
                                                </td>
                                                <td height="30" width="300">
                                                    <input type="text" name="standard" class="form-control" value="${orderCs.standard}" readonly style="border: none;background-color: white"/>
                                                </td>
                                                <td height="30" width="300">
                                                    <input type="text" name="productNum" class="form-control" value="${orderCs.productNum}"readonly style="border: none;background-color: white"/>
                                                </td>
                                            </tr>
                                            </#list>
                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                             <div class="form-group">
                                 <label class="col-sm-2 control-label">套餐名称：</label>
                                 <div class="col-sm-3">
                                     <input type="text" class="form-control" id="orderName" name="orderName" value="${cs.orderName!''}" readonly style="border: none;background-color: white">
                                 </div>
                                 <label class="col-sm-2 control-label">显示价格（单位：元）：</label>
                                 <div class="col-sm-3">
                                     <input type="text" class="form-control" id="orderPrice" name="orderPrice" value="${cs.orderPrice!''}" readonly style="border: none;background-color: white">
                                 </div>
                             </div>

                            <#--<div class="form-group">-->
                                <#--<label class="col-sm-2 control-label">结算价格（单位：元）：</label>-->
                                <#--<div class="col-sm-3">-->
                                    <#--<input type="text" class="form-control" id="orderSettleprice" name="orderSettleprice" value="${cs.orderSettleprice!''}" readonly="readonly">-->
                                <#--</div>-->
                            <#--</div>-->
                            <div class="form-group">
                                <label class="col-sm-2 control-label">套餐描述：</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" id="orderDesc" name="orderDesc" rows="7" readonly style="border: none;background-color: white;resize:none">${cs.orderDesc!''}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面图片：</label>
                                <div class="col-sm-3">
                                <#if cs.coverPic!'' != ''>
                                    <img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${cs.coverPic!''}">
                                <#else>
                                    <img alt="封面图片" id="coverPicImg"  height="200" width="200" src="${cs.coverPic!''}" hidden="hidden">
                                </#if>
                                </div>

                                <label class="col-sm-2 control-label">详情图片：</label>
                                <div class="col-sm-3">
                                <#if cs.detailPic!'' != ''>
                                    <img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${cs.detailPic!''}">
                                <#else>
                                    <img alt="详情图片" id="detailPicImg"  height="200" width="200" src="${cs.detailPic!''}" hidden="hidden">
                                </#if>
                                </div>
                            </div>


                            <input type="hidden" id="id" name="id" value="${cs.id!''}">

                           <div class="clearfix form-actions col-md-10 text-center">
                               <label class="col-sm-4 control-label">采购数量：</label>
                               <div class="col-sm-3">
                                   <input class="form-control" id="productNum2" type="number" min="1" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" style="text-align: center">
                               </div>
                               <div class="col-sm-3">
                                       <button class="btn btn-w-m" style="background-color: #85c360" type="button" onclick="addCar2('${cs.id}')">
                                           <i class="ace-icon fa fa-reply bigger-110"></i>  加入购物车
                                       </button>
                                       <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                           <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                                       </button>
                               </div>
                        </div>
                        </form>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //单品加入购物车
    function addCar1(njProductId) {
        var reg = /^[1-9]\d*$/;
        var productNum = $("#productNum1").val();
        if(!reg.test(productNum)){
            $("#productNum1").val(1)
            parent.window.bootbox.alert("数量最少为1");
            return;
        }
        $.ajax({
            url: '/njShoppingCar/njAddShoppingCar',
            type: 'POST',
            async: true,
            data: {"id" : njProductId,"productNum":productNum},
            success:function(data){
                parent.window.bootbox.alert(data.msg);
            }
        })
    }
    //套餐加入购物车
    function addCar2(njProductId) {
        var reg = /^[1-9]\d*$/;
        var productNum = $("#productNum2").val();
        if(!reg.test(productNum)){
            $("#productNum2").val(1)
            parent.window.bootbox.alert("数量最少为1");
            return;
        }
        $.ajax({
            url: '/njShoppingCar/njAddShoppingCar',
            type: 'POST',
            async: true,
            data: {"id" : njProductId,"productNum":productNum},
            success:function(data){
                parent.window.bootbox.alert(data.msg);
            }
        })
    }
</script>
</body>
</html>

