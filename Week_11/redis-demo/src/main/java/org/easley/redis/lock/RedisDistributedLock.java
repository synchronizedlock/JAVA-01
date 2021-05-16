package org.easley.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class RedisDistributedLock {

    private Jedis jedis;

    public RedisDistributedLock() {
        this.jedis = new Jedis("localhost", 6379);
    }

    public String acquireDistributedLock(String lockName, long acquireTimeoutInMS, long lockTimeoutInMS) {
        String retIdentifier = null;
        try {
            String lockKey = "lock:" + lockName;
            String identifier = UUID.randomUUID().toString();
            int lockExpire = (int) (lockTimeoutInMS / 1000L);
            long end = System.currentTimeMillis() + acquireTimeoutInMS;

            while (System.currentTimeMillis() < end) {
                if (jedis.setnx(lockKey, identifier) == 1L) {
                    jedis.expire(lockKey, lockExpire);
                    retIdentifier = identifier;
                    break;
                }
                if (jedis.ttl(lockKey) == -1L) {
                    jedis.expire(lockKey, lockExpire);
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException var24) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retIdentifier;
    }

    public boolean releaseDistributedLock(String lockName, String identifier) {
        boolean retFlag = false;
        try {
            String lockKey = "lock:" + lockName;
            while (true) {
                jedis.watch(new String[]{lockKey});
                if (!identifier.equals(jedis.get(lockKey))) {
                    break;
                }

                Transaction trans = jedis.multi();
                trans.del(lockKey);
                List<Object> results = trans.exec();
                if (results != null) {
                    retFlag = true;
                    break;
                }
            }
            jedis.unwatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retFlag;
    }


    public String tryLock(String lockKey) {
        return acquireDistributedLock(lockKey, 10, 10);
    }

    public boolean unLock(String lockKey, String identifier) {
        return releaseDistributedLock(lockKey, identifier);
    }
}
