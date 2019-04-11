package com.carryjey.social.service.impl;

import com.carryjey.social.CommunitySquareApplication.RedisConfig;
import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.service.inf.RateLimitingService;
import com.carryjey.social.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/**
 * @author CarryJey
 * @since 2019/1/5
 */
@Service
public class RateLimitingServiceImpl implements RateLimitingService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisConfig redisConfig;

    /**
     * redis key
     */
    private static final String RATE_LIMIT_LOGIN = "login";

    @Value("${app.rateLimit.login:10}")
    private int configKeyRateLimitLogin;

    private String getKey(String module, String prefix, String subject) {
        return "rate_limit:" + module + ":" + prefix + subject;
    }

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private void checkVcodeLimit(String key, long limit, TimeUnit timeUnit, String warn) {
        String limitKey = redisConfig.addPrefix(key);

        long count = redisTemplate.opsForValue().increment(limitKey, 1);
        if (count == 1) {
            // 暂时不考虑设置超时时间失败的情况
            redisTemplate.expire(limitKey, 1, timeUnit);
        }

        if (count > limit) {
            logger.info("too many requests, limit key: {}, count: {}, limit: {}.", limitKey, count, limit);
            ApiAssert.newStatusException(ErrorCode.RATE_LIMIT_ERROR, warn);
        }
    }

    /**
     * @param key redis-key
     * @param limit 限制频次数
     * @param timeUnit 时间长度，秒/分/时/天
     * @param expireDelayed 超时是否延期
     * @param warn 超频提醒
     */
    private void checkLimit(String key, long limit, TimeUnit timeUnit, Boolean expireDelayed, String warn) {
        /**
         * @param 超时时间倍数，tiemout=2，超时时间则为2*timeUnit
         */
        checkLimit(key, limit, 1, timeUnit, expireDelayed, warn);
    }

    private void checkLimit(
        String key, long limit, int timeout, TimeUnit timeUnit, Boolean expireDelayed, String warn) {
        String limitKey = redisConfig.addPrefix(key);

        long count = redisTemplate.opsForValue().increment(limitKey, 1);
        if (count == 1) {
            // 暂时不考虑设置超时时间失败的情况
            redisTemplate.expire(limitKey, timeout, timeUnit);
        }

        if (count > limit) {
            logger.warn("too many requests, limit key: {}, count: {}, limit: {}.", limitKey, count, limit);
            if (expireDelayed && timeout > 0) {
                // 超过临界值之后, 每次错误都延长记录有效期
                redisTemplate.expire(limitKey, timeout, timeUnit);
            }
            ApiAssert.newStatusException(ErrorCode.RATE_LIMIT_ERROR, warn);
        }
    }

    @Override
    public void checkLogin(long userId) {
        //登录限制每分钟30次
        String key = getKey(RATE_LIMIT_LOGIN, "per_user_id_", String.valueOf(userId));
        String warn = "您的登录操作过于频繁，每分钟限制登录次数为" + configKeyRateLimitLogin + "次,请1分钟后重新尝试";
        checkLimit(key, configKeyRateLimitLogin, TimeUnit.MINUTES, false, warn);
    }
}
