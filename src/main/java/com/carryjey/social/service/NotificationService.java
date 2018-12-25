package com.carryjey.social.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.NotificationMapper;
import com.carryjey.social.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    // 查询消息
    public List<Map<String, Object>> selectByUserId(long userId, Boolean read, Integer limit) {
        List<Map<String, Object>> notifications = notificationMapper.selectByUserId(userId, read, limit);
        if (!read) {
            notificationMapper.updateNotificationStatus(userId);
        }
        return notifications;
    }

    // 查询未读消息数量
    public long countNotRead(long userId) {
        return notificationMapper.countNotRead(userId);
    }

    public void deleteByTopicId(Integer topicId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getTopicId, topicId);
        notificationMapper.delete(wrapper);
    }

    public void deleteByUserId(long userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Notification::getTargetUserId, userId).or().eq(Notification::getUserId, userId);
        notificationMapper.delete(wrapper);
    }

    public void insert(long userId, long targetUserId, Integer topicId, String action, String content) {
        Notification notification = new Notification();
        notification.setAction(action);
        notification.setContent(content);
        notification.setUserId(userId);
        notification.setTargetUserId(targetUserId);
        notification.setTopicId(topicId);
        notification.setInTime(new Date());
        notification.setRead(false);
        notificationMapper.insert(notification);
    }
}
