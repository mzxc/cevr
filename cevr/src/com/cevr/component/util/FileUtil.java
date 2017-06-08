/*
 * 文 件 名:  FileUtil.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-23
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cevr.component.util;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.cevr.component.logger.NestLogger;

/**
 * 文件保存工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileUtil
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(FileUtil.class);
    
    /**
     * 
     * 存储springmvc文件流工具
     * 
     * @param dirPath
     * @param saveObject
     * @param fileName
     * @see [类、类#方法、类#成员]
     */
    public static boolean fileIO(final String dirPath, final MultipartFile saveObject, final String fileName)
    {
        boolean ifCreate = false;
        File file = new File(dirPath);
        if (!file.exists())
        {
            file.mkdirs();
        }
        file = new File(dirPath, fileName);
        if (!file.exists())
        {
            try
            {
                ifCreate = file.createNewFile();
                final FileOutputStream outStr = new FileOutputStream(file);
                outStr.write(saveObject.getBytes());
                outStr.flush();
                outStr.close();
            }
            catch (final Exception e)
            {
                NestLogger.showException(e);
                log.error(NestLogger.buildLog("保存文件出错: ", e, true));
            }
        }
        return ifCreate;
    }
    
    /**
     * 
     * 是否是图片
     * 
     * @param in in
     * @return boolean boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean ifImage(final InputStream in)
    {
        Image image;
        try
        {
            image = ImageIO.read(in);
            if (image == null)
            {
                return false;
            }
        }
        catch (final IOException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * 删除文件
     * 
     * @param dirPath dirPath
     * @param fileName dirPath
     * @see [类、类#方法、类#成员]
     */
    public static boolean deleteFile(final String dirPath, final String fileName)
    {
        boolean ifDel = false;
        final File file = new File(dirPath + "/" + fileName);
        if (file.exists())
        {
            ifDel = file.delete();
        }
        return ifDel;
    }
}
