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
		$("#userName").ckMustEnAndCN();
		$("#userName").ckMaxLength(10);
		$("#userTel").ckMustNumber();
		$("#userTel").ckMaxLength(11);
		$("#userEmail").ckMaxLength(50);
		$("#ifInputUserInfo").val("0");
	});
	function tabClick(v){
		//$("#tableForm").attr({action: "${basePath}/cevr/common/forward/main/voteframe", method: "POST"});
		//$("#tableForm").submit(); 
		//post('common/forward/main/voteframe',{tab:1});
		window.location.href='asyn/index/voteframe/'+v;
	}
	function post(url, params) {
	    var temp = document.createElement("form"); //创建form表单
	    temp.action = url;
	    temp.method = "post";
	    temp.style.display = "none";//表单样式为隐藏
	    for (var item in params) {//初始化表单内部的控件
	       //根据实际情况创建不同的标签元素
	        var opt =document.createElement("input");  //添加input标签
	        opt.type="text";   //类型为text 
	        opt.id = item;      //设置id属性
	        opt.name = item;    //设置name属性
	        opt.value = params[item];   //设置value属性
	        temp.appendChild(opt);
	    }
	    
	    document.body.appendChild(temp);
	    temp.submit();
	    return temp;
	}
</script>
</head>
<body>
	<!-- <embed width="640" height="360" allowfullscreen="true" name="ExternalInterfaceExample" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" allowscriptaccess="always" quality="high" src="http://data.cnlive.com/export/CNLivePlayer.swf?hasBorder=false&amp;uuid=69299e499d55424ca8b300a9978e60a5" wmode="transparent">-->


	<input id="ticketType" type="hidden" value="1" />
	<input id="imgType" type="hidden" value="1" />
		<ul id="carInfo" class="col-xs-12">

		</ul>
		<div class="col-xs-12 text-center paddingTBLR5_2 ">
				<input type="button" onclick="tabClick(1)" value="外观设计投票" class="col-xs-1 mybtn btn-pink" />
				<input type="button" onclick="tabClick(2)" value="空间设计投票" class="col-xs-1 mybtn btn-pink" />
				<input type="button" onclick="tabClick(3)" value="内饰设计投票" class="col-xs-1 mybtn btn-pink" />
		</div>
	<Iframe id="hideCarMovie" width=655 height=377  style="display: none;" src="asyn/index/player/1" ></iframe>
</body>
<script type="text/javascript">
	function showMovie(id){
		$("#hideCarMovie").attr('src',"asyn/index/player/"+id); 
		layer.open({
			type: 1,
			closeBtn: false,
			title: false,
			shadeClose: true,
			area: ['655px', '377px'], //宽高
			content: $('#hideCarMovie')
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
					//if(groupId != carInfo[index].carGroupId){
					//	var breakUp = '<li class="col-xs-12 paddingTBLR5_3">&nbsp;</li>';
					//	var groupLi = '<li class="col-xs-12 text-center"><span style="font-size: 50px;">' + carInfo[index].carGroup + '</span></li>';
					//	$("#carInfo").append(breakUp).append(groupLi);
					//	groupId = carInfo[index].carGroupId;
					//}
					
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
							//var img = '<div onclick="showMovie()" class="swiper-slide"><img class="col-xs-12 paddingTBLR5_3" alt="" src="' + carImgs[i] + '"></div>';
							var img = '<div onclick="showMovie('+carInfo[index].carId+')" class="swiper-slide"><img class="col-xs-12 paddingTBLR5_3" alt="" src="' + carImgs[i] + '"></div>';
							//var embed = '<embed src='http://player.youku.com/player.php/sid/XMjYxMTMxMTUzMg==/v.swf' allowFullScreen='true' quality='high' width='480' height='400' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed>'

							imgHtml = imgHtml + img;
						}
						imgHtml = imgHtml + swiperHtmlEnd;
					}
					carLi = carLi + imgHtml;
					carLi = carLi + '<div class="col-xs-12 text-center paddingTBLR15_3">'
									 +     '<span class="col-xs-5 text-right paddingTBLR5_3 textover" style="font-size: 15px;">'  +carInfo[index].carGroup + carInfo[index].carName + '</span>'
									 //+ 	   '<div class="col-xs-5 text-left paddingTBLR5_3 textover">当前票数: <span id="clickSpan_' + carInfo[index].carId + '">' + carInfo[index].ticketNum + '</span> 票</div>'
									 //+ 	   '<div class="col-xs-2 text-left">'
									 //+         '<input id="clickInput_' + carInfo[index].carId + '" mes="0" group="group' + carInfo[index].carGroupId + '" onclick="clickTicket(\'' + carInfo[index].carId + '\')" class="btn-green mybtn paddingTBLR5_3" type="button" value="投票"/>'
									 //+     '</div>'
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