$(document).ready(function(){
	//设置bootbox语言
	bootbox.setLocale("zh_CN"); 
	login();
	getVerify();
	var btn_enter = false;//控制弹出框
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){ // 按 Esc 
          }
        if(e && e.keyCode==113){ // 按 F2 
           }            
         if(e && e.keyCode==13){ // enter 键
        	 if(btn_enter)
        	 {
        		 $(".btn btn-primary").click();
        		 btn_enter = false;
        	 }else{
        		 $("#_btn").click();
        	 }
        }
    }; 
	//登录
	function login()
	{
		$("#_btn").on("click",function(){
			var uname = $("#uname").val();
			var pwd = $("#pwd").val();
			var vercodes = $("#vercodes").val();
			if(uname!=""&& pwd!=""&& vercodes!="")
			{
				$.post("/user/login", {"uname":uname,"pwd":pwd,"codes":vercodes},
			    function(data){
					
				    if(data.status==200)
				    {
				    	window.location.replace("/user/main"); //跳转
				    }else
				    {
				    	bootbox.alert(data.message);
				    	btn_enter = true;
				    }
			    }, "json");
				
			}else{
				var _msg = "";
				var _msg1 = "";
				var _msg2 = "";
				var _msg3 = "";
				if(uname=="")
				{
					_msg1 = "用户名、";
				}
				if(pwd=="")
				{
					_msg2 = "密码、";
				}
				if(vercodes=="")
				{
					_msg3 = "验证码、";
				}
				_msg = _msg1+_msg2+_msg3;
				_msg = "请填写："+_msg.substring(0,_msg.length-1);
				bootbox.alert(_msg);
				btn_enter = true;
			}
		});
	}
	
	//获取验证码
	function getVerify(){
		
		$("#imgVerify").on("click",function(){
			$("#imgVerify").attr("src","/getVerify?"+Math.random());
		});
	}
	
});

