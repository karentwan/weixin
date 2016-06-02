<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>辅导员绑定页面</title>
    <meta name="viewport"content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form id="form" action="http://weixin2.ngrok.natapp.cn/weixin2.0/instruBind" method="post" style="margin:auto;" onsubmit="return sub()" >
    	<input type="hidden" id="openId" name="openId" value="<%= request.getAttribute("openId")%>"/>
    	<input type="text" id="name"  name="name" />
    	<input type="password" id="psd" name="psd"/>
    	<input type="text" id="nick" name="nick"/>
    	<input type="text" id="nick" name="college" />
    	<br/>
    	<input type="submit" value="submit" />
    </form>
  </body>
  
  <script type="text/javascript">
	function sub() {
  		var name = document.getElementById("name");
  		var openId = document.getElementById("openId");
  		var psd = document.getElementById("psd");
  		if( !name.value.trim() || !psd.value.trim()) {
  			alert("名字不能为空，请输入姓名!");
  			return false;
  		} else {
  			return true;
  		}
  	}
  
  </script>
  
</html>
