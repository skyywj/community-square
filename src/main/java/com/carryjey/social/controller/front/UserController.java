package com.carryjey.social.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.User;
import com.carryjey.social.service.CollectService;
import com.carryjey.social.service.CommentService;
import com.carryjey.social.service.TopicService;
import com.carryjey.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectService collectService;

    @GetMapping("/{username}")
    public String profile(@PathVariable String username, Model model) {
        // 查询用户个人信息
        User user = userService.selectByUsername(username);
        // 查询用户的话题
        IPage<Map<String, Object>> topics = topicService.selectByUserId(user.getUserId(), 1, 10);
        // 查询用户参与的评论
        IPage<Map<String, Object>> comments = commentService.selectByUserId(user.getUserId(), 1, 10);
        // 查询用户收藏的话题数
        Integer collectCount = collectService.countByUserId(user.getUserId());

        model.addAttribute("user", user);
        model.addAttribute("topics", topics);
        model.addAttribute("comments", comments);
        model.addAttribute("collectCount", collectCount);
        return "front/user/profile";
    }

    @GetMapping("/{username}/topics")
    public String topics(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        // 查询用户个人信息
        User user = userService.selectByUsername(username);
        // 查询用户的话题
        IPage<Map<String, Object>> topics = topicService.selectByUserId(user.getUserId(), pageNo, null);
        model.addAttribute("user", user);
        model.addAttribute("topics", topics);
        return "front/user/topics";
    }

    @GetMapping("/{username}/comments")
    public String comments(
        @PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        // 查询用户个人信息
        User user = userService.selectByUsername(username);
        // 查询用户参与的评论
        IPage<Map<String, Object>> comments = commentService.selectByUserId(user.getUserId(), pageNo, null);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "front/user/comments";
    }

    @GetMapping("/{username}/collects")
    public String collects(
        @PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        // 查询用户个人信息
        User user = userService.selectByUsername(username);
        // 查询用户参与的评论
        IPage<Map<String, Object>> collects = collectService.selectByUserId(user.getUserId(), pageNo, null);
        model.addAttribute("user", user);
        model.addAttribute("collects", collects);
        return "front/user/collects";
    }
}
