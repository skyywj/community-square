package com.carryjey.social.service.inf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Comment;
import com.carryjey.social.model.Topic;
import com.carryjey.social.model.User;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface CommentService {

    // 根据话题id查询评论
    List<Map<String, Object>> selectByTopicId(Integer topicId);

    // 删除话题时删除相关的评论
    void deleteByTopicId(Integer topicId);

    // 根据用户id删除评论记录
    void deleteByUserId(long userId);

    // 保存评论
    Comment insert(String content, Topic topic, User user, Integer commentId, HttpSession session);

    Comment selectById(Integer id);

    // 更新评论
    void update(Comment comment);

    // 对评论点赞
    int vote(Comment comment, User user, HttpSession session);

    // 删除评论
    void delete(Integer id, HttpSession session);

    // 查询用户的评论
    IPage<Map<String, Object>> selectByUserId(long userId, Integer pageNo, Integer pageSize);

    // 盖楼排序
    List<Map<String, Object>> sortByLayer(List<Map<String, Object>> comments);

    IPage<Map<String, Object>> selectAllForAdmin(
            Integer pageNo, String startDate, String endDate, String username);
}
