package com.gomyck.component.core.redis;

import com.gomyck.component.core.redis.RedisCache.Todo;

public class Test {
    public static void main(String[] args) {
        RedisCache.startDoIt();
        RedisCache.cache("who", "123123123");
        System.out.println(RedisCache.get("who"));
        RedisCache.finishDoIt();
//        RedisCache.cache("who1", "45345353");
//        System.out.println(RedisCache.get("who1"));
        RedisCache.doSomeThing(new Todo(){
            @Override
            public void doIt() {
                RedisCache.cache("who123", "123123123");
                System.out.println(RedisCache.get("who123"));
            }
        });
    }
}
