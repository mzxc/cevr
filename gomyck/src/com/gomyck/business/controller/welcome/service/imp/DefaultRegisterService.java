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
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "DefaultRegisterService")
public class DefaultRegisterService extends BaseDao implements IRegisterService
{
    /**
     * 重载方法
     * 
     * @param user
     */
    @Override
    public void save(final Object obj)
    {
        this.save(obj);
    }
}
