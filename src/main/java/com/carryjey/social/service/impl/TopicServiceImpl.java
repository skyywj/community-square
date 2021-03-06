package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carryjey.social.mapper.TopicMapper;
import com.carryjey.social.model.*;
import com.carryjey.social.service.inf.*;
import com.carryjey.social.util.Constants;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private TopicTagService topicTagService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public IPage<Map<String, Object>> selectAll(Integer pageNo, String tab) {
        IPage<Map<String, Object>> iPage =
                new Page<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig().get("pageSize").toString()));
        IPage<Map<String, Object>> page = topicMapper.selectAll(iPage, tab);
        selectTags(page, topicTagService, tagService);
        return page;
    }

    @Override
    public void selectTags(IPage<Map<String, Object>> page, TopicTagService topicTagService, TagService tagService) {
        page.getRecords()
                .forEach(
                        map -> {
                            List<TopicTag> topicTags = topicTagService.selectByTopicId((Integer) map.get("id"));
                            List<Integer> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                            List<Tag> tags = tagService.selectByIds(tagIds);
                            map.put("tags", tags);
                        });
    }

    // 查询话题作者其它的话题
    @Override
    public List<Topic> selectAuthorOtherTopic(long userId, Integer topicId, Integer limit) {
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("in_time");
        if (topicId != null) {
            wrapper.lambda().ne(Topic::getId, topicId);
        }
        if (limit != null) {
            wrapper.last("limit " + limit);
        }
        return topicMapper.selectList(wrapper);
    }

    // 查询用户的话题
    @Override
    public IPage<Map<String, Object>> selectByUserId(long userId, Integer pageNo, Integer pageSize) {
        IPage<Map<String, Object>> iPage =
                new Page<>(
                        pageNo,
                        pageSize == null
                                ? Integer.parseInt(systemConfigService.selectAllConfig().get("pageSize").toString())
                                : pageSize);
        return topicMapper.selectByUserId(iPage, userId);
    }

    // 保存话题
    @Override
    public Topic insertTopic(String title, String content, String tags, User user, HttpSession session) {
        Topic topic = new Topic();
        topic.setTitle(Jsoup.clean(title, Whitelist.simpleText()));
        topic.setContent(content);
        topic.setInTime(new Date());
        topic.setUserId(user.getUserId());
        topic.setUserName(user.getUsername());
        topic.setUserAvatar(user.getAvatar());
        topic.setCreatedTime(System.currentTimeMillis());
        topic.setUpdatedTime(System.currentTimeMillis());
        topicMapper.insert(topic);
        // 增加用户积分
        user.setScore(
                user.getScore()
                        + Integer.parseInt(systemConfigService.selectAllConfig().get("createTopicScore").toString()));
        userService.update(user);
        if (session != null) {
            session.setAttribute("_user", user);
        }
        // 保存标签
        List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
        // 处理标签与话题的关联
        topicTagService.insertTopicTag(topic.getId(), tagList);
        return topic;
    }

    // 根据id查询话题
    @Override
    public Topic selectById(Integer id) {
        return topicMapper.selectById(id);
    }

    @Override
    public int selectByTitle(String title) {
        List<Topic> topics = topicMapper.selectByTitle(title);
        return topics.size();
    }

    // 更新话题
    @Override
    public void update(Topic topic) {
        topicMapper.updateById(topic);
    }

    // 更新话题
    @Override
    public Topic updateTopic(Topic topic, String title, String content, String tags) {
        topic.setTitle(Jsoup.clean(title, Whitelist.simpleText()));
        topic.setContent(content);
        topic.setModifyTime(new Date());
        topicMapper.updateById(topic);
        // 旧标签每个topicCount都-1
        tagService.reduceTopicCount(topic.getId());
        // 保存标签
        List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
        // 处理标签与话题的关联
        topicTagService.insertTopicTag(topic.getId(), tagList);
        return topic;
    }

    // 删除话题
    @Override
    public void delete(Topic topic, HttpSession session) {
        Integer id = topic.getId();
        // 删除相关通知
        notificationService.deleteByTopicId(id);
        // 删除相关收藏
        collectService.deleteByTopicId(id);
        // 删除相关的评论
        commentService.deleteByTopicId(id);
        // 将话题对应的标签 topicCount -1
        tagService.reduceTopicCount(id);
        // 删除相应的关联标签
        topicTagService.deleteByTopicId(id);
        // 减去用户积分
        User user = userService.selectByUserId(topic.getUserId());
        user.setScore(
                user.getScore()
                        - Integer.parseInt(systemConfigService.selectAllConfig().get("deleteTopicScore").toString()));
        userService.update(user);
        if (session != null) {
            session.setAttribute("_user", user);
        }
        // 最后删除话题
        topicMapper.deleteById(id);
    }

    // 根据用户id删除帖子
    @Override
    public void deleteByUserId(long userId) {
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Topic::getUserId, userId);
        topicMapper.delete(wrapper);
    }

    // ---------------------------- admin ----------------------------

    @Override
    public IPage<Map<String, Object>> selectAllForAdmin(
            Integer pageNo, String startDate, String endDate, String username) {
        IPage<Map<String, Object>> iPage =
                new Page<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("pageSize")));
        return topicMapper.selectAllForAdmin(iPage, startDate, endDate, username);
    }

    @Override
    public int vote(Topic topic, User user, HttpSession session) {
        String upIds = topic.getUpIds();
        // 将点赞用户id的字符串转成集合
        Set<String> strings = StringUtils.commaDelimitedListToSet(upIds);
        // 把新的点赞用户id添加进集合，这里用set，正好可以去重，如果集合里已经有用户的id了，那么这次动作被视为取消点赞
        Integer userScore = user.getScore();
        if (strings.contains(String.valueOf(user.getUserId()))) { // 取消点赞行为
            strings.remove(String.valueOf(user.getUserId()));
            userScore -= Integer.parseInt(systemConfigService.selectAllConfig().get("upTopicScore").toString());
            notificationService.cancelNotification(topic.getId(), user.getUserId(), topic.getUserId(), Constants.VOTE_TOPIC);
        } else { // 点赞行为
            strings.add(String.valueOf(user.getUserId()));
            userScore += Integer.parseInt(systemConfigService.selectAllConfig().get("upTopicScore").toString());
            List<Notification> notifications = notificationService.getNotification(topic.getId(), user.getUserId(), topic.getUserId(), Constants.VOTE_TOPIC);
            if (notifications.size() > 0) {
                notificationService.updateNotificationTime(topic.getId(), user.getUserId(), topic.getUserId(), Constants.VOTE_TOPIC);
            } else {
                notificationService.insert(user, topic.getUserId(), topic, Constants.VOTE_TOPIC, "");
            }
        }
        // 再把这些id按逗号隔开组成字符串
        topic.setUpIds(StringUtils.collectionToCommaDelimitedString(strings));
        // 更新帖子点赞
        this.update(topic);
        // 更新积分
        user.setScore(userScore);
        userService.update(user);
        if (session != null) {
            session.setAttribute("_user", user);
        }
        return strings.size();
    }
}
