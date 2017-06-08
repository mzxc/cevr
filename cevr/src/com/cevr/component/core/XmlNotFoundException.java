/*
 * 文 件 名:  XmlNotFoundException.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cevr.component.core;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-16]
 * @see #XmlNotFoundException
 * @since 1.0
 */
@SuppressWarnings("serial")
public class XmlNotFoundException extends XmlReaderException
{
    /**
     * <默认构造函数>
     */
    public XmlNotFoundException()
    {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public XmlNotFoundException(final String message)
    {
        super(message);
    }
    
    /**
     * <默认构造函数>
     */
    public XmlNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
    
}
