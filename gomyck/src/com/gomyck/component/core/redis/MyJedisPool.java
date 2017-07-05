package com.gomyck.component.core.redis;

import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * redis 连接池
 * 
 * @author 郝洋 QQ:474798383
 * @version [版本号, 2017年6月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyJedisPool {
    
    private static int port = 6379;
    
//    private static String host = "127.0.0.1";
    private static String host = "192.168.1.19";
    
    private final static int EXRP_HOUR = 60 * 60;
    
    private final static int EXRP_DAY = 60 * 60 * 24;
    
    private final static int EXRP_MONTH = 60 * 60 * 24 * 30;
    
    private static ReentrantLock lockPool = new ReentrantLock();
    
    private static ReentrantLock lockJedis = new ReentrantLock();
    
    private static JedisPool jedisPool;
    
    static {
        assert !lockPool.isHeldByCurrentThread();
        lockPool.lock();
        try {
            if (jedisPool == null) {
                initJedisPool();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            lockPool.unlock();
        }
    }
    
    public static void initJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(15);
        config.setMaxWaitMillis(2000);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, host, port, EXRP_DAY);
    }
    
    public static Jedis getJedis() {
        assert !lockJedis.isHeldByCurrentThread();
        lockJedis.lock();
        if (jedisPool == null) {
            initJedisPool();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        finally {
            lockJedis.unlock();
        }
        return jedis;
    }
    
    /**
     * 根据索引值，切换数据库，打开连接
     * @param index 索引值
     * @return
     */
    public static Jedis getJedis(int index) {
        assert !lockJedis.isHeldByCurrentThread();
        lockJedis.lock();
        if (jedisPool == null) {
            initJedisPool();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        finally {
            lockJedis.unlock();
        }
        jedis.select(index);
        return jedis;
    }
    
    public static void release(Jedis jedis, boolean isBroken) {  
        if (jedis != null) {  
            if (isBroken) {  
                jedisPool.returnBrokenResource(jedis);  
            } else {  
                jedisPool.returnResource(jedis);  
            }  
        }  
    }  
    
}
