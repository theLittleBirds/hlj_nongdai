<!DOCTYPE html>
<html>
<head>
    <title>添加收货地址</title>
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
                    <input type="hidden" id="carIds" value="${carIds}"/>
                    <form class="form-horizontal" method="post" style="padding-top: 10%;margin-left: 31%" id="njAddress">
                        <input type="hidden" id="addressId" name="addressId" value="${busNjAddress.addressId}"/>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span style="font-size: 17px">添加收货地址</span></label>
                            <div class="col-sm-3">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">收货人</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="contact" name="contact" placeholder="必填" value="${busNjAddress.contact}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="phone" name="phone" maxlength="11" placeholder="必填" value="${busNjAddress.phone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">收货地址</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="addressZh" name="addressZh" placeholder="请填写详情的收货地址" value="${busNjAddress.addressZh}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否设为默认</label>
                            <div class="col-sm-3">
                                <label class="custom-control custom-radio">
                                    <#if busNjAddress.isDefault == ''>
                                        <input type="radio" checked='checked' name="isDefault" class="custom-control-input" id="isDefault"  value=1>是
                                        <input type="radio" name="isDefault" class="custom-control-input" id="isDefault"  value=2>否
                                    </#if>
                                    <#if busNjAddress.isDefault == '1'>
                                        <input type="radio" checked='checked' name="isDefault" class="custom-control-input" id="isDefault"  value=1>是
                                        <input type="radio" name="isDefault" class="custom-control-input" id="isDefault"  value=2>否
                                    </#if>
                                    <#if busNjAddress.isDefault == '2'>
                                        <input type="radio"  name="isDefault" class="custom-control-input" id="isDefault"  value=1>是
                                        <input type="radio" checked='checked' name="isDefault" class="custom-control-input" id="isDefault"  value=2>否
                                    </#if>
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"></label>
                            <div class="col-sm-3">
                                <button class="btn btn-w-m" style="background-color: #85c360;width: 160px;" type="button" onclick="insertAddress()">
                                    <i class="ace-icon fa fa-reply bigger-110"></i>  确认
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

    //添加收货地址
    function insertAddress() {
        var carIds = $("#carIds").val();//携带购物车id集合
        var contact =$("#contact").val();
        var phone =$("#phone").val();
        var addressZh =$("#addressZh").val();
        if(contact == '' || contact == null){
            alert("请输入收货人姓名！");
            return;
        }
        if(phone == '' || phone == null){
            alert("请输入收货人电话！");
            return;
        }
        if(addressZh == '' || addressZh == null){
            alert("请输入收货地址！");
            return;
        }
        $.ajax({
            url: '/njAddress/njAddAddress',
            type: 'POST',
            async: true,
            data: $("#njAddress").serialize(),
            success:function(data){
                parent.window.bootbox.alert(data.msg);
                window.location.href = "/njSubmitOrder/njSubmitOrderPage?carIds="+carIds;
            }
        })
    }
</script>
</body>
</html>

