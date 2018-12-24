package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Objects;

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

    private String userName;
    private String userAvatar;
    private String title;
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

    public long getUserId() {
        return userId;
    }

    public Notification setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public Notification setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Notification setAction(String action) {
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

    public String getUserName() {
        return userName;
    }

    public Notification setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public Notification setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Notification setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Notification{"
            + "id="
            + id
            + ", topicId="
            + topicId
            + ", userId="
            + userId
            + ", targetUserId="
            + targetUserId
            + ", action='"
            + action
            + '\''
            + ", content='"
            + content
            + '\''
            + ", read="
            + read
            + ", userName='"
            + userName
            + '\''
            + ", userAvatar='"
            + userAvatar
            + '\''
            + ", title='"
            + title
            + '\''
            + ", createdTime="
            + createdTime
            + ", updatedTime="
            + updatedTime
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification that = (Notification) o;
        return userId == that.userId
            && targetUserId == that.targetUserId
            && createdTime == that.createdTime
            && updatedTime == that.updatedTime
            && Objects.equals(id, that.id)
            && Objects.equals(topicId, that.topicId)
            && Objects.equals(action, that.action)
            && Objects.equals(content, that.content)
            && Objects.equals(read, that.read)
            && Objects.equals(userName, that.userName)
            && Objects.equals(userAvatar, that.userAvatar)
            && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            topicId,
            userId,
            targetUserId,
            action,
            content,
            read,
            userName,
            userAvatar,
            title,
            createdTime,
            updatedTime);
    }
}
