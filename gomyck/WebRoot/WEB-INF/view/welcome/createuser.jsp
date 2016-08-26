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
<title>注册用户</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta charset="UTF-8">
<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="plugins/layer/layer.js"></script>
<script type="text/javascript" src="script/ckUI.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="style/welcome/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
<link href="style/welcome/css/templatemo_style.css" rel="stylesheet" type="text/css">	
</head>
<script type="text/javascript">
$(function(){
	$("#first_name").focus();
	$("#createUserForm").find("input:not(#createUserBtn):not(#optionsRadiosMan):not(#optionsRadiosWom)").val("");
	$("#createUserBtn").bind("click", function(){
		var options = {
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				if($.ckTrim($("#first_name").val()) == "" || $.ckTrim($("#last_name").val()) == ""){
					$.showMsg("请输入姓名", 2);
					$("#first_name").focus();
					return false;
				}
				if($("#email").isEmail() == false){
					$.showMsg("请输入正确的邮箱", 2);
					return false;
				}
				if(!checkValueByCreate(1, $("#email")[0]) || !checkValueByCreate(2, $("#username")[0])){
					return false;
				}
				if($.isPassword($("#password").val()) == false){
					$.showMsg("密码必须为6-16个字母、数字、下划线", 2);
					$("#password").focus();
					return false;
				}
				if(!$.ckEquals("password", "password_confirm")){
					$.showMsg("亲,密码不一致", 2);
					return false;
				}
				layer.msg("正在处理...",{icon : 16,time : 0, shade: [0.1]});
			},
			success : function(data) {
				layer.closeAll();
				if(data.result){
					$.showMsg(data.msg, 6, 3, function(){
						window.location.href = "common/forward/welcome/login";
					});
				}else{
					$.showMsg(data.msg, 7)
					return false;
				}
			},
			error : function(result) {
				$.showMsg("亲!您的网络不给力哦~", 2);
			}
		};
		$("#createUserForm").ajaxSubmit(options);
	});
});

function checkValueByCreate(type, _this){
	var flag = $.ckValidate({
		self : _this,
		async : false,
		url : "asyn/reg/validataParam",
		type : type,
		sameValidate : true,
		notBeNull : {
			flag : true
		},
		onSuccess : function(){
			return true;
		}
	});
	return flag;
}

function checkValue(type, _this){
	$.ckValidate({
		type : type,
		self : _this,
		async : true,
		sameValidate : false,
		url : "asyn/reg/validataParam",
		notBeNull : {
			flag : true
		}
	});
}

function ifAgreeMyRaw(){
    if($("#agreeMyRaw").prop("checked")){
   		$("#createUserBtn").attr("disabled",false);
    }else{
    	$("#createUserBtn").attr("disabled",true);
    }
}
</script>
<body class="templatemo-bg-gray">
	<h1 class="margin-bottom-14">注册新用户</h1>
	<div class="container">
		<div class="col-md-12">			
			<form id="createUserForm" class="form-horizontal templatemo-create-account templatemo-container" role="form" action="asyn/reg/createUser" method="post">
				<div class="form-inner">
					<div class="form-group">
			          <div class="col-md-6">		          	
			            <label for="first_name" class="control-label">姓</label>
			            <input type="text" maxlength="5" class="form-control" id="first_name" name="realsurname" placeholder="姓氏">		            		            		            
			          </div>  
			          <div class="col-md-6">		          	
			            <label for="last_name" class="control-label">名</label>
			            <input type="text" maxlength="5" class="form-control" id="last_name" name="realname" placeholder="名字">		            		            		            
			          </div>             
			        </div>
			        <div class="form-group">
			          <div class="col-md-12">		          	
			            <label for="username" class="control-label">Email</label>
			            <input type="email" maxlength="30" onblur="checkValue(1, this)" defaultValue="" class="form-control" id="email" name="email" placeholder="常用的Email">		            		            		            
			          </div>              
			        </div>			
			        <div class="form-group">
			          <div class="col-md-6">		          	
			            <label for="username" class="control-label">用户名</label>
			            <input type="text" maxlength="10" onblur="checkValue(2, this)" defaultValue="" class="form-control" id="username" name="userName" placeholder="请输入您在本系统狂拽炫酷的代号">		            		            		            
			          </div>
			          <div class="col-md-6 templatemo-radio-group">
			          	<label class="radio-inline">
		          			<input type="radio" name="sex" id="optionsRadiosMan" value="1" checked="checked"> 男
		          		</label>
		          		<label class="radio-inline">
		          			<input type="radio" name="sex" id="optionsRadiosWom" value="0"> 女
		          		</label>
			          </div>             
			        </div>
			        <div class="form-group">
			          <div class="col-md-6">
			            <label for="password" class="control-label">密码</label>
			            <input type="password" maxlength="16" class="form-control" name="password" id="password" value="" placeholder="6-16个字母、数字、下划线">
			          </div>
			          <div class="col-md-6">
			            <label for="password" class="control-label">确认密码</label>
			            <input type="password" maxlength="16" class="form-control" name="confirmPassword" id="password_confirm" value="" placeholder="请牢记密码">
			          </div>
			        </div>
			        <div class="form-group">
			          <div class="col-md-12">
			            <label><input type="checkbox" id="agreeMyRaw" onclick="ifAgreeMyRaw()" />我同意使用gomyck系统并遵守系统约束</label>
			          </div>
			        </div>
			        <div class="form-group">
			          <div class="col-md-12">
			            <input id="createUserBtn" type="button" disabled="disabled" value="提交" class="btn btn-info">
			            <a href="common/forward/welcome/login" class="pull-right">登陆</a>
			          </div>
			        </div>	
				</div>				    	
		      </form>		      
		</div>
	</div>
</body>
</html>