package com.carryjey.social.service.inf;

import com.carryjey.social.model.Tag;
import com.carryjey.social.model.TopicTag;

import java.util.List;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface TopicTagService {

    List<TopicTag> selectByTopicId(Integer topicId);

    List<TopicTag> selectByTagId(Integer tagId);

    void insertTopicTag(Integer topicId, List<Tag> tagList);

    // 删除话题所有关联的标签记录
    void deleteByTopicId(Integer id);
}
