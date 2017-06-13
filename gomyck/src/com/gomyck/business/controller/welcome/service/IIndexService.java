/*
 * 文 件 名:  IRegisterService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.business.controller.welcome.service;

import java.util.List;
import java.util.Map;

import com.gomyck.business.controller.common.message.ResultMessage;
import com.gomyck.business.model.entity.BizCarVideo1004;
import com.gomyck.business.model.to.TicketInfo;

/**
 * 投票server
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see #saveObj
 * @since 1.0
 */
public interface IIndexService
{
	/**
	 * 查询所有车辆信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> searchCarInfo(TicketInfo ti);
	
	BizCarVideo1004 findVideoByCarId(TicketInfo ti);
	
	/**
	 * 新增投票
	 * 
	 * @param ti
	 * @return
	 */
	ResultMessage addTicketInfo(TicketInfo ti);
	
	/**
	 * 删除投票
	 * 
	 * @param ti
	 * @return
	 */
	ResultMessage delTicketInfo(TicketInfo ti);
}
