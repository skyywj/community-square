package com.carryjey.social.controller.api;

import com.carryjey.social.service.inf.NotificationService;
import com.carryjey.social.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseApiController {

    @Autowired
    private NotificationService notificationService;

    //查询未读通知数目
    @GetMapping("/notRead")
    public Result notRead() {
        return success(notificationService.countNotRead(getUser().getUserId()));
    }
}
