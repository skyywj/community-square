package com.carryjey.social.controller.api;

import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.model.Collect;
import com.carryjey.social.service.CollectService;
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
@RequestMapping("/api/collect")
public class CollectApiController extends BaseApiController {

    @Autowired
    private CollectService collectService;

    // 收藏话题
    @GetMapping("/get")
    public Result get(Integer topicId) {
        Collect collect = collectService.selectByTopicIdAndUserId(topicId, getUser().getId());
        ApiAssert.isNull(collect, "做人要知足，每人每个话题只能收藏一次哦！");
        collectService.insert(topicId, getUser().getId());
        return success();
    }

    // 取消收藏
    @GetMapping("/delete")
    public Result delete(Integer topicId) {
        Collect collect = collectService.selectByTopicIdAndUserId(topicId, getUser().getId());
        ApiAssert.notNull(collect, "你都没有收藏这个话题，哪来的取消？");
        collectService.delete(topicId, getUser().getId());
        return success();
    }
}
