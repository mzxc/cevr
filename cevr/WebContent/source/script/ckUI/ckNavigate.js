$(function(){
	var location = window.location.href;
	if(location.toLowerCase().indexOf("index") <= 0 && location.toLowerCase().indexOf("/selectUserSharePromotion") <= 0 && location.toLowerCase().indexOf("/proinfo") <= 0 && location.toLowerCase().indexOf("/gotoupdateapk") <= 0){
		var url = $.ckGetContext() + "/resources/";
		var html = '<ul class="col-xs-7 flexAlign" style="position:fixed;bottom:15%;right:-48.6%;z-index:911">'
					+'    <li class="col-xs-2 fastbtn"><img src="' + url + 'image/fastbtn.png" alt="" width="100%"/></li>'
					+'    <li class="col-xs-10 text-center bgpink paddingTB5" style="border-top-left-radius: 5px;border-bottom-left-radius: 5px;">'
					+'        <div onclick="$.ckGotoView(\'index\')" class="col-xs-4" style="padding:5px 0;font-size:0.15rem;"><img style="margin-bottom:0.05rem;" src="' + url + 'image/footer1.png" alt=""\ width="35%"/><br/>本天首页</div>'
					+'        <div onclick="$.ckGotoView(\'my/myIndex\')" class="col-xs-4" style="padding:5px 0;font-size:0.15rem;"><img style="margin-bottom:0.05rem;" src="' + url + 'image/footer5.png" alt=""\ width="35%"/><br/>个人中心</div>'
					+'        <div onclick="history.go(-1)" class="col-xs-4" style="padding:5px 0;font-size:0.15rem;"><img style="margin-bottom:0.05rem;" src="' + url + 'image/returnup.png" alt=""\ width="35%"/><br/>后&nbsp;退</div>'
					+'    </li>'
					+'</ul>'
					+'<div class="blackfade" style="width:100%;height:100%;background: rgba(0,0,0,0.3);position:fixed;top:0;z-index:910;display: none;"></div>';
		$("body").append(html);
	}
	
    var nextall=$(".fastbtn").next().width()+1;
    var i=0;
    $(".fastbtn").click(function(){
        if(i==0)
        {
            $(".blackfade").css({display:"block"});
            $(this).parent().animate({
                right:"0"
            }, 180)
            i=1;
        }
        else
        {
            $(".blackfade").css({display:"none"});
            $(this).parent().animate({
                right:-nextall
            }, 180)
            i=0;
        }
    })
});