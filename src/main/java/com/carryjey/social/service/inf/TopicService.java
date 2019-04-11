package com.carryjey.social.service.inf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.*;
import com.carryjey.social.service.impl.TopicTagServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface TopicService {

    IPage<Map<String, Object>> selectAll(Integer pageNo, String tab);

    void selectTags(IPage<Map<String, Object>> page, TopicTagService topicTagService, TagService tagService);

    // 查询话题作者其它的话题
    List<Topic> selectAuthorOtherTopic(long userId, Integer topicId, Integer limit);

    // 查询用户的话题
    IPage<Map<String, Object>> selectByUserId(long userId, Integer pageNo, Integer pageSize);

    // 保存话题
    Topic insertTopic(String title, String content, String tags, User user, HttpSession session);

    // 根据id查询话题
    Topic selectById(Integer id);

    int selectByTitle(String title);

    // 更新话题
    void update(Topic topic);

    // 更新话题
    Topic updateTopic(Topic topic, String title, String content, String tags);

    // 删除话题
    void delete(Topic topic, HttpSession session);

    // 根据用户id删除帖子
    void deleteByUserId(long userId);

    IPage<Map<String, Object>> selectAllForAdmin(
            Integer pageNo, String startDate, String endDate, String username);

    int vote(Topic topic, User user, HttpSession session);

}
