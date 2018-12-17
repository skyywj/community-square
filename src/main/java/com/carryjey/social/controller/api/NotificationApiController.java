package com.carryjey.social.controller.api;

import com.carryjey.social.service.NotificationService;
import com.carryjey.social.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya. Copyright (c) 2018, All Rights Reserved. https://yiiu.co
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseApiController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notRead")
    public Result notRead() {
        return success(notificationService.countNotRead(getUser().getId()));
    }
}
