<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,com.weixin.dao.InstruDao,java.text.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>辅导员查询界面</title>
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
    <%
    	InstruDao id = InstruDao.getInstance();
    	//获得辅导员姓名
    	String name = (String)request.getAttribute("name");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String time = sdf.format(new Date());
    	Map<String, List<String>> map = id.querySign(name, time);
    	//得到学号
    	List<String> accountList = map.get("account");
    	//得到姓名
    	List<String> nameList = map.get("name");
    	//得到未签到的人数
    	Map<String, List<String>> unSignMap = id.queryUnSign(name, time);
    	List<String> unAccountList = unSignMap.get("account");
    	List<String> unNameList = unSignMap.get("name");
    	//获取已请假的人
    	Map<String, List<String>> leaveMap = id.queryLeave(name, time);
    	List<String> leaveAccount = leaveMap.get("account");
    	List<String> leaveName = leaveMap.get("name");
    %>
    <table border="1" style="margin:auto;">
    <caption>已经签到了的人数</caption>
    	<tr>
    		<th>学号</th>
    		<th>姓名</th>
    	</tr>
    	<% for(int i = 0; i < accountList.size(); i ++) { %>
    	<tr>
    		<td><%=accountList.get(i) %></td>
    		<td><%=nameList.get(i) %></td>
    	</tr>
    	<% } %>
    	
    </table>
    <br/>
    <table border="1" style="margin:auto;">
    <caption>请假的人数</caption>
    	<tr>
    		<th>学号</th>
    		<th>姓名</th>
    	</tr>
    	<% for(int i = 0; i < leaveAccount.size(); i ++) { %>
    	<tr>
    		<td><%=leaveAccount.get(i) %></td>
    		<td><%=leaveName.get(i) %></td>
    	</tr>
    	<% } %>
    	
    </table>
    <br/>
    <table border="1" style="margin:auto;">
    <caption>未签到的人数</caption>
    	<tr>
    		<th>学号</th>
    		<th>姓名</th>
    	</tr>
    	<% for(int i = 0; i < unAccountList.size(); i++) { %>
    	<tr>
    		<td><%=unAccountList.get(i) %></td>
    		<td><%=unNameList.get(i) %></td>
    	</tr>
    	<%} %>
    </table>
    
    
  </body>
</html>
