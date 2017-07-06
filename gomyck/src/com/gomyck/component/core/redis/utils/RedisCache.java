package com.gomyck.component.core.redis.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.gomyck.component.core.redis.MyJedisPool;

import redis.clients.jedis.Jedis;

/**
 * RedisCache : redis 缓存 插件
 *
 * @author mazhai
 * @since 2015-03-20 11:12
 */
public abstract class RedisCache {
    
    // 键命令
    
    /**
     * 删除一个指定key<如果存在>
     * 
     * @param key 键
     * @return
     */
    public static Boolean delKey(String key) {
        Jedis jedis = getJedis();
        boolean flag = jedis.del(key) == 1 ? true : false;
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 返回存储在指定键的值<序列化>
     * 
     * @param key 键
     * @return
     */
    public static byte[] dumpKey(String key) {
        Jedis jedis = getJedis();
        byte[] dump = jedis.dump(key);
        returnResource(jedis);
        return dump;
    }
    
    /**
     * 判断一个键是否存在
     * 
     * @param key 键
     * @return
     */
    public static Boolean existsKey(String key) {
        Jedis jedis = getJedis();
        boolean flag = jedis.exists(key);
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 设置键的到期时间<以秒为单位>
     * 
     * @param key 键
     * @param seconds 到期时间_秒
     * @return
     */
    public static Boolean expireKeySeconds(String key, int seconds) {
        Jedis jedis = getJedis();
        boolean flag = jedis.expire(key, seconds) == 1 ? true : false;
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 根据指定条件,查找与之匹配的所有键
     * 
     * @param pattern 条件
     * @return
     */
    public static Set<String> keysPattern(String pattern) {
        Jedis jedis = getJedis();
        Set<String> keys = jedis.keys(pattern);
        returnResource(jedis);
        return keys;
    }
    
    /**
     * 将键移动到另一个数据库
     * 
     * @param key 键
     * @param dbIndex 数据库游标
     * @return
     */
    public static Boolean moveKeyDB(String key, int dbIndex) {
        Jedis jedis = getJedis();
        boolean flag = jedis.move(key, dbIndex) == 1 ? true : false;
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 删除指定键的过期时间<永久存在>
     * 
     * @param key 键
     * @return
     */
    public static Boolean persistKey(String key) {
        Jedis jedis = getJedis();
        boolean flag = jedis.persist(key) == 1 ? true : false;
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 获取键的剩余到期时间
     * 
     * @param key 键
     * @return 返回值<以秒为单位>|[-2键不存在,-1没有过期时间]
     */
    public static Long ttlKey(String key) {
        Jedis jedis = getJedis();
        long flag = jedis.ttl(key);
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 获取一个键<随机>
     * 
     * @return
     */
    public static String randomKey() {
        Jedis jedis = getJedis();
        String flag = jedis.randomKey();
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 更改键的名称
     * 
     * @param oldkey 原有的键
     * @param newkey 新的键名
     * @return
     */
    public static Boolean renameKeyNewkey(String oldkey, String newkey) {
        Jedis jedis = getJedis();
        boolean flag = "OK".equals(jedis.rename(oldkey, newkey));
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 如果新的键名不存在，更改键的名称
     * 
     * @param oldkey 原有的键
     * @param newkey 新的键名
     * @return
     */
    public static Boolean renamenxKeyNewkey(String oldkey, String newkey) {
        Jedis jedis = getJedis();
        boolean flag = jedis.renamenx(oldkey, newkey) == 1 ? true : false;
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 返回存储在键中的值的数据类型
     * 
     * @param key 键
     */
    public static String typeKey(String key) {
        Jedis jedis = getJedis();
        String flag = jedis.type(key);
        returnResource(jedis);
        return flag;
    }
    
    /*-----------------------------------------------------------------------------------*/
    
    /**
     * 写入单个键值对
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static String cache(String key, String value) {
        Jedis jedis = getJedis();
        String flag = jedis.set(key, value);
        returnResource(jedis);
        return flag;
    }
    
    /**
     * 所有键值
     * 
     * @param key 键
     * @return
     */
    public static String get(String key) {
        Jedis jedis = getJedis();
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }
    
    /**
     * redis 计数+1
     * 
     * @param key 键
     * @return
     */
    public static Long incr(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.incr(key);
        returnResource(jedis);
        return result;
    }
    
    /**
     * redis 计数-1
     * 
     * @param key 键
     * @return
     */
    public static Long decr(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.decr(key);
        returnResource(jedis);
        return result;
    }
    
    /*-----------------------------------------------------------------------------------*/
    
    /**
     * 写入map
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static Boolean HMSET(String key, Map<String, String> value) {
        Jedis jedis = getJedis();
        Boolean result = "OK".equals(jedis.hmset(key, value));
        returnResource(jedis);
        return result;
    }
    
    /**
     * 获取map 所有键值对
     * 
     * @param key 键
     * @return
     */
    public static Map<String, String> HGETALL(String key) {
        Jedis jedis = getJedis();
        Map<String, String> map = jedis.hgetAll(key);
        returnResource(jedis);
        return (Map<String, String>)map;
    }
    
    /**
     * 获取map下子键值
     * 
     * @param key 键
     * @param field 子键
     * @return
     */
    public static String getMapKey(String key, String field) {
        Jedis jedis = getJedis();
        String result = jedis.hget(key, field);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 给指定的map下子键值，进行加减运算
     * 
     * @param key 键
     * @param field 子键
     * @param value 值<正数为加,负数为减>
     * @return
     */
    public static Long hincrMapKey(String key, String field, long value) {
        Jedis jedis = getJedis();
        Long result = jedis.hincrBy(key, field, value);
        returnResource(jedis);
        return result;
    }
    
    /*-----------------------------------------------------------------------------------*/
    
    /**
     * 写入list值
     * 
     * @param key 键
     * @param strings
     */
    public static void lpush(String key, String... strings) {
        Jedis jedis = getJedis();
        jedis.lpush(key, strings);
        returnResource(jedis);
    }
    
    /**
     * 获取list值
     * 
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标 -1 代表后面全部
     * @return
     */
    public static List<String> lrange(String key, long start, long end) {
        Jedis jedis = getJedis();
        List<String> result = jedis.lrange(key, start, end);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 
     * 删除list
     * 
     * @param key
     * @param start
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String ltrim(String key, long start, long end) {
        Jedis jedis = getJedis();
        String ltrim = jedis.ltrim(key, start, end);
        returnResource(jedis);
        return ltrim;
    }
    
    public static Long llen(String key) {
        Jedis jedis = getJedis();
        Long llen = jedis.llen(key);
        returnResource(jedis);
        return llen;
    }
    
    /*-----------------------------------------------------------------------------------*/
    
    /**
     * 写入set
     * 
     * @param key 键
     * @param value 值
     */
    public static void sadd(String key, String... member) {
        Jedis jedis = getJedis();
        jedis.sadd(key, member);
        returnResource(jedis);
    }
    
    /**
     * 获取全部set
     * 
     * @param key 键
     * @return
     */
    public static Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> result = jedis.smembers(key);
        returnResource(jedis);
        return result;
    }
    
    /*-----------------------------------------------------------------------------------*/
    
    /**
     * 写入有序set
     * 
     * @param key 键
     * @param score 分数
     * @param member 内容
     */
    public static void zadd(String key, Double score, String member) {
        Jedis jedis = getJedis();
        jedis.zadd(key, score, member);
        returnResource(jedis);
    }
    
    /**
     * 获取有序set_升序(asc)
     * 
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标 -1代表后面全部
     * @return
     */
    public static Set<String> zrange(String key, long start, long end) {
        Jedis jedis = getJedis();
        Set<String> result = jedis.zrange(key, start, end);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 获取有序set_降序(desc)
     * 
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标 -1代表后面全部
     * @return
     */
    public static Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = getJedis();
        Set<String> result = jedis.zrevrange(key, start, end);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 移除一个有序set成员
     * 
     * @param key 键
     * @param member 内容
     * @return
     */
    public static long zrem(String key, String member) {
        Jedis jedis = getJedis();
        long result = jedis.zrem(key, member);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 移除一个有序set成员
     * 
     * @param key 键
     * @param start 分数_开始
     * @param end 分数_结束
     * @return
     */
    public static long zremrangeByScore(String key, String start, String end) {
        Jedis jedis = getJedis();
        long result = jedis.zremrangeByScore(key, start, end);
        returnResource(jedis);
        return result;
    }
    
    /**
     * 获取有序set的成员数
     * 
     * @param key 键
     * @return
     */
    public static long zcard(String key) {
        Jedis jedis = getJedis();
        long result = jedis.zcard(key);
        returnResource(jedis);
        return result;
    }
    
    private static final String JEDIS_KEY = "jedis";
    
    private static final String DB_INDEX = "dbIndex";
    
    private static final String IS_INITED = "isInited";
    
    private static final String IF_RETURN = "ifReturn";
    
    private static final boolean RELOAD_STRATEGY = false;
    
    private static final String DO_SOMETHING = "ifDoSomeThing";
    
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    
    private static ThreadLocal<Map<String, Object>> currentJedis = new ThreadLocal<Map<String, Object>>();
    
    private static Jedis getJedis() {
        int dbIndex = 0;
        if (getIfReturn()) {
            String warnStr = "Resource is returned! Please don't get redis resource after release!!! you can do this after call method: RedisCache.startDoIt() and before call RedisCache.finishDoIt() OR wirite an Annotation: @RedisManager on this method head";
            dbIndex = initStrategy(warnStr);
        }
        if (getIfInit() && !getIfDoSomeThing()) {
            String warnStr = "Please don't call more than two methods of the class: RedisCache,  you can do this after call method: RedisCache.startDoIt() and before call RedisCache.finishDoIt() OR wirite an Annotation: @RedisManager on this method head";
            dbIndex = initStrategy(warnStr);
        }
        Jedis thisJedis = null;
        if (currentJedis.get() != null && currentJedis.get().get(JEDIS_KEY) != null) {
            thisJedis = (Jedis)currentJedis.get().get(JEDIS_KEY);
        }
        else {
            thisJedis = MyJedisPool.getJedis();
        }
        thisJedis.select(dbIndex);
        setJedis(thisJedis);
        return thisJedis;
    }
    
    //
    /**
     * 重新加载策略
     * 
     * 取消兼容重新获取连接对象，如果需要多次操作redis数据库，请在方法的头部添加注解：RedisManager 或者 用如下代码包裹方法体： RedisCache.startDoIt() ...
     * RedisCache.finishDoIt() 或者 使用doIt方法包裹原有方法体： RedisCache.doSomeThing(new Todo{实现doIt方法})
     * 
     * @param warnStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static int initStrategy(String warnStr) {
        if (RELOAD_STRATEGY || getIfDoSomeThing()) {
            int dbIndex;
            boolean ifDoSomeThing;
            boolean ifInit;
            boolean ifReturn;
            dbIndex = getDBIndex();
            ifDoSomeThing = getIfDoSomeThing();
            ifInit = getIfInit();
            ifReturn = getIfReturn();
            currentJedis.remove();
            setIfInit(ifInit);
            setDBIndex(dbIndex);
            setIfReturn(ifReturn);
            setIfDoSomeThing(ifDoSomeThing);
            if (RELOAD_STRATEGY)
                logger.warn(warnStr);
            return dbIndex;
        }
        else {
            currentJedis.remove();
            throw new RuntimeException(warnStr);
        }
    }
    
    private static void setJedis(Jedis jedis) {
        Assert.notNull(jedis);
        if (currentJedis.get() != null) {
            currentJedis.get().put(JEDIS_KEY, jedis);
        }
        else {
            Map<String, Object> jedisMap = new HashMap<String, Object>();
            jedisMap.put(JEDIS_KEY, jedis);
            currentJedis.set(jedisMap);
        }
    }
    
    private static void setIfInit(Boolean ifInit) {
        Assert.notNull(ifInit);
        if (currentJedis.get() != null) {
            currentJedis.get().put(IS_INITED, ifInit);
        }
        else {
            Map<String, Object> jedisMap = new HashMap<String, Object>();
            jedisMap.put(IS_INITED, ifInit);
            currentJedis.set(jedisMap);
        }
    }
    
    private static boolean getIfInit() {
        if (currentJedis.get() == null || currentJedis.get().get(IS_INITED) == null) {
            return false;
        }
        return (Boolean)currentJedis.get().get(IS_INITED);
    }
    
    private static void setDBIndex(Integer index) {
        Assert.notNull(index);
        if (currentJedis.get() != null) {
            currentJedis.get().put(DB_INDEX, index);
        }
        else {
            Map<String, Object> jedisMap = new HashMap<String, Object>();
            jedisMap.put(DB_INDEX, index);
            currentJedis.set(jedisMap);
        }
    }
    
    private static Integer getDBIndex() {
        if (currentJedis.get() != null && currentJedis.get().get(DB_INDEX) != null) {
            return (Integer)(currentJedis.get().get(DB_INDEX));
        }
        else {
            return 0;
        }
    }
    
    private static void setIfDoSomeThing(Boolean ifDoSomeThing) {
        Assert.notNull(ifDoSomeThing);
        if (currentJedis.get() != null) {
            currentJedis.get().put(DO_SOMETHING, ifDoSomeThing);
        }
        else {
            Map<String, Object> jedisMap = new HashMap<String, Object>();
            jedisMap.put(DO_SOMETHING, ifDoSomeThing);
            currentJedis.set(jedisMap);
        }
    }
    
    private static boolean getIfDoSomeThing() {
        if (currentJedis.get() == null || currentJedis.get().get(DO_SOMETHING) == null) {
            return false;
        }
        return (Boolean)currentJedis.get().get(DO_SOMETHING);
    }
    
    private static void setIfReturn(Boolean ifReturn) {
        Assert.notNull(ifReturn);
        if (currentJedis.get() != null) {
            currentJedis.get().put(IF_RETURN, ifReturn);
        }
        else {
            Map<String, Object> jedisMap = new HashMap<String, Object>();
            jedisMap.put(IF_RETURN, ifReturn);
            currentJedis.set(jedisMap);
        }
    }
    
    private static boolean getIfReturn() {
        if (currentJedis.get() == null || currentJedis.get().get(IF_RETURN) == null) {
            return false;
        }
        return (Boolean)currentJedis.get().get(IF_RETURN);
    }
    
    private static void returnResource(Jedis jedis) {
        if (getIfDoSomeThing())
            return;
        setIfReturn(true);
        MyJedisPool.release(jedis);
    }
    
    public static void selectDB(int index) {
        setDBIndex(index);
        String select = getJedis().select(index);
        if (!"OK".equals(select))
            setDBIndex(0);
    }
    
    /**
     * 该方法可用spring通知配置
     * 
     * @param todo
     * @see [类、类#方法、类#成员]
     */
    public static void doSomeThing(Todo todo) {
        startDoIt();
        todo.doIt();
        finishDoIt();
    }
    
    public static void startDoIt() {
        setIfDoSomeThing(true);
    }
    
    public static void finishDoIt() {
        setIfDoSomeThing(false);
        returnResource((Jedis)currentJedis.get().get(JEDIS_KEY));
    }
    
    public interface Todo {
        public void doIt();
    }
    
}