package com.carryjey.social.controller.front;

import com.carryjey.social.model.SubscribeRecord;
import com.carryjey.social.service.impl.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/25
 */
@Controller
@RequestMapping("/subscribe")
public class SubscribeController {
    private final Logger logger = LoggerFactory.getLogger(SubscribeController.class);

    @Autowired
    private SubscribeService subscribeService;

    @GetMapping("/add")
    public boolean subscribe(@RequestParam long fromUserId) {
        try {
            subscribeService.subscribe(fromUserId);
            return true;
        } catch (Exception ex) {
            logger.warn("subscribe error fromUserId:{}", fromUserId);
            throw new RuntimeException();
        }
    }

    @GetMapping("/cancel")
    public boolean cancelSubscribe(@RequestParam long fromUserId) {
        try {
            subscribeService.cancelSubscribe(fromUserId);
            return true;
        } catch (Exception ex) {
            logger.warn("cancel subscribe error fromUserId:{}", fromUserId);
            throw new RuntimeException();
        }
    }

    @GetMapping("/list")
    public List<SubscribeRecord> cancelSubscribe() {
        try {
            return subscribeService.getSubscribeList();
        } catch (Exception ex) {
            logger.warn("get subscribe list error");
            throw new RuntimeException();
        }
    }
}
