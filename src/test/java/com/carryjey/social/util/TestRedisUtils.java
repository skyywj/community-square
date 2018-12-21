package com.carryjey.social.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanwenjie
 * @since 2018/12/21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestRedisUtils {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testCon() {
        redisUtil.setString("test", "001");
    }
}
