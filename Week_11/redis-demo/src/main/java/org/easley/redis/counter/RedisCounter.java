package org.easley.redis.counter;

import org.easley.redis.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCounter {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;

    public void initStock(Integer cnt) {
        redisTemplate.opsForValue().set("stock_total_key", cnt);
    }

    public boolean decreaseStock() {
        String lockKey = "stock_key";
        redisLock.tryLock(lockKey);
        try {
            Integer stock = (Integer) redisTemplate.opsForValue().get("stock_total_key");
            if (stock <= 0) {
                return false;
            }
            stock--;
            redisTemplate.opsForValue().set("stock_total_key", stock);
            return true;
        } finally {
            redisLock.unLock(lockKey);
        }
    }
}
