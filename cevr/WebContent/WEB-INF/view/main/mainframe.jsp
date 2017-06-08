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
<link href="source/style/css/styleall.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="source/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="source/plugins/layer/layer.js"></script>
<script type="text/javascript" src="source/plugins/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="source/script/ckUI/ckUI.js"></script>
<style type="text/css">
	html{text-align:center;}
	body{width:65%;margin:0 auto;text-align:left;}
</style>
<script>
	$(function(){
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
									 +     '<span class="col-xs-5 text-right " style="font-size: 20px;">' + carInfo[index].carName + '</span>'
									 + 	   '<div class="col-xs-4 text-left paddingTBLR5_3">当前票数: ' + carInfo[index].ticketNum + ' 票</div>'
									 + 	   '<div class="col-xs-3 text-left"><input onclick="clickTicket()" class="btn-green paddingTBLR5_3" type="button" value="投票"/></div>'
									 + '</div>'
								+ '</li>';
					$("#carInfo").append(carLi);
				}
			}
		});
	});
	
	function clickTicket(){
		layer.open({
			type: 1,
			area: ['420px', '240px'], //宽高
			content: $("#ticketUserInfo")
		});
	}
</script>
</head>
<body>
	<div class="col-xs-12">
		<ul id="carInfo" class="col-xs-12">

		</ul>
	</div>
	
	<div id="ticketUserInfo">
		啊啊啊啊啊 啊啊
	</div>
</body>
</html>