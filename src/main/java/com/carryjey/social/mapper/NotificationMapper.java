package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carryjey.social.model.Notification;
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
        @Param("userId") long userId, @Param("read") Boolean read, @Param("limit") Integer limit);

    // 查询未读消息数量
    @Select("select count(1) from notification where target_user_id = #{userId} and `read` = false")
    long countNotRead(@Param("userId") long userId);

    // 将未读消息置为已读
    @Update("update notification set `read` = true where target_user_id = #{targetUserId}")
    void updateNotificationStatus(@Param("targetUserId") long targetUserId);
}
