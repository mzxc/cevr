/*
 * 文 件 名:  CkXmlNodess.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-24
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.component.core.xml;

/**
 * xml节点通用接口
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-24]
 * @see #CkXmlNodes
 * @since 1.0
 */
public abstract class CkXmlNodes implements XmlNodes {
    @Override
    public abstract void setXML_TAGS(final Object xml_tags);
    
    @Override
    public abstract String getElementValue(final String propName, final String elId);
    
}
