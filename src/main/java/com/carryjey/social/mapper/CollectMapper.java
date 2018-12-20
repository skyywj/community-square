package com.carryjey.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public interface CollectMapper extends BaseMapper<Collect> {

    IPage<Map<String, Object>> selectByUserId(IPage<Map<String, Object>> iPage, @Param("userId") long userId);
}
