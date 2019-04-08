<!DOCTYPE html>
<html>
<head>
    <title>上传定金图片信息</title>
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
                        <input type="hidden" name="merOrderId" id="merOrderId" value="${busNjmerorder.merOrderId}">
						<div class="form-group">
							<table style="width: 80%;margin-left: 10%;border-collapse:separate; border-spacing:10px;">
								<tbody id = "imageList">
									
								</tbody>
							</table>
						</div>
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                            <button class="btn btn-w-m btn-success" type="button" onclick="window.history.go(-1);">
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
refreshMedia();
var pictureType ;
var view;

function refreshMedia(){
    var merOrderId = $("#merOrderId").val();
	$.ajax({
		type: "POST",
		url: "/njAssociationOrder/merOrderImgList",
		data: {"merOrderId":merOrderId},
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
                    var typeName = "-";
					if("1" == data[i].fileType){
						typeName = "定金";
					}else if("2" == data[i].fileType){
						typeName = "尾款";
					}else if("3" == data[i].fileType){
						typeName = "发货单";
					}else if("4" == data[i].fileType){
						typeName = "收货单";
					}
	    			html = html + "<td align='center'><div style='float:left;width:100%;'><img style='height:100px;width:160px;cursor:pointer;' src='"
	    			+ data[i].small + "' data-original='"+data[i].big +"'/></div><div style='float:left;width:100%;'>"+typeName+"</div></td>";
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
</script>
</body>
</html>

