package com.carryjey.social.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanwenjie
 * @since 2019/1/7
 */
@SpringBootTest
@Component
@RunWith(SpringRunner.class)
public class RateLimitTest {
    @Autowired
    private RateLimitingService rateLimitingService;

    @Test
    public void testLimitLogin() {
        rateLimitingService.checkLogin(526751214013124608l);
    }
}
