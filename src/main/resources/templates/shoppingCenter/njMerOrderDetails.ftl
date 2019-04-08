<!DOCTYPE html>
<html>
<head>
    <title>订单详情</title>
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
                        <div>
                            <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">订单详情</span>
                        </div>
                        <form class="form-horizontal col-sm-12 m-t" method="post" style="padding-top: 10px;" id="addform">

                            <div class="form-group">
								<label class="col-sm-2 control-label">组织机构名称：</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="orgName" name="orgName" value="${busNjmerorder.orgName}" disabled="disabled">
								</div>

                                <label class="col-sm-2 control-label">结算总金额：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="totalSettlePrice" name="totalSettlePrice" value="${busNjmerorder.totalSettlePrice}" disabled="disabled">
                                </div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">联系人：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="contact" name="contact" value="${busNjmerorder.contact}" disabled="disabled">
                                </div>

                                <label class="col-sm-2 control-label">联系人电话：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="phone" name="phone" value="${busNjmerorder.phone}" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">联系人地址：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="addressZh" name="addressZh" value="${busNjmerorder.addressZh}" disabled="disabled">
                                </div>
                            </div>

                            <input type="hidden" id="orderId" name="merOrderId" value="${busNjmerorder.merOrderId}">

                        </form>
                        <div>
                            <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">商品详情</span>
                        </div>
                        <table class="table table-striped table-bordered table-hover table-condensed" style="margin-left: 18%;width: 64%;">
                            <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>商品品牌名称</th>
                                <th>商品分类名称</th>
                                <th>商品数量</th>
                                <th>商品单价</th>
                            </tr>
                            </thead>
                            <tbody>
							<#list busNjorderinfoList as busNjorderinfo>
                            <tr>
                                <td>${busNjorderinfo.productName}</td>
                                <td>${busNjorderinfo.productBrandName}</td>
                                <td>${busNjorderinfo.productCategoryName}</td>
                                <td>${busNjorderinfo.productNum}</td>
                                <td>${busNjorderinfo.productPrice}</td>
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


</script>
</body>
</html>

