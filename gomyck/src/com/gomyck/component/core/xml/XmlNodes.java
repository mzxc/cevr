/*
 * 文 件 名:  CkXmlNodes.java
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
 * xml节点
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-24]
 * @see #XmlNodes
 * @since 1.0
 */
public interface XmlNodes {
    public void setXML_TAGS(final Object xml_tags);
    
    public String getElementValue(final String propName, final String elId);
}
