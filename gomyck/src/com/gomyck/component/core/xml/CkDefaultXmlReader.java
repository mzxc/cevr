/*
 * 文 件 名:  DefaultXmlReader.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.component.core.xml;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gomyck.component.core.xml.exception.DuplicateLabelException;
import com.gomyck.component.core.xml.exception.XmlNotFoundException;
import com.gomyck.component.utils.StringUtil;

/**
 * 默认xml加载器
 * 
 * CkXmlGetter.getXmlNodes("sql", "serachAll")
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-16]
 * @see #CkDefaultXmlReader()
 * @since 1.0
 */
@SuppressWarnings("serial")
public class CkDefaultXmlReader extends CkXmlReader {
    /**
     * 日志服务
     */
    private static Logger log = Logger.getLogger(CkDefaultXmlReader.class);
    
    public static final String DEFAULT_XML_PATH = "src/ck.xml";
    
    public static final String DEFAULT_XML_ENTITY = "defaultCkXmlEntity";
    
    public static final String DEFAULT_XML_TAG = "defaultXmlTags";
    
    public static final String DEFAULT_XML_ADAPTER = "defaultXmlLoader";
    
    public static final String DEFAULT_XML_ADAPTER_NAME = CkDefaultXmlReader.class.getName() + ".ck";
    
    private static final String[] TAG_NAMES = {"prop", "sql"};
    
    {
        XML_TAG = DEFAULT_XML_TAG;
        XML_PATH = DEFAULT_XML_PATH;
        XML_ENTITY = DEFAULT_XML_ENTITY;
        XML_ADAPTER = DEFAULT_XML_ADAPTER;
        XML_ADAPTER_NAME = DEFAULT_XML_ADAPTER_NAME;
    }
    
    /**
     * <默认构造函数>
     */
    public CkDefaultXmlReader() {
        super();
    }
    
    /**
     * 重载方法
     * 
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(final ServletConfig config)
        throws ServletException {
        log.info("xmlLoader is Start...");
        super.init(config);
        final String thisXmlPath = config.getInitParameter(INIT_PARAM);
        XML_PATH = (thisXmlPath == null ? XML_PATH : thisXmlPath);
        getCurrentContextByPro().setAttribute(XML_ADAPTER, this);
        getCurrentContextByPro().setAttribute(XML_ADAPTER_NAME, DEFAULT_XML_ADAPTER_NAME);
        getCurrentContextByPro().setAttribute(XML_ENTITY, loadXml());
        final CkDefaultXmlNodes ckDefaultNodes = new CkDefaultXmlNodes();
        ckDefaultNodes.setXML_TAGS(getXmlTags());
        getCurrentContextByPro().setAttribute(XML_TAG, ckDefaultNodes);
        log.info("xmlLoader is initialized...");
    }
    
    /**
     * 重载方法
     * 
     * @throws XmlNotFoundException
     */
    @Override
    public void refresh() {
        for (final String tagName : TAG_NAMES) {
            final NodeList tagList = this.currentDocument.getElementsByTagName(tagName);
            final Map<String, String> nodeMap = new HashMap<String, String>();
            for (int i = 0; i < tagList.getLength(); i = i + 1) {
                final Node node = tagList.item(i);
                if (node.hasChildNodes() && node.getNodeType() == Node.ELEMENT_NODE && node.getChildNodes().getLength() == 1) {
                    final Element el = (Element)node;
                    final String tagId = el.getAttribute("id");
                    if (!StringUtil.isEmpty(nodeMap.get(tagId))) {
                        throw new DuplicateLabelException("tag id same as >>>" + tagId + "<<<");
                    }
                    nodeMap.put(tagId, el.getTextContent());
                }
            }
            putIntoXmlTags(tagName, nodeMap);
        }
    }
}
