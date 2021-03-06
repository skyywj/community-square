package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.NotificationMapper;
import com.carryjey.social.model.Notification;
import com.carryjey.social.model.Topic;
import com.carryjey.social.model.User;
import com.carryjey.social.service.inf.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    // 查询消息
    @Override
    public List<Map<String, Object>> selectByUserId(long toUserId, Boolean read, Integer limit) {
        List<Map<String, Object>> notifications = notificationMapper.selectByUserId(toUserId, read, limit);
        if (!read) {
            notificationMapper.updateNotificationStatus(toUserId);
        }
        return notifications;
    }

    // 查询未读消息数量
    @Override
    public long countNotRead(long userId) {
        return notificationMapper.countNotRead(userId);
    }

    @Override
    public void deleteByTopicId(Integer topicId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getTopicId, topicId);
        notificationMapper.delete(wrapper);
    }

    @Override
    public void deleteByUserId(long userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getToUserId, userId).or().eq(Notification::getToUserId, userId);
        notificationMapper.delete(wrapper);
    }

    @Override
    public void insert(User user, long toUserId, Topic topic, int action, String content) {
        Notification notification = new Notification();
        notification.setAction(action);
        notification.setContent(content);
        notification.setFromUserId(user.getUserId());
        notification.setFromUserAvatar(user.getAvatar());
        notification.setFromUserName(user.getUsername());
        notification.setToUserId(toUserId);
        notification.setTopicId(topic.getId());
        notification.setTopicTitle(topic.getTitle());
        notification.setRead(false);
        notification.setCreatedTime(System.currentTimeMillis());
        notification.setUpdatedTime(System.currentTimeMillis());
        notificationMapper.insert(notification);
    }

    @Override
    public List<Notification> getNotification(long topicId, long fromUserId, long toUserId, int action) {
        List<Notification> notifications = notificationMapper.getNotification(topicId, fromUserId, toUserId, action);
        return notifications;
    }

    @Override
    public void updateNotificationTime(long topicId, long fromUserId, long toUserId, int action) {
        notificationMapper.updateNotification(topicId, fromUserId, toUserId, action, System.currentTimeMillis());
    }

    @Override
    public void cancelNotification(long topicId, long fromUserId, long toUserId, int action) {
        notificationMapper.cancelNotification(topicId, fromUserId, toUserId, action);
    }
}
