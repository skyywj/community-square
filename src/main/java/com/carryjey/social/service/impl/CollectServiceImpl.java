package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carryjey.social.mapper.CollectMapper;
import com.carryjey.social.model.Collect;
import com.carryjey.social.model.Topic;
import com.carryjey.social.model.User;
import com.carryjey.social.service.inf.CollectService;
import com.carryjey.social.service.inf.NotificationService;
import com.carryjey.social.service.inf.SystemConfigService;
import com.carryjey.social.service.inf.TopicService;
import com.carryjey.social.util.Constants;
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
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    TopicTagServiceImpl topicTagService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private NotificationService notificationService;

    // 查询话题被多少人收藏过
    @Override
    public List<Collect> selectByTopicId(Integer topicId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getTopicId, topicId);
        return collectMapper.selectList(wrapper);
    }

    // 查询用户是否收藏过某个话题
    @Override
    public Collect selectByTopicIdAndUserId(Integer topicId, long userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getTopicId, topicId).eq(Collect::getUserId, userId);
        List<Collect> collects = collectMapper.selectList(wrapper);
        if (collects.size() > 0) {
            return collects.get(0);
        }
        return null;
    }

    // 收藏话题
    @Override
    public Collect insert(Integer topicId, User user) {
        Collect collect = new Collect();
        collect.setTopicId(topicId);
        collect.setUserId(user.getUserId());
        collect.setInTime(new Date());
        collectMapper.insert(collect);

        // 通知
        Topic topic = topicService.selectById(topicId);
        // 收藏自己的话题不发通知

        if (user.getUserId() != topic.getUserId()) {
            notificationService.insert(user, topic.getUserId(), topic, Constants.COLLECT, null);
        }

        return collect;
    }

    // 删除（取消）收藏
    @Override
    public void delete(Integer topicId, long userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getTopicId, topicId).eq(Collect::getUserId, userId);
        collectMapper.delete(wrapper);
    }

    // 根据话题id删除收藏记录
    @Override
    public void deleteByTopicId(Integer topicId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getTopicId, topicId);
        collectMapper.delete(wrapper);
    }

    // 根据用户id删除收藏记录
    @Override
    public void deleteByUserId(long userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getUserId, userId);
        collectMapper.delete(wrapper);
    }

    // 查询用户收藏的话题数
    @Override
    public int countByUserId(long userId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Collect::getUserId, userId);
        return collectMapper.selectCount(wrapper);
    }

    // 查询用户收藏的话题
    @Override
    public IPage<Map<String, Object>> selectByUserId(long userId, Integer pageNo, Integer pageSize) {
        IPage<Map<String, Object>> iPage =
                new Page<>(
                        pageNo,
                        pageSize == null
                                ? Integer.parseInt(systemConfigService.selectAllConfig().get("pageSize").toString())
                                : pageSize);
        iPage = collectMapper.selectByUserId(iPage, userId);
        topicService.selectTags(iPage, topicTagService, tagService);
        return iPage;
    }
}
