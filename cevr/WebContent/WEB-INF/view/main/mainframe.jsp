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
<link href="source/style/css/style.css" rel="stylesheet" type="text/css">
<link href="source/style/css/swiper.min.css" rel="stylesheet" type="text/css">
<link href="source/plugins/layer/skin/layer.css" rel="stylesheet" type="text/css">
<link href="source/style/css/styleall.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="source/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="source/plugins/layer/layer.js"></script>
<script type="text/javascript" src="source/plugins/swiper/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="source/script/ckUI/ckUI.js"></script>
<script type="text/javascript" src="source/plugins/bootstrap/js/bootstrap.min.js"></script>
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
	<header class="main-header">
	    <div class="container">
	        <div class="row">
	            <div class="logo">
	                <img src="source/images/logo.png" alt=""/>
	            </div>
	        </div>
	        <div class="row">
	            <div class="col-sm-10 h-title">
	                2017环青海湖（国际）电动汽车挑战赛参赛车型主观评测项目网络投票活动 <span>投票时间：2017.6.16-20</span>
	            </div>
	            <div class="col-sm-12 h-info">
	                活动说明：2017CEVR电动汽车挑战赛有人气更接地气！赛事14项专业评测项目中外观设计，空间设计、内饰设计3个评测项目，将进行VR视频网络互动投票。          360°全景视频体验，让您全面了解参赛车型的各个细节，您的投票分数将加权计入最终评分，谁能拿到大奖，也有您的功劳哦~把握您的网络评委权利，动动手指，选出你心目中的各项最佳车型吧~              成为网络评委，还有福利哦~将抽取30名随机赠送参赛电动汽车模型及精美周边纪念品（温馨提示：请您填写真实手机号，以便获奖后客服与您及时联系，获奖名单将在6月23号后，于环青海湖国际电动汽车挑战赛的官方微信公众号公布。
	
	            </div>
	        </div>
	    </div>
	</header>
	<!-- <embed width="640" height="360" allowfullscreen="true" name="ExternalInterfaceExample" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" allowscriptaccess="always" quality="high" src="http://data.cnlive.com/export/CNLivePlayer.swf?hasBorder=false&amp;uuid=69299e499d55424ca8b300a9978e60a5" wmode="transparent">-->

	<section class="content-warp">
    <div class="container">
    	<div class="row">
            <div class="c-title">参赛车型360全景视频展示</div>
        </div>
        <div id="car_show" class="row c-layout">
        </div>
         <div class="row c-link">
            <div class="col-sm-4">
                <a href="javascript:void(0)" onclick="tabClick(1)" class="c-button">外观设计投票</a>
            </div>
            <div class="col-sm-4">
                <a href="javascript:void(0)" onclick="tabClick(2)" class="c-button">空间设计投票</a>
            </div>
            <div class="col-sm-4">
                <a href="javascript:void(0)" onclick="tabClick(3)" class="c-button">内饰设计投票</a>
            </div>
        </div>
	<input id="ticketType" type="hidden" value="1" />
	<input id="imgType" type="hidden" value="1" />
	<!-- 
		<ul id="carInfo" class="col-xs-12">
		</ul>
		<div class="col-xs-12 text-center paddingTBLR5_2 ">
				<input type="button" onclick="tabClick(1)" value="外观设计投票" class="col-xs-1 mybtn btn-pink" />
				<input type="button" onclick="tabClick(2)" value="空间设计投票" class="col-xs-1 mybtn btn-pink" />
				<input type="button" onclick="tabClick(3)" value="内饰设计投票" class="col-xs-1 mybtn btn-pink" />
		</div>
		<Iframe id="hideCarMovie" width=655 height=377  style="display: none;" src="asyn/index/player/1" ></iframe>
	
	
	-->
	    </div>
	    
</section>
<footer>
    <div class="container">
        <p class="f-title">
            科技让生活更美好！
        </p>
        <p>感谢您的参与</p>
        <p>诚邀您关注第四届环青海湖(国际)电动汽车挑战赛！</p>
    </div>

</footer>
<div class="fix" style="width: 135px;position: fixed;top: 30%;right: 10px;">
    <img src="./source/images/er.jpg" alt="" style="width: 135px;height: 135px"/>
    <p class="text-center" style="color: #FFFFFF">关&nbsp;注&nbsp;我&nbsp;们&nbsp;了&nbsp;解</br>更&nbsp;多&nbsp;赛&nbsp;事&nbsp;详&nbsp;情</p>
</div>
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
					
					var cardiv = '<div class="col-sm-3" videoid="XMjc3OTM0MDc2MA">';
					var imgHtml = "";
					if($.ckIsEmpty(carInfo[index].carImgs)){
						imgHtml = '<img onclick="showMovie()" src="' + carInfo[index].carImgs + '">';
					}else{
						var carImgs = carInfo[index].carImgs.split("|");
						var swiperHtmlHead = '<div class="swiper-container"><div class="swiper-wrapper">'
						var swiperHtmlEnd = '</div></div>';
						imgHtml = imgHtml + swiperHtmlHead;
						for(var i = 0; i < carImgs.length;i = i + 1){
							//var img = '<div onclick="showMovie()" class="swiper-slide"><img class="col-xs-12 paddingTBLR5_3" alt="" src="' + carImgs[i] + '"></div>';
							var img = '<div onclick="showMovie('+carInfo[index].carId+')" class="swiper-slide"><img alt="" src="' + carImgs[i] + '"></div>';
							//var embed = '<embed src='http://player.youku.com/player.php/sid/XMjYxMTMxMTUzMg==/v.swf' allowFullScreen='true' quality='high' width='480' height='400' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed>'

							imgHtml = imgHtml + img;
						}
						imgHtml = imgHtml + swiperHtmlEnd;
					}
					cardiv = cardiv + imgHtml;
					cardiv = cardiv 
									//+ '<div class="col-xs-12 text-center paddingTBLR15_3">'
									 +     '<p>'  +carInfo[index].carGroup + carInfo[index].carName + '</p>'
									 //+ 	   '<div class="col-xs-5 text-left paddingTBLR5_3 textover">当前票数: <span id="clickSpan_' + carInfo[index].carId + '">' + carInfo[index].ticketNum + '</span> 票</div>'
									 //+ 	   '<div class="col-xs-2 text-left">'
									 //+         '<input id="clickInput_' + carInfo[index].carId + '" mes="0" group="group' + carInfo[index].carGroupId + '" onclick="clickTicket(\'' + carInfo[index].carId + '\')" class="btn-green mybtn paddingTBLR5_3" type="button" value="投票"/>'
									 //+     '</div>'
									// + '</div>'
								+ '</div>';
					$("#car_show").append(cardiv); 
				}
				//var mySwiper = new Swiper('.swiper-container', {
				//	centeredSlides: true,
			    //    autoplay: 4500,
			    //    loop:true,
			    //    autoplayDisableOnInteraction: false
				//})
			}
		});
	}
</script>
</html>