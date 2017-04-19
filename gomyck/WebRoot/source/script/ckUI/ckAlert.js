/**
 * 弹出层插件
 * auth:h_yang
 * version: 2.0
 * 更新日志: 2017-01-25 加入自定义按钮 目前仅支持俩个 属性中加入button : {left :"", right : ""}
 * 更新日志: 2017-03-11 加入目标元素绑定, target属性: 可以为元素ID  也可以为元素JQUERY对象  加入speed属性  为弹出速度
 * 更新日志: 2017-03-14 修复了弹出层的覆盖BUG
 */
$.extend({
	ckAlertIndex : 0,
	ckAlert : function(_option){
		var urlArr = window.location.href.split("/");
		var url = urlArr[0] + "//" + urlArr[1] + urlArr[2] + "/" + urlArr[3];
		var _tempOption = {
			info : (typeof _option == "string" || typeof _option == "number") ? _option : "", // 提示信息
			type : 1,               // 显示类型1:弹出框 2:提示确定 默认为1
			icon : 1,               // 显示图标,默认是1
			button : {left : "取消", right : "确定"},
			onSure: function(ifClose) {    // 点击确定回调
				// do somthing
			},
			onRefuse : function(ifClose){	// 点击取消回调
				// do somthing
			},
			speed  : 200,  //弹出速度
			target : null  //指定元素ID或jquery对象
		}
		// 以下为层级坐标__不要动
		var index = $.ckAlertIndex;
		$.ckAlertIndex = Number($.ckAlertIndex) + 1;
		// 以上为层级坐标__不要动
		var _thisOption = $.extend(true,{},_tempOption, _option);
		//CSS 开始
		var style_main       = 'style="width: 100%; height: 100%; position: fixed; background: rgba(0,0,0,0.4); top:0; z-index: 999;display: none;"';
		var style_alert_main = 'style="background:#fff;position:absolute;top:30%;left:0;right:0;margin:0 auto;border-radius: 5px;"';
		var style_btn_left       = 'style="padding:15px 15%; text-align: center;border-bottom-left-radius : 5px;background: #bebebe;color: #fff;border: 1px solid #bebebe;"';
		var style_btn_right      = 'style="padding:15px 15%; text-align: center;border-bottom-right-radius: 5px;' + (_thisOption.type == 1 ? "border-bottom-left-radius: 5px;" : "") + 'background: #ff6d60;color: #fff;border: 1px solid #ff6d60;"';
		var style_padding_TB_10 = 'style="padding:10px 0;"';
		var style_padding_TB_20 = 'style="padding:20px 0;"';
		var style_padding_TB_40 = 'style="padding:40px 0;"color:#000;';
		//CSS 结束
		var sure_btn   = "";//按钮1
		var refuse_btn = "";//按钮2
		switch(_thisOption.type){
			case 2 : size =  6;refuse_btn = '<li id="onRefuse' + index + '" class="col-xs-6"><button class="col-xs-12" ' + style_btn_left + '><h4>' + _thisOption.button.left + '</h4></button></li>';
			case 1 : size = (_thisOption.type == 1 ? 12 : 6);sure_btn   = '<li id="onSure'   + index + '" class="col-xs-' + size + '"><button class="col-xs-12" ' + style_btn_right + '><h4>' + _thisOption.button.right + '</h4></button></li>';break;
		}
		var context = 
			'<li class="col-xs-12" ' + style_padding_TB_10 + '></li>'
			+ '<li id="alertInfo" class="col-xs-12" ' + style_padding_TB_40 + '>'
			+ '<h4>' + _thisOption.info + '</h4>'
			+ '</li>'
			+ '<li class="col-xs-12 paddingTB10"></li>';
		var _targetHtml = "";
		if(_thisOption.target != null){
			var _targetHtml = $.ckGetSomeThing(_thisOption.target)[0].prop("outerHTML");
			context = '<li class="col-xs-12" ' + style_padding_TB_10 + '></li>';
			context = context + _targetHtml;
			context = context + '<li class="col-xs-12" ' + style_padding_TB_10 + '></li>';
			$.ckGetSomeThing(_thisOption.target)[0].andSelf().remove();
		}
		$("body").append(
			'<div id="showInfo" mes="' + index + '" ' + style_main + '>'
			+ '<ul class="col-xs-10 text-center" ' + style_alert_main + '>'
				+ context
				+ refuse_btn
				+ sure_btn
			+ '</ul>'
			+ '</div>'
		);
		// 确定回调
		$("#onSure" + index).bind("click", function(){
			var ifClose = _thisOption.onSure();
			if(ifClose == false){
				return;
			}
			if(_thisOption.target != null){
				$("body").append(_targetHtml);
			}
			$("div[mes='"+ index +"']").animate({top:"-100%"}, _thisOption.speed, function(){
				$("div[mes='"+ index +"']").hide().remove();
			});
		});
		// 失败回调
		$("#onRefuse" + index).bind("click", function(){
			var ifClose = _thisOption.onRefuse();
			if(ifClose == false){
				return;
			}
			if(_thisOption.target != null){
				$("body").append(_targetHtml);
			}
			$("div[mes='"+ index +"']").animate({top:"-100%"}, _thisOption.speed, function(){
				$("div[mes='"+ index +"']").hide().remove();
			});
		});
		if(_thisOption.target != null){
			$.ckGetSomeThing(_thisOption.target)[0].show();
		}
		$("div[mes='"+ index +"']").css({display:"block",top:"-100%"});
		$("div[mes='"+ index +"']").animate({top:"0"}, _thisOption.speed);
	}
});
window._alert = window.alert // 临时保存一下；
window.alert = $.ckAlert;