package com.carryjey.social.service.inf;

import com.carryjey.social.model.Notification;
import com.carryjey.social.model.Topic;
import com.carryjey.social.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface NotificationService {
    // 查询消息
    List<Map<String, Object>> selectByUserId(long toUserId, Boolean read, Integer limit);

    // 查询未读消息数量
    long countNotRead(long userId);

    void deleteByTopicId(Integer topicId);

    void deleteByUserId(long userId);

    void insert(User user, long toUserId, Topic topic, int action, String content);

    List<Notification> getNotification(long topicId, long fromUserId, long toUserId, int action);

    void updateNotificationTime(long topicId, long fromUserId, long toUserId, int action);

    void cancelNotification(long topicId, long fromUserId, long toUserId, int action);
}
