<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String tab = (String)request.getAttribute("tab");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="" />
<link rel="Bookmark" href="" />
<title>环青海湖（国际）电动汽车挑战赛|QingHai Lake China</title> 
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
		if(${tab}=='1')
		{
			$("#tab_title").text("赛车车型外观设计投票");
		}else if(${tab}=='2'){
			$("#tab_title").text("赛车车型空间设计投票");
		}else{
			$("#tab_title").text("赛车车型内饰设计投票");
		}
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
				area: ['320px', '250px'], //宽高
				content: $("#ticketUserInfo")
			});
		}else{
			changeButton(carId);
		}
	}
	
	function changeButton(carId){
		var _input = $("#clickInput_" + carId);
		if(_input.attr("mes") == "0"){
			sureTicket(carId);
		}else{
			unSureTicket(carId);
		}
	}
	
	function sureUserInfo(){
		if(!$.ckIsMobile("userTel")){
			layer.msg("请输入正确的手机号");
			return;
		}
		if(!$.ckIsEmail("userEmail") && !$.ckIsEmpty($("#userEmail").val())){
			layer.msg("请输入正确的邮箱");
			return;
		}
		$("#ifInputUserInfo").val("1");
		changeButton(showDivCarId);
		layer.close(userInfoDiv);
	}
	
	function sureTicket(carId){
		var userName = $("#userName").val();
		var userTel = $("#userTel").val();
		var userEmail = $("#userEmail").val();
		var ticketType = $("#ticketType").val();
		$.ajax({
			url: "asyn/index/ticketCar",
			data: {userName: userName, userTel: userTel, userEmail: userEmail, carId: carId, ticketTypeId: ticketType},
			type: "post",
			dataType: "json",
			success: function(result){
				if(result.result){
					var _input = $("#clickInput_" + carId);
					_input.val("取消投票");
					_input.attr("mes", "1");
					var _span = $("#clickSpan_" + carId);
					_span.text($.ckAdd(_span.text(), 1));
					var group = _input.attr("group");
					$("input[group='" + group + "']").not(_input).attr("disabled", "disabled");
					$("input[group='" + group + "']").not(_input).attr("value", "投票");
					layer.msg("投票成功");
				}else{
					layer.msg(result.msg);
				}
			},
			error: function(){
				layer.msg("服务器开小差，请稍后再试!");
			}
		});
	}
	
	function unSureTicket(carId){
		var _input = $("#clickInput_" + carId);
		var ticketType = $("#ticketType").val();
		$.ajax({
			url: "asyn/index/unTicketCar",
			data: {carId: carId, ticketTypeId: ticketType},
			type: "post",
			dataType: "json",
			success: function(result){
				if(result.result){
					_input.val("投票");
					_input.attr("mes", "0");
					var _span = $("#clickSpan_" + carId);
					_span.text($.ckSub(_span.text(), 1));
					var group = _input.attr("group");
					$("input[group='" + group + "']").removeAttr("disabled");
				}else{
					layer.msg(result.msg);
				}
			},
			error: function(){
				layer.msg("服务器开小差，请稍后再试!");
			}
		});
	}
</script>
</head>
<body>
	<input id="ticketType" type="hidden" value="1" />
	<input id="imgType" type="hidden" value="1" />
	<div class="col-xs-12">
		<div class="col-xs-12 text-center paddingTBLR15_3">
			<span id="tab_title" style="font-size: 50px;">
			
			</span>
		</div>
		<ul id="carInfo" class="col-xs-12">
			
		</ul>
	</div>
	
	<div id="ticketUserInfo" style="display: none;">
		<input id="ifInputUserInfo" type="hidden" value="0"/>
		<ul class="col-xs-12">
			<div class="col-xs-12 text-center paddingTBLR5_3">
				<li class="col-xs-3 text-right paddingTB5 ">姓&emsp;名:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userName" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR5_3">
				<li class="col-xs-3 text-right paddingTB5 ">手机号:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userTel" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR5_3">
				<li class="col-xs-3 text-right paddingTB5">邮&emsp;箱:&nbsp;</li>
				<li class="col-xs-9 text-left"><input id="userEmail" class="paddingTB5 bggray2" type="text" /></li>
			</div>
			<div class="col-xs-12 text-center paddingTBLR10_3">
				<div class="col-xs-3"></div>
				<input type="button" onclick="sureUserInfo()" value="确定" class="col-xs-6 mybtn btn-pink" />
				<div class="col-xs-3"></div>
			</div>
		</ul>
	</div>
	
	<iframe id="hideCarMovie" height=498 width=510 style="display: none;" src='http://player.youku.com/embed/XMjc5NTA0NDkyNA==' frameborder=0 'allowfullscreen'></iframe>
	
</body>
<script type="text/javascript">
	function showMovie(){
		layer.open({
			type: 1,
			closeBtn: false,
			title: false,
			shadeClose: true,
			area: ['510px', '510px'], //宽高
			content: $("#hideCarMovie")
		});
	}

	function initCarInfo(){
		$.ajax({
			url: "asyn/index/getCarInfo",
			type: "post",
			data: {ticketTypeId: $("#ticketType").val(), imgType: $("#imgType").val()},
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
					
					var carLi = '<li class="col-xs-4">';
					var imgHtml = "";
					if($.ckIsEmpty(carInfo[index].carImgs)){
						imgHtml = '<img onclick="showMovie()" class="col-xs-12 paddingTBLR5_3" alt="" src="' + carInfo[index].carImgs + '">';
					}else{
						var carImgs = carInfo[index].carImgs.split("|");
						var swiperHtmlHead = '<div class="col-xs-12 swiper-container"><div class="swiper-wrapper col-xs-12">'
						var swiperHtmlEnd = '</div></div>';
						imgHtml = imgHtml + swiperHtmlHead;
						for(var i = 0; i < carImgs.length;i = i + 1){
							var img = '<div onclick="showMovie()" class="swiper-slide"><img class="col-xs-12 paddingTBLR5_3" alt="" src="' + carImgs[i] + '"></div>';
							imgHtml = imgHtml + img;
						}
						imgHtml = imgHtml + swiperHtmlEnd;
					}
					carLi = carLi + imgHtml;
					carLi = carLi + '<div class="col-xs-12 text-center paddingTBLR15_3">'
									 +     '<span class="col-xs-5 text-right paddingTBLR5_3 textover" style="font-size: 15px;">' + carInfo[index].carName + '</span>'
									 + 	   '<div class="col-xs-5 text-left paddingTBLR5_3 textover">当前票数: <span id="clickSpan_' + carInfo[index].carId + '">' + carInfo[index].ticketNum + '</span> 票</div>'
									 + 	   '<div class="col-xs-2 text-left">'
									 +         '<input id="clickInput_' + carInfo[index].carId + '" mes="0" group="group' + carInfo[index].carGroupId + '" onclick="clickTicket(\'' + carInfo[index].carId + '\')" class="btn-green mybtn paddingTBLR5_3" type="button" value="投票"/>'
									 +     '</div>'
									 + '</div>'
								+ '</li>';
					$("#carInfo").append(carLi); 
				}
				var mySwiper = new Swiper('.swiper-container', {
					centeredSlides: true,
			        autoplay: 4500,
			        loop:true,
			        autoplayDisableOnInteraction: false
				})
			}
		});
	}
</script>
</html>