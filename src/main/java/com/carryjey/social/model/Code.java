package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class Code implements Serializable {

    private static final long serialVersionUID = -6008460350253418202L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    // 关联是哪个用户的验证码
    private long userId;
    // 邮件链接里附带上的验证码
    private String code;
    private Date inTime;
    // 过期时间
    private Date expireTime;
    // 要修改的邮箱
    private String email;
    // 是否使用过
    private Boolean used;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
