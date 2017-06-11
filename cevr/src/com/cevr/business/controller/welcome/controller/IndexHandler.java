/*
 * 文 件 名:  IndexHandler.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-3
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cevr.business.controller.welcome.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cevr.business.controller.common.message.ResultMessage;
import com.cevr.business.controller.welcome.service.IIndexService;
import com.cevr.business.interceptor.LogInfo;
import com.cevr.business.model.to.TicketInfo;
import com.cevr.component.util.IpUtil;
import com.cevr.component.util.ResultBuild;

/**
 * 首页
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-3]
 * @see IndexHandler
 * @since 1.0
 */
@Controller
@RequestMapping(value = "asyn/index")
public class IndexHandler {
	
	@Autowired
	@Qualifier("DefaultIndexServiceImp")
	private IIndexService iis;
	
	@LogInfo(operateModelNm="投票首页", operateFuncNm="访问首页")
	@RequestMapping(value="getCarInfo", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCarInfo(TicketInfo ti){
		System.out.println(ti.getTicketTypeId());
		List<Map<String, Object>> searchCarInfo = iis.searchCarInfo(ti);
		return ResultBuild.init(true, "查询成功", searchCarInfo);
	}
//	@LogInfo(operateModelNm="投票首页", operateFuncNm="访问首页")
//	@RequestMapping(value="tab/{tab}", method=RequestMethod.GET)
//	public String tab(@PathVariable String tab){
//		System.out.println(tab);
//		//List<Map<String, Object>> searchCarInfo = iis.searchCarInfo(ti);
//    	
//		//response.sendRedirect(request.getContextPath() + "/common/forward/main/mainframe");
//		return "main/exteriorDesign";
//	}
	@LogInfo(operateModelNm="投票首页", operateFuncNm="新增投票")
	@RequestMapping(value="ticketCar", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ticketCar(TicketInfo ti, HttpServletRequest request){
		ti.setFromIp(IpUtil.getRemoteAddr(request));
		ti.setTicketNum(1);
		ResultMessage rm = iis.addTicketInfo(ti);
		return ResultBuild.init(rm.isIfSuccess(), rm.getMsg(), rm);
	}
    
    @LogInfo(operateModelNm = "投票首页", operateFuncNm = "带参数页面通用跳转")
    @RequestMapping(value = "voteframe/{tab}", method = RequestMethod.GET)
    public String gotoVoteframe(HttpServletRequest request, HttpServletResponse response,@PathVariable final String tab)
    {
		request.setAttribute("tab", tab);
        return "main/voteframe";
    }
    
    @LogInfo(operateModelNm = "投票首页", operateFuncNm = "带参数页面通用跳转")
    @RequestMapping(value = "player/{id}", method = RequestMethod.GET)
    public String gotoPlayer(HttpServletRequest request, HttpServletResponse response,@PathVariable final String id)
    {
		request.setAttribute("src", "http://data.cnlive.com/export/CNLivePlayer.swf?hasBorder=false&amp;uuid=58b6bbf6ddd3426abbeb42c631ac43fe");
        return "main/playerJsp";
    }
    
	@LogInfo(operateModelNm="投票首页", operateFuncNm="删除投票")
	@RequestMapping(value="unTicketCar", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unTicketCar(TicketInfo ti, HttpServletRequest request){
		ti.setFromIp(IpUtil.getRemoteAddr(request));
		ti.setTicketNum(1);
		ResultMessage rm = iis.delTicketInfo(ti);
		return ResultBuild.init(rm.isIfSuccess(), rm.getMsg(), rm);
	}
}
