/*
 * 文 件 名:  DefaultRegisterService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cevr.business.controller.welcome.service.imp;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;

import com.cevr.business.controller.common.message.ResultMessage;
import com.cevr.business.controller.welcome.service.IIndexService;
import com.cevr.business.model.entity.BizCar;
import com.cevr.business.model.entity.BizTicket;
import com.cevr.business.model.entity.BizTicketPeople;
import com.cevr.business.model.to.TicketInfo;
import com.cevr.component.core.dao.BaseDao;
import com.cevr.component.core.xml.context.CkXmlGetter;
import com.cevr.component.logger.NestLogger;
import com.cevr.component.util.DateUtil;
import com.cevr.component.util.IdUtil;

/**
 * 注册ser
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see #saveObj
 * @since 1.0
 */
@Service(value = "DefaultIndexServiceImp")
public class DefaultIndexServiceImp extends BaseDao implements IIndexService
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> searchCarInfo() {
		// TODO 查询车辆信息
		String sql = CkXmlGetter.getXmlNodes("sql", "serachAll_car");
		return this.createSqlQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		//return this.findAll(BizCar.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultMessage addTicketInfo(TicketInfo ti) {
		// TODO 新增投票信息,先判断是否投过 啊啊
		try{
			BizCar bc = (BizCar)this.findByPrimaryKey(BizCar.class, ti.getCarId());
			String sql = CkXmlGetter.getXmlNodes("sql", "findTicketInfo");
			BigInteger num = (BigInteger)this.createSqlQuery(sql, DateUtil.getDate(DateUtil.nowStr("yyyy-MM-dd"), "yyyy-MM-dd"), ti.getFromIp(), bc.getGroupId()).uniqueResult();
			if(num != null && num.intValue() > 0){
				return ResultMessage.initMsg(false, "3000", "您今日已为该组投票，请明天再来吧");
			}
		}catch(Exception e){
			NestLogger.showException(e);
			return ResultMessage.initMsg(false, "3000", "您已经投过改组了，请明天再来吧");
		}
		Map<String, Object> param = this.initParams();
		param.put("userName", ti.getUserName());
		param.put("userTel", ti.getUserTel());
		List<BizTicketPeople> btps = (List<BizTicketPeople>)this.findByProperties(BizTicketPeople.class, param);
		BizTicketPeople btp = null;
		if(btps == null || btps.size() < 1){
			btp = new BizTicketPeople(IdUtil.getUUID(), ti.getUserName(), ti.getUserTel(), "0", ti.getUserEmail(), "0", "0", "0", ti.getTicketTime(), ti.getTicketTime(), "0000");
			try{
				this.save(btp);
			}catch(Exception e){
				NestLogger.showException(e);
				return ResultMessage.initMsg(false, "5001", "新增投票人信息失败");
			}
		}else{
			btp = btps.get(0);
		}
		BizTicket bt = new BizTicket(IdUtil.getUUID(), ti.getFromIp() ,ti.getTicketTime(), ti.getTicketNum(), btp.getId(), ti.getCarId(), "0", "0", "0", ti.getTicketTime(), ti.getTicketTime(), "0000");
		try{
			this.save(bt);
		}catch(Exception e){
			NestLogger.showException(e);
			return ResultMessage.initMsg(false, "5002", "新增投票信息失败");
		}
		return ResultMessage.initMsg(true, "2000", "投票成功");
	}

	@Override
	public ResultMessage delTicketInfo(TicketInfo ti) {
		// TODO 取消投票
		try{
			Map<String, Object> param = this.initParams();
			Map<String, Object> ticketTime = new HashMap<String, Object>();
			ticketTime.put(SYMBOL, ">");
			ticketTime.put(VALUE, DateUtil.getDate(DateUtil.nowStr("yyyy-MM-dd"), "yyyy-MM-dd"));
			param.put("fromIp", ti.getFromIp());
			param.put("clickTime", ticketTime);
			param.put("cancleflag", "0");
			param.put("clickCarId", ti.getCarId());
			BizTicket bt = (BizTicket)this.findByProperties(BizTicket.class, param).get(0);
			bt.setCancleflag("1");
			bt.setUpdatetime(new Date());
			bt.setOperatorId("0000");
			this.update(bt);
			return ResultMessage.initMsg(true, "2000", "取消成功");
		}catch(Exception e){
			return ResultMessage.initMsg(false, "5000", "不可取消");
		}
	}
}
