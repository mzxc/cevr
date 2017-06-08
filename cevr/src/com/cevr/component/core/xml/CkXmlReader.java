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
package com.cevr.component.core.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cevr.component.core.XmlNotFoundException;

/**
 * xml文件读取工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-16]
 * @see #CkXmlReader
 * 本类为xml加载器核心类。负责加载xml实体，具体如何去操作xml，则调用refresh();
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class CkXmlReader extends HttpServlet implements XmlReader
{
	private static ServletContext currentServletContext;
	
	protected Document currentDocument;
    
    protected static String XML_PATH = "ck.xml";
    
    protected static String XML_TAG = "ckXmlTags";
    
    protected static String XML_ENTITY = "ckXmlEntity";
    
    protected static String XML_ADAPTER = "ckXmlLoader";
    
    protected static String XML_ADAPTER_NAME = CkXmlReader.class.getName() + ".ck";
    
    protected static final String INIT_PARAM = "sqlXmlPath";
    
    protected static boolean CK_INIT = false;
    
    private static volatile Map<String, Map<String, String>> XML_TAG_ENTITY = new Hashtable<String, Map<String, String>>();
    
    private static final Map<ClassLoader, ServletContext> currentContextPerThread = new ConcurrentHashMap<ClassLoader, ServletContext>(1);
    
    /**
     * 重载方法
     * 
     * @throws SAXException
     * 
     */
    @Override
    public Document loadXml()
    {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
        	final DocumentBuilder db = dbf.newDocumentBuilder();
        	if(!CK_INIT){
        		XML_PATH = System.getProperty("gomyck.root") + XML_PATH;
        	}
            final InputSource is = new InputSource(new FileInputStream(XML_PATH));
            this.currentDocument = this.currentDocument != null && !CK_INIT ? this.currentDocument : db.parse(is);
            refresh();
            final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            if (ccl == CkXmlReader.class.getClassLoader() && !CK_INIT)
            {
                currentContextPerThread.put(ccl, currentServletContext);
            }
            CK_INIT = true;
            return this.currentDocument;
        }
        catch (final IOException e)
        {
        	CK_INIT = false;
            throw new XmlNotFoundException("xml file is not found in : " + XML_PATH, e);
        }
        catch (final Exception e)
        {
        	CK_INIT = false;
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * 普通刷新
     * 
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    protected abstract void refresh() throws Exception;
    
    /**
     * 重载方法
     * 
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException
    {
        super.init();
        currentServletContext = getServletContext();
        
    }

	protected void putIntoXmlTags(final String tagName, final Map<String, String> nodeMap)
    {
        XML_TAG_ENTITY.put(tagName, nodeMap);
    }
    
    protected Map<String, Map<String, String>> getXmlTags()
    {
        return XML_TAG_ENTITY;
    }
    
    protected static ServletContext getCurrentContext()
    {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        if (ccl != null)
        {
            final ServletContext ccpt = currentContextPerThread.get(ccl);
            if (ccpt != null)
            {
                return ccpt;
            }
        }
        return currentServletContext;
    }
    
    protected ServletContext getCurrentContextByPro()
    {
        return currentServletContext;
    }
}
