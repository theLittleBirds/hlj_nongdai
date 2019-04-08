<!DOCTYPE html>
<html>
<head>
    <title>子订单列表</title>
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
                    <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" >
                        <div>
                            <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">订单</span>
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
					</form>
                    <div>
                        <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">订单详情</span>
                    </div>
                    	<table id="tableid" class="table table-hover" style="margin-top: 5px;text-align: center">
                            <tr style="font-weight: bold ">
                                <td>商家名称</td>
                                <td>商品名称</td>
                                <td>商品类别</td>
                                <td>购买单价(元)</td>
                                <td>购买数量</td>
                            </tr>
                            <#list orderInfoList as clist>
                            <tr>
                                <td>${clist.merName}</td>
                                <td>${clist.productName}</td>
                                <td>${clist.productCategoryName}</td>
                                <td>${clist.productPrice}</td>
                                <td>${clist.productNum}</td>
                            </tr>
							</#list>
                    	</table>
						<div style="margin-top: 4%;margin-left: 43%">
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


<script type="text/javascript">
    refreshMedia();
    //查看照片
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
                    // if(data.length == 0){
                    //     view = null;
                    // }else if(view == null){
                    //     view = new Viewer(document.getElementById('imageList'), {
                    //         url: 'data-original'
                    //     });
                    // }else{
                    //     view.update();
                    // }
                }
            }
        });
    }
</script>
</body>
</html>

