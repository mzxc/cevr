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
<script>
	$(function(){
		$.ajax({
			url: "asyn/index/getCarInfo",
			type: "post",
			dataType: "json",
			success: function(reuslt){
				var carInfo = reuslt.data;
				for(var index = 0; index < carInfo.length; index = index + 1){
					var carLi = '<li class="col-xs-4">'
									 + '<img class="col-xs-12 paddingLR5" alt="" src="http://192.168.1.166:8080/cevr_img/car.png">'
									 + '<span class="col-xs-12 text-center">' + carInfo[index].carName + '</span>'
								+ '</li>';
					$("#carInfo").append(carLi);
				}
			}
		});
	});
</script>
</head>
<body>
	<div class="col-xs-12">
		<ul id="carInfo" class="col-xs-12">

		</ul>
	</div>
</body>
</html>