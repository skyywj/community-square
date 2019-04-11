package com.carryjey.social.service.inf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.Tag;

import java.util.List;
import java.util.Map;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface TagService {

    Tag selectById(Integer id);

    Tag selectByName(String name);

    List<Tag> selectByIds(List<Integer> ids);

    // 根据话题查询关联的所有标签
    List<Tag> selectByTopicId(Integer topicId);

    // 将创建话题时填的tag处理并保存
    List<Tag> insertTag(String newTags);

    // 将标签的话题数都-1
    void reduceTopicCount(Integer id);

    // 查询标签关联的话题
    IPage<Map<String, Object>> selectTopicByTagId(Integer tagId, Integer pageNo);

    // 查询标签列表
    IPage<Tag> selectAll(Integer pageNo, String name);

    void update(Tag tag);

    // 如果 topic_tag 表里还有关联的数据，这里删除会报错
    void delete(Integer id);

    //同步标签的话题数
    void async();
}
