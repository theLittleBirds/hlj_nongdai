<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title>农鲸</title>
    <meta name="keywords" content="农鲸">
	<meta name="description" content="">

	<link rel="shortcut icon" href="/admin/image/favicon.ico">
	<!-- Bootstrap -->
	<link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/admin/css/login.css" rel="stylesheet">
	
    <script src="/resource/bootstrap/js/ie-emulation-modes-warning.js"></script>
    <!--[if !IE]> -->
         <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <!-- <![endif]-->
    <!--[if lte IE 8]>
         <script src="/resource/jquery/jquery-1.12.0.min.js"></script>
    <![endif]-->
    <!--[if gt IE 8]>
         <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <![endif]-->
    <!-- 让不支持html5的浏览器“支持”html5标签 -->
    <!--[if lt IE 9]>  
      <script src="/resource/util/html5shiv.js"></script>
      <script src="/resource/util/respond.min.js"></script>
    <![endif]-->
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
    
    <script src="/admin/js/login.js"></script>
</head>
<body >	
	<div class="container">
		<div class="row">
			<div class="center">
				<h1>
					<span class="white"></span>
				</h1>
			</div>
			<div class="col-sm-4 panel panel-default div">
				<div class="margin-base-vertical center" style="font-size:20px;height:45px;line-height:22px;overflow:hidden;">
					<!-- <table height=100% width=100%>
						<tr>
							<td style="vertical-align:middle;">
							
							</td>
						</tr>
					</table> -->
					
				</div>
				
				
				<form class="margin-base-vertical">
					<div style="margin-top:12%;">
						<p class="input-group" style="width:100%;margin-left:10%">
							<span class="input-group-addon">  
                                <a class="glyphicon glyphicon-user"></a> 
                            </span>
							<input style="width:80%;" id="uname" type="text" class="form-control input-lg" name="uname" placeholder="用户名" />
						</p>
						<p class="input-group" style="width:100%;margin-top:4%;margin-left:10%">
							<span class="input-group-addon">  
                                <a class="glyphicon glyphicon-lock"></a> 
                            </span>
							<input style="width:80%;" id="pwd" type="password" class="form-control input-lg" name="password" placeholder="密码" />
						</p>
						<p class="input-group" style="width:82%;margin-top:4%;margin-left:10%">  
                            <span class="input-group-addon">  
                                <a class="glyphicon glyphicon-check"></a>  
                            </span>
                        	<input style="width: 100%; height: 39px;" id="vercodes" type="text" class="form-control input-lg" placeholder="请输入验证码" />
                        	<span class="input-group-addon"> 
                        		<img src="/getVerify?" alt="更换验证码" id="imgVerify" style="cursor:pointer">
                            </span>
                    	</p>
					</div>
					
					<div style="margin-top:8%;">
						<p class="text-center">
							<button id="_btn" type="button" style="width:50%;" class="btn btn-success btn-lg">登录</button>
						</p>
					</div>
				</form>
			</div>
			<div class="col-sm-4 div" style="margin-top:1%;padding-left:1%;padding-right:1%;">
				<span style="float:left"><a class="white" href="#" style="font-size:15px;text-decoration: none"></a></span>
				<span style="float:right"><a class="white" href="#" style="font-size:15px;text-decoration: none"></a></span>
			</div>
			<div style="position: absolute; bottom: 50px; right:10%; text-align: center;">
				<img src="/admin/image/logo.png" style="width:80%"/>
				<h2><span  style="color:white">融通城乡 智惠三农</span></h2>
	        </div>
		</div>
	</div>
	
</body>
</html>
