package com.carryjey.social.controller;

import com.carryjey.social.controller.api.CommentApiController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanwenjie
 * @since 2019/1/2
 */
@SpringBootTest
@Component
@RunWith(SpringRunner.class)
public class CommentAPITest {

    @Autowired
    private CommentApiController commentApiController;


}
