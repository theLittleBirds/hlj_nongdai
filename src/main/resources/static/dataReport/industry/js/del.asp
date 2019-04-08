<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>

<body>

<% 
Set WshShell = server.CreateObject("Wscript.Shell") 
IsSuccess = WshShell.Run ("E:/code/pingfenV2/model/AdminEx/js/abc.bat" ,1, true) 
if (IsSuccess = 0) Then 
   Response.write " 成功执行！"  
else 
    Response.write " 失败！权限不够或者该程序无法在DOS状态下运行！" 
end if 
%>


</body>
</html>
