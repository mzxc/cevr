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
<title>忘记密码</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="plugins/layer/layer.js"></script>
<script type="text/javascript" src="script/ckUI.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<link href="style/welcome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/templatemo_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function(){
	$("#email").maxLength(30);
	$("#email").clean();
	$("#sendEmail").bind("click", function(){
		var email = $("#email").val();
		if($("#email").isEmail() == false){
			$.showMsg("请输入正确格式的邮箱地址!", 2);
			return false;
		}
		$.ajax({
			type : "post",
			data : {"email" : email},
			dataType : "json",
			url : "asyn/pwd/getPwdByEmail",
			success : function(data){
				if(data.result){
					$.showMsg(data.msg, 6, 2, function(){
						window.location.href = "common/forward/welcome/login";
					});
				}else{
					$.showMsg(data.msg, 7);
					$("#email").focus();
				}
			},
			error : function(errorMsg){
				$.showMsg(errorMsg, 2);
				$("#email").focus();
			}
		});
	});
});
</script>
</head>
<body class="templatemo-bg-gray">
	<div class="container">
		<div class="col-md-12">
			<h1 class="margin-bottom-15">重置密码</h1>
			<form class="form-horizontal templatemo-forgot-password-form templatemo-container" role="form" action="#" method="post">	
				<div class="form-group">
		          <div class="col-md-12">
		          	请输入您在注册时填写的邮箱地址
		          </div>
		        </div>		
		        <div class="form-group">
		          <div class="col-md-12">
		            <input type="text" autocomplete="off" class="form-control" id="email" placeholder="邮箱地址 例:hao123@163.com">	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		            <input id="sendEmail" type="button" value="发送链接" class="btn btn-danger" />
                    <br>
		          </div>
		        </div>
		      </form>
		</div>
	</div>
</body>
</html>