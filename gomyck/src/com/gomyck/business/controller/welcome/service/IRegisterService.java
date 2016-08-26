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

import com.gomyck.business.model.entity.welcome.BizUser;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IRegisterService
{
    void saveUser(BizUser user);
}
