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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cevr.business.controller.welcome.service.IIndexService;
import com.cevr.business.model.entity.BizCar;
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
	
	@RequestMapping(value="getCarInfo", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCarInfo(){
		List<BizCar> searchCarInfo = iis.searchCarInfo();
		return ResultBuild.init(true, "查询成功", searchCarInfo);
	}
}
