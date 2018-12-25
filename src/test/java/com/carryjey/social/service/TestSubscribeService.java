package com.carryjey.social.service;

import com.carryjey.social.model.SubscribeRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestSubscribeService {

    @Autowired
    private SubscribeService subscribeService;

    @Test
    public void getSubscribeListTest() {
        List<SubscribeRecord> subscribeRecords = subscribeService.getSubscribeList();
        System.out.print(subscribeRecords.size() + "\n");
    }
}
