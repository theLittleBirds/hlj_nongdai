<!DOCTYPE html>
<html>
<head>
    <title>贷中监控风险报告</title>
    
    <link rel="stylesheet" href="/static/dataReport/plugins/bootstrap-3.3.6/css/bootstrap.min.css" >
	<link href="/static/dataReport/plugins/hplus_v4.1.0/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	<link href="/static/dataReport/plugins/hplus_v4.1.0/css/animate.min.css" rel="stylesheet">
	<link href="/static/dataReport/plugins/hplus_v4.1.0/css/style.min.css" rel="stylesheet">
	<link href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/iCheck/custom.css" rel="stylesheet">
	<link rel="stylesheet" href="/static/dataReport/plugins/webuploader-0.1.5/webuploader.css">
	<link rel="stylesheet" href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/chosen/chosen.css">
	<link rel="stylesheet" href="/static/dataReport/css/style.css">
    <!-- checkbox样式 -->
    <link rel="stylesheet"
          href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css">
    <!-- radio样式 -->
    <link rel="stylesheet"
          href="/static/dataReport/plugins/hplus_v4.1.0/css/plugins/iCheck/custom.css">

    <style>
        .control-label {
            text-align: left !important;
        }

        .title {
            font-size: 14px;
            text-align: left;
            color: #000000;
            border-bottom: 1px dotted #CBCBCB;
            padding-bottom: 5px;
            margin-bottom: 5px !important;
        }
    </style>

</head>

<body class="gray-bg">

<div class="wrapper wrapper-content" style="padding: 0;">
    <div class="row">
        <div class="col-sm-12">
            <form class="form-horizontal" action="" method="post">
                <div class="ibox">
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group">
                        	<label class="col-sm-3 control-label"  style="color: #ff3333;">逾期风险等级：${bad_rate!'无风险'}</label>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label"  style="color: #ff3333;">用户行为分：${score!'无风险'}</label>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label"  style="color: #ff3333;">"坏"客户占比：${bad_rate!'无风险'}</label>
                        </div>
                    </div>    
                    
                    <div class="ibox-title">
                        <h2 >风险名单：身份证号命中法院执行风险名单</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="blacklist">
                        	
                        </div>
                        <div class="form-group" id="_blacklist">

                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >模糊名单：身份证号命中法院执行模糊名单</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="fuzzyblacklist">
                        	
                        </div>
                        <div class="form-group" id="_fuzzyblacklist">
                        	
                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >关注名单：身份证号命中高风险关注名单</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="greylist">
                        	
                        </div>
                        <div class="form-group" id="_greylist">
                        	
                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >信贷逾期名单： 身份证号命中信贷逾期名单</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="p2pdiscredit">
                        	
                        </div>
                        <div class="form-group" id="_p2pdiscredit">
                        	
                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >多平台申请：身份证号命中多平台申请 </h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="precrosspartner">
                        	
                        </div>
                    </div>
                    <div class="ibox-title">
                        <h2 >多平台负债：身份证号命中多平台负债</h2>
                    </div>
                    <div class="ibox-content clearfix" style="padding-bottom: 0px;">
                        <div class="form-group" id="postcrosspartner">
                        	
                        </div>
                    </div>                    
                </div>

                <div class="clearfix form-actions" style="margin-bottom: 20px;">
                    <div class="col-md-9 text-center">
                        <button class="btn btn-w-m" type="button" onclick="window.close();">
                            <i class="ace-icon fa fa-reply bigger-110"></i>
                            	关闭
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/static/dataReport/plugins/jquery-2.2.2.min.js"></script>
<script src="/static/dataReport/plugins/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<script src="/static/dataReport/plugins/hplus_v4.1.0/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/static/dataReport/js/validate.expand.js"></script>
<script src="/static/dataReport/plugins/hplus_v4.1.0/js/plugins/validate/messages_zh.min.js"></script>
<script src="/static/dataReport/plugins/hplus_v4.1.0/js/plugins/iCheck/icheck.min.js"></script>
<script src="/static/dataReport/plugins/hplus_v4.1.0/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/dataReport/plugins/layer/layer.min.js"></script>
<script src="/static/dataReport/js/script.js"></script>
<script src="/admin/js/timeOut.js"></script>
<script>

    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    });
	
    
    //风险名单：身份证号命中法院执行风险名单
    var blacklist = ${blacklist!'null'};
    
    //模糊名单：身份证号命中法院执行模糊名单
    var fuzzyblacklist = ${fuzzyblacklist!'null'};

    //关注名单：身份证号命中高风险关注名单
    var greylist = ${greylist!'null'};
    
    //信贷逾期名单： 身份证号命中信贷逾期名单
    var p2pdiscredit = ${p2pdiscredit!'null'};
    
    //多平台申请：身份证号命中多平台申请 
    var precrosspartner = ${precrosspartner!'null'};
    
    //多平台负债：身份证号命中多平台负债 
    var postcrosspartner = ${postcrosspartner!'null'};
    
</script>

<script src="/static/tongdun/blacklist.js"></script>
<script src="/static/tongdun/fuzzyblacklist.js"></script>
<script src="/static/tongdun/greylist.js"></script>
<script src="/static/tongdun/p2pdiscredit.js"></script>
<script src="/static/tongdun/precrosspartner.js"></script>
<script src="/static/tongdun/postcrosspartner.js"></script>
</body>
</html>

