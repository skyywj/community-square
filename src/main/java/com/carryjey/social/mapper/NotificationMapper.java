package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carryjey.social.model.Notification;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public interface NotificationMapper extends BaseMapper<Notification> {

    List<Map<String, Object>> selectByUserId(
            @Param("userId") long toUserId, @Param("read") Boolean read, @Param("limit") Integer limit);

    // 查询未读消息数量
    @Select("select count(1) from notification where to_user_id = #{toUserId} and `read` = false")
    long countNotRead(@Param("toUserId") long toUserId);

    // 将未读消息置为已读
    @Update("update notification set `read` = true where to_user_id = #{toUserId}")
    void updateNotificationStatus(@Param("toUserId") long toUserId);

    @Select("select * from notification where topic_id = #{topicId} and from_user_id = #{fromUserId} and to_user_id = #{toUserId} and action = #{action}")
    List<Notification> getNotification(@Param("topicId") long topicId, @Param("fromUserId") long fromUserId, @Param("toUserId") long toUserId, @Param("action") int action);

    @Update("update notification set updated_time = #{time} where topic_id = #{topicId} and from_user_id = #{fromUserId} and to_user_id = #{toUserId} and action = #{action}")
    void updateNotification(@Param("topicId") long topicId, @Param("fromUserId") long fromUserId, @Param("toUserId") long toUserId, @Param("action") int action, @Param("time") long time);

    @Delete("delete from notification where topic_id = #{topicId} and from_user_id = #{fromUserId} and to_user_id = #{toUserId} and action = #{action}")
    void cancelNotification(@Param("topicId") long topicId, @Param("fromUserId") long fromUserId, @Param("toUserId") long toUserId, @Param("action") int action);
}
