package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
public class User implements Serializable {

    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private long userId;
    private String username;
    private String githubName;
    private String telegramName;
    private String avatar;

    @JsonIgnore
    private String password;

    private String email;
    private String website;
    private String bio;
    private Integer score;
    private String token;
    // 有消息通知是否通过邮箱收取
    private Boolean emailNotification;
    private long createdTime;
    private long updatedTime;

    // 有消息通知是否通过telegram收取
    // 文档上写的可以通过username跟userId发送消息，但测试结果是只能通过userId发送
    // 难道我调接口的姿势不对？待我后面再收拾它。。
    // private Boolean telegramNotification;

    public long getUserId() {
        return userId;
    }

    public User setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getTelegramName() {
        return telegramName;
    }

    public void setTelegramName(String telegramName) {
        this.telegramName = telegramName;
    }

    public String getGithubName() {
        return githubName;
    }

    public void setGithubName(String githubName) {
        this.githubName = githubName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public User setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public User setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "User{"
            + "id="
            + id
            + ", userId="
            + userId
            + ", username='"
            + username
            + '\''
            + ", githubName='"
            + githubName
            + '\''
            + ", telegramName='"
            + telegramName
            + '\''
            + ", avatar='"
            + avatar
            + '\''
            + ", password='"
            + password
            + '\''
            + ", email='"
            + email
            + '\''
            + ", website='"
            + website
            + '\''
            + ", bio='"
            + bio
            + '\''
            + ", score="
            + score
            + ", token='"
            + token
            + '\''
            + ", emailNotification="
            + emailNotification
            + ", createdTime="
            + createdTime
            + ", updatedTime="
            + updatedTime
            + '}';
    }
}
