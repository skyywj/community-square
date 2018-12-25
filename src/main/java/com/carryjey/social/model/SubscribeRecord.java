package com.carryjey.social.model;

import java.util.Objects;

/**
 * @author CarryJey
 * @since 2018/12/25
 */
public class SubscribeRecord {
    private long userId;
    private long frromUserId;
    private int status;
    private long createdTime;
    private long updatedTime;

    public long getUserId() {
        return userId;
    }

    public SubscribeRecord setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getFrromUserId() {
        return frromUserId;
    }

    public SubscribeRecord setFrromUserId(long frromUserId) {
        this.frromUserId = frromUserId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public SubscribeRecord setStatus(int status) {
        this.status = status;
        return this;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public SubscribeRecord setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public SubscribeRecord setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    public String toString() {
        return "SubscribeRecord{"
            + "userId="
            + userId
            + ", frromUserId="
            + frromUserId
            + ", status="
            + status
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
        SubscribeRecord that = (SubscribeRecord) o;
        return userId == that.userId
            && frromUserId == that.frromUserId
            && status == that.status
            && createdTime == that.createdTime
            && updatedTime == that.updatedTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, frromUserId, status, createdTime, updatedTime);
    }
}
