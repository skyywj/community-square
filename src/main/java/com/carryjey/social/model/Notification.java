package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class Notification implements Serializable {
    private static final long serialVersionUID = 3235461023789820336L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer topicId;
    private long userId;
    // 通知对象ID
    private long targetUserId;
    // 动作: REPLY, COMMENT, COLLECT, TOPIC_UP, COMMENT_UP
    private String action;
    private String content;
    // 是否已读
    @TableField("`read`")
    private Boolean read;

    private long createdTime;
    private long updatedTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public Notification setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public Notification setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }
}
