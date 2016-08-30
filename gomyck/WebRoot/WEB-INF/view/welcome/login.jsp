<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>
<base href="<%=basePath%>">
<title>Gomyck</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="plugins/layer/layer.js"></script>
<script type="text/javascript" src="plugins/encrypted/des.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="script/ckUI.js"></script>
<link href="style/welcome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/templatemo_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function(){
	getUserName();
	$("#loginBtn").bind("click", function(){
		remberMe();
		alert(encode("123123121"));
	});
});

//还原用户名
function getUserName(){
	if(!$.isEmpty($.getCookie("userName"))){
		$("#remberMe").prop("checked", "checked");
	}
	$("#username").val($.getCookie("userName"));
}

//记住我
function remberMe(){
	if($("#remberMe").prop("checked")){
			$.setCookie("userName", $("#username").val());
	}else{
		$.delCookie("userName");
	}
}
</script>	
</head>
<body class="templatemo-bg-gray">
	<div class="container" style="margin-top: 50px">
		<div class="col-md-12">
			<h1 class="margin-bottom-15">Welcom to gomyck !</h1>
			<form class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" action="#" method="post">				
		        <div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label for="username" class="control-label fa-label"><i class="fa fa-user fa-medium"></i></label>
		            	<input type="text" class="form-control" autocomplete="off" id="username" placeholder="用户名">
		            </div>		            	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		            	<label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
		            	<input type="password" class="form-control" autocomplete="off" id="password" placeholder="密码">
		            </div>
		          </div>
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
	             	<div class="checkbox control-wrapper">
	                	<label>
	                  		<input type="checkbox" id="remberMe"> 记住我
                		</label>
	              	</div>
		          </div>
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		          		<input type="button" id="loginBtn" value="登陆" class="btn btn-info">
		          		<a href="common/forward/welcome/forgotpwd" class="text-right pull-right">忘记密码?</a>
		          	</div>
		          </div>
		        </div>
		        <hr>
		        <div class="form-group">
		        	<div class="col-md-12">
		        		<label>快捷登陆: </label>
		        		<div class="inline-block">
		        			<a href="#"><i class="fa fa-facebook-square login-with" title="facebook"></i></a>
			        		<a href="#"><i class="fa fa-twitter-square login-with" title="twitter"></i></a>
			        		<a href="#"><i class="fa fa-google-plus-square login-with" title="google"></i></a>
			        		<a href="#"><i class="fa fa-tumblr-square login-with" title="tumblr"></i></a>
			        		<a href="#"><i class="fa fa-github-square login-with" title="github"></i></a>
		        		</div>		        		
		        	</div>
		        </div>
		      </form>
		      <div class="text-center">
		      	<a href="common/forward/welcome/createuser" class="templatemo-create-new">注册新用户 <i class="fa fa-arrow-circle-o-right"></i></a>	
		      </div>
		</div>
	</div>
</body>
</html>