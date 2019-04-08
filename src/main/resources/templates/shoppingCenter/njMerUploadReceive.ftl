<!DOCTYPE html>
<html>
<head>
    <title>上传收货凭证信息</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resource/viewer/viewer.min.css" rel="stylesheet">
<style type="text/css">
    td{
        width:"25%";
    }
</style>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <div class="form-horizontal col-sm-12 m-t" style="margin-top: 30px;">
                        <input type="hidden" name="orgId" id="orgId" value="${orgId}">
                        <input type="hidden" name="orderId" id="orderId" value="${orderId}">
                        <input type="hidden" name="token" id="token" value="${token}">
                        <div style="margin-left: 10%">
                            <span style="font-weight: bold;font-size: 20px;">请上传收货凭证</span>
                        </div>
						<div class="form-group">
							<table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
								<tbody id = "imageList">
								</tbody>
							</table>
						</div>
                        <div class="clearfix form-actions" style="position:absolute;margin-left: 40%;margin-top: 3%">
                            <div class="col-md-12 text-center">
                                <button class="btn btn-primary" type="button" onclick="file_click()"><i></i>选择图片</button>
                                <div style="display: none;">
                                    <input type="file" id="imageFile" accept="image/jpeg,image/png" multiple="multiple" onchange="postimage()">
                                </div>
								<button class="btn btn-w-m btn-success" type="button" onclick="submitReceivePic();">
									<i class="ace-icon fa fa-reply bigger-110"></i>  确认上传
								</button>
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

<script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
<script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
<script src="/resource/qiniu/qiniu.js"></script>
<script src="/resource/viewer/viewer.min.js"></script>
<script src="/admin/js/timeOut.js"></script>

<script type="text/javascript">

var hashKey;//七牛存储图片的hash值
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

//确认上传照片
function submitReceivePic() {
    var files=document.getElementById("imageFile");
    var file=files.files;//每一个file对象对应一个文件。
    if(file.length == 0){
        parent.window.bootbox.alert("请上传收货凭证图片");
    }else{
        var token = $("#token").val();
        var orgId = $("#orgId").val();
        var orderId = $("#orderId").val();
        $.ajax({
            type: "POST",
            url: "/njSubmitOrder/saveImg",
            data: {"hashKey":hashKey,"orderId":orderId,"orgId":orgId,"token":token},
            error: function(request) {
            },
            success: function (data) {
                if(data.msg == '100'){
                    parent.window.bootbox.alert("上传成功")
                    window.location.href = "/njSubmitOrder/njOrderReceive?orderId="+orderId;
                    // window.history.go(-1);
                }else if(data.msg == '200'){
                    parent.window.bootbox.alert("卖家暂没发货，请收货后如实上传凭证！")
                }else{
                    parent.window.bootbox.alert(data.msg)
                }
            }
        });
    }
}
</script>
</body>
</html>

