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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="" />
<link rel="Bookmark" href="" />
<title>gomyck!</title>
<link href="source/style/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="source/style/css/swiper.min.css" rel="stylesheet" type="text/css">
<link href="source/style/css/stylepublic.css" rel="stylesheet" type="text/css">
<link href="source/plugins/layer/skin/layer.css" rel="stylesheet" type="text/css">
<link href="source/style/css/styleall.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="source/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="source/plugins/layer/layer.js"></script>
<script type="text/javascript" src="source/plugins/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="source/script/ckUI/ckUI.js"></script>
<style type="text/css">
	html{text-align:center;}
	body{width:70%;margin:0 auto;text-align:left;}
</style>
<script>
	$(function(){
		initCarInfo();
		$("#userName").ckMustEnAndCN();
		$("#userName").ckMaxLength(10);
		$("#userTel").ckMustNumber();
		$("#userTel").ckMaxLength(11);
		$("#userEmail").ckMaxLength(50);
		$("#ifInputUserInfo").val("0");
	});
	
	var userInfoDiv;
	var showDivCarId;
	function clickTicket(carId){
		showDivCarId = carId;
		if($("#ifInputUserInfo").val() == "0"){
			userInfoDiv = layer.open({
				type: 1,
				area: ['320px', '240px'], //宽高
				content: $("#ticketUserInfo")
			});
		}else{
			changeButton(carId);
		}
	}
	
	function changeButton(carId){
		var _input = $("#clickInput_" + carId);
		if(_input.attr("mes") == "0"){
			_input.val("取消投票");
			_input.attr("mes", "1");
		}else{
			_input.val("投票");
			_input.attr("mes", "0");
		}
	}
	
	function sureUserInfo(){
		if(!$.ckIsMobile("userTel")){
			alert("请输入正确的手机号");
			return;
		}
		if(!$.ckIsEmail("userEmail")){
			alert("请输入正确的邮箱");
			return;
		}
		$("#ifInputUserInfo").val("1");
		changeButton(showDivCarId);
		layer.close(userInfoDiv);
	}
</script>
</head>
<body>
	<div class="col-xs-12">
		<ul id="carInfo" class="col-xs-12">

		</ul>
	</div>
	
	<div id="ticketUserInfo" style="display: none;">
		<input id="ifInputUserInfo" type="hidden" value="0"/>
		<ul class="col-xs-12">
			<div class="col-xs-12 text-center paddingTBLR5_3 ">
				<li class="col-xs-3 text-right paddingTB5 ">姓&emsp;名:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userName" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR5_3 ">
				<li class="col-xs-3 text-right paddingTB5 ">手机号:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userTel" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR5_3 ">
				<li class="col-xs-3 text-right paddingTB5 ">邮&emsp;箱:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userEmail" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR10_3 ">
				<div class="col-xs-3"></div>
				<input type="button" onclick="sureUserInfo()" value="确定" class="col-xs-6 mybtn btn-pink" />
				<div class="col-xs-3"></div>
			</div>
		</ul>
	</div>
</body>
<script type="text/javascript">
	function initCarInfo(){
		$.ajax({
			url: "asyn/index/getCarInfo",
			type: "post",
			dataType: "json",
			success: function(reuslt){
				var carInfo = reuslt.data;
				var groupId = "";
				for(var index = 0; index < carInfo.length; index = index + 1){
					if(groupId != carInfo[index].carGroupId){
						var breakUp = '<li class="col-xs-12 paddingTBLR5_3">&nbsp;</li>';
						var groupLi = '<li class="col-xs-12 text-center"><span style="font-size: 50px;">' + carInfo[index].carGroup + '</span></li>';
						
						$("#carInfo").append(breakUp).append(groupLi);
						groupId = carInfo[index].carGroupId;
					}
					var carLi = '<li class="col-xs-4">'
									 + '<img class="col-xs-12 paddingTBLR5_3" alt="" src="' + carInfo[index].carImgs + '">'
									 + '<div class="col-xs-12 text-center paddingTBLR15_3">'
									 +     '<span class="col-xs-5 text-right paddingTBLR5_3" style="font-size: 15px;">' + carInfo[index].carName + '</span>'
									 + 	   '<div class="col-xs-5 text-left paddingTBLR5_3">当前票数: ' + carInfo[index].ticketNum + ' 票</div>'
									 + 	   '<div class="col-xs-2 text-left"><input id="clickInput_' + carInfo[index].carId + '" mes="0" onclick="clickTicket(\'' + carInfo[index].carId + '\')" class="btn-green paddingTBLR5_3 mybtn" type="button" value="投票"/></div>'
									 + '</div>'
								+ '</li>';
					$("#carInfo").append(carLi);
				}
			}
		});
	}
</script>
</html>