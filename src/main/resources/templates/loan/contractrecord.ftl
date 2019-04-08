<!DOCTYPE html>
<html>
<head>
    <title>合同记录</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-10">
            <div class="ibox">
                <div class="ibox-content clearfix">
                	<div class="row" style="padding: 10px;margin-left: 10px;">                    	
                    	<table class="table table-bordered m-t" style="margin-top: 10px;">
         					<thead><tr><th>合同名称</th><th>签约人</th><th>下载</th></tr></thead>
         					<tbody id="tableid">
         						<#list arr as one>
         							<tr>
         								<td>${one.name}</td>
         								<td>${one.signatories}</td>
         								<td>
         									<#if one.status == 3>
         										<a style='cursor:pointer;' href='/loan/loadpdf?id=${one.id}'>下载</a>
         									</#if> 
         								</td>
         							</tr>
         						</#list> 
         					</tbody>
                    	</table> 
                    	<div class="clearfix form-actions">
                            <div class="col-sm-12 text-center">
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

</body>
</html>