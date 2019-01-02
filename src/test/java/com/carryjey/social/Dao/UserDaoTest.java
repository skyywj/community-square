package com.carryjey.social.Dao;

import com.carryjey.social.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author CarryJey
 * @since 2019/1/2
 */
@SpringBootTest
@Component
@RunWith(SpringRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUserById() {
        User user = userDao.getByUserId(526751214013124608l);
        System.out.print(user.getUsername() + "\n");
    }
}
