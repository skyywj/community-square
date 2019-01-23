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
    private String topicTitle;
    private long fromUserId;
    private String fromUserName;
    private String fromUserAvatar;
    // 通知对象ID
    private long toUserId;
    // 动作: REPLY, COMMENT, COLLECT, TOPIC_UP, COMMENT_UP
    private int action;
    private String content;
    // 是否已读
    @TableField("`read`")
    private Boolean read;

    private long createdTime;
    private long updatedTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Notification setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public Notification setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public Notification setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
        return this;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public Notification setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public Notification setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public Notification setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
        return this;
    }

    public long getToUserId() {
        return toUserId;
    }

    public Notification setToUserId(long toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public int getAction() {
        return action;
    }

    public Notification setAction(int action) {
        this.action = action;
        return this;
    }


    public String getContent() {
        return content;
    }

    public Notification setContent(String content) {
        this.content = content;
        return this;
    }


    public Boolean getRead() {
        return read;
    }

    public Notification setRead(Boolean read) {
        this.read = read;
        return this;
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
