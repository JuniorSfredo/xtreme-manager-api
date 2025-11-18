package com.juniorsfredo.xtreme_management_api.infrastructure.cache;

import com.juniorsfredo.xtreme_management_api.domain.repositories.cache.ResetPasswordCache;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ResetPasswordCacheImpl implements ResetPasswordCache {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String SAVE_CODE_PREFIX_KEY = "usr-reset-code:";

    private static final String SAVE_SESSION_PREFIX_KEY = "usr-session-reset-code:";

    @Autowired
    public ResetPasswordCacheImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode(Long userId) {
        return redisTemplate.opsForValue().get(SAVE_CODE_PREFIX_KEY + userId);
    }

    @Override
    public void saveCode(Long userId, String code) {
        String key = SAVE_CODE_PREFIX_KEY + userId;
        this.save(key, code, Duration.ofMinutes(5));
    }

    @Override
    public void deleteCode(Long userId) {

    }

    @Override
    public void saveSessionToken(Long userId, String sessionToken) {
        String key = SAVE_SESSION_PREFIX_KEY + userId;
        this.save(key, sessionToken, Duration.ofMinutes(5));
    }

    @Override
    public String getSessionToken(Long userId) {
        return "";
    }

    @Override
    public void deleteSessionToken(Long userId) {

    }

    private void save(String key, String value, @Nullable Duration minutes) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        if (minutes == null) ops.set(key, value);

        else ops.set(key, value, minutes);
    }
}
