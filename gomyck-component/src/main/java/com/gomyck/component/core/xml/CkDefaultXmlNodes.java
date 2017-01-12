/*
 * 文 件 名:  CkDefaultTags.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-24
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.component.core.xml;

import java.util.Map;

import com.gomyck.component.core.NodesShapeException;
import com.gomyck.component.util.StringUtil;

/**
 * 默认xml节点集合类
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-24]
 * @see #CkDefaultXmlNodes
 * @since 1.0
 */
public class CkDefaultXmlNodes extends CkXmlNodes
{
    private volatile Map<String, Map<String, String>> xml_tags;
    
    /**
     * 设置 xML_TAGS
     * 
     * @param 对xML_TAGS进行赋值
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setXML_TAGS(final Object xml_tags)
    {
        try
        {
            this.xml_tags = (Map<String, Map<String, String>>)xml_tags;
        }
        catch (final Exception e)
        {
            throw new NodesShapeException(" xmlNodeList shape error!please check xmlReaderAdapter type! ", e);
        }
    }
    
    /**
     * 重载方法
     * 
     * @param propName
     * @param elId
     * @return
     */
    @Override
    public String getElementValue(final String propName, final String elId)
    {
        return " " + StringUtil.strTrim(this.xml_tags.get(propName).get(elId)) + " ";
    }
    
}
