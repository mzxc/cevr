/*
 * 文 件 名:  XmlParser.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2017-1-7
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.component.core.xml.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml实体解析
 * 
 * @author 郝洋
 * @version [版本号, 2017-1-7]
 * @see XmlParser #parseXml(String)
 * @see XmlParser #parseXml(File)
 * @since 1.0
 */
public class XmlParser {
    private static final XmlParser xp = new XmlParser();;
    
    private static final Map<Thread, Document> doc = new HashMap<Thread, Document>();
    
    private XmlParser() {
        super();
    }
    
    /**
     * 
     * 解析字符串类型的xml
     * 
     * @param xmlBody
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static XmlParser parseXml(final String xmlBody) {
        try {
            final DocumentBuilderFactory _dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder _db = _dbf.newDocumentBuilder();
            final Document _doc = _db.parse(new ByteArrayInputStream(xmlBody.getBytes()));
            doc.put(Thread.currentThread(), _doc);
        }
        catch (final Exception e) {
            System.out.println(getExceptionDetail(e));
            return null;
        }
        return xp;
    }
    
    /**
     * 
     * 加载文件形式的xml
     * 
     * @param xmlBody
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static XmlParser parseXml(final File xmlBody) {
        try {
            final DocumentBuilderFactory _dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder _db = _dbf.newDocumentBuilder();
            final Document _doc = _db.parse(xmlBody);
            doc.put(Thread.currentThread(), _doc);
        }
        catch (final Exception e) {
            System.out.println(getExceptionDetail(e));
            return null;
        }
        return xp;
    }
    
    /**
     * 
     * 解析流文件类型的xml
     * 
     * @param xmlBody
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static XmlParser parseXml(final InputStream xmlBody) {
        try {
            final DocumentBuilderFactory _dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder _db = _dbf.newDocumentBuilder();
            final Document _doc = _db.parse(xmlBody);
            doc.put(Thread.currentThread(), _doc);
        }
        catch (final Exception e) {
            System.out.println(getExceptionDetail(e));
            return null;
        }
        return xp;
    }
    
    /**
     * 
     * xml转实体
     * 
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T convertEntity(final Class<T> clazz) {
        try {
            final Object target = clazz.newInstance();
            final Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i = i + 1) {
                final Field field = fields[i];
                String fieldName = field.getName();
                final String headChar = fieldName.substring(0, 1).toUpperCase();
                fieldName = headChar + fieldName.substring(1, fieldName.length());
                final Method method = clazz.getMethod("set" + fieldName, field.getType());
                method.invoke(target, getNodeVlaue(field.getName()));
            }
            return (T)target;
        }
        catch (final Exception e) {
            System.out.println(getExceptionDetail(e));
        }
        return null;
    }
    
    /**
     * 
     * 获取节点值
     * 
     * @param tagName
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String getNodeVlaue(final String tagName) {
        final NodeList _nl = doc.get(Thread.currentThread()).getElementsByTagName(tagName);
        if (_nl == null)
            return "";
        final Node _node = _nl.item(0);
        if (_node == null)
            return "";
        return _node.getTextContent() == null ? "" : _node.getTextContent().trim();
    }
    
    /**
     * 打印详细异常信息
     * 
     * @param t 异常
     * @return string信息
     */
    private static String getExceptionDetail(final Throwable t) {
        final StringWriter _stringWriter = new StringWriter();
        final PrintWriter _writer = new PrintWriter(_stringWriter);
        t.printStackTrace(_writer);
        final StringBuffer _buffer = _stringWriter.getBuffer();
        return _buffer.toString();
    }
}
