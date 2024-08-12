package org.school.stylish.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimiterUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final int RATE_LIMIT = 10;
    private final int EXPIRE_TIME = 1; // 1 seconds

    public boolean allowRequest(String ip) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String countStr = valueOperations.get(ip);
        int count = countStr != null ? Integer.parseInt(countStr) : 0;

        if (count < RATE_LIMIT) {
            valueOperations.increment(ip);
            if (count == 0) {
                redisTemplate.expire(ip, EXPIRE_TIME, java.util.concurrent.TimeUnit.SECONDS);
            }
            return true;
        } else {
            return false;
        }
    }
}
