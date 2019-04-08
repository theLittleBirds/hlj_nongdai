<!DOCTYPE html>
<html>
<head>
    <title>服务站订单详情-收货</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/layer/layer.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                	<div class="row" style="background-color: white;padding: 0px; margin-left: 0px;">
                        <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" >
                            <div>
                                <span style="font-weight: bold;font-size: 20px;margin-left: 0%;">订单详情</span>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="productBrand">购买者：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" value="${order.memberName}" readonly style="border: none;background-color: white;width: 100%;">
                                </div>
                                <label class="col-sm-2 control-label" for="productCategory">订单总金额：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" value="${order.totalPrice}" readonly style="border: none;background-color: white;width: 100%;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="productBrand">收货人姓名：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" value="${orderAddress.contact}" readonly style="border: none;background-color: white;width: 100%;">
                                </div>
                                <label class="col-sm-2 control-label" for="productCategory">收货人电话：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" value="${orderAddress.phone}" readonly style="border: none;background-color: white;width: 100%;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="productBrand">收货地址：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" value="${orderAddress.addressZh}" readonly style="border: none;background-color: white;width: 100%;">
                                </div>
                                <label class="col-sm-2 control-label" for="productCategory">付款凭证：</label>
                                <div class="col-sm-3">
                                    <table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
                                        <tbody id = "imageList">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <input type="hidden" id="hashKey" value="${order.payPic}">
                            <input type="hidden" id="orderId" name="orderId" value="${order.orderId}">
                        </form>
                        <div>
                            <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">商户列表</span>
                        </div>
                        <table class="table table-striped table-bordered table-hover table-condensed" style="text-align:center;margin-left: 10%;width: 73%;">
                            <thead>
                            <tr>
                                <th style="text-align: center">商户名</th>
                                <th style="text-align: center">总价</th>
                                <th style="text-align: center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
							<#list orgList as org>
                                <tr>
                                    <td>${org.fullCname}</td>
                                    <td>${org.totalPrice}</td>
                                    <td>
                                        <button class="btn btn-primary" type="button" onclick="productEdit('${org.orgId}',this)" ><i class="fa fa-search"'></i>查看</button>
                                        <#if merorderList??>
                                            <#if merorderList?size == orgList?size>
                                                <#list merorderList as ml>
                                                    <#if ml.orgId = org.orgId>
                                                        <#if ml.sendPic != '' &&  ml.receivePic == ''>
                                                        <button class="btn btn-primary" type="button" onclick="uploadImg('${org.orgId}',this)"><i class="fa fa-search"></i>上传收货凭证</button>
                                                        <#elseif  ml.sendPic != '' && ml.receivePic != ''>
                                                        <button class="btn btn-primary" type="button" onclick="lookImg('${org.orgId}',this)"><i class="fa fa-search"></i>查看收货凭证</button>
                                                        </#if>
                                                    </#if>
                                                </#list>
                                            <#else>
                                                <#list merorderList as ml>
                                                    <#if ml.orgId = org.orgId>
                                                        <#if ml.sendPic != '' &&  ml.receivePic == ''>
                                                        <button class="btn btn-primary" type="button" onclick="uploadImg('${org.orgId}',this)"><i class="fa fa-search"></i>上传收货凭证</button>
                                                        <#elseif  ml.sendPic != '' && ml.receivePic != ''>
                                                        <button class="btn btn-primary" type="button" onclick="lookImg('${org.orgId}',this)"><i class="fa fa-search"></i>查看收货凭证</button>
                                                        </#if>
                                                    <#else>
                                                    <button class="btn btn-primary" type="button" style="color: red">商家暂没发货</button>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        <#else>
                                            <button class="btn btn-primary" type="button" style="color: red">商家暂没发货</button>
                                        </#if>
                                    </td>
                                </tr>
							</#list>
                            </tbody>
                        </table>
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                                <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                    <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
//查看商家下面的订单
function productEdit(id){
    var orderId = $("#orderId").val();
    window.location.href = "/njSubmitOrder/njOrderInfoPage?orgId="+id+"&orderId="+orderId;
}
//上传收货凭证
function uploadImg(id){
    var orderId = $("#orderId").val();
    window.location.href = "/njSubmitOrder/uploadImg?orgId="+id+"&orderId="+orderId;
}
//查看收货凭证
function lookImg(id){
    var orderId = $("#orderId").val();
    window.location.href = "/njSubmitOrder/lookImg?orgId="+id+"&orderId="+orderId;
}
refreshMedia();
//查看主订单照片
function refreshMedia(){
    var hashKey = $("#hashKey").val();
    $.ajax({
        type: "POST",
        url: "/njSubmitOrder/njSubmitOrderKey",
        data: {"hashKey":hashKey,},
        error: function(request) {
        },
        success: function(data) {
            $("#imageList").empty();
            if(data != null){
                var html = "";
                for (var i = 0; i < data.length; i++) {
                    var remainder = i%4;
                    if(remainder == 0){
                        html = html + "<tr>";
                    }
                    html = html + "<td align='center'><div style='float:left;width:100%;'><img style='height:100px;width:160px;cursor:pointer;' src='"
                            + data[i].small + "' data-original='"+data[i].big +"'/></div><div style='float:left;width:100%;'></div></td>";
                    if(remainder == 3){
                        html = html + "</tr>";
                    }
                }
                var size = data.length%4;
                if(size == 1){
                    html = html + "<td></td><td></td><td></td></tr>";
                }else if(size == 2){
                    html = html + "<td></td><td></td></tr>";
                }else if(size == 3){
                    html = html + "<td></td></tr>";
                }
                $("#imageList").append(html);
            }
        }
    });
}
</script>
</body>
</html>

