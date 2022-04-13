package com.necpu.util;

import io.netty.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public Object get(String key){
        if (redisTemplate.hasKey(key)){
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    public boolean delete(String key){
        redisTemplate.delete(key);
        if (redisTemplate.hasKey(key)){
            return false;
        }
        return true;
    }
}
