package com.gomyck.component.core.redis.test;

import com.gomyck.component.core.redis.utils.RedisCache;
import com.gomyck.component.core.redis.utils.RedisCache.Todo;

public class Test {
    public static void main(String[] args) {
        RedisCache.startDoIt();
        RedisCache.cache("who", "asdas");
        System.out.println(RedisCache.get("who"));
        RedisCache.finishDoIt();
        // RedisCache.cache("who1", "45345353");
        // System.out.println(RedisCache.get("who1"));
        RedisCache.doSomeThing(new Todo() {
            @Override
            public void doIt() {
                RedisCache.cache("who123", "123123123");
                System.out.println(RedisCache.get("who123"));
            }
        });
    }
}
