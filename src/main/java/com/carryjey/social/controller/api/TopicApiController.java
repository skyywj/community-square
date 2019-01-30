package com.carryjey.social.controller.api;

import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.model.Topic;
import com.carryjey.social.model.User;
import com.carryjey.social.service.TopicService;
import com.carryjey.social.util.Result;
import com.carryjey.social.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@RestController
@RequestMapping("/api/topic")
public class TopicApiController extends BaseApiController {

    @Autowired
    private TopicService topicService;

    // 保存话题
    @PostMapping("/create")
    public Result create(String title, String content, String tags, HttpSession session) {
        ApiAssert.notEmpty(title, "请输入标题");
        String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
        Set<String> set = StringUtil.removeEmpty(strings);
        ApiAssert.notTrue(set.size() > 5 || set.size() == 0, "请输入标签且标签最多5个");
        //保存话题
        // 再次将tag转成逗号隔开的字符串
        tags = StringUtils.collectionToCommaDelimitedString(set);
        ApiAssert.notTrue(topicService.selectByTitle(title) > 0, "该标题已存在，请换一个的标题吧-_-");
        Topic topic = topicService.insertTopic(title, content, tags, getUser(), session);
        return success(topic);
    }

    // 更新话题
    @PostMapping("/edit")
    public Result edit(Integer id, String title, String content, String tags) {
        ApiAssert.notEmpty(title, "请输入标题");
        String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
        Set<String> set = StringUtil.removeEmpty(strings);
        ApiAssert.notTrue(set.size() > 5 || set.size() == 0, "请输入标签且标签最多5个");
        // 更新话题
        Topic topic = topicService.selectById(id);
        ApiAssert.isTrue(topic.getUserId() == getUser().getUserId(), "谁给你的权限修改别人的话题的？");
        // 再次将tag转成逗号隔开的字符串
        tags = StringUtils.collectionToCommaDelimitedString(set);
        topic = topicService.updateTopic(topic, title, content, tags);
        return success(topic);
    }

    // 删除话题
    @GetMapping("/delete")
    public Result delete(Integer id, HttpSession session) {
        Topic topic = topicService.selectById(id);
        ApiAssert.isTrue(topic.getUserId() == getUser().getUserId(), "谁给你的权限删除别人的话题的？");
        topicService.delete(topic, session);
        return success();
    }

    @GetMapping("/vote")
    public Result vote(Integer id, HttpSession session) {
        User user = getUser();
        Topic topic = topicService.selectById(id);
        ApiAssert.notNull(topic, "这个话题可能已经被删除了");
        ApiAssert.notTrue(topic.getUserId() == user.getUserId(), "给自己话题点赞，脸皮真厚！！");
        int voteCount = topicService.vote(topic, getUser(), session);
        return success(voteCount);
    }
}
