package com.carryjey.social.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = -652489649240623801L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 配置键
    @TableField("`key`")
    private String key;
    // 配置值
    @TableField("`value`")
    private String value;
    // 配置描述
    private String description;
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
