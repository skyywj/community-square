package com.carryjey.social.controller.api;

import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.model.Comment;
import com.carryjey.social.model.Topic;
import com.carryjey.social.service.inf.CommentService;
import com.carryjey.social.service.inf.TopicService;
import com.carryjey.social.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@RestController
@RequestMapping("/api/comment")
public class CommentApiController extends BaseApiController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;

    // 创建评论
    @PostMapping("/create")
    public Result create(String content, Integer topicId, Integer commentId, HttpSession session) {
        ApiAssert.notEmpty(content, "请输入评论内容");
        ApiAssert.notNull(topicId, "话题ID呢？");
        Topic topic = topicService.selectById(topicId);
        ApiAssert.notNull(topic, "你晚了一步，话题可能已经被删除了");
        Comment comment = commentService.insert(content, topic, getUser(), commentId, session);
        return success(comment);
    }

    // 更新评论
    @PostMapping("/update")
    public Result update(Integer id, String content) {
        ApiAssert.notNull(id, "评论ID呢？");
        ApiAssert.notEmpty(content, "请输入评论内容");
        Comment comment = commentService.selectById(id);
        ApiAssert.notNull(comment, "这个评论可能已经被删除了，多发点对别人有帮助的评论吧");
        comment.setContent(content);
        commentService.update(comment);
        return success(comment);
    }

    // 删除评论
    @GetMapping("/delete")
    public Result delete(Integer id, HttpSession session) {
        commentService.delete(id, session);
        return success();
    }

    // 点赞评论
    @GetMapping("/vote")
    public Result vote(Integer id, HttpSession session) {
        Comment comment = commentService.selectById(id);
        ApiAssert.notNull(comment, "这个评论可能已经被删除了");
        ApiAssert.notTrue(comment.getUserId() == getUser().getUserId(), "给自己评论点赞，脸皮真厚！！");
        int voteCount = commentService.vote(comment, getUser(), session);
        return success(voteCount);
    }
}
