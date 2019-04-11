package com.carryjey.social.service.inf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Collect;
import com.carryjey.social.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface CollectService {

    // 查询话题被多少人收藏过
    List<Collect> selectByTopicId(Integer topicId);

    // 查询用户是否收藏过某个话题
    Collect selectByTopicIdAndUserId(Integer topicId, long userId);

    // 收藏话题
    Collect insert(Integer topicId, User user);

    // 删除（取消）收藏
    void delete(Integer topicId, long userId);

    // 根据话题id删除收藏记录
    void deleteByTopicId(Integer topicId);

    // 根据用户id删除收藏记录
    void deleteByUserId(long userId);

    // 查询用户收藏的话题数
    int countByUserId(long userId);

    // 查询用户收藏的话题
    IPage<Map<String, Object>> selectByUserId(long userId, Integer pageNo, Integer pageSize);
}
