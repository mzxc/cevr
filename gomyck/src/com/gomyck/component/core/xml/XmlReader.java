/*
 * 文 件 名:  XmlReader.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.component.core.xml;

import org.w3c.dom.Document;

/**
 * xml加载器
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface XmlReader
{
    public Document loadXml();
    
}
