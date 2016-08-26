package com.gomyck.component.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 
 * @Title: PropertiesReader.java
 * 
 * @Description: 读取Properties配置文件
 * 
 * @Copyright: Copyright (c) 2007
 * 
 * @Company: 九方科技
 * 
 * @Author:
 * 
 * @Version: 1.0
 * 
 * @Todo:
 * 
 */
public class PropertiesReader
{
    private static final PropertiesReader instance = new PropertiesReader();
    
    final Properties prop = new Properties();
    
    private PropertiesReader()
    {
        
    }
    
    public static PropertiesReader getInstanceByResource()
    {
        return instance;
    }
    
    public PropertiesReader(final String filPath) throws IOException
    {
        final FileReader fr = new FileReader(new File(filPath));
        this.prop.load(fr);
    }
    
    /**
     * 获取配置文件中的值
     * 
     * @param file_name properties文件路径,不需要带后缀，只需要文件名即可
     * @param key 对应要获取key的值
     * @return
     * @throws MissingResourceException
     * 
     */
    public String getValue(final String file_name, final String key) throws MissingResourceException
    {
        final ResourceBundle res = ResourceBundle.getBundle(file_name);
        String value = "";
        try
        {
            value = res.getString(key);
        }
        catch (final MissingResourceException e)
        {
            throw e;
        }
        return value;
    }
    
    public String getValueByKey(final String key)
    {
        return this.prop.get(key) + "";
    }
}
