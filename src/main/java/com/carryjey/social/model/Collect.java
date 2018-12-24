package com.carryjey.social.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class Collect implements Serializable {

    private static final long serialVersionUID = 7610730966340643542L;
    private Integer topicId;
    private long userId;
    private Date inTime;

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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}
