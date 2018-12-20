package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<Map<String, Object>> selectByTopicId(@Param("topicId") Integer topicId);

    IPage<Map<String, Object>> selectByUserId(IPage<Map<String, Object>> iPage, @Param("userId") long userId);

    IPage<Map<String, Object>> selectAllForAdmin(
        IPage<Map<String, Object>> iPage,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("username") String username);
}
