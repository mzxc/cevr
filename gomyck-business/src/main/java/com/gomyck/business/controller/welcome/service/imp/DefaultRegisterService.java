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
package com.gomyck.business.controller.welcome.service.imp;

import org.springframework.stereotype.Service;

import com.gomyck.business.controller.welcome.service.IRegisterService;
import com.gomyck.component.core.dao.BaseDao;

/**
 * 注册ser
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see #saveObj
 * @since 1.0
 */
@Service(value = "DefaultRegisterService")
public class DefaultRegisterService extends BaseDao implements IRegisterService
{

	@Override
	public void regUser(Object... objs) {
		for(Object obj : objs){
			this.save(obj);
		}
	}
	
}
