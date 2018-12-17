package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by tomoya. Copyright (c) 2018, All Rights Reserved. https://yiiu.co
 */
public interface TagMapper extends BaseMapper<Tag> {

    IPage<Map<String, Object>> selectTopicByTagId(IPage<Map<String, Object>> iPage, @Param("tagId") Integer tagId);
}
