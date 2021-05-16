package org.easley.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    public void tryLock(String key){
        while (true){
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Boolean result = valueOperations.setIfAbsent(key, "1", 1, TimeUnit.MINUTES);
            if(result){
                break;
            }
            try{
                TimeUnit.MICROSECONDS.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean tryLockWithError(String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Boolean result = valueOperations.setIfAbsent(key, new Object(), 1, TimeUnit.MINUTES);
        if(!result){
            throw new RuntimeException("未获取到锁");
        }
        return true;
    }

    public void unLock(String key){
        redisTemplate.delete(key);
    }
}

