package com.juniorsfredo.xtreme_management_api.infrastructure.cache;

import com.juniorsfredo.xtreme_management_api.domain.repositories.cache.ResetPasswordCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class ResetPasswordCacheImpl implements ResetPasswordCache {

    private RedisTemplate<String, String> redisTemplate;

    private final String genericKey = "usr-reset-code:";

    @Override
    public String getCode(Long userId) {
        return redisTemplate.opsForValue().get(genericKey + userId);
    }

    @Override
    public void saveCode(Long userId, String code) {
        String key = genericKey + userId;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, code, Duration.ofMinutes(5));
    }

    @Override
    public void deleteCode(Long userId) {

    }
}
