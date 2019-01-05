package com.carryjey.social.util;

/**
 * Created by tomoya at 2018/12/17
 */
public abstract class Constants {

    private Constants() {}

    /**
     * 字段名字常量定义 redis命名
     */
    public static final String REDIS_SYSTEM_CONFIG_KEY = "system_config";

    public static final String REDIS_SCORE_RANK_LIST_KEY = "score_rank_list";

    public static final String FIELD_NAME_USER_ID = "userId";
    public static final String FIELD_NAME_UPDATED_TIME = "updatedTime";
    public static final String FIELD_NAME_VERSION = "version";

    /**
     * 标志位常量定义
     */
    public static final int OPEN_SUBSCRIBE_STATUS = 1;

    public static final int CLOSE_SUBSCRIBE_STATUS = 0;

    public static final int COMMENT = 0;
    public static final int REPLY = 1;
    public static final int COLLECT = 2;
    public static final int VOTE_TOPIC = 3;
    public static final int VOTE_COMMENT = 4;
}
