package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.TopicTagMapper;
import com.carryjey.social.model.Tag;
import com.carryjey.social.model.TopicTag;
import com.carryjey.social.service.inf.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class TopicTagServiceImpl implements TopicTagService {

    @Autowired
    private TopicTagMapper topicTagMapper;

    @Override
    public List<TopicTag> selectByTopicId(Integer topicId) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, topicId);
        return topicTagMapper.selectList(wrapper);
    }

    @Override
    public List<TopicTag> selectByTagId(Integer tagId) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTagId, tagId);
        return topicTagMapper.selectList(wrapper);
    }

    @Override
    public void insertTopicTag(Integer topicId, List<Tag> tagList) {
        // 先删除topicId对应的所有记录
        this.deleteByTopicId(topicId);
        // 循环保存对应关联
        tagList.forEach(
                tag -> {
                    TopicTag topicTag = new TopicTag();
                    topicTag.setTopicId(topicId);
                    topicTag.setTagId(tag.getId());
                    topicTagMapper.insert(topicTag);
                });
    }

    // 删除话题所有关联的标签记录
    @Override
    public void deleteByTopicId(Integer id) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicTag::getTopicId, id);
        topicTagMapper.delete(wrapper);
    }
}
