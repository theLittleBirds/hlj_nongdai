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
									<input type="text" class="form-control" id="orgName" name="orgName" value="${njorder.orgName}" disabled="disabled">
								</div>

								<label class="col-sm-2 control-label">用户名称：</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="memberName" name="memberName" value="${njorder.memberName}" disabled="disabled">
								</div>
							</div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">显示总金额：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="totalPrice" name="totalPrice" value="${njorder.totalPrice}" disabled="disabled">
                                </div>

                                <label class="col-sm-2 control-label">结算总金额：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="totalSettlePrice" name="totalSettlePrice" value="${njorder.totalSettlePrice}" disabled="disabled">
                                </div>
                            </div>

                            <input type="hidden" id="orderId" name="orderId" value="${njorder.orderId}">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">付款凭证：</label>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
                                            <tbody id = "imageList">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                        </form>
                        <div>
                            <span style="font-weight: bold;font-size: 20px;margin-left: 1%;">商户列表</span>
                        </div>
                        <table class="table table-striped table-bordered table-hover table-condensed" style="margin-left: 18%;width: 64%;">
                            <thead>
                            <tr>
                                <th>商户名</th>
                                <th>总价</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
							<#list orgList as org>
                            <tr>
                                <td>${org.fullCname}</td>
                                <td>${org.totalPrice}</td>
                                <td>
									<button class="btn btn-primary" type="button" onclick="productEdit('${org.orgId}',this)" ><i class="fa fa-search"'></i>查看</button>
									<#if org.flag == "null" || org.flag == ""><#--空表示待付定金-->
                                        <button class="btn btn-primary" type="button" onclick="uploadImg('${org.orgId}','1',this)"><i class="fa fa-search"></i>上传定金支付凭证</button>
									</#if>
                                    <#if org.flag == "2"><#--2表示待付尾款-->
                                        <button class="btn btn-primary" type="button" onclick="uploadImg('${org.orgId}','2',this)"><i class="fa fa-search"></i>上传尾款支付凭证</button>
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

refreshMedia();
function refreshMedia(){
    var orderId = $("#orderId").val();
    $.ajax({
        type: "POST",
        url: "/njServiceOrder/orderImg",
        data: {"orderId":orderId,},
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
                if(data.length == 0){
                    view = null;
                }else if(view == null){
                    view = new Viewer(document.getElementById('imageList'), {
                        url: 'data-original'
                    });
                }else{
                    view.update();
                }
            }
        }
    });
}

function productEdit(id){
    var orderId = $("#orderId").val();
    window.location.href = "/njServiceOrder/njOrderInfoPage?orgId="+id+"&orderId="+orderId;
}
function uploadImg(id,flag){
    var orderId = $("#orderId").val();
    window.location.href = "/njServiceOrder/uploadImg?orgId="+id+"&orderId="+orderId+"&flag="+flag;
}

</script>
</body>
</html>

