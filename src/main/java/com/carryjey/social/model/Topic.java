package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String content;
    private Date inTime;
    private Date modifyTime;
    private long userId;
    private String userName;
    private String userAvatar;
    // 评论数
    private Integer commentCount;
    // 收藏数
    private Integer collectCount;
    // 浏览数
    private Integer view;
    // 置顶
    private Boolean top;
    // 加精
    private Boolean good;
    // 点赞用户的id
    private String upIds;

    private long createdTime;
    private long updatedTime;

    public String getUpIds() {
        return upIds;
    }

    public void setUpIds(String upIds) {
        this.upIds = upIds;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public Topic setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public Topic setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public Topic setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public Topic setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "Topic{"
            + "id="
            + id
            + ", title='"
            + title
            + '\''
            + ", content='"
            + content
            + '\''
            + ", inTime="
            + inTime
            + ", modifyTime="
            + modifyTime
            + ", userId="
            + userId
            + ", userName='"
            + userName
            + '\''
            + ", userAvatar='"
            + userAvatar
            + '\''
            + ", commentCount="
            + commentCount
            + ", collectCount="
            + collectCount
            + ", view="
            + view
            + ", top="
            + top
            + ", good="
            + good
            + ", upIds='"
            + upIds
            + '\''
            + ", createdTime="
            + createdTime
            + ", updatedTime="
            + updatedTime
            + '}';
    }
}
