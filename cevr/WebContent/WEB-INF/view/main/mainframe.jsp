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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>环青海湖（国际）电动汽车挑战赛|QingHai Lake China</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
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
<script>
	
</script>
</head>
<body>
	<input id="ticketType" type="hidden" value="1" />
	<input id="imgType" type="hidden" value="1" />
	<header class="main-header">
	    <div class="container">
	        <div class="row">
	            <div class="logo">
	                <img src="source/images/logo.png" alt=""/>
	            </div>
	        </div>
	        <div class="row">
	        <div class="col-sm-10 h-title">
                2017环青海湖（国际）电动汽车挑战赛参赛车型主观评测项目网络投票活动 <span>投票时间：2017.6.17-20</span>
            </div>
            <div class="col-sm-12 h-info">
                <span style="font-weight: 600">活动说明：</span> 2017CEVR电动汽车挑战赛有人气更接地气！赛事14项专业评测项目中的外观设计、空间设计、内饰设计3个评测项目将进行VR视频网络互动投票。360°全景视频体验，让您全面了解参赛车型各个细节，您的投票分数将加权计入最终评分，谁能拿到大奖，也有您的功劳哦~把握您的网络评委权利，动动手指选出心目中的各项最佳车型吧~参与投票，还有福利哦~我们将抽取30名幸运网络评委随机赠送参赛电动汽车模型及精美周边纪念品。（温馨提示：请您填写真实手机号，以便获奖后客服与您及时联系，获奖名单将在6月23号后于环青海湖国际电动汽车挑战赛的官方微信公众号公布。）

            </div>
	        </div>
	    </div>
	</header>
<section class="content-warp">
    <div class="container">
    	<div class="row">
            <div class="c-title">参赛车型360°全景视频展示</div>
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
    <img src="source/images/er.jpg" alt="" style="width: 135px;height: 135px;z-index: 999"/>
    <p class="text-center" style="color: #FFFFFF">关&nbsp;注&nbsp;我&nbsp;们&nbsp;了&nbsp;解</br>更&nbsp;多&nbsp;赛&nbsp;事&nbsp;详&nbsp;情</p>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4></h4>
            </div>
            <div class="modal-body">
                
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
<script type="text/javascript">
	$(function(){
		initCarInfo();
	});
	function tabClick(v){
		//window.location.href='${basePath}asyn/index/voteframe/'+v;
		top.window.location ='${basePath}asyn/index/voteframe/'+v;
	}

	function showMovie(_this){
		$('#myModal').empty();
		$('#myModal').modal('show');
		var videoid = $(_this).attr('videoid');
		var videoinfo = $(_this).find('p').text();
		var objectHtml = '<iframe height=498 width=510 src="' + videoid + '" frameborder=0 "allowfullscreen"></iframe>';
		$('.modal-body').append(objectHtml);
		$('.modal-header h4').text(videoinfo);
	}

	function initCarInfo(){
		$.ajax({
			url: "asyn/index/getCarInfo",
			type: "post",
			data: {ticketTypeId: $("#ticketType").val(), imgType: $("#imgType").val()},
			dataType: "json",
			success: function(reuslt){
				var carInfo = reuslt.data;
				var carInfos = "";
				var divPrefix = '<div class="col-sm-6 clearfix">';
				var divSuffix = '</div>';
				for(var index = 0; index < carInfo.length; index = index + 1){
					if(index ==0 || (index)%2==0){
						carInfos = carInfos + divPrefix;
					}
					carInfos = carInfos + '<div onclick="showMovie(this)" class="v-layout" videoid="'+carInfo[index].src+'">';
					var imgHtml = "";
					if($.ckIsEmpty(carInfo[index].carImgs)){
						imgHtml = '<img src="' + carInfo[index].carImgs + '">';
					}else{
						var carImgs = carInfo[index].carImgs.split("|");
						//var swiperHtmlHead = '<div class="swiper-container"><div class="swiper-wrapper">'
						//var swiperHtmlEnd = '</div></div>';
						//imgHtml = imgHtml + swiperHtmlHead;
						for(var i = 0; i < carImgs.length;i = i + 1){
							var img = '<div><img  alt="" src="' + carImgs[i] + '"></div>';
							imgHtml = imgHtml + img;
							break;
						}
						//imgHtml = imgHtml + swiperHtmlEnd;
					}
					carInfos = carInfos + imgHtml;
					//carInfos = carInfos + '<p>'  + carInfo[index].carGroup + "  " + carInfo[index].carNo + "  " + carInfo[index].carName + '</p></div>';
					carInfos = carInfos + '<p>'  + carInfo[index].carGroup + " " + carInfo[index].carName + '</p></div>';

					if(index !=0 && (index)%2==1){
						carInfos = carInfos + divSuffix;
					}
				}
				$("#car_show").append(carInfos); 
				/* var mySwiper = new Swiper('.swiper-container', {
					centeredSlides: true,
			        autoplay: 4500,
			        loop:true,
			        autoplayDisableOnInteraction: false
				}); */
			}
		});
	}
</script>
</html>