<!DOCTYPE html>
<html>
<head>
    <title>购物车</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/moment.js"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/layer/layer.min.js"></script>
    <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  	<script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  	<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
	<script src="/admin/js/timeOut.js"></script>
	<script src="/resource/bootbox/bootbox.js"></script>
    <script src="/resource/qiniu/qiniu.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                	<div class="row" style="background-color: white;padding: 10px;margin-left: 0px;">
                        <div>
                            <span style="font-weight: bold;font-size: 20px;">商品</span>
                        </div>
                        <input type="hidden" id="carIds" value="${carIds}"/>
                        <input type="hidden" name="token" id="token" value="${token}">
                        <table id="tableid" class="table table-hover" style="margin-top: 5px;">
                            <tr style="font-weight: bold ">
                                <td>品牌</td>
                                <td>商品名称</td>
                                <td>类别</td>
                                <td>单价(元)</td>
                                <td>数量</td>
                            </tr>
                            <#list carList as clist>
                            <tr>
                                <td>${clist.productBrandName}</td>
                                <td>${clist.productName}</td>
                                <td>${clist.productCategoryName}</td>
                                <td>${clist.productPrice}</td>
                                <td>${clist.productNum}</td>
                            </tr>
                            </#list>
                        </table>
                        <div>
                            <div>
                                <span style="font-weight: bold;font-size: 20px;margin-top: 10px;">收货地址</span>
                                <a style="margin-left: 62.3%;color: #85c360;cursor:pointer;text-decoration: none" onclick="toAddress()">新增收货地址</a>
                            </div>
                            <input type="hidden" id="haveAddress" value="${size}">
                            <#if addressList?? && (addressList?size > 0) >
                                <div>
                                    <table id="address" class="table table-hover" style="margin-top: 5px;border: none">
                                        <#list addressList as alist>
                                            <tr>
                                                <td>${alist.contact}</td>
                                                <td>${alist.phone}</td>
                                                <td>${alist.addressZh}</td>
                                                <td>
                                                    <a class="btn btn-primary" style="cursor:pointer;text-decoration: none" onclick="updAddress('${alist.addressId}')">修改</a>
                                                    <a class="btn btn-primary" style="cursor:pointer;text-decoration: none" onclick="delAddress('${alist.addressId}')">删除</a>
                                                    <#if alist.isDefault == '2'>
                                                        <a class="btn btn-primary" style="cursor:pointer;text-decoration: none" onclick="setDefAddress('${alist.addressId}')">设为默认</a>
                                                    </#if>
                                                </td>
                                            </tr>
                                            <#if alist.isDefault == '1'>
                                                <input type="hidden" id="addressDefId" value="${alist.addressId}" >
                                             </#if>
                                        </#list>
                                    </table>
                                </div>
                            <#else>
                                <span style="margin-left: 41%;margin-top: 9%;position: absolute">暂无收货地址，请添加！</span>
                            </#if>
                        </div>
                    <#if size == '0'>
                        <div style="margin-top: 20%;">
                    <#else>
                        <div>
                    </#if>
                            <div>
                                <span style="font-weight: bold;font-size: 20px;">付款凭证</span>
                            </div>
                            <div>
                                <button class="btn btn-primary" type="button" onclick="file_click()" style="margin-left: 11%;margin-top: 3%">上传</button>
                                <div style="display: none;">
                                    <input type="file" id="imageFile" accept="image/jpeg,image/png" multiple="multiple" onchange="postimage()">
                                </div>
                            </div>
                            <div class="form-group">
                                <table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
                                    <tbody id = "imageList">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <#--<#if size == '0'>-->
                            <#--<div class="form-group" style="position:absolute;margin-top: 20%; margin-left: 60%;">-->
                        <#--<#else>-->
                            <div class="form-group" style="position:absolute;margin-top: 3%; margin-left: 60%;">
                        <#--</#if>-->
                                <div class="col-sm-0"></div>
                                <div class="col-sm-12">
                                    <div class="col-sm-8" style="font-size: 16px;color: red">
                                        <label>合计：￥</label>
                                        <span>${totalMoney}</span>
                                    </div>
                                    <div class="col-sm-4">
                                        <button class="btn btn-w-m" style="background-color: #85c360; margin-top: -10px;" type="button" onclick="submitOrder()">
                                            <i class="ace-icon fa fa-reply bigger-110"></i>  提交订单
                                        </button>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var hashKey;//七牛存储图片的hash值
//去新增地址页面
function toAddress() {
    var carIds = $("#carIds").val();
    window.location.href = "/njAddress/njToAddress?carIds="+carIds;
}
//修改地址
function updAddress(id) {
    var carIds = $("#carIds").val();
    window.location.href = "/njAddress/njToUpdAddress?id="+id+"&carIds="+carIds;
}
//删除地址
function delAddress(id){
    parent.window.bootbox.confirm({
        buttons: {
            cancel: {
                label: '否'
            },
            confirm: {
                label: '是'
            }
        },
        message: '确认要删除该收货地址吗？',
        callback: function(result) {
            if(result){
                $.ajax({
                    url:'/njAddress/njDeleteAddress',
                    type:'POST',
                    async:true,
                    data:{
                        id: id
                    },
                    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        if(data){
                            parent.window.bootbox.alert("删除成功！");
                            window.location.reload();
                        }else{
                            parent.window.bootbox.alert("删除失败！");
                            window.location.reload();
                        }
                    }
                })
            }
        }
    });
}
//设置为默认地址
function setDefAddress(id){
    parent.window.bootbox.confirm({
        buttons: {
            cancel: {
                label: '否'
            },
            confirm: {
                label: '是'
            }
        },
        message: '确认要将该收货地址设置为默认吗？',
        callback: function(result) {
            if(result){
                $.ajax({
                    url:'/njAddress/njsetDefAddress',
                    type:'POST',
                    async:true,
                    data:{
                        id: id
                    },
                    dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        if(data){
                            parent.window.bootbox.alert("设置成功！");
                            window.location.reload();
                        }else{
                            parent.window.bootbox.alert("设置失败！");
                            window.location.reload();
                        }
                    }
                })
            }
        }
    });

}
//确认订单
function submitOrder() {
    var carIds = $("#carIds").val();//商品集合
    var haveAddress = $("#haveAddress").val();
    var addressId = $("#addressDefId").val();//选择收货地址的id
    if(haveAddress == '0'){
        parent.window.bootbox.alert("请先添加收货地址！");
    }else{
        var files=document.getElementById("imageFile");
        var file=files.files;//每一个file对象对应一个文件。
        if(file.length == 0){
            parent.window.bootbox.alert("请上传付款凭证图片");
        }else{
            window.location.href = "/njSubmitOrder/njSubmitOrders?carIds="+carIds+"&addressId="+addressId+"&hashKey="+hashKey;
        }
    }

}
//点击上传事件
function file_click(){
    $("#imageFile").click();
}

//向七牛服务器上传照片
function postimage(){
    parent.window.bootbox.dialog({
        message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>上传中</span>",
        closeButton: false,
        className: "bootboxModal",
    });
    var files=document.getElementById("imageFile");
    var file=files.files;//每一个file对象对应一个文件。
    if(file.length == 0){
        parent.window.bootbox.alert("请选择图片");
        return false;
    };
    var token = $("#token").val();
    var options = {
        quality: 0.60,
        noCompressIfLarger: true
    };
    var config = {
        useCdnDomain: true,
        region: qiniu.region.z0
    };
    var putExtra = {
        fname: "",
        params: {},
        mimeType: [] || null
    };
    var sum = 0;
    var observer = {
        next:function(res){

        },
        error:function(err){
            parent.window.bootbox.alert("保存失败"+err.message);
        },
        complete:function(res){
            hashKey = res.hash;
            sum = sum + 1;
            if(sum == file.length){
                parent.removeBootBox("bootboxModal");
                parent.window.bootbox.alert("保存成功");
                refreshMedia(hashKey);
            }
        }
    };
    for (var int = 0; int < file.length; int++) {
        qiniu.compressImage(file[int], options).then(function (data) {
            var observable = qiniu.upload(data.dist, null, token, putExtra, config);
            var subscription = observable.subscribe(observer) ;// 上传开始
        });
    }

}

function refreshMedia(hashKey){
    $.ajax({
        type: "POST",
        url: "/njSubmitOrder/njSubmitOrderKey",
        data: {hashKey : hashKey},
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
                    html = html + "<td align='center'><img style='height:100px;width:160px;' src='" + data[i].small + "'/></td>";
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

