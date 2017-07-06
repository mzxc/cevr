package com.gomyck.component.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * 
 * 日志生成工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-13]
 * @see #NestLogger
 * @since 1.2
 */
public class NestLogger {
    /**
     * 开发者模式
     */
    public static final boolean DEVELOP_MODEL = true;
    
    private static final Logger logger = Logger.getLogger(NestLogger.class);
    
    private static final File file;
    
    private static FileWriter fw;
    
    private static String FILEPATH = "../sysLog/ckLog.log";
    
    static {
        file = new File(FILEPATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (final IOException e) {
                logger.error("初始化本地日志文件错误...." + e);
            }
        }
        else if (file.length() > 536870912L) {
            file.renameTo(new File(FILEPATH + ".bak"));
        }
        try {
            fw = new FileWriter(file, true);
        }
        catch (final IOException e) {
            logger.error("初始化本地日志文件错误...." + e);
        }
    }
    
    /**
     * 
     * 异常处理方法
     * 
     * @param e
     * @see [类、类#方法、类#成员]
     */
    public static void showException(final Exception e) {
        logger.error(getTrace(e));
        if (DEVELOP_MODEL) {
            e.printStackTrace();
        }
    }
    
    /**
     * 日志生成
     * 
     * @param whatWork 事件描述
     * @param e 异常对象
     * @param ifGetTraceInfo 是否打印详细信息
     * @return 日志信息
     */
    public static String buildLog(final String whatWork, final Exception e, final boolean ifGetTraceInfo) {
        return "Logger Info : 日志详情: " + whatWork + (e == null ? "" : " , 当前异常信息为: " + (ifGetTraceInfo ? getTrace(e) : e.toString()));
    }
    
    /**
     * 打印详细异常信息
     * 
     * @param t 异常
     * @return string信息
     */
    private static String getTrace(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
    
    public static void locationLog(final String logInfo) {
        try {
            fw.append(logInfo + "\n");
            fw.flush();
        }
        catch (final IOException e) {
            new GotoLog(logInfo).start();
            e.printStackTrace();
        }
    }
    
    static class GotoLog extends Thread {
        private final String logStr;
        
        public GotoLog(final String logStr) {
            this.logStr = logStr;
        }
        
        /**
         * 重载方法
         */
        @Override
        public void run() {
            super.run();
            try {
                sleep(5000);
            }
            catch (final InterruptedException e) {
                e.printStackTrace();
            }
            locationLog(this.logStr);
        }
    }
}
