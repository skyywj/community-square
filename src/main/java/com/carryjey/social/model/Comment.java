package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 8413239906874427490L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer topicId;
    private long userId;
    private String userName;
    private String userAvatar;
    private String content;
    private Integer commentId;
    // 点赞用户的id
    private String upIds;
    private Date inTime;
    private long createdTime;
    private long updatedTime;

    public String getUpIds() {
        return upIds;
    }

    public void setUpIds(String upIds) {
        this.upIds = upIds;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public Comment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public Comment setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public Comment setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public Comment setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "Comment{"
            + "id="
            + id
            + ", topicId="
            + topicId
            + ", userId="
            + userId
            + ", userName='"
            + userName
            + '\''
            + ", userAvatar='"
            + userAvatar
            + '\''
            + ", content='"
            + content
            + '\''
            + ", commentId="
            + commentId
            + ", upIds='"
            + upIds
            + '\''
            + ", inTime="
            + inTime
            + ", createdTime="
            + createdTime
            + ", updatedTime="
            + updatedTime
            + '}';
    }
}
