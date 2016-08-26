package com.gomyck.component.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gomyck.component.logger.NestLogger;

/**
 * 
 * @author h_yang
 * 
 */
public class ConnectionPool extends Thread
{
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    
    /**
     * 连接池
     */
    private static final List<Connection> connPool = new ArrayList<Connection>();
    
    /**
     * 连接池状态机
     */
    private static int[] poolInfo;
    
    /**
     * 初始化连接池
     */
    public void initConnectionPool()
    {
        checkConnectionSatus();
        distoryConnectionPool();
        int poolSize = 0;
        logger.info("正在初始化连接池..");
        for (int i = 0; i < 10; i = i + 1)
        {
            Connection conn = createConnection();
            if (conn == null)
            {
                conn = createConnection();
                if (conn == null)
                {
                    throw new RuntimeException("连接池无法获取连接.....");
                }
            }
            connPool.add(conn);
            poolSize = poolSize + 1;
        }
        poolInfo = new int[poolSize];
        for (int i = 0; i < poolSize; i = i + 1)
        {
            poolInfo[i] = i;
        }
        logger.info("当前空闲连接数 : " + getFreeConnNum());
        logger.info("初始化连接池结束..");
    }
    
    /**
     * 创建数据库连接
     * 
     * @return connection
     */
    private static Connection createConnection()
    {
        String dbConfigPath;
        try
        {
            dbConfigPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
            dbConfigPath += "com/ctj/resource/db.properties";
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            dbConfigPath = "D:/WorkSpace/CTJ_MyEclipse_WorkSpace/Accounting/src/com/ctj/resource/db.properties";
        }
        final Properties pro = new Properties();
        try
        {
            pro.load(new FileInputStream(dbConfigPath));
            final String jdbcUrl = pro.get("jdbcUrl").toString();
            final String userName = pro.get("username").toString();
            final String passWord = pro.get("password").toString();
            final String jdbcDriver = pro.get("driverClass").toString();
            Class.forName(jdbcDriver);
            final Connection conn = DriverManager.getConnection(jdbcUrl, userName, passWord);
            return conn;
        }
        catch (SQLException sqlException)
        {
            NestLogger.showException(sqlException);
            logger.error("can not get JDBConnection..." + sqlException.toString(),
                new RuntimeException("can not get JDBConnection..." + sqlException.toString()));
            return createConnection();
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            NestLogger.showException(classNotFoundException);
            logger.error("not found jdbcDriver..." + classNotFoundException.toString(),
                new RuntimeException("not found jdbcDriver..." + classNotFoundException.toString()));
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            NestLogger.showException(fileNotFoundException);
            logger.error("not found properties..." + fileNotFoundException.toString(),
                new RuntimeException("not found properties..." + fileNotFoundException.toString()));
        }
        catch (IOException ioException)
        {
            NestLogger.showException(ioException);
            logger.error("load properties error..." + ioException.toString(),
                new RuntimeException("load properties error..." + ioException.toString()));
        }
        return null;
    }
    
    /**
     * 连接线程检查启动
     */
    private static void checkConnectionSatus()
    {
        new ConnectionPool().start();
    }
    
    /**
     * 销毁线程池
     */
    private static void distoryConnectionPool()
    {
        logger.info("正在销毁连接池...");
        for (Connection conn : connPool)
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e)
                {
                    NestLogger.showException(e);
                    logger.error("销毁连接池时出现错误:" + e.toString());
                }
            }
        }
        for (int i = 0; i < connPool.size(); i = i + 1)
        {
            connPool.remove(i);
        }
        logger.info("连接池成功被销毁...");
    }
    
    @Override
    public void run()
    {
        logger.info("线程池状态检查服务启动...");
        while (true)
        {
            try
            {
                Thread.sleep(1200000L);
                checkIt();
            }
            catch (Exception e)
            {
                NestLogger.showException(e);
                new ConnectionPool().start();
                logger.error("连接池状态检查服务出错,正在重新初始化.......异常信息:" + e.toString());
            }
        }
    }
    
    /**
     * 检查线程池状态
     */
    private static void checkIt()
    {
        for (int i = 0; i < poolInfo.length; i = i + 1)
        {
            final int poolIndex = poolInfo[i];
            if (poolIndex == -1)
            {
                continue;
            }
            Connection conn = connPool.get(i);
            try
            {
                if (conn == null || conn.isClosed() || !conn.isValid(0))
                {
                    conn = createConnection();
                    connPool.remove(i);
                    connPool.add(i, conn);
                    logger.info("存在异常的连接,重新初始化并更新..当前空闲连接数 : " + getFreeConnNum());
                }
            }
            catch (SQLException e)
            {
                conn = createConnection();
                connPool.remove(i);
                connPool.add(i, conn);
                NestLogger.showException(e);
                logger.info("连接池状态检查时出现异常,重新初始化并更新..当前空闲连接数 : " + getFreeConnNum());
            }
        }
        // logger.info("数据库连接池状态检查成功..当前空闲连接数 : " + getFreeConnNum());
    }
    
    /**
     * 获取连接
     * 
     * @return connection
     */
    public static synchronized Connection getConnection()
    {
        for (int i = 0; i < poolInfo.length; i = i + 1)
        {
            final int poolIndex = poolInfo[i];
            if (poolIndex == -1)
            {
                continue;
            }
            poolInfo[i] = -1;
            Connection conn = connPool.get(i);
            try
            {
                if (conn == null || conn.isClosed() || !conn.isValid(0))
                {
                    conn = createConnection();
                    connPool.remove(i);
                    connPool.add(i, conn);
                }
            }
            catch (SQLException e)
            {
                conn = createConnection();
                connPool.remove(i);
                connPool.add(i, conn);
                NestLogger.showException(e);
                logger.info("获取数据库连接时出现异常..重新获取连接,当前空闲连接数 : " + getFreeConnNum());
            }
            logger.info("获取数据库连接成功..当前空闲连接数 : " + getFreeConnNum());
            return conn;
        }
        logger.info("获取数据库连接失败..当前空闲连接数 : " + getFreeConnNum());
        return null;
    }
    
    /**
     * 关闭连接
     * 
     * @param conn 正在使用的连接
     * @throws SQLException 异常
     */
    public static synchronized void closeConnection(Connection conn)
        throws SQLException
    {
        boolean ifGiveBack = false;
        for (int i = 0; i < poolInfo.length; i = i + 1)
        {
            final int poolIndex = poolInfo[i];
            if (poolIndex == -1 && connPool.get(i).equals(conn))
            {
                poolInfo[i] = i;
                if (conn == null || conn.isClosed() || !conn.isValid(0))
                {
                    conn = null;
                    final Connection newConn = createConnection();
                    connPool.remove(i);
                    connPool.add(i, newConn);
                }
                ifGiveBack = true;
                logger.info("归还数据库连接成功..当前空闲连接数 : " + getFreeConnNum());
                return;
            }
        }
        if (!ifGiveBack)
        {
            logger.info("归还数据库连接失败..出现冗余连接,正在关闭该连接..当前空闲连接数 : " + getFreeConnNum());
            conn.close();
        }
    }
    
    /**
     * 获取空闲连接数
     * 
     * @return 数量
     */
    private static int getFreeConnNum()
    {
        int freeConnNum = 0;
        for (int i = 0; i < poolInfo.length; i = i + 1)
        {
            if (poolInfo[i] != -1)
            {
                freeConnNum = freeConnNum + 1;
            }
        }
        return freeConnNum;
    }
}
