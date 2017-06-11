<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'playerJsp.jsp' starting page</title>
    
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
    <object id="ExternalInterfaceExample" width="640" height="360" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-aE6D-11cf-96B8-444553540000">
	<param value="http://data.cnlive.com/export/CNLivePlayer.swf?hasBorder=false&amp;uuid=58b6bbf6ddd3426abbeb42c631ac43fe" name="movie">
	<param value="high" name="quality">
	<param value="always" name="allowScriptAccess">
	<param value="true" name="allowFullScreen">
	<embed width="640" height="360" allowfullscreen="true" name="ExternalInterfaceExample" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" allowscriptaccess="always" quality="high" src="${src }" wmode="transparent">
	</object>
  </body>
</html>
