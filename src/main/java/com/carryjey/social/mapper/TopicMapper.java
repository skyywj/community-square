package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Topic;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public interface TopicMapper extends BaseMapper<Topic> {

    IPage<Map<String, Object>> selectAll(IPage<Map<String, Object>> iPage, @Param("tab") String tab);

    IPage<Map<String, Object>> selectByTag(IPage<Map<String, Object>> iPage, @Param("tag") String tag);

    IPage<Map<String, Object>> selectByUserId(IPage<Map<String, Object>> iPage, @Param("userId") long userId);

    IPage<Map<String, Object>> selectAllForAdmin(
        IPage<Map<String, Object>> iPage,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("username") String username);
}
