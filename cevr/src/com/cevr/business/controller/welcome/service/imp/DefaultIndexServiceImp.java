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

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;

import com.cevr.business.controller.welcome.service.IIndexService;
import com.cevr.component.core.dao.BaseDao;
import com.cevr.component.core.xml.context.CkXmlGetter;

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

	@Override
	public List<Map<String, Object>> searchCarInfo() {
		String sql = CkXmlGetter.getXmlNodes("sql", "serachAll_car");
		return this.createSqlQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		//return this.findAll(BizCar.class);
	}
	
}
