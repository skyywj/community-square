package com.carryjey.social.service;

import com.carryjey.social.service.inf.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by CarryJey on 2019-04-11.
 */
@SpringBootTest
@Component
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail(){
        //收件邮箱
        String toMailAddress = "test@qq.com";
        //邮件标题
        String title = "我们去爬山吧？";
        //邮件内容
        String content = "明天有时间么？";
        emailService.sendEmail(toMailAddress,title,content);
    }
}
