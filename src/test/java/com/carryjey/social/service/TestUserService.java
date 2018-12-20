package com.carryjey.social.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanwenjie
 * @since 2018/12/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testSelectByUserName(){
        System.out.print(userService.selectByUsername("CarryJey"));
    }
}
