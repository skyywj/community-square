package com.carryjey.social.model;

import java.util.Objects;

/**
 * @author CarryJey
 * @since 2018/12/27
 */
public class ImStat {

    private long userId;
    private long avatarVersion;
    private long createdTime;
    private long updatedTime;

    public long getUserId() {
        return userId;
    }

    public ImStat setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getAvatarVersion() {
        return avatarVersion;
    }

    public ImStat setAvatarVersion(long avatarVersion) {
        this.avatarVersion = avatarVersion;
        return this;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public ImStat setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public ImStat setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "ImStat{"
            + "userId="
            + userId
            + ", avatarVersion="
            + avatarVersion
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
        ImStat imStat = (ImStat) o;
        return userId == imStat.userId
            && avatarVersion == imStat.avatarVersion
            && createdTime == imStat.createdTime
            && updatedTime == imStat.updatedTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, avatarVersion, createdTime, updatedTime);
    }
}
