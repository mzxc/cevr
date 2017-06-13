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
</head>
<body>
<input id="ticketType" type="hidden" value="${tab}" />
<input id="imgType" type="hidden" value="${tab}" />
<input id="ifInputUserInfo" type="hidden" value="0" />
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
             	 外观设计评价指标:针对车辆工艺、造型、外观部件等进行综合评价。
                <p>1、工艺设计：发动机盖、后尾箱及车门贴合度、接缝大小、钣金工艺、焊点平整度</p>
                <p>2、造型设计：车头、车位、车身整体造型、设计；</p>
                <p>3、侧面设计：反光镜、车门设计、轮圈设计方面；</p>
                <p>4、顶部设计：顶部做工设计、钣金材料；</p>
                <p>5、灯具设计：前照灯、尾灯、刹车灯、转型等、LED日间行车灯、雾灯设计</p>

            </div>
        </div>
    </div>
</header>
<section class="content-warp">
    <div id="carInfo" class="container toupiao">
        <div class="row">
            <div id="showTitle" class="c-title">参赛车型外观设计投票</div>
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
    <img src="source/images/er.jpg" alt="" style="width: 135px;height: 135px"/>
    <p class="text-center" style="color: #FFFFFF">关&nbsp;注&nbsp;我&nbsp;们&nbsp;了&nbsp;解</br>更&nbsp;多&nbsp;赛&nbsp;事&nbsp;详&nbsp;情</p>
</div>
<div class="modal fade" id="ticketUserInfo" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: none">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4></h4>
            </div>
            <div class="modal-body" style="padding: 0 100px;">
                <div class="row">
                    <p style="text-align: center;color: #0f0856;margin-bottom: 20px;font-size: 20px">填写真实电话参与抽奖</p>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="userTel" class="col-sm-4 control-label text-right" style="height: 34px;line-height: 34px;">手机号：</label>
                        <div class="col-sm-8">
                            <input type="mobile" class="form-control" id="userTel" placeholder="手机号">
                        </div>
                    </div>
                </div>
                <div class="row text-center" style="margin: 20px 0">
                    <a onclick="sureUserInfo()" class="c-button" style="display:inline-block;color: #000001;padding:6px 42px;;background: #fdee03;border-radius: 5px;">确&nbsp;&nbsp;认</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	$(function(){
		initCarInfo();
		if('${tab}'=='1'){
			$("#showTitle").text("赛车车型外观设计投票");
		}else if('${tab}'=='2'){
			$("#showTitle").text("赛车车型空间设计投票");
		}else{
			$("#showTitle").text("赛车车型内饰设计投票");
		}
		$("#userTel").ckMustNumber();
		$("#userTel").ckMaxLength(11);
		$("#ifInputUserInfo").val("0");
	});
	
	var showDivCarId;
	function clickTicket(carId){
		showDivCarId = carId;
		if($("#ifInputUserInfo").val() == "0"){
			$("#ticketUserInfo").modal('show');
			$("#userTel").focus();
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
		if(!$.ckIsMobile("userTel") && !$.ckIsEmpty($("#userTel").val())){
			layer.msg("请输入正确的手机号");
			return;
		}
		$("#ifInputUserInfo").val("1");
		changeButton(showDivCarId);
	}
	
	function sureTicket(carId){
		var index = layer.load(1, {
		  shade: [0.1,'#fff']
		});
		$("#ticketUserInfo").modal('hide');
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
				layer.close(index);
				if(result.result){
					var _input = $("#clickInput_" + carId);
					_input.text("取消投票");
					_input.attr("mes", "1");
					_input.addClass("active");
					var _span = $("#clickSpan_" + carId);
					_span.text($.ckAdd(_span.text(), 1));
					var group = _input.attr("group");
					$("button[group='" + group + "']").not(_input).attr("disabled", "disabled");
					$("button[group='" + group + "']").not(_input).attr("value", "投票");
					layer.msg("投票成功");
				}else{
					layer.msg(result.msg);
				}
			},
			error: function(){
				layer.close(index);
				layer.msg("服务器开小差，请稍后再试!");
			}
		});
	}
	
	function unSureTicket(carId){
		var index = layer.load(1, {
		  shade: [0.1,'#fff']
		});
		var _input = $("#clickInput_" + carId);
		var ticketType = $("#ticketType").val();
		$.ajax({
			url: "asyn/index/unTicketCar",
			data: {carId: carId, ticketTypeId: ticketType},
			type: "post",
			dataType: "json",
			success: function(result){
				layer.close(index);
				if(result.result){
					_input.text("投票");
					_input.attr("mes", "0");
					_input.removeClass("active");
					var _span = $("#clickSpan_" + carId);
					_span.text($.ckSub(_span.text(), 1));
					var group = _input.attr("group");
					$("button[group='" + group + "']").removeAttr("disabled");
				}else{
					layer.msg(result.msg);
				}
			},
			error: function(){
				layer.close(index);
				layer.msg("服务器开小差，请稍后再试!");
			}
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
				var carInfoDiv = "";
				for(var index = 0; index < carInfo.length; index = index + 1){
					if(groupId != carInfo[index].carGroupId){
						var groupDiv = '<div class="row"><div class="c-title">' + carInfo[index].carGroup + '</div></div>';
						$("#carInfo").append(groupDiv);
						carInfoDiv = carInfoDiv + '<div class="row c-layout">';
						groupId = carInfo[index].carGroupId;
					}
					carInfoDiv = carInfoDiv +  '<div class="col-sm-3 text-center" >'
					                + '<p>' +carInfo[index].carGroup + '  ' + carInfo[index].carNo + '  ' + carInfo[index].carName + '</p>'
					                + '<p><span id="clickSpan_' + carInfo[index].carId + '">' + carInfo[index].ticketNum + '</span>票</p>'
					                + '<button id="clickInput_' + carInfo[index].carId + '" mes="0" group="group' + carInfo[index].carGroupId + '" onclick="clickTicket(\'' + carInfo[index].carId + '\')">投票</button>'
		            		     + '</div>';
		            if(index < carInfo.length - 1 &&  groupId != carInfo[(index + 1)].carGroupId){
		            	carInfoDiv = carInfoDiv + '</div>';
		            	$("#carInfo").append(carInfoDiv);
		            	carInfoDiv = '';
		            }else if(index == carInfo.length - 1){
		            	carInfoDiv = carInfoDiv + '</div>';
		            	$("#carInfo").append(carInfoDiv);
		            	carInfoDiv = '';
		            }
				}
			}
		});
	}
</script>
</html>